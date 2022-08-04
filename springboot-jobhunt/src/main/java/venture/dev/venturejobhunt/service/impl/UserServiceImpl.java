package venture.dev.venturejobhunt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import venture.dev.venturejobhunt.dao.UserDao;
import venture.dev.venturejobhunt.domain.User;
import venture.dev.venturejobhunt.service.UserService;
import venture.dev.venturejobhunt.utils.CMd5;

import java.util.Calendar;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;


    @Override
    public User login(String u, String password) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getPassword, CMd5.encrypt(password)).eq(User::getPhone,u).or().eq(User::getEmail,u).eq(User::getPassword, CMd5.encrypt(password));
        User user = userDao.selectOne(lqw);
        return user;
    }

    @Override
    public boolean register(User user) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR,1970);
        long time = ca.getTime().getTime();
        user.setLastsign(time);//默认时间戳值1970
        user.setBalance(50);//注册送50
        user.setPassword(CMd5.encrypt(user.getPassword()));
        try {
            return userDao.insert(user) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getInfo(int id) {
        return userDao.selectById(id);
    }

    @Override
    public boolean update(User user) {
        return userDao.updateById(user) > 0;
    }

    @Override
    public boolean sign(int userid) {
        Calendar ca = Calendar.getInstance();
        //当天没签到可签到，则只需精确到某一天即可，小时分钟秒直接置0
        ca.set(Calendar.HOUR_OF_DAY,0);
        ca.set(Calendar.MINUTE,0);
        ca.set(Calendar.SECOND,0);
        ca.set(Calendar.MILLISECOND,0);
        long last = userDao.selectLastSign(userid);//获取数据库时间戳
        long now = ca.getTime().getTime();//获取当前时间戳
        if(last < now){
            userDao.updateSign(userid,now);
            userDao.increaseBalance(userid);
            return true;
        }else {
            return false;
        }
    }


    public IPage<User> getAll(int current, int size, String key){
        IPage<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotBlank(key),User::getEmail,key).or().
                like(Strings.isNotBlank(key),User::getPhone,key).or().like(Strings.isNotBlank(key),User::getUsername,key);
        IPage<User> userIPage = userDao.selectPage(page, lqw);
        return userIPage;
    }

    public boolean delete(int id){
        return userDao.deleteById(id) > 0;
    }

    public String getPassword(int id){
        return userDao.selectPassword(id);
    }
}

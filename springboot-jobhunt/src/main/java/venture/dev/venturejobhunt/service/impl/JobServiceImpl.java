package venture.dev.venturejobhunt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import venture.dev.venturejobhunt.dao.JobDao;
import venture.dev.venturejobhunt.dao.UserDao;
import venture.dev.venturejobhunt.domain.Job;
import venture.dev.venturejobhunt.domain.JobWithUsername;
import venture.dev.venturejobhunt.domain.Statistic;
import venture.dev.venturejobhunt.service.JobService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobDao jobDao;
    @Autowired
    UserDao userDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //查job
    @Override
    public IPage<Job> findAll(int current, int size, String key,boolean user,int uid) {
        IPage<Job> jobIPage = new Page<>(current,size);
        LambdaQueryWrapper<Job> jqw = new LambdaQueryWrapper<>();
        jqw.like(Strings.isNotBlank(key),Job::getTitle,key).eq(user,Job::getUid,uid).orderByDesc(Job::getUid);
        return jobDao.selectPage(jobIPage, jqw);
    }

    //查job以及job所属用户的名称
    @Override
    public IPage<JobWithUsername> findAllWithUser(int current, int size, String key,String cname,boolean topping) {
        Page<JobWithUsername> jobWithUsernamePage = new Page<>(current, size);
        LambdaQueryWrapper<JobWithUsername> lqw = new LambdaQueryWrapper<>();
        //根据关键字是否为空以关键字进行查询，并且只查询已经上架的和设置为展示的job
        lqw.like(Strings.isNotBlank(key),JobWithUsername::getTitle,key).eq(JobWithUsername::isKeeping,true)
                .eq(JobWithUsername::isShowing,true).eq(Strings.isNotBlank(cname),JobWithUsername::getCname,cname).eq(JobWithUsername::isTopping,topping);
        jobDao.selectListWithUsernamer(jobWithUsernamePage,lqw);
        return jobWithUsernamePage;
    }

    @Override
    public JobWithUsername findOneWithUser(int id) {
        Page<JobWithUsername> jobPage = new Page<>();
        QueryWrapper<JobWithUsername> wrapper = new QueryWrapper<>();
        wrapper.eq("l.id",id);
        IPage<JobWithUsername> jobWithUsernameIPage = jobDao.selectListWithUsernamer(jobPage, wrapper);
        try {
        JobWithUsername jobWithUsername = jobWithUsernameIPage.getRecords().get(0);
        //访问完访问量则+1
        if(jobWithUsername != null){
            jobWithUsername.setView(jobWithUsername.getView() + 1);
            jobDao.updateById(jobWithUsername);
        }

            return jobWithUsername;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    @Override
    public Job findById(int jid,int uid) {
        LambdaQueryWrapper<Job> jqw = new LambdaQueryWrapper<>();
        jqw.eq(Job::getId,jid).eq(uid != 0,Job::getUid,uid);
        return jobDao.selectOne(jqw);
    }

    //用户发布
    @Override
    public boolean add(Job job) {
        int uid = job.getUid();
        job.setKeeping(true);//默认上架
        job.setDate(sdf.format(new Date()));
        if(userDao.getBalanceById(uid) >= 1){//余额大于1才发布
            userDao.reduceBalance(uid);//扣除1
            if(jobDao.insert(job) > 0){
                ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
                QueryWrapper<Statistic> qw = new QueryWrapper<>();
                //用字符串进行操作
                String total = vo.get("jobTotal");
                String today = vo.get("jobToday");
                if(total == null){
                    total = String.valueOf(jobDao.selectCount(null));
                    vo.set("jobTotal",total);
                }else{
                    //发布成功则统计总数+1
                    vo.set("jobTotal",String.valueOf(Integer.parseInt(total) + 1));
                }
                if(today == null){
                    today = "1";
                    vo.set("jobToday",today);
                }else {
                    vo.set("jobToday",String.valueOf(Integer.parseInt(today) + 1));
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //用户更新
    @Override
    public boolean update(Job job) {
        LambdaUpdateWrapper<Job> juw = new LambdaUpdateWrapper<>();
        juw.eq(Job::getId,job.getId()).eq(Job::getUid,job.getUid()).set(Job::getCname,job.getCname()).set(Job::getTitle,job.getTitle())
                .set(Job::getDetail,job.getDetail()).set(Job::getGroupnum,job.getGroupnum()).set(Job::isGuarantee,job.isGuarantee())
                .set(Job::getPrice,job.getPrice()).set(Job::getContactor1,job.getContactor1()).set(Job::getContactor2,job.getContactor2())
                .set(Job::isShowing,job.isShowing());
        return jobDao.update(null,juw) > 0;
    }

    //删除
    @Override
    public boolean delete(int id, int uid) {
        LambdaQueryWrapper<Job> jqw = new LambdaQueryWrapper<>();
        jqw.eq(Job::getId,id).eq(uid != 0,Job::getUid,uid);
        return jobDao.delete(jqw) > 0;
    }

    // 全站帖子数据统计
    @Override
    public Statistic getStatistic() {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        QueryWrapper<Statistic> qw = new QueryWrapper<>();
        //用字符串进行操作
        String total = vo.get("jobTotal");
        String today = vo.get("jobToday");
        if(total == null){
            total = String.valueOf(jobDao.selectCount(null));
            vo.set("jobTotal",total);
        }
        if(today == null){
            today = "0";
            vo.set("jobToday",today);
        }

        return new Statistic(Integer.parseInt(total),Integer.parseInt(today));//转换为整型返回
    }

    // 指定用户帖子数据统计
    public int getSumByUser(int uid){
        LambdaQueryWrapper<Job> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Job::getUid,uid);
        Integer sum = jobDao.selectCount(lqw);
        return sum;
    }

    // 分页获取指定用户所有帖子
    public IPage<JobWithUsername> getAllByUser(int uid,int current,int size){
        Page<JobWithUsername> jwu = new Page<>(current,size);
        LambdaQueryWrapper<JobWithUsername> lqw = new LambdaQueryWrapper<>();
        lqw.eq(JobWithUsername::getUid,uid);
        jobDao.selectListWithUsernamer(jwu, lqw);
        return jwu;
    }


    // 帖子点赞
    public boolean updateThumb(int id){
        return jobDao.updateThumb(id);
    }


    @Override
    public IPage<JobWithUsername> getAllForMaster(int current, int size, String key,String username, String cname, boolean topping, boolean keeping) {
        Page<JobWithUsername> jwu = new Page<>(current,size);
        LambdaQueryWrapper<JobWithUsername> lqw = new LambdaQueryWrapper<>();
        //解决lambda缓存问题
        //TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), JobWithUsername.class);
        lqw.eq(topping,JobWithUsername::isTopping,topping).eq(JobWithUsername::isKeeping,keeping)
                .like(Strings.isNotBlank(key),JobWithUsername::getTitle,key).like(Strings.isNotBlank(username),JobWithUsername::getUsername,username)
                .eq(Strings.isNotBlank(cname),JobWithUsername::getCname,cname);
        jobDao.selectListWithUsernamer(jwu, lqw);
        return jwu;
    }

    @Override
    public boolean updateForMaster(Job job) {
        return jobDao.updateById(job) > 0;
    }
}



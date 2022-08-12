package venture.dev.venturejobhunt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import venture.dev.venturejobhunt.dao.JobDao;
import venture.dev.venturejobhunt.dao.UserDao;
import venture.dev.venturejobhunt.service.GeneralService;
import venture.dev.venturejobhunt.utils.DateUtils;

import java.util.*;

@Service
public class GeneralServiceImpl implements GeneralService {
    @Autowired
    JobDao jobDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<Map<String, Integer>> getJobSort() {
        List<Map<String, Integer>> map = jobDao.selectCountByCname();
        return map;
    }

    @Override
    public List<Map<String, Integer>> getJobPost() {
        List<Map<String, Integer>> list = new ArrayList<>();
        Calendar ca = null;
        for(int i = 1;i <=7;i++){
            // 每次循环都重新获取一边当前日期
            ca = Calendar.getInstance();
            // 获取当前日期的前i天
            Calendar bica = DateUtils.beforeNDays(ca, i);
            int count = 0;
            //// sql group by 分组会找不到时会返回null，这里try...catch进行处理
            try {
                count = jobDao.selectCountBydate(DateUtils.format(bica,
                        DateUtils.DATE_FORMAT));
            } catch (Exception e) {
                count = 0;
            }
            HashMap<String, Integer> map = new HashMap<>();
            // 将当前日期和此日期发布数放入map
            map.put(DateUtils.format(bica, DateUtils.DATE_FORMAT),count);
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Long>> getUserTrend() {
        List<Map<String, Long>> list = new ArrayList<>();
        Calendar ca = null;
        for(int i = 1;i <=7;i++){
            // 每次循环都重新获取一边当前日期
            ca = Calendar.getInstance();
            // 获取当前日期的前i天
            Calendar bica = DateUtils.beforeNDays(ca, i);
            long count = 0;
            // sql group by 分组会找不到时会返回null，这里try...catch进行处理
            try {
                count = userDao.selectCountByRegtime(DateUtils.format(bica,
                        DateUtils.DATE_FORMAT));
            } catch (Exception e) {
                count = 0L;
            }
            HashMap<String, Long> map = new HashMap<>();
            // 将当前日期和此日期发布数放入map
            map.put(DateUtils.format(bica, DateUtils.DATE_FORMAT),count);
            list.add(map);
        }
        return list;
    }

    //用户总数，直接从数据库取
    @Override
    public long userCount() {
        return userDao.selectCount(null);
    }

    @Override
    public long userAlive() {
        long time = DateUtils.beforeNDays(Calendar.getInstance(), 7).getTime().getTime();
        return userDao.selectAlive(time);
    }
    //帖子总数，直接从数据库取
    @Override
    public long jobCount() {
        return jobDao.selectCount(null);
    }

}

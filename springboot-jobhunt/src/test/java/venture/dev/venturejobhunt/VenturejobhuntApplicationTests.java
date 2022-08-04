package venture.dev.venturejobhunt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import venture.dev.venturejobhunt.dao.JobDao;
import venture.dev.venturejobhunt.dao.UserDao;
import venture.dev.venturejobhunt.domain.Job;
import venture.dev.venturejobhunt.domain.JobWithUsername;
import venture.dev.venturejobhunt.service.JobService;
import venture.dev.venturejobhunt.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class VenturejobhuntApplicationTests {
    @Autowired
    JobDao jobDao;

    @Autowired
    UserDao userDao;

    @Autowired
    JobService jobService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testSelectAll(){
        List<Job> jobs = jobDao.selectList(null);
        for (Job job : jobs) {
            System.out.println(job);
        }
    }

    @Test
    void testPage(){
        IPage<Job> jobIPage = new Page<>(1,5);
        IPage<Job> jobIPage1 = jobDao.selectPage(jobIPage, null);
        System.out.println(jobIPage == jobIPage1);
    }

    @Test
    void testBalanceUpdate(){
        System.out.println(userDao.reduceBalance(5));
    }

    @Test
    void testJwU(){
        Page<JobWithUsername> jobWithUsernamePage = new Page<>(1, 3);
        LambdaQueryWrapper<JobWithUsername> lqw = new LambdaQueryWrapper<>();
        lqw.eq(JobWithUsername::getUid,5);
        jobDao.selectListWithUsernamer(jobWithUsernamePage,lqw);
        System.out.println(jobWithUsernamePage.getOrders());
    }

    @Test
    void testJobService(){
        IPage<JobWithUsername> allWithUser = jobService.findAllWithUser(1, 3, "网站",null,true);
        System.out.println(allWithUser.getRecords());
    }

    @Test
    void testRedis(){
        ValueOperations<String, String> svo = stringRedisTemplate.opsForValue();
        svo.set("totaljob","3000");
        System.out.println(svo.get("hjh1"));
    }

    @Test
    void testCanledar(){
        Calendar yesterday = DateUtils.yesterday(null);
        Long time = yesterday.getTime().getTime();
        System.out.println(time);
    }

    @Test
    void testDate(){
        for (int i = 0; i < 100; i++){
            Calendar ca = Calendar.getInstance();
            System.out.println(new Date());
            ca.set(Calendar.HOUR_OF_DAY,0);
            ca.set(Calendar.MINUTE,0);
            ca.set(Calendar.SECOND,0);
            ca.set(Calendar.MILLISECOND,0);
            System.out.println(ca.getTime().getTime());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}

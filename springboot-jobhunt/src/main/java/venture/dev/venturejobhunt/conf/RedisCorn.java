package venture.dev.venturejobhunt.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import venture.dev.venturejobhunt.dao.JobDao;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class RedisCorn {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    JobDao jobDao;

    @Scheduled(cron = "0 0 0  * * ?")
    public void todayTasks() {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set("jobToday","0");
        System.err.println("今天发布数置零 ---" + LocalDateTime.now());
    }

    //每周日零点重新从数据库中统计总文章数
    @Scheduled(cron = "0 0 0 ? * 7")
    public void sumTaskl(){
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        Integer count = jobDao.selectCount(null);
        vo.set("jobTotal",String.valueOf(count));
        System.err.println("---从数据库查总数" + LocalDateTime.now());
    }
}

package venture.dev.venturejobhunt.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class RedisCorn {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 0  * * ?")
    private void configureTasks() {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set("jobToday","0");
        System.err.println("今天发布数置零 ---" + LocalDateTime.now());
    }
}

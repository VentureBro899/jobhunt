package venture.dev.venturejobhunt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import venture.dev.venturejobhunt.dao.NoticeDao;
import venture.dev.venturejobhunt.domain.Notice;
import venture.dev.venturejobhunt.domain.Statistic;
import venture.dev.venturejobhunt.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeDao noticeDao;

    // 公告做缓存处理
    @Autowired
    StringRedisTemplate redis;

    @Override
    public boolean update(Notice notice) {
        ValueOperations<String, String> vo = redis.opsForValue();
        boolean flag = noticeDao.updateById(notice) > 0;
        if(flag){
            vo.set("noticeContent",notice.getContent());
            vo.set("noticeColor",notice.getColor());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Notice get() {
        ValueOperations<String, String> vo = redis.opsForValue();
        //用字符串进行操作
        String content = vo.get("noticeContent");
        String color = vo.get("noticeColor");
        if(content == null){
            content = String.valueOf(noticeDao.selectOne(null).getContent());
            color = String.valueOf(noticeDao.selectOne(null).getColor());
            vo.set("noticeContent",content);
            vo.set("noticeColor",color);
        }
        return new Notice(0,content,color);
    }
}

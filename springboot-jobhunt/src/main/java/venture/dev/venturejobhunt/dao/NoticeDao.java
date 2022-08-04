package venture.dev.venturejobhunt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import venture.dev.venturejobhunt.domain.Notice;

@Mapper
public interface NoticeDao extends BaseMapper<Notice> {
}

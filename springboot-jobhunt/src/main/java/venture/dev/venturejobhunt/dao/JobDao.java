package venture.dev.venturejobhunt.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import venture.dev.venturejobhunt.domain.Job;
import venture.dev.venturejobhunt.domain.JobWithUsername;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobDao extends BaseMapper<Job> {
    /**
     * 自定义多表查询sql，结合分页查询和条件构造器，注意条件构造器需要加 ${ew.customSqlSegment}
     * ${ew.customSqlSegment}代表where *=*
     *
     * @param page
     * @param Wrapper
     * @return java.util.List<venture.dev.venturejobhunt.domain.JobWithUsername>
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/21 20:28
     */

    @Select("select l.*,r.username,r.avatar from jh_job l,jh_user r ${ew.customSqlSegment} and l.uid=r.id ORDER BY id DESC")
    IPage<JobWithUsername> selectListWithUsernamer(IPage page, @Param(Constants.WRAPPER) Wrapper Wrapper);


    @Update("update jh_job set thumb=thumb+1 where id=#{id}")
    boolean updateThumb(int id);

    @Select("select cname name,count(*) value from jh_job group by cname")
    List<Map<String,Integer>> selectCountByCname();

    @Select("select count(*) from jh_job where date=#{date} GROUP BY date ")
    Integer selectCountBydate(String date);
}

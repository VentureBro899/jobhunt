package venture.dev.venturejobhunt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import venture.dev.venturejobhunt.domain.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select balance from jh_user where id=#{id}")
    int getBalanceById(int id);

    // 余额-1
    @Update("update jh_user set balance=balance-1 where id=#{id}")
    boolean reduceBalance(int id);

    // 余额+1
    @Update("update jh_user set balance=balance+1 where id=#{id}")
    boolean increaseBalance(int id);

    // 查询上次签到时间
    @Select("select lastsign from jh_user where id=#{id}")
    long selectLastSign(int id);

    // 最近签到用户数
    @Select("select count(*) from jh_user where lastsign>=#{timestamp}")
    long selectAlive(long timestamp);

    // 签到
    @Update("update jh_user set lastsign=#{now} where id=#{id}")
    boolean updateSign(@Param("id")int id,@Param("now") long now);

    // 获取用户密码
    @Select("select password from jh_user where id=#{id}")
    String selectPassword(int id);

    // 统计指定指定日期用户
    @Select("select count(*) as proportion from jh_user where regdate=#{date} GROUP BY regdate")
    long selectCountByRegtime(String date);


}

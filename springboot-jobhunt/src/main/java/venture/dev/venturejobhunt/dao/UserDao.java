package venture.dev.venturejobhunt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import venture.dev.venturejobhunt.domain.User;

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

    // 签到
    @Update("update jh_user set lastsign=#{now} where id=#{id}")
    boolean updateSign(@Param("id")int id,@Param("now") long now);

    // 获取用户密码
    @Select("select password from jh_user where id=#{id}")
    String selectPassword(int id);
}

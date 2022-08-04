package venture.dev.venturejobhunt.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("jh_user")
public class User {
    private int id;
    private String username;
    @TableField(select = false)
    private String password;
    private String email;
    private String phone;
    private int balance;
    private String area;
    private String avatar;
    private long lastsign;
}

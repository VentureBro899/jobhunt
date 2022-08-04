package venture.dev.venturejobhunt.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("jh_job")
public class Job {
    private int id;
    private String cname;
    private int uid;
    private String title;
    private String detail;
    private int view;
    private int thumb;
    private int groupnum;
    private boolean guarantee;
    private int price;
    private String contactor1;
    private String contactor2;
    private String date;
    private boolean showing;
    private boolean topping;
    private boolean hot;
    private boolean keeping;//上下架
    private String picture;
}

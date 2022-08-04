package venture.dev.venturejobhunt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("jh_ppt")
public class PPT {
    private int id;
    private String description;
    private String image;
    private String router;
    private boolean showing;
}

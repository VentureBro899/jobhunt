package venture.dev.venturejobhunt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    // 网站job总数
    private int total;
    // 今天发布job数
    private int today;
}

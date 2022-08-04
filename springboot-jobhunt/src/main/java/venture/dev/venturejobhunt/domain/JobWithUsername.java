package venture.dev.venturejobhunt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobWithUsername extends Job{
    private String username;
    private String avatar;
}

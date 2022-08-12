package venture.dev.venturejobhunt.service;

import java.util.List;
import java.util.Map;

public interface GeneralService {
    List<Map<String, Integer>> getJobSort();
    List<Map<String, Integer>> getJobPost();
    List<Map<String, Long>> getUserTrend();

    long userCount();
    long userAlive();
    long jobCount();

}

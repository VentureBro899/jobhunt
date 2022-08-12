package venture.dev.venturejobhunt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import venture.dev.venturejobhunt.domain.Job;
import venture.dev.venturejobhunt.domain.JobWithUsername;
import venture.dev.venturejobhunt.domain.Statistic;

public interface JobService {
    /**
     * @param current 当前页码
     * @param size    每页显示条数
     * @param key     关键字查询
     * @return java.util.List<venture.dev.venturejobhunt.domain.Job>
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/11 17:19
     */
    IPage<Job> findAll(int current, int size, String key,boolean usage,int uid);

    /**
     * 通过用户id以及帖子id查找
     * @param jid 帖子id
     * @param uid 用户id
     * @return
     */
    Job findById(int jid,int uid);

    boolean add(Job job);

    boolean update(Job job);

    /**
     * 删除还要传uid，用于验证是否是删除登录用户所属的job
     * @param id
    	 * @param uid
     * @return boolean
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/11 17:24
     */

    boolean delete(int id,int uid);

    /**
     * 多表查询job，返回job与job归属用户的信息
     *
     * @param current
     * @param size
     * @param key
     * @return com.baomidou.mybatisplus.core.metadata.IPage<venture.dev.venturejobhunt.domain.Job>
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/21 19:43
     */

    IPage<JobWithUsername> findAllWithUser(int current, int size, String key,String cname,boolean topping);

    JobWithUsername findOneWithUser(int id);

    Statistic getStatistic();

    boolean updateThumb(int id);

 int getSumByUser(int uid);

 IPage<JobWithUsername> getAllByUser(int uid,int current,int size);

 IPage<JobWithUsername> getAllForMaster(int current,int size,String key,String username,String cname,boolean topping,boolean keeping);

 boolean updateForMaster(Job job);


}

package venture.dev.venturejobhunt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import venture.dev.venturejobhunt.domain.Job;
import venture.dev.venturejobhunt.domain.JobWithUsername;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.domain.Statistic;
import venture.dev.venturejobhunt.exception.UserUnloginException;
import venture.dev.venturejobhunt.service.JobService;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;


    /**
     * 分页获取与通过关键字获取
     * @param current
	 * @param size
	 * @param key 搜索关键字
	 * @param user 是否开启基于用户查询,开启则必须处于登录状态
	 * @param session
     * @return java.util.List<venture.dev.venturejobhunt.domain.Job>
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/18 20:25
     */

    @GetMapping({"/{current}/{size}","/{current}/{size}"})
    public R getJobList(@PathVariable int current, @PathVariable int size,
                                @RequestParam(required = false) String key,@RequestParam(required = false) boolean user,HttpSession session){
        int uid = session.getAttribute("userid") != null ? (int)session.getAttribute("userid") : 0;
        if(user && uid == 0){
            throw new UserUnloginException("用户越权");
        }
        IPage<Job> all = jobService.findAll(current, size, key,user,uid);
        return new R(20000,"success",all.getRecords());
    }

    /**
     * 分页获取帖子以及帖子发布者数据,非置顶
     * @param current
	 * @param size
	 * @param keyword 搜索关键词
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/24 10:20
     */

    @GetMapping("/jau/{current}/{size}")
    public R getJobListWithUser(@PathVariable int current, @PathVariable int size,
                                @RequestParam(required = false) String keyword,@RequestParam(required = false) String cname,
                                @RequestParam(required = false,defaultValue = "false") boolean topping){
        IPage<JobWithUsername> all = jobService.findAllWithUser(current,size,keyword,cname,topping);
        return new R(20000,"success",all.getRecords());
    }

    /**
     * 分页获取指定用户的帖子
     * @param uid 用户id
     * @param current 当前页数
     * @param size 每页大小
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/24 10:19
     */

    @GetMapping("/jau/{uid}/{current}/{size}")
    public R getJobListWithUser(@PathVariable int uid,@PathVariable int current, @PathVariable int size){
        IPage<JobWithUsername> all = jobService.getAllByUser(uid,current,size);
        return new R(20000,"success",all);
    }


   /**
    * 用户更新文章时获取文章数据
    * @param id
   	 * @param session
    * @return venture.dev.venturejobhunt.domain.R
    * @author venture
    * @creed: Nothing Ventured,nothing gained
    * @date 2022/7/24 10:20
    */

    @GetMapping("/{id}")
    public R getOneJob(@PathVariable int id,HttpSession session){
        if(session.getAttribute("userid") == null){
            return new R(30001,"parame error");
        }
       int uid = (int)session.getAttribute("userid");
        Job job = jobService.findById(id, uid);
        if(job != null){
            return new R(20000,"success",job);
        }else {
            return new R(30000,"fail");
        }
    }

    /**
     * 根据jobid获取job，连同发布者用户名以及头像
     * @param id
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/24 10:23
     */

    @GetMapping("/getOneWUA/{id}")
    public R getOneWithUser(@PathVariable int id){
        JobWithUsername jwu = jobService.findOneWithUser(id);
        if(jwu != null){
            return new R(20000,"success",jwu);
        }else{
            return new R(30001,"no exist");
        }

    }

    @GetMapping("/random/{id}")
    public R getOneByRandom(@PathVariable int id){
        R total = this.getTotal();
        Statistic statistic = (Statistic) total.getData();
        int max = statistic.getTotal();
        int i = new Random().nextInt(max);
        int sum = 0;//循环总次数
        JobWithUsername jwu = null;
        while (true) {//随机数索引越界则重取随机数
            try {
                sum++;
                if(sum >= 3) break;
                if(i == id)  i = new Random().nextInt(max);//随机id与对当前文章id相同则重新获取
                jwu = jobService.findOneWithUser(i);
                break;
            } catch (IndexOutOfBoundsException e) {
                i = new Random().nextInt(max);
                continue;
            }
        }

        if(jwu != null){
            return new R(20000,"success",jwu);
        }else{
            return new R(30001,"no exist");
        }
    }

    /**
     * 用户发布帖子
     * @param job
     * @param session 用于鉴别提交时是否是文章所属用户操作
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/24 10:21
     */

    @PostMapping()
    public R addJob(@RequestBody Job job, HttpSession session){
        if(session.getAttribute("userid") == null){
            return new R(30001,"parame error");
        }
        job.setUid((int)session.getAttribute("userid"));
        if(jobService.add(job)){
            return new R(20000,"add success");
        }else {
            return new R(30000,"add fail");
        }
    }

    /**
     * 更新帖子
     * @param job
     * @param session
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/24 10:22
     */

    @PutMapping()
    public R updJob(@RequestBody Job job, HttpSession session){
        if(session.getAttribute("userid") == null){
            return new R(30001,"parame error");
        }
        job.setUid((int)session.getAttribute("userid"));
        if(jobService.update(job)){
            return new R(20000,"add success");
        }else {
            return new R(30000,"add fail");
        }
    }

    // 点赞接口
    @PutMapping("/thumb/{id}")
    public R thumb(@PathVariable int id){
        boolean flag = jobService.updateThumb(id);
        return new R(20000,"success");
    }


    // 获取全站统计数据
    @GetMapping("/getTotal")
    public R getTotal(){
        return new R(20000,"success",jobService.getStatistic());
    }

    // 获取指定用户统计数据
    @GetMapping("/getTotal/{uid}")
    public R getTotal(@PathVariable int uid){
        int sum = jobService.getSumByUser(uid);
        return new R(20000,"success",sum);
    }


}

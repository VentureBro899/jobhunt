package venture.dev.venturejobhunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.service.GeneralService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistic")
public class GeneralController {
    @Autowired
    GeneralService generalService;

    // 文章类型占比
    @GetMapping("/jobsort")
    public R jobSort(){
        try{
            List<Map<String, Integer>> jobSort = generalService.getJobSort();
            return new R(jobSort);
        }catch (Exception e){
            e.printStackTrace();
            return new R(50000);
        }
    }

    // 近七天帖子发布统计
    @GetMapping("/jobpost")
    public R jobPost(){
        try{
            List jobPost = generalService.getJobPost();
            return new R(jobPost);
        }catch (Exception e){
            e.printStackTrace();
            return new R(50000);
        }
    }

    // 近七天用户增长量
    @GetMapping("/usertrend")
    public R userTrend(){
        try{
            List userTrend = generalService.getUserTrend();
            return new R(userTrend);
        }catch (Exception e){
            e.printStackTrace();
            return new R(50000);
        }
    }
    // 用户总数
    @GetMapping("/usertotal")
    public R userToal(){
        try{
            long userCount = generalService.userCount();
            return new R(userCount);
        }catch (Exception e){
            e.printStackTrace();
            return new R(50000);
        }
    }


    // 近七天活跃数
    @GetMapping("/useralive")
    public R userAlive(){
        try{
            long aliveCount = generalService.userAlive();
            return new R(aliveCount);
        }catch (Exception e){
            e.printStackTrace();
            return new R(50000);
        }
    }

    // 帖子总数
    @GetMapping("/jobtotal")
    public R jobToal(){
        try{
            long jobCount = generalService.jobCount();
            return new R(jobCount);
        }catch (Exception e){
            e.printStackTrace();
            return new R(50000);
        }
    }
}

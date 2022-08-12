package venture.dev.venturejobhunt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import venture.dev.venturejobhunt.domain.Job;
import venture.dev.venturejobhunt.domain.JobWithUsername;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.service.JobService;

@RestController
@RequestMapping("/master/job")
public class JobControllerForMaster {
	@Autowired
	JobService jobService;


    /**
     *
     * @param current 当前页
	 * @param size    页大小
     *  ----------------------
     *  以下为可选项
	 * @param title    标题
	 * @param username 用户名
	 * @param cname    分类名
	 * @param topping  是否置顶
	 * @param keeping  是否上架
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/26 10:10
     */

    @GetMapping({"/{current}/{size}"})
    public R getList(@PathVariable int current,@PathVariable int size,@RequestParam(required = false) String title,@RequestParam(required = false) String username
            ,@RequestParam(required = false) String cname,@RequestParam(required = false) boolean topping,@RequestParam(required = false) boolean keeping){
		IPage<JobWithUsername> list = jobService.getAllForMaster(current, size, title, username, cname, topping, keeping);
		return new R(20000,"success",list);
    }

	@GetMapping("/{id}")
	public R getOne(@PathVariable int id){
		Job job = jobService.findById(id, 0);
		if(job != null)
			return new R(20000,"success",job);
		else
			return new R (30001,"param error");
	}

	@PutMapping()
	public R updOne(@RequestBody Job job){
		boolean flag = jobService.updateForMaster(job);
		return new R(flag ? 20000 : 40003 ,"success");
	}

	@DeleteMapping("/{ids}")
	public R delOne(@PathVariable int[] ids){
		try {
			for(int id:ids){
				boolean flag = jobService.delete(id,0);
			}
			return new R(20000,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return new R(30001,"error");
		}
	}
}

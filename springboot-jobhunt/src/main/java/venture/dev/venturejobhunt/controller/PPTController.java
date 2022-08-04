package venture.dev.venturejobhunt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import venture.dev.venturejobhunt.domain.PPT;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.service.PPTService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/master/ppt")
public class PPTController {
    @Autowired
    PPTService pptService;

    @GetMapping("/{id}")
    public R getOne(@PathVariable int id){
        PPT ppt = pptService.getByid(id);
        return new R(ppt);
    }

    @GetMapping("/{current}/{size}")
    public R getList(@PathVariable int current, @PathVariable int size){
        IPage<PPT> all = pptService.findAll(current, size,true);
        return new R(all);
    }

    @PutMapping
    public R updOne(@RequestBody PPT ppt){
        boolean flag = pptService.upd(ppt);
        return new R(flag);
    }

    @DeleteMapping("/{id}")
    public R delOne(@PathVariable int id){
        boolean flag = pptService.del(id);
        return new R(flag);
    }

    @PostMapping()
    public R addOne(@RequestBody PPT ppt){
        boolean flag = pptService.add(ppt);
        return new R(flag);
    }

    /**
     * 前台获取ppt
     * @param
     * @return venture.dev.venturejobhunt.domain.R
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/27 10:28
     */

    @GetMapping()
    public R getAllForUser(){
        IPage<PPT> all = pptService.findAll(1, 10,false);
        return new R(all);
    }
}

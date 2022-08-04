package venture.dev.venturejobhunt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.domain.User;
import venture.dev.venturejobhunt.service.UserService;
import venture.dev.venturejobhunt.utils.CMd5;

@RestController
@RequestMapping("/master/user")
public class UserControllerForMaster {
    @Autowired
    UserService userService;

    @GetMapping({"/{current}/{size}/{key}","/{current}/{size}/"})
    public R getList(@PathVariable int current,@PathVariable int size,@PathVariable(required = false) String key){
        IPage<User> page = userService.getAll(current, size, key);
        return new R(20000,"success",page);
    }

    @GetMapping("/{id}")
    public R getOne(@PathVariable int id){
        User user = userService.getInfo(id);
        return new R(20000,"success",user);
    }

    @PutMapping()
    public R updOne(@RequestBody User user){
        if (Strings.isBlank(user.getPassword())){//未修改密码
            user.setPassword(userService.getPassword(user.getId()));
        }else{//修改了密码
            user.setPassword(CMd5.encrypt(user.getPassword()));
        }
        boolean flag = userService.update(user);
        return new R(20000,"success");
    }

    @DeleteMapping("/{id}")
    public R deleteUser(@PathVariable int id){
        boolean flag = userService.delete(id);
        if(flag){
            return new R(20000,"success");
        }
        return new R(30003,"fail");
    }
}

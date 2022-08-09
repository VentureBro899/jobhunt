package venture.dev.venturejobhunt.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.domain.User;
import venture.dev.venturejobhunt.service.UserService;
import venture.dev.venturejobhunt.utils.CMd5;
import venture.dev.venturejobhunt.utils.FileTypeJudge;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");//日期格式化，以日期归档文件

    @Value("${venture.mysetting.upload-file-path}")
    private String realPath;//注入文件存放的绝对路径

    //登录
    @PostMapping("/login")
    public R login(String account, String password, HttpSession session, HttpServletResponse resp){
        User user = userService.login(account, password);
        if(user != null){
            //用户id存session
            session.setAttribute("userid",user.getId());
            session.setMaxInactiveInterval(3600 * 48);
            return new R(20000,"success",user);
        }else{
            return new R(40000,"fail");
        }
    }

    //注册
    @PostMapping("/register")
    public R register(@RequestBody User user){
        boolean flag = userService.register(user);
        if(flag){
            return new R(20000,"success");
        }else {
            return new R(40001,"fail");
        }
    }
    //登出
    @PostMapping("/logout")
    public R logout(HttpServletRequest req,HttpServletResponse resp){
        req.getSession().invalidate();
        return new R(20000,"logout success");
    }

    //校验登陆状态
    @GetMapping("/islogin")
    public R isLogin(HttpSession session){
        if(session.getAttribute("userid") != null)
            return new R(20000,"logined");
        else
            return new R(30000,"unlogin");
    }

    //忘记密码
    @GetMapping("/forgetpw")
    public R forgetpw(){
        return null;
    }

    @PostMapping("/sign")
    public R sign(HttpSession session){
        int userid = (int)session.getAttribute("userid");
        boolean flag = userService.sign(userid);
        if(flag){
            return new R(20000,"sign success");
        }else{
            return new R(30002,"sign fail");
        }
    }


    @PutMapping("/updateInfo")
    public R updateInfo(String account,String oldPassword,@RequestParam(required = false) String newPassword,String username,String avatar){
        User user = userService.login(account, oldPassword);
        if(user != null){
            if(Strings.isNotBlank(newPassword)){//改了则用新密码
                user.setPassword(CMd5.encrypt(newPassword));
            }else {// 没改则用旧密码
                user.setPassword(CMd5.encrypt(oldPassword));
            }
            user.setUsername(username);
            user.setAvatar(avatar);
            boolean flag = userService.update(user);
            return new R(20000,"success");
        }else{// 原密码不正确
            return new R(30001,"fail");
        }
    }



    @PostMapping("/upload")
    public R upload(MultipartFile uploadFile,HttpServletRequest req) throws IOException {
        String oldName = uploadFile.getOriginalFilename();
        String suffix = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        if(!(FileTypeJudge.isSpecify(uploadFile.getInputStream(),"png")  || FileTypeJudge.isSpecify(uploadFile.getInputStream(),
                "jpg") || FileTypeJudge.isSpecify(uploadFile.getInputStream(),"gif"))){
            return  new R(40003,"not match");
        }
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        folder.setWritable(true,false);
        String filePath="";
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String newName = UUID.randomUUID().toString() +
                suffix;
        try {
            File imgfile = new File(folder, newName);
            imgfile.setWritable(true,false);
            uploadFile.transferTo(imgfile);
            filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                    req.getServerPort() + "/uploadimages/" + format + newName;
          //  System.out.println(filePath);

        } catch (IOException e) {
            e.printStackTrace();
            return new R(40002,"upload fail");
        }
        return new R(20000,"upload success",filePath);
    }

    // 获取登录用户信息
    @GetMapping("/getInfo")
    public R getInfo(HttpSession session){
        User user = userService.getInfo((int) session.getAttribute("userid"));
        return new R(20000,"",user);
    }

    // 获取指定用户信息
    @GetMapping("/getinfo/{uid}")
    public R getInfo(@PathVariable int uid){
        User user = userService.getInfo(uid);
        return new R(20000,"",user);
    }
}

package venture.dev.venturejobhunt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.exception.AuthorizeException;

import javax.servlet.http.HttpSession;
/**
 * 管理员鉴权
 * @param
 * @return
 * @author venture
 * @creed: Nothing Ventured,nothing gained
 * @date 2022/7/27 10:05
 */


//
@Aspect
@Component
public class MasterAuthAspect {


    //指定切点
    @Pointcut("execution(* venture.dev.venturejobhunt.controller.JobControllerForMaster.*(..))" +
            " || execution(* venture.dev.venturejobhunt.controller.UserControllerForMaster.*(..)) || " +
            "execution(* venture.dev.venturejobhunt.controller.PPTController.*(..)) && " +
            "!execution(* venture.dev.venturejobhunt.controller.PPTController.getAllForUser())" +
            "|| execution(* venture.dev.venturejobhunt.controller.NoticeController.*(..))" +
            "&& !execution(* venture.dev.venturejobhunt.controller.NoticeController.get())" +
            "|| execution(* venture.dev.venturejobhunt.controller.GeneralController.*(..))")
    public void verify() {
    }

    //增强
    @Around("verify()")
    public Object doVerify(ProceedingJoinPoint pjp) {
        //获取session对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        int userid = 0;
        try {
            userid = (int)session.getAttribute("userid");
            if (userid == 10000) {// 管理员放行
                Object proceed = pjp.proceed();
                return proceed;
            }else {
                throw new AuthorizeException();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return new R(30001,"用户未登录");
        }
    }
}

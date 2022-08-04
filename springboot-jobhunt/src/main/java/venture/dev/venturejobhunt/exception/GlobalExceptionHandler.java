package venture.dev.venturejobhunt.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import venture.dev.venturejobhunt.domain.R;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public R DulicateEntity(SQLIntegrityConstraintViolationException e){
        System.out.println("============================================捕获sql约束异常================================================");
        System.out.println("时间:" + new Date() + "-----------------------");
        e.printStackTrace();
        System.out.println("=======================================================================================================");
        return new R(30003,"手机号或邮箱已被其他账户使用");
    }

    @ExceptionHandler({AuthorizeException.class,NumberFormatException.class})
    public R AuthReject(Exception e){
        System.out.println("============================================捕获管理员越权异常================================================");
        System.out.println("时间:" + new Date() + "-----------------------");
        e.printStackTrace();
        System.out.println("=======================================================================================================");
        return new R(30000,"权限不足");
    }

}
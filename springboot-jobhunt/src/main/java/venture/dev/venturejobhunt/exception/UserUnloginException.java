package venture.dev.venturejobhunt.exception;

//用户未登录异常
public class UserUnloginException extends RuntimeException{
    public UserUnloginException() {
    }

    public UserUnloginException(String message) {
        super(message);
    }
}

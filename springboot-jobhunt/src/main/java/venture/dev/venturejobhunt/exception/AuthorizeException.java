package venture.dev.venturejobhunt.exception;

public class AuthorizeException extends RuntimeException {
    public AuthorizeException() {
    }

    public AuthorizeException(String message) {
        super("无权访问" + message);
    }
}

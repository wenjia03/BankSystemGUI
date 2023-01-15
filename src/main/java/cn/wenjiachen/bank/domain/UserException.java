package cn.wenjiachen.bank.domain;

public class UserException extends Throwable {
    /**
     *
     */
    public UserException() {
        super();
    }

    /**
     * @param message
     */
    public UserException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}

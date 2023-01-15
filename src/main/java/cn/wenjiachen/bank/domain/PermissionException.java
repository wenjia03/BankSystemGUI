package cn.wenjiachen.bank.domain;

public class PermissionException extends Throwable {
    /**
     *
     */
    public PermissionException() {
        super();
    }

    /**
     * @param message
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}

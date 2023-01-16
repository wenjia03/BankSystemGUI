package cn.wenjiachen.bank.domain;

/**
 * 权限类异常
 * 直接继承Exception类。
 * （其实可以继承Throwable类，这样无需在方法签名中声明异常，且无需考虑该异常和Exception
 * 的相对位置关系，但不方便维护和Debug，所以按标准继承Exception）
 *
 * @author WenjiaChen
 * @date 2023/1/15
 */
@SuppressWarnings("MissingJavadoc")
public class PermissionException extends Exception {


    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}

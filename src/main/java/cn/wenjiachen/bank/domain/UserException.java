package cn.wenjiachen.bank.domain;

/**
 * 用户异常类，用于处理用户相关的异常。
 */
public class UserException extends Throwable {

    /**
     * 用户异常类构造函数
     */
    public UserException() {
        super();
    }

    /**
     * 用户异常类构造函数
     *
     * @param message 异常信息
     */
    public UserException(String message) {
        super(message);
    }

    /**
     * 用户异常类构造函数
     *
     * @param message 异常信息
     * @param cause   异常原因
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}

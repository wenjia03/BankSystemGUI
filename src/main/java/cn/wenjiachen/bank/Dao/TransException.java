package cn.wenjiachen.bank.Dao;

/**
 * @author Wenjia Chen
 * @date 2023/1/817:22
 */
public class TransException extends Exception{
    /**
     * 构造方法
     */
    public TransException() {
        super();
    }

    /**
     * 异常信息
     * @param message 异常信息
     */
    public TransException(String message) {
        super("交易出错：" + message);
    }
}

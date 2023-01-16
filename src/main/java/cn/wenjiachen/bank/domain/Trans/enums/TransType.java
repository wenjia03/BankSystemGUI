package cn.wenjiachen.bank.domain.Trans.enums;

/**
 * 交易类型
 */
public enum TransType {

    /**
     * 存款
     */
    DEPOSIT,
    /**
     * 取款
     */
    WITHDRAW,
    /**
     * 转账
     */
    TRANSFER,
    /**
     * 支付
     */
    PAYMENT,
    /**
     * 止付
     */
    STOP,
    /**
     * 退回
     */
    RETURN,
    /**
     * 冻结
     */
    LOCK
    // 存款、取款、转账、支付、止付、退回
}

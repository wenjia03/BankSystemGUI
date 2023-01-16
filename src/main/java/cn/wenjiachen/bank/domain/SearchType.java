package cn.wenjiachen.bank.domain;

/**
 * 搜索类型
 */
public enum SearchType {
    /**
     * 全部查询
     */
    ALL,
    /**
     * 按照预留电话号码查询
     */
    PHONE,
    /**
     * 按照姓名查询
     */
    NAME,
    /**
     * 按照身份证号查询
     */
    ID_CARD,
    /**
     * 按照储户银行卡号查询
     */
    BANK_CARD,
    /**
     * 按照交易流水号查询
     */
    TRANS_ID,
    /**
     * 按照交易备注查询
     */
    TRANS_NOTE,
    /**
     * 依据交易关联卡号查询
     */
    ASSOCIATED_CARD_ID
}

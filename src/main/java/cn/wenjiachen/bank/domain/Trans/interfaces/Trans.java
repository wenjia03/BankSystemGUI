package cn.wenjiachen.bank.domain.Trans.interfaces;

import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.bank.Bank;

import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/223:49
 */
public interface Trans {
    // 交易日期
    public Date TransDate = null;

    // 交易ID
    public String TransID = null;

    // 交易类型
    public TransType TransType = null;

    // 交易状态
    public TransStatus TransStatus = null;

    // 往来交易行
    public Bank TransFromBank = null;

    public Bank TransToBank = null;



}

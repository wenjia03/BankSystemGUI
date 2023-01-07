package cn.wenjiachen.bank.domain.Trans;

import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.bank.Bank;
import cn.wenjiachen.bank.utils.Tools;

import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/223:49
 */
public class Trans {
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

    // 交易金额
    public Double TransMoney = null;

    // 往来账户
    public String FromID = null;

    public String ToID = null;


    public Trans(Date transDate, String transID, cn.wenjiachen.bank.domain.Trans.enums.TransType transType, cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Bank transFromBank, Bank transToBank, Double transMoney, String fromID, String toID) {

        TransDate = transDate;
        TransID = transID;
        TransType = transType;
        TransStatus = transStatus;
        TransFromBank = transFromBank;
        TransToBank = transToBank;
        TransMoney = transMoney;
        FromID = fromID;
        ToID = toID;
    }

    /**
     * 此处进行修改 ： 无参构造不生成随机ID ： 无参构造方法的实质就是为了DAO的查询使用 如果生成随机ID将无法判断是否查询到
     *
     * ~~无参构造方法默认生成一个随机的ID~~
     */
    public Trans() {
    }

    /**
     * 不带ID的构造方法 会自动创建当前的ID
     *
     * @param transDate 交易时间
     * @param transType 交易类别
     * @param transStatus 交易状态
     * @param transFromBank 交易发起行
     * @param transToBank 交易接收行
     * @param transMoney 交易金额
     * @param fromID 交易发起账户
     * @param toID 交易接受账户
     */
    public Trans(Date transDate, cn.wenjiachen.bank.domain.Trans.enums.TransType transType, cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Bank transFromBank, Bank transToBank, Double transMoney, String fromID, String toID) {
        TransDate = transDate;
        TransID = Tools.generateID();
        TransType = transType;
        TransStatus = transStatus;
        TransFromBank = transFromBank;
        TransToBank = transToBank;
        TransMoney = transMoney;
        FromID = fromID;
        ToID = toID;
    }

    public Date getTransDate() {
        return TransDate;
    }

    public void setTransDate(Date transDate) {
        TransDate = transDate;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public cn.wenjiachen.bank.domain.Trans.enums.TransType getTransType() {
        return TransType;
    }

    public void setTransType(cn.wenjiachen.bank.domain.Trans.enums.TransType transType) {
        TransType = transType;
    }

    public cn.wenjiachen.bank.domain.Trans.enums.TransStatus getTransStatus() {
        return TransStatus;
    }

    public void setTransStatus(cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus) {
        TransStatus = transStatus;
    }

    public Bank getTransFromBank() {
        return TransFromBank;
    }

    public void setTransFromBank(Bank transFromBank) {
        TransFromBank = transFromBank;
    }

    public Bank getTransToBank() {
        return TransToBank;
    }

    public void setTransToBank(Bank transToBank) {
        TransToBank = transToBank;
    }

    public Double getTransMoney() {
        return TransMoney;
    }

    public void setTransMoney(Double transMoney) {
        TransMoney = transMoney;
    }

    /**
     * 通过数据库中的字符串转换为金额
     *
     * @param transMoney 交易金额
     */
    public void setTransMoney(String transMoney) {
        TransMoney = Double.parseDouble(transMoney);
    }

    public String getFromID() {
        return FromID;
    }

    public void setFromID(String fromID) {
        FromID = fromID;
    }

    public String getToID() {
        return ToID;
    }

    public void setToID(String toID) {
        ToID = toID;
    }
}

package cn.wenjiachen.bank.domain.Trans;

import cn.wenjiachen.bank.domain.Profile;
import cn.wenjiachen.bank.domain.User;

import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:03
 */
public class ProfileTrans extends Trans {

    // 往来账户详情
    public Profile fromUserProfile;

    public Profile toUserProfile;

    // 柜员号
    public User Teller;


    /**
     * 交易构造函数
     * 用于DAO和Service之间的数据传输，不公开使用
     *
     * @param transDate   交易日期
     * @param transID     交易ID
     * @param transType   交易类型
     * @param transStatus 交易状态
     * @param transMoney  交易金额
     * @param fromID      出账账户银行卡号（交易发起方）
     * @param toID        入账账户银行卡号（交易接收方）
     * @param tellerUID   柜员UID
     * @param transInfo   交易信息
     */
    public ProfileTrans(Date transDate, String transID, cn.wenjiachen.bank.domain.Trans.enums.TransType transType,
                        cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Double transMoney, String fromID,
                        String toID, String tellerUID, String transInfo) {
        super(transDate, transID, transType, transStatus, transMoney, fromID, toID, tellerUID, transInfo);
    }

    /**
     * 交易构造函数
     */
    public ProfileTrans() {
    }

    /**
     * 交易构造函数，常用于创建交易，会自动生成交易ID
     *
     * @param transDate       交易日期
     * @param transType       交易类型
     * @param transStatus     交易状态
     * @param transMoney      交易金额
     * @param fromID          出账账户银行卡号（交易发起方）
     * @param toID            入账账户银行卡号（交易接收方）
     * @param tellerUID       柜员UID
     * @param transInfo       交易信息
     * @param fromUserProfile 出账账户详情
     * @param toUserProfile   入账账户详情
     * @param teller          柜员User对象
     */
    public ProfileTrans(Date transDate, cn.wenjiachen.bank.domain.Trans.enums.TransType transType,
                        cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Double transMoney,
                        String fromID, String toID, String tellerUID, String transInfo, Profile fromUserProfile,
                        Profile toUserProfile, User teller) {
        super(transDate, transType, transStatus, transMoney, fromID, toID, tellerUID, transInfo);
        this.fromUserProfile = fromUserProfile;
        this.toUserProfile = toUserProfile;
        Teller = teller;
    }

    /**
     * 获取出账账户详情
     *
     * @return 出账账户详情
     */
    public Profile getFromUserProfiles() {
        return fromUserProfile;
    }

    /**
     * 设置出账账户详情
     *
     * @param fromUserProfile 出账账户详情
     */
    public void setFromUserProfiles(Profile fromUserProfile) {
        this.fromUserProfile = fromUserProfile;
        this.FromID = fromUserProfile.getUserBankCardNumber();
    }

    /**
     * 获取入账账户详情
     *
     * @return 入账账户详情
     */
    public Profile getToUserProfiles() {
        return toUserProfile;
    }

    /**
     * 设置入账账户详情
     *
     * @param toUserProfile 入账账户详情
     */
    public void setToUserProfiles(Profile toUserProfile) {
        this.toUserProfile = toUserProfile;
        this.ToID = toUserProfile.getUserBankCardNumber();
    }

    /**
     * 获取柜员User对象
     *
     * @return 柜员User对象
     */
    public User getTeller() {
        return Teller;
    }

    /**
     * 设置柜员User对象
     *
     * @param teller 柜员User对象
     */
    public void setTeller(User teller) {
        Teller = teller;
    }


}

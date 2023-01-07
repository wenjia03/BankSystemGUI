package cn.wenjiachen.bank.domain.Trans;

import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.domain.bank.Bank;

import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:03
 */
public class ProfileTrans extends Trans{

    // 往来账户详情
    public Profiles fromUserProfiles;

    public Profiles toUserProfiles;

    // 柜员号
    public User Teller;


    public ProfileTrans(Date transDate, String transID, cn.wenjiachen.bank.domain.Trans.enums.TransType transType, cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Bank transFromBank, Bank transToBank, Double transMoney, String fromID, String toID) {
        super(transDate, transID, transType, transStatus, transFromBank, transToBank, transMoney, fromID, toID);
    }

    public ProfileTrans(Date transDate, String transID, cn.wenjiachen.bank.domain.Trans.enums.TransType transType, cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Bank transFromBank, Bank transToBank, Double transMoney, String fromID, String toID, Profiles fromUserProfiles, Profiles toUserProfiles, User teller) {
        super(transDate, transID, transType, transStatus, transFromBank, transToBank, transMoney, fromID, toID);
        this.fromUserProfiles = fromUserProfiles;
        this.toUserProfiles = toUserProfiles;
        Teller = teller;
    }
}

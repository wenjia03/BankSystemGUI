package cn.wenjiachen.bank.domain.Trans;

import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.User;


import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:03
 */
public class ProfileTrans extends Trans {

    // 往来账户详情
    public Profiles fromUserProfiles;

    public Profiles toUserProfiles;

    // 柜员号
    public User Teller;


    public ProfileTrans(Date transDate, String transID, cn.wenjiachen.bank.domain.Trans.enums.TransType transType,
                        cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Double transMoney, String fromID,
                        String toID, String tellerUID, String transInfo) {
        super(transDate, transID, transType, transStatus, transMoney, fromID, toID, tellerUID, transInfo);
    }

    public ProfileTrans() {
    }

    public ProfileTrans(Date transDate, cn.wenjiachen.bank.domain.Trans.enums.TransType transType,
                        cn.wenjiachen.bank.domain.Trans.enums.TransStatus transStatus, Double transMoney,
                        String fromID, String toID, String tellerUID, String transInfo, Profiles fromUserProfiles,
                        Profiles toUserProfiles, User teller) {
        super(transDate, transType, transStatus, transMoney, fromID, toID, tellerUID, transInfo);
        this.fromUserProfiles = fromUserProfiles;
        this.toUserProfiles = toUserProfiles;
        Teller = teller;
    }

    public Profiles getFromUserProfiles() {
        return fromUserProfiles;
    }

    public void setFromUserProfiles(Profiles fromUserProfiles) {
        this.fromUserProfiles = fromUserProfiles;
        this.FromID = fromUserProfiles.getUserBankCardNumber();
    }

    public Profiles getToUserProfiles() {
        return toUserProfiles;
    }

    public void setToUserProfiles(Profiles toUserProfiles) {
        this.toUserProfiles = toUserProfiles;
        this.ToID = toUserProfiles.getUserBankCardNumber();
    }

    public User getTeller() {
        return Teller;
    }

    public void setTeller(User teller) {
        Teller = teller;
    }


}

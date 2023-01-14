package cn.wenjiachen.bank.domain;

/**
 * @author Wenjia Chen
 * @date 2023/1/422:34
 */
public class Profiles {

    // 用户真实姓名
    public String userName;

    // 该用户资料绑定的用户UUID
    public String UserUUID;

    // 用户开户行卡号
    public String UserBankCardNumber;


    public Profiles() {
    }

    public Profiles(String userName, String userUUID) {
        this.userName = userName;
        UserUUID = userUUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUUID() {
        return UserUUID;
    }

    public void setUserUUID(String userUUID) {
        UserUUID = userUUID;
    }

    public String getUserBankCardNumber() {
        return UserBankCardNumber;
    }

    public void setUserBankCardNumber(String userBankCardNumber) {
        UserBankCardNumber = userBankCardNumber;
    }
}

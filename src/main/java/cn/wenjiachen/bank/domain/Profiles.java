package cn.wenjiachen.bank.domain;

import cn.wenjiachen.bank.utils.Securities;

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
    public String UserBankCardPassword;

    // 用户开户行卡余额
    public Double UserBankCardBalance;


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

    public void setPassword(String password) {
        UserBankCardPassword = Securities.encryptPassword(password);
    }

    public void setUserBankCardPassword(String userBankCardPassword) {
        UserBankCardPassword = userBankCardPassword;
    }

    public Double getUserBankCardBalance() {
        return UserBankCardBalance;
    }

    public void setUserBankCardBalance(Double userBankCardBalance) {
        UserBankCardBalance = userBankCardBalance;
    }

    /**
     * 判断用户输入的密码是否正确
     *
     * @param password 密码
     */
    public Boolean isPasswordValid(String password) {
        return Securities.comparePasswords(password, UserBankCardPassword);
    }

    public Boolean checkPassword(String password) {
        return Securities.comparePasswords(password, UserBankCardPassword);
    }
}

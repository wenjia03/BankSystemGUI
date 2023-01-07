package cn.wenjiachen.bank.domain;

import cn.wenjiachen.bank.domain.bank.Bank;
import cn.wenjiachen.bank.utils.Securities;

import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/422:34
 */
public class UserProfiles extends Profiles {


    // 用户出生日期
    public Date userBirthDate;

    // 用户开户行
    public Bank UserBank;

    // 用户开户行卡号
    public String UserBankCardNumber;

    // 用户开户行卡密码
    public String UserBankCardPassword;

    // 用户开户行卡余额
    public Double UserBankCardBalance;


    public String phoneNumber;

    public String address;


    public UserProfiles() {
    }

    public UserProfiles(String userName, Date userBirthDate, Bank userBank, String userBankCardNumber, String userBankCardPassword, Double userBankCardBalance, String bindingUserUUID, String phoneNumber, String address) {
        this.userName = userName;
        this.userBirthDate = userBirthDate;
        UserBank = userBank;
        UserBankCardNumber = userBankCardNumber;
        UserBankCardPassword = userBankCardPassword;
        UserBankCardBalance = userBankCardBalance;
        BindingUserUUID = bindingUserUUID;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getUserBankCardPassword() {
        return UserBankCardPassword;
    }

    public String getBindingUserUUID() {
        return BindingUserUUID;
    }

    public void setBindingUserUUID(String bindingUserUUID) {
        BindingUserUUID = bindingUserUUID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(Date userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public Bank getUserBank() {
        return UserBank;
    }

    public void setUserBank(Bank userBank) {
        UserBank = userBank;
    }

    public String getUserBankCardNumber() {
        return UserBankCardNumber;
    }

    public void setUserBankCardNumber(String userBankCardNumber) {
        UserBankCardNumber = userBankCardNumber;
    }


    public void setUserBankCardPassword(String password) {
        UserBankCardPassword = Securities.encryptPassword(password);
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
}

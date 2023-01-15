package cn.wenjiachen.bank.domain;

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




    // 用户开户行卡密码



    public String phoneNumber;

    public String address;

    public String UserIDCard;


    public UserProfiles() {
    }

    public UserProfiles(String UserName, String userBankCardNumber) {
        super.userName = UserName;
        super.UserUUID = Securities.getUUID();
        UserBankCardNumber = userBankCardNumber;
    }

    public UserProfiles(String userName, Date userBirthDate, String userBankCardNumber, String userBankCardPassword, Double userBankCardBalance, String bindingUserUUID, String phoneNumber, String address, String userIDCard) {
        this.userName = userName;
        this.userBirthDate = userBirthDate;
        UserBankCardNumber = userBankCardNumber;
        UserBankCardPassword = userBankCardPassword;
        UserBankCardBalance = userBankCardBalance;
        UserUUID = bindingUserUUID;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.UserIDCard = userIDCard;
    }

    public String getUserBankCardPassword() {
        return UserBankCardPassword;
    }

    public String getUserUUID() {
        return UserUUID;
    }

    public void setUserUUID(String userUUID) {
        UserUUID = userUUID;
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






    public String getUserIDCard() {
        return UserIDCard;
    }

    public void setUserIDCard(String userIDCard) {
        UserIDCard = userIDCard;
    }



    @Override
    public String toString() {
        return "姓名：'" + userName +
                ", 生日：" + userBirthDate +
                ", 银行卡号：'" + UserBankCardNumber +
                ", 电话号：'" + phoneNumber +
                ", 地址：'" + address +
                ", 身份证号：'" + UserIDCard;
    }


}

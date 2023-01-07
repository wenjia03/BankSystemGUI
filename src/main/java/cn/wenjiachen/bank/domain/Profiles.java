package cn.wenjiachen.bank.domain;

import cn.wenjiachen.bank.domain.bank.Bank;
import cn.wenjiachen.bank.utils.Securities;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/422:34
 */
public class Profiles {

    // 用户真实姓名
    public String userName;

    // 该用户资料绑定的用户UUID
    public String BindingUserUUID;


    public Profiles() {
    }

    public Profiles(String userName, String bindingUserUUID) {
        this.userName = userName;
        BindingUserUUID = bindingUserUUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBindingUserUUID() {
        return BindingUserUUID;
    }

    public void setBindingUserUUID(String bindingUserUUID) {
        BindingUserUUID = bindingUserUUID;
    }
}

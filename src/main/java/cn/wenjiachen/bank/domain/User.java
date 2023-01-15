package cn.wenjiachen.bank.domain;

import cn.wenjiachen.bank.utils.Securities;

import java.util.Objects;


/**
 * @author Wenjia Chen
 * @date 2023/1/219:11
 */
public class User {
    /**
     * 安全用户类，最顶层用户，用于处理服务器的登录信息
     */
    private String email;
    private String passHash;
    private String UUID;

    private String MFA;
    private Boolean isMFAEnabled;
    private Boolean isLocked;

    private String permissionGroupID;

    private String userName;

    public User() {
        this.UUID = Securities.getUUID();
    }

    public User(String email, String passHash, String MFA, Boolean isMFAEnabled, Boolean isLocked, String userName) {
        this.email = email;
        this.passHash = passHash;
        this.MFA = MFA;
        this.isMFAEnabled = isMFAEnabled;
        this.isLocked = isLocked;
        this.UUID = Securities.getUUID();
        this.userName = userName;
    }

    public User(Integer id, String email, String passHash, String MFA, Integer isLocked, String UUID, String userName) {
        this.email = email;
        this.passHash = passHash;
        this.MFA = MFA;
        this.isMFAEnabled = MFA.length() != 0;
        this.isLocked = isLocked == 1;
        this.UUID = UUID;
        this.userName = userName;
    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.passHash = Securities.encryptPassword(password);
        this.MFA = "";
        this.isMFAEnabled = false;
        this.isLocked = false;
        this.UUID = Securities.getUUID();
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.passHash = Securities.encryptPassword(password);
    }

    public Boolean isPasswordValid(String password) {
        return Securities.comparePasswords(password, this.passHash);
    }

    public String getEmail() {
        return email;
    }

    public String getPassHash() {
        return passHash;
    }

    public String getMFA() {
        return MFA;
    }

    public Boolean isMFAEnabled() {
        return MFA != null && MFA.length() != 0;
    }

    public Boolean isLocked() {
        return isLocked;
    }

    public String getUUID() {
        return UUID;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", passHash='" + passHash + '\'' +
                ", UUID='" + UUID + '\'' +
                ", MFA='" + MFA + '\'' +
                ", isMFAEnabled=" + isMFAEnabled +
                ", isLocked=" + isLocked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return Objects.equals(this.UUID, ((User) o).UUID);
    }

    public String getPermissionGroupID() {
        return permissionGroupID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMFA(String MFA) {
        this.MFA = MFA;
    }

    public void setMFAEnabled(Boolean MFAEnabled) {
        isMFAEnabled = MFAEnabled;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public void setPermissionGroupID(String permissionGroupID) {
        this.permissionGroupID = permissionGroupID;
    }

    /**
     * 验证该用户的MFA是否正确
     *
     * @param code MFA验证码
     * @return MFA有效性
     */
    public Boolean MFAValidate(Integer code) {
        if (MFA == null || this.MFA.length() == 0) {
            // 用户未开启MFA功能 直接返回false
            return false;
        }
        return Securities.isCodeValid(this.MFA, code);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

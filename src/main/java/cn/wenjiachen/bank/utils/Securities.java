package cn.wenjiachen.bank.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Wenjia Chen
 * @date 2023/1/221:20
 */
public class Securities {
    /**
     * 获取一个随机生成的UUID
     *
     * @return UUID
     */
    public static String getUUID() {

        return UUID.randomUUID().toString();
    }

    /**
     * 比较获取的UUID是否相同
     *
     * @param uuid1 传入的UUID
     * @param uuid2 传入的UUID
     * @return boolean 是否相同
     */
    public static boolean compareUUIDs(String uuid1, String uuid2) {
        return Objects.equals(UUID.fromString(uuid1), UUID.fromString(uuid2));
    }

    // 实现 2FA生成

    /**
     * 生成2FA密钥
     *
     * @return 2FA密钥
     */
    public static String generate2FA() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    /**
     * 验证2FA密钥
     *
     * @param secret 2FA密钥
     * @param code   2FA验证码
     * @return boolean 是否验证成功
     */
    public static boolean isCodeValid(String secret, int code) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(secret, code);
    }

    /**
     * 获取新的2FA验证码
     *
     * @param secret 2FA密钥
     * @return 2FA验证码
     */
    public static Integer get2FACode(String secret) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.getTotpPassword(secret);
    }

    /**
     * 加密密码
     *
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        // 使用BCrypt加密
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    /**
     * 比较密码是否相同
     *
     * @param password 传入的密码
     * @param hash     加密后的密码
     * @return boolean 是否相同
     */
    public static boolean comparePasswords(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static boolean integerToBool(Integer i) {
        return i == 1;
    }

    public static Integer boolToInteger(boolean b) {
        return b ? 1 : 0;
    }
}

package cn.wenjiachen.bank.service.user;

import cn.wenjiachen.bank.Dao.impl.UserProfileDaoImpl;
import cn.wenjiachen.bank.domain.UserProfile;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Zhao Zong
 * @date 2023/1/4 23:27
 */
public class UserProfileService {

    private static UserProfileDaoImpl dao = null;

    static {
        try {
            dao = new UserProfileDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建用户的Profile
     * @param profile 用户的资料
     * @return 返回是否创建成功
     * @throws Exception
     */
    public static Integer createProfile(UserProfile profile) throws SQLException {
        return dao.createProfile(profile);
    }

    /**
     * 用UUID查询用户的资料
     * @param uuid 用户UUID
     * @return 返回用户的用户资料
     *
     * @throws Exception 异常
     */
    public static List<UserProfile> fetchProfileByUserUUID(String uuid) throws SQLException {
        return dao.fetchProfilesByUUID(uuid) ;
    }

    /**
     * 用用户名查询用户的资料
     * @param name 用户name
     * @return 返回用户名为name的用户资料列表
     * @throws Exception
     */
    public static List<UserProfile> fetchProfilesByName(String name) throws SQLException{
        return dao.fetchProfilesByName(name) ;
    }

    /**
     *用用户的电话号码查询用户的资料
     * @param phone
     * @return 返回用户的资料
     * @throws Exception
     */
    public static List<UserProfile> fetchProfilesByPhone(String phone) throws SQLException {
        return dao.fetchProfilesByPhone(phone) ;
    }

    /**
     * 用用户的CardID查询用户的资料
     * @param cardID
     * @return 返回用户的资料
     * @throws Exception
     */
    public static List<UserProfile> fetchProfilesByCardID(String cardID) throws SQLException {
        return dao.fetchProfilesByCardID(cardID) ;
    }

    /**
     * 查询所有用户资料列表
     * @return 返回所有用户资料列表
     * @throws Exception
     */
    public static List<UserProfile> fetchAllProfiles() throws SQLException {
        return dao.fetchAllProfiles() ;
    }

    /**
     * 依据用户的UUID删除用户资料
     * @param profile 用户资料
     * @return 返回删除是否成功
     * @throws Exception
     */
    public static boolean deleteProfile(UserProfile profile) throws SQLException {
        List<UserProfile> profilesList = dao.fetchProfilesByUUID(profile.getUserUUID());
        if(profilesList.size() == 0) {
            return false ;
        } else {
            return  dao.deleteProfile(profile);
        }
    }

    /**
     * 依据用户的UUID修改用户资料
     * @param profile
     * @return 返回修改是否成功
     * @throws Exception
     */
    public static boolean updateProfile(UserProfile profile) throws SQLException {
        List<UserProfile> profilesList = dao.fetchProfilesByUUID(profile.UserUUID);
        if(profilesList.size() == 0) {
            return false ;
        } else {
            return dao.updateProfile(profile);
        }
    }
}

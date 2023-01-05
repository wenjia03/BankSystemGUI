package cn.wenjiachen.bank.service.user;

import cn.wenjiachen.bank.DAO.impl.UserDaoImpl;
import cn.wenjiachen.bank.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/317:22
 */
public class UserService {

    private static UserDaoImpl userDao;

    static {
        try {
            userDao = new UserDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建用户
     *
     * @param email    用户名
     * @param password 密码
     * @return 返回用户信息
     */

    public static User createUser(String email, String password) throws SQLException {
        User user = new User(email, password);
        userDao.createUser(user);
        return user;
    }

    /**
     * 依据UUID删除用户
     *
     * @param uuid uuid
     * @return 返回是否删除成功
     */

    public static Boolean deleteUser(String uuid) throws SQLException {
        return userDao.deleteUserByUUID(uuid);
    }

    /**
     * 修改用户密码
     *
     * @param uuid     uuid
     * @param password 密码
     * @return 返回用户信息
     */

    public static User changePassword(String uuid, String password) throws SQLException {
        List<User> userList = userDao.fetchUserByUUID(uuid);
        if (userList.size() == 0) {
            return null;
        } else {
            User user = userList.get(0);
            user.setPassword(password);
            userDao.changeUserInfo(user);
            return user;
        }
    }

    /**
     * 修改邮箱账户
     *
     * @param uuid  uuid
     * @param email 邮箱
     * @return 修改后的用户对象
     */

    public static User changeEmail(String uuid, String email) throws SQLException {
        List<User> userList = userDao.fetchUserByUUID(uuid);
        if (userList.size() == 0) {
            return null;
        } else {
            User user = userList.get(0);
            user.setEmail(email);
            userDao.changeUserInfo(user);
            return user;
        }
    }

    /**
     * 修改用户所在权限组
     *
     * @param uuid              uuid
     * @param permissionGroupID 权限组ID
     * @return 修改后的用户对象
     */

    public static User changePermissionGroup(String uuid, String permissionGroupID) throws SQLException {
        List<User> userList = userDao.fetchUserByUUID(uuid);
        if (userList.size() == 0) {
            return null;
        } else {
            User user = userList.get(0);
            user.setPermissionGroupID(permissionGroupID);
            userDao.changeUserInfo(user);
            return user;
        }
    }

    /**
     * 启动或者关闭用户MFA
     *
     * @param uuid uuid
     * @param MFA  MFA
     * @return 新用户对象
     */

    public static User changeMFA(String uuid, String MFA) throws SQLException {
        List<User> userList = userDao.fetchUserByUUID(uuid);
        if (userList.size() == 0) {
            return null;
        } else {
            User user = userList.get(0);
            if (MFA.length() == 0) {
                user.setMFA("");
                user.setMFAEnabled(false);
            } else {
                user.setMFA(MFA);
                user.setMFAEnabled(true);
            }
            userDao.changeUserInfo(user);
            return user;
        }
    }

    /**
     * 修改用户锁定状态
     *
     * @param uuid     uuid
     * @param isLocked 锁定状态
     * @return 新用户对象
     */

    public static User changeLock(String uuid, Boolean isLocked) throws SQLException {
        List<User> userList = userDao.fetchUserByUUID(uuid);
        if (userList.size() == 0) {
            return null;
        } else {
            User user = userList.get(0);
            user.setLocked(isLocked);
            userDao.changeUserInfo(user);
            return user;
        }
    }

    /**
     * 依据UUID获取用户信息
     *
     * @param uuid uuid
     * @return 用户信息
     */

    public static User fetchUser(String uuid) {
        List<User> userList = null;
        try {
            userList = userDao.fetchUserByUUID(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert userList != null;
        if (userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 依据邮箱获取用户信息
     *
     * @param email 邮箱
     * @return 用户信息
     */
    public static User fetchUserByEmail(String email) {
        List<User> userList = null;
        try {
            userList = userDao.fetchUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert userList != null;
        if (userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }
}
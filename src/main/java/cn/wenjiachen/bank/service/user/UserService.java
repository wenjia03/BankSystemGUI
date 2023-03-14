package cn.wenjiachen.bank.service.user;

import cn.wenjiachen.bank.Dao.impl.UserDaoImpl;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.exception.UserException;

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
     * @param userName 用户姓名
     * @return 返回用户信息
     * @throws UserException 用户异常
     * @throws SQLException  数据库异常
     */

    public static User createUser(String email, String password, String userName) throws SQLException, UserException {
        if (fetchUserByEmail(email) != null) {
            throw new UserException("当前用户名用户已存在");
        }
        User user = new User(email, password, userName);
        userDao.createUser(user);
        return user;
    }

    /**
     * 创建用户
     *
     * @param email          登录名
     * @param password       密码
     * @param userName       用户姓名
     * @param userPermission 用户权限
     * @return 返回用户信息
     * @throws SQLException  数据库异常
     * @throws UserException 用户异常
     */
    public static User createUser(String email, String password, String userName, String userPermission) throws SQLException, UserException {
        if (fetchUserByEmail(email) != null) {
            throw new UserException("当前用户名用户已存在");
        }
        User user = new User(email, password, userName);
        user.setPermissionGroupID(userPermission);
        userDao.createUser(user);
        return user;
    }

    /**
     * 创建用户
     *
     * @param email          登录名
     * @param password       密码
     * @param userName       用户姓名
     * @param userPermission 用户权限
     * @param MFA            MFA Token
     * @return 返回用户信息
     * @throws SQLException  数据库异常
     * @throws UserException 用户异常
     */
    public static User createUser(String email, String password, String userName, String userPermission, String MFA) throws SQLException, UserException {
        if (fetchUserByEmail(email) != null) {
            throw new UserException("当前用户名用户已存在");
        }
        User user = new User(email, password, userName);
        user.setPermissionGroupID(userPermission);
        user.setMFA(MFA);
        user.setMFAEnabled(true);
        userDao.createUser(user);
        return user;
    }

    /**
     * 依据UUID删除用户
     *
     * @param uuid uuid
     * @return 返回是否删除成功
     * @throws SQLException 数据库异常
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
     * @throws SQLException 数据库异常
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
     * @throws SQLException 数据库异常
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
     * @throws SQLException 数据库异常
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
     * @throws SQLException 数据库异常
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
     * @throws SQLException 数据库异常
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

    /**
     * 依据用户权限组获取用户
     *
     * @param PGID 权限组ID
     * @return 用户列表
     * @throws SQLException 数据库异常
     */
    public static List<User> fetchUserByPermissionGroup(String PGID) throws SQLException {
        return userDao.fetchUserByPermissionGroupID(PGID);
    }

    /**
     * 更新用户
     *
     * @param user 用户对象
     * @return 是否成功
     * @throws SQLException 数据库异常
     */
    public static Boolean updateUser(User user) throws SQLException {
        return userDao.changeUserInfo(user);
    }

    /**
     * 获取所有用户
     *
     * @return 用户列表
     * @throws SQLException 数据库异常
     */
    public static List<User> fetchAllUser() throws SQLException {
        return userDao.fetchAllUsers();
    }
}

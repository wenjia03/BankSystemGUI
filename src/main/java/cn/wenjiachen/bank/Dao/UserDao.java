package cn.wenjiachen.bank.Dao;

import cn.wenjiachen.bank.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据访问层，其为用户提供可直接使用的操作数据库的方法。
 */
public interface UserDao {
    /**
     * 创建用户
     *
     * @param user 用户信息对象
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    Integer createUser(User user) throws SQLException;

    /**
     * 根据用户对象查询用户信息
     *
     * @param user 用户名
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    List<User> fetchUser(User user) throws SQLException;

    /**
     * 依据登录名查询用户信息
     *
     * @param emailAddress 登录名
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    List<User> fetchUserByEmail(String emailAddress) throws SQLException;

    /**
     * 依据UUID查询用户信息
     *
     * @param UUID 用户UUID
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    List<User> fetchUserByUUID(String UUID) throws SQLException;

    /**
     * 依据权限组查询用户信息
     *
     * @param permissionGroupID 权限组ID
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    List<User> fetchUserByPermissionGroupID(String permissionGroupID) throws SQLException;

    /**
     * 删除用户
     *
     * @param user 用户信息对象
     * @return 是否删除成功
     * @throws SQLException 数据库异常
     */
    boolean deleteUser(User user) throws SQLException;

    /**
     * 更新用户信息
     *
     * @return 是否更新成功
     * @throws SQLException 数据库异常
     */
    List<User> fetchAllUsers() throws SQLException;

    /**
     * 更新用户信息
     *
     * @param user 用户信息对象
     * @return 是否更新成功
     * @throws SQLException 数据库异常
     */
    Boolean changeUserInfo(User user) throws SQLException;

}

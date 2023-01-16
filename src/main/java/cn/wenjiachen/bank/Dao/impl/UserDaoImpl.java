package cn.wenjiachen.bank.Dao.impl;

import cn.wenjiachen.bank.Dao.UserDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据访问层，其为用户提供可直接使用的操作数据库的方法。
 *
 * @author Wenjia Chen
 * @date 2023/1/220:40
 */
public class UserDaoImpl implements UserDao {
    private static DataSource ds;

    /**
     * 构造函数 初始化数据源
     *
     * @throws Exception 异常
     */
    public UserDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }

    /**
     * 创建一个用户
     *
     * @param user 用户对象
     * @return 创建的用户对象
     */
    @Override
    public Integer createUser(User user) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_user (" +
                "UUID, email, passHash , MFA , isLocked , permissionGroupID, userName ) VALUES  " +
                "(" +
                "'" + user.getUUID() + "'," +
                "'" + user.getEmail() + "'," +
                "'" + user.getPassHash() + "'," +
                "'" + user.getMFA() + "'," +
                "" + (user.isLocked() ? "1" : "0") +
                ",'" + user.getPermissionGroupID() + "'," +
                "'" + user.getUserName() + "'" +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " User :" + user);
        connection.close();
        return i;
    }

    /**
     * 根据用户对象查询用户信息
     *
     * @param user 用户名
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<User> fetchUser(User user) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_user WHERE " +
                "UUID = '" + user.getUUID() + "' OR " +
                "email = '" + user.getEmail() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet);
        List<User> res = SQLConfig.parseResultSetAll(resultSet, User.class);
        connection.close();
        System.out.println("Fetched " + res.size() + " User :" + user);
        return res;
    }

    /**
     * 依据登录名查询用户信息
     *
     * @param emailAddress 登录名
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<User> fetchUserByEmail(String emailAddress) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_user WHERE " +
                "email = '" + emailAddress + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> res = SQLConfig.parseResultSetAll(resultSet, User.class);
        connection.close();
        if (res == null || res.size() == 0) {
            System.out.println("Fetched 0 User :" + emailAddress);
        } else
            System.out.println("Fetched " + res.size() + " User :" + emailAddress);
        return res;
    }

    /**
     * 依据UUID查询用户信息
     *
     * @param UUID 用户UUID
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<User> fetchUserByUUID(String UUID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_user WHERE " +
                "UUID = '" + UUID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> res = SQLConfig.parseResultSetAll(resultSet, User.class);
        System.out.println("Fetched " + res.size() + " User :" + UUID);
        connection.close();
        return res;
    }

    /**
     * 依据权限组查询用户信息
     *
     * @param permissionGroupID 权限组ID
     * @return 用户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<User> fetchUserByPermissionGroupID(String permissionGroupID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_user WHERE " +
                "permissionGroupID = '" + permissionGroupID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> res = SQLConfig.parseResultSetAll(resultSet, User.class);
        System.out.println("Fetched " + res.size() + " User :" + permissionGroupID);
        connection.close();
        return res;
    }


    /**
     * 删除用户
     *
     * @param user 用户信息对象
     * @return 是否删除成功
     * @throws SQLException 数据库异常
     */
    @Override
    public boolean deleteUser(User user) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_user WHERE " +
                "UUID = '" + user.getUUID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " User :" + user);
        connection.close();
        return i > 0;
    }

    /**
     * 依据UUID删除用户
     *
     * @param uuid UUID
     * @return 是否删除成功
     * @throws SQLException 数据库异常
     */
    public boolean deleteUserByUUID(String uuid) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_user WHERE " +
                "UUID = '" + uuid + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " User :" + uuid);
        connection.close();
        return i > 0;
    }


    /**
     * 更新用户信息
     *
     * @return 是否更新成功
     * @throws SQLException 数据库异常
     */
    @Override
    public List<User> fetchAllUsers() throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_user ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> res = SQLConfig.parseResultSetAll(resultSet, User.class);
        System.out.println("Fetched " + res.size() + " User ");
        connection.close();
        return res;
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息对象
     * @return 是否更新成功
     * @throws SQLException 数据库异常
     */
    public Boolean changeUserInfo(User user) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE sec_user SET " +
                "email = '" + user.getEmail() + "'," +
                "passHash = '" + user.getPassHash() + "'," +
                "MFA = '" + user.getMFA() + "'," +
                "isLocked = " + (user.isLocked() ? "1" : "0") + "," +
                "permissionGroupID = '" + user.getPermissionGroupID() + "'," +
                "userName = '" + user.getUserName() + "'" +
                "WHERE UUID = '" + user.getUUID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " User :" + user);
        connection.close();
        return i > 0;
    }
}

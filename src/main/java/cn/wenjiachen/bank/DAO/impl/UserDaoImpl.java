package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.UserDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/220:40
 */
public class UserDaoImpl implements UserDao {
    private static DataSource ds;

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
     * 使用缺省的 User对象去查询用户
     *
     * @param user
     * @return
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
     * 基于注册emailAddress 去查询对应的用户
     *
     * @param emailAddress
     * @return
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
     * @param UUID
     * @return
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
     * @param permissionGroupID
     * @return
     * @throws SQLException
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
     * @param user
     * @return
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
     * @return
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

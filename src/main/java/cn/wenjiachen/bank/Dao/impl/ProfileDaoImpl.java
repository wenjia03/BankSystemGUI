package cn.wenjiachen.bank.Dao.impl;

import cn.wenjiachen.bank.Dao.ProfileDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:32
 */
public class ProfileDaoImpl implements ProfileDao<Profile> {


    private static DataSource ds;

    public ProfileDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }


    /**
     * 创建储户
     *
     * @param profile 储户信息对象
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    @Override
    public Integer createProfile(Profile profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_profile (" +
                "userName,BindingUserUUID"
                + ")VALUES(" +
                "'" + profile.getUserName() + "'," +
                "'" + profile.getUserUUID() + "'," +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " Profile :" + profile.getUserName());
        connection.close();
        return i;
    }

    /**
     * 根据储户名查询储户信息
     *
     * @param name 储户名
     * @return 储户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<Profile> fetchProfilesByName(String name) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE userName = '" + name + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profile> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profile profile = new Profile();
            profile.setUserName(resultSet.getString("userName"));
            profile.setUserUUID(resultSet.getString("BindingUserUUID"));
            profiles.add(profile);
        }
        connection.close();
        return profiles;
    }

    /**
     * 根据储户UUID查询储户信息
     *
     * @param UUID 储户UUID
     * @return 储户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<Profile> fetchProfilesByUUID(String UUID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE BindingUserUUID = '" + UUID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profile> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profile profile = new Profile();
            profile.setUserName(resultSet.getString("userName"));
            profile.setUserUUID(resultSet.getString("BindingUserUUID"));
            profiles.add(profile);
        }
        connection.close();
        return profiles;
    }

    /**
     * 获取所有的储户信息
     *
     * @return 储户信息查询集
     * @throws SQLException 数据库异常
     */
    @Override
    public List<Profile> fetchAllProfiles() throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profile> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profile profile = new Profile();
            profile.setUserName(resultSet.getString("userName"));
            profile.setUserUUID(resultSet.getString("BindingUserUUID"));
            profiles.add(profile);
        }
        connection.close();
        return profiles;
    }

    /**
     * 删除储户
     *
     * @param profile 储户对象
     * @return 删除的结果
     * @throws SQLException 数据库异常
     */
    @Override
    public boolean deleteProfile(Profile profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_profile WHERE userName = '" + profile.getUserName() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " Profile :" + profile.getUserName());
        connection.close();
        return i > 0;
    }

    /**
     * 更新储户信息 以储户UUID为准
     *
     * @param profile 储户对象（更新后的）
     * @return 结果
     * @throws SQLException 数据库异常
     */
    @Override
    public boolean updateProfile(Profile profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE sec_profile SET " +
                "userName = '" + profile.getUserName() + "'" +
                "WHERE BindingUserUUID = '" + profile.getUserName() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " Profile :" + profile.getUserName());
        connection.close();
        return i > 0;
    }
}

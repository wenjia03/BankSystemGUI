package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.ProfileDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Profiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:32
 */
public class ProfileDaoImpl implements ProfileDao<Profiles> {

    // todo 完善文档
    private static DataSource ds;

    public ProfileDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }


    /**
     * @param profile
     * @return
     * @throws Exception
     */
    @Override
    public Integer createProfile(Profiles profile) throws Exception {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_profile (" +
                     "userName,BindingUserUUID"
                     + ")VALUES(" +
                     "'" + profile.getUserName() + "'," +
                     "'" + profile.getBindingUserUUID() + "'," +
                     ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " Profile :" + profile.getUserName());
        return i;
    }

    /**
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public List<Profiles> fetchProfilesByName(String name) throws Exception {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE userName = '" + name + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profiles> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profiles profile = new Profiles();
            profile.setUserName(resultSet.getString("userName"));
            profile.setBindingUserUUID(resultSet.getString("BindingUserUUID"));
            profiles.add(profile);
        }
        return profiles;
    }

    /**
     * @param UUID
     * @return
     * @throws Exception
     */
    @Override
    public List<Profiles> fetchProfilesByUUID(String UUID) throws Exception {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE BindingUserUUID = '" + UUID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profiles> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profiles profile = new Profiles();
            profile.setUserName(resultSet.getString("userName"));
            profile.setBindingUserUUID(resultSet.getString("BindingUserUUID"));
            profiles.add(profile);
        }
        return profiles;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<Profiles> fetchAllProfiles() throws Exception {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profiles> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profiles profile = new Profiles();
            profile.setUserName(resultSet.getString("userName"));
            profile.setBindingUserUUID(resultSet.getString("BindingUserUUID"));
            profiles.add(profile);
        }
        return profiles;
    }

    /**
     * @param profile
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deleteProfile(Profiles profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_profile WHERE userName = '" + profile.getUserName() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " Profile :" + profile.getUserName());
        return i > 0;
    }

    /**
     * @param profile
     * @return
     * @throws SQLException
     */
    @Override
    public boolean updateProfile(Profiles profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE sec_profile SET " +
                     "userName = '" + profile.getUserName() + "'" +
                     "WHERE BindingUserUUID = '" + profile.getUserName() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " Profile :" + profile.getUserName());
        return i > 0;
    }
}

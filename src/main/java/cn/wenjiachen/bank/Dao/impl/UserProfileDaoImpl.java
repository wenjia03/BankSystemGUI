package cn.wenjiachen.bank.Dao.impl;


import cn.wenjiachen.bank.Dao.ProfileDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.UserProfile;

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
 * @date 2023/1/422:45
 */
public class UserProfileDaoImpl implements ProfileDao<UserProfile> {
    private static DataSource ds;

    /**
     * 构造方法 初始化数据源
     *
     * @throws Exception 异常
     */
    public UserProfileDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }


    /**
     * 创建一个用户资料
     *
     * @param profile 用户资料
     * @return 创建成功的数目
     * @throws SQLException 异常
     */
    @Override
    public Integer createProfile(UserProfile profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_profile (" +
                "userName , userBirthDate,UserBankCardNumber,UserBankCardPassword,UserBankCardBalance,BindingUserUUID,phoneNumber,address,UserIDCard"
                + ")VALUES(" +
                "'" + profile.getUserName() + "'," +
                "" + (profile.userBirthDate != null ? ("'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(profile.getUserBirthDate())) + "'" : "NULL") + "," +
                "'" + profile.getUserBankCardNumber() + "'," +
                "'" + profile.getUserBankCardPassword() + "'," +
                "'" + profile.getUserBankCardBalance().toString() + "'," +
                "'" + profile.getUserUUID() + "'," +
                "'" + profile.getPhoneNumber() + "'," +
                "'" + profile.getAddress() + "'," +
                "'" + profile.getUserIDCard() + "'" +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " Profile :" + profile.getUserName());
        connection.close();
        return i;
    }

    /**
     * 使用用户姓名查询一个用户资料
     *
     * @param name 用户姓名
     * @return 用户资料
     * @throws SQLException 异常
     */
    @Override
    public List<UserProfile> fetchProfilesByName(String name) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE userName = '" + name + "'";
        return getProfiles(connection, sql);
    }

    /**
     * 使用用户UUID查询一个用户资料
     *
     * @param UUID 用户UUID
     * @return 用户资料
     * @throws SQLException 异常
     */
    @Override
    public List<UserProfile> fetchProfilesByUUID(String UUID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE BindingUserUUID = '" + UUID + "'";
        return getProfiles(connection, sql);
    }

    private List<UserProfile> getProfiles(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserProfile> profiles = new ArrayList<>();
        while (resultSet.next()) {
            UserProfile profile = new UserProfile();
            profile.setUserName(resultSet.getString("userName"));
            profile.setUserBirthDate(resultSet.getDate("userBirthDate"));
            profile.setUserBankCardNumber(resultSet.getString("UserBankCardNumber"));
            profile.setUserBankCardPassword(resultSet.getString("UserBankCardPassword"));
            profile.setUserBankCardBalance(Double.parseDouble(resultSet.getString("UserBankCardBalance")));
            profile.setUserUUID(resultSet.getString("BindingUserUUID"));
            profile.setPhoneNumber(resultSet.getString("phoneNumber"));
            profile.setAddress(resultSet.getString("address"));
            profile.setUserIDCard(resultSet.getString("UserIDCard"));
            profiles.add(profile);
        }
        connection.close();
        return profiles;
    }

    /**
     * 依据用户电话号码查询用户资料
     *
     * @param phone 用户电话号码
     * @return 用户资料
     * @throws SQLException 异常
     */
    public List<UserProfile> fetchProfilesByPhone(String phone) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE phoneNumber = '" + phone + "'";
        return getProfiles(connection, sql);
    }

    /**
     * 依据用户银行卡号查询用户资料
     *
     * @param cardID 用户银行卡号
     * @return 用户资料
     * @throws SQLException 异常
     */
    public List<UserProfile> fetchProfilesByCardID(String cardID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE UserBankCardNumber = '" + cardID + "'";
        return getProfiles(connection, sql);
    }

    /**
     * 获取所有的用户资料
     *
     * @return 用户资料
     * @throws SQLException 异常
     */
    @Override
    public List<UserProfile> fetchAllProfiles() throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile";
        return getProfiles(connection, sql);
    }

    /**
     * 依据用户UUID删除用户资料
     *
     * @param profile 用户资料
     * @return 删除成功状态
     * @throws SQLException 异常
     */
    @Override
    public boolean deleteProfile(UserProfile profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_profile WHERE BindingUserUUID = '" + profile.getUserUUID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " Profile :" + profile.getUserName());
        connection.close();
        return i != 0;
    }

    /**
     * 依据用户UUID更新用户资料
     *
     * @param profile 用户资料
     * @return 更新成功状态
     * @throws SQLException 异常
     */
    @Override
    public boolean updateProfile(UserProfile profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE sec_profile SET " +
                "userName = '" + profile.getUserName() + "'," +
                "userBirthDate = " + (profile.userBirthDate != null ? ("'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(profile.getUserBirthDate())) + "'" : "NULL") + "," +
                "UserBankCardNumber = '" + profile.getUserBankCardNumber() + "'," +
                "UserBankCardPassword = '" + profile.getUserBankCardPassword() + "'," +
                "UserBankCardBalance = '" + profile.getUserBankCardBalance().toString() + "'," +
                "phoneNumber = '" + profile.getPhoneNumber() + "'," +
                "address = '" + profile.getAddress() + "'," +
                "UserIDCard = '" + profile.getUserIDCard() + "' " +
                "WHERE BindingUserUUID = '" + profile.getUserUUID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " Profile :" + profile.getUserName());
        connection.close();
        return i != 0;
    }
}

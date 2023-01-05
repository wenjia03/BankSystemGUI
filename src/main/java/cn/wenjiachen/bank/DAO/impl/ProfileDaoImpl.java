package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.BankDao;
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
import java.util.Date;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/422:45
 */
public class ProfileDaoImpl implements ProfileDao {
    private static DataSource ds;

    public ProfileDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }


    /**
     * 创建一个用户资料
     *
     * @param profile 用户资料
     * @return 创建成功的数目
     * @throws SQLException
     */
    @Override
    public Integer createProfile(Profiles profile) throws Exception {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_profile (" +
                     "userName , userBirthDate,UserBankID,UserBankCardNumber,UserBankCardPassword,UserBankCardBalance,BindingUserUUID,phoneNumber,address"
                     + ")VALUES(" +
                     "'" + profile.getUserName() + "'," +
                     "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(profile.getUserBirthDate()) + "'," +
                     "'" + profile.getUserBank().BankID + "'," +
                     "'" + profile.getUserBankCardNumber() + "'," +
                     "'" + profile.getUserBankCardPassword() + "'," +
                     "'" + profile.getUserBankCardBalance().toString() + "'," +
                     "'" + profile.getBindingUserUUID() + "'," +
                     "'" + profile.getPhoneNumber() + "'," +
                     "'" + profile.getAddress() + "'" +
                     ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " Profile :" + profile.getUserName());
        return i;
    }

    /**
     * 使用用户姓名查询一个用户资料
     *
     * @param name 用户姓名
     * @return 用户资料
     * @throws SQLException
     */
    @Override
    public List<Profiles> fetchProfilesByName(String name) throws Exception {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE userName = '" + name + "'";
        return getProfiles(connection, sql);
    }

    /**
     * 使用用户UUID查询一个用户资料
     *
     * @param UUID 用户UUID
     * @return 用户资料
     * @throws Exception 异常
     */
    @Override
    public List<Profiles> fetchProfilesByUUID(String UUID) throws Exception {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_profile WHERE BindingUserUUID = '" + UUID + "'";
        return getProfiles(connection, sql);
    }

    private List<Profiles> getProfiles(Connection connection, String sql) throws Exception {
        BankDao bankDao = new BankDaoImpl();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Profiles> profiles = new ArrayList<>();
        while (resultSet.next()) {
            Profiles profile = new Profiles();
            profile.setUserName(resultSet.getString("userName"));
            profile.setUserBank(bankDao.fetchBankByBankID(resultSet.getString("UserBankID")).get(0));
            profile.setUserBirthDate(resultSet.getDate("userBirthDate"));
            profile.setUserBankCardNumber(resultSet.getString("UserBankCardNumber"));
            profile.setUserBankCardPassword(resultSet.getString("UserBankCardPassword"));
            profile.setUserBankCardBalance(Double.parseDouble(resultSet.getString("UserBankCardBalance")));
            profile.setBindingUserUUID(resultSet.getString("BindingUserUUID"));
            profile.setPhoneNumber(resultSet.getString("phoneNumber"));
            profile.setAddress(resultSet.getString("address"));
            profiles.add(profile);
        }
        return profiles;
    }

    /**
     * 依据用户电话号码查询用户资料
     *
     * @param phone 用户电话号码
     * @return 用户资料
     * @throws SQLException 异常
     */
    @Override
    public List<Profiles> fetchProfilesByPhone(String phone) throws Exception {
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
    @Override
    public List<Profiles> fetchProfilesByCardID(String cardID) throws Exception {
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
    public List<Profiles> fetchAllProfiles() throws Exception {
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
    public boolean deleteProfile(Profiles profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_profile WHERE BindingUserUUID = '" + profile.getBindingUserUUID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " Profile :" + profile.getUserName());
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
    public boolean updateProfile(Profiles profile) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE sec_profile SET " +
                "userName = '" + profile.getUserName() + "'," +
                "UserBankID = '" + profile.getUserBank().getBankID() + "'," +
                "userBirthDate = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(profile.getUserBirthDate()) + "'," +
                "UserBankCardNumber = '" + profile.getUserBankCardNumber() + "'," +
                "UserBankCardPassword = '" + profile.getUserBankCardPassword() + "'," +
                "UserBankCardBalance = '" + profile.getUserBankCardBalance().toString()+ "'," +
                "phoneNumber = '" + profile.getPhoneNumber() + "'," +
                "address = '" + profile.getAddress() + "'" +
                "WHERE BindingUserUUID = '" + profile.getBindingUserUUID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " Profile :" + profile.getUserName());
        return i != 0;
    }
}

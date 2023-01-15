package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.TransDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Trans.ProfileTrans;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.UserProfiles;
import cn.wenjiachen.bank.service.user.UserProfileService;
import cn.wenjiachen.bank.service.user.UserService;
import cn.wenjiachen.bank.utils.Tools;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:10
 */
public abstract class ProfileTransDaoImpl implements TransDao<ProfileTrans> {
    private static DataSource ds;

    public ProfileTransDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();

    }

    /**
     * @param trans
     * @return
     * @throws Exception
     */
    @Override
    public Integer create(ProfileTrans trans) throws SQLException {
        Connection conn = ds.getConnection();
        String sql = "INSERT INTO bse_trans (" +
                "TransDate , TransID , TransType , TransStatus  , TransMoney ," +
                " FromID ,ToID, TellerUID,TransInfo" +
                ")VALUES(" +
                "'" + Tools.dateToDatetime(trans.TransDate) + "'," +
                "'" + trans.TransID + "'," +
                "'" + trans.TransType + "'," +
                "'" + trans.TransStatus + "'," +
                "'" + trans.TransMoney.toString() + "'," +
                "'" + trans.FromID + "'," +
                "'" + trans.ToID + "'," +
                "'" + trans.TellerUID + "'," +
                "'" + trans.getTransInfo() + "'" +
                ")";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        conn.close();
        return i;
    }

    /**
     * @param trans
     * @return
     * @throws Exception
     */
    @Override
    public Boolean delete(ProfileTrans trans) throws SQLException {
        Connection conn = ds.getConnection();
        String sql = "DELECT FROM bse_trans WHERE TransID = '"
                + trans.TransID + "'";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        conn.close();
        return i != 0;
    }

    /**
     * @param trans
     * @return
     * @throws Exception
     */
    @Override
    public Boolean update(ProfileTrans trans) throws SQLException {
        Connection conn = ds.getConnection();
        String sql = "UPDATE bse_trans SET " +
                "TransDate = '" + Tools.dateToDatetime(trans.getTransDate()) + "'," +
                "TransType = " + trans.TransType.name() + "," +
                "TransStatus =" + trans.TransStatus.name() + "," +
                "TransMoney = '" + trans.TransMoney.toString() + "'," +
                "FromID = '" + trans.FromID + "'," +
                "ToID = '" + trans.ToID + "'";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        conn.close();
        return i != 0;
    }

    /**
     * @param transID 交易ID
     * @return
     * @throws Exception
     */
    @Override
    public ProfileTrans fetchTransByTransID(String transID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_trans WHERE " +
                "TransID = '" + transID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        Trans trans = new Trans();
        while (resultSet.next()) {
            trans.TransID = resultSet.getString("TransID");
            trans.TransDate = resultSet.getDate("TransDate");
            trans.TransType = TransType.valueOf(resultSet.getString("TransType"));
            trans.TransStatus = TransStatus.valueOf(resultSet.getString("TransStatus"));
            trans.TransMoney = Double.parseDouble(resultSet.getString("TransMoney"));
            trans.ToID = resultSet.getString("ToID");
            trans.FromID = resultSet.getString("FromID");
            trans.TransInfo = resultSet.getString("TransInfo");
        }
        connection.close();
        return covert(trans);
    }

    /**
     * @param cardID 银行卡号
     * @return
     * @throws Exception
     */
    public List<ProfileTrans> fetchTransAboutCard(String cardID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_trans WHERE " +
                "FromID = '" + cardID + "' OR " +
                "ToID = '" + cardID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Trans> transList = new ArrayList<>();
        while (resultSet.next()) {
            Trans trans = new Trans();
            trans.TransID = resultSet.getString("TransID");
            trans.TransDate = resultSet.getDate("TransDate");
            trans.TransType = TransType.valueOf(resultSet.getString("TransType"));
            trans.TransStatus = TransStatus.valueOf(resultSet.getString("TransStatus"));
            trans.TransMoney = Double.parseDouble(resultSet.getString("TransMoney"));
            trans.ToID = resultSet.getString("ToID");
            trans.FromID = resultSet.getString("FromID");
            trans.TellerUID = resultSet.getString("TellerUID");
            trans.TransInfo = resultSet.getString("TransInfo");
            transList.add(trans);
        }
        connection.close();
        return covert(transList);
    }

    /**
     * @param cardID 银行卡号
     * @param type   交易类型
     * @return
     * @throws Exception
     */
    @Override
    public List<ProfileTrans> fetchTransAboutCard(String cardID, TransType type) throws SQLException {
        List<ProfileTrans> transList = fetchTransAboutCard(cardID);
        List<ProfileTrans> res = new ArrayList<>();
        for (ProfileTrans i : transList) {
            if (i.TransType == type) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * @param cardID 银行卡号
     * @param isFrom 是否为转出交易 真只显示转出交易 假显示转入交易
     * @return
     * @throws Exception
     */
    @Override
    public List<ProfileTrans> fetchTransAboutCard(String cardID, Boolean isFrom) throws SQLException {
        List<ProfileTrans> list = new ArrayList<>();
        List<ProfileTrans> transList = fetchTransAboutCard(cardID);
        for (ProfileTrans t : transList) {
            if (isFrom) {
                if (t.FromID.equals(cardID)) {
                    list.add(t);
                }
            } else {
                if (t.ToID.equals(cardID)) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * @param cardID 银行卡号
     * @param isFrom 是否为转出交易 真只显示转出交易 假显示转入交易
     * @param type   交易类型
     * @return
     * @throws Exception
     */
    @Override
    public List<ProfileTrans> fetchTransAboutCard(String cardID, Boolean isFrom, TransType type) throws SQLException {
        List<ProfileTrans> l = fetchTransAboutCard(cardID, isFrom);
        List<ProfileTrans> transList = new ArrayList<>();
        for (ProfileTrans t : l) {
            if (t.TransType == type) {
                transList.add(t);
            }
        }
        return transList;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<ProfileTrans> fetchAllTrans() throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_trans";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Trans> transList = new ArrayList<>();
        while (resultSet.next()) {
            Trans trans = new Trans();
            trans.TransID = resultSet.getString("TransID");
            trans.TransDate = resultSet.getDate("TransDate");
            trans.TransType = TransType.valueOf(resultSet.getString("TransType"));
            trans.TransStatus = TransStatus.valueOf(resultSet.getString("TransStatus"));
            trans.TransMoney = Double.parseDouble(resultSet.getString("TransMoney"));
            trans.ToID = resultSet.getString("ToID");
            trans.FromID = resultSet.getString("FromID");
            trans.TellerUID = resultSet.getString("TellerUID");
            trans.TransInfo = resultSet.getString("TransInfo");
            transList.add(trans);
        }
        connection.close();
        return covert(transList);
    }


    private ProfileTrans covert(Trans trans) throws SQLException {
        if (trans.getTransID() == null) {
            return null;
        }
        ProfileTrans t = new ProfileTrans(
                trans.getTransDate(),
                trans.getTransID(),
                trans.getTransType(),
                trans.getTransStatus(),
                trans.getTransMoney(),
                trans.getFromID(),
                trans.getToID(),
                trans.getTellerUID(),
                trans.getTransInfo()
        );
        t.Teller = UserService.fetchUser(trans.TellerUID);
        if (t.TransType != TransType.DEPOSIT) {
            // 存款类型没有出账账户
            List<UserProfiles> profilesList = UserProfileService.fetchProfilesByCardID(t.FromID);
            UserProfiles up;
            if (profilesList.size() == 0)
                up = new UserProfiles("已注销", t.FromID);
            else
                up = profilesList.get(0);
            t.fromUserProfiles = up;
        }

        if (t.TransType != TransType.WITHDRAW) {
            // 取款类型没有入账账户
            List<UserProfiles> profilesList = UserProfileService.fetchProfilesByCardID(t.ToID);
            UserProfiles up;
            if (profilesList.size() == 0)
                up = new UserProfiles("已注销", t.ToID);
            else
                up = profilesList.get(0);
            t.toUserProfiles = up;
        }
        return t;
    }


    private List<ProfileTrans> covert(List<Trans> transList) throws SQLException {
        List<ProfileTrans> list = new ArrayList<>();
        for (Trans trans : transList) {
            list.add(covert(trans));
        }
        return list;
    }
}

package cn.wenjiachen.bank.Dao.impl;

import cn.wenjiachen.bank.Dao.TransDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Trans.ProfileTrans;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.UserProfile;
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

    /**
     * 构造函数 获取数据源
     *
     * @throws Exception 异常
     */
    public ProfileTransDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();

    }

    /**
     * 创建交易
     *
     * @param trans 交易信息对象
     * @return 是否创建成功
     * @throws SQLException 数据库异常
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
     * 删除交易信息
     * <b>经过讨论，该方法在业务中不使用，因为银行管理系统内的交易流水无特殊情况不得删除。</b>
     *
     * @param trans 交易信息对象
     * @return 是否删除成功
     * @throws SQLException 数据库异常
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
     * 更新交易，依据交易的UUID
     *
     * @param trans 交易信息对象
     * @return 是否更新成功
     * @throws SQLException 数据库异常
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
     * 依据交易流水查询交易信息对象
     * 此处断言<b>交易存在唯一流水号，所以不返回结果集</b>
     *
     * @param transID 交易流水号
     * @return 交易信息对象
     * @throws SQLException 数据库异常
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
     * 依据交易相关卡号查询交易信息对象
     *
     * @param cardID 卡号
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
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
     * 依据交易相关卡号查询交易信息对象
     * 该方法为上一个方法的重载，用于查询指定类型的交易，指定更精细的筛选条件
     *
     * @param cardID 卡号
     * @param type   交易类型
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
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
     * 依据交易相关卡号查询交易信息对象
     * 该方法为上一个方法的重载，用于查询指定类型的交易，指定更精细的筛选条件
     *
     * @param cardID 卡号
     * @param isFrom 是否为转出方（true为转出方，false为转入方）
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
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
     * 依据交易相关卡号查询交易信息对象
     * 该方法为上一个方法的重载，用于查询指定类型的交易，指定更精细的筛选条件
     *
     * @param cardID 卡号
     * @param type   交易类型
     * @param isFrom 是否为转出方（true为转出方，false为转入方）
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
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
     * 查询所有的交易信息
     *
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
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
            List<UserProfile> profilesList = UserProfileService.fetchProfilesByCardID(t.FromID);
            UserProfile up;
            if (profilesList.size() == 0)
                up = new UserProfile("已注销", t.FromID);
            else
                up = profilesList.get(0);
            t.fromUserProfile = up;
        }

        if (t.TransType != TransType.WITHDRAW) {
            // 取款类型没有入账账户
            List<UserProfile> profilesList = UserProfileService.fetchProfilesByCardID(t.ToID);
            UserProfile up;
            if (profilesList.size() == 0)
                up = new UserProfile("已注销", t.ToID);
            else
                up = profilesList.get(0);
            t.toUserProfile = up;
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

package cn.wenjiachen.bank.Dao.impl;

import cn.wenjiachen.bank.Dao.TransDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
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
 * @date 2023/1/616:25
 */
public class TransDaoImpl implements TransDao<Trans> {

    private static DataSource ds;

    /**
     * 无参构造方法，用于获取数据源
     *
     * @throws Exception 异常
     */
    public TransDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }

    /**
     * 依据交易对象创建一个交易项
     *
     * @param trans 交易对象
     * @return 是否创建成功
     * @throws SQLException 创建失败时抛出异常
     */
    @Override
    public Integer create(Trans trans) throws SQLException {
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
     * 依据交易编号删除交易
     *
     * @param trans 交易对象
     * @return 是否删除成功
     * @throws Exception 删除失败时抛出异常
     */
    @Override
    public Boolean delete(Trans trans) throws SQLException {
        Connection conn = ds.getConnection();
        String sql = "DELECT FROM bse_trans WHERE TransID = '"
                + trans.TransID + "'";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        conn.close();
        return i != 0;

    }

    /**
     * 依据交易对象的TransID更新交易
     *
     * @param trans 交易对象
     * @return 是否更新成功
     * @throws Exception 更新失败时抛出异常
     */
    @Override
    public Boolean update(Trans trans) throws SQLException {
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
     * 通过交易ID查询交易
     *
     * @param transID 交易ID
     * @throws Exception 查询失败时抛出异常
     */
    @Override
    public Trans fetchTransByTransID(String transID) throws SQLException {
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
            if (trans.TransType != TransType.WITHDRAW)
                trans.ToID = resultSet.getString("ToID");
            if (trans.TransType != TransType.DEPOSIT)
                trans.FromID = resultSet.getString("FromID");
            trans.TransInfo = resultSet.getString("TransInfo");
        }
        connection.close();
        return trans;
    }

    /**
     * 依据交易有关银行卡号去查询相关交易
     *
     * @param cardID 银行卡号
     * @return 交易列表
     * @throws Exception 查询失败时抛出异常
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID) throws SQLException {
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
            if (trans.TransType != TransType.WITHDRAW)
                trans.ToID = resultSet.getString("ToID");
            if (trans.TransType != TransType.DEPOSIT)
                trans.FromID = resultSet.getString("FromID");
            trans.TellerUID = resultSet.getString("TellerUID");
            trans.TransInfo = resultSet.getString("TransInfo");
            transList.add(trans);
        }
        connection.close();
        return transList;
    }

    /**
     * 限制交易状态的查询
     *
     * @param cardID 银行卡号
     * @param type   交易类型
     * @return 交易列表
     * @throws Exception
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID, TransType type) throws SQLException {
        List<Trans> transList = fetchTransAboutCard(cardID);
        List<Trans> res = new ArrayList<>();
        for (Trans i : transList) {
            if (i.TransType == type) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 查询所有关于此卡的交易 且设置是否只查询转出账户交易
     *
     * @param cardID 银行卡号
     * @param isFrom 是否为转出交易 真只显示转出交易 假显示转入交易
     * @return 交易列表
     * @throws SQLException 异常
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID, Boolean isFrom) throws SQLException {
        List<Trans> list = new ArrayList<>();
        List<Trans> transList = fetchTransAboutCard(cardID);
        for (Trans t : transList) {
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
     * 查询对应的交易
     *
     * @param cardID 银行卡号
     * @param isFrom 是否为转出交易 真只显示转出交易 假显示转入交易
     * @param type   交易类型
     * @return 交易列表
     * @throws Exception 异常
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID, Boolean isFrom, TransType type) throws SQLException {
        List<Trans> l = fetchTransAboutCard(cardID, isFrom);
        List<Trans> transList = new ArrayList<>();
        for (Trans t : l) {
            if (t.TransType == type) {
                transList.add(t);
            }
        }
        return transList;
    }

    /**
     * 查询所有的订单信息
     *
     * @return List 订单的结果集
     * @throws Exception 异常
     */
    @Override
    public List<Trans> fetchAllTrans() throws SQLException {
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
            if (trans.TransType != TransType.WITHDRAW)
                trans.ToID = resultSet.getString("ToID");
            if (trans.TransType != TransType.DEPOSIT)
                trans.FromID = resultSet.getString("FromID");
            trans.TellerUID = resultSet.getString("TellerUID");
            trans.TransInfo = resultSet.getString("TransInfo");
            transList.add(trans);
        }
        connection.close();
        return transList;
    }

}


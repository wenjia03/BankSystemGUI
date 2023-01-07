package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.TransDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.utils.Tools;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/616:25
 */
public class TransDaoImpl implements TransDao<Trans> {

    private static DataSource ds;

    public TransDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }

    /**
     * 依据交易对象创建一个交易项
     *
     * @param trans 交易对象
     * @return 是否创建成功
     * @throws Exception 创建失败时抛出异常
     */
    @Override
    public Integer create(Trans trans) throws Exception {
        Connection conn = ds.getConnection();
        String sql = "INSERT INTO bse_trans (" +
                     "TransDate , TransID , TransType , TransStatus , TransFromBankID , TransToBankID , TransMoney ," +
                     " FromID ,ToID" +
                     ")VALUES(" +
                     "'" + Tools.dateToDatetime(trans.TransDate) + "'," +
                     "'" + trans.TransID + "'," +
                     "'" + trans.TransType + "'," +
                     "'" + trans.TransStatus + "'," +
                     "'" + trans.TransFromBank.getBankID() + "'," +
                     "'" + trans.TransToBank.getBankID() + "'," +
                     "'" + trans.TransMoney.toString() + "'," +
                     "'" + trans.FromID + "'," +
                     "'" + trans.ToID + "'" +
                     ")";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
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
    public Boolean delete(Trans trans) throws Exception {
        Connection conn = ds.getConnection();
        String sql = "DELECT FROM bse_trans WHERE TransID = '"
                     + trans.TransID + "'";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
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
    public Boolean update(Trans trans) throws Exception {
        Connection conn = ds.getConnection();
        String sql = "UPDATE bse_trans SET " +
                     "TransDate = '"       + Tools.dateToDatetime(trans.getTransDate()) + "'," +
                     "TransType = "        + trans.TransType.name() + "," +
                     "TransStatus ="       + trans.TransStatus.name() + "," +
                     "TransFromBankID = '" + trans.TransFromBank.getBankID() + "'," +
                     "TransToBankID = '"   + trans.TransToBank.getBankID() + "'," +
                     "TransMoney = '"      + trans.TransMoney.toString() + "'," +
                     "FromID = '"          + trans.FromID + "'," +
                     "ToID = '"            + trans.ToID + "'";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        return i != 0;
    }

    /**
     * 通过交易ID查询交易
     *
     * @param transID 交易ID
     * @throws Exception 查询失败时抛出异常
     */
    @Override
    public Trans fetchTransByTransID(String transID) throws Exception {
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


        }

    }

    /**
     * @param cardID
     * @return
     * @throws Exception
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID) throws Exception {
        return null;
    }

    /**
     * @param cardID
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID, TransType type) throws Exception {
        return null;
    }

    /**
     * @param cardID
     * @param isFrom
     * @return
     * @throws Exception
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID, Boolean isFrom) throws Exception {
        return null;
    }

    /**
     * @param cardID
     * @param isFrom
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public List<Trans> fetchTransAboutCard(String cardID, Boolean isFrom, TransType type) throws Exception {
        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<Trans> fetchAllTrans() throws Exception {
        return null;
    }

}


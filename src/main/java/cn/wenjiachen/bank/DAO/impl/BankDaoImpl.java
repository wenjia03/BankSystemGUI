package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.BankDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.bank.Bank;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/423:02
 */
public class BankDaoImpl implements BankDao {

    private static DataSource ds;

    public BankDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }


    /**
     * 创建银行
     *
     * @param bank 银行对象
     * @return 整数，0为创建失败，1为创建成功
     * @throws SQLException SQL异常
     */
    @Override
    public Integer createBank(Bank bank) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO bse_bank (" +
                     "bankID, bankName, bankSWIFTID" +
                     ")VALUES(" +
                     "'" + bank.getBankID() + "'," +
                     "'" + bank.getBankName() + "'," +
                     "'" + bank.getBankSWIFTID() + "'" +
                     ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " Bank :" + bank.getBankName());
        return i;
    }

    /**
     * 通过银行对象查找银行
     *
     * @param bank 银行对象
     * @return 银行对象
     * @throws SQLException SQL异常
     */
    @Override
    public List<Bank> fetchBank(Bank bank) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_bank WHERE bankID = '" + bank.getBankID() + "' OR bankName = '" + bank.getBankName() + "' OR bankSWIFTID = '" + bank.getBankSWIFTID() + "'";
        return getBanks(connection, sql);
    }

    /**
     * 通过银行ID查找银行
     *
     * @param bankID 银行ID
     * @return 银行对象列表
     * @throws SQLException SQL异常
     */
    @Override
    public List<Bank> fetchBankByBankID(String bankID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_bank WHERE bankID = '" + bankID + "'";
        return getBanks(connection, sql);
    }

    private List<Bank> getBanks(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Bank> banks = new ArrayList<>();
        if (resultSet.next()) {
            banks.add(new Bank(resultSet.getString("bankName"), resultSet.getString("bankID"), resultSet.getString("bankSWIFTID")));
        }
        return banks;
    }

    /**
     * 通过银行名称查找银行
     *
     * @param bankName 银行名称
     * @return 银行对象列表
     * @throws SQLException SQL异常
     */
    @Override
    public List<Bank> fetchBankByBankName(String bankName) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_bank WHERE bankName = '" + bankName + "'";
        return getBanks(connection, sql);
    }

    /**
     * 通过银行SWIFTID查找银行
     *
     * @param bankSWIFTID 银行SWIFTID
     * @return 银行对象列表
     * @throws SQLException SQL异常
     */
    @Override
    public List<Bank> fetchBankByBankSWIFTID(String bankSWIFTID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_bank WHERE bankSWIFTID = '" + bankSWIFTID + "'";
        return getBanks(connection, sql);
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public List<Bank> fetchAllBanks() throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM bse_bank";
        return getBanks(connection, sql);
    }

    /**
     * 删除银行对象
     *
     * @param bank 银行对象
     * @return 整数，0为删除失败，1为删除成功
     * @throws SQLException SQL异常
     */
    @Override
    public boolean deleteBank(Bank bank) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM bse_bank WHERE bankID = '" + bank.getBankID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " Bank :" + bank.getBankName());
        return i != 0;
    }

    /**
     * 依据当前对象的银行ID更新银行对象
     *
     * @param bank 银行对象
     * @return 操作执行状态
     * @throws SQLException SQL异常
     */
    @Override
    public boolean updateBank(Bank bank) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE bse_bank SET " +
                     "bankName = '" + bank.getBankName() + "'," +
                     "bankSWIFTID = '" + bank.getBankSWIFTID() + "'" +
                     "WHERE bankID = '" + bank.getBankID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " Bank :" + bank.getBankName());
        return i != 0;
    }
}

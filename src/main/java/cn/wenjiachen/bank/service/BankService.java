package cn.wenjiachen.bank.service;

import cn.wenjiachen.bank.DAO.impl.BankDaoImpl;
import cn.wenjiachen.bank.domain.bank.Bank;
import cn.wenjiachen.bank.utils.Securities;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/618:14
 */
public class BankService {

    private static BankDaoImpl dao = null;

    static {
        try {
            dao = new BankDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 创建银行
     * @param bank 银行
     * @return 返回是否创建成功
     * @throws SQLException 数据库执行异常
     */
    public static Boolean create(Bank bank) throws SQLException {
        return dao.createBank(bank) == 1;
    }

    /**
     * 创建一个银行 返回银行对象
     * @param name 银行名称
     * @param swiftid SWIFTID
     * @return 返回银行对象
     * @throws SQLException 数据库执行异常
     */
    public static Bank create(String name,String swiftid) throws SQLException {
        Bank bank = new Bank();
        bank.setBankName(name);
        bank.setBankSWIFTID(swiftid);
        bank.setBankID(Securities.getUUID());
        if (dao.createBank(bank) == 1) {
            return bank;
        }
        return null;
    }

    /**
     * 通过银行ID查询银行
     *
     * @param id 银行ID
     * @return 返回一个银行对象
     * @throws SQLException 数据库执行异常
     */
    public static Bank fetchBankByID(String id) throws SQLException {
        List<Bank> banks = dao.fetchBankByBankID(id);
        if (banks.size() == 1) {
            return banks.get(0);
        }
        return null;
    }

    /**
     * 通过银行名称查询银行
     *
     * @param name 银行名称
     * @return 返回一个List
     * @throws SQLException 数据库执行异常
     */
    public static List<Bank> fetchBankByName(String name) throws SQLException {
        return dao.fetchBankByBankName(name);
    }

    /**
     * 通过银行SWIFTID查询银行
     *
     * @param swiftid 银行SWIFTID
     * @return 返回一个List
     * @throws SQLException 数据库执行异常
     */
    public static List<Bank> fetchBankBySWIFTID(String swiftid) throws SQLException {
        return dao.fetchBankByBankSWIFTID(swiftid);
    }

    /**
     * 获取所有的银行对象
     *
     * @return 返回一个List 为结果集
     * @throws SQLException 数据库执行异常
     */
    public static List<Bank> fetchAllBank() throws SQLException {
        return dao.fetchAllBanks();
    }

    /**
     * 更新银行信息
     *
     * @param bank 银行对象
     * @return 返回是否更新成功
     * @throws SQLException 数据库执行异常
     */
    public static Boolean update(Bank bank) throws SQLException {
        return dao.updateBank(bank);
    }

    /**
     * 删除银行
     *
     * @param bank 银行对象
     * @return 返回是否删除成功
     * @throws SQLException 数据库执行异常
     */
    public static Boolean delete(Bank bank) throws SQLException {
        return dao.deleteBank(bank);
    }

}

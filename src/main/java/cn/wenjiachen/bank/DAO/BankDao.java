package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.bank.Bank;

import java.sql.SQLException;
import java.util.List;

public interface BankDao {
    public Integer createBank(Bank bank) throws SQLException;

    public List<Bank> fetchBank(Bank bank) throws SQLException;

    public List<Bank> fetchBankByBankID(String bankID) throws SQLException;

    public List<Bank> fetchBankByBankName(String bankName) throws SQLException;

    public List<Bank> fetchBankByBankSWIFTID(String bankSWIFTID) throws SQLException;

    public List<Bank> fetchAllBanks() throws SQLException;

    public boolean deleteBank(Bank bank) throws SQLException;

    public boolean updateBank(Bank bank) throws SQLException;


}

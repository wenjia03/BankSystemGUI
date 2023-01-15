package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;

import java.sql.SQLException;
import java.util.List;

public interface TransDao<T extends Trans> {

    public Integer create(T trans) throws SQLException;

    public Boolean delete(T trans) throws SQLException;

    public Boolean update(T trans) throws SQLException;

    public T fetchTransByTransID(String transID) throws SQLException;

    public List<T> fetchTransAboutCard(String cardID) throws SQLException;

    public List<T> fetchTransAboutCard(String cardID,  TransType type) throws SQLException;

    public List<T> fetchTransAboutCard(String cardID,Boolean isFrom) throws SQLException;

    public List<T> fetchTransAboutCard(String cardID, Boolean isFrom, TransType type) throws SQLException;

    public List<T> fetchAllTrans() throws SQLException;


}

package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;

import java.util.List;

public interface TransDao<T extends Trans> {

    public Integer create(T trans) throws Exception;

    public Boolean delete(T trans) throws Exception;

    public Boolean update(T trans) throws Exception;

    public T fetchTransByTransID(String transID) throws Exception;

    public List<T> fetchTransAboutCard(String cardID) throws Exception;

    public List<T> fetchTransAboutCard(String cardID,  TransType type) throws Exception;

    public List<T> fetchTransAboutCard(String cardID,Boolean isFrom) throws Exception;

    public List<T> fetchTransAboutCard(String cardID, Boolean isFrom, TransType type) throws Exception;

    public List<T> fetchAllTrans() throws Exception;


}

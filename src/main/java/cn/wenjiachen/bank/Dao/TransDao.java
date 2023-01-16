package cn.wenjiachen.bank.Dao;

import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;

import java.sql.SQLException;
import java.util.List;

/**
 * 交易信息数据访问层，其为交易信息提供可直接使用的操作数据库的方法。
 * Trans类存在父子集关系，对于父子关系，体现在“权限”上。子类拥有父类更详细的信息，用于处理更细致的操作。
 * 但是，基本操作（如查询）不需要子类的信息，因此，对于基本操作，只需要父类的信息即可。所以基本操作相同。
 * 所以两个类实现同一个接口，该接口泛化。
 *
 * @param <T> 是Trans本身和其衍生类。
 */
public interface TransDao<T extends Trans> {

    /**
     * 创建交易
     *
     * @param trans 交易信息对象
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    public Integer create(T trans) throws SQLException;

    /**
     * 删除交易信息
     * <b>经过讨论，该方法在业务中不使用，因为银行管理系统内的交易流水无特殊情况不得删除。</b>
     *
     * @param trans 交易信息对象
     * @return 是否删除成功
     * @throws SQLException 数据库异常
     */
    public Boolean delete(T trans) throws SQLException;


    /**
     * 更新交易，依据交易的UUID
     *
     * @param trans 交易信息对象
     * @return 是否更新成功
     * @throws SQLException 数据库异常
     */
    public Boolean update(T trans) throws SQLException;

    /**
     * 依据交易流水查询交易信息对象
     * 此处断言<b>交易存在唯一流水号，所以不返回结果集</b>
     *
     * @param transID 交易流水号
     * @return 交易信息对象
     * @throws SQLException 数据库异常
     */
    public T fetchTransByTransID(String transID) throws SQLException;

    /**
     * 依据交易相关卡号查询交易信息对象
     *
     * @param cardID 卡号
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchTransAboutCard(String cardID) throws SQLException;

    /**
     * 依据交易相关卡号查询交易信息对象
     * 该方法为上一个方法的重载，用于查询指定类型的交易，指定更精细的筛选条件
     *
     * @param cardID 卡号
     * @param type   交易类型
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchTransAboutCard(String cardID, TransType type) throws SQLException;

    /**
     * 依据交易相关卡号查询交易信息对象
     * 该方法为上一个方法的重载，用于查询指定类型的交易，指定更精细的筛选条件
     *
     * @param cardID 卡号
     * @param isFrom 是否为转出方（true为转出方，false为转入方）
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchTransAboutCard(String cardID, Boolean isFrom) throws SQLException;

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
    public List<T> fetchTransAboutCard(String cardID, Boolean isFrom, TransType type) throws SQLException;

    /**
     * 查询所有的交易信息
     *
     * @return 交易信息对象查询结果集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchAllTrans() throws SQLException;


}

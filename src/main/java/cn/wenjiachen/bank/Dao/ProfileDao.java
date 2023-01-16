package cn.wenjiachen.bank.Dao;

import cn.wenjiachen.bank.domain.Profile;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户信息数据访问层，其为用户信息提供可直接使用的操作数据库的方法。
 * Profile类存在父子集关系，对于父子关系，体现在“权限”上。子类拥有父类更详细的信息，用于处理更细致的操作。
 * 但是，基本操作（如查询）不需要子类的信息，因此，对于基本操作，只需要父类的信息即可。所以基本操作相同。
 * 所以两个类实现同一个接口，该接口泛化。
 *
 * @param <T> 是Profile本身和其衍生类。
 */
public interface ProfileDao<T extends Profile> {

    /**
     * 创建储户
     *
     * @param profile 储户信息对象
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    public Integer createProfile(T profile) throws SQLException;

    /**
     * 根据储户名查询储户信息
     *
     * @param name 储户名
     * @return 储户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchProfilesByName(String name) throws SQLException;

    /**
     * 根据储户UUID查询储户信息
     *
     * @param UUID 储户UUID
     * @return 储户信息列表，一个查询集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchProfilesByUUID(String UUID) throws SQLException;

    /**
     * 获取所有的储户信息
     *
     * @return 储户信息查询集
     * @throws SQLException 数据库异常
     */
    public List<T> fetchAllProfiles() throws SQLException;

    /**
     * 删除储户
     *
     * @param profile 储户对象
     * @return 删除的结果
     * @throws SQLException 数据库异常
     */
    public boolean deleteProfile(T profile) throws SQLException;

    /**
     * 更新储户信息 以储户UUID为准
     *
     * @param profile 储户对象（更新后的）
     * @return 结果
     * @throws SQLException 数据库异常
     */
    public boolean updateProfile(T profile) throws SQLException;

}

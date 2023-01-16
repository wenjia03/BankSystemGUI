package cn.wenjiachen.bank.Dao;

import cn.wenjiachen.bank.domain.Permission.Permissions;

import java.sql.SQLException;
import java.util.List;

public interface PermissionDao {

    /**
     * 创建权限组
     *
     * @param PermissionGroupID   权限组ID
     * @param PermissionGroupName 权限组名称
     * @return 创建的权限组数量
     * @throws SQLException SQL异常
     */
    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName) throws SQLException;

    /**
     * 依据权限组对象创建权限组
     *
     * @param permissions 权限组对象
     * @return 创建的权限组数量
     * @throws SQLException SQL异常
     */
    public Integer createPermissionGroup(Permissions permissions) throws SQLException;

    /**
     * 依据权限组详细信息创建权限组
     *
     * @param PermissionGroupID   权限组ID
     * @param PermissionGroupName 权限组名称
     * @param Permissions         权限组权限，未解析的权限字符串，使用逗号分隔
     * @return 创建的权限组数量
     * @throws SQLException SQL异常
     */
    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName, String Permissions) throws SQLException;

    /**
     * 查找权限组
     *
     * @param PermissionGroupID 权限组ID
     * @return 权限组对象查找集，一个List
     * @throws SQLException SQL异常
     */
    public List<Permissions> fetchPermissionGroup(String PermissionGroupID) throws SQLException;

    /**
     * 依据权限组的UUID删除权限组
     *
     * @param PermissionGroupID 权限组ID
     * @return 是否删除成功
     * @throws SQLException SQL异常
     */
    public boolean deletePermissionGroup(String PermissionGroupID) throws SQLException;

    /**
     * 更新权限组信息，底层依据其UUID进行更新
     *
     * @param permissions 权限组对象
     * @return 是否更新成功
     * @throws SQLException SQL异常
     */
    public boolean updatePermissionGroup(Permissions permissions) throws SQLException;

    /**
     * 返回当前系统中所有的权限组
     *
     * @return 权限组对象查找集，一个List
     * @throws SQLException SQL异常
     */
    public List<Permissions> fetchAllPermissions() throws SQLException;


}

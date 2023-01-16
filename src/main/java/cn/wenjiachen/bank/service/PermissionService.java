package cn.wenjiachen.bank.service;

import cn.wenjiachen.bank.Dao.PermissionDao;
import cn.wenjiachen.bank.Dao.impl.PermissionDaoImpl;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.PermissionException;
import cn.wenjiachen.bank.service.user.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * 权限业务层，其为权限提供可直接使用的操作业务，对DAO进行封装、解耦
 */
public class PermissionService {
    private static PermissionDao dao = null;

    static {
        try {
            dao = new PermissionDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从数据库中获取所有的权限列表
     *
     * @return 权限列表
     * @throws SQLException 数据库异常
     */
    public static List<Permissions> getPermissionsList() throws SQLException {
        return dao.fetchAllPermissions();
    }

    /**
     * 从数据库中获取指定UUID的权限
     * 该方法是上一个方法的重载
     *
     * @param permissionGroupID 权限组UUID
     * @return 权限查找集合
     * @throws SQLException 数据库异常
     */
    public static List<Permissions> getPermissionsList(String permissionGroupID) throws SQLException {
        return dao.fetchPermissionGroup(permissionGroupID);
    }

    /**
     * 创建一个权限组
     *
     * @param permissions 权限组对象
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    public static boolean createPermissionGroup(Permissions permissions) throws SQLException {
        return dao.createPermissionGroup(permissions) == 1;
    }

    /**
     * 创建一个权限组
     * 该方法为上一个方法的重载
     *
     * @param permissionGroupID   权限组UUID
     * @param permissionGroupName 权限组名称
     * @param permission          权限列表
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    public static boolean createPermissionGroup(String permissionGroupID, String permissionGroupName, String permission) throws SQLException {
        return dao.createPermissionGroup(permissionGroupID, permissionGroupName, permission) == 1;
    }

    /**
     * 创建一个权限组，该权限组无任何权限。
     * 该方法为上一个方法的重载
     *
     * @param permissionGroupID   权限组UUID
     * @param permissionGroupName 权限组名称
     * @return 是否创建成功
     * @throws SQLException 数据库异常
     */
    public static boolean createPermissionGroup(String permissionGroupID, String permissionGroupName) throws SQLException {
        return dao.createPermissionGroup(permissionGroupID, permissionGroupName) == 1;
    }

    /**
     * 删除一个权限组
     *
     * @param permissionGroupID 权限组UUID
     * @return 是否删除成功
     * @throws SQLException        数据库异常
     * @throws PermissionException 权限组操作异常
     */
    public static boolean deletePermissionGroup(String permissionGroupID) throws SQLException, PermissionException {
        if (UserService.fetchUserByPermissionGroup(permissionGroupID).size() > 0) {
            throw new PermissionException("当前权限组下存在用户，无法删除");
        }
        return dao.deletePermissionGroup(permissionGroupID);
    }

    /**
     * 更新一个权限组
     *
     * @param permissions 权限组对象
     * @return 是否更新成功
     * @throws SQLException 数据库异常
     */
    public static boolean updatePermissionGroup(Permissions permissions) throws SQLException {
        return dao.updatePermissionGroup(permissions);
    }
}

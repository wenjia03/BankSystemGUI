package cn.wenjiachen.bank.service;

import cn.wenjiachen.bank.DAO.PermissionDao;
import cn.wenjiachen.bank.DAO.impl.PermissionDaoImpl;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.PermissionException;
import cn.wenjiachen.bank.service.user.UserService;

import java.sql.SQLException;
import java.util.List;

public class PermissionService {
    private static PermissionDao dao = null;

    static {
        try {
            dao = new PermissionDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Permissions> getPermissionsList() throws SQLException {
        return dao.fetchAllPermissions();
    }

    public static List<Permissions> getPermissionsList(String permissionGroupID) throws SQLException {
        return dao.fetchPermissionGroup(permissionGroupID);
    }

    public static boolean createPermissionGroup(Permissions permissions) throws SQLException {
        return dao.createPermissionGroup(permissions) == 1;
    }

    public static boolean createPermissionGroup(String permissionGroupID, String permissionGroupName, String permission) throws SQLException {
        return dao.createPermissionGroup(permissionGroupID, permissionGroupName, permission) == 1;
    }

    public static boolean createPermissionGroup(String permissionGroupID, String permissionGroupName) throws SQLException {
        return dao.createPermissionGroup(permissionGroupID, permissionGroupName) == 1;
    }

    public static boolean deletePermissionGroup(String permissionGroupID) throws SQLException, PermissionException {
        if(UserService.fetchUserByPermissionGroup(permissionGroupID).size() > 0){
            throw new PermissionException("当前权限组下存在用户，无法删除");
        }
        return dao.deletePermissionGroup(permissionGroupID);
    }

    public static boolean updatePermissionGroup(Permissions permissions) throws SQLException {
        return dao.updatePermissionGroup(permissions);
    }
}

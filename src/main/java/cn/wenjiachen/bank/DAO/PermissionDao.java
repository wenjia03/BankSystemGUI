package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.Permission.Permissions;

import java.sql.SQLException;
import java.util.List;

public interface PermissionDao {

    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName) throws SQLException;

    public Integer createPermissionGroup(Permissions permissions) throws SQLException;

    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName, String Permissions) throws SQLException;

    public List<Permissions> fetchPermissionGroup(String PermissionGroupID) throws SQLException;

    public boolean deletePermissionGroup(String PermissionGroupID) throws SQLException;

    public boolean updatePermissionGroup(Permissions permissions) throws SQLException;

    public List<Permissions> fetchAllPermissions() throws SQLException;


}

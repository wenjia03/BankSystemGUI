package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.Permission.Permissions;

import java.util.List;

public interface PermissionDao {

    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName) throws Exception;

    public Integer createPermissionGroup(Permissions permissions) throws Exception;

    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName, String Permissions) throws Exception;

    public List<Permissions> fetchPermissionGroup(String PermissionGroupID) throws Exception;

    public boolean deletePermissionGroup(String PermissionGroupID) throws Exception;

    public boolean updatePermissionGroup(Permissions permissions) throws Exception;

    public List<Permissions> fetchAllPermissions() throws Exception;


}

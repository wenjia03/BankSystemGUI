package cn.wenjiachen.bank.domain.Permission.impl;

import cn.wenjiachen.bank.utils.Securities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/315:08
 */
public class PermissionsImpl implements cn.wenjiachen.bank.domain.Permission.Permissions {
    /**
     * 权限组ID
     */
    public String PermissionGroupID = null;

    /**
     * 权限组名称
     */
    public String PermissionGroupName = null;

    /**
     * 权限列表
     */
    public List<String> Permissions = null;

    /**
     * 检查是否拥有某个权限
     *
     * @param permission 权限
     * @return 是否拥有
     */
    public boolean HasPermission(String permission) {
        return Permissions.contains(permission);
    }

    /**
     * 检查是否拥有某些权限
     *
     * @param permissions 权限列表
     * @return 是否拥有
     */
    public boolean HasPermission(List<String> permissions) {
        for (String permission : permissions) {
            if (HasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param permissions
     * @return
     */
    @Override
    public boolean HasPermission(String[] permissions) {
        return HasPermission(Arrays.asList(permissions));
    }

    public String getPermissionGroupID() {
        return PermissionGroupID;
    }

    public void setPermissionGroupID(String permissionGroupID) {
        PermissionGroupID = permissionGroupID;
    }

    public String getPermissionGroupName() {
        return PermissionGroupName;
    }

    public void setPermissionGroupName(String permissionGroupName) {
        PermissionGroupName = permissionGroupName;
    }

    public List<String> getPermissions() {
        return Permissions;
    }

    public String getPermissionsString() {
        return String.join(",", Permissions);
    }

    /**
     * @param permission
     * @return
     */
    @Override
    public boolean addPermission(String permission) {
        if (Permissions.contains(permission)) {
            return false;
        }
        Permissions.add(permission);
        return true;
    }

    /**
     * @param permission
     * @return
     */
    @Override
    public boolean deletePermission(String permission) {
        if (!Permissions.contains(permission)) {
            return false;
        }
        Permissions.remove(permission);
        return true;
    }

    public void setPermissions(List<String> permissions) {
        Permissions = permissions;
    }

    public void setPermissions(String permissions) {
        Permissions = Arrays.asList(permissions.split(","));
    }

    public PermissionsImpl(String permissionGroupName, List<String> permissions) {
        this.PermissionGroupID = Securities.getUUID();
        PermissionGroupName = permissionGroupName;
        Permissions = permissions;
    }

    public PermissionsImpl(String permissionGroupName, String permissions) {
        this.PermissionGroupID = Securities.getUUID();
        PermissionGroupName = permissionGroupName;
        Permissions = Arrays.asList(permissions.split(","));
    }

    public PermissionsImpl(String permissionGroupID, String permissionGroupName, List<String> permissions) {
        PermissionGroupID = permissionGroupID;
        PermissionGroupName = permissionGroupName;
        Permissions = permissions;
    }

    public PermissionsImpl(String permissionGroupID, String permissionGroupName, String permissions) {
        PermissionGroupID = permissionGroupID;
        PermissionGroupName = permissionGroupName;
        Permissions = Arrays.asList(permissions.split(","));
    }

    public PermissionsImpl() {
        this.PermissionGroupID = Securities.getUUID();
        this.Permissions = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PermissionsImpl{" +
                "PermissionGroupID='" + PermissionGroupID + '\'' +
                ", PermissionGroupName='" + PermissionGroupName + '\'' +
                ", Permissions=" + Permissions +
                '}';
    }
}


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
     * 检查当前权限组是否包含指定权限
     * 当前方法的判断逻辑为：在组内只要有一个权限包含，即承认存在权限。
     * 该方法为上一个方法对可变参数的重载，属于对直接字符串数组的支持。
     *
     * @param permissions 权限名称组
     * @return 是否包含
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
     * 增加一个权限
     *
     * @param permission 权限名称
     * @return 是否增加成功
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
     * 删除一个权限
     *
     * @param permission 权限名称
     * @return 是否删除成功
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

    /**
     * 构造函数
     *
     * @param permissionGroupName 权限组名称
     * @param permissions         权限列表
     */
    public PermissionsImpl(String permissionGroupName, List<String> permissions) {
        this.PermissionGroupID = Securities.getUUID();
        PermissionGroupName = permissionGroupName;
        Permissions = permissions;
    }

    /**
     * 构造函数
     *
     * @param permissionGroupName 权限组名称
     * @param permissions         权限列表，以逗号分隔
     */
    public PermissionsImpl(String permissionGroupName, String permissions) {
        this.PermissionGroupID = Securities.getUUID();
        PermissionGroupName = permissionGroupName;
        Permissions = Arrays.asList(permissions.split(","));
    }

    /**
     * 构造函数，用于DAO和Service层的数据传递
     *
     * @param permissionGroupID   权限组ID
     * @param permissionGroupName 权限组名称
     * @param permissions         权限列表
     */
    public PermissionsImpl(String permissionGroupID, String permissionGroupName, List<String> permissions) {
        PermissionGroupID = permissionGroupID;
        PermissionGroupName = permissionGroupName;
        Permissions = permissions;
    }

    /**
     * 构造函数，用于DAO和Service层的数据传递
     *
     * @param permissionGroupID   权限组ID
     * @param permissionGroupName 权限组名称
     * @param permissions         权限列表，以逗号分隔
     */
    public PermissionsImpl(String permissionGroupID, String permissionGroupName, String permissions) {
        PermissionGroupID = permissionGroupID;
        PermissionGroupName = permissionGroupName;
        Permissions = Arrays.asList(permissions.split(","));
    }

    /**
     * 无参构造函数，用于创建新权限业务和DAO层的数据传递
     */
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


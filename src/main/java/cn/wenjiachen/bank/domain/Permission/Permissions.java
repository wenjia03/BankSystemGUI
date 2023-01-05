package cn.wenjiachen.bank.domain.Permission;

import java.util.List;

public interface Permissions {
    public String PermissionGroupID = null;
    public String PermissionGroupName = null;

    public List<String> Permissions = null;

    public boolean HasPermission(String permission);

    public boolean HasPermission(List<String> permissions);

    public String getPermissionGroupID();

    public String getPermissionGroupName();

    public List<String> getPermissions();

    public String getPermissionsString();
}

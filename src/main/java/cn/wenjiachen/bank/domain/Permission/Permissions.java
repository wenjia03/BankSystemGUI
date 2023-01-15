package cn.wenjiachen.bank.domain.Permission;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Permissions {
    public String PermissionGroupID = null;
    public String PermissionGroupName = null;

    public List<String> Permissions = null;

    public boolean HasPermission(String permission);

    public boolean HasPermission(List<String> permissions);
    public boolean HasPermission(String[] permissions);

    public String getPermissionGroupID();

    public String getPermissionGroupName();

    public List<String> getPermissions();

    public String getPermissionsString();

    public void setPermissionGroupID(String permissionGroupID);

    public void setPermissionGroupName(String permissionGroupName);

    public void setPermissions(List<String> permissions);

    public void setPermissions(String permissions);


    public boolean addPermission(String permission);

    public boolean deletePermission(String permission);

    public final static Map<String, String> PermissionMaps =
            Stream.of(new String[][]{
                    {"HIGH_ALL", "所有高级权限"},
                    {"NORMAL_DEPOSITOR_CREATE", "标准储户创建"},
                    {"NORMAL_DEPOSITOR_DELETE", "标准储户删除"},
                    {"NORMAL_DEPOSITOR_FETCH", "标准储户查询"},
                    {"NORMAL_DEPOSITOR_UPDATE", "标准储户更新"},
                    {"HIGH_DEPOSITOR", "高级储户权限"},
                    {"NORMAL_TRANS_CREATE", "标准交易创建"},
                    {"NORMAL_TRANS_FETCH", "标准交易查询"},
                    {"HIGH_TRANS", "高级交易权限"},
                    {"NORMAL_BUSINESS_DEPOSIT", "标准业务存款"},
                    {"NORMAL_BUSINESS_WITHDRAW", "标准业务取款"},
                    {"HIGH_BUSINESS", "高级业务权限"},
                    {"HIGH_ADMIN", "高级管理员权限"}
            }).collect(Collectors.toMap((data -> data[0]), data -> data[1]));
    public final static List<String> validPermissionString = Arrays.asList(PermissionMaps.keySet().toArray(new String[0]));
}

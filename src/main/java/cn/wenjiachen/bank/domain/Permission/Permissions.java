package cn.wenjiachen.bank.domain.Permission;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 权限类接口
 * 该接口为权限类的抽象接口，所有权限类都应该实现该接口，其为系统提供了权限的基本操作。
 *
 * @author wenjiachen
 * @since 2023/1/5
 */
public interface Permissions {
    /**
     * 权限组ID，为一个标准的UUID
     */
    String PermissionGroupID = null;
    /**
     * 权限组名称
     */
    String PermissionGroupName = null;

    /**
     * 权限组所包含权限，为解析过的权限列表，属于一个字符串数组。
     */
    List<String> Permissions = null;

    /**
     * 检查当前权限组是否包含指定权限
     *
     * @param permission 权限名称
     * @return 是否包含
     */
    boolean HasPermission(String permission);

    /**
     * 检查当前权限组是否包含指定权限
     * 当前方法的判断逻辑为：在组内只要有一个权限包含，即承认存在权限。
     *
     * @param permissions 权限名称组
     * @return 是否包含
     */

    boolean HasPermission(List<String> permissions);

    /**
     * 检查当前权限组是否包含指定权限
     * 当前方法的判断逻辑为：在组内只要有一个权限包含，即承认存在权限。
     * 该方法为上一个方法对可变参数的重载，属于对直接字符串数组的支持。
     *
     * @param permissions 权限名称组
     * @return 是否包含
     */
    boolean HasPermission(String[] permissions);

    /**
     * 获取当前权限组的权限组ID
     *
     * @return 权限组ID
     */
    String getPermissionGroupID();

    /**
     * 获取当前权限组的权限组名称
     *
     * @return 权限组名称
     */
    String getPermissionGroupName();

    /**
     * 获取当前权限组的权限列表
     *
     * @return 权限列表
     */
    List<String> getPermissions();

    /**
     * 获取当前权限组的权限列表的解析字符串，使用逗号分隔，存储于数据库中使用
     *
     * @return 权限列表的解析字符串
     */
    String getPermissionsString();

    /**
     * 设置权限组ID，用于DAO中，平常业务不接触。
     *
     * @param permissionGroupID 权限组ID
     */
    void setPermissionGroupID(String permissionGroupID);

    /**
     * 设置权限组名称
     *
     * @param permissionGroupName 权限组名称
     */
    void setPermissionGroupName(String permissionGroupName);

    /**
     * 设置权限组的权限列表
     *
     * @param permissions 权限列表
     */
    void setPermissions(List<String> permissions);

    /**
     * 设置权限组的权限列表，使用逗号分隔的字符串，用于DAO中，平常业务不接触。
     *
     * @param permissions 权限列表
     */
    void setPermissions(String permissions);

    /**
     * 增加一个权限
     *
     * @param permission 权限名称
     * @return 是否增加成功
     */
    boolean addPermission(String permission);

    /**
     * 删除一个权限
     *
     * @param permission 权限名称
     * @return 是否删除成功
     */
    boolean deletePermission(String permission);

    /**
     * 可供对照的权限项和权限名称解释的映射表
     */
    final static Map<String, String> PermissionMaps =
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
    /**
     * 当前系统中能接受的可使用的权限项列表
     */
    final static List<String> validPermissionString = Arrays.asList(PermissionMaps.keySet().toArray(new String[0]));
}

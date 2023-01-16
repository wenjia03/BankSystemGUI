package cn.wenjiachen.bank.Dao.impl;

import cn.wenjiachen.bank.Dao.PermissionDao;
import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.Permission.impl.PermissionsImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/315:10
 */
public class PermissionDaoImpl implements PermissionDao {

    private static DataSource ds;

    public PermissionDaoImpl() throws Exception {
        ds = SQLConfig.getDataSource();
    }

    /**
     * 创建权限组
     *
     * @param PermissionGroupID   权限组ID
     * @param PermissionGroupName 权限组名称
     * @return 创建的权限组数量
     * @throws SQLException SQL异常
     */
    public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_permission_group (" +
                "PermissionGroupID, PermissionGroupName" +
                ")VALUES(" +
                "'" + PermissionGroupID + "'," +
                "'" + PermissionGroupName + "'" +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " PermissionGroup :" + PermissionGroupID);
        connection.close();
        return i;
    }

    /**
     * 依据权限组对象创建权限组
     *
     * @param permissions 权限组对象
     * @return 创建的权限组数量
     * @throws SQLException SQL异常
     */
    public Integer createPermissionGroup(Permissions permissions) throws SQLException {
        return createPermissionGroup(permissions.getPermissionGroupID(), permissions.getPermissionGroupName(), permissions.getPermissionsString());
    }

    /**
     * 依据权限组详细信息创建权限组
     *
     * @param permissionGroupID   权限组ID
     * @param permissionGroupName 权限组名称
     * @param permission          权限组权限，未解析的权限字符串，使用逗号分隔
     * @return 创建的权限组数量
     * @throws SQLException SQL异常
     */
    public Integer createPermissionGroup(String permissionGroupID, String permissionGroupName, String permission) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "INSERT INTO sec_permission_group (" + "PermissionGroupID, PermissionGroupName, Permissions" +
                ")VALUES(" +
                "'" + permissionGroupID + "'," +
                "'" + permissionGroupName + "'," +
                "'" + permission + "'" +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Created " + i + " Permission :" + permission);
        connection.close();
        return i;
    }

    /**
     * 查找权限组
     *
     * @param PermissionGroupID 权限组ID
     * @return 权限组对象查找集，一个List
     * @throws SQLException SQL异常
     */
    @Override
    public List<Permissions> fetchPermissionGroup(String PermissionGroupID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_permission_group WHERE " +
                "PermissionGroupID = '" + PermissionGroupID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Permissions> permissionsList = new ArrayList<>();
        while (resultSet.next()) {
            PermissionsImpl permissions = new PermissionsImpl();
            permissions.setPermissionGroupID(resultSet.getString("PermissionGroupID"));
            permissions.setPermissionGroupName(resultSet.getString("PermissionGroupName"));
            permissions.setPermissions(resultSet.getString("Permissions"));
            permissionsList.add(permissions);
        }
        connection.close();
        return permissionsList;
    }

    /**
     * 依据权限组的UUID删除权限组
     *
     * @param PermissionGroupID 权限组ID
     * @return 是否删除成功
     * @throws SQLException SQL异常
     */
    @Override
    public boolean deletePermissionGroup(String PermissionGroupID) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "DELETE FROM sec_permission_group WHERE " +
                "PermissionGroupID = '" + PermissionGroupID + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Deleted " + i + " PermissionGroup :" + PermissionGroupID);
        connection.close();
        return i > 0;
    }

    /**
     * 更新权限组信息，底层依据其UUID进行更新
     *
     * @param permissions 权限组对象
     * @return 是否更新成功
     * @throws SQLException SQL异常
     */
    @Override
    public boolean updatePermissionGroup(Permissions permissions) throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "UPDATE sec_permission_group SET " +
                "PermissionGroupName = '" + permissions.getPermissionGroupName() + "'," +
                "Permissions = '" + permissions.getPermissionsString() + "'" +
                "WHERE PermissionGroupID = '" + permissions.getPermissionGroupID() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println("Updated " + i + " PermissionGroup :" + permissions.getPermissionGroupID());
        connection.close();
        return i > 0;
    }

    /**
     * 返回当前系统中所有的权限组
     *
     * @return 权限组对象查找集，一个List
     * @throws SQLException SQL异常
     */
    @Override
    public List<Permissions> fetchAllPermissions() throws SQLException {
        Connection connection = ds.getConnection();
        String sql = "SELECT * FROM sec_permission_group";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Permissions> permissionsList = new ArrayList<>();
        while (resultSet.next()) {
            PermissionsImpl permissions = new PermissionsImpl();
            permissions.setPermissionGroupID(resultSet.getString("PermissionGroupID"));
            permissions.setPermissionGroupName(resultSet.getString("PermissionGroupName"));
            permissions.setPermissions(resultSet.getString("Permissions"));
            permissionsList.add(permissions);
        }
        connection.close();
        return permissionsList;
    }


}

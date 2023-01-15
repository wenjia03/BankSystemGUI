package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.PermissionDao;
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

    public Integer createPermissionGroup(Permissions permissions) throws SQLException {
        return createPermissionGroup(permissions.getPermissionGroupID(), permissions.getPermissionGroupName(), permissions.getPermissionsString());
    }

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
     * @param PermissionGroupID
     * @return
     * @throws Exception
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
     * @param PermissionGroupID
     * @return
     * @throws Exception
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
     * @param permissions
     * @return
     * @throws Exception
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
     * @return
     * @throws Exception
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

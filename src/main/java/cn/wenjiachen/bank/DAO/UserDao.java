package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public Integer createUser(User user) throws SQLException;

    public List<User> fetchUser(User user) throws SQLException;

    public List<User> fetchUserByEmail(String emailAddress) throws SQLException;

    public List<User> fetchUserByUUID(String UUID) throws SQLException;

    public List<User> fetchUserByPermissionGroupID(String permissionGroupID) throws SQLException;

    public boolean deleteUser(User user) throws SQLException;

    public List<User> fetchAllUsers() throws SQLException;


}

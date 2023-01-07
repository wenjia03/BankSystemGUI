package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.Profiles;

import java.sql.SQLException;
import java.util.List;

public interface ProfileDao<T extends Profiles> {

    public Integer createProfile(T profile) throws Exception;

    public List<T> fetchProfilesByName(String name) throws Exception;

    public List<T> fetchProfilesByUUID(String UUID) throws Exception;

    public List<T> fetchAllProfiles() throws Exception;

    public boolean deleteProfile(T profile) throws SQLException;

    public boolean updateProfile(T profile) throws SQLException;

}

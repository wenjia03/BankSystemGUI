package cn.wenjiachen.bank.DAO;

import cn.wenjiachen.bank.domain.Profiles;

import java.sql.SQLException;
import java.util.List;

public interface ProfileDao {

    public Integer createProfile(Profiles profile) throws Exception;

    public List<Profiles> fetchProfilesByName(String name) throws Exception;

    public List<Profiles> fetchProfilesByUUID(String UUID) throws Exception;

    public List<Profiles> fetchProfilesByPhone(String phone) throws Exception;

    public List<Profiles> fetchProfilesByCardID(String cardID) throws Exception;

    public List<Profiles> fetchAllProfiles() throws Exception;

    public boolean deleteProfile(Profiles profile) throws SQLException;

    public boolean updateProfile(Profiles profile) throws SQLException;

}

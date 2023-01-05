package cn.wenjiachen.bank.service.user;

import cn.wenjiachen.bank.DAO.ProfileDao;
import cn.wenjiachen.bank.DAO.impl.ProfileDaoImpl;
import cn.wenjiachen.bank.DAO.impl.UserDaoImpl;
import cn.wenjiachen.bank.domain.Profiles;

import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/423:27
 */
public class ProfileService {

    private static ProfileDaoImpl dao = null;

    static {
        try {
            dao = new ProfileDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取用户的Profile
     * @param uuid 用户UUID
     * @return 返回用户的Profile
     *
     * @throws Exception 异常
     */
    public static List<Profiles> fetchProfileByUserUUID(String uuid) throws Exception {
        return dao.fetchProfilesByUUID(uuid);
    }

}

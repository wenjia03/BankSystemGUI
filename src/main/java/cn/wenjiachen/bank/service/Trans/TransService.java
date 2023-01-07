package cn.wenjiachen.bank.service.Trans;

import cn.wenjiachen.bank.DAO.impl.ProfileTransDaoImpl;
import cn.wenjiachen.bank.DAO.impl.UserDaoImpl;

/**
 * @author Wenjia Chen
 * @date 2023/1/615:27
 */
public class TransService {
    private static ProfileTransDaoImpl dao;

    static {
        try {
            dao = new ProfileTransDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建订单
     *
     * @param trans 需要创建的订单对象
     */

}

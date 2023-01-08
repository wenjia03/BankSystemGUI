package cn.wenjiachen.bank.DAO.impl;

import cn.wenjiachen.bank.DAO.TransDao;
import cn.wenjiachen.bank.domain.Trans.ProfileTrans;

/**
 * @author Wenjia Chen
 * @date 2023/1/523:10
 */
public abstract class ProfileTransDaoImpl implements TransDao<ProfileTrans> {
    /**
     * @param trans
     * @return
     * @throws Exception
     */
    @Override
    public Integer create(ProfileTrans trans) throws Exception {
        return null;
    }
}

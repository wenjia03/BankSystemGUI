package cn.wenjiachen.bank.service.Trans;

import cn.wenjiachen.bank.DAO.TransException;
import cn.wenjiachen.bank.DAO.impl.ProfileTransDaoImpl;
import cn.wenjiachen.bank.DAO.impl.TransDaoImpl;
import cn.wenjiachen.bank.DAO.impl.UserDaoImpl;
import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.UserProfiles;
import cn.wenjiachen.bank.service.user.UserProfileService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/615:27
 */
public class TransService {
    private static TransDaoImpl dao;

    static {
        try {
            dao = new TransDaoImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建订单
     *
     * @param trans 需要创建的订单对象
     * @return 返回是否创建成功
     *
     */
    public static Boolean createTrans(Trans trans) throws Exception {
        return dao.create(trans) == 1;
    }

    /**
     * 创建一个订单
     *
     * @param from 转出账户
     * @param to 转入账户
     * @param amount 交易金额
     * @param type 转账类型
     * @return
     * @throws TransException 交易出错
     */
    public static Trans createTrans(String from, String to, String amount,  TransStatus type) throws Exception {
        List<UserProfiles> userProfiles = UserProfileService.fetchProfilesByCardID(from);
        if (userProfiles.size() == 0) {
            throw new TransException("转出账户不存在");
        }
        UserProfiles fromUser = userProfiles.get(0);
        List<UserProfiles> toUserProfiles = UserProfileService.fetchProfilesByCardID(to);
        if (toUserProfiles.size() == 0) {
            throw new TransException("转入账户不存在");
        }
        UserProfiles toUser = toUserProfiles.get(0);
        if(fromUser.getUserBankCardBalance() - Double.parseDouble(amount) < 0){
            throw new TransException("交易账户余额不足");
        }
        fromUser.setUserBankCardBalance(fromUser.getUserBankCardBalance() - Double.parseDouble(amount));
        toUser.setUserBankCardBalance(toUser.getUserBankCardBalance() + Double.parseDouble(amount));
        UserProfileService.updateProfile(fromUser);
        UserProfileService.updateProfile(toUser);
//        Trans trans = new Trans(new java.util.Date())
                // 获取当前日期时间对象Date
        return null;


    }
}

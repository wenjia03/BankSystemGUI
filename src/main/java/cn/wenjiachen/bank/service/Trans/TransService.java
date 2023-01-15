package cn.wenjiachen.bank.service.Trans;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.DAO.TransDao;
import cn.wenjiachen.bank.DAO.TransException;
import cn.wenjiachen.bank.DAO.impl.ProfileTransDaoImpl;
import cn.wenjiachen.bank.DAO.impl.TransDaoImpl;
import cn.wenjiachen.bank.domain.Trans.ProfileTrans;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.domain.UserProfiles;
import cn.wenjiachen.bank.service.user.UserProfileService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/615:27
 */
public class TransService {
    // todo 完善Javadoc
    private static TransDao<Trans> transDao;
    private static TransDao<ProfileTrans> profileTransDao;

    static {
        try {
            transDao = new TransDaoImpl();
            profileTransDao = new ProfileTransDaoImpl() {
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建订单
     *
     * @param trans 需要创建的订单对象
     * @return 返回是否创建成功
     */
    public static Boolean createTrans(Trans trans) throws Exception {
        return transDao.create(trans) == 1;
    }

    /**
     * 创建一个订单
     * 对于存款和取款，没有的出账账户和入账账户传入null即可
     *
     * @param from      转出账户
     * @param to        转入账户
     * @param amount    交易金额
     * @param type      转账类型
     * @param teller    当前交易操作员
     * @param TransInfo 交易信息
     * @return 当前的交易对象
     * @throws TransException 交易出错
     */
    public static Trans createTrans(String from, String to, String amount, TransType type,
                                    User teller, String TransInfo) throws TransException, SQLException {
        UserProfiles toUser = null;
        UserProfiles fromUser = null;
        if (type != TransType.DEPOSIT) {
            // 存款不需要转出账户（出账账户）
            List<UserProfiles> userProfiles = UserProfileService.fetchProfilesByCardID(from);
            if (userProfiles.size() == 0) {
                throw new TransException("转出账户不存在");
            }
            fromUser = userProfiles.get(0);
        }
        if (type != TransType.WITHDRAW) {
            // 取款不需要转入账户（入账账户）
            List<UserProfiles> toUserProfiles = UserProfileService.fetchProfilesByCardID(to);
            if (toUserProfiles.size() == 0) {
                throw new TransException("转入账户不存在");
            }
            toUser = toUserProfiles.get(0);
        }
        if (type != TransType.DEPOSIT && fromUser.getUserBankCardBalance() - Double.parseDouble(amount) < 0) {
            Trans trans = new Trans(new java.util.Date(), type,
                    TransStatus.FAIL, Double.parseDouble(amount), fromUser.getUserBankCardNumber(),
                    toUser != null ? toUser.getUserBankCardNumber() : null, teller.getUUID(), TransInfo);
            transDao.create(trans);
            throw new TransException("交易账户余额不足");
        }
        try {
            if (Double.parseDouble(amount) <= 0) {
                throw new TransException("交易金额必须大于0");
            }
        } catch (NumberFormatException e) {
            throw new TransException("无法解析的交易额：" + e.getMessage());
        }
        if (type != TransType.DEPOSIT) {
            fromUser.setUserBankCardBalance(fromUser.getUserBankCardBalance() - Double.parseDouble(amount));
            UserProfileService.updateProfile(fromUser);
        }

        if (type != TransType.WITHDRAW) {
            toUser.setUserBankCardBalance(toUser.getUserBankCardBalance() + Double.parseDouble(amount));
            UserProfileService.updateProfile(toUser);
        }
        Trans trans = new Trans(new java.util.Date(), type,
                TransStatus.SUCCESS, Double.parseDouble(amount),
                fromUser != null ? fromUser.getUserBankCardNumber() : null,
                toUser != null ? toUser.getUserBankCardNumber() : null,
                teller.getUUID(), TransInfo);
        transDao.create(trans);
        return trans;
    }

    /**
     * 查询所有交易记录
     *
     * @return 交易记录列表
     * @throws Exception
     */
    public static List<ProfileTrans> fetchAllTrans() throws SQLException {
        return profileTransDao.fetchAllTrans();
    }

    /**
     * 带筛选器的查询
     *
     * @param cardID 账户
     * @return 交易记录列表
     * @throws Exception
     */
    public static List<ProfileTrans> fetchTransByCardID(String cardID) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID);
    }

    public static List<ProfileTrans> fetchTransByCardID(String cardID, TransType type) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID, type);
    }

    public static List<ProfileTrans> fetchTransByCardID(String cardID, Boolean isFrom) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID, isFrom);
    }

    public static List<ProfileTrans> fetchTransByCardID(String cardID, TransType type, Boolean isFrom) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID, isFrom, type);
    }

    public static Boolean update(Trans ts) throws SQLException {
        return transDao.update(ts);
    }

    public static Boolean update(ProfileTrans ts) throws SQLException {
        return profileTransDao.update(ts);
    }

    public static Boolean delete(Trans ts) throws SQLException {
        return transDao.delete(ts);
    }


    public static Trans stopTrans(Trans ts) throws TransException, SQLException {
        if (ts == null)
            throw new TransException("交易不存在");
        if (ts.TransType == TransType.DEPOSIT || ts.TransType == TransType.WITHDRAW)
            throw new TransException("存款和取款无法止付");
        if (ts.TransStatus != TransStatus.PROCESSING)
            throw new TransException("只有处理中的交易才能止付");
        List<UserProfiles> profilesList = UserProfileService.fetchProfilesByCardID(ts.FromID);
        List<UserProfiles> toList = UserProfileService.fetchProfilesByCardID(ts.ToID);
        if (profilesList.size() == 0 || toList.size() == 0)
            throw new TransException("已销户账户无法止付");
        UserProfiles from = profilesList.get(0);
        UserProfiles to = profilesList.get(0);
        if (to.getUserBankCardBalance() < ts.TransMoney)
            throw new TransException("止付账户余额不足");
        ts.TransStatus = TransStatus.FAIL;
        update(ts);
        return createTrans(
                to.UserBankCardNumber,
                from.getUserBankCardNumber(),
                ts.TransMoney.toString(),
                ts.TransType,
                Application.LoginedUser,
                "止付交易 " + ts.TransID + " " + ts.TransInfo
        );
    }

    public static Trans returnTrans(Trans ts) throws TransException, SQLException {
        if (ts == null)
            throw new TransException("交易不存在");
        if (ts.TransType == TransType.DEPOSIT || ts.TransType == TransType.WITHDRAW)
            throw new TransException("存款和取款无法退回");
        if (ts.TransStatus != TransStatus.SUCCESS)
            throw new TransException("只有成功的交易才能退回");
        List<UserProfiles> profilesList = UserProfileService.fetchProfilesByCardID(ts.FromID);
        List<UserProfiles> toList = UserProfileService.fetchProfilesByCardID(ts.ToID);
        if (profilesList.size() == 0 || toList.size() == 0)
            throw new TransException("已销户账户无法退回");
        UserProfiles from = profilesList.get(0);
        UserProfiles to = profilesList.get(0);
        return createTrans(
                to.UserBankCardNumber,
                from.getUserBankCardNumber(),
                ts.TransMoney.toString(),
                ts.TransType,
                Application.LoginedUser,
                "退回交易 " + ts.TransID + " " + ts.TransInfo
        );
    }
}

package cn.wenjiachen.bank.service.Trans;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.Dao.TransDao;
import cn.wenjiachen.bank.exception.TransException;
import cn.wenjiachen.bank.Dao.impl.ProfileTransDaoImpl;
import cn.wenjiachen.bank.Dao.impl.TransDaoImpl;
import cn.wenjiachen.bank.domain.Trans.ProfileTrans;
import cn.wenjiachen.bank.domain.Trans.Trans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.domain.UserProfile;
import cn.wenjiachen.bank.service.user.UserProfileService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Wenjia Chen
 * @date 2023/1/615:27
 */
public class TransService {
    /**
     * TransDao对象
     */
    private static TransDao<Trans> transDao;
    /**
     * ProfileTransDao对象
     */
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
     * @throws SQLException 数据库异常
     */
    public static Boolean createTrans(Trans trans) throws SQLException {
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
     * @throws SQLException   数据库异常
     */
    public static Trans createTrans(String from, String to, String amount, TransType type,
                                    User teller, String TransInfo) throws TransException, SQLException {
        UserProfile toUser = null;
        UserProfile fromUser = null;
        if (type != TransType.DEPOSIT) {
            // 存款不需要转出账户（出账账户）
            List<UserProfile> userProfiles = UserProfileService.fetchProfilesByCardID(from);
            if (userProfiles.size() == 0) {
                throw new TransException("转出账户不存在");
            }
            fromUser = userProfiles.get(0);
        }
        if (type != TransType.WITHDRAW) {
            // 取款不需要转入账户（入账账户）
            List<UserProfile> toUserProfiles = UserProfileService.fetchProfilesByCardID(to);
            if (toUserProfiles.size() == 0) {
                throw new TransException("转入账户不存在");
            }
            toUser = toUserProfiles.get(0);
        }
        if (type != TransType.DEPOSIT && fromUser.getUserBankCardBalance() - Double.parseDouble(amount) < 0) {
            // 非存款交易都需要验证出账账户余额
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
     * @throws SQLException 数据库异常
     */
    public static List<ProfileTrans> fetchAllTrans() throws SQLException {
        return profileTransDao.fetchAllTrans();
    }

    /**
     * 带筛选器的查询
     *
     * @param cardID 账户
     * @return 交易记录列表
     * @throws SQLException 数据库异常
     */
    public static List<ProfileTrans> fetchTransByCardID(String cardID) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID);
    }

    /**
     * 依据交易相关卡号和类型查询交易记录
     *
     * @param cardID 卡号
     * @param type   交易类型
     * @return 交易记录列表
     * @throws SQLException 数据库异常
     */
    public static List<ProfileTrans> fetchTransByCardID(String cardID, TransType type) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID, type);
    }

    /**
     * 依据交易相关卡号和类型查询交易记录
     *
     * @param cardID 卡号
     * @param isFrom 是否为出账账户
     * @return 交易记录列表
     * @throws SQLException 数据库异常
     */
    public static List<ProfileTrans> fetchTransByCardID(String cardID, Boolean isFrom) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID, isFrom);
    }

    /**
     * 依据交易相关卡号和类型查询交易记录
     *
     * @param cardID 卡号
     * @param type   交易类型
     * @param isFrom 是否为出账账户
     * @return 交易记录列表
     * @throws SQLException 数据库异常
     */
    public static List<ProfileTrans> fetchTransByCardID(String cardID, TransType type, Boolean isFrom) throws SQLException {
        return profileTransDao.fetchTransAboutCard(cardID, isFrom, type);
    }

    /**
     * 更新交易信息
     *
     * @param ts 交易信息
     * @return 更新成功与否
     * @throws SQLException 数据库异常
     */
    public static Boolean update(Trans ts) throws SQLException {
        return transDao.update(ts);
    }

    /**
     * 更新交易信息
     *
     * @param ts 交易信息
     * @return 更新成功与否
     * @throws SQLException 数据库异常
     */

    public static Boolean update(ProfileTrans ts) throws SQLException {
        return profileTransDao.update(ts);
    }

    /**
     * 删除交易信息
     * <b>根据讨论，我们认为银行系统不能删除交易信息，该方法弃用。</b>
     * 我们为相关功能提供了止付和退回功能，来代替删除交易信息的功能。
     *
     * @param ts 交易信息
     * @return 删除成功与否
     * @throws SQLException 数据库异常
     */
    public static Boolean delete(Trans ts) throws SQLException {
        return transDao.delete(ts);
    }

    /**
     * 止付交易
     * <br>
     * 止付交易是面向于处理中的交易，强制终止交易执行（Fail）并将钱款原路返回。
     * <br>
     * 在本系统中，我们假定正在处理中的交易（PROCESSING）已经完成扣款和转移资金至担保账户的过程，等待卡组织完成交易。对于出账账户，交易额已被从账户中划转，对于入账账户，交易额已被从担保账户中划入，但是该部分交易额会被锁定无法使用。
     * <br>
     * 所以，止付会将原交易状态设置为FAIL，并且将资金原路返回，且会创建一个同类型交易记录，记录止付交易转移资金。
     *
     * @param ts 交易信息
     * @return 止付成功与否
     * @throws TransException 交易异常
     * @throws SQLException   数据库异常
     */
    public static Trans stopTrans(Trans ts) throws TransException, SQLException {
        if (ts == null)
            throw new TransException("交易不存在");
        if (ts.TransType == TransType.DEPOSIT || ts.TransType == TransType.WITHDRAW)
            throw new TransException("存款和取款无法止付");
        if (ts.TransStatus != TransStatus.PROCESSING)
            throw new TransException("只有处理中的交易才能止付");
        List<UserProfile> profilesList = UserProfileService.fetchProfilesByCardID(ts.FromID);
        List<UserProfile> toList = UserProfileService.fetchProfilesByCardID(ts.ToID);
        if (profilesList.size() == 0 || toList.size() == 0)
            throw new TransException("已销户账户无法止付");
        UserProfile from = profilesList.get(0);
        UserProfile to = profilesList.get(0);
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

    /**
     * 退回交易
     * <br>
     * 退回交易是代替删除的第二个交易，其和止付交易的区别在于，退回交易不会将资金原路返回，而是将资金转移到退回账户。
     * <br>
     * 且退回交易需要双方同意，相当于误操作的补偿，其面向于已经完成的交易。该操作需要高级授权。
     * <br>
     * 退回交易是一个正常交易，所以，如果退回账户余额不足，会导致交易失败。
     *
     * @param ts 交易信息
     * @return 退回成功与否
     * @throws TransException 交易异常
     * @throws SQLException   数据库异常
     */
    public static Trans returnTrans(Trans ts) throws TransException, SQLException {
        if (ts == null)
            throw new TransException("交易不存在");
        if (ts.TransType == TransType.DEPOSIT || ts.TransType == TransType.WITHDRAW)
            throw new TransException("存款和取款无法退回");
        if (ts.TransStatus != TransStatus.SUCCESS)
            throw new TransException("只有成功的交易才能退回");
        List<UserProfile> profilesList = UserProfileService.fetchProfilesByCardID(ts.FromID);
        List<UserProfile> toList = UserProfileService.fetchProfilesByCardID(ts.ToID);
        if (profilesList.size() == 0 || toList.size() == 0)
            throw new TransException("已销户账户无法退回");
        UserProfile from = profilesList.get(0);
        UserProfile to = profilesList.get(0);
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

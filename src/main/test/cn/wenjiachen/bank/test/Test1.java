package cn.wenjiachen.bank.test;

import cn.wenjiachen.bank.DAO.BankDao;
import cn.wenjiachen.bank.DAO.impl.BankDaoImpl;
import cn.wenjiachen.bank.DAO.impl.ProfileDaoImpl;
import cn.wenjiachen.bank.DAO.impl.UserDaoImpl;
import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.bank.Bank;
import cn.wenjiachen.bank.utils.Securities;
import cn.wenjiachen.bank.utils.Tools;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/423:58
 */
public class Test1 {

    @Test
    public void test() throws Exception {
//        ProfileDaoImpl profileDao = new ProfileDaoImpl();
//        profileDao.createProfile(new Profiles(
//                "陈文嘉",
//                new Date(2003, Calendar.DECEMBER,6),
//                new Bank("中国工商银行","1234578","SWIFTICBC"),
//                "6228480402564890018",
//                Securities.encryptPassword("123456"),
//                123456.78,
//                "96082f7e-f7cd-4e4e-84b1-006b7f592527",
//                "12345678901",
//                "翻斗大街翻斗花园二号楼1001室"
//        ));
        BankDao bankDao = new BankDaoImpl();
        bankDao.createBank(new Bank("中国工商银行", "1234578", "SWIFTICBC"));
        System.out.println(new Date(2003 - 1900, Calendar.DECEMBER, 6));
        System.out.println(Tools.dateToDatetime(new Date(2003 - 1900, Calendar.DECEMBER, 6)));
    }

    @Test
//    public void Test2() {
//        UserDaoImpl userDao = new UserDaoImpl();
//        userDao.createUser()
//    }
}

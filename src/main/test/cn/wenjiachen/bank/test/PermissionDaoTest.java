
package cn.wenjiachen.bank.test;

import cn.wenjiachen.bank.Dao.PermissionDao;
import cn.wenjiachen.bank.Dao.impl.PermissionDaoImpl;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.Permission.impl.PermissionsImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Permission数据层测试类
 *
 * @author Zhao Zong
 */
public class PermissionDaoTest {

    PermissionDao permissionDao = new PermissionDaoImpl();

    public PermissionDaoTest() throws Exception {
    }

    /**
     * 测试创建权限组
     * public Integer createPermissionGroup(Permissions permissions) throws Exception;
     */
    @Test
    public void test1() throws Exception {
        System.out.println("test1");
        Permissions permissions = new PermissionsImpl("2214626930", "TestPermission", "Admin.*,Admin.Trans.*");
        permissionDao.createPermissionGroup(permissions);
    }

    /**
     * 测试创建权限组
     * public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName) throws Exception;
     */
    @Test
    public void test2() throws Exception {
        System.out.println("Test2");
        permissionDao.createPermissionGroup("2214", "沙耀祖");
    }

    /**
     * 测试创建权限组
     * public Integer createPermissionGroup(String PermissionGroupID, String PermissionGroupName, String Permissions) throws Exception;
     */
    @Test
    public void test3() throws Exception {
        System.out.println("test3");
        permissionDao.createPermissionGroup("2214626930", "沙耀祖", "一级,二级,三级");
    }

    /**
     * 测试删除权限组
     * public boolean deletePermissionGroup(String PermissionGroupID) throws Exception;
     */
    @Test
    public void test4() throws Exception {
        System.out.println("test4");
        permissionDao.deletePermissionGroup("2213");
    }

    /**
     * 测试修改权限组
     * boolean updatePermissionGroup(Permissions permissions) throws Exception;
     */
    @Test
    public void test5() throws Exception {
        System.out.println("test5");
        Permissions permissions = new PermissionsImpl("2214626930", "TestPermission2", "行星级,恒星级,宇宙级");
        System.out.println(permissionDao.updatePermissionGroup(permissions));
    }

    /**
     * 测试通过权限组ID查询获得权限组列表
     * public List<Permissions> fetchPermissionGroup(String PermissionGroupID) throws Exception;
     */
    @Test
    public void test6() throws Exception {
        System.out.println("test6");
        List<Permissions> permissions = permissionDao.fetchPermissionGroup("2214626930");
        System.out.println(permissions.size());


    }

    /**
     * 测试查找所有的权限列表
     * public List<Permissions> fetchAllPermissions() throws Exception;
     */
    @Test
    public void test7() throws Exception {
        System.out.println("test7");
        List<Permissions> permissions = permissionDao.fetchAllPermissions();
        System.out.println(permissions.size());
    }
}
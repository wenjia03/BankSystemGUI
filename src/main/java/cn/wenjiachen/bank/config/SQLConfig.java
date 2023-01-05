package cn.wenjiachen.bank.config;

import cn.wenjiachen.bank.utils.Securities;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.spi.ObjectFactory;

/**
 * @author Wenjia Chen
 * @date 2023/1/219:16
 */

public class SQLConfig {

    private static DataSource sg = null;

    private static final SQLConfig sqlSingle = null;

    private SQLConfig() {
    }

    /**
     * 创建一个DataSource源到ActFramework下管理
     * 设置该SQLConfig类为单例模式
     */
    public static DataSource getDataSource() throws Exception {
        if (sg == null) {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(Paths.get("src/main/resources/sql.properties")));
            sg = DruidDataSourceFactory.createDataSource(properties);
        }
        return sg;
    }

    /**
     * 通过反射获取将ResultSet转换为对象
     *
     * @param rs  ResultSet 对象
     * @param cls 要转换的对象的 Class
     * @param <T> 要转换的对象的类型
     * @return 转换后的对象
     */
    public static <T> List<T> parseResultSetAll(ResultSet rs, Class<T> cls) {
        // 该反射使用的是无参构造 用于创建缺省对象
        try {
            List<T> list = new ArrayList<>();
            //将查询的所有数据转换为对象添加到集合
            while (rs.next()) {

                //实例化对象
                T obj = cls.newInstance();
                //获取类中所有的属性
                Field[] arrf = cls.getDeclaredFields();
                //遍历属性
                for (Field f : arrf) {
                    Object object;
                    try {
                        // 允许存在空元素 需要在无参构造方法中设置初始值
                        object = rs.getObject(f.getName());
                        // 此处将在MySQL中使用Integer存储的Boolean类型转换为Boolean类型
//                        System.out.println(f.getType().toString());
                        if ((f.getType().toString().equals("boolean") || (f.getType().toString().equals("class java.lang.Boolean")))) {
                            object = Securities.integerToBool((int) object);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    //设置忽略访问校验
                    f.setAccessible(true);
                    //为属性设置内容
                    f.set(obj, object);
                }
                list.add(obj);//添加到集合

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

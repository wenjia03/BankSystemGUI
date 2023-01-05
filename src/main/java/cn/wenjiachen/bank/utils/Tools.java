package cn.wenjiachen.bank.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wenjia Chen
 * @date 2023/1/223:06
 */
public class Tools {
    /**
     * 基于反射功能生成可以被用于插入的SQL语句
     *
     * @return SQL语句
     */
    public static <T> String getSQLCommand(Object o, Class<T> cls) {
        try {
            Field[] fields = cls.getDeclaredFields();
            StringBuilder sql = new StringBuilder();
            sql.append("(");
            for (Field field : fields) {
                field.setAccessible(true);
                sql.append(field.getName()).append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().equals(String.class)) {
                    sql.append("'").append(field.get(o)).append("',");
                } else {
                    if (field.get(o) == null) {
                        sql.append("null,");

                    } else if (field.getType().equals(Boolean.class)) {
                        sql.append(((Boolean) field.get(o)) ? "1" : "0").append(",");
                    } else {
                        sql.append(field.get(o)).append(",");
                    }
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
            return sql.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String dateToDatetime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    // 将yyyy-MM-dd HH:mm:ss格式的字符串转换为Date
    public static Date datetimeToDate(String datetime) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
    }
}

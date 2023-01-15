package cn.wenjiachen.bank.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


/**
 * @author Wenjia Chen
 * @date 2023/1/223:06
 */
public class Tools {

    public static final int BLACK = -16777216;
    public static final int WHITE = -1;
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖"};
    private static final String[] CN_UPPER_UNIT = {"", "拾", "佰", "仟", "万", "拾", "佰",
            "仟", "亿", "拾", "佰", "仟"};
    private static final String CN_FULL = "整";
    private static final String CN_NEGATIVE = "负";

    private static final String CN_YUAN = "元";

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

    /**
     * 生成一个带时间戳的16位数字ID 返回String
     */
    public static String generateID() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (int) (Math.random() * 10000);
    }

    /**
     * 生成一个银行卡号
     */
    public static String generateCardID() {
        return "6222021145" + (int) (Math.random() * 1000000000);
    }

    public static String splitCardID(String cardID) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cardID.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                sb.append(" ");
            }
            sb.append(cardID.charAt(i));
        }
        return sb.toString();
    }

    public static boolean isIDCard(String text) {
        // 验证中华人民共和国公民身份证号码
        // 1. 长度为18位
        if (text.length() != 18) {
            return false;
        }
        // 2. 除最后一位外，都为数字
        for (int i = 0; i < 17; i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        // 3. 最后一位可以是数字或X x
        if (!Character.isDigit(text.charAt(17)) && (text.charAt(17) != 'X' && text.charAt(17) != 'x')) {
            return false;
        }
        // 4. 前17位的加权和能被11整除
//        int sum = 0;
//        for (int i = 0; i < 17; i++) {
//            sum += (int) (Math.pow(2, 17 - i) % 11) * (text.charAt(i) - '0');
//        }
//        sum %= 11;
//        if (sum == 2) {
//            return text.charAt(17) == 'X';
//        } else {
//            return (text.charAt(17) - '0') == sum;
//        }
        return true;
    }

    // 验证身份证号中是否能提取合法出生日期
    public static boolean isIDCardDate(String text) {
        if (text.length() != 18) {
            return false;
        }
        String date = text.substring(6, 14);
        try {
            new SimpleDateFormat("yyyyMMdd").parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 提取身份证号中的出生日期 转换为Date
    public static Date getIDCardBirthday(String text) {
        String date = text.substring(6, 14);
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(date);

        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate dateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }


    public static <T> T valueGetKey(Map map, String value, Class<T> cls) {
        Set set = map.keySet();
        for (Object o : set) {
            if (map.get(o).equals(value)) {
                return (T) o;
            }
        }
        return null;
    }


    public static String toChineseUpper(double number) {
        StringBuilder sb = new StringBuilder();
        boolean negative = false;
        if (number < 0) {
            negative = true;
            number = -number;
        }
        long numberLong = Math.round(number * 100);
        if (numberLong == 0) {
            return CN_UPPER_NUMBER[0];
        }
        int scale = (int) (numberLong % 100);
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        numberLong = numberLong / 100;
        if (scale > 0) {
            numIndex = 2;
            getZero = true;
        }


        if (numberLong == 0) {
            sb.insert(0, CN_UPPER_NUMBER[0]);
        }
        String numUnitBuf = "";
        while (numberLong > 0) {
            int num = (int) (numberLong % 10);
            if (num == 0) {
                if (!getZero) {
                    getZero = true;
//                    sb.insert(0, CN_UPPER_NUMBER[num]);
                }
                if (numUnit % 4 == 0) {
                    numUnitBuf = CN_UPPER_UNIT[numUnit];
                }

                getZero = true;
            } else {
                if (numUnit % 4 != 0)
                    sb.insert(0, numUnitBuf);
                sb.insert(0, CN_UPPER_NUMBER[num] + CN_UPPER_UNIT[numUnit]);
                numUnitBuf = "";
                getZero = false;
            }

            numberLong = numberLong / 10;
            numUnit++;
        }

        if (scale > 0) {
            sb.append("点");
            Stack<String> stack = new Stack<>();
            for (int i = 0; i < numIndex; i++) {
                int scaleNum = (int) (scale % 10);
                stack.add(CN_UPPER_NUMBER[scaleNum]);
                scale = scale / 10;
            }
            while (!stack.empty()) {
                sb.append(stack.pop());
            }
        }
        sb.append(CN_YUAN);
        if (negative) {
            sb.insert(0, CN_NEGATIVE);
        }

        if (scale <= 0 && !getZero) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }


    public static String getQRCodeBase64(String qrCodeText) {
        /*
         * 编码二维码
         * 参考文档：
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
         * https://blog.ronanlefichant.fr/2022/08/javafx-image-view-base64.html
         * https://juejin.cn/post/7059024334127366181
         * https://docs.oracle.com/javase/8/docs/api/java/util/Base64.Encoder.html
         */
        String QrCodeStr = qrCodeText;
        String base64 = null;
        // 创建二维码
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            Map<EncodeHintType, String> charcter = new HashMap<>();
            // 设置字符集
            charcter.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的四个参数   需要生成的字符串，类型设置为二维码，二维码宽度，二维码高度，字符串字符集
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(QrCodeStr, BarcodeFormat.QR_CODE, 500, 500, charcter);
            // 二维码像素，也就是上面设置的 500
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            // 创建二维码对象
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // 按照上面定义好的二维码颜色编码生成二维码
                    image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            // 1、第一种方式
            // 生成的二维码图片对象转 base64
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // 设置图片的格式
            ImageIO.write(image, "png", stream);
            // 生成的二维码base64
            base64 = encoder.encodeToString(stream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;

    }

    public static String getTOTPQRCode(String secret, String account, String issuer) {
        /*
         * 生成TOTP二维码
         * 参考文档：
         * https://stackoverflow.com/questions/34520928/how-to-generate-a-qr-code-for-google-authenticator-that-correctly-shows-issuer-d
         */
        String qrCodeText = "otpauth://totp/" + issuer + ":" + account + "?secret=" + secret + "&issuer=" + issuer;
        return getQRCodeBase64(qrCodeText);
    }

    public static Image getTOTPQRCodeImage(String secret, String account, String issuer) {
        String base64 = getTOTPQRCode(secret, account, issuer);
        Base64.Decoder decoder = Base64.getDecoder();
        return new Image(decoder.wrap(new ByteArrayInputStream(base64.getBytes())));
    }

}

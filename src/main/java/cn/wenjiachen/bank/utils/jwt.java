package cn.wenjiachen.bank.utils;

/**
 * @author Wenjia Chen
 * @date 2023/1/223:42
 */


import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.*;


import java.util.Date;

/**
 * JWT工具类
 */
public class jwt {

    private static final long tokenExpiration = 365L * 24 * 60 * 60 * 1000;//token过期时间
    private static final String tokenSignKey = "IE@Ty#H#_7,ZCDrzz,ywMCcIT2d,&6+LZo_j3NYtqT6NEQiUZX!.axMP!o(uulz0XW93G)&l-6UAXKHGy$^AW6F$(Ywo_K^~+N*&pTO5cI4X~EIu)eM-Qb=V^hZ#f9Sv";
    //加密密钥

    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                .setSubject("AUTH-USER")//主题
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))//过期时间
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }


    public static Object jwtGet(String gt, String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);//用之前服务器写好的tokenSignKey进行验证解密
            Claims claims = claimsJws.getBody();
            return claims.get(gt);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}


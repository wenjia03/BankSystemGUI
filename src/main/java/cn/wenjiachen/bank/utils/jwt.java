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

    /**
     * 创建一个JWT
     * <br>
     * <b>已弃用，为做Web设计的时候遗留产物，已被弃用。</b>
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return JWT
     */
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


    /**
     * 从JWT中获取对象
     * <br>
     * <b>已弃用，为做Web设计的时候遗留产物，已被弃用。</b>
     *
     * @param gt    获取的对象Key
     * @param token JWT
     * @return 对象
     */
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


package org.xr.happy.common.utils;


import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xr.happy.common.vo.UserVo;
import org.xr.happy.exception.GlobalException;
import sun.misc.BASE64Decoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String JWT_SECRET = "c6d45225f5c040b590e848b0c29dfc54";

    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMillis 有效时间
     * @return String
     */
    public static String createJWT(String id, String subject, Long ttlMillis) throws IOException {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id) // 是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setSubject(subject)   // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志
                .setIssuer("user")     // 颁发者是使用 HTTP 或 HTTPS 方案的 URL（区分大小写），其中包含方案、主机及（可选的）端口号和路径部分
                .setIssuedAt(now)      // jwt的签发时间
                .signWith(SignatureAlgorithm.HS256, secretKey); // 设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            logger.info("jwt Token expired time is :{}", expDate);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static Claims validateJWT(String jwtStr) {

        try {
            Claims claims = parseJWT(jwtStr);
            return claims;
        } catch (IOException e) {
            throw new RuntimeException("parse jwt token exception");
        }

    }

    private static SecretKey generalKey() throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encodedKey = decoder.decodeBuffer(JWT_SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) throws IOException {
        SecretKey secretKey = generalKey();

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws Exception {

       /* Long id = System.currentTimeMillis();

        UserVo build = UserVo.builder().userId(1L)
                .email("8080@163.com")
                .username("steven")
                .password("123456")
                .userUniqueToken("fdafadfafdad").build();

        String userVoJson = JSON.toJSONString(build);

        String jwtToken = JwtUtil.createJWT(id.toString(), userVoJson, 5000l);

        logger.info("jwt token created success !：{}", jwtToken);

        Claims claims = null;
        try {
            claims = JwtUtil.validateJWT(jwtToken);
            logger.info(claims.toString());
        } catch (ExpiredJwtException e) {
            logger.error("token 过期");
        }*/
    }
}

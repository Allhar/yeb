package com.allhar.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    public static final String CLAIM_KEY_USERNAME="sub";
    public static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 根据用户信息生成Token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){

        Map<String,Object> claim = new HashMap<>();

        claim.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claim.put(CLAIM_KEY_CREATED,new Date());

        return generateToken(claim);

    }

    /**
     * 根据token获取用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){

        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
           username = null;
        }

        return username;

    }

    /**
     * 从token获取荷载
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成JWT TOKEN
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();

    }

    /**
     * 生成荷载token失效时间
     */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+expiration*1000);
    }


    /**
     * 验证token是否有效,用户名相同,token时间有效
     * @param token
     * @return
     */
    public boolean validateToken(String token , UserDetails userDetails){
        Claims claims = getClaimsFromToken(token);
        Date date = claims.getExpiration();

        String userNameFromToken = getUserNameFromToken(token);
        return userNameFromToken.equals(userDetails.getUsername()) && !(date.before(new Date()));

    }


    /**
     * 是否token已经过期 可以被刷新
     * @param token
     * @return
     */
    private boolean canRefresh(String token){

        Claims claims = getClaimsFromToken(token);
        Date date = claims.getExpiration();

        return !date.before(new Date());

    }


    /**
     * 刷新token过期时间
     * @param token
     * @return
     */
    public String refreshToken(String token){

        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());

        return generateToken(claims);


    }
}

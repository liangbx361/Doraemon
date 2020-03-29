package com.doraemon.user.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtHandler {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    private static final String ROLE_CLAIMS = "rol";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7L;

    private static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    private static JwtHandler sJwtHandler;

    private SecretKey secretKey;

    public synchronized static JwtHandler getInstance() {
        if(sJwtHandler == null) {
            sJwtHandler = new JwtHandler();
        }
        return sJwtHandler;
    }

    private JwtHandler() {
        // 生成足够的安全随机密钥，以适合符合规范的签名
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);
        secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);
    }

    public String createToken(Integer userId, List<String> roles) {
        String tokenPrefix = Jwts.builder()
            .setHeaderParam("typ", TOKEN_TYPE)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .claim("rol", String.join(",", roles))
            // 发行者
            .setIssuer("doraemon")
            .setIssuedAt(new Date())
            // 科目
            .setSubject(userId.toString())
            // 过期时间
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .compact();

        return TOKEN_PREFIX + tokenPrefix;
    }

    public List<SimpleGrantedAuthority> getUserRolesByToken(String token) {
        String role = (String) getTokenBody(token).get(ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public String getUserId(String token) {
        return getTokenBody(token).getSubject();
    }

    private Claims getTokenBody(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }
}

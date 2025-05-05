package kr.co.wikibook.gallery.common.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final Key singKey;

    static {
        // Secret key for HMAC signing 32 bytes (256 bits)
        String secretKey = "SECRET_KEY_202505051651321235_!!";
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        singKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
    }
    
    public static String generate(String subject, String name, Object value, int expireMinutes) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + (expireMinutes * 60 * 1000L));
        
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");
        
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(name, value);
        
        return Jwts.builder()
                .setHeader(headerMap)
                .setSubject(subject)
                .setExpiration(expTime)
                .addClaims(claims)
                .signWith(singKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isValid(String token) {
        if (StringUtils.hasLength(token)) {
            try {
                Jwts.parser()
                        .setSigningKey(singKey)
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {
                // Token has expired
            } catch (JwtException e) {
                // JWT token is invalid
            }
        }
        return false;
    }

    public static Map<String, Object> getBody(String token) {
        if (StringUtils.hasLength(token)) {
            try {
                return Jwts.parser()
                        .setSigningKey(singKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            } catch (JwtException e) {
                // JWT token is invalid
            }
        }
        return null;
    }
}

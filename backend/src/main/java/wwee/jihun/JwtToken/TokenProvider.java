package wwee.jihun.JwtToken;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SecretKey;

    public String CreateAccessToken(String userId, String userName){
        return createToken(userId , userName);
    }

    private String createToken(String userId, String userName) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("userName", userName);
        ZonedDateTime now = ZonedDateTime.now();
        long tokenValidTime = 60 * 30L;
        ZonedDateTime tokenValidity = now.plusSeconds(tokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SecretKey)
                .compact();
    }
    
    public String getUserId(String token){
        return parseClaims(token).get("userId", String.class);
    }

    public boolean validateToken(String token) {
        Logger log = (Logger) LoggerFactory.getLogger(TokenProvider.class);
        try {
            Jwts.parserBuilder().setSigningKey(SecretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("토큰 정보가 부족합니다.");
        }
        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(SecretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

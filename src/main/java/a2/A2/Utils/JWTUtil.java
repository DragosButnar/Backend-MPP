package a2.A2.Utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXP_TIME = 864_000_000;

    public String generateToken(Object data){
        return Jwts.builder()
                .setSubject(data.toString())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact().replace(" ", "");
    }

    public Jws<Claims> unpackToken(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token);
    }
}
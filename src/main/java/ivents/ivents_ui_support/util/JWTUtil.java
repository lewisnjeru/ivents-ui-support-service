package ivents.ivents_ui_support.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    private final String SECRET = "nBVrDS8VAmhzDaEriUs0aPUXKzQgMuNjqSSVWPJp6bCp2LU3rs7dK4pgbxJHyigY7tzEJ3i3zzGcBr5ZaVSe5Q=="; // must match auth-service

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
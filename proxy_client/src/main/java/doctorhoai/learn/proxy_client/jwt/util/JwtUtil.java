package doctorhoai.learn.proxy_client.jwt.util;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public interface JwtUtil {
    String extractUsername( final String token );
    Date extractExpiration( final String token );
    <T> T extractClaims( final String token, final Function<Claims, T> claims );
    String generateToken( final UserDetails userDetails );
    Boolean validateToken( final String token, final UserDetails userDetails );
}

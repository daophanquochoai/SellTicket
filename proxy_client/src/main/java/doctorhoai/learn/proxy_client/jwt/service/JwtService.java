package doctorhoai.learn.proxy_client.jwt.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(final String token);
    Date extractExpiration( final String token );
    <T> T extractClaims(final String token, final Function<Claims, T> claimsResolver);
    String generateToken( final UserDetails user );
    Boolean validateToken( final String token, final UserDetails user );
}

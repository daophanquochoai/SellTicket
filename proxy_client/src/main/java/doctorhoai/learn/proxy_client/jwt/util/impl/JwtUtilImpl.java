package doctorhoai.learn.proxy_client.jwt.util.impl;

import doctorhoai.learn.proxy_client.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilImpl implements JwtUtil {

    private static final String SECRET = "HocVienCongNgheBuuChinhVienThongCoSoHoChiMinh";

    @Override
    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimFunction) {
        final Claims claim = this.extractAllClaims(token);
        return  claimFunction.apply(claim);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("roles", authorities);
        return this.createToken(claims, userDetails.getUsername());
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (
                username.equals(userDetails.getUsername())
        );
    }

    public String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}

package doctorhoai.learn.proxy_client.jwt.util.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.CustomerDto;
import doctorhoai.learn.proxy_client.business.user.model.EmployeeDto;
import doctorhoai.learn.proxy_client.business.user.service.CustomerFeign;
import doctorhoai.learn.proxy_client.business.user.service.EmploymentFeign;
import doctorhoai.learn.proxy_client.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtilImpl implements JwtUtil {

    private static final String SECRET = "HocVienCongNgheBuuChinhVienThongCoSoHoChiMinh";
    private final EmploymentFeign employmentFeign;
    private final CustomerFeign customerFeign;

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
        ResponseEntity<Response> data;
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        if( authorities.get(0).equals("ROLE_ADMIN")){
            data = employmentFeign.getInfoAccount(userDetails.getUsername());
            if( data.getStatusCode() != HttpStatusCode.valueOf(200)){
                log.error("Can't get info account");
                throw new RuntimeException("Can't get info account");
            }
            EmployeeDto employeeDto = objectMapper.convertValue(data.getBody().getData(),EmployeeDto.class);
            claims.put("name", employeeDto.getName());
            claims.put("email", employeeDto.getEmail());
            claims.put("id", employeeDto.getId());
        }else{
            data = customerFeign.getInfoAccount(userDetails.getUsername());
            if( data.getStatusCode() != HttpStatusCode.valueOf(200)){
                log.error("Can't get info account");
                throw new RuntimeException("Can't get info account");
            }
            CustomerDto customerDto = objectMapper.convertValue(data.getBody().getData(), CustomerDto.class);
            claims.put("name", customerDto.getName());
            claims.put("email", customerDto.getEmail());
            claims.put("id", customerDto.getId());
        }
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

package doctorhoai.learn.proxy_client.jwt.service.impl;

import doctorhoai.learn.proxy_client.jwt.service.JwtService;
import doctorhoai.learn.proxy_client.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final JwtUtil jwtUtil;

    @Override
    public String extractUsername(String token) {
        log.info("**String, Jwt service extract username from given token!**");
        return jwtUtil.extractUsername(token);
    }

    @Override
    public Date extractExpiration(String token) {
        log.info("**Date, Jwt service extract expiration from given token!**");
        return jwtUtil.extractExpiration(token);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        log.info("**T, Jwt service extract claims from given token and claimResolver Function!**");
        return jwtUtil.extractClaims(token, claimsResolver);
    }

    @Override
    public String generateToken(UserDetails user) {
        log.info("**String, Jwt service generate token from given userdetails!**");
        return jwtUtil.generateToken(user);
    }

    @Override
    public Boolean validateToken(String token, UserDetails user) {
        log.info("**Boolean, Jwt service validate token from given token and userdetail**");
        return jwtUtil.validateToken(token, user);
    }
}

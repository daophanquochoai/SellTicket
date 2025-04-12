package doctorhoai.learn.proxy_client.security.OAuth2;

import doctorhoai.learn.proxy_client.jwt.service.TokenService;
import doctorhoai.learn.proxy_client.jwt.util.JwtUtil;
import doctorhoai.learn.proxy_client.jwt.util.impl.JwtUtilImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Value("${app.oauth2.redirectUri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = redirectUri.isEmpty() ?
                determineTargetUrl(request, response, authentication) : redirectUri;
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) oauthUser;
        final Map<String, Object> claims = new HashMap<>();
        claims.put("name", userDetails.getName());
        claims.put("email", userDetails.getEmail());
        claims.put("id", userDetails.getId());
        claims.put("cccd", "");
        claims.put("provider", "SOCIAL");
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, JwtUtilImpl.getSecret())
                .compact();
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

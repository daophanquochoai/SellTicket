package doctorhoai.learn.proxy_client.config.filter;

import doctorhoai.learn.proxy_client.exception.UnAuthorizedException;
import doctorhoai.learn.proxy_client.jwt.service.JwtService;
import doctorhoai.learn.proxy_client.jwt.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    @Lazy
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/authenticate")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("**JwtRequestFilter, one per request, validating and extracting token**");
        final var authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }
        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(!tokenService.findToken(jwt)){
                throw new UnAuthorizedException("Bad credentials");
            }
            if( this.jwtService.validateToken(jwt, userDetails) ) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        =new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                throw new UnAuthorizedException("Bad credentials");
            }
        }
        filterChain.doFilter(request, response);
        log.info("**JwtRequestFilter, one per request, validating and extracting token**");
    }
}

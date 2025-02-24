package doctorhoai.learn.proxy_client.security;

import doctorhoai.learn.proxy_client.config.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
public class SecurityConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/authenticate", "/api/**", "/actuator/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/room-service/api/**","/film-service/api/typefilm/**", "/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**", "/rate-service/api/rate/film/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/payment-service/api/bill/add","/user-service/api/customer/add").permitAll()
                                .requestMatchers("/user-service/api/account/bank/**","/user-service/api/customer/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST,"room-service/api/**","/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"room-service/api/**","/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"room-service/api/**","/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**").hasRole("ADMIN")
                                .requestMatchers("/rate-service/api/rate/add/**").hasRole("USER")
                                .requestMatchers("/film-service/api/film/**", "/dish-service/api/dish/**","/dish-service/api/typedish/**","/rate-service/api/rate/delete/**", "/rate-service/api/rate/active/**","/payment-service/api/bill/**","/user-service/api/employment/**","/user-service/api/role/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager)
                .build();
    }

}

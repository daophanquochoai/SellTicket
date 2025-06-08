package doctorhoai.learn.proxy_client.security;

import doctorhoai.learn.proxy_client.config.filter.JwtRequestFilter;
import doctorhoai.learn.proxy_client.security.OAuth2.CustomAuthenticationSuccessHandler;
import doctorhoai.learn.proxy_client.security.OAuth2.CustomOAuth2UserService;
import doctorhoai.learn.proxy_client.security.OAuth2.OAuth2AuthenticationFailureHandler;
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
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/authenticate", "/api/**", "/actuator/**","/swagger-ui/**", "/v3/api-docs/**", "/user-service/api/customer/enviroment","/film-service/api/slider/**", "/user-service/api/contact/add/**","/login/**", "/oauth2/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/room-service/api/**","/film-service/api/typefilm/**","/dish-service/api/**","/film-service/api/**","/filmshowtime-service/api/filmshowtime/**", "/rate-service/api/rate/film/**", "/payment-service/api/ticket/**", "/payment-service/api/billchair/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/payment-service/api/bill/payment","/payment-service/api/bill/add","/user-service/api/customer/add", "/user-service/api/customer/change/customer/**", "/user-service/api/customer/forget/customer/**").permitAll()
                                .requestMatchers("/user-service/api/account/bank/**","/user-service/api/customer/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST,"room-service/api/**","/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**", "/payment-service/api/ticket/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"room-service/api/**","/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**", "/payment-service/api/ticket/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"room-service/api/**","/film-service/api/**","/dish-service/api/**","/filmshowtime-service/api/filmshowtime/**", "/payment-service/api/ticket/**").hasRole("ADMIN")
                                .requestMatchers("/rate-service/api/rate/add/**").hasRole("USER")
                                .requestMatchers("/film-service/api/film/**", "/dish-service/api/dish/**","/dish-service/api/typedish/**","/rate-service/api/rate/delete/**", "/rate-service/api/rate/active/**","/payment-service/api/bill/**","/payment-service/api/report/**","/user-service/api/employment/**","/user-service/api/role/**", "/user-service/api/contact/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2->oauth2.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

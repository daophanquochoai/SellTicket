package doctorhoai.learn.proxy_client.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class AuthenticationConfig {
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "call findByUsername(?);"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "call findAuthorizationByUserName(?);"
        );
        return jdbcUserDetailsManager;
    }

    @Bean
    public AuthenticationManager authenticationManager(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
        var authentication = new DaoAuthenticationProvider();
        authentication.setUserDetailsService(jdbcUserDetailsManager);
        authentication.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authentication);
    }
}

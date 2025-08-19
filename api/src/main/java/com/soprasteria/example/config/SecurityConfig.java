package com.soprasteria.example.config;

import javax.sql.DataSource;

import com.soprasteria.example.domain.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] CUSTOMER_ENDPOINTS = {
        "/api/customers/**",
    };

    private static final String[] ADMIN_ENDPOINTS = {
        "/api/admin/**",
    };

    private final String username;

    private final String password;

    public SecurityConfig(
        @Value("${spring.security.user.name}") final String username,
        @Value("${spring.security.user.password}") final String password
    ) {
        this.username = username;
        this.password = password;
    }

    /* JDBC UserDetailsManager - Default Schema from JdbcDaoImpl
    @Bean
    public UserDetailsService jdbcUserDetailsService(final DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    */

    /*  In Memory UserDetailsManager
    @Bean
    public InMemoryUserDetailsManager userDetailsService(final PasswordEncoder passwordEncoder) {
        final UserDetails anyUser = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(anyUser);
    }
    */

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(createRequestMatchers())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> createRequestMatchers() {
        return (authz) ->
            authz.requestMatchers(CUSTOMER_ENDPOINTS).hasRole(RoleType.USER.name())
                .requestMatchers(ADMIN_ENDPOINTS).hasRole(RoleType.ADMIN.name())
                .anyRequest().denyAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

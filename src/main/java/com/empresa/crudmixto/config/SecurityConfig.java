package com.empresa.crudmixto.config;

import com.empresa.crudmixto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userService.findByEmail(username);
            if (user.isPresent()) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.get().getEmail())
                        .password(user.get().getPassword())
                        .roles(user.get().getRole())
                        .build();
            }
            throw new RuntimeException("Usuario no encontrado");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/proyectos", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        // Allow logout via GET and POST (support both) to avoid 404 from simple links while keeping POST support
        .logoutRequestMatcher(new org.springframework.security.web.util.matcher.OrRequestMatcher(
            new org.springframework.security.web.util.matcher.AntPathRequestMatcher("/logout", "GET"),
            new org.springframework.security.web.util.matcher.AntPathRequestMatcher("/logout", "POST")
        ))
                .permitAll()
            );

        return http.build();
    }

}
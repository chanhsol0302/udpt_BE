package com.example.authservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF cho stateless API
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login").permitAll() // Cho phép truy cập /auth/login mà không cần xác thực
                .anyRequest().authenticated() // Tất cả các request khác đều yêu cầu xác thực
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Cấu hình không sử dụng session (stateless)
            );
        return http.build();
    }

    // Nếu bạn muốn dùng PasswordEncoder cho việc xác thực (khi kết nối User Service), bạn sẽ thêm bean này:
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //    return new BCryptPasswordEncoder();
    // }
}
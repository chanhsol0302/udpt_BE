package com.example.patientservice.security;

import com.example.patientservice.security.jwt.JwtAuthEntryPoint;
import com.example.patientservice.security.jwt.JwtAuthFilter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Đánh dấu lớp này là lớp cấu hình
@Configuration
// Kích hoạt tính năng bảo mật web của Spring boot
@EnableWebSecurity
public class SecurityConfig {
	// Inject JwtAuthFilter
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	// Inject JwtAuthEntryPoint để xử lý lỗi xác thực
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        // Tắt CSRF (Cross-Site Request Forgery) vì nó không cần thiết cho REST API không dùng session
        .csrf(csrf -> csrf.disable())
        // Cấu hình xử lý ngoại lệ: khi có lỗi xác thực, sử dụng unauthorizedHandler
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        // Cấu hình quản lý session: đặt chính sách là STATELESS
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Cấu hình quy tắc ủy quyền cho các yêu cầu HTTP
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/patients").permitAll()
            .requestMatchers("/api/patients/**").permitAll()
            
            // Mặc định, bất kỳ request nào khác không được định nghĩa cụ thể ở trên cũng yêu cầu xác thực
            .anyRequest().authenticated() 
        );

	    // Thêm JwtAuthFilter vào chuỗi bộ lọc của Spring Security.
	    // Nó sẽ được chạy TRƯỚC UsernamePasswordAuthenticationFilter
	    // đảm bảo JWT được xử lý đầu tiên.
    	http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    	return http.build();
    }
}
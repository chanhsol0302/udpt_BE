package com.example.patientservice.security.jwt;

// Các thư viện của Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// JwtAuthEntryPoint xử lý các trường hợp người dùng truy cập tài nguyên được bảo vệ mà không có xác thực hợp lệ.
// Nó sẽ trả về lỗi 401 Unauthorized.
@Component // Đánh dấu là Spring component để Spring có thể tự động inject
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Thiết lập loại nội dung phản hồi là JSON
        response.setContentType("application/json");
        // Thiết lập trạng thái HTTP là 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Ghi thông báo lỗi JSON vào body của phản hồi
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
        // System.err.println("Unauthorized error: " + authException.getMessage());
    }
}
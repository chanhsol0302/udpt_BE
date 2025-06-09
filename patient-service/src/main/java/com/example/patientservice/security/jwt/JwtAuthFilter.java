package com.example.patientservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User; // Sử dụng User của Spring Security làm UserDetails
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter; // Đảm bảo filter chạy một lần duy nhất cho mỗi request

import java.io.IOException;
import java.util.Collections; // Để tạo Authority đơn giản
import java.util.UUID;

// JwtAuthFilter là một Servlet Filter sẽ chặn các request để kiểm tra JWT token.
@Component // Đánh dấu là Spring component để có thể inject và được Spring quản lý
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // Inject JwtUtil để giải mã và xác thực token

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain // Chuỗi các filter tiếp theo
    ) throws ServletException, IOException {
        // Trích xuất Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String username = null;
        UUID userId = null;
        String role = null;

        // Kiểm tra xem header có tồn tại và bắt đầu bằng "Bearer " không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Nếu không có token hoặc sai định dạng, chuyển tiếp request đến các filter khác
            // hoặc đến endpoint được permitAll(). SecurityContextHolder sẽ vẫn rỗng.
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy phần token (bỏ "Bearer ")
        jwt = authHeader.substring(7); 

        // Xác thực Token bằng JwtUtil
        if (!jwtUtil.validateToken(jwt)) {
            // Nếu token không hợp lệ , từ chối request với 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Invalid or expired token.\"}");
            return; // Dừng xử lý request tại đây
        }

        try {
            // Trích xuất thông tin từ Token nếu token hợp lệ (JwtUtil)
            username = jwtUtil.extractUsername(jwt);
            userId = jwtUtil.extractUserId(jwt);
            role = jwtUtil.extractRole(jwt);
        } catch (Exception e) {
            // Nếu có lỗi khi trích xuất claims (thiếu 1 trong 3), cũng từ chối với 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Invalid token structure or missing claims: " + e.getMessage() + "\"}");
            return;
        }

        // Thiết lập Authentication vào SecurityContextHolder
        // Chỉ thiết lập nếu username đã được trích xuất và chưa có Authentication trong SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	// Tạo biến tạm thời để khẳng định là role không đổi
        	final String finalRole = role;
            // Tạo một đối tượng UserDetails đơn giản từ thông tin trong token.
            // Spring Security sử dụng UserDetails để đại diện cho người dùng đã xác thực.
            UserDetails userDetails = new User(
                username, // Username
                "",       // Mật khẩu là rỗng vì đã xác thực qua token, không cần mật khẩu thực
                Collections.singletonList(() -> "ROLE_" + finalRole.toUpperCase()) // Tạo GrantedAuthority từ role
                                                                           // Nếu role là "PATIENT", tạo "ROLE_PATIENT"
            );

            // Tạo đối tượng xác thực (Authentication object)
            // UsernamePasswordAuthenticationToken là một loại Authentication cơ bản.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, // Principal (UserDetails)
                    null,        // Credentials (null vì đã xác thực bằng token)
                    userDetails.getAuthorities() // Quyền hạn của người dùng
            );
            // Thêm chi tiết request (IP, session ID nếu có) vào đối tượng xác thực
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Lưu userId và role vào request attributes.
            // Điều này giúp Controller dễ dàng lấy thông tin người dùng đã xác thực mà không cần parse lại token.
            request.setAttribute("userIdFromToken", userId);
            request.setAttribute("roleFromToken", role);

            // Thiết lập đối tượng xác thực vào SecurityContextHolder.
            // Điều này cho Spring Security biết rằng người dùng hiện tại đã được xác thực.
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Chuyển tiếp request đến các filter khác trong chuỗi hoặc đến Controller
        filterChain.doFilter(request, response);
    }
}
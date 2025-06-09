package com.example.patientservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.UUID;

// JwtUtil parse và validate JWT tokens.
@Component // Đánh dấu là Spring component để có thể inject vào các lớp khác
public class JwtUtil {

    // Khóa bí mật JWT, được inject từ application.properties
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Trả về đối tượng Key được tạo từ SECRET_KEY đã mã hóa Base64
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Trích xuất một claim cụ thể từ token bằng cách sử dụng hàm claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Trích xuất tất cả các claims từ token
    // Parse token và xác minh chữ ký
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Thiết lập khóa để xác minh chữ ký
                .build()
                .parseClaimsJws(token) // Phân tích token và kiểm tra chữ ký
                .getBody(); // Lấy phần payload (claims)
    }

    // Trích xuất "username" từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Trích xuất "userId" từ token
    public UUID extractUserId(String token) {
        // Lấy claim "userId" dưới dạng String và chuyển đổi sang UUID
        return UUID.fromString(extractClaim(token, claims -> claims.get("userId", String.class)));
    }

    // Trích xuất "role" từ token
    public String extractRole(String token) {
        // Lấy claim "role" dưới dạng String
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Trích xuất ngày hết hạn (expiration date) từ token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Kiểm tra token đã hết hạn chưa
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kiểm tra tính hợp lệ toàn diện của token
    // Bao gồm xác minh chữ ký và kiểm tra hết hạn
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            // Nếu không có exception, token là hợp lệ về mặt chữ ký và định dạng
            return !isTokenExpired(token); // Sau đó kiểm tra ngày hết hạn
        } catch (Exception e) {
            // Log lỗi để dễ debug hơn
            System.err.println("JWT Validation Error: " + e.getMessage());
            return false;
        }
    }
}
package vu.nh.training.AuthService.controller.dtos.requests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vu.nh.training.AuthService.controller.dtos.responses.JwtResponse;

@RestController
@RequestMapping("/api/middleware")
public class MiddleWare {
    //refreshToken and rotateToken
    @PostMapping("")
    public ResponseEntity<JwtResponse> verify(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(new JwtResponse("", "", 0));
        // Return the 401 Unauthorized
    }
}
/*
Description: Middleware xác thực token và kiểm tra quyền hạn người dùng khi truy cập các endpoint khác. Được gọi trong các service khác để xác thực JWT qua Keycloak.
Logic:
Lấy token từ header Authorization.
Kiểm tra tính hợp lệ và xác thực token với Keycloak.
Xác định vai trò và quyền truy cập từ token.
*/
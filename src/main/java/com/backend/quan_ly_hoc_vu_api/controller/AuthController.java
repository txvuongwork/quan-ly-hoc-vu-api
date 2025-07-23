package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.config.jwt.SecurityUtils;
import com.backend.quan_ly_hoc_vu_api.dto.AuthDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthResponse> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        AuthDTO.AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        Long id = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(authService.getProfile(id));
    }

}
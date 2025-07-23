package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.config.jwt.GenerateJwtResult;
import com.backend.quan_ly_hoc_vu_api.config.jwt.JwtProvider;
import com.backend.quan_ly_hoc_vu_api.dto.AuthDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.helper.exception.AuthenticationException;
import com.backend.quan_ly_hoc_vu_api.model.User;
import com.backend.quan_ly_hoc_vu_api.model.UserSession;
import com.backend.quan_ly_hoc_vu_api.service.AuthService;
import com.backend.quan_ly_hoc_vu_api.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    UserService userService;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;

    @Override
    @Transactional
    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest request) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException(INVALID_CREDENTIAL_ERR));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException(INVALID_CREDENTIAL_ERR);
        }

        GenerateJwtResult jwtTokens = jwtProvider.generateToken(user);

        user.setNewSession(new UserSession(jwtTokens.tokenId(), jwtTokens.expiredDate()));

        return AuthDTO.AuthResponse.builder()
                .token(jwtTokens.accessToken())
                .tokenType("Bearer")
                .user(userService.mapToDTO(user))
                .message("Login successful")
                .build();
    }

    @Override
    public UserDTO getProfile(Long id) {
        User user = userService.getUserByIdOrThrow(id);
        return userService.mapToDTO(user);
    }

}

package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.AuthDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;

public interface AuthService {
    AuthDTO.AuthResponse login(AuthDTO.LoginRequest request);
    UserDTO getProfile(Long id);
}

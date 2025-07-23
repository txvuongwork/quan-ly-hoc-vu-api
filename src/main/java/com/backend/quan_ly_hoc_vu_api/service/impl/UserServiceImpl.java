package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.config.jwt.SecurityUtils;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import com.backend.quan_ly_hoc_vu_api.model.User;
import com.backend.quan_ly_hoc_vu_api.repository.UserRepository;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import com.backend.quan_ly_hoc_vu_api.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    MajorService majorService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND_ERROR));
    }

    @Override
    public UserDTO mapToDTO(User user) {
        Major major = user.getMajor();

        if (major == null) {
            return UserDTO.builder()
                          .id(user.getId())
                          .email(user.getEmail())
                          .fullName(user.getFullName())
                          .role(user.getRole())
                          .build();
        }

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .major(majorService.mapToDTO(major))
                .build();
    }

    @Override
    public User getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BadRequestException(USER_NOT_FOUND_ERROR);
        }
        return getUserByIdOrThrow(userId);
    }


    @Override
    public User getUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND_ERROR));
    }

}
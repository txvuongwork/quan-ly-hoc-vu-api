package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.AuthDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.dto.request.ChangePasswordRequest;
import com.backend.quan_ly_hoc_vu_api.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> findByEmail(String email);

    UserDTO getUserById(Long id);

    UserDTO mapToDTO(User user);

    User getUserByIdOrThrow(Long id);

    User getCurrentUser();

    List<UserDTO> getAllTeachers();

}

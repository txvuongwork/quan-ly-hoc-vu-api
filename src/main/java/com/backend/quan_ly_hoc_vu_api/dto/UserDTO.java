package com.backend.quan_ly_hoc_vu_api.dto;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private MajorDTO major;
}
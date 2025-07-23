package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum UserRole {

    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

}

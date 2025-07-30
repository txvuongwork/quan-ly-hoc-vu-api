package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum EnrollmentStatus {

    COMPLETED("COMPLETED"),
    ENROLLED("ENROLLED"),
    DROPPED("DROPPED");

    private final String value;

    EnrollmentStatus(String value) {
        this.value = value;
    }

}

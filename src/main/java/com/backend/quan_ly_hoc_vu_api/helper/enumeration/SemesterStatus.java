package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum SemesterStatus {

    UPCOMING("UPCOMING"),
    ONGOING("ONGOING"),
    COMPLETED("COMPLETED");

    private final String value;

    SemesterStatus(String value) {
        this.value = value;
    }

}

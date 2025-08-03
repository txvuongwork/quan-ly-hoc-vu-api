package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum SemesterStatus {

    DRAFT("DRAFT"),
    REGISTRATION_OPEN("REGISTRATION_OPEN"),
    REGISTRATION_CLOSED("REGISTRATION_CLOSED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    SemesterStatus(String value) {
        this.value = value;
    }

}

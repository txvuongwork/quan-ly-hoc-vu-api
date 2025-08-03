package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum ClassStatus {

    DRAFT("DRAFT"),
    OPEN_FOR_REGISTRATION("OPEN_FOR_REGISTRATION"),
    CONFIRMED("CONFIRMED"),
    CANCELLED("CANCELLED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    ClassStatus(String value) {
        this.value = value;
    }

}

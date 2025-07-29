package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum ClassStatus {

    OPEN("OPEN"),
    CLOSED("CLOSED"),
    CANCELED("CANCELED");

    private final String value;

    ClassStatus(String value) {
        this.value = value;
    }

}

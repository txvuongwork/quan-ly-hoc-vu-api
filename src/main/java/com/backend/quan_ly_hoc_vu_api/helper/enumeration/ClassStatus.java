package com.backend.quan_ly_hoc_vu_api.helper.enumeration;

import lombok.Getter;

@Getter
public enum ClassStatus {

    OPENED("OPENED"),
    CLOSED("CLOSED"),
    CANCELED("CANCELED"),
    WAITING_REGISTER("WAITING_REGISTER"),;

    private final String value;

    ClassStatus(String value) {
        this.value = value;
    }

}

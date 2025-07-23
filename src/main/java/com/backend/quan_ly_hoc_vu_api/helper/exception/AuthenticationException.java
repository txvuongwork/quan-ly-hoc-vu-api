package com.backend.quan_ly_hoc_vu_api.helper.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.UNAUTHORIZED_TITLE;

public class AuthenticationException extends AbstractThrowableProblem {

    public AuthenticationException(String message) {
        super(null, UNAUTHORIZED_TITLE, Status.UNAUTHORIZED, message);
    }

}


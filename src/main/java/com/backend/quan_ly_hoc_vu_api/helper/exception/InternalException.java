package com.backend.quan_ly_hoc_vu_api.helper.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InternalException extends AbstractThrowableProblem {
    public InternalException() {
        super(null, "Internal Server Error", Status.INTERNAL_SERVER_ERROR, null);
    }

}



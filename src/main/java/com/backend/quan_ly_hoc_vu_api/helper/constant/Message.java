package com.backend.quan_ly_hoc_vu_api.helper.constant;

import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
public class Message {

    // Common Response Titles
    public static String BAD_REQUEST_TITLE = "Bad Request";
    public static String UNAUTHORIZED_TITLE = "Unauthorized";
    public static String INTERNAL_SERVER_TITLE = "Internal Server Error";
    public static String NOT_FOUND_TITLE = "Not Found";
    public static String FORBIDDEN_TITLE = "Forbidden";

    // Common Error Messages
    public static String BODY_MISSING_MSG = "Required request body is missing";
    public static String INTERNAL_SERVER_ERROR = "error.internal-server";
    public static String UNAUTHORIZED = "error.unauthorized";
    public static String BAD_REQUEST = "error.bad-request";
    public static String FORBIDDEN = "error.forbidden.access-denied";

    // Authentication & Authorization Errors
    public static final String EMAIL_REQUIRED_ERROR = "error.validate.email.required";
    public static final String EMAIL_FORMAT_ERROR = "error.validate.email.format";
    public static final String PASSWORD_REQUIRED_ERROR = "error.validate.password.required";
    public static final String PASSWORD_MIN_LENGTH_ERROR = "error.validate.password.min-length";
    public static final String LOGIN_TYPE_REQUIRED_ERROR = "error.validate.login-type.required";
    public static final String USER_NOT_AUTHENTICATED_ERROR = "error.user.not-authenticated";
    public static final String INVALID_CREDENTIAL_ERR = "error.validate.login.invalid-credential";
    public static final String UNIVERSITY_REQUIRED_ERROR = "error.validate.university.required";
    public static final String ACCOUNT_ALREADY_VERIFIED_ERROR = "error.account.already-verified";
    public static final String ACCOUNT_NOT_VERIFIED_ERROR = "error.account.not-verified";
    public static final String ACCOUNT_BLOCKED_ERROR = "error.account.blocked";

    // User Related Errors
    public static final String USER_NOT_FOUND_ERROR = "error.user.not-found";
    public static final String USER_ALREADY_EXISTS = "error.user.already-exists";
    public static final String USER_STUDENT_ID_EXISTS_ERROR = "error.user.student-id.exists";
    public static final String FULL_NAME_REQUIRED_ERROR = "error.validate.full-name.required";
    public static final String EMAIL_DOMAIN_MISMATCH_ERROR = "error.validate.email.domain-mismatch";
    public static final String FIRST_NAME_REQUIRED_ERROR = "error.validate.first-name.required";
    public static final String LAST_NAME_REQUIRED_ERROR = "error.validate.last-name.required";
    public static final String DATE_OF_BIRTH_REQUIRED_ERROR = "error.validate.date-of-birth.required";
    public static final String GENDER_REQUIRED_ERROR = "error.validate.gender.required";
    public static final String CURRENT_PASSWORD_REQUIRED_ERROR = "error.validate.current-password.required";
    public static final String CURRENT_PASSWORD_INCORRECT_ERROR = "error.validate.current-password.incorrect";
    public static final String NEW_PASSWORD_REQUIRED_ERROR = "error.validate.new-password.required";
    public static final String NEW_PASSWORD_SIZE_ERROR = "error.validate.new-password.size";

    // Subject Related Errors
    public static final String SUBJECT_CODE_REQUIRED_ERROR = "error.validate.subject-code.required";
    public static final String SUBJECT_NAME_REQUIRED_ERROR = "error.validate.subject-name.required";
    public static final String SUBJECT_CODE_EXISTED_ERROR = "error.validate.subject-code.existed";

}
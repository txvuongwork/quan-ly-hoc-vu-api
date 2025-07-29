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

    // Major
    public static final String MAJOR_NOT_FOUND_ERROR = "error.major.not-found";
    public static final String MAJOR_CODE_REQUIRED_ERROR = "error.validate.major-code.required";
    public static final String MAJOR_NAME_REQUIRED_ERROR = "error.validate.major-name.required";
    public static final String MAJOR_CODE_EXISTED_ERROR = "error.validate.major-code.existed";
    public static final String MAJOR_CANNOT_DELETE_ERROR = "error.major.cannot-delete.has-subjects";

    // Subject Related Errors
    public static final String SUBJECT_NOT_FOUND_ERROR = "error.subject.not-found";
    public static final String SUBJECT_CODE_REQUIRED_ERROR = "error.validate.subject-code.required";
    public static final String SUBJECT_NAME_REQUIRED_ERROR = "error.validate.subject-name.required";
    public static final String SUBJECT_CODE_EXISTED_ERROR = "error.validate.subject-code.existed";
    public static final String SUBJECT_MAJOR_REQUIRED_ERROR = "error.validate.subject-major.required";
    public static final String SUBJECT_CREDITS_REQUIRED_ERROR = "error.validate.subject-credits.required";
    public static final String SUBJECT_CANNOT_DELETE_ERROR = "error.subject.cannot-delete.has-classes";

    // Semester Related Errors
    public static final String SEMESTER_NOT_FOUND_ERROR = "error.semester.not-found";
    public static final String SEMESTER_NAME_REQUIRED_ERROR = "error.validate.semester-name.required";
    public static final String SEMESTER_YEAR_REQUIRED_ERROR = "error.validate.semester-year.required";
    public static final String SEMESTER_NUMBER_REQUIRED_ERROR = "error.validate.semester-number.required";
    public static final String SEMESTER_START_REQUIRED_ERROR = "error.validate.semester-start.required";
    public static final String SEMESTER_END_REQUIRED_ERROR = "error.validate.semester-end.required";
    public static final String REGISTRATION_START_REQUIRED_ERROR = "error.validate.registration-start.required";
    public static final String REGISTRATION_END_REQUIRED_ERROR = "error.validate.registration-end.required";
    public static final String SEMESTER_ACTIVE_REQUIRED_ERROR = "error.validate.semester-active.required";
    public static final String SEMESTER_YEAR_NUMBER_EXISTED_ERROR = "error.validate.semester-year-number.existed";

    // Class Related Errors
    public static final String CLASS_NOT_FOUND_ERROR = "error.class.not-found";
    public static final String CLASS_CODE_REQUIRED_ERROR = "error.validate.class-code.required";
    public static final String CLASS_CODE_EXISTED_ERROR = "error.validate.class-code.existed";
    public static final String CLASS_SUBJECT_REQUIRED_ERROR = "error.validate.class-subject.required";
    public static final String CLASS_SEMESTER_REQUIRED_ERROR = "error.validate.class-semester.required";
    public static final String CLASS_TEACHER_REQUIRED_ERROR = "error.validate.class-teacher.required";
    public static final String CLASS_MAX_STUDENTS_REQUIRED_ERROR = "error.validate.class-max-students.required";
    public static final String CLASS_MIN_STUDENTS_REQUIRED_ERROR = "error.validate.class-min-students.required";
    public static final String CLASS_START_DATE_REQUIRED_ERROR = "error.validate.class-start-date.required";
    public static final String CLASS_END_DATE_REQUIRED_ERROR = "error.validate.class-end-date.required";
    public static final String CLASS_PROCESS_PERCENT_REQUIRED_ERROR = "error.validate.class-process-percent.required";
    public static final String CLASS_MIDTERM_PERCENT_REQUIRED_ERROR = "error.validate.class-midterm-percent.required";
    public static final String CLASS_FINAL_PERCENT_REQUIRED_ERROR = "error.validate.class-final-percent.required";
    public static final String CLASS_STATUS_REQUIRED_ERROR = "error.validate.class-status.required";
    public static final String CLASS_PERCENT_TOTAL_INVALID_ERROR = "error.validate.class-percent.total-invalid";
    public static final String CLASS_MIN_MAX_STUDENTS_INVALID_ERROR = "error.validate.class.min-max-students-invalid";
    public static final String CLASS_DATE_RANGE_INVALID_ERROR = "error.validate.class.date-range-invalid";

    // Class Schedule Related Errors
    public static final String CLASS_SCHEDULES_REQUIRED_ERROR = "error.validate.class-schedules.required";
    public static final String CLASS_SCHEDULE_DAY_REQUIRED_ERROR = "error.validate.class-schedule-day.required";
    public static final String CLASS_SCHEDULE_DAY_INVALID_ERROR = "error.validate.class-schedule-day.invalid";
    public static final String CLASS_SCHEDULE_START_PERIOD_REQUIRED_ERROR = "error.validate.class-schedule-start-period.required";
    public static final String CLASS_SCHEDULE_END_PERIOD_REQUIRED_ERROR = "error.validate.class-schedule-end-period.required";
    public static final String CLASS_SCHEDULE_PERIOD_INVALID_ERROR = "error.validate.class-schedule-period.invalid";
    public static final String CLASS_SCHEDULE_DUPLICATE_DAY_ERROR = "error.validate.class-schedule.duplicate-day";

}
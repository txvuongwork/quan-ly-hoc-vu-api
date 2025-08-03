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
    public static final String INVALID_CREDENTIAL_ERR = "error.validate.login.invalid-credential";
    public static final String UNIVERSITY_REQUIRED_ERROR = "error.validate.university.required";
    public static final String USER_NOT_AUTHENTICATED_ERROR = "error.validate.not-authenticated";

    // User Related Errors
    public static final String USER_NOT_FOUND_ERROR = "error.user.not-found";
    public static final String FULL_NAME_REQUIRED_ERROR = "error.validate.full-name.required";
    public static final String CURRENT_PASSWORD_REQUIRED_ERROR = "error.validate.current-password.required";
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
    public static final String SEMESTER_CODE_REQUIRED_ERROR = "error.validate.semester-code.required";
    public static final String SEMESTER_CODE_EXISTED_ERROR = "error.validate.semester-code.existed";
    public static final String SEMESTER_START_DATE_REQUIRED_ERROR = "error.validate.semester-start-date.required";
    public static final String SEMESTER_END_DATE_REQUIRED_ERROR = "error.validate.semester-end-date.required";
    public static final String SEMESTER_DATE_RANGE_INVALID_ERROR = "error.validate.semester.date-range-invalid";
    public static final String SEMESTER_STATUS_REQUIRED_ERROR = "error.validate.semester-status.required";
    public static final String SEMESTER_STATUS_TRANSITION_INVALID_ERROR = "error.semester.status-transition.invalid";
    public static final String SEMESTER_DATE_OVERLAP_ERROR = "error.semester.date-overlap";

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
    public static final String CLASS_NOT_VALID_FOR_REGISTER_ERROR = "error.validate.class.not-valid-for-register";
    public static final String CLASS_STATUS_UPDATE_NOT_ALLOWED_ERROR = "error.class.status-update.not-allowed";
    public static final String CLASS_STATUS_UPDATE_INVALID_TARGET_ERROR = "error.class.status-update.invalid-target";

    // Class Schedule Related Errors
    public static final String CLASS_SCHEDULES_REQUIRED_ERROR = "error.validate.class-schedules.required";
    public static final String CLASS_SCHEDULE_DAY_REQUIRED_ERROR = "error.validate.class-schedule-day.required";
    public static final String CLASS_SCHEDULE_DAY_INVALID_ERROR = "error.validate.class-schedule-day.invalid";
    public static final String CLASS_SCHEDULE_START_PERIOD_REQUIRED_ERROR = "error.validate.class-schedule-start-period.required";
    public static final String CLASS_SCHEDULE_END_PERIOD_REQUIRED_ERROR = "error.validate.class-schedule-end-period.required";
    public static final String CLASS_SCHEDULE_PERIOD_INVALID_ERROR = "error.validate.class-schedule-period.invalid";
    public static final String CLASS_SCHEDULE_DUPLICATE_DAY_ERROR = "error.validate.class-schedule.duplicate-day";

    // Class Status Transition Errors
    public static final String CLASS_STATUS_TRANSITION_INVALID_ERROR = "error.class.status-transition.invalid";
    public static final String CLASS_STATUS_CANNOT_OPEN_REGISTRATION_NOT_ENDED_ERROR = "error.class.status.cannot-open.registration-not-ended";
    public static final String CLASS_STATUS_CANNOT_OPEN_INSUFFICIENT_STUDENTS_ERROR = "error.class.status.cannot-open.insufficient-students";
    public static final String CLASS_STATUS_CANNOT_CANCEL_REGISTRATION_NOT_ENDED_ERROR = "error.class.status.cannot-cancel.registration-not-ended";
    public static final String CLASS_STATUS_CANNOT_CLOSE_SEMESTER_NOT_ENDED_ERROR = "error.class.status.cannot-close.semester-not-ended";
    public static final String CLASS_STATUS_CANNOT_CLOSE_NOT_OPENED_ERROR = "error.class.status.cannot-close.not-opened";

    // Enrollment Related Errors
    public static final String ENROLLMENT_NOT_FOUND_ERROR = "error.enrollment.not-found";
    public static final String ENROLLMENT_CLASS_REQUIRED_ERROR = "error.validate.enrollment-class.required";
    public static final String ENROLLMENT_REGISTRATION_PERIOD_CLOSED_ERROR = "error.enrollment.registration-period-closed";
    public static final String ENROLLMENT_CLASS_FULL_ERROR = "error.enrollment.class-full";
    public static final String ENROLLMENT_ALREADY_ENROLLED_ERROR = "error.enrollment.already-enrolled";
    public static final String ENROLLMENT_SCHEDULE_CONFLICT_ERROR = "error.enrollment.schedule-conflict";
    public static final String ENROLLMENT_CREDIT_LIMIT_EXCEEDED_ERROR = "error.enrollment.credit-limit-exceeded";
    public static final String ENROLLMENT_CLASS_NOT_AVAILABLE_ERROR = "error.enrollment.class-not-available";
    public static final String ENROLLMENT_CANNOT_CANCEL_OUTSIDE_REGISTRATION_PERIOD_ERROR = "error.enrollment.cannot-cancel-outside-registration-period";
    public static final String ENROLLMENT_NO_ACTIVE_REGISTRATION_PERIOD_ERROR = "error.enrollment.no-active-registration-period";
    public static final String SEMESTER_CANNOT_START_UNCONFIRMED_CLASSES_ERROR = "error.semester.cannot-start.has-unconfirmed-classes";

}
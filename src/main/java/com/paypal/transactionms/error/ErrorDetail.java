package com.paypal.transactionms.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorDetail {

	// 1xxx SYSTEM errors
	INTERNAL_SERVER_ERROR("TRANSACTIONMS_1001", "internal.server.error", ErrorType.SYSTEM,
			"An internal server error occurred. Please try after sometime."),
	USER_NOT_AUTHENTICATED("TRANSACTIONMS_1002", "user.not.authenticated", ErrorType.SYSTEM, "User not authenticated."),
	NOT_ALLOWED_TO_USE_APP("TRANSACTIONMS_1003", "not.allowed.to.use.app", ErrorType.SYSTEM,
			"You are not allowed to use this app."),
	SOFT_UPGRADE_AVAILABLE("TRANSACTIONMS_1004", "soft.upgrade.available", ErrorType.SYSTEM, "Soft upgrade available."),
	HARD_UPGRADE_REQUIRED("TRANSACTIONMS_1005", "hard.upgrade.required", ErrorType.SYSTEM, "Hard upgrade required.");

	// 2xxx VALIDATION errors

	private static final String TO_STRING_TEMPLATE = "code: %s, propertyKey: %s, errorType: %s, defaultMessage: %s";

	String code;
	String propertyKey;
	ErrorType errorType;
	String defaultMessage;

	private ErrorDetail(String code, String propertyKey, ErrorType errorType, String defaultMessage) {
		this.code = code;
		this.propertyKey = propertyKey;
		this.errorType = errorType;
		this.defaultMessage = defaultMessage;
	}

	@Override
	public String toString() {
		return String.format(TO_STRING_TEMPLATE, code, propertyKey, errorType, defaultMessage);
	}

	public enum ErrorType {
		SYSTEM("system"), VALIDATION("validation"), BUSINESS("business");

		String error;

		private ErrorType(String error) {
			this.error = error;
		}

		@Override
		public String toString() {
			return error;
		}
	}

	public String getCode() {
		return code;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
}
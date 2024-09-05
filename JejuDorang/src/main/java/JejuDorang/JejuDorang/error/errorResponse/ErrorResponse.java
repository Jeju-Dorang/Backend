package JejuDorang.JejuDorang.error.errorResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import JejuDorang.JejuDorang.error.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
	private String code;
	private String message;
	private int status;
	private List<CustomFieldError> errors;

	@Getter
	public static class CustomFieldError{
		private String field;
		private String value;
		private String reason;

		private CustomFieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		private CustomFieldError(FieldError fieldError){
			this.field = fieldError.getField();
			this.value = fieldError.getRejectedValue().toString();
			this.reason = fieldError.getDefaultMessage();
		}

	}
	private void setErrorCode(ErrorCode errorCode){
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
	}

	private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
		setErrorCode(errorCode);
		this.errors = errors.stream().map(CustomFieldError::new).collect(Collectors.toList());
	}

	private ErrorResponse(ErrorCode errorCode, String exceptionMessage) {
		setErrorCode(errorCode);
		this.errors = List.of(new CustomFieldError("", "", exceptionMessage));
	}

	public static ErrorResponse of(ErrorCode errorCode){
		return new ErrorResponse(errorCode, Collections.emptyList());
	}
	public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult){
		return new ErrorResponse(errorCode, bindingResult.getFieldErrors());
	}

	public static ErrorResponse of(ErrorCode errorCode, String exceptionMessage){
		return new ErrorResponse(errorCode, exceptionMessage);
	}

}

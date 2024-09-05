package JejuDorang.JejuDorang.error.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import JejuDorang.JejuDorang.error.errorResponse.ErrorResponse;
import JejuDorang.JejuDorang.error.errorcode.ErrorCode;
import JejuDorang.JejuDorang.error.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		ErrorCode errorCode = e.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity
			.status(errorCode.getHttpStatus().value())
			.body(JejuDorang.JejuDorang.error.errorResponse.ErrorResponse.of(errorCode));
	}
}

package JejuDorang.JejuDorang.error.exception;

import JejuDorang.JejuDorang.error.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}

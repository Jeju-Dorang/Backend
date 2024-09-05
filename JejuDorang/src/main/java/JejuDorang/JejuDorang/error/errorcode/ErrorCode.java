package JejuDorang.JejuDorang.error.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	String name();
	HttpStatus getHttpStatus();
	String getMessage();
	String getCode();
	int getStatus();
}

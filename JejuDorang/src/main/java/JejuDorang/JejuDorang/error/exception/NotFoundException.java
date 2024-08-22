package JejuDorang.JejuDorang.error.exception;

import JejuDorang.JejuDorang.error.errorcode.CommonErrorCode;
import JejuDorang.JejuDorang.error.errorcode.ErrorCode;

public class NotFoundException extends CustomException {
	public NotFoundException() {
		super(CommonErrorCode.RESOURCE_NOT_FOUND);
	}
}


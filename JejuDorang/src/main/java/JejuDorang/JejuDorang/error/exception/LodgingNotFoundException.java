package JejuDorang.JejuDorang.error.exception;

import JejuDorang.JejuDorang.error.errorcode.CommonErrorCode;

public class LodgingNotFoundException extends CustomException{
	public LodgingNotFoundException() {
		super(CommonErrorCode.LODGING_NOT_FOUND);
	}
}

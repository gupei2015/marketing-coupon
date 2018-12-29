package lk.project.marketing.client.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -5771568110674413033L;

	/**
	 * 错误码
	 */
	private Integer errorCode;

	/**
	 * 返回的错误消息
	 */
	private String resultMsg;

	public BusinessException(int errorCode, String retMsg) {
		super(retMsg);
		this.resultMsg = retMsg;
		this.errorCode = errorCode;
		log.error(retMsg);
	}

	public BusinessException(int errorCode, String retMsg, String message) {
		super(message);
		this.resultMsg = retMsg;
		this.errorCode = errorCode;
		log.error(retMsg);
	}
	
	public BusinessException(int errorCode, String retMsg, Throwable cause) {
		super(cause);
		this.resultMsg = retMsg;
		this.errorCode = errorCode;
		log.error(retMsg);
	}

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause,
							 boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.resultMsg = message;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.resultMsg = message;
	}

	public BusinessException(String message) {
		super(message);
		this.resultMsg = message;
	}

	public BusinessException(Throwable cause) {
		super(cause);
		this.resultMsg = cause.getMessage();
	}

	public BusinessException(BusinessErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMessage());
		this.resultMsg = errorCodeEnum.getMessage();
		this.errorCode = errorCodeEnum.getCode();
	}

}


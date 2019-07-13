package cn.tedu.store.controller.exception;

/**
 *請求異常,是當前項目中控制器類拋出異常的基類
 */
public class RequestException extends RuntimeException {

	private static final long serialVersionUID = 1117101227251814290L;

	public RequestException() {
		super();
	}

	public RequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestException(String message) {
		super(message);
	}

	public RequestException(Throwable cause) {
		super(cause);
	}
	
}

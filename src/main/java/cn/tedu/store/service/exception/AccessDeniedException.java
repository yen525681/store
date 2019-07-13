package cn.tedu.store.service.exception;

/**
 * 訪問被拒絕的異常
 */
public class AccessDeniedException extends ServiceException {

	private static final long serialVersionUID = -5522947907918029773L;

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}
	
}

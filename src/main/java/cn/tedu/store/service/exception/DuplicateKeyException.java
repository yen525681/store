package cn.tedu.store.service.exception;

/**
 * 違反了Unique約束的異常
 */
public class DuplicateKeyException extends ServiceException {

	private static final long serialVersionUID = 7342082855256795362L;

	public DuplicateKeyException() {
		super();
	}

	public DuplicateKeyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateKeyException(String message) {
		super(message);
	}

	public DuplicateKeyException(Throwable cause) {
		super(cause);
	}
	
}

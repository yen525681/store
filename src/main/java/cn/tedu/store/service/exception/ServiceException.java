package cn.tedu.store.service.exception;

/**
 * 業務異常,是當前項目中所有業務異常的基類
 */
public class ServiceException  extends RuntimeException{

	private static final long serialVersionUID = 3479587231040893413L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
}

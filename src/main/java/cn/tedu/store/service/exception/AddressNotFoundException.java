package cn.tedu.store.service.exception;

/**
 * 收貨地址數據不存在
 */
public class AddressNotFoundException extends ServiceException {

	private static final long serialVersionUID = -6743411944054424816L;

	public AddressNotFoundException() {
		super();
	}

	public AddressNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AddressNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddressNotFoundException(String message) {
		super(message);
	}

	public AddressNotFoundException(Throwable cause) {
		super(cause);
	}
	
}

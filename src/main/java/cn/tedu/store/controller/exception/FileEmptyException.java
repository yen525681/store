package cn.tedu.store.controller.exception;

/**
 * 上傳文件為空異常
 */
public class FileEmptyException extends FileUploadException {

	private static final long serialVersionUID = 3888506555536311435L;

	public FileEmptyException() {
		super();
	}

	public FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileEmptyException(String message) {
		super(message);
	}

	public FileEmptyException(Throwable cause) {
		super(cause);
	}
	
}

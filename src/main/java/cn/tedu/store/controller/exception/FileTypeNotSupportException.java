package cn.tedu.store.controller.exception;

/**
 * 上傳不支持文件類型異常
 */
public class FileTypeNotSupportException extends FileUploadException {

	private static final long serialVersionUID = -6386965701894054889L;

	public FileTypeNotSupportException() {
		super();
	}

	public FileTypeNotSupportException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileTypeNotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileTypeNotSupportException(String message) {
		super(message);
	}

	public FileTypeNotSupportException(Throwable cause) {
		super(cause);
	}
	
}

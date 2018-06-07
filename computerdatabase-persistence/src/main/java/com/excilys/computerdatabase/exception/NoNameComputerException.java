package com.excilys.computerdatabase.exception;

public class NoNameComputerException extends ValidationException {

	private static final long serialVersionUID = 1L;
	
	public NoNameComputerException() {
	}

	public NoNameComputerException(String message) {
		super(message);
	}

	public NoNameComputerException(Throwable cause) {
		super(cause);
	}

	public NoNameComputerException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoNameComputerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

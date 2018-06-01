package com.excilys.computerdatabase.exception;

public class DateDiscontinuedIntroducedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateDiscontinuedIntroducedException() {
	}

	public DateDiscontinuedIntroducedException(String message) {
		super(message);
	}

	public DateDiscontinuedIntroducedException(Throwable cause) {
		super(cause);
	}

	public DateDiscontinuedIntroducedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateDiscontinuedIntroducedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

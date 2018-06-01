package com.excilys.computerdatabase.exception;

public class CompanyDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyDoesNotExistException() {
	}

	public CompanyDoesNotExistException(String arg0) {
		super(arg0);
	}

	public CompanyDoesNotExistException(Throwable arg0) {
		super(arg0);
	}

	public CompanyDoesNotExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CompanyDoesNotExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}

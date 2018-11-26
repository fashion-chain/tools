package org.fok.tools.thread.exception;

import javassist.NotFoundException;

public class ThreadPoolAlreadyExistsException extends NotFoundException {
	public ThreadPoolAlreadyExistsException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

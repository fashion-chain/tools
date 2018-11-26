package org.fok.tools.thread.exception;

import javassist.NotFoundException;

public class ThreadPoolNotExistsException extends NotFoundException {
	public ThreadPoolNotExistsException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

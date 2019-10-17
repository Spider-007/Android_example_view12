package com.htmitech.emportal.base;

public class CommunicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5205214329431905205L;

	public CommunicationException() {
	}

	/**
	 * Constructs a new CommunicationException with the current stack
     * trace and the specified detail message.
	 * 
	 * @param detailMessage
	 *            the detail message for this exception.
	 */
	public CommunicationException(String detailMessage) {
		super(detailMessage);
	}

}

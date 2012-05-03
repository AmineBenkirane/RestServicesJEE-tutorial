/**
 * 
 */
package org.tutorials.persons.dao.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author a.benkirane
 *
 */
public class TechnicalException extends RuntimeException {
	private static final Logger log = LoggerFactory.getLogger(TechnicalException.class);

	private static final long serialVersionUID = 1L;

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
		log.error(message);
	}

	public TechnicalException(String message) {
		super(message);
		log.error(message);
	}

	public TechnicalException(Exception exception) {
		super(exception);
	}

	public String toString(){
		return this.getMessage();
	}
}

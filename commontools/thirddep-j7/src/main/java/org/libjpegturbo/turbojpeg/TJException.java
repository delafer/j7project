/**
 *
 */
package org.libjpegturbo.turbojpeg;

/**
 * @author Korvin
 *
 */
public class TJException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 5739825299598164754L;

	/**
	 *
	 */
	public TJException() {
		super();
	}

	/**
	 * @param message
	 */
	public TJException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TJException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TJException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TJException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
		//super(message, cause, enableSuppression, writableStackTrace);
	}

}

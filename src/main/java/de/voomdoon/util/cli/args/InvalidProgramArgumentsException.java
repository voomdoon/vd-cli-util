package de.voomdoon.util.cli.args;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class InvalidProgramArgumentsException extends Exception {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -1062301974641691277L;

	/**
	 * DOCME add JavaDoc for constructor InvalidProgramArgumentsException
	 * 
	 * @param message
	 * @since 0.1.0
	 */
	protected InvalidProgramArgumentsException(String message) {
		super(message);
	}
}

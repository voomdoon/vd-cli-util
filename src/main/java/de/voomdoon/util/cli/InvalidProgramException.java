package de.voomdoon.util.cli;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class InvalidProgramException extends Exception {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -7583479993117570446L;

	/**
	 * DOCME add JavaDoc for constructor InvalidProgramException
	 * 
	 * @param e
	 * @since 0.1.0
	 */
	public InvalidProgramException(NoSuchMethodException e) {
		super(e);
	}
}

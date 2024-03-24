package de.voomdoon.util.cli;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class InvalidSubProgramException extends Exception {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = 4940648216526706202L;

	/**
	 * DOCME add JavaDoc for constructor InvalidSubProgramException
	 * 
	 * @param e
	 * @since 0.1.0
	 */
	public InvalidSubProgramException(NoSuchMethodException e) {
		super(e);
	}

	/**
	 * DOCME add JavaDoc for constructor InvalidSubProgramException
	 * 
	 * @param message
	 * @since 0.1.0
	 */
	public InvalidSubProgramException(String message) {
		super(message);
	}
}

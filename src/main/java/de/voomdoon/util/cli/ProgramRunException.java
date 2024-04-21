package de.voomdoon.util.cli;

import de.voomdoon.util.cli.args.InvalidProgramArgumentsException;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class ProgramRunException extends RuntimeException {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -6595043002148868007L;

	/**
	 * @since 0.1.0
	 */
	private final String helpString;

	/**
	 * DOCME add JavaDoc for constructor ProgramRunException
	 * 
	 * @param message
	 * @param cause
	 * @since 0.1.0
	 */
	public ProgramRunException(String message, InvalidProgramArgumentsException cause) {
		super(message, cause);

		this.helpString = null;
	}

	/**
	 * DOCME add JavaDoc for constructor ProgramRunException
	 * 
	 * @param message
	 * @param helpString
	 * @since 0.1.0
	 */
	public ProgramRunException(String message, String helpString) {
		super(message);

		this.helpString = helpString;
	}

	/**
	 * DOCME add JavaDoc for constructor ProgramRunException
	 * 
	 * @param cause
	 * @since 0.1.0
	 */
	public ProgramRunException(Throwable cause) {
		super(cause);

		this.helpString = null;
	}

	/**
	 * @return helpString
	 * @since 0.1.0
	 */
	public String getHelpString() {
		return helpString;
	}
}

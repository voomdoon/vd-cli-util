package de.voomdoon.util.cli.args;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class InvalidProgramArgumentsException extends Exception {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -9008776324532151311L;

	/**
	 * DOCME add JavaDoc for constructor InvalidProgramArgumentsException
	 * 
	 * @param option
	 * @param message
	 * @since 0.1.0
	 */
	public InvalidProgramArgumentsException(Option option, String message) {
		super("Invalid argument for option " + option.longName() + ": " + message);
	}
}

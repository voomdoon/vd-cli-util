package de.voomdoon.util.cli.args;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class InvalidProgramOptionException extends InvalidProgramArgumentsException {

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
	public InvalidProgramOptionException(Option option, String message) {
		super("Invalid argument for option '" + option.longName() + "': " + message);
	}
}

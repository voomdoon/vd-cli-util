package de.voomdoon.util.cli;

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
	private String helpString;

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
	}

	/**
	 * @return helpString
	 * @since 0.1.0
	 */
	public String getHelpString() {
		return helpString;
	}
}

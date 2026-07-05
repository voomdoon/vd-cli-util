package de.voomdoon.util.cli;

/**
 * Exception thrown by {@link Program#run()}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class ProgramRunException extends RuntimeException {

	/**
	 * Serialization version.
	 *
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -6595043002148868007L;

	/**
	 * Help text associated with the failure.
	 *
	 * @since 0.1.0
	 */
	private final String helpString;

	/**
	 * Creates a program run exception with help text.
	 * 
	 * @param message
	 *            detail message as {@link String}
	 * @param helpString
	 *            help text as {@link String}
	 * @since 0.1.0
	 */
	public ProgramRunException(String message, String helpString) {
		super(message);

		this.helpString = helpString;
	}

	/**
	 * Creates a program run exception wrapping a cause.
	 * 
	 * @param cause
	 *            underlying {@link Throwable}
	 * @since 0.1.0
	 */
	public ProgramRunException(Throwable cause) {
		// TESTME
		super(cause);

		this.helpString = null;
	}

	/**
	 * Returns the help text.
	 *
	 * @return help text as {@link String}, or {@code null}
	 * @since 0.1.0
	 */
	public String getHelpString() {
		return helpString;
	}
}

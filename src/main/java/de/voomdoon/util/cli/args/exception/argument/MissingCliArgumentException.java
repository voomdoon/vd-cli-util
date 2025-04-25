package de.voomdoon.util.cli.args.exception.argument;

/**
 * {@link CliArgumentException} for missing positional argument.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class MissingCliArgumentException extends CliArgumentException {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = 822359137130262340L;

	/**
	 * @since 0.1.0
	 */
	private String argumentName;

	/**
	 * @param argumentName
	 *            {@link String}
	 * @since 0.1.0
	 */
	public MissingCliArgumentException(String argumentName) {
		super("Missing argument: " + argumentName + "!");

		this.argumentName = argumentName;
	}

	/**
	 * @return argumentName
	 * @since 0.1.0
	 */
	public String getArgumentName() {
		return argumentName;// TESTME
	}
}

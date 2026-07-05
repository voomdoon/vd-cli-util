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
	 * Serialization version.
	 *
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = 822359137130262340L;

	/**
	 * Name of the missing argument.
	 *
	 * @since 0.1.0
	 */
	private String argumentName;

	/**
	 * Creates an exception for a missing positional argument.
	 *
	 * @param argumentName
	 *            {@link String}
	 * @since 0.1.0
	 */
	public MissingCliArgumentException(String argumentName) {
		super("Missing argument: " + argumentName + "!");

		this.argumentName = argumentName;
	}

	/**
	 * Returns the missing argument name.
	 *
	 * @return argument name as {@link String}
	 * @since 0.1.0
	 */
	public String getArgumentName() {
		return argumentName;// TESTME
	}
}

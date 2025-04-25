package de.voomdoon.util.cli.args.exception.argument;

import de.voomdoon.util.cli.args.exception.CliInputException;

/**
 * Base {@link CliInputException} for issues related to positional arguments.
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
public abstract class CliArgumentException extends CliInputException {

	/**
	 * @since 0.2.0
	 */
	private static final long serialVersionUID = 8215436733104103632L;

	/**
	 * @param message
	 *            {@link String}
	 * @since 0.2.0
	 */
	protected CliArgumentException(String message) {
		super(message);
	}
}

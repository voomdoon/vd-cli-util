package de.voomdoon.util.cli.args.exception;

import java.util.Objects;

/**
 * Base {@link Exception} for all issues related to CLI input.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class CliInputException extends Exception {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -1062301974641691277L;

	/**
	 * Creates a CLI input exception.
	 *
	 * @param message
	 *            detail message as {@link String}
	 * @since 0.1.0
	 */
	protected CliInputException(String message) {
		super(Objects.requireNonNull(message, "message"));
	}
}

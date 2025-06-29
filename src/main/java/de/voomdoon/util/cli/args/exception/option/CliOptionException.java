package de.voomdoon.util.cli.args.exception.option;

import java.util.Objects;

import de.voomdoon.util.cli.args.Option;
import de.voomdoon.util.cli.args.exception.CliInputException;

/**
 * Base {@link CliInputException} for issues related to named options.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class CliOptionException extends CliInputException {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = -9008776324532151311L;

	/**
	 * DOCME add JavaDoc for method generateMessage
	 * 
	 * @param option
	 * @param message
	 * @return
	 * @since 0.1.0
	 */
	private static String generateMessage(Option option, String message) {
		if (option.mandatory()) {
			return "Missing mandatory CLI option '" + Objects.requireNonNull(option, "option").longName() + "':!";
		}

		return "Invalid CLI option '" + Objects.requireNonNull(option, "option").longName() + "': "
				+ Objects.requireNonNull(message, "message") + "!";
	}

	/**
	 * @param option
	 *            {@link Option}
	 * @param message
	 *            {@link String}
	 * @since 0.1.0
	 */
	public CliOptionException(Option option, String message) {
		super(generateMessage(option, message));
	}
}

package de.voomdoon.util.cli.args.exception.option;

import java.util.Objects;

import de.voomdoon.util.cli.args.Option;

/**
 * {@link CliOptionException} for missing {@link Option} value.
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
public class MissingCliOptionValueException extends CliOptionException {

	/**
	 * @since 0.2.0
	 */
	private static final long serialVersionUID = 2327175536166172631L;

	/**
	 * @param option
	 *            {@link Option}
	 * @since 0.2.0
	 */
	public MissingCliOptionValueException(Option option) {
		super(Objects.requireNonNull(option, "option"), "missing value");
	}
}

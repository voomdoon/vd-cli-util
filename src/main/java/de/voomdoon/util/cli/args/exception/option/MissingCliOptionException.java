package de.voomdoon.util.cli.args.exception.option;

import de.voomdoon.util.cli.args.Option;

//TESTME

/**
 * {@link CliOptionException} for missing {@link Option}.
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
public class MissingCliOptionException extends CliOptionException {

	/**
	 * @since 0.2.0
	 */
	private static final long serialVersionUID = 6655911921020642425L;

	/**
	 * Creates an exception for a missing option.
	 * 
	 * @param option
	 *            missing {@link Option}
	 * @since 0.2.0
	 */
	public MissingCliOptionException(Option option) {
		super(option, "missing");
	}
}

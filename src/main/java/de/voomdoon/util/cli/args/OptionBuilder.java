package de.voomdoon.util.cli.args;

import java.util.function.Consumer;

/**
 * Builder for {@link Option}.
 * 
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class OptionBuilder {

	/**
	 * @since 0.1.0
	 */
	private Consumer<Option> callback;

	/**
	 * @since 0.1.0
	 */
	private String longName;

	/**
	 * @since 0.1.0
	 */
	private boolean mandatory;

	/**
	 * @since 0.1.0
	 */
	private String valueName;

	/**
	 * Creates a builder that passes built options to a callback.
	 * 
	 * @param callback
	 *            callback as {@link Consumer}
	 * @since 0.1.0
	 */
	public OptionBuilder(Consumer<Option> callback) {
		this.callback = callback;
	}

	/**
	 * Builds the option.
	 *
	 * @return {@link Option}
	 * @since 0.1.0
	 */
	public Option build() {
		Option option = new Option(longName, valueName, mandatory);

		callback.accept(option);

		return option;
	}

	/**
	 * Configures this option to accept a value.
	 *
	 * @param valueName
	 *            value name as {@link String}
	 * @return {@link OptionBuilder}
	 * @since 0.1.0
	 */
	public OptionBuilder hasValue(String valueName) {
		this.valueName = valueName;

		return this;
	}

	/**
	 * Marks this option as mandatory.
	 * 
	 * @return this {@link OptionBuilder}
	 * @since 0.1.0
	 */
	public OptionBuilder isMandatory() {
		this.mandatory = true;

		return this;
	}

	/**
	 * Sets the long option name.
	 *
	 * @param longName
	 *            long name as {@link String}
	 * @return {@link OptionBuilder}
	 * @since 0.1.0
	 */
	public OptionBuilder longName(String longName) {
		this.longName = longName;

		return this;
	}
}

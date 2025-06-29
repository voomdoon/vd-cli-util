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

	private boolean mandatory;

	/**
	 * @since 0.1.0
	 */
	private String valueName;

	/**
	 * DOCME add JavaDoc for constructor OptionBuilder
	 * 
	 * @param callback
	 * @since 0.1.0
	 */
	public OptionBuilder(Consumer<Option> callback) {
		this.callback = callback;
	}

	/**
	 * @return
	 * @since 0.1.0
	 */
	public Option build() {
		Option option = new Option(longName, valueName, mandatory);

		callback.accept(option);

		return option;
	}

	/**
	 * @param valueName
	 * @return {@link OptionBuilder}
	 * @since 0.1.0
	 */
	public OptionBuilder hasValue(String valueName) {
		this.valueName = valueName;

		return this;
	}

	/**
	 * DOCME add JavaDoc for method isMandatory
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public OptionBuilder isMandatory() {
		this.mandatory = true;

		return this;
	}

	/**
	 * @param longName
	 *            longName
	 * @return {@link OptionBuilder}
	 * @since 0.1.0
	 */
	public OptionBuilder longName(String longName) {
		this.longName = longName;

		return this;
	}
}

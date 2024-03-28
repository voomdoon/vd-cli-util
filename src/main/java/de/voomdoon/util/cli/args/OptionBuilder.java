package de.voomdoon.util.cli.args;

import java.util.function.Consumer;

/**
 * DOCME add JavaDoc for
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
	private boolean hasValue;

	/**
	 * @since 0.1.0
	 */
	private String longName;

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
		Option option = new Option(longName, hasValue);

		callback.accept(option);

		return option;
	}

	/**
	 * @return {@link OptionBuilder}
	 * @since 0.1.0
	 */
	public OptionBuilder hasValue() {
		this.hasValue = true;

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

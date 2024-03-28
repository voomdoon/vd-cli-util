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
		Option option = new Option(longName);

		callback.accept(option);

		return option;
	}

	/**
	 * @param longName
	 *            longName
	 * @return
	 * @since 0.1.0
	 */
	public OptionBuilder setLongName(String longName) {
		this.longName = longName;

		return this;
	}
}

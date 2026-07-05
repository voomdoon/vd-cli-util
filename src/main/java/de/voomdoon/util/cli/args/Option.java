package de.voomdoon.util.cli.args;

//TODO rename valueName to valueLabel

/**
 * Command line option definition.
 *
 * @author André Schulz
 *
 * @param longName
 *            long name as {@link String}
 * @param valueName
 *            optional value name as {@link String}
 * @param mandatory
 *            whether the option is mandatory
 *
 * @since 0.1.0
 */
public record Option(String longName, String valueName, boolean mandatory) {

	/**
	 * Reports whether this option accepts a value.
	 *
	 * @return {@code true} if a value is accepted
	 * @since 0.1.0
	 */
	public boolean hasValue() {
		return valueName() != null;
	}
}

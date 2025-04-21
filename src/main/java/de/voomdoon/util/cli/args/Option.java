package de.voomdoon.util.cli.args;

//TODO rename valueName to valueLabel

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public record Option(String longName, String valueName) {

	/**
	 * @return
	 * @since 0.1.0
	 */
	public boolean hasValue() {
		return valueName() != null;
	}
}

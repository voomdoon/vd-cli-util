package de.voomdoon.util.cli.args;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public record Option(String longName, String valueName) {

	/**
	 * DOCME add JavaDoc for method hasValue
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public boolean hasValue() {
		return valueName() != null;
	}
}

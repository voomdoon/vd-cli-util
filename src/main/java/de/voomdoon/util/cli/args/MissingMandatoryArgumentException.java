package de.voomdoon.util.cli.args;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class MissingMandatoryArgumentException extends Exception {

	/**
	 * @since 0.1.0
	 */
	private static final long serialVersionUID = 822359137130262340L;

	/**
	 * @since 0.1.0
	 */
	private String argumentName;

	/**
	 * DOCME add JavaDoc for constructor MIssingMandatoryArgumentException
	 * 
	 * @param argumentName
	 * @since 0.1.0
	 */
	public MissingMandatoryArgumentException(String argumentName) {
		super("Missing mandatory argument: " + argumentName + "!");

		this.argumentName = argumentName;
	}

	/**
	 * @return argumentName
	 * @since 0.1.0
	 */
	public String getArgumentName() {
		return argumentName;
	}
}

package de.voomdoon.util.cli;

/**
 * Exception thrown by {@link ProgramRunner#run(String[])} and {@link ProgramRunner#run(Class, String[])}.
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
public class ProgramExecutionException extends RuntimeException {

	/**
	 * @since 0.2.0
	 */
	private static final long serialVersionUID = 8209674709863855600L;

	/**
	 * Creates an exception wrapping a program execution failure.
	 * 
	 * @param cause
	 *            underlying {@link Throwable}
	 * @since 0.2.0
	 */
	public ProgramExecutionException(Throwable cause) {
		super(cause);
	}
}

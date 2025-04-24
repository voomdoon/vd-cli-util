package de.voomdoon.util.cli;

/**
 * Exception thrown by {@link ProgramRunner#run(String[])} and {@link ProgramRunner#run(Class, String[])}.
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
class ProgramExecutionException extends RuntimeException {

	/**
	 * @since 0.2.0
	 */
	private static final long serialVersionUID = 8209674709863855600L;

	/**
	 * DOCME add JavaDoc for constructor ProgramRunningException
	 * 
	 * @param cause
	 * @since 0.2.0
	 */
	public ProgramExecutionException(Throwable cause) {
		super(cause);
	}
}

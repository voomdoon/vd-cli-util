package de.voomdoon.util.cli;

import de.voomdoon.logging.LogManager;
import de.voomdoon.logging.Logger;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class Program {

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @param clazz
	 * @param args
	 * @since 0.1.0
	 */
	public static void run(String[] args) {
		ProgramRunUtil.run(args);
	}

	/**
	 * @since 0.1.0
	 */
	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * DOCME add JavaDoc for constructor Program
	 * 
	 * @param args
	 * @since 0.1.0
	 */
	protected Program(String[] args) {
		// TODO parse arguments
	}

	/**
	 * DOCME add JavaDoc for method runProgram
	 * 
	 * @throws Exception
	 * @since 0.1.0
	 */
	protected abstract void runProgram() throws Exception;
}

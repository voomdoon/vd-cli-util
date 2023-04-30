package de.voomdoon.util.cli;

import java.lang.reflect.Constructor;

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
	public static void run(Class<? extends Program> clazz, String[] args) {
		try {
			Constructor<? extends Program> constructor = clazz.getDeclaredConstructor(String[].class);
			constructor.setAccessible(true);
			Program program = constructor.newInstance(new Object[] { args });
			program.runProgram();
		} catch (Exception e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'run': " + e.getMessage(), e);
		}
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

	}

	/**
	 * DOCME add JavaDoc for method runProgram
	 * 
	 * @throws Exception
	 * @since 0.1.0
	 */
	protected abstract void runProgram() throws Exception;
}

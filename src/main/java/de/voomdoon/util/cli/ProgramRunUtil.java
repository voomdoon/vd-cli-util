package de.voomdoon.util.cli;

import java.lang.reflect.Constructor;

import de.voomdoon.util.cli.args.InvalidProgramArgumentsException;
import de.voomdoon.util.cli.args.InvalidProgramOptionException;
import lombok.experimental.UtilityClass;

/**
 * Utility class to run a {@link Program}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
@UtilityClass
class ProgramRunUtil {

	/**
	 * @since 0.2.0
	 */
	private static boolean testingMode = false;

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @param clazz
	 * @param args
	 * @throws InvalidProgramArgumentsException
	 * @since 0.1.0
	 */
	static void run(Class<? extends Program> clazz, String[] args) throws InvalidProgramArgumentsException {
		Program program;

		try {
			Constructor<? extends Program> constructor = clazz.getDeclaredConstructor();
			program = constructor.newInstance();
		} catch (Exception e) {
			// TODO implement error handling
			throw new RuntimeException("Failed to instantiate " + clazz.getName() + ": " + e.getMessage(), e);
		}

		try {
			program.init(args);
		} catch (InvalidProgramOptionException e) {
			throw e;
		}

		try {
			program.runProgram();
		} catch (InvalidProgramArgumentsException e) {
			throw e;
		} catch (Exception e) {
			throw new ProgramRunException(e);
		}
	}

	/**
	 * Intended to be called by {@link Program#run(String[])} only.
	 * 
	 * @param args
	 * @since 0.1.0
	 */
	static void run(String[] args) {
		runInternal(args);
	}

	/**
	 * This method is used via reflection by {@code ProgramTestingUtil}.
	 * 
	 * @since 0.2.0
	 */
	@SuppressWarnings("unused")
	private static void enableTestingMode() {
		testingMode = true;
	}

	/**
	 * DOCME add JavaDoc for method handleError
	 * 
	 * @param exception
	 * @since 0.1.0
	 */
	private static void handleError(Exception exception) {
		if (exception instanceof InvalidProgramArgumentsException) {
			System.err.println(exception.getMessage());
		} else {
			// TODO implement handleError
			throw new UnsupportedOperationException("Method 'handleError' not implemented yet", exception);
		}

		if (!testingMode) {
			System.exit(-1);
		} else {
			throw new ProgramRunException(exception);
		}
	}

	/**
	 * DOCME add JavaDoc for method runInternal
	 * 
	 * @param args
	 * @since 0.1.0
	 */
	private static void runInternal(String[] args) {
		String className = Thread.currentThread().getStackTrace()[4].getClassName();

		Class<?> clazz;

		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to find Class '" + className + "': " + e.getMessage(), e);
		}

		@SuppressWarnings("unchecked")
		Class<? extends Program> programmClass = (Class<? extends Program>) clazz;

		try {
			run(programmClass, args);
		} catch (Exception e) {
			handleError(e);
		}
	}
}

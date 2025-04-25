package de.voomdoon.util.cli;

import java.lang.reflect.Constructor;

import de.voomdoon.util.cli.args.exception.CliInputException;
import lombok.experimental.UtilityClass;

/**
 * Utility class to run a {@link Program}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
@UtilityClass
class ProgramRunner {

	/**
	 * @since 0.2.0
	 */
	private static boolean testingMode = false;

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @param clazz
	 * @param args
	 * @throws ProgramExecutionException
	 * @since 0.1.0
	 */
	static void run(Class<? extends Program> clazz, String[] args) {
		try {
			runInternal(clazz, args);
		} catch (Exception e) {
			handleError(e, clazz);// TESTME
		}
	}

	/**
	 * Intended to be called by {@link Program#run(String[])} only.
	 * 
	 * @param args
	 * @throws ProgramExecutionException
	 * @since 0.1.0
	 */
	static void run(String[] args) {
		Class<? extends Program> programClass = getProgramClass();

		run(programClass, args);
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
	 * DOCME add JavaDoc for method getProgramClass
	 * 
	 * @return
	 * @since 0.2.0
	 */
	private static Class<? extends Program> getProgramClass() {
		String className = Thread.currentThread().getStackTrace()[4].getClassName();

		Class<?> clazz;

		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to find Class '" + className + "': " + e.getMessage(), e);
		}

		@SuppressWarnings("unchecked")
		Class<? extends Program> programmClass = (Class<? extends Program>) clazz;
		return programmClass;
	}

	/**
	 * DOCME add JavaDoc for method handleError
	 * 
	 * @param exception
	 * @param programmClass
	 * @since 0.1.0
	 */
	private static void handleError(Exception exception, Class<? extends Program> programmClass) {
		if (!testingMode) {
			// TESTME
			System.err.println(exception.getMessage());
			// FEATURE #56: use different exit codes for different errors
			System.exit(-1);
		} else {
			throw new ProgramExecutionException(exception);
		}
	}

	/**
	 * DOCME add JavaDoc for method runInternal
	 * 
	 * @param clazz
	 * @param args
	 * @throws CliInputException
	 * @since 0.1.0
	 */
	private static void runInternal(Class<? extends Program> clazz, String[] args) throws CliInputException {
		Program program;

		try {
			Constructor<? extends Program> constructor = clazz.getDeclaredConstructor();
			program = constructor.newInstance();
		} catch (Exception e) {
			// TODO implement error handling
			throw new RuntimeException("Failed to instantiate " + clazz.getName() + ": " + e.getMessage(), e);
		}

		program.init(args);

		try {
			program.runProgram();
		} catch (CliInputException e) {
			throw e;
		} catch (ProgramRunException e) {
			throw e;// TESTME
		} catch (Exception e) {
			throw new ProgramRunException(e);// TESTME
		}
	}
}

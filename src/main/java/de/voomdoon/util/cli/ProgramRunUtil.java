package de.voomdoon.util.cli;

import java.lang.reflect.Constructor;

import de.voomdoon.util.cli.args.InvalidProgramArgumentsException;
import lombok.experimental.UtilityClass;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
@UtilityClass
class ProgramRunUtil {

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @param clazz
	 * @param args
	 * @since 0.1.0
	 */
	static void run(Class<? extends Program> clazz, String[] args) {
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
		} catch (InvalidProgramArgumentsException e) {
			throw new ProgramRunException("Failed to initialize Program " + clazz.getName() + ": " + e.getMessage(), e);
		}

		try {
			program.runProgram();
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
		String className = Thread.currentThread().getStackTrace()[3].getClassName();

		Class<?> clazz;

		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to find Class '" + className + "': " + e.getMessage(), e);
		}

		@SuppressWarnings("unchecked")
		Class<? extends Program> programmClass = (Class<? extends Program>) clazz;

		run(programmClass, args);
	}
}

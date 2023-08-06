package de.voomdoon.util.cli;

import java.lang.reflect.Constructor;

import lombok.experimental.UtilityClass;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
@UtilityClass
public class ProgramRunUtil {

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
	 * DOCME add JavaDoc for method run
	 * 
	 * @param args
	 * @since 0.1.0
	 */
	public static void run(String[] args) {
		String className = Thread.currentThread().getStackTrace()[3].getClassName();

		Class<?> clazz;

		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'run': " + e.getMessage(), e);
		}

		@SuppressWarnings("unchecked")
		Class<? extends Program> programmClass = (Class<? extends Program>) clazz;

		try {
			run(programmClass, args);
		} catch (Exception e) {
			throw new ProgramRunException(e);
		}
	}
}
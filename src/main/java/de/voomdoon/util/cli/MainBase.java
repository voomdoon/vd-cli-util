package de.voomdoon.util.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class MainBase {

	/**
	 * @since 0.1.0
	 */
	private String[] args;

	/**
	 * @since 0.1.0
	 */
	private Map<String, Class<?>> subMains;

	/**
	 * DOCME add JavaDoc for constructor MainBase
	 * 
	 * @param args
	 * @param subMains
	 * @since 0.1.0
	 */
	protected MainBase(String[] args, Map<String, Class<?>> subMains) {
		this.args = args;
		this.subMains = subMains;
	}

	/**
	 * DOCME add JavaDoc for method getName
	 * 
	 * @return
	 * @since DOCME add inception version number
	 */
	protected abstract String getName();

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @since 0.1.0
	 */
	protected void run() {
		if (args.length > 0 && args[0].equals("--help")) {
			printHelp();
			return;
		}

		Class<?> clazz = subMains.get(args[0]);

		// FEATURE error if no match

		Method method;

		try {
			method = clazz.getMethod("main", String[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO implement error handling
			throw new IllegalStateException("Error at 'startProgram': " + e.getMessage(), e);
		}

		try {
			method.invoke(null, (Object) ArrayUtils.remove(args, 0));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO implement error handling
			throw new IllegalStateException("Error at 'startProgram': " + e.getMessage(), e);
		}
	}

	/**
	 * DOCME add JavaDoc for method printHelp
	 * 
	 * @since DOCME add inception version number
	 */
	private void printHelp() {
		println(getName());
		// FEATURE print sub-mains with key
	}

	/**
	 * DOCME add JavaDoc for method println
	 * 
	 * @param message
	 * @since DOCME add inception version number
	 */
	private void println(String message) {
		System.out.println(message);
	}
}

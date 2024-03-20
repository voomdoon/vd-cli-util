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
	 * @since 0.1.0
	 */
	protected abstract String getName();

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @throws InvalidSubProgramException
	 * 
	 * @since 0.1.0
	 */
	protected void run() throws InvalidSubProgramException {
		if (args.length == 0 || args[0].equals("--help")) {
			printHelp();
			return;
		}

		Class<?> clazz = subMains.get(args[0]);

		if (clazz == null) {
			throw new ProgramRunException("Failed to find sub-main for '" + args[0] + "!", getHelpString());
		}

		Method method = getMainMethod(clazz);

		Object subArgs = ArrayUtils.remove(args, 0);

		executeMainMethod(method, subArgs);
	}

	/**
	 * DOCME add JavaDoc for method executeMainMethod
	 * 
	 * @param method
	 * @param subArgs
	 * @since 0.1.0
	 */
	private void executeMainMethod(Method method, Object subArgs) {
		try {
			method.invoke(null, subArgs);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Error at 'invoke': " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException("Error at 'invoke': " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			// TODO implement error handling ProgramRunException
			throw new IllegalStateException("Error at 'invoke': " + e.getMessage(), e);
		}
	}

	/**
	 * DOCME add JavaDoc for method getHelpString
	 * 
	 * @return
	 * @since 0.1.0
	 */
	private String getHelpString() {
		StringBuilder sb = new StringBuilder();
		sb.append("sub-mains:");
		subMains.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareToIgnoreCase(e2.getKey()))
				.forEach(e -> sb.append("\n• ").append(e.getKey()));
		// TODO add description of sub-main

		return sb.toString();
	}

	/**
	 * DOCME add JavaDoc for method getMainMethod
	 * 
	 * @param clazz
	 * @return
	 * @throws InvalidSubProgramException
	 * @since 0.1.0
	 */
	private Method getMainMethod(Class<?> clazz) throws InvalidSubProgramException {
		Method method;

		try {
			method = clazz.getMethod("main", String[].class);
		} catch (NoSuchMethodException e) {
			throw new InvalidSubProgramException(e);
		} catch (SecurityException e) {
			throw new IllegalStateException("Error at 'getMethod': " + e.getMessage(), e);
		}

		boolean isStatic = (method.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0;

		if (!isStatic) {
			throw new InvalidSubProgramException("Method 'main' must be static!");
		}

		return method;
	}

	/**
	 * DOCME add JavaDoc for method printHelp
	 * 
	 * @since 0.1.0
	 */
	private void printHelp() {
		println(getName());
		println(getHelpString());
	}

	/**
	 * DOCME add JavaDoc for method println
	 * 
	 * @param message
	 * @since 0.1.0
	 */
	private void println(String message) {
		System.out.println(message);
	}
}

package de.voomdoon.util.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class MainBase extends Program {

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
	protected MainBase(Map<String, Class<?>> subMains) {
		this.subMains = subMains;
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	protected void initOptions() {
		// nothing to do
	}

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @throws InvalidSubProgramException
	 * 
	 * @since 0.1.0
	 */
	@Override
	protected void runProgram() throws InvalidSubProgramException {
		String subMain;

		try {
			subMain = pollArg("sub-main");
		} catch (NoSuchElementException e) {
			System.err.println("Missing argument 'sub-main'!");
			printHelp();
			return;
		}

		if ("--help".equals(subMain)) {
			printHelp();
			return;
		}

		Class<?> clazz = subMains.get(subMain);

		if (clazz == null) {
			throw new ProgramRunException("Failed to find sub-main for '" + subMain + "!", getHelpString());
		}

		Method method = getMainMethod(clazz);

		executeMainMethod(method, getArguments().pollAllArgs().toArray(new String[0]));
	}

	/**
	 * DOCME add JavaDoc for method executeMainMethod
	 * 
	 * @param method
	 * @param subArgs
	 * @since 0.1.0
	 */
	private void executeMainMethod(Method method, String[] subArgs) {
		try {
			method.invoke(null, (Object) subArgs);
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

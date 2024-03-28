package de.voomdoon.util.cli;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

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
	 * @since 0.1.0
	 */
	private Queue<String> args;

	/**
	 * DOCME add JavaDoc for constructor Program
	 * 
	 * @param args
	 * @since 0.1.0
	 */
	protected Program() {
		// nothing to do yet
	}

	/**
	 * DOCME add JavaDoc for method init
	 * 
	 * @since DOCME add inception version number
	 */
	protected void init(String[] args) {
		this.args = new LinkedList<>(Arrays.asList(args));
	}

	/**
	 * DOCME add JavaDoc for method pollArg
	 * 
	 * @param name
	 * @return
	 * @since DOCME add inception version number
	 */
	protected String pollArg(String name) {
		if (args.isEmpty()) {
			throw new NoSuchElementException(name);
		}

		return args.poll();
	}

	/**
	 * DOCME add JavaDoc for method runProgram
	 * 
	 * @throws Exception
	 * @since 0.1.0
	 */
	protected abstract void runProgram() throws Exception;
}

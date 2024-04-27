package de.voomdoon.util.cli.test;

import de.voomdoon.util.cli.Program;

/**
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class TestProgramWithMandatoryArgument extends Program {

	/**
	 * @since 0.1.0
	 */
	public static final String ARGUMENT_NAME = "manatory-arg";

	/**
	 * @param args
	 * @since 0.1.0
	 */
	public static void run(String[] args) {
		Program.runWithoutExit(args);
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	public void run() throws Exception {
		pollArg(ARGUMENT_NAME);
	}
}
package de.voomdoon.util.cli.test;

import de.voomdoon.util.cli.Program;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
public class ErrorTestProgram extends Program {

	/**
	 * @since 0.2.0
	 */
	@Override
	protected void run() throws Exception {
		throw new RuntimeException("test-error");
	}
}

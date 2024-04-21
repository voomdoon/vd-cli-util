package de.voomdoon.util.cli.test;

import de.voomdoon.util.cli.args.Option;

/**
 * DOCME add JavaDoc for ProgramTest.RunTest
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class TestProgramWithOptionWithLongNameAndValue extends NoOpTestProgram {

	/**
	 * @since 0.1.0
	 */
	public static final String TEST_OPTION = "test-option";

	/**
	 * @since 0.1.0
	 */
	public static final String TEST_OPTION_VALUE_NAME = "test-value-name";

	/**
	 * @since 0.1.0
	 */
	private Option option;

	/**
	 * @return option
	 * @since 0.1.0
	 */
	public Option getOption() {
		return option;
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	protected void initOptions() {
		option = addOption().longName(TEST_OPTION).hasValue(TEST_OPTION_VALUE_NAME).build();
	}
}

package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.MissingMandatoryArgumentException;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ProgramRunUtilTest {

	/**
	 * DOCME add JavaDoc for ProgramRunUtilTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Run_Class_StringArray_Test extends TestBase {

		/**
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class TestProgramWithMandatoryArgument extends Program {

			/**
			 * @since 0.1.0
			 */
			private static final String ARGUMENT_NAME = "manatory-arg";

			/**
			 * @since 0.1.0
			 */
			@Override
			public void run() throws Exception {
				pollArg(ARGUMENT_NAME);
			}
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_ProgramRunException() throws Exception {
			logTestStart();

			String[] args = new String[0];

			assertThatThrownBy(() -> ProgramRunUtil.run(TestProgramWithMandatoryArgument.class, args))
					.isInstanceOf(ProgramRunException.class).hasCauseInstanceOf(MissingMandatoryArgumentException.class)
					.hasMessageContaining(TestProgramWithMandatoryArgument.ARGUMENT_NAME);
		}
	}
}

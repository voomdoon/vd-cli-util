package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.MissingMandatoryArgumentException;
import de.voomdoon.util.cli.test.TestProgramWithMandatoryArgument;

/**
 * Test class for {@link ProgramRunUtil}
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ProgramRunUtilTest {

	/**
	 * Test class for {@link ProgramRunUtil#run(Class, String[])}
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Run_Class_StringArray_Test extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_ProgramRunException() throws Exception {
			logTestStart();

			String[] args = new String[0];

			assertThatThrownBy(() -> ProgramRunUtil.run(TestProgramWithMandatoryArgument.class, args))
					.isInstanceOf(MissingMandatoryArgumentException.class)
					.hasMessageContaining(TestProgramWithMandatoryArgument.ARGUMENT_NAME);
		}
	}
}

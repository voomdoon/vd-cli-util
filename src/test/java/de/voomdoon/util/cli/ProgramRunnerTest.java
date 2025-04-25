package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogEvent;
import de.voomdoon.logging.LogEventHandler;
import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.exception.argument.MissingCliArgumentException;
import de.voomdoon.util.cli.test.TestProgramWithMandatoryArgument;
import de.voomdoon.util.cli.testing.ProgramTestingUtil;

/**
 * Tests for {@link ProgramRunner}
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ProgramRunnerTest {

	/**
	 * Tests for {@link ProgramRunner#run(Class, String[])}
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Run_Class_StringArray_Test extends TestBase implements LogEventHandler {

		/**
		 * @since 0.2.0
		 */
		private List<LogEvent> logEvents = new ArrayList<>();

		/**
		 * @since 0.2.0
		 */
		@Override
		public void handleLogEvent(LogEvent logEvent) {
			this.logEvents.add(logEvent);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_MissingMandatoryArgumentException() throws Exception {
			logTestStart();

			ProgramTestingUtil.enableTestingMode();

			String[] args = new String[0];

			assertThatThrownBy(() -> ProgramRunner.run(TestProgramWithMandatoryArgument.class, args))
					.isInstanceOf(ProgramExecutionException.class).hasCauseInstanceOf(MissingCliArgumentException.class)//
					.cause().hasMessageContaining(TestProgramWithMandatoryArgument.ARGUMENT_NAME);
		}
	}
}

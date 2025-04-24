package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogEvent;
import de.voomdoon.logging.LogEventHandler;
import de.voomdoon.logging.LogLevel;
import de.voomdoon.logging.LogManager;
import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.MissingMandatoryArgumentException;
import de.voomdoon.util.cli.test.ErrorTestProgram;
import de.voomdoon.util.cli.test.TestProgramWithMandatoryArgument;
import de.voomdoon.util.cli.testing.ProgramTestingUtil;

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
		void test_error_isLogged() throws Exception {
			logTestStart();

			ProgramTestingUtil.enableTestingMode();

			LogManager.addLogEventHandler(this);

			try {
				ProgramRunUtil.run(ErrorTestProgram.class, new String[0]);
			} catch (Exception e) {
				logger.debug("ignored exception: " + e.getMessage(), e);
			}

			assertThat(logEvents.stream().filter(e -> e.getLevel() == LogLevel.FATAL).toList()).hasSize(1);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_MissingMandatoryArgumentException() throws Exception {
			logTestStart();

			ProgramTestingUtil.enableTestingMode();

			String[] args = new String[0];

			assertThatThrownBy(() -> ProgramRunUtil.run(TestProgramWithMandatoryArgument.class, args))
					.isInstanceOf(ProgramExecutionException.class)
					.hasCauseInstanceOf(MissingMandatoryArgumentException.class)//
					.cause().hasMessageContaining(TestProgramWithMandatoryArgument.ARGUMENT_NAME);
		}
	}
}

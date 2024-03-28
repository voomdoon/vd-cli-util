package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogEvent;
import de.voomdoon.logging.LogLevel;
import de.voomdoon.testing.logging.tests.LoggingCheckingTestBase;
import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ProgramTest extends LoggingCheckingTestBase {

	/**
	 * DOCME add JavaDoc for ProgramTest.RunTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class TestProgram extends Program {

		/**
		 * DOCME add JavaDoc for constructor TestProgram
		 * 
		 * @since 0.1.0
		 */
		protected TestProgram() {
			super();
		}

		/**
		 * @since DOCME add inception version number
		 */
		@Override
		public String pollArg(String name) {
			return super.pollArg(name);
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		protected void runProgram() throws Exception {
			// nothing to do
		}
	}

	/**
	 * DOCME add JavaDoc for ProgramTest
	 *
	 * @author André Schulz
	 *
	 * @since DOCME add inception version number
	 */
	@Nested
	class PollArgTest extends TestBase {

		/**
		 * @since DOCME add inception version number
		 */
		@Test
		void test_empty() throws Exception {
			logTestStart();

			TestProgram program = new TestProgram();
			program.init(new String[0]);

			assertThatThrownBy(() -> program.pollArg("test-name")).isInstanceOf(NoSuchElementException.class)
					.hasMessageContaining("test-name");
		}

		/**
		 * @since DOCME add inception version number
		 */
		@Test
		void test_one() throws Exception {
			logTestStart();

			TestProgram program = new TestProgram();
			program.init(new String[] { "arg0" });

			assertThat(program.pollArg("name0")).isEqualTo("arg0");
		}
	}

	/**
	 * DOCME add JavaDoc for ProgramTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class RunTest extends TestBase {

		/**
		 * DOCME add JavaDoc for ProgramTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class TestProgram extends Program {

			/**
			 * @since 0.1.0
			 */
			private static Optional<TestProgram> INSTANCE = Optional.empty();

			/**
			 * DOCME add JavaDoc for method run
			 * 
			 * @param args
			 * @since 0.1.0
			 */
			public static void run(String[] args) {
				Program.run(args);
			}

			/**
			 * @since 0.1.0
			 */
			private final AtomicInteger runProgramCallCount = new AtomicInteger();

			/**
			 * DOCME add JavaDoc for constructor TestProgram
			 * 
			 * @param args
			 * @since 0.1.0
			 */
			public TestProgram() {
				super();

				INSTANCE = Optional.of(this);
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void runProgram() throws Exception {
				runProgramCallCount.incrementAndGet();
			}
		}

		/**
		 * DOCME add JavaDoc for method test_runProgram_called
		 * 
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test_runProgram_called() throws Exception {
			logTestStart();

			TestProgram.run(new String[0]);

			assertThat(TestProgram.INSTANCE).isPresent();
			assertThat(TestProgram.INSTANCE.get().runProgramCallCount).hasValue(1);
		}
	}

	/**
	 * DOCME add JavaDoc for method test_logger
	 * 
	 * @since 0.1.0
	 */
	@Test
	void testName() throws Exception {
		logTestStart();

		TestProgram program = new TestProgram();
		program.init(new String[0]);
		program.logger.debug("test");

		assertThat(getLogCache().getLogEvents(LogLevel.DEBUG)).hasSize(1);
		assertThat(getLogCache().getLogEvents(LogLevel.DEBUG).get(0)).extracting(LogEvent::getMessage)
				.isEqualTo("test");
	}
}

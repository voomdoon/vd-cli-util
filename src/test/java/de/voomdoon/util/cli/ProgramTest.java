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
import de.voomdoon.util.cli.args.Arguments;
import de.voomdoon.util.cli.args.Option;
import de.voomdoon.util.cli.test.NoOpTestProgram;

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
	public static class TestProgramWithOption extends NoOpTestProgram {

		/**
		 * @since 0.1.0
		 */
		private Option option;

		/**
		 * @since 0.1.0
		 */
		@Override
		protected void initOptions() {
			option = addOption().longName("test-option").hasValue().build();
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class GetOptionValueTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_absent() {
			logTestStart();

			NoOpTestProgram program = new TestProgramWithOption();
			program.init(new String[0]);

			Optional<String> actual = program.getOptionValue(null);

			assertThat(actual).isEmpty();
		}

		/**
		 * DOCME add JavaDoc for method test_present
		 * 
		 * @since 0.1.0
		 */
		@Test
		void test_present() throws Exception {
			logTestStart();

			TestProgramWithOption program = new TestProgramWithOption();
			program.init(new String[] { "--test-option", "test-value" });

			Optional<String> actual = program.getOptionValue(program.option);

			assertThat(actual).hasValue("test-value");
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
	class PollArgTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty() throws Exception {
			logTestStart();

			NoOpTestProgram program = new NoOpTestProgram();
			program.init(new String[0]);

			assertThatThrownBy(() -> program.pollArg("test-name")).isInstanceOf(NoSuchElementException.class)
					.hasMessageContaining("test-name");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_one() throws Exception {
			logTestStart();

			NoOpTestProgram program = new NoOpTestProgram();
			program.init(new String[] { "arg0" });

			assertThat(program.pollArg("name0")).isEqualTo("arg0");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_withOptions() throws Exception {
			logTestStart();

			Program program = new TestProgramWithOption();
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
	 * @since 0.1.0
	 */
	@Test
	void testGetArguments() throws Exception {
		logTestStart();

		NoOpTestProgram program = new NoOpTestProgram();
		program.init(new String[0]);

		Arguments actual = program.getArguments();

		assertThat(actual).isNotNull();
	}

	/**
	 * DOCME add JavaDoc for method test_logger
	 * 
	 * @since 0.1.0
	 */
	@Test
	void testName() throws Exception {
		logTestStart();

		NoOpTestProgram program = new NoOpTestProgram();
		program.init(new String[0]);
		program.logger.debug("test");

		assertThat(getLogCache().getLogEvents(LogLevel.DEBUG)).hasSize(1);
		assertThat(getLogCache().getLogEvents(LogLevel.DEBUG).get(0)).extracting(LogEvent::getMessage)
				.isEqualTo("test");
	}
}

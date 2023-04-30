package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;

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
		 * @param args
		 * @since 0.1.0
		 */
		protected TestProgram(String[] args) {
			super(args);
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
			 * @since 0.1.0
			 */
			private Optional<String[]> constructorArgs = Optional.empty();

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
			public TestProgram(String[] args) {
				super(args);

				constructorArgs = Optional.of(args);

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
		 * DOCME add JavaDoc for method test
		 * 
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test_constructor_args() throws Exception {
			logTestStart();

			Program.run(TestProgram.class, new String[] { "test" });

			assertThat(TestProgram.INSTANCE).isPresent();
			assertThat(TestProgram.INSTANCE.get().constructorArgs.get()).contains("test");
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

			Program.run(TestProgram.class, new String[0]);

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

		TestProgram program = new TestProgram(new String[0]);
		program.logger.debug("test");

		assertThat(getLogCache().getLogEvents(LogLevel.DEBUG)).hasSize(1);
		assertThat(getLogCache().getLogEvents(LogLevel.DEBUG).get(0)).extracting(LogEvent::getMessage)
				.isEqualTo("test");
	}

	/**
	 * DOCME add JavaDoc for method getLogEvent
	 * 
	 * @param timestamp
	 * @param sourceClass
	 * @param level
	 * @param message
	 * @return
	 * @since 0.1.0
	 */
	private LogEvent getLogEvent(long timestamp, Class<?> sourceClass, LogLevel level, Object message) {
		return new LogEvent() {

			@Override
			public Throwable getError() {
				// TODO implement getError
				throw new UnsupportedOperationException("'getError' not implemented at 'Type1682868671772'!");
			}

			@Override
			public LogLevel getLevel() {
				return level;
			}

			@Override
			public Object getMessage() {
				return message;
			}

			@Override
			public Class<?> getSourceClass() {
				return sourceClass;
			}

			@Override
			public long getTimestamp() {
				return timestamp;
			}
		};
	}
}

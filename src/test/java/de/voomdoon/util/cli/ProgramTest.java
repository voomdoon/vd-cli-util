package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.logging.tests.LoggingCheckingTestBase;
import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.Arguments;
import de.voomdoon.util.cli.args.InvalidProgramOptionException;
import de.voomdoon.util.cli.args.MissingMandatoryArgumentException;
import de.voomdoon.util.cli.test.NoOpTestProgram;
import de.voomdoon.util.cli.test.TestProgramWithMandatoryArgument;
import de.voomdoon.util.cli.test.TestProgramWithOptionWithLongNameAndValue;
import de.voomdoon.util.cli.testing.ProgramTestingUtil;
import de.voomdoon.util.commons.SystemOutput;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ProgramTest extends LoggingCheckingTestBase {

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class GetOptionValueTest extends TestBase {

		/**
		 * @throws InvalidProgramOptionException
		 * @since 0.1.0
		 */
		@Test
		void test_absent() throws InvalidProgramOptionException {
			logTestStart();

			NoOpTestProgram program = new TestProgramWithOptionWithLongNameAndValue();
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

			TestProgramWithOptionWithLongNameAndValue program = new TestProgramWithOptionWithLongNameAndValue();
			program.init(new String[] { "--test-option", "test-value" });

			Optional<String> actual = program.getOptionValue(program.getOption());

			assertThat(actual).hasValue("test-value");
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class HelpTest extends HelpTestBase {

		/**
		 * DOCME add JavaDoc for ProgramTest.HelpTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class ProgramWithName extends NoOpTestProgram {

			/**
			 * @since 0.1.0
			 */
			@Override
			protected String getName() {
				return "test-program";
			}
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_name_default() throws Exception {
			logTestStart();

			initRunAndAssert(NoOpTestProgram.class).contains("NoOpTestProgram");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_name_specific() throws Exception {
			logTestStart();

			initRunAndAssert(ProgramWithName.class).contains("test-program");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_option_longName() throws Exception {
			logTestStart();

			initRunAndAssert(TestProgramWithOptionWithLongNameAndValue.class)
					.contains("--" + TestProgramWithOptionWithLongNameAndValue.TEST_OPTION);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_option_valueName() throws Exception {
			logTestStart();

			initRunAndAssert(TestProgramWithOptionWithLongNameAndValue.class)
					.contains(" <" + TestProgramWithOptionWithLongNameAndValue.TEST_OPTION_VALUE_NAME + ">");
		}
	}

	/**
	 * 
	 * DOCME add JavaDoc for ProgramTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	abstract static class HelpTestBase extends TestBase {

		/**
		 * @since 0.1.0
		 */
		protected static final String HELP = "--help";

		/**
		 * @since 0.1.0
		 */
		AbstractStringAssert<?> initRunAndAssert(Class<? extends Program> clazz) throws InvocationTargetException {
			Program program;

			try {
				program = clazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO implement error handling
				throw new RuntimeException("Error at 'initRunAndAssert': " + e.getMessage(), e);
			}

			try {
				program.init(new String[] { HELP });
			} catch (InvalidProgramOptionException e) {
				throw new RuntimeException("Error at 'initRunAndAssert': " + e.getMessage(), e);
			}

			SystemOutput output = SystemOutput.run(() -> program.runProgram());

			output.log(logger);

			return assertThat(output).extracting(SystemOutput::getOut).asString();
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

			assertThatThrownBy(() -> program.pollArg("test-name")).isInstanceOf(MissingMandatoryArgumentException.class)
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

			Program program = new TestProgramWithOptionWithLongNameAndValue();
			program.init(new String[] { "arg0" });

			assertThat(program.pollArg("name0")).isEqualTo("arg0");
		}
	}

	/**
	 * Test class for {@link Program#run(String[])}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Run_StringArray_Test extends TestBase {

		/**
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
			 * @param args
			 * @since 0.1.0
			 */
			public TestProgram() {
				INSTANCE = Optional.of(this);
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void run() throws Exception {
				runProgramCallCount.incrementAndGet();
			}
		}

		/**
		 * DOCME add JavaDoc for method test_error_missingMandatoryArgument_nameIsPrinted
		 * 
		 * @since 0.1.0
		 */
		@Test
		void test_error_missingMandatoryArgument_nameIsPrinted() throws Exception {
			logTestStart();

			ProgramTestingUtil.enableTestingMode();

			SystemOutput output = SystemOutput.run(() -> {
				try {
					TestProgramWithMandatoryArgument.run(new String[0]);
				} catch (ProgramRunException e) {
					// ignore
				}
			});

			output.log(logger);

			assertThat(output.getErr()).contains(TestProgramWithMandatoryArgument.ARGUMENT_NAME)
					.doesNotContain("Exception");
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
}

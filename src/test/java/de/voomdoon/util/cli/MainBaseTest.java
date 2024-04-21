package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.MainBaseTest.TestMainBases.TestMain;
import de.voomdoon.util.cli.MainBaseTest.TestMainBases.TestMainWithTwoSubPrograms;
import de.voomdoon.util.cli.MainBaseTest.TestPrograms.InvalidProgramWithoutMainMethod;
import de.voomdoon.util.cli.MainBaseTest.TestPrograms.ValidProgram;
import de.voomdoon.util.cli.ProgramTest.HelpTestBase;
import de.voomdoon.util.commons.SystemOutput;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class MainBaseTest {

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class TestMainBases {

		/**
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class InvalidMainWithSubProgramWithoutMainMethod extends MainBase {

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void registerSubMains() {
				registerSubMain(SUB_KEY, InvalidProgramWithoutMainMethod.class);
			}
		}

		/**
		 * DOCME add JavaDoc for MainBaseTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class TestMain extends MainBase {

			/**
			 * @since 0.1.0
			 */
			private static final String NAME = "My-Main";

			/**
			 * @since 0.1.0
			 */
			@Override
			protected String getName() {
				return NAME;
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void registerSubMains() {
				registerSubMain(SUB_KEY, ValidProgram.class);
			}
		}

		/**
		 * DOCME add JavaDoc for MainBaseTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class TestMainWithTwoSubPrograms extends MainBase {

			/**
			 * @since 0.1.0
			 */
			@Override
			protected String getName() {
				return "My-Main";
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void registerSubMains() {
				registerSubMain("test-sub-a", ValidProgram.class);
				registerSubMain("test-sub-b", ValidProgram.class);// TODO other
			}
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class TestPrograms {

		/**
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class InvalidProgramWithoutMainMethod extends Program {

			// XXX to sub-programs really need main method?

			/**
			 * @param args
			 * @since 0.1.0
			 */
			protected InvalidProgramWithoutMainMethod() {
				super();
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void run() throws Exception {
				throw new UnsupportedOperationException("'runProgram' not implemented at 'Program'!");
			}
		}

		/**
		 * DOCME add JavaDoc for MainBaseTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class ValidProgram extends Program {

			/**
			 * @since 0.1.0
			 */
			private static List<String> ARGUMENTS;

			/**
			 * DOCME add JavaDoc for constructor SubMainA
			 * 
			 * @param args
			 * @since 0.1.0
			 */
			protected ValidProgram() {
				super();
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void run() throws Exception {
				ARGUMENTS = getArguments().pollAllArgs();
			}
		}
	}

	/**
	 * DOCME add JavaDoc for MainBaseTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class HelpTest extends HelpTestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_help_containsName() throws Exception {
			logTestStart();

			initRunAndAssert(TestMain.class).contains(TestMain.NAME);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_help_containsSubMainKeys() throws Exception {
			logTestStart();

			initRunAndAssert(TestMain.class).contains(SUB_KEY);
		}
	}

	/**
	 * Test class for {@link MainBase#run()}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class RunTest extends TestBase {

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			MainBase main = new TestMain();
			main.init(new String[] { SUB_KEY, "test-arg" });
			main.run();

			assertThat(ValidProgram.ARGUMENTS).isEqualTo(List.of("test-arg"));
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_noArgs_printsHelp() throws Exception {
			logTestStart();

			MainBase main = new TestMain();
			main.init(new String[0]);

			SystemOutput output = SystemOutput.run(() -> main.runProgram());

			assertThat(output).extracting(SystemOutput::getOut).asString().contains("My-Main");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_ProgramRunException_subMainNotMatching_exceptionHelpMessageContainsValidSubMains() throws Exception {
			logTestStart();

			MainBase main = new TestMainWithTwoSubPrograms();
			main.init(new String[] { "something" });

			ProgramRunException actual = assertThrows(ProgramRunException.class, () -> main.runProgram());

			assertThat(actual).extracting(ProgramRunException::getHelpString).asString().contains("sub-a", "sub-b");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_ProgramRunException_subMainNotMatching_exceptionMessageContainsInvalidArgument() throws Exception {
			logTestStart();

			MainBase main = new TestMain();
			main.init(new String[] { "something" });

			ProgramRunException actual = assertThrows(ProgramRunException.class, () -> main.runProgram());

			assertThat(actual).extracting(Exception::getMessage).asString().contains("something");
		}
	}

	/**
	 * @since 0.1.0
	 */
	private static final String SUB_KEY = "test-sub";
}

package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.MainBaseTest.TestMains.InvalidMainWithNonStaticMainMethod;
import de.voomdoon.util.cli.MainBaseTest.TestMains.InvalidMainWithoutMainMethod;
import de.voomdoon.util.cli.MainBaseTest.TestMains.TestMain;
import de.voomdoon.util.cli.MainBaseTest.TestPrograms.InvalidProgramWithoutMainMethod;
import de.voomdoon.util.cli.MainBaseTest.TestPrograms.ValidProgram;
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
	public static class TestMains {

		/**
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class InvalidMainWithNonStaticMainMethod extends MainBase {

			/**
			 * @param args
			 * @param subMains
			 * @since 0.1.0
			 */
			protected InvalidMainWithNonStaticMainMethod(String[] args, Map<String, Class<?>> subMains) {
				super(args, subMains);
			}

			/**
			 * @param args
			 * @since 0.1.0
			 */
			public void main(String[] args) {
				// nothing to do
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected String getName() {
				// TODO implement getName
				throw new UnsupportedOperationException("'getName' not implemented at 'MainBase'!");
			}
		}

		/**
		 * DOCME add JavaDoc for MainBaseTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		public static class InvalidMainWithoutMainMethod extends MainBase {

			/**
			 * @param args
			 * @param subMains
			 * @since 0.1.0
			 */
			protected InvalidMainWithoutMainMethod(String[] args, Map<String, Class<?>> subMains) {
				super(args, subMains);
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected String getName() {
				// TODO implement getName
				throw new UnsupportedOperationException("'getName' not implemented at 'MainBase'!");
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
			 * DOCME add JavaDoc for constructor MyMain
			 * 
			 * @param args
			 * @param subMains
			 * @since 0.1.0
			 */
			TestMain(String[] args, Map<String, Class<?>> subMains) {
				super(args, subMains);
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected String getName() {
				return "My-Main";
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

			/**
			 * @param args
			 * @since 0.1.0
			 */
			protected InvalidProgramWithoutMainMethod(String[] args) {
				super(args);
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void runProgram() throws Exception {
				// TODO implement runProgram
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
			private static String[] ARGS;

			/**
			 * DOCME add JavaDoc for method main
			 * 
			 * @param args
			 * @since 0.1.0
			 */
			@SuppressWarnings("unused")
			public static void main(String[] args) {
				ARGS = args;
			}

			/**
			 * DOCME add JavaDoc for constructor SubMainA
			 * 
			 * @param args
			 * @since 0.1.0
			 */
			protected ValidProgram(String[] args) {
				super(args);
				// TODO Auto-generated constructor stub
			}

			/**
			 * @since 0.1.0
			 */
			@Override
			protected void runProgram() throws Exception {
				// TODO implement runProgram
				throw new UnsupportedOperationException("'runProgram' not implemented at 'Program'!");
			}
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

			new TestMain(new String[] { "a", "test" }, Map.of("a", ValidProgram.class)).run();

			assertThat(ValidProgram.ARGS).isEqualTo(new String[] { "test" });
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_help_containsName() throws Exception {
			logTestStart();

			TestMain main = new TestMain(new String[] { "--help" }, Map.of());

			SystemOutput output = SystemOutput.run(() -> run(main));

			assertThat(output).extracting(SystemOutput::getOut).asString().contains("My-Main");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_help_containsSubMainKeys() throws Exception {
			logTestStart();

			TestMain main = new TestMain(new String[] { "--help" }, Map.of("test-sub", Object.class));

			SystemOutput output = SystemOutput.run(() -> run(main));

			assertThat(output).extracting(SystemOutput::getOut).asString().contains("test-sub");
		}

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test_InvalidProgramException_mainMethodMissing() throws Exception {
			logTestStart();

			InvalidMainWithoutMainMethod main = new InvalidMainWithoutMainMethod(new String[] { "sub", "dummy-arg" },
					Map.of("sub", InvalidMainWithoutMainMethod.class));

			assertThat(assertThrows(InvalidSubProgramException.class, () -> main.run()))
					.extracting(InvalidSubProgramException::getCause).isInstanceOf(NoSuchMethodException.class);
		}

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test_InvalidProgramException_mainMethodNotStatic() throws Exception {
			logTestStart();

			InvalidMainWithNonStaticMainMethod main = new InvalidMainWithNonStaticMainMethod(
					new String[] { "sub", "dummy-arg" }, Map.of("sub", InvalidMainWithNonStaticMainMethod.class));

			assertThat(assertThrows(InvalidSubProgramException.class, () -> main.run()))
					.extracting(InvalidSubProgramException::getMessage).asString().contains("static");
		}

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test_InvalidSubProgramException_mainMethodMissing() throws Exception {
			logTestStart();

			TestMain main = new TestMain(new String[] { "a", "test" },
					Map.of("a", InvalidProgramWithoutMainMethod.class));

			assertThat(assertThrows(InvalidSubProgramException.class, () -> main.run()))
					.extracting(InvalidSubProgramException::getCause).isInstanceOf(NoSuchMethodException.class);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_noArgs_printsHelp() throws Exception {
			logTestStart();

			TestMain main = new TestMain(new String[] {}, Map.of());

			SystemOutput output = SystemOutput.run(() -> run(main));

			assertThat(output).extracting(SystemOutput::getOut).asString().contains("My-Main");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_ProgramRunException_subMainNotMatching_exceptionHelpMessageContainsValidSubMains() throws Exception {
			logTestStart();

			TestMain main = new TestMain(new String[] { "something" },
					Map.of("sub-a", Object.class, "sub-b", Object.class));

			ProgramRunException actual = assertThrows(ProgramRunException.class, () -> main.run());

			assertThat(actual).extracting(ProgramRunException::getHelpString).asString().contains("sub-a", "sub-b");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_ProgramRunException_subMainNotMatching_exceptionMessageContainsInvalidArgument() throws Exception {
			logTestStart();

			TestMain main = new TestMain(new String[] { "something" }, Map.of());

			ProgramRunException actual = assertThrows(ProgramRunException.class, () -> main.run());

			assertThat(actual).extracting(Exception::getMessage).asString().contains("something");
		}

		/**
		 * @param main
		 * @return
		 * @since 0.1.0
		 */
		private void run(TestMain main) {
			try {
				main.run();
			} catch (InvalidSubProgramException e) {
				fail(e);
			}
		}
	}
}

package de.voomdoon.util.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.commons.Output;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class MainBaseTest {

	/**
	 * Test class for {@link MainBase#run()}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	static class RunTest extends TestBase {

		/**
		 * DOCME add JavaDoc for MainBaseTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		private class MyMain extends MainBase {

			/**
			 * DOCME add JavaDoc for constructor MyMain
			 * 
			 * @param args
			 * @param subMains
			 * @since 0.1.0
			 */
			MyMain(String[] args, Map<String, Class<?>> subMains) {
				super(args, subMains);
			}

			/**
			 * @since DOCME add inception version number
			 */
			@Override
			protected String getName() {
				return "My-Main";
			}
		}

		/**
		 * DOCME add JavaDoc for MainBaseTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		private class MySubMainA {

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
		}

		/**
		 * DOCME add JavaDoc for method testName
		 * 
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			new MyMain(new String[] { "a", "test" }, Map.of("a", MySubMainA.class)).run();

			assertThat(MySubMainA.ARGS).isEqualTo(new String[] { "test" });
		}

		/**
		 * DOCME add JavaDoc for method test_help_containsName
		 * 
		 * @since DOCME add inception version number
		 */
		@Test
		void test_help_containsName() throws Exception {
			logTestStart();

			MyMain main = new MyMain(new String[] { "--help" }, Map.of());

			Output output = Output.run(() -> main.run());

			assertThat(output).extracting(Output::getOut).asString().contains("My-Main");
		}
	}
}

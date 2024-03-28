package de.voomdoon.util.cli.args;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ArgumentsTest extends TestBase implements Consumer<Option> {

	/**
	 * @since 0.1.0
	 */
	private static final String ANY_LONG_NAME = "test-option";

	/**
	 * @since 0.1.0
	 */
	private static final String TEST_VALUE = "test-value";

	/**
	 * @since 0.1.0
	 */
	@Override
	public void accept(Option option) {
		// TODO implement accept
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testGetOptionValue_longName() {
		logTestStart();

		String[] args = new String[] { "--" + ANY_LONG_NAME, TEST_VALUE };
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).hasValue().build());
		Option option = options.iterator().next();

		Arguments actual = new Arguments(args, options);

		assertThat(actual.getOptionValue(option)).hasValue(TEST_VALUE);
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHasOption_false() {
		logTestStart();

		String[] args = new String[0];
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).build());
		Option option = options.iterator().next();

		Arguments actual = new Arguments(args, options);

		assertThat(actual.hasOption(option)).isFalse();
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHasOption_longName() {
		logTestStart();

		String[] args = new String[] { "--" + ANY_LONG_NAME };
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).build());
		Option option = options.iterator().next();

		Arguments actual = new Arguments(args, options);

		assertThat(actual.hasOption(option)).isTrue();
	}
}

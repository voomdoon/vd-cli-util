package de.voomdoon.util.cli.args;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.exception.option.CliOptionException;
import de.voomdoon.util.cli.args.exception.option.MissingCliOptionException;

/**
 * Tests for {@link Arguments}.
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
	private static final String ANY_VALUE_NAME = "test-value-name";

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
	void testConstructor_error_InvalidProgramArgumentsException_missingValue() throws Exception {
		logTestStart();

		String[] args = new String[] { "--" + ANY_LONG_NAME };
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).hasValue(ANY_VALUE_NAME).build());

		assertThatThrownBy(() -> new Arguments(args, options)).isInstanceOf(CliOptionException.class)
				.hasMessageContaining(ANY_LONG_NAME).hasMessageContaining("value");
	}

	/**
	 * DOCME add JavaDoc for method testGetOptionValue_error_mandatoryOptionMissing
	 * 
	 * @since 0.1.0
	 */
	@Test
	void testGetOptionValue_error_mandatoryOptionMissing() throws Exception {
		logTestStart();

		String[] args = new String[0];
		Set<Option> options = Set
				.of(new OptionBuilder(this).longName(ANY_LONG_NAME).hasValue(ANY_VALUE_NAME).isMandatory().build());

		assertThatThrownBy(() -> new Arguments(args, options)).isInstanceOf(MissingCliOptionException.class)
				.hasMessageContaining(ANY_LONG_NAME);
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testGetOptionValue_longName() throws CliOptionException {
		logTestStart();

		String[] args = new String[] { "--" + ANY_LONG_NAME, TEST_VALUE };
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).hasValue(ANY_VALUE_NAME).build());
		Option option = options.iterator().next();

		Arguments actual = new Arguments(args, options);

		assertThat(actual.getOptionValue(option)).hasValue(TEST_VALUE);
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testGetOptionValue_withOtherArgsLookLikeOption() throws Exception {
		logTestStart();

		String arg1 = "--" + ANY_LONG_NAME;
		String[] args = new String[] { "--" + ANY_LONG_NAME, TEST_VALUE, "arg0", arg1, "arg2" };
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).hasValue(ANY_VALUE_NAME).build());
		Option option = options.iterator().next();

		Arguments actual = new Arguments(args, options);

		assertThat(actual.getOptionValue(option)).hasValue(TEST_VALUE);
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHasOption_false() throws CliOptionException {
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
	void testHasOption_longName() throws CliOptionException {
		logTestStart();

		String[] args = new String[] { "--" + ANY_LONG_NAME };
		Set<Option> options = Set.of(new OptionBuilder(this).longName(ANY_LONG_NAME).build());
		Option option = options.iterator().next();

		Arguments actual = new Arguments(args, options);

		assertThat(actual.hasOption(option)).isTrue();
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testPollAllArgs_consumesAll_callTwiceIsEmpty() throws Exception {
		logTestStart();

		String[] args = new String[] { "arg0" };
		Arguments arguments = new Arguments(args, Set.of());

		arguments.pollAllArgs();

		List<String> actuals = arguments.pollAllArgs();

		assertThat(actuals).isEmpty();
	}
}

package de.voomdoon.util.cli.args.exception.option;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.cli.args.Option;
import de.voomdoon.util.cli.args.OptionBuilder;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class MissingCliOptionExceptionTest extends TestBase implements Consumer<Option> {

	/**
	 * @since 0.1.0
	 */
	@Override
	public void accept(Option t) {
		// ignore
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_mandatory_true() {
		logTestStart();

		Option option = new OptionBuilder(this).isMandatory().build();

		MissingCliOptionException actual = new MissingCliOptionException(option);

		assertThat(actual).hasMessageContaining("mandatory");
	}
}

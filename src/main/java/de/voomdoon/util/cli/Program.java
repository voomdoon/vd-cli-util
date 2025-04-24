package de.voomdoon.util.cli;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import de.voomdoon.logging.LogManager;
import de.voomdoon.logging.Logger;
import de.voomdoon.util.cli.args.Arguments;
import de.voomdoon.util.cli.args.Option;
import de.voomdoon.util.cli.args.OptionBuilder;
import de.voomdoon.util.cli.args.exception.InvalidProgramOptionException;
import de.voomdoon.util.cli.args.exception.MissingMandatoryArgumentException;

/**
 * Base class for command line programs.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class Program {

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	interface HelpGenerator {

		/**
		 * DOCME add JavaDoc for method getFull
		 * 
		 * @return
		 * @since 0.1.0
		 */
		String getFull();
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private class Options implements Consumer<Option> {

		/**
		 * @since 0.1.0
		 */
		private Option help;

		/**
		 * @since 0.1.0
		 */
		private Set<Option> options = new HashSet<>();

		/**
		 * @since 0.1.0
		 */
		@Override
		public void accept(Option option) {
			options.add(option);
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private class ProgramHelpGenerator implements HelpGenerator {

		/**
		 * @since 0.1.0
		 */
		@Override
		public String getFull() {
			StringBuilder sb = new StringBuilder();
			// FEATURE add version
			sb.append(getName());
			appendOptions(sb);
			// FEATURE add other info (e.g. website)

			return sb.toString();
		}

		/**
		 * @param sb
		 * @param option
		 * @return
		 * @since 0.1.0
		 */
		private void appendOption(StringBuilder sb, Option option) {
			sb.append("\n    ").append("--").append(option.longName());

			if (option.hasValue()) {
				sb.append(" <").append(option.valueName()).append(">");
			}
		}

		/**
		 * @param sb
		 * @since 0.1.0
		 */
		private void appendOptions(StringBuilder sb) {
			if (!options.options.isEmpty()) {
				sb.append("\noptions:");
			}

			options.options.stream().sorted((o1, o2) -> o1.longName().compareToIgnoreCase(o2.longName()))
					.forEach(o -> appendOption(sb, o));
		}
	}

	/**
	 * Intended to be called by {@code main} method.
	 * 
	 * @param clazz
	 * @param args
	 * @since 0.1.0
	 */
	public static void run(String[] args) {
		ProgramRunUtil.run(args);
	}

	/**
	 * @since 0.1.0
	 */
	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * @since 0.1.0
	 */
	private Arguments arguments;

	/**
	 * @since 0.1.0
	 */
	private Options options;

	/**
	 * DOCME add JavaDoc for constructor Program
	 * 
	 * @param args
	 * @since 0.1.0
	 */
	protected Program() {
		// nothing to do yet
	}

	/**
	 * DOCME add JavaDoc for method addOption
	 * 
	 * @return
	 * @since 0.1.0
	 */
	protected OptionBuilder addOption() {
		return new OptionBuilder(options);
	}

	/**
	 * @return arguments
	 * @since 0.1.0
	 */
	protected Arguments getArguments() {
		return arguments;
	}

	/**
	 * DOCME add JavaDoc for method getHelpGenerator
	 * 
	 * @return
	 * @since 0.1.0
	 */
	protected HelpGenerator getHelpGenerator() {
		return new ProgramHelpGenerator();
	}

	/**
	 * DOCME add JavaDoc for method getName
	 * 
	 * @return
	 * @since 0.1.0
	 */
	protected String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * @param option
	 *            {@link Option}
	 * @return {@link Optional} of {@link String}
	 * @since 0.1.0
	 */
	protected Optional<String> getOptionValue(Option option) {
		return arguments.getOptionValue(option);
	}

	/**
	 * @throws InvalidProgramOptionException
	 * 
	 * @since 0.1.0
	 */
	protected void init(String[] args) throws InvalidProgramOptionException {
		// TODO rename to initProgram
		initOptionsInternal();

		arguments = new Arguments(args, options.options);
	}

	/**
	 * @since 0.1.0
	 */
	protected void initOptions() {
		logger.trace("initOptions not overwritten");
	}

	/**
	 * @param name
	 *            name of the argument
	 * @return {@link String}
	 * @throws MissingMandatoryArgumentException
	 * @since 0.1.0
	 */
	protected String pollArg(String name) throws MissingMandatoryArgumentException {
		return arguments.pollArg(name);
	}

	/**
	 * Main method to be executed.
	 * 
	 * @throws Exception
	 * @since 0.1.0
	 */
	protected abstract void run() throws Exception;

	/**
	 * @throws Exception
	 * @since 0.1.0
	 */
	protected void runProgram() throws Exception {
		if (arguments.hasOption(options.help)) {
			System.out.println(getHelpGenerator().getFull());
		} else {
			run();
		}
	}

	/**
	 * @since 0.1.0
	 */
	private void initOptionsInternal() {
		options = new Options();
		options.help = addOption().longName("help").build();
		initOptions();
	}
}

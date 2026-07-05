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
import de.voomdoon.util.cli.args.exception.argument.MissingCliArgumentException;
import de.voomdoon.util.cli.args.exception.option.CliOptionException;

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
	private class ProgramOptions implements Consumer<Option> {

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
	 * @param args
	 *            command line arguments as {@link String} array
	 * @since 0.1.0
	 */
	public static void run(String[] args) {
		ProgramRunner.run(args);
	}

	/**
	 * Logger for the concrete program class.
	 *
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
	private ProgramOptions options;

	/**
	 * Creates a command line program.
	 * @since 0.1.0
	 */
	protected Program() {
		// nothing to do yet
	}

	/**
	 * Starts building an option.
	 * 
	 * @return {@link OptionBuilder}
	 * @since 0.1.0
	 */
	protected OptionBuilder addOption() {
		return new OptionBuilder(options);
	}

	/**
	 * Returns the parsed arguments.
	 *
	 * @return {@link Arguments}
	 * @since 0.1.0
	 */
	protected Arguments getArguments() {
		return arguments;
	}

	/**
	 * Returns the help generator.
	 * 
	 * @return {@link HelpGenerator}
	 * @since 0.1.0
	 */
	protected HelpGenerator getHelpGenerator() {
		return new ProgramHelpGenerator();
	}

	/**
	 * Returns the program name.
	 * 
	 * @return program name as {@link String}
	 * @since 0.1.0
	 */
	protected String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * Returns the value supplied for an option.
	 *
	 * @param option
	 *            {@link Option}
	 * @return {@link Optional} of {@link String}
	 * @since 0.1.0
	 */
	protected Optional<String> getOptionValue(Option option) {
		return arguments.getOptionValue(option);
	}

	/**
	 * Initializes the program from command line arguments.
	 *
	 * @param args
	 *            command line arguments as {@link String} array
	 * @throws CliOptionException
	 *             if an option is invalid
	 * 
	 * @since 0.1.0
	 */
	protected void init(String[] args) throws CliOptionException {
		// TODO rename to initProgram
		initOptionsInternal();

		arguments = new Arguments(args, options.options);
	}

	/**
	 * Initializes program options.
	 *
	 * @since 0.1.0
	 */
	protected void initOptions() {
		logger.trace("initOptions not overwritten");
	}

	/**
	 * Removes and returns the next positional argument.
	 *
	 * @param name
	 *            name of the argument
	 * @return {@link String}
	 * @throws MissingCliArgumentException
	 *             if no positional argument remains
	 * @since 0.1.0
	 */
	protected String pollArg(String name) throws MissingCliArgumentException {
		return arguments.pollArg(name);
	}

	/**
	 * Main method to be executed.
	 * 
	 * @throws Exception
	 *             if program execution fails
	 * @since 0.1.0
	 */
	protected abstract void run() throws Exception;

	/**
	 * Runs the program or displays help.
	 *
	 * @throws Exception
	 *             if program execution fails
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
		options = new ProgramOptions();
		options.help = addOption().longName("help").build();
		initOptions();
	}
}

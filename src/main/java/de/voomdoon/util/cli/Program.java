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

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class Program {

	/**
	 * DOCME add JavaDoc for Program
	 *
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
	 * DOCME add JavaDoc for Program
	 *
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
			sb.append(getName());
			appendOptions(sb);
			// TODO add website

			return sb.toString();
		}

		/**
		 * DOCME add JavaDoc for method appendOption
		 * 
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
		 * DOCME add JavaDoc for method appendOptions
		 * 
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
	 * DOCME add JavaDoc for method run
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
	 * @return arguments
	 * @since 0.1.0
	 */
	public Arguments getArguments() {
		return arguments;
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
	 * DOCME add JavaDoc for method getOptionValue
	 * 
	 * @param option
	 * @return
	 * @since 0.1.0
	 */
	protected Optional<String> getOptionValue(Option option) {
		return arguments.getOptionValue(option);
	}

	/**
	 * DOCME add JavaDoc for method init
	 * 
	 * @throws InvalidProgramArgumentsException
	 * 
	 * @since 0.1.0
	 */
	protected void init(String[] args) throws InvalidProgramArgumentsException {
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
	 * DOCME add JavaDoc for method pollArg
	 * 
	 * @param name
	 * @return
	 * @since 0.1.0
	 */
	protected String pollArg(String name) {
		return arguments.pollArg(name);
	}

	/**
	 * DOCME add JavaDoc for method runProgram
	 * 
	 * @throws Exception
	 * @since 0.1.0
	 */
	protected abstract void run() throws Exception;

	/**
	 * DOCME add JavaDoc for method run
	 * 
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
	 * DOCME add JavaDoc for method initOptionsInternal
	 * 
	 * @since 0.1.0
	 */
	private void initOptionsInternal() {
		options = new Options();
		options.help = addOption().longName("help").build();
		initOptions();
	}
}

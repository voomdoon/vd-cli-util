package de.voomdoon.util.cli.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.voomdoon.util.cli.args.exception.InvalidProgramOptionException;
import de.voomdoon.util.cli.args.exception.MissingMandatoryArgumentException;

/**
 * Accessor for CLi arguments and options.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class Arguments {

	/**
	 * @since 0.1.0
	 */
	private LinkedList<String> args;

	/**
	 * @since 0.1.0
	 */
	private Map<Option, String> optionValues;

	/**
	 * DOCME add JavaDoc for constructor Arguments
	 * 
	 * @param args
	 * @param options
	 * @throws InvalidProgramOptionException
	 * @since 0.1.0
	 */
	public Arguments(String[] args, Set<Option> options) throws InvalidProgramOptionException {
		this.args = new LinkedList<>(Arrays.asList(args));

		parseOptions(options);
	}

	/**
	 * @param option
	 *            {@link Option}
	 * @return {@link Optional} of {@link String}
	 * @since 0.1.0
	 */
	public Optional<String> getOptionValue(Option option) {
		return Optional.ofNullable(optionValues.get(option));
	}

	/**
	 * @param option
	 *            {@link Option}
	 * @return {@code true} or {@code false}
	 * @since 0.1.0
	 */
	public boolean hasOption(Option option) {
		return getOptionValue(option).isPresent();
	}

	/**
	 * 
	 * @return {@link List} of all remaining arguments
	 * @since 0.1.0
	 */
	public List<String> pollAllArgs() {
		List<String> remaining = List.copyOf(args);
		args.clear();

		return remaining;
	}

	/**
	 * @param name
	 *            name of the argument
	 * @return {@link String}
	 * @throws MissingMandatoryArgumentException
	 * @since 0.1.0
	 */
	public String pollArg(String name) throws MissingMandatoryArgumentException {
		if (args.isEmpty()) {
			throw new MissingMandatoryArgumentException(name);
		}

		return args.poll();
	}

	/**
	 * @param options
	 *            {@link Set} of {@link Option}
	 * @throws InvalidProgramOptionException
	 * 
	 * @since 0.1.0
	 */
	private void parseOptions(Set<Option> options) throws InvalidProgramOptionException {
		Map<String, Option> longNameOptions = options.stream()
				.collect(Collectors.toMap(Option::longName, Function.identity()));

		optionValues = new HashMap<>();

		while (!args.isEmpty()) {
			String arg = args.peek();

			if (arg.startsWith("--")) {
				Option option = longNameOptions.get(arg.substring(2));

				if (option != null) {
					parseOptionValue(option);
				} else {
					return;
				}
			} else {
				return;
			}
		}
	}

	/**
	 * @param option
	 * @throws InvalidProgramOptionException
	 * @since 0.1.0
	 */
	private void parseOptionValue(Option option) throws InvalidProgramOptionException {
		args.poll();

		if (option.hasValue()) {
			if (!args.isEmpty()) {
				optionValues.put(option, args.poll());
			} else {
				throw new InvalidProgramOptionException(option, "missing value");
			}
		} else {
			optionValues.put(option, "true");
		}
	}
}

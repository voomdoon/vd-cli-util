package de.voomdoon.util.cli.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.voomdoon.util.cli.InvalidProgramArgumentsException;

/**
 * DOCME add JavaDoc for
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
	 * @throws InvalidProgramArgumentsException
	 * @since 0.1.0
	 */
	public Arguments(String[] args, Set<Option> options) throws InvalidProgramArgumentsException {
		this.args = new LinkedList<>(Arrays.asList(args));

		parseOptions(options);
	}

	/**
	 * DOCME add JavaDoc for method getAllRemaining
	 * 
	 * @since DOCME add inception version number
	 */
	public List<String> getAllRemaining() {
		List<String> remaining = List.copyOf(args);
		args.clear();

		return remaining;
	}

	/**
	 * DOCME add JavaDoc for method getOptionValue
	 * 
	 * @param option
	 * @return
	 * @since 0.1.0
	 */
	public Optional<String> getOptionValue(Option option) {
		return Optional.ofNullable(optionValues.get(option));
	}

	/**
	 * DOCME add JavaDoc for method hasOption
	 * 
	 * @param option
	 * @return
	 * @since 0.1.0
	 */
	public boolean hasOption(Option option) {
		return getOptionValue(option).isPresent();
	}

	/**
	 * DOCME add JavaDoc for method poll
	 * 
	 * @param name
	 * @return
	 * @since 0.1.0
	 */
	public String pollArg(String name) {
		if (args.isEmpty()) {
			throw new NoSuchElementException(name);
		}

		return args.poll();
	}

	/**
	 * DOCME add JavaDoc for method parseOptions
	 * 
	 * @param options
	 * @throws InvalidProgramArgumentsException
	 * 
	 * @since 0.1.0
	 */
	private void parseOptions(Set<Option> options) throws InvalidProgramArgumentsException {
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
	 * @throws InvalidProgramArgumentsException
	 * @since 0.1.0
	 */
	private void parseOptionValue(Option option) throws InvalidProgramArgumentsException {
		args.poll();

		if (option.hasValue()) {
			if (!args.isEmpty()) {
				optionValues.put(option, args.poll());
			} else {
				throw new InvalidProgramArgumentsException(option, "missing value");
			}
		} else {
			optionValues.put(option, "true");
		}
	}
}

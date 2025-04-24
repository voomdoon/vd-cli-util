package de.voomdoon.util.cli;

import java.util.HashMap;
import java.util.Map;

import de.voomdoon.util.cli.args.exception.InvalidProgramOptionException;
import de.voomdoon.util.cli.args.exception.MissingMandatoryArgumentException;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class MainBase extends Program {

	/**
	 * @since 0.1.0
	 */
	private class MainBaseHelpSupplier implements HelpGenerator {

		/**
		 * @since 0.1.0
		 */
		private HelpGenerator parent;

		/**
		 * DOCME add JavaDoc for constructor HelpSupplier
		 * 
		 * @param parent
		 * @since 0.1.0
		 */
		public MainBaseHelpSupplier(HelpGenerator parent) {
			this.parent = parent;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public String getFull() {
			StringBuilder sb = new StringBuilder();
			sb.append(parent.getFull());
			// TODO improve order (add footer)

			sb.append("\n\nsub-mains:");

			subMains.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())).forEach(entry -> {
				sb.append("\n    • ").append(entry.getKey());// TODO other info
			});

			return sb.toString();
		}
	}

	/**
	 * @since 0.1.0
	 */
	private Map<String, Class<? extends Program>> subMains;

	/**
	 * @since 0.1.0
	 */
	@Override
	protected HelpGenerator getHelpGenerator() {
		return new MainBaseHelpSupplier(super.getHelpGenerator());
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	protected void init(String[] args) throws InvalidProgramOptionException {
		super.init(args);

		initSubMains();
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	protected void initOptions() {
		// nothing to do
	}

	/**
	 * DOCME add JavaDoc for method registerSubMain
	 * 
	 * @param key
	 * @param clazz
	 * @since 0.1.0
	 */
	protected void registerSubMain(String key, Class<? extends Program> clazz) {
		subMains.put(key, clazz);
	}

	/**
	 * DOCME add JavaDoc for method registerSubMains
	 * 
	 * @since 0.1.0
	 */
	protected abstract void registerSubMains();

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @throws InvalidSubProgramException
	 * 
	 * @since 0.1.0
	 */
	@Override
	protected void run() {
		String subMain;

		try {
			subMain = pollArg("sub-main");
		} catch (MissingMandatoryArgumentException e) {
			System.err.println("Missing argument 'sub-main'!");
			printHelp();
			return;
		}

		Class<? extends Program> clazz = subMains.get(subMain);

		if (clazz == null) {
			throw new ProgramRunException("Failed to find sub-main for '" + subMain + "!", getHelpString());
		}

		ProgramRunUtil.run(clazz, getArguments().pollAllArgs().toArray(new String[0]));
	}

	/**
	 * DOCME add JavaDoc for method getHelpString
	 * 
	 * @return
	 * @since 0.1.0
	 */
	private String getHelpString() {
		StringBuilder sb = new StringBuilder();
		sb.append("sub-mains:");
		subMains.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareToIgnoreCase(e2.getKey()))
				.forEach(e -> sb.append("\n• ").append(e.getKey()));
		// TODO add description of sub-main

		return sb.toString();
	}

	/**
	 * @since 0.1.0
	 */
	private void initSubMains() {
		subMains = new HashMap<>();
		registerSubMains();
		subMains = Map.copyOf(subMains);
	}

	/**
	 * @since 0.1.0
	 */
	private void printHelp() {
		println(getName());
		println(getHelpString());
	}

	/**
	 * @param message
	 * @since 0.1.0
	 */
	private void println(String message) {
		System.out.println(message);
	}
}

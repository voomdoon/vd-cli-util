package de.voomdoon.util.cli.testing;

import de.voomdoon.util.cli.Program;
import lombok.experimental.UtilityClass;

/**
 * Testing utility for {@link Program}.
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
@UtilityClass
public class ProgramTestingUtil {

	/**
	 * Enables testing mode for the {@link Program} class. <br>
	 * This disables calling {@link System#exit(int)} but throws {@code ProgramExecutionException}.
	 * 
	 * @since 0.2.0
	 */
	public static void enableTestingMode() {
		// FEATURE add soft check with logging whether called within test scope

		try {
			Class<?> clazz = Class.forName("de.voomdoon.util.cli.ProgramRunUtil");
			var method = clazz.getDeclaredMethod("enableTestingMode");
			method.setAccessible(true);
			method.invoke(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to enable testing mode via reflection", e);
		}
	}
}

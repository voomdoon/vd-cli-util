package de.voomdoon.util.cli.testing;

import lombok.experimental.UtilityClass;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.2.0
 */
@UtilityClass
public class ProgramTestingUtil {

	/**
	 * DOCME add JavaDoc for method enableTestingMode
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

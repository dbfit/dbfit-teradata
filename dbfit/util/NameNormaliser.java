
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

/**
 * Utility class for normalising identifiers.
 */
public class NameNormaliser {
	private NameNormaliser() {
		// utility classes should not be instanciated
	}

	private static String replaceIllegalCharactersWithSpacesRegex = "[^a-zA-Z0-9_.#]";

	private static String replaceIllegalCharacters(final String name) {
		return name.replaceAll(replaceIllegalCharactersWithSpacesRegex, "");
	}

	public static String normaliseName(final String name) {
		if (name == null)
			return "";
		return replaceIllegalCharacters(name.toLowerCase());
	}
}
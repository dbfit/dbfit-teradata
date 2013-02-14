
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

public class NoMatchingRowFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoMatchingRowFoundException() {
		super("No matching row found");
	}
}

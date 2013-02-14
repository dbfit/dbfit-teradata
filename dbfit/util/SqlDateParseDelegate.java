
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.text.DateFormat;

public class SqlDateParseDelegate {
	private static DateFormat df = DateFormat.getDateInstance();

	public static Object parse(String s) throws Exception {
		try {
			return java.sql.Date.valueOf(s);
		} catch (IllegalArgumentException iex) {
			java.util.Date ud = df.parse(s);
			return new java.sql.Date(ud.getTime());
		}
	}
}

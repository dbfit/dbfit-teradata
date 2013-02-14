
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.text.DateFormat;
import java.text.ParseException;

/** this horrible class is a workaround for date format incompatibilities in javax.sql package
 * it will first try to parse the date in the standard JDBC timestamp format. If that fails, then 
 * it will try to parse it in the standard JDBC date format. If that also fails, it will try the current locale
 * date/time format, and the current locale date format.
 */
public class SqlTimestampParseDelegate {
	private static DateFormat dtf = DateFormat.getDateTimeInstance();
	private static DateFormat df = DateFormat.getDateInstance();

	public static Object parse(String s) throws Exception {
		try {
			return java.sql.Timestamp.valueOf(s);
		} catch (IllegalArgumentException iex) {
			try {
				return new java.sql.Timestamp(java.sql.Date.valueOf(s)
						.getTime());
			} catch (IllegalArgumentException iex2) {
				try {
					java.util.Date ud = dtf.parse(s);
					return new java.sql.Timestamp(ud.getTime());
				} catch (ParseException pex) {
					java.util.Date ud = df.parse(s);
					return new java.sql.Timestamp(ud.getTime());
				}
			}
		}
	}
}

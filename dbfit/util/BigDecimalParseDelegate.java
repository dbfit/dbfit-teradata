
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;
import java.math.BigDecimal;

public class BigDecimalParseDelegate {
		public static Object parse(String s) {
			System.out.println("BigDecimalParseDelegate: parse: s: "+s);
			return new BigDecimal(s);
		};
}

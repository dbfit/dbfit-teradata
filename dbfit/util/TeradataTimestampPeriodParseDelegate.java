
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.text.DateFormat;
import dbfit.util.DbStruct;

public class TeradataTimestampPeriodParseDelegate {
	
	private static DateFormat df = DateFormat.getDateTimeInstance();	
	
	public static Object parse(String s) throws Exception {
		
		System.out.println("TeradataTimestampPeriodParseDelegate: parse: s: " + s);
		String[] periodParts = s.split(",");
		
		java.sql.Timestamp F = (java.sql.Timestamp) SqlTimestampParseDelegate.parse(periodParts[0]);
		java.sql.Timestamp T = (java.sql.Timestamp) SqlTimestampParseDelegate.parse(periodParts[1]);
		
		Object[] data = { F, T }; 
		       
		return new TeradataTimestampPeriod(data);
	}
}
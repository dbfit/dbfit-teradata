
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

public class TeradataDatePeriodParseDelegate {
	
	private static DateFormat df = DateFormat.getDateInstance();	
	
	public static Object parse(String s) throws Exception {
		
		System.out.println("TeradataDatePeriodParseDelegate: parse: s: " + s);
		String[] periodParts = s.split(",");
		java.sql.Date F;
		java.sql.Date T;
		java.util.Date ParsedFrom;
		java.util.Date ParsedTo;
		
		try {
			//ParsedFrom = java.sql.Date.valueOf(periodParts[0]);
			F = java.sql.Date.valueOf(periodParts[0]);
		} catch (IllegalArgumentException iex) {
			java.util.Date ud = df.parse(periodParts[0]);
			//ParsedFrom = new java.sql.Date(ud.getTime());
			F = new java.sql.Date(ud.getTime());
		}
		
		try {
			//ParsedTo = java.sql.Date.valueOf(periodParts[1]);
			T = java.sql.Date.valueOf(periodParts[1]);
		} catch (IllegalArgumentException iex) {
			java.util.Date ud = df.parse(periodParts[1]);
			//ParsedTo = new java.sql.Date(ud.getTime());
			T = new java.sql.Date(ud.getTime());
		}
		
		Object[] data = { F, T }; 
		       
		//return new TeradataDatePeriod(ParsedFrom, ParsedTo);
		return new TeradataDatePeriod(data);
	}
}

/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.sql.SQLException;

public class TeradataTimestampPeriod extends DbStruct {
	
	public TeradataTimestampPeriod(Object[] dates) {
		
		super("PERIOD(TIMESTAMP)", dates);
	}
	
	@Override
	public String toString() {
		String r = "";
		try {
			Object[] a = super.getAttributes();

			for (int i = 0; i <= a.length; i++) {
				if (i > 0)
					r = r + ",";
			
					r = r + a.toString();
			}
		}
		catch (SQLException e){
			System.out.println("TeradataTimestampPeriod: toString: caught exception");
		}
		
		return r;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if (other == null)
			return false;

		if (!(other instanceof TeradataTimestampPeriod))
			return false;
		
		TeradataTimestampPeriod odp = (TeradataTimestampPeriod)other;
		
		Object[] thisAtts = null;
		Object[] otherAtts = null;
		
		try {
			otherAtts = odp.getAttributes();
			thisAtts = this.getAttributes();
		}
		catch (SQLException e) {
			System.out.println("TeradataTimestampPeriod: equals: caught exception");
		}
		
		if (!(thisAtts[0].equals(otherAtts[0])))
			return false;
		
		if (!(thisAtts[1].equals(otherAtts[1])))
			return false;
		
		return true;
	}
}
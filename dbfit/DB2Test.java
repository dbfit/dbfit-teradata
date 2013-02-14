
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit;

import dbfit.environment.DB2Environment;

public class DB2Test  extends DatabaseTest {
	public DB2Test(){
		super(new DB2Environment());
	}
	public void dbfitDotDB2Test() {
		// required by fitnesse release 20080812
	}
}

/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit;

import dbfit.environment.*;
public class SqlServerTest extends DatabaseTest {
	public SqlServerTest(){
		super(new SqlServerEnvironment());
	}
	public void dbfitDotSqlServerTest() {
		// required by fitnesse release 20080812
	}

}

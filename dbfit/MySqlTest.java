
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit;

import dbfit.environment.*;
public class MySqlTest extends DatabaseTest {
	public MySqlTest(){
		super(new MySqlEnvironment());
	}
	public void dbfitDotMySqlTest() {
		// required by fitnesse release 20080812
	}
}

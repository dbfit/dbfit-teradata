
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.environment;

public class DbEnvironmentFactory {
	private static DBEnvironment environment;
	public static DBEnvironment getDefaultEnvironment(){
		return environment;
	}
	public static void setDefaultEnvironment( DBEnvironment newDefaultEnvironment){
		environment=newDefaultEnvironment;
	}
}

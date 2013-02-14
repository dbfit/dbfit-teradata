
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.fixture;

import java.sql.PreparedStatement;

import dbfit.environment.DBEnvironment;
import dbfit.environment.DbEnvironmentFactory;
import fit.Fixture;
import fit.Parse;

public class Execute extends Fixture{
	private String statement;
	private DBEnvironment dbEnvironment;
	public Execute (){
		dbEnvironment=DbEnvironmentFactory.getDefaultEnvironment();
	}
	public Execute (DBEnvironment env, String statement){
		this.statement=statement;
		this.dbEnvironment=env;
	}
	public void doRows(Parse rows) {
		try{
			if (statement==null) statement=args[0];
			PreparedStatement st=dbEnvironment.createStatementWithBoundFixtureSymbols(statement);
			st.execute();
		}
		catch (Exception e){
			throw new Error(e);
		}
	}
}

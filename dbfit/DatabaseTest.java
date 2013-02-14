
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dbfit.environment.DBEnvironment;
import fit.Fixture;
import fit.Parse;
import fitlibrary.SequenceFixture;

public class DatabaseTest extends Fixture{
	protected DBEnvironment environment;	
	// ugly workaround since fitlibrary no longer allows this to be 
	// overridden; we create an inner sequence fixture and pass the 
	// execution to it, but this one is now a fixture to allow things to be overridden
    public void interpretTables(Parse tables){
    	dbfit.util.Options.reset();
    	SequenceFixture sf=new SequenceFixture ();
    	sf.listener=listener;
    	sf.counts = counts;
        sf.summary = summary;
        sf.setSystemUnderTest(this);
    	sf.interpretTables(tables);
		try{
			System.out.println("Rolling back");
			if (environment!=null){
				environment.rollback();
				environment.closeConnection();
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
    public DatabaseTest(DBEnvironment environment) {
    	System.out.println("DatabaseTest: DatabaseTest()");
		this.environment = environment;
	}
	public void connect(String dataSource, String username, String password,String database)  throws SQLException {
		System.out.println("DatabaseTest: connect(String, String, String, String)");
		environment.connect(dataSource, username, password,database);
		environment.getConnection().setAutoCommit(false);
	}
	public void connect(String dataSource, String username, String password) throws SQLException {
		System.out.println("DatabaseTest: connect(String, String, String)");
        environment.connect(dataSource, username, password);
		environment.getConnection().setAutoCommit(false);
	}
	public void connect(String connectionString) throws SQLException {
		System.out.println("DatabaseTest: connect()");
		environment.connect(connectionString);
		environment.getConnection().setAutoCommit(false);
	}
	public void connectUsingFile(String filePath) throws SQLException,IOException,FileNotFoundException {
		System.out.println("DatabaseTest: connectUsingFile()");
        environment.connectUsingFile(filePath);
		environment.getConnection().setAutoCommit(false);
	}
	
    public void close() throws SQLException 
    {
    	System.out.println("DatabaseTest: close()");
		environment.rollback();
    	environment.closeConnection();
    }

	public void setParameter(String name, String value) {
		dbfit.fixture.SetParameter.setParameter(name, value);
	}
	public void clearParameters() {
		dbfit.util.SymbolUtil.clearSymbols();
	}
    public Fixture query(String query)
    {
        return new dbfit.fixture.Query(environment, query);
    }
    public Fixture orderedQuery(String query)
    {
        return new dbfit.fixture.Query(environment, query,true);
    }
    public Fixture execute(String statement)
    {
        return new dbfit.fixture.Execute(environment, statement);
    }
    public Fixture executeProcedure(String statement)
    {
        return new dbfit.fixture.ExecuteProcedure(environment, statement);
    }
    public Fixture executeProcedureExpectException(String statement)
    {
        return new dbfit.fixture.ExecuteProcedure(environment, statement,true);
    }
    public Fixture executeProcedureExpectException(String statement, int code)
    {
        return new dbfit.fixture.ExecuteProcedure(environment, statement,code);
    }
    public Fixture insert(String tableName){
    	return new dbfit.fixture.Insert(environment,tableName);
    }
    public Fixture update(String tableName){
    	return new dbfit.fixture.Update(environment,tableName);
    }
    public Fixture clean(){
    	return new dbfit.fixture.Clean(environment);
    }
    public void rollback() throws SQLException{
    	System.out.println("DatabaseTest: rollback()");
    	environment.rollback();
		environment.getConnection().setAutoCommit(false);
    }
    public void commit() throws SQLException{
    	System.out.println("DatabaseTest: commit()");
    	environment.commit();
		environment.getConnection().setAutoCommit(false);
    }
    public Fixture queryStats() {
    	return new dbfit.fixture.QueryStats(environment);
    }
    public Fixture inspectProcedure(String procName){
    	return new dbfit.fixture.Inspect(environment,dbfit.fixture.Inspect.MODE_PROCEDURE,procName);
    }
    public Fixture inspectTable(String tableName){
    	return new dbfit.fixture.Inspect(environment,dbfit.fixture.Inspect.MODE_TABLE,tableName);
    }
    public Fixture inspectView(String tableName){
    	return new dbfit.fixture.Inspect(environment,dbfit.fixture.Inspect.MODE_TABLE,tableName);
    }
    public Fixture inspectQuery(String query){
    	return new dbfit.fixture.Inspect(environment,dbfit.fixture.Inspect.MODE_QUERY,query);
    }
    public Fixture storeQuery(String query, String symbolName){
    	return new dbfit.fixture.StoreQuery(environment,query,symbolName);
    }
    public Fixture compareStoredQueries(String symbol1, String symbol2){
    	return new dbfit.fixture.CompareStoredQueries(environment,symbol1, symbol2);    	
    }
	public void setOption(String option, String value){
		dbfit.util.Options.setOption(option, value);
	}
}

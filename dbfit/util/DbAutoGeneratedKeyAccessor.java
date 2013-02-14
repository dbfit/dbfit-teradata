
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fit.Fixture;

public class DbAutoGeneratedKeyAccessor extends DbParameterAccessor {
	
	public DbAutoGeneratedKeyAccessor (DbParameterAccessor c){
		super(c.getName(),DbParameterAccessor.RETURN_VALUE,c.getSqlType(),c.type,c.getPosition());
	}
	private PreparedStatement statement;
	public void bindTo(Fixture f, PreparedStatement cs, int ind) throws SQLException{
		this.statement=cs;
		this.fixture=f;
	}
	public void set(Object value) throws Exception {
			throw new UnsupportedOperationException("Trying to set value of output parameter "+getName());
	}	
	public Object get() throws IllegalAccessException, InvocationTargetException {
		try{
		ResultSet rs=statement.getGeneratedKeys();
		if (rs.next()) //todo: first try to find by name (mysql does not support name-based return keys)
			return rs.getObject(1);
		}
		catch (SQLException sqle){
			throw new InvocationTargetException(sqle);
		}
		throw new IllegalAccessException("statement has not generated any keys");
	}
}

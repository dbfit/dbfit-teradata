
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.sql.*;

//This class is used instead of calling Connection.createStruct, which is
// only supported in JDK 6.0 or later
public class DbStruct implements Struct {

    private Object[] m_attributes;
    private String m_sqlTypeName;

    public DbStruct()
    {
    	System.out.println("DbStruct: DbStruct()");
    }
    
    public DbStruct(String sqlTypeName, Object[] attributes)
    {
    	System.out.println("DbStruct: DbStruct(String, Object[])");
    	
        m_sqlTypeName = sqlTypeName;
        m_attributes = attributes;
    }

    // Returns attributes
    public Object[] getAttributes() throws SQLException
    {
        return m_attributes;
    }

    // Returns SQLTypeName
    public String getSQLTypeName() throws SQLException
    {
        return m_sqlTypeName;
    }

    // This method is not supported, but needs to be included
    public Object[] getAttributes(java.util.Map map) throws SQLException
    {
        //Unsupported Exception
        throw new SQLException("getAttributes(Map) NOT SUPPORTED");
    }
}

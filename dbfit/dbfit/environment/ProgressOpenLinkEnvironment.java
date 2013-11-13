package dbfit.environment;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Clob;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import dbfit.util.DbParameterAccessor;
import dbfit.util.NameNormaliser;
import dbfit.util.TypeNormaliser;
import dbfit.util.TypeNormaliserFactory;

import dbfit.util.TeradataDatePeriodParseDelegate;
import dbfit.util.TeradataDatePeriod;
import dbfit.util.TeradataTimestampPeriodParseDelegate;
import dbfit.util.TeradataTimestampPeriod;
import dbfit.util.TeradataTimePeriodParseDelegate;
import dbfit.util.TeradataTimePeriod;
import dbfit.util.DbStruct;

import fit.TypeAdapter;

public class ProgressOpenLinkEnvironment extends AbstractDbEnvironment {
	
	public ProgressOpenLinkEnvironment() {
	}
		  
    public boolean supportsOuputOnInsert(){
    	return false;
    }
	protected String getDriverClassName() {
		return "openlink.jdbc3.Driver";
	}
	protected String getConnectionString(String dataSource)
    {
        return "jdbc:openlink://"+dataSource;
	}
	protected String getConnectionString(String dataSource, String dataBase)
    {
        String url = "jdbc:openlink://"+dataSource;
        return url;
	}
	
	private static String paramNamePattern = ":([A-Za-z0-9_]+)";
	private static Pattern paramsNames = Pattern.compile(":([A-Za-z0-9_]+)");
	
	public Pattern getParameterPattern(){
		return paramsNames;
	}
	
	protected String parseCommandText(String commandText) {
		commandText = commandText.replaceAll(paramNamePattern, "?");
		return super.parseCommandText(commandText);
	}
	
    public Map<String, DbParameterAccessor> getAllProcedureParameters(String procName) throws SQLException
    {
    	System.out.println("TeradataEnvironment: getAllProcedureParameters: tableOrViewName: " + procName);
    	throw new UnsupportedOperationException("ProgressOpenLinkEnvironment: getAllProcedureParameters: Progress database does not support stored procedures");
	}

    public Map<String, DbParameterAccessor> getAllColumns(String tableOrViewName) throws SQLException
    {
		System.out.println("ProgressOpenLinkEnvironment: getAllColumns: tableOrViewName: "+tableOrViewName);
    	
    	String[] qualifiers = NameNormaliser.normaliseName(tableOrViewName).split("\\.");
		
		String cols = "_field-name, _data-type, 'IN'"; 
		String qry = "SELECT " + cols + " FROM _field, _file ";
		qry = qry  + "WHERE _field._file-recid = RECID(_file )";
		qry = qry  + "AND _file-name = '" + tableOrViewName + "' ";
		if (qualifiers.length==2) {
			qry += "AND _file._owner = ? ";
		}
		qry += "ORDER BY _field._order";
		return readIntoParams(qualifiers, qry);
	}

	private Map<String, DbParameterAccessor> readIntoParams(String[] queryParameters, String query)
		throws SQLException {
		
		System.out.println("ProgressOpenLinkEnvironment: readIntoParams: query: "+query);
		for (int i=0; i<queryParameters.length; i++) {
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: queryParameters["+i+"]: "+queryParameters[i]);
		}
		CallableStatement dc=currentConnection.prepareCall(query);
		for (int i = 0; i < queryParameters.length; i++) {
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: Setting value for parameter: "+i);			
			dc.setString(i+1,queryParameters[i].toUpperCase());
		}
		ResultSet rs = dc.executeQuery();
		Map<String, DbParameterAccessor> allParams = new HashMap<String, DbParameterAccessor>();
		int position=0;
		while (rs.next()) {
			String paramName = rs.getString(1);
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: paramName: "+paramName+", has length: "+paramName.length());
			if (paramName.equals("")) {
				System.out.println("ProgressOpenLinkEnvironment: readIntoParams: paramName==\"\"");
				paramName = ""; // Function return values get empty parameter names.
			}
			String dataType = rs.getString(2);
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: dataType: "+dataType);
			String direction = rs.getString(3);
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: direction: "+direction);
			int paramDirection;
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: +paramName.trim().toUpperCase()+: +"+paramName.trim().toUpperCase()+"+");
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: setting paramDirection to getParameterDirection(direction): "+getParameterDirection(direction));
			paramDirection=getParameterDirection(direction);
			
			System.out.println("ProgressOpenLinkEnvironment: readIntoParams: creating new DbParameterAccessor for paramName: " + paramName + ", paramDirection: " + paramDirection + ", dataType: " + dataType);
			int intSqlType = getSqlType(dataType);
			Class<?> clsJavaClass = getJavaClass(dataType);
			DbParameterAccessor dbp = new DbParameterAccessor(paramName, paramDirection, intSqlType, clsJavaClass, paramDirection == DbParameterAccessor.RETURN_VALUE ? -1 : position++);
			//DbParameterAccessor dbp = new DbParameterAccessor(paramName, paramDirection, getSqlType(dataType), getJavaClass(dataType), paramDirection == DbParameterAccessor.RETURN_VALUE ? -1 : position++);			
			allParams.put(NameNormaliser.normaliseName(paramName), dbp);
		}
		System.out.println("ProgressOpenLinkEnvironment: readIntoParams: returning");
		return allParams;
	}

	// List interface has sequential search, so using list instead of array to map types
	private static List<String> stringTypes = Arrays.asList(new String[] { "CHARACTER","FIXCHAR" });
	private static List<String> longTypes = Arrays.asList(new String[]{ "BIGINT" });
	private static List<String> intTypes = Arrays.asList(new String[] { "INTEGER", "RECID" });
	private static List<String> shortTypes = Arrays.asList(new String[]{ "SHORT" });
	private static List<String> decimalTypes = Arrays.asList(new String[] { "DECIMAL" });
	private static List<String> doubleTypes = Arrays.asList(new String[]{ "DOUBLE","FLOAT" });
	private static List<String> dateTypes = Arrays.asList(new String[] { "DATE" });
	private static List<String> timestampTypes = Arrays.asList(new String[]{ "TIMESTAMP" });
	private static List<String> timeTypes = Arrays.asList(new String[]{ "TIME" });
	private static List<String> binaryTypes = Arrays.asList(new String[]{ "BYTE" });
	private static List<String> varBinaryTypes = Arrays.asList(new String[]{ "RAW" });
	private static List<String> booleanTypes = Arrays.asList(new String[]{ "LOGICAL" });
	
	private static List<Integer> stringSqlTypes = Arrays.asList(new Integer[] { java.sql.Types.CHAR, java.sql.Types.VARCHAR });
	private static List<Integer> longSqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.BIGINT });
	private static List<Integer> intSqlTypes = Arrays.asList(new Integer[] { java.sql.Types.INTEGER });
	private static List<Integer> shortSqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.SMALLINT });
	private static List<Integer> decimalSqlTypes = Arrays.asList(new Integer[] { java.sql.Types.DECIMAL });
	private static List<Integer> doubleSqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.DOUBLE });
	private static List<Integer> dateSqlTypes = Arrays.asList(new Integer[] { java.sql.Types.DATE });
	private static List<Integer> timestampSqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.TIMESTAMP });
	private static List<Integer> timeSqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.TIME });
	private static List<Integer> binarySqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.BINARY });
	private static List<Integer> varBinarySqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.VARBINARY, java.sql.Types.LONGVARBINARY });
	private static List<Integer> booleanSqlTypes = Arrays.asList(new Integer[]{ java.sql.Types.BOOLEAN, java.sql.Types.BIT });
	
	private static String normaliseTypeName(String dataType) {
		
		System.out.println("ProgressOpenLinkEnvironment: normaliseTypeName: received: " + dataType);
		
		dataType = dataType.toUpperCase().trim();
		
		int idx = 0;
		
		idx = dataType.indexOf(" ");
		if (idx >= 0)
			dataType = dataType.substring(0, idx);
			
		idx = dataType.indexOf("(");
		if (idx >= 0) 
			dataType = dataType.substring(0, idx);
		
		System.out.println("ProgressOpenLinkEnvironment: normaliseTypeName: returning: " + dataType);
		return dataType;
	}
	
	private static int getSqlType(String dataType) {
		//todo:strip everything from first blank
		dataType = normaliseTypeName(dataType);
		
		System.out.println("ProgressOpenLinkEnvironment: getSqlType: received data type: " + dataType);
		
		if (stringTypes.contains(dataType) ) return java.sql.Types.VARCHAR;
		if (longTypes.contains(dataType) ) return java.sql.Types.BIGINT;
		if (intTypes.contains(dataType) ) return java.sql.Types.INTEGER;
		if (shortTypes.contains(dataType) ) return java.sql.Types.SMALLINT;
		if (decimalTypes.contains(dataType) )return java.sql.Types.NUMERIC;
		if (doubleTypes.contains(dataType) ) return java.sql.Types.DOUBLE;
		if (dateTypes.contains(dataType) ) return java.sql.Types.DATE;
		if (timestampTypes.contains(dataType)) return java.sql.Types.TIMESTAMP;
		if (timeTypes.contains(dataType)) return java.sql.Types.TIME;
		if (binaryTypes.contains(dataType)) return java.sql.Types.BINARY;
		if (varBinaryTypes.contains(dataType)) return java.sql.Types.VARBINARY;
		if (booleanTypes.contains(dataType)) return java.sql.Types.BOOLEAN;

		throw new UnsupportedOperationException("ProgressOpenLinkEnvironment: getSqlType: Type " + dataType + " is not supported");
	}
	public Class<?> getJavaClass(String dataType) {
		
		System.out.println("ProgressOpenLinkEnvironment: getJavaClass: received data type: " + dataType);
		
		dataType = normaliseTypeName(dataType);
		
		// Be sure to align the returned Class types with those returned
		// by ResultSetMetaData.getColumnTypeName.
		
		boolean dataTypeIsInt = true;
		
		try {
			Integer.parseInt(dataType);
		}
		catch (Exception e){
			dataTypeIsInt = false;
		}
		
		if (dataTypeIsInt) {
			Integer dataTypeInt = new Integer(dataType);
			if (stringSqlTypes.contains(dataTypeInt))		return String.class;
			if (longSqlTypes.contains(dataTypeInt))			return Long.class;
			if (intSqlTypes.contains(dataTypeInt))			return Integer.class;
			if (shortSqlTypes.contains(dataTypeInt))		return Short.class;
			if (doubleSqlTypes.contains(dataTypeInt))		return Double.class;
			if (decimalSqlTypes.contains(dataTypeInt))		return BigDecimal.class;
			if (dateSqlTypes.contains(dataTypeInt))			return java.sql.Date.class;
			if (timestampSqlTypes.contains(dataTypeInt))	return java.sql.Timestamp.class;
			if (timeSqlTypes.contains(dataTypeInt))			return java.sql.Time.class;
			if (booleanSqlTypes.contains(dataTypeInt))		return Boolean.class;
		}
		else {
			if (stringTypes.contains(dataType))				return String.class;
			if (longTypes.contains(dataType))				return Long.class;
			if (intTypes.contains(dataType))				return Integer.class;
			if (shortTypes.contains(dataType))				return Short.class;
			if (doubleTypes.contains(dataType))				return Double.class;
			if (decimalTypes.contains(dataType))			return BigDecimal.class;
			if (dateTypes.contains(dataType))				return java.sql.Date.class;
			if (timestampTypes.contains(dataType))			return java.sql.Timestamp.class;
			if (timeTypes.contains(dataType))				return java.sql.Time.class;
			if (booleanTypes.contains(dataType))			return Boolean.class;
		}
		
		throw new UnsupportedOperationException("ProgressOpenLinkEnvironment: getJavaClass: Type " + dataType + " is not supported");
	}
	
	private static int getParameterDirection(String direction) {
		if ("IN".equals(direction)) return DbParameterAccessor.INPUT;
		if ("OUT".equals(direction)) return DbParameterAccessor.OUTPUT;
		if ("IN/OUT".equals(direction)) return DbParameterAccessor.INPUT_OUTPUT;
		//todo return val
		throw new UnsupportedOperationException("ProgressOpenLinkEnvironment: Direction " + direction + " is not supported");
	}
	
    public String buildInsertCommand(String tableName, DbParameterAccessor[] accessors)
    {
        System.out.println("ProgressOpenLinkEnvironment: buildInsertCommand");
    	StringBuilder sb = new StringBuilder("insert into ");
        sb.append(tableName).append("(");
        String comma = "";
        String retComma = "";

        StringBuilder values = new StringBuilder();
        StringBuilder retNames = new StringBuilder();
        StringBuilder retValues = new StringBuilder();

        for (DbParameterAccessor accessor : accessors)
        {
            if (accessor.getDirection()==DbParameterAccessor.INPUT)
            {
                sb.append(comma);
                values.append(comma);
                sb.append(accessor.getName());
                //values.append(":").append(accessor.getName());
                values.append("?");
                comma = ",";
            }
            else
            {
                retNames.append(retComma);
                retValues.append(retComma);
                retNames.append(accessor.getName());
                //retValues.append(":").append(accessor.getName());
                retValues.append("?");
                retComma = ",";
            }
        }
        sb.append(") values (");
        sb.append(values);
        sb.append(")");
        System.out.println("ProgressOpenLinkEnvironment: buildInsertCommand: sb.toString(): "+sb.toString());
        return sb.toString();
    }
}
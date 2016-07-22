package com.pqsoft.util;

import java.sql.Clob;

public class SqlUtil {

	public static String getClobTypeInfo3(Object o, String columnName) throws Exception {
		if (o == null) return null;
		Clob clob = (Clob) o;
		return clob.getSubString(1, (int) clob.length());
	}

}

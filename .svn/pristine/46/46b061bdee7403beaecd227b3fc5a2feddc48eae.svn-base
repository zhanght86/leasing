package com.pqsoft.base.interfaceTemplate.service;

import com.pqsoft.skyeye.api.AbstractCache;
import com.pqsoft.skyeye.api.Dao;

public class SqlConfigMemcached extends AbstractCache<String> {
	private static SqlConfigMemcached sqlConfigMemcached = new SqlConfigMemcached();

	public static String getSQL(String NAME) {
		return sqlConfigMemcached.get(NAME);
	}
	
	public static void cleanSqlMemcach(String NAME) {
		sqlConfigMemcached.clean(NAME);
	}

	@Override
	public String update(String NAME) {
		return Dao.selectOne("interfaceTemplate.getSQL", NAME);
	}

}

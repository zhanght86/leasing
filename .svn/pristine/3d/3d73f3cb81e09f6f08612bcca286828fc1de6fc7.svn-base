package com.pqsoft.telephonenumberlist.dao;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 将List<String>结构的数据存储成元素1,元素2,元素3...的形式
 *
 * @author 钟林俊
 * @version V1.0 2016-06-23 13:26
 */
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int columnIndex, List<String> dataList, JdbcType jdbcType) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder(dataList.get(0));
        for(int i = 1; i < dataList.size(); i++){
            stringBuilder.append(",").append(dataList.get(i));
        }
        preparedStatement.setString(columnIndex, stringBuilder.toString());
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return getList(resultSet.getString(columnName));
    }

//    @Override
//    public List<String> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
//        return getList(resultSet.getString(columnIndex));
//    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return getList(callableStatement.getString(columnIndex));
    }

    private List<String> getList(String data) throws SQLException {
        if(data == null){
            return null;
        }
        return Arrays.asList(data.split(","));
    }
}

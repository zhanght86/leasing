package com.pqsoft.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * Mybatis工具类
 *
 * @className MybatisUtil
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class MybatisUtil {

    /**
     * 打印mybatis的mapper中的delete语句
     *
     * @author 钟林俊
     * @param className 全类名
     * @param tablePrefix 表名前缀
     * @throws ClassNotFoundException
     */
    public static void printDelete(String className, String tablePrefix) throws ClassNotFoundException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(className), "className为空！");
        String objName = StringUtils.substringAfterLast(className, ".");
        Class<?> aClass = Class.forName(className);
        StringBuilder sb = new StringBuilder("<delete id=\"delete");
        sb.append(objName).append("\" parameterType=\"\">\n").append("\tdelete from ").append(tablePrefix).append("_").append(objName).append("\n").append("\twhere\n</delete>");
        System.out.println(sb.toString());
    }

    /**
     * 打印Mybatis的Mapper中的update语句
     *
     * @author 钟林俊
     * @param className 全类名
     * @param tablePrefix 表名前缀
     * @return update
     * @throws ClassNotFoundException
     */
    public static void printUpdate(String className, String tablePrefix) throws ClassNotFoundException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(className), "className为空！");
        String objName = StringUtils.substringAfterLast(className, ".");
        Class<?> aClass = Class.forName(className);
        StringBuilder sb = new StringBuilder("<update id=\"update" + objName + "\" parameterType=\"" + className + "\">\n");
        sb.append("\tupdate ");
        if(!Strings.isNullOrEmpty(tablePrefix)){
            sb.append(tablePrefix.toUpperCase()).append("_");
        }
        sb.append(objName.toUpperCase()).append("\n\t<set>");
        for(Field field : aClass.getDeclaredFields()){
            String jdbcType = getJdbcType(field.getType().getName());
            if(Strings.isNullOrEmpty(jdbcType)){
               continue;
            }
            String fieldName = field.getName();
            String columnName = com.pqsoft.util.StringUtils.camelToUnderLine(fieldName);
            sb.append("\n\t\t<if test=\"").append(fieldName).append(" != null\">\n\t\t\t").append(columnName).append(" = #{").append(fieldName).append(" , jdbcType=")
                    .append(jdbcType).append("},\n\t\t</if>");
        }
        sb.append("\n\t</set>\n\twhere\n</update>");
        System.out.println(sb.toString());
    }

    private static String getJdbcType(String className) {
        switch (className){
            case "char":
            case "java.lang.Character":
            case "java.lang.String":
                return "VARCHAR";
            case "int":
            case "java.lang.Integer":
            case "byte":
            case "java.lang.Byte":
            case "short":
            case "java.lang.Short":
            case "boolean":
            case "java.lang.Boolean":
                return "INTEGER";
            case "long":
            case "java.lang.Long":
            case "double":
            case "java.lang.Double":
            case "java.math.BigDecimal":
                return "DECIMAL";
            case "java.util.Date":
            case "java.sql.Date":
            case "java.sql.Timestamp":
                return "TIMESTAMP";
            default:
                return "";
        }
    }
}

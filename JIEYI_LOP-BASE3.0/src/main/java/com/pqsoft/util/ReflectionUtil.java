package com.pqsoft.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * 反射工具类
 *
 * @className ReflectionUtil
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class ReflectionUtil {

    /**
     * 通过反射调用指定对象的指定属性的get方法
     *
     * @author 钟林俊
     * @param t 指定对象
     * @param fieldName 属性名(支持关联对象的属性访问，以"."区分)
     * @param <T> 指定对象的类
     * @return 类T的对象t调用指定属性的get方法后的返回值
     */
    public static <T> Object getGetterResult(T t, String fieldName){
        Preconditions.checkNotNull(t, "传入对象为空！");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(fieldName), "属性名称为空！");

        String methodName;

        boolean recursive = StringUtils.contains(fieldName, ".");
        if(recursive) {
            methodName = "get" + StringUtils.capitalize(StringUtils.substringBefore(fieldName, "."));
        } else {
            methodName = "get" + StringUtils.capitalize(fieldName);
        }

        try{
            Method method = t.getClass().getDeclaredMethod(methodName);
            Object obj = method.invoke(t);

            if(recursive){
                return getGetterResult(obj, StringUtils.substringAfter(fieldName, "."));
            }else{
                return obj;
            }

        } catch (IllegalAccessException|InvocationTargetException e){
            throw new RuntimeException(t.getClass().getName() + "" + methodName + "访问失败！" + "\n" + t + "\n" + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(t.getClass().getName() + "" + methodName + "配置错误！" + "\n" + t + "\n" + e.getMessage());
        }
    }

    /**
     * 打印指定类的所有set方法
     * 例：tName.setMethodName();\n
     *
     * @author 钟林俊
     * @param tClass 类对象
     * @param tName set方法的调用者名称
     * @param <T> 泛型
     */
    public static <T> void printSetters(Class<T> tClass, String tName){
        printWithPrefix(tClass, tName, "set");
    }

    /**
     * 打印指定类的所有get方法
     * 例：tName.getMethodName();\n
     *
     * @author 钟林俊
     * @param tClass 类对象
     * @param tName set方法的调用者名称
     * @param <T> 泛型
     */
    public static <T> void printGetters(Class<T> tClass, String tName){
        printWithPrefix(tClass, tName, "get");
    }

    private static <T> void printWithPrefix(Class<T> tClass, String tName, String prefix){
        Preconditions.checkNotNull(tClass, "类对象为空！");
        Method[] methods = tClass.getDeclaredMethods();
        String objName = tName == null? "" : tName;
        String name;
        for(Method method : methods){
            name = method.getName();
            if(name.startsWith(prefix)){
                System.out.println(objName + "." + name + "();");
            }
        }
    }
}

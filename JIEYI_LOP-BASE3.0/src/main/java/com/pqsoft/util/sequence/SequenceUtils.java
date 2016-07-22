package com.pqsoft.util.sequence;
import java.util.HashMap; 
import java.util.Map; 
import java.sql.SQLException; 

/** 
* <b>Note</b>: Java实现的Sequence工具 
*/ 
public class SequenceUtils { 
    private static SequenceUtils _instance = new SequenceUtils(); 
    private Map<String, KeyInfo> keyMap = new HashMap<String, KeyInfo>(20); //Sequence载体容器 
    private static final int POOL_SIZE = 1;      //Sequence值缓存大小 

    /** 
     * 禁止外部实例化 
     */ 
    private SequenceUtils() { 
    } 

    /** 
     * 获取SequenceUtils的单例对象 
     * @return SequenceUtils的单例对象 
     */ 
    public static SequenceUtils getInstance() { 
        return _instance; 
    } 

    /** 
     * 获取下一个Sequence键值 
     * @param keyName Sequence名称 
     * @return 下一个Sequence键值 
     */ 
    public synchronized long nextValue(String keyName,long s_maxkey,boolean recycleFlag) { 
        KeyInfo keyInfo = null; 
        Long keyObject = null; 
        try { 
            if (keyMap.containsKey(keyName)) { 
                keyInfo = keyMap.get(keyName); 
            } else { 
                keyInfo = new KeyInfo(keyName, POOL_SIZE,1,s_maxkey,recycleFlag); 
                keyMap.put(keyName, keyInfo); 
            } 
            keyObject = keyInfo.getNextKey(); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
        return keyObject; 
    } 
}
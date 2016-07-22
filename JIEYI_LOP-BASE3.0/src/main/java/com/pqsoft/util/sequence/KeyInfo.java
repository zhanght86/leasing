package com.pqsoft.util.sequence;
import java.sql.*; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DaoSupport;

import com.pqsoft.action.SysAction;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

/** 
* <b>Note</b>: Sequence载体 
*/ 
public class KeyInfo { 
	/*
	 * CREATE TABLE T_SYS_SEQUENCE
		(
		SEQUENCE_NAME VARCHAR2(200),
		nextvalue NUMBER,
		init_value NUMBER,
		min_value NUMBER,
		max_value NUMBER,
		RECYCLE_flag NUMBER ,
		cache_size INT
		)
	 */
	private String keyName;     //Sequence的名称 
	private long nextKey = 1;       //下一个Sequence值 
	private long init_value = 1;       //初始值 
	private long minKey;        //当前Sequence载体的最小值 
    private long maxKey;        //当前Sequence载体的最大值 
    private int poolSize = 1;       //Sequence值缓存大小 
    
    private int incrementBy = 1;//增长数
    private long s_minKey = 1 ;		//最小值
    private long s_maxKey = 99999999999999999l;//最大值

    private boolean recycleflag = false ;//是否循环

    public KeyInfo(String keyName, int poolSize) throws SQLException { 
        this.poolSize = poolSize; 
        this.keyName = keyName; 
        retrieveFromDB(); 
    } 
    
    public KeyInfo(String keyName, int poolSize,int incrementBy, long s_maxKey,boolean recycleflag) throws SQLException { 
        this.poolSize = poolSize; 
        this.keyName = keyName; 
        this.s_maxKey = s_maxKey; 
        this.incrementBy = incrementBy; 
        this.recycleflag = recycleflag; 
        retrieveFromDB(); 
    } 

    public String getKeyName() { 
        return keyName; 
    } 

    public long getMaxKey() { 
        return maxKey; 
    } 

    public long getMinKey() { 
        return minKey; 
    } 

    public int getPoolSize() { 
        return poolSize; 
    } 

    /** 
     * 获取下一个Sequence值 
     * 
     * @return 下一个Sequence值 
     * @throws SQLException 
     */ 
    public synchronized long getNextKey() throws SQLException { 
        if (nextKey > maxKey) { 
            retrieveFromDB(); 
        } 
        return nextKey++; 
    } 

    /** 
     * 执行Sequence表信息初始化和更新工作 
     * 
     * @throws SQLException 
     */ 
    private void retrieveFromDB() throws SQLException { 
    	Map<String,Object> param = new HashMap<String,Object>();
    	
        //查询数据库 
        List l = Dao.execSelectSql("select * from T_SYS_SEQUENCE t where t.NAME = '"+keyName+"'"); 
        if (l.size()>0) {
        	Map<String,Object> m = (Map<String,Object>)l.get(0);
        	s_minKey =  Long.valueOf(StringUtils.nullToString(m.get("MIN_VALUE")));		//最小值
        	s_maxKey = Long.valueOf(StringUtils.nullToString(m.get("MAX_VALUE")));//最大值
        	recycleflag = StringUtils.nullToString(m.get("MAX_VALUE")).equals("1");//是否循环
        	
    		if(nextKey > s_maxKey){
    			nextKey = Long.valueOf(StringUtils.nullToString(m.get("INIT_VALUE"))); 
    		}else{
    			nextKey = Long.valueOf(StringUtils.nullToString(m.get("NEXTVALUE"))); 
    		}
    		maxKey =  nextKey + poolSize - 1; 
    		minKey = nextKey; 
    		//更新数据库 
//    		System.out.println("更新Sequence最大值！"); 
    		param.put("sql", "update T_SYS_SEQUENCE set nextvalue = "+(maxKey+1)+" where NAME='"+keyName+"'");
    		Dao.update("System.execSelectSql",param);
    		Dao.commit();
    		
    		
    } else { 
//            System.out.println("执行Sequence数据库初始化工作！"); 
            String init_sql = "INSERT INTO T_SYS_SEQUENCE(NAME,nextvalue,init_value,min_value,max_value,RECYCLE_flag,cache_size,incrementBy) VALUES('" 
            	+ keyName + "'," + init_value + "," + nextKey + "," + s_minKey + "," + s_maxKey + "," + (recycleflag ? 1 : 0) + "," + poolSize + "," + incrementBy + ")"; 
            param.put("sql",init_sql);
            Dao.insert("System.execSelectSql",param);
            maxKey = poolSize; 
            minKey = maxKey - poolSize+1; 
            nextKey = minKey; 
            Dao.commit();
            return; 
        } 
    } 
}
 
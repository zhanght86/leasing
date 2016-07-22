//package com.pqsoft.util;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.fusionChart.service.DataReleaseService;
//import com.pqsoft.skyeye.api.Dao;
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 报表中心
// * </p>
// * <p>
// * Description: 数据发布 部署处理 线程类； 实现生成xml保存数据库；
// * </p>
// * <p>
// * 每个数据发布操作启动一个新线程，根据模块、类型执行对应方法生成一张报表
// * 控制数据发布状态（发布中、成功、失败）
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * @author 吴剑东  wujiandongit@163.com 
// * @version 1.0
// */
//public class ReportDeployHandler extends Thread {
//	/** 数据发布详细信息 */
//	private Map<String, Object> map;
//	/** 模块名称 */
//	private String moduleCode;
//	/** 类型名称 */
//	private String cardCode;
//	/** 数据发布ID */
//	private int id;
//	/** 月数据时点 */
//	private String dataTmMonth;
//	/** 年数据时点 */
//	private String dataTmYear;
//	/** 实现类 */
//	private DataReleaseService service ;
//	/** Velocity上下文 */
//	private VelocityContext context;
//	/** 报表生成工具 */
//	private CreateReportUtil util ;
//
//	/**
//	* @brief 数据发布线程类构造函数
//	* @param map 数据发布详细信息
//	*/ 
//	public ReportDeployHandler(Map<String, Object> map) {
//		this.map = map;
//	}
//	/**
//	* run方法，初始化，根据模块、类型执行对应方法生成一张报表，控制数据发布状态（发布中、成功、失败）
//	* @exception Exception
//	*/ 
//	public void run() {
//		try {
//			/** 变量初始化开始  */ 
//
//			moduleCode = (String)map.get("MODULE_CODE");
//			cardCode = (String)map.get("CARD_CODE");
//			id = (Integer)map.get("ID");
//			dataTmMonth = (String)map.get("DATA_TIME");
//			dataTmYear = dataTmMonth.substring(0,4);
//			service = new DataReleaseService();
//			context = new VelocityContext();
//			util = new CreateReportUtil(service,context,dataTmMonth,dataTmYear);
//			/** 变量初始化结束  */ 
//			
//			
//			/** 根据模块、类型执行对应方法生成一张报表  */ 
//
//			/**  生成项目报表-收益分析XML */
//			if("项目报表模块".equals(moduleCode) && "项目收益".equals(cardCode)){
//				this.projectProceeds();
//			}
//			
//			/** 修改状态 1: 发布成功 */
//			map.put("STATE", "1");
//			service.updateStatusByid(map);
//		} catch (Exception e) {
//			//回滚事务
//			Dao.rollback();
//			/** 修改状态 2: 发布失败  */
//			map.put("STATE", "2");
//			service.updateStatusByid(map);
//			e.printStackTrace();
//		}finally {
//			//提交事务
//			Dao.commit();
//		}
//	}
//	/**
//	 * 新增数据发布xml信息
//	 * @param CHART_ID 图表序号，CHART_NAME 图表名称 ，XML_TEXT 图表XML数据
//	 * @exception Exception
//	 * @author 吴剑东  2013-5-17
//	 */
//	public void doAddDataReleaseXML(String CHART_ID,String CHART_NAME,byte[] XML_TEXT){
//		try {
//			Map<String, Object> m = new HashMap<String, Object>();
//			m.put("REPORT_ID", id);
//			m.put("CHART_ID", CHART_ID);
//			m.put("CHART_NAME", CHART_NAME);
//			m.put("XML_TEXT",XML_TEXT);
//			/** 添加数据到数据库  */
//			service.addDataReleaseXML(m);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 生成 项目报表-收益分析 报表所有XML
//	 * @exception Exception
//	 * @author 吴剑东  2013-5-17
//	 */
//	public void projectProceeds()throws Exception{
//		/** 行业、业务项目租金收益五年分析  */
//		Object xml = util.createHYYW5YearProceedS(dataTmYear);
//		doAddDataReleaseXML("1","行业、业务项目租金收益五年分析",xml.toString().getBytes("GB2312"));
//			
//	}
//
//}

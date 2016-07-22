//package com.pqsoft.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.fusionChart.service.DataReleaseService;
//
///**
// * <p>
// * Title: 融资租赁信息系统平台 报表中心
// * </p>
// * <p>
// * Description: 报表生成工具，每个方法生成一个图表；
// * <p>
// * 方法通过数据时点及业务逻辑生成该数据时点对应数据
// * 通过上下文传入对应.vm文件生产XML数据并返回
// * </p>
// * </p>
// * <p>
// * Company: 平强软件
// * </p>
// * @author 吴剑东 2013-5-17  wujiandongit@163.com
// * @version 1.0
// */
//public class CreateReportUtil {
//	/** Velocity上下文 */
//	private VelocityContext context;
//	/** 实现类 */
//	private DataReleaseService service;
//	/** 图表颜色 */
//	private String[] colors ={"BEDAE8","2BCBFF","0796DA","0D61A9","1D3D64"};
//	
//	/** 年集合 */
//	private List<?> yearList;
//	/** 月集合 */
//	private List<?> monthList;
//	/** 数据时点当月 */
//	private String dataTmMonth;
//	/** 数据时点上年当月 */
//	private String lastDataTmMonth;
//	
//	
//	/**
//	* @brief 报表生成工具类构造函数
//	* @param service 实现类， context Velocity上下文
//	*/ 
//	public CreateReportUtil(DataReleaseService service ,VelocityContext context,String dataTmMonth,String dataTmYear){
//		this.service = service;
//		this.context = context;
//		this.dataTmMonth = dataTmMonth;
//		/** 查询前推五年用于XML category */
//		yearList = service.select5Year(dataTmYear);
//		/** 查询前推12月 用于XML category */
//		monthList = service.select12Month(dataTmMonth);
//		/** 上年当月 用于XML category */
//		String[] arr = dataTmMonth.split("-");
//		lastDataTmMonth = (Integer.parseInt(arr[0])-1)+"-"+arr[1];
//		
//	}
//	
//	
//	/**
//	 * 生成项目报表-收益分析-行业、业务项目租金收益五年分析
//	 * @param dataTm 数据时点
//	 * @return XML数据
//	 * @exception Exception
//	 * @author 吴剑东  2013-5-17
//	 */
//	public Object createHYYW5YearProceedS(String dataTm) throws Exception{
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("year",dataTm);
//		map.put("TYPE", "业务类型");
//		/** 查询业务类别 */
////		List<?> list = service.selectYWType(map);
////		
////		/** 遍历类型 */
////		for (int i = 0; i < list.size(); i++) {
////			Map<String, Object> mtypes = (Map<String, Object>) list.get(i);
////			mtypes.put("year",dataTm);
////			mtypes.put("TYPE", "业务类型");
////			mtypes.put("REALTIME_NAME", "利息");
////			/** 根据类型查询前推五年数据 */
////			List<?> typeList = service.selectYWTypeData(mtypes);
////			mtypes.put("typeList", typeList);
////			mtypes.put("color", colors[i]);
////		}
////		
////		context.put("list", list);
//		context.put("yearList", yearList);
//		return VM.html("analysis/biReport/xml/"+"HYYW5YearProceeds.vm", context);
//	}
//	
//	
//	/**
//	 * 生成项目报表-收益分析-月行业租金收益趋势分析
//	 * @param dataTm 数据时点
//	 * @return XML数据
//	 * @exception Exception
//	 * @author 吴剑东  2013-5-17
//	 */
//	public Object createHYMonthProceeds(String dataTm) throws Exception{
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("dataTm", dataTm);
//		map.put("TYPE", "业务类型");
//		
//		/** 查询业务类别 */
//		List<?> list = service.selectYWType(map);
//		/** 遍历类型 */
//		for (int i = 0; i < list.size(); i++) {
//			
//			Map<String, Object> mtypes = (Map<String, Object>) list.get(i);
//			/** 根据类型查询前推12月 数据 */
//			mtypes.put("dataTm", dataTm);
//			mtypes.put("TYPE", "业务类型");
//			mtypes.put("REALTIME_NAME", "利息");
//			mtypes.put("list", list);
//			
//			List<?> typeList = service.selectHYMonthByType(mtypes);
//			mtypes.put("typeList", typeList);
//		}
//		
//		context.put("list", list);
//		context.put("monthList", monthList);
//		return VM.html("analysis/biReport/xml/"+"HYMonthProceeds.vm", context);
//	}
//	
//	/**
//	 * 同一月份,不同业务类型，相同资产级别的数量对比XML
//	 * @param dataTm 数据时点
//	 * 付玉龙
//	 *time2013-6-25下午03:45:57
//	 */
//	public Object createHYYWMonthTQ(String dataTm) throws Exception{
//		
//		/** 查询业务类别*/
//		List<Map<String,Object>> businessTypeList = service.selectBusinessType();
//		for (int i = 0; i < businessTypeList.size(); i++) {
//			Map<String,Object> businessTypeMap = businessTypeList.get(i);
//			businessTypeMap.put("YEAR_MONTH", dataTm);
//			/** 查询业务类别下的资产条数*/
//			List<Map<String,Object>> assetsCountList = service.selectAssetsCount(businessTypeMap);
//			businessTypeMap.put("ASSERS_COUNT", assetsCountList);
//		}
//		/** 查询资产分类类型*/
//		List<Map<String,Object>> assetsTypeList = service.selectAssetsType();
//		context.put("assetsTypeList", assetsTypeList);
//		context.put("businessTypeList", businessTypeList);
//		context.put("dataTm", dataTm);
//		return VM.html("analysis/biReport/xml/"+"differentBusinessSameAssets.vm", context);
//	}
//	
//	/**
//	 * 每级资产随时间的数量变化XML
//	 * @param dataTm 数据时点
//	 * 付玉龙
//	 *time2013-6-25下午03:45:57
//	 */
//	@SuppressWarnings("unchecked")
//	public Object createAssetsCountMonthTQ(String dataTm) throws Exception{
//		
//		/** 查询月份*/
//		List<?> monthList = service.select12Month(dataTm);
//		/** 查询资产分类类型*/
//		List<Map<String,Object>> assetsTypeList = service.selectAssetsType();
//		for (int i = 0; i < assetsTypeList.size(); i++) {
//			Map<String,Object> assetsTypeMap = assetsTypeList.get(i);
//			List<Map<String,Object>> assetsCountList = new ArrayList<Map<String,Object>>();
//			for (int j = 0; j < monthList.size(); j++) {
//				Map<String,Object> monthMap = (Map<String,Object>) monthList.get(j);
//				assetsTypeMap.put("MONTHS", monthMap.get("MONTHS"));
////				assetsCountList.add(service.selectLevelCount(assetsTypeMap));
//			}
//			assetsTypeMap.put("ASSETS_COUNTS",assetsCountList);
//		}
//		context.put("monthList", monthList);
//		context.put("assetsTypeList", assetsTypeList);
//		return VM.html("analysis/biReport/xml/"+"assetsTimeCount.vm", context);
//	}
//	
//	/**
//	 * 不同资产级别的数量对比XML
//	 * @param dataTm 数据时点
//	 * 付玉龙
//	 *time2013-6-25下午03:45:57
//	 */
//	public Object createDifferentAssetsCountMonthTQ(String dataTm) throws Exception{
//		
//		/** 查询不同级别资产数据条数*/
//		List<Map<String,Object>> assetsCountList = service.selectDifferentAssetsCount(dataTm);
//		/** 查询资产分类类型*/
//		List<Map<String,Object>> assetsTypeList = service.selectAssetsType();
//		context.put("assetsTypeList", assetsTypeList);
//		context.put("assetsCountList", assetsCountList);
//		context.put("dataTm", dataTm);
//		return VM.html("analysis/biReport/xml/"+"differentAssetsCount.vm", context);
//	}
//	
//	/**
//	 * 承租人资产级别随时间的变化XML
//	 * @param dataTm 数据时点
//	 * @param lesseeCode 承租人编号
//	 * 付玉龙
//	 *time2013-6-25下午03:45:57
//	 */
//	@SuppressWarnings("unchecked")
//	public Object createLesseeAssetsCountMonthTQ(String dataTm,String RENTER_CODE,String RENTER_NAME) throws Exception{
//		Map<String,Object> param = new HashMap<String, Object>();
//		param.put("dataTm", dataTm);
//		param.put("RENTER_CODE", RENTER_CODE);
//		/** 查询月份*/
//		List<?> monthList = service.select12Month(dataTm);
//		/** 根据承租人查支付表编号*/
//		List<Map<String,Object>> payCodeList = service.selectPayCode(param);
//		for (int i = 0; i < payCodeList.size(); i++) {
//			List<Map<String,Object>> assetsTypeList = new ArrayList<Map<String,Object>>();
//			Map<String,Object> payCodeMap = payCodeList.get(i);
//			for (int j = 0; j < monthList.size(); j++) {
//				Map<String,Object> monthMap = (Map<String,Object>) monthList.get(j);
//				payCodeMap.put("dataTm", monthMap.get("MONTHS"));
////				assetsTypeList.add(service.selectAssetsLevel(payCodeMap));
//			}
//			payCodeMap.put("assetsTypeList", assetsTypeList);
//		}
//		context.put("monthList", monthList);
//		context.put("payCodeList", payCodeList);
//		context.put("RENTER_NAME", RENTER_NAME);
//		return VM.html("analysis/biReport/xml/"+"lesseeAssetsCount.vm", context);
//	}
//	
//	/**
//	 * 查所有承租人
//	 * 付玉龙
//	 *time2013-6-25下午03:45:57
//	 */
//	public List<Map<String,Object>> selectLessee(String dataTm) throws Exception{
//		return service.selectLessee(dataTm);
//	}
//	
//	
//}

package com.pqsoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.pqsoft.skyeye.api.Dao;

public class AcrobatENG {
	// 日志服务
	final static Logger logger = Logger.getLogger("AcrobatENG");

	public static <T> AcrobatENG newInstance() {
		return new AcrobatENG();
	}

	/**
	 * 模仿Velocity制作的PDF表单填充工具
	 * 
	 * @param pdfFormFile
	 * @param acrobatContext
	 * @param output
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void mergeAcrobat(InputStream input, HashMap acrobatContext, OutputStream output) throws Exception {
		if (acrobatContext == null) {
			logger.error("传入的待填充参数为空");
			return;
		}
		logger.debug("AcrobatENG rendering..");

		PdfReader reader = new PdfReader(input);
		PdfStamper stamp = new PdfStamper(reader, output);
		AcroFields afields = stamp.getAcroFields();
		List<Map<String, Object>> list = Dao.selectList("SRV_AIF.getAllSql");
		Map<String, Object> m = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String SQL = "";
		Map<String, Object> newParam = new HashMap<String, Object>();
		newParam.put("PROJECT_ID", acrobatContext.get("PROJECT_ID"));
		for (Iterator iterator = afields.getFields().keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (key == null || key.length() <= 0) {
				continue;
			}
			// 填充字段
			for (int j = 0; j < list.size(); j++) {
				m = list.get(j);
				if (key.equals(m.get("NAME").toString().trim())) {
					Iterator iter = newParam.entrySet().iterator();
					while (iter.hasNext()) {
						try {
							Map.Entry entry = (Map.Entry) iter.next();
							SQL = m.get("SQL").toString().replace("#{" + entry.getKey() + "}", entry.getValue().toString());
							map.put("SQL_TEXT", SQL);
							List sqlValueS = Dao.selectList("runInterfaceTemplate.getSqlValue", map);
							String sqlValue = "";
							if(sqlValueS != null && sqlValueS.size() > 0){
								if(sqlValueS.size() == 1){
									Map<String,Object> map1 = (Map<String, Object>) sqlValueS.get(0);
									Iterator k = map1.entrySet().iterator();
									while (k.hasNext()) {
										Map.Entry me = (Map.Entry) k.next();
										if(me.getValue() != null && !me.getValue().toString().equals("")){
											sqlValue = me.getValue().toString(); 
											afields.setField(key, sqlValue);
										}
									}
								}else{
									for(int i=0;i<sqlValueS.size();i++){
										Map<String,Object> map1 = (Map<String, Object>) sqlValueS.get(i);
										Iterator k = map1.entrySet().iterator();
										while (k.hasNext()) {
											Map.Entry me = (Map.Entry) k.next();
											if(me.getValue() != null && !me.getValue().toString().equals("")){
												sqlValue = me.getValue().toString(); 
												afields.setField(key + i, sqlValue);
											}
										}
									}
								}
							}
						} catch (Exception e) {
							logger.error(e, e);
						}
					}
				}
			}
		}

		// 关闭输出
		stamp.setFormFlattening(true);
		stamp.close();
	}

	/**
	 * 模仿Velocity制作的PDF表单填充工具
	 * 
	 * @param inputPath
	 * @param acrobatContext
	 * @param outputPath
	 * @throws Exception
	 */
	public void mergeAcrobat(InputStream input, HashMap<String, Object> acrobatContext, String outputPath) throws Exception {
		try {
			FileOutputStream output = new FileOutputStream(new File(outputPath));
			mergeAcrobat(input, acrobatContext, output);
			output.close();
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-004: 准备PDF输出流时出错。参考：" + e.getMessage());
		} catch (IOException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-004: 关闭PDF输出流时出错。参考：" + e.getMessage());
		}
	}

	/**
	 * 模仿Velocity制作的PDF表单填充工具
	 * 
	 * @param inputPath
	 * @param AcrobatContext
	 * @param output
	 * @throws Exception
	 */
	public void mergeAcrobat(String inputPath, HashMap<String, Object> acrobatContext, OutputStream output) throws Exception {
		try {
			FileInputStream input = new FileInputStream(new File(inputPath));
			mergeAcrobat(input, acrobatContext, output);
			input.close();
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-003: 准备PDF输入流时出错。参考：" + e.getMessage());
		} catch (IOException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-003: 关闭PDF输入流时出错。参考：" + e.getMessage());
		}
	}

	/**
	 * 模仿Velocity制作的PDF表单填充工具
	 * 
	 * @param inputPath
	 * @param AcrobatContext
	 * @param outputPath
	 * @throws Exception
	 */
	public static void mergeAcrobat(String inputPath, HashMap<String, Object> acrobatContext, String outputPath) throws Exception {
		try {
			FileInputStream input = new FileInputStream(new File(inputPath));
			File of = new File(outputPath);
			if (!of.getParentFile().exists()) {
				of.getParentFile().mkdirs();
			}
			FileOutputStream output = new FileOutputStream(new File(outputPath));
			mergeAcrobat(input, acrobatContext, output);
			input.close();
			output.close();
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-004: 准备PDF输入/输出流时出错。参考：" + e.getMessage());
		} catch (IOException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-004: 关闭PDF输入/输出流时出错。参考：" + e.getMessage());
		}
	}

	/**
	 * 提取PDF有单中的域名称
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> parseAcrobat(InputStream input) throws Exception {
		List<String> acrobatContext = new ArrayList<String>();
		try {
			PdfReader reader = new PdfReader(input);
			PdfStamper stamp = new PdfStamper(reader, null);
			AcroFields afields = stamp.getAcroFields();

			for (Iterator iterator = afields.getFields().keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (key == null || key.length() <= 0) {
					continue;
				}
				// 取表单中的字段
				acrobatContext.add(key);
			}

		} catch (IOException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-005: 处理PDF表单时错误，可能是读取的文件不存在或输出流异常。参考：" + e.getMessage());
		} catch (DocumentException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-006: 填充PDF表单时发生异常。参考：" + e.getMessage());
		}
		return acrobatContext;
	}

	/**
	 * 提取PDF有单中的域名称
	 * 
	 * @param inputPath
	 * @return
	 * @throws Exception
	 */
	public List<String> parseAcrobat(String inputPath) throws Exception {
		try {

			FileInputStream input = new FileInputStream(new File(inputPath));
			return parseAcrobat(input);
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new Exception("AcrobatENG-007: 准备PDF输入流时出错。参考：" + e.getMessage());
		}
	}

	/**
	 * 根据传入的策略名（通常是文件名，会被大写处理），读取待填充数据
	 * 
	 * @param key
	 * @param params
	 *            传入的查询，用keyset替换查询SQL中的关键字${key}
	 * @return
	 */
	public static HashMap<String, Object> getFills(String key, Map<String, Object> params) {
		HashMap<String, Object> fills = new HashMap<String, Object>();
		fills.put("填充策略", key);
		// 准备填充策略
		System.out.println("---------------key=" + key);
		List<HashMap<String, Object>> lstSQL = Dao.selectList("SRV_AIF.getSQL", key.toUpperCase());
		for (HashMap<String, Object> mSQL : lstSQL) {
			logger.info(mSQL.get("SQL"));
			if (mSQL.get("SQL") == null) {
				fills.put("错误信息", "填充SQL为空");
				logger.error("\tSQL不能为空");
				// return fills;
				continue;
			}

			// 准备待执行的SQL，用params中的key进行替代${key}
			HashMap<String, Object> map = new HashMap<String, Object>();
			String sql = mSQL.get("SQL").toString();
			map.put("sql", sql);
			for (Object mk : params.keySet()) {
				if (mk != null) {
					String akey = mk.toString();
					String aval = params.get(akey).toString();
					if (sql.contains("{" + akey + "}")) {
						sql = sql.replaceAll("\\{" + akey + "\\}", aval);
						map.remove("sql");
						map.put("sql", sql);
					}
				}
			}
			if ("".equals(map.get("sql"))) continue;
			try {
				List<HashMap<String, Object>> lstFillData = Dao.selectList("SRV_AIF.execute", map);
				if (lstFillData.size() > 0) {
					logger.debug("\t\t获取结果集，记录数:" + lstFillData.size());

					int iCnt = 0;
					for (HashMap<String, Object> fillData : lstFillData) {
						logger.debug("\t\t处理第" + iCnt + "条记录:" + fillData);
						logger.debug("\t\t\t准备进行SQL->PDF域名称转换");
						if (mSQL.get("SQL_FIELD") == null) {
							logger.error("FIELD_SQL不能为空，原始名称为：" + mSQL.get("FORMLABEL"));
							continue;
						}

						// 如果FIELD_PDF为空，则用FIELD_SQL进行同名填充
						if (mSQL.get("PDF_FIELD") == null) {
							mSQL.put("PDF_FIELD", mSQL.get("SQL_FIELD"));
						}

						String field_sql = mSQL.get("SQL_FIELD").toString().toUpperCase();
						String field_pdf = mSQL.get("PDF_FIELD").toString().toLowerCase();

						logger.debug("\t\t\t待转换域： " + field_sql + " -> " + field_pdf + "\t");
						if (fillData != null && !"null".equals(fillData) && !fillData.isEmpty() && fillData.containsKey(field_sql)) {
							String[] lstFields = field_pdf.trim().toUpperCase().split(",");
							if (lstFields.length > 0) {
								String strPDFFieldName = lstFields[0];
								String data = fillData.get(field_sql).toString();
								// 处理多条记录的结果集
								if (iCnt > 0) {
									strPDFFieldName = strPDFFieldName + iCnt;
								}
								logger.debug("\t\t\t\t填充值： " + field_sql + ":" + data + " -> PDF域" + strPDFFieldName);
								fills.put(strPDFFieldName, data);
							} else {
								logger.error("\t\t\t\t转换失败，未找到要填充的PDF表单域");
							}
						}
						iCnt++;
					}
				} else {
					logger.error("查询未获取到任何数据，查询参数：" + map);
					continue;
				}
				logger.debug("lstFillData=" + lstFillData);
			} catch (Exception e) {

				logger.debug(sql + "参数-----" + map);
			}

		}
		return fills;
	}

	// public static void main(String[] args) throws Exception, IOException {
	// String dir =
	// "/mmVolume/mmHome/Documents/王淼的工作/2012.03.04@合同管理模板化/己转化完成的PDF/";
	// String pdfRead = dir + "融资租赁合同（修定20111202）.pdf";
	// String pdfWrite = dir + "t1.pdf";
	// FileInputStream input = new FileInputStream(new File(pdfRead));
	// FileOutputStream output = new FileOutputStream(new File(pdfWrite));
	//
	// logger.info(AcrobatENG.newInstance().parseAcrobat(pdfRead));
	//
	// HashMap fills = new HashMap();
	// fills.put("姓名", "张三");
	// fills.put("项目名称", "自动填充的项目名称");
	// fills.put("电话", "13671234507");
	// fills.put("地址", "向往密云，途中");
	// fills.put("起租额", "5000万");
	//
	// // 注意不要读了2次流
	// AcrobatENG.newInstance().mergeAcrobat(input, fills, output);
	//
	// }
}

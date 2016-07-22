package com.pqsoft.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CreateXml {
	public static String convertStatus(String str) {
		String s = null;
		if ("正常".equals(str)) {
			s = "ZHENGCHANG";
		}
		if ("关注".equals(str)) {
			s = "GUANZHU";
		}
		if ("次级".equals(str)) {
			s = "CIJI";
		}
		if ("可疑".equals(str)) {
			s = "KEYI";
		}
		if ("损失".equals(str)) {
			s = "SUNSHI";
		}
		return s;
	}

	/**
	 * 写入xml文件
	 * 
	 * @param doc
	 * @param path
	 * @throws IOException
	 */
	public static void writeXml(Document doc, String path) throws IOException {
		OutputFormat out = OutputFormat.createPrettyPrint();
		out.setEncoding("GBK");
		out.setIndent("    ");
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path), out);
		xmlWriter.write(doc);
		xmlWriter.close();
	}

	/**
	 * R0 创建 已收/未收 租金 xml
	 * 
	 * @param map
	 * @return Document
	 */
	@SuppressWarnings("unchecked")
	public static Document createYswsXml(Map map) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");

		root.addAttribute("caption", "租金");
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "1");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		doc.setRootElement(root);

		root.addElement("set").addAttribute("label", "已收租金").addAttribute(
				"value", String.valueOf(map.get("yishou"))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "未收租金").addAttribute(
				"value", String.valueOf(map.get("weishou"))).addAttribute(
				"isSliced", "0");

		return doc;
	}
	/**
	 * R0 创建 未还款原因 xml
	 * 
	 * @param map
	 * @return Document
	 */
	@SuppressWarnings("unchecked")
	public static Document createStatisticsXml(Map map) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");

		root.addAttribute("caption", "未还款原因统计");
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "1");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		doc.setRootElement(root);
		
		List<Object> list=(List<Object>)map.get("list");
		for (Object object : list) {
			Map<String,Object> m=(Map<String,Object>)object;
			root.addElement("set").addAttribute("label", m.get("FLAG").toString()).addAttribute(
					"value", String.valueOf(map.get("statistics"+m.get("CODE")))).addAttribute(
					"isSliced", "0");
		}

		return doc;
	}
	/**
	 * R1创建 催收结果 xml
	 * 
	 * @param map
	 * @return Document
	 */
	@SuppressWarnings("unchecked")
	public static Document createResultStatisticsXml(Map map) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");

		root.addAttribute("caption", "催收类型统计");
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "1");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		doc.setRootElement(root);
		
		/*List<Object> list=(List<Object>)map.get("list");
		for (Object object : list) {
			Map<String,Object> m=(Map<String,Object>)object;
			root.addElement("set").addAttribute("label", m.get("FLAG").toString()).addAttribute(
					"value", String.valueOf(map.get("statistics"+m.get("CODE")))).addAttribute(
					"isSliced", "0");
		}*/
		root.addElement("set").addAttribute("label", "打电话").addAttribute(
				"value", String.valueOf(map.get("statistics"+1))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "上门").addAttribute(
				"value", String.valueOf(map.get("statistics"+0))).addAttribute(
				"isSliced", "0");
		return doc;
	}
	/**
	 * R0 创建 应收租金构成 xml
	 * 
	 * @param map
	 * @return Document
	 */
	@SuppressWarnings("unchecked")
	public static Document createYingshouXml(Map map) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");

		root.addAttribute("caption", "应收租金");
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "1");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		doc.setRootElement(root);

		root.addElement("set").addAttribute("label", "应收租金").addAttribute(
				"value", String.valueOf(map.get("weishou"))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "收入").addAttribute(
				"value", String.valueOf(map.get("shouru"))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "逾期罚息").addAttribute(
				"value", String.valueOf(map.get("yuqifaxi"))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "营业税").addAttribute(
				"value", String.valueOf(map.get("yingyeshui"))).addAttribute(
				"isSliced", "0");

		return doc;
	}

	/**
	 * R0 创建 分公司 xml
	 * 
	 * @param map
	 * @return Document
	 */
	@SuppressWarnings("unchecked")
	public static Document createYinshouShiyebuXml(List decpNameList,
			List weishouList) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");

		root.addAttribute("caption", "应收租金");
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "0");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		root.addAttribute("showNames", "0");
		doc.setRootElement(root);

		for (int i = 0; i < decpNameList.size(); i++) {
			root.addElement("set").addAttribute("label",
					String.valueOf(decpNameList.get(i))).addAttribute("value",
					String.valueOf(weishouList.get(i))).addAttribute(
					"isSliced", "0");
		}
		return doc;
	}

	/**
	 * R0 创建 应收租金构成 xml
	 * 
	 * @param map
	 * @return Document
	 */
	@SuppressWarnings("unchecked")
	public static Document createZongjineXml(Map map) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");

		root.addAttribute("caption", "五级分类(总)--金额");
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "1");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		doc.setRootElement(root);

		root.addElement("set").addAttribute("label", "正常").addAttribute(
				"value", String.valueOf(map.get("zhengchang"))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "关注").addAttribute(
				"color", "E6E61A").addAttribute("value",
				String.valueOf(map.get("guanzhu"))).addAttribute("isSliced",
				"0");
		root.addElement("set").addAttribute("label", "次级").addAttribute(
				"color", "E6941A").addAttribute("value",
				String.valueOf(map.get("ciji"))).addAttribute("isSliced", "0");
		root.addElement("set").addAttribute("label", "可疑").addAttribute(
				"color", "DD2248").addAttribute("value",
				String.valueOf(map.get("keyi"))).addAttribute("isSliced", "1");
		root.addElement("set").addAttribute("label", "损失").addAttribute(
				"color", "111111").addAttribute("value",
				String.valueOf(map.get("sunshi")))
				.addAttribute("isSliced", "1");

		return doc;
	}

	/**
	 * R1各个分公司各种状态
	 * 
	 * @param name
	 * @param list
	 * @param status
	 * @return
	 */
	public static Document createR1AssetChangeCount(String name,
			List<Map> list, String status) {
		Document doc = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("chart");
		root.addAttribute("caption", name);
		root.addAttribute("subcaption", String.valueOf(list.get(0)
				.get("GONGSI")));
		root.addAttribute("lineThickness", "1");
		root.addAttribute("showValues", "0");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("anchorRadius", "0");
		root.addAttribute("divLineAlpha", "20");
		root.addAttribute("divLineColor", "CC3300");
		root.addAttribute("divLineIsDashed", "1");
		root.addAttribute("showAlternateHGridColor", "1");
		root.addAttribute("alternateHGridAlpha", "5");
		root.addAttribute("alternateHGridColor", "CC3300");
		root.addAttribute("shadowAlpha", "40");
		root.addAttribute("labelStep", "1");
		root.addAttribute("numvdivlines", "5");
		root.addAttribute("chartRightMargin", "35");
		root.addAttribute("bgColor", "FFFFFF,CC3300");
		root.addAttribute("bgAngle", "270");
		root.addAttribute("bgAlpha", "10,10");
		doc.setRootElement(root);

		String s = convertStatus(status);
		Element categories = root.addElement("categories");
		for (Map map : list) {
			categories.addElement("category").addAttribute("label",
					String.valueOf(map.get("SHIJIAN")));
		}

		Element dataset = root.addElement("dataset").addAttribute("seriesName",
				status).addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset.addElement("set").addAttribute("value",
					String.valueOf(map.get(s)));
		}

		Element styles = root.addElement("styles");
		Element definition = styles.addElement("definition");
		definition.addElement("style").addAttribute("name", "CaptionFont")
				.addAttribute("type", "font").addAttribute("size", "12");
		Element application = styles.addElement("application");
		application.addElement("apply").addAttribute("toObject", "CAPTION")
				.addAttribute("styles", "CaptionFont");
		application.addElement("apply").addAttribute("toObject", "SUBCAPTION")
				.addAttribute("styles", "CaptionFont");

		return doc;
	}

	/**
	 * 全部状态，全部公司
	 * 
	 * @param name
	 * @param list
	 * @return
	 */
	public static Document createR1AssetChangeAllCount(String name,
			List<Map> list) {
		Document doc = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("chart");
		root.addAttribute("caption", name);
		root.addAttribute("subcaption", String.valueOf(list.get(0)
				.get("GONGSI")));
		root.addAttribute("lineThickness", "1");
		root.addAttribute("showValues", "0");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("anchorRadius", "0");
		root.addAttribute("divLineAlpha", "20");
		root.addAttribute("divLineColor", "CC3300");
		root.addAttribute("divLineIsDashed", "1");
		root.addAttribute("showAlternateHGridColor", "1");
		root.addAttribute("alternateHGridAlpha", "5");
		root.addAttribute("alternateHGridColor", "CC3300");
		root.addAttribute("shadowAlpha", "40");
		root.addAttribute("labelStep", "1");
		root.addAttribute("numvdivlines", "5");
		root.addAttribute("chartRightMargin", "35");
		root.addAttribute("bgColor", "FFFFFF,CC3300");
		root.addAttribute("bgAngle", "270");
		root.addAttribute("bgAlpha", "10,10");
		doc.setRootElement(root);

		Element categories = root.addElement("categories");
		for (Map map : list) {
			categories.addElement("category").addAttribute("label",
					String.valueOf(map.get("SHIJIAN")));
		}

		Element dataset1 = root.addElement("dataset").addAttribute(
				"seriesName", "正常").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset1.addElement("set").addAttribute("value",
					String.valueOf(map.get("ZHENGCHANG")));
		}
		Element dataset2 = root.addElement("dataset").addAttribute(
				"seriesName", "关注").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset2.addElement("set").addAttribute("value",
					String.valueOf(map.get("GUANZHU")));
		}
		Element dataset3 = root.addElement("dataset").addAttribute(
				"seriesName", "次级").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset3.addElement("set").addAttribute("value",
					String.valueOf(map.get("CIJI")));
		}
		Element dataset4 = root.addElement("dataset").addAttribute(
				"seriesName", "可疑").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset4.addElement("set").addAttribute("value",
					String.valueOf(map.get("KEYI")));
		}
		Element dataset5 = root.addElement("dataset").addAttribute(
				"seriesName", "损失").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset5.addElement("set").addAttribute("value",
					String.valueOf(map.get("SUNSHI")));
		}

		Element styles = root.addElement("styles");
		Element definition = styles.addElement("definition");
		definition.addElement("style").addAttribute("name", "CaptionFont")
				.addAttribute("type", "font").addAttribute("size", "12");
		Element application = styles.addElement("application");
		application.addElement("apply").addAttribute("toObject", "CAPTION")
				.addAttribute("styles", "CaptionFont");
		application.addElement("apply").addAttribute("toObject", "SUBCAPTION")
				.addAttribute("styles", "CaptionFont");

		return doc;
	}

	public static Document createR1AssetMadeAllCount(String name,
			String subname, List<Map> list) {
		Document doc = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("chart");
		root.addAttribute("caption", name);
		root.addAttribute("subcaption", subname);
		root.addAttribute("palette", "1");
		root.addAttribute("shownames", "1");
		root.addAttribute("showLegend", "0");
		root.addAttribute("showvalues", "0");
		root.addAttribute("showSum", "1");
		root.addAttribute("yAxisMaxValue", "100");
		root.addAttribute("decimals", "0");
		root.addAttribute("overlapColumns", "0");
		doc.setRootElement(root);

		Element categories = root.addElement("categories");

		Element dataset1 = root.addElement("dataset").addAttribute(
				"seriesName", "正常").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset1.addElement("set").addAttribute("value",
					String.valueOf(map.get("正常")));
		}
		Element dataset2 = root.addElement("dataset").addAttribute(
				"seriesName", "关注").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset2.addElement("set").addAttribute("color", "E6E61A")
					.addAttribute("value", String.valueOf(map.get("关注")));
		}
		Element dataset3 = root.addElement("dataset").addAttribute(
				"seriesName", "次级").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset3.addElement("set").addAttribute("color", "E6941A")
					.addAttribute("value", String.valueOf(map.get("次级")));
		}
		Element dataset4 = root.addElement("dataset").addAttribute(
				"seriesName", "可疑").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset4.addElement("set").addAttribute("color", "DD2248")
					.addAttribute("value", String.valueOf(map.get("可疑")));
		}
		Element dataset5 = root.addElement("dataset").addAttribute(
				"seriesName", "损失").addAttribute("anchorBorderColor", "1D8BD1")
				.addAttribute("anchorBgColor", "1D8BD1");
		for (Map map : list) {
			dataset5.addElement("set").addAttribute("color", "111111")
					.addAttribute("value", String.valueOf(map.get("损失")));
		}

		return doc;
	}

	/**
	 * 创建R2
	 * 
	 * @param map
	 * @return
	 */
	public static Document createR2(Map map, String name) {
		Document doc = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("chart");
		root.addAttribute("caption", name);
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "1");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("bgAngle", "360");
		root.addAttribute("showBorder", "1");
		root.addAttribute("startingAngle", "70");
		root.addAttribute("showValues", "0");
		root.addAttribute("showNames", "0");
		doc.setRootElement(root);

		root.addElement("set").addAttribute("label", "正常").addAttribute(
				"value", String.valueOf(map.get("正常"))).addAttribute(
				"isSliced", "0");
		root.addElement("set").addAttribute("label", "关注").addAttribute(
				"color", "E6E61A").addAttribute("value",
				String.valueOf(map.get("关注"))).addAttribute("isSliced", "0");
		root.addElement("set").addAttribute("label", "次级").addAttribute(
				"color", "E6941A").addAttribute("value",
				String.valueOf(map.get("次级"))).addAttribute("isSliced", "0");
		root.addElement("set").addAttribute("label", "可疑").addAttribute(
				"color", "DD2248").addAttribute("value",
				String.valueOf(map.get("可疑"))).addAttribute("isSliced", "1");
		root.addElement("set").addAttribute("label", "损失").addAttribute(
				"color", "111111").addAttribute("value",
				String.valueOf(map.get("损失"))).addAttribute("isSliced", "1");

		return doc;
	}

	/**
	 * R3
	 * 
	 * @param name
	 * @param list
	 * @param status
	 * @return
	 */
	public static Document createR3(List<Map> list, String status) {
		Document doc = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("chart");
		root.addAttribute("caption", status);
		root.addAttribute("palette", "4");
		root.addAttribute("decimals", "0");
		root.addAttribute("enableSmartLabels", "0");
		root.addAttribute("showValues", "0");
		root.addAttribute("showNames", "0");
		root.addAttribute("enableRotation", "0");
		root.addAttribute("bgColor", "FFFFFF,FFFFFF");
		root.addAttribute("bgAlpha", "40,100");
		root.addAttribute("bgRatio", "0,100");
		root.addAttribute("showBorder", "1");
		doc.setRootElement(root);

		for (Map map : list) {
			if (status.equals("损失")) {
				root.addElement("set").addAttribute("label",
						String.valueOf(map.get("公司"))).addAttribute("value",
						String.valueOf(1)).addAttribute("isSliced", "0");
			} else {
				root.addElement("set").addAttribute("label",
						String.valueOf(map.get("公司"))).addAttribute("value",
						String.valueOf(map.get(status))).addAttribute(
						"isSliced", "0");
			}
		}

		return doc;
	}

}

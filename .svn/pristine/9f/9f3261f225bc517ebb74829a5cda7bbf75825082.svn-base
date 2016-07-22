package com.pqsoft.bjca.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.Log;

import cn.org.bjca.cloud.model.CloudUnitSignInfo;
import cn.org.bjca.common.model.AnySignOutput;
import cn.org.bjca.common.model.DocumentExtType;
import cn.org.bjca.common.model.KWRuleInfo;
import cn.org.bjca.common.model.UnitSignInfo;
import cn.org.bjca.openservice.oasis.OASISClient;
import cn.org.bjca.openservice.oasis.config.ClientConfiguration;

public class OASClient {
	private static final String ACCESS_ID = "81d02d1d5f3cc9b9cdac7b89b41e856a";
	private static final String ACCESS_KEY = "Z3Sn1tePqeM=";
	private static final String BUSS_ChANNEL = "30010135";
	private static final String ORGAN_APP_NAME = "B30010135";
//	private static final String OASIS_ENDPOINT = "http://oas.bjca.org.cn:18001/AnySign_HttpServer/services/";
	private static final String OASIS_ENDPOINT = "http://60.247.77.102:11214/AnySign_HttpServer/services/";
	private static OASISClient client;
	static {
		init();
	}

	/**
	 * @param args
	 */
	public static void init() {
		ClientConfiguration config = new ClientConfiguration();
		client = new OASISClient(OASIS_ENDPOINT, ACCESS_ID, ACCESS_KEY, config);
	}

	/**
	 * @param srcPath
	 *            原PDF文件位置
	 * @param targetPath
	 *            盖章后PDF文件位置
	 * @param text
	 *            盖章初（按页面文本定位）
	 * @param imgPath
	 *            签章图片路径（必须为*.gif格式）
	 * @throws Exception
	 */
	public static void execPdf(String srcPath, String targetPath, String text, String imgPath) throws Exception {
		Log.debug(srcPath);
		Log.debug(targetPath);
		Log.debug(text);
		Log.debug(imgPath);
		CloudUnitSignInfo cloudUnitSignInfo = new CloudUnitSignInfo();
		/**
		 * PDF生成类
		 */
		cloudUnitSignInfo.setDocContent(FileUtils.readFileToByteArray(new File(srcPath)));
		cloudUnitSignInfo.setDocumentExtType(DocumentExtType.PDF);
		/**
		 * 附件 合成PDF图片及证据
		 */
		cloudUnitSignInfo.setUnitSignInfoList(getUnitSignInfoLS(text, imgPath));
		cloudUnitSignInfo.setSavaSignDoc(false);
		AnySignOutput anySignOutput = client.excuteGetUnitSignPDF(cloudUnitSignInfo);
		System.out.println(anySignOutput.getStatusId());
		if (anySignOutput.getStatusId() == 0) {
			File targetFile = new File(targetPath);
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}
			FileCopyUtils.copy(anySignOutput.getPDFContent(), targetFile);
		}

	}

	private static List<UnitSignInfo> getUnitSignInfoLS(String text, String imgPath) throws IOException {
		List<UnitSignInfo> unitSignInfoLS = new ArrayList<UnitSignInfo>();
		UnitSignInfo unitSignInfo = new UnitSignInfo();
		KWRuleInfo kwRuleInfoUnit = new KWRuleInfo();
		kwRuleInfoUnit.setKW(text);
		kwRuleInfoUnit.setKWOffset("0");
		kwRuleInfoUnit.setKWPos("1");
		kwRuleInfoUnit.setPageno("1");
		unitSignInfo.setKwRuleInfo(kwRuleInfoUnit);
		unitSignInfo.setOrganAppName(ORGAN_APP_NAME);
		unitSignInfo.setSignRuleType("0");
		unitSignInfo.setSignImage(FileUtils.readFileToByteArray(new File(imgPath)));
		unitSignInfoLS.add(unitSignInfo);
		return unitSignInfoLS;
	}
}

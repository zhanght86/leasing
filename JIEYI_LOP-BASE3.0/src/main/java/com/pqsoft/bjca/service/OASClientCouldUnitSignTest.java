package com.pqsoft.bjca.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import cn.org.bjca.cloud.model.CloudUnitSignInfo;
import cn.org.bjca.common.model.AnySignOutput;
import cn.org.bjca.common.model.DocumentExtType;
import cn.org.bjca.common.model.KWRuleInfo;
import cn.org.bjca.common.model.UnitSignInfo;
import cn.org.bjca.openservice.oasis.OASISClient;
import cn.org.bjca.openservice.oasis.config.ClientConfiguration;

public class OASClientCouldUnitSignTest {
	private static final String ACCESS_ID = "";
	private static final String ACCESS_KEY = "";
	private static final String BUSS_ChANNEL = "20000";
	private static final String OASIS_ENDPOINT = "http://60.247.77.109:11214/AnySign_HttpServer/services/";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		sendMessage();
	}

	private static void sendMessage() throws Exception {

		ClientConfiguration config = new ClientConfiguration();

		OASISClient client = new OASISClient(OASIS_ENDPOINT, ACCESS_ID, ACCESS_KEY, config);

		System.out.println("提交P2P REQ...");

		excuteUnitSign(client);

	}

	public static void excuteUnitSign(OASISClient client) throws IOException {

		CloudUnitSignInfo cloudUnitSignInfo = new CloudUnitSignInfo();
		/**
		 * PDF生成类
		 */
		String filePath = "C:/Users/lichao/Desktop/1.pdf";
		cloudUnitSignInfo.setDocContent(FileUtils.readFileToByteArray(new File(filePath)));
		cloudUnitSignInfo.setDocumentExtType(DocumentExtType.PDF);
		/**
		 * 附件 合成PDF图片及证据
		 */

		cloudUnitSignInfo.setUnitSignInfoList(getUnitSignInfoLS());
		cloudUnitSignInfo.setSavaSignDoc(false);

		AnySignOutput anySignOutput = client.excuteGetUnitSignPDF(cloudUnitSignInfo);

		try {
			System.out.println(anySignOutput.getStatusId());
			System.out.println(anySignOutput.getPDFContent().length);
			FileCopyUtils.copy(anySignOutput.getPDFContent(), new File("C:/Users/lichao/Desktop/2.pdf"));
//			FileUtils.writeByteArrayToFile(new File("C:/Users/lichao/Desktop/2.pdf"), anySignOutput.getPDFContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*******************************************************/
	/********************* 封包 **********************************/
	/*******************************************************/
	public static List<UnitSignInfo> getUnitSignInfoLS() throws IOException {
		List<UnitSignInfo> unitSignInfoLS = new ArrayList<UnitSignInfo>();
		UnitSignInfo unitSignInfo = new UnitSignInfo();
		KWRuleInfo kwRuleInfoUnit = new KWRuleInfo();
		kwRuleInfoUnit.setKW("上海快钱信息服务有限公司");
		kwRuleInfoUnit.setKWOffset("0");
		kwRuleInfoUnit.setKWPos("1");
		kwRuleInfoUnit.setPageno("1");
		unitSignInfo.setKwRuleInfo(kwRuleInfoUnit);
		unitSignInfo.setOrganAppName("AnySignDefault");
		unitSignInfo.setSignRuleType("0");
		unitSignInfo.setSignImage(FileUtils.readFileToByteArray(new File("C:/Users/lichao/Desktop/1.gif")));
		unitSignInfoLS.add(unitSignInfo);
		return unitSignInfoLS;
	}
}

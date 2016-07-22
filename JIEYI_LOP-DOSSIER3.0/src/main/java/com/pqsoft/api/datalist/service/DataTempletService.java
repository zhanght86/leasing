package com.pqsoft.api.datalist.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class DataTempletService {
	private static final String PATH = "datatemplet.";

	//管理页面查询
	public Page queryMainPage(Map<String, Object> params) {
		params.put("TYPE1","资料管理-阶段");
		params.put("TYPE2","业务类型");
		params.put("TYPE3","客户类型");
		params.put("TYPE4","事业部");
		return PageUtil.getPage(params, PATH + "mainPage", PATH
				+ "mainPageCount");
	}

	public List<Map<String, Object>> queryRecords(Map<String, Object> params) {
		List<Map<String, Object>> records = Dao.selectList(PATH
				+ "queryRecords", params);
		return records;
	}

	//修改一条记录
	@SuppressWarnings("unchecked")
	public int updateRecord(Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(params.get("param"));
		List<Map<String, Object>> factors = json.getJSONArray("data1");
		List<Map<String, Object>> list = json.getJSONArray("data3");
//		List<Map<String, Object>> list2 = json.getJSONArray("data4");
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.println("data3:" + list);
//		System.out.println("data4:" + list2);
		System.out.println("factors:" + factors);
		int updateCount=0;
		int insertCount=0;
		param.put("ID", json.get("ID"));
		if(isNum((String) json.get("PHASE"))){
			param.put("PHASE",json.get("PHASE") );
		}else{
			Map<String, Object> map= new HashMap<String, Object>();
			map.put("PHASE", json.get("PHASE"));
			Map<String, Object> code=Dao.selectOne(PATH + "selectCode",map);
			System.out.println("code:"+code.get("CODE"));
			param.put("PHASE",code.get("CODE"));
		}
		
		param.put("MEMO", json.get("MEMO"));
		updateCount=Dao.update(PATH + "updateRecord", param);
		System.out.println("PAMAM:" + param);
		
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", json.get("ID"));
		Dao.delete(PATH + "delFactor", p1);
		Dao.delete(PATH + "delFile", p1);
		String id = Dao.getSequence("SEQ_t_MATERIAL_MGT");
		
		if(updateCount==0){			
			param.put("ID", id);
			param.put("PHASE", json.get("PHASE"));
			param.put("MEMO", json.get("MEMO"));
			insertCount=Dao.insert(PATH + "insertRecord", param);
		}

	

		/**
		 * 条件
		 */
		Map<String, Object> p2 = new HashMap<String, Object>();
		for (Map<String, Object> obj : factors) {
			if (obj.get("FACTOR_DICT_ID") != null
					&& obj.get("FACTOR_SYS") != null) {
				p2.put("FACTOR_DICT_ID", obj.get("FACTOR_DICT_ID"));
				p2.put("FACTOR_SYS", obj.get("FACTOR_SYS"));
				if(updateCount>0){
					p2.put("MM_ID", json.get("ID"));
				}else{
					p2.put("MM_ID", id);
				}		
				Dao.insert(PATH + "insertFactor", p2);
			}

		}

		Map<String, Object> p3 = new HashMap<String, Object>();
		for (Map<String, Object> obj : list) {			
			if(updateCount>0){
				p3.put("MM_ID", json.get("ID"));
			}else{
				p3.put("MM_ID", id);
			}	
			if (obj.get("FILE_DICT_ID") != null && obj.get("TYPE") != null&& obj.get("FENSHU") != null) {
				p3.put("FILE_DICT_ID", obj.get("FILE_DICT_ID"));
				p3.put("TYPE", obj.get("TYPE"));
				p3.put("FENSHU", obj.get("FENSHU"));
				p3.put("ISCHOICE", obj.get("ISCHOICE"));
				Dao.insert(PATH + "insertFile", p3);
			}

		}

//		for (Map<String, Object> obj : list2) {
//			Map<String, Object> p4 = new HashMap<String, Object>();
//			p4.put("MM_ID", json.get("ID"));
//			if (obj.get("FILE_DICT_ID") != null && obj.get("FENSHU") != null) {
//				p4.put("FILE_DICT_ID", obj.get("FILE_DICT_ID"));
//				p4.put("FENSHU", obj.get("FENSHU"));
//				Dao.update(PATH + "insertFile1", p4);
//			}
//		}
		if(updateCount>0){
			return updateCount;
		}else{
			return insertCount;
		}
		
	}

	@SuppressWarnings("unchecked")
	//插入一条记录
	public String insertRecord(Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(params.get("param"));
		List<Map<String, Object>> list = json.getJSONArray("data1");
		List<Map<String, Object>> list2 = json.getJSONArray("data3");
//		List<Map<String, Object>> list3 = json.getJSONArray("data4");
		Map<String, Object> param = new HashMap<String, Object>();

		System.out.println("data1:" + list);
		System.out.println("data3:" + list2);
//		System.out.println("data4:" + list3);
		// 插入资料主记录表
		String id = Dao.getSequence("SEQ_t_MATERIAL_MGT");
		param.put("ID", id);
		param.put("PHASE", json.get("PHASE"));
		param.put("MEMO", json.get("MEMO"));
		Dao.insert(PATH + "insertRecord", param);

		// 插入资料条件记录
		for (Map<String, Object> obj : list) {
			if (obj.size() > 0) {
				param.putAll(obj);
				param.put("MM_ID", id);
				Dao.insert(PATH + "insertFactor", param);
			}
		}

		// 插入资料清单记录
		Map<String, Object> objzoo = new HashMap<String, Object>();
		for (Map<String, Object> obj : list2) {
			if (obj.size() > 0) {
				objzoo.putAll(obj);
				objzoo.put("MM_ID", id);
				Dao.insert(PATH + "insertFile", objzoo);
			}
		}

		// 插入资料清单记录
//		Map<String, Object> zoo = new HashMap<String, Object>();
//		for (Map<String, Object> obj : list3) {
//			if (obj.size() > 0) {
//				zoo.putAll(obj);
//				zoo.put("MM_ID", id);
//				Dao.update(PATH + "insertFile1", zoo);
//			}
//		}

		return id;
	}

	//删除资料配置
	public int delRecord(Map<String, Object> params) {
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", params.get("ID"));
		Dao.delete(PATH + "delFactor", p1);
		Dao.delete(PATH + "delFile", p1);
		return Dao.delete(PATH + "delRecord", params);
	}

	//加载资料配置清单
	public Map<String, Object> loadRecord(Map<String, Object> params) {
		// TODO
		List<Map<String, Object>> recs = Dao.selectList(PATH + "queryRecords",
				params);
		Map<String, Object> rec = recs.get(0);
		List<Map<String, Object>> factors = Dao
				.execSelectSql("SELECT FACTOR_DICT_ID, FACTOR_SYS FROM T_MATERIAL_MGT_FACTOR WHERE MM_ID="
						+ params.get("ID"));
		List<String> files = Dao
				.execSelectSql("SELECT MM_ID,FILE_DICT_ID,TYPE,FENSHU,ISCHOICE FROM T_MATERIAL_MGT_FILE WHERE MM_ID="
						+ params.get("ID"));

		List<String> sphase = Dao
				.execSelectSql("SELECT ID,PHASE,MEMO FROM t_materical_mgtl WHERE ID="
						+ params.get("ID"));
		rec.put("factors", factors);
		rec.put("files", files);
		// rec.put("files1", files1);
		rec.put("sphase", sphase);
		return rec;
	}

	// 拿到放款上传文件的--一级文件类型列表。。放款特殊，一个项目，多次放款。每次放款文件不同
	public List<Map<String, Object>> getLevelOneListFK(String phase,
			String proCode, String fkId) {
		// String phase = "放款前";
		String[] setedFiles = this.getFileListByCode(phase, proCode);
		String proId = new CustInfoInputService().getProId(proCode);
		for (String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			String str1=setedFile.substring(setedFile.indexOf("-")+1, setedFile.length());//
			String str11=str1.substring(str1.indexOf("-")+1, str1.length());//资料名称id
			String str2=setedFile.substring(0, setedFile.indexOf("-"));//资料名称
			String str3=str1.substring(0, str1.indexOf("-"));//资料配置阶段
			System.out.println("========yx===str1====="+str1+"========str2====="+str2+"========str3====="+str3+"===========str11========="+str11);
			p1.put("MMF_ID", str3);
			p1.put("FILE_DICT_ID", str11);
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_TYPE", str2);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			//del gengchangbao JZZL-98 2016年2月18日18:35:03 start
			//p1.put("FK_ID", fkId);
			//del gengchangbao JZZL-98 2016年2月18日18:35:03 end
			p1.put("CREATE_CODE", Security.getUser() == null ? "god" : Security
					.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH + "getFpfs", p1);
			if (fpfs == null || fpfs.size() < 1) {// 如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH + "insertFpf", p1);
			}
		}
		Map<String, Object> p1 = new HashMap<String, Object>();
		if ("放款".equals(phase)) {
			p1.put("TPM_BUSINESS_PLATE_DY", phase);
		} else {
			p1.put("TPM_BUSINESS_PLATE", phase);
		}
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		//del gengchangbao JZZL-98 2016年2月18日18:35:03 start
		//p1.put("FK_ID", fkId);
		//del gengchangbao JZZL-98 2016年2月18日18:35:03 end
		List<Map<String, Object>> ls = Dao.selectList(PATH + "getFpfs1", p1);
		return ls;
	}

	public List<Map<String, Object>> getLevelOneList(String phase,
			String proCode) {
		String[] setedFiles = this.getFileListByCode(phase, proCode);// 多条资料记录，
																		// 随机取第一条记录
		String proId = new CustInfoInputService().getProId(proCode);
		// 第一次进入资料上传页面的时候执行
		for (String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			String str1=setedFile.substring(setedFile.indexOf("-")+1, setedFile.length());//
			String str11=str1.substring(str1.indexOf("-")+1, str1.length());//资料名称id
			String str2=setedFile.substring(0, setedFile.indexOf("-"));//资料名称
			String str3=str1.substring(0, str1.indexOf("-"));//资料配置阶段
			System.out.println("========yx===str1====="+str1+"========str2====="+str2+"========str3====="+str3+"===========str11========="+str11);
			p1.put("MMF_ID", str3);
			p1.put("FILE_DICT_ID", str11);
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_TYPE", str2);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser() == null ? "god" : Security
					.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH + "getFpfs", p1);
			if (fpfs == null || fpfs.size() < 1) {// 如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH + "insertFpf", p1);
			}
		}
		// /////////////////////////////////////////////////////////////

		// 查询项目下所有资料
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("TPM_BUSINESS_PLATE", phase);
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		List<Map<String, Object>> ls = Dao.selectList(PATH + "getFpfs1", p1);
		return ls;
	}

	// 立项，特殊。。。因为要取出共同承租人、担保人的资料
	public List<Map<String, Object>> getLevelOneListLX(String mainType,
			String proCode) {
		String phase = "立项";
		String[] setedFiles = this.getFileListByCode(phase, proCode, mainType);
		String proId = new CustInfoInputService().getProId(proCode);
		for (String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			String str1=setedFile.substring(setedFile.indexOf("-")+1, setedFile.length());//
			String str11=str1.substring(str1.indexOf("-")+1, str1.length());//资料名称id
			String str2=setedFile.substring(0, setedFile.indexOf("-"));//资料名称
			String str3=str1.substring(0, str1.indexOf("-"));//资料配置阶段
			System.out.println("========yx===str1====="+str1+"========str2====="+str2+"========str3====="+str3+"===========str11========="+str11);
			p1.put("MMF_ID", str3);
			p1.put("FILE_DICT_ID", str11);
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_CUSTOMER_TYPE", mainType);
			p1.put("TPM_TYPE", str2);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser() == null ? "god" : Security
					.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH + "getFpfs", p1);

			// FIXME 这里的问题：如 婚姻证明(必选) != 婚姻证明(非必选) ，本是要改必选为非必选，结果又加了一个，而且还删不了。
			if (fpfs == null || fpfs.size() < 1) {// 如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH + "insertFpf", p1);
			}
		}
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("TPM_BUSINESS_PLATE", phase);
		p1.put("TPM_CUSTOMER_TYPE", mainType);
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		List<Map<String, Object>> ls = Dao.selectList(PATH + "getFpfs1", p1);
		return ls;
	}

	
	// 立项，特殊。。。因为要取出共同承租人、担保人的资料
	public List<Map<String, Object>> getLevelOneList_New(String mainType,
			String proCode) {
		//String phase = "立项";
		String phase = "立项";
		String[] setedFiles = this.getFileListByCode(phase, proCode, mainType);
		String proId = new CustInfoInputService().getProId(proCode);
		for (String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			String str1=setedFile.substring(setedFile.indexOf("-")+1, setedFile.length());//
			String str11=str1.substring(str1.indexOf("-")+1, str1.length());//资料名称id
			String str2=setedFile.substring(0, setedFile.indexOf("-"));//资料名称
			String str3=str1.substring(0, str1.indexOf("-"));//资料配置阶段
			System.out.println("========yx===str1====="+str1+"========str2====="+str2+"========str3====="+str3+"===========str11========="+str11);
			p1.put("MMF_ID", str3);
			p1.put("FILE_DICT_ID", str11);
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_CUSTOMER_TYPE", mainType);
			p1.put("TPM_TYPE", str2);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser() == null ? "god" : Security
					.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH + "getFpfs", p1);

			// FIXME 这里的问题：如 婚姻证明(必选) != 婚姻证明(非必选) ，本是要改必选为非必选，结果又加了一个，而且还删不了。
			if (fpfs == null || fpfs.size() < 1) {// 如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH + "insertFpf", p1);
			}
		}
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("TPM_BUSINESS_PLATE", phase);
		p1.put("TPM_CUSTOMER_TYPE", mainType);
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		List<Map<String, Object>> ls = Dao.selectList(PATH + "getFpfs1", p1);
		return ls;
	}
	public List<Map<String, Object>> getLevelOneList(Map<String, Object> params) {
		if (com.pqsoft.leeds.utils.StringUtil.isBlank(params.get("PHASE")))
			params.put("PHASE", "立项");
		String proCode;
		if (com.pqsoft.leeds.utils.StringUtil.isBlank(params.get("PRO_CODE"))) {
			proCode = new CustInfoInputService().getProCode(params.get(
					"PROJECT_ID").toString());
		} else
			proCode = params.get("PRO_CODE").toString();
		String phase = params.get("PHASE").toString();
		if (phase.startsWith("放款")) {// 以"放款"为开头的项目
			String fkId = params.get("FK_ID").toString();
			return this.getLevelOneListFK(phase, proCode, fkId);
		} else if (phase.equals("立项")) {
			String mainType = params.get("mainType") == null ? "承租人" : params
					.get("mainType").toString();
			return this.getLevelOneListLX(mainType, proCode);
		} else {
			return this.getLevelOneList(phase, proCode);
		}
	}

	// -------------------拿到资料配置中的一条清单
	public String[] getFileListByCode(String phase, String proCode) {
		String proId = new CustInfoInputService().getProId(proCode);
		return this.getFileList(phase, proId);
	}

	public String[] getFileListByCode(String phase, String proCode,
			String mainType) {
		String proId = new CustInfoInputService().getProId(proCode);
		return this.getFileList(phase, proId, mainType);
	}

	public String[] getFileList(String phase, String proId) {
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached()
				.get("资料管理-条件类型");
		List<String> factorFlags = new ArrayList<String>();
		for (Map<String, Object> factorType : factorTypes) {
			// 执行sql，拿到条件
			String sql = factorType.get("REMARK") == null ? "" : factorType
					.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", proId);
			List<Map<String, Object>> fields = null;
			if (StringUtils.isNotBlank(sql)) {
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1)
				field = fields.get(0);
			else
				continue;
			if (field == null)
				continue;
			List<Map<String, Object>> factors = null;
			if (factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factors = new SysDictionaryMemcached().get(factorType.get(
						"CODE").toString());
			} else
				factors = new DataDictionaryMemcached().get(factorType.get(
						"CODE").toString());

			if (factors != null) {
				for (Map<String, Object> factor : factors) {
					if (field.get("CODE") != null
							&& field.get("CODE").toString()
									.equals(factor.get("CODE")))
						factorFlags.add(factor.get("FLAG").toString());
				}
			}
		}

		Map<String, Object> p1 = new HashMap<String, Object>();
		if ("放款".equals(phase)) {
			p1.put("PHASE_T", phase);
		} else {
			p1.put("PHASE_V", phase);
		}

		p1.put("FACTORS", factorFlags);
		p1.put("ZLGLJD","资料管理-阶段");
		List<Map<String, Object>> res = Dao
				.selectList(PATH + "getFileList", p1);
		if (res != null && res.size() >= 1) {
			Map<String, Object> re = res.get(0);
			return re.get("FILES").toString().split(",");
		}
		return new String[] {};
	}

	public String[] getFileList(String phase, String proId, String mainType) {
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		List<String> factorFlags = new ArrayList<String>();
		for (Map<String, Object> factorType : factorTypes) {
			if (factorType.get("CODE").equals("主体类型")) {
				factorFlags.add(mainType);
				continue;
			}
			// 执行sql，拿到条件
			String sql = factorType.get("REMARK") == null ? "" : factorType.get("REMARK").toString();
			if(StringUtils.isBlank(sql)){
				continue;
			}
			sql = sql.replace("${PROJECT_ID}", proId);
			
			List<Map<String, Object>> fields = null;
			if(sql != null && !"".equals(sql)){
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1)
				field = fields.get(0);
			else
				continue;
			if (field == null)
				continue;
			List<Map<String, Object>> factors = null;

			if (factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factors = new SysDictionaryMemcached().get(factorType.get(
						"CODE").toString());
			} else {
				factors = new DataDictionaryMemcached().get(factorType.get(
						"CODE").toString());
			}
			
			if (factors != null) {
				for (Map<String, Object> factor : factors) {
					if (field.get("CODE") != null
							&& (field.get("CODE").toString()
									.equals(factor.get("CODE")) || field
									.get("CODE").toString()
									.equals(factor.get("FLAG"))))
						factorFlags.add(factor.get("FLAG").toString());
				}
			}
		}

		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("PHASE_V", phase);
		p1.put("FACTORS", factorFlags);
		p1.put("ZLGLJD","资料管理-阶段");
		List<Map<String, Object>> res = Dao
				.selectList(PATH + "getFileList", p1);
		if (res != null && res.size() >= 1) {
			Map<String, Object> re = res.get(0);
			return re.get("FILES").toString().split(",");
		}
		return new String[] {};
	}

	public List<Map<String, Object>> getFileInfoList(Map<String, Object> params) {
		params.put("FILE_LEVEL", 2);
		return Dao.selectList(PATH + "getFpfs", params);
	}

	/**
	 * 查看项目所需文件
	 * 
	 * @author: yx
	 * @date: 2015-4-9
	 * @return_type:List<Map<String,Object>>
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFpType(Map<String, Object> params) {
		params.put("FILE_LEVEL", 1);
		return Dao.selectList(PATH + "getFpType", params);
	}

	public List<Map<String, Object>> getFileInfoList(String phase, String proId) {
		Map<String, Object> params = new HashMap<String, Object>();

		if ("放款".equals(phase)) {
			params.put("TPM_BUSINESS_PLATE_DY", phase);
		} else {
			params.put("TPM_BUSINESS_PLATE", phase);
		}

		params.put("PROJECT_ID", proId);
		return this.getFileInfoList(params);
	}

	public List<Map<String, Object>> getFileInfoList(String phase,
			String proId, String custType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TPM_BUSINESS_PLATE", phase);
		params.put("PROJECT_ID", proId);
		params.put("CUST_TYPE", custType);
		return this.getFileInfoList(params);
	}

	/**
	 * 查看客户所需文件类别
	 * 
	 * @author: yx
	 * @date: 2015-4-9
	 * @return_type:List<Map<String,Object>>
	 * @param phase
	 *            阶段
	 * @param proId
	 *            项目id
	 * @param custType
	 *            客户类型
	 * @return
	 */
	public List<Map<String, Object>> getFileTypeList(String phase,
			String proId, String custType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TPM_BUSINESS_PLATE", phase);
		params.put("PROJECT_ID", proId);
		params.put("CUST_TYPE", custType);
		return this.getFpType(params);
	}

	/**
	 * 查看客户所需文件类别
	 * 
	 * @author: yx
	 * @date: 2015-4-9
	 * @return_type:List<Map<String,Object>>
	 * @param phase
	 *            阶段
	 * @param proId
	 *            项目id
	 * @return
	 */
	public List<Map<String, Object>> getFileTypeList(String phase, String proId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TPM_BUSINESS_PLATE", phase);
		params.put("PROJECT_ID", proId);
		return this.getFpType(params);
	}

	public List<Map<String, Object>> getFileInfoList(String parentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("PARENT_ID", parentId);
		return this.getFileInfoList(params);
	}

	public static void main(String[] args) {
		int a = 2, b = 1;
		for (; a < 8; a++) {
			b += a;
			a += 2;
		}
		System.out.println(a + ", " + b);
	}

	public List<Map<String, Object>> getSupplyMaterials(
			Map<String, Object> params) {
		List<Map<String, Object>> allFiles = new DataDictionaryMemcached()
				.get("资料管理-资料名称");
		List<Map<String, Object>> allFiles1 = new ArrayList<Map<String, Object>>();
		allFiles1.addAll(allFiles);
		List<Map<String, Object>> curFiles = this.getLevelOneList(params);
		for (Iterator<Map<String, Object>> it = allFiles1.iterator(); it
				.hasNext();) {
			Map<String, Object> f1 = it.next();
			for (Map<String, Object> f2 : curFiles) {
				if (f1.get("FLAG").toString().equals(f2.get("TPM_TYPE")))
					it.remove();
			}
		}
		return allFiles1;
	}

	public List<Map<String, Object>> toShowRecodeApp(Map<String, Object> map) {
		map.put("FACTORS1", map.get("FLAG_TYPE"));
		map.put("FACTORS2", map.get("B"));
		map.put("FACTORS3", map.get("C"));
		System.out.println("ada:" + map.get("FLAG_TYPE") + ":" + map.get("B")
				+ ":" + map.get("C"));
		return Dao.selectList(PATH + "toShowRecodeApp", map);
	}
	
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}

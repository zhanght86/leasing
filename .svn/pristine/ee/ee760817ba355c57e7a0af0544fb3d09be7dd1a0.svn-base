package com.pqsoft.leeds.materialMgt.service;

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

public class MaterialMgtService {
	private static final String PATH = "materialMgt.";

	public Page queryMainPage(Map<String, Object> params) {
		return PageUtil.getPage(params, PATH + "mainPage", PATH + "mainPageCount");
	}

	public List<Map<String, Object>> queryRecords(Map<String, Object> params) {

		List<Map<String, Object>> records = Dao.selectList(PATH + "queryRecords", params);
		return records;
	}

	public int updateRecord(Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(params.get("param"));
		List<Map<String, Object>> factors = json.getJSONArray("data1");
		List<Map<String, Object>> qingdanList = json.getJSONArray("data2");
		Map<String, Object> param = new HashMap<String,Object>();
		
		param.put("ID", json.get("ID"));
		param.put("PHASE", json.get("PHASE"));
		param.put("MEMO", json.get("MEMO"));
		
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", json.get("ID"));
		Dao.delete(PATH + "delFactor", p1);
		Dao.delete(PATH + "delFile", p1);
		for (Map<String, Object> f : factors) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", json.get("ID"));
			if( f.get("FACTOR_DICT_ID")!=null && f.get("FACTOR_SYS")!=null){
				p2.put("FACTOR_DICT_ID", f.get("FACTOR_DICT_ID"));
				p2.put("FACTOR_SYS", f.get("FACTOR_SYS"));
				Dao.insert(PATH + "insertFactor", p2);
			}
		}
		for (Map<String, Object> obj : qingdanList) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", json.get("ID"));
			p2.putAll(obj);
			Dao.insert(PATH + "insertFile", p2);
		}
		return Dao.update(PATH + "updateRecord", param);
	}

	@SuppressWarnings("unchecked")
	public String insertRecord(Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(params.get("param"));
		List<Map<String, Object>> list = json.getJSONArray("data1");
		List<Map<String, Object>> list1 = json.getJSONArray("data2");
		Map<String, Object> param = new HashMap<String,Object>();
		
		//插入资料主记录表
		String id = Dao.getSequence("SEQ_MATERIAL_MGT");
		param.put("ID", id);
		param.put("PHASE", json.get("PHASE"));
		param.put("MEMO", json.get("MEMO"));
		
		Dao.insert(PATH + "insertRecord", param);
		
		//插入资料条件记录
		for(Map<String,Object> obj : list){
			if(obj.size()>0){
				param.putAll(obj);
				param.put("MM_ID",id);
				Dao.insert(PATH + "insertFactor", param);
			}
		}
		
		//插入资料资料记录
		Map<String,Object> ziliao = new HashMap<String,Object>();
		for(Map<String,Object> obj : list1){
			ziliao.putAll(obj);
			ziliao.put("MM_ID", id);
			Dao.insert(PATH + "insertFile", ziliao);
		}
		
//		for (Map<String, Object> f : (List<Map<String, Object>>) params.get("fs")) {
//			Map<String, Object> p2 = new HashMap<String, Object>();
//			p2.put("MM_ID", params.get("ID"));
//			p2.put("FACTOR_DICT_ID", f.get("FACTOR_DICT_ID"));
//			p2.put("FACTOR_SYS", f.get("FACTOR_SYS"));
//			Dao.insert(PATH + "insertFactor", p2);
//		}
//		for (String fileId : (String[]) params.get("FILES")) {
//			Map<String, Object> p1 = new HashMap<String, Object>();
//			p1.put("MM_ID", id);
//			p1.put("FILE_DICT_ID", fileId);
//			Dao.insert(PATH + "insertFile", p1);
//		}
		
		return id;
	}

	public int delRecord(Map<String, Object> params) {
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", params.get("ID"));
		Dao.delete(PATH + "delFactor", p1);
		Dao.delete(PATH + "delFile", p1);
		return Dao.delete(PATH + "delRecord", params);
	}

	public Map<String, Object> loadRecord(Map<String, Object> params) {
		// TODO
		List<Map<String, Object>> recs = Dao.selectList(PATH + "queryRecords", params);
		Map<String, Object> rec = recs.get(0);
		List<Map<String, Object>> factors = Dao.execSelectSql("SELECT FACTOR_DICT_ID, FACTOR_SYS FROM MATERIAL_MGT_FACTOR WHERE MM_ID="
				+ params.get("ID"));
		List<String> files = Dao.execSelectSql("SELECT FILE_DICT_ID,TYPE FROM MATERIAL_MGT_FILE WHERE MM_ID=" + params.get("ID"));
		rec.put("factors", factors);
		rec.put("files", files);
		return rec;
	}
	
	//拿到放款上传文件的--一级文件类型列表。。放款特殊，一个项目，多次放款。每次放款文件不同
	public List<Map<String, Object>> getLevelOneListFK(String phase, String proCode, String fkId) {
//		String phase = "放款前";
		String[] setedFiles = this.getFileListByCode(phase, proCode);
		String proId = new CustInfoInputService().getProId(proCode);
		for(String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_TYPE", setedFile);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("FK_ID", fkId);
			p1.put("CREATE_CODE", Security.getUser()==null?"god": Security.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH+"getFpfs", p1);
			if(fpfs ==null || fpfs.size()<1) {//如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH+"insertFpf", p1);
			}
		}
		Map<String, Object> p1 = new HashMap<String, Object>();
		if("放款".equals(phase)){
			p1.put("TPM_BUSINESS_PLATE_DY", phase);
		}else {
			p1.put("TPM_BUSINESS_PLATE", phase);
		}
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		p1.put("FK_ID", fkId);
		List<Map<String, Object>> ls = Dao.selectList(PATH+"getFpfs1", p1);
		return ls;
	}
	
	public List<Map<String, Object>> getLevelOneList(String phase, String proCode) {
		String[] setedFiles = this.getFileListByCode(phase, proCode);//多条资料记录， 随机取第一条记录
		String proId = new CustInfoInputService().getProId(proCode);
		//第一次进入资料上传页面的时候执行
		for(String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>(); 
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_TYPE", setedFile);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser()==null?"god": Security.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH+"getFpfs", p1);
			if(fpfs ==null || fpfs.size()<1) {//如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH+"insertFpf", p1);
			}
		}
		///////////////////////////////////////////////////////////////
		
		// 查询项目下所有资料
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("TPM_BUSINESS_PLATE", phase);
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		List<Map<String, Object>> ls = Dao.selectList(PATH+"getFpfs1", p1);
		return ls;
	}
	
	//立项，特殊。。。因为要取出共同承租人、担保人的资料
	public List<Map<String, Object>> getLevelOneListLX(String mainType, String proCode) {
		String phase = "立项";
		String[] setedFiles = this.getFileListByCode(phase, proCode, mainType);
		String proId = new CustInfoInputService().getProId(proCode);
		for(String setedFile : setedFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			//判断文件是否为必填资料
			String tpm_flag_ = setedFile.split("\\(")[1];
			String temp_flag = tpm_flag_.split("\\)")[0];
			p1.put("TPM_FLAG", "必选".equals(temp_flag)?"1":"0");
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_CUSTOMER_TYPE", mainType);
			p1.put("TPM_TYPE", setedFile);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser()==null?"god": Security.getUser().getCode());
			List<Map> fpfs = Dao.selectList(PATH+"getFpfs", p1);
			
			//FIXME 这里的问题：如 婚姻证明(必选)  != 婚姻证明(非必选)  ，本是要改必选为非必选，结果又加了一个，而且还删不了。
			if(fpfs ==null || fpfs.size()<1) {//如果不存在，添加
				p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				Dao.insert(PATH+"insertFpf", p1);
			}
		}
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("TPM_BUSINESS_PLATE", phase);
		p1.put("TPM_CUSTOMER_TYPE", mainType);
		p1.put("PROJECT_ID", proId);
		p1.put("FILE_LEVEL", 1);
		List<Map<String, Object>> ls = Dao.selectList(PATH+"getFpfs1", p1);
		return ls;
	}
	
	public List<Map<String, Object>> getLevelOneList(Map<String, Object> params) {
		if(com.pqsoft.leeds.utils.StringUtil.isBlank(params.get("PHASE"))) params.put("PHASE", "立项");
		String proCode;
		if(com.pqsoft.leeds.utils.StringUtil.isBlank(params.get("PRO_CODE"))) {
			proCode = new CustInfoInputService().getProCode(params.get("PROJECT_ID").toString());
		} else
			proCode = params.get("PRO_CODE").toString();
		String phase = params.get("PHASE").toString();
		if(phase.startsWith("放款")) {//以"放款"为开头的项目
			String fkId = params.get("FK_ID").toString();
			return this.getLevelOneListFK(phase, proCode, fkId);
		} else if(phase.equals("立项")) {
			String mainType = params.get("mainType")==null?"承租人":params.get("mainType").toString();
			return this.getLevelOneListLX(mainType, proCode);
		} else {
			return this.getLevelOneList(phase, proCode);
		}
	}
	//-------------------拿到资料配置中的一条清单	
	public String[] getFileListByCode(String phase, String proCode) {
		String proId = new CustInfoInputService().getProId(proCode);
		return this.getFileList(phase, proId);
	}
	public String[] getFileListByCode(String phase, String proCode, String mainType) {
		String proId = new CustInfoInputService().getProId(proCode);
		return this.getFileList(phase, proId, mainType);
	}
	public String[] getFileList(String phase, String proId) {
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		List<String> factorFlags = new ArrayList<String>();
		for (Map<String, Object> factorType : factorTypes) {
			// 执行sql，拿到条件
			String sql = factorType.get("REMARK") == null ? "" : factorType.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", proId);
			List<Map<String, Object>> fields = null;
			if(StringUtils.isNotBlank(sql)){
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1) field = fields.get(0);
			else continue;
			if(field==null) continue;
			List<Map<String, Object>> factors = null;
			if (factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factors = new SysDictionaryMemcached().get(factorType.get("CODE").toString());
			} else factors = new DataDictionaryMemcached().get(factorType.get("CODE").toString());
			
			if (factors != null) {
				for (Map<String, Object> factor : factors) {
					if (field.get("CODE") != null && field.get("CODE").toString().equals(factor.get("CODE")))
						factorFlags.add(factor.get("FLAG").toString());
				}
			}
		}

		Map<String, Object> p1 = new HashMap<String, Object>();
		if("放款".equals(phase)){
			p1.put("PHASE_T", phase);
		}else{
			p1.put("PHASE_V", phase);
		}
		
		p1.put("FACTORS", factorFlags);
		List<Map<String, Object>> res = Dao.selectList(PATH + "getFileList", p1);
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
			if(factorType.get("CODE").equals("主体类型")) {
				factorFlags.add(mainType);
				continue;
			}
			// 执行sql，拿到条件
			String sql = factorType.get("REMARK") == null ? "" : factorType.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", proId);
			List<Map<String, Object>> fields = null;
			try {
				fields = Dao.execSelectSql(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1) field = fields.get(0);
			else continue;
			if(field==null) continue;
			List<Map<String, Object>> factors = null;
			if (factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factors = new SysDictionaryMemcached().get(factorType.get("CODE").toString());
			} else factors = new DataDictionaryMemcached().get(factorType.get("CODE").toString());
			if (factors != null) {
				for (Map<String, Object> factor : factors) {
					if (field.get("CODE") != null && (field.get("CODE").toString().equals(factor.get("CODE"))||
							field.get("CODE").toString().equals(factor.get("FLAG"))))
						factorFlags.add(factor.get("FLAG").toString());
				}
			}
		}
		
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("PHASE_V", phase);
		p1.put("FACTORS", factorFlags);
		List<Map<String, Object>> res = Dao.selectList(PATH + "getFileList", p1);
		if (res != null && res.size() >= 1) {
			Map<String, Object> re = res.get(0);
			return re.get("FILES").toString().split(",");
		}
		return new String[] {};
	}
	
	public List<Map<String, Object>> getFileInfoList(Map<String, Object> params) {
		params.put("FILE_LEVEL", 2);
		return Dao.selectList(PATH+"getFpfs", params);
	}
	
	/**
	 * 查看项目所需文件
	 * @author: yx 
	 * @date: 2015-4-9
	 * @return_type:List<Map<String,Object>>
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFpType(Map<String, Object> params) {
		params.put("FILE_LEVEL", 1);
		return Dao.selectList(PATH+"getFpType", params);
	}
	
	public List<Map<String, Object>> getFileInfoList(String phase, String proId) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if("放款".equals(phase)){
			params.put("TPM_BUSINESS_PLATE_DY", phase);
		}else{
			params.put("TPM_BUSINESS_PLATE", phase);
		}
		
		params.put("PROJECT_ID", proId);
		return this.getFileInfoList(params);
	}
	
	
	public List<Map<String, Object>> getFileInfoList(String phase, String proId, String custType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TPM_BUSINESS_PLATE", phase);
		params.put("PROJECT_ID", proId);
		params.put("CUST_TYPE", custType);
		return this.getFileInfoList(params);
	}
	
	/**
	 * 查看客户所需文件类别
	 * @author: yx 
	 * @date: 2015-4-9
	 * @return_type:List<Map<String,Object>>
	 * @param phase   阶段 
	 * @param proId   项目id
	 * @param custType  客户类型
	 * @return
	 */
	public List<Map<String, Object>> getFileTypeList(String phase, String proId, String custType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TPM_BUSINESS_PLATE", phase);
		params.put("PROJECT_ID", proId);
		params.put("CUST_TYPE", custType);
		return this.getFpType(params);
	}
	
	/**
	 * 查看客户所需文件类别
	 * @author: yx 
	 * @date: 2015-4-9
	 * @return_type:List<Map<String,Object>>
	 * @param phase  阶段
	 * @param proId  项目id
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
		int a =2, b=1;
		for(; a<8; a++) {
			b +=a;
			a +=2;
		}
		System.out.println(a + ", " + b);
	}

	public List<Map<String, Object>> getSupplyMaterials(Map<String, Object> params) {
		List<Map<String, Object>> allFiles = new DataDictionaryMemcached().get("资料管理-资料名称");
		List<Map<String, Object>> allFiles1 = new ArrayList<Map<String, Object>>();
		allFiles1.addAll(allFiles);
		List<Map<String, Object>> curFiles = this.getLevelOneList(params);
		for(Iterator<Map<String, Object>> it = allFiles1.iterator(); it.hasNext();) {
			Map<String, Object> f1 = it.next();
			for(Map<String, Object> f2 : curFiles) {
				if(f1.get("FLAG").toString().equals(f2.get("TPM_TYPE"))) it.remove();
			}
		}
		return allFiles1;
	}
}

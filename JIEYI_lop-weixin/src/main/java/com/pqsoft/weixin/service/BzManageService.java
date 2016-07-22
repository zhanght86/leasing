package com.pqsoft.weixin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.api.datalist.service.DataTempletService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;
import com.pqsoft.weixin.utils.AppUtils.Phase;

/**
 * 微信业务处理
 * @author LUYANFENG @ 2015年3月30日
 *
 */
public class BzManageService{

	/**
	 * 取文件
	 * @param vc
	 * @param PHASE
	 * @param proId
	 * @return
	 */
	public List<Map<String, Object>> getFile(VelocityContext vc, Phase PHASE, String proId, String fk_id) {
		List<Map<String, Object>> ms = Dao.execSelectSql(
				  "SELECT CASE B.TYPE WHEN 'NP' THEN 1 ELSE 2 END CUST_TYPE,"
				+ " CASE A.JOINT_TENANT WHEN '2' THEN 3 WHEN '3' THEN 4 ELSE 0 END GT_TYPE,"
				+ " CASE A.GUARANTEE WHEN '2' THEN 5 WHEN '3' THEN 6 ELSE 0 END GUA_TYPE"
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B"
				+ " WHERE A.CLIENT_ID = B.ID AND A.ID='"+proId+"'");
		Map<String, Object> m = ms.get(0);
		Map<String,Object> mss = new HashMap<String,Object>();
		mss.put("ID", proId);
		Map<String,Object> projectMap = Dao.selectOne("weixin_view.select_project_byID", mss);
		vc.put("PRO_ID", projectMap.get("PRO_ID"));
		vc.put("PRO_CODE", projectMap.get("PRO_CODE"));
		vc.put("CLIENT_ID", projectMap.get("CLIENT_ID"));
		vc.put("PROJECT_ID", projectMap.get("PROJECT_ID"));
		vc.put("CLERK_ID", Security.getUser().getId() );
		vc.put("ID", proId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> mainTypes = new DataDictionaryMemcached().get("主体类型");
		List<Map<String, Object>> mts = new ArrayList<Map<String, Object>>();
		for(String key : m.keySet()) {
			if(PHASE != Phase.立项 && !"CUST_TYPE".equals(key+"")) continue;
			if( (m.get(key)+"").equals("0") ) continue;
			
			for(Map<String, Object> mainType : mainTypes) {
				if( (mainType.get("CODE")+"").equals(m.get(key)+"") ) {
					mts.add(mainType);
					Page files = this.getFiles(projectMap.get("PRO_CODE")+"",projectMap.get("PROJECT_ID")+"", fk_id, ""+mainType.get("FLAG"), PHASE);
					mainType.put("page", files);
				}
			}
		}
		Collections.sort(mts,new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String code1 = o1.get("CODE").toString();
				String code2 = o2.get("CODE").toString();
				return code1.compareTo(code2);
			}
		});
		return mts;
	}
	
	/**
	 * 新的获取文件
	 * @param PRO_CODE
	 * @param PROJECT_ID
	 * @param FK_ID
	 * @param MAIN_TYPE
	 * @param PHASE //需要提供阶段参数
	 */
	public Page getFiles(String PRO_CODE,String PROJECT_ID , String FK_ID , String MAIN_TYPE, Phase PHASE){
		String proCode ;
		if(StringUtil.isBlank(PRO_CODE))
			proCode = new CustInfoInputService().getProCode(PROJECT_ID );
		else proCode = PRO_CODE;
		PHASE = PHASE == null ?Phase.立项 : PHASE;
		
		List<Map<String, Object>> listSJZD;
		DataTempletService mm = new DataTempletService();
		if(PHASE == Phase.放款前  || PHASE == Phase.放款后 ) {
			String fkId = FK_ID;
			listSJZD = mm.getLevelOneListFK(PHASE.toString(), proCode, fkId);
		} else if(PHASE == Phase.立项 ) {
			String mainType = MAIN_TYPE;
			listSJZD = mm.getLevelOneListLX(mainType, proCode);
		} else {
			listSJZD = mm.getLevelOneList(PHASE.toString(), proCode);
		}
		
		
		for(Map<String, Object> m : listSJZD) {
//			System.out.println(m);
			//FIXME 资料类型 对应的 图标 。系统暂没有维护的地方，先写死
			String TPM_TYPE = (String) m.get("TPM_TYPE");
			if(TPM_TYPE.startsWith("业务申请书")){
				m.put("ICON", "sq.png");
			}else if(TPM_TYPE.contains("份证明")){	
				m.put("ICON", "id.png");
			}else if(TPM_TYPE.contains("婚姻")){	
				m.put("ICON", "marry.png");
			}else if(TPM_TYPE.contains("住址") || TPM_TYPE.contains("租住")){	
				m.put("ICON", "home.png");
			}else if(TPM_TYPE.startsWith("收入证明")){
				m.put("ICON", "sr.png");
			}else if(TPM_TYPE.startsWith("驾驶证")){	
				m.put("ICON", "drive.png");
			}else if(TPM_TYPE.startsWith("资产证明")){	
				m.put("ICON", "zc.png");
			}else if(TPM_TYPE.startsWith("银行对账单")){	
				m.put("ICON", "yh.png");
			}else if(TPM_TYPE.startsWith("车辆销售合同")){	
				m.put("ICON", "carht.png");
			}else if(TPM_TYPE.startsWith("抵押租赁合同")){	
				m.put("ICON", "dy.png");
			}else if(TPM_TYPE.startsWith("机动车销售发票")){	
				m.put("ICON", "fp.png");
			}else if(TPM_TYPE.startsWith("商业保险单及保险批单")){	
				m.put("ICON", "bx.png");
			}else if(TPM_TYPE.startsWith("车辆登记证")){	
				m.put("ICON", "car-dj.png");
			}else if(TPM_TYPE.startsWith("车辆登记证（含抵押信息）")){	
				m.put("ICON", "car-dj.png");
			}else if(TPM_TYPE.startsWith("银行委托扣款账户")){	
				m.put("ICON", "zh.png");
			}else if(TPM_TYPE.startsWith("补充资料")){
				m.put("ICON", "bc.png");
			}else if(TPM_TYPE.startsWith("近两年财务报表")){	
				m.put("ICON", "sq.png");
			}else if(TPM_TYPE.startsWith("上年度完税证明")){	
				m.put("ICON", "cb.png");
			}else if(TPM_TYPE.startsWith("业务往来资料")){	
				m.put("ICON", "wl.png");
			}else if(TPM_TYPE.startsWith("行驶证")){	
				m.put("ICON", "xs.png");
			}else if(TPM_TYPE.startsWith("GPS加装激活证明")){	
				m.put("ICON", "GPS.png");
			}else if(TPM_TYPE.startsWith("家访报告")){	
				m.put("ICON", "jf.png");
			}else{
				m.put("ICON", "sq.png");
			}
			if(TPM_TYPE.endsWith("(必选)") ){
				m.put("ICON", m.get("ICON").toString().replaceFirst("\\.", "-blue."));
			}
			// 去掉必选非必选
			m.put("TPM_TYPE", TPM_TYPE.replaceAll("\\(非必选\\)", "").replaceAll("\\(必选\\)", ""));
			
			
			
			Map<String, Object> p1 = new HashMap<String, Object>();
			p1.put("PARENT_ID", m.get("ID"));
			List<Map<String, Object>> ls = Dao.selectList("materialMgt.getFpfs", p1);
			m.put("json", JSONObject.fromObject(m).toString() );
			m.put("files", ls);
		}
		Page page = new Page();
		page.addDate(JSONArray.fromObject(listSJZD), listSJZD.size());
		return page;
	}

	/**
	 * @param status null 时不处理这个值
	 * @author LUYANFENG @ 2015年5月14日
	 */
	public boolean checkProjectMan(String PROJECT_ID , int... status) {
		Map<String,Object> qryMap = new HashMap<String,Object>();
		qryMap.put("CREATE_ID", Security.getUser().getId());
		qryMap.put("ID", PROJECT_ID);
		Map<String,Object> porjectMap = Dao.selectOne("weixin_view.check_project_man", qryMap);
		if(porjectMap != null && !porjectMap.isEmpty()){
			if(status == null){
				return true;
			}
			Object status_ = porjectMap.get("STATUS");
			Arrays.sort(status);
			if( Arrays.binarySearch(status, Integer.valueOf(status_.toString())) != -1){
				return true;
			}
		}else{
			throw new ActionException("您不能进行此操作");
		}
		return false ;
		
	}

	public List<Map<String, Object>> getFiles(String ID) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ID", ID);
		return Dao.selectList("weixin_view.getFiles" , param);
	}
}

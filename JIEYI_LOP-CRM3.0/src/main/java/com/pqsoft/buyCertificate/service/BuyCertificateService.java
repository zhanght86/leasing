package com.pqsoft.buyCertificate.service;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.DateSiteUtil;
import com.pqsoft.util.FileCatalogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class BuyCertificateService {
	//private Logger logger = Logger.getLogger(BuyCertificateService.class);
	private final String PATH_BUYCERTIFICATE= "buyCertificate." ;
	
	/**
	 * add by lishuo 01-18-2016
	 * 查询lease_code
	 * @param param
	 * @return
	 */
	public String queryLease_Code(Map<String,Object> param){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "queryLease_Code",param);
	}
	
	/**
	 * add by lishuo 01-19-2016
	 * 查询lease_code
	 * @param param
	 * @return
	 */
	public String queryProject_ID(Map<String,Object> param){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "queryProject_ID",param);
	}
	/**
	 * 合格证查询(带分页)
	* @param @param map
	* @param @return
	* @return Page
	* @throws
	* @date 2014-5-15 下午07:39:21 
	* @author WuYanFei
	 */
	public Page getCertificatePage(Map<String,Object> map){
		map.put("CUST_SIGN_NAME1", map.get("CUST_SIGN_NAME")); //xml用CUST_SIGN_NAME1来做判断，不然会与fcc.name别名冲突 Add By YangJ 2014-5-26 上午10:25:52 
		Page page=new Page(map) ;
		map.put("ORD_ID", Security.getUser().getOrg().getId());
		List<Object> ListS=null;
		//List<Object> listC=null;
		/**合格证按合同编号倒序，有合格证的在后**/
		List<Map<String,Object>> listNoCert= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listHaveCert= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listAll= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> Buymanagelist=Dao.selectList(PATH_BUYCERTIFICATE + "getCertificatePage", map);
		
		for(Map<String,Object> m:Buymanagelist){
			String pay_id = m.get("PAY_ID").toString();
		    ListS = Dao.selectList(PATH_BUYCERTIFICATE + "getSCertificate",pay_id);
		    
		    //查找票据
		    //listC = Dao.selectList(PATH_BUYCERTIFICATE + "getCCertificate",pay_id);
		    //m.put("invoice", listC);
		    m.put("Certificate", ListS);
		    m.put("CertificateCount", ListS.size());
		    if(ListS.size()>0){
				 listHaveCert.add(m);
		    }else{
		    	 listNoCert.add(m);
		    }
		}
		listAll.addAll(listNoCert);
		listAll.addAll(listHaveCert);
		JSONArray array = JSONArray.fromObject(listAll);
		page.addDate(array, Dao.selectInt(PATH_BUYCERTIFICATE + "getCertificateCountPage", map)) ;
		return  page;
	}
	
	/**
	 * 车务请求交车
	 * @param map
	 * @return
	 */
	public Page getCertificatePage2(Map<String,Object> map){
		map.put("CUST_SIGN_NAME1", map.get("CUST_SIGN_NAME")); //xml用CUST_SIGN_NAME1来做判断，不然会与fcc.name别名冲突 Add By YangJ 2014-5-26 上午10:25:52 
		Page page=new Page(map) ;
		List<Object> ListS=null;
		//List<Object> listC=null;
		/**合格证按合同编号倒序，有合格证的在后**/
		List<Map<String,Object>> listNoCert= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listHaveCert= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listAll= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> Buymanagelist=Dao.selectList(PATH_BUYCERTIFICATE + "getCertificatePage2", map);
		
		for(Map<String,Object> m:Buymanagelist){
			String pay_id = m.get("PAY_ID").toString();
		    ListS = Dao.selectList(PATH_BUYCERTIFICATE + "getSCertificate",pay_id);
		    
		    //查找票据
		    //listC = Dao.selectList(PATH_BUYCERTIFICATE + "getCCertificate",pay_id);
		    //m.put("invoice", listC);
		    m.put("Certificate", ListS);
		    m.put("CertificateCount", ListS.size());
		    if(ListS.size()>0){
				 listHaveCert.add(m);
		    }else{
		    	 listNoCert.add(m);
		    }
		}
		listAll.addAll(listNoCert);
		listAll.addAll(listHaveCert);
		JSONArray array = JSONArray.fromObject(listAll);
		page.addDate(array, Dao.selectInt(PATH_BUYCERTIFICATE + "getCertificateCountPage2", map)) ;
		return  page;
	}

	public boolean doUploadPicture(Map<String, Object> param,String URL) {
		// 设置文件上传的大小 目前设置为50M
		final long MAX_SIZE = 50 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path.picture");
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		File file = null;
		// 如果是文件
		String fileName = param.get("FILE_NAME").toString() ;
		file = new File(path + File.separator + URL);// 原图文件目录
		if (!file.exists()) {// 检查磁盘上是否存在path路径文件
			file.mkdirs();// 在磁盘上创建路径
		}
		if (param.containsKey("VALUE2")) {
			file = new File(path + File.separator + URL + File.separator + UUID.randomUUID() + fileName + "." + param.get("VALUE2"));// 原图
		} else {
			file = new File(path + File.separator + URL + File.separator + UUID.randomUUID() + fileName);// 原图
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * 查询该目录下文件名称重复的最大序列的图片信息
	 */
	public Map<String, Object> selectRepeatFileMax(Map<String, Object> param) {
		return Dao.selectOne("fileCatalogUtil.selectRepeatFileMax", param);
	}
	/**
	 * 合格证查询
	 */
	public Page getCertificate(Map<String,Object> map){
		List<Object> ListS=null;
		List<Object> listC=null;
		List<Map<String,Object>> listAll= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> Buymanagelist=Dao.selectList(PATH_BUYCERTIFICATE + "getAllCertificate", map);
		for(Map<String,Object> m:Buymanagelist){
			String pay_id = m.get("PAY_ID").toString();
		    ListS = Dao.selectList(PATH_BUYCERTIFICATE + "getSCertificate",pay_id);
		    listC = Dao.selectList(PATH_BUYCERTIFICATE + "getCCertificate",pay_id);
		    m.put("invoice", listC);
		    m.put("Certificate", ListS);
		    m.put("CertificateCount", ListS.size());
		    listAll.add(m);
		}
		Page page = new Page(map);
		JSONArray array = JSONArray.fromObject(listAll);
		page.addDate(array, Dao.selectInt(PATH_BUYCERTIFICATE + "getAllCertificateCount", map));
		return  page;
	}
	
	
	/**
	 * 查询事业部
	 */
//	public Object getDisvsion()throws Exception{
//		return BaseDao.newInstance().selectForList("BuyCertificate.queryDisvison");
//	}
	
	/**
	 * 查询合格证图片
	 */
	public List<Map<String,Object>> getUploadFile(Map<String,Object> m){
		return Dao.selectList(PATH_BUYCERTIFICATE + "getCertifcateInfoUpload",m);
	}
	
	/**
	 * 删除合格证图片
	 */
	public boolean deleteUploadFile(Map<String,Object> m){
		return Dao.update(PATH_BUYCERTIFICATE + "deleteUploadFile",m)>0?true:false;
	}
	
	/**
	 * 判断合格证编号是否重复
	 */
	public List<Map<String,Object>> checkCode(Map<String,Object> m){
		return Dao.selectList(PATH_BUYCERTIFICATE + "checkCode",m);
	}
	/**
	 * 判断车架号是否重复
	 */
	public List<Map<String,Object>> CheckCarSysmle(Map<String,Object> m){
		return Dao.selectList(PATH_BUYCERTIFICATE + "CheckCarSysmle",m);
	}
	/**
	 * 判断发动机型号是否重复
	 */
	public List<Map<String,Object>> CheckEngineType(Map<String,Object> m){
		return Dao.selectList(PATH_BUYCERTIFICATE +  "CheckEngineType",m);
	}
	/**
	 * 合格证信息查看
	 */
	public Map<String,Object> checkCertificate(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "CertificateInfo", m);
	}
	/**
	 * 得到设备原始型号
	 */
	public Map<String,Object> getEquipCode(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "getEquipCode",m);
	}
	/**
	 * 获取联系人、支付表编号
	 */
	public Map<String,Object> getInfo(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "getInfo",m);
	}
	/**
	 * 合格证信息录入-带入设备信息
	 */
	public Map<String,Object> getEquipInfo(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "selectEquipInfo", m);
	}
	/**
	 * 合格证信息录入-带入支付表信息
	 */
	public Map<String,Object> getbuycontractInfo(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "selectbuyContractInfo",m);
	}
	/**
     * add by lishuo 01-17-2016
     * 合格证修改查看GPS信息
     */
    public List<Map<String,Object>> queryGPSMessage(Map<String,Object> m){
        return Dao.selectList(PATH_BUYCERTIFICATE + "queryGPSMessage", m);
    }
	/**
	 * 合格证新建-设备选择列表
	 */
	public List<Map<String,Object>> getEquipList(Map<String,Object> m){
		return Dao.selectList(PATH_BUYCERTIFICATE + "EquipListById", m);
	}
	/**
	 * add by lishuo 01-11-2016
	 * 合格证新建-设备选择列表
	 */
	public List<Map<String,Object>> gerMessage(Map<String,Object> m){
		return Dao.selectList(PATH_BUYCERTIFICATE + "gerMessage", m);
	}
	/**
	 * 查询最大流水号
	 */
	public String getMaxno(){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "getMaxno",null);
	}
	/**
	 * add by lishuo 01-14-2016
	 * 根据合同编号查询ProjectID
	 */
	public String queryProjectID(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "queryProjectID", m);
	}
	
	/**
	 * add by lishuo 01-14-2016
	 * 插入GPS表数据
	 */
	public boolean insertGPSINFO2(Map<String,Object> m){
		String ID=Dao.getSequence("seq_gps_id");
		m.put("ID", ID);
		return Dao.insert(PATH_BUYCERTIFICATE + "insertGPSINFO2", m)>0?true:false;
	}
	/**
	 * add by lishuo 01-14-2016
	 * 插入GPS表数据
	 */
	public boolean insertGPSINFO3(Map<String,Object> m){
		String ID=Dao.getSequence("seq_gps_id");
		m.put("ID", ID);
		return Dao.insert(PATH_BUYCERTIFICATE + "insertGPSINFO3", m)>0?true:false;
	}
	/**
	 * add by lishuo 01-14-2016
	 * 插入GPS表数据
	 */
	public boolean insertGPSINFO4(Map<String,Object> m){
		String ID=Dao.getSequence("seq_gps_id");
		m.put("ID", ID);
		return Dao.insert(PATH_BUYCERTIFICATE + "insertGPSINFO4", m)>0?true:false;
	}
	/**
	 * add by lishuo 01-14-2016
	 * 插入GPS表数据
	 */
	public boolean insertGPSINFO(Map<String,Object> m){
		String ID=Dao.getSequence("seq_gps_id");
		m.put("ID", ID);
		return Dao.insert(PATH_BUYCERTIFICATE + "insertGPSINFO", m)>0?true:false;
	}
	/**
	 * 合格证信息录入
	 */
	public boolean addCertificateInfo(Map<String,Object> m){
		return Dao.insert(PATH_BUYCERTIFICATE + "insertCertificate", m)>0?true:false;
	}
	/**
	 * 合格证信息修改
	 */
	public boolean updateCertificateInfo(Map<String,Object> m){
		return Dao.update(PATH_BUYCERTIFICATE + "updateCertificateById", m)>0?true:false;
	}
	/**
	 * 获取合格证id
	 */
	public String getCertificateid(){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "getCertificateid", null);
	}
	/**
	 * 获取图片id
	 */
	public String getCertimageid(){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "getCertimageid", null);
	}
	/**
	 * 上传图片-主表
	 */
	public String uploadImage(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "insertUploadImage", m);
	}
	/**
	 * 上传图片1
	 */
	public String uploadFile(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "insertUploadFile", m);
	}
	/**
	 * 上传图片2
	 */
	public String uploadFile2(Map<String,Object> m){
		return Dao.selectOne(PATH_BUYCERTIFICATE + "insertUploadFile2", m);
	}
	/**
	 * 更新设备下的合格证id
	 */
	public boolean  updateEquip_Certificateid(Map<String,Object> map){
		return Dao.update(PATH_BUYCERTIFICATE + "updateCertificate_id", map)>0?true:false;
	}
	
	/**
	 * add by lishuo 01-17-2016
	 * 更新GPS状态
	 */
	public boolean updateGpsInfo(Map<String,Object> map){
		return Dao.update(PATH_BUYCERTIFICATE + "updateGpsInfo", map)>0?true:false;
	}
	/**
	 * 更新合格证状态
	 */
	public boolean updateStatus(Map<String,Object> map){
		return Dao.update(PATH_BUYCERTIFICATE + "updateCertificate_status", map)>0?true:false;
	}
	/**
	 * 查询图片id
	 */
	public List<Map<String,Object>> selectFielId(String catalogId){
		return Dao.selectList(PATH_BUYCERTIFICATE + "selectFielId", catalogId);
	}


	public boolean uploadReceiptFile(Map<String,Object> map){
		return Dao.update(PATH_BUYCERTIFICATE+"uploadReceiptFile",map)>0?true:false;
	}
	
	/**
	 * 创建文件路径
	 * @param path
	 * @return
	 */
	public boolean createDirectory(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public Map<String,Object> uploadPic(Map<String,Object> param){
		Map<String,Object> refer = new HashMap<String,Object>() ;
		boolean flag=false ;
		String info ="上传图片失败";
		String path = param.get("FILE_PATH").toString() ; //上传图片路径
		refer.put("FLAG", flag) ;
		refer.put("INFO", info) ;
		refer.put("PATH", path) ;
		try{
			final long MAX_SIZE = 30*1024*1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			
			String root = "" ;//拿到配置文件中设置的上传文件路径
			createDirectory(root);//调用创建存放上传文件 文件夹方法

			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			
			File f = new File(param.get("FILE_PATH").toString()) ;
			List<File> fileList = new ArrayList<File>() ;
			fileList.add(f) ;
			Iterator<?> it = fileList.iterator();
			List filepathList = new ArrayList();
			
			File file = null;
			int num=1;
			Map<String,Object> map = new HashMap<String,Object>();
			InputStream is  = null;
			String file_name = "";
			String file_type = "";
			String file_id = "";
			map.put("USERNAME", param.get("USERNAME"));
//			//获取合格证id、图片id
//			String imgid=service.getCertimageid();
//			map.put("IMGID", imgid);
			String seqid=getCertificateid();
			map.put("CERTIFICATE_ID", seqid);
			while(it.hasNext()){
				FileItem fileitem = (FileItem)it.next();
				if(fileitem.isFormField()){
					map.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				}else {
					is  =   fileitem.getInputStream();
					file_name = fileitem.getName();
					file_type = fileitem.getName().toString().substring(fileitem.getName().toString().lastIndexOf(".")+1, fileitem.getName().toString().length());
					num++;
					FileCatalogUtil fcu = new FileCatalogUtil();
					String renter_name = map.get("RENTER_NAME")==null?"":map.get("RENTER_NAME").toString();
					String renter_code = map.get("RENTER_CODE")==null?"":map.get("RENTER_CODE").toString();
					String file_path = map.get("FILE_PATH").toString();
					String catalogId = fcu.getCatalogIdByPath(renter_name,renter_code,file_path);
					String CERTIFICATE_CODE=map.get("NO_1")==null?"":map.get("NO_1").toString();
					String remark = map.get("REMARK")==null?"":map.get("REMARK").toString();
					if(!fcu.isNull(catalogId)){
						file_id = fcu.insertFile(catalogId, file_name, file_type, is, remark);
					}
					map.put("IMGID", file_id);
					map.put("path", "流水号"+CERTIFICATE_CODE);
					map.put("fileName", file_name);
					uploadFile(map);//合格证上传图片
					uploadImage(map);//向合格证图片信息中间表插入数据
					flag=true ;
					info = "上传图片成功" ;
				}
			}
		}catch(Exception e){
			
		}
		return refer ;
	}
	public static List<Object> queryDataDictionary(String name) {
		return Dao.selectList("buyCertificate.queryDataDictionaryForName", name);
	}
	
	/**
	 * 添加签收单
	 * @param map
	 * @return
	 * @author King 2015年7月31日
	 */
	public int addQianshoudan(Map<String,Object> map){
		int num=Dao.update("buyCertificate.addQianshoudan", map);
		//查询出还款计划ID
		Map mapPay=Dao.selectOne("buyCertificate.queryPayIdByBuyCon",map);
		
		//查询已核销最大期数
		Map maxNum=Dao.selectOne("buyCertificate.queryBeginningMaxNum",map);
		//设定起租日
		if(mapPay !=null && mapPay.get("PAY_ID") !=null && !mapPay.get("PAY_ID").equals("")){
//			paymentService paymentSer=new paymentService();
			try{
				//先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
				Map<String,Object> ISCELXMap=Dao.selectOne("PayTask.querySchemeSFCELXByPay",mapPay);
				if(ISCELXMap !=null && ISCELXMap.get("VALUE_STR") !=null && ISCELXMap.get("VALUE_STR").equals("3")){
					
					DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,true);//参数PAY_ID,3固定为放款日期类型标示
					
				}else{
					
					//根据关键日期管理查询出还款日和起租日
					DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,false);//参数PAY_ID,3固定为放款日期类型标示
					
					Map<String,Object> mapbase = Dao.selectOne("PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);
					
					//查询起租日和还款日
					Map<String,Object> dataMap=Dao.selectOne("PayTask.queryPayDataByPayId", mapPay);
					
					if (mapbase != null) {
						mapbase.put("SCHEME_ID", mapbase.get("ID"));
						List<Map<String,Object>> clobList=Dao.selectList("leaseApplication.queryfil_scheme_clobForCs", mapbase);
						for (Map<String, Object> map2 : clobList) {
							mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
						}
						
						try{
							mapbase.put("AMOUNT", Dao.selectInt("PayTask.queryAmountCount", mapPay));
						}catch(Exception e){}
						
						
						mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
						mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
						mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
						mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
						mapbase.put("pay_way", mapbase.get("PAY_WAY"));
						
						mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
						mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
						mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
						mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
						
						mapbase.put("START_DATE", dataMap.get("START_DATE"));
						mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));
						
						JSONObject page=null;
						Class<?> cla = Class.forName("com.pqsoft.pay.service.PayTaskService");
						
						// add gengchangbao  2016年5月19日     JZZL-189 Start
						page = (JSONObject) cla.getMethod("quoteCalculateTest_New", Map.class).invoke(cla.newInstance(), mapbase);
						List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ZFSJ");
						int maxNumInt = 0;
						if (maxNum != null && maxNum.get("MAX_NUM") != null){
							maxNumInt = Integer.valueOf( maxNum.get("MAX_NUM").toString());
						}
						for(int hh=0;hh<irrList.size();hh++){
							Map mapIrr=irrList.get(hh);
							if (hh + 1 > maxNumInt) {
								continue;
							}
							if(mapIrr !=null){
								mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));
								mapIrr.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
								mapIrr.put("qc", (hh+1) + "");
								Dao.update("PayTask.updatePayDetailPayDate",mapIrr);
								
								Dao.update("PayTask.updateBeginningPayDate",mapIrr);
							}
						}
						// add gengchangbao  2016年5月19日     JZZL-189 Start
						// del gengchangbao  2016年5月19日     JZZL-189 Start
//						page = (JSONObject) cla.getMethod("quoteCalculateTest", Map.class).invoke(cla.newInstance(), mapbase);
//						List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
						
//						double ZJHJ = 0.00;//租金合计
//						for(int hh=0;hh<irrList.size();hh++){
//							//"zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
//							Map mapIrr=irrList.get(hh);
//							if(mapIrr !=null){
//								mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));
//								
//								ZJHJ = ZJHJ + Double.parseDouble((mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj")+"":"0");
//								//更新租金
//								mapIrr.put("ITEM_NAME", "租金");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新PMT租金
//								mapIrr.put("ITEM_NAME", "PMT租金");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("PMTzj") !=null && mapIrr.get("PMTzj") !="") ? mapIrr.get("PMTzj"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新本金
//								mapIrr.put("ITEM_NAME", "本金");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("bj") !=null && mapIrr.get("bj") !="") ? mapIrr.get("bj"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新利息
//								mapIrr.put("ITEM_NAME", "利息");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("lx") !=null && mapIrr.get("lx") !="") ? mapIrr.get("lx"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新剩余本金
//								mapIrr.put("ITEM_NAME", "剩余本金");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("sybj") !=null && mapIrr.get("sybj") !="") ? mapIrr.get("sybj"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新管理费
//								mapIrr.put("ITEM_NAME", "管理费");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("glf") !=null && mapIrr.get("glf") !="") ? mapIrr.get("glf"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新手续费
//								mapIrr.put("ITEM_NAME", "手续费");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("sxf") !=null && mapIrr.get("sxf") !="") ? mapIrr.get("sxf"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//								//更新利息增值税
//								mapIrr.put("ITEM_NAME", "利息增值税");
//								mapIrr.put("ITEM_MONEY", (mapIrr.get("lxzzs") !=null && mapIrr.get("lxzzs") !="") ? mapIrr.get("lxzzs"):"0");
//								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
//							}
//							
//							
//						}
//						
//						//更新还款计划主表租金合计
//						Map pmap=new HashMap();
//						pmap.put("ID", mapPay.get("PAY_ID"));
//						pmap.put("ZJHJ", ZJHJ);
//						Dao.update("PayTask.updatePayHeadMoneyAll",pmap);

						
						System.out.println("------------------------dataMap="+dataMap);
						//同步应收期初表数据
						// 同步财务数据
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("PAY_ID", mapPay.get("PAY_ID")+"");
						temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
//						temp.put("PMT", "PMT租金");
//						temp.put("ZJ", "租金");
//						temp.put("SYBJ", "剩余本金");
//						temp.put("D", "第");
//						temp.put("QI", "期");
//						// 删除财务表数据
//						Dao.delete("PayTask.deleteBeginning", temp);
//						Dao.insert("PayTask.synchronizationBeginning", temp);
						// del gengchangbao  2016年5月19日     JZZL-189 END
						
						//同步中间表
						//刷单条逾期数据
						Dao.update("PayTask.doDunDateByPaylist_code",temp);
						//Dao.update("PayTask.doFROMBEGINNGINTOJOIN_PRC_ONE",temp);
						
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		return num;
	}
}

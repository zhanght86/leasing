package com.pqsoft.base.product.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.SysDictionaryConstant;
import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.product.service.ProductService;
import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ProductAction extends Action {
	private String path = "base/product/";
	private ProductService service = new ProductService();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		List<Map<String, Object>> data = (List<Map<String, Object>>) SysDictionaryMemcached.getList("租赁物关联标志");
		if (data.size() > 0) {
			Map<String, Object> map = data.get(0);
			String code = map.get("CODE").toString();
			if (SysDictionaryConstant.RENTER_FLAG_COM.equals(code)) {
				return toMgProduct();
			} else if (SysDictionaryConstant.RENTER_FLAG_SUP.equals(code)) {
				return toMgProduct_a();
			} else {
				throw new ActionException("系统配置错误，请先配置租赁物关联关系！");
			}
		} else {
//			throw new ActionException("系统配置错误，请先配置租赁物关联关系！");
			return toMgProduct();	//默认转到与厂商关联
		}
	}
	
	public Reply toMgProduct(){
		VelocityContext context = new VelocityContext();
		//查询厂商(下拉选)
		CompanyService comService = new CompanyService();
		context.put("companys", comService.getAllCompany());
		//产品类别
		List<?> list = DataDictionaryMemcached.getList("产品类别");
		context.put("proType", list);
		
		//验证权限
		context.put("doAddProduct", Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-设备"}));//组织架构 刷新
		context.put("doAddXL", Security.hasPermission(new String []{"参数配置","车型数据设置","添加/修改-系列"}));//组织架构 刷新
		context.put("doAddXH", Security.hasPermission(new String []{"参数配置","车型数据设置","添加/修改-型号"}));//组织架构 修改
		
		return new ReplyHtml(VM.html(path+"Product.vm", context));
	}
	
	public Reply toMgProduct_a(){
		VelocityContext context = new VelocityContext();
		//查询经销商(下拉选)
		SuppliersService suppliersService = new SuppliersService();
		context.put("suppliers", suppliersService.showSuppliersList());
		//产品类别
		List<?> list = DataDictionaryMemcached.getList("产品类别");
		context.put("proType", list);
		
		//验证权限
		context.put("doAddProduct", Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-设备"}));//组织架构 刷新
		context.put("doAddXH", Security.hasPermission(new String []{"参数配置","车型数据设置","添加/修改-型号"}));//组织架构 修改
		
		return new ReplyHtml(VM.html(path+"Product_a.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProduct(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//查询所有产品大类
		param.put("COM_SUP_FLAG", SysDictionaryConstant.RENTER_FLAG_COM);
		List<Object> productList = service.getComPro(param);
		context.put("products", productList);
		
		//权限判断
		boolean addProductBig = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-设备"});//修改设备&设备是否可用的权限
		context.put("updateProductBig", addProductBig);
		return new ReplyAjax(VM.html(path+"Product_Big.vm", context));
	}  
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","列表显示"})
	@aDev(code = "180012", email = "zhangzhenlei@pqsoft.cn", name = "张振雷")
	public Reply getProduct_a(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param.put("COM_SUP_FLAG", SysDictionaryConstant.RENTER_FLAG_SUP);
		List<Object> productList = service.getCompanyProductByFlag(param);
		context.put("products", productList);
		 //权限判断
		boolean addProductBig = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-设备"});//修改设备&设备是否可用的权限
		context.put("updateProductBig", addProductBig);
		return new ReplyAjax(VM.html(path+"Product_Big_a.vm", context));
	}  
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProductCatena(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//通过产品大类id查询产品系别
		List<Object> productCatena = service.getProCatenaByProId(param);
		context.put("catenaList", productCatena);
		//权限判断
		boolean upCatenaPer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-系列"});//修改设备&设备是否可用的权限
		context.put("upCatenaPer", upCatenaPer);
		return new ReplyAjax(VM.html(path+"Product_Catena.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProductType(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//通过系列ID和制作商ID查询产品型号
		List<Object> productTypes = service.getProTypeList(param);
		context.put("Protypes", productTypes);
		
		//权限判断
		boolean upTypePer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-型号"});//修改设备&设备是否可用的权限
		context.put("upTypePer", upTypePer);
		return new ReplyAjax(VM.html(path+"Product_Type.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","列表显示"})
	@aDev(code = "180012", email = "zhangzhenlei@pqsoft.cn", name = "张振雷")
	public Reply getProductType_a(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//权限判断
		boolean upTypePer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-型号"});//修改设备&设备是否可用的权限
		context.put("upTypePer", upTypePer);
		//通过系列ID和制作商ID查询产品型号
		List<Object> productTypes = service.getTypeListByProId(param);
		context.put("Protypes", productTypes);
		return new ReplyAjax(VM.html(path+"Product_Type_a.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","添加/修改-设备"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveProduct(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		Boolean flag = true;
		String msg = "";
		//增加关联经销商
		List<Map<String, Object>> data = (List<Map<String, Object>>) SysDictionaryMemcached.getList("租赁物关联标志");
		if (data.size() > 0) {
			param.put("COM_SUP_FLAG", data.get(0).get("CODE").toString());
		} else {
//			throw new ActionException("系统配置错误，请先配置租赁物关联关系！");
			param.put("COM_SUP_FLAG", SysDictionaryConstant.RENTER_FLAG_COM);	//默认取与厂商关联
		}
		//增加关联经销商
		
		//判断修改还是添加
		if(param.containsKey("PRODUCT_ID") && param.get("PRODUCT_ID") !=null && !"".equals(param.get("PRODUCT_ID").toString())){
			result = service.updateProBig(param);
		}else{
            result = service.addProBig(param);			
		}
		if(result>0){
			flag = true ; 
			msg ="操作成功！";
			//查询所有产品大类
			List<Object> productList = service.getComPro(param);
			context.put("products", productList);
			//权限判断
			boolean addProductBig = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-设备"});//修改设备&设备是否可用的权限
			context.put("updateProductBig", addProductBig);
			return new ReplyAjax(flag,VM.html(path+"Product_Big.vm", context) ,msg);
		}else{
			flag = false ; 
			msg = "操作失败！" ;
			return new ReplyAjax(flag, msg);
		}
		 
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","添加/修改-系列"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveCatena(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		Boolean flag = true;
		String msg = "";
		//判断修改还是添加
		if(param.containsKey("CATENA_ID") && param.get("CATENA_ID") !=null && !"".equals(param.get("CATENA_ID").toString())){
			result = service.updateProCatena(param);
		}else{
            result = service.addProCatena(param);			
		}
		if(result>0){
			flag = true ; 
			msg ="操作成功！";
			///通过产品大类id查询产品系别
			List<Object> productCatena = service.getProCatenaByProId(param);
			context.put("catenaList", productCatena);
			//权限判断
			boolean upCatenaPer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-系列"});//修改设备&设备是否可用的权限
			context.put("upCatenaPer", upCatenaPer);
			return new ReplyAjax(flag,VM.html(path+"Product_Catena.vm", context) ,msg);
		}else{
			flag = false ; 
			msg = "操作失败！" ;
			return new ReplyAjax(flag, msg);
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","添加/修改-型号"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveType(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		Boolean flag = true;
		String msg = "";
		//判断修改还是添加
//		param.put("FULL_NAME", param.get("PRODUCT_NAME").toString()+param.get("NAME").toString());
		if(param.containsKey("ID") && param.get("ID") !=null && !"".equals(param.get("ID").toString())){
			result = service.updateProType(param);
		}else{
            result = service.addProType(param);			
		}
		if(result>0){
			flag = true ; 
			msg ="操作成功！";
			//通过系列ID和制作商ID查询产品型号
			List<Object> productTypes = service.getProTypeList(param);
			context.put("Protypes", productTypes);
			//权限判断
			boolean upTypePer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-型号"});//修改设备&设备是否可用的权限
			context.put("upTypePer", upTypePer);
			return new ReplyAjax(flag,VM.html(path+"Product_Type.vm", context) ,msg);
		}else{
			flag = false ; 
			msg = "操作失败！" ;
			return new ReplyAjax(flag, msg);
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","添加/修改-型号"})
	@aDev(code = "180012", email = "zhangzhenlei@pqsoft.cn", name = "张振雷")
	public Reply saveType_a(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		Boolean flag = true;
		String msg = "";
		//判断修改还是添加
//		param.put("FULL_NAME", param.get("PRODUCT_NAME").toString()+param.get("NAME").toString());
		if(param.containsKey("ID") && param.get("ID") !=null && !"".equals(param.get("ID").toString())){
			result = service.updateProType(param);
		}else{
            result = service.addProType(param);			
		}
		if(result>0){
			flag = true ; 
			msg ="操作成功！";
			//通过系列ID和制作商ID查询产品型号
			List<Object> productTypes = service.getTypeListByProId(param);
			context.put("Protypes", productTypes);
			//权限判断
			boolean upTypePer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-型号"});//修改设备&设备是否可用的权限
			context.put("upTypePer", upTypePer);
			return new ReplyAjax(flag,VM.html(path+"Product_Type_a.vm", context) ,msg);
		}else{
			flag = false ; 
			msg = "操作失败！" ;
			return new ReplyAjax(flag, msg);
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delProType(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		service.delTypeDepr(param);
		int result = service.delProType(param);
		if(result >0){
			flag = true ;
			msg = "删除成功！";
		}else{
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delProCatena(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		//通过系列ID查询产品型号
		int catenaTypeCount = service.getCatenaType(param);
		if(catenaTypeCount!=0){
			flag = false;
			msg = "请删除所属系列的型号";
			return new ReplyAjax(flag,msg);
		}
		int result = service.delProCatena(param);
		if(result >0){
			flag = true ;
			msg = "删除成功！";
		}else{
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delProduct(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		///通过产品大类id查询产品系别条数
		List<Object> productCatena = service.getProCatenaByProId(param);
		if(productCatena.size()!=0){
			flag = false;
			msg = "请删除所属设备的系列";
			return new ReplyAjax(flag,msg);
		}
		int result = service.delProduct(param);
		if(result >0){
			flag = true ;
			msg = "删除成功！";
		}else{
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","其他参数"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply saveTypeParamsPage(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("type_id", param.get("TYPE_ID"));
		List<Object> typeParams = (List<Object>) new SysDictionaryMemcached().get("产品参数");
		context.put("typeParams", typeParams);
		List<Object> deprParams = service.getDeprList(param);
		context.put("deprParams", deprParams);
		List<Object> paramValue = service.getTypeParams(param);
		//add gengchangbao 2016/1/26 JZZL-77 start
		List<?> modelYearParams = DataDictionaryMemcached.getList("款式");
		List<?> displacementParams = DataDictionaryMemcached.getList("排量");
		List<?> standWayParams = DataDictionaryMemcached.getList("变速箱");
		
		//del gengchangbao 2016/2/15 JZZL-95 start
		//List<?> wheelbaseParams = DataDictionaryMemcached.getList("车型");
		//del gengchangbao 2016/1/15 JZZL-95 end
		
		List<?> seatsParams = DataDictionaryMemcached.getList("座位数");
		context.put("modelYearParams", modelYearParams);
		context.put("displacementParams", displacementParams);
		context.put("standWayParams", standWayParams);
		//context.put("wheelbaseParams", wheelbaseParams);
		context.put("seatsParams", seatsParams);
		//add gengchangbao 2016/1/26 JZZL-77 end
		context.put("paramValueSize", paramValue.size());
		context.put("paramValue", paramValue);
		return new ReplyAjax(VM.html(path+"Product_Type_Params.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","车型数据设置","区域价格"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply getTypeCityPrice(){
		Map<String, Object> param = _getParameters();
		param.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		VelocityContext context = new VelocityContext();
		List<Object> cityPriceList = service.getTypeCityPrice(param);
		context.put("cityPriceList", cityPriceList);
		return new ReplyAjax(VM.html(path+"Product_Type_CityPrice.vm", context));
	}
	
	//add gengchangbao JZZL-171 2016年4月27日 start
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"参数配置","车型数据设置","区域价格"})
	@aDev(code = "170027", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply getTypeCityPriceForUpd(){
		Map<String, Object> param = _getParameters();
		param.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		VelocityContext context = new VelocityContext();
		List<Object> cityPriceList = service.getTypeCityPrice(param);
		context.put("param", param);
		context.put("cityPriceList", cityPriceList);
		Map<String,Object> cityPriceInfo = null;
		if (cityPriceList != null && cityPriceList.size() > 0) {
			for (int i = 0; i < cityPriceList.size() ; i++) {
				cityPriceInfo  = (Map<String, Object>) cityPriceList.get(i);
				cityPriceInfo.put("cityList", service.queryManagerArea(
						Security.getUser().getOrg().getPlatformId(),cityPriceInfo
						.get("PROVINCE") + "", 3));
				cityPriceInfo.put("index", i);
			}
			
		}
		context.put("AREAS", service.queryManagerArea(Security.getUser().getOrg().getPlatformId(),null, 2));
		
		context.put("CITYS", service.queryManagerArea(
				Security.getUser().getOrg().getPlatformId(), _getParameters()
				.get("AREA_ID") + "", 3));
		
		return new ReplyAjax(VM.html(path+"Product_Type_CityPriceForUpd.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170027", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply saveCityPrice(){
		Map<String, Object> params = JSONObject.fromObject(_getParameters().get("params"));
		boolean flag = true;
		String msg = "";
		try{
			Map<String, Object> cityPriceMap = null;
			String sealIndex = "0";//saveCityPrice
			service.deleteCityPrice(params);
			for (String key : params.keySet()) {
				if (key.contains("PROJECT_PROVINCE_")){
					cityPriceMap = new HashMap<String, Object>();
					sealIndex = key.replace("PROJECT_PROVINCE_", "");
					cityPriceMap.put("TYPE_ID", params.get("TYPE_ID"));
					cityPriceMap.put("PROJECT_PROVINCE", params.get("PROJECT_PROVINCE_"+sealIndex));
					cityPriceMap.put("PROJECT_CITY", params.get("PROJECT_CITY_"+sealIndex));
					cityPriceMap.put("PRICE", params.get("PRICE_"+sealIndex));
					//add gengchangbao JZZL-188 2016年5月16日10:17:14 Start
					cityPriceMap.put("CC_PRICE", params.get("CC_PRICE_"+sealIndex));
					//add gengchangbao JZZL-188 2016年5月16日10:17:14 End
					//add gengchangbao JZZL- 204 2016年6月15日09:53:56 Start
					cityPriceMap.put("REAL_PRICE", params.get("REAL_PRICE_"+sealIndex));
					//add gengchangbao JZZL-204 2016年6月15日09:54:03 End
					
					//add gengchangbao JZZL-265 2016年7月14日10:51:36 Start
					cityPriceMap.put("SYX", params.get("SYX_"+sealIndex));
					cityPriceMap.put("JQX", params.get("JQX_"+sealIndex));
					cityPriceMap.put("CCS", params.get("CCS_"+sealIndex));
					//add gengchangbao JZZL-265 2016年7月14日10:51:36 End
					service.addCityPrice(cityPriceMap);
				}
			}
		}catch(Exception e){
			flag = false;
			msg = "保存失败！";
		}
		
		return new ReplyAjax(flag, msg);
	}
	//add gengchangbao JZZL-171 2016年4月27日 end
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name ={"参数配置","车型数据设置","其他参数[保存按钮]"})
	@aDev(code = "170027", email = "wangjunjie_snolf@163.com", name = "王俊杰")
	public Reply saveTypeParams(){
		Map<String, Object> params = JSONObject.fromObject(_getParameters().get("params"));
		boolean flag = true;
		String msg = "";
		int result = 0;
		boolean saveDepr = saveDeprParams(params);
		JSONArray paramsList = JSONArray.fromObject(params.get("TYPE_PARAMS"));
		//add gengchangbao 2016/1/26 JZZL-77 Start
		String FULL_NAME = "";// 年款-排量-变速箱-车型-座位数
		String modelYear = "";//款式 
		String displacement = "";//排量
		String standWay = "";//变速箱
		String models = "";//车型
		String seats = "";//座位数
		if(params.get("PARAMETER_ID")!=null && !"".equals(params.get("PARAMETER_ID"))){
			for (Object temp : paramsList) {
				Map<String, Object> param = (Map<String, Object>) temp;
				param.put("TYPE_ID", params.get("TYPE_ID"));
				if (param != null && param.get("CODE") !=null) {
					if ("CHEXINGNIANKUAN".equals(param.get("CODE").toString())) {
						//款式 
						modelYear = param.get("TYPE_PARAM").toString();
						if("".equals(modelYear)){
							return new ReplyAjax(false, "请选择款式 ！");
						}
					}
					if ("PAIQILIANG".equals(param.get("CODE").toString())) {
						//排量
						displacement = param.get("TYPE_PARAM").toString();
						if("".equals(displacement)){
							return new ReplyAjax(false, "请选择排量！");
						}
					}
					if ("PAIDANGFANGSHI".equals(param.get("CODE").toString())) {
						//变速箱
						standWay = param.get("TYPE_PARAM").toString();
						if("".equals(standWay)){
							return new ReplyAjax(false, "请选择变速箱！");
						}
					}
					if ("ZHOUJU".equals(param.get("CODE").toString())) {
						//车型
						models = param.get("TYPE_PARAM").toString();
						if("".equals(models)){
							return new ReplyAjax(false, "请填写车型！");
							//return new ReplyAjax(false, "请选择车型！");
						}
					}
					if ("ZUOWEISHU".equals(param.get("CODE").toString())) {
						//座位数
						seats = param.get("TYPE_PARAM").toString();
						if("".equals(seats)){
							return new ReplyAjax(false, "请选择座位数！");
						}
					}
				}
			}
			
			for (Object temp : paramsList) {
				Map<String, Object> param = (Map<String, Object>) temp;
				param.put("TYPE_ID", params.get("TYPE_ID"));
				result = service.updateTypeParams(param);
			}
			Map<String, Object> proTypeParams = new HashMap<String, Object>();
			FULL_NAME = modelYear + " " + displacement + " "+standWay+" "+ models + " " +seats;
			proTypeParams.put("ID", params.get("TYPE_ID"));
			proTypeParams.put("FULL_NAME", FULL_NAME);
			service.updateProType(proTypeParams);
			
			if(result > 0){
				VelocityContext context = new VelocityContext();
				flag = true;
				msg = "修改产品参数成功";
				Map<String, Object> proTypeParamsUpd = new HashMap<String, Object>();
				proTypeParamsUpd.put("CATENA_ID", params.get("CATENA_ID"));
				//通过系列ID和制作商ID查询产品型号
				List<Object> productTypes = service.getProTypeList(proTypeParamsUpd);
				context.put("Protypes", productTypes);
				//权限判断
				boolean upTypePer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-型号"});//修改设备&设备是否可用的权限
				context.put("upTypePer", upTypePer);
				return new ReplyAjax(flag,VM.html(path+"Product_Type.vm", context) ,msg);
				//add gengchangbao 2016/1/26 JZZL-77 End
			}else {
				flag = false;
				msg = "修改产品参数失败";
			}
		}
		else {
			for (Object temp : paramsList) {
				//add gengchangbao 2016/1/26 JZZL-77 Start
				Map<String, Object> param = (Map<String, Object>) temp;
				param.put("TYPE_ID", params.get("TYPE_ID"));
				if (param != null && param.get("CODE") !=null) {
					if ("CHEXINGNIANKUAN".equals(param.get("CODE").toString())) {
						//款式 
						modelYear = param.get("TYPE_PARAM").toString();
						if("".equals(modelYear)){
							return new ReplyAjax(false, "请选择款式 ！");
						}
					}
					if ("PAIQILIANG".equals(param.get("CODE").toString())) {
						//排量
						displacement = param.get("TYPE_PARAM").toString();
						if("".equals(displacement)){
							return new ReplyAjax(false, "请选择排量！");
						}
					}
					if ("PAIDANGFANGSHI".equals(param.get("CODE").toString())) {
						//变速箱
						standWay = param.get("TYPE_PARAM").toString();
						if("".equals(standWay)){
							return new ReplyAjax(false, "请选择变速箱！");
						}
					}
					if ("ZHOUJU".equals(param.get("CODE").toString())) {
						//车型
						models = param.get("TYPE_PARAM").toString();
						if("".equals(models)){
							return new ReplyAjax(false, "请填写车型！");
							//return new ReplyAjax(false, "请选择车型！");
						}
					}
					if ("ZUOWEISHU".equals(param.get("CODE").toString())) {
						//座位数
						seats = param.get("TYPE_PARAM").toString();
						if("".equals(seats)){
							return new ReplyAjax(false, "请选择座位数！");
						}
					}
				}
				//add gengchangbao 2016/1/26 JZZL-77 End
			}
			for (Object temp : paramsList) {
				//add gengchangbao 2016/1/26 JZZL-77 Start
				Map<String, Object> param = (Map<String, Object>) temp;
				param.put("TYPE_ID", params.get("TYPE_ID"));
				//add gengchangbao 2016/1/26 JZZL-77 End
				result = service.addTypeParams(param);
				
			}
			//add gengchangbao 2016/1/26 JZZL-77 Start
			Map<String, Object> proTypeParams = new HashMap<String, Object>();
			FULL_NAME = modelYear + " " + displacement + " "+standWay+" "+ models + " " +seats;
			proTypeParams.put("ID", params.get("TYPE_ID"));
			proTypeParams.put("FULL_NAME", FULL_NAME);
			service.updateProType(proTypeParams);
			if(result > 0){
				VelocityContext context = new VelocityContext();
				flag = true;
				msg = "添加产品参数成功";
				Map<String, Object> proTypeParamsAdd = new HashMap<String, Object>();
				proTypeParamsAdd.put("CATENA_ID", params.get("CATENA_ID"));
				//通过系列ID和制作商ID查询产品型号
				List<Object> productTypes = service.getProTypeList(proTypeParamsAdd);
				context.put("Protypes", productTypes);
				//权限判断
				boolean upTypePer = Security.hasPermission(new String[]{"参数配置","车型数据设置","添加/修改-型号"});//修改设备&设备是否可用的权限
				context.put("upTypePer", upTypePer);
				return new ReplyAjax(flag,VM.html(path+"Product_Type.vm", context) ,msg);
				//add gengchangbao 2016/1/26 JZZL-77 End
			}else {
				flag = false;
				msg = "添加产品参数失败";
			}
		}
		return new ReplyAjax(flag, msg);
	}
//	TYPE_ID		PERIOD(期数)传		DEPR_RATE(折旧率)传		RESI_RATE(残值率)算
	/**
	 * 保存折旧率信息
	 * Author: SnowWolf
	 * Time: 2015年3月4日
	 * @param params
	 * @return boolean
	 */
	public boolean saveDeprParams(Map<String, Object> params){
		service.delTypeDepr(params);
		//保存折旧率
		float depr_rate = 0 ;
		JSONArray deprList = JSONArray.fromObject(params.get("DEPR_PARAMS"));
		for (Object deprParam : deprList) {
			Map<String, Object> param = (Map<String, Object>) deprParam;
			param.put("TYPE_ID", params.get("TYPE_ID"));
			depr_rate += Float.parseFloat(param.get("DEPR_RATE").toString());
			float RESI_RATE = 1 - depr_rate;
			param.put("RESI_RATE", RESI_RATE);
			service.addTypeDepr(param);
		}
		return true;
	}
	
}

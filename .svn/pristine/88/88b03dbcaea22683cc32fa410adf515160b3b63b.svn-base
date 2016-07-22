package com.pqsoft.base.productmMobile.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.product.service.ProductService;
import com.pqsoft.base.productmMobile.service.ProductMobileService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ProductMobileAction extends Action {
	private String path = "base/productMobile/";
	private ProductMobileService service = new ProductMobileService();

	@Override
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"基础信息管理","手机端产品管理","列表"})
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		//查询厂商(下拉选)
		CompanyService comService = new CompanyService();
		context.put("companys", comService.getAllCompany());
		//验证权限
		return new ReplyHtml(VM.html(path+"ProductMobile.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"基础信息管理","手机端产品管理","查询[按钮]产品大类"})
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getProduct(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//查询所有产品大类
		List<Object> productList = service.getProBig(param);
		context.put("products", productList);
		
		//权限判断
		boolean addProductBig = Security.hasPermission(new String[]{"基础信息管理","手机端产品管理","添加/修改-设备信息"});//修改设备&设备是否可用的权限
		context.put("updateProductBig", addProductBig);
		return new ReplyAjax(VM.html(path+"ProductMobile_Big.vm", context));
	}  
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"基础信息管理","手机端产品管理","查询对应产品系列"})
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getProductCatena(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//通过产品大类id查询产品系别
		List<Object> productCatena = service.getProCatenaByProId(param);
		context.put("catenaList", productCatena);
		//权限判断
		boolean upCatenaPer = Security.hasPermission(new String[]{"基础信息管理","手机端产品管理","添加/修改-设备系列信息"});//修改设备&设备是否可用的权限
		context.put("upCatenaPer", upCatenaPer);
		return new ReplyAjax(VM.html(path+"ProductMobile_Catena.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"基础信息管理","手机端产品管理","查询产品型号"})
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getProductType(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//通过系列ID和制作商ID查询产品型号
		List<Object> productTypes = service.getProTypeList(param);
		context.put("Protypes", productTypes);
		
		//权限判断
		boolean upTypePer = Security.hasPermission(new String[]{"基础信息管理","手机端产品管理","修改设备型号可见状态"});//修改设备型号可见状态的权限
		context.put("upTypePer", upTypePer);
		return new ReplyAjax(VM.html(path+"ProductMobile_Type.vm", context));
	}
	
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"基础信息管理","手机端产品管理","修改设备型号可见状态"})
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply updProTypeSo(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		Boolean flag = true;
		String msg = "";
			result = service.updateProTypeSo(param);
		if(result>0){
			flag = true ; 
			msg ="操作成功！";
			//通过系列ID和制作商ID查询产品型号
			Map m=service.getslidzzid(param);
			List<Object> productTypes = service.getProTypeList(m);
			context.put("Protypes", productTypes);
			//权限判断
			boolean upTypePer = Security.hasPermission(new String[]{"基础信息管理","手机端产品管理","添加/修改-设备型号信息"});//修改设备&设备是否可用的权限
			context.put("upTypePer", upTypePer);
			return new ReplyAjax(flag,VM.html(path+"ProductMobile_Type.vm", context) ,msg);
		}else{
			flag = false ; 
			msg = "操作失败！" ;
			return new ReplyAjax(flag, msg);
		}
	}
	
	
	

}

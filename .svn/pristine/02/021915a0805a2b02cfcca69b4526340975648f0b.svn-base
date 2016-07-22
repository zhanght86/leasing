package com.pqsoft.action;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.product.service.ProductService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.osi.api.IDCard;
import com.pqsoft.skyeye.osi.api.Nciic;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.PageUtil;

/**
 * @author King 2013-9-13
 */

public class DemoAction extends Action {
	private BaseSchemeService service = new BaseSchemeService();

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "xgm")
	public Reply execute() {
		Map<String,Object> param=_getParameters();
		param.put("CUST_ID", param.get("CLIENT_ID"));
		String msg = "验证成功！";
		Boolean flag = true ;
		Map<String,Object> client=service.getClient(param);
		if(client==null)
		{
			msg="系统中无此客户！";
			flag = false ;
		}else if("".equals(client.get("NAME"))){
			msg="系统中无此客户！";
			flag = false ;
		}else if("".equals(client.get("ID_CARD_NO"))){
			msg="身份证号为空，请先录入身份证号！";
			flag = false ;
		}
		List<Map<String, Object>> list = service.getIdCard(param);
		if(list.size()>0)
		{
			msg="系统已验证此客户！";
			flag = true ;
		}else{
		List<Object> sbm=(List)new SysDictionaryMemcached().get("SBM");
		Map<String,Object> mapsbm=(Map<String,Object>) sbm.get(0);
		String xml="<ROWS><INFO><SBM>"+mapsbm.get("CODE")+"</SBM></INFO><ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW>"
				+"<ROW FSD='北京' YWLX='租赁'><GMSFHM>"+client.get("ID_CARD_NO")+"</GMSFHM><XM>"+client.get("NAME")+"</XM></ROW></ROWS>";
		String LICENSE="";
		try {
			 xml=Nciic.executeClient(LICENSE, xml);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="身份验证失败！";
			flag = false ;
		}
		
		Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); 
            Element rootElt = doc.getRootElement(); 
            
            int number=0;
            
            //成功回传
            Iterator iter = rootElt.elementIterator("ROW"); 
            
            
            Map<String,Object> m=new HashMap<String,Object>();
            m.put("CUST_ID", param.get("CLIENT_ID"));
            // 遍历head节点
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                Iterator OUTPUT = recordEle.elementIterator("OUTPUT"); 
                while (OUTPUT.hasNext()) {
                	number++;
                    Element itemEle = (Element) OUTPUT.next();
                    Iterator ITEM = itemEle.elementIterator("ITEM"); 
                    while (ITEM.hasNext()) {
                        Element itemItem = (Element) ITEM.next();
                        String gmsfhm = itemItem.elementTextTrim("gmsfhm"); 
                        if(gmsfhm !=null && !"".equals(gmsfhm))
                        {
                        	m.put("CARD_ID", gmsfhm);
                        }
                        String result_gmsfhm = itemItem.elementTextTrim("result_gmsfhm");
                        if(result_gmsfhm !=null && !"".equals(result_gmsfhm))
                        {
                        	m.put("ID_CHECK_RESULT", result_gmsfhm);
                        }
                        String xm = itemItem.elementTextTrim("xm"); 
                        if(xm !=null && !"".equals(xm))
                        {
                        	m.put("CARD_NAME", xm);
                        }
                        String result_xm = itemItem.elementTextTrim("result_xm");
                        if(result_xm !=null && !"".equals(result_xm))
                        {
                        	m.put("NAME_CHECK_RESULT", result_xm);
                        }
                        String cym = itemItem.elementTextTrim("cym");
                        if(cym !=null && !"".equals(cym))
                        {
                        	m.put("CYM", cym);
                        }
                        String result_cym = itemItem.elementTextTrim("result_cym");
                        if(result_cym !=null && !"".equals(result_cym))
                        {
                        	m.put("result_cym", result_cym);
                        }
                        String xb = itemItem.elementTextTrim("xb"); 
                        if(xb !=null && !"".equals(xb))
                        {
                        	m.put("GENDER", xb);
                        }
                        String result_xb = itemItem.elementTextTrim("result_xb"); 
                        if(result_xb !=null && !"".equals(result_xb))
                        {
                        	m.put("result_xb", result_xb);
                        }
                        String mz = itemItem.elementTextTrim("mz");
                        if(mz !=null && !"".equals(mz))
                        {
                        	m.put("RACE", mz);
                        }
                        String result_mz = itemItem.elementTextTrim("result_mz");
                        if(result_mz !=null && !"".equals(result_mz))
                        {
                        	m.put("result_mz", result_mz);
                        }
                        String csrq = itemItem.elementTextTrim("csrq");
                        if(csrq !=null && !"".equals(csrq))
                        {
                        	m.put("BIRTH_DATE", csrq);
                        }
                        String result_csrq = itemItem.elementTextTrim("result_csrq");
                        if(result_csrq !=null && !"".equals(result_csrq))
                        {
                        	m.put("result_csrq", result_csrq);
                        }
                        String fwcs = itemItem.elementTextTrim("fwcs");
                        if(fwcs !=null && !"".equals(fwcs))
                        {
                        	m.put("FWCS", fwcs);
                        }
                        String result_fwcs = itemItem.elementTextTrim("result_fwcs");
                        if(result_fwcs !=null && !"".equals(result_fwcs))
                        {
                        	m.put("result_fwcs", result_fwcs);
                        }
                        String whcd = itemItem.elementTextTrim("whcd");
                        if(whcd !=null && !"".equals(whcd))
                        {
                        	m.put("WHCD", whcd);
                        }
                        String result_whcd = itemItem.elementTextTrim("result_whcd");
                        if(result_whcd !=null && !"".equals(result_whcd))
                        {
                        	m.put("result_whcd", result_whcd);
                        }
                        String hyzk = itemItem.elementTextTrim("hyzk");
                        if(hyzk !=null && !"".equals(hyzk))
                        {
                        	m.put("HYZT", hyzk);
                        }
                        String result_hyzk = itemItem.elementTextTrim("result_hyzk");
                        if(result_hyzk !=null && !"".equals(result_hyzk))
                        {
                        	m.put("result_hyzk",result_hyzk);
                        }
                        String zz = itemItem.elementTextTrim("zz");
                        if(zz !=null && !"".equals(zz))
                        {
                        	m.put("ZZ", zz);
                        }
                        String result_zz = itemItem.elementTextTrim("result_zz");
                        if(result_zz !=null && !"".equals(result_zz))
                        {
                        	m.put("result_zz", result_zz);
                        }
                        String jgssx = itemItem.elementTextTrim("jgssx");
                        if(jgssx !=null && !"".equals(jgssx))
                        {
                        	m.put("JGSSX", jgssx);
                        }
                        String result_jgssx = itemItem.elementTextTrim("result_jgssx");
                        if(result_jgssx !=null && !"".equals(result_jgssx))
                        {
                        	m.put("result_jgssx", result_jgssx);
                        }
                        String csdssx = itemItem.elementTextTrim("csdssx");
                        if(csdssx !=null && !"".equals(csdssx))
                        {
                        	m.put("CSDSSX", csdssx);
                        }
                        String result_csdssx = itemItem.elementTextTrim("result_csdssx");
                        if(result_csdssx !=null && !"".equals(result_csdssx))
                        {
                        	m.put("result_csdssx", result_csdssx);
                        }
                        String xp = itemItem.elementTextTrim("xp");
                        if(xp !=null && !"".equals(xp))
                        {
                        	m.put("IMAGE", xp);
                        }
                        String result_name = itemItem.elementTextTrim("result_name");
                        if(result_name !=null && !"".equals(result_name))
                        {
                        	m.put("SXBZXR", result_name);
                        }
                        String result_cust_code = itemItem.elementTextTrim("result_cust_code");
                        if(result_cust_code !=null && !"".equals(result_cust_code))
                        {
                        	m.put("CASE_CODE", result_cust_code);
                        }
                        String result_gist_unit = itemItem.elementTextTrim("result_gist_unit");
                        if(result_gist_unit !=null && !"".equals(result_gist_unit))
                        {
                        	m.put("GIST_UNIT", result_gist_unit);
                        }
                        String result_area_name = itemItem.elementTextTrim("result_area_name");
                        if(result_area_name !=null && !"".equals(result_area_name))
                        {
                        	m.put("AREA_NAME", result_area_name);
                        }
                        String result_performance = itemItem.elementTextTrim("result_performance");
                        if(result_performance !=null && !"".equals(result_performance))
                        {
                        	m.put("PERFORMANCE", result_performance);
                        }
                        String result_disreput_type_name = itemItem.elementTextTrim("result_disreput_type_name");
                        if(result_disreput_type_name !=null && !"".equals(result_disreput_type_name))
                        {
                        	m.put("DISREPUT_TYPE_NAME", result_disreput_type_name);
                        }
                        String result_publish_date = itemItem.elementTextTrim("result_publish_date");
                        if(result_publish_date !=null && !"".equals(result_publish_date))
                        {
                        	m.put("PUBLISH_DATE", result_publish_date);
                        }
                        String ERROR_MESAGE = itemItem.elementTextTrim("errormesage");
                        if(ERROR_MESAGE !=null && !"".equals(ERROR_MESAGE))
                        {
                        	m.put("ERROR_MESAGE", ERROR_MESAGE);
                        	msg = ERROR_MESAGE;
                        }
                        String errormesagecol = itemItem.elementTextTrim("errormesagecol");
                        if(errormesagecol !=null && !"".equals(errormesagecol))
                        {
                        	m.put("ERROR_MESAGECOL", errormesagecol);
                        }
                        String ErrorCode = itemItem.elementTextTrim("ErrorCode");
                        if(ErrorCode !=null && !"".equals(ErrorCode))
                        {
                        	m.put("ERROR_CODE", ErrorCode);
                        }
                        String ErrorMsg = itemItem.elementTextTrim("ErrorMsg");
                        if(ErrorMsg !=null && !"".equals(ErrorMsg))
                        {
                        	m.put("ERROR_MSG", ErrorMsg);
                        }
                    }
                }
            }
            
            if(number == 0){//说明异常返回结果
            	
            		Iterator ROWS = rootElt.elementIterator("ROWS");
            		while (ROWS.hasNext()) {
            			Element rowEle = (Element) ROWS.next();
                		Iterator ROW = rowEle.elementIterator("ROW");
                		
                		Element itemItem = (Element) ROW.next();
                        String ErrorMsg = itemItem.elementTextTrim("ErrorMsg");
                        msg=ErrorMsg;
                        flag = true ;
            		}
            		
            		
            }
            
            
            if(number>0){
            	m.put("CREATE_CUST_ID", Security.getUser().getId());
                m.put("CREATE_CUST", Security.getUser().getName());
                service.insAuthentication(m);
            }
            
        } catch (DocumentException e) {
            e.printStackTrace();
            msg="读取身份验证信息失败！";
			flag = false ;
        } catch (Exception e) {
            e.printStackTrace();
            msg="读取身份验证信息失败！";
			flag = false ;
        }}
        return new ReplyAjax(flag, msg);
	}

	/**
	 * 列右侧冻结demo
	 * 
	 * @return
	 * @author:King 2013-9-13
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toDemo() {
		VelocityContext context = new VelocityContext();
		context.put("PAY_WAY", new DataDictionaryMemcached().get("还款政策"));
		context.put("BUSINESS_MODEL", new DataDictionaryMemcached().get("立项业务类型"));
		context.put("STATUS", new DataDictionaryMemcached().get("商务政策状态"));
		context.put("PLEDGE_STATUS", new DataDictionaryMemcached().get("牌抵挂"));
		context.put("PAYMENT_STATUS", new DataDictionaryMemcached().get("放款政策"));
		context.put("SALE_MODEL", new DataDictionaryMemcached().get("销售模式"));
		context.put("POWER", new DataDictionaryMemcached().get("动力类型"));
		context.put("APPLIY_SCOPE", new DataDictionaryMemcached().get("商务政策使用范围"));
		context.put("RENTAL_PACKAGE", new DataDictionaryMemcached().get("租赁包"));
		context.put("COMPANY_ID", new CompanyService().getAllCompany());
		// context.put("SUPPLIER_ID", new
		// SuppliersService().queryAllSuppleirs(new HashMap<String,Object>()));
		context.put("PRODUCT_ID", new ProductService().getProBig(null));
		return new ReplyHtml(VM.html("demo/demo.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply baseSchemeListPage() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.remove("param");
		param.putAll(json);
		return new ReplyAjaxPage(service.baseSchemeManager(param));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply id5() {
		IDCard card = new IDCard();
		card.setName("李超");
		card.setIdCard("340822198810096216");
		card.check();
		return new ReplyHtml("<img src='data:image/png;base64," + card.getPhoto() + "' />");
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply tovmlist() {
		return new ReplyHtml(VM.html("demo/demo.vm", null));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply vmlist(){
//		int a=1;
//		for(int i=0;i<100;i++){
//			Map<String,Object> map=new HashMap<String,Object>();
//			if(i%2==0){
//				map.put("TERM", a);
//				map.put("CODE", "利息");
//				map.put("MONEY", 100);
////				map.put("ZJ", 200);
//			}else{
//				map.put("TERM", a);
//				map.put("CODE", "本金");
//				map.put("MONEY", 200);
////				map.put("ZJ", 200);
//			}
//			list.add(map);
//			
////			if(i%10==3){
////				Map<String,Object> mm=new HashMap<String,Object>();
////				mm.put("TERM", a);
////				mm.put("CODE", "违约金");
////				mm.put("MONEY", 10);
////				list.add(mm);
////			}
//			if(i%2==1&&i!=0)
//				a++;
//		}
////		context.put("list", list);
//		Page page=new Page();
//		page.addDate(JSONArray.fromObject(list), list.size());
		return new ReplyAjaxPage(PageUtil.getPage(_getParameters(), "demo.queryList", "demo.queryListCount"));
	}
}

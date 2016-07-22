$(function(){
	/*2016年4月22日 11:46:11 吴国伟去掉 disable*/
	//disChange();
});	
function saveChange(){
	
		var PROJECT_ID = $("#PROJECT_ID").val();
		var SCHEME_ROW_NUM = $("#SCHEME_ROW_NUM").val();
		var SCHEME_ID = $("#SCHEME_ID").val();
		
		var SUPPLIERS_NAME =  $("#SUPPLIERS_ID").find("option:selected").text();//经销商
		var COMPANY_NAME = $("#COMPANY_ID").find("option:selected").text();//厂商
		var PRODUCT_NAME = $("#PRODUCT_ID").find("option:selected").text();//品牌
		var CATENA_NAME = $("#CATENA_ID").find("option:selected").text();//车系
		var SPEC_NAME = $("#SPEC_ID").find("option:selected").text();//型号
		
		
		var CAR_COLOR = $("#CAR_COLOR").val();//颜色
		var XH_PARAM = $("#XH_PARAM").val();//型号参数
		var PRO_REMARK = $("#PRO_REMARK").val();//备注
		
		var SCHEME_CODE = $("#SCHEME_CODE").val();//产品名称
		var lease_term = $("#lease_term").val();//期数
		
		var sscgj = $("#sscgj").val();//车辆实际采购价
		var syx = $("#syx").val();//商业险
		var ccs = $("#ccs").val();//车船税
		var jqx = $("#jqx").val();//交强险
		
		var gzs = $("#gzs").val();//购置税
		var spf = $("#spf").val();//上牌费
		var lqf = $("#lqf").val();//路桥费
		var lpf = $("#lpf").val();//临牌费
		var hbbf = $("#hbbf").val();//环保标费
		var qtfy = $("#qtfy").val();//其他费用
		
		var bgyy = $("#bgyy").val();//变更原因
		var bgbz = $("#bgbz").val();//变更备注
		
		var data = {};
		
		//------------保存到项目设备表------BEGIN-------------
		data.PROJECT_ID = PROJECT_ID;
		data.SCHEME_ROW_NUM = SCHEME_ROW_NUM;
		data.SCHEME_ID = SCHEME_ID;
		
		data.SUPPLIERS_NAME = SUPPLIERS_NAME;
		data.COMPANY_NAME = COMPANY_NAME;
		data.PRODUCT_NAME = PRODUCT_NAME;
		data.CATENA_NAME = CATENA_NAME;
		data.SPEC_NAME = SPEC_NAME;
		
		data.CAR_COLOR = CAR_COLOR;
		data.XH_PARAM = XH_PARAM;
		data.PRO_REMARK = PRO_REMARK;
		data.TOTAL_PRICE = sscgj;
		data.BX = syx;
		data.CCS = ccs;
		data.JQX = jqx;
		
		data.gzs = gzs;
		data.spf = spf;
		data.lqf = lqf;
		data.lpf = lpf;
		data.hbbf = hbbf;
		data.qtfy = qtfy;
		
		
		//data.SCHEME_CODE = SCHEME_CODE;
		data.SCHEME_ID = SCHEME_CODE;
		//------------保存到项目设备表------END-----------
		
		//----------保存到项目方案表：期数-------BEGIN-------
		data.LEASE_TERM = lease_term;
		//----------保存到项目方案表：期数--------------
		
		
		//---------保存到项目表：变更原因  变更备注-----BEGIN-------
		data.CHANGERES = bgyy;
		data.CHANGEMEMO = bgbz;
		//---------保存到项目表：变更原因  变更备注------END------
		
		$.ajax({
    		url:_basePath+"/proChange/ProChange!saveProjectChange.action",
    		data : data,
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			if(json.flag){
    				jQuery.messager.alert("提示","保存成功!");
    		
    			}else{
    				alert(json.msg);
    			}
    		}
    	});
	}
	//当页面申请状态不是新建的情况下，所有内容都设置为不可输入---by tianhuihui

	function disChange(){
		if($("#askStatus").val()!=1){
			$("input").attr("disabled",true);
			$("textarea").attr("disabled",true);
			$("select").attr("disabled",true);
			$("button").attr("disabled",true);
		}

	}
	
	function submitChange(){
		
		var SUPPLIERS_ID_OLD = $("#SUPPLIERS_ID_OLD").val();
		var COMPANY_ID_OLD = $("#COMPANY_ID_OLD").val();
		var PRODUCT_ID_OLD = $("#PRODUCT_ID_OLD").val();
		var CATENA_ID_OLD = $("#CATENA_ID_OLD").val();
		var SPEC_ID_OLD = $("#SPEC_ID_OLD").val();
		
		var CAR_COLOR_OLD = $("#CAR_COLOR_OLD").val();
		var XH_PARAM_OLD = $("#XH_PARAM_OLD").val();
		var PRO_REMARK_OLD =$("#PRO_REMARK_OLD").val(); 
		
		var SCHEME_CODE_OLD_STR = $("#SCHEME_CODE_OLD_STR").attr("STR");
		var lease_term_OLD = $("#lease_term_OLD").val();
		
		var sscgj_OLD = $("#sscgj_OLD").val();
		var gzs_OLD = $("#gzs_OLD").val();
		var syx_OLD = $("#syx_OLD").val();
		var ccs_OLD = $("#ccs_OLD").val();
		var spf_OLD = $("#spf_OLD").val();
		var lqf_OLD = $("#lqf_OLD").val();
		var lpf_OLD = $("#lpf_OLD").val();
		var hbbf_OLD = $("#hbbf_OLD").val();
		var jqx_OLD= $("#jqx_OLD").val();
		var qtfy_OLD = $("#qtfy_OLD").val();
		
		
		var PROJECT_ID = $("#PROJECT_ID").val();
		var SCHEME_ROW_NUM = $("#SCHEME_ROW_NUM").val();
		var SCHEME_ID = $("#SCHEME_ID").val();
		
		var SUPPLIERS_NAME =  $("#SUPPLIERS_ID").find("option:selected").text();//经销商
		var COMPANY_NAME = $("#COMPANY_ID").find("option:selected").text();//厂商
		if(COMPANY_NAME == "--请选择--"){
			alert("请选择厂商!");
			return;
		}
		var PRODUCT_NAME = $("#PRODUCT_ID").find("option:selected").text();//品牌
		if(PRODUCT_NAME == "--请选择--"){
			alert("请选择品牌!");
			return;
		}
		var CATENA_NAME = $("#CATENA_ID").find("option:selected").text();//车系
		if(CATENA_NAME == "--请选择--"){
			alert("请选择车系!");
			return;
		}
		var SPEC_NAME = $("#SPEC_ID").find("option:selected").text();//型号
		if(SPEC_NAME == "--请选择--"){
			alert("请选择型号!");
			return;
		}
		
		var CAR_COLOR = $("#CAR_COLOR").val();//颜色
		var XH_PARAM = $("#XH_PARAM").val();//型号参数
		var PRO_REMARK = $("#PRO_REMARK").val();//备注
		
		var SCHEME_CODE = $("#SCHEME_CODE").val();//产品名称ID
		var SCHEME_CODE_STRNAME=$("#SCHEME_CODE").find("option:selected").text();//产品名称
		var lease_term = $("#lease_term").val();//期数
		
		var sscgj = $("#sscgj").val();//车辆实际采购价
		var syx = $("#syx").val();//商业险
		var ccs = $("#ccs").val();//车船税
		var jqx = $("#jqx").val();//交强险
		
		var gzs = $("#gzs").val();//购置税
		var spf = $("#spf").val();//上牌费
		var lqf = $("#lqf").val();//路桥费
		var lpf = $("#lpf").val();//临牌费
		var hbbf = $("#hbbf").val();//环保标费
		var qtfy = $("#qtfy").val();//其他费用
		
		var bgyy = $("#bgyy").val();//变更原因
		
		
		var res = "";
		if(SUPPLIERS_ID_OLD != null && SUPPLIERS_ID_OLD != "" && SUPPLIERS_ID_OLD != SUPPLIERS_NAME){
			res=res+" 经销商由" + SUPPLIERS_ID_OLD + "变成: "+SUPPLIERS_NAME+" ;厂商由" + COMPANY_ID_OLD + "变成: "+COMPANY_NAME
				+" ;品牌由" + PRODUCT_ID_OLD + "变成: "+PRODUCT_NAME+" ;车系由" + CATENA_ID_OLD + "变成:"+CATENA_NAME+" ;型号由" + SPEC_ID_OLD + "变成: "+SPEC_NAME + ";";
		}
		if(CAR_COLOR_OLD != null && CAR_COLOR_OLD != "" && CAR_COLOR_OLD != CAR_COLOR){
			res = res + " 颜色由" + CAR_COLOR_OLD + "变成: "+CAR_COLOR + ";";
		}
		if(XH_PARAM_OLD != null && XH_PARAM_OLD != "" && XH_PARAM_OLD != XH_PARAM){
			res = res + " 型号参数由" + XH_PARAM_OLD + "变成: " +XH_PARAM + ";";
		}
		if(PRO_REMARK_OLD != null && PRO_REMARK_OLD != "" && PRO_REMARK_OLD != PRO_REMARK){
			res = res + " 备注由" + PRO_REMARK_OLD + "变成: "+PRO_REMARK + ";";
		}
		if(SCHEME_CODE_OLD_STR != null && SCHEME_CODE_OLD_STR != "" && SCHEME_CODE_OLD_STR != SCHEME_CODE_STRNAME){
			res = res+" 产品名称由" + SCHEME_CODE_OLD_STR + "变成: "+$("#SCHEME_CODE").find("option:selected").text() + ";";
		}
		if(lease_term_OLD != null && lease_term_OLD != "" && lease_term_OLD != lease_term){
			res = res + " 期数由" + lease_term_OLD + "变成: "+lease_term + ";";
		}
		if(sscgj_OLD != null && sscgj_OLD != "" && sscgj_OLD != sscgj){
			res = res+" 车辆实际采购价由" + sscgj_OLD + "变成: "+sscgj + ";";
		}
		if(gzs_OLD != null && gzs_OLD != "" && gzs_OLD != gzs){
			res = res+" 车辆购置税由" + gzs_OLD + "变成: "+gzs + ";";
		}
		if(syx_OLD != null && syx_OLD != "" && syx_OLD != syx){
			res = res+" 商业险由" + syx_OLD + "变成: "+syx + ";";
		}
		if(ccs_OLD != null && ccs_OLD != "" && ccs_OLD != ccs){
			res = res+" 车船税由" + ccs_OLD + "变成: "+ccs + ";";
		}
		if(spf_OLD != null && spf_OLD != "" && spf_OLD != spf){
			res = res+" 上牌费由" + spf_OLD + "变成: "+spf + ";";
		}
		if(lpf_OLD != null && lpf_OLD != "" && lpf_OLD != lpf){
			res = res+" 临牌费由" + lpf_OLD + "变成: "+lpf + ";";
		}
		if(hbbf_OLD != null && hbbf_OLD != "" && hbbf_OLD != hbbf){
			res = res+" 环保标费由" + hbbf_OLD + "变成: "+hbbf + ";";
		}
		if(jqx_OLD != null && jqx_OLD != "" && jqx_OLD != jqx){
			res = res+" 交强险由" + jqx_OLD + "变成: "+jqx + ";";
		}
		if(qtfy_OLD != null && qtfy_OLD != "" && qtfy_OLD != qtfy){
			res = res+" 其他费用由" + qtfy_OLD + "变成: "+qtfy + ";";
		}
		
		
		if(res != null && res != ''){
			$("#bgbz").val(res);
			$("#WMEMO", window.parent.parent.document).val(res);
		}
		
		var data = {};
		
		//------------保存到项目设备表------BEGIN-------------
		data.PROJECT_ID = PROJECT_ID;
		data.SCHEME_ROW_NUM = SCHEME_ROW_NUM;
		data.SCHEME_ID = SCHEME_ID;
		
		data.SUPPLIERS_NAME = SUPPLIERS_NAME;
		data.COMPANY_NAME = COMPANY_NAME;
		data.PRODUCT_NAME = PRODUCT_NAME;
		data.CATENA_NAME = CATENA_NAME;
		data.SPEC_NAME = SPEC_NAME;
		
		data.CAR_COLOR = CAR_COLOR;
		data.XH_PARAM = XH_PARAM;
		data.PRO_REMARK = PRO_REMARK;
		data.TOTAL_PRICE = sscgj;
		data.BX = syx;
		data.CCS = ccs;
		data.JQX = jqx;
		
		data.gzs = gzs;
		data.spf = spf;
		data.lqf = lqf;
		data.lpf = lpf;
		data.hbbf = hbbf;
		data.qtfy = qtfy;
		
		
		//data.SCHEME_CODE = SCHEME_CODE;
		data.SCHEME_ID = SCHEME_CODE;
		//------------保存到项目设备表------END-----------
		
		//----------保存到项目方案表：期数-------BEGIN-------
		data.LEASE_TERM = lease_term;
		//----------保存到项目方案表：期数--------------
		
		
		//---------保存到项目表：变更原因  变更备注-----BEGIN-------
		data.CHANGERES = bgyy;
		if(res != null && res != ''){
			data.CHANGEMEMO = res;
		}else{
			data.CHANGEMEMO = $("#bgbz").val();
		}
		
		//---------保存到项目表：变更原因  变更备注------END------
		
		$.ajax({
    		url:_basePath+"/proChange/ProChange!saveProjectChange.action",
    		data : data,
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			if(json.flag){
    				$.ajax({
    		    		url:_basePath+"/proChange/ProChange!changeSchemeStatus.action",
    		    		data : data,
    		    		type:"post",
    		    		dataType:"json",
    		    		success:function (json){
    		    			if(json.flag){
	    		    			jQuery.messager.alert("提示","提交成功!");
	    	    				$("#askStatus").val("2");
	    	    				//disChange();
	    	    				location.reload();
    		    			}else{
    		    				alert(json.msg);
    		    			}
    		    		}
    		    	});
    			}else{
    				alert(json.msg);
    			}
    		}
    	});
	}
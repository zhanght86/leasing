$(document).ready(function(){
	$("#dlgFaPiao").dialog('close');
	revfinsnInit();

	$("input").each(function(){
		$(this).attr({title:$.trim($(this).val())});
	});
	$("select").each(function(){
		$(this).attr({title:$.trim($(this).find("option:selected").text())});
	});
	$("input").blur(function(){
		$(this).attr({title:$.trim($(this).val())});
	});
	$("select").change(function(){
		$(this).attr({title:$.trim($(this).find("option:selected").text())});
	});
	
	$("#UPDATE_DELIVER_DATE").datebox({onSelect: function (date) {
		$("#DELIVER_DATE").val($("#UPDATE_DELIVER_DATE").datebox("getValue"));
	}});
	
});

function idCardCheck(custId){
	$.ajax({
		url:_basePath+"/crm/Customer!doCheckCustIDCard.action",
		data : {id:custId},
		type:"post",
		dataType:"json",
		success:function (json){
			if(json.flag){
				$("#idCardCheckSpan").css("color","green").text("验证成功");
			}else{
				alert(json.msg);
			}
		}
	});
	
}

/**
 * 根据区域id获取城市名称
 * @param ID
 * @return
 */
function getCity(AREA_ID) {
	$.ajax({
		url:_basePath+"/project/project!getFilSystemCity.action?AREA_ID="+AREA_ID,
		type:"post",
		dataType:"json",
		success:function (data){
			//将本行的输入框初始化
			$("#PROJECT_CITY").each(function (){
				//初始化
				if ($(this).is("select")){
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
					
				}
			});
			for(var i=0;i<data.length;i++){
				$("#PROJECT_CITY").append($("<option value='"+data[i].ID+"'>"+data[i].NAME+"</option>"));				
			}
		}
	});
}
var FQCOUNT = 10000;

/**
 * 复制某个行
 * @param {Object} fromId
 * @param {Object} toId
 */
function copyTr(fromId,toId)
{
	var project_mode=$("#PROJECT_MODEL").val();
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","eq_body_tr");
	if(project_mode=='3'){
		$(tr).find(".FQMSDJ_JE").attr("fqcount",FQCOUNT);
		$(tr).find(".FQMSDJ_JE").attr("id","FQMSDJ_JE"+FQCOUNT);
		$(tr).find(".FQMSDJ_TB").attr("id","FQMSDJ_TB"+FQCOUNT);
		
		++FQCOUNT;
	}
	$(tr).find("input[name='CERTIFICATE_DATE']").attr("class","easyui-datebox");
	$("#"+toId).append(tr);
	
	var COMPANY_NAME1=$("input[name=COMPANY_NAME1]").val();
	if(COMPANY_NAME1=='潍柴重机'){
		var trApp='<td class="hiddenTD"><input name="GENERATOR_MODEL" id="GENERATOR_MODEL" style="width:90px"></td><td class="hiddenTD"><input name="GENERATOR_NUMBER" id="GENERATOR_NUMBER" style="width:90px"></td>';
		$(tr).append(trApp);
	}else if(project_mode=='1'){
		var trApp='<td class="hiddenTD"><input name="PRODUCT_ADDRESS" id="PRODUCT_ADDRESS" style="width:90px"></td><td class="hiddenTD"><input name="CERTIFICATE_NUM" id="CERTIFICATE_NUM" style="width:90px"></td><td class="hiddenTD"><input name="LIMIT_NUM" id="LIMIT_NUM" style="width:90px"></td><td class="hiddenTD"><input name="COMPANY_FULLNAME" id="COMPANY_FULLNAME" style="width:190px"></td><td class="hiddenTD"><input name="IMPORT_NUM" id="IMPORT_NUM" style="width:90px"></td><td class="hiddenTD"><input name="INSPEC_NUM" id="INSPEC_NUM" style="width:90px"></td><td class="hiddenTD"><input name="TONNAGE" id="TONNAGE" style="width:90px"></td><td class="hiddenTD"><input name="ACTUAL_PRODUCT_NAME" id="ACTUAL_PRODUCT_NAME" style="width:190px"></td><td class="hiddenTD"><input name="ACTUAL_PRODUCT_TYPE" id="ACTUAL_PRODUCT_TYPE" style="width:190px"></td>';
		$(tr).append(trApp);
	}
	else if(project_mode=='3'){
		var trApp='<td class="hiddenTD"><input name="ACTUAL_PRODUCT_NAME" id="ACTUAL_PRODUCT_NAME" style="width:190px"></td><td class="hiddenTD"><input name="ACTUAL_PRODUCT_TYPE" id="ACTUAL_PRODUCT_TYPE" style="width:190px"></td>';
		$(tr).append(trApp);
	}
	$.parser.parse(tr);
	
//	//初始化表单中的验证器
//	$(".easyui-numberbox").each(function(){
//		$(this).numberbox(); 
//	});
//	//$(tr).find(".easyui-datebox").datebox();

}

function copyTrGuaran(fromId,toId)
{
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","guaran_body_tr");
	$("#"+toId).append(tr);
}


function deleteTrGuaran(tbodyId)
{
	$("#"+tbodyId+"> tr").each(function (){
		var box=$(this).find(":checkbox");
		if ($(box).attr("checked"))
		{
			$(this).remove();
		}
	});
}

/**
 * 删除tbody中复选框被选中的某行
 * @param {Object} elementId
 */
function deleteTr(tbodyId)
{
	$("#"+tbodyId+"> tr").each(function (){
		var box=$(this).find(":checkbox");
		if ($(box).attr("checked"))
		{
			$(this).remove();
			getAllTotal();
		}
	});
	BusPol();
}


/**
 * 查询设备系列
 */
function queryEqType(obj){
	var proId=$(obj).find("option:selected").val();
	var tr=$(obj).parent().parent();
	$.ajax({
		url:_basePath+"/project/project!queryCatenaByEqID.action?PRODUCT_ID="+proId,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			var catena=$(tr).find("select[name='PRODUCT_CATENA']");
			$(catena).empty();
			
			var spec=$(tr).find("select[name='THING_SPEC']");
			$(spec).empty();
			
			$(catena).append("<option value=''>--请选择--</option>");
			$(spec).append("<option value=''>--请选择--</option>");
			for(var i=0;i<data.length;i++){
				catena.append($("<option>").val(data[i].CATENA_ID).text(data[i].CATENA_NAME));
			}
		}
	});
}


/**
 * 查询设备型号
 */
function queryEqSpec(obj){
	var catenaId=$(obj).find("option:selected").val();
	var tr=$(obj).parent().parent();
	$.ajax({
		url:_basePath+"/project/project!querySpecByEqID.action?CATENA_ID="+catenaId,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			var spec=$(tr).find("select[name='THING_SPEC']");
			$(spec).empty();
			for(var i=0;i<data.length;i++){
				$(spec).append($("<option>").val(data[i].ID).text(data[i].NAME).attr("price",data[i].PRICE));
			}
			var price=$(spec).find("option:selected").attr("price");
			$(tr).find("input[name=UNIT_PRICE]").val(anyDouble(price));
			getTotal(obj);
		}
	});
}

/**
 * 自动关联设备价格
 * @param {Object} obj
 */
function getEqPrice(obj)
{
	var price=$(obj).find("option:selected").attr("price");
	var tr=$(obj).parent().parent();
	$(tr).find("input[name=UNIT_PRICE]").val(anyDouble(price));
	getTotal(obj);
};


/**
 * 计算设备小计
 */
function getTotal(obj){
	var tr=$(obj).parent().parent();
	var price=$(tr).find("input[name='UNIT_PRICE']").val();	//单价
	if (price==''||price==undefined)
	{
		price=0;
	}
	var amount=$(tr).find("input[name='AMOUNT']").val();	//数量
	if (amount==''||amount==undefined)
	{
		amount=0;
	}
	var total=parseFloat(price)*parseInt(amount);
	$(tr).find("input[name='TOTAL']").val(total);
	getAllTotal();
}


/**
 * 计算设备总价格
 */
function getAllTotal()
{
	var allTotal=0;
	$("input[name='TOTAL']").each(function (){
		var total=$(this).val();
		if (total==''||total==undefined)
	{
		total=0;
	}
		allTotal=parseFloat(total)+parseFloat(allTotal);
	});
	$("#LEASE_TOPRIC").val(allTotal);
	$("#chinaAllTotal").text(atoc(allTotal));
	if($("input[name='LEASE_TOPRIC']").length > 0 )
	{
		$("input[name='LEASE_TOPRIC']").val(allTotal);
		var PROJECT_ADD=$("#PROJECT_ADD").val();
		if(PROJECT_ADD==''||PROJECT_ADD==null||PROJECT_ADD=="undefined"||PROJECT_ADD==undefined)
			changeEqumentMoney();
	}
}


/**
 * 小写金额转换大写
 * @param numberValue
 * @return
 */
function atoc(numberValue) {
	var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for ( var i = 0; i < len; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		if (i != (len - 3) && i != (len - 7) && i != (len - 11)
				&& i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
}



function submitAddCustProject () {
	var customer_ID=jQuery.trim($("#CUSTOMER_ID").val());
	var customer_Code=jQuery.trim($("#CUSTOMER_CODE").val());
	var customer_Type=jQuery.trim($("#CUSTOMER_TYPE").val());
	var PROJECT_NAME = jQuery.trim($("#PROJECT_NAME").val());
	var ORD_ID=$("select[name='ORD_ID']").val();
	var PROJECT_AREA = jQuery.trim($("#PROJECT_AREA").val());
	var LEASE_TOPRIC = $("#LEASE_TOPRIC").val();
	var scheme_id =$('input:radio[name=scheme_id]:checked').val();
	var CUSTOMER_NAME=jQuery.trim($("#CUSTOMER_NAME").val());
	var PROJECT_AREA_NAME = jQuery.trim($("#PROJECT_AREA").find("option:selected").text());
	var ORD_NAME=$("select[name=ORD_ID]").find("option:selected").text();
	var SUPPLIERS_FLAG=jQuery.trim($("#SUPPLIERS_FLAG").val());
	var DELIVER_ADDRESS=jQuery.trim($("#DELIVER_ADDRESS").val());
	var DELIVER_DATE=jQuery.trim($("input[name=DELIVER_DATE]").val());
	var PROJECT_MODEL=$("input[name=PROJECT_MODEL]").val();
	var PROJECT_MODEL_SHOTNAME=$("input[name=PROJECT_MODEL]").attr("SHOT_NAME");
	var MODEL_NAME=$("input[name=PROJECT_MODEL]").attr("MODEL_NAME");
	var COMPANY_NAME1=$("input[name=COMPANY_NAME1]").val();
	var COMPANY_ID1=$("input[name=COMPANY_ID_]").val();//厂商id
	var SUP_SHORTNAME=$("input[name=SUP_SHORTNAME]").val();//供应商简称
	var COMPANY_CODE=$("input[name=COMPANY_CODE]").val();//厂商编号
	var SUP_LEVEL=$("input[name=SUP_LEVEL]").val();//供应商模式
	var SUP_ID=$("input[name=SUP_ID]").val();//供应商id
	if (PROJECT_NAME == ''){
		$.messager.alert("提示","请输项目名称");
		return ;
	}else if(COMPANY_CODE==''&&COMPANY_NAME1=='陕重汽'&&SUP_LEVEL=='A'){
		$.messager.alert("提示","请输入厂家编号");
		return ;
	}else if (PROJECT_AREA == ''){
		$.messager.alert("提示","请选择区域");
		return ;
	}else if(ORD_ID == '') {
		$.messager.alert("提示","请选择组织机构");
		return;
	}else if(scheme_id == "" || scheme_id == undefined || scheme_id.length<=0){
		 $.messager.alert("提示","请选择商务政策");
		 return;
	}else if (DELIVER_ADDRESS == ''){
		$.messager.alert("提示","请输入交货地点");
		return ;
	}else if (DELIVER_DATE == ''){
			$.messager.alert("提示","请输入验收日期");
			return ;
	}
	 
	 //商务板块
	 var BUSINESS_SECTOR=$("#BUSINESS_SECTOR").val();
	var flag=true;
	var Eq=[];
	 $(".eq_body_tr").each(function(){
				var temp={};
				temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").val();
				temp.COMPANY_CODE = $(this).find("[name='COMPANY_CODE']").val();
				temp.COMPANY_NAME = $(this).find("[name='COMPANY_NAME']").val();
				temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").val();
				temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_NAME']").val();
				
				//得到设备名称
				var eqName = $(this).find("select[name='THING_NAME']").find("option:selected");
				if ($(eqName).val() == "" || $(eqName).val() == null || $(eqName).val() == undefined) {
					flag = false;
					$(eqName).focus();
					$.messager.alert("提示","请选择设备名称");
					return;
				};
				temp.PRODUCT_ID = $(this).find("[name='THING_NAME']").val();
				temp.PRODUCT_NAME = $(this).find("[name='THING_NAME']").find("option:selected").text();
				
				//得到设备型号
				var eqCatena = $(this).find("select[name='PRODUCT_CATENA']").find("option:selected");
				if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
					flag = false;
					$(eqType).focus();
					$.messager.alert("提示","请选择设备系别");
					return;
				};
				temp.CATENA_ID = $(this).find("[name='PRODUCT_CATENA']").val();
				temp.CATENA_NAME = $(this).find("[name='PRODUCT_CATENA']").find("option:selected").text();
				
				//得到设备型号
				var eqType = $(this).find("select[name='THING_SPEC']").find("option:selected");
				if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
					flag = false;
					$(eqType).focus();
					$.messager.alert("提示","请选择设备型号");
					return;
				};
				temp.SPEC_ID = $(this).find("[name='THING_SPEC']").val();
				temp.SPEC_NAME = $(this).find("[name='THING_SPEC']").find("option:selected").text();
				
				//得到出厂编号
				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
//				if ($(WHOLE_ENGINE_CODE).val() == "" || $(WHOLE_ENGINE_CODE).val() == undefined || $(WHOLE_ENGINE_CODE).val()<=0) {
//					flag = false;
//					$(WHOLE_ENGINE_CODE).focus();
//					alert("请输入出厂编号");
//					return;
//				};
				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
				
				//得到出厂日期
				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
//				if ($(CERTIFICATE_DATE).val() == "" || $(CERTIFICATE_DATE).val() == undefined || $(CERTIFICATE_DATE).val()<=0) {
//					flag = false;
//					$(CERTIFICATE_DATE).focus();
//					alert("请输入出厂日期");
//					return;
//				};
				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
				
				//得到发动机编号
				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
//				if ($(ENGINE_TYPE).val() == "" || $(ENGINE_TYPE).val() == undefined || $(ENGINE_TYPE).val()<=0) {
//					flag = false;
//					$(ENGINE_TYPE).focus();
//					alert("请输入发动机编号");
//					return;
//				};
				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
				
				//得到车架号
				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
//				if ($(CAR_SYMBOL).val() == "" || $(CAR_SYMBOL).val() == undefined || $(CAR_SYMBOL).val()<=0) {
//					flag = false;
//					$(CAR_SYMBOL).focus();
//					alert("请输入车架号");
//					return;
//				};
				temp.CAR_SYMBOL = $(this).find("[name='CAR_SYMBOL']").val();
				
				temp.STAYBUY_PRICE = $(this).find("[name='STAYBUY_PRICE']").val();
				
				//得到设备的单价
				var unitPrice = $(this).find("input[name='UNIT_PRICE']");
				if ($(unitPrice).val() == "" || $(unitPrice).val() == undefined || $(unitPrice).val()<=0) {
					flag = false;
					$(unitPrice).focus();
					$.messager.alert("提示","请输入设备单价");
					return;
				};
				temp.UNIT_PRICE = $(this).find("[name='UNIT_PRICE']").val();
				
				//获取设备数量
				var amount = $(this).find("input[name='AMOUNT']");
				if ($(amount).val() == "" || $(amount).val() == undefined  || $(amount).val()<=0) {
					flag = false;
					$(unitPrice).focus();
					$.messager.alert("提示","请输入设备数量");
					return;
				};
				temp.AMOUNT = $(this).find("[name='AMOUNT']").val();
				
				//获取小计
				var TOTAL_PRICE = $(this).find("input[name='TOTAL']");
				if ($(TOTAL_PRICE).val() == "" || $(TOTAL_PRICE).val() == undefined  || $(TOTAL_PRICE).val()<=0) {
					flag = false;
					$(TOTAL_PRICE).focus();
					$.messager.alert("提示","请输入设备数量");
					return;
				};
				temp.TOTAL_PRICE = $(this).find("[name='TOTAL']").val();
				
				temp.UNIT = $(this).find("[name='UNIT']").val();
				temp.UNITTEST = $(this).find("[name='UNIT']").find("option:selected").text();
				temp.TOTAL = $(this).find("[name='TOTAL']").val();
				
				var project_mode=$("#PROJECT_MODEL").val();
				var COMPANY_NAME1=$("input[name=COMPANY_NAME1]").val();
				if(COMPANY_NAME1=='潍柴重机'){
					temp.GENERATOR_MODEL=$(this).find("[name='GENERATOR_MODEL']").val();
					temp.GENERATOR_NUMBER=$(this).find("[name='GENERATOR_NUMBER']").val();
				} else if(project_mode=='1'){
					//获取产地
					var PRODUCT_ADDRESS = $(this).find("input[name='PRODUCT_ADDRESS']");
//					if ($(PRODUCT_ADDRESS).val() == "" || $(PRODUCT_ADDRESS).val() == undefined  || $(PRODUCT_ADDRESS).val()<=0) {
//						flag = false;
//						$(PRODUCT_ADDRESS).focus();
//						alert("请输入产地");
//						return;
//					};
					temp.PRODUCT_ADDRESS = $(this).find("[name='PRODUCT_ADDRESS']").val();
					
					//获取合格证书
//					var CERTIFICATE_NUM = $(this).find("input[name='CERTIFICATE_NUM']");
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == undefined  || $(CERTIFICATE_NUM).val()<=0) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						alert("请输入合格证书");
//						return;
//					};
					temp.CERTIFICATE_NUM = $(this).find("[name='CERTIFICATE_NUM']").val();
					
					//获取限乘人数
					var LIMIT_NUM = $(this).find("input[name='LIMIT_NUM']");
					if ($(LIMIT_NUM).val() == "" || $(LIMIT_NUM).val() == undefined  || $(LIMIT_NUM).val()=='undefined') {
						flag = false;
						$(LIMIT_NUM).focus();
						$.messager.alert("提示","请输入限乘人数");
						return;
					};
					temp.LIMIT_NUM = $(this).find("[name='LIMIT_NUM']").val();
					
					//获取机动车辆生产企业名称
					var COMPANY_FULLNAME = $(this).find("input[name='COMPANY_FULLNAME']");
//					if ($(COMPANY_FULLNAME).val() == "" || $(COMPANY_FULLNAME).val() == undefined  || $(COMPANY_FULLNAME).val()<=0) {
//						flag = false;
//						$(COMPANY_FULLNAME).focus();
//						alert("请输入机动车辆生产企业名称");
//						return;
//					};
					temp.COMPANY_FULLNAME = $(this).find("[name='COMPANY_FULLNAME']").val();
					
					temp.IMPORT_NUM = $(this).find("[name='IMPORT_NUM']").val();//获取进口证明书号
					
					temp.INSPEC_NUM = $(this).find("[name='INSPEC_NUM']").val();//获取商检单号
					
					temp.TONNAGE = $(this).find("[name='TONNAGE']").val();//获取吨位
					
					
					//实际车辆开票名称
					var ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']");
//					if ($(ACTUAL_PRODUCT_NAME).val() == "" || $(ACTUAL_PRODUCT_NAME).val() == undefined  || $(ACTUAL_PRODUCT_NAME).val()<=0) {
//						flag = false;
//						$(ACTUAL_PRODUCT_NAME).focus();
//						alert("请输入实际车辆开票名称");
//						return;
//					};
					temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
					
					//实际车辆开票型号
					var ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']");
//					if ($(ACTUAL_PRODUCT_TYPE).val() == "" || $(ACTUAL_PRODUCT_TYPE).val() == undefined  || $(ACTUAL_PRODUCT_TYPE).val()<=0) {
//						flag = false;
//						$(ACTUAL_PRODUCT_TYPE).focus();
//						alert("请输入实际车辆开票型号");
//						return;
//					};
					temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
					
				}
				else if(project_mode=='3'){
					//实际车辆开票名称
					var ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']");
					if ($(ACTUAL_PRODUCT_NAME).val() == "" || $(ACTUAL_PRODUCT_NAME).val() == undefined  || $(ACTUAL_PRODUCT_NAME).val()<=0) {
						flag = false;
						$(ACTUAL_PRODUCT_NAME).focus();
						$.messager.alert("提示","请输入实际车辆开票名称");
						return;
					};
					temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
					
					//实际车辆开票型号
					var ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']");
					if ($(ACTUAL_PRODUCT_TYPE).val() == "" || $(ACTUAL_PRODUCT_TYPE).val() == undefined  || $(ACTUAL_PRODUCT_TYPE).val()<=0) {
						flag = false;
						$(ACTUAL_PRODUCT_TYPE).focus();
						$.messager.alert("提示","请输入实际车辆开票型号");
						return;
					};
					temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
				}
				Eq.push(temp);
		});
	 if(flag)
	 {
		 var dataJson ={
					EqList:Eq,
					CLIENT_ID:customer_ID,
					PRO_NAME:PROJECT_NAME,
					STAUS:0,
					AREA_ID:PROJECT_AREA,
					ORD_ID:ORD_ID,
					LEASE_TOPRIC:LEASE_TOPRIC,
					CUSTOMER_TYPE:customer_Type,
					POB_ID:scheme_id,
					CUSTOMER_NAME:CUSTOMER_NAME,
					PROJECT_AREA_NAME:PROJECT_AREA_NAME,
					ORD_NAME:ORD_NAME,
					SUPPLIERS_FLAG:SUPPLIERS_FLAG,
					DELIVER_ADDRESS:DELIVER_ADDRESS,
					DELIVER_DATE:DELIVER_DATE,
					PROJECT_MODEL:PROJECT_MODEL,
					PROJECT_MODEL_SHOTNAME:PROJECT_MODEL_SHOTNAME,
					MODEL_NAME:MODEL_NAME,
					COMPANY_NAME1:COMPANY_NAME1,
					COMPANY_ID1:COMPANY_ID1,
					SUP_SHORTNAME:SUP_SHORTNAME,
					SUP_ID:SUP_ID,
					COMPANY_CODE:COMPANY_CODE,
					SUP_LEVEL:SUP_LEVEL,
					BUSINESS_SECTOR:BUSINESS_SECTOR
				};
				$("#ChangeViewData").val(JSON.stringify(dataJson));
				$("#formPrjoctView").submit();
	 }
	 else
	 {
		 return;
	 }
	
}

function BusPol()
{
	var product_id="";
	var company_id="";
	var suppliers_id="";
	var PROJECT_MODEL=$("#PROJECT_MODEL").attr("MODEL_NAME");
	$(".eq_body_tr").each(function(){
		var THING_ID=$(this).find("[name='THING_NAME']").val();
		if(THING_ID.length>0){
			if(product_id.length<=0)
			{
				product_id=$(this).find("[name='THING_NAME']").val();
			}
			else if(product_id.indexOf($(this).find("[name='THING_NAME']").val())<0){
				product_id=product_id+","+$(this).find("[name='THING_NAME']").val();
			}
		}
		company_id = $(this).find("[name='COMPANY_ID']").val();
		suppliers_id = $(this).find("[name='SUPPLIERS_ID']").val();
	});
	$("#BusinessPolicy_DIV").empty();
	if(product_id.length>0)
	{
		$.ajax({
			url:_basePath+"/project/project!queryBussPol.action?COMPANY_ID="+company_id+"&SUPPLIER_ID="+suppliers_id+"&PRODUCT_ID="+product_id+"&PROJECT_MODEL_FLAG="+encodeURI(PROJECT_MODEL),
			type:"post",
			dataType:"json",
			success:function(list)
			{
				
				for(var i=0;i<list.data.length;i++){
					
					var scheme_name=list.data[i].SCHEME_NAME;list.data[i].ALIASES
					var remark="业务类型："+PROJECT_MODEL;
					
					if(list.data[i].PAY_WAY !=null &&  list.data[i].PAY_WAY.length>0)
					{
						remark=remark+"&#10;支付方式："+list.data[i].PAY_WAY;
					}
					
					if(list.data[i].START_PERCENT !=null && list.data[i].START_PERCENT.length>0)
					{
						remark=remark+"&#10;起租比例："+list.data[i].START_PERCENT+"%";
					}
					
					if(list.data[i].BAIL_PERCENT !=null && list.data[i].BAIL_PERCENT.length>0)
					{
						remark=remark+"&#10;保证金比例："+list.data[i].BAIL_PERCENT+"%";
					}
					
					if(list.data[i].DB_BIL_PERCENT !=null && list.data[i].DB_BIL_PERCENT.length>0)
					{
						remark=remark+"&#10;DB保证金比例："+list.data[i].DB_BIL_PERCENT+"%";
					}
					
					if(list.data[i].PLEDGE_STATUS !=null && list.data[i].PLEDGE_STATUS.length>0)
					{
						remark=remark+"&#10;牌抵挂："+list.data[i].PLEDGE_STATUS;
					}
					
					if(list.data[i].SALE_MODEL !=null && list.data[i].SALE_MODEL.length>0)
					{
						remark=remark+"&#10;销售模式："+list.data[i].SALE_MODEL;
					}
					
					if(list.data[i].POWER !=null && list.data[i].POWER.length>0)
					{
						remark=remark+"&#10;动力类型："+list.data[i].POWER;
					}
					
					if(list.data[i].APPLIY_SCOPE !=null && list.data[i].APPLIY_SCOPE.length>0)
					{
						remark=remark+"&#10;使用范围："+list.data[i].APPLIY_SCOPE;
					}
					if(i==0){
						$("#BusinessPolicy_DIV").append("<span title='"+remark+"'><input type='radio' id='scheme_id' checked name='scheme_id'  value='"+list.data[i].SCHEME_NAME_ALL+"'>"+list.data[i].ALIASES+"</span>");
					}else{
						$("#BusinessPolicy_DIV").append("<span title='"+remark+"'><input type='radio' id='scheme_id' name='scheme_id'  value='"+list.data[i].SCHEME_NAME_ALL+"'>"+list.data[i].ALIASES+"</span>");
					}
				}
//				$("#BusinessPolicy_DIV").append("<span title='自定义'><input type='radio' id='scheme_id' name='scheme_id'  value='-1'>自定义</span>");
			}
		});
	}
}

function submitAddScheme(_TYPE)
{
	$("#submitAddScheme0").attr("disabled",true);
	$("#submitAddScheme0").linkbutton("disable");
	var MONEYALL=$("#MONEYALL").val();
	if(MONEYALL==null||MONEYALL==''){
		$.messager.alert("提示","请先进行测算");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	}
	var JBPMFLAG=$("#JBPMFLAG").val();
	if(_TYPE=='1'&&JBPMFLAG=='0'){
		$.messager.alert("提示","请先保存项目方案信息");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return ;
	}
	var BALANCE_LOAN_SWITCH=$("#BALANCE_LOAN_SWITCH").val();
	var B_MODEL_SWITCH=$("#B_MODEL_SWITCH").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var GUARANTEE_MODEL=$("#GUARANTEE_MODEL").val();
	var IRREGULAR_REPAYMENT_SWITCH=$("#IRREGULAR_REPAYMENT_SWITCH").val();
	var PAY_WAY=$("#PAY_WAY").val();
	var DATAFILL_SWITCH=$("#DATAFILL_SWITCH").val();
	var DATE_COMPLEMENT=$("#DATE_COMPLEMENT").val();

	//差额放款 供应商差额放款开关关闭
	if(PAYMENT_STATUS=='2'&&BALANCE_LOAN_SWITCH=='1'){
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		$.messager.alert("提示","您没有差额放款的权限！");
		return;
	}else if(B_MODEL_SWITCH=='1'&&GUARANTEE_MODEL=='B_MODE'){
		//供应商B模式  B模式开关关闭
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		$.messager.alert("提示","您没有担保方式为B模式的权限！");
		return;
	}else if(IRREGULAR_REPAYMENT_SWITCH=='1'&&(PAY_WAY=='2'||PAY_WAY=='3')){
		//供应商  季度内不规则还款关闭
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		$.messager.alert("提示","您没有季度内不规则还款的权限！");
		return;
	}else if(DATAFILL_SWITCH=='1'&&DATE_COMPLEMENT=='3'){
		$.messager.alert("提示","有到期资料未补齐项目，无此项选择权限");
		return;
	}
	var CRDIT_MONEY=$("input[name='CRDIT_MONEY']").val();//授信额度
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	//商务板块
	var BUSINESS_SECTOR=$("#BUSINESS_SECTOR").val();
	var PROJECT_MODEL=$("#PROJECT_MODEL").val();
	if(PROJECT_MODEL=='5'&&(BUSINESS_SECTOR=='CVS'||BUSINESS_SECTOR=='CVP')&&parseFloat(FINANCE_TOPRIC)<3000000){
		$.messager.alert("提示","融资金额过小，不能使用大项目业务模式");
		$("#submitAddScheme0").attr("disabled",false);
		$("#submitAddScheme0").linkbutton("enable");
		return ;
	}else if(PROJECT_MODEL=='5'&&(BUSINESS_SECTOR=='TMP'||BUSINESS_SECTOR=='TMS')&&parseFloat(FINANCE_TOPRIC)<3500000){
		$.messager.alert("提示","融资金额过小，不能使用大项目业务模式");
		$("#submitAddScheme0").attr("disabled",false);
		$("#submitAddScheme0").linkbutton("enable");
		return ;
	}
//	var MONEYALL=$("input[name='MONEYALL']").val();//租金合计
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));//租金合计-起租租金
	var COMPANY_ID=$("input[name=COMPANY_ID]").val();//厂商ID
	var PRO_ID=$("#PRO_ID").val();
	
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
	//得到银行信息  判断首付款或租金付款方式为网银时银行信息必填
	var BANK_ID = $("select[name='BANK_ID']").find("option:selected");
	if(FIRST_PAYMENT_TYPE == "1" || RENT_PAYMENT_TYPE == "1"){
		if ($(BANK_ID).val() == "" || $(BANK_ID).val() == null || $(BANK_ID).val() == undefined) {
			flag = false;
			$(BANK_ID).focus();
			$.messager.alert("提示","请选择扣款银行!");
			if(_TYPE!='1'){
				$("#submitAddScheme0").attr("disabled",false);
				$("#submitAddScheme0").linkbutton("enable");
			}
			return;
		};
	}

	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	
	if(FIRST_PAYMENT_TYPE=='1' && PAYMENT_STATUS!='1'){
		$.messager.alert("提示","请选择全额放款");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return ;
	}
	refinsncingType();
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	if (PLATFORM_TYPE == "" || PLATFORM_TYPE == undefined || PLATFORM_TYPE<0) {
		flag = false;
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	};
	
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
//	if(RENT_PAYMENT_TYPE!='1'&&FIRST_PAYMENT_TYPE!='1'){
//		$("#FINAL_TYPE").val('');
//		$("#FINAL_TYPE").attr("disabled","disabled");
//	}
	var FINAL_TYPE = $("select[name='FINAL_TYPE']").find("option:selected").val();
	var FINAL_CUST_ID = $("select[name='FINAL_CUST_ID']").find("option:selected").val();
	
	var LEASE_TERM=$("#lease_term").val();
	if (LEASE_TERM == "" || LEASE_TERM == undefined || LEASE_TERM<=0) {
		flag = false;
		$("#lease_term").focus();
		$.messager.alert("提示","请输入期限");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	};
	
	var lease_period=0;
	if($("#lease_period").length > 0 )
	{
		lease_period=$("#lease_period").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入周期!");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	}
	var _payCountOfYear=accDiv(12,lease_period);
	
	var LEASE_PERIOD=$("#lease_period").find("option:selected").val();
	var YEAR_INTEREST=0;
	if($("#YEAR_INTEREST").length > 0 )
	{
		YEAR_INTEREST=$("#YEAR_INTEREST").val();
	}
	
	if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<=0) {
		flag = false;
		$("#YEAR_INTEREST").focus();
		$.messager.alert("提示","请维护商务政策，确保存在年利率！");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	};
	
	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000");
	
	var DELIVER_DATE="";
	if($("#DELIVER_DATE").length > 0 )
	{
		DELIVER_DATE=$("#DELIVER_DATE").val();
	}
	else
	{
		$.messager.alert("提示","请输入交换时间!");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	}
	
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请选择支付方式!");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	}
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
		$.messager.alert("提示","请输入首期付款日期！");
		$("#submitAddScheme0").attr("disabled",false);
		$("#submitAddScheme0").linkbutton("enable");
		return ;
	}
	
	var FEES='0';
	if($("#fees").length > 0 ){
		FEES=$("#fees").val();
	}
	
	var FEES_PRICE='0';
	if($("#FEES_PRICE").length > 0 ){
		FEES_PRICE=$("#FEES_PRICE").val();
	}
	
	var guaranList=[];
	 $(".guaran_body_tr").each(function(){
				var temp={};
				temp.GUARAN_TYPE = $(this).find("select[name='GUARAN_TYPE']").find("option:selected").val();
				temp.GUARAN_NAME = $(this).find("[name='GUARAN_NAME']").val();
				temp.ORGANIZATION_CODE = $(this).find("[name='ORGANIZATION_CODE']").val();
				temp.GUARAN_PHONE = $(this).find("[name='GUARAN_PHONE']").val();
				temp.FAUSTPFAND=$(this).find("[name='FAUSTPFAND']").val();
				temp.GUARANTEE_ENTITY=$(this).find("[name='GUARANTEE_ENTITY']").val();
				guaranList.push(temp);
	 });
	
	var GUARANTEE=$("#THIRD_PARTY_GUARANTEES").val();
	if(GUARANTEE=='1'){
		if(guaranList.length<1){
			$.messager.alert("提示","请录入担保信息");
			if(_TYPE!='1'){
				$("#submitAddScheme0").attr("disabled",false);
				$("#submitAddScheme0").linkbutton("enable");
			}
			return ;
		}
	}
	var REMARK=$("#REMARK").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var BANK_ID=$(BANK_ID).val();
	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
	var SUPPLIER_ID=$("input[name=SUPPLIER_ID]").val();
	
	var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&date="+DELIVER_DATE+"&project_code="+PRO_CODE+"&project_id="+PRO_ID+"&rows="+JSON.stringify(Rows);
	//----------------准备还款计划数据--------结束
	var data_="PLATFORM_TYPE="+PLATFORM_TYPE+"&PROJECT_MODEL="+PROJECT_MODEL+"&FIRSTPAYDATE="+FIRSTPAYDATE
				+"&PRO_CODE="+PRO_CODE+"&COMPANY_ID="+COMPANY_ID+"&SUPPLIER_ID="+SUPPLIER_ID+"&LEASE_TOPRIC="
				+LEASE_TOPRIC+"&PRO_ID="+PRO_ID+"&PROJECT_ID="+PRO_ID+"&CLIENT_ID="+CLIENT_ID+"&BANK_ID="
				+BANK_ID+"&FINAL_TYPE="+FINAL_TYPE+"&FINAL_CUST_ID="+FINAL_CUST_ID+"&INVOICE_TARGET_TYPE="
				+INVOICE_TARGET_TYPE+"&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD
				+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+"&YEAR_INTEREST="+YEAR_INTEREST+"&REMARK="+REMARK
				+"&FEES="+FEES+"&FEES_PRICE="+FEES_PRICE+"&guaranList="+JSON.stringify(guaranList)
				+"&param="+getFromData1("#addSchemeForm")+"&MONEYALL="+MONEYALL+"&ZKL="+$("#ZKL").val();
	data_ = data_ + data1;
	data_=data_+"&_TYPE="+_TYPE;
	var FIRST_MONEY_ALL=$("#FIRSTPAYALL").val();
	var MONEY_ALL=$("#MONEYALL").val();
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	data_=data_+"&FIRST_MONEY_ALL="+FIRST_MONEY_ALL+"&MONEY_ALL="+MONEY_ALL;
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	if(RENT_WAY_INVOICE!=''&&RENT_WAY_INVOICE!=null&&RENT_WAY_INVOICE!=undefined&&RENT_WAY_INVOICE!="undefined"){
		data_=data_+"&RENT_WAY_INVOICE="+RENT_WAY_INVOICE;
	}
	
	var url;
	if(_TYPE=='0'){ //保存
		url=_basePath+"/project/project!addSchemeForProject.action";
	}else{//发起流程
		url=_basePath+"/project/project!startProjectStartByJbpm.action";
		$("#submitAddScheme_").removeAttr("onclick");
		$("#submitAddScheme_").attr("title","流程发起中...");
	}
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:url,
		data:data_,
		success:function(json){
			//wjd 2013-12-10 项目模式为分期  设备列表显示分期模式单价
			if(PROJECT_MODEL == '3'){
				showEquipmentFQ(PRO_ID);
			}
			if(json.flag){
				if(_TYPE=='1'){
				$.messager.alert("提示","发起流程成功！","info",function(){
					$.messager.alert("提示",json.msg+json.data,"info",function(){
						top.closeTab("项目一览");
						PRO_CODE=$("#PRO_CODE").val();
						top.addTab("项目一览",_basePath+"/project/project!getDataList.action?PRO_CODE="+PRO_CODE);
						top.closeTab("项目立项");
						
					});
					
				});
				
				}else{
					$.messager.alert("提示","方案保存成功！请上传资料");
					$("#JBPMFLAG").val('1');
					$("#submitAddScheme0").linkbutton("disable");
					$("#submitAddScheme0").attr("disabled","disabled");
					var PRO_CODE=$("#PRO_CODE").val();
					var url=_basePath+"/crm/Customer!toMgElectronicPhotoAlbum1.action?PRO_CODE="+PRO_CODE+"&startJbpm=true";
					$("#base_ifo").tabs('add',{
							title:'附件信息',
							href:url
					});
				}
			}else{
				$.messager.alert("提示",json.msg);
				if(_TYPE!='1'){
					$("#submitAddScheme0").attr("disabled",false);
					$("#submitAddScheme0").linkbutton("enable");
				}
				$("#submitAddScheme_").attr("onclick", "submitAddScheme()");
				$("#submitAddScheme_").removeAttr("title");
			}
		}
	});		
//	
//	$.ajax({
//		url:_basePath+"/project/project!doCheckCompanyCredit.action",
//		type:"post",
//		data:"PROJECT_ID="+PRO_ID+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+"&COMPANY_ID="+COMPANY_ID+"&SUP_ID="+SUPPLIER_ID,
//		dataType:"json",
//		success:function(rst){
//			if(rst.flag){
//				
//			}else{
//				$.messager.alert("提示","请确认厂商授信额度是否过低");
//			}
//		},
//		error:function(rst){
//			$.messager.alert("提示",rst.msg);
//		}
//	});
}
function testinfo(){
	var GUARANTEE_MONEY = $("#GUARANTEE_MONEY").val();
	var FEES_PRICE = $("#FEES_PRICE").val();
	var PURCHASE_PRICE = $("#PURCHASE_PRICE").val();
	var MONEYALL = $("#MONEYALL").val();
	
	alert(GUARANTEE_MONEY+":担保费  "+FEES_PRICE+":手续费  "+PURCHASE_PRICE+":留购价款  "+MONEYALL+":租金总额  ");
	
}

function showEquipmentFQ(PRO_ID){
	
	document.getElementById("FQMSDJ").style.display="";
	$.ajax({
		url:_basePath+"/project/project!queryEquipmentFQ.action",
		type:"post",
		data:"PROJECT_ID="+PRO_ID,
		dataType:"json",
		success:function(data){
			$("input[id^='FQMSDJ_JE']").each(function(i){
				var fqcount = $(this).attr("fqcount");
				$("#FQMSDJ_JE"+fqcount).val(data[i].MONEY);
				document.getElementById("FQMSDJ_TB"+fqcount).style.display="";
			});
		}
	});
	
	//分期模式提示信息
	var GUARANTEE_MONEY = $("#GUARANTEE_MONEY").val()==""?0:$("#GUARANTEE_MONEY").val();//担保费
	var FEES_PRICE = $("#FEES_PRICE").val()==""?0:$("#FEES_PRICE").val();//手续费
	var PURCHASE_PRICE = $("#PURCHASE_PRICE").val()==""?0:$("#PURCHASE_PRICE").val();//留购价款
	var MONEYALL = $("#MONEYALL").val()==""?0:$("#MONEYALL").val();//租金总额
	var price1 = parseInt(GUARANTEE_MONEY) + parseInt(FEES_PRICE) + parseInt(PURCHASE_PRICE) + parseInt(MONEYALL);
	
	var DEPOSIT_MONEY = $("#DEPOSIT_MONEY").val()==""?0:$("#DEPOSIT_MONEY").val();//客户保证金
	var FIRSTPAYALL = $("#FIRSTPAYALL").val()==""?0:$("#FIRSTPAYALL").val();//首付款
	var price2 = parseInt(FIRSTPAYALL) - parseInt(DEPOSIT_MONEY);
//	var price3 = "${empty FirstRent ? FIRST_RENT : FirstRent  }";
	var mesStage = "为保证数据准确性,请先点击保存按钮!请以保存关闭后重新打开显示的数据为准!";
	mesStage += "\n分期款项总额为:" + price1;
	mesStage += "\n首期付款金额为:" + price2;
	mesStage += "\n每期分期金额请参照测算第一期租金";
	mesStage += "\n保证金金额(不计利息)为:" + DEPOSIT_MONEY+"!";
	alert(mesStage);

	
}



function showEquipmentFQHXL(){
	
	//分期模式提示信息
	var GUARANTEE_MONEY = $("#GUARANTEE_MONEY").val()==""?0:$("#GUARANTEE_MONEY").val();//担保费
	var FEES_PRICE = $("#FEES_PRICE").val()==""?0:$("#FEES_PRICE").val();//手续费
	var PURCHASE_PRICE = $("#PURCHASE_PRICE").val()==""?0:$("#PURCHASE_PRICE").val();//留购价款
	var MONEYALL = $("#MONEYALL").val()==""?0:$("#MONEYALL").val();//租金总额
	var price1 = parseInt(GUARANTEE_MONEY) + parseInt(FEES_PRICE) + parseInt(PURCHASE_PRICE) + parseInt(MONEYALL);
	
	var DEPOSIT_MONEY = $("#DEPOSIT_MONEY").val()==""?0:$("#DEPOSIT_MONEY").val();//客户保证金
	var FIRSTPAYALL = $("#FIRSTPAYALL").val()==""?0:$("#FIRSTPAYALL").val();//首付款
	var price2 = parseInt(FIRSTPAYALL) - parseInt(DEPOSIT_MONEY);
//	var price3 = "${empty FirstRent ? FIRST_RENT : FirstRent  }";
	var mesStage = "为保证数据准确性,请先点击保存按钮!请以保存关闭后重新打开显示的数据为准!";
	mesStage += "\n分期款项总额为:" + price1;
	mesStage += "\n首期付款金额为:" + price2;
	mesStage += "\n每期分期金额请参照测算第一期租金";
	mesStage += "\n保证金金额(不计利息)为:" + DEPOSIT_MONEY+"!";
	alert(mesStage);

	
}

function getFromData1(str) {
	var saveRecord = new Array();
	$(str + ' [name]').each(
			function() {
				var temp={};
				if ($(this).is(":checkbox,:radio")) {
					if ($(this).attr("checked")) {
						temp.KEY_NAME_ZN=$(this).attr("SID");
						temp.KEY_NAME_EN=$(this).attr("name");
						temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
						temp.VALUE_STR=$.trim($(this).val());
						temp.MINVALUE=$(this).attr("minValue");
						temp.COMPUTE=$(this).attr("compute");
						temp.COMPUTEBY=$(this).attr("computeBy");
						temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
						temp.ROW_NUM=$(this).attr("ROW_NUM");
					}
				} else {
					if ($(this).is("select")) {
						temp.KEY_NAME_ZN=$(this).attr("SID");
						temp.KEY_NAME_EN=$(this).attr("name");
						temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
						temp.VALUE_STR=$.trim($(this).find(":selected").val());
						temp.MINVALUE=$(this).attr("minValue");
						temp.COMPUTE=$(this).attr("compute");
						temp.COMPUTEBY=$(this).attr("computeBy");
						temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
						temp.ROW_NUM=$(this).attr("ROW_NUM");
					} else {
						var name=$(this).attr("name");
						if(name=='STARTING_DATE_PROJECT'){
							temp.KEY_NAME_ZN=$("#"+name).attr("SID");
							temp.KEY_NAME_EN=$(this).attr("name");
							temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
							temp.VALUE_STR=$.trim($(this).val());
							temp.MINVALUE=$(this).attr("minValue");
							temp.COMPUTE=$(this).attr("compute");
							temp.COMPUTEBY=$(this).attr("computeBy");
							temp.ITEM_FLAG=$("#"+name).attr("ITEM_FLAG");
							temp.ROW_NUM=$("#"+name).attr("ROW_NUM");
						}else{
							temp.KEY_NAME_ZN=$(this).attr("SID");
							temp.KEY_NAME_EN=$(this).attr("name");
							temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
							temp.VALUE_STR=$.trim($(this).val());
							temp.MINVALUE=$(this).attr("minValue");
							temp.COMPUTE=$(this).attr("compute");
							temp.COMPUTEBY=$(this).attr("computeBy");
							temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
							temp.ROW_NUM=$(this).attr("ROW_NUM");
						}
					}
				}
				saveRecord.push(temp);
			});
	return JSON.stringify(saveRecord);
}

/**
 * 除法
 * @param arg1
 * @param arg2
 * @return
 */
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length ;} catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length ;} catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}
//修改保存
function submitUpdateSchemeSave(){
	var PRO_ID = $("#PRO_ID").val();
	var UPDATE_PROJECT_AREA = $("#UPDATE_PROJECT_AREA").val();
	if(UPDATE_PROJECT_AREA ==''|| UPDATE_PROJECT_AREA==null){
		$.messager.alert("提示","请选择区域!");
		return true;
	}
	
	var UPDATE_DELIVER_ADDRESS = $("#UPDATE_DELIVER_ADDRESS").val();
	if(UPDATE_DELIVER_ADDRESS ==''|| UPDATE_DELIVER_ADDRESS==null){
		$.messager.alert("提示","请填写交货地点!");
		return true;
	}
	var UPDATE_DELIVER_DATE = $("#UPDATE_DELIVER_DATE").datebox("getValue");
	if(UPDATE_DELIVER_DATE ==''|| UPDATE_DELIVER_DATE==null){
		$.messager.alert("提示","请填写验收日期!");
		return true;
	}
	
	var BALANCE_LOAN_SWITCH=$("#BALANCE_LOAN_SWITCH").val();
	var B_MODEL_SWITCH=$("#B_MODEL_SWITCH").val();
	var B_MODEL_SWITCH=$("#B_MODEL_SWITCH").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var IRREGULAR_REPAYMENT_SWITCH=$("#IRREGULAR_REPAYMENT_SWITCH").val();
	var PAY_WAY=$("#PAY_WAY").val();
	var DATAFILL_SWITCH=$("#DATAFILL_SWITCH").val();
	var DATE_COMPLEMENT=$("#DATE_COMPLEMENT").val();

	
	//差额放款 供应商差额放款开关关闭
	if(PAYMENT_STATUS=='2'&&BALANCE_LOAN_SWITCH=='1'){
		$.messager.alert("提示","您没有差额放款的权限！");
		return;
	}else if(B_MODEL_SWITCH=='1'&&GUARANTEE_MODEL=='B_MODE'){
		//供应商B模式  B模式开关关闭
		$.messager.alert("提示","您没有担保方式为B模式的权限！");
		return;
	}else if(IRREGULAR_REPAYMENT_SWITCH=='1'&&(PAY_WAY=='2'||PAY_WAY=='3')){
		//供应商  季度内不规则还款关闭
		$.messager.alert("提示","您没有季度内不规则还款的权限！");
		return;
	}else if(DATAFILL_SWITCH=='1'&&DATE_COMPLEMENT=='3'){
		$.messager.alert("提示","有到期资料未补齐项目，无此项选择权限");
		return;
	}
	
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	var PROJECT_MODEL=$("#PROJECT_MODEL").val();
	//商务板块
	var BUSINESS_SECTOR=$("#BUSINESS_SECTOR").val();
	if(PROJECT_MODEL=='5'&&(BUSINESS_SECTOR=='CVS'||BUSINESS_SECTOR=='CVP')&&parseFloat(FINANCE_TOPRIC)<3000000){
		$.messager.alert("提示","融资金额过小，不能使用大项目业务模式");
		$("#submitAddScheme0").attr("disabled",false);
		$("#submitAddScheme0").linkbutton("enable");
		return ;
	}else if(PROJECT_MODEL=='5'&&(BUSINESS_SECTOR=='TMP'||BUSINESS_SECTOR=='TMS')&&parseFloat(FINANCE_TOPRIC)<3500000){
		$.messager.alert("提示","融资金额过小，不能使用大项目业务模式");
		$("#submitAddScheme0").attr("disabled",false);
		$("#submitAddScheme0").linkbutton("enable");
		return ;
	}
	var MONEYALL=$("input[name='MONEYALL']").val();//租金合计
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));
	refinsncingType();
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	if (PLATFORM_TYPE == "" || PLATFORM_TYPE == undefined || PLATFORM_TYPE<0) {
		//flag = false;
		//return;
	};
	var COMPANYCODE2=$("input[name=COMPANY_CODE2]").val();
	var SUP_LEVEL=$("#SUP_LEVEL").val();
	var COMPANY_NAME1=$("#COMPANY_NAME1").val();
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
	//得到银行信息  判断首付款或租金付款方式为网银时银行信息必填
	var BANK_ID = $("select[name='BANK_ID']").attr("selected",true);
	if(FIRST_PAYMENT_TYPE == "1" || RENT_PAYMENT_TYPE == "1"){
		if ($(BANK_ID).val() == "" || $(BANK_ID).val() == null || $(BANK_ID).val() == undefined) {
			flag = false;
			$(BANK_ID).focus();
			alert("请选择扣款银行!");
			return;
		};
	}else if(COMPANYCODE2=''&&SUP_LEVEL=='A'&&COMPANY_NAME1=='陕重汽'){
		$.messager.alert("提示","请输入厂家编号");
	}

	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	
	if(FIRST_PAYMENT_TYPE=='1' && PAYMENT_STATUS!='1'){
		$.messager.alert("提示","首期付款方式为网银,放款政策只能选择全额放款!");
		return ;
	}
	
	var FINAL_TYPE = $("select[name='FINAL_TYPE']").find("option:selected").val();
	var FINAL_CUST_ID = $("select[name='FINAL_CUST_ID']").find("option:selected").val();
	
	var LEASE_TERM=$("#lease_term").val();
	if (LEASE_TERM == "" || LEASE_TERM == undefined || LEASE_TERM<=0) {
		flag = false;
		$("#lease_term").focus();
		$.messager.alert("提示","请输入期限");
		return;
	};
	var lease_period=0;
	if($("#lease_period").length > 0 )
	{
		lease_period=$("#lease_period").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入周期!");
		return;
	}
	var _payCountOfYear=accDiv(12,lease_period);
	
	var LEASE_PERIOD=$("#lease_period").find("option:selected").val();
	var YEAR_INTEREST=0;
	if($("#YEAR_INTEREST").length > 0 )
	{
		YEAR_INTEREST=$("#YEAR_INTEREST").val();
	}
	
	if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<=0) {
		flag = false;
		$("#YEAR_INTEREST").focus();
		$.messager.alert("提示","请维护商务政策，确保存在年利率！");
		return;
	};
	
	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000");
	
	var DELIVER_DATE="";
	if($("#DELIVER_DATE").length > 0 )
	{
		DELIVER_DATE=$("#DELIVER_DATE").val();
	}
	else
	{
		$.messager.alert("提示","请输入交换时间!");
		return;
	}
	
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请选择支付方式!");
		return;
	}
	
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
		$.messager.alert("提示","请输入首期付款日期！");
		return ;
	}
	
	var FEES=0;
	if($("#fees").length > 0 ){
		FEES=$("#fees").val();
	}
	
	var FEES_PRICE='0';
	if($("#FEES_PRICE").length > 0 ){
		FEES_PRICE=$("#FEES_PRICE").val();
	}
	
	var PRO_CODE=$("#PRO_CODE").val();
	var PRO_ID=$("#PRO_ID").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var BANK_ID=$(BANK_ID).val();
	
	 var COMPANY_ID;
	 var SUPPLIERS_ID;
	
	var flag=true;
	var Eq=[];
	 $(".eq_body_tr").each(function(){
				var temp={};
				temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").val();
				temp.COMPANY_CODE = $(this).find("[name='COMPANY_CODE']").val();
				temp.COMPANY_NAME = $(this).find("[name='COMPANY_NAME']").val();
				temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").val();
				temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_NAME']").val();
				COMPANY_ID=temp.COMPANY_ID;
				SUPPLIERS_ID=temp.SUPPLIERS_ID;
				//得到设备名称
				var eqName = $(this).find("select[name='THING_NAME']").find("option:selected");
				if ($(eqName).val() == "" || $(eqName).val() == null || $(eqName).val() == undefined) {
					flag = false;
					$(eqName).focus();
					$.messager.alert("提示","请选择设备名称");
					return;
				};
				temp.PRODUCT_ID = $(this).find("[name='THING_NAME']").val();
				temp.PRODUCT_NAME = $(this).find("[name='THING_NAME']").find("option:selected").text();
				
				//得到设备型号
				var eqCatena = $(this).find("select[name='PRODUCT_CATENA']").find("option:selected");
				if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
					flag = false;
					$(eqType).focus();
					$.messager.alert("提示","请选择设备系别");
					return;
				};
				temp.CATENA_ID = $(this).find("[name='PRODUCT_CATENA']").val();
				temp.CATENA_NAME = $(this).find("[name='PRODUCT_CATENA']").find("option:selected").text();
				
				//得到设备型号
				var eqType = $(this).find("select[name='THING_SPEC']").find("option:selected");
				if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
					flag = false;
					$(eqType).focus();
					$.messager.alert("提示","请选择设备型号");
					return;
				};
				temp.SPEC_ID = $(this).find("[name='THING_SPEC']").val();
				temp.SPEC_NAME = $(this).find("[name='THING_SPEC']").find("option:selected").text();
				
				//得到出厂编号
				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
//				if ($(WHOLE_ENGINE_CODE).val() == "" || $(WHOLE_ENGINE_CODE).val() == undefined || $(WHOLE_ENGINE_CODE).val()<=0) {
//					flag = false;
//					$(WHOLE_ENGINE_CODE).focus();
//					alert("请输入出厂编号");
//					return;
//				};
				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
				
				//得到出厂日期
				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
//				if ($(CERTIFICATE_DATE).val() == "" || $(CERTIFICATE_DATE).val() == undefined || $(CERTIFICATE_DATE).val()<=0) {
//					flag = false;
//					$(CERTIFICATE_DATE).focus();
//					alert("请输入出厂日期");
//					return;
//				};
				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
				
				//得到发动机编号
				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
//				if ($(ENGINE_TYPE).val() == "" || $(ENGINE_TYPE).val() == undefined || $(ENGINE_TYPE).val()<=0) {
//					flag = false;
//					$(ENGINE_TYPE).focus();
//					alert("请输入发动机编号");
//					return;
//				};
				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
				
				//得到车架号
				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
//				if ($(CAR_SYMBOL).val() == "" || $(CAR_SYMBOL).val() == undefined || $(CAR_SYMBOL).val()<=0) {
//					flag = false;
//					$(CAR_SYMBOL).focus();
//					alert("请输入车架号");
//					return;
//				};
				temp.CAR_SYMBOL = $(this).find("[name='CAR_SYMBOL']").val();
				
				temp.STAYBUY_PRICE = $(this).find("[name='STAYBUY_PRICE']").val();
				
				//得到设备的单价
				var unitPrice = $(this).find("input[name='UNIT_PRICE']");
				if ($(unitPrice).val() == "" || $(unitPrice).val() == undefined || $(unitPrice).val()<=0) {
					flag = false;
					$(unitPrice).focus();
					$.messager.alert("提示","请输入设备单价");
					return;
				};
				temp.UNIT_PRICE = $(this).find("[name='UNIT_PRICE']").val();
				
				//获取设备数量
				var amount = $(this).find("input[name='AMOUNT']");
				if ($(amount).val == "" || $(amount).val() == undefined  || $(amount).val()<=0) {
					flag = false;
					$(unitPrice).focus();
					$.messager.alert("提示","请输入设备数量");
					return;
				};
				temp.AMOUNT = $(this).find("[name='AMOUNT']").val();
				
				//获取小计
				var TOTAL_PRICE = $(this).find("input[name='TOTAL']");
				if ($(TOTAL_PRICE).val == "" || $(TOTAL_PRICE).val() == undefined  || $(TOTAL_PRICE).val()<=0) {
					flag = false;
					$(TOTAL_PRICE).focus();
					$.messager.alert("提示","请输入设备数量");
					return;
				};
				temp.TOTAL_PRICE = $(this).find("[name='TOTAL']").val();
				
				temp.UNIT = $(this).find("[name='UNIT']").val();
				temp.UNITTEST = $(this).find("[name='UNIT']").find("option:selected").text();
				temp.TOTAL = $(this).find("[name='TOTAL']").val();
				
				var project_mode=$("#PROJECT_MODEL").val();
				if(project_mode=='1'){
					//获取产地
					var PRODUCT_ADDRESS = $(this).find("input[name='PRODUCT_ADDRESS']");
//					if ($(PRODUCT_ADDRESS).val == "" || $(PRODUCT_ADDRESS).val() == undefined  || $(PRODUCT_ADDRESS).val()<=0) {
//						flag = false;
//						$(PRODUCT_ADDRESS).focus();
//						alert("请输入产地");
//						return;
//					};
					temp.PRODUCT_ADDRESS = $(this).find("[name='PRODUCT_ADDRESS']").val();
					
//					//获取合格证书
//					var CERTIFICATE_NUM = $(this).find("input[name='CERTIFICATE_NUM']");
//					if ($(CERTIFICATE_NUM).val == "" || $(CERTIFICATE_NUM).val() == undefined  || $(CERTIFICATE_NUM).val()<=0) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						alert("请输入合格证书");
//						return;
//					};
					temp.CERTIFICATE_NUM = $(this).find("[name='CERTIFICATE_NUM']").val();
					
					//获取限乘人数
					var LIMIT_NUM = $(this).find("input[name='LIMIT_NUM']");
					if ($(LIMIT_NUM).val == "" || $(LIMIT_NUM).val() == undefined  || $(LIMIT_NUM).val()=='undefined') {
						flag = false;
						$(LIMIT_NUM).focus();
						alert("请输入限乘人数");
						return;
					};
					temp.LIMIT_NUM = $(this).find("[name='LIMIT_NUM']").val();
					
					//获取机动车辆生产企业名称
					var COMPANY_FULLNAME = $(this).find("input[name='COMPANY_FULLNAME']");
//					if ($(COMPANY_FULLNAME).val == "" || $(COMPANY_FULLNAME).val() == undefined  || $(COMPANY_FULLNAME).val()<=0) {
//						flag = false;
//						$(COMPANY_FULLNAME).focus();
//						alert("请输入机动车辆生产企业名称");
//						return;
//					};
					temp.COMPANY_FULLNAME = $(this).find("[name='COMPANY_FULLNAME']").val();
					
					temp.IMPORT_NUM = $(this).find("[name='IMPORT_NUM']").val();//获取进口证明书号
					
					temp.INSPEC_NUM = $(this).find("[name='INSPEC_NUM']").val();//获取商检单号
					
					temp.TONNAGE = $(this).find("[name='TONNAGE']").val();//获取吨位
					
					
					//实际车辆开票名称
					var ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']");
//					if ($(ACTUAL_PRODUCT_NAME).val == "" || $(ACTUAL_PRODUCT_NAME).val() == undefined  || $(ACTUAL_PRODUCT_NAME).val()<=0) {
//						flag = false;
//						$(ACTUAL_PRODUCT_NAME).focus();
//						alert("请输入实际车辆开票名称");
//						return;
//					};
					temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
					
					//实际车辆开票型号
					var ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']");
//					if ($(ACTUAL_PRODUCT_TYPE).val == "" || $(ACTUAL_PRODUCT_TYPE).val() == undefined  || $(ACTUAL_PRODUCT_TYPE).val()<=0) {
//						flag = false;
//						$(ACTUAL_PRODUCT_TYPE).focus();
//						alert("请输入实际车辆开票型号");
//						return;
//					};
					temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
				}
				else if(project_mode=='3'){
					//实际车辆开票名称
					var ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']");
					if ($(ACTUAL_PRODUCT_NAME).val() == "" || $(ACTUAL_PRODUCT_NAME).val() == undefined  || $(ACTUAL_PRODUCT_NAME).val()<=0) {
						flag = false;
						$(ACTUAL_PRODUCT_NAME).focus();
						alert("请输入实际车辆开票名称");
						return;
					};
					temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
					
					//实际车辆开票型号
					var ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']");
					if ($(ACTUAL_PRODUCT_TYPE).val() == "" || $(ACTUAL_PRODUCT_TYPE).val() == undefined  || $(ACTUAL_PRODUCT_TYPE).val()<=0) {
						flag = false;
						$(ACTUAL_PRODUCT_TYPE).focus();
						alert("请输入实际车辆开票型号");
						return;
					};
					temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
				}
					
				Eq.push(temp);
		});
	 
	 
	 var guaranList=[];
	 $(".guaran_body_tr").each(function(){
				var temp={};
				temp.GUARAN_TYPE = $(this).find("select[name='GUARAN_TYPE']").find("option:selected").val();
				temp.GUARAN_NAME = $(this).find("[name='GUARAN_NAME']").val();
				temp.ORGANIZATION_CODE = $(this).find("[name='ORGANIZATION_CODE']").val();
				temp.GUARAN_PHONE = $(this).find("[name='GUARAN_PHONE']").val();
				temp.FAUSTPFAND = $(this).find("[name='FAUSTPFAND']").val();
				temp.GUARANTEE_ENTITY = $(this).find("[name='GUARANTEE_ENTITY']").val();
				guaranList.push(temp);
	 });
	 
		var GUARANTEE=$("#THIRD_PARTY_GUARANTEES").val();
		if(GUARANTEE=='1'){
			if(guaranList.length<1){
				$.messager.alert("提示","请录入担保信息");
				return ;
			}
		}
	 var REMARK=$("#REMARK").val();
	
	 var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+LEASE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&date="+DELIVER_DATE+"&project_code="+PRO_CODE+"&project_id="+PRO_ID+"&rows="+JSON.stringify(Rows);;
	//----------------准备还款计划数据--------结束
	var JBPM_ID=$("#JBPM_ID").val();
	var data_="UPDATE_PROJECT_AREA="+UPDATE_PROJECT_AREA+"&UPDATE_DELIVER_ADDRESS="+UPDATE_DELIVER_ADDRESS+"&UPDATE_DELIVER_DATE="+UPDATE_DELIVER_DATE+"&JBPM_ID="+JBPM_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE+"&PROJECT_MODEL="+PROJECT_MODEL+
			  "&PRO_CODE="+PRO_CODE+"&COMPANY_ID="+COMPANY_ID+"&SUP_ID="+SUPPLIERS_ID+"&PRO_ID="+PRO_ID+
			  "&PROJECT_ID="+PRO_ID+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+
			  "&REMARK="+REMARK+"&CLIENT_ID="+CLIENT_ID+"&BANK_ID="+BANK_ID+"&FINAL_TYPE="+FINAL_TYPE+
			  "&FINAL_CUST_ID="+FINAL_CUST_ID+"&INVOICE_TARGET_TYPE="+INVOICE_TARGET_TYPE+
			  "&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD+"&YEAR_INTEREST="+YEAR_INTEREST+
			  "&FEES="+FEES+"&FEES_PRICE="+FEES_PRICE+"&EqList="+JSON.stringify(Eq)+
			  "&guaranList="+JSON.stringify(guaranList)+"&param="+getFromData1("#addSchemeForm")+
			  "&MONEYALL="+MONEYALL+"&ZKL="+$("#ZKL").val()+"&COMPANY_CODE="+COMPANYCODE2;
	data_ = data_ + data1;
	var FIRST_MONEY_ALL=$("#FIRSTPAYALL").val();
	var MONEY_ALL=$("#MONEYALL").val();
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	data_=data_+"&FIRST_MONEY_ALL="+FIRST_MONEY_ALL+"&MONEY_ALL="+MONEY_ALL+"&FIRSTPAYDATE="+FIRSTPAYDATE;
	if(RENT_WAY_INVOICE!=''&&RENT_WAY_INVOICE!=null&&RENT_WAY_INVOICE!=undefined&&RENT_WAY_INVOICE!="undefined"){
		data_=data_+"&RENT_WAY_INVOICE="+RENT_WAY_INVOICE;
	}
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:_basePath+"/project/project!updateSchemeForProject.action",
		data:data_,
		success:function(json){

			if(PROJECT_MODEL == '3'){
				showEquipmentFQ(PRO_ID);
			}
			
			if(json.flag){
					$.messager.alert("提示","方案保存成功！");
//					$("#JBPM_ID").val('');
//					$("#submitAddScheme_1").linkbutton("disable");
//					$("#submitAddScheme_1").attr("disabled","disabled");
			}else{
				$.messager.alert("提示",json.msg);
			}
		}
	});	
}

//修改发起评审
function submitUpdateScheme()
{
	var BALANCE_LOAN_SWITCH=$("#BALANCE_LOAN_SWITCH").val();
	var B_MODEL_SWITCH=$("#B_MODEL_SWITCH").val();
	var B_MODEL_SWITCH=$("#B_MODEL_SWITCH").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var IRREGULAR_REPAYMENT_SWITCH=$("#IRREGULAR_REPAYMENT_SWITCH").val();
	var PAY_WAY=$("#PAY_WAY").val();
	//差额放款 供应商差额放款开关关闭
	if(PAYMENT_STATUS=='2'&&BALANCE_LOAN_SWITCH=='1'){
		$.messager.alert("提示","您没有差额放款的权限！");
		return;
	}else if(B_MODEL_SWITCH=='1'&&GUARANTEE_MODEL=='B_MODE'){
		//供应商B模式  B模式开关关闭
		$.messager.alert("提示","您没有担保方式为B模式的权限！");
		return;
	}else if(IRREGULAR_REPAYMENT_SWITCH=='1'&&(PAY_WAY=='2'||PAY_WAY=='3')){
		//供应商  季度内不规则还款关闭
		$.messager.alert("提示","您没有季度内不规则还款的权限！");
		return;
	}
	
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	var MONEYALL=$("input[name='MONEYALL']").val();//租金合计
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));
	refinsncingType();
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	if (PLATFORM_TYPE == "" || PLATFORM_TYPE == undefined || PLATFORM_TYPE<0) {
		flag = false;
		return;
	};
	
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
	//得到银行信息  判断首付款或租金付款方式为网银时银行信息必填
	var BANK_ID = $("select[name='BANK_ID']").find("option:selected");
	if(FIRST_PAYMENT_TYPE == "1" || RENT_PAYMENT_TYPE == "1"){
		if ($(BANK_ID).val() == "" || $(BANK_ID).val() == null || $(BANK_ID).val() == undefined) {
			flag = false;
			$(BANK_ID).focus();
			alert("请选择扣款银行!");
			return;
		};
	}

	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	
	if(FIRST_PAYMENT_TYPE=='1' && PAYMENT_STATUS!='1'){
		$.messager.alert("提示","请选择全额放款");
		return ;
	}
	
	var FINAL_TYPE = $("select[name='FINAL_TYPE']").find("option:selected").val();
	var FINAL_CUST_ID = $("select[name='FINAL_CUST_ID']").find("option:selected").val();
	
	var LEASE_TERM=$("#lease_term").val();
	if (LEASE_TERM == "" || LEASE_TERM == undefined || LEASE_TERM<=0) {
		flag = false;
		$("#lease_term").focus();
		alert("请输入期限");
		return;
	};
	
	var lease_period=0;
	if($("#lease_period").length > 0 )
	{
		lease_period=$("#lease_period").find("option:selected").val();
	}
	else
	{
		alert("请输入周期!");
		return;
	}
	var _payCountOfYear=accDiv(12,lease_period);
	
	var LEASE_PERIOD=$("#lease_period").find("option:selected").val();
	var YEAR_INTEREST=0;
	if($("#YEAR_INTEREST").length > 0 )
	{
		YEAR_INTEREST=$("#YEAR_INTEREST").val();
	}
	
	if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<=0) {
		flag = false;
		$("#YEAR_INTEREST").focus();
		alert("请维护商务政策，确保存在年利率！");
		return;
	};
	
	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000");
	
	var DELIVER_DATE="";
	if($("#DELIVER_DATE").length > 0 )
	{
		DELIVER_DATE=$("#DELIVER_DATE").val();
	}
	else
	{
		alert("请输入交换时间!");
		return;
	}
	
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		alert("请选择支付方式!");
		return;
	}
	
	var FEES=0;
	if($("#fees").length > 0 ){
		FEES=$("#fees").val();
	}
	
	var FEES_PRICE='0';
	if($("#FEES_PRICE").length > 0 ){
		FEES_PRICE=$("#FEES_PRICE").val();
	}
	
	var PRO_CODE=$("#PRO_CODE").val();
	var PRO_ID=$("#PRO_ID").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var BANK_ID=$(BANK_ID).val();
	
	 var COMPANY_ID;
	 var SUPPLIERS_ID;
	
	var flag=true;
	var Eq=[];
	 $(".eq_body_tr").each(function(){
				var temp={};
				temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").val();
				temp.COMPANY_CODE = $(this).find("[name='COMPANY_CODE']").val();
				temp.COMPANY_NAME = $(this).find("[name='COMPANY_NAME']").val();
				temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").val();
				temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_NAME']").val();
				COMPANY_ID=temp.COMPANY_ID;
				SUPPLIERS_ID=temp.SUPPLIERS_ID;
				//得到设备名称
				var eqName = $(this).find("select[name='THING_NAME']").find("option:selected");
				if ($(eqName).val() == "" || $(eqName).val() == null || $(eqName).val() == undefined) {
					flag = false;
					$(eqName).focus();
					alert("请选择设备名称");
					return;
				};
				temp.PRODUCT_ID = $(this).find("[name='THING_NAME']").val();
				temp.PRODUCT_NAME = $(this).find("[name='THING_NAME']").find("option:selected").text();
				
				//得到设备型号
				var eqCatena = $(this).find("select[name='PRODUCT_CATENA']").find("option:selected");
				if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
					flag = false;
					$(eqType).focus();
					alert("请选择设备系别");
					return;
				};
				temp.CATENA_ID = $(this).find("[name='PRODUCT_CATENA']").val();
				temp.CATENA_NAME = $(this).find("[name='PRODUCT_CATENA']").find("option:selected").text();
				
				//得到设备型号
				var eqType = $(this).find("select[name='THING_SPEC']").find("option:selected");
				if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
					flag = false;
					$(eqType).focus();
					alert("请选择设备型号");
					return;
				};
				temp.SPEC_ID = $(this).find("[name='THING_SPEC']").val();
				temp.SPEC_NAME = $(this).find("[name='THING_SPEC']").find("option:selected").text();
				
				//得到出厂编号
				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
				
				//得到出厂日期
				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
				
				//得到发动机编号
				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
				
				//得到车架号
				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
				temp.CAR_SYMBOL = $(this).find("[name='CAR_SYMBOL']").val();
				
				temp.STAYBUY_PRICE = $(this).find("[name='STAYBUY_PRICE']").val();
				
				//得到设备的单价
				var unitPrice = $(this).find("input[name='UNIT_PRICE']");
				if ($(unitPrice).val() == "" || $(unitPrice).val() == undefined || $(unitPrice).val()<=0) {
					flag = false;
					$(unitPrice).focus();
					alert("请输入设备单价");
					return;
				};
				temp.UNIT_PRICE = $(this).find("[name='UNIT_PRICE']").val();
				
				//获取设备数量
				var amount = $(this).find("input[name='AMOUNT']");
				if ($(amount).val == "" || $(amount).val() == undefined  || $(amount).val()<=0) {
					flag = false;
					$(unitPrice).focus();
					alert("请输入设备数量");
					return;
				};
				temp.AMOUNT = $(this).find("[name='AMOUNT']").val();
				
				//获取小计
				var TOTAL_PRICE = $(this).find("input[name='TOTAL']");
				if ($(TOTAL_PRICE).val == "" || $(TOTAL_PRICE).val() == undefined  || $(TOTAL_PRICE).val()<=0) {
					flag = false;
					$(TOTAL_PRICE).focus();
					alert("请输入设备数量");
					return;
				};
				temp.TOTAL_PRICE = $(this).find("[name='TOTAL']").val();
				
				temp.UNIT = $(this).find("[name='UNIT']").val();
				temp.UNITTEST = $(this).find("[name='UNIT']").find("option:selected").text();
				temp.TOTAL = $(this).find("[name='TOTAL']").val();
				
				var project_mode=$("#PROJECT_MODEL").val();
				if(project_mode=='1'){
					//获取产地
					var PRODUCT_ADDRESS = $(this).find("input[name='PRODUCT_ADDRESS']");
					temp.PRODUCT_ADDRESS = $(this).find("[name='PRODUCT_ADDRESS']").val();
					temp.CERTIFICATE_NUM = $(this).find("[name='CERTIFICATE_NUM']").val();
					
					//获取限乘人数
					var LIMIT_NUM = $(this).find("input[name='LIMIT_NUM']");
					if ($(LIMIT_NUM).val == "" || $(LIMIT_NUM).val() == undefined  || $(LIMIT_NUM).val()=='undefined') {
						flag = false;
						$(LIMIT_NUM).focus();
						alert("请输入限乘人数");
						return;
					};
					temp.LIMIT_NUM = $(this).find("[name='LIMIT_NUM']").val();
					
					//获取机动车辆生产企业名称
					var COMPANY_FULLNAME = $(this).find("input[name='COMPANY_FULLNAME']");
					temp.COMPANY_FULLNAME = $(this).find("[name='COMPANY_FULLNAME']").val();
					
					temp.IMPORT_NUM = $(this).find("[name='IMPORT_NUM']").val();//获取进口证明书号
					
					temp.INSPEC_NUM = $(this).find("[name='INSPEC_NUM']").val();//获取商检单号
					
					temp.TONNAGE = $(this).find("[name='TONNAGE']").val();//获取吨位
					
					
					//实际车辆开票名称
					var ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']");
					temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
					
					//实际车辆开票型号
					var ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']");
					temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
				}
				else if(project_mode=='3'){
					//实际车辆开票名称
					var ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']");
					if ($(ACTUAL_PRODUCT_NAME).val() == "" || $(ACTUAL_PRODUCT_NAME).val() == undefined  || $(ACTUAL_PRODUCT_NAME).val()<=0) {
						flag = false;
						$(ACTUAL_PRODUCT_NAME).focus();
						alert("请输入实际车辆开票名称");
						return;
					};
					temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
					
					//实际车辆开票型号
					var ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']");
					if ($(ACTUAL_PRODUCT_TYPE).val() == "" || $(ACTUAL_PRODUCT_TYPE).val() == undefined  || $(ACTUAL_PRODUCT_TYPE).val()<=0) {
						flag = false;
						$(ACTUAL_PRODUCT_TYPE).focus();
						alert("请输入实际车辆开票型号");
						return;
					};
					temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
				}
					
				Eq.push(temp);
		});
	 
	 
	 var guaranList=[];
	 $(".guaran_body_tr").each(function(){
				var temp={};
				temp.GUARAN_TYPE = $(this).find("select[name='GUARAN_TYPE']").find("option:selected").val();
				temp.GUARAN_NAME = $(this).find("[name='GUARAN_NAME']").val();
				temp.ORGANIZATION_CODE = $(this).find("[name='ORGANIZATION_CODE']").val();
				temp.GUARAN_PHONE = $(this).find("[name='GUARAN_PHONE']").val();
				guaranList.push(temp);
	 });
	 var REMARK=$("#REMARK").val();
	 var PROJECT_MODEL=$("#PROJECT_MODEL").val();
	 var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+LEASE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&date="+DELIVER_DATE+"&project_code="+PRO_CODE+"&project_id="+PRO_ID+"&rows="+JSON.stringify(Rows);;
	//----------------准备还款计划数据--------结束
	
	var JBPM_ID='';
	var data_="JBPM_ID="+JBPM_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE+"&PROJECT_MODEL="+PROJECT_MODEL+
			  "&PRO_CODE="+PRO_CODE+"&COMPANY_ID="+COMPANY_ID+"&SUP_ID="+SUPPLIERS_ID+"&PRO_ID="+PRO_ID+
			  "&PROJECT_ID="+PRO_ID+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+
			  "&REMARK="+REMARK+"&CLIENT_ID="+CLIENT_ID+"&BANK_ID="+BANK_ID+"&FINAL_TYPE="+FINAL_TYPE+
			  "&FINAL_CUST_ID="+FINAL_CUST_ID+"&INVOICE_TARGET_TYPE="+INVOICE_TARGET_TYPE+
			  "&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD+"&YEAR_INTEREST="+YEAR_INTEREST+
			  "&FEES="+FEES+"&FEES_PRICE="+FEES_PRICE+"&EqList="+JSON.stringify(Eq)+
			  "&guaranList="+JSON.stringify(guaranList)+"&param="+getFromData1("#addSchemeForm")+
			  "&MONEYALL="+MONEYALL;
	data_ = data_ + data1;
	var FIRST_MONEY_ALL=$("#FIRSTPAYALL").val();
	var MONEY_ALL=$("#MONEY_ALL").val();
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	data_=data_+"&FIRST_MONEY_ALL="+FIRST_MONEY_ALL+"&MONEY_ALL="+MONEY_ALL+"&FIRSTPAYDATE="+FIRSTPAYDATE;
	if(RENT_WAY_INVOICE!=''&&RENT_WAY_INVOICE!=null&&RENT_WAY_INVOICE!=undefined&&RENT_WAY_INVOICE!="undefined"){
		data_=data_+"&RENT_WAY_INVOICE="+RENT_WAY_INVOICE;
	}
	$("#submitUpdateScheme_").removeAttr("onclick");
	$("#submitUpdateScheme_").attr("title","流程发起中...");
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:_basePath+"/project/project!updateSchemeForProject.action",
		data:data_,
		success:function(json){
			if(json.flag){
					$.messager.alert("提示","发起流程成功！");
					top.closeTab("项目一览");
					top.addTab("项目一览",_basePath+"/project/project!getDataList.action?PRO_CODE="+PRO_CODE);
					top.closeTab(PRO_CODE+"方案修改");
			}else{
				$.messager.alert("提示",json.msg);
				$("#submitUpdateScheme_").attr("onclick","submitUpdateScheme()");
				$("#submitUpdateScheme_").removeAttr("title");
			}
		}
	});	
	
//	$.ajax({
//		url:_basePath+"/project/project!doCheckCompanyCredit.action",
//		type:"post",
//		data:"PROJECT_ID="+PRO_ID+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+"&COMPANY_ID="+COMPANY_ID+"&SUP_ID="+SUPPLIERS_ID,
//		dataType:"json",
//		success:function(rst){
//			if(rst.flag){
//
//			}else{
//				$.messager.alert("提示","请确认厂商授信额度是否过低");
//			}
//		},
//		error:function(rst){
//			$.messager.alert("提示",rst.msg);
//		}
//	});	
}

function STARTAPPCONFIRM(){
	var START_DATE=$("#DELIVER_DATE").datebox("getValue");
	var PROJECT_ID=$("#PROJECT_ID").val();
	var LOAN_ACCOUNT=$("#LOAN_ACCOUNT").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var POWER=$("#POWER").val();
	var DATE_COMPLEMENT=$("#DATE_COMPLEMENT").val();
	var DELIVER_ADDRESS=$("#DELIVER_ADDRESS").val();
	var LOAN_JINE=$("#LOAN_JINE").val();
	$.ajax({
		type:"post",
		url:_basePath+"/project/project!doUpdateStartDate.action",
		data:"PROJECT_ID="+PROJECT_ID+"&START_DATE="+START_DATE+"&LOAN_ACCOUNT="+LOAN_ACCOUNT
			 +"&PAYMENT_STATUS="+PAYMENT_STATUS+"&POWER="+POWER+"&DATE_COMPLEMENT="+DATE_COMPLEMENT
			 +"&DELIVER_ADDRESS="+encodeURI(DELIVER_ADDRESS)+"&LOAN_JINE="+LOAN_JINE,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","操作成功！");
			}else{
				$.messager.alert("提示",json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","请联系网络管理员");
		}
	});
}

function SIGNED_DATE(){
	var SIGNED_DATE=$("#SIGNED_DATE").datebox('getValue');
	var PROJECT_ID=$("#PROJECT_ID").val();
	var LOAN_ACCOUNT=$("#LOAN_ACCOUNT").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var LOAN_ACCOUNT_NAME=$("#LOAN_ACCOUNT_NAME").val();
	var LOAN_JINE=$("#LOAN_JINE").val();
//	var POWER=$("#POWER").val();
	var DATE_COMPLEMENT=$("#DATE_COMPLEMENT").val();
	var COMPANY_NAME1=$("#COMPANY_NAME1").val();
	var PROJECT_MODEL=$("#PROJECT_MODEL").val();
	
	var array=new Array();
	$(".eqTr").each(function(){
		var temp={};
		temp.EQ_ID=$(this).find("[name='EQ_ID']").val();
		temp.WHOLE_ENGINE_CODE=$(this).find("[name='WHOLE_ENGINE_CODE']").val();
		temp.CERTIFICATE_DATE=$(this).find("#CERTIFICATE_DATE").datebox('getValue');
		temp.ENGINE_TYPE=$(this).find("[name='ENGINE_TYPE']").val();
		temp.CAR_SYMBOL=$(this).find("[name='CAR_SYMBOL']").val();
		if(COMPANY_NAME1=="潍柴重机"){
			temp.GENERATOR_MODEL=$(this).find("[name='GENERATOR_MODEL']").val();
			temp.GENERATOR_NUMBER=$(this).find("[name='GENERATOR_NUMBER']").val();
		}else if(PROJECT_MODEL=='1'){
			temp.PRODUCT_ADDRESS=$(this).find("[name='PRODUCT_ADDRESS']").val();
			temp.CERTIFICATE_NUM=$(this).find("[name='CERTIFICATE_NUM']").val();
			temp.LIMIT_NUM=$(this).find("[name='LIMIT_NUM']").val();
			temp.COMPANY_FULLNAME=$(this).find("[name='COMPANY_FULLNAME']").val();
			temp.IMPORT_NUM=$(this).find("[name='IMPORT_NUM']").val();
			temp.INSPEC_NUM=$(this).find("[name='INSPEC_NUM']").val();
			temp.TONNAGE=$(this).find("[name='TONNAGE']").val();
			temp.ACTUAL_PRODUCT_NAME=$(this).find("[name=ACTUAL_PRODUCT_NAME]").val();
			temp.ACTUAL_PRODUCT_TYPE=$(this).find("[name=ACTUAL_PRODUCT_TYPE]").val();
		}else if(PROJECT_MODEL=='3'){
			temp.ACTUAL_PRODUCT_NAME=$(this).find("[name=ACTUAL_PRODUCT_NAME]").val();
			temp.ACTUAL_PRODUCT_TYPE=$(this).find("[name=ACTUAL_PRODUCT_TYPE]").val();
		}
		array.push(temp);
	});
	$.ajax({
		type:"post",
		url:_basePath+"/project/project!doUpdateSignedDate.action",
		data:"PROJECT_ID="+PROJECT_ID+"&SIGNED_DATE="+SIGNED_DATE+"&LOAN_ACCOUNT="+LOAN_ACCOUNT+"&LOAN_JINE="+LOAN_JINE+
			  "&LOAN_ACCOUNT_NAME="+LOAN_ACCOUNT_NAME+"&PAYMENT_STATUS="+PAYMENT_STATUS+"&DATE_COMPLEMENT="+DATE_COMPLEMENT+
			  "&EQUIPMENT="+JSON.stringify(array),
		dataType:"json",
		success:function(json){
			if(json.flag){
//				$.messager.alert("提示","保存成功!");
				STARTAPPCONFIRM();
			}else{
				$.messager.alert("提示",json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","请联系网络管理员");
		}
	});
}

function model_Changer(obj){
	var project_mode=$(obj).val();
	var COMPANY_NAME1=$("input[name=COMPANY_NAME1]").val();
	if(COMPANY_NAME1=='潍柴重机'){
			var trHead='<td bgcolor="#fbfcfc" width="150" class="hiddenTD">发电机型号</td><td bgcolor="#fbfcfc" width="150" class="hiddenTD">发电机编号</td>';
			var tr='<td class="hiddenTD"><input name="GENERATOR_MODEL" id="GENERATOR_MODEL" style="width:90px"></td><td class="hiddenTD"><input name="GENERATOR_NUMBER" id="GENERATOR_NUMBER" style="width:90px"></td>';		
			$(".trHead").append(trHead);
	}else if(project_mode=='1'){
		var trHead='<td bgcolor="#fbfcfc" width="80px" class="hiddenTD"><span style="color:red">*</span>产地</td><td bgcolor="#fbfcfc" width="100" class="hiddenTD"><span style="color:red">*</span>合格证书</td><td bgcolor="#fbfcfc" width="100" class="hiddenTD"><span style="color:red">*</span>限乘人数</td><td bgcolor="#fbfcfc" width="170" class="hiddenTD"><span style="color:red">*</span>机动车辆生产企业名称（全称）</td><td bgcolor="#fbfcfc" width="100" class="hiddenTD">进口证明书号</td><td bgcolor="#fbfcfc" width="100" class="hiddenTD">商检单号</td><td bgcolor="#fbfcfc" width="100"  class="hiddenTD">吨位</td><td bgcolor="#fbfcfc" width="250" class="hiddenTD"><span style="color:red">*</span>实际车辆开票名称（<span style="color:red">车辆类型</span>）</td><td bgcolor="#fbfcfc" width="250" class="hiddenTD"><span style="color:red">*</span>实际车辆开票型号（<span style="color:red">车厂牌型号</span>）</td>';
		var tr='<td class="hiddenTD"><input name="PRODUCT_ADDRESS" id="PRODUCT_ADDRESS" style="width:90px"></td><td class="hiddenTD"><input name="CERTIFICATE_NUM" id="CERTIFICATE_NUM" style="width:90px"></td><td class="hiddenTD"><input name="LIMIT_NUM" id="LIMIT_NUM"  style="width:90px"></td><td class="hiddenTD"><input name="COMPANY_FULLNAME" id="COMPANY_FULLNAME" style="width:190px"></td><td class="hiddenTD"><input name="IMPORT_NUM" id="IMPORT_NUM" style="width:90px"></td><td class="hiddenTD"><input name="INSPEC_NUM" id="INSPEC_NUM" style="width:90px"></td><td class="hiddenTD"><input name="TONNAGE" id="TONNAGE" style="width:90px"></td><td class="hiddenTD"><input name="ACTUAL_PRODUCT_NAME" id="ACTUAL_PRODUCT_NAME" style="width:190px"></td><td class="hiddenTD"><input name="ACTUAL_PRODUCT_TYPE" id="ACTUAL_PRODUCT_TYPE" style="width:190px"></td>';
		$(".eq_body_tr").append(tr);
	}else if(project_mode=='3'){
		var trHead='实际车辆开票名称（<span style="color:red">车辆类型</span>）</td><td bgcolor="#fbfcfc" width="250" class="hiddenTD"><span style="color:red">*</span>实际车辆开票型号（<span style="color:red">车厂牌型号</span>）</td>';
		var tr='<td class="hiddenTD"><input name="ACTUAL_PRODUCT_NAME" id="ACTUAL_PRODUCT_NAME" style="width:190px"></td><td class="hiddenTD"><input name="ACTUAL_PRODUCT_TYPE" id="ACTUAL_PRODUCT_TYPE" style="width:190px"></td>';
		$(".eq_body_tr").append(tr);
	}else{
		$(".hiddenTD").remove();
	}
}

function finalCustChanger(obj){
	var FINAL_TYPE=$(obj).find("option:selected").val();
	var CLIENT_ID=jQuery.trim($("#CLIENT_ID").val());
	var CUSTOMER_NAME=jQuery.trim($("#CUSTOMER_NAME").val());
	var CARD_ORA_NO_INIT=jQuery.trim($("#CARD_ORA_NO_INIT").val());
	var tr=$(obj).parent().parent();
	var spec=$(tr).find("select[name='FINAL_CUST_ID']");
	var bankid=$(tr).find("select[name='BANK_ID']");
	$(bankid).empty();
	$(bankid).append($("<option>").val("").text("提示：请先维护扣款银行否则视为网银电汇"));
	if(FINAL_TYPE=='0'){
		$(spec).empty();
		$(spec).append($("<option>").val("").text("--请选择--"));
		$(spec).append($("<option>").val(CLIENT_ID).text(CUSTOMER_NAME+"("+CARD_ORA_NO_INIT+")"));
	}
	else if(FINAL_TYPE=='1'){
		
		$.ajax({
			url:_basePath+"/project/project!queryCustByType.action?FINAL_TYPE="+FINAL_TYPE+"&CLIENT_ID="+CLIENT_ID,
			type:"post",
			dataType:"json",
			success:function (data)
			{
				$(spec).empty();
				$(spec).append($("<option>").val("").text("--请选择--"));
				for(var i=0;i<data.length;i++){
					$(spec).append($("<option>").val(data[i].CUST_ID).text(data[i].CUST_NAME+"("+data[i].CARD_ORA_NO+")"));
				}
			}
		});
	}
}

function finalBankChanger(obj){
	var FINAL_CUST=$(obj).find("option:selected").val();
	var tr=$(obj).parent().parent();
	var spec=$(tr).find("select[name='BANK_ID']");
		$.ajax({
			url:_basePath+"/project/project!queryBankByCust.action?FINAL_CUST="+FINAL_CUST,
			type:"post",
			dataType:"json",
			success:function (data)
			{
				$(spec).empty();
				if(data.length<=0){
					$(spec).append($("<option>").val("").text("提示：请先维护扣款银行否则视为网银电汇"));
				}
				for(var i=0;i<data.length;i++){
					var textName=data[i].BANK_CUSTNAME+" - "+data[i].BANK_NAME+" （"+data[i].BANK_ACCOUNT+"）";
					$(spec).append($("<option>").val(data[i].CO_ID).text(textName));
				}
			}
		});
	
}

function refinsncingType(){
	var COMPANY_NAME=$("input[name=COMPANY_NAME]").val();
	var PLATFORM_TYPE="";
	if(COMPANY_NAME=='山重建机'){
		var refin=$("select[name=REFINSNCING_RENT]").find("option:selected").val();
		if(refin=='yes')
		{
			//直租回租自己选
			PLATFORM_TYPE=$("#PLATFORM_TYPE_Select").val();
			
		}
		else{
			var APPLIY_SCOPE=$("select[name=APPLIY_SCOPE]").find("option:selected").val();//商务政策使用范围
			if(APPLIY_SCOPE=='NEW'){
				var PLEDGE_STATUS=$("select[name=PLEDGE_STATUS]").find("option:selected").text();//牌抵挂
				if(PLEDGE_STATUS=='不上牌'){
					//直租
					PLATFORM_TYPE=0;
				}
				else{
					//回租
					var PROJECT_MODEL=$("input[name=PROJECT_MODEL]").val();
					if(PROJECT_MODEL=='3'){
						PLATFORM_TYPE=1;
					}
					else if(PROJECT_MODEL=='6'||PROJECT_MODEL=='7'||PROJECT_MODEL=='8'||PROJECT_MODEL=='9'){
						PLATFORM_TYPE=1;
					}
					else{
						PLATFORM_TYPE=0;
					}
				}
			}
			else{
				var PLEDGE_STATUS=$("select[name=PLEDGE_STATUS]").find("option:selected").text();
				var PROJECT_MODEL=$("input[name=PROJECT_MODEL]").val();
				if(PLEDGE_STATUS=='不上牌'){
					//回租
					if(PROJECT_MODEL=='3'){
						PLATFORM_TYPE=1;
					}
					else if(PROJECT_MODEL=='6'||PROJECT_MODEL=='7'||PROJECT_MODEL=='8'||PROJECT_MODEL=='9'){
						PLATFORM_TYPE=1;
					}
					else{
						PLATFORM_TYPE=0;
					}
				}
			}
		}
	}
	else{
		var APPLIY_SCOPE=$("select[name=APPLIY_SCOPE]").find("option:selected").val();//商务政策使用范围
		if(APPLIY_SCOPE=='NEW'){
			var PLEDGE_STATUS=$("select[name=PLEDGE_STATUS]").find("option:selected").text();//牌抵挂
			if(PLEDGE_STATUS=='不上牌'){
				//直租
				PLATFORM_TYPE=0;
			}
			else{
				//回租
				var PROJECT_MODEL=$("input[name=PROJECT_MODEL]").val();
				if(PROJECT_MODEL=='3'){
					PLATFORM_TYPE=1;
				}
				else if(PROJECT_MODEL=='6'||PROJECT_MODEL=='7'||PROJECT_MODEL=='8'||PROJECT_MODEL=='9'){
					PLATFORM_TYPE=1;
				}
				else{
					PLATFORM_TYPE=0;
				}
			}
		}
		else{
			var PLEDGE_STATUS=$("select[name=PLEDGE_STATUS]").find("option:selected").text();
			var PROJECT_MODEL=$("input[name=PROJECT_MODEL]").val();
			if(PLEDGE_STATUS=='不上牌'){
				//回租
				if(PROJECT_MODEL=='3'){
					PLATFORM_TYPE=1;
				}
				else if(PROJECT_MODEL=='6'||PROJECT_MODEL=='7'||PROJECT_MODEL=='8'||PROJECT_MODEL=='9'){
					PLATFORM_TYPE=1;
				}
				else{
					PLATFORM_TYPE=0;
				}
			}
		}
	}
	$("#PLATFORM_TYPE").val(PLATFORM_TYPE);
	
}

function refinsnChage(obj){
	var refin=$(obj).find("option:selected").val();
	if(refin=='yes'){
		$(".yincangDiv").attr("style","");
	}
	else{
		$(".yincangDiv").attr("style","display:none");
	}
}

function revfinsnInit(){
	var COMPANY_NAME=$("input[name=COMPANY_NAME]").val();
	if(COMPANY_NAME=='山重建机'){
		var refin=$("select[name=REFINSNCING_RENT]").find("option:selected").val();
		if(refin=='yes')
		{
			$(".yincangDiv").attr("style","");
		}
	}
}

/**
 * 减法
 * @param arg1
 * @param arg2
 * @return
 */
function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

function fpOpen(obj){
	var tr=$(obj).parent().parent();
	var ENGINE_TYPE=$(tr).find("input[name=ENGINE_TYPE]").val();//发动机编号
	var CAR_SYMBOL=$(tr).find("input[name=CAR_SYMBOL]").val();//车架号
	var PRODUCT_ADDRESS=$(tr).find("input[name=PRODUCT_ADDRESS]").val();//产地 
	var CERTIFICATE_NUM=$(tr).find("input[name=CERTIFICATE_NUM]").val();//合格证书
	var LIMIT_NUM=$(tr).find("input[name=LIMIT_NUM]").val();//限乘人数 
	var IMPORT_NUM=$(tr).find("input[name=IMPORT_NUM]").val();//进口证明书号 
	var INSPEC_NUM=$(tr).find("input[name=INSPEC_NUM]").val();//商检单号 
	var TONNAGE=$(tr).find("input[name=TONNAGE]").val();//吨位 
	var ACTUAL_PRODUCT_NAME=$(tr).find("input[name=ACTUAL_PRODUCT_NAME]").val();//车辆类型 
	var ACTUAL_PRODUCT_TYPE=$(tr).find("input[name=ACTUAL_PRODUCT_TYPE]").val();//车厂牌型号 
	
	$("input[name=biaoji]").val("0");
	$(tr).find("input[name=biaoji]").val("1");
	
	$("#ENGINE_TYPE_UIN").val(ENGINE_TYPE);
	$("#CAR_SYMBOL_UIN").val(CAR_SYMBOL);
	$("#PRODUCT_ADDRESS_UIN").val(PRODUCT_ADDRESS);
	$("#CERTIFICATE_NUM_UIN").val(CERTIFICATE_NUM);
	$("#LIMIT_NUM_UIN").val(LIMIT_NUM);
	$("#IMPORT_NUM_UIN").val(IMPORT_NUM);
	$("#INSPEC_NUM_UIN").val(INSPEC_NUM);
	$("#TONNAGE_UIN").val(TONNAGE);
	$("#ACTUAL_PRODUCT_NAME_UIN").val(ACTUAL_PRODUCT_NAME);
	$("#ACTUAL_PRODUCT_TYPE_UIN").val(ACTUAL_PRODUCT_TYPE);
	$("#ObjUpdate").val(obj);
	
	$("#dlgFaPiao").dialog('open');
}


function fpSave(){
	$("input[name=biaoji]").each(function(){
		if($(this).val()=='1'){
			var tr=$(this).parent().parent();
			var ENGINE_TYPE=$("#ENGINE_TYPE_UIN").val();
			var CAR_SYMBOL=$("#CAR_SYMBOL_UIN").val();
			var PRODUCT_ADDRESS=$("#PRODUCT_ADDRESS_UIN").val();
			var CERTIFICATE_NUM=$("#CERTIFICATE_NUM_UIN").val();
			var LIMIT_NUM=$("#LIMIT_NUM_UIN").val();
			var IMPORT_NUM=$("#IMPORT_NUM_UIN").val();
			var INSPEC_NUM=$("#INSPEC_NUM_UIN").val();
			var TONNAGE=$("#TONNAGE_UIN").val();
			var ACTUAL_PRODUCT_NAME=$("#ACTUAL_PRODUCT_NAME_UIN").val();
			var ACTUAL_PRODUCT_TYPE=$("#ACTUAL_PRODUCT_TYPE_UIN").val();
			
			$(tr).find("input[name=ENGINE_TYPE]").val(ENGINE_TYPE);
			$(tr).find("input[name=CAR_SYMBOL]").val(CAR_SYMBOL);
			$(tr).find("input[name=PRODUCT_ADDRESS]").val(PRODUCT_ADDRESS);
			$(tr).find("input[name=CERTIFICATE_NUM]").val(CERTIFICATE_NUM);
			$(tr).find("input[name=LIMIT_NUM]").val(LIMIT_NUM);
			$(tr).find("input[name=IMPORT_NUM]").val(IMPORT_NUM);
			$(tr).find("input[name=INSPEC_NUM]").val(INSPEC_NUM);
			$(tr).find("input[name=TONNAGE]").val(TONNAGE);
			$(tr).find("input[name=ACTUAL_PRODUCT_NAME]").val(ACTUAL_PRODUCT_NAME);
			$(tr).find("input[name=ACTUAL_PRODUCT_TYPE]").val(ACTUAL_PRODUCT_TYPE);
			$("#dlgFaPiao").dialog('close');
		}
	});
	
}

//显示该客户逾期项目信息
function seProjByKHMC(KHMC){
	top.addTab("该客户逾期信息", _basePath + "/call/OverdueClientStatistics!yqSearch.action?KHMC=" + encodeURI(KHMC));
}

function ExpZCBModel(){
	window.location.href=_basePath+"/project/project!ExpZCBModel.action";
}
/**
 * 打开资产包导入窗口
 * @return
 *
 * @author King 2014-2-11
 */
function OPENZCBModel(){
	$("#zcbimpdiv").dialog("open");
	$("#FILE_PROJECT_NAME").val($("#PROJECT_NAME").val());
}
function closeZCB(){
	$("#zcbimpdiv").dialog("close");
}
/**
 * 导入资产包
 * @return
 *
 * @author King 2014-2-11
 */
function IMPZCB(){
	var url=_basePath+"/project/project!impZCB.action";
	$('#zcbform').form('submit',
			{
                onSubmit: function()
				{
					PROJECT_NAME=$("#ZCB").val();
					if(PROJECT_NAME==null||PROJECT_NAME==''||PROJECT_NAME=='undefined'||PROJECT_NAME==undefined){
						$.messager.alert("提示","请选择需要导入的文件");
						return false;
					}
                },
                url:url+"?PROJECT_NAME="+$("#FILE_PROJECT_NAME").val()+"&fileN="+$('#ZCB').val(),
				success:function(data)
				{	var json=$.parseJSON(data);
                	if(json.flag){
                		$.messager.alert("提示","导入成功!");
                		$('#zcbimpdiv').dialog('close');
                	}else{
                		$.messager.alert("提示",json.msg);
                	}
				}
        });
}

/**
 * 删除资产包
 * @return
 *
 * @author King 2014-2-12
 */
function DELZCBModel(){
	$.messager.confirm("询问","确认删除资产包数据吗？",function(r){
		if(r){
			var PROJECT_NAME=$("#PROJECT_NAME").val();
			$.ajax({
				type:'post',
				url:_basePath+"/project/project!delZCB.action",
				data:"PROJECT_NAME="+encodeURI(PROJECT_NAME),
				dataType:'json',
				success:function(json){
					if(json.flag)
						$.messager.alert("提示","删除成功");
					else
						$.messager.alert("提示",json.msg);
				}
			});
		}
	});
}

function QUERYZCBModel(){
	var PROJECT_NAME=$("#PROJECT_NAME").val();
	top.addTab("资产包明细",_basePath+"/project/project!QUERYZCBModel.action?PROJECT_NAME="+encodeURI(PROJECT_NAME));
}

function STARTSUBJBPMPSH(){
	var JBPM_ID=$("#SUB_JBPM_ID").val();
	$.ajax({
		type:'post',
		url:_basePath+"/project/project!STARTSUBJBPMPSH.action",
		data:"JBPM_ID="+encodeURI(JBPM_ID),
		dataType:'json',
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","评审会流程已经发起");
			}else{
				$.messager.alert("提示","评审会流程发起失败");
			}
		}
	});
}
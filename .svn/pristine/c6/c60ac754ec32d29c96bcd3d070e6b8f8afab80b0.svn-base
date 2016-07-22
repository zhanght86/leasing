$(document).ready(function(){
//	$("#FIRSTMONEYHELP").dialog('close');
	$("#zcbimpdiv").dialog('close');
//	revfinsnInit();

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
	
//	$("#UPDATE_DELIVER_DATE").datebox({onSelect: function (date) {
//		$("#DELIVER_DATE").val($("#UPDATE_DELIVER_DATE").datebox("getValue"));
//	}});
	
});

function platformChangeCredate(obj){
	var platValue=$(obj).val();
	if(platValue == '2' || platValue == '11'){
		$("#spanPlat").attr("style","");
	}else{
		$("#spanPlat").attr("style","display:none");
	}
}

function platformChange(obj){
	var platValue=$(obj).val();
	if(platValue == '2' || platValue == '11'){
		$("#spanPlat").attr("style","");
	}else{
		$("#spanPlat").attr("style","display:none");
	}
	
	var eq_template_Value_OLD=$("#eq_template_Value_OLD").val();
	if(platValue == '2'){
		$("#eq_template_Value").val("eq_templateSH");
		if(eq_template_Value_OLD == 'eq_templateSH'){
			;
		}else{
			$("#eq_body> tr").each(function (){
					$(this).remove();
			});
			copyTrEQ();
			getAllTotal();
			BusPol();
		}
		$("#eq_template_Value_OLD").val("eq_templateSH");
	}else{
		$("#eq_template_Value").val("eq_templateZZ");
		if(eq_template_Value_OLD == 'eq_templateZZ'){
			;
		}else{
			$("#eq_body> tr").each(function (){
					$(this).remove();
			});
			copyTrEQ();
			getAllTotal();
			BusPol();
		}
		$("#eq_template_Value_OLD").val("eq_templateZZ");
	}
	
	
	
}

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

function idCardCheck1(custId){//承租人身份证验证
	$.ajax({
		url:_basePath+"/crm/Customer!doCheckCustIDCard.action",
		data : {id:custId},
		type:"post",
		dataType:"json",
		success:function (json){
			if(json.flag){
				$("#idCardCheckSpan").css("color","green").text("验证成功");
				$("#CUST_PHOTO").html("<img src='data:image/png;base64,"+json.msg+"' style='padding:5px;margin:0px;'/>");
			}else{
				alert(json.msg);
			}
		}
	});
	
}

function idCardCheck2(custId){//承租人配偶身份证验证
	$.ajax({
		url:_basePath+"/crm/Customer!doCheckCustIDCard1.action",
		data : {id:custId},
		type:"post",
		dataType:"json",
		success:function (json){
			if(json.flag){
				$("#idSpoustDetCardCheckSpan").css("color","green").text("验证成功");
				$("#SPOUSE_PHOTO").html("<img src='data:image/png;base64,"+json.msg+"' style='padding:5px;margin:0px;'/>");
			}else{
				alert(json.msg);
			}
		}
	});
	
}

function idCardCheck3(custId){//企业团队身份证验证
	$.ajax({
		url:_basePath+"/crm/Customer!doCheckCustIDCard3.action",
		data : {id:custId},
		type:"post",
		dataType:"json",
		success:function (json){
			if(json.flag){
				$("#idCardCheckSpan").css("color","green").text("验证成功");
				$("#SPOUSE_PHOTO").html("<img src='data:image/png;base64,"+json.msg+"' style='padding:5px;margin:0px;'/>");
			}else{
				alert(json.msg);
			}
		}
	});
	
}
function idCardCheck4(){//企业团队身份证验证
	var NAME=$("#toAddEn input[name='NAME']").val();
	var ID_CARD=$("#toAddEn input[name='ID_CARD']").val();
	$.ajax({
		url:_basePath+"/crm/Customer!doCheckCustIDCard4.action",
		type:"post",
		data : {NAME:NAME,ID_CARD:ID_CARD},
		dataType:"json",
		success:function (json){
			if(json.flag){
				$("#idCardCheckSpan").css("color","green").text("验证成功");
				$("#SPOUSE_PHOTO").html("<img src='data:image/png;base64,"+json.msg+"' style='padding:5px;margin:0px;'/>");
				$("#toAddEn input[name='IDCARD_PHOTO']").val(json.msg);
				$("#toAddEn input[name='IDCARD_CHECK']").val("CHECKSUCCESS");
			}else{

				$("#toAddEn input[name='IDCARD_CHECK']").val("CHECKFAIL");
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
function getCity(obj) {
	var AREA_ID=$(obj).val();
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

var saveFlag = true;

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
	$.parser.parse(tr);
}

function copyTrEQ()
{
	var fromId=$("#eq_template_Value").val();
	var toId=$("#eq_body_Value").val();
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
	$.parser.parse(tr);
}

function copyTrGuaran(fromId,toId)
{
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","guaran_body_tr");
	$("#"+toId).append(tr);
}

function copyTrFL(fromId,toId)
{
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","fl_body_tr");
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
//重置厂商
function queryCompanyCZ(obj){
//	var SUPPLIER_ID=$(obj).parent().prev().find("option:selected").val();//平台、经销商id
//	alert(SUPPLIER_ID);
	queryCompany($(obj).parent().prev());
}

//重置厂商(方案变更申请)
function queryCompanyChangeCZ(obj){
//	var SUPPLIER_ID=$(obj).parent().prev().find("option:selected").val();//平台、经销商id
//	alert(SUPPLIER_ID);
	queryCompany($(obj).parent());
}

function queryCompany(obj){
	var SUPPLIER_ID=$(obj).find("option:selected").val();//平台、经销商id
	if(SUPPLIER_ID==null||SUPPLIER_ID==''||SUPPLIER_ID=='undefined')
		return;
	var tr=$(obj).parent().parent();
	var COMPANY_ID=$(tr).find("select[name='COMPANY_ID']");//厂商id
	$(COMPANY_ID).empty();
	$(COMPANY_ID).append("<option value='' selected>--请选择--</option>");
	
	$.ajax({
		url:_basePath+"/project/project!queryComType.action?SUPPLIER_ID="+SUPPLIER_ID,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			for(var i=0;i<data.length;i++){
				COMPANY_ID.append($("<option test="+data[i].COMPANY_NAME + " CODE=" + data[i].COMPANY_CODE +">").val(data[i].COMPANY_ID).text(data[i].COMPANY_NAME));
			}
		}
	});
	
}

function querySuppliers(obj){
	var WHETHER_SALES_SI=$(obj).find("option:selected").val();//采购类型id
	var PRO_ID = $("#PROJECT_ID").val();
	var SSJGPAGEFLAG = '0';
	if (PRO_ID !='undefined' && PRO_ID != null && PRO_ID != '') {
		SSJGPAGEFLAG = '1';
	}
	//add gengchangbao JZZL-205 2016年6月15日13:20:57 Start
	var systemName = $("#systemName").val();
	//add gengchangbao JZZL-205 2016年6月15日13:20:57 End
	if(WHETHER_SALES_SI==null||WHETHER_SALES_SI==''||WHETHER_SALES_SI=='undefined')
		return;
	if ((WHETHER_SALES_SI == 1 || WHETHER_SALES_SI == 3) && systemName != '捷翊' && (PRO_ID =='undefined' || PRO_ID == null || PRO_ID == '')) {
		$("#UNIT_PRICE").attr("readonly",true);
	} else {
		$("#UNIT_PRICE").removeAttr("readonly");
	}
	var tr=$(obj).parent().parent();
	var SUPPLIERS_ID=$(tr).find("select[name='SUPPLIERS_ID']");//经销商id
	$(SUPPLIERS_ID).empty();
	$(SUPPLIERS_ID).append("<option value='' selected>--请选择--</option>");
	
	$.ajax({
		url:_basePath+"/project/project!querySuppByCgxl.action?WHETHER_SALES_SI="+WHETHER_SALES_SI+"&SSJGPAGEFLAG="+SSJGPAGEFLAG,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			for(var i=0;i<data.length;i++){
				SUPPLIERS_ID.append($("<option test="+data[i].SUP_NAME + " score=" + data[i].SCORE +">").val(data[i].SUP_ID).text(data[i].SUP_NAME));
				
				//<option value="$!item.SUP_ID"  test="$!item.SUP_NAME" score="$!item.SCORE">$!item.SUP_NAME</option>
			}
		}
	});
	
}

/**
 * 根据平台查询可以使用的供应商
 */
function queryComType(obj){
	var COMPANY_ID=$(obj).find("option:selected").val();//平台、厂商
	var tr=$(obj).parent().parent();
		
	//add by lishuo 12-28-2015 微卡提示
	var COMPANY_NAME =$("#COMPANY_ID").find("option:selected").attr('test');//厂商
	if( COMPANY_NAME =='捷众微卡' ){
		$.messager.alert("提示","请注意：现有业务中微卡车型的最高融资额不能超过50000!");
	}
	
//	var SUP_ID=$(tr).find("select[name='SUPPLIERS_ID']");//供应商
//	$(SUP_ID).empty();
	
	var THING_NAME=$(tr).find("select[name='THING_NAME']");//设备名称
	$(THING_NAME).empty();
	
	var PRODUCT_CATENA=$(tr).find("select[name='PRODUCT_CATENA']");//设备系列
	$(PRODUCT_CATENA).empty();
	
	var THING_SPEC=$(tr).find("select[name='THING_SPEC']");//型号（设备）
	$(THING_SPEC).empty();
	
//	$(SUP_ID).append("<option value='' test=''>--请选择--</option>");
	$(THING_NAME).append("<option value='' test=''>--请选择--</option>");
	$(PRODUCT_CATENA).append("<option value=''>--请选择--</option>");
	$(THING_SPEC).append("<option value=''>--请选择--</option>");
	
//	$.ajax({
//		url:_basePath+"/project/project!queryComType.action?COMPANY_ID="+COMPANY_ID,
//		type:"post",
//		dataType:"json",
//		success:function (data)
//		{
//			for(var i=0;i<data.length;i++){
//				SUP_ID.append($("<option test="+data[i].SUP_NAME + " score=" + data[i].SCORE +">").val(data[i].SUP_ID).text(data[i].SUP_NAME));
//			}
//		}
//	});
	
	$.ajax({
		url:_basePath+"/project/project!queryThingType.action?COMPANY_ID="+COMPANY_ID,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			for(var i=0;i<data.length;i++){
				THING_NAME.append($("<option test="+data[i].PRODUCT_NAME+">").val(data[i].PRODUCT_ID).text(data[i].PRODUCT_NAME));
			}
		}
	});
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
			var catena=$(tr).find("select[name='PRODUCT_CATENA']");//设备系列
			$(catena).empty();
			
			var spec=$(tr).find("select[name='THING_SPEC']");//型号（设备）
			$(spec).empty();
			
			$(catena).append("<option value=''>--请选择--</option>");//设备系列
			$(spec).append("<option value=''>--请选择--</option>");//型号（设备）
			for(var i=0;i<data.length;i++){
				catena.append($("<option>").val(data[i].CATENA_ID).text(data[i].CATENA_NAME));
			}
		}
	});
}

/**
 * 标准型售后回租的设备选择
 */
function queryEqNameTypeSHHZ(obj){
	var TYPE_ID=$(obj).find("option:selected").attr("TYPE_ID");
	var UNIT_PRICE=$(obj).find("option:selected").attr("PRICE");
	var tr=$(obj).parent().parent();
	$(tr).find("input[name='THING_SPEC']").val(TYPE_ID);
	$(tr).find("input[name='UNIT_PRICE']").val(UNIT_PRICE);
	getTotal(obj);
}

/**
 * 查询设备型号
 */
function queryEqSpec(obj){
	// add gengchangbao JZZL-171 2016年4月27日 start
	var PROJECT_AREA = jQuery.trim($("#PROJECT_AREA").find("option:selected").val());
	var CITY_ID = jQuery.trim($("#PROJECT_CITY").find("option:selected").val());
	var WHETHER_SALES_SI = $("#WHETHER_SALES_SI").val();
	if ($("#pageUpdFlag").val() == 'upd'){
		PROJECT_AREA = $("#AREA_ID").val();
		CITY_ID = $("#CITY_ID").val();
	}
	if (WHETHER_SALES_SI == 1) {
		PROJECT_AREA = '';
		CITY_ID = '';
	}
	// add gengchangbao JZZL-171 2016年4月27日 end
	var catenaId=$(obj).find("option:selected").val();
	var tr=$(obj).parent().parent();
	$.ajax({
		url:_basePath+"/project/project!querySpecByEqID.action?CATENA_ID="+catenaId+"&province="+PROJECT_AREA+"&city="+CITY_ID,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			var spec=$(tr).find("select[name='THING_SPEC']");
			$(spec).empty();
			$(spec).css('width','195');//add gengchangbao 2016/1/27 JZZL-77 
			$(spec).append("<option value='' >--请选择--</option>");//Add By YangJ 2014年5月9日21:06:37
			for(var i=0;i<data.length;i++){
				//upd gengchangbao 2016/1/27 JZZL-77 Start
				//$(spec).append($("<option score="+ data[i].SCORE+">"  ).val(data[i].ID).text(data[i].NAME ).attr("price",data[i].PRICE));
				$(spec).append($("<option score="+ data[i].SCORE+">"  ).val(data[i].ID).text(data[i].FULL_NAME ).attr("price",data[i].PRICE)
						.attr("cc_price",data[i].CC_PRICE).attr("real_price",data[i].REAL_PRICE).attr("SYX",data[i].SYX).attr("JQX",data[i].JQX)
						.attr("CCS",data[i].CCS));
				//upd gengchangbao 2016/1/27 JZZL-77 Start
			}
			var price=$(spec).find("option:selected").attr("price");
			var cc_price=$(spec).find("option:selected").attr("cc_price");
			var real_price=$(spec).find("option:selected").attr("real_price");
			//add gengchangbao JZZL-265 2016年7月14日10:51:36 Start
			var SYX=$(spec).find("option:selected").attr("SYX");
			var JQX=$(spec).find("option:selected").attr("JQX");
			var CCS=$(spec).find("option:selected").attr("CCS");
			//add gengchangbao JZZL-265 2016年7月14日10:51:36 End
			$(tr).find("input[name=UNIT_PRICE]").val(anyDouble(price));
			//add gengchangbao JZZL-182 2016年5月11日10:03:00 start
			$(tr).find("input[name=CC_PRICE]").val(anyDouble(cc_price));
			//add gengchangbao JZZL-182 2016年5月11日10:03:00 end
			$(tr).find("input[name=REAL_PRICE]").val(anyDouble(real_price));
			
			//add gengchangbao JZZL-265 2016年7月14日10:51:36 Start
			$(tr).find("input[name=BX]").val(anyDouble(SYX));
			$(tr).find("input[name=JQX]").val(anyDouble(JQX));
			$(tr).find("input[name=CCS]").val(anyDouble(CCS));
			bx_cc();
			//add gengchangbao JZZL-265 2016年7月14日10:51:36 End
			getTotal(obj);
		}
	});
}

/**
 * 查询设备型号
 */
function queryEqSpecByCity(obj){
	var PROJECT_AREA = jQuery.trim($("#PROJECT_AREA").find("option:selected").val());
	var CITY_ID = $(obj).find("option:selected").val();
	var catenaId = jQuery.trim($("#PRODUCT_CATENA").find("option:selected").val());
	var thingSpec = jQuery.trim($("#THING_SPEC").find("option:selected").val());
	
	var WHETHER_SALES_SI = jQuery.trim($("#WHETHER_SALES_SI").find("option:selected").val());
	
	if (WHETHER_SALES_SI == 1) {
		PROJECT_AREA = '';
		CITY_ID = '';
	}
	
	var tr=$(obj).parent().parent();
	$.ajax({
		url:_basePath+"/project/project!querySpecByEqID.action?CATENA_ID="+catenaId+"&province="+PROJECT_AREA+"&city="+CITY_ID,
		type:"post",
		dataType:"json",
		success:function (data)
		{
			var spec=$("#THING_SPEC");
			$(spec).empty();
			$(spec).css('width','145');
			$(spec).append("<option value='' >--请选择--</option>");
			for(var i=0;i<data.length;i++){
				if (thingSpec != null && thingSpec !='' && thingSpec == data[i].ID) {
					$(spec).append($("<option score="+ data[i].SCORE+" selected >"  ).val(data[i].ID).text(data[i].FULL_NAME )
							.attr("price",data[i].PRICE).attr("cc_price",data[i].CC_PRICE)
							.attr("real_price",data[i].REAL_PRICE).attr("SYX",data[i].SYX).attr("JQX",data[i].JQX)
							.attr("CCS",data[i].CCS));
					$("#UNIT_PRICE").val(anyDouble(data[i].PRICE));
					//add gengchangbao JZZL-182 2016年5月11日10:03:00 start
					$("#CC_PRICE").val(anyDouble(data[i].CC_PRICE));
					//add gengchangbao JZZL-182 2016年5月11日10:03:00 end
					$("#REAL_PRICE").val(anyDouble(data[i].REAL_PRICE));
					$("#BX").val(anyDouble(data[i].SYX));
					$("#JQX").val(anyDouble(data[i].JQX));
					$("#CCS").val(anyDouble(data[i].CCS));
					bx_cc();
				} else {
					$(spec).append($("<option score="+ data[i].SCORE+">"  ).val(data[i].ID).text(data[i].FULL_NAME ).attr("price",data[i].PRICE).attr("cc_price",data[i].CC_PRICE)
							.attr("real_price",data[i].REAL_PRICE).attr("SYX",data[i].SYX).attr("JQX",data[i].JQX)
							.attr("CCS",data[i].CCS));
				}
			}
			
			getTotal($("#UNIT_PRICE"));
			changeTotal($("#UNIT_PRICE"));
		}
	});
}

/**
 * 自动关联设备价格
 * @param {Object} obj
 */
function getEqPrice(obj)
{//alert("自动关联设备价格");
	var price=$(obj).find("option:selected").attr("price");
	var cc_price=$(obj).find("option:selected").attr("cc_price");
	var real_price=$(obj).find("option:selected").attr("real_price");
	
	var SYX=$(obj).find("option:selected").attr("SYX");
	var JQX=$(obj).find("option:selected").attr("JQX");
	var CCS=$(obj).find("option:selected").attr("CCS");
	
	var tr=$(obj).parent().parent();
	$(tr).find("input[name=UNIT_PRICE]").val(anyDouble(price));
	//add gengchangbao JZZL-182 2016年5月11日10:03:00 start
	$(tr).find("input[name=CC_PRICE]").val(anyDouble(cc_price));
	//add gengchangbao JZZL-182 2016年5月11日10:03:00 end
	$(tr).find("input[name=REAL_PRICE]").val(anyDouble(real_price));
	$(tr).find("input[name=ACTUAL_PRICE]").val(anyDouble(price));
	
	$("#BX").val(anyDouble(SYX));
	$("#JQX").val(anyDouble(JQX));
	$("#CCS").val(anyDouble(CCS));
	
	getTotal(obj);
};

/**
 * 自动关联设备价格
 * @param {Object} obj
 */
function getEqPriceHZ(obj)
{//alert("自动关联设备价格");
	var price=$(obj).find("option:selected").attr("PRICE");
	var tr=$(obj).parent().parent();
	$(tr).find("input[name=UNIT_PRICE]").val(anyDouble(price));
	$(tr).find("input[name=ACTUAL_PRICE]").val(anyDouble(price));
	getTotal(obj);
};

//add by lishuo 12-28-2015 查询微卡限额
function CheckWKPrice(){
	var CODE="WKXE";
	var url=_basePath+"/project/project!CheckTotalPrice.action";
	var args={"CODE":CODE};
	var QueryPrice;
	$.ajax({
		url:url,
		data:args,
		dataType:"json",
		type:"post",
		async : false,
		success:function(data){
			if(data){
				var json =data.data;
				QueryPrice=json[0].FLAG;
				$("#UNIT_WKPRICE_HIDDEN").val(QueryPrice);
			}
		}
	});
}

//add by lishuo 12.14.2015 查询融车金额限额
function CheckPrice(){
	var CODE="JJXE";
	var url=_basePath+"/project/project!CheckTotalPrice.action";
	var args={"CODE":CODE};
	var QueryPrice;
	$.ajax({
		url:url,
		data:args,
		dataType:"json",
		type:"post",
		async : false,
		success:function(data){
			if(data){
				var json = data.data;
				QueryPrice=json[0].FLAG;
				$("#UNIT_PRICE_HIDDEN").val(QueryPrice);
			}
		}
	});
}

/**
 * 计算设备小计
 */
function getTotal(obj,FLAG){
	var tr=$(obj).parent().parent();
	var price=$(tr).find("input[name='UNIT_PRICE']").val();	//单价
	//var price=$(tr).find("input[name='ACTUAL_PRICE']").val();	//单价
	//add by lishuo 12.14.2015 12W控制 528-531 541-547 
	//CheckPrice();
	//var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
	//var QUERY_UNIT_PRICE = $("#UNIT_PRICE_HIDDEN").val();
	//add by lishuo 12.28.2015 微卡限额超5W控制 start
	var COMPANY_NAME =$("#COMPANY_ID").find("option:selected").attr('test');//厂商
	if( COMPANY_NAME =='捷众微卡' ){
		CheckWKPrice();
		var QUERY_WKUNIT_PRICE= $("#UNIT_WKPRICE_HIDDEN").val();//微卡限额
		if(UNIT_PRICE - QUERY_WKUNIT_PRICE >0){
			$.messager.alert("提示","现有业务中微卡的最高采购价不能超过50000，请您重新选择。");
		}
	}
	//add by lishuo 12.28.2015 微卡限额超5W控制  end
	/*if(UNIT_PRICE - QUERY_UNIT_PRICE > 0){
		$(tr).find("input[name='ACTUAL_PRICE']").val(QUERY_UNIT_PRICE);
		price =QUERY_UNIT_PRICE;
	}else{
		$("input[name='ACTUAL_PRICE']").val(UNIT_PRICE);
		$("#LEASE_TOPRIC").val(QUERY_UNIT_PRICE);
	}*/
	
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
	getCustomerPaymentMoney();
	//add by lishuo 03-31-2016 进件修改页面产品费用12w限制与不限制 Start 
	var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
	if(FLAG=='2'){//修改页面
		if($("#KSQX_DATE_MODIFY_VAL").val() === null || $("#KSQX_DATE_MODIFY_VAL").val() === '' || $("KSQX_DATE_MODIFY_VAL").val() === undefined){//产品无政策开始时间
			$("input[name='ACTUAL_PRICE']").val($("input[name='UNIT_PRICE']").val());
			$("#LEASE_TOPRIC").val($("input[name='UNIT_PRICE']").val());
			$("#INVESTIGATE_MONEY").val(0);
		}
		if($("#KSQX_DATE_MODIFY_VAL").val().length > 0){//有产品政策启动时间
			var begin=new Date($("#KSQX_DATE_MODIFY_VAL").val().replace(/-/g,"/"));
			var end=new Date("2016-02-02".replace(/-/g,"/"));
			if(end-begin>0){//2016-02-02之前的产品，启动12w校验，并计算客户自付差价
				/*CheckPrice();
				var QUERY_UNIT_PRICE = $("#UNIT_PRICE_HIDDEN").val(); 为查询12w 生产有可能会修改暂注释*/
				if(UNIT_PRICE- 120000 >0){
					$("input[name='ACTUAL_PRICE']").val(120000);
					$("#LEASE_TOPRIC").val(120000);
					$("#INVESTIGATE_MONEY").val(UNIT_PRICE - 120000);
					if($("#START_PERCENT").val()/10 > 0){//不为00 的产品情况
						   $("#START_MONEY").val(120000*($("#START_PERCENT").val()/100));//12w限制并计算首期租金
						}
				}else{//2016-02-02之前的产品，启动12w校验且UNIT_PRICE < 12w
					var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
					$("input[name='ACTUAL_PRICE']").val(UNIT_PRICE);
					$("#INVESTIGATE_MONEY").val(0);
				}
			}else{//2016-02-02之后的产品，不启动12w校验客户自付差价为0
				$("input[name='ACTUAL_PRICE']").val($("input[name='UNIT_PRICE']").val());
				$("#LEASE_TOPRIC").val($("input[name='UNIT_PRICE']").val());
				$("#INVESTIGATE_MONEY").val(0);
			}
		}else{//修改页面但产品政策时间2016-02-02 之前或着为空
			var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
			$("input[name='ACTUAL_PRICE']").val(UNIT_PRICE);
		}
	}else{//进件页面
		$("input[name='ACTUAL_PRICE']").val(UNIT_PRICE);
		if($("#KSQX_DATE").val() === null || $("#KSQX_DATE").val() === '' || $("#KSQX_DATE").val() === undefined){//产品无政策开始时间
			$("input[name='ACTUAL_PRICE']").val($("input[name='UNIT_PRICE']").val());
			$("#LEASE_TOPRIC").val($("input[name='UNIT_PRICE']").val());
			$("#INVESTIGATE_MONEY").val(0);
		}
		if($("#KSQX_DATE").val().length > 0 ){//进件后换产品价格，修改客户自付差价
			/*CheckPrice();
			var QUERY_UNIT_PRICE = $("#UNIT_PRICE_HIDDEN").val(); 为查询12w 生产有可能会修改暂注释*/
			var begin=new Date($("#KSQX_DATE").val().replace(/-/g,"/"));
			var end=new Date("2016-02-02".replace(/-/g,"/"));
			if(UNIT_PRICE - 120000 > 0 && end - begin > 0){
				$("#INVESTIGATE_MONEY").val(UNIT_PRICE - 120000);
				if($("#START_PERCENT").val()/10 > 0){//不为00 的产品情况
				   $("#START_MONEY").val(120000*($("#START_PERCENT").val()/100));//12w限制并计算首期租金
				}
			}else{
				$("#INVESTIGATE_MONEY").val(0);
			}
		}
	}
	//add by lishuo 03-31-2016 进件修改页面产品费用12w限制与不限制 End
}
//计算客户自付价格（指导价减去实际单价）
function getCustomerPaymentMoney(){
	
	var UNIT_PRICE =  parseInt( $("input[name='UNIT_PRICE']").val());
	var ACTUAL_PRICE = parseInt( $("input[name='ACTUAL_PRICE']").val());
	if(UNIT_PRICE<ACTUAL_PRICE){
		//alert("单价不能大于指导价"); modify by lishuo 04-07-2016
		$("input[name='ACTUAL_PRICE']").val(UNIT_PRICE);
		$("#INVESTIGATE_MONEY").val(0);
	}else{
		var customerpaymentmoney = UNIT_PRICE - ACTUAL_PRICE;
		$("#INVESTIGATE_MONEY").val(customerpaymentmoney);
		//$("#LEASE_TOPRIC").val(QUERY_UNIT_PRICE);
	}
	
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
		if($(this).attr("sid")==0){
			if($(this).parent().parent().find(":checkbox").attr("checked")){
				allTotal=parseFloat(total)+parseFloat(allTotal);
			}
		}else{
			allTotal=parseFloat(total)+parseFloat(allTotal);
		}
	});
	$("#LEASE_TOPRIC_ZD").val(allTotal);
	$("#LEASE_TOPRIC1").val(allTotal);
	$("#chinaAllTotal").text(atoc(allTotal));
	if($("input[name='LEASE_TOPRIC_ZD']").length > 0 )
	{
		$("input[name='LEASE_TOPRIC_ZD']").val(allTotal);
		$("#LEASE_TOPRIC").val($("input[name='LEASE_TOPRIC_ZD']").val());
		if($("#LEASE_TOPRIC").val()>=0){
			changeEqumentMoney();
		}
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



//function submitAddCustProject () {
//	var customer_ID=jQuery.trim($("#CUSTOMER_ID").val());
//	var customer_Code=jQuery.trim($("#CUSTOMER_CODE").val());
//	var customer_Type=jQuery.trim($("#CUSTOMER_TYPE").val());
//	var PROJECT_NAME = jQuery.trim($("#PROJECT_NAME").val());
//	var PROJECT_AREA = jQuery.trim($("#PROJECT_AREA").find("option:selected").val());
//	var PROVINCE_NAME=jQuery.trim($("#PROJECT_AREA").find("option:selected").text());
//	var CITY_ID = jQuery.trim($("#PROJECT_CITY").find("option:selected").val());
//	var CITY_NAME = jQuery.trim($("#PROJECT_CITY").find("option:selected").text());
//	var LEASE_TOPRIC = $("#LEASE_TOPRIC").val();
//	var scheme_id =$('input:radio[name=scheme_id]:checked').val();
//	var CUSTOMER_NAME=jQuery.trim($("#CUSTOMER_NAME").val());
//	var INDUSTRY_FICATION=jQuery.trim($("#INDUSTRY_FICATION").find("option:selected").val());
//	//业务类型
//	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
//	var LEASE_MODEL=$("#LEASE_MODEL").val();
//	var PRO_CODE=$("#PRO_CODE").val();
//	var PROJECT_MODEL_SHOTNAME=$("input[name=PROJECT_MODEL]").attr("SHOT_NAME");
//	
//	if (PROJECT_NAME == ''){
//		$.messager.alert("提示","请输项目名称");
//		return ;
//	}else if (PROJECT_AREA == ''){
//		$.messager.alert("提示","请选择区域");
//		return ;
//	}else if (CITY_ID == ''){
//		$.messager.alert("提示","请选择城市");
//		return ;
//	}else if(scheme_id == "" || scheme_id == undefined || scheme_id.length<=0){
//		 $.messager.alert("提示","请选择商务政策");
//		 return;
//	}else if(INDUSTRY_FICATION == ''){
//		$.messager.alert("提示","请选择行业");
//		return ;
//	}
//	
//	var flag=true;
//	var Eq=[];
//	 $(".eq_body_tr").each(function(){
//				var temp={};
//				temp.SCHEME_ID=scheme_id;
//				temp.AREA_ID=PROJECT_AREA;
//				temp.CITY_ID=CITY_ID;
//				temp.PROVINCE_NAME=PROVINCE_NAME;
//				temp.CITY_NAME=CITY_NAME;
//				temp.INDUSTRY_FICATION=INDUSTRY_FICATION;
//				var CERTIFICATE_NUM = $(this).find("input[name='CERTIFICATE_NUM']") ;
//				var ENGINE_CODE = $(this).find("input[name='ENGINE_CODE']") ;
//		
//				if(PLATFORM_TYPE=='2' || PLATFORM_TYPE=='7' || (PLATFORM_TYPE=='4' && LEASE_MODEL=='back_leasing'))//标准型售后回租
//				{
//					
//					var COMPANY_NAME = $(this).find("input[name='COMPANY_NAME']");
//					if ($(COMPANY_NAME).val() == "" || $(COMPANY_NAME).val() == null || $(COMPANY_NAME).val() == undefined) {
//						flag = false;
//						$(COMPANY_NAME).focus();
//						$.messager.alert("提示","请填写厂商");
//						return;
//					};
//					
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == null || $(CERTIFICATE_NUM).val() == undefined) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						$.messager.alert("提示","请填写合格证号");
//						return;
//					};
//					
//					
//					temp.COMPANY_NAME =$(this).find("input[name='COMPANY_NAME']").val();
//					
//					var SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']");
//					if ($(SUPPLIERS_NAME).val() == "" || $(SUPPLIERS_NAME).val() == null || $(SUPPLIERS_NAME).val() == undefined) {
//						flag = false;
//						$(SUPPLIERS_NAME).focus();
//						$.messager.alert("提示","请填写供应商");
//						return;
//					};
//					temp.SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']").val();
//					
//					//得到设备名称
//					var THING_NAME = $(this).find("input[name='THING_NAME']");
//					if ($(THING_NAME).val() == "" || $(THING_NAME).val() == null || $(THING_NAME).val() == undefined) {
//						flag = false;
//						$(THING_NAME).focus();
//						$.messager.alert("提示","请填写设备名称");
//						return;
//					};
//					temp.PRODUCT_NAME =  $(this).find("input[name='THING_NAME']").val();
//					
//					//得到设备型号
//					var eqCatena = $(this).find("input[name='PRODUCT_CATENA']");
//					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
//						flag = false;
//						$(eqCatena).focus();
//						$.messager.alert("提示","请填写设备系别");
//						return;
//					};
//					temp.CATENA_NAME = $(this).find("input[name='PRODUCT_CATENA']").val();
//					
//					//得到设备型号
//					var eqType = $(this).find("input[name='THING_SPEC']");
//					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
//						flag = false;
//						$(eqType).focus();
//						$.messager.alert("提示","请填写设备型号");
//						return;
//					};
//					temp.SPEC_NAME =$(this).find("input[name='THING_SPEC']").val();
//				}
//				else{
//					var COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected");
//					if ($(COMPANY_ID).val() == "" || $(COMPANY_ID).val() == null || $(COMPANY_ID).val() == undefined) {
//						flag = false;
//						$(COMPANY_ID).focus();
//						$.messager.alert("提示","请选择厂商");
//						return;
//					};
//					temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected").val();
//					temp.COMPANY_CODE = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("CODE");
//					temp.COMPANY_NAME = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("test");
//					
//					var SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected");
//					if ($(SUPPLIERS_ID).val() == "" || $(SUPPLIERS_ID).val() == null || $(SUPPLIERS_ID).val() == undefined) {
//						flag = false;
//						$(SUPPLIERS_ID).focus();
//						$.messager.alert("提示","请选择供应商");
//						return;
//					};
//					temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").val();
//					temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").attr("test");
//					
//					//得到设备名称
//					var eqName = $(this).find("select[name='THING_NAME']").find("option:selected");
//					if ($(eqName).val() == "" || $(eqName).val() == null || $(eqName).val() == undefined) {
//						flag = false;
//						$(eqName).focus();
//						$.messager.alert("提示","请选择设备名称");
//						return;
//					};
//					temp.PRODUCT_ID = $(this).find("[name='THING_NAME']").val();
//					temp.PRODUCT_NAME = $(this).find("[name='THING_NAME']").find("option:selected").text();
//					
//					//得到设备型号
//					var eqCatena = $(this).find("select[name='PRODUCT_CATENA']").find("option:selected");
//					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
//						flag = false;
//						$(eqType).focus();
//						$.messager.alert("提示","请选择设备系别");
//						return;
//					};
//					temp.CATENA_ID = $(this).find("[name='PRODUCT_CATENA']").val();
//					temp.CATENA_NAME = $(this).find("[name='PRODUCT_CATENA']").find("option:selected").text();
//					
//					//得到设备型号
//					var eqType = $(this).find("select[name='THING_SPEC']").find("option:selected");
//					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
//						flag = false;
//						$(eqType).focus();
//						$.messager.alert("提示","请选择设备型号");
//						return;
//					};
//					temp.SPEC_ID = $(this).find("[name='THING_SPEC']").val();
//					temp.SPEC_NAME = $(this).find("[name='THING_SPEC']").find("option:selected").text();
//				}
//				
//				//得到合格证号
//				temp.CERTIFICATE_NUM = $(CERTIFICATE_NUM).val();
//				//得到发动机号/许可证号
//				temp.ENGINE_CODE = $(ENGINE_CODE).val() ;
//				//得到出厂编号
//				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
//				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
//				
//				//得到出厂日期
//				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
//				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
//				
//				//得到发动机编号
//				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
//				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
//				
//				//得到车架号
//				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
//				temp.CAR_SYMBOL = $(this).find("[name='CAR_SYMBOL']").val();
//				
//				temp.STAYBUY_PRICE = $(this).find("[name='STAYBUY_PRICE']").val();
//				
//				//得到设备的单价
//				var unitPrice = $(this).find("input[name='UNIT_PRICE']");
//				if ($(unitPrice).val() == "" || $(unitPrice).val() == undefined || $(unitPrice).val()<=0) {
//					flag = false;
//					$(unitPrice).focus();
//					$.messager.alert("提示","请输入设备单价");
//					return;
//				};
//				temp.UNIT_PRICE = $(this).find("[name='UNIT_PRICE']").val();
//				
//				//获取设备数量
//				var amount = $(this).find("input[name='AMOUNT']");
//				if ($(amount).val() == "" || $(amount).val() == undefined  || $(amount).val()<=0) {
//					flag = false;
//					$(unitPrice).focus();
//					$.messager.alert("提示","请输入设备数量");
//					return;
//				};
//				temp.AMOUNT = $(this).find("[name='AMOUNT']").val();
//				
//				//获取小计
//				var TOTAL_PRICE = $(this).find("input[name='TOTAL']");
//				if ($(TOTAL_PRICE).val() == "" || $(TOTAL_PRICE).val() == undefined  || $(TOTAL_PRICE).val()<=0) {
//					flag = false;
//					$(TOTAL_PRICE).focus();
//					$.messager.alert("提示","请输入设备数量");
//					return;
//				};
//				temp.TOTAL_PRICE = $(this).find("[name='TOTAL']").val();
//				
//				temp.UNIT = $(this).find("[name='UNIT']").val();
//				temp.UNITTEST = $(this).find("[name='UNIT']").find("option:selected").text();
//				temp.TOTAL = $(this).find("[name='TOTAL']").val();
//				
//				Eq.push(temp);
//		});
//	 if(flag)
//	 {
//		 var dataJson ={
//					EqList:Eq,
//					CLIENT_ID:customer_ID,
//					PRO_NAME:PROJECT_NAME,
//					STAUS:0,
//					AREA_ID:PROJECT_AREA,
//					LEASE_TOPRIC:LEASE_TOPRIC,
//					CUSTOMER_TYPE:customer_Type,
//					POB_ID:scheme_id,
//					CUSTOMER_NAME:CUSTOMER_NAME,
//					PROVINCE_NAME:PROVINCE_NAME,
//					CITY_ID:CITY_ID,
//					CITY_NAME:CITY_NAME,
//					PLATFORM_TYPE:PLATFORM_TYPE,
//					LEASE_MODEL:LEASE_MODEL,
//					PRO_CODE:PRO_CODE,
//					PROJECT_MODEL_SHOTNAME:PROJECT_MODEL_SHOTNAME,
//					INDUSTRY_FICATION:INDUSTRY_FICATION
//				};
//				$("#ChangeViewData").val(JSON.stringify(dataJson));
//				$("#formPrjoctView").submit();
//	 }
//	 else
//	 {
//		 return;
//	 }
//	
//}


//function submitAppendCustProject(){
//	var PROJECT_ID=jQuery.trim($("#PROJECT_ID").val());
//	var scheme_id =$('input:radio[name=scheme_id]:checked').val();
//	var LEASE_TOPRIC = $("#LEASE_TOPRIC").val();
//	var PLATFORM_TYPE=$("input[name=PLATFORM_TYPE]").val();
//	if(scheme_id == "" || scheme_id == undefined || scheme_id.length<=0){
//		 $.messager.alert("提示","请选择商务政策");
//		 return;
//	}
//	
//	var INDUSTRY_FICATION=jQuery.trim($("#INDUSTRY_FICATION").find("option:selected").val());
//	var PROJECT_AREA = jQuery.trim($("#PROJECT_AREA").find("option:selected").val());
//	var CITY_ID = jQuery.trim($("#PROJECT_CITY").find("option:selected").val());
//	var PROVINCE_NAME=jQuery.trim($("#PROJECT_AREA").find("option:selected").text());
//	var CITY_NAME = jQuery.trim($("#PROJECT_CITY").find("option:selected").text());
//	
//	if (PROJECT_AREA == ''){
//		$.messager.alert("提示","请选择区域");
//		return ;
//	}else if (CITY_ID == ''){
//		$.messager.alert("提示","请选择城市");
//		return ;
//	}else if(INDUSTRY_FICATION == ''){
//		$.messager.alert("提示","请选择行业");
//		return ;
//	}
//	var flag=true;
//	var Eq=[];
//	 $(".eq_body_tr").each(function(){
//				var temp={};
//				temp.SCHEME_ID=scheme_id;
//				temp.AREA_ID=PROJECT_AREA;
//				temp.CITY_ID=CITY_ID;
//				temp.PROVINCE_NAME=PROVINCE_NAME;
//				temp.CITY_NAME=CITY_NAME;
//				temp.INDUSTRY_FICATION=INDUSTRY_FICATION;
//				
//				var CERTIFICATE_NUM = $(this).find("input[name='CERTIFICATE_NUM']") ;
//				var ENGINE_CODE = $(this).find("input[name='ENGINE_CODE']") ;
//				if(PLATFORM_TYPE=='2' || PLATFORM_TYPE=='7' || (PLATFORM_TYPE=='4' && LEASE_MODEL=='back_leasing'))//标准型售后回租
//				{
//					var COMPANY_NAME = $(this).find("input[name='COMPANY_NAME']");
//					if ($(COMPANY_NAME).val() == "" || $(COMPANY_NAME).val() == null || $(COMPANY_NAME).val() == undefined) {
//						flag = false;
//						$(COMPANY_NAME).focus();
//						$.messager.alert("提示","请填写厂商");
//						return;
//					};
//					temp.COMPANY_NAME =$(this).find("input[name='COMPANY_NAME']").val();
//					
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == null || $(CERTIFICATE_NUM).val() == undefined) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						$.messager.alert("提示","请填写合格证号");
//						return;
//					};
//					
//					var SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']");
//					if ($(SUPPLIERS_NAME).val() == "" || $(SUPPLIERS_NAME).val() == null || $(SUPPLIERS_NAME).val() == undefined) {
//						flag = false;
//						$(SUPPLIERS_NAME).focus();
//						$.messager.alert("提示","请填写供应商");
//						return;
//					};
//					temp.SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']").val();
//					
//					//得到设备名称
//					var THING_NAME = $(this).find("input[name='THING_NAME']");
//					if ($(THING_NAME).val() == "" || $(THING_NAME).val() == null || $(THING_NAME).val() == undefined) {
//						flag = false;
//						$(THING_NAME).focus();
//						$.messager.alert("提示","请填写设备名称");
//						return;
//					};
//					temp.PRODUCT_NAME =  $(this).find("input[name='THING_NAME']").val();
//					
//					//得到设备型号
//					var eqCatena = $(this).find("input[name='PRODUCT_CATENA']");
//					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
//						flag = false;
//						$(eqCatena).focus();
//						$.messager.alert("提示","请填写设备系别");
//						return;
//					};
//					temp.CATENA_NAME = $(this).find("input[name='PRODUCT_CATENA']").val();
//					
//					//得到设备型号
//					var eqType = $(this).find("input[name='THING_SPEC']");
//					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
//						flag = false;
//						$(eqType).focus();
//						$.messager.alert("提示","请填写设备型号");
//						return;
//					};
//					temp.SPEC_NAME =$(this).find("input[name='THING_SPEC']").val();
//				}
//				else{
//					var COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected");
//					if ($(COMPANY_ID).val() == "" || $(COMPANY_ID).val() == null || $(COMPANY_ID).val() == undefined) {
//						flag = false;
//						$(COMPANY_ID).focus();
//						$.messager.alert("提示","请选择厂商");
//						return;
//					};
//					temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected").val();
//					temp.COMPANY_CODE = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("CODE");
//					temp.COMPANY_NAME = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("test");
//					
//					var SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected");
//					if ($(SUPPLIERS_ID).val() == "" || $(SUPPLIERS_ID).val() == null || $(SUPPLIERS_ID).val() == undefined) {
//						flag = false;
//						$(SUPPLIERS_ID).focus();
//						$.messager.alert("提示","请选择供应商");
//						return;
//					};
//					temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").val();
//					temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").attr("test");
//					
//					//得到设备名称
//					var eqName = $(this).find("select[name='THING_NAME']").find("option:selected");
//					if ($(eqName).val() == "" || $(eqName).val() == null || $(eqName).val() == undefined) {
//						flag = false;
//						$(eqName).focus();
//						$.messager.alert("提示","请选择设备名称");
//						return;
//					};
//					temp.PRODUCT_ID = $(this).find("[name='THING_NAME']").val();
//					temp.PRODUCT_NAME = $(this).find("[name='THING_NAME']").find("option:selected").text();
//					
//					//得到设备型号
//					var eqCatena = $(this).find("select[name='PRODUCT_CATENA']").find("option:selected");
//					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
//						flag = false;
//						$(eqType).focus();
//						$.messager.alert("提示","请选择设备系别");
//						return;
//					};
//					temp.CATENA_ID = $(this).find("[name='PRODUCT_CATENA']").val();
//					temp.CATENA_NAME = $(this).find("[name='PRODUCT_CATENA']").find("option:selected").text();
//					
//					//得到设备型号
//					var eqType = $(this).find("select[name='THING_SPEC']").find("option:selected");
//					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
//						flag = false;
//						$(eqType).focus();
//						$.messager.alert("提示","请选择设备型号");
//						return;
//					};
//					temp.SPEC_ID = $(this).find("[name='THING_SPEC']").val();
//					temp.SPEC_NAME = $(this).find("[name='THING_SPEC']").find("option:selected").text();
//				}
//				
//				//得到出厂编号
//				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
//				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
//				
//				//得到出厂日期
//				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
//				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
//				
//				//得到发动机编号
//				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
//				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
//				
//				//得到车架号
//				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
//				temp.CAR_SYMBOL = $(this).find("[name='CAR_SYMBOL']").val();
//				
//				//得到合格证号
//				temp.CERTIFICATE_NUM = $(CERTIFICATE_NUM).val();
//				//得到发动机号/许可证号
//				temp.ENGINE_CODE = $(ENGINE_CODE).val() ;
//				
//				temp.STAYBUY_PRICE = $(this).find("[name='STAYBUY_PRICE']").val();
//				
//				//得到设备的单价
//				var unitPrice = $(this).find("input[name='UNIT_PRICE']");
//				if ($(unitPrice).val() == "" || $(unitPrice).val() == undefined || $(unitPrice).val()<=0) {
//					flag = false;
//					$(unitPrice).focus();
//					$.messager.alert("提示","请输入设备单价");
//					return;
//				};
//				temp.UNIT_PRICE = $(this).find("[name='UNIT_PRICE']").val();
//				
//				//获取设备数量
//				var amount = $(this).find("input[name='AMOUNT']");
//				if ($(amount).val() == "" || $(amount).val() == undefined  || $(amount).val()<=0) {
//					flag = false;
//					$(unitPrice).focus();
//					$.messager.alert("提示","请输入设备数量");
//					return;
//				};
//				temp.AMOUNT = $(this).find("[name='AMOUNT']").val();
//				
//				//获取小计
//				var TOTAL_PRICE = $(this).find("input[name='TOTAL']");
//				if ($(TOTAL_PRICE).val() == "" || $(TOTAL_PRICE).val() == undefined  || $(TOTAL_PRICE).val()<=0) {
//					flag = false;
//					$(TOTAL_PRICE).focus();
//					$.messager.alert("提示","请输入设备数量");
//					return;
//				};
//				temp.TOTAL_PRICE = $(this).find("[name='TOTAL']").val();
//				
//				temp.UNIT = $(this).find("[name='UNIT']").val();
//				temp.UNITTEST = $(this).find("[name='UNIT']").find("option:selected").text();
//				temp.TOTAL = $(this).find("[name='TOTAL']").val();
//				
//				Eq.push(temp);
//		});
//	 if(flag)
//	 {
//		 var dataJson ={
//				 	SCORE:$("#ALL_SCORE").val(),
//					EqList:Eq,
//					PROJECT_ID:PROJECT_ID,
//					POB_ID:scheme_id,
//					LEASE_TOPRIC:LEASE_TOPRIC,
//					PLATFORM_TYPE:PLATFORM_TYPE,
//					AREA_ID:PROJECT_AREA,
//					PROVINCE_NAME:PROVINCE_NAME,
//					CITY_ID:CITY_ID,
//					CITY_NAME:CITY_NAME,
//					INDUSTRY_FICATION:INDUSTRY_FICATION
//				};
//				$("#ChangeViewData").val(JSON.stringify(dataJson));
//				$("#formPrjoctView").submit();
//	 }
//	 else
//	 {
//		 return;
//	 }
//}


/**
 * 报价筛选商务政策
 */
function BusPol()
{
	//平台
	var MANAGER_NAME=$("#MANAGER_NAME").val();
	//行业
	var INDUSTRY_FICATION=$("#INDUSTRY_FICATION").find("option:selected").val();
	//区域--省
	var PROJECT_AREA=$("#PROJECT_AREA").val();
	//业务类型
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	//客户等级
	var CLIENT_GRADE='';
	var THING_KIND=$("#THING_KIND").find("option:selected").val();
	if(THING_KIND==null||THING_KIND==''||THING_KIND==undefined||THING_KIND=='undefined')
		THING_KIND='';
	//分数
	var ALL_SCORE=$("#ALL_SCORE").val();
	var LEASE_MODEL=$("#LEASE_MODEL").val();
	
	var data="";
	if(PLATFORM_TYPE=='2' || PLATFORM_TYPE=='7' || (PLATFORM_TYPE=='4' && LEASE_MODEL=='back_leasing')){
		data="BUSINESS_PLATFORM="+MANAGER_NAME+"&INDUSTRY_INVOLVED="+INDUSTRY_FICATION+"&AREA="
		 +PROJECT_AREA+"&BUSINESS_TYPE="+PLATFORM_TYPE+"&CLIENT_GRADE="+CLIENT_GRADE+"&ALL_SCORE="+ALL_SCORE;
	}
	else{
		var product_id="";
		var company_id="";
		var suppliers_id="";
		var product_catena_id="";
		var product_type_id="";
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
			var PRODUCT_CATENA=$(this).find("[name='PRODUCT_CATENA']").val();
			if(PRODUCT_CATENA.length>0){
				if(product_catena_id.length<=0)
				{
					product_catena_id=$(this).find("[name='PRODUCT_CATENA']").val();
				}
				else if(product_catena_id.indexOf($(this).find("[name='PRODUCT_CATENA']").val())<0){
					product_catena_id=product_catena_id+","+$(this).find("[name='PRODUCT_CATENA']").val();
				}
			}
			var THING_SPEC=$(this).find("[name='THING_SPEC']").val();
			if(THING_SPEC.length>0&&THING_SPEC!="--请选择--"){
				if(product_type_id.length<=0)
				{
					product_type_id=$(this).find("[name='THING_SPEC']").val();
				}
				else if(product_type_id.indexOf($(this).find("[name='THING_SPEC']").val())<0){
					product_type_id=product_type_id+","+$(this).find("[name='THING_SPEC']").val();
				}
			}
			var COMPANY_ID=$(this).find("[name=COMPANY_ID]").val();
			if(COMPANY_ID.length>0){
				if(company_id.length<=0)
				{
					company_id=$(this).find("[name='COMPANY_ID']").val();
				}
				else if(company_id.indexOf($(this).find("[name='COMPANY_ID']").val())<0){
					company_id=company_id+","+$(this).find("[name='COMPANY_ID']").val();
				}
			}
			var SUPPLIERS_ID=$(this).find("[name=SUPPLIERS_ID]").val();
			if(SUPPLIERS_ID.length>0){
				if(suppliers_id.length<=0)
				{
					suppliers_id=$(this).find("[name='SUPPLIERS_ID']").val();
				}
				else if(suppliers_id.indexOf($(this).find("[name='SUPPLIERS_ID']").val())<0){
					suppliers_id=suppliers_id+","+$(this).find("[name='SUPPLIERS_ID']").val();
				}
			}
		});
		/**********对拼接的串按数字从小到大排序 start****************/
		company_id=company_id.split(",").sort(function(a,b){return parseInt(a)<parseInt(b)?-1:1}).join(",");
//		company_id=','+company_id+",";
		suppliers_id=suppliers_id.split(",").sort(function(a,b){return parseInt(a)<parseInt(b)?-1:1}).join(",");
		product_id=product_id.split(",").sort(function(a,b){return parseInt(a)<parseInt(b)?-1:1}).join(",");
		product_type_id=product_type_id.split(",").sort(function(a,b){return parseInt(a)<parseInt(b)?-1:1}).join(",");
		product_catena_id=product_catena_id.split(",").sort(function(a,b){return parseInt(a)<parseInt(b)?-1:1}).join(",");
		/**********对拼接的串排序 end****************/
		data="COMPANY_ID="+company_id+"&SUPPLIER_ID="+suppliers_id+"&PRODUCT_ID="+product_id
		 +"&BUSINESS_PLATFORM="+MANAGER_NAME+"&INDUSTRY_INVOLVED="+INDUSTRY_FICATION+"&AREA="
		 +PROJECT_AREA+"&BUSINESS_TYPE="+PLATFORM_TYPE+"&CLIENT_GRADE="+CLIENT_GRADE+"&ALL_SCORE="+ALL_SCORE
		 +"&CATENA_ID="+product_catena_id+"&PRODUCT_TYPE_ID="+product_type_id+"&THING_KIND="+THING_KIND;
	}
	
	$("#BusinessPolicy_DIV").empty();
		$.ajax({
			url:_basePath+"/project/project!queryBussPol.action",
			data:data,
			type:"post",
			dataType:"json",
			success:function(list)
			{
				$("#scheme_code").empty();
				var selec=$("#scheme_code");
				selec.append("<option value=''>--请选择--</option>");
				for(var i=0;i<list.data.length;i++){
					selec.append("<option value='"+list.data[i].SCHEME_CODE+"'>"+list.data[i].SCHEME_NAME+list.data[i].ALIASES+"</option>");
				}
			}
		});
}
/**
 * 获取报价设备信息
 * 
 * @author King 2015年3月1日
 */
function getEqList(){
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	var Eq=[];
	 $(".eq_body_tr").each(function(){
				var temp={};
				temp.SCHEME_ID=$("#SCHEME_CODE").val();
				temp.AREA_ID= jQuery.trim($("#PROJECT_AREA").find("option:selected").val());
				temp.CITY_ID=jQuery.trim($("#PROJECT_CITY").find("option:selected").val());
				temp.PROVINCE_NAME=jQuery.trim($("#PROJECT_AREA").find("option:selected").text());
				temp.CITY_NAME=jQuery.trim($("#PROJECT_CITY").find("option:selected").text());
				temp.INDUSTRY_FICATION=jQuery.trim($("#INDUSTRY_FICATION").find("option:selected").val());
				var CERTIFICATE_NUM = $(this).find("input[name='CERTIFICATE_NUM']") ;
				var ENGINE_CODE = $(this).find("input[name='ENGINE_CODE']") ;
		
				if((PLATFORM_TYPE=='4' && LEASE_MODEL=='back_leasing'))//标准型售后回租
				{
					
					var COMPANY_NAME = $(this).find("input[name='COMPANY_NAME']");
					if ($(COMPANY_NAME).val() == "" || $(COMPANY_NAME).val() == null || $(COMPANY_NAME).val() == undefined) {
						flag = false;
						$(COMPANY_NAME).focus();
						$.messager.alert("提示","请填写厂商");
						return;
					};
					
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == null || $(CERTIFICATE_NUM).val() == undefined) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						$.messager.alert("提示","请填写合格证号");
//						return;
//					};
					
					
					temp.COMPANY_NAME =$(this).find("input[name='COMPANY_NAME']").val();
					
					var SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']");
					if ($(SUPPLIERS_NAME).val() == "" || $(SUPPLIERS_NAME).val() == null || $(SUPPLIERS_NAME).val() == undefined) {
						flag = false;
						$(SUPPLIERS_NAME).focus();
						$.messager.alert("提示","请填写供应商");
						return;
					};
					temp.SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']").val();
					
					//得到设备名称
					var THING_NAME = $(this).find("input[name='THING_NAME']");
					if ($(THING_NAME).val() == "" || $(THING_NAME).val() == null || $(THING_NAME).val() == undefined) {
						flag = false;
						$(THING_NAME).focus();
						$.messager.alert("提示","请填写设备名称");
						return;
					};
					temp.PRODUCT_NAME =  $(this).find("input[name='THING_NAME']").val();
					
					//得到设备型号
					var eqCatena = $(this).find("input[name='PRODUCT_CATENA']");
					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
						flag = false;
						$(eqCatena).focus();
						$.messager.alert("提示","请填写设备系别");
						return;
					};
					temp.CATENA_NAME = $(this).find("input[name='PRODUCT_CATENA']").val();
					
					//得到设备型号
					var eqType = $(this).find("input[name='THING_SPEC']");
					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
						flag = false;
						$(eqType).focus();
						$.messager.alert("提示","请填写设备型号");
						return;
					};
					temp.SPEC_NAME =$(this).find("input[name='THING_SPEC']").val();
				} else if(PLATFORM_TYPE=='2')//标准型售后回租
				{
					
					var COMPANY_NAME = $(this).find("input[name='COMPANY_NAME']");
					if ($(COMPANY_NAME).val() == "" || $(COMPANY_NAME).val() == null || $(COMPANY_NAME).val() == undefined) {
						flag = false;
						$(COMPANY_NAME).focus();
						$.messager.alert("提示","请填写厂商");
						return;
					};
					
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == null || $(CERTIFICATE_NUM).val() == undefined) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						$.messager.alert("提示","请填写合格证号");
//						return;
//					};
					
					
					temp.COMPANY_NAME =$(this).find("input[name='COMPANY_NAME']").val();
					
					var SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']");
					if ($(SUPPLIERS_NAME).val() == "" || $(SUPPLIERS_NAME).val() == null || $(SUPPLIERS_NAME).val() == undefined) {
						flag = false;
						$(SUPPLIERS_NAME).focus();
						$.messager.alert("提示","请填写供应商");
						return;
					};
					temp.SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']").val();
					
					//得到设备名称
					var THING_NAME = $(this).find("select[name='THING_NAME']");
					if ($(THING_NAME).val() == "" || $(THING_NAME).val() == null || $(THING_NAME).val() == undefined) {
						flag = false;
						$(THING_NAME).focus();
						$.messager.alert("提示","请选择设备名称");
						return;
					};
					temp.PRODUCT_NAME =  $(this).find("select[name='THING_NAME']").val();
					
//					//得到设备型号
//					var eqCatena = $(this).find("input[name='PRODUCT_CATENA']");
//					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
//						flag = false;
//						$(eqCatena).focus();
//						$.messager.alert("提示","请填写设备系别");
//						return;
//					};
					temp.CATENA_NAME = $(this).find("input[name='PRODUCT_CATENA']").val();
					
					//得到设备型号
					var eqType = $(this).find("input[name='THING_SPEC']");
					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
						flag = false;
						$(eqType).focus();
						$.messager.alert("提示","请填写设备型号");
						return;
					};
					temp.SPEC_NAME =$(this).find("input[name='THING_SPEC']").val();
				}
				else{
					var COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected");
					if ($(COMPANY_ID).val() == "" || $(COMPANY_ID).val() == null || $(COMPANY_ID).val() == undefined) {
						flag = false;
						$(COMPANY_ID).focus();
						$.messager.alert("提示","请选择厂商");
						return;
					};
					temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected").val();
					temp.COMPANY_CODE = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("CODE");
					temp.COMPANY_NAME = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("test");
					
					var SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected");
					if ($(SUPPLIERS_ID).val() == "" || $(SUPPLIERS_ID).val() == null || $(SUPPLIERS_ID).val() == undefined) {
						flag = false;
						$(SUPPLIERS_ID).focus();
						$.messager.alert("提示","请选择供应商");
						return;
					};
					temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").val();
					temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").attr("test");
					
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
				}
				
				//得到合格证号
				temp.CERTIFICATE_NUM = $(CERTIFICATE_NUM).val();
				//得到发动机号/许可证号
				temp.ENGINE_CODE = $(ENGINE_CODE).val() ;
				//得到出厂编号
//				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
				
				//得到出厂日期
//				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
				
				//得到发动机编号
//				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
				
				//得到车架号
//				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
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
				temp.ACTUAL_PRICE = $(this).find("[name='ACTUAL_PRICE']").val();
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
				
				temp.BX = $(this).find("[name='BX']").val();
				temp.CCS = $(this).find("[name='CCS']").val();
				temp.PRO_REMARK = $(this).find("[name='PRO_REMARK']").val();
				
				Eq.push(temp);
				saveFlag = false;
		});
	 return JSON.stringify(Eq);
}

function submitAddScheme(_TYPE)
{
	if(_TYPE=='1'){
		var FLAGTRUE=true;
		//先判断必选资料是否都上传，然后在发起流程
		var rows = $("#pageTable12345").datagrid("getRows"); 
		for(var i=0;i<rows.length;i++)
		{
			//查询每一行资料是否必选
			var CODE_TYPE_FLAG= rows[i].CODE_TYPE_FLAG;
			if(CODE_TYPE_FLAG=='1'){//为必选
				var PICTURE= rows[i].PICTURE;
				if(PICTURE=='YES'){
					;
				}
				else{
					var DZDA_TYPE=rows[i].DZDA_TYPE;
					alert(DZDA_TYPE+"是必选资料，请选上传在发起流程！");
					FLAGTRUE=false;
					return false;
				}
			}
		}
		
		if(!FLAGTRUE){
			return false;
		}
	}
	
	
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
	var PAY_WAY=$("#PAY_WAY").val();
	var CRDIT_MONEY=$("input[name='CRDIT_MONEY']").val();//授信额度
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	var MONEYALL=$("input[name='MONEYALL']").val();//租金合计
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));//租金合计-起租租金
	var COMPANY_ID=$("input[name=COMPANY_ID]").val();//厂商ID
	var PRO_ID=$("#PRO_ID").val();
	var SCHEME_ROW_NUM=$("#SCHEME_ROW_NUM").val();
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
	//得到银行信息  判断首付款或租金付款方式为网银时银行信息必填
	var BANK_ID = $("select[name='BANK_ID']").find("option:selected");
	var ACCOUNT_TYPE=$("select[name='ACCOUNT_TYPE']").find("option:selected").val();;
	var ISWITHHOLDING=$("input[name='ISWITHHOLDING']:checked").val();
//	if(FIRST_PAYMENT_TYPE == "1" || RENT_PAYMENT_TYPE == "1"){
//		if ($(BANK_ID).val() == "" || $(BANK_ID).val() == null || $(BANK_ID).val() == undefined) {
//			flag = false;
//			$(BANK_ID).focus();
//			$.messager.alert("提示","请选择扣款银行!");
//			if(_TYPE!='1'){
//				$("#submitAddScheme0").attr("disabled",false);
//				$("#submitAddScheme0").linkbutton("enable");
//			}
//			return;
//		};
//	}

	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
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
	if(PLATFORM_TYPE !='7'){
		if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<0) {
			flag = false;
			$("#YEAR_INTEREST").focus();
			$.messager.alert("提示","请维护商务政策，确保存在年利率！");
			if(_TYPE!='1'){
				$("#submitAddScheme0").attr("disabled",false);
				$("#submitAddScheme0").linkbutton("enable");
			}
			return;
		};
	}
	
	var flList=[];
	if(PLATFORM_TYPE =='8'){
		var LHSQFS = $("select[name='LHSQFS']").find("option:selected");
	
		if ($(LHSQFS).val() == "" || $(LHSQFS).val() == null || $(LHSQFS).val() == undefined) {
			flag = false;
			$(LHSQFS).focus();
			$.messager.alert("提示","请选择联合收取方式!");
			if(_TYPE!='1'){
				$("#submitAddScheme0").attr("disabled",false);
				$("#submitAddScheme0").linkbutton("enable");
			}
			return;
		};
		
		var flNum=0;
		$(".fl_body_tr").each(function(){
				flNum++;
					var temp={};
					temp.FL_ID = $(this).find("select[name='FL_ID']").find("option:selected").val();
					temp.FL_FLAG = $(this).find("input[name='FL_FLAG']").val();
					flList.push(temp);
		 });
		if(flNum==0){
			$.messager.alert("提示","请选择联合租赁融资公司，如果没有你需要选择的公司，请告知管理员维护联合租赁融资公司!");
			if(_TYPE!='1'){
				$("#submitAddScheme0").attr("disabled",false);
				$("#submitAddScheme0").linkbutton("enable");
			}
			return;
		}
	}
	
	
//	var YEAR_INTEREST_Pay=accDiv(YEAR_INTEREST,100);
//	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000000000");
	var YEAR_INTEREST_Pay=Math.round(parseFloat(YEAR_INTEREST)*1000000)/1000000/100;
	
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入支付方式!");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	}
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
//	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
//		$.messager.alert("提示","请输入首期付款日期！");
//		$("#submitAddScheme0").attr("disabled",false);
//		$("#submitAddScheme0").linkbutton("enable");
//		return ;
//	}
	
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
	
//	var GUARANTEE=$("#GUARANTEE").val();
//	if(GUARANTEE=='1'){
//		if(guaranList.length<1){
//			$.messager.alert("提示","请录入担保信息");
//			if(_TYPE!='1'){
//				$("#submitAddScheme0").attr("disabled",false);
//				$("#submitAddScheme0").linkbutton("enable");
//			}
//			return ;
//		}
//	}
	var FINAL_CUST_ID=$("#FINAL_CUST_ID").val();
	var REMARK=$("#REMARK").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
//	var SUPPLIER_ID=$("input[name=SUPPLIER_ID]").val();
	
	var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&project_code="+PRO_CODE+"&project_id="+PRO_ID+"&rows="+JSON.stringify(Rows);
	//----------------准备还款计划数据--------结束
	var data_="PLATFORM_TYPE="+PLATFORM_TYPE+"&FIRSTPAYDATE="+$("#FIRSTPAYDATE").datebox('getValue')
				+"&PRO_CODE="+PRO_CODE+"&LEASE_TOPRIC="
				+$("#LEASE_TOPRIC").val()+"&PRO_ID="+PRO_ID+"&PROJECT_ID="+PRO_ID+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&CLIENT_ID="+CLIENT_ID+"&BANK_ID="
				+$(BANK_ID).val()+"&ACCOUNT_TYPE="+ACCOUNT_TYPE+"&ISWITHHOLDING="+ISWITHHOLDING+"&FINAL_TYPE="+FINAL_TYPE+"&FINAL_CUST_ID="+FINAL_CUST_ID+"&INVOICE_TARGET_TYPE="
				+INVOICE_TARGET_TYPE+"&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD
				+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+"&YEAR_INTEREST="+YEAR_INTEREST+"&REMARK="+REMARK
				+"&FEES="+FEES+"&FEES_PRICE="+FEES_PRICE+"&guaranList="+JSON.stringify(guaranList)+"&flList="+JSON.stringify(flList)
				+"&projectScheme="+getProjectSchemeBaseInfo("#addSchemeForm")+"&MONEYALL="+MONEYALL+"&ZKL="+$("#ZKL").val();
	data_ = data_ + data1;
	data_=data_+"&_TYPE="+_TYPE;
	var FIRST_MONEY_ALL=$("#FIRSTPAYALL").val();
	var MONEY_ALL=$("#MONEYALL").val();
	data_=data_+"&FIRST_MONEY_ALL="+FIRST_MONEY_ALL+"&MONEY_ALL="+MONEY_ALL;
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	if(RENT_WAY_INVOICE!=''&&RENT_WAY_INVOICE!=null&&RENT_WAY_INVOICE!=undefined&&RENT_WAY_INVOICE!="undefined"){
		data_=data_+"&RENT_WAY_INVOICE="+RENT_WAY_INVOICE;
	}
	var url;
	if(_TYPE=='0'){ //保存
		data_=data_+"&PRO_NAME="+$("#PROJECT_NAME").val()+"&STAUS=0&AREA_ID="+jQuery.trim($("#PROJECT_AREA").find("option:selected").val())
				+"&CUSTOMER_TYPE="+$("#CUSTOMER_TYPE").val()+"&POB_ID="+$("#SCHEME_CODE").val()
				+"&PROVINCE_NAME="+jQuery.trim($("#PROJECT_AREA").find("option:selected").text())
				+"&CITY_ID="+jQuery.trim($("#PROJECT_CITY").find("option:selected").val())
				+"&CITY_NAME"+jQuery.trim($("#PROJECT_CITY").find("option:selected").text())
				+"&INDUSTRY_FICATION="+jQuery.trim($("#INDUSTRY_FICATION").find("option:selected").val())
				+"&EqList="+getEqList()+"&GUARANTEE="+$("#ISGUARANTOR").val()+"&JOINT_TENANT="+$("#JOINT_TENANT").val()
				+"&THING_KIND="+$("#THING_KIND").val()+"&KEQUN="+$("#KEQUN").val()+"&JOINT_TENANT_ID="+$("#JOINT_TENANT_ID").val();
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
					top.addTab("项目修改",_basePath+"/project/project!projectUpdate.action?PROJECT_ID="+json.data);
					top.closeTab("项目立项");
					$.messager.alert("提示","方案保存成功！请上传资料");
				}
			}else{
			
				if(_TYPE!='1'){
					$("#submitAddScheme0").attr("disabled",false);
					$("#submitAddScheme0").linkbutton("enable");
				}
				$("#submitAddScheme_").attr("onclick", "submitAddScheme()");
				$("#submitAddScheme_").removeAttr("title");
			}
		}
	});		
}

function submitAddpayScheme(){	
	$("#submitAddScheme_1").attr("disabled",true);
	$("#submitAddScheme_1").linkbutton("disable");
	var flag=false;
	$(".addData:checked").each(function(){
				flag=true;
		}
	);
//	$(".addData").each(function(){
//		if(this.checked){
//			flag=true;
//		}
//		}
//	);
	if(flag==false){
		alert("请先选择设备并进行测算");
		$("#submitAddScheme_1").attr("disabled",false);
		$("#submitAddScheme_1").linkbutton("enable");
		return;
	}
	var LEASE_TOPRIC_SY=$("#LEASE_TOPRIC_SY").val();
	var MONEYALL=$("#MONEYALL").val();
	if(MONEYALL==null||MONEYALL==''){
		$.messager.alert("提示","请先进行测算");
		$("#submitAddScheme_1").attr("disabled",false);
		$("#submitAddScheme_1").linkbutton("enable");
		return;
	}
	var JBPMFLAG=$("#JBPMFLAG").val();
	var PAY_WAY=$("#PAY_WAY").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CRDIT_MONEY=$("input[name='CRDIT_MONEY']").val();//授信额度
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	if(parseFloat(LEASE_TOPRIC)>parseFloat(LEASE_TOPRIC_SY)){
		alert("实际成交价大于项目剩余金额");
		$("#submitAddScheme_1").attr("disabled",false);
		$("#submitAddScheme_1").linkbutton("enable");
		return;
	}
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	var MONEYALL=$("input[name='MONEYALL']").val();//租金合计
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));//租金合计-起租租金
	var COMPANY_ID=$("input[name=COMPANY_ID]").val();//厂商ID
	var PRO_ID=$("#PRO_ID").val();
	var SCHEME_ROW_NUM=$("#SCHEME_ROW_NUM").val();
	
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
	//得到银行信息  判断首付款或租金付款方式为网银时银行信息必填
	var BANK_ID = $("select[name='BANK_ID']").find("option:selected");
	var ACCOUNT_TYPE=$("select[name='ACCOUNT_TYPE']").find("option:selected").val();;
	var ISWITHHOLDING=$("input[name='ISWITHHOLDING']:checked").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	var FIRST_PAYMENT_TYPE=$("#FIRST_PAYMENT_TYPE").val();
	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
	var RENT_PAYMENT_TYPE=$("#RENT_PAYMENT_TYPE").val();
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
		$("#submitAddScheme_1").attr("disabled",false);
		$("#submitAddScheme_1").linkbutton("enable");
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
		$("#submitAddScheme_1").removeAttr("disabled");
		return;
	}
	var _payCountOfYear=accDiv(12,lease_period);
	
	var LEASE_PERIOD=$("#lease_period").find("option:selected").val();
	var YEAR_INTEREST=0;
	if($("#YEAR_INTEREST").length > 0 )
	{
		YEAR_INTEREST=$("#YEAR_INTEREST").val();
	}
	if(PLATFORM_TYPE !='7'){
		if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<0) {
			flag = false;
			$("#YEAR_INTEREST").focus();
			$.messager.alert("提示","请维护商务政策，确保存在年利率！");
			$("#submitAddScheme_1").attr("disabled",false);
			$("#submitAddScheme_1").linkbutton("enable");
			return;
		};
	}
	
//	var flList=[];
//	if(PLATFORM_TYPE =='8'){
//		var LHSQFS = $("select[name='LHSQFS']").find("option:selected");
//	
//		if ($(LHSQFS).val() == "" || $(LHSQFS).val() == null || $(LHSQFS).val() == undefined) {
//			flag = false;
//			$(LHSQFS).focus();
//			$.messager.alert("提示","请选择联合收取方式!");
//			if(_TYPE!='1'){
//				$("#submitAddScheme0").attr("disabled",false);
//				$("#submitAddScheme0").linkbutton("enable");
//			}
//			return;
//		};
//		
//		var flNum=0;
//		$(".fl_body_tr").each(function(){
//				flNum++;
//					var temp={};
//					temp.FL_ID = $(this).find("select[name='FL_ID']").find("option:selected").val();
//					temp.FL_FLAG = $(this).find("input[name='FL_FLAG']").val();
//					flList.push(temp);
//		 });
//		if(flNum==0){
//			$.messager.alert("提示","请选择联合租赁融资公司，如果没有你需要选择的公司，请告知管理员维护联合租赁融资公司!");
//			if(_TYPE!='1'){
//				$("#submitAddScheme0").attr("disabled",false);
//				$("#submitAddScheme0").linkbutton("enable");
//			}
//			return;
//		}
//	}
	
//	var YEAR_INTEREST_Pay=accDiv(YEAR_INTEREST,100);
//	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000000000");
	var YEAR_INTEREST_Pay=Math.round(parseFloat(YEAR_INTEREST)*1000000)/1000000/100;
	
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入支付方式!");
		$("#submitAddScheme_1").attr("disabled",false);
		$("#submitAddScheme_1").linkbutton("enable");
		return;
	}
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
//	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
//		$.messager.alert("提示","请输入首期付款日期！");
//		$("#submitAddScheme0").attr("disabled",false);
//		$("#submitAddScheme0").linkbutton("enable");
//		return ;
//	}
	
	var FEES='0';
	if($("#fees").length > 0 ){
		FEES=$("#fees").val();
	}
	
	var FEES_PRICE='0';
	if($("#FEES_PRICE").length > 0 ){
		FEES_PRICE=$("#FEES_PRICE").val();
	}
//	var guaranList=[];
//	 $(".guaran_body_tr").each(function(){
//				var temp={};
//				temp.GUARAN_TYPE = $(this).find("select[name='GUARAN_TYPE']").find("option:selected").val();
//				temp.GUARAN_NAME = $(this).find("[name='GUARAN_NAME']").val();
//				temp.ORGANIZATION_CODE = $(this).find("[name='ORGANIZATION_CODE']").val();
//				temp.GUARAN_PHONE = $(this).find("[name='GUARAN_PHONE']").val();
//				temp.FAUSTPFAND=$(this).find("[name='FAUSTPFAND']").val();
//				temp.GUARANTEE_ENTITY=$(this).find("[name='GUARANTEE_ENTITY']").val();
//				guaranList.push(temp);
//	 });
	
	var FINAL_CUST_ID=$("#FINAL_CUST_ID").val();
	var REMARK=$("#REMARK").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
//	var SUPPLIER_ID=$("input[name=SUPPLIER_ID]").val();
	
	var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&project_code="+PRO_CODE+"&project_id="+PRO_ID+"&rows="+JSON.stringify(Rows);
	//----------------准备还款计划数据--------结束
	var data_="PLATFORM_TYPE="+PLATFORM_TYPE+"&FIRSTPAYDATE="+$("#FIRSTPAYDATE").datebox('getValue')
				+"&PRO_CODE="+PRO_CODE+"&LEASE_TOPRIC="
				+$("#LEASE_TOPRIC").val()+"&PRO_ID="+PRO_ID+"&PROJECT_ID="+PRO_ID+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&CLIENT_ID="+CLIENT_ID+"&BANK_ID="
				+$(BANK_ID).val()+"&ACCOUNT_TYPE="+ACCOUNT_TYPE+"&ISWITHHOLDING="+ISWITHHOLDING+"&FINAL_TYPE="+FINAL_TYPE+"&FINAL_CUST_ID="+FINAL_CUST_ID+"&INVOICE_TARGET_TYPE="
				+INVOICE_TARGET_TYPE+"&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD
				+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+"&YEAR_INTEREST="+YEAR_INTEREST+"&REMARK="+REMARK
				+"&FEES="+FEES+"&FEES_PRICE="+FEES_PRICE
				+"&projectScheme="+getProjectSchemeBaseInfo("#addSchemeForm")+"&MONEYALL="+MONEYALL+"&ZKL="+$("#ZKL").val();
	data_ = data_ + data1;
	var FIRST_MONEY_ALL=$("#FIRSTPAYALL").val();
	var MONEY_ALL=$("#MONEYALL").val();
	data_=data_+"&FIRST_MONEY_ALL="+FIRST_MONEY_ALL+"&MONEY_ALL="+MONEY_ALL;
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	if(RENT_WAY_INVOICE!=''&&RENT_WAY_INVOICE!=null&&RENT_WAY_INVOICE!=undefined&&RENT_WAY_INVOICE!="undefined"){
		data_=data_+"&RENT_WAY_INVOICE="+RENT_WAY_INVOICE;
	}
	var url;
		data_=data_+"&PRO_NAME="+$("#PROJECT_NAME").val()+"&STAUS=0&AREA_ID="+jQuery.trim($("#PROJECT_AREA").find("option:selected").val())
				+"&CUSTOMER_TYPE="+$("#CUSTOMER_TYPE").val()+"&POB_ID="+$("#SCHEME_CODE").val()
				+"&PROVINCE_NAME="+jQuery.trim($("#PROJECT_AREA").find("option:selected").text())
				+"&CITY_ID="+jQuery.trim($("#PROJECT_CITY").find("option:selected").val())
				+"&CITY_NAME"+jQuery.trim($("#PROJECT_CITY").find("option:selected").text())
				+"&INDUSTRY_FICATION="+jQuery.trim($("#INDUSTRY_FICATION").find("option:selected").val())
				+"&EqList="+getEqList1()+"&GUARANTEE="+$("#ISGUARANTOR").val()+"&JOINT_TENANT="+$("#JOINT_TENANT").val()
				+"&THING_KIND="+$("#THING_KIND").val()+"&KEQUN="+$("#KEQUN").val()+"&JOINT_TENANT_ID="+$("#JOINT_TENANT_ID").val();
		url=_basePath+"/project/project!addSchemeForPayProject.action";
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:url,
		data:data_,
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","生成支付表成功！","info",function(){
						top.closeTab("合同生成");
						top.addTab("合同生成", _basePath+"/project/project!project_Info_Main.action");
						top.closeTab("起租申请");
						});
			}else{
				$.messager.alert("提示","生成支付表失败！");
				$("#submitAddScheme_1").attr("disabled",false);
				$("#submitAddScheme_1").linkbutton("enable");
			}
		}
	});	
}
//保存方案
function submitAppendScheme(_TYPE)
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
	
	
	var GUARANTEE_MODEL=$("#GUARANTEE_MODEL").val();
	
	var PAY_WAY=$("#PAY_WAY").val();
	
	var DATE_COMPLEMENT=$("#DATE_COMPLEMENT").val();

	
	var CRDIT_MONEY=$("input[name='CRDIT_MONEY']").val();//授信额度
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));//租金合计-起租租金
	var PRO_ID=$("#PRO_ID").val();
	var SCHEME_ROW_NUM=$("#SCHEME_ROW_NUM").val();

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
	if(PLATFORM_TYPE !='7'){
		if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<0) {
			flag = false;
			$("#YEAR_INTEREST").focus();
			$.messager.alert("提示","请维护商务政策，确保存在年利率！");
			if(_TYPE!='1'){
				$("#submitAddScheme0").attr("disabled",false);
				$("#submitAddScheme0").linkbutton("enable");
			}
			return;
		};
	}
//	var YEAR_INTEREST_Pay=accDiv(YEAR_INTEREST,100);
//	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000000000");
	var YEAR_INTEREST_Pay=Math.round(parseFloat(YEAR_INTEREST)*1000000)/1000000/100;
	
	var DELIVER_DATE="";
	if($("#DELIVER_DATE").length > 0 )
	{
		DELIVER_DATE=$("#DELIVER_DATE").val();
	}
	else
	{
//		$.messager.alert("提示","请输入交换时间!");
//		if(_TYPE!='1'){
//			$("#submitAddScheme0").attr("disabled",false);
//			$("#submitAddScheme0").linkbutton("enable");
//		}
//		return;
	}
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入支付方式!");
		if(_TYPE!='1'){
			$("#submitAddScheme0").attr("disabled",false);
			$("#submitAddScheme0").linkbutton("enable");
		}
		return;
	}
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
//	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
//		$.messager.alert("提示","请输入首期付款日期！");
//		$("#submitAddScheme0").attr("disabled",false);
//		$("#submitAddScheme0").linkbutton("enable");
//		return ;
//	}
	
	var FEES='0';
	if($("#fees").length > 0 ){
		FEES=$("#fees").val();
	}
	
	var FEES_PRICE='0';
	if($("#FEES_PRICE").length > 0 ){
		FEES_PRICE=$("#FEES_PRICE").val();
	}
	
	
	var REMARK=$("#REMARK").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
	
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&date="+DELIVER_DATE+"&project_code="+PRO_CODE+"&project_id="+PRO_ID+"&rows="+JSON.stringify(Rows);
	//----------------准备还款计划数据--------结束
	var data_="PLATFORM_TYPE="+PLATFORM_TYPE+"&FIRSTPAYDATE="+FIRSTPAYDATE
				+"&PRO_CODE="+PRO_CODE+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&LEASE_TOPRIC="
				+LEASE_TOPRIC+"&PRO_ID="+PRO_ID+"&PROJECT_ID="+PRO_ID+"&CLIENT_ID="+CLIENT_ID+"&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD
				+"&YEAR_INTEREST="+YEAR_INTEREST+"&projectScheme="+getProjectSchemeBaseInfo("#addSchemeForm")
				+"&MONEYALL="+MONEYALL+"&ZKL="+$("#ZKL").val()+"&EqList="+getEqList1();
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

	var url=_basePath+"/project/project!appendSchemeForProject.action";
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:url,
		data:data_,
		success:function(json){
			
			if(json.flag){
				$.messager.alert("提示","方案保存成功！");
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
	if($("#SALE_NAME").val()=='' || ($("#SALE_NAME").val()=='其他' && $("#otherName").val()=='')){
		alert("请维护必填项信息:[销售人员]");
		return;
	}
	$("#submitAddScheme_1").attr("disabled",true);
	$("#submitAddScheme_1").linkbutton("disable");
	var PROJECT_ID = $("#PROJECT_ID").val();
	
	var SCHEME_ID_ACTUAL=$("#SCHEME_ID_ACTUAL").val();
	var CC_PRICE=$("#CC_PRICE").val();
	var REAL_PRICE=$("#REAL_PRICE").val();
	var SCHEME_ROW_NUM= $("#SCHEME_ROW_NUM").val();
	var AREA_ID= $("select[name='PROJECT_AREA']").find("option:selected").val();
	var PROJECT_AREA = $("select[name='PROJECT_AREA']").find("option:selected").text();
//	debugger;
//	if(PROJECT_AREA ==''|| PROJECT_AREA==null){
//		$.messager.alert("提示","请选择区域!");
//		return true;
//	}
	
	var PROJECT_CITY = $("select[name='PROJECT_CITY']").find("option:selected").val();
//	var CITY_NAME = $("select[name='PROJECT_CITY']").find("option:selected").text();
//	if(PROJECT_CITY ==''|| PROJECT_CITY==null){
//		$.messager.alert("提示","请选择区域!");
//		return true;
//	}
	
	var INDUSTRY_FICATION= $("select[name='INDUSTRY_FICATION']").find("option:selected").val();
//	if(INDUSTRY_FICATION ==''|| INDUSTRY_FICATION==null){
//		$.messager.alert("提示","请选择行业!");
//		return true;
//	}
	
	
	var PAY_WAY=$("#PAY_WAY").val();
	
	
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	
	
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	
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
	
	var PAY_WAY_=$("#PAY_WAY").val();
	if(PAY_WAY==null||PAY_WAY==''||PAY_WAY=='undefined'||PAY_WAY==undefined )
	{
		$.messager.alert("提示","请输入支付方式!");
		return;
	}
	
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
//	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
//		$.messager.alert("提示","请输入首期付款日期！");
//		return ;
//	}
	
	var JOINT_TENANT=$("#JOINT_TENANT").val();
	var JOINT_TENANT_ID=$("#JOINT_TENANT_ID").val();
//	try{
//		if(JOINT_TENANT!=null&&JOINT_TENANT!=''&&JOINT_TENANT!='undefined'&&JOINT_TENANT!=undefined&& (JOINT_TENANT==2 || JOINT_TENANT==3)){
//			if($("#JOINT_TENANT_ID").val()==''||$("#JOINT_TENANT_ID").val()==null||$("#JOINT_TENANT_ID").val()=='undefined'||$("#JOINT_TENANT_ID").val()==undefined){
//				$.messager.alert("提示","请选择共同承租人!");
//				return ;
//			}
//		}
//	}catch(Exception){}	
	
	
	//----------------准备还款计划数据--------开始
	var scheme = getProjectSchemeBaseInfo("#addSchemeForm");
	var EqList=getEqList();
	if (saveFlag) {
		$("#submitAddScheme_1").attr("disabled",false);
		$("#submitAddScheme_1").linkbutton("enable");
		return;
	}
	//---------汽车颜色------start--
	var carColor = $('#CAR_COLOR').val();
	var xhParam = $('#XH_PARAM').val();
	var bx = $('#BX').val();
	var ccs = $('#CCS').val();
	var pro_remark = $('#PRO_REMARK').val();
	var JQX = $('#JQX').val();
	//---------汽车颜色------end--
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&rows="+JSON.stringify(Rows);
    //var checkIndex=$("#SUPPLIERS_ID").get(0).selectedIndex;
	//var suppliersId=document.getElementByName("SUPPLIERS_ID").options[checkIndex].text;
	
	var $tsObj=$(document.getElementsByName("SUPPLIERS_ID")[0]);//经销商
	suppliersId=$tsObj.find("option:selected").text();
	
	var $tsObj=$(document.getElementsByName("THING_NAME")[0]);//品牌
	thingName=$tsObj.find("option:selected").text();
	
	var $tsObj=$(document.getElementsByName("PRODUCT_CATENA")[0]);//系列
	productCatena=$tsObj.find("option:selected").text();

	var $tsObj=$(document.getElementsByName("THING_SPEC")[0]);//车型
	thingSpec=$tsObj.find("option:selected").text();
	
	var $tsObj=$(document.getElementsByName("ACTUAL_PRICE")[0]);//实际采购价
	actualPrice=$tsObj.find("option:selected").text();
	//alert("实际采购价"+actualPrice);

	/*var $tsObj=$(document.getElementsByName("CAR_COLOR")[0]);//颜色
	carColor=$tsObj.find("option:selected").text();*/
	//alert("颜色"+carColor);
	
	/*var $tsObj=$(document.getElementsByName("XH_PARAM")[0]);//详细型号
	xhParam=$tsObj.find("option:selected").text();*/
	//alert("详细型号"+xhParam);
	
	/*var $tsObj=$(document.getElementsByName("BX")[0]);//商业险INSURANCE_MONEY
	bx=$tsObj.find("option:selected").text();*/
	//alert("商业险"+bx);
	
	var $tsObj=$(document.getElementsByName("SCHEME_CODE1")[0]);//产品方案名称
	planName=$tsObj.find("option:selected").text();

	var $tsObj=$(document.getElementsByName("lease_term")[0]);//期数
	leaseTerm=$tsObj.find("option:selected").text();
	
	var financeTopric=$("#FINANCE_TOPRIC").val();//融资额

	var firstpayall=$("#FIRSTPAYALL").val();//首笔款
	//如果选择其他的时候SALE_NAME取输入框 的值
	var SALE_NAME = $("#SALE_NAME").val();
	if(SALE_NAME==="其他"){
		SALE_NAME = $("#otherName").val();
	}
	var data_="PROJECT_AREA="+PROJECT_AREA+"&AREA_ID="+AREA_ID+"&INDUSTRY_FICATION="+INDUSTRY_FICATION+"&PROJECT_CITY="+PROJECT_CITY+"&SCHEME_ID_ACTUAL="+SCHEME_ID_ACTUAL+
	  "&PROJECT_ID="+PROJECT_ID+"&JOINT_TENANT="+JOINT_TENANT+"&JOINT_TENANT_ID="+JOINT_TENANT_ID+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&projectScheme="+scheme+"&EqList="+EqList+"&SUPPLIERS_ID="+suppliersId+
	  "&THING_NAME="+thingName+"&PRODUCT_CATENA="+productCatena+"&THING_SPEC="+thingSpec+"&ACTUAL_PRICE="+actualPrice+"&CAR_COLOR="+carColor+"&XH_PARAM="+xhParam+"&BX="+bx+
	  "&SCHEME_CODE1="+planName+"&lease_term="+leaseTerm+"&FINANCE_TOPRIC="+financeTopric+"&FIRSTPAYALL="+firstpayall;
	data_ = data_ + data1+"&CAR_COLOR="+carColor+"&XH_PARAM="+xhParam+"&BX="+bx+"&CCS="+ccs+"&PRO_REMARK="+pro_remark+"&JQX="+JQX+"&CC_PRICE="+CC_PRICE +"&REAL_PRICE="+REAL_PRICE+"&SALE_NAME="+SALE_NAME;
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:_basePath+"/project/project!updateSchemeForProject.action",
		data:data_,
		success:function(json){
			if(json.flag){
					$.messager.alert("提示","方案修改成功！");
					
			}else{
				$.messager.alert("提示",json.msg);
			}
		}
	});	
	
}

//修改发起评审
function submitUpdateScheme()
{
	//客户收益分析信息录入
	var EARNINGS=$("#EARNINGS").val();
	if(EARNINGS==true){
		checkEarnings();
	}
	
	var FLAGTRUE=true;
	//先判断必选资料是否都上传，然后在发起流程
	var rows = $("#pageTable12345").datagrid("getRows"); 
	for(var i=0;i<rows.length;i++)
	{
		//查询每一行资料是否必选
		var CODE_TYPE_FLAG= rows[i].CODE_TYPE_FLAG;
		if(CODE_TYPE_FLAG=='1'){//为必选
			var PICTURE= rows[i].PICTURE;
			if(PICTURE=='YES'){
				;
			}
			else{
				var DZDA_TYPE=rows[i].DZDA_TYPE;
				alert(DZDA_TYPE+"是必选资料，请选上传在发起流程！");
				FLAGTRUE=false;
				return false;
			}
		}
	}
	
	if(!FLAGTRUE){
		return false;
	}
	
	var PRO_ID=$("#PRO_ID").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	 var SUPPLIERS_ID;
	 $(".eq_body_tr").each(function(){
			SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").val();
		});
	var data_="&SUP_ID="+SUPPLIERS_ID+"&PRO_ID="+PRO_ID+
			  "&PROJECT_ID="+PRO_ID+"&CLIENT_ID="+CLIENT_ID+"&PRO_CODE="+PRO_CODE;
	$("#submitUpdateScheme_").removeAttr("onclick");
	$("#submitUpdateScheme_").attr("title","流程发起中...");
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:_basePath+"/project/project!projectStartJbpm.action",
		data:data_,
		success:function(json){
			if(json.flag){			
						$.messager.alert("提示","发起流程成功！","info",function(){
						top.closeTab("项目一览");
						top.addTab("项目一览",_basePath+"/project/project!getDataList.action?PRO_CODE="+$("#PRO_CODE").val());
						top.closeTab(PRO_CODE+"方案修改");
						});
					
					//alert(json.msg+json.data) ;
									
			}else{
				$.messager.alert("提示",json.msg);
				$("#submitUpdateScheme_").attr("onclick","submitUpdateScheme()");
				$("#submitUpdateScheme_").removeAttr("title");
			}
		}
	});	
}
//合同重签修改发起评审
function submitUpdateSchemeCQ()
{
	//客户收益分析信息录入
	var EARNINGS=$("#EARNINGS").val();
	if(EARNINGS==true){
		checkEarnings();
	}
	
	var FLAGTRUE=true;
	//先判断必选资料是否都上传，然后在发起流程
	var rows = $("#pageTable12345").datagrid("getRows"); 
	for(var i=0;i<rows.length;i++)
	{
		//查询每一行资料是否必选
		var CODE_TYPE_FLAG= rows[i].CODE_TYPE_FLAG;
		if(CODE_TYPE_FLAG=='1'){//为必选
			var PICTURE= rows[i].PICTURE;
			if(PICTURE=='YES'){
				;
			}
			else{
				var DZDA_TYPE=rows[i].DZDA_TYPE;
				alert(DZDA_TYPE+"是必选资料，请选上传在发起流程！");
				FLAGTRUE=false;
				return false;
			}
		}
	}
	
	if(!FLAGTRUE){
		return false;
	}
	
	var PRO_ID=$("#PRO_ID").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	 var SUPPLIERS_ID;
	 $(".eq_body_tr").each(function(){
			SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").val();
		});
	var data_="&SUP_ID="+SUPPLIERS_ID+"&PRO_ID="+PRO_ID+
			  "&PROJECT_ID="+PRO_ID+"&CLIENT_ID="+CLIENT_ID+"&PRO_CODE="+PRO_CODE;
	$("#submitUpdateScheme_").removeAttr("onclick");
	$("#submitUpdateScheme_").attr("title","流程发起中...");
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:_basePath+"/project/project!projectStartJbpmCQ.action",
		data:data_,
		success:function(json){
			if(json.flag){			
						$.messager.alert("提示","发起流程成功！","info",	function(){
						top.closeTab("合同重签");
						top.addTab("合同重签",_basePath+"/project/project!project_re_sign_Main.action");
					    top.closeTab(PRO_CODE+"重签申请");
						});
					
					//alert(json.msg+json.data) ;
									
			}else{
				$.messager.alert("提示",json.msg);
				$("#submitUpdateSchemeCQ_").attr("onclick","submitUpdateSchemeCQ()");
				$("#submitUpdateSchemeCQ_").removeAttr("title");
			}
		}
	});	
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

//function revfinsnInit(){
//	var COMPANY_NAME=$("input[name=COMPANY_NAME]").val();
//	if(COMPANY_NAME=='山重建机'){
//		var refin=$("select[name=REFINSNCING_RENT]").find("option:selected").val();
//		if(refin=='yes')
//		{
//			$(".yincangDiv").attr("style","");
//		}
//	}
//}

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
function seProjByKHMC(KHMC,CUST_NAME){
	//判断逾期
	$.ajax({
		type:'post',
		url:_basePath+"/project/project!isDunCust.action?CUST_ID="+KHMC,
		dataType:'json',
		success:function(json){
			if(json.data=='0'){
				$.messager.alert("提示","新客户，无逾期记录!");
			}else if(json.data=='1'){
				$.messager.alert("提示","无逾期!");
			}
			else{
				top.addTab("客户："+CUST_NAME+" 逾期信息", _basePath + "/project/project!query_overdue_AllByCust.action?CUST_ID=" + encodeURI(KHMC));
			}
		}
	});
	
	
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
	var FILE_PROJECT_NAME=$("#FILE_PROJECT_NAME").val();
	if(FILE_PROJECT_NAME == ''){
		alert("请选输入项目名称！");
		return;
	}
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

function SchemeView(SCHEME_ROW_NUM,PROJECT_ID,PROJECT_CODE,ROWNUMBER,PLATFORM_TYPE,SCHEME_ID){
	top.addTab(PROJECT_CODE+"项目方案"+ROWNUMBER+"查看",_basePath+"/project/project!SchemeView.action?SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID);
}

function SchemeUpdate(SCHEME_ROW_NUM,PROJECT_ID,PROJECT_CODE,ROWNUMBER,PLATFORM_TYPE,SCHEME_ID,SCHEME_ID_ACTUAL){
	top.addTab(PROJECT_CODE+"项目方案"+ROWNUMBER+"修改",_basePath+"/project/project!SchemeUpdate.action?SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID+"&SCHEME_ID_ACTUAL="+SCHEME_ID_ACTUAL);
}

function SchemeNew(SCHEME_ROW_NUM,PROJECT_ID,PROJECT_CODE,ROWNUMBER,PLATFORM_TYPE,SCHEME_ID){
	top.addTab(PROJECT_CODE+"项目方案"+ROWNUMBER+"新建",_basePath+"/project/project!SchemeNew.action?SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID);
}

function SchemeDelete(SCHEME_ROW_NUM,PROJECT_ID,PROJECT_CODE,ROWNUMBER,PLATFORM_TYPE){
	$.messager.confirm("删除","确定要删除该条数据吗？",function(r){
		if(r){
			$.ajax({
				type:'post',
				url:_basePath+"/project/project!SchemeDelete.action?PROJECT_ID="+PROJECT_ID+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM,
				dataType:'json',
				success:function(json){
					if(json.flag){
						$.messager.alert("提示","删除方案成功");
						$(".TR"+SCHEME_ROW_NUM).remove();
					}else{
						$.messager.alert("提示","删除方案失败");
					}
				}
			});		
		}
	});
}

function saveBaseInfo(PROJECT_ID){
	var CUST_STATUS=$("#CUST_STATUS").val();
	var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	var FINAL_TYPE = $("select[name='FINAL_TYPE']").find("option:selected").val();
	var FINAL_CUST_ID = $("select[name='FINAL_CUST_ID']").find("option:selected").val();
	var BANK_ID;
	if(CUST_STATUS == '0'){
		BANK_ID = $("#BANK_ID").val();
	}else{
		BANK_ID = $("select[name='BANK_ID']").find("option:selected").val();
	}
	
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	var ACCOUNT_TYPE=$("select[name='ACCOUNT_TYPE']").find("option:selected").val();
	if(CUST_STATUS == '0'){
		
		if (ACCOUNT_TYPE == "" || ACCOUNT_TYPE == null || ACCOUNT_TYPE == undefined) {
			flag = false;
			$("select[name='ACCOUNT_TYPE']").focus();
			$.messager.alert("提示","请选择帐号类型!");
			return;
		};
		
		//modify by lishuo 01-28-2016 Start
		var ACCOUNT_BANK=$("#ACCOUNT_BANK").val();
		if (ACCOUNT_BANK == "" || ACCOUNT_BANK == null || ACCOUNT_BANK == undefined) {
			flag = false;
			$("#ACCOUNT_BANK").focus();
			$.messager.alert("提示","请选择所属总行!");
			
			return;
		};
		
		var HEAD_OFFICE=$("#HEAD_OFFICE").val();
		if (HEAD_OFFICE == "" || HEAD_OFFICE == null || HEAD_OFFICE == undefined) {
			flag = false;
			$("#HEAD_OFFICE").focus();
			$.messager.alert("提示","请填写开户行全称!");
			
			return;
		};
		//modify by lishuo 01-28-2016 End
		
		var ACCOUNT_NAME=$("#ACCOUNT_NAME").val();
		if (ACCOUNT_NAME == "" || ACCOUNT_NAME == null || ACCOUNT_NAME == undefined) {
			flag = false;
			$("#ACCOUNT_NAME").focus();
			$.messager.alert("提示","请填写持卡人!");
			
			return;
		};
		
		var ACCOUNT_COUNT=$("#ACCOUNT_COUNT").val();
		if (ACCOUNT_COUNT == "" || ACCOUNT_COUNT == null || ACCOUNT_COUNT == undefined) {
			flag = false;
			$("#ACCOUNT_COUNT").focus();
			$.messager.alert("提示","请填写账户!");
			
			return;
		};
	}
	
	var ISWITHHOLDING=$("input[name='ISWITHHOLDING']:checked").val();
	
	var date_="";
	if(PLATFORM_TYPE =='8'){
		var LHSQFS = $("select[name='LHSQFS']").find("option:selected");
		if ($(LHSQFS).val() == "" || $(LHSQFS).val() == null || $(LHSQFS).val() == undefined) {
			flag = false;
			$(LHSQFS).focus();
			$.messager.alert("提示","请选择联合收取方式!");
			
			return;
		};
		
		var flList=[];
		var flNum=0;
		$(".fl_body_tr").each(function(){
				flNum++;
					var temp={};
					temp.FL_ID = $(this).find("select[name='FL_ID']").find("option:selected").val();
					temp.FL_FLAG = $(this).find("input[name='FL_FLAG']").val();
					flList.push(temp);
		 });
		if(flNum==0){
			$.messager.alert("提示","请选择联合租赁融资公司，如果没有你需要选择的公司，请告知管理员维护联合租赁融资公司!");
			return;
		}
		date_="&LHSQFS="+LHSQFS.val()+"&flList="+JSON.stringify(flList);
	}
	
	var REMARK=$("#REMARK").val();
	
	var data_="PROJECT_ID="+PROJECT_ID+"&ISWITHHOLDING="+ISWITHHOLDING
					 +"&ACCOUNT_TYPE="+ACCOUNT_TYPE+"&INVOICE_TARGET_TYPE="+INVOICE_TARGET_TYPE+"&FINAL_TYPE="+FINAL_TYPE
					 +"&FINAL_CUST_ID="+FINAL_CUST_ID+"&BANK_ID="+BANK_ID+"&REMARK="+encodeURI(REMARK)
					 +"&PLATFORM_TYPE="+PLATFORM_TYPE+date_+"&CUST_STATUS="+CUST_STATUS;
	if(CUST_STATUS==0){
		data_=data_+"&ACCOUNT_BANK="+$("#ACCOUNT_BANK").val()+"&ACCOUNT_NAME="+$("#ACCOUNT_NAME").val()+"&ACCOUNT_COUNT="+$("#ACCOUNT_COUNT").val()
				+"&CLIENT_ID="+$("#CLIENT_ID").val()+"&HEAD_OFFICE="+$("#HEAD_OFFICE").val();// add HEAD_OFFICE by lishuo 01-28-2016 
	}
	$.ajax({
		type:'post',
		url:_basePath+"/project/project!saveBaseInfo.action",
		data:data_,
		dataType:'json',
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功");
				$('#BAPROJECTCODEBTN').linkbutton('disable');

			}else{
				$.messager.alert("提示","保存失败");
			}
		}
	});
}


//Add By:YangJ 2014年5月9日10:57:14  改动供应商和型号时，改变分数
function RefreshScore(){
	
	$("#score").empty();
	var temp1=new Array;
	var temp2=new Array;
	$(".eq_body_tr").each(
			function()
				{
					temp1.push($(this).find("select[name=SUPPLIERS_ID]").find("option:selected").attr("SCORE"));
					temp2.push($(this).find("select[name=THING_SPEC]").find("option:selected").attr("score"));
				});

//	计算供应商平均分数
	var tempScore=0;
	for(var i=0;i<temp1.length;i++)
	{	
		temp1[i]=(temp1[i]==null)? 0:temp1[i];
//		alert("供应商分数 ："+temp1[i]);
		tempScore=tempScore+parseFloat( temp1[i]);
	}
	$("#SUP_SCORE").val(((tempScore/temp1.length)*$("#supscore").val()/100).toFixed(2));
	
//	计算产品平均分数
	tempScore=0;
	for(var i=0;i<temp2.length;i++)
	{
		temp2[i]=(temp2[i]==null)? 0:temp2[i];
		tempScore=tempScore+parseFloat(temp2[i]);
	}
	
	$("#PROD_SCORE").val(((tempScore/temp2.length)*$("#prodscore").val()/100).toFixed(2));
	
//	刷新总分
	$("#ALL_SCORE").val((parseFloat ($("#CUST_SCORE").val())+parseFloat ($("#PROJ_SCORE").val())+parseFloat ($("#SUP_SCORE").val())+parseFloat ($("#PROD_SCORE").val())).toFixed(2));
	
	showScore();
	}
	
//刷新分数显示
function showScore(){
    var title;
    title="客户评分:"+$("#CUST_SCORE").val()+" 信用评分:"+$("#PROJ_SCORE").val()+" 供应商评分:"+$("#SUP_SCORE").val()+" 产品评分:"+$("#PROD_SCORE").val();
$("#score").empty();
$("#score").append($("#ALL_SCORE").val());
$("#score").attr("title",title);    
$("#fenshu").attr("title",title);    
}


function SIGN_SAVE(){
	var PROJECT_ID=$("input[name=PROJECT_ID]").val();
	var SELF_SIGN_NAME=$("input[name=SELF_SIGN_NAME]").val();
	var CUST_SIGN_NAME=$("input[name=CUST_SIGN_NAME]").val();
	var SELF_SIGN_DATE=$("input[name=SELF_SIGN_DATE]").val();
	var SIGNED_DATE=$("input[name=SIGNED_DATE]").val();
	
	if (SELF_SIGN_DATE == "" || SELF_SIGN_DATE == undefined || SELF_SIGN_DATE==null) {
		$("input[name=SELF_SIGN_DATE]").focus();
		$.messager.alert("提示","请输入出租人签字日期！");
		return;
	};
	
	if (SIGNED_DATE == "" || SIGNED_DATE == undefined || SIGNED_DATE==null) {
		$("input[name=SIGNED_DATE]").focus();
		$.messager.alert("提示","请输入承租人签字日期！");
		return;
	};
	
	$.ajax({
		type:"post",
		url:_basePath+"/project/project!SIGN_SAVE.action",
		data:"PROJECT_ID="+PROJECT_ID+"&SELF_SIGN_NAME="+encodeURI(SELF_SIGN_NAME)+"&CUST_SIGN_NAME="+encodeURI(CUST_SIGN_NAME)+"&SELF_SIGN_DATE="+SELF_SIGN_DATE+
			  "&SIGNED_DATE="+SIGNED_DATE,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功!");
			}else{
				$.messager.alert("提示",json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","请联系网络管理员");
		}
	});
}


function FLChange(obj){
	var TELPHONE=$(obj).find("option:selected").attr("TELPHONE");
	var OPEN_BANK_NAME=$(obj).find("option:selected").attr("OPEN_BANK_NAME");
	var OPEN_BANK_ACCOUNT=$(obj).find("option:selected").attr("OPEN_BANK_ACCOUNT");
	var OPEN_BANK_ADDRESS=$(obj).find("option:selected").attr("OPEN_BANK_ADDRESS");
	
	var tr=$(obj).parent().parent();
	$(tr).find("input[name=TELPHONE]").val(TELPHONE);//联系电话
	$(tr).find("input[name=OPEN_BANK_NAME]").val(OPEN_BANK_NAME);//开户银行
	$(tr).find("input[name=OPEN_BANK_ACCOUNT]").val(OPEN_BANK_ACCOUNT);//开户帐号 
	$(tr).find("input[name=OPEN_BANK_ADDRESS]").val(OPEN_BANK_ADDRESS);//开户地址
}
function ISWITHHOLDING(e){
	if(e=='1')
	{
		$("#FINAL_TYPE").attr("disabled",true);
		$("#FINAL_CUST_ID").attr("disabled",true);
		$("#BANK_ID").attr("disabled",true);
		$("#ACCOUNT_TYPE").attr("disabled",true);
	}else
	{
		$("#FINAL_TYPE").removeAttr('disabled');
		$("#FINAL_CUST_ID").removeAttr('disabled');
		$("#BANK_ID").removeAttr('disabled');
		$("#ACCOUNT_TYPE").removeAttr('disabled');
	}
}
function AccountCusttype(obj)
{
var ACCOUNTTYPE=$(obj).find("option:selected").val();
var CLIENT_ID=$("#CLIENT_ID").val();
var tr=$(obj).parent().parent();
var spec=$(tr).find("select[name='BANK_ID']");
$.ajax({
	url:_basePath+"/project/project!ACCOUNTTYPE.action?ACCOUNTTYPE="+ACCOUNTTYPE+"&CLIENT_ID="+CLIENT_ID,
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

function showScheme(param){
	$("input[name='ACTUAL_PRICE']").val($("input[name='UNIT_PRICE']").val());//add by lishuo 03-29-2016
	//add by lishuo 04-05-2016 集采方案提示 Start
	if($("#scheme_code").find("option:selected").text().indexOf('集采') > -1){
	   $.messager.alert("提示","此方案为集采方案，请注意不要修改4S店采购价！");
	}
	//add by lishuo 04-05-2016 集采方案提示 End
	$("#FIRSTMONEYHELP").panel('destroy');
	$("#GTCZRDIV").panel('destroy');
	$.ajax({
		url:_basePath+"/project/project!projectShowSchemeApply.action",
		data:'POB_ID='+$(param).val(),
		dataType:"html",
		success:function(html){
			$("#showScheme").html(html);
			//alert(111);
			//modify  by lishuo 12.17.2015 客户保证金比例 ,其他费用(03-15-2016)  Start
			/*2016年5月17日 17:45:48 吴国伟*/
//			if($("#DEPOSIT_PERCENT")){
//				$("#DEPOSIT_PERCENT").val($("#DEPOSIT_PERCENT_VAL").val());
//			}
			/***/
			if($("#QITA_MONEY_VAL")){
				$("#QITA_MONEY").val($("#QITA_MONEY_VAL").val());
				$("#Dead_QITA_MONEY_VAL").val($("#QITA_MONEY_VAL").val());//其他费用恒定值（从scheme取值）
			}
			//modify  by lishuo 12.17.2015 客户保证金比例  ,其他费用(03-15-2016)  End
			$("#LEASE_TOPRIC").val($("#LEASE_TOPRIC_ZD").val());
			$("#LEASE_TOPRIC1").val($("#LEASE_TOPRIC_ZD").val());
			$("input[name='FIRSTPAYDATE']").datebox();
			$("#operateDiv").show();
			//获取客户自付价格
			//add by lishuo 03-29-2016  12W校验 Start 
			var policyStartTime =$("#KSQX_DATE").val();
			var begin=new Date($("#KSQX_DATE").val().replace(/-/g,"/"));
			var end=new Date("2016-02-02".replace(/-/g,"/"));
			if(end-begin>0){//2016-02-02之前的产品，启动12W校验
				CheckPrice();
				var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
				var QUERY_UNIT_PRICE = $("#UNIT_PRICE_HIDDEN").val();
				if($("#LEASE_TOPRIC").val()- QUERY_UNIT_PRICE >0){
					$("input[name='ACTUAL_PRICE']").val(QUERY_UNIT_PRICE);
					$("#LEASE_TOPRIC").val(QUERY_UNIT_PRICE);
				}
		     }
			var UNIT_PRICE =  parseInt($("input[name='UNIT_PRICE']").val());
		    var ACTUAL_PRICE = parseInt($("input[name='ACTUAL_PRICE']").val());
		    var customerpaymentmoney = UNIT_PRICE - ACTUAL_PRICE;
		    $("#INVESTIGATE_MONEY").val(customerpaymentmoney);
		   //add by lishuo 03-29-2016  12W校验  End
		}
	});
}
/**
 * @author lishuo
 * @param obj
 * @function:判断产品是否需开启12w校验
 * @date : 2016年3月29日16:43:05
 */
function changeTotal(obj){
	CheckPrice();
	var begin=new Date($("#KSQX_DATE").val().replace(/-/g,"/"));
	var end=new Date("2016-02-02".replace(/-/g,"/"));
	if(end-begin>0){
		var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
		var QUERY_UNIT_PRICE = $("#UNIT_PRICE_HIDDEN").val();
		if($("#LEASE_TOPRIC").val()- QUERY_UNIT_PRICE >0){
			$("input[name='ACTUAL_PRICE']").val(QUERY_UNIT_PRICE);
			$("#LEASE_TOPRIC").val(QUERY_UNIT_PRICE);
		}
	}
}

//修改方案产品方案名称修改联动
function updSchemeCode(param){
	//add by lishuo 04-05-2016 集采方案提示 Start
	if($("#SCHEME_CODE1").find("option:selected").text().indexOf('集采') > -1){
	   $.messager.alert("提示","此方案为集采方案，请注意不要修改4S店采购价!");
	}
	//add by lishuo 04-05-2016 集采方案提示 End
	var SCHEME_CODE=$("#SCHEME_CODE1").val();
	$("#SCHEME_CODE").val(SCHEME_CODE);
	
	//var SCHEME_CODEe=$("select[name='SCHEME_CODE1']").find("option:selected").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var SCHEME_ROW_NUM=$("#SCHEME_ROW_NUM").val();
	var SCHEME_ID=$("#SCHEME_ID").val();
	
	//add by lishuo 12.10.2015
	var YSZJQC ="YSZJQC";//首期租金预付月数标识
	var BZJ ='客户保证金比例';//客户保证金比例 标识
	var SQYFYS, KHBZJ,YEAR_RATE;
	var url=_basePath+"/project/project!QueryYSZJQC.action";
	var args={"SCHEME_CODE":SCHEME_CODE,"YSZJQC":YSZJQC,"BZJ":BZJ,"SCHEME_CODE":SCHEME_CODE,"PROJECT_ID":PROJECT_ID};

	$.ajax({
		url:url,
		data:args,
		dataType:"json",
		type:"post",
		success:function(data){
			if(data){
				var json = data.data;
				if(json[0] != null){
					SQYFYS=json[0].VALUE_STR;
				}
				if(json[1] !=null){
					KHBZJ=json[1].VALUE_STR;
				}
				if(json[2] !=null){
					YEAR_RATE=json[2].YEAR_RATE;
				}
			}
		}
	});
	//alert("下拉变动"+PROJECT_ID+"----"+SCHEME_ROW_NUM+"---"+SCHEME_ID);
	$.ajax({
		url:_basePath+"/project/project!ProductUpdate.action",
		data:'POB_ID='+$(param).val()+'&PROJECT_ID='+PROJECT_ID+'&SCHEME_ROW_NUM='+SCHEME_ROW_NUM+'&SCHEME_ID='+SCHEME_ID,
		type:'POST',
		dataType:"json",
		success:function(json){
			var myobj = json.data;
			//客户保证金，首期租金预付月数赋值
			$("#YSZJQC").val(SQYFYS);
			if(KHBZJ != null){
				$("#DEPOSIT_PERCENT").val(KHBZJ);
			}
			if(YEAR_RATE > 0){
				$("#YEAR_INTEREST").val(YEAR_RATE);
			}
			console.log(myobj); 
			
			for(var i=0;i<myobj.length;i++){
				
				for(var x=0;x<myobj[i].productlist.length;x++){
					/*2016年4月14日 18:18:10  吴国伟 金融新算法涉及支付方式的修改  start*/
					if(myobj[i].productlist[x].KEY_NAME_ZN=='收益率标准'){
						var syl = $("#"+myobj[i].productlist[x].KEY_NAME_EN);
						var p2p_id = $("#P2P_ID").html();
							if(syl.val() == undefined){ //判断是否存在收益率标准
								if(!(p2p_id.indexOf("收益率标准")>=0)){//判断P2P_ID是否存在收益率标准 防止重复添加
									var str ="<td  class=\"text_right\">收益率标准</td>"+
						            "<td><input name=\"SCHEME_SYL_BZ_VALUE\" id=\"SCHEME_SYL_BZ_VALUE\" style=\"width:70px;\" value="+myobj[i].productlist[x].VALUE_STR+"  type=\"text\" disabled=\"disabled\"/></td>";
						            $("#P2P_ID").append(str);
								}
								}else{
								$("#SCHEME_SYL_BZ_VALUE").val(myobj[i].productlist[x].VALUE_STR);
							}
						
					}
					
					
					if(myobj[i].productlist[x].KEY_NAME_EN=='PAY_WAY'){
						/*是否显示合同融资额和总服务费  2016年4月15日 11:23:14 吴国伟 satrt*/
							if(myobj[i].productlist[x].VALUE_STR=='平息法'){
								$("#P2P_ID").css("display","none");
							}else{
								$("#P2P_ID").css("display","");
							}
						/*-------------------------------------------------------end*/
							
						var se = $("select[sid='"+myobj[i].productlist[x].KEY_NAME_ZN+"']");
						if(se ==undefined) continue;
						se.html('');
						if(myobj[i].productlist[x].VALUE_STR=='平息法') {
							se.append("<option value='7' selected>"+myobj[i].productlist[x].VALUE_STR+"</option>");
						}else if(myobj[i].productlist[x].VALUE_STR=='捷众拆分法') {
							se.append("<option value='8' selected>"+myobj[i].productlist[x].VALUE_STR+"</option>");
						}else{
							se.append("<option value='7' selected>平息法</option>");
						}
					}
					/*---------------------------------------------------------end*/
					if("START_PERCENT"==myobj[i].productlist[x].KEY_NAME_EN){
						$("#START_PERCENT").val(myobj[i].productlist[x].VALUE_STR);
						$("#START_PERCENT").attr("calculate",myobj[i].productlist[x].CALCULATE);
						getYearRate();//选择产品，自动变动首期租金比例、首期租金
						continue;
					}
					//add by lishuo 03-16-2016 修改页面五菱产品换方案带出其他费用 Start
					if("QITA_MONEY"==myobj[i].productlist[x].KEY_NAME_EN){
						$("#QITA_MONEY_MODIFY_VAL").val(myobj[i].productlist[x].VALUE_STR);
						getYearRate(2);//修改页面自动计算 其他费用
					}
					//add by lishuo 03-16-2016 修改页面五菱产品换方案带出其他费用 End
					
					//add by lishuo 03-1-2016 修改页面五菱产品显示其他费用，其余产品其他费用为空 Start
					if("QITA_MONEY"==myobj[i].productlist[x].KEY_NAME_EN && SCHEME_CODE.indexOf('月租') == -1){
						$("#QITA_MONEY").val(myobj[i].productlist[x].VALUE_STR);//原先为""，意思是其他产品的其他费用值值空，需求定其他产品的其他费用会有值
					}
					//add by lishuo 03-1-2016 End
					
					//add by lishuo 03-30-2016 修改页面带出产品政策时间,并根据换的产品进行金额控制 Start
					if("KSQX_DATE"==myobj[i].productlist[x].KEY_NAME_EN){
					 if(myobj[i].productlist[x].VALUE_STR === null || myobj[i].productlist[x].VALUE_STR === '' || myobj[i].productlist[x].VALUE_STR === undefined){//产品无政策开始时间
						$("input[name='ACTUAL_PRICE']").val($("input[name='UNIT_PRICE']").val());
						$("#LEASE_TOPRIC").val($("input[name='UNIT_PRICE']").val());
						$("#INVESTIGATE_MONEY").val(0);
					  }
						$("#KSQX_DATE_MODIFY_VAL").val(myobj[i].productlist[x].VALUE_STR);
						if($("#KSQX_DATE_MODIFY_VAL").val().length > 0){//有产品政策启动时间
							var begin=new Date($("#KSQX_DATE_MODIFY_VAL").val().replace(/-/g,"/"));
							var end=new Date("2016-02-02".replace(/-/g,"/"));
							if(end-begin>0){//2016-02-02之前的产品，启动12w校验
								CheckPrice();
								var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
								var QUERY_UNIT_PRICE = $("#UNIT_PRICE_HIDDEN").val();
								if(UNIT_PRICE- QUERY_UNIT_PRICE >0){
									$("input[name='ACTUAL_PRICE']").val(QUERY_UNIT_PRICE);
									$("#LEASE_TOPRIC").val(QUERY_UNIT_PRICE);
								}
								var UNIT_PRICE =  parseInt($("input[name='UNIT_PRICE']").val());
							    var ACTUAL_PRICE = parseInt($("input[name='ACTUAL_PRICE']").val());
							    var customerpaymentmoney = UNIT_PRICE - ACTUAL_PRICE;
							    $("#INVESTIGATE_MONEY").val(customerpaymentmoney);
							}else{//修改页换产品政策时间放开,并根据换的产品进行金额控制
								$("input[name='ACTUAL_PRICE']").val($("input[name='UNIT_PRICE']").val());
								$("#LEASE_TOPRIC").val($("input[name='UNIT_PRICE']").val());
								$("#INVESTIGATE_MONEY").val(0);
							}
						}
					}
					//add by lishuo 03-30-2016 修改页面带出产品政策时间  End
					
					
					var se = $("select[sid='"+myobj[i].productlist[x].KEY_NAME_EN+"']");
					if(se ==undefined) continue;
					var op = se.find("option[value='"+myobj[i].productlist[x].FYGS+"']");
					if(op ==undefined) continue;
					op.attr("selected","selected");
					se.change();
				}
			} 
		}
	});
}
function checkBank(bank_account){
	jQuery.ajax({
		url: _basePath+'/project/project!checkBank.action',
		data: 'BANK_ACCOUNT='+bank_account,
		dataType: 'json',
		success: function(data){
			if(data.flag==false){
				alert('账户已重复！');
				$('#ACCOUNT_COUNT').val('');
			}
		}
	});
}


function getEqList1(){
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	var Eq=[];
	 $(".eq_body_tr").each(function(){
			 if($(this).children().children().next().attr("checked")=="checked"){
				var temp={};
				temp.SCHEME_ID=$("#SCHEME_CODE").val();
				var CERTIFICATE_NUM = $(this).find("input[name='CERTIFICATE_NUM']") ;
				var ENGINE_CODE = $(this).find("input[name='ENGINE_CODE']") ;
				if((PLATFORM_TYPE=='4' && LEASE_MODEL=='back_leasing'))//标准型售后回租
				{
					
					var COMPANY_NAME = $(this).find("input[name='COMPANY_NAME']");
					if ($(COMPANY_NAME).val() == "" || $(COMPANY_NAME).val() == null || $(COMPANY_NAME).val() == undefined) {
						flag = false;
						$(COMPANY_NAME).focus();
						$.messager.alert("提示","请填写厂商");
						return;
					};
					
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == null || $(CERTIFICATE_NUM).val() == undefined) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						$.messager.alert("提示","请填写合格证号");
//						return;
//					};
					
					
					temp.COMPANY_NAME =$(this).find("input[name='COMPANY_NAME']").val();
					
					var SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']");
					if ($(SUPPLIERS_NAME).val() == "" || $(SUPPLIERS_NAME).val() == null || $(SUPPLIERS_NAME).val() == undefined) {
						flag = false;
						$(SUPPLIERS_NAME).focus();
						$.messager.alert("提示","请填写供应商");
						return;
					};
					temp.SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']").val();
					
					//得到设备名称
					var THING_NAME = $(this).find("input[name='THING_NAME']");
					if ($(THING_NAME).val() == "" || $(THING_NAME).val() == null || $(THING_NAME).val() == undefined) {
						flag = false;
						$(THING_NAME).focus();
						$.messager.alert("提示","请填写设备名称");
						return;
					};
					temp.PRODUCT_NAME =  $(this).find("input[name='THING_NAME']").val();
					
					//得到设备型号
					var eqCatena = $(this).find("input[name='PRODUCT_CATENA']");
					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
						flag = false;
						$(eqCatena).focus();
						$.messager.alert("提示","请填写设备系别");
						return;
					};
					temp.CATENA_NAME = $(this).find("input[name='PRODUCT_CATENA']").val();
					
					//得到设备型号
					var eqType = $(this).find("input[name='THING_SPEC']");
					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
						flag = false;
						$(eqType).focus();
						$.messager.alert("提示","请填写设备型号");
						return;
					};
					temp.SPEC_NAME =$(this).find("input[name='THING_SPEC']").val();
				} else if(PLATFORM_TYPE=='2')//标准型售后回租
				{
					
					var COMPANY_NAME = $(this).find("input[name='COMPANY_NAME']");
					if ($(COMPANY_NAME).val() == "" || $(COMPANY_NAME).val() == null || $(COMPANY_NAME).val() == undefined) {
						flag = false;
						$(COMPANY_NAME).focus();
						$.messager.alert("提示","请填写厂商");
						return;
					};
					
//					if ($(CERTIFICATE_NUM).val() == "" || $(CERTIFICATE_NUM).val() == null || $(CERTIFICATE_NUM).val() == undefined) {
//						flag = false;
//						$(CERTIFICATE_NUM).focus();
//						$.messager.alert("提示","请填写合格证号");
//						return;
//					};
					
					
					temp.COMPANY_NAME =$(this).find("input[name='COMPANY_NAME']").val();
					
					var SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']");
					if ($(SUPPLIERS_NAME).val() == "" || $(SUPPLIERS_NAME).val() == null || $(SUPPLIERS_NAME).val() == undefined) {
						flag = false;
						$(SUPPLIERS_NAME).focus();
						$.messager.alert("提示","请填写供应商");
						return;
					};
					temp.SUPPLIERS_NAME = $(this).find("input[name='SUPPLIERS_NAME']").val();
					
					//得到设备名称
					var THING_NAME = $(this).find("select[name='THING_NAME']");
					if ($(THING_NAME).val() == "" || $(THING_NAME).val() == null || $(THING_NAME).val() == undefined) {
						flag = false;
						$(THING_NAME).focus();
						$.messager.alert("提示","请选择设备名称");
						return;
					};
					temp.PRODUCT_NAME =  $(this).find("select[name='THING_NAME']").val();
					
//					//得到设备型号
//					var eqCatena = $(this).find("input[name='PRODUCT_CATENA']");
//					if ($(eqCatena).val() == "" || $(eqCatena).val() == null || $(eqCatena).val() == undefined) {
//						flag = false;
//						$(eqCatena).focus();
//						$.messager.alert("提示","请填写设备系别");
//						return;
//					};
					temp.CATENA_NAME = $(this).find("input[name='PRODUCT_CATENA']").val();
					
					//得到设备型号
					var eqType = $(this).find("input[name='THING_SPEC']");
					if ($(eqType).val() == "" || $(eqType).val() == null || $(eqType).val() == undefined) {
						flag = false;
						$(eqType).focus();
						$.messager.alert("提示","请填写设备型号");
						return;
					};
					temp.SPEC_NAME =$(this).find("input[name='THING_SPEC']").val();
				}
				else{
					var COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected");
					if ($(COMPANY_ID).val() == "" || $(COMPANY_ID).val() == null || $(COMPANY_ID).val() == undefined) {
						flag = false;
						$(COMPANY_ID).focus();
						$.messager.alert("提示","请选择厂商");
						return;
					};
					temp.COMPANY_ID = $(this).find("[name='COMPANY_ID']").find("option:selected").val();
					temp.COMPANY_CODE = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("CODE");
					temp.COMPANY_NAME = $(this).find("[name='COMPANY_ID']").find("option:selected").attr("test");
					
					var SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected");
					if ($(SUPPLIERS_ID).val() == "" || $(SUPPLIERS_ID).val() == null || $(SUPPLIERS_ID).val() == undefined) {
						flag = false;
						$(SUPPLIERS_ID).focus();
						$.messager.alert("提示","请选择供应商");
						return;
					};
					temp.SUPPLIERS_ID = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").val();
					temp.SUPPLIERS_NAME = $(this).find("[name='SUPPLIERS_ID']").find("option:selected").attr("test");
					
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
				}
				
				//得到合格证号
				temp.CERTIFICATE_NUM = $(CERTIFICATE_NUM).val();
				//得到发动机号/许可证号
				temp.ENGINE_CODE = $(ENGINE_CODE).val() ;
				//得到出厂编号
//				var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
				temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
				
				//得到出厂日期
//				var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
				temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
				
				//得到发动机编号
//				var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
				temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
				
				//得到车架号
//				var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
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
				temp.ACTUAL_PRICE = $(this).find("[name='ACTUAL_PRICE']").val();
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
				
				temp.BX = $(this).find("[name='BX']").val();
				temp.CCS = $(this).find("[name='CCS']").val();
				temp.PRO_REMARK = $(this).find("[name='PRO_REMARK']").val();
				
				Eq.push(temp);
			 }
		});
	 return JSON.stringify(Eq);
}

//判断是否录入收益分析
function checkEarnings(){
	var CLIENT_ID=$("#CLIENT_ID").val();
	jQuery.ajax({
		url:_basePath+"/project/Earnings!selectEarningsId.action",
		data:"CLIENT_ID="+CLIENT_ID,
		dataType:"json",
		success:function(json){
			if(json.flag==false){
				$.messager.confirm("提示",json.msg+" 是否录入?",function(tr){
					if(tr){
						return;
					}
				});
			}
		}
	});
}

function showIrr(){
	var showIrrSpan=$("#showIrrSpan").html();
	if(showIrrSpan==null||showIrrSpan==''){
		$.messager.alert("消息","暂无收益率可查看");
		return;
	}
	$.ajax({
		type:'post',
		url:_basePath + "/pay/PayTask!showIrr.action",
		dataType:'json',
		success:function(json){
			if(json.flag)
				$.messager.alert("查看","租金收益率为"+$("#showIrrSpan").html()+"%");
			else{
				alert(json.msg);
			}
		}
	});
	
}
//验证实际单价是否大于指导价
function toCheckSJDJ(obj){
	var tr=$(obj).parent().parent();
	var actual_price=$(obj).val();
	var unit_price =$(tr).find("[name='UNIT_PRICE']").val();
	if(parseFloat(unit_price)<parseFloat(actual_price)){
		alert("实际单价不能大于指导价!");
		$(obj).val(unit_price);
	}
}


///begin add by zhangzhl
//同步客户区域
function getCustomerArea(cust_id,cust_type){
	if(cust_type == "" || cust_id == ""){
		$.messager.alert("消息","无客户信息，请重试！");
		return;
	}
	var chk = $("#chk_customer_area").attr("checked");
	if(chk == "checked"){
		$.ajax({
			type:'post',
			url:_basePath + "/project/project!getCustomerArea.action",
			data : {
				CUSTOMER_TYPE : cust_type,
				CUSTOMER_ID : cust_id
			},
			dataType:'json',
			success:function(data){
				var province = "", city = "";
				if(data.WORK_ADDRESS_PROVINCE != undefined && data.WORK_ADDRESS_CITY != undefined){
					province = data.WORK_ADDRESS_PROVINCE;
					city = data.WORK_ADDRESS_CITY;
				} else if (data.HOUSE_ADDRESS_PROVINCE != undefined && data.HOUSE_ADDRESS_CITY != undefined){
					province = data.HOUSE_ADDRESS_PROVINCE;
					city = data.HOUSE_ADDRESS_CITY;
				}
				if(province != "" && city != ""){
					$("#PROJECT_AREA").val(province);
					$.ajax({
						url:_basePath+"/project/project!getFilSystemCity.action?AREA_ID="+province,
						type:"post",
						dataType:"json",
						success:function (dt){
							//将本行的输入框初始化
							$("#PROJECT_CITY").each(function (){
								//初始化
								if ($(this).is("select")){
									$(this).empty();
									$(this).append($("<option>").val("").text("--请选择--"));
								}
							});
							for(var i=0;i<dt.length;i++){
								$("#PROJECT_CITY").append($("<option value='"+dt[i].ID+"'>"+dt[i].NAME+"</option>"));	
							}
							$("#PROJECT_CITY").val(city);
						}
					});
					$("#PROJECT_AREA").attr("disabled","disabled");
					$("#PROJECT_CITY").attr("disabled","disabled");
					BusPol();	//执行onchange()中的另一个方法
				}
			},
			error : function(){
				$.messager.alert("消息","获取客户地址失败！");
			}
		});
	} else {
		$("#PROJECT_AREA").attr("disabled",false);
		$("#PROJECT_CITY").attr("disabled",false);
	}
}

//end add by zhangzhl 


function ShowInfo(){
	$.messager.alert("消息","暂不支持方案删除！");
}


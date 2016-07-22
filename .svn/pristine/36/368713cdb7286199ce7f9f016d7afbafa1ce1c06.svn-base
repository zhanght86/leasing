$(function(){ 
	var type =  $("#TYPE").attr("selected",true).val();
	if(type == "LP"){
		$.ajax({
			url:_basePath+"/customers/custFinance!toViewFinance.action?CLIENT_ID="+$("#CLIENT_ID").val(),
			type:"post",
			dataType:"json",
			success:function(json){
				if(json.data != null){
					$("#KJHJ_MONEY").val(json.data.KJHJ_MONEY);
					$("#DWDB_MONEY").val(json.data.DWDB_MONEY);
					$("#ZCZE_MONEY").val(json.data.ZCZE_MONEY);
					$("#LDZC_MONEY").val(json.data.LDZC_MONEY);
					$("#GDZC_MONEY").val(json.data.GDZC_MONEY);
					$("#FZ_MONEY").val(json.data.FZ_MONEY);
					$("#LDFZ_MONEY").val(json.data.LDFZ_MONEY);
					$("#CQFZ_MONEY").val(json.data.CQFZ_MONEY);
					$("#ZCFZ_RATE").val(json.data.ZCFZ_RATE);
					$("#LDBL_RATE").val(json.data.LDBL_RATE);
					$("#JZCSYL").val(json.data.JZCSYL);
					$("#SNYYSR").val(json.data.SNYYSR);
					$("#SNYYLR").val(json.data.SNYYLR);
					$("#YYLRL").val(json.data.YYLRL);
					
					$("#YYSRZZL").val(json.data.YYSRZZL);
					$("#YYLRZZL").val(json.data.YYLRZZL);
					$("#ZZCBCL").val(json.data.ZZCBCL);
					$("#FINANCE_ID").val(json.data.FINANACE_ID);
				}
			}
		});
	}
});

/**
 * 证件号码验证
 * @param cardId
 * @return
 */
function checkCardNo(cardId){
	var cardNo = $("#"+cardId).val();
	var id_card_type = $("#ID_CARD_TYPE").attr("selected",true).val();
	if(id_card_type == "1"){
		if(isCardNo(cardId,id_card_type)){			
			$.ajax({
				type:"post",
				url:_basePath + "/customers/Customers!checkIdCardNo.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type,
				dataType:"json",
				success:function(json){
					if(json.data){
						alert("证件号已存在，请重新填写！");
						$("#"+cardId).val("");
					}
				}
			});
		}
	}else {
		jQuery.ajax({
			type:"post",
			url:_basePath + "/customers/Customers!checkIdCardNo.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type,
			dataType:"json",
			success:function(json){
				if(json.data){
					alert("证件号已存在，请重新填写！");
					$("#"+cardId).val("");
				}
			}
		});
	}
}

/**
 * 组织机构代码证验证
 * @param oragnizationCodeId
 * @return
 */
function checkOragnizationCode(oragnizationCodeId){
	var ORAGNIZATION_CODE = $("#"+oragnizationCodeId).val();
	jQuery.ajax({
		type:"post",
		url:_basePath + "/customers/Customers!oragnizationCode.action?ORAGNIZATION_CODE="+ORAGNIZATION_CODE,
		dataType:"json",
		success:function(json){
			if(json.flag){
				alert("组织机构代码证号已存在，请重新填写！");
				$("#"+oragnizationCodeId).val("");
			}
		}
	});	
}
/**
 * 更改客户类型
 * @param val
 * @return
 */
function choseCustType(val){
	window.location.href="../customers/Customers!toAddCust.action?CUST_TYPE="+val;
}

/**
 * 根据省的id获取市
 * @param val
 * @return
 */
function getCity(val){
	jQuery.ajax({
		url:"../customers/Customers!getCity.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				//将本行的输入框初始化
				$("#PROJECT_CITY").each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.length;i++){
					$("#PROJECT_CITY").append($("<option value='"+json[i].ID+"'>"+json[i].NAME+"</option>"));				
				}
		}
	});
}

/**
 * 保存客户信息
 * 
 * @return
 */
function submitForm() {	
	if (checking()) {
		var ID_CARD_NO = "";
		if ($("#ID_CARD_NO").val() != undefined) {
			ID_CARD_NO = $("#ID_CARD_NO").val();
		}
		var CORP_BUSINESS_LICENSE = "";
		if ($("#CORP_BUSINESS_LICENSE").val() != undefined) {
				CORP_BUSINESS_LICENSE = $("#CORP_BUSINESS_LICENSE").val();
		}
			jQuery.ajax({
				url: "../customers/Customers!toJudgeIsCust.action?CUST_NAME=" +
				encodeURI($("#CUST_NAME").val()) +
				"&ID_CARD_NO=" +
				encodeURI(ID_CARD_NO) +
				"&CORP_BUSINESS_LICENSE=" +
				encodeURI(CORP_BUSINESS_LICENSE),
				type: 'post',
				dataType: "json",
				success: function(data){
					if (data.flag == false) {
						return alert("该客户已存在！！");
					}
					else {
						$("#doSaveCust").attr("disabled", true);
						jQuery.ajax({
							url: _basePath + '/customers/Customers!doAddCust.action',
							data: "param=" +
							getCustData('custSave'),
							dataType: "json",
							success: function(data){
								if (data.flag == false) {
									alert("新增客户失败");
									window.location.href = _basePath + '/customers/Customers!findCustomersPage.action';
								}
								else {
									alert("新增客户成功");
									top.closeTab("添加客户明细");
									top.addTab("修改客户信息", _basePath
											+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="
											+ data.msg + "&TYPE=" + $("#TYPE").attr("selected", true).val() + "&tab=update"+"&date="+new Date().getTime());
								}
							}
						});
					}
				}
			});
	} else {
		alert("请填写必填项目");
	}
}



/**
 * 修改客户
 * @author yx
 * @date 2013-09-03
 * @return
 */
function updateFormNu() {
	$("#updateSaveNu").linkbutton("disable");
	if (checking()){
		jQuery.ajax({
			type:"post",
			url : _basePath + "/credit/CreditCustomer!toUpdateCustInfo.action",
			data : "param=" + getCustData('updateCust') + "&CLIENT_ID="
					+ $("#CLIENT_ID").val(),
			dataType : "json",
			success : function(result) {
				if (result.flag == false) {
					alert("修改失败");
				} else {
					alert("修改成功");
				}
				$("#updateSaveNu").linkbutton("enable");
			}
		});
	}else {
		alert("请填写必填写必填选项！");
		$("#updateSaveNu").linkbutton("enable");
	}
}


/**
 * 修改客户
 * @author yx
 * @date 2013-09-03
 * @return
 */
function updateForm() {
	$("#updateSave").attr("disabled", true);
	if (checking()) {
		$("#doSaveCust").attr("disabled",true);
		jQuery.ajax( {
			url : _basePath + '/credit/CreditCustomer!toUpdateCustInfo.action',
			data : "param=" + getCustData('updateCust') + "&CLIENT_ID="
					+ $("#CLIENT_ID").val(),
			dataType : "json",
			success : function(result) {
				if (result.flag == false) {
					$.messager.alert("提示",result.msg);
				} else {
					$.messager.alert("提示",result.msg);
//					alert("修改客户成功");
				}
			}
		});
	}else {
		alert("请填写必填写必填选项！");
	}
}

/**
 * 判断配偶情况
 * 
 * @param val
 * @return
 */
function choustSpoust(val) {
	if (val == "1Marriage" || val == "4Marriage" || val == "4" || val == "1") {
		$("#marriage_type").attr("display", "");
		$("#marriage_type").attr("style", "diaplay:block");
	} else {
		$("#marriage_type").attr("style", "display:none");
	}
}

/**
 * 必填项校验 个人 杨雪 2013-08-30
 * @return
 */
function checking() {
	var type = $("#TYPE").attr("selected", true).val();
	var flag = true;
	if (type == "NP") {
		$(".warm").each(function() {
			if ($(this).val() == '' || $(this).val() == null) {
				$(this).addClass("red");
				flag = false;
			} else {
				$(this).removeClass("red");
			}
		});
		
		$(".warmlegalDate").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				$(this).addClass("red");
				flag = false;
			}else {
				$(this).removeClass("red");
			}
			
		});
	} else {
		$(".warmlegal").each(function() {
			if ($(this).val() == '' || $(this).val() == null ) {
				$(this).addClass("red");
				flag = false;
			} else {
				$(this).removeClass("red");
			}
		});
		$(".warmlegalDate").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				$(this).addClass("red");
				flag = false;
			}else {
				$(this).removeClass("red");
			}
			
		});
	}
	return flag;
}

/**
 * 获取页面参数
 * @author 杨雪
 * @date 2013-09-03
 * @param save
 * @returnF
 */
function getCustData(save) {
	var getCustData = new Array();
	var TYPE = $("select[name=TYPE]").attr("selected","selected").val();
	var IS_MARRY = $("select[name=IS_MARRY]").attr("selected","selected").val();
	
	
	$("#"+save).each(function(){
		var temp = {};
		temp.CUST_NAME = $(this).find($("input[name=CUST_NAME]")).val();
		temp.CREDIT_ID = $(this).find($("input[name=CREDIT_ID]")).val();
		temp.MAIL_ADDRESS=$(this).find($("input[name=MAIL_ADDRESS]")).val();
		temp.TYPE = TYPE;
		if(TYPE == "NP"){
			var xqah_list = [];
			var xg_list = [];
			$("[name = XQAH]:checkbox").each(function(){
				if($(this).is(":checked")){
					var temp1={};
					temp1.CODE=$(this).attr("value");
					temp1.TYPE='XQAH';
					xqah_list.push(temp1);
				}
			});
			$("[name = XG]:checkbox").each(function(){
				if($(this).is(":checked")){
					var temp2={};
					temp2.CODE=$(this).attr("value");
					temp2.TYPE='XG';
					xg_list.push(temp2);
				}
			});
		temp.xqah_list = xqah_list;
		temp.xg_list = xg_list;
		temp.ID_CARD_TYPE = $(this).find($("select[name=ID_CARD_TYPE]")).attr("selected",true).val();
		temp.ID_CARD_NO = $(this).find($("input[name=ID_CARD_NO]")).val();
		temp.BIRTHDAY = $(this).find($("input[name=BIRTHDAY]")).val();
		temp.TEL_PHONE = $(this).find($("input[name=TEL_PHONE]")).val();
		temp.POST = $(this).find($("input[name=POST]")).val();
		temp.PHONE = $(this).find($("input[name=PHONE]")).val();
		temp.SEX = $(this).find($("select[name=SEX]")).attr("selected",true).val();
		temp.DEGREE_EDU = $(this).find($("select[name=DEGREE_EDU]")).attr("selected",true).val();
		temp.IS_MARRY = $(this).find($("select[name=IS_MARRY]")).attr("selected",true).val();
		temp.NATION = $(this).find($("select[name=NATION]")).attr("selected",true).val();
		temp.IS_CHILDRED = $(this).find($("select[name=IS_CHILDRED]")).attr("selected",true).val();
		temp.PROVINCE = $(this).find($("select[name=PROVINCE]")).attr("selected",true).val();
		temp.CITY = $(this).find($("select[name=PROJECT_CITY]")).attr("selected",true).val();
		temp.WORK_UNIT = $(this).find($("input[name=WORK_UNIT]")).val();
		temp.COMPANY_PROPERTY = $(this).find($("select[name=COMPANY_PROPERTY]")).attr("selected",true).val();
		temp.ENTRY_TIME = $(this).find($("input[name=ENTRY_TIME]")).attr("selected",true).val();
		temp.POSITION = $(this).find($("input[name=POSITION]")).val();
		temp.FAX = $(this).find($("input[name=FAX]")).val();
		temp.COMPANY_ADDR = $(this).find($("input[name=COMPANY_ADDR]")).val();
		temp.HOUSE_ADDRESS = $(this).find($("input[name=HOUSE_ADDRESS]")).val();
		temp.HOUSR_RE_ADDRESS = $(this).find($("input[name=HOUSR_RE_ADDRESS]")).val();
		temp.TAX_QUALIFICATION = $(this).find($("select[name=TAX_QUALIFICATION]")).attr("selected",true).val();
		temp.INDUSTRY_FICATION = $("select[name=INDUSTRY_FICATION]").attr("selected",true).val();
		temp.REMARK = $(this).find($("textarea[name=REMARK]")).val();
		
		temp.USER_YEAR = $(this).find($("input[name=USER_YEAR]")).val();
		temp.PHYSICAL_STATE = $(this).find($("select[name=PHYSICAL_STATE]")).attr("selected",true).val();
		
		if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
			temp.SPOUST_NAME = $(this).find($("input[name=SPOUST_NAME]")).val();
			temp.SPOUST_SEX = $(this).find($("select[name=SPOUST_SEX]")).attr("selected",true).val();
			temp.SPOUST_BIRTHDAY = $(this).find($("input[name=SPOUST_BIRTHDAY]")).val();
			temp.SPOUST_NATION = $(this).find($("select[name=SPOUST_NATION]")).val();
			temp.SPOUDT_ID_CARD_NO = $(this).find($("input[name=SPOUDT_ID_CARD_NO]")).val();
			temp.SPOUDT_TEL_PHONE = $(this).find($("input[name=SPOUDT_TEL_PHONE]")).val();
			temp.SPOUST_HOUSR_RE_ADDRESS = $(this).find($("input[name=SPOUST_HOUSR_RE_ADDRESS]")).val();
			temp.SPOUST_WORK_UNIT = $(this).find($("input[name=SPOUST_WORK_UNIT]")).val();
			temp.SPOUST_COMPANY_PROPERTY = $(this).find($("select[name=SPOUST_COMPANY_PROPERTY]")).attr("selected",true).val();
			temp.SPOUST_POSITION = $(this).find($("input[name=SPOUST_POSITION]")).val();
			temp.SPOUST_WORK_PHONE = $(this).find($("input[name=SPOUST_WORK_PHONE]")).val();
			temp.SPOUST_FAX = $(this).find($("input[name=SPOUST_FAX]")).val();
			temp.SPOUST_COMPANY_ADDR = $(this).find($("input[name=SPOUST_COMPANY_ADDR]")).val();
			temp.SPOUST_DEGREE_EDU = $(this).find($("select[name=SPOUST_DEGREE_EDU]")).attr("selected",true).val();		
			temp.SPOUST_PHYSICAL_STATE = $(this).find($("select[name=SPOUST_PHYSICAL_STATE]")).attr("selected",true).val();	
		}
		
		}else{
			temp.BUSINESS_TYPE = $(this).find($("select[name=BUSINESS_TYPE]")).attr("selected",true).val();
			temp.CORP_BUSINESS_LICENSE = $(this).find($("input[name=CORP_BUSINESS_LICENSE]")).val();
			temp.MAIL_ADDRESS=$(this).find($("input[name=MAIL_ADDRESS]")).val();
			temp.TAX_CODE = $(this).find($("input[name=TAX_CODE]")).val();
			temp.ORAGNIZATION_CODE = $(this).find($("input[name=ORAGNIZATION_CODE]")).val();
			temp.SETUP_DATE = $(this).find($("input[name=SETUP_DATE]")).val();
			temp.PERIOD_OF_VALIDITY = $(this).find($("input[name=PERIOD_OF_VALIDITY]")).val();
			temp.REGISTE_DATE = $(this).find($("input[name=REGISTE_DATE]")).val();
			temp.REGISTE_CAPITAL = $(this).find($("input[name=REGISTE_CAPITAL]")).val();
			temp.RC_CURRENCY_TYPE=$(this).find($("select[name=RC_CURRENCY_TYPE]")).attr("selected",true).val();
			temp.POST = $(this).find($("input[name=POST]")).val();
			temp.REGISTE_PHONE = $(this).find($("input[name=REGISTE_PHONE]")).val();
			temp.FAX = $(this).find($("input[name=FAX]")).val();
			temp.NUMBER_PER = $(this).find($("input[name=NUMBER_PER]")).val();
			temp.TAX_QUALIFICATION = $(this).find($("select[name=TAX_QUALIFICATION]")).attr("selected",true).val();
			temp.RATEPAYING = $(this).find($("select[name=RATEPAYING]")).attr("selected",true).val();
			temp.REGISTE_ADDRESS = $(this).find($("input[name=REGISTE_ADDRESS]")).val();
			temp.WORK_ADDRESS = $(this).find($("input[name=WORK_ADDRESS]")).val();
			temp.IS_GUARANTEE = $(this).find($("select[name=IS_GUARANTEE]")).attr("selected",true).val();
			temp.PROVINCE = $(this).find($("select[name=PROVINCE]")).attr("selected",true).val();
			temp.CITY = $(this).find($("select[name=PROJECT_CITY]")).attr("selected",true).val();
			temp.MAIN_BUSINESS = $(this).find($("input[name=MAIN_BUSINESS]")).val();
			temp.LEGAL_PERSON = $(this).find($("input[name=LEGAL_PERSON]")).val();
			temp.LEGAL_PERSON_PHONE = $(this).find($("input[name=LEGAL_PERSON_PHONE]")).val();
			temp.REMARK = $(this).find($("textarea[name=REMARK]")).val();
			temp.SCALE_ENTERPRISE = $(this).find($("select[name=SCALE_ENTERPRISE]")).attr("selected",true).val();
			temp.INDUSTRY_FICATION = $(this).find($("select[name=INDUSTRY_FICATION]")).attr("selected",true).val();
		}
		getCustData.push(temp);
	});
	return encodeURI(JSON.stringify(getCustData));
}

/**********************************************客户开户行*************************************************************************/
function toAddBank(){
//	alert("1");
//	alert($("#bankInfo").offset().top);
	$("#bankAdd").dialog('open',{top:500,left:200});
	
	$("#doAddBank").form('clear');
	$("#BNAK_CLIENT_ID").val($("#client_id").val());
}
//
//function toSaveBank() {
//	jQuery.ajax({
//		type:"post",
//		url:_basePath+'/customers/Customers!isAccountHave.action',
//		data: "CLIENT_ID="+$("#BNAK_CLIENT_ID").val()+"&BANK_NAME="+$("#BANK_NAME_1").combobox('getValue')+"&BANK_ACCOUNT="+$("#BANK_ACCOUNT_1").val(),
//		dataType:"json",
//		success:function(json){
//			if(json.flag==true){
//				toSaveBank_();
//			}else {
//				alert("该银行帐号已存在，请重新填写");
//			}
//		}
//	});
//		
//}

function toSaveBank(){
	$("#doAddBank").form('submit',{
		url:_basePath+'/customers/Customers!doAddBank.action',
		success : function(data) {
			if (data.flag == false) {
				alert("银行信息添加失败");
				//关闭弹出框
				$("#bankAdd").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			} else {
				alert("银行信息添加成功！");
				//关闭弹出框
				$("#bankAdd").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			}
		}
	});
}

function toUpdateBank(){
	var row = $("#bankOpen").datagrid('getSelected');
	if(row){
		$.ajax({
			url:_basePath + "/customers/Customers!findBankDetailByid.action?CO_ID="+row.CO_ID+"&date="+new Date().getTime(),
			type:"post",
			dataType:"json",
			success:function(json){
				var json = eval(json.bank);
				for(var i=0; i<json.length; i++){
					$("#BANK_NAME1").val(json[i].BANK_NAME);
//					$("#BANK_NAME1").combobox('select',json[i].BANK_NAME);
					$("#BANK_ACCOUNT1").val(json[i].BANK_ACCOUNT);
					$("#BANK_ADDRESS1").val(json[i].BANK_ADDRESS);
					$("#BANK_CUSTNAME1").val(json[i].BANK_CUSTNAME);
					$("#HEAD_OFFICE1").val(json[i].HEAD_OFFICE);
					$("#FLAG1").combobox('select',json[i].FLAG);
					$("#REMARK2").val(json[i].REMARK);
					$("#CO_ID").val(json[i].CO_ID);
				}
			}
		});
		$("#bankUpdate").dialog('open');
	}else{
		alert("请选择要修改的银行信息");
	}
}

function toSaveUpdateBank(){
	$("#doUpdateBank").form('submit',{
		url:_basePath+'/customers/Customers!doUpdageLeagBank.action',
		success : function(data) {
			var json=$.parseJSON(data);
			if (json.flag == false) {
//				alert("客户开户行修改失败");
				$.messager.alert("提示",json.msg);
				//关闭弹出框
				$("#bankUpdate").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			} else {
//				alert("客户开户行修改成功");
				$.messager.alert("提示",json.msg);
				//关闭弹出框
				$("#bankUpdate").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			}
		}
	});
}


/**
 * 删除开户行
 * @return
 */
function removeit() {
	if (confirm("确认删除？")) {
		var row = $("#bankOpen").datagrid('getSelected');
		if (row) {
			jQuery.ajax( {
				url : _basePath + "/customers/Customers!delOpenBank.action",
				data : "CO_ID=" + row.CO_ID + "&CLIENT_ID=" + row.CLIENT_ID,
				dataType : "json",
				success : function(data) {
					if (data.flag == true) {
						alert("开户行删除成功");

						$('#bankOpen').datagrid('load', {
							"param" : getFromData("#bank")
						});
					} else {
						alert("开户行删除失败");

						$('#bankOpen').datagrid('load', {
							"param" : getFromData("#bank")
						});
					}
				},
				error : function(e) {
					alert(e.message);
				}
			});
		}else{
			alert("请选择要删除的银行信息");
		}
	}
}


function closeDailog(div){
	$("#"+div).dialog('close');
}

/**********************************************客户法人财报*************************************************************************/

function saveFinance(){
	if($("#FINANCE_ID").val()==""){
		var url  = _basePath+"/customers/custFinance!toAddFinance.action";
		$("#saveFinance").attr("action",url);
		$("#saveFinance").form("submit",{
			dataType:"json",
			success:function(json){
				json = jQuery.parseJSON(json);
				if(json.flag == true){
					$.messager.alert("提示", json.msg);
					window.location.href = _basePath+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="+ $("#CLIENT_ID").val() + "&TYPE=" + $("#TYPE").attr("selected",true).val() + "&tab=update";
				}else {
					$.messager.alert("提示", json.msg);
				}
			}
		});
	}else {
		var url  = _basePath+"/customers/custFinance!toUpdateFinance.action";
		$("#saveFinance").attr("action",url);
		$("#saveFinance").form("submit",{
			dataType:"json",
			success:function(json){
				json = jQuery.parseJSON(json);
				if(json.flag == true){
					$.messager.alert("提示", json.msg);
					window.location.href = _basePath+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="+ $("#CLIENT_ID").val() + "&TYPE=" + $("#TYPE").attr("selected",true).val() + "&tab=update";
				}else {
					$.messager.alert("提示", json.msg);
				}
			}
		});
	}
}
// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

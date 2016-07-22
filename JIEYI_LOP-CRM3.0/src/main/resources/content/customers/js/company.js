$(document).ready(function() {
	//总、分公司
	$(".zgs").click(function(){
		$("#SUPER_ID").attr("disabled",true);
	});
	$(".fgs").click(function(){
		$("#SUPER_ID").attr("disabled",false);
	});
	//行业全选
	$("#choose").click(function(){
		if($(this).is(":checked")){
			$("input[name='hy_id']").attr("checked", true);
		}else{
			$("input[name='hy_id']").attr("checked", false);
		}
	});
	//打开行业输入DIV
	$(".INSTRUTRY").click(function(){
		$("#addIndustry").dialog('open',{position:[220,220]});
	});
	//添加行业
	$("#doInstrutry").click(function(){
		var checkIdData = [];
		var hyName = '';
		$("[name = hy_id]:checkbox").each(function(){
			if($(this).is(":checked")){
				var temp={};
				temp.TYPE_ID=$(this).attr("value");
				temp.TYPE='行业类型';
				hyName = $(this).attr("hyValue")+","+ hyName ;
				checkIdData.push(temp);
			}
		});
		$("#FA_NAMEINSTRUTRY").empty();
		$("#FA_NAMEINSTRUTRY").val(hyName);
		$("#FA_INSTRUTRY").empty();
		$("#FA_INSTRUTRY").val(JSON.stringify(checkIdData));
		$("#FA_INSTRUTRY_ZT").empty();
		$("#FA_INSTRUTRY_ZT").val('1');
		closeDailog('addIndustry');
		
	});
	//业务类型全选
	$("#choose_business").click(function(){
		if($(this).is(":checked")){
			$("input[name='business_id']").attr("checked", true);
		}else{
			$("input[name='business_id']").attr("checked", false);
		}
	});
	//打开业务类型输入DIV
	$(".BUSINESS").click(function(){
		$("#addBusiness").dialog('open',{top:300,left:200});
	});
	//添加类型
	$("#doBusiness").click(function(){
		var checkIdData = [];
		var BusinessName = '';
		$("[name = business_id]:checkbox").each(function(){
			if($(this).is(":checked")){
				var temp={};
				temp.TYPE_ID=$(this).attr("value");
				temp.TYPE='业务类型';
				BusinessName = $(this).attr("BusinessValue")+","+ BusinessName ;
				checkIdData.push(temp);
			}
		});
		$("#FA_NAMEBUSINESS").empty();
		$("#FA_NAMEBUSINESS").val(BusinessName);
		$("#FA_BUSINESS").empty();
		$("#FA_BUSINESS").val(JSON.stringify(checkIdData));
		$("#FA_BUSINESS_ZT").empty();
		$("#FA_BUSINESS_ZT").val('1');
		closeDailog('addBusiness');
		
	});
	//厂商全选
	$("#choose_manufacturers").click(function(){
		if($(this).is(":checked")){
			$("input[name='manufacturers_id']").attr("checked", true);
		}else{
			$("input[name='manufacturers_id']").attr("checked", false);
		}
	});
	//打开厂商输入DIV
	$(".MANUFACTURERS").click(function(){
		$("#addManufacturers").dialog('open',{top:300,left:200});
	});
	//添加厂商
	$("#doManufacturers").click(function(){
		var checkIdData = [];
		var ManufacturersName = '';
		var ManufacturesID ='';
		$("[name = manufacturers_id]:checkbox").each(function(){
			if($(this).is(":checked")){
				var temp={};
				temp.TYPE_ID=$(this).attr("value");
				temp.TYPE='厂商';
				ManufacturesID = $(this).attr("value")+","+ ManufacturesID ;
				ManufacturersName = $(this).attr("ManufacturersValue")+","+ ManufacturersName ;
				checkIdData.push(temp);
			}
		});
		$("#FA_NAMEMANUFACTURERS").empty();
		$("#FA_NAMEMANUFACTURERS").val(ManufacturersName);
		$("#MANUFACTURESID").empty();
		$("#MANUFACTURESID").val(ManufacturesID);
		$("#FA_MANUFACTURERS").empty();
		$("#FA_MANUFACTURERS").val(JSON.stringify(checkIdData));
		
		$("#FA_NAMEAGENT").val('');
		$("#FA_AGENT").val('');
		$("#FA_MANUFACTURERS_ZT").empty();
		$("#FA_MANUFACTURERS_ZT").val('1');
		closeDailog('addManufacturers');
		
	});
	
	//供应商全选
	$("#choose_agent").click(function(){
		if($(this).is(":checked")){
			$("input[name='agent_id']").attr("checked", true);
		}else{
			$("input[name='agent_id']").attr("checked", false);
		}
	});
	//打开供应商输入DIV
	$(".AGENT").click(function(){
		var  MANUFACTURESID = $("#MANUFACTURESID").val();
		if(MANUFACTURESID==null || MANUFACTURESID ==''){
			alert("请选择厂商信息！");
			return;
			$("#addAgent").dialog('open',{top:300,left:200});
		}else{
			var  FA_NAMEAGENT = $("#FA_NAMEAGENT").val();
			FA_NAMEAGENT = ',' + FA_NAMEAGENT;
			$.ajax({
				url:_basePath + "/customers/CompanyManager!queryJxs.action?&MANUFACTURESID="+MANUFACTURESID,
				type:"post",
				dataType:"json",
				success:function(json){
					var list = json.jxs;
					$("#updateSup").html('');
					var dataStr='';
					for(var i=0;i<list.length;i++){
						   var SUP_ID = list[i]['SUP_ID'];
						   var SUP_NAME = list[i]['SUP_NAME'];
						   if (FA_NAMEAGENT.indexOf(','+SUP_NAME+',') >= 0) {
							   dataStr = dataStr+
								  "<tr width='100%'>"+
									"<td width='20%'><input type='checkbox' checked  id ='agent_id' name='agent_id' value='"+SUP_ID+"' AgentValue='"+SUP_NAME+"'></td>"+
					                "<td width='80%'>"+SUP_NAME+"</td>"+
								  "</tr>";
						   } else {
							   dataStr = dataStr+
								  "<tr width='100%'>"+
									"<td width='20%'><input type='checkbox'  id ='agent_id' name='agent_id' value='"+SUP_ID+"' AgentValue='"+SUP_NAME+"'></td>"+
					                "<td width='80%'>"+SUP_NAME+"</td>"+
								  "</tr>";
						   }
							   
						      
						}

					$("#updateSup").append(dataStr);
				}
			});
			$("#addAgent").dialog('open',{top:300,left:200});
		}
		
		
	});
	//添加供应商
	$("#doAgent").click(function(){
		var checkIdData = [];
		var AgentName = '';
		$("[name = agent_id]:checkbox").each(function(){
			if($(this).is(":checked")){
				var temp={};
				temp.TYPE_ID=$(this).attr("value");
				temp.TYPE='供应商';
				AgentName = $(this).attr("AgentValue")+","+ AgentName ;
				checkIdData.push(temp);
			}
		});
		$("#FA_NAMEAGENT").empty();
		$("#FA_NAMEAGENT").val(AgentName);
		$("#FA_AGENT").empty();
		$("#FA_AGENT").val(JSON.stringify(checkIdData));
		$("#FA_AGENT_ZT").empty();
		$("#FA_AGENT_ZT").val('1');
		closeDailog('addAgent');
		
	});
	//打开地址输入DIV
	$(".FA_NAMEADDRESS").click(function(){
		$("#addArea").dialog('open',{top:300,left:200});
	});
	$("#doArea").click(function(){
		var areaid=$("#AREA_ID").val();
		var areaname=$("#AREA_NAME").val();
//		if(areaid.indexOf(",,,")>=0){
		if(areaid.length==3){
			alert("选择区域不正确，最小范围为国家！");
			return;
		}else {
			$("#AREAADD").html();
			//$("#FA_NAMEADDRESS").empty();
			//$("#FA_NAMEADDRESS").val(FA_NAMEADDRESS+"|"+areaname);
			//$("#FA_ADDRESS").empty();
			//$("#FA_ADDRESS").val(FA_ADDRESS+"|"+areaid);
			var strArea = "<tr class='addareas'><td > <input type='text' name='ADDAREANAME' value='"+areaname+"' readonly  style='width:300px'/><input type='hidden' name='ADDAREAID' value='"+areaid+"'/> <a href='#' onClick='getDel(this)'>删除</a></td></tr>";
			$("#AREAADD").append(strArea);
		}
		
	});
});
function getDel(id){
	$(id).parent().remove();
}
function closeDailog(div){
	$("#"+div).dialog('close');
}

/**
 * 查询模糊
 * @author 吴国伟
 * @return
 */
function tofindData() {
	var FA_NAME = $("input[name='FA_NAME']").val();

	$('#companyTab').datagrid('load', {
		"FA_NAME" : FA_NAME
	});
}

/**
 * 客户管理-操作
 * @param val
 * @param row
 * @return
 */
function getValue(val, row) {
	
		return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewCompany("
		+ row.ID
		+ ")'>查看</a>  |  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toUpdateCompany("
		+ row.ID
		+ ")'>修改</a> ";
	
}
/**
 * 进入公司添加[页面]
 * 
 * @return
 */
function toAddCompanyInfo() {
	top.addTab("公司平台管理", _basePath
			+ "/customers/CompanyManager!toAddCompany.action?tab=update"+"&date="+new Date().getTime());
}
function toUpdateCompany(value, type) {
	top.addTab("修改公司信息", _basePath
			+ "/customers/CompanyManager!toUpdateCompany.action?ID="
			+ value +  "&tab=update"+"&date="+new Date().getTime());
}
function toViewCompany(value, type){
	top.addTab("查看公司信息", _basePath
			+ "/customers/CompanyManager!toViewCompany.action?ID="
			+ value +  "&tab=update"+"&date="+new Date().getTime());
}
/**
 * 保存信息
 * 
 * @return
 */
function submitForm1() {	
	if (checking()) {
			$("#doSave").attr("disabled", true);
			$("#COMPANYDATA").val(getCompanyData('companySave'));
			$("#companySave").submit();
//			jQuery.ajax({
//				url: _basePath + '/customers/CompanyManager!doAddCompany.action',
//				data: "param=" +
//				getCompanyData('companySave'),
//				dataType: "json",
//				success: function(data){
//					if (data.flag == true) {
//						alert("新增公司成功");
//						top.closeTab("添加公司明细");
//						top.addTab("公司平台", _basePath + '/customers/CompanyManager!findCompanyData.action');
//					
//					}
//					else{
//						alert("新增公司失败");
//						top.closeTab("添加公司明细");
//						top.addTab("公司平台", _basePath
//								+ '/customers/CompanyManager!findCompanyData.action');
//					}
//					
//				}
//			});

	} else {
		alert("请填写必填项目");
	}
}
/**
 * 必填项校验 
 * @return
 */
function checking() {
	var flag = true;
	
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
	
	return flag;
}
function submitForm() {	
if (checking()) {
	$("#doSave").linkbutton("disable");
	//$("#doSave").attr("disabled", true);
	$("#companySave").each(function(){
	var dataJson ={
		FA_LEVEL: $(this).find($("input:radio[name='FA_LEVEL'][checked]")).val(),
		SUPER_ID: $(this).find($("select[name=SUPER_ID]")).attr("selected",true).val(),
		
		FA_INSTRUTRY : $(this).find($("input[name=FA_INSTRUTRY]")).val(),
		FA_ADDRESS : $(this).find($("input[name=FA_ADDRESS]")).val(),
		FA_BUSINESS : $(this).find($("input[name=FA_BUSINESS]")).val(),
		FA_MANUFACTURERS : $(this).find($("input[name=FA_MANUFACTURERS]")).val(),
		FA_AGENT: $(this).find($("input[name=FA_AGENT]")).val(),
		
		FA_NAME : $(this).find($("input[name=FA_NAME]")).val(),
		EN_NAME : $(this).find($("input[name=EN_NAME]")).val(),
		SHORT_NAME: $(this).find($("input[name=SHORT_NAME]")).val(),
		WORKER_NUM : $(this).find($("input[name=WORKER_NUM]")).val(),
		FA_TYPE :$(this).find($("select[name=FA_TYPE]")).attr("selected",true).val(),
		TAX_NUM : $(this).find($("input[name=TAX_NUM]")).val(),
		
		BUS_LICENSE : $(this).find($("input[name=BUS_LICENSE]")).val(),
		BUS_DEADLINE : $(this).find($("input[name=BUS_DEADLINE]")).val(),
		FOUND_DATE : $(this).find($("input[name=FOUND_DATE]")).val(),
		REG_CAPITAL : $(this).find($("input[name=REG_CAPITAL]")).val(),
		RUN_SCOPE : $(this).find($("input[name=RUN_SCOPE]")).val(),
		FA_PHONE : $(this).find($("input[name=FA_PHONE]")).val(),		
		FA_ADD : $(this).find($("input[name=FA_ADD]")).val(),
		FA_POSTCODE : $(this).find($("input[name=FA_POSTCODE]")).val(),
		ORG_CODE : $(this).find($("input[name=ORG_CODE]")).val(),		
		REG_ADD : $(this).find($("input[name=REG_ADD]")).val(),
		REMARK : $("#REMARK").val(),
		LEGAL_PERSON : $(this).find($("input[name=LEGAL_PERSON]")).val(),
		LEGAL_PHONE : $(this).find($("input[name=LEGAL_PHONE]")).val(),
		LEGAL_IDECARD : $(this).find($("input[name=LEGAL_IDECARD]")).val(),
		LEGAL_CODE : $(this).find($("input[name=LEGAL_CODE]")).val(),
		LEGAL_ADD : $(this).find($("input[name=LEGAL_ADD]")).val(),
		LEGAL_MOBILE : $(this).find($("input[name=LEGAL_MOBILE]")).val(),
		
		LINKMAN : $(this).find($("input[name=LINKMAN]")).val(),
		LINKMAN_IDECARD : $(this).find($("input[name=LINKMAN_IDECARD]")).val(),
		LINKMAN_PHONE : $(this).find($("input[name=LINKMAN_PHONE]")).val(),
		LINKMAN_CODE : $(this).find($("input[name=LINKMAN_CODE]")).val(),
		LINKMAN_MOBILE : $(this).find($("input[name=LINKMAN_MOBILE]")).val(),
		LINKMAN_ADD : $(this).find($("input[name=LINKMAN_ADD]")).val(),
		
		FA_BINK : $(this).find($("input[name=FA_BINK]")).val(),
		FA_ACCOUNT : $(this).find($("input[name=FA_ACCOUNT]")).val(),
		FA_BANK_ADDRESS : $(this).find($("input[name=FA_BANK_ADDRESS]")).val()
	  };
	
		$("#COMPANYDATA").val(JSON.stringify(dataJson));
		
	});	
		$("#companyFrom").submit();
	} else {
		alert("请填写必填项目");
	}

	
}
function submitFormUpdate(){
	
	
		
		var addareas =[];
		$(".addareas").each(function(){
			var item={};
			item.ADDAREANAME = $(this).find($("input[name=ADDAREANAME]")).val();
			item.ADDAREAID = $(this).find($("input[name=ADDAREAID]")).val();
			addareas.push(item);
		});
		$("#FA_NAMEADDRESS").val(addareas);
		
		//处理银行信息
		var bankInfo =[];
		
		var bankTemp={};
		bankTemp.FA_BINK=$("input[name='FA_BINK']").val();
		bankTemp.FA_ACCOUNT=$("input[name='FA_ACCOUNT']").val();
		bankTemp.FA_BANK_ADDRESS=$("input[name='FA_BANK_ADDRESS']").val();
		bankTemp.FLAG=1;
		bankTemp.MANAGER_ID=$("input[name='ID']").val();
		bankInfo.push(bankTemp);
		
		$(".eq_body_tr").each(function(){
			var temp={};
			temp.FA_BINK=$(this).find("input[name='FA_BINK_PT']").val();
			temp.FA_ACCOUNT=$(this).find("input[name='FA_ACCOUNT_PT']").val();
			temp.FA_BANK_ADDRESS=$(this).find("input[name='FA_BANK_ADDRESS_PT']").val();
			temp.FLAG=2;
			temp.MANAGER_ID=$("input[name='ID']").val();
			temp.FA_BANK_TYPE=$("#FA_BANK_TYPE").val();
			temp.SON_COMPANY_ID=$(this).find("select[name='SON_COMPANY_ID']").val();$("#").val();
			bankInfo.push(temp);
		});
		
		
		
		if (checking()) {
		$("#doUpdate").linkbutton("disable");
		//$("#doUpdate").attr("disabled", true);
		$("#companyUpdate").each(function(){
		var dataJson ={
			//FA_LEVEL: $(this).find($("input[name=FA_LEVEL][checked]")).val(),
			//SUPER_ID: $(this).find($("select[name=SUPER_ID]")).attr("selected",true).val(),
			ID: $(this).find($("input[name=ID]")).val(),
			FA_INSTRUTRY : $(this).find($("input[name=FA_INSTRUTRY]")).val(),
			FA_INSTRUTRY_ZT : $(this).find($("input[name=FA_INSTRUTRY_ZT]")).val(),
			FA_NAMEADDRESS : addareas,
			//FA_INSTRUTRY_ZT : $(this).find($("input[name=FA_INSTRUTRY_ZT]")).val(),
			FA_BUSINESS : $(this).find($("input[name=FA_BUSINESS]")).val(),
			FA_BUSINESS_ZT : $(this).find($("input[name=FA_BUSINESS_ZT]")).val(),
			FA_MANUFACTURERS : $(this).find($("input[name=FA_MANUFACTURERS]")).val(),
			FA_MANUFACTURERS_ZT : $(this).find($("input[name=FA_MANUFACTURERS_ZT]")).val(),
			FA_AGENT: $(this).find($("input[name=FA_AGENT]")).val(),
			FA_AGENT_ZT : $(this).find($("input[name=FA_AGENT_ZT]")).val(),
			
			//FA_NAME : $(this).find($("input[name=FA_NAME]")).val(),
			EN_NAME : $(this).find($("input[name=EN_NAME]")).val(),
			SHORT_NAME: $(this).find($("input[name=SHORT_NAME]")).val(),
			WORKER_NUM : $(this).find($("input[name=WORKER_NUM]")).val(),
			FA_TYPE :$(this).find($("select[name=FA_TYPE]")).attr("selected",true).val(),
			TAX_NUM : $(this).find($("input[name=TAX_NUM]")).val(),
			
			BUS_LICENSE : $(this).find($("input[name=BUS_LICENSE]")).val(),
			BUS_DEADLINE : $(this).find($("input[name=BUS_DEADLINE]")).val(),
			FOUND_DATE : $(this).find($("input[name=FOUND_DATE]")).val(),
			REG_CAPITAL : $(this).find($("input[name=REG_CAPITAL]")).val(),
			RUN_SCOPE : $(this).find($("input[name=RUN_SCOPE]")).val(),
			FA_PHONE : $(this).find($("input[name=FA_PHONE]")).val(),		
			FA_ADD : $(this).find($("input[name=FA_ADD]")).val(),
			FA_POSTCODE : $(this).find($("input[name=FA_POSTCODE]")).val(),
			ORG_CODE : $(this).find($("input[name=ORG_CODE]")).val(),		
			REG_ADD : $(this).find($("input[name=REG_ADD]")).val(),
			REMARK : $("#REMARK").val(),
			LEGAL_PERSON : $(this).find($("input[name=LEGAL_PERSON]")).val(),
			LEGAL_PHONE : $(this).find($("input[name=LEGAL_PHONE]")).val(),
			LEGAL_IDECARD : $(this).find($("input[name=LEGAL_IDECARD]")).val(),
			LEGAL_CODE : $(this).find($("input[name=LEGAL_CODE]")).val(),
			LEGAL_ADD : $(this).find($("input[name=LEGAL_ADD]")).val(),
			LEGAL_MOBILE : $(this).find($("input[name=LEGAL_MOBILE]")).val(),
			
			LINKMAN : $(this).find($("input[name=LINKMAN]")).val(),
			LINKMAN_IDECARD : $(this).find($("input[name=LINKMAN_IDECARD]")).val(),
			LINKMAN_PHONE : $(this).find($("input[name=LINKMAN_PHONE]")).val(),
			LINKMAN_CODE : $(this).find($("input[name=LINKMAN_CODE]")).val(),
			LINKMAN_MOBILE : $(this).find($("input[name=LINKMAN_MOBILE]")).val(),
			LINKMAN_ADD : $(this).find($("input[name=LINKMAN_ADD]")).val(),
			
			FA_BINK : $(this).find($("input[name=FA_BINK]")).val(),
			FA_ACCOUNT : $(this).find($("input[name=FA_ACCOUNT]")).val(),
			FA_BANK_ADDRESS : $(this).find($("input[name=FA_BANK_ADDRESS]")).val(),
			bankInfo:bankInfo
		  };
		
			$("#COMPANYDATA").val(JSON.stringify(dataJson));
			
		});	
			$("#companyFromUpdate").submit();
		} else {
			alert("请填写必填项目");
		}
}


function copyTr(fromId,toId)
{
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","eq_body_tr");
	$("#"+toId).append(tr);
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
		}
	});
}
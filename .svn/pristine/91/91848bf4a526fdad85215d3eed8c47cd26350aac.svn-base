$(document).ready(function(){
	$("#checkAll").click(function() {
		var flag = $("#checkAll").attr("checked");
		if (flag == "checked") {
			$(".checkId").each(function() {
				if ($(this).attr("disabled") != "disabled") {
					$(this).attr("checked", true);
				}
			});
		} else {
			$(".checkId").each(function() {
				$(this).attr("checked", false);
			});
		}
	});
	$('#dlgUp').dialog('close');
	$("#pageTable").datagrid({
		url:"Complement!pageAjax.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		toolbar:'#pageForm',
        frozenColumns:[[
        {field:'aaa',title:'操作',width:250,align:'center',formatter:function(value,row,rowIndex){
		  var STATUS=row.STATUS;
		 //申请 <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='buqi(" + JSON.stringify(row) + ")'>变更</a> | 
		 //变更<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateCom(" + JSON.stringify(row) + ")'>变更</a> | 
		 //延期转DB保证金<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateEndDate(" + JSON.stringify(row) + ")'>变更</a> | 
		 if(STATUS==1 || row.DATE_COMPLEMENT==1)//已补齐（可以做变更）
		 {
			 if(row.UPDATE_TYPE==1){
				return "变更流程中";
			 }
			 else{
				return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateCom(" + JSON.stringify(row) + ")'>变更</a>";
			 }
			
		 }
		 else if(STATUS==2)//转保证金（可以做变更）
		 {
			if(row.UPDATE_TYPE==1){
				return "变更流程中";
			 }
			 else{
				return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateCom(" + JSON.stringify(row) + ")'>变更</a>";
			 }
		 }
 	 else if(STATUS==3)//未到期未补齐
 	 {
 		var str="";
 		 if(row.COM_TYPE==1){
 				str= "补齐流程中 | ";
 		}
 		else{
 			str=" <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='buqi(" + JSON.stringify(row) + ")'>资料补齐</a> | ";
 		}
 		 
 		if(row.UPDATE_TYPE==1){
				str=str+ "变更流程中 | ";
			 }
			 else{
				str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateCom(" + JSON.stringify(row) + ")'>变更</a> | ";
			 }
 		str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateEndDate(" + JSON.stringify(row) + ")'>延期</a>";
 		return str;
		 }
 	 else if(STATUS==4)//已到期未补齐
 	 {
 		var str="<img src='"+_basePath+"/customers/imag/red.png'> &nbsp;&nbsp;";
 		 if(row.COM_TYPE==1){
 				str=str+ "补齐流程中 | ";
 		}
 		else{
 			str=str+" <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='buqi(" + JSON.stringify(row) + ")'>资料补齐</a> | ";
 		}
 		 
 		if(row.UPDATE_TYPE==1){
				str=str+ "变更流程中 | ";
			 }
			 else{
				str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateCom(" + JSON.stringify(row) + ")'>变更</a> | ";
			 }
 		str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateEndDate(" + JSON.stringify(row) + ")'>延期</a> | ";
 		str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sendDB(" + JSON.stringify(row) + ")'>转DB保证金</a>";
 		return str;
 	 }
 	 else if(STATUS==5)//全额放款未补齐
 	 {
 		var str="";
 		 if(row.COM_TYPE==1){
 				str="补齐流程中 | ";
 		}
 		else{
 			str=str+" <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='buqi(" + JSON.stringify(row) + ")'>资料补齐</a> | ";
 		}
 		 
 		if(row.UPDATE_TYPE==1){
				str=str+"变更流程中 | ";
			 }
			 else{
				str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateCom(" + JSON.stringify(row) + ")'>变更</a> | ";
			 }
 		str=str+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updateEndDate(" + JSON.stringify(row) + ")'>延期</a>";
 		return str;
 	 }
 }
	}
 ]],
		columns:[[
		          	{field:'ST_NAME',title:'状态',width:100},
		          	{field:'STATUS',hidden:true},
		          	{field:'COM_TYPE',hidden:true},
		          	{field:'UPDATE_TYPE',hidden:true},
		          	{field:'SUPPLIER_ID',hidden:true},
		          	{field:'ID',hidden:true},
		          	{field:'DATE_COMPLEMENT',hidden:true},
		          	{field:'PROJ_ID',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'AREA_NAME',title:'区域',width:100},
		          	{field:'SUPPLIER_NAMES',title:'供应商',width:200},
		          	{field:'PRO_CODE',title:'项目编号',width:100,formatter:function(value,rowData,rowIndex){
			      		return "<a href='javascript:void(0)'  onclick='sechemSHow(" + JSON.stringify(rowData) + ")'>"+rowData.PRO_CODE+"</a>";
		      		}},
		          	{field:'CUST_NAME',title:'客户名称',width:200},
		          	{field:'COMPANY_NAMES',title:'厂商',width:200},
		          	{field:'PRODUCT_NAMES',title:'租赁物名称',width:200},
		          	{field:'ENGINE_TYPES',title:'机型',width:150},
		          	{field:'WHOLE_ENGINE_CODES',title:'出厂编号',width:150},
		          	{field:'QZ_DATE',title:'起租确定日',width:100},
		          	{field:'END_DATE',title:'资料补齐到期日',width:100},
		          	{field:'LEASE_TOPRIC',title:'租赁物购买价款',width:100},
		          	{field:'FK_RATIO',title:'已放款比例',width:100},
		          	{field:'PLAN_MONEY',title:'已放款金额',width:100},
		          	{field:'TYPE',hidden:true}]]
	});
	
	$("#dossierApplyBtn").click(function(){
		if($("#dossierApplyBtn").attr("disabled")!="disabled"){
			dossierApply();
		}
	});
	
	$("#checkAll").click(function() {
		var flag = $("#checkAll").attr("checked");
		if (flag == "checked") {
			$(".DOSSIERID").each(function() {
				if ($(this).attr("disabled") != "disabled") {
					$(this).attr("checked", true);
				}
			});
		} else {
			$(".DOSSIERID").each(function() {
				$(this).attr("checked", false);
			});
		}
	});
	
	$("#addRecordButton").click(function() {
		if ($("#addRecordButton").attr("disabled") != "disabled") {
			saveDossierBorrowApp();
		}
	});
});

function sechemSHow(row)
{
	 if (row){
		 var PROJECT_ID=row.PROJ_ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}



function test()
{
	window.location.href="../complement/Complement!execute.action";
}


//延期转DB保证金
function updateEndDate(row){
	 if (row){
		 var PRO_CODE=row.PRO_CODE;
		 var QZ_DATE=row.QZ_DATE;
		 var END_DATE=row.END_DATE;
		 var ID=row.ID;
			$('#PROJECT_CODE_UPDATE').val(PRO_CODE);
			$('#ID_UPDATE').val(ID);
			$('#QZ_DATE_UPDATE').val(QZ_DATE);
			$('#END_DATE1_UPDATE').val(END_DATE);
			$("input[name=END_DATE2_UPDATE]").val(END_DATE);
		 $("#dlgUp").dialog("open");
	}else{
		$.messager.alert("请选择一个项目!");
	}
	
}

function sendDB(row){
	 if (row){
		 var PROJECT_ID=row.PROJ_ID;
		 var PRO_CODE=row.PRO_CODE;
		 jQuery.ajax({
				url:_basePath+'/complement/Complement!sendDB.action',
				data:'PROJECT_ID='+PROJECT_ID+'&PROJECT_CODE='+PRO_CODE,
				dataType: 'json',
				success:function(json){
					if(json.flag==true){
						alert('转保证金成功');
						seach();
					}else{
						alert(json.errorMsg);
					}	
				}
			});
	}else{
		$.messager.alert("请选择一个项目!");
	}
	 
	 
}

//资料补齐
function buqi(row){
	if (row){
		top.addTab("资料补齐申请",_basePath+"/complement/Complement!doShowRetentionDataZL.action?PROJECT_ID="+row.PROJ_ID+"&CLIENT_TYPE="+row.TYPE+"&LC_TYPE=ZLBQ&SUPPLIER_ID="+row.SUPPLIER_ID+"&CLIENT_ID="+row.CUST_ID+"&PRO_CODE="+row.PRO_CODE+"&PAYLIST_CODE="+row.PRO_CODE+"-1&CUST_NAME="+encodeURI(row.CUST_NAME));	
//		 top.addTab("资料补齐申请",_basePath+"/complement/Complement!complementC.action?PROJECT_ID="+row.PROJ_ID+"&SUPPLIER_ID="+row.SUPPLIER_ID+"&CLIENT_ID="+row.CUST_ID+"&PRO_CODE="+row.PRO_CODE+"&CUST_NAME="+encodeURI(row.CUST_NAME));	
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function updateCom(row){
	if (row){
		top.addTab("资料变更申请",_basePath+"/complement/Complement!doShowRetentionDataZLBG.action?PROJECT_ID="+row.PROJ_ID+"&CLIENT_TYPE="+row.TYPE+"&LC_TYPE=ZLBG&SUPPLIER_ID="+row.SUPPLIER_ID+"&CLIENT_ID="+row.CUST_ID+"&PRO_CODE="+row.PRO_CODE+"&PAYLIST_CODE="+row.PRO_CODE+"-1&CUST_NAME="+encodeURI(row.CUST_NAME));	
//		 top.addTab("资料变更申请",_basePath+"/complement/Complement!complementU.action?PROJECT_ID="+row.PROJ_ID+"&SUPPLIER_ID="+row.SUPPLIER_ID+"&CLIENT_ID="+row.CUST_ID+"&PRO_CODE="+row.PRO_CODE+"&CUST_NAME="+encodeURI(row.CUST_NAME));	
	}else{
		$.messager.alert("请选择一个项目!");
	}
}
function toProjectChangeApply(row){
	if (row){
		top.addTab("资料变更申请",_basePath+"/complement/Complement!toProjectChangeApply.action?PROJECT_ID="+row.PROJ_ID+"&CLIENT_TYPE="+row.TYPE+"&LC_TYPE=ZLBG&SUPPLIER_ID="+row.SUPPLIER_ID+"&CLIENT_ID="+row.CUST_ID+"&PRO_CODE="+row.PRO_CODE+"&PAYLIST_CODE="+row.PRO_CODE+"-1&CUST_NAME="+encodeURI(row.CUST_NAME));	
	}else{
		$.messager.alert("请选择一个项目!");
	}
}


/**
 * 复制某个行
 * @param {Object} fromId
 * @param {Object} toId
 */
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

function dossierApply(){
	var PROJECT_CODE=$("#PROJECT_CODE").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var PROJECT_ID1=$("#PROJECT_ID").val();
	var PAYLIST_CODE=$("#PAYLIST_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var SEND_TYPE=$("#SEND_TYPE").val();
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var flag=false;
	var saveRecord=new Array();
	$(".eq_body_tr").each(function(){
			flag=true;
			var temp={};
			
			temp.TPM_CODE=null;
			temp.TPM_BUSINESS_PLATE='';
			temp.TPM_TYPE=$(this).find("input[name=TPM_TYPE]").val();
			temp.TPM_CUSTOMER_TYPE='';
			temp.TPM_ID='';
			temp.PDF_PATH='';
			temp.NAME=$(this).find("input[name=TPM_NAME]").val();
			var type=$(this).find("select[name=Doss_Type]").val();
			if(type=='1')//承租人资料
			{
				temp.PROJECT_ID='';
				PROJECT_ID='';
				temp.PAYLIST_CODE='';
				PAYLIST_CODE='';
			}else{//合同资料
				temp.PROJECT_ID=PROJECT_ID;
				temp.PAYLIST_CODE=PAYLIST_CODE;
			}
			temp.DOSSIERTYPE=$(this).find("select[name=DOSSIERTYPE]").val();
			temp.DOSSIER_COUNT=$(this).find("input[name=DOSSIER_COUNT]").val();
			temp.DOSSIER_PAGE=$(this).find("input[name=DOSSIER_PAGE]").val();
			saveRecord.push(temp);
	});
	if(flag==false){
		$.messager.alert("提示","请选择需要申请的文件");
		return;
	}
	$.ajax({
		type:"post",
		url:_basePath+"/complement/Complement!complementSave.action",
		data:"APPLY_TYPE=1&PROJECT_CODE="+PROJECT_CODE+"&SUPPLIER_ID="+SUPPLIER_ID+"&PROJECT_ID="+PROJECT_ID1+"&PAYLIST_CODE="+PAYLIST_CODE+"&CLIENT_ID="+CLIENT_ID+"&SEND_TYPE="+SEND_TYPE+"&FILEINFO="+encodeURI(JSON.stringify(saveRecord)),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","申请成功！");
				$("#dossierApplyBtn").linkbutton("disable");
				$("#dossierApplyBtn").attr("disabled","disabled");
			}else{
				$.messager.alert("提示","申请失败"+json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","网络原因，请联系管理员");
		}
	});
}

function saveDossierBorrowApp() {
	if (!$("#borrowform").form('validate')) {
		return false;
	}
	var flag = false;
	var saveRecord = new Array();
	
	$(".DOSSIERID").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td");
					var tr = $(this).parent("td").parent("tr");
					temp.DOSSIERID = $(this).val();
					temp.FILE_NAME = $(td).find("input[name=FILE_NAME]").val();
					temp.FILE_TYPE = $(td).find("input[name=FILE_TYPE]").val();
					temp.DOSSIER_CODE = $(td).find("input[name=DOSSIER_CODE]")
							.val();
					temp.PAYLIST_CODE = $(td).find("input[name=PAYLIST_CODE]").val();
					temp.PORTFOLIO_NUMBER = $(td).find(
							"input[name=PORTFOLIO_NUMBER]").val();
					temp.CABINET_NUMBER = $(td).find(
							"input[name=CABINET_NUMBER]").val();
					temp.DOSSIER_TEMP = $(tr).find("select[name=DOSSIER_TEMP]").val();
					temp.DOSSIER_COUNT = $(tr)
							.find("input[name=DOSSIER_COUNT]").val();
					temp.FILEPAGE = $(tr).find("input[name=FILEPAGE]").val();
					temp.DOSSIER_STATUS = $(tr).find(
							"input[name=DOSSIER_STATUS]").val();
					saveRecord.push(temp);
				}
			});
	
	var PROJECT_MODEL=$("#PROJECT_MODEL").val();
	
	var Eq=[];
	$(".eq_body_tr").each(function() {
		
		var temp={};
		
		temp.EQ_ID = $(this).find("[name='EQ_ID']").val();
		
		//得到设备名称
		temp.PRODUCT_NAME = $(this).find("[name='PRODUCT_NAME']").val();
		
		//得到设备系别
		temp.CATENA_NAME = $(this).find("[name='CATENA_NAME']").val();
		
		//得到设备型号
		temp.SPEC_NAME = $(this).find("[name='SPEC_NAME']").val();
		
		//得到出厂编号
		var WHOLE_ENGINE_CODE = $(this).find("input[name='WHOLE_ENGINE_CODE']");
		if ($(WHOLE_ENGINE_CODE).val() == "" || $(WHOLE_ENGINE_CODE).val() == undefined || $(WHOLE_ENGINE_CODE).val()<=0) {
			flag = false;
			$(WHOLE_ENGINE_CODE).focus();
			alert("请输入出厂编号");
			return;
		};
		temp.WHOLE_ENGINE_CODE = $(this).find("[name='WHOLE_ENGINE_CODE']").val();
		
		//得到出厂日期
		var CERTIFICATE_DATE = $(this).find("input[name='CERTIFICATE_DATE']");
		if ($(CERTIFICATE_DATE).val() == "" || $(CERTIFICATE_DATE).val() == undefined || $(CERTIFICATE_DATE).val()<=0) {
			flag = false;
			$(CERTIFICATE_DATE).focus();
			alert("请输入出厂日期");
			return;
		};
		temp.CERTIFICATE_DATE = $(this).find("[name='CERTIFICATE_DATE']").val();
		
		//得到发动机编号
		var ENGINE_TYPE = $(this).find("input[name='ENGINE_TYPE']");
		if ($(ENGINE_TYPE).val() == "" || $(ENGINE_TYPE).val() == undefined || $(ENGINE_TYPE).val()<=0) {
			flag = false;
			$(ENGINE_TYPE).focus();
			alert("请输入发动机编号");
			return;
		};
		temp.ENGINE_TYPE = $(this).find("[name='ENGINE_TYPE']").val();
		
		//得到车架号
		var CAR_SYMBOL = $(this).find("input[name='CAR_SYMBOL']");
		if ($(CAR_SYMBOL).val() == "" || $(CAR_SYMBOL).val() == undefined || $(CAR_SYMBOL).val()<=0) {
			flag = false;
			$(CAR_SYMBOL).focus();
			alert("请输入车架号");
			return;
		};
		temp.CAR_SYMBOL = $(this).find("[name='CAR_SYMBOL']").val();
		
		temp.STAYBUY_PRICE = $(this).find("[name='STAYBUY_PRICE']").val();
		
		//得到设备的单价
		temp.UNIT_PRICE = $(this).find("[name='UNIT_PRICE']").val();
		
		//获取设备数量
		temp.AMOUNT = $(this).find("[name='AMOUNT']").val();
		
		temp.UNIT = $(this).find("[name='UNIT']").val();
		
		if(PROJECT_MODEL=='1'){
			//获取产地
			temp.PRODUCT_ADDRESS = $(this).find("[name='PRODUCT_ADDRESS']").val();
			
			//获取合格证书
			temp.CERTIFICATE_NUM = $(this).find("[name='CERTIFICATE_NUM']").val();
			
			//获取限乘人数
			temp.LIMIT_NUM = $(this).find("[name='LIMIT_NUM']").val();
			
			//获取机动车辆生产企业名称
			temp.COMPANY_FULLNAME = $(this).find("[name='COMPANY_FULLNAME']").val();
			
			temp.IMPORT_NUM = $(this).find("[name='IMPORT_NUM']").val();//获取进口证明书号
			
			temp.INSPEC_NUM = $(this).find("[name='INSPEC_NUM']").val();//获取商检单号
			
			temp.TONNAGE = $(this).find("[name='TONNAGE']").val();//获取吨位
			
			
			//实际车辆开票名称
			temp.ACTUAL_PRODUCT_NAME = $(this).find("input[name='ACTUAL_PRODUCT_NAME']").val();
			
			//实际车辆开票型号
			temp.ACTUAL_PRODUCT_TYPE = $(this).find("input[name='ACTUAL_PRODUCT_TYPE']").val();
			
		}
		Eq.push(temp);
	});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要变更的文件");
		return;
	}
	
	
	
	var RESTOREPURPOSE = $("#RESTOREPURPOSE").val();
	var PROJECT_CODE = $("#PROJECT_CODE").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	var CLIENT_ID = $("#CLIENT_ID").val();
	var CUST_NAME = $("#CUST_NAME").val();
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var url= _basePath+ "/complement/Complement!doAddBorrowApp.action?RESTOREPURPOSE="+ encodeURI(RESTOREPURPOSE)+"&SUPPLIER_ID="+SUPPLIER_ID + "&PROJECT_CODE=" + PROJECT_CODE+ "&PROJECT_ID=" + PROJECT_ID + "&CLIENT_ID="+ CLIENT_ID + "&CUST_NAME=" + encodeURI(CUST_NAME)+ "&PROJECT_MODEL=" + encodeURI(PROJECT_MODEL) + "&EQINFO="+ encodeURI(JSON.stringify(Eq))  + "&DOSSIERINFO=" + encodeURI(JSON.stringify(saveRecord));
//	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
//	$("#formSubmit").submit();
//	
	
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","发起流程成功！","info",function(){
					$.messager.alert("提示",json.msg+json.data,"info",function(){
						$("#dossierApplyBtn").linkbutton("disable");
						$("#dossierApplyBtn").attr("disabled","disabled");
					});
				});
				
			}else{
				$.messager.alert("提示","发起流程失败"+json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","网络原因，请联系管理员");
		}
	});
}





function saveCheckedContract(){
var flag=false;
var saveRecord=new Array();
var CLIENT_ID=$("#CLIENT_ID").val();
var i=1;
$(".checkId").each(function(){
if($(this).attr("checked")=="checked"){
	flag=true;
	var temp={};
	var td=$(this).parent("td");
	var tr=$(td).parent("tr");
	temp.TPM_CODE=$(td).find("input[name=FILE_CODE]").val();
	temp.TPM_TYPE=$(td).find("input[name=TPM_TYPE]").val();
	temp.TPM_CUSTOMER_TYPE=$(td).find("input[name=TPM_CUSTOMER_TYPE]").val();
	temp.PDF_PATH=$(td).find("input[name=PDF_PATH]").val();
	temp.PROJECT_ID=$(td).find("input[name=PROJECT_ID]").val();
	temp.FILE_REMARK=$(td).find("input[name=FILE_REMARK]").val();
	temp.PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
	temp.DOSSIERTYPE=$(tr).find("input[name=DOSSIERTYPE"+i+"]:checked").val();
	temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
	temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
	temp.FILE_TYPE=$(td).find("input[name=FILE_TYPE]").val();
	temp.CLIENT_ID=CLIENT_ID;
	temp.TPM_BUSINESS_PLATE="资料补齐";
	saveRecord.push(temp);
}
i++;
});

var PROJECT_CODE=$("#PROJECT_CODE").val();
var SEND_TYPE=$("#SEND_TYPE").val();
var SUPPLIER_ID=$("#SUPPLIER_ID").val();
var CLIENT_ID=$("#CLIENT_ID").val();
var PROJECT_CODE=$("#PROJECT_CODE").val();
var PROJECT_ID=$("#PROJECT_ID").val();
$.ajax({
type:"post",
url:_basePath+"/complement/Complement!doAddCheckedContractData.action",
data:"PROJECT_CODE="+PROJECT_CODE+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&SUPPLIER_ID="+SUPPLIER_ID +"&SEND_TYPE="+SEND_TYPE+"&LC_TYPE=ZLBQ&FILEINFO="+JSON.stringify(saveRecord),
dataType:"json",
success:function(json){
	if(json.flag){
		$("#save_button").linkbutton("disable");
		$("#save_button").attr("disabled","disabled");
		$.messager.alert("提示","发起流程成功！",'info',function(){
			$.messager.alert("提示",json.msg+json.data);
		});
	}else{
		$.messager.alert("提示","发起流程失败"+json.msg);
	}
},
error:function(){
	$.messager.alert("提示","网络原因，请联系管理员");
}
});
}


function saveCheckedBGContract(){
	var PROJECTCHANGEID=$("#PROJECTCHANGEID").val();
	var flag=false;
	var saveRecord=new Array();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var i=1;
	$(".checkId").each(function(){
	if($(this).attr("checked")=="checked"){
		flag=true;
		var temp={};
		var td=$(this).parent("td");
		var tr=$(td).parent("tr");
		temp.TPM_CODE=$(td).find("input[name=FILE_CODE]").val();
		temp.TPM_TYPE=$(td).find("input[name=TPM_TYPE]").val();
		temp.TPM_CUSTOMER_TYPE=$(td).find("input[name=TPM_CUSTOMER_TYPE]").val();
		temp.PDF_PATH=$(td).find("input[name=PDF_PATH]").val();
		temp.PROJECT_ID=$(td).find("input[name=PROJECT_ID]").val();
		temp.FILE_REMARK=$(td).find("input[name=FILE_REMARK]").val();
		temp.PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
		temp.DOSSIERTYPE=$(tr).find("input[name=DOSSIERTYPE"+i+"]:checked").val();
		temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
		temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
		temp.FILE_TYPE=$(td).find("input[name=FILE_TYPE]").val();
		temp.CLIENT_ID=CLIENT_ID;
		temp.TPM_BUSINESS_PLATE="资料变更";
		saveRecord.push(temp);
	}
	i++;
	});

	var PROJECT_CODE=$("#PROJECT_CODE").val();
	var SEND_TYPE=$("#SEND_TYPE").val();
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var PROJECT_CODE=$("#PROJECT_CODE").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	$.ajax({
	type:"post",
	url:_basePath+"/complement/Complement!doAddCheckedBGContractData.action",
	data:"PROJECT_CODE="+PROJECT_CODE+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&SUPPLIER_ID="+SUPPLIER_ID +"&SEND_TYPE="+SEND_TYPE+"&PROJECTCHANGEID="+PROJECTCHANGEID+"&LC_TYPE=ZLBG&FILEINFO="+JSON.stringify(saveRecord),
	dataType:"json",
	success:function(json){
		if(json.flag){
			$("#save_button").linkbutton("disable");
			$("#save_button").attr("disabled","disabled");
			$.messager.alert("提示","发起流程成功！",'info',function(){
				$.messager.alert("提示",json.msg+json.data);
			});
		}else{
			$.messager.alert("提示","发起流程失败"+json.msg);
		}
	},
	error:function(){
		$.messager.alert("提示","网络原因，请联系管理员");
	}
	});
	}
function projectChange(FLAG){
	$("#projectChange").attr("disabled",true);
	$("#projectChange").linkbutton("disable");
	$("#projectChange1").attr("disabled",true);
	$("#projectChange1").linkbutton("disable");
	var ID_CARD_NO=$("#ID_CARD_NO").val();
	var OPEN_BANK_NAME=$("#OPEN_BANK_NAME").val();
	var ID_CARD_NO_C=$("#ID_CARD_NO_C").val();
	var OPEN_ACCOUNT_NAME=$("#OPEN_ACCOUNT_NAME").val();
	var OPEN_ACCOUNT=$("#OPEN_ACCOUNT").val();
	var OPEN_ACCOUNT_C=$("#OPEN_ACCOUNT_C").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var REMARK=$("#REMARK").val();
	if(ID_CARD_NO!=ID_CARD_NO_C){
		$.messager.alert("提示","两次输入的身份证信息不同!");
		$("#projectChange").attr("disabled",false);
		$("#projectChange").linkbutton("enable");
		$("#projectChange1").attr("disabled",false);
		$("#projectChange1").linkbutton("enable");
		return ;
	}else if(OPEN_ACCOUNT!=OPEN_ACCOUNT_C){
		$.messager.alert("提示","两次输入的银行卡号不同！");
		$("#projectChange").attr("disabled",false);
		$("#projectChange").linkbutton("enable");
		$("#projectChange1").attr("disabled",false);
		$("#projectChange1").linkbutton("enable");
		return ;
	}
	
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
			temp.ACTUAL_PRODUCT_NAME=$(this).find("[name='ACTUAL_PRODUCT_NAME']").val();
			temp.ACTUAL_PRODUCT_TYPE=$(this).find("[name='ACTUAL_PRODUCT_TYPE']").val();
		}
		array.push(temp);
	});
	//FLAG=1 表示新增卡号   FLAG=2 表示变更所有项目的该卡号信息
	$.ajax({
		type:"post",
		url:_basePath+"/complement/Complement!doAddProjectChangeApply.action",
		data:"PROJECT_ID="+PROJECT_ID+"&EQUIPMENT="+JSON.stringify(array)+"&ID_CARD_NO="+ID_CARD_NO+"&OPEN_BANK_NAME="+encodeURI(OPEN_BANK_NAME)
			 +"&ID_CARD_NO_C="+encodeURI(ID_CARD_NO_C)+"&OPEN_ACCOUNT_NAME="+encodeURI(OPEN_ACCOUNT_NAME)+"&OPEN_ACCOUNT="
			 +OPEN_ACCOUNT+"&OPEN_ACCOUNT_C="+OPEN_ACCOUNT_C+"&REMARK="+encodeURI(REMARK)+"&FLAG="+FLAG,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","修改成功！");
				$("#PROJECTCHANGEID").val(json.data);
			}else{
				$.messager.alert("提示","保存失败"+json.msg);
				$("#projectChange").attr("disabled",false);
				$("#projectChange").linkbutton("enable");
				$("#projectChange1").attr("disabled",false);
				$("#projectChange1").linkbutton("enable");
			}
		},
		error:function(){
			$.messager.alert("提示","网络问题，请联系管理员");
			$("#projectChange").attr("disabled",false);
			$("#projectChange").linkbutton("enable");
			$("#projectChange1").attr("disabled",false);
			$("#projectChange1").linkbutton("enable");
		}
	});
}

function projectUpdateChange(){
	$("#projectUpdateChange").attr("disabled",true);
	$("#projectUpdateChange").linkbutton("disable");
	var ID_CARD_NO=$("#ID_CARD_NO").val();
	var OPEN_BANK_NAME=$("#OPEN_BANK_NAME").val();
	var ID_CARD_NO_C=$("#ID_CARD_NO_C").val();
	var OPEN_ACCOUNT_NAME=$("#OPEN_ACCOUNT_NAME").val();
	var OPEN_ACCOUNT=$("#OPEN_ACCOUNT").val();
	var OPEN_ACCOUNT_C=$("#OPEN_ACCOUNT_C").val();
	var REMARK=$("#REMARK").val();
	var PROJECTCHANGEID=$("#PROJECTCHANGEID").val();
	if(ID_CARD_NO!=ID_CARD_NO_C){
		$.messager.alert("提示","两次输入的身份证信息不同!");
		$("#projectUpdateChange").attr("disabled",false);
		$("#projectUpdateChange").linkbutton("enable");
		return ;
	}else if(OPEN_ACCOUNT!=OPEN_ACCOUNT_C){
		$.messager.alert("提示","两次输入的银行卡号不同！");
		$("#projectUpdateChange").attr("disabled",false);
		$("#projectUpdateChange").linkbutton("enable");
		return ;
	}
	
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
			temp.ACTUAL_PRODUCT_NAME=$(this).find("[name='ACTUAL_PRODUCT_NAME']").val();
			temp.ACTUAL_PRODUCT_TYPE=$(this).find("[name='ACTUAL_PRODUCT_TYPE']").val();
		}
		array.push(temp);
	});
	
	$.ajax({
		type:"post",
		url:_basePath+"/complement/Complement!doUpdateProjectChangeApply.action",
		data:"EQUIPMENT="+JSON.stringify(array)+"&ID_CARD_NO="+ID_CARD_NO+"&OPEN_BANK_NAME="+encodeURI(OPEN_BANK_NAME)
			 +"&ID_CARD_NO_C="+encodeURI(ID_CARD_NO_C)+"&OPEN_ACCOUNT_NAME="+encodeURI(OPEN_ACCOUNT_NAME)+"&OPEN_ACCOUNT="
			 +OPEN_ACCOUNT+"&OPEN_ACCOUNT_C="+OPEN_ACCOUNT_C+"&REMARK="+encodeURI(REMARK)+"&PROJECTCHANGEID="+PROJECTCHANGEID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","修改成功！");
				$("#PROJECTCHANGEID").val(json.data);
			}else{
				$.messager.alert("提示","保存失败"+json.msg);
				$("#projectUpdateChange").attr("disabled",false);
				$("#projectUpdateChange").linkbutton("enable");
			}
		},
		error:function(){
			$.messager.alert("提示","网络问题，请联系管理员");
			$("#projectUpdateChange").attr("disabled",false);
			$("#projectUpdateChange").linkbutton("enable");
		}
	});	
}

function exportData(){
	$("#divFrom").empty();
	var COMPANY_NAME=$("input[name='COMPANY_NAME']").val();
	var SUP_NAME=$("input[name='SUP_NAME']").val();
	var PRO_CODE=$("input[name='PRO_CODE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var STATUS=$("select[name='STATUS']").val();
	var PRODUCT_NAME=$("select[name='PRODUCT_NAME']").val();
	var QZ_DATE1=$("input[name='QZ_DATE1']").val();
	var QZ_DATE2=$("input[name='QZ_DATE2']").val();
	var END_DATE1=$("input[name='END_DATE1']").val();
	var END_DATE2=$("input[name='END_DATE2']").val();
	
	var url=_basePath+"/complement/Complement!uploadMessage.action?COMPANY_NAME="+COMPANY_NAME+"&SUP_NAME="+SUP_NAME+"&PRO_CODE="+PRO_CODE+"&CUST_NAME="+CUST_NAME+"&STATUS="+STATUS+"&PRODUCT_NAME="+PRODUCT_NAME+"&QZ_DATE1="+QZ_DATE1+"&QZ_DATE2="+QZ_DATE2+"&END_DATE1="+END_DATE1+"&END_DATE2="+END_DATE2;
	
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}
$(function() {
	$('#fhfaMg').datagrid({
		url:_basePath+"/refinanceFHFA/RefinanceFHFA!toMgDeductData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[ 	 	 
					{field:'OPERATION',align:'center',width:40,title:'操作',formatter:function(value,rowData,rowIndex){
						   var url = "";
						   if(rowData.CREDIT_STATUS == "1"){
							   url = "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toAddRefinanceWay("
								+ rowData.OPERATION + ")'>添加融资方式</a>";
						   }else{                		 
							   url ="<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toAddRefinanceWay_()'>添加融资方式 </a>";
						   }
						  return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewFHFA("
								+ rowData.OPERATION+")'>查看</a>  " 
								+ "|  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toUpdateFHFA("
								+ rowData.OPERATION+")'>修改</a> " 
								+ "|  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='doDeleteFHFA("
								+ rowData.OPERATION+")'>删除</a> " 
								+ "|<br/> <a href='javascript:void(0);' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toAddCreditGranting("
								+ rowData.OPERATION + ")'>添加授信</a>"
								+ "|<br/>"+url;
					}},
					{field:'FHFA_CREDIT',align:'center',width:40,title:'授信信息',formatter:function(value,rowData,rowIndex){
			        	  return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toCreditGranting("+ rowData.FHFA_CREDIT+")'>授信</a>";
	                   }},
			          {field:'REFINANCE_TYPE',align:'center',width:40,title:'融资方式',formatter:function(value,rowData,rowIndex){
	                	   return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toRefinanceWay("+ rowData.REFINANCE_TYPE+")'>融资方式</a>";
	                   }},
		          {field:'FHFA_ID',align:'center',width:20},
		          {field:'ORGAN_NAME',align:'center',width:40,title:'融资机构名称'},
		          {field:'LINKMAN',align:'center',width:40,title:'联系人'},
		          {field:'LINKMAN_PHONE',align:'center',width:40,title:'联系电话'},
		          {field:'ORGAN_ADD',align:'center',width:40,title:'融资机构地址'},
		          {field:'CREDIT_STATUS',align:'center',width:40,title:'授信状态',formatter:function(value,rowData,rowIndex){
		        	  if(value=="1"){
		        		  return "已授信";
		        	  }else {
		        		  return "未授信";
		        	  }
		          }},
		          {field:'CREDIT_BIN_DEADLINE',align:'center',width:40,title:'授信起始日期'},
		          {field:'CREDIT_END_DEADLINE',align:'center',width:40,title:'授信结束日期'},
		          {field:'TOTAL_CREDIT',align:'center',width:40,title:'授信额度'},
		          {field:'PRACTICAL_LIMIT',align:'center',width:40,title:'实际使用额度'},
		          {field:'BALANCE',align:'center',width:40,title:'余额'}
		          
		          
		          ]]
	});
	
	$("#addTbody").click(function(e){
		if($(e.target).is(".del")){
			$(e.target).parents("tr").remove();
		}
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var ORGAN_NAME = $("input[name='ORGAN_NAME1']").val();
	var CREDIT_DEADLINE1 = $("input[name='CREDIT_DEADLINE1']").val();
	var CREDIT_DEADLINE2 = $("input[name='CREDIT_DEADLINE2']").val();
//	var CREDIT_STATUS = $("select[name='CREDIT_STATUS']").attr("selected",true).val();
	var CREDIT_STATUS=$("#CREDIT_STATUS").val();
	$('#fhfaMg').datagrid('load', {
		"ORGAN_NAME1" : ORGAN_NAME,
		"CREDIT_DEADLINE1" : CREDIT_DEADLINE1,
		"CREDIT_DEADLINE2" : CREDIT_DEADLINE2,
		"CREDIT_STATUS":CREDIT_STATUS
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#CREDIT_DEADLINE1").datebox('clear');
	$("#CREDIT_DEADLINE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 进入添加页面
 * @return
 */
function toAddFhfa(){
	$("#toAddFhfa").dialog('open');
	jQuery.get(_basePath+'/refinanceFHFA/RefinanceFHFA!toAddFHFA.action?data='+new Date(),function(html){
		$("#openAddFhfa").html(html);
	});
}

/**
 * 添加融资机构
 * @return
 */
function doAddFhfa(){
	var data = {};
	data["baseData"] = baseData();	
	data["accountBank"] = accountBank();	
	jQuery.ajax({
		type:"post",
		url:_basePath+"/refinanceFHFA/RefinanceFHFA!doAddFHFA.action",
		data:"data="+JSON.stringify(data),
		dataType:"json",
		success:function(json){
		if(json.flag == true){
			 $.messager.alert("提示","添加成功！！");			
		 }else{
			 $.messager.alert("提示","添加失败！！");			 
		 }
		$("#toAddFhfa").dialog('close');
		//页面刷新
		$('#fhfaMg').datagrid('load');
		
		}
	});
}

/**
 * 融资机构基础数据
 * @return
 */
function baseData(){
	var ptype = "";
	var petype = "";
	var petypestr = "";
	var ptypestr = "";
	var baseData = {};
	baseData["ORGAN_NAME"] = $("#ORGAN_NAME").val();	//融资机构名称	
	baseData["LINKMAN"]  = $("#LINKMAN").val();//联系人
	baseData["ORGAN_ADD"]  = $("#ORGAN_ADD").val();//融资机构地址
	baseData["LINKMAN_PHONE"]  = $("#LINKMAN_PHONE").val();//联系电话
	baseData["REPAYMENT_MAN"]  = $("#REPAYMENT_MAN").val();//还款人
	baseData["BAIL_DEPOSITOR"]  = $("#BAIL_DEPOSITOR").val();//保证金存款人
	ptype = document.getElementsByName("PAYMENT_TYPE");//支付类型
    petype = document.getElementsByName("PAYMENT_METHOD");//支付方式
    for(var i = 0;i<ptype.length;i++){
		if(ptype[i].checked){
			ptypestr+=ptype[i].value+",";
		}	
	}
	for(var i = 0;i<petype.length;i++){
		if(petype[i].checked){
			petypestr+=petype[i].value+",";
		}	
	}
	
	baseData["PAYMENT_TYPE"]  = ptypestr;
	baseData["PAYMENT_METHOD"]  = petypestr;
	return baseData;
}

/**
 * 融资机构帐号信息
 * @return
 */
function accountBank(){
	var accountBank = [];
	$(".addData:not(.templete)").each(function(){
		var temp = {};
		temp.BANK_TYPE = $(this).find("select[name=accountType]").attr("selected",true).val();//帐号类型
		temp.BANK_ACCOUNT = $(this).find("input[name=BANK_ACCOUNT]").val();//帐号
		accountBank.push(temp);
	});
	return accountBank;
}

/**
 * 进入添加授信页面
 * @author yx
 * @date 2013-10-14
 * @param FHFA_ID  融资机构id
 * @return
 */
function toAddCreditGranting(FHFA_ID){
	$("#toAddReCredit").dialog('open');
	jQuery.get(_basePath+'/reCreditManagement/ReCreditManagement!toAddReCredit.action?FHFA_ID='+FHFA_ID+'&data='+new Date(),function(html){
		$("#openAddReCredit").html(html);
	});
}

/**
 * 查看融资机构
 * @param FHFA_ID 融资机构id 
 * @return
 */
function toViewFHFA(FHFA_ID){
	$("#toSearchFhfa").dialog('open');
	jQuery.get(_basePath+'/refinanceFHFA/RefinanceFHFA!toSearchFhfa.action?FHFA_ID='+FHFA_ID+'&data='+new Date(),function(html){
		$("#openSearchFhfa").html(html);
	});
}

/**
 * 进入修改页面
 * @return
 */
function toUpdateFHFA(FHFA_ID){
	$("#toUpdateFhfa").dialog('open');
	jQuery.get(_basePath+'/refinanceFHFA/RefinanceFHFA!toUpdateFHFA.action?FHFA_ID='+FHFA_ID+'&data='+new Date(),function(html){
		$("#openUpdateFhfa").html(html);
	});
}

/**
 * 删除
 * @return
 */
function doDeleteFHFA(ID){
	$.messager.confirm('提示','确定删除融资机构?',function(r){
		if(r){
			jQuery.ajax({
				type:"post",
				url:_basePath+"/refinanceFHFA/RefinanceFHFA!doDeleteFHFA.action",
				data:"ID="+ID,
				dataType:"json",
				success:function(json){
				if(json.flag == true){
					 $.messager.alert("提示","删除成功!");			
					 //页面刷新
					 $('#fhfaMg').datagrid('load');
				 }else{
					 $.messager.alert("提示","删除失败!");			 
				 }
				}
			});
		}
	});
}

/**
 * 修改融资机构操作
 * @return
 */
function doUpdateFhfa(){
	var data = {};
	data["baseData"] = baseUdateData();	
	data["accountBank"] = accountUpdateBank();	
	jQuery.ajax({
		type:"post",
		url:_basePath+"/refinanceFHFA/RefinanceFHFA!doUpdateFHFA.action",
		data:"data="+JSON.stringify(data),
		dataType:"json",
		success:function(json){
		if(json.flag == true){
			 $.messager.alert("提示","修改成功！！");			
		 }else{
			 $.messager.alert("提示","修改失败！！");			 
		 }
		$("#toUpdateFhfa").dialog('close');
		//页面刷新
		$('#fhfaMg').datagrid('load');
		
		}
	});
}

/**
 * 融资机构基础数据-修改
 * @return
 */
function baseUdateData(){
	var ptype = "";
	var petype = "";
	var petypestr = "";
	var ptypestr = "";
	var baseUdateData = {};
	baseUdateData["FHFA_ID"] = $("#ID").val();	//融资机构id
	baseUdateData["ORGAN_NAME"] = $("#ORGAN_NAME1").val();	//融资机构名称	
	baseUdateData["LINKMAN"]  = $("#LINKMAN1").val();//联系人
	baseUdateData["ORGAN_ADD"]  = $("#ORGAN_ADD1").val();//融资机构地址
	baseUdateData["LINKMAN_PHONE"]  = $("#LINKMAN_PHONE1").val();//联系电话
	baseUdateData["REPAYMENT_MAN"]  = $("#REPAYMENT_MAN1").val();//还款人
	baseUdateData["BAIL_DEPOSITOR"]  = $("#BAIL_DEPOSITOR1").val();//保证金存款人
	ptype = document.getElementsByName("PAYMENT_TYPE1");//支付类型
    petype = document.getElementsByName("PAYMENT_METHOD1");//支付方式
    for(var i = 0;i<ptype.length;i++){
		if(ptype[i].checked){
			ptypestr+=ptype[i].value+",";
		}	
	}
	for(var i = 0;i<petype.length;i++){
		if(petype[i].checked){
			petypestr+=petype[i].value+",";
		}	
	}
	
	baseUdateData["PAYMENT_TYPE"]  = ptypestr;
	baseUdateData["PAYMENT_METHOD"]  = petypestr;
	return baseUdateData;
}

/**
 * 融资机构帐号信息-修改
 * @return
 */
function accountUpdateBank(){
	var accountBank = [];
	$(".updateData:not(.templete)").each(function(){
		var temp = {};
		temp.BANK_ID = $(this).find("input[name=BANK_ID]").val();//帐号id
		temp.BANK_TYPE = $(this).find("select[name=accountType]").attr("selected",true).val();//帐号类型
		temp.BANK_ACCOUNT = $(this).find("input[name=BANK_ACCOUNT]").val();//帐号
		accountBank.push(temp);
	});
	return accountBank;
}

/**
 * 融资方式
 * @author yx
 * @date 2013-10-14
 * @param JG_ID
 * @return
 */
function toRefinanceWay(JG_ID){
	$("#toShowRefinaceWay").dialog('open');
	jQuery.get(_basePath+'/RefinanceMethod/RefinanceMethod!methodList.action?JG_ID='+JG_ID+'&data='+new Date(),function(html){
		$("#openShowRefinaceWay").html(html);
	});
}

function toAddRefinanceWay_(){
	$.messager.alert("提示","请先添加授信！");
}

/**
 * 进入添加-融资方式
 * @author yx
 * @date 2013-10-15
 * @param FHFA_ID
 * @return
 */
function toAddRefinanceWay(FHFA_ID){
	$("#toAddRefinaceWay").dialog('open');
	jQuery.get(_basePath+'/RefinanceMethod/RefinanceMethod!addMethod.action?ID='+FHFA_ID+'&data='+new Date(),function(html){
		$("#openAddRefinaceWay").html(html);
	});
}

/**
 * 作废银行中号
 * @param id
 * @return
 */
function zfAccount(id){
	$.messager.confirm('提示','确定作废银行账号?',function(r){
		if (r){
			jQuery.ajax({
				url:_basePath+"/refinanceFHFA/RefinanceFHFA!upDataFhfaBankStatus.action?BANK_ID="+id,
				type:"post",
				dataType:"json",
				success:function(json){
					if(json.flag==true){
						$.messager.alert('提示','银行账号作废成功');
						window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action";
					}else {
						$.messager.alert('提示','银行账号作废失败');
						window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action";
					}
				}
			});
		}
	});
}

/**
 * 关闭dialog
 * @return
 */
function closeFhfa(div){
	$("#"+div).dialog('close');
}

/**
 * 添加行
 * @return
 */
function addR(){
	var tempTR=$(".templete").clone();
	$("#addTbody").append(tempTR.removeClass("hidden templete"));
}

/**
 * 删除行
 * @param obj
 * @return
 */
function del(obj)
{
	$(obj).parents("tr").remove();
}
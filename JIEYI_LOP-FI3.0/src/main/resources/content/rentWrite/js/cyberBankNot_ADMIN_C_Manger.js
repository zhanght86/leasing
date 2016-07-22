$(document).ready(function(){
	var FI_REALITY_BANK_FLAF=$("select[name='bankFlag']").find("option:selected").val();
	var FI_REALITY_BANK="";
	if(FI_REALITY_BANK_FLAF=='1'){
		FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
	}
	$("#cyberBankNot_C_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		toolbar:'#pageForm',
		queryParams:{"FI_REALITY_BANK" : FI_REALITY_BANK},
		url:'rentNotWrite!cyberBankNot_C_PageAjax.action',
		onClickCell:onClickCell,
		onAfterEdit:function(rowIndex, rowData, changes){
			var PAID_MONEY=changes.PAID_MONEY;
			var BEGINNING_MONEY=rowData.BEGINNING_MONEY;
			if(parseFloat(PAID_MONEY)<=0)
			{
				$('#cyberBankNot_C_PageTable').datagrid('updateRow',{
					index: rowIndex,
					row: {
						PAID_MONEY: BEGINNING_MONEY
					}
				});
			}
			else if(parseFloat(BEGINNING_MONEY)-parseFloat(PAID_MONEY)<0){
				$('#cyberBankNot_C_PageTable').datagrid('updateRow',{
					index: rowIndex,
					row: {
						PAID_MONEY: BEGINNING_MONEY
					}
				});
			}
			
		},
		columns:[[
		          	{field:'ID',checkbox:true,width:50},
		          	{field:'LOCKNAME',title:'锁状态',width:50},
		          	{field:'CUSTNAME',title:'承租人',width:150},
		          	{field:'ID_CARD_NO',title:'客户身份证号/组织架构',width:150},
		          	{field:'ACCOUNT_NAME',title:'开户名',width:150},
		          	{field:'BANK_NAME',title:'开户银行',width:150},
		          	{field:'BANK_ACCOUNT',title:'客户账号',width:150},
		          	{field:'BEGINNING_MONEY_BE',title:'金额',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:150},
	                {field:'PAYLIST_CODE',title:'还款计划',width:150}, 
		          	{field:'BEGINNING_NUM',title:'期次',width:50},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:150},
		          	{field:'BEGINNING_NAME',title:'类别',width:70},
		          	{field:'BEGINNING_STATUS',title:'状态',width:70},
		          	{field:'BEGINNING_FALSE_REASON',title:'失败原因',width:170},
		          	{field:'SIGN_FLAG',hidden:true},
		          	{field:'SIGN_NAME',title:'是否签约',width:70,formatter:function(value,rowData,rowIndex){
		          		if(rowData.SIGN_FLAG=='0')
		          		{
		          			return value;
		          		}
		          		else if(rowData.SIGN_FLAG=='1')
		          		{
		          			return value;
		          		}
		          		else
		          		{
		          			return "未签约";
		          		}
	                	  
	                  }},
	                 {field:'BEGINNING_MONEY',title:'本次应付金额',width:100},
			         {field:'PAID_MONEY',title:'本次实付金额',width:100, editor: {type:'text'}},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'CUST_NAME',hidden:true},
		          	{field:'PROJ_ID',hidden:true}
		         ]]
	});
});


//非网银-创建结算单
function creatNotBankFund()
{
	$("#divFrom").empty();
	var datagridList=$('#cyberBankNot_C_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	var IDS="";
	for(var i = 0; i < datagridList.length; i++){
		if(i==0){
			IDS=datagridList[i].ID;
		}
		else{
			IDS=IDS+","+datagridList[i].ID;
		}
	}
	
	$.ajax({
		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS,
	    type:'post',
	    dataType:'json',
	    success:function(json){
		    if(json.data == '2'){
		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
		    }
		    else if(json.data == '3'){
		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
		    }
		    else{
		    	var selectData = [];
		    	for(var i = 0; i < datagridList.length; i++)
		    	{
		    		var temp={};
		    		temp.ID=datagridList[i].ID;
		    		temp.PAID_MONEY=datagridList[i].PAID_MONEY;
		    		selectData.push(temp);
		    	}
		    	var dataJson ={selectData:selectData};
		    	var url = _basePath+"/rentWrite/rentNotWrite!bank_Not_C_Submit.action?selectDateHidden="+JSON.stringify(dataJson);
				$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'><input id='FI_FAG' name='FI_FAG' value='15'><input id='APP_CREATE_TYPE' name='APP_CREATE_TYPE' value='1'></form>");
				$("#formRoll").submit();
		    }
	    }
	});
	
}


/********************************************************以下为设置单元格可修改***齐江龙********************************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		if(disNum==1){
			disNum=0;
			return jq.each(function() {
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
					for ( var i = 0; i < fields.length; i++) {
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field) {
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for ( var i = 0; i < fields.length; i++) {
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
			});
		}
	}
});

var editIndex = undefined;

function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#cyberBankNot_C_PageTable').datagrid('validateRow', editIndex)){
	$('#cyberBankNot_C_PageTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCell(index, field) {
	if(!$("input[type='checkbox']")[index+1].disabled){
		disNum=1;
		endEditing();
		$('#cyberBankNot_C_PageTable').datagrid('endEdit', index);
		$('#cyberBankNot_C_PageTable').datagrid('editCell', {index : index,field : field});
		editIndex=index;
	}
	else{
		if(editIndex != undefined){
			$('#cyberBankNot_C_PageTable').datagrid('endEdit', editIndex);
			editIndex=undefined;
		}
	}
}
/********************************************************以上为设置单元格可修改***齐江龙********************************************************************************/

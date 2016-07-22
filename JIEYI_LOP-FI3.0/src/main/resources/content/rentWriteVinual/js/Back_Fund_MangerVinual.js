function dd(){
$("#fund_Back_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'rentWriteVinual!query_Back_Fund_Page.action',
		onSelect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onSelectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		columns:[[
		          	{field:'ID',checkbox:true,width:100},
		          	{field:'UPLOAD_TYPE',title:'状态',align:'center',width:100,formatter:function(value,rowData,rowIndex){
		          		if(value == '0'){
		          			 return "未导出";
		          		}else{
	                	  return "已导出";
	                	}
	                  }},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:100,align:'center'},
	                {field:'CUSTNAME',title:'客户名称',width:150,align:'center'},
		          	{field:'COMPANY_NAME',title:'厂商',width:150,align:'center'},
		          	{field:'SUPPLIERS_NAME',title:'经销商',width:150,align:'center'},
		          	{field:'PRODUCT_NAME',title:'品牌',width:150,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:150,align:'center'},
		          	{field:'BEGINNING_NAME',title:'款项名称',width:100,align:'center'},
		          	{field:'BEGINNING_NUM',title:'期次',width:50,align:'center'},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:100,align:'center'},
		       //	  	{field:'BEGINNING_MONEY',title:'应收金额',width:100},
		       //   	{field:'BEGINNING_PAID',title:'实收金额',width:100},
		       //   	{field:'VINUAL_MONEY',title:'虚拟收入金额',width:100},
		          	{field:'VINUAL_MONEY',title:'退款金额',width:100,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款帐号',width:200,align:'center'},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'SUP_ID',hidden:true}
		         ]]
	});

}

function onChangeSelect()
{
	var datagridList=$("#fund_Back_PageTable").datagrid('getSelections');
	var pages=$(".pagination-num").val();
	var rowss=$(".pagination-page-list").val();
	
	var BEGINNING_MONEYAll=0;
	var NUM=0;
	
	for(var i = 0; i < datagridList.length; i++)
	{
		var jj=datagridList[i].ROWNO-(pages-1)*rowss;
		if(!$("input[type='checkbox']")[jj].disabled){
			var BEGINNING_MONEY=datagridList[i].VINUAL_MONEY;
			BEGINNING_MONEYAll=fomatFloat(accAdd(BEGINNING_MONEYAll,BEGINNING_MONEY),2);
			NUM++;
		}
	}
	$("#FI_PROJECT_NUM").val(NUM);
	$("#FI_PAY_MONEY").val(BEGINNING_MONEYAll);
}

//驳回
function ISBack_BOHUI(){
	$("#divFrom").empty();
	var datagridList=$('#fund_Back_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续驳回操作！");
		return false;
	}
	
	$.messager.confirm("提示","您确定对选中的数据驳回？",function(flag){
		if(flag){
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG=datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson ={selectData:selectData};
			var url = _basePath+"/rentWrite/rentWriteVinual!fund_Back_BoHui.action?selectDateHidden="+JSON.stringify(dataJson);
			$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'></form>");
			$("#formRoll").submit();
		}
	});
}

//驳回
function ISBack_ChongZ(){
	$("#divFrom").empty();
	var datagridList=$('#fund_Back_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续重置操作！");
		return false;
	}
	
	$.messager.confirm("提示","您确定对选中的数据重置？",function(flag){
		if(flag){
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG=datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson ={selectData:selectData};
			var url = _basePath+"/rentWrite/rentWriteVinual!fund_Back_ChongZ.action?selectDateHidden="+JSON.stringify(dataJson);
			$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'></form>");
			$("#formRoll").submit();
		}
	});
}

function ISBack_Fund(){
	$("#divFrom").empty();
	var datagridList=$('#fund_Back_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	
	
	var FI_ACCOUNT_DATE=$("input[name='FI_ACCOUNT_DATE']").val();
	
	if(FI_ACCOUNT_DATE == "" || FI_ACCOUNT_DATE == undefined || FI_ACCOUNT_DATE.length<=0){
		 alert("请输入退款日期！！");
		 return false;
	 }
	
	$.messager.confirm("提示","您确定对选中的数据退款？",function(flag){
		if(flag){
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{	
				datagridList[i].FI_ACCOUNT_DATE = FI_ACCOUNT_DATE;
				var temp={};
				temp.PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG=datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson ={selectData:selectData};
			var url = _basePath+"/rentWrite/rentWriteVinual!fund_Back_C_Submit.action?selectDateHidden="+JSON.stringify(dataJson)+"&FI_ACCOUNT_DATE="+FI_ACCOUNT_DATE + "&datagridList="+JSON.stringify(datagridList);
			$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'><input id='FI_FAG' name='FI_FAG' value='18'><input id='APP_CREATE_TYPE' name='APP_CREATE_TYPE' value='2'></form>");
			$("#formRoll").submit();
		}
	});
}


function ISBack_Fund_POOL(){
	$("#divFrom").empty();
	var datagridList=$('#fund_Back_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	
	var FI_ACCOUNT_DATE=$("input[name='FI_ACCOUNT_DATE']").val();
	
	if(FI_ACCOUNT_DATE == "" || FI_ACCOUNT_DATE == undefined || FI_ACCOUNT_DATE.length<=0){
		 alert("请输入退款日期！！");
		 return false;
	 }
	
	$.messager.confirm("提示","您确定对选中的数据退款到租金池？",function(flag){
		if(flag){
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG=datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson ={selectData:selectData};
			var url = _basePath+"/rentWrite/rentWriteVinual!fund_Back_C_Submit.action?selectDateHidden="+JSON.stringify(dataJson)+"&FI_ACCOUNT_DATE="+FI_ACCOUNT_DATE;
			$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'><input id='FI_FAG' name='FI_FAG' value='19'><input id='APP_CREATE_TYPE' name='APP_CREATE_TYPE' value='2'></form>");
			$("#formRoll").submit();
		}
	});
}

function ISBack_UPLOAD(){
	$("#divFrom").empty();
	var LEASE_CODE=$("input[name='LEASE_CODE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var UPLOAD_TYPE=$("select[name='UPLOAD_TYPE']").find("option:selected").val();
	var COM_NAME=$("select[name='COM_NAME']").find("option:selected").val();
	var PRODUCT_NAME=$("select[name='PRODUCT_NAME']").find("option:selected").val();
	var SUPPER_NAME=$("input[name='SUPPER_NAME']").val();
	
	var url= _basePath+"/rentWrite/rentWriteVinual!pay_Back_Excle.action?LEASE_CODE="+LEASE_CODE+"&CUST_NAME="+CUST_NAME+"&UPLOAD_TYPE="+UPLOAD_TYPE+"&COM_NAME="+COM_NAME+"&PRODUCT_NAME="+PRODUCT_NAME+"&SUPPER_NAME="+SUPPER_NAME;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}


/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}

function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);   
}
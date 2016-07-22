$(document).ready(function(){
	$('#fundMangerForm').datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'ID',align:'center',width:100,title:'付款单号'},
		          {field:'FI_FLAG',title:'核销方式',width:150,formatter:function(value,rowData,rowIndex){
		        	  	if(value=='2')
		          		{
		          			return "租金-网银";
		          		}
		        	  	else if(value=='3')
		          		{
		          			return "租金-非网银";
		          		}
		          		else if(value=='4')
		          		{
		          			return "供应商垫付-网银";
		          		}
		          		else if(value=='5')
		          		{
		          			return "供应商垫付-非网银";
		          		}
		          		else if(value=='15')
		          		{
		          			return "租金-网银-不足额";
		          		}
		          		else if(value=='16')
		          		{
		          			return "供应商垫付-网银-虚拟";
		          		}
		          		else if(value=='17')
		          		{
		          			return "供应商垫付-非网银-虚拟";
		          		}
	                	  
	                  }},
	              {field:'COMPANY_NAMES',align:'center',width:200,title:'厂商'},
	              {field:'SUPPLIER_NAMES',align:'center',width:200,title:'供应商'},
	              {field:'PRODUCT_NAMES',align:'center',width:200,title:'租赁物名称'},
	              {field:'D_CLIENT_NAME',align:'center',width:200,title:'承租人'},
	              {field:'D_PROJECT_CODE',align:'center',width:150,title:'项目编号'},
	              {field:'D_PAY_CODE',align:'center',width:150,title:'还款计划编号'},
	              {field:'D_PAY_PROJECT',align:'center',width:100,title:'来款项目'},
	              {field:'PERIOD',align:'center',width:100,title:'期次'},
	              {field:'D_PAY_MONEY',align:'center',width:100,title:'付款金额'},
	              {field:'D_RECEIVE_DATE',align:'center',width:100,title:'应收日期'},
	              {field:'FI_ACCOUNT_DATE',align:'center',width:100,title:'核销日期'},
	              {field:'FI_STATUS',title:'状态',width:150,formatter:function(value,rowData,rowIndex){
		        	  	if(value=='0')
		          		{
		          			return "已申请未提交";
		          		}
		        	  	else if(value=='2')
		          		{
		          			return "已核销";
		          		}
		          		else if(value=='3')
		          		{
		          			return "已提交核销失败";
		          		}
		          		else if(value=='4')
		          		{
		          			return "已驳回";
		          		}
		          		else if(value=='5')
		          		{
		          			return "已提交未核销";
		          		}
	                  }},
	               {field:'UPLOADTYPE',title:'导出状态',width:100,formatter:function(value,rowData,rowIndex){
	                	  	var FI_FLAG=rowData.FI_FLAG;
			        	  	if(value=='0' && (FI_FLAG=='2' || FI_FLAG=='4'))
			          		{
			          			return "未导出";
			          		}
			        	  	else if(value=='1')
			          		{
			          			return "已导出";
			          		}
			          		
		          }},
	              {field:'FI_TO_THE_PAYEE',align:'center',width:200,title:'来款人'},
	              {field:'FI_TO_THE_ACCOUNT',align:'center',width:200,title:'来款账号'},
	              {field:'FI_REALITY_BANK',align:'center',width:200,title:'核销银行'}
		    ]]
	})         
	
});


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
}
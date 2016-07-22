$(document).ready(function(){
var PAYMENT_JBPM_ID=$("input[name='PAYMENT_JBPM_ID']").val();
var TASK_NAME=$("input[name='TASK_NAME']").val();
$("#eq_Head_Query_PageTable").datagrid({
		url:_basePath+"/bpm/JbpmFi!pay_Head_Query_JBPM_Mg_AJAX.action",
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		queryParams:{"PAYMENT_JBPM_ID":PAYMENT_JBPM_ID,"TASK_NAME":TASK_NAME},
		columns:[[
		          	{field:'ID',hidden:true},
		          	{field:'SUPPER_NAME',title:'供应商',width:100},
		          	//{field:'COMP_NAME',title:'厂商',width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:100},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:100},
		          	{field:'DISCRETIONARY_FUNDS',title:'自主资金',width:100,
		          	formatter:function(value,rowData,rowIndex){
		          		if(value != null && value !='')
		          		{
		          			return value+"&nbsp;&nbsp;<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updFK(" + JSON.stringify(rowData) + ")'>修改</a> ";
		          		}
	                  }},
		          	{field:'DEDUCTION_RATE',title:'抵扣比例',width:100},
		          	{field:'DEDUCTION_MONEY',title:'抵扣金额',width:100},
		          	{field:'LAST_MONEY',title:'实际放款金额',width:100},
		          	{field:'ACCBILL_PRICE',title:'承兑汇票',width:100},
		          	{field:'BANKBILL_PRICE',title:'银行汇款',width:100},
		          	{field:'LIMIT_BOND',title:'额度保证金',width:100},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:100},
		          	{field:'PROJECT_NUM',title:'项目数量',width:50},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200},
		          	{field:'PAY_BANK_ACCOUNT',title:'付款帐号',width:200}
		         ]],view:detailview,
			 		detailFormatter : function(index, row) {
			 			if(null!=row.ID){
			 			return '<div id="ddv-' + row.ID + '" style="padding:5px 0;"></div>';
			 			}
			 		},
					onExpandRow : function(index, row) {
						jQuery.ajax({
							url:_basePath+"/bpm/JbpmFi!pay_Detail_Query_Eq_Date.action?HEAD_ID="+row.ID,  
							type:'post',
							dataType:'json',
						    success: function(json){
								var data = {flag:json.flag,total:json.data.length,rows:json.data};
								var pRowIndex = "ddv-"+row.ID;
								$('#ddv-'+row.ID).datagrid({
									fitColumns:true,
									columns:[[
					                            {field:'PROJECT_CODE',align:'center',width:150,title:'项目编号'},
					                            {field:'CUST_NAME',align:'center',width:200,title:'客户名称'},
					                            //{field:'PRODUCT_NAMES',align:'center',width:200,title:'租赁物名称'},
					                            //{field:'SPEC_NAME',align:'center',width:150,title:'机型'},
					                            //{field:'WHOLE_ENGINE_CODES',align:'center',width:150,title:'出厂编号'},
					                            //{field:'EQUIPMENT_AMOUNTS',align:'center',width:50,title:'台量'},
					                            //{field:'DELIVERY_DATE',align:'center',width:150,title:'交货时间'},
					                            //{field:'DELIVERY_ADDR',align:'center',width:200,title:'交货地点'},
					                            {field:'START_DATE',align:'center',width:150,title:'起租确认日'},
					                            {field:'PAY_MONEY',align:'center',width:150,title:'实际放款金额'},
					                            {field:'PAY_NAME',align:'center',width:150,title:'款项名称'},
					                            {field:'RESERVE_DATE',align:'center',width:150,title:'预付日期'},
					                            {field:'PAYEE_NAME',align:'center',width:200,title:'收款单位'},
					                            {field:'PAY_BANK_ACCOUNT',align:'center',width:150,title:'放款账号'},
					                            //{field:'INVOICE_NUM',align:'center',width:150,title:'发票号'},
					                            //{field:'INVOICE_DATE',align:'center',width:150,title:'发票日期'},
					                            {field:'PAYMENT_NAME',align:'center',width:150,title:'放款方式'}
									         ]],
									onResize:function(){
				                        $('#eq_Head_Query_PageTable').datagrid('fixDetailRowHeight',index);
				                    },
				                    onLoadSuccess:function(){
				                        setTimeout(function(){
				                            $('#eq_Head_Query_PageTable').datagrid('fixDetailRowHeight',index);
				                        },0);
				                    }
								});
								 $('#eq_Head_Query_PageTable').datagrid('fixDetailRowHeight',index);
									$('#ddv-'+row.ID).datagrid("loadData",data);
						}
					});
			 		}
	})
});
function exp_Pay_Detail(){
	$("#divFrom").empty();
	var JBPM_ID=$("input[name='PAYMENT_JBPM_ID']").val();
	var url = "JbpmFi!pay_Head_Excle_Head_Mg.action?PAYMENT_JBPM_ID="+JBPM_ID;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
	
}

function exp_Pay_ProDetail(){
	$("#divFrom").empty();
	var JBPM_ID=$("input[name='PAYMENT_JBPM_ID']").val();
	var url = "JbpmFi!pay_Head_Excle_Detail_Mg.action?PAYMENT_JBPM_ID="+JBPM_ID;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
	
}
function updFK(row)
{
	 var ID=row.ID;
	 $("#DISCRETIONARY").dialog('open');
	 $("#ID").val(ID);
}
function closeDailogExp()
{
	$("#DISCRETIONARY").dialog('close');
}
function toSaveExperience()
{
	$.ajax({
		url:_basePath+"/bpm/JbpmFi!doUpdtDISCRETIONARY.action",
		data:"ID="+$("#ID").val()+"&DISCRETIONARY_FUNDS="+$("#DISCRETIONARY_FUNDS").val(),
		type:"post",
		dataType:"json",
		success:function(data){
		  if(data.flag==false){
			  //消息弹出： 添加失败消息
			  $.messager.show({
				  title:'自有资金修改',
				  msg:data.msg,
				  showType:'show'
			   });
			//关闭弹出框
			  $("#DISCRETIONARY").dialog('close'); 
			  //刷新页面
		  }else{
			  //消息弹出： 添加成功消息
			  $.messager.show({
				  title:'自有资金修改',
				  msg:data.msg,
				  showType:'show'
			   });
			  $("#DISCRETIONARY").dialog('close');//关闭弹出框
			//刷新页面
			  $('#eq_Head_Query_PageTable').datagrid('load', {
					"CREATEUSERCODE" : $("#CREATEUSERCODE").val(),
					"PAYMENT_JBPM_ID" : $("#PAYMENT_JBPM_ID").val(),
					"JBPM_ID" : $("#JBPM_ID").val(),
					"TASK_NAME" : $("#TASK_NAME").val(),
					"SUPPLIER_ID" : $("#SUPPLIER_ID").val(),
				});
		  }
		}
	});
}

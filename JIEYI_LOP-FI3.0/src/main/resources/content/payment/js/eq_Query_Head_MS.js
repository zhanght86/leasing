$(document).ready(function(){
$("#eq_Head_Query_PageTable").datagrid({
		url:"payment!pay_Head_Query_Eq_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',hidden:true},
		          	{field:'SUPPER_NAME',title:'出卖人',width:200,align:'center'},
		          	//{field:'COMP_NAME',title:'厂商',width:200},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:150,align:'center'},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:150,align:'center'},
		          	{field:'DEDUCTION_RATE',title:'抵扣比例',width:100,align:'center'},
		          	{field:'DEDUCTION_MONEY',title:'抵扣金额',width:150,align:'center'},
		          	{field:'LAST_MONEY',title:'实际放款金额',width:150,align:'center'},
		          	{field:'ACCBILL_PRICE',title:'承兑汇票',width:150,align:'center'},
		          	{field:'BANKBILL_PRICE',title:'银行汇款',width:150,align:'center'},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:150,align:'center'},
		          	{field:'REALITY_DATE',title:'实付日期',width:150,align:'center'},
	                {field:'REALITY_BANK_NAME',title:'核销银行',width:150,align:'center'},
		          	{field:'PROJECT_NUM',title:'项目数量',width:50,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'付款帐号',width:200,align:'center'},
		          	{field:'FLAG',title:'状态',width:150,align:'center'}
		         ]],view:detailview,
			 		detailFormatter : function(index, row) {
			 			return '<div id="ddv-' + row.ID + '" style="padding:5px 0;"></div>';
			 		},
					onExpandRow : function(index, row) {
						jQuery.ajax({
							url:_basePath+"/payment/payment!pay_Detail_Query_Eq_Date.action?HEAD_ID="+row.ID,  
							type:'post',
							dataType:'json',
						    success: function(json){
								var data = {flag:json.flag,total:json.data.length,rows:json.data};
								var pRowIndex = "ddv-"+row.ID;
								$('#ddv-'+row.ID).datagrid({
									fitColumns:true,
									columns:[[
									          	{field:'STATUS_FLAG',align:'center',width:200,title:'状态'},
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
					                            //{field:'PAYMENT_NAME',align:'center',width:150,title:'放款方式'}
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

function exportHeadData(){

	$("#divFrom").empty();
	var PAYMENT_CODE=$("input[name='PAYMENT_CODE']").val();
	var SUPPER_NAME=$("input[name='SUPPER_NAME']").val();
	var PAYEE_NAME=$("input[name='PAYEE_NAME']").val();
	var BEGGIN_DATE1=$("input[name='BEGGIN_DATE1']").val();
	var BEGGIN_DATE2=$("input[name='BEGGIN_DATE2']").val();
	var STATUS=$("select[name='STATUS']").find("option:selected").val();
	var COMP_ID=$("select[name='COMP_ID']").find("option:selected").val();
	var url="payment!pay_Head_Excle_Eq_Mg.action?PAYMENT_CODE="+PAYMENT_CODE+"&SUPPER_NAME="+SUPPER_NAME+"&PAYEE_NAME="+PAYEE_NAME+"&BEGGIN_DATE1="+BEGGIN_DATE1+"&BEGGIN_DATE2="+BEGGIN_DATE2+"&STATUS="+STATUS+"&COMP_ID="+COMP_ID;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
	
}

function exportDetailData(){
	$("#divFrom").empty();
	var PAYMENT_CODE=$("input[name='PAYMENT_CODE']").val();
	var SUPPER_NAME=$("input[name='SUPPER_NAME']").val();
	var PAYEE_NAME=$("input[name='PAYEE_NAME']").val();
	var BEGGIN_DATE1=$("input[name='BEGGIN_DATE1']").val();
	var BEGGIN_DATE2=$("input[name='BEGGIN_DATE2']").val();
	var STATUS=$("select[name='STATUS']").find("option:selected").val();
	var COMP_ID=$("select[name='COMP_ID']").find("option:selected").val();
	var url="payment!pay_Detail_Excle_Eq_Mg.action?PAYMENT_CODE="+PAYMENT_CODE+"&SUPPER_NAME="+SUPPER_NAME+"&PAYEE_NAME="+PAYEE_NAME+"&BEGGIN_DATE1="+BEGGIN_DATE1+"&BEGGIN_DATE2="+BEGGIN_DATE2+"&STATUS="+STATUS+"&COMP_ID="+COMP_ID;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}
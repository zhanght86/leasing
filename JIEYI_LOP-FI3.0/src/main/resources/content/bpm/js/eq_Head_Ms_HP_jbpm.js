$(document).ready(function(){
	$("#PAYMENT_CASE").dialog('close');
var PAYMENT_JBPM_ID=$("input[name='PAYMENT_JBPM_ID']").val();
$("#eq_Head_Query_PageTable").datagrid({
		url:_basePath+"/bpm/JbpmFi!pay_Head_Query_JBPM_Mg_AJAXJSON.action",
		rownumbers:true,//左侧自动显示行数
		showFooter: true,
		toolbar:'#pageForm',
		queryParams:{"PAYMENT_JBPM_ID":PAYMENT_JBPM_ID},
		frozenColumns:[[
		    {field:'aaa',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
		    	    if(null != rowData.ID){
		    	    	return "<a href='javascript:void(0);' onclick=payMentUForm(" + JSON.stringify(rowData) + ")>修改</a>   <a href='javascript:void(0);' onclick=delPaymentHead(" + JSON.stringify(rowData) + ")>剔除</a>";  
		    	    }
		    }}
		]],		
		columns:[[
		          	{field:'ID',hidden:true},
		          	{field:'SUPPER_NAME',title:'供应商',width:100},
		          	//{field:'COMP_NAME',title:'厂商',width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:100},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:100},
		          	{field:'DEDUCTION_RATE',title:'抵扣比例',width:100},
		          	{field:'DEDUCTION_MONEY',title:'抵扣金额',width:100},
		          	{field:'LAST_MONEY',title:'实际放款金额',width:100},
		          	{field:'ACCBILL_PRICE',title:'承兑汇票',width:100},
		          	{field:'BANKBILL_PRICE',title:'银行汇款',width:100},
		          	{field:'LIMIT_BOND',title:'额度保证金',width:100},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:100},
		          	//{field:'PROJECT_NUM',title:'项目数量',width:50},
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
					                           // {field:'PAYMENT_NAME',align:'center',width:150,title:'放款方式'},
												{field:'aaa',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
													if(null!= rowData.CUST_NAME){
														return "<a href='javascript:void(0);' onclick=delPaymentProject(" + JSON.stringify(rowData) +"," + JSON.stringify(row) + ")>剔除</a>";  
													}
													
												}}
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

function payMentUForm(row){
	if (row){
		 var PAYMENT_CODE=row.PAYMENT_CODE;
		 var ID=row.ID;
		 var PAYMENT_MONEY=row.LAST_MONEY;
		 var ACCBILL_PRICE=row.ACCBILL_PRICE;
		 var BANKBILL_PRICE=row.BANKBILL_PRICE;
		 
		 $("#PAYMENT_CODE_U").val(PAYMENT_CODE);
		 $("#ID_U").val(ID);
		 $("#PAYMENT_MONEY_U").val(PAYMENT_MONEY);
		 $("#ACCBILL_PRICE_U").val(ACCBILL_PRICE);
		 $("#BANKBILL_PRICE_U").val(BANKBILL_PRICE);
		 $("#PAYMENT_CASE").dialog('open');
	}else{
		$.messager.alert("请选择一条付款单!");
	}
}

function paseDouble(vale){
	var PAYMENT_MONEY=$("#PAYMENT_MONEY_U").val();
	if(vale=='ACCBILL_PRICE_U'){
		var ACCBILL_PRICE=$("#ACCBILL_PRICE_U").val();
		var BANKBILL_PRICE=jian(PAYMENT_MONEY,ACCBILL_PRICE);
		$("#BANKBILL_PRICE_U").val(BANKBILL_PRICE);
	}
	else{
		var BANKBILL_PRICE=$("#BANKBILL_PRICE_U").val();
		var ACCBILL_PRICE=jian(PAYMENT_MONEY,BANKBILL_PRICE);
		$("#ACCBILL_PRICE_U").val(ACCBILL_PRICE);
	}
}

//精确计算浮点数 网上C的 减法
function jian(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
    try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
    m=Math.pow(10,Math.max(r1,r2));
    //last modify by deeka
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}

function subPayment(){
	
	var ID=$("#ID_U").val();
	var ACCBILL_PRICE=$("#ACCBILL_PRICE_U").val();
	var BANKBILL_PRICE=$("#BANKBILL_PRICE_U").val();
	
	var PAYMENT_CODE=$("#PAYMENT_CODE_U").val();
	jQuery.ajax({
		type:"post",
		url:"JbpmFi!pay_Head_Eq_Update_HP.action?ID="+ID+"&ACCBILL_PRICE="+ACCBILL_PRICE+"&BANKBILL_PRICE="+BANKBILL_PRICE,
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改付款单："+PAYMENT_CODE+"成功！");
				$("#PAYMENT_CASE").dialog('close');
				$("#eq_Head_Query_PageTable").datagrid('reload');
			}else{
				alert("修改付款单失败"+json.msg);
			}
		},
	error:function(e){alert("修改付款单失败");}   
	});
}

//只能输入数字和小数点(全部清空)
function doubleValue1(ele)
{
	var value=ele.value;
	if(!(/[^\d{1,100}\.]/.test(value)))
	{
		ele.value=value.replace(/[^\d{1,100}\.]/,"");
	}
	else
	{
		ele.value="0";
	}
		
}


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


function delPaymentHead(rowData){
	
	if(confirm("供应商:"+rowData.SUPPER_NAME+" ,确定剔除 ?")){
		jQuery.ajax({
			type:"post",
			url:"JbpmFi!pay_Head_Eq_Del_Supper.action?ID="+rowData.ID,
			dataType:"json",
			success:function(e){
				if(e.flag){
					$.messager.alert("提示","剔除供应商："+rowData.SUPPER_NAME+"成功!");
					$("#eq_Head_Query_PageTable").datagrid('reload');
				}else{
					$.messager.alert("提示","修改付款单失败"+e.msg);
				}
			},
		error:function(e){$.messager.alert("提示","修改付款单失败");}   
		});	
	}
	
}


function delPaymentProject(rowData,rowDataParent){
	if(rowDataParent.BANKBILL_PRICE < rowData.PAY_MONEY ){
		$.messager.alert("提示","不允许剔除,项目实际放款金额大于银行汇款金额!");
		return ;
	}
	
	if(confirm("项目编号:"+rowData.PROJECT_CODE+" ,确定剔除 ?")){
		jQuery.ajax({
			type:"post",
			url:"JbpmFi!pay_Head_Eq_Del_Project.action?PROJECT_CODE="+rowData.PROJECT_CODE+"&ID="+rowDataParent.ID+"&PAY_MONEY="+rowData.PAY_MONEY,
			dataType:"json",
			success:function(e){
				if(e.flag){
					$.messager.alert("提示","剔除项目："+rowData.PROJECT_CODE+"成功!");
					$("#eq_Head_Query_PageTable").datagrid('reload');
				}else{
					$.messager.alert("提示","修改付款单失败"+e.msg);
				}
			},
		error:function(e){$.messager.alert("提示","修改付款单失败");}   
		});	
	}
	
}
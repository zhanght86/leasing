$(function() {
	$('#toMgPayment').datagrid({
		url:_basePath+"/rePayment/RePayment!toMgPayment.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[ 	 	 	 	 	 	 	 	 	 	 	 	
		          //{field:'ID',align:'center',width:10},
		          {field:'operate',align:'center',width:40,title:'操作',formatter:function(value,rowData,rowIndex){
                	  var url = "";                
                	  if(rowData.STATUS=="2	"||rowData.STATUS==undefined){
                		   url += "<a href='../rePayment/RePayment!toPayPlan.action?BID="+rowData.BID+"&PROJECT_ID="+rowData.ID
                		  				+"&STATUS="+rowData.STATUS+"&LOAN_AMUNT="+rowData.LOAN_AMOUNT+"&PAY_TIME="+rowData.LOAN_TIME+"'>方案录入</a>";
                	  }else if(rowData.STATUS=="1"){
                		   url +=  "<a href='../rePayment/RePayment!toPayPlan.action?BID="+rowData.BID+"&PROJECT_ID="+rowData.ID
  		  								+"&STATUS="+rowData.STATUS+"&LOAN_AMUNT="+rowData.LOAN_AMOUNT+"&PAY_TIME="+rowData.LOAN_TIME+"'>方案确认</a>";
 		        	  } else if(rowData.STATUS=="3"){
 		        		  url += "<a href='../rePayment/RePayment!toaddPayPlan.action?PROJECT_ID="+rowData.ID+"&PAY_ID="+rowData.PAY_ID+"&holiday_pay_type="+rowData.HOLIDAYPAYMODE+"'>录入支付表</a>"
								      +"  |  <a href='../rePayment/RePayment!systemCreatePay.action?PROJECT_ID="+rowData.ID+"&PAY_ID="+rowData.PAY_ID
								      +"&LOAN_AMOUNT="+rowData.LOAN_AMOUNT+"&TERM="+rowData.TERM+"&PERIOD="+rowData.PERIOD+"&INTEREST="+rowData.INTEREST
								      +"&PAY_TYPE="+rowData.PAY_TYPE+"&PAY_DATE="+rowData.PAY_DATE+"&holiday_pay_type="+rowData.HOLIDAYPAYMODE+"'>系统生成支付表</a>";
 		        	  } else if(rowData.STATUS=="4"){
 		        		   url +="<a href='../rePayment/RePayment!toUpdatePayPlan.action?PROJECT_ID="+rowData.ID+"&PAY_ID="+rowData.PAY_ID+"&jump_type=3' >修改还款计划书</a> | " +
 		        		  				"<a href='../rePayment/RePayment!toqueryPayDetail.action?PROJECT_ID="+rowData.ID+"&PAY_ID="+rowData.PAY_ID+"&jump_type=3' >确认还款计划书</a>";
 		        	  } else if(rowData.STATUS=="2"){
 		        		   url += "<a href='../rePayment/RePayment!toqueryPayDetail.action?PROJECT_ID="+rowData.ID+"&PAY_ID="+rowData.PAY_ID+"&jump_type=1' >查看还款计划书</a>";
 		        	  } else if(rowData.STATUS=="5"||value=="6"){
 		        		  url += "<a href='../rePayment/RePayment!toqueryPayDetail.action?PROJECT_ID="+rowData.ID+"&PAY_ID="+rowData.PAY_ID+"&jump_type=3' >查看还款计划书</a> "
									  +"  |  <a id='btnaddBeiZhu' href='RePaymentDetail!queryActualRepay.action?PROJECT_ID="+rowData.ID+"&PAY_TYPE="+rowData.PAY_TYPE+"'/>查看还款明细</a>";
 		        	  }
		        	  return url;
                   }},
		          {field:'STATUS',align:'center',width:20,title:'任务状态',formatter:function(value, row, roeIndex){
		        	  if(value==undefined){
		        		  return "方案未录入";
		        	  }else if(value=="1"){
		        		  return "方案录入";
		        	  } else if(value=="2"){
		        		  return "方案确认";
		        	  }  else if(value=="3"){
		        		  return "还款计划书生成";
		        	  } else if(value=="4"){
		        		  return "还款计划书确认";
		        	  } else if(value=="5"){
		        		  return "开始还款";
		        	  } 
		          }},
		          {field:'ORGAN_NAME',align:'center',width:30,title:'融资机构'},
		          {field:'BAILOUTWAY_NAME',align:'center',width:30,title:'融资方式'},
		          {field:'PROJECT_NAME',align:'center',width:30,title:'项目名称'},
		          {field:'DEDUCT_ACCOUNT',align:'center',width:40,title:'还款账号'},
		          {field:'LOAN_TIME',align:'center',width:30,title:'放款日'},
		          {field:'LOAN_AMOUNT',align:'center',width:30,title:'放款金额'}
		          
		          ]]
	});
});

//搜索
function toSeacher(){
	$("#toMgPayment").datagrid('load',{"param":getFromData("#pageForm")});
}

//清空搜索结果
function clearQuery(){
	$("#CREDIT_DEADLINE1").datebox('clear');
	$("#CREDIT_DEADLINE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}
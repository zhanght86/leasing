$(function() {
	$('#loanMg').datagrid({
		url:_basePath+"/refundLoan/RefundLoan!toMgLoanData.action",
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
                	  if(value=="1"){
                		  return url =  "<a href='javascript:void(0);' onclick='toBankPass("+rowData.ID+")'>银行通过</a>" 
		        			  +" |　<a id='btnaddBeiZhu' href='javascript:void(0);' onclick='addBeiZhu("+rowData.ID+")'>放款失败</a>";
 		        	  } else if(value=="2"){
 		        		 return url = "<a href='javascript:void(0);' onclick='toStartTask("+rowData.ID+")'>发起付款流程</a> " ;
// 		        		|"+
//		        		    "<a href='../refundLoan/RefundLoan!getLoanEntryPage.action?PROJECT_ID="+rowData.ID+"'>添加项目</a> " +
//        		 		" |<a href='../refundLoan/RefundLoan!getUpdateLoanPage.action?PROJECT_ID="+rowData.ID+"'>财务</a>" +
//        		 		"  </br>|<a href='../refundLoan/RefundLoan!enterIntoFinance.action?PROJECT_ID="+rowData.ID+"'>银行</a> |
 		        	  } else if(value=="3"){
 		        		  return url = "<a id='getbtnBeiZhu' href='javascript:void(0);'  onclick='searchBeiZhu("+rowData.ID+")'>查看放款失败原因</a>";
 		        	  } else if(value=="5" || value=="6" || value=="4"){
 		        		  return "<a href='../refundLoan/RefundLoan!queryLoanDetail.action?PROJECT_ID="+rowData.ID+"'>查看放款信息</a>";
 		        	  } 
		        	  return url;
                   }},
		          {field:'PROJECT_STATUS',align:'center',width:20,title:'项目状态',formatter:function(value, row, roeIndex){
		        	  if(value=="1"){
		        		  return "提交银行";
		        	  } else if(value=="2"){
		        		  return "银行审核通过";
		        	  }  else if(value=="3"){
		        		  return "银行放款不通过";
		        	  } else if(value=="4"){
		        		  return "发起流程";
		        	  } else if(value=="5"){
		        		  return "放款成功";
		        	  } else if(value=="6"){
		        		  return "放款失败";
		        	  } else if(value=="0"){
		        		  return "内部审批通过";
		        	  }
		          }},
		          {field:'ORGAN_NAME',align:'center',width:30,title:'融资机构'},
		          {field:'BAILOUTWAY_NAME',align:'center',width:30,title:'融资方式'},
		          {field:'PROJECT_NAME',align:'center',width:30,title:'项目名称'},
		          {field:'PROJECT_CODE',align:'center',width:40,title:'融资业务编号'},
		          {field:'CREATE_TIME',align:'center',width:40,title:'项目创建时间'},
		          {field:'PAY_TOTAL',align:'center',width:40,title:'贷款总额'},
		          {field:'LOAN_AMOUNT',align:'center',width:40,title:'放款总额'}
		         
		          ]]
	});
});

function toStartTask(id){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/refundLoan/RefundLoan!appForPayment.action",
		data:"PROJECT_ID="+id,
		dataType:"json",
		success:function(json){
			if(json.flag==true){
				$.messager.alert("提示",json.msg+json.data);
				window.location.href = _basePath+"/refundLoan/RefundLoan!toMgLoan.action";
			}else{
				$.messager.alert("提示","发起付款流程失败");
			}
		}
	});
}

//银行通过
function toBankPass(id){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/refundLoan/RefundLoan!importLoanInfo.action",
		data:"PROJECT_ID="+id,
		dataType:"json",
		success:function(json){
			if(json.flag==true){
				$.messager.alert("提示","银行通过成功");
				window.location.href = _basePath+"/refundLoan/RefundLoan!toMgLoan.action";
			}else{
				$.messager.alert("提示","银行通过失败");
			}
		}
	});
}

//查询
function toSeacher(){
	$("#loanMg").datagrid('load',{"param":getFromData("#pageForm")});
}

//清空搜索结果
function clearQuery(){
	$("#CREATE_TIME").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

//添加备注弹出框   
function addBeiZhu(ID){
	$("#previewAddBeiZhu").dialog({
		title:"添加备注",
		closed:false,
		cache:false,
		buttons:[{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$("#previewAddBeiZhu").dialog('close');
			}
		},{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				if($("#LOANFAILURE").val() == ""){
					alert("放款失败原因不能为空");
				}else{
					jQuery.ajax({
						url:_basePath+"/refundLoan/RefundLoan!addLoanFailure.action",
    					type:"post",
    					data:"PROJECT_ID="+ID+"&LOANFAILURE="+$("#LOANFAILURE").val(),
    					dataType:"json",
    					success:function(json){
							if(json.flag==true){
								$.messager.alert("提示","添加放款失败原因成功！");
								window.loaction.href=_basePath+"/refundLoan/RefundLoan!toMgLoan.action?listype=-3";
							}else{
								$.messager.alert("提示","添加放款失败原因失败！");
							}
							$("#LOANFAILURE").val("");
						}
					});
					$("#previewAddBeiZhu").dialog('close');
				}
			}
		}]
	});
}

//查看失敗原因
function searchBeiZhu(seqId){
	jQuery.ajax({
		url:_basePath+"/refundLoan/RefundLoan!queryLoanFailure.action",
		type:"post",
		data:"PROJECT_ID="+seqId,
		dataType:'json',
		success:function(data){
			$("#LOANFAILURE2").val(data.data.LOANFAILURE);
		}
	});
	
	$("#getpreviewBeiZhu").dialog({
		title:"查看失败原因",
		closed:false,
		cache:false,
		buttons:[{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$("#getpreviewBeiZhu").dialog('close');
			}
		}]
	});
}
//
////确认资金到账
//function confrimLoan(seqId){
//	if(confirm("资金确认到账吗？")){
//		jQuery.ajax({
//			url:_basePath+"/refundLoan/RefundLoan!confrimLoan.action?PROJECT_ID="+seqId,
//			type:"post",
//			dataType:"json",
//			success:function(json){
//				if(json.flag==true){
//					alert("资金确认到账成功");
//				}else {
//					alert("资金确认到账失败");
//				}
//			}
//		});
//	}
//}
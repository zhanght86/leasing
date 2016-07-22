
function del(obj){
	$(obj).parent().parent().remove();
}
var i = 1;
function addR(){
	
	var tr=$("#bank_tr").clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","addData");
	$(tr).find("input[name='WRITE_TIME']").attr("class","easyui-datebox");
	//$(tr).find("input[name='DELETE']").attr("class","del");
	$("#addTbody").append(tr);
	
	$.parser.parse(tr);
	
//	var tempTR=$(".templete").clone();	
//	tempTR.find("input[name='WRITE_TIME']").attr("class","datebox");
//	$("#addTbody").append(tempTR.removeClass("hidden templete"));
	//$.parser.parse($("#WRITE_TIME"+i));
	return i++;
}

/**
 * 打开dialog
 * @param div
 * @return
 */
function toAddBankWater(div){
	$("#"+div).dialog('open');
//	$("#doAddBankWater").form('clear');
	//$("#CLIENT_ID").val($("#BankWater_CLIENT_ID").val());
	//$("#CREDIT_ID").val($("#BankWater_CREDIT_ID").val());
}
function toShowBankWater() {
	var row = $("#bankWater").datagrid('getSelected');
	 $("#QUERYBANK_NAME").val(row.BANK_NAME);
	 $("#QUERYBANK_OBJ").val(row.BANK_OBJ);
	 $("#QUERYOBJECT_NAME").val(row.OBJECT_NAME);
		$("#queryBankWater").dialog('open');
		$('#querysbankWater').datagrid('load', {
            "BANKWATER_ID" : row.ID
	   });
				
	
}
/**
 * 关闭dialog
 * @return
 */
function closeDailog(div){
	$("#"+div).dialog('close');
}

function toSaveBankWater(){
	var myData = [];
	$(".addData").each(function(){
		var temp={};
		temp.WRITE_TIME = $(this).find($("input[name=WRITE_TIME]")).val();
		temp.B_CASH_INFLOW = $(this).find($("input[name=B_CASH_INFLOW]")).val();
		temp.B_CASH_OUTFLOW = $(this).find($("input[name=B_CASH_OUTFLOW]")).val();
		temp.B_CASH_RESERVE = $(this).find($("input[name=B_CASH_RESERVE]")).val();
		myData.push(temp);
	});
	var data_ = "myData="+JSON.stringify(myData)
		+"&BANK_NAME="+$("#BANK_NAME_DATA").val()
		+"&BANK_OBJ="+$("select[name=BANK_OBJ_DATA]").attr("selected",true).val()
		+"&OBJECT_NAME="+$("#OBJECT_NAME_DATA").val()
	    +"&CREDIT_ID="+$("#CREDIT_ID").val()
	    +"&CLIENT_ID="+$("#CLIENT_ID").val();
	jQuery.ajax({
		url: _basePath+"/analysisBySynthesis/BankWater!doInertBANKWATER.action",
		data: data_,
		dataType:"json",
		success : function(data) {
			if(data.flag==false){
				  $.messager.alert('提示',"添加失败");
				  $("#addBankWater").dialog('close');
				  $("#bankWater").datagrid('load',{
					  "param":getFromData("#bankwater")
				  });
			  }else{
				  $.messager.alert('提示',"添加成功");
				  $("#addBankWater").dialog('close');
				  $("#bankWater").datagrid('load',{
					  "param":getFromData("#bankwater")
				  });
			  }
		}
	});
//	$("#doAddBankWater").form('submit',{
//		url:_basePath+"/analysisBySynthesis/BankWater!doInertBANKWATER.action",
//		success:function(data){
//		  data = eval("("+data+")");
//		  if(data.flag==false){
//			  $.messager.alert('提示',"添加失败");
//			  $("#addBankWater").dialog('close');
//			  $("#bankWater").datagrid('load',{
//				  "param":getFromData("#bankwater")
//			  });
//		  }else{
//			  $.messager.alert('提示',"添加成功");
//			  $("#addBankWater").dialog('close');
//			  $("#bankWater").datagrid('load',{
//				  "param":getFromData("#bankwater")
//			  });
//		  }
//		}
//	});
}

function doDelBankWater(){	
	$.messager.confirm('提示','确认删除?',function(r){
		if (r){
			var row = $("#bankWater").datagrid('getSelected');
			if (row){
				jQuery.ajax({
					url: _basePath+"/analysisBySynthesis/BankWater!doDelBANKWATER.action",
					data: "BANKWATER_ID="+row.ID,
					dataType:"json",
					success : function(data) {
						if (data.flag == true) {
							 //提示框： 添加企业股东及份额
							  $.messager.alert('提示',"删除成功");
							  $("#bankWater").datagrid('load',{
								  "param":getFromData("#bankwater")
							  });
						} else {
							 //提示框： 添加企业股东及份额
							$.messager.alert('提示',"删除失败");
							$("#bankWater").datagrid('load',{
								  "param":getFromData("#bankwater")
							  });
						}
					},
					error: function(e){
						$.messager.alert("操作失败， 请联系系统管理员");
					}
				});
			}else {
				alert("请选择需删除的数据");
			}
		}
	});
}
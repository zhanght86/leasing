function Claim(){
	//var row = $('#pageTable').datagrid('getSelected');
//	if (row){
//		 if(confirm("确定要认领该任务吗？")){
//			 jQuery.ajax({
//				url: _basePath+"/base/task/TaskClaim!Claim.action",
//				data: "ID="+row.ID,
//				dataType:"json",
//				success: function(res){
//					if(res.flag==true){
//						$.messager.alert("提示",res.msg);
//						$('#pageTable').datagrid('reload');
//				   }
//				   else{
//					   $.messager.alert("提示",res.msg);
//					   alert("操作失败请重试！");
//				   }
//				}
//					 
//			 });
//		 }
//	}
	jQuery.ajax({
		url: _basePath+"/base/task/TaskClaim!Claim.action",
		dataType:"json",
		success: function(res){
			if(res.flag==true){
				$.messager.alert("提示",res.msg);
				$('#pageTable').datagrid('reload');
		   }
		   else{
			   $.messager.alert("提示",res.msg);
		   }
		}
			 
	 });
}
function Allot(){
	$("#UserOut").dialog({
		autoOpen:false,
		title:'分配人员',
		reaziable:true,
		modal:true,
		width:250,
		height:150
	});
	$("#UserOut").dialog('close');
	jQuery.get("TaskClaim!getUsers.action?TASK_NAME="+TASK_NAME+"&ID="+row.ID,function(html){
		$("#UserOut").html(html);	
	});
	$("#UserOut").dialog("open");
}
function doRuleY(CODE,ID){
	jQuery.ajax({
		url: _basePath+"/base/task/TaskClaim!Allot.action",
		data: "ID="+ID+"&CODE="+CODE,
		dataType:"json",
		success: function(res){
			 if(res.flag==true){
					$.messager.alert("提示",res.msg);
					$("#UserOut").dialog('close');
					$('#pageTable').datagrid('reload');
			   }else{
				   $.messager.alert("提示",res.msg);
				   alert("操作失败请重试！");
			   }
		}
	 });
}


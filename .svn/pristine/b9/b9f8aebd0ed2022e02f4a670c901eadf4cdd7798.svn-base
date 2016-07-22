function newOne(){
	top.addTab("添加规则", _basePath+"/base/task/TaskAllocation!add.action");
}

function updateOne(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("修改规则",_basePath+"/base/task/TaskAllocation!upd.action?ID="+row.ID);
    }
}

function del(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		 if(confirm("确定要删除该规则吗？")){
			 jQuery.ajax({
				url: _basePath+"/base/task/TaskAllocation!del.action",
				data: "ID="+row.ID,
				dataType:"json",
				success: function(res){
					if(res.flag==true){
						$.messager.alert("提示",res.msg);
						$('#pageTable').datagrid('reload');
				   }
				   else{
					   $.messager.alert("提示",res.msg);
					   alert("操作失败请重试！");
				   }
				}
					 
			 });
		 }
	}
}
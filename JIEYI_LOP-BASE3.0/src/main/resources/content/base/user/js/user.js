function newOne(){
	top.addTab("添加员工", _basePath+"/base/user/Manage!add.action");
}

function updateOne(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("员工信息修改",_basePath+"/base/user/Manage!show.action?EMPLOYEE_ID="+row.EMPLOYEE_ID);
    }
}

function del(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		 if(confirm("确定要删除该员工吗？")){
			 jQuery.ajax({
				url: _basePath+"/base/user/Manage!delUser.action",
				data: "USER_ID="+row.EMPLOYEE_ID,
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
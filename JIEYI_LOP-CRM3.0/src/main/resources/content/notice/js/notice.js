function newOne(){
	top.addTab("添加公告", _basePath+"/notice/Notice!toAdd.action");
	
}

function updateOne(ID){
	//var row = $('#pageTable').datagrid('getSelected');
	
		top.addTab("公告修改",_basePath+"/notice/Notice!toUpdate.action?ID="+ID);
    
}

function del(ID){
		 if(confirm("确定要删除该公告吗？")){
			 jQuery.ajax({
				url: _basePath+"/notice/Notice!delNotice.action",
				data: "ID="+ID,
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
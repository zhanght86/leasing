function newOne(){
	top.addTab("添加广告", _basePath+"/advert/AdvertManager!toAdd.action");
	
}

function updateOne(ID){
	//var row = $('#pageTable').datagrid('getSelected');
	
		top.addTab("广告修改",_basePath+"/advert/AdvertManager!toUpdate.action?ID="+ID);
    
}

function del(ID){
	 if(confirm("确定要删除该公告吗？")){
		 jQuery.ajax({
			url: _basePath+"/advert/AdvertManager!delAd.action",
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
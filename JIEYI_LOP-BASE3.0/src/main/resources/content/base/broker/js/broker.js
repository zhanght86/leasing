function newOne(){
	top.addTab("添加经纪人", _basePath+"/base/broker/Broker!addSupPage.action");
}

function changeType(val){
	window.location.href = _basePath+"/base/broker/Broker!addSupPage.action?TYPE="+val;
}

function update(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
//		top.addTab("经纪人信息修改",_basePath+"/base/broker/Broker!toUpdateSuppliersMain.action?SUP_ID="+row);
		top.addTab("经纪人信息修改",_basePath+"/base/broker/Broker!modifySupPage.action?SUP_ID="+row);
    }
}

function del(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		 if(confirm("确定要删除该经纪人吗？")){
			 jQuery.ajax({
				url: "Broker!delSuppliers.action",
				data: "SUP_ID="+row,
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

function getChildArea(val,childId1,childId2){
	jQuery.ajax({
		url: _basePath+'/base/suppliers/Suppliers!getAddress.action?PARENT_ID='+val,
		type: 'post',
		dataType: 'json',
		success: function (json){
			//将本行的输入框初始化
			$("#"+childId1).each(function (){
				//初始化
				if ($(this).is("select")){
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
					
				}
			});
			//将本行的输入框初始化
			if(childId2!=null){
				$("#"+childId2).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
			}
			
			for(var i=0;i<json.address.length;i++){
				$("#"+childId1).append($("<option value='"+json.address[i].ID+"'>"+json.address[i].NAME+"</option>"));				
			}
		}
	});
}


/**
 * 判断配偶情况
 * 
 * @param val
 * @return
 */
function choustSpoust(val) {
//	if (val == "1Marriage" || val == "4Marriage" || val == "4" || val == "1") {
//		$("#marriage_type").attr("display", "");
//		$("#marriage_type").attr("style", "diaplay:block");
//	} else {
//		$("#marriage_type").attr("style", "display:none");
//	}
}
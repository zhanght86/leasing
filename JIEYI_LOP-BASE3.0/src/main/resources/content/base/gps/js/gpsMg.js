
function se(){
	var content=$("input[name='content']").val();
	$('#pageTable').datagrid('load', { "content":content});
}

function format(val,row){
	return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='showGPSInfo("+ row.ID +")'>"+val+"</a>";
}
function deal(val,row){
	if(val == '1'){
		return "解锁";
	}
	if(val == undefined){
		return "";
	}
		
	return "锁车";
}

function deal1(val,row){
	if(val == '1'){
		return "正常";
	}
	if(val == undefined){
		return "";
	}
	return "异常";
}

function showGPSInfo(id){
	top.addTab("设备GPS信息["+id+"]", _basePath+"/base/gps/GPSMG!getGPSInfo.action?ID="+id);
}



function se(){
	var content = {};
	$('#queryForm :input').each(function(){
		if ($(this).is("select")) {
			content[$(this).attr("name")] = $(this).find(":selected").val();
		} else {
			content[$(this).attr("name")] = $.trim($(this).val());
		}
	});
	$('#pageTable').datagrid("load",content);
}

function lock(id){
	if(confirm("确认要发起申请吗？")){
		$.ajax({
			url:_basePath+"/vehicleControl/VehicleControl!startControlByJbpm.action",
			data:'ID='+id,
			dataType:'json',
			type:'post',
			success:function(data){
				alert(data.msg);
				$('#pageTable').datagrid("load");
			}
		});
	}
}

function unlock(){
	
}
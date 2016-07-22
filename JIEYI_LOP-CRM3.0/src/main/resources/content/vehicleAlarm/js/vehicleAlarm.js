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

function alarmClose(id){
	if(confirm("确定要申请关闭报警吗？")){
		$.ajax({
			url:_basePath+'/vehicleAlarm/VehicleAlarm!startAlarmByJbpm.action',
			data:'ID='+id,
			dataType:'json',
			type:'post',
			success:function(data){
				alert(data.msg);
				se();
			}
		});
	}
}
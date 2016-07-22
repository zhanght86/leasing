function getValue(value,rowDate,inedx){
	return "<a href='javascript:void(0)' onclick='showAR("+value+")'>查看明细</a>";
}

function showAR(ID){
	top.addTab("查看绩效明细",_basePath+"/performance/AssessmentResult!getARDetailData.action?ID="+ID+"&_dateTime="+new Date());
}

//搜索
function conditionsSelect(){
	$('#pageTable').datagrid('load', {
		KH_DATE_START:$('#KH_DATE_START').datebox('getValue'),
		KH_DATE_END:$('#KH_DATE_END').datebox('getValue'),
		DEPARTMENT:$("#DEPARTMENT").val(),
		POST:$("#POST").val(),
		KH_NAME:$("#KH_NAME").val(),
		POST_LEVEL:$("#POST_LEVEL").val()
	});
}

function clearInput(){
	$("#pageForm input").val("");
}

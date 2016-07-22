function searchInfo(){
	var content = {};
	$('#pageForm :input').each(function(){
		content[$(this).attr('name')] = $(this).val();
	});
	console.debug(content);
	$('#pageTab').datagrid('load',content);
}
function apply(){
	var rows = $('#pageTab').datagrid('getSelections');
	var ids = '';
	if(rows.length<=0){
		return alert('请选择数据');
	};
	for(var i=0;i<rows.length;i++){
		ids +=rows[i].ID;
		if(i!=rows.length-1){
			ids +=','; 
		}
	}
	top.addTab("服务费申请",_basePath+"/serviceFee/ServiceFee!apply.action?IDS="+ids);
}
function serviceFeeApply(){
	var params = new Array();
	$(".mytable").each(function (){
		var $table = $(this);
		var jsonObj = {};
		$table.find("input").each(function(){
			if(this.name){
				jsonObj[this.name] = this.value;
			}
		});
		params.push(JSON.stringify(jsonObj));
	});
	console.debug(params);
	$.ajax({
		url:_basePath+'/serviceFee/ServiceFee!startServiceFeeByJbpm.action',
		data: "jsonStr="+params,
		dataType: 'json',
		type:'post',
		success: function(data){
			alert(data.msg+data.data);
		}
	});
}
function exportExcel(){
	var rows = $("#pageTab").datagrid("getSelections");
	var ids = '';
	if(rows.length<1){
		return alert("请选择导出的数据");
	}
	for(var i=0;i<rows.length;i++){
		ids += rows[i].ID;
		if(i!=rows.length-1){
			ids += ',';
		}
	}
	window.location.href=_basePath+"/serviceFee/ServiceFee!exportExcel.action?IDS="+ids;
}
function readExcel(){
	var fileName = $("#upFile").val();
	if(checkExcel(fileName)==false){
		return alert("请选择Excel文件！");
	}
	$.ajaxFileUpload({
		url:_basePath+'/serviceFee/ServiceFee!readExcel.action',
		dataType:'json',
		fileElementId:'upFile',
		success:function(json){
			var obj = JSON.parse(json);
			alert(obj.msg);
			$('#pageTab').datagrid('reload');
		}
	});
}
function checkExcel(ext){
	if(ext != null && ext != ''){
		var houzhui = ext.substring(ext.lastIndexOf(".")+1,ext.length);
		var str = new Array("xls","xlsx");
		for(var i=0;i<str.length;i++){
			if(houzhui==str[i]){
				return true;
			}
		}
		return false;
	}else{
		return false;
	}
}
function saveButton(){
	$("#subButton").attr("disabled","disabled");
	var flag = true;
	// 收集报表数据
	getDabtData();
	if(flag){
		$('#formView').form('submit',{
			 success:function(data){
			      data=eval("("+data+")");
				 if(data.flag==true){
					 $.messager.alert('提示', "保存成功", "保存成功");
				 }else {
					 $.messager.alert('提示', "保存失败", "保存失败");
				 }
			 }
		});
	}
}

function getDabtData() {
	var flag = true;
	$(".tbody > tr").each(function() {
		var name = $(this).attr("name");
		var inputs = $(this).find("input");
		var item = {};
		for ( var i = 0; i < inputs.length && flag; i++) {
			var value = $(inputs[i]).val();
			if (value.length > 0) {
				// 不为空
//			if (isNaN(value)) {
//				flag = false;
//
//				alert("请输入数字");
//				$(inputs[i]).focus();
//			}

		} else {
			// 为空，默认0
			value = "";
		}
		item[name + i] = value;
	}
	var input = $("<input>");
	$(input).attr("name", name);
	$(input).attr("type", "hidden");
	$(input).val(JSON.stringify(item));
	$("#formView").append($(input));
	});
	return flag;
}



/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var CNAME = $("input[name='CNAME']").val();
	var INSETDATE = $("input[name='INSETDATE']").val();
	$('#condition').datagrid('load', {
		"CNAME" : CNAME,
		"INSETDATE" : INSETDATE
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#INSETDATE").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 打开dialog
 * @param div
 * @return
 */
function toAddCondition(div){
	$("#"+div).dialog('open');
	$("#insetBCform").form('clear');
}


/**
 * 验证数据
 * @param val
 * @return
 */
function checkedData(){
	if(isNaN($("#BIDBONDRENT").val())){
		   alert("请填写数字信息");
		  return $("#BIDBONDRENT").val("");
	}
}

function doAddCondition() {
	var date="";
	$("#toAddCondition [name]").each(function(){
		var ele=$(this);
		date+="&"+ele.attr("name")+"="+ele.val();
	});
	date=date.substring(1,date.length);
	
	jQuery.ajax({
		url : _basePath+"/condition/Condition!doAjaxInsetBailoutCondition.action",
		data : date,
		dataType:'json',
		success : function(json)
		{
			if(json.flag==false){
				$.messager.alert("提示","添加失败");
				$("#toAddCondition").dialog('close');
			}else{
				$.messager.alert("提示","添加成功");
				$("#toAddCondition").dialog('close');
			}
			$("#condition").datagrid('load');
		}
	  });
}

/**
 * 关闭dialog
 * @return
 */
function closeAddCondition(div){
	$("#"+div).dialog('close');
}

/**
 * 取自数据库
 * @return
 */
function dataDictionary(){
	document.getElementById("dictionary").value="正在获取..";
	document.getElementById("dictionary").disabled=true;
	jQuery.ajax({
		url : _basePath+"/condition/Condition!insertDataDictionary.action",
		type:'post',
		dataType:'json',
		success : function(json)
		{
			if(json.flag==false){
				$.messager.alert("提示","从数据字典获取失败");
				$("#toAddCondition").dialog('close');
			}else{
				$.messager.alert("提示","从数据字典获取成功");
				$("#toAddCondition").dialog('close');
			}
			$("#condition").datagrid('load');
		}
	  });
}
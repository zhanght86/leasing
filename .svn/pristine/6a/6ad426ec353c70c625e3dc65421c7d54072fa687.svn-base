function toExcel() {
	var form = document.getElementById("frmSearch");
	var action = form.action;
	var temp = action;
	var span = document.getElementsByTagName("span");
	for ( var i = 0; i < span.length; i++) {
		var tmp = span[i].innerHTML;
		if (tmp.match(/^\d+$/g)) {
			form.PAGE_CURR.value = tmp;
		}
	}
	action = action.replace(/toManager/, "add");
	form.action = action;
	form.submit();
	form.PAGE_CURR.value = 1;
	form.action = temp;

}

function addFirst(first) {
	var id1 = document.getElementById("pqsoft_first").value;
	var id = new Date().getTime();
	$(first.parentElement.parentElement.parentElement)
			.append(
					" <tr><td ><font style='font-size:15px;font-weight:bold;'>大类标题:"
							+ "</font><input type='text' name='first_title"
							+ id1
							+ "' id='first_title"
							+ id
							+ "'> "
							+ "<a href='javascript:void(0)' onclick='addSecond(this,"
							+ id
							+ ")'> 添加条款</a> | <a href='javascript:void(0)' onclick='removeSecond(this,"
							+ id + ")'>删除</a></td></tr>");
}
function addSecond(second, id1) {
	var id = new Date().getTime();
	$(second.parentElement)
			.append(
					"<tr><td style='border: 0px solid blue ;'>条款名：<input type='text' name='second_title"
							+ id1
							+ "' id='second_title"
							+ id
							+ "'>"
							+ "<a href='javascript:void(0)' onclick='addThird(this,"
							+ id
							+ ")'> 添加选项</a> | <a href='javascript:void(0)' onclick='removeSecond(this,"
							+ id + ")'>删除</a></td></tr>");
}
function removeSecond(second) {
	$(second.parentElement).remove();
}

function addThird(third, id1) {
	var id = new Date().getTime();
	$(third.parentElement)
			.append(
					"<td style='width:200px;border: 0px solid blue ;'>选项：<input type='text' name='third_title"
							+ id1
							+ "' id='third_title"
							+ id
							+ "'><a href='javascript:void(0)' onclick='removeSecond(this,"
							+ id + ")'>删除</a></td>");

}

function editRenterType(val) {
	if (val == "3" || val == "4") {
		$("#renter_type option[value='1']").remove();
	} else {
		$("#renter_type option[value='1']").remove();
		$("#renter_type").append("<option value='1'>个人</option>");
	}
}
function editEvaluateType(val) {
	if(val == "1"){val=0;}else{val=1;}
	$("#EVALUATE_TYPE").empty();
	$("<option value=''>---请选择---</option>").appendTo("#EVALUATE_TYPE");
	jQuery.ajax({
		type:"post",
		async: true,
		url:_basePath+"/secuEvaluate/EvaluateDictionary!getTypesGradeMolud.action",
		data:"MAIN_TYPE=" + val,
		dataType:"json",
		success:function(json){
			for(var item in json.data.list){
				$("<option value='"+json.data.list[item].TYPE+"'>"+json.data.list[item].TYPE+"</option>").appendTo("#EVALUATE_TYPE");
			}
		}
	});
}

function addEvalu(val) {
	if(val == null || val == "" || val == "undefined"){
		return;
	}
	var id1 = document.getElementById("pqsoft_first").value;
	var id = new Date().getTime();
	$("#norm")
			.append(" <tr><td ><font style='font-size:13px;font-weight:bold;'>定量等级项:"
							+ "</font><input type='text' value="+val+" readonly name='evaluate_title"
							+ id1 + "' id='evaluate_title"
							+ id + "'> <a href='javascript:void(0)' onclick='removeSecond(this)'>删除</a></td></tr>");
}
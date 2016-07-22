function add(my, parentid) {
	var id = new Date().getTime();
	var parents = $(my).parent();
	parents
			.append("<div class='kk'>大类标题:"
					+ "<input id='first_title"
					+ id
					+ "' type='text' name='first_title"
					+ parentid
					+ "'>"
					+ "<a href='javascript:void(0)' onclick='addkk(this,"
					+ id
					+ ")'>添加条款</a> | <a href='javascript:void(0)' onclick='removeSecond(this)'>删除</a></div>");
}

function addkk(my, parentid) {
	var id = new Date().getTime();
	if (parentid > id) {
		id = parseInt(parentid) + parseInt(id);
	}
	var parents = $("div[id='" + parentid + "']").last();
	if (parents == null || parents.length <= 0) {
		parents = $(my).parent();
		parents
				.after("<div id='"
						+ parentid
						+ "' class='kkk'>"
						+ "条款名:<input type='text' name='second_title"
						+ parentid
						+ "'  id='second_title"
						+ id
						+ "'>"
						+ "<font color='red' id='"
						+ id
						+ "'></font>"
						+ "<a href='javascript:void(0)' onclick='addkkk(this,"
						+ id
						+ ")'>添加条款</a> | <a href='javascript:void(0)' onclick='removeSecond(this)'>删除</a></div>");
	} else {
		var tmps = $(
				"div[id="
						+ parents.children("input").attr("id").replace(/\D+/,
								"") + "]").last();
		if (tmps == null || tmps.length <= 0) {
			tmps = parents;
		}
		tmps
				.after("<div id='"
						+ parentid
						+ "' class='kkk'>"
						+ "条款名:<input type='text' name='second_title"
						+ parentid
						+ "'  id='second_title"
						+ id
						+ "'>"
						+ "<font color='red' id='"
						+ id
						+ "'></font>"
						+ "<a href='javascript:void(0)' onclick='addkkk(this,"
						+ id
						+ ")'>添加条款</a> | <a href='javascript:void(0)' onclick='removeSecond(this)'>删除</a></div>");

	}
}

function addkkk(my, parentid) {
	var id = new Date().getTime();
	if (parentid > id) {
		id = parseInt(parentid) + parseInt(id);
	}
	var parents = $("div[id='" + parentid + "']").last();
	if (parents == null || parents.length <= 0) {
		parents = $(my).parent();
	}
	parents
			.after("<div id='"
					+ parentid
					+ "' class='kkkk'><input id='third_title"
					+ id
					+ "' type='text' name='third_title"
					+ parentid
					+ "'> <a href='javascript:void(0)' onclick='removeSecond(this)'>删除</a></div>");

}

function removeSecond(second) {
	$(second.parentElement).remove();
}

function save() {
	var pqsoft_Name = $("input[name='pqsoft']").val();
	if (pqsoft_Name == null || pqsoft_Name == "" || pqsoft_Name == "undefined") {
		alert("请填写模版名称！");
		return;
	}
	var mains = $("#mains").val();
    if (mains == null || mains == "" || mains == "undefined") {
        alert("请选择主体！");
        return;
    }
	var renter_type = $("#renter_type").val();
    if (renter_type == null || renter_type == "" || renter_type == "undefined") {
        alert("请选择主体类型！");
        return;
    }
    
	
	$("#saveID").linkbutton('disable');
	var $input = $("input[type='text']");
	var inputs = $input.get();
	var payline = [];
	for ( var i = 0; i < inputs.length; i++) {
		var input = inputs[i];
		if (input.value == null || input.value == "") {
			continue;
		}
		var temp = {};
		var d13 = new RegExp("\\d{13}");
		if (input.name == 'pqsoft') {
			temp.id = input.id.match(d13)[0];
			temp.title = input.value;
			temp.mains = $("#mains").val();
			temp.renter_type = $("#renter_type").val();
			temp.cr_trade_type = $("#CR_TRADE_TYPE").val();
		} else if (input.name.search(/first_title/g) != -1) {
			temp.id = input.id.match(d13)[0];
			temp.title = input.value;
			temp.parentsid = input.name.match(d13)[0];
		} else if(input.name.search(/evaluate_title/g) != -1) {
			temp.id = input.id.match(d13)[0];
			temp.title = input.value;
			temp.parentsid = input.name.match(d13)[0];
			temp.norm = '1';
        } else if (input.name.search(/second_title/g) != -1) {
			temp.id = input.id.match(d13)[0];
			temp.title = input.value;
			temp.auto_value = $(input).attr("class");
			temp.parentsid = input.name.match(d13)[0];
		} else if (input.name.search(/third_title/g) != -1) {
			temp.id = input.id.match(d13)[0];
			var tmp = input.value;
			temp.content = tmp.split("&&")[0];
			temp.score = tmp.split("&&")[1];
			temp.parentsid = input.name.match(d13)[0];
		}
		payline.push(temp);
	}
	$('#addform').form('submit', {
		onSubmit : function() {
			$("#fromData").val("{aa:" + JSON.stringify(payline) + "}");
		},
		success : function() {
			alert("修改成功");
			top.updateWhole();
			top.closeTab("修改打分模版");
		}
	});
}

$(function() {
	// 初始化的时候显示打分配置选项类容
	var inputs_ = $(".kkk").children("input[class^='']");
	for ( var i = 0; i < inputs_.length; i++) {
		var input_ = inputs_[i];
		var vi = $("input[type='radio'][value=" + input_.classList[0] + "]");
		$(input_).next().html(vi.next().html());
	}
	
	editEvaluateType($("#renter_type").val());
});
function editRenterType(val) {
	if (val == "3" || val == "4") {
		$("#renter_type option[value='1']").remove();
	} else {
		$("#renter_type option[value='1']").remove();
		$("#renter_type").append("<option value='1'>个人</option>");
	}
}


function editEvaluateType(val){
	if(val == "1"){val=0;}else{val=1;}
	$("#EVALUATE_TYPE").empty();
	$("<option value=''>---请选择---</option>").appendTo("#EVALUATE_TYPE");
	jQuery.ajax({
		type:"post",
		async: false,
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
	var id1 = $("input[name='pqsoft']").attr("PARENTSID");
	var id = new Date().getTime();
	$("#norm")
			.append("<div class='kk'><font style='font-size:13px;font-weight:bold;'> &nbsp;评定等级项:"
							+ "</font><input type='text' value="+val+" readonly name='evaluate_title"
							+ id1 + "' id='evaluate_title"
							+ id + "'> <a href='javascript:void(0)' onclick='removeSecond(this)'>删除</a></div>");
}

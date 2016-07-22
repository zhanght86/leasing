

//审批结论change事件
function chgSPJLZS(){
	var spjl = $("#spjlZS").val();
	if(spjl == '3' || spjl == '4'){
		$.ajax({
    		url:_basePath+"/letterOpinion/LetterOpinion!getJJDL.action",
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			$("#jjdlZS").empty();
    			$("#spfkZS").empty();
    			$("#spfkZS").append("<option value=''>--请选择--</option>");
    			$("#spfkZS").val("");
    			var arr = eval(json.data);
    			var str = "<option value=''>--请选择--</option>";
    			for(var i=0;i<arr.length;i++){
    				str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
    			}
    			$("#jjdlZS").append(str);
    		}
    	});
	}else if(spjl == '2'){
		$.ajax({
    		url:_basePath+"/letterOpinion/LetterOpinion!getSPFK.action",
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			$("#spfkZS").empty();
    			$("#jjdlZS").empty();
    			$("#jjdlZS").append("<option value=''>--请选择--</option>");
    			$("#jjdlZS").val("");
    			$("#jjflZS").empty();
    			$("#jjflZS").append("<option value=''>--请选择--</option>");
    			$("#jjflZS").val("");
    			var arr = eval(json.data);
    			var str = "<option value=''>--请选择--</option>";
    			for(var i=0;i<arr.length;i++){
    				str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
    			}
    			$("#spfkZS").append(str);
    		}
    	});
	}else{
		$("#spfkZS").empty();
		$("#spfkZS").append("<option value=''>--请选择--</option>");
		$("#spfkZS").val("");
		$("#jjdlZS").empty();
		$("#jjdlZS").append("<option value=''>--请选择--</option>");
		$("#jjdlZS").val("");
		$("#jjflZS").empty();
		$("#jjflZS").append("<option value=''>--请选择--</option>");
		$("#jjflZS").val("");
	}
	var strspjlZS = $("#spjlZS").find("option:selected").text();
	$("#wbbzZS").val($("#wbbzZS").val()+strspjlZS+",");
}

//选择拒绝大类，关联拒绝分类
function chgjjdlZS(){
	
	var jjdl = $("#jjdlZS").val();
	var strJJDL = $("#jjdlZS").find("option:selected").text();
	var data = {};
	data.strJJDL = strJJDL;
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!getJJFL.action",
		type:"post",
		data:data,
		dataType:"json",
		success:function (json){
			$("#jjflZS").empty();
			var arr = eval(json.data);
			var str = "<option value=''>--请选择--</option>";
			for(var i=0;i<arr.length;i++){
				str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
			}
			$("#jjflZS").append(str);
		}
	});
	var jjdlZS = $("#jjdlZS").find("option:selected").text();
	$("#wbbzZS").val($("#wbbzZS").val()+jjdlZS+",");
}
/**
 * add by lishuo 
 * 在审批意见没有提交前，初审意见可以再修改(需点击) 
 */
/*function modifyChangeCS(){
    $("#spjlCS").removeAttr("disabled");
    $("#spfkCS").removeAttr("disabled");
    $("#jjdlCS").removeAttr("disabled");
    $("#jjflCS").removeAttr("disabled");
    $("#xsnpCS").removeAttr("disabled");
    $("#wbbzCS").removeAttr("disabled");
    $("#zcCS").linkbutton('enable');
    $("#tjCS").linkbutton('enable');
}*/
/**
 * add by lishuo 
 * 在审批意见没有提交前，终意见可以修改，初审意见不能修改。(需点击) 
 */
/*function modifyChangeZS(){
    $("#spjlZS").removeAttr("disabled");
    $("#spfkZS").removeAttr("disabled");
    $("#jjdlZS").removeAttr("disabled");
    $("#jjflZS").removeAttr("disabled");
    $("#xsnpZS").removeAttr("disabled");
    $("#wbbzZS").removeAttr("disabled");
    $("#zcZS").linkbutton('enable');
    $("#tjZS").linkbutton('enable');
}*/
//提交
/*function submitChangeZS(){
	var project_id = $("#project_id").val();
	var spjlZS = $("#spjlZS").val();
	var spfkZS = $("#spfkZS").val();
	var jjdlZS = $("#jjdlZS").val();
	var jjflZS = $("#jjflZS").val();
	var xsnpZS = $("#xsnpZS").val();
	var wbbzZS = $("#wbbzZS").val();
	if(spjlZS == null || spjlZS == ''){
		alert("请选择审批结论!");
		return;
	}
	if(spjlZS == '2'){
		if(spfkZS == null || spfkZS == ''){
			alert("有条件通过不能为空!");
			return;
		}
	}
	if(spjlZS == '3'){
		if(jjdlZS == null || jjdlZS == ''){
			alert("请选择拒绝大类!");
			return;
		}else{
			if(jjflZS == null || jjflZS == ''){
				alert("请选择拒绝分类!");
				return;
			}
		}
	}
	var data={};
	data.ENDTRIAL_SPJL = spjlZS;
	data.ENDTRIAL_YTJTG = spfkZS;
	data.ENDTRIAL_JJDL = jjdlZS;
	data.ENDTRIAL_JJFL = jjflZS;
	data.ENDTRIAL_CONTENT = xsnpZS;
	data.ENDTRIAL_WBBZ = wbbzZS;
	data.PROJECT_ID = project_id;
	data.SUBMZS = "1";
	
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!saveLetterOpinion.action?TYPE=2",
		type:"post",
		dataType:"json",
		data: data,
		success:function (json){
			if(json.flag){
				jQuery.messager.alert("提示","提交成功!");
				$("#spjlZS").attr("disabled","disabled");
				$("#spfkZS").attr("disabled","disabled");
				$("#jjdlZS").attr("disabled","disabled");
				$("#jjflZS").attr("disabled","disabled");
				$("#xsnpZS").attr("disabled","disabled");
				$("#wbbzZS").attr("disabled","disabled");
				$("#WMEMO", window.parent.parent.document).val(wbbzZS);
				$("#zcZS").linkbutton('disable');
				$("#tjZS").linkbutton('disable');
			}else{
				jQuery.messager.alert("提示","提交失败!");
			}
		}
	});	
}*/

//暂存
function saveChangeZS(){
	
	var project_id = $("#project_id").val();
	var spjlZS = $("#spjlZS").val();
	var spfkZS = $("#spfkZS").val();
	var jjdlZS = $("#jjdlZS").val();
	var jjflZS = $("#jjflZS").val();
	var xsnpZS = $("#xsnpZS").val();
	var wbbzZS = $("#wbbzZS").val();
	
	//add gengchangbao JZZL-118 2016年3月15日 start
	if (spjlZS == 2 && spfkZS == '') {
		alert("请选择【有条件通过】的选项！");
		return;
	}
	
	if ((spjlZS == 3 || spjlZS == 4) && jjdlZS == '') {
		alert("请选择【拒绝大类】的选项！");
		return;
	}
	
	if ((spjlZS == 3 || spjlZS == 4) && jjflZS == '') {
		alert("请选择【拒绝分类】的选项！");
		return;
	}
	//add gengchangbao JZZL-118 2016年3月15日 end
	var data={};
	data.ENDTRIAL_SPJL = spjlZS;
	data.ENDTRIAL_YTJTG = spfkZS;
	data.ENDTRIAL_JJDL = jjdlZS;
	data.ENDTRIAL_JJFL = jjflZS;
	data.ENDTRIAL_CONTENT = xsnpZS;
	data.ENDTRIAL_WBBZ = wbbzZS;
	data.PROJECT_ID = project_id;
	
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!saveLetterOpinion.action?TYPE=2",
		type:"post",
		dataType:"json",
		data: data,
		success:function (json){
			if(json.flag){
				jQuery.messager.alert("提示","保存成功!");
			}else{
				jQuery.messager.alert("提示","保存失败!");
			}
		}
	});	
}

function chgjjflZS(){
	var jjflZS = $("#jjflZS").find("option:selected").text();
	$("#wbbzZS").val($("#wbbzZS").val()+jjflZS+",");
}

function chgYtjtgZS(){
	var spfkZS = $("#spfkZS").find("option:selected").text();
	$("#wbbzZS").val($("#wbbzZS").val()+spfkZS+",");
}


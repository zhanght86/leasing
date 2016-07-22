
$(document).ready(function(){
	var type=$("#type").val();
	//var forbiddenCS = $("#forbiddenCS").val();
	//var forbiddenZS = $("#forbiddenZS").val();
	
	if(type == 'csyj'){
		$("#zsyj").hide();
		refush();
//		if(forbiddenCS != null && forbiddenCS == '1'){
//			forbidden();
//			$("#zcCS").linkbutton('disable');
//			$("#tjCS").linkbutton('disable');
//		}
	}else if(type == 'zsyj'){
		refush();
		forbidden();
		$("#zcCS").linkbutton('disable');
//		if(forbiddenCS != null && forbiddenCS == '1'){
//			forbidden();
//			$("#zcCS").linkbutton('disable');
//			$("#tjCS").linkbutton('disable');
//			//add by lishuo 01-05-2016
//			//$("#xgCS").linkbutton('disable');
//		}
		refushZS();
		forbiddenZS();
		$("#zcZS").linkbutton('disable');
//		if(forbiddenZS != null && forbiddenZS == '1'){
//			$("#spjlZS").attr("disabled","disabled");
//			$("#spfkZS").attr("disabled","disabled");
//			$("#jjdlZS").attr("disabled","disabled");
//			$("#jjflZS").attr("disabled","disabled");
//			$("#xsnpZS").attr("disabled","disabled");
//			$("#wbbzZS").attr("disabled","disabled");
//			$("#zcZS").linkbutton('disable');
//			$("#tjZS").linkbutton('disable');
//		}
	}
});

//终审意见数据加载
function refushZS(){
	var data = {};
	var project_id = $("#project_id").val();
	
	data.PROJECT_ID = project_id;
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!getLetterOpinion.action",
		type:"post",
		dataType:"json",
		data: data,
		success:function (json){
			var array = eval(json.data);
			var ENDTRIAL_SPJL = $("#spjlZS").val();
			if(ENDTRIAL_SPJL == '2'){
				//有条件通过
				$.ajax({
		    		url:_basePath+"/letterOpinion/LetterOpinion!getSPFK.action",
		    		type:"post",
		    		dataType:"json",
		    		success:function (json){
		    			$("#spfkZS").empty();
		    			var arr = eval(json.data);
		    			var str = "<option value=''>--请选择--</option>";
		    			for(var i=0;i<arr.length;i++){
		    				if(array[0]['ENDTRIAL_YTJTG'] == arr[i]['CODE']){
		    					str+="<option value='"+arr[i]['CODE']+"' selected >"+arr[i]['FLAG']+"</option>";
		    				}else{
		    					str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
		    				}
		    			}
		    			$("#spfkZS").append(str);
		    		}
		    	});
			}else if(ENDTRIAL_SPJL == '3'){
				//拒绝大类
				$.ajax({
		    		url:_basePath+"/letterOpinion/LetterOpinion!getJJDL.action",
		    		type:"post",
		    		dataType:"json",
		    		success:function (json){
		    			$("#jjdlZS").empty();
		    			var arr = eval(json.data);
		    			var str = "<option value=''>--请选择--</option>";
		    			for(var i=0;i<arr.length;i++){
		    				if(array[0]['ENDTRIAL_JJDL'] == arr[i]['CODE']){
		    					str+="<option value='"+arr[i]['CODE']+"' selected >"+arr[i]['FLAG']+"</option>";
		    				}else{
		    					str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
		    				}
		    			}
		    			$("#jjdlZS").append(str);
		    			var dataFL = {};
		    			dataFL.strJJDL = $("#jjdlZS").find("option:selected").text();
		    			//拒绝分类
		    			$.ajax({
				    		url:_basePath+"/letterOpinion/LetterOpinion!getJJFL.action",
				    		type:"post",
				    		dataType:"json",
				    		data: dataFL,
				    		success:function (json){
				    			$("#jjflZS").empty();
				    			var arr = eval(json.data);
				    			var str = "<option value=''>--请选择--</option>";
				    			for(var M=0;M<arr.length;M++){
				    				if(array[0]['ENDTRIAL_JJFL'] == arr[M]['CODE']){
				    					str+="<option value='"+arr[M]['CODE']+"' selected >"+arr[M]['FLAG']+"</option>";
				    				}else{
				    					str+="<option value='"+arr[M]['CODE']+"'>"+arr[M]['FLAG']+"</option>";
				    				}
				    			}
				    			$("#jjflZS").append(str);
				    		}
				    	});
		    		}
				});
			}
		}
	});
}

//初审意见数据加载
function refush(){
	var data = {};
	var project_id = $("#project_id").val();
	
	data.PROJECT_ID = project_id;
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!getLetterOpinion.action",
		type:"post",
		dataType:"json",
		data: data,
		success:function (json){
			var array = eval(json.data);
			
			var FIRSTTRIAL_SPJL = $("#spjlCS").val();
			if(FIRSTTRIAL_SPJL == '2'){
				//有条件通过
				$.ajax({
		    		url:_basePath+"/letterOpinion/LetterOpinion!getSPFK.action",
		    		type:"post",
		    		dataType:"json",
		    		success:function (json){
		    			$("#spfkCS").empty();
		    			var arr = eval(json.data);
		    			var str = "<option value=''>--请选择--</option>";
		    			for(var i=0;i<arr.length;i++){
		    				if(array[0]['FIRSTTRIAL_YTJTG'] == arr[i]['CODE']){
		    					str+="<option value='"+arr[i]['CODE']+"' selected >"+arr[i]['FLAG']+"</option>";
		    				}else{
		    					str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
		    				}
		    			}
		    			$("#spfkCS").append(str);
		    		}
		    	});
			}else if(FIRSTTRIAL_SPJL == '3'){
				//拒绝大类
				$.ajax({
		    		url:_basePath+"/letterOpinion/LetterOpinion!getJJDL.action",
		    		type:"post",
		    		dataType:"json",
		    		success:function (json){
		    			$("#jjdlCS").empty();
		    			var arr = eval(json.data);
		    			var str = "<option value=''>--请选择--</option>";
		    			for(var i=0;i<arr.length;i++){
		    				if(array[0]['FIRSTTRIAL_JJDL'] == arr[i]['CODE']){
		    					str+="<option value='"+arr[i]['CODE']+"' selected >"+arr[i]['FLAG']+"</option>";
		    				}else{
		    					str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
		    				}
		    			}
		    			$("#jjdlCS").append(str);
		    			var dataFL = {};
		    			dataFL.strJJDL = $("#jjdlCS").find("option:selected").text();
		    			//拒绝分类
		    			$.ajax({
				    		url:_basePath+"/letterOpinion/LetterOpinion!getJJFL.action",
				    		type:"post",
				    		dataType:"json",
				    		data: dataFL,
				    		success:function (json){
				    			$("#jjflCS").empty();
				    			var arr = eval(json.data);
				    			var str = "<option value=''>--请选择--</option>";
				    			for(var M=0;M<arr.length;M++){
				    				if(array[0]['FIRSTTRIAL_JJFL'] == arr[M]['CODE']){
				    					str+="<option value='"+arr[M]['CODE']+"' selected >"+arr[M]['FLAG']+"</option>";
				    				}else{
				    					str+="<option value='"+arr[M]['CODE']+"'>"+arr[M]['FLAG']+"</option>";
				    				}
				    			}
				    			$("#jjflCS").append(str);
				    		}
				    	});
		    		}
				});
			}
		}
	});
}

//审批结论change事件
function chgSPJLCS(){
	var spjl = $("#spjlCS").val();
	
	var strspjlCS = $("#spjlCS").find("option:selected").text();
	$("#wbbzCS").val($("#wbbzCS").val()+strspjlCS+",");
	if(spjl == '3' || spjl == '4'){
		$.ajax({
    		url:_basePath+"/letterOpinion/LetterOpinion!getJJDL.action",
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			$("#jjdlCS").empty();
    			$("#spfkCS").empty();
    			$("#spfkCS").append("<option value=''>--请选择--</option>");
    			$("#spfkCS").val("");
    			var arr = eval(json.data);
    			var str = "<option value=''>--请选择--</option>";
    			for(var i=0;i<arr.length;i++){
    				str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
    			}
    			$("#jjdlCS").append(str);
    		}
    	});
	}else if(spjl == '2'){
		$.ajax({
    		url:_basePath+"/letterOpinion/LetterOpinion!getSPFK.action",
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			$("#spfkCS").empty();
    			$("#jjdlCS").empty();
    			$("#jjdlCS").append("<option value=''>--请选择--</option>");
    			$("#jjdlCS").val("");
    			$("#jjflCS").empty();
    			$("#jjflCS").append("<option value=''>--请选择--</option>");
    			$("#jjflCS").val("");
    			var arr = eval(json.data);
    			var str = "<option value=''>--请选择--</option>";
    			for(var i=0;i<arr.length;i++){
    				str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
    			}
    			$("#spfkCS").append(str);
    		}
    	});
	}else{
		$("#spfkCS").empty();
		$("#spfkCS").append("<option value=''>--请选择--</option>");
		$("#spfkCS").val("");
		$("#jjdlCS").empty();
		$("#jjdlCS").append("<option value=''>--请选择--</option>");
		$("#jjdlCS").val("");
		$("#jjflCS").empty();
		$("#jjflCS").append("<option value=''>--请选择--</option>");
		$("#jjflCS").val("");
	}
}

function chgYtjtgCS(){
	var spfkCS = $("#spfkCS").find("option:selected").text();
	$("#wbbzCS").val($("#wbbzCS").val()+spfkCS+",");
}


//选择拒绝大类，关联拒绝分类
function chgjjdlCS(){
	
	var jjdl = $("#jjdlCS").val();
	var strJJDL = $("#jjdlCS").find("option:selected").text();
	var strjjdlCS = $("#jjdlCS").find("option:selected").text();
	$("#wbbzCS").val($("#wbbzCS").val()+strjjdlCS+",");
	var data = {};
	data.strJJDL = strJJDL;
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!getJJFL.action",
		type:"post",
		data:data,
		dataType:"json",
		success:function (json){
			$("#jjflCS").empty();
			var arr = eval(json.data);
			var str = "<option value=''>--请选择--</option>";
			for(var i=0;i<arr.length;i++){
				str+="<option value='"+arr[i]['CODE']+"'>"+arr[i]['FLAG']+"</option>";
			}
			$("#jjflCS").append(str);
		}
	});
	
}

//提交
/*function submitChangeCS(){
	var project_id = $("#project_id").val();
	var spjlCS = $("#spjlCS").val();
	var spfkCS = $("#spfkCS").val();
	var jjdlCS = $("#jjdlCS").val();
	var jjflCS = $("#jjflCS").val();
	var xsnpCS = $("#xsnpCS").val();
	var wbbzCS = $("#wbbzCS").val();
	if(spjlCS == null || spjlCS == ''){
		alert("请选择审批结论!");
		return;
	}
	if(spjlCS == '2'){
		if(spfkCS == null || spfkCS == ''){
			alert("有条件通过不能为空!");
			return;
		}
	}
	if(spjlCS == '3'){
		if(jjdlCS == null || jjdlCS == ''){
			alert("请选择拒绝大类!");
			return;
		}else{
			if(jjflCS == null || jjflCS == ''){
				alert("请选择拒绝分类!");
				return;
			}
		}
	}
	var data={};
	data.FIRSTTRIAL_SPJL = spjlCS;
	data.FIRSTTRIAL_YTJTG = spfkCS;
	data.FIRSTTRIAL_JJDL = jjdlCS;
	data.FIRSTTRIAL_JJFL = jjflCS;
	data.FIRSTTRIAL_CONTENT = xsnpCS;
	data.FIRSTTRIAL_WBBZ = wbbzCS;
	data.PROJECT_ID = project_id;
	data.SUBMCS = "1";
	data.ENDTRIAL_WBBZ = wbbzCS;
	
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!saveLetterOpinion.action?TYPE=1",
		type:"post",
		dataType:"json",
		data: data,
		success:function (json){
			if(json.flag){
				jQuery.messager.alert("提示","提交成功!");
				forbidden();
				$("#WMEMO", window.parent.parent.document).val(wbbzCS);
				$("#zcCS").linkbutton('disable');
				$("#tjCS").linkbutton('disable');
			}else{
				jQuery.messager.alert("提示","提交失败!");
			}
		}
	});	
}*/

//暂存
function saveChangeCS(){
	
	var project_id = $("#project_id").val();
	var spjlCS = $("#spjlCS").val();
	var spfkCS = $("#spfkCS").val();
	var jjdlCS = $("#jjdlCS").val();
	var jjflCS = $("#jjflCS").val();
	var xsnpCS = $("#xsnpCS").val();
	var wbbzCS = $("#wbbzCS").val();
	//add gengchangbao JZZL-118 2016年3月15日 start
	if (spjlCS == 2 && spfkCS == '') {
		alert("请选择【有条件通过】的选项！");
		return;
	}
	
	if ((spjlCS == 3 || spjlCS == 4) && jjdlCS == '') {
		alert("请选择【拒绝大类】的选项！");
		return;
	}
	
	if ((spjlCS == 3 || spjlCS == 4) && jjflCS == '') {
		alert("请选择【拒绝分类】的选项！");
		return;
	}
	//add gengchangbao JZZL-118 2016年3月15日 end
	var data={};
	data.FIRSTTRIAL_SPJL = spjlCS;
	data.FIRSTTRIAL_YTJTG = spfkCS;
	data.FIRSTTRIAL_JJDL = jjdlCS;
	data.FIRSTTRIAL_JJFL = jjflCS;
	data.FIRSTTRIAL_CONTENT = xsnpCS;
	data.FIRSTTRIAL_WBBZ = wbbzCS;
	data.PROJECT_ID = project_id;
	
	$.ajax({
		url:_basePath+"/letterOpinion/LetterOpinion!saveLetterOpinion.action?TYPE=1",
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

//页面禁止编辑
function forbidden(){
	$("#spjlCS").attr("disabled","disabled");
	$("#spfkCS").attr("disabled","disabled");
	$("#jjdlCS").attr("disabled","disabled");
	$("#jjflCS").attr("disabled","disabled");
	$("#xsnpCS").attr("disabled","disabled");
	$("#wbbzCS").attr("disabled","disabled");
}

//页面禁止编辑
function forbiddenZS(){
	$("#spjlZS").attr("disabled","disabled");
	$("#spfkZS").attr("disabled","disabled");
	$("#jjdlZS").attr("disabled","disabled");
	$("#jjflZS").attr("disabled","disabled");
	$("#xsnpZS").attr("disabled","disabled");
	$("#wbbzZS").attr("disabled","disabled");
}

function chgjjflCS(){
	var strjjflCS = $("#jjflCS").find("option:selected").text();
	$("#wbbzCS").val($("#wbbzCS").val()+strjjflCS+",");
}


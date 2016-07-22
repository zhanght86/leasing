function CHUSHEN1(){
	var array=new Array();
	$(".SH_COUNT").each(function(){
		var temp={};
		var trNode=$(this).parent("td").parent("tr");
		if($(this).val()!=null && $(this).val()!=''){
			temp.NUM=$(trNode).find("[name=NUM]").val();
			temp.DATA_ID=$(trNode).find("[name=DATA_ID]").val();
			temp.FILE_NAME=$(trNode).find("[name=FILE_NAME]").val();
			temp.FILECOUNT=$(trNode).find("[name=FILECOUNT]").val();
			temp.SH_COUNT=$.trim($(this).val());
			temp.SFQQ=$(trNode).find("[name='SFQQ"+temp.NUM+"']:checked").val();
			temp.REMARK=$(trNode).find("[name=REMARK]").val();
			array.push(temp);
		}
	});
	$.ajax({
		type:'post',
		url:_basePath+"/creditlist/CreditCoreFileInfo!addProjectChuShen.action",
		data:"fileList="+JSON.stringify(array)+"&BQZLCNR="+encodeURI($("#BQZLCNR").val())
			 +"&BQZLCNSJ="+$('#BQZLCNSJ').datebox('getValue')+"&PROJECT_ID="
			 +$("#PROJECT_ID").val()+"&SENDTYPE="+$("[name='SENDTYPE']:checked").val(),
	    dataType:'json',
	    success:function(json){
			if(json.flag==true){
//				searchCode1(json.applyId);
				alert("请确认补齐归档流程图");
			}else
			alert(json.msg);
		}
	});
}
function guidang(){
	var array=new Array();
	$(".SH_COUNT").each(function(){
		var temp={};
		var trNode=$(this).parent("td").parent("tr");
		if($(this).val()!=null && $(this).val()!=''){//即是必须填写 实收分数 
			temp.NUM=$(trNode).find("[name=NUM]").val();
			temp.ID=$(trNode).find("[name=ID]").val();
			temp.DATA_ID=$(trNode).find("[name=DATA_ID]").val();
			temp.FILE_NAME=$(trNode).find("[name=FILE_NAME]").val();
			temp.FILECOUNT=$(trNode).find("[name=FILECOUNT]").val();
			temp.SH_COUNT=$.trim($(this).val());
			temp.SFZS=$(trNode).find("[name='SFZS"+temp.NUM+"']:checked").val();
			temp.SFQQ=$(trNode).find("[name='SFQQ"+temp.NUM+"']:checked").val();
			temp.REMARK=$(trNode).find("[name=REMARK]").val();
			array.push(temp);
		}
	});
	$.ajax({
		type:'post',
		url:_basePath+"/creditlist/CreditCoreFileInfo!addProjectBuQi.action",
		data:"fileList="+JSON.stringify(array)+"&BQZLCNR="+encodeURI($("#BQZLCNR").val())
		+"&BQZLCNSJ="+$('#BQZLCNSJ').datebox('getValue')+"&PROJECT_ID="
		+$("#PROJECT_ID").val()+"&SENDTYPE="+$("[name='SENDTYPE']:checked").val(),
		dataType:'json',
		success:function(json){
		if(json.flag==true){
			alert("保存成功");
			$("#guidang").linkbutton('disable');  
		}else
			alert(json.msg);
	}
	});
}
function searchCode1(applyId) {
	var array=new Array();
	var SENDTYPE=$("[name='SENDTYPE']:checked").val();
	$(".SH_COUNT").each(function(){
		var temp={};
		var trNode=$(this).parent("td").parent("tr");
		if($(this).val()!=null && $(this).val()!=''){
			temp.NUM=$(trNode).find("[name=NUM]").val();
			temp.DATA_ID=$(trNode).find("[name=DATA_ID]").val();
			temp.file_name=$(trNode).find("[name=FILE_NAME]").val();
			temp.FILECOUNT=$(trNode).find("[name=FILECOUNT]").val();
			temp.count=$(this).val();
			temp.SFQQ=$(trNode).find("[name='SFQQ"+temp.NUM+"']:checked").val();
			temp.REMARK=$(trNode).find("[name=REMARK]").val();
			temp.DOSSIERTYPE='1';
			temp.project_file_id=temp.DATAID;
			temp.filepage='';
			temp.sendtype=SENDTYPE;
			array.push(temp);
		}
	});
		var CrBecrCode=$("#CrBecrCode").val();
		var PROJECT_CODE = $("#PROJECT_CODE").val();
		var CLIENT_ID = $("#CLIENT_ID").val();
		var FILEINFO = JSON.stringify(array);
		var DOSSIER_APPLYID=applyId;
		var APPLY_TYPE=$("#APPLY_TYPE").val();
		jQuery.ajax( {
			url : _basePath + "/pigeonhole/Pigeonhole!doAddPigeonholeApplyForProject.action",
			type : "post",
			data : "FILEINFO=" + FILEINFO+"&CLIENT_ID="+CLIENT_ID+"&SENDTYPE="+SENDTYPE+"&APPLY_TYPE="+APPLY_TYPE + "&PROJECT_CODE="
					+ PROJECT_CODE + "&CrBecrCode=" + CrBecrCode+"&DOSSIER_APPLYID="+DOSSIER_APPLYID+"&JBPM_ID="+$("#JBPM_ID").val(),
			success : function(i) {
				if (i == 1) {
					alert("申请成功");
					$("#guidangtijiao").attr("disabled","true");
				}else if(i==2){
					alert("该申请已经存在");
				} else {
					alert("申请失败");
				}
			},
			error : function(i) {
				alert("网络连接失败，请重试");
			}
		});
	}
function CHUSHEN(){
	$("#CHUSHENEBTN").attr("disabled",true);
	$("#CHUSHENEBTN").linkbutton("disable");
	var flag=false;
	var array=new Array();
	$(".ID").each(function(){
		var temp={};
		var trNode=$(this).parent("td").parent("tr");
		if($.trim($(trNode).find("[name=SH_COUNT]").val())!=null && $.trim($(trNode).find("[name=SH_COUNT]").val())!=''){//即是必须填写 实收分数 
			flag=true;
		}
		temp.NUM=$(trNode).find("[name=NUM]").val();
		temp.ID=$(trNode).find("[name=ID]").val();
		temp.DATA_ID=$(trNode).find("[name=DATA_ID]").val();
		temp.FILE_NAME=$(trNode).find("[name=FILE_NAME]").val();
		temp.FILECOUNT=$(trNode).find("[name=FILECOUNT]").val();
		temp.SH_COUNT=$.trim($(trNode).find("[name=SH_COUNT]").val());
		temp.TYPE=$.trim($(trNode).find("[name=TYPE]").val());
		if(temp.SH_COUNT==''||temp.SH_COUNT==null){
			temp.SH_COUNT='0';
		}
		temp.SFZS=$(trNode).find("[name='SFZS"+temp.NUM+"']:checked").val();
		temp.SFQQ=$(trNode).find("[name='SFQQ"+temp.NUM+"']:checked").val();
		temp.REMARK=$(trNode).find("[name=REMARK]").val();
		array.push(temp);
	});
	if(array.length>=1&&flag){
		$.ajax({
			type:'post',
			url:_basePath+"/creditlist/CreditCoreFileInfo!addProjectChuShen.action",
			data:"fileList="+JSON.stringify(array)+"&BQZLCNR="+encodeURI($("#BQZLCNR").val())
				 +"&BQZLCNSJ="+$('#BQZLCNSJ').datebox('getValue')+"&PROJECT_ID="
				 +$("#PROJECT_ID").val()+"&SENDTYPE="+$("[name='SENDTYPE']:checked").val(),
		    dataType:'json',
		    success:function(json){
				if(json.flag==true){
					$.messager.alert("提示","保存成功!");
	//				searchCode1(json.applyId);
				}else{
					$.messager.alert("提示",json.msg);
					$("#CHUSHENEBTN").attr("disabled",false);
					$("#CHUSHENEBTN").linkbutton("enable");
				}
			}
		});
	}else{
		$.messager.alert("提示","请录入实收份数!");
		$("#CHUSHENEBTN").attr("disabled",false);
		$("#CHUSHENEBTN").linkbutton("enable");
	}
}
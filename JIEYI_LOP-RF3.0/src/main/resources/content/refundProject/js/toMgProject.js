//搜索
function toSeacher(){
	var BAILOUTWAY_NAME = $("selected[name='BAILOUTWAY_NAME']").attr("selected",true).val();
	var ORGAN_NAME = $("input[name='ORGAN_NAME']").val();
	var PROJECT_NAME = $("input[name='PROJECT_NAME']").val();
	$("#toMgProject").datagrid("load",{
		"PROJECT_NAME":PROJECT_NAME,
		"ORGAN_NAME":ORGAN_NAME,
		"BAILOUTWAY_NAME":BAILOUTWAY_NAME
	});
}

//清空
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

//操作
function operation(val, row){
	var url = "";
	if(row.PROJECT_STATUS=="未提交"){
		url = "| <a href='javascript:void(0)' onclick='toUpdatePro("+row.PRO_ID+")'>修改</a>";
	}else if(row.PROJECT_STATUS=="内部审核通过"){
		url = "| <a href='#' onclick='toCommitBank("+row.PRO_ID+")'>提交银行</a>";
//		url = "| <a href='javascript:void(0)' onclick='toExpPro("+JSON.stringify(row)+")'>导出</a></br> <a href='#' onclick='toCommitBank("+row.PRO_ID+")'>提交银行</a>";
	}else {
		url = "";
	}
	return "<a href='javascript:void(0)' onclick='toShowPro("+row.PRO_ID+")'>查看</a> "+url;
}


//导出zip
function toExpPro(row){
	$("#divFrom").empty();
	var url="RefundProject!dataDownByProId.action?PRO_ID="+row.PRO_ID+"&PROJECT_CODE="+row.PROJECT_CODE;
	
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
	
}

//查看
function toShowPro(PRO_ID){
	top.addTab("融资项目查看",_basePath+"/refundProject/RefundProject!toShowPro.action?PROJECT_ID="+PRO_ID+"&data="+new Date());
}

//修改
function toUpdatePro(pro_id){
	top.addTab("融资项目修改",_basePath+"/refundProject/RefundProject!toUpdatePro.action?PROJECT_ID="+pro_id+"&data="+new Date());
}

//提交银行
function toCommitBank(pro_id) {
	jQuery.ajax({
		url : _basePath+"/refundProject/RefundProject!refundProjectStatus.action",
		data : "ID="+pro_id,
		dataType:"json",
		success : function(json){
			if(json.flag==true){
				$.messager.alert("提示","提交成功！");
				window.location.href=_basePath+"/refundProject/RefundProject.action";
			}else{
				$.messager.alert("提示","提交失败！");
				window.location.href=_basePath+"/refundProject/RefundProject.action";
			}
		}
	});
}

//查看支付表
function checkpaylist(val, row){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/refundProject/RefundProject!getProjectPayList.action",
		data:"PROJECT_ID="+row.PRO_ID,
		success:function(s){
			$("#toShowProPay").empty();
			$("#toShowProPay").append(s);
			$("#toShowProPay").dialog("open");
		}
	});
}

//上传文件
function btnUpload(){
	var PATHROOT = jQuery.trim($("#upfile").val());
	var PROJECT_ID = $("#PROJECT_ID").val();
	var FILE_TYPE = $("#FILE_TYPE option:selected").val();
	if(PATHROOT=='' || FILE_TYPE ==''){
		alert("条件不全");
		return false;
	}
    jQuery.ajaxFileUpload({
    	type:"post",
	    url:_basePath+"/refundProject/RefundProject!doUpload.action?FILE_TYPE="+FILE_TYPE+"&PROJECT_ID="+PROJECT_ID,
	    secureuri:false,
	    fileElementId:"upfile",
		dataType : 'json',
	    success: function (json,status){
	    	if(status){
//	    		alert(json.FILE_TYPE);
				$("#filecontent").append($("<tr>")
					.append('<td>'+json.data.FILE_TYPE+ ' -- ' + json.data.FILE_NAME+'</td>')
					.append('<td>'+json.data.USER_NAME+'</td>')
					.append('<td>'+json.data.CREATE_DATE+'</td>')
					.append('<td><a href="$!_basePath/refundProject/RefundProject!doDownload.action?id='
							+json.data.ID+'">下载</a> <a href="javascript:void(0);" sid="'
							+json.data.ID+'" onclick="deleteFile(this)">删除</a></td>'));
	    	}else{
	    		alert("上传失败");
	    	}
	    },
	    error: function (json){
	    	alert("上传失败,请重新选择");
	    }
   	});
}

//删除文件
function deleteFile(ele){
	if(confirm("确定要删除此附件?")){
		jQuery.ajax({
			url : _basePath+"/refundProject/RefundProject!doDelete.action?id="+$(ele).attr("sid"),
			dataType : "json",
			success : function(json){
				if(json.flag==true){
					$(ele).parent().parent().remove();
				}else{
					alert("删除失败");
				}
			}
		});
	}
}
function searchcircles() {
	$('#circlesTable').datagrid('load', {});
}

var url;
function addcircles() {
	$('#circlesDialog').dialog('open').dialog('setTitle', '添加工商信息');
	$('#circlesAdd').form('clear');
	$('#CIRCLES_CLIENT_ID').val($('#clientId').val());
	url = '../crm/CreditDossier!doSaveCircles.action';
}


function openUpdatecircles() {
	$('#circlesUpdate').form('clear');
	var row = $("#circlesTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的工商信息");
		return true;
	}
	$('#circlesUpdateDialog').dialog('open').dialog('setTitle', '修改工商');
	$('#UPDATE_CIRCLES_ID').val(row.ID);         
	$('#UPDATE_CIRCLES_DATE').datebox("setValue",row.HAPPEN_TIME);
	$('#UPDATE_CIRCLES_REMARK').val(row.REMARK);
    
}


function updatecircles() {
	$('#circlesUpdate').form('submit', {
		url : '../crm/CreditDossier!doUpdateCircles.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#circlesUpdateDialog').dialog('close'); // close the dialog
				$('#circlesTable').datagrid('load', {
					"param" : getFromData("#pForm")
				});
			} else {
				$.messager.alert("提示","修改失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}



function savecircles() {
	$('#circlesAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#circlesDialog').dialog('close'); // close the dialog
				$('#circlesTable').datagrid('load', {});
			} else {
				$.messager.alert("提示","保存失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}
function delcircles() {
	var row = $("#circlesTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要删除的工商信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/CreditDossier!doDeleteCircles.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#circlesTable').datagrid('load', {});
				} else {
					$.messager.alert("提示","删除失败请重试！");
				}
			},
			error : function(e) {
				alert(e.message);
			}
		});
	}
}


function setcirclesDel(val, row) {
	return "<a href='#' onclick='delcircles(" + val + ")'>删除</a>";
}

function downCircles(val, row){
	if(row.FILE_NAME==null || row.FILE_NAME==""){
		return "无附件";
	}else{
		return "<a href='"+_basePath+"/credit/CreditRepayment!downLoadRecordFile.action?FILE_URL="+row.FILE_PATH+"&FILE_NAME="+row.FILE_NAME+"'>"+row.FILE_NAME+"</a>";
	}
}
function upCircles() {
	var pathRoot = jQuery.trim($("#upfileGS").val());
	if(pathRoot==''){
		return false;
	}

    jQuery.ajaxFileUpload({
	    url:_basePath+"/credit/CreditRepayment!uploadRecord.action",
	    secureuri:false,
	    fileElementId:"upfileGS",
	    dataType: "String",
	    success: function (data){
			var dateMap=eval(data);
			var file_path=dateMap[0].FILE_PATH;
			var file_name = dateMap[0].FILE_NAME;
			$("#FILE_PATHGS").val(file_path);
			$("#FILE_NAMEGS").val(file_name);
			alert('上传成功！');
	    },
    	    error: function (callback){
	    	alert("上传失败,请重新选择(音频文件大小不能超过1G)");
	    }
   	});	
    
}
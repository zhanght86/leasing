function searchtax() {
	$('#taxTable').datagrid('load', {});
}

var url;
function addtax() {
	$('#taxDialog').dialog('open').dialog('setTitle', '添加税务信息');
	$('#taxAdd').form('clear');
	$('#TAX_CLIENT_ID').val($('#clientId').val());
	url = '../crm/CreditDossier!doSaveTax.action';
}


function openUpdatetax() {
	$('#taxUpdate').form('clear');
	var row = $("#taxTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的工商信息");
		return true;
	}
	$('#taxUpdateDialog').dialog('open').dialog('setTitle', '修改工商');
	$('#UPDATE_TAX_ID').val(row.ID);         
	$('#UPDATE_TAX_DATE').datebox("setValue",row.HAPPEN_TIME);
	$('#UPDATE_TAX_REMARK').val(row.REMARK);
    
}


function updatetax() {
	$('#taxUpdate').form('submit', {
		url : '../crm/CreditDossier!doUpdateTax.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#taxUpdateDialog').dialog('close'); // close the dialog
				$('#taxTable').datagrid('load', {
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



function savetax() {
	$('#taxAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#taxDialog').dialog('close'); // close the dialog
				$('#taxTable').datagrid('load', {});
			} else {
				$.messager.alert("提示","保存失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}
function deltax() {
	var row = $("#taxTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要删除的法院信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/CreditDossier!doDeleteTax.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#taxTable').datagrid('load', {});
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


function settaxDel(val, row) {
	return "<a href='#' onclick='deltax(" + val + ")'>删除</a>";
}

function downtax(val, row){
	if(row.FILE_NAME==null || row.FILE_NAME==""){
		return "无附件";
	}else{
		return "<a href='"+_basePath+"/credit/CreditRepayment!downLoadRecordFile.action?FILE_URL="+row.FILE_PATH+"&FILE_NAME="+row.FILE_NAME+"'>"+row.FILE_NAME+"</a>";
	}
}
function uptax() {
	var pathRoot = jQuery.trim($("#upfileSW").val());
	if(pathRoot==''){
		return false;
	}

    jQuery.ajaxFileUpload({
	    url:_basePath+"/credit/CreditRepayment!uploadRecord.action",
	    secureuri:false,
	    fileElementId:"upfileSW",
	    dataType: "String",
	    success: function (data){
			var dateMap=eval(data);
			var file_path=dateMap[0].FILE_PATH;
			var file_name = dateMap[0].FILE_NAME;
			$("#FILE_PATHSW").val(file_path);
			$("#FILE_NAMESW").val(file_name);
			alert('上传成功！');
	    },
    	    error: function (callback){
	    	alert("上传失败,请重新选择(音频文件大小不能超过1G)");
	    }
   	});	
    
}
//银行征信
function searchPayment() {
	$('#paymentTable').datagrid('load', {});
}

var url;
function addPayment() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
		$('#paymentDialog').dialog('open').dialog('setTitle', '添加银行征信');
		$('#paymentAdd').form('clear');
		$('#payment_CREDIT_ID').val($('#creditId').val());
		$('#payment_SUP_ID').val($('#supId').val());
		url = _basePath+'/base/sp/CreditDossier!doSavePayment.action';
	}
}
function savePayment() {
	$('#paymentAdd').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#paymentDialog').dialog('close'); // close the dialog
		$('#paymentTable').datagrid('load', {});
	} else {
		$.messager.alert("提示","保存失败请重试！");
	}
},
error : function(e) {
	alert(e.message);
}
	});
}
function delPayment() {
	var row = $("#paymentTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : _basePath+"/base/sp/CreditDossier!doDeletePayment.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#paymentTable').datagrid('load', {});
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



function openUpdatePayment() {
	$('#paymentUpdate').form('clear');
	var row = $("#paymentTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的产权信息");
		return true;
	}
	$('#paymentUpdateDialog').dialog('open').dialog('setTitle', '修改银行征信');
	$('#UPDATE_PAYMENT_ID').val(row.ID);
	$('#UPDATE_LOAN_INFORMATION').val(row.LOAN_INFORMATION);
	$('#UPDATE_LINE_CREDIT').val(row.LINE_CREDIT);  
	$('#UPDATE_LOAN_DATE').datebox("setValue",row.LOAN_DATE);
	$('#UPDATE_TERM_LOAN_DATE').datebox("setValue",row.TERM_LOAN_DATE);
	$('#UPDATE_TOTAL_LOAN_AMOUNT').val(row.TOTAL_LOAN_AMOUNT);  
	$('#UPDATE_LOAN_BALANCE').val(row.LOAN_BALANCE); 
	$('#UPDATE_OVERDUE_PERIOD').val(row.OVERDUE_PERIOD); 
	$('#UPDATE_HIGHEST_OVERDUE_PERIOD').val(row.HIGHEST_OVERDUE_PERIOD); 
	$('#UPDATE_PAYMENT_REMARK').val(row.REMARK); 
    
}

function paymentInterest() {
	$('#paymentUpdate').form('submit', {
		url : _basePath+'/base/sp/CreditDossier!doUpdatePayment.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#paymentUpdateDialog').dialog('close'); // close the dialog
				$('#paymentTable').datagrid('load', {
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
function upRecord() {
	var pathRoot = jQuery.trim($("#upfile111").val());
	if(pathRoot==''){
		return false;
	}

    jQuery.ajaxFileUpload({
	    url:_basePath+"/base/sp/CreditDossier!uploadRecord.action",
	    secureuri:false,
	    fileElementId:"upfile111",
	    dataType: "String",
	    success: function (data){
			var dateMap=eval(data);
			var file_path=dateMap[0].FILE_PATH;
			var file_name = dateMap[0].FILE_NAME;
			$("#FILE_PATH").val(file_path);
			$("#FILE_NAME").val(file_name);
			alert('上传成功！');
	    },
    	    error: function (callback){
	    	alert("上传失败,请重新选择(音频文件大小不能超过1G)");
	    }
   	});	
    
}
function down(val, row){
	if(row.FILE_NAME==null || row.FILE_NAME==""){
		return "无附件";
	}else{
		return "<a href='"+_basePath+"/base/sp/CreditDossier!downLoadRecordFile.action?FILE_URL="+row.FILE_PATH+"&FILE_NAME="+row.FILE_NAME+"'>附件下载</a>";
	}
}

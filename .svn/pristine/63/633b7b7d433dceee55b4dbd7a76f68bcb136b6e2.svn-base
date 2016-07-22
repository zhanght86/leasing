//$(document).ready(function(){
var sealTempIndex = 0;
$(function() {
	$("#fromDate").form('clear');
	$("#pageTable")
			.datagrid(
				{
				url : _basePath
						+ "/paper/PaperFilesManage!toPaperFilesShow.action",
				pagination : true,// 分页
				rownumbers : true,// 行数
				singleSelect : true,// 单选模式
				toolbar : '#pageForm',
				columns : [ [
					{
						field : 'ID',
						align : 'center',
						title : '操作',
						width : 10,
						formatter : function(value, rowData,
								rowIndex) {
							var rehtml = "<a href='javascript:void(0);' onclick='delFile("
										+ value
										+ ")'>删除</a>&nbsp;";
							rehtml += "|<a href='javascript:void(0);' onclick='downloadFile("
																					+ value
																					+ ")'>下载</a>&nbsp;";
							return rehtml;
						}
					},
						{
							field : 'FILE_NAME',
							align : 'center',
							width : 15,
							title : '资料名'
						},
						{
							field : 'SPEAK',
							align : 'center',
							width : 20,
							title : '资料说明'
						},
	
						{
							field : 'CREATE_TIME',
							align : 'center',
							width : 10,
							title : '上传日期'
						},
						
						{
							field : 'CLERK_NAME',
							align : 'center',
							width : 10,
							title : '操作人'
						},
						{
							field : 'REMARK',
							align : 'center',
							width : 20,
							title : '备注',
							
						}
						 ] ]
			});
	});

//查询重新加载画面数据
function conditionsSelect() {
	$('#pageTable').datagrid('load', {
		"param" : getFromData("#fromDate")
	});
}

//清空
function clearSelect(formId) {
	$("#" + formId).form('clear');
}

//下载资料文件
function downloadFile(ID) {
	window.location.href = _basePath
			+ "/paper/PaperFilesManage!downPaperFile.action?ID="
			+ encodeURI(ID) + "&_datetime=" + new Date().getTime();
}

//删除资料文件
function delFile(ID) {
	$.messager
	.confirm(
			'提示框',
			'确定要删除吗?',
			function(flag) {
				if (flag) {
					jQuery
						.ajax( {
							url : _basePath
									+ "/paper/PaperFilesManage!doDeletePaperFile.action?ID=" + encodeURI(ID),
							dataType : "json",
							success : function(json) {
								if (json.flag) {
									$('#pageTable').datagrid('load', {
										"param" : getFromData("#fromDate")
									});
								} else {
									alert(json.msg);
								}
							}
						});
				}
			});
	
}


//上传资料文件
function uploadPaperFile() {
	$("#uploadPaperFileDiv").show();
	$("#uploadPaperFileDiv")
			.dialog(
					{
						title : "上传资料文件",
						modal : true,
						resizable : true,
						buttons : [
								{
									id : "btnbc",
									text : '保 存',
									iconCls : 'icon-save',
									handler : function() {
										$('#uploadPaperFileForm')
												.form(
														{
															url : _basePath + '/paper/PaperFilesManage!uploadPaperFile.action',
															 onSubmit: function(){
																 var check = $("#fromDate").form('validate');
																 if(check){
																	 var file =
																	 $("#file").val();
																	 if(file == null ||
																		 file == ''){
																		 alert("请选择要上传的资料文件");
																		 return false;
																	 }
																	 $('#btnbc').linkbutton('disable');
																 }
															 },
															success : function(json) {
																json = jQuery
																		.parseJSON(json);
																if (json.flag) {
																	alert("保存成功");
																	$("#uploadPaperFileForm").form('clear');
																	$("#uploadPaperFileDiv")
																			.dialog(
																					'close');
																	$('#pageTable').datagrid('load', {
																		"param" : getFromData("#fromDate")
																	});
																} else {
																	alert(json.msg);
																}
																$("#uploadPaperFileForm").form('clear');
															}
														});
										$('#uploadPaperFileForm').submit();
									}
								}, {
									text : '关 闭',
									iconCls : 'icon-cancel',
									handler : function() {
										$("#uploadPaperFileDiv").dialog('close');
									}
								} ]
					});
}
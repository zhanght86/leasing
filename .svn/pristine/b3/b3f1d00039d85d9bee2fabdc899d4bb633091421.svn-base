$(function() {
	$("#fromDate").form('clear');
	$("#pageTable").datagrid(
					{
						url : _basePath + "/qsd/Qsd!findqsd.action",
						pagination : true,// 分页
						rownumbers : true,// 行数
						singleSelect : true,// 单选模式
						fitColumns : true,
						toolbar : '#pageForm',
						 frozenColumns:[[
											{
												field : 'TPM_ID',
												align : 'center',
												title : '操作',
												width : 100,
												formatter : function(value, rowData,rowIndex) 
												{
													var rehtml = "<a href='javascript:void(0);' onclick='uploadPdfTemplate("
																+ value
																+ ",\"uploadPdfTemplateDiv\",\"uploadPdfTemplateForm\")'>签收</a>";
													return rehtml;
												}
											} ]],
						columns : [ [
						            
								{
									field : 'CUST_NAME',
									align : 'center',
									width : 10,
									title : '客户名称'
								},
								{
									field : 'LEASE_CODE',
									align : 'center',
									width : 10,
									title : '融资租赁合同编号'
								},

								{
									field : 'PAYLIST_CODE',
									align : 'center',
									width : 10,
									title : '还款计划编号号'
								},
				
								{
									field : 'PLATFORM_TYPE',
									align : 'center',
									width : 10,
									title : '业务类型'
								},
								{
									field : 'COMPANY_NAME',
									align : 'center',
									width : 10,
									title : '厂商'
								},
								{
									field : 'PRODUCT_NAME',
									align : 'center',
									width : 10,
									title : '设备名称'
								},

								{
									field : 'SPEC_NAME',
									align : 'center',
									width : 10,
									title : '设备型号'
								},
								{
									field : 'SUPPLIERS_NAME',
									align : 'center',
									width : 10,
									title : '供应商'
								},
								{
									field : 'CAR_SYMBOL',
									align : 'center',
									width : 10,
									title : '整机编号/车架号'
								},
								{
									field : 'ENGINE_TYPE',
									align : 'center',
									width : 10,
									title : '发动机号'
								}
								] ],
						view : detailview,
						detailFormatter : function(index, row) {
							return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
						},
						onExpandRow : function(index, row) {
							jQuery.ajax( {
										type : "POST",
										dataType : "json",
										url : _basePath
												+ '/qsd/Qsd!toUnfoldDate.action?row='
												+ encodeURI(JSON.stringify(row)),
										// data: jsonData,
										success : function(json) {
											var data = {
												flag : json.flag,
												total : json.data.length,
												rows : json.data
											};
											var pRowIndex = "ddv-" + index;
											$('#ddv-' + index).datagrid(
															{
																fitColumns : true,
																singleSelect : true,
																rownumbers : true,
																loadMsg : '加载中...',
																height : 'auto',
																columns : [ [
																		{
																			field : 'ID',
																			align : 'center',
																			title : '操作',
																			width : 25,
																			formatter : function(value,rowData,rowIndex) 
																			{
																				var ddd = "<a href='javascript:void(0);' onclick='downloadPdf("
																							+ value
																							+ ")'>下载</a>&nbsp;";
																				ddd += "|&nbsp;<a href='javascript:void(0);' onclick='deleteqsd("
																					+ value
																					+ ",\""
																					+ rowData.TPF_TPM_ID
																					+ "\",\""
																					+ pRowIndex
																					+ "\","
																					+ rowIndex
																					+ ")'>删除</a>";
																				return ddd;
																			}
																		},
																		{
																			field : 'FILE_NAME',
																			align : 'center',
																			width : 18,
																			title : '文件名称'
																		},
																		{
																			field : 'RECEIVE_DATE',
																			align : 'center',
																			width : 10,
																			title : '签收时间'
																		},
																		{
																			field : 'FILE_URL',
																			align : 'center',
																			width : 10,
																			title : '文件路径'
																		}
																		 ] ],
																onResize : function() {
																	$('#pageTable').datagrid('fixDetailRowHeight',index);
																},
																onLoadSuccess : function() {
																	setTimeout(
																			function() {
																				$(
																						'#pageTable')
																						.datagrid(
																								'fixDetailRowHeight',
																								index);
																			},
																			0);
																}
															});
											$('#pageTable')
													.datagrid(
															'fixDetailRowHeight',
															index);
											$('#ddv-' + index).datagrid(
													"loadData", data);
										}
									});
						}
					} );
});
function uploadPdfTemplate(TPM_ID, divId, formId) {
	$("#TPM_ID").val(TPM_ID);
	$("#" + divId).show();
	$("#" + divId).dialog(
					{
						title : "上传",
						modal : true,
						resizable : true,
						buttons : [
								{
									id : "btnbc",
									text : '保 存',
									iconCls : 'icon-save',
									handler : function() {
										$('#btnbc').linkbutton('disable');
										$('#' + formId)
												.form(
														{
															url : _basePath + '/qsd/Qsd!uploadPdfTemplate.action',
															success : function(
																	json) {
																json = jQuery
																		.parseJSON(json);
																if (json.flag) {
																	alert("保存成功");
																	$("#"+ divId).dialog('close');
																	$('#pageTable').datagrid('load',
																					{
																						"param" : JSON.stringify(json.data)
																					});
																} else {
																	alert(json.msg);
																}
															}
														});
										$('#' + formId).submit();
									}
								}, {
									text : '关 闭',
									iconCls : 'icon-cancel',
									handler : function() {
										$("#" + divId).dialog('close');
									}
								} ]
					});
}
function downloadPdf(ID) {
	window.location.href = _basePath + "/qsd/Qsd!downPdfTemplate.action?ID=" + encodeURI(ID) + "&_datetime=" + new Date().getTime();
}
function deleteqsd(ID, TPM_ID, pRowIndex, rowIndex) {
	$.messager.confirm('提示框','确定要删除吗?',
					function(flag) {
						if (flag) {
							jQuery.ajax( {
										url : _basePath
												+ "/qsd/Qsd!doDeleteqsd.action?ID="
												+ encodeURI(ID)
												+ "&_datetime="
												+ new Date().getTime(),
										dataType : "json",
										success : function(json) {
											if (json.flag) {
												$('#pageTable').datagrid('load',
																{
																	"param" : JSON.stringify(json.data)
																});
											} else {
												alert(json.msg);
											}
										}
									});
						}
					});
}
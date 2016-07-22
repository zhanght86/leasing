//$(document).ready(function(){
var sealTempIndex = 0;
$(function() {
	$("#fromDate").form('clear');
	$("#pageTable")
			.datagrid(
					{
						url : _basePath
								+ "/pdfTemplate/PdfTemplateManagement!toMgPdfTemplate.action",
						pagination : true,// 分页
						rownumbers : true,// 行数
						singleSelect : true,// 单选模式
						fitColumns : true,
						toolbar : '#pageForm',
						columns : [ [
							{
								field : 'TPM_ID',
								align : 'center',
								title : '操作',
								width : 15,
								formatter : function(value, rowData,
										rowIndex) {
									var rehtml = "<a href='javascript:void(0);' onclick='viewDetails("
											+ value
											+ ",\"pdfDivShow\",\"pdfFormShow\")'>查看</a>&nbsp;";
//									if ("true" == $("#isUpdate").val()) {
										rehtml += "|&nbsp;<a href='javascript:void(0);' onclick='updatePdfTemplate("
												+ value
												+ ",\"pdfForm1\",\"editorPdfForm1\",\"修改模版\")'>修改</a>&nbsp;";
//									}
//									if ("true" == $("#isUpload").val()) {
										rehtml += "|&nbsp;<a href='javascript:void(0);' onclick='uploadPdfTemplate("
												+ value
												+ ",\"uploadPdfTemplateDiv\",\"uploadPdfTemplateForm\")'>上传</a>";
//									}
									return rehtml;
								}
							},
								// {field:'TPM_CODE',align:'center',width:10,title:'文本编号'},
								// Modify By:YangJ 2014年5月6日17:05:03
								{
									field : 'TPM_TYPE',
									align : 'center',
									width : 10,
									title : '模版类型'
								},
								/*
								 * {field:'TPM_FILE_TYPE',align:'center',width:10,title:'所属模版类型',formatter:function(value,rowData,rowIndex){
								 * if(value == '1'){ return "项目留购"; }else
								 * if(value == '2'){ return "项目回购"; }else
								 * if(value == '3'){ return "立项合同"; }else
								 * if(value == '4'){ return "租金变更"; } } },Add
								 * By: YangJ 2014年5月7日13:50:57
								 */
								{
									field : 'TPM_BUSINESS_PLATE',
									align : 'center',
									width : 10,
									title : '所属行业板块'
								},

								{
									field : 'TPM_BUSINESS_TYPE',
									align : 'center',
									width : 10,
									title : '业务类型'
								},
								// formatter:function(value,rowData,rowIndex){
								// if(value=="1")
								// return "直接租赁";
								// else if(value=="2")
								// return "标准型售后回租";
								// else if(value=="3")
								// return "标准型售后回租（委托）";
								// else if(value=="4")
								// return "销售型售后回租";
								// else if(value=="5")
								// return "转租赁";
								// else if(value=="6")
								// return "经营性租赁";
								// else if(value=="7")
								// return "委托租赁";
								// else if(value=="8")
								// return "联合租赁";
								// else if(value=="9")
								// return "厂商直接租赁";
								// else if(value=="10")
								// return "厂商售后回租";
								// else if(value=="11")
								// return "大项目租赁";
								// else
								// return "未知";
								// }},//add by yangj 2014年5月6日19:22:39 //modify
								// by yangj 2014年5月19日 12:20:14
								{
									field : 'TPM_CUSTOMER_TYPE',
									align : 'center',
									width : 10,
									title : '类型'
								},
								// formatter:function(value,rowData,rowIndex){
								// if(value=="LP")
								// return "法人";
								// else if(value=="NP")
								// return "个人";
								// }}, //modify by yangj 2014年5月19日 12:20:14
								{
									field : 'TPM_MUST_SELECT',
									align : 'center',
									width : 10,
									title : '必选',
									formatter : function(value, rowData,
											rowIndex) {
										if (value == "1")
											return "是";
										else if (value == "0")
											return "否";
										else
											return ("未知");
									}
								},

								{
									field : 'TPM_UPLOAD_DATE',
									align : 'center',
									width : 10,
									title : '创建日期'
								},
								{
									field : 'TPM_VERSION',
									align : 'center',
									width : 10,
									title : '启用版本'
								}
								 ] ],
						view : detailview,
						detailFormatter : function(index, row) {
							return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
						},
						onExpandRow : function(index, row) {
							jQuery
									.ajax( {
										type : "POST",
										dataType : "json",
										url : _basePath
												+ '/pdfTemplate/PdfTemplateManagement!toUnfoldDate.action?row='
												+ encodeURI(JSON.stringify(row)),
										// data: jsonData,
										success : function(json) {
											var data = {
												flag : json.flag,
												total : json.data.length,
												rows : json.data
											};
											var pRowIndex = "ddv-" + index;
											$('#ddv-' + index)
													.datagrid(
															{
																fitColumns : true,
																singleSelect : true,
																rownumbers : true,
																loadMsg : '加载中...',
																height : 'auto',
																columns : [ [
																		{
																			field : 'NAME',
																			align : 'center',
																			width : 18,
																			title : '模版名称'
																		},
																		{
																			field : 'START_DATE',
																			align : 'center',
																			width : 10,
																			title : '使用开始时间'
																		},
																		{
																			field : 'END_DATE',
																			align : 'center',
																			width : 10,
																			title : '使用结束时间'
																		},
																		{
																			field : 'PDF_SIZE',
																			align : 'center',
																			width : 10,
																			title : '模版大小'
																		},
																		{
																			field : 'PDF_VERSION',
																			align : 'center',
																			width : 10,
																			title : '版本号'
																		},
																		{
																			field : 'STATUS',
																			align : 'center',
																			width : 10,
																			title : '使用状态'
																		},
																		{
																			field : 'ID',
																			align : 'center',
																			title : '操作',
																			width : 25,
																			formatter : function(
																					value,
																					rowData,
																					rowIndex) {
																				if (rowData.STATUS == '启用') {
																					// "<a
																					// href='javascript:void(0);'
																					// onclick='pdfView("+value+")'>预览</a>&nbsp;|&nbsp;"+
																					var ddd = "<a href='javascript:void(0);' onclick='downloadPdf("
																							+ value
																							+ ")'>下载</a>&nbsp;";
																					if ("true" == $(
																							"#isUpdate")
																							.val()) {
																						ddd += "|&nbsp;<a href='javascript:void(0);' onclick='updatePdfFile("
																								+ value
																								+ ",\"uploadPdfTemplateDiv1\",\"uploadPdfTemplateForm1\",\"修改pdf模版\")'>修改</a>";
																					}
																					return ddd;
																				} else {
																					// "<a
																					// href='javascript:void(0);'
																					// onclick='pdfView("+value+")'>预览</a>&nbsp;|&nbsp;"+
																					var ddd = "<a href='javascript:void(0);' onclick='downloadPdf("
																							+ value
																							+ ")'>下载</a>&nbsp;";
																					if ("true" == $(
																							"#isStart")
																							.val()) {
																						ddd += "|&nbsp;<a href='javascript:void(0);' onclick='changeStatus("
																								+ value
																								+ ",\""
																								+ rowData.NAME
																								+ "\")'>启用</a>&nbsp;";
																					}
																					if ("true" == $(
																							"#isUpdate")
																							.val()) {
																						ddd += "|&nbsp;<a href='javascript:void(0);' onclick='updatePdfFile("
																								+ value
																								+ ",\"uploadPdfTemplateDiv1\",\"uploadPdfTemplateForm1\",\"修改pdf模版\")'>修改</a>&nbsp;";
																					}
																					if ("true" == $(
																							"#isDel")
																							.val()) {
																						ddd += "|&nbsp;<a href='javascript:void(0);' onclick='deletePDF("
																								+ value
																								+ ",\""
																								+ rowData.TPF_TPM_ID
																								+ "\",\""
																								+ pRowIndex
																								+ "\","
																								+ rowIndex
																								+ ")'>删除</a>";
																					}
																					return ddd;
																				}
																			}
																		} ] ],
																onResize : function() {
																	$(
																			'#pageTable')
																			.datagrid(
																					'fixDetailRowHeight',
																					index);
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
					});

	$(".checkInput").combobox(
			{
				onSelect : function() {
					var id = $(this).attr("id") + 1;
					var oldVal = $("#" + id).val();
					if (oldVal == null || oldVal == '') {
						$("#" + id).val($(this).combobox("getValue"));
					} else {
						$("#" + id).val(
								$("#" + id).val() + "；"
										+ $(this).combobox("getValue"));
					}
				}
			});

	$(".checkInput1").combobox(
			{
				onSelect : function() {
					var id = $(this).attr("id") + 1;
					var oldVal = $("#" + id).val();
					if (oldVal == null || oldVal == '') {
						$("#" + id).val($(this).combobox("getValue"));
					} else {
						$("#" + id).val(
								$("#" + id).val() + "；"
										+ $(this).combobox("getValue"));
					}
				}
			});
});

function addPdfTemplate(divId, formId, type) {
	$("#" + divId).show();
	$("#" + divId)
			.dialog(
					{
						title : type,
						modal : true,
						resizable : true,
						buttons : [
								{
									id : "btnbc",
									text : '保 存',
									iconCls : 'icon-save',
									handler : function() {
										if (checkOnly() == false) {
											return;
										}
										// if(checkNull()==false){
									// return;}

									$('#' + formId)
											.form(
													{
														url : _basePath + '/pdfTemplate/PdfTemplateManagement!doAddPdfTemplate.action',
														// 若文本框加非空等校验需用到此段代码
														// onSubmit: function(){
														// var check =
														// $("#"+formId).form('validate');
														// if(check){
														// var file =
														// $("#file").val();
														// if(file == null ||
														// file == ''){
														// alert("请选择要上传的pdf模版");
														// return false;
														// }
														// }else{
														// return check;
														// }
														// },
														success : function(json) {

															json = jQuery
																	.parseJSON(json);
															if (json.flag) {
																$('#btnbc')
																		.linkbutton(
																				'disable');
																alert("保存成功");
																$("#" + divId)
																		.append(
																				'<input type="hidden" name="CHECK_TPM_CODE" value="' + $(
																						"#EDITOR_TPM_CODE")
																						.val() + '"/>');
																$("#" + divId)
																		.dialog(
																				'close');
																// alert(JSON.stringify(json.data));
																$('#pageTable')
																		.datagrid(
																				'load',
																				{
																					"param" : JSON
																							.stringify(json.data)
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
	$("#" + formId).form('clear');
}

function checkNull() {
	var flag = true;
	alert($("#EDITOR_TPM_MUST_SELECT").val());
	if ($("#EDITOR_TPM_TYPE").val() == ""
			|| $("#EDITOR_TPM_TYPE").val() == "null"
			|| $("#EDITOR_TPM_TYPE").val() == null) {
		alert("模版类型不能为空！");
		flag = false;
	}
	if ($("#EDITOR_TPM_MUST_SELECT").val() == ""
			|| $("#EDITOR_TPM_MUST_SELECT").val() == "null"
			|| $("#EDITOR_TPM_MUST_SELECT").val() == null) {
		alert("是否必选不能为空！");
		flag = false;
	}
	if ($("#EDITOR_TPM_CUSTOMER_TYPE").val() == ""
			|| $("#EDITOR_TPM_CUSTOMER_TYPE").val() == "null"
			|| $("#EDITOR_TPM_CUSTOMER_TYPE").val() == null) {
		alert("模版对应的客户类型不能为空！");
		flag = false;
	}
	if ($("#EDITOR_TPM_BUSINESS_TYPE").val() == ""
			|| $("#EDITOR_TPM_BUSINESS_TYPE").val() == "null"
			|| $("#EDITOR_TPM_BUSINESS_TYPE").val() == null) {
		alert("业务类型不能为空！");
		flag = false;
	}
	if ($("#EDITOR_TPM_BUSINESS_PLATE1").val() == ""
			|| $("#EDITOR_TPM_BUSINESS_PLATE1").val() == "null"
			|| $("#EDITOR_TPM_BUSINESS_PLATE1").val() == null) {
		alert("所属板块不能为空！");
		flag = false;
	}
	if ($("#EDITOR_TPM_CONTRACT_NUMBER").val() == ""
			|| $("#EDITOR_TPM_CONTRACT_NUMBER").val() == "null"
			|| $("#EDITOR_TPM_CONTRACT_NUMBER").val() == null) {
		alert("数量不能为空！");
		flag = false;
	}
	return flag;
}

function updatePdfTemplate(TPM_ID, divId, formId, type) {
	$("#" + divId).show();
	$("#" + divId)
			.dialog(
					{
						title : type,
						modal : true,
						resizable : true,
						buttons : [
								{
									id : "btnbc",
									text : '保 存',
									iconCls : 'icon-save',
									handler : function() {

										var jsonData = $("#" + formId)
												.serialize();
										jQuery
												.ajax( {
													type : "POST",
													dataType : "json",
													url : _basePath
															+ '/pdfTemplate/PdfTemplateManagement!doUpdatePdfTemplate.action?TPM_ID='
															+ TPM_ID
															+ "&_datetime="
															+ new Date()
																	.getTime(),
													data : jsonData,
													success : function(json) {

														if (json.flag) {
															alert("保存成功");
															$('#btnbc')
																	.linkbutton(
																			'disable');
															// 文本编号可修改时放开此代码
															// $("#"+divId).append('<input
															// type="hidden"
															// name="CHECK_TPM_CODE"
															// value="'+$("#EDITOR_TPM_CODE").val()+'"/>');
															$("#" + formId)
																	.form(
																			'clear');
															$("#" + divId)
																	.dialog(
																			'close');
															$('#pageTable')
																	.datagrid(
																			'load',
																			{
																				"param" : JSON
																						.stringify(json.data)
																			});
														} else {
															alert(json.msg);
														}
													}
												});
									}
								}, {
									text : '关 闭',
									iconCls : 'icon-cancel',
									handler : function() {
										$("#" + divId).dialog('close');
									}
								} ]
					});

	jQuery
			.ajax( {
				url : _basePath
						+ "/pdfTemplate/PdfTemplateManagement!toUpdatePdfTemplate.action?TPM_ID="
						+ encodeURI(TPM_ID) + "&_datetime="
						+ new Date().getTime(),
				dataType : "json",
				success : function(json) {

					// alert(json.data.TPM_CONTRACT_NUMBER+" ｜｜
				// "+json.data.TPM_BUSINESS_TYPE+" ｜｜
				// "+json.data.TPM_BUSINESS_PLATE);
				$("#EDITOR_TPM_TYPE2").combobox('select', json.data.TPM_TYPE);
				$("#EDITOR_TPM_MUST_SELECT2").combobox('select',
						json.data.TPM_MUST_SELECT);
				// $("#EDITOR_TPM_CUSTOMER_TYPE2").combobox('select',
				// json.data.TPM_CUSTOMER_TYPE);
				// $("#EDITOR_TPM_BUSINESS_TYPE2").combobox('select',
				// json.data.TPM_BUSINESS_TYPE);
				$("#EDITOR_TPM_CUSTOMER_TYPE21").val(
						json.data.TPM_CUSTOMER_TYPE);
				$("#EDITOR_TPM_BUSINESS_TYPE21").val(
						json.data.TPM_BUSINESS_TYPE);// modify by YangJ
				// 2014年5月19日
				// 13:34:28
				$("#EDITOR_TPM_BUSINESS_PLATE21").val(
						json.data.TPM_BUSINESS_PLATE);
				$("#EDITOR_TPM_CONTRACT_NUMBER2").val(
						json.data.TPM_CONTRACT_NUMBER);
				$("#EDITOR_TPM_OTHER2").val(json.data.TPM_OTHER);
				$("#EDITOR_TPM_NOTE2").val(json.data.TPM_NOTE);
				$("#EDITOR_TPM_FUNCTION_DESCRIPTION2").val(
						json.data.TPM_FUNCTION_DESCRIPTION);
				$("#TPM_SIGNATURE_LOGO2").val(
						json.data.TPM_SIGNATURE_LOGO);
				$("#TPM_SEAL_SERVICE2").val(
						json.data.TPM_SEAL_SERVICE);
				// $("").val();combobox('select', json.data.TPM_FILE_TYPE)
				// $("").val();
				// $("").val();
				// $("").val();
				// $("").val();
				// $("").val();
				// $("").val();
				// $("").val();
				// $("").val();
				//			
				// $("#EDITOR_TPM_SIGNED_OCCASION1").val(json.data.TPM_SIGNED_OCCASION);
				// $("#EDITOR_TPM_USE_DEPARTMENT1").val(json.data.TPM_USE_DEPARTMENT);
				// $("#EDITOR_TPM_OTHER").val(json.data.TPM_OTHER);
				// $("#EDITOR_TPM_NOTE").val(json.data.TPM_NOTE);
				//			
				// $("#EDITOR_TPM_CODE1").val(json.data.TPM_CODE);
				//			
				// $("#EDITOR_TPM_SIGNED_OCCASION1").val(json.data.TPM_SIGNED_OCCASION);
				// $("#EDITOR_TPM_SIGNATURE_NAME31").val(json.data.TPM_SIGNATURE_NAME);
				// $("#EDITOR_TPM_TEXT_TYPE31").val(json.data.TPM_TEXT_TYPE);
				// $("#EDITOR_TPM_LEASE_WAY31").val(json.data.TPM_LEASE_WAY);
				// $("#EDITOR_TPM_MANUFACTURERS31").val(json.data.TPM_MANUFACTURERS);
				// $("#EDITOR_TPM_USE_DEPARTMENT31").val(json.data.TPM_USE_DEPARTMENT);
				// $("#EDITOR_TPM_SIGNED_OCCASION31").val(json.data.TPM_SIGNED_OCCASION);
				// $("#EDITOR_TPM_FUNCTION_DESCRIPTION").val(json.data.TPM_FUNCTION_DESCRIPTION);
				//			
				// $("#EDITOR_TPM_BUSINESS_PLATE31").val(json.data.TPM_BUSINESS_PLATE);
				// // $("#"+formId).form('validate');校验时用
				// $("#EDITOR_TPM_TYPE1").combobox('select',
				// json.data.TPM_TYPE);
				// $("#EDITOR_TPM_CUSTOMER_TYPE1").combobox('select',
				// json.data.TPM_CUSTOMER_TYPE);
				// $("#EDITOR_TPM_APPLY_AGENT1").combobox('select',
				// json.data.TPM_APPLY_AGENT);
				// $("#EDITOR_TPM_SALES_WAY1").combobox('select',
				// json.data.TPM_SALES_WAY);
				// $("#EDITOR_TPM_SIGNATURE1").combobox('select',
				// json.data.TPM_SIGNATURE);
				// $("#EDITOR_TPM_BREACH_MONEY1").combobox('select',
				// json.data.TPM_BREACH_MONEY);
				// $("#EDITOR_TPM_FILE_TYPE1").combobox('select',
				// json.data.TPM_FILE_TYPE);
				// // $("#"+formId).form('validate');校验时用
				var fromId=$("#eq_template_Value").val();//TODO
				var toId=$("#eq_body_Value").val();
				$("#"+toId).html('');
				sealTempIndex=0;
				if (json.data.fryzTemplate != undefined && json.data.fryzTemplate != null && json.data.fryzTemplate != '') {
					var jsonTemp = json.data.fryzTemplate;
					for(var i=0; i<jsonTemp.length; i++){
						
						var tr=$("#"+fromId).clone();
						$(tr).removeAttr("style");
						$(tr).removeAttr("id");
						$(tr).attr("class","eq_body_tr");
						$(tr).find("select[name*=SEAL_PATH_TBA]")
						.val(jsonTemp[i].SEAL_PATH);
						$(tr).find("select[name*=SEAL_PATH_TBA]").attr("name",'SEAL_PATH_TBA_'+sealTempIndex);
						$(tr).find("input[name='TPM_SEAL_SERVICE2_TAB']").attr("value",jsonTemp[i].TPM_SEAL_SERVICE);
						$(tr).find("input[name='TPM_SEAL_SERVICE2_TAB']").attr("name",'TPM_SEAL_SERVICE2_TAB_'+sealTempIndex);
						$("#"+toId).append(tr);
						//$.parser.parse(tr);
						sealTempIndex++;
					}
				}
			}
			});
}

function updatePdfFile(ID, divId, formId, type) {
	$("#" + divId).show();
	$("#" + divId)
			.dialog(
					{
						title : type,
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
															url : _basePath + '/pdfTemplate/PdfTemplateManagement!doUpdatePdfFile.action',
															// 若文本框加非空等校验需用到此段代码
															// onSubmit:
															// function(){
															// var check =
															// $("#"+formId).form('validate');
															// if (check) {
															// return check;
															// }
															// },
															success : function(
																	json) {
																json = jQuery
																		.parseJSON(json);
																if (json.flag) {
																	alert("保存成功");
																	$(
																			"#"
																					+ divId)
																			.dialog(
																					'close');
																	$(
																			'#pageTable')
																			.datagrid(
																					'load',
																					{
																						"param" : JSON
																								.stringify(json.data)
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
	$("#" + formId).form('clear');
	jQuery
			.ajax( {
				url : _basePath
						+ "/pdfTemplate/PdfTemplateManagement!toUpdatePdfFile.action?ID="
						+ encodeURI(ID) + "&_datetime=" + new Date().getTime(),
				dataType : "json",
				success : function(json) {
					$("#ID").val(ID);
					$("#TPM_ID1").val(json.data.TPF_TPM_ID);
					$("#START_DATE1").datebox("setValue", json.data.START_DATE);
					$("#END_DATE1").datebox("setValue", json.data.END_DATE);
					// $("#"+formId).form('validate');校验时用1
				}
			});
}

function conditionsSelect() {
	$('#pageTable').datagrid('load', {
		"param" : getFromData("#fromDate")
	});
}

function clearSelect(formId) {
	$("#" + formId).form('clear');
}

function deletePDF(ID, TPM_ID, pRowIndex, rowIndex) {
	$.messager
			.confirm(
					'提示框',
					'确定要删除吗?',
					function(flag) {
						if (flag) {
							jQuery
									.ajax( {
										url : _basePath
												+ "/pdfTemplate/PdfTemplateManagement!doDeletePdfTemplate.action?ID="
												+ encodeURI(ID) + "&TPM_ID="
												+ encodeURI(TPM_ID)
												+ "&_datetime="
												+ new Date().getTime(),
										dataType : "json",
										success : function(json) {
											if (json.flag) {
												$('#pageTable')
														.datagrid(
																'load',
																{
																	"param" : JSON
																			.stringify(json.data)
																});
											} else {
												alert(json.msg);
											}
										}
									});
						}
					});
}

function downloadPdf(ID) {
	window.location.href = _basePath
			+ "/pdfTemplate/PdfTemplateManagement!downPdfTemplate.action?ID="
			+ encodeURI(ID) + "&_datetime=" + new Date().getTime();
}

function changeStatus(ID, NAME) {
	$.messager
			.confirm(
					'提示框',
					'确定启用模版\"' + NAME + '\"?',
					function(flag) {
						if (flag) {
							jQuery
									.ajax( {
										url : _basePath
												+ "/pdfTemplate/PdfTemplateManagement!doUpdateChangeStatus.action?ID="
												+ encodeURI(ID) + "&_datetime="
												+ new Date().getTime(),
										dataType : "json",
										success : function(json) {
											if (json.flag) {
												$('#pageTable')
														.datagrid(
																'load',
																{
																	"param" : JSON
																			.stringify(json.data)
																});
											} else {
												alert(json.msg);
											}
										}
									});
						}

					});
}

function pdfView(ID) {
	top.addTab("PDF模版查看", _basePath
			+ "/pdfTemplate/PdfTemplateManagement!toShowDetails.action?ID="
			+ encodeURI(ID) + "&_datetime=" + new Date().getTime());
}

// function checkInputValue(thisInput){
// alert($(thisInput).attr("id"));
// }

function checkOnly() {
	var inputs = $("input[name='CHECK_TPM_CODE']");
	for ( var i = 0; i < inputs.length; i++) {
		if ($(inputs[i]).val() == $("#EDITOR_TPM_CODE").val()) {
			alert("文本编号已存在，请重新填写其他文本编号");
			return false;
		}
	}
	return true;
}

function viewDetails(TPM_ID, divId, formId) {
	$("#" + divId).show();
	$("#" + divId).dialog( {
		title : "模版详情",
		modal : true,
		resizable : true,
		buttons : [ {
			text : '关 闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#" + divId).dialog('close');
			}
		} ]
	});
	$("#" + formId).form('clear');
	jQuery
			.ajax( {
				url : _basePath
						+ "/pdfTemplate/PdfTemplateManagement!toShowPdfTemplate.action?TPM_ID="
						+ encodeURI(TPM_ID) + "&_datetime="
						+ new Date().getTime(),
				dataType : "json",
				success : function(json) {
					$("#EDITOR_TPM_TYPE3").combobox('select',
							json.data.TPM_TYPE);
					$("#EDITOR_TPM_MUST_SELECT3").combobox('select',
							json.data.TPM_MUST_SELECT);
					// $("#EDITOR_TPM_CUSTOMER_TYPE3").combobox('select',
					// json.data.TPM_CUSTOMER_TYPE);
					// $("#EDITOR_TPM_BUSINESS_TYPE3").combobox('select',
					// json.data.TPM_BUSINESS_TYPE);
					$("#EDITOR_TPM_CUSTOMER_TYPE31").val(
							json.data.TPM_CUSTOMER_TYPE);
					$("#EDITOR_TPM_BUSINESS_TYPE31").val(
							json.data.TPM_BUSINESS_TYPE);// modify by yangj
					// 2014年5月19日
					// 13:37:14
					$("#EDITOR_TPM_BUSINESS_PLATE31").val(
							json.data.TPM_BUSINESS_PLATE);
					$("#EDITOR_TPM_CONTRACT_NUMBER3").val(
							json.data.TPM_CONTRACT_NUMBER);
					$("#EDITOR_TPM_OTHER3").val(json.data.TPM_OTHER);
					$("#EDITOR_TPM_NOTE3").val(json.data.TPM_NOTE);
					$("#EDITOR_TPM_FUNCTION_DESCRIPTION3").val(
							json.data.TPM_FUNCTION_DESCRIPTION);
					$("#TPM_SIGNATURE_LOGO1").val(
							json.data.TPM_SIGNATURE_LOGO);
					$("#TPM_SEAL_SERVICE1").val(
							json.data.TPM_SEAL_SERVICE);
					// $("#SHOW_TPM_SIGNED_OCCASION").val(json.data.TPM_SIGNED_OCCASION);
					// $("#SHOW_TPM_USE_DEPARTMENT").val(json.data.TPM_USE_DEPARTMENT);
					// $("#SHOW_TPM_OTHER").val(json.data.TPM_OTHER);
					// $("#SHOW_TPM_NOTE").val(json.data.TPM_NOTE);
					// $("#SHOW_TPM_CODE").val(json.data.TPM_CODE);
					// $("#SHOW_TPM_SIGNED_OCCASION").val(json.data.TPM_SIGNED_OCCASION);
					// $("#SHOW_TPM_SIGNATURE_NAME").val(json.data.TPM_SIGNATURE_NAME);
					// $("#SHOW_TPM_TEXT_TYPE").val(json.data.TPM_TEXT_TYPE);
					// $("#SHOW_TPM_LEASE_WAY").val(json.data.TPM_LEASE_WAY);
					// $("#SHOW_TPM_MANUFACTURERS").val(json.data.TPM_MANUFACTURERS);
					// $("#SHOW_TPM_USE_DEPARTMENT").val(json.data.TPM_USE_DEPARTMENT);
					// $("#SHOW_TPM_SIGNED_OCCASION").val(json.data.TPM_SIGNED_OCCASION);
					// $("#SHOW_TPM_FUNCTION_DESCRIPTION").val(json.data.TPM_FUNCTION_DESCRIPTION);
					// $("#SHOW_TPM_TYPE").val(json.data.TPM_TYPE);
					// $("#SHOW_TPM_CUSTOMER_TYPE").val(json.data.TPM_CUSTOMER_TYPE);
					// $("#SHOW_TPM_APPLY_AGENT").val(json.data.TPM_APPLY_AGENT);
					// $("#SHOW_TPM_BUSINESS_PLATE").val(json.data.TPM_BUSINESS_PLATE);
					// $("#SHOW_TPM_SALES_WAY").val(json.data.TPM_SALES_WAY);
					// $("#SHOW_TPM_SIGNATURE").val(json.data.TPM_SIGNATURE);
					// $("#SHOW_TPM_BREACH_MONEY").val(json.data.TPM_BREACH_MONEY);
					// if(json.data.TPM_FILE_TYPE == '1'){
					// $("#SHOW_TPM_FILE_TYPE").val("项目留购");
					// }else if(json.data.TPM_FILE_TYPE == '2'){
					// $("#SHOW_TPM_FILE_TYPE").val("项目回购");
					// }else if(json.data.TPM_FILE_TYPE == '3'){
					// $("#SHOW_TPM_FILE_TYPE").val("立项合同");
					// }else if(json.data.TPM_FILE_TYPE == '4'){
					// $("#SHOW_TPM_FILE_TYPE").val("租金变更");
					// }
					var fromId=$("#eq_template_ValueToShow").val();//TODO
					var toId=$("#eq_body_ValueToShow").val();
					$("#"+toId).html('');
					if (json.data.fryzTemplate != undefined && json.data.fryzTemplate != null && json.data.fryzTemplate != '') {
						var jsonTemp = json.data.fryzTemplate;
						for(var i=0; i<jsonTemp.length; i++){
							
							var tr=$("#"+fromId).clone();
							$(tr).removeAttr("style");
							$(tr).removeAttr("id");
							$(tr).attr("class","eq_body_tr");
							$(tr).find("select[name*=SEAL_PATH_TBA]")
							.val(jsonTemp[i].SEAL_PATH);
							$(tr).find("input[name='TPM_SEAL_SERVICE1_TAB']").attr("value",jsonTemp[i].TPM_SEAL_SERVICE);
							$(tr).find("input[name='TPM_SEAL_SERVICE1_TAB']").attr("readonly",true);
							$("#"+toId).append(tr);
							//$.parser.parse(tr);
						}
					}
				}
			});
}

function uploadPdfTemplate(TPM_ID, divId, formId) {
	$("#TPM_ID").val(TPM_ID);
	$("#" + divId).show();
	$("#" + divId)
			.dialog(
					{
						title : "上传PDF模版",
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
															url : _basePath + '/pdfTemplate/PdfTemplateManagement!uploadPdfTemplate.action',
															success : function(
																	json) {
																json = jQuery
																		.parseJSON(json);
																if (json.flag) {
																	alert("保存成功");
																	$(
																			"#"
																					+ divId)
																			.dialog(
																					'close');
																	$(
																			'#pageTable')
																			.datagrid(
																					'load',
																					{
																						"param" : JSON
																								.stringify(json.data)
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

function copyTrFAQYYZ()
{
	var fromId=$("#eq_template_Value").val();
	var toId=$("#eq_body_Value").val();
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","eq_body_tr");
	$(tr).find("select[name*=SEAL_PATH_TBA]").attr("name",'SEAL_PATH_TBA_'+sealTempIndex);
	$(tr).find("input[name='TPM_SEAL_SERVICE2_TAB']").attr("name",'TPM_SEAL_SERVICE2_TAB_'+sealTempIndex);
	$("#"+toId).append(tr);
	$.parser.parse(tr);
	sealTempIndex++;
}

function copyTrToAddFAQYYZ()
{
	var fromId=$("#eq_template_ValueToAdd").val();
	var toId=$("#eq_body_ValueToAdd").val();
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","eq_body_tr");
	$(tr).find("select[name*=SEAL_PATH_TBA]").attr("name",'SEAL_PATH_TBA_'+sealTempIndex);
	$(tr).find("input[name='TPM_SEAL_SERVICE3_TAB']").attr("name",'TPM_SEAL_SERVICE2_TAB_'+sealTempIndex);
	$("#"+toId).append(tr);
	$.parser.parse(tr);
	sealTempIndex++;
}

/**
 * 删除tbody中复选框被选中的某行
 * @param {Object} elementId
 */
function deleteTr(tbodyId)
{
	$("#"+tbodyId+"> tr").each(function (){
		var box=$(this).find(":checkbox");
		if ($(box).attr("checked"))
		{
			$(this).remove();
		}
	});
}

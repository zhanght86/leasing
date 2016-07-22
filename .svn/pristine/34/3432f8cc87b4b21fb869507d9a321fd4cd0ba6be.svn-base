$(document).ready(function() {
	$('#dialogDiv').dialog({
		onClose : function() {
			$("#fromDate").form('clear');
			$('#CUSTOMER_TYPE1').combobox({
				required : false
			});
			$('#MARRIAGE_SITUATION1').combobox({
				required : false
			});
			// window.location.href = _basePath +
			// '/api/datalist/DataList.action'
		}
	});
});

// 校验是否重复
function ifRepeat() {
	jQuery.ajax({
		url : "DataList!toValidateName.action?_dateTime=" + new Date(),
		data : "WARRANT_NAME1=" + $("#WARRANT_NAME1").val(),
		type : "post",
		dataType : "json",
		success : function(json) {
			// console.info(json);
			if (json.flag) {
				alert("权证名称已存在,请重新输入");
				$("#WARRANT_NAME1").attr("value", "");
			}
		}
	});
}

function addDataList() {
	$("#fromDate").form('clear');
	$('#CUSTOMER_TYPE1').combobox('disable');
	$('#MARRIAGE_SITUATION1').combobox('disable');
	$(".checkInput").combobox({
		onSelect : function() {
			var f = $("#WARRANT_TYPE1").combobox('getValue');
			var p = $("#CUSTOMER_TYPE1").combobox('getValue');
			if (f == 2 || f == 4) {
				$('#CUSTOMER_TYPE1').combobox('enable');
				$('#MARRIAGE_SITUATION1').combobox('enable');
				if (p == '1') {
					$('#MARRIAGE_SITUATION1').combobox('enable');
				} else if (p == '2') {
					$('#MARRIAGE_SITUATION1').combobox('clear');
					$('#MARRIAGE_SITUATION1').combobox('disable');
				}
			} else {
				$('#CUSTOMER_TYPE1').combobox('clear');
				$('#MARRIAGE_SITUATION1').combobox('clear');
				$('#CUSTOMER_TYPE1').combobox('disable');
				$('#MARRIAGE_SITUATION1').combobox('disable');
			}

		}

	});
	$("#dialogDiv")
			.dialog(
					{
						title : "添 加",
						buttons : [
								{
									id : "btnbc",
									text : '保 存',
									iconCls : 'icon-save',
									handler : function() {
										$('#btnbc').linkbutton('disable');

										if ($('#fromDate').form('validate')) {
											jQuery
													.ajax({
														url : "DataList!doAddDataList.action?_dateTime="
																+ new Date(),
														data : "param="
																+ getFromData("#fromDate"),
														type : "post",
														dataType : "json",
														success : function(json) {
															if (json.flag) {

																$("#dialogDiv")
																		.dialog(
																				'close');
																$('#pageTable')
																		.datagrid(
																				'load',
																				{
																					ID : json.data.ID
																				});
																alert('保存成功！');
															} else {
																$('#btnbc')
																		.linkbutton(
																				'enable');
																$.messager
																		.alert(
																				'提示',
																				'保存失败，请与管理员联系！',
																				'warning');
															}
														}
													})
										} else {
											$('#btnbc').linkbutton('enable');
										}
									}
								}, {
									text : '关 闭',
									iconCls : 'icon-cancel',
									handler : function() {
										$("#dialogDiv").dialog('close');

									}
								} ]
					});
	$("#dialogDiv").dialog('open');
}

// 搜索
function conditionsSelect() {
	$('#pageTable').datagrid('load', {
		WARRANT_NAME : $("#WARRANT_NAME").val(),
		WARRANT_TYPE : $("#WARRANT_TYPE").combobox('getValue'),
		WARRANT_GRADE : $("#WARRANT_GRADE").combobox('getValue')
	});
}

function getOperation(value, rowDate, index) {
	return "<a href='javascript:void(0)' onclick='toFindDataList(" + value
			+ ")'>查看</a>" + "&nbsp|&nbsp"
			+ "<a href='javascript:void(0)' onclick='toUpdateDataList(" + value
			+ ")'>修改</a>" + "&nbsp|&nbsp"
			+ "<a href='javascript:void(0)' onclick='deleteDataList(" + value
			+ "," + index + ")'>删除</a>";
}
function toFindDataList(ID) {
	jQuery.ajax({
		url : "DataList!getDataListData.action?_dateTime=" + new Date(),
		data : "ID=" + ID,
		type : "post",
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				$("#fromDate1 input[name='ID']").val(json.data.ID);

				$("#WARRANT_NAME2").val(json.data.WARRANT_NAME);
				$("#WARRANT_TYPE2").combobox('select', json.data.WARRANT_TYPE);
				$("#WARRANT_GRADE2")
						.combobox('select', json.data.WARRANT_GRADE);
				$("#CUSTOMER_TYPE2")
						.combobox('select', json.data.CUSTOMER_TYPE);
				$("#MARRIAGE_SITUATION2").combobox('select',
						json.data.MARRIAGE_SITUATION);
				$("#dialogDivShow").dialog({
					title : "查 看",
					buttons : [ {
						text : '关 闭',
						iconCls : 'icon-cancel',
						handler : function() {
							$("#dialogDivShow").dialog('close');
						}
					} ]
				});
				$("#dialogDivShow").dialog('open');
			} else {
				$.messager.alert('提示', '加载失败，请与管理员联系！');
			}
		}
	})
}
function clearInput() {
	$("#pageForm input").val("");
}

function toUpdateDataList(ID) {
	jQuery
			.ajax({
				url : "DataList!getDataListData.action?_dateTime=" + new Date(),
				data : "ID=" + ID,
				type : "post",
				dataType : "json",
				success : function(json) {
					if (json.flag) {
						$("#fromDate input[name='ID']").val(json.data.ID);

						$("#WARRANT_NAME1").val(json.data.WARRANT_NAME);
						$("#WARRANT_TYPE1").combobox('select',
								json.data.WARRANT_TYPE);
						$("#WARRANT_GRADE1").combobox('select',
								json.data.WARRANT_GRADE);
						$("#CUSTOMER_TYPE1").combobox('select',
								json.data.CUSTOMER_TYPE);
						$("#MARRIAGE_SITUATION1").combobox('select',
								json.data.MARRIAGE_SITUATION);
						$("#dialogDiv")
								.dialog(
										{
											title : "修 改",
											buttons : [
													{
														id : "btnbc_update",
														text : '保 存',
														iconCls : 'icon-save',
														handler : function() {
															$('#btnbc_update')
																	.linkbutton(
																			'disable');
															jQuery
																	.ajax({
																		url : "DataList!doUpdateDataList.action?_dateTime="
																				+ new Date(),
																		data : "param="
																				+ getFromData("#fromDate"),
																		type : "post",
																		dataType : "json",
																		success : function(
																				json) {
																			if (json.flag) {
																				$("#dialogDiv").dialog('close');
																				$('#pageTable').datagrid('load',{ID : json.data.ID});
																				alert('保存成功！');
																			} else {
																				$('#btnbc_update').linkbutton('enable');
																				$.messager.alert('提示','保存失败，请与管理员联系！','warning');
																			}
																		}
																	})
														}
													},
													{
														text : '关 闭',
														iconCls : 'icon-cancel',
														handler : function() {
															$("#dialogDiv")
																	.dialog(
																			'close');
														}
													} ]
										});
						$("#dialogDiv").dialog('open');
					} else {
						$.messager.alert('提示', '加载失败，请与管理员联系！');
					}
				}
			})
}

function deleteDataList(ID, index) {
	$('#pageTable').datagrid('selectRow', index);
	var opts = $('#pageTable').datagrid('getSelected');
	jQuery.messager.confirm('提示', '确定删除权证名称为"' + opts.WARRANT_NAME + '"的数据吗？',
			function(r) {
				if (r) {
					jQuery.ajax({
						url : "DataList!doDeleteDataList.action?ID=" + ID
								+ "&_dateTime=" + new Date(),
						type : "post",
						dataType : "json",
						success : function(json) {
							if (json.flag) {
								$('#pageTable').datagrid('load');
								alert("删除成功");
							} else {
								$.messager.alert('提示', '删除失败，请与管理员联系！',
										'warning');
							}
						}
					});
				}
			});
}

function exportExcel() {
	window.location.href = _basePath
			+ "/api/datalist/DataList!exportExcel.action";
}
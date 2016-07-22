$(function() {
	leeds.keyer.winEnter(query);
	// 新增记录弹窗
	var recordDivOptions = leeds.dialogInitOp('recordDiv', 'recordForm',
			addRecords, 800, {top:10});
	$('#recordDiv').dialog(recordDivOptions);
	 
	// 记录列表
	$('#recordList').datagrid({
//		title : '资料管理',
//		iconCls : 'icon-edit',
		url : _basePath + '/leeds/materialMgt/MaterialMgt!toRecordMainPage.action',
		queryParams : {
			
		},
		columns : [[{
					field : 'MEMO',
					title : '备注',
					width : 150,
					align : 'center'
				}, {
					field : 'PHASE_V',
					title : '阶段',
					width : 70,
					align : 'center'
				}, {
					field : 'FACTORS',
					title : '条件',
					width : 200,
					align : 'center'
				}, {
					field : 'FILES',
					title : '资料清单',
					width : 250,
					align : 'center'
				}, {
					field : 'ID',
					title : '操作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						var res = '';
						res += '<a href="javascript:void(0)" onclick="toUpdate('
								+ value + ')">修改</a>'
								+ ' | <a href="javascript:void(0)" onclick="delRecord('
								+ value + ')">删除</a>';
						return res;
					}
				}]],
		fitColumns : true,
		striped : true,
		nowrap: false,
		pagination : true,
		pageSize: 20,
		rownumbers : true,
		singleSelect : true,
		toolbar : '#tbRecord'
	});
});

// ----------------------------记录，增删改查---------------------begin--------

// toUpdate
function toUpdate(id) {
	loadRecord(id);
	var recordDivOptions1 = leeds.dialogInitOp('recordDiv', 'recordForm',
			updRecord, 800, {top:10});
	$('#recordDiv').dialog(recordDivOptions1);
	$('#recordDiv').dialog('open');
}

// delRecord
function delRecord(id) {
	leeds.del(id, _basePath + '/leeds/materialMgt/MaterialMgt!delRecord.action',
			'recordList');
}
// loadRecord
function loadRecord(id) {
	var url = _basePath + '/leeds/materialMgt/MaterialMgt!loadRecord.action';
	$.ajax({
		url : url,
		data : 'ID=' + id,
		dataType : 'json',
		success : function(res) {
			$('#recordForm').form('load', res.data);
			$.each(res.data.factors, function(i,n){
				$('#'+n.FACTOR_SYS+'_'+n.FACTOR_DICT_ID).attr('checked', true);
			});
			
			$.each(res.data.files, function(i,n){
				var c = i+1;
				$("#FILE_TYPE"+n.FILE_DICT_ID).find("input[class='zlqd']").each(function(){
					var dicId = $(this).attr("dictid");
					if(dicId == n.FILE_DICT_ID){
						if($(this).val()==n.TYPE){
							$(this).attr("checked",true);
						}
					}
				});
				
//				$("input[name='TYPE"+c+"']").each(function(){
//					var dicId = $(this).attr("dictid");
//					if(dicId == n.FILE_DICT_ID){
//						if($(this).val()==n.TYPE){
//							$(this).attr("checked",true);
//						}
//					}
//				});				
			});
		}
	});
	
}
// addRecords
function addRecords() {
	if($('#recordForm input[name=FACTOR]:checked').size()==0) {
		leeds.msg('请最少选择一项【条件】！');
		return;
	}
	
	var data1 = [];
	$(".factor").each(function(){
		var temp2 = {};
		temp2.FACTOR_DICT_ID = $(this).find("input:checkbox:checked").val();
		temp2.FACTOR_SYS = $(this).find("input:checkbox:checked").attr("sysFactorValue");
		data1.push(temp2);
	});
	
	var data2 = [];
	var count = 1;
	$(".qingdanlist").each(function(){
		var temp3 = {};
		temp3.FILE_DICT_ID = $(this).find("input[name=FILE_DICT_ID]").val();
		temp3.TYPE = $(this).find("input:radio:checked").val();
		data2.push(temp3);
	});
	
	var resutlData = {
		data1:data1,
		data2:data2,
		PHASE:$("#PHASE").combobox('getValue'),
		MEMO:$("#MEMO").val()
	};
	
	jQuery.ajax({
		type:"post",
		url: _basePath + '/leeds/materialMgt/MaterialMgt!addRecords.action',
		data: "param=" + encodeURI(JSON.stringify(resutlData)),
		dataType: "json",
		success: function(data){
			if (data.flag == false) {
				alert("失败!");
				$('#recordDiv').dialog('close');
			}
			else {
				alert("成功");
				$('#recordDiv').dialog('close');
				window.location.href = _basePath + '/leeds/materialMgt/MaterialMgt.action';
			}
		}		
	});
	
//	$('#recordForm').form('submit', {
//		url : _basePath + '/leeds/materialMgt/MaterialMgt!addRecords.action',
//		onSubmit : function(param) {
//			
//		},
//		success : function(data) {
//			$('#recordList').datagrid('load');
//			$('#recordDiv').dialog('close');
//		}
//	});
}
// updRecord
function updRecord() {
	if($('#recordForm input[name=FACTOR]:checked').size()==0) {
		leeds.msg('请最少选择一项【条件】！');
		return;
	}
	
	var data1 = [];
	$(".factor").each(function(){
		var temp2 = {};
		temp2.FACTOR_DICT_ID = $(this).find("input:checkbox:checked").val();
		temp2.FACTOR_SYS = $(this).find("input:checkbox:checked").attr("sysFactorValue");
		data1.push(temp2);
	});
	
	var data2 = [];
	$(".qingdanlist").each(function(){
		var temp3 = {};
		temp3.FILE_DICT_ID = $(this).find("input[name=FILE_DICT_ID]").val();
		temp3.TYPE = $(this).find("input:radio:checked").val();
		data2.push(temp3);
	});
	
	var resutlData = {
		data1:data1,
		data2:data2,
		PHASE:$("#PHASE").combobox('getValue'),
		MEMO:$("#MEMO").val(),
		ID:$("#M_ID").val()
	};
	
	jQuery.ajax({
		type:"post",
		url: _basePath + '/leeds/materialMgt/MaterialMgt!updRecord.action',
		data: "param=" + encodeURI(JSON.stringify(resutlData)),
		dataType: "json",
		success: function(data){
			if (data.flag == false) {
				alert("失败!");
				$('#recordDiv').dialog('close');
			}
			else {
				alert("成功");
				$('#recordDiv').dialog('close');
				window.location.href = _basePath + '/leeds/materialMgt/MaterialMgt.action';
			}
		}		
	});
	
//	if($('#recordForm input[name=FILE_NAME]:checked').size()==0) {
//		leeds.msg('请最少选择一项【资料清单】！');
//		return;
//	}
//	$('#recordForm').form('submit', {
//		url : _basePath + '/leeds/materialMgt/MaterialMgt!updRecord.action',
//		onSubmit : function(param) {
//			
//		},
//		success : function(data) {
//			$('#recordList').datagrid('load');
//			$('#recordDiv').dialog('close');
//		}
//	});
}
function query() {
	var params = leeds.getFormParams('queryForm');
	$("#recordList").datagrid('load', params);
}

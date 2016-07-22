$(function() {
	leeds.keyer.winEnter(query);
	// 新增记录弹窗
	var recordDivOptions = leeds.dialogInitOp('recordDiv', 'recordForm',
			saveRecord, 800);
	$('#recordDiv').dialog(recordDivOptions);

	// 记录列表
	$('#recordList').datagrid({
//		title : '话术管理',
//		iconCls : 'icon-edit',
		fit: true,
		url : _basePath + '/leeds/talkSkill/TalkSkill!toRecordMainPage.action',
		queryParams : {
			
		},
		columns : [[{
					field : 'TYPE_V',
					title : '类别',
					width : 100,
					align : 'center'
				}, {
					field : 'TITLE',
					title : '标题',
					width : 100,
					align : 'center'
				}, {
					field : 'CONTENT',
					title : '模板',
					width : 300,
					align : 'center'
				}, {
					field : 'MEMO',
					title : '备注',
					width : 150,
					align : 'center'
				}, {
					field : 'ID',
					title : '操作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						var res = '';
						if(row.IS_VALID=='0') {//未启用
							res = '<a href="javascript:void(0)" onclick="signIn('
								+ row.ID+ ')"' 
								+' title="点击启用">' +
								'<font style="color:red; margin-left:5px">未启用</font></a>';
						} else {
							res = '<a href="javascript:void(0)" onclick="signOut('
								+ row.ID
								+ ')" title="点击禁用">' +
								'<font style="color:green; margin-left:5px">已启用</font></a>';
						}
						
						res += ' | <a href="javascript:void(0)" onclick="updateRecord('
								+ value
								+ ')">修改</a>'
								+ ' | <a href="javascript:void(0)" onclick="delRecord('
								+ value + ')">删除</a>';
						return res;
					}
				}]],
		nowrap: false,
		fitColumns : true,
		striped : true,
		pagination : true,
		pageSize: 20,
		rownumbers : true,
		singleSelect : true,
		toolbar : '#tbRecord'
	});
});

// ----------------------------记录，增删改查---------------------begin--------

// updateRecord
function updateRecord(id) {
	loadRecord(id);
	var equipDivOptions = leeds.dialogInitOp('recordDiv', 'recordForm',
			saveRecord, 750);
	$('#recordDiv').dialog(equipDivOptions);
	$('#recordDiv').dialog('open');
}

// delRecord
function delRecord(id) {
	leeds.del(id, _basePath + '/leeds/talkSkill/TalkSkill!delRecord.action',
			'recordList');
}
// loadRecord
function loadRecord(id) {
	var url = _basePath + '/leeds/talkSkill/TalkSkill!loadRecord.action';
	leeds.loadToForm(id, url, 'recordForm');
}
// saveRecord
function saveRecord() {
	$('#recordForm').form('submit', {
		url : _basePath + '/leeds/talkSkill/TalkSkill!saveRecord.action',
		onSubmit : function(param) {
			
		},
		success : function(data) {
			$('#recordList').datagrid('load');
			$('#recordDiv').dialog('close');
		}
	});
}
function query() {
	var params = leeds.getFormParams('queryForm');
	$("#recordList").datagrid('load', params);
}

// ----------------------------记录，增删改查---------------------end--------
// 导出
function exportMoni() {
	if ($('#recordList').datagrid('getRows').length == 0) {
		$.messager.alert('提示', '无数据！');
		return;
	}
	var params = $('#queryForm').serialize();
	window.location.href = _basePath
			+ '/leeds/sign_in/SignIn!exportMoni.action?'+params;
}
//----------启用/禁用begin
function signIn(id) {
	sign(1, id);
}
function signOut(id) {
	sign(0, id);
}
function sign(signStatus, id) {
	
	$.ajax({
		url: _basePath + '/leeds/talkSkill/TalkSkill!saveRecord.action',
		data: 'IS_VALID='+signStatus+'&ID='+id,
		dataType: 'json',
		success: function(res) {
			if(res.flag) {
				$('#recordList').datagrid('reload');
				if(signStatus==1) leeds.msg('已启用');
				else leeds.msg('已禁用');
			} else {
				leeds.alert('请求失败，请联系管理员！');
			}
		}
	});
}
//-----------签到end
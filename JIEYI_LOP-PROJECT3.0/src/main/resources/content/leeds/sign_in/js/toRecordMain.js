$(function() {
	leeds.keyer.winEnter(query);
	// 新增记录弹窗
	var recordDivOptions = leeds.dialogInitOp('recordDiv', 'recordForm',
			saveRecord, 550);
	$('#recordDiv').dialog(recordDivOptions);

	// 记录列表
	$('#recordList').datagrid({
		title : '签到处',
		iconCls : 'icon-edit',
		url : _basePath + '/leeds/sign_in/SignIn!toRecordMainPage.action',
		queryParams : {
//			EQUIP_ID : $('#EQUIP_ID').val()
		},
		columns : [[{
					field : 'SIGN_STATUS',
					title : '<img src="'+_basePath+'/leeds/utils/images/signTitle.png" style="width:35px;height:25px"/>',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						var res = '';
						if(value=='0') {//未签到
							res = '<a href="javascript:void(0)" onclick="signIn('
								+ row.ID+ ')"' 
								+' title="点击签到"><img src="'+_basePath+'/leeds/utils/images/signOut.gif">' +
								'<font style="color:red; margin-left:5px">未签到</font></a>';
						} else {
							res = '<a href="javascript:void(0)" onclick="signOut('
								+ row.ID
								+ ')" title="点击签退"><img src="'+_basePath+'/leeds/utils/images/signIn.gif">' +
								'<font style="color:green; margin-left:5px">已签到</font></a>';
						}
						
						return res;
					}
				}, {
					field : 'NAME',
					title : '姓名',
					width : 100,
					align : 'center'
				}, {
					field : 'JOB',
					title : '职务',
					width : 100,
					align : 'center'
				}, {
					field : 'PHONE',
					title : '电话',
					width : 100,
					align : 'center'
				}, {
					field : 'COMPANY',
					title : '公司',
					width : 150,
					align : 'center'
				}, {
					field : 'ID',
					title : '操作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0)" onclick="updateRecord('
								+ value
								+ ')">修改</a>'
								+ ' | <a href="javascript:void(0)" onclick="delRecord('
								+ value + ')">删除</a>';
					}
				}]],
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
	leeds.del(id, _basePath + '/leeds/sign_in/SignIn!delRecord.action',
			'recordList');
}
// loadRecord
function loadRecord(id) {
	var url = _basePath + '/leeds/sign_in/SignIn!loadRecord.action';
	leeds.loadToForm(id, url, 'recordForm');
}
// saveRecord
function saveRecord() {
	$('#recordForm').form('submit', {
		url : _basePath + '/leeds/sign_in/SignIn!saveRecord.action',
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
//----------签到begin
function signIn(id) {
	sign(1, id);
}
function signOut(id) {
	sign(0, id);
}
function sign(signStatus, id) {
	
	$.ajax({
		url: _basePath + '/leeds/sign_in/SignIn!saveRecord.action',
		data: 'SIGN_STATUS='+signStatus+'&ID='+id,
		dataType: 'json',
		success: function(res) {
			if(res.flag) {
				$('#recordList').datagrid('reload');
				if(signStatus==1) leeds.msg('已签到');
				else leeds.msg('已签退');
			} else {
				leeds.alert('签到失败，请联系管理员！');
			}
		}
	});
}
//-----------签到end
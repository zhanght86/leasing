$(function() {
	
});

function saveSupply() {
	if($(':checked').size()<1) {
		leeds.msg('请至少选择一项资料！');
		return false;
	}
	$('#saveSupplyBtn').linkbutton('disable', true);
	$('#recordForm').form('submit',{
		url: _basePath+'/leeds/materialMgt/MaterialMgt!supplyMaterial.action',
		onSubmit: function(param) {
			param.PROJECT_ID = PROJECT_ID;
			param.PHASE = PHASE;
		},
		success: function(data) {
			var data = $.parseJSON(data);
			if(data.flag) leeds.msg('成功补充'+data.data+'项资料');
			else leeds.msg('保存失败');
		}
	});
}
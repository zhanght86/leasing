function btnUpRes() {
	var ids = "";
	var idsf = "";
	var checkNodes = $('#tt').tree('getChecked', [ 'checked' ]);
	for ( var i = 0; i < checkNodes.length; i++) {
		if (ids != '')
			ids += ',';
		ids += checkNodes[i].id;
	}
	checkNodes = $('#tt').tree('getChecked', 'indeterminate');
	for ( var i = 0; i < checkNodes.length; i++) {
		if (idsf != '')
			idsf += ',';
		idsf += checkNodes[i].id;
	}

	jQuery.ajax({
		url : "Auth!doSetAdmin.action",
		data : "ids=" + ids + "&idsf=" + idsf,
		type : "post",
		dataType : "json",
		success : function(json) {
			if (json[0].flag) {
				alert("更新成功");
			} else {
				alert("更新失败");
			}
		}
	});
}

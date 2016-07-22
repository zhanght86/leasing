function disconnect(){
	var eqs = $("input[name='ID']");
	var eds = $("input[name='ID']:checked");
	var total = 0;
	var ed_total = 0;
	var ids = new Array();
	//计算物品总计
	eqs.each(function(){
		var temp = $(this).parent().siblings().last().text();
		total = total + (temp == null ? 0 : parseFloat(temp));
		ids.push($(this).val());
	})
	//拆分比例
	var SPLIT_RATIO = $("input[name='SPLIT_RATIO']").val();
	if (eds.size() <= 0 && SPLIT_RATIO == "") {
		alert("请选择要拆分的设备");
		return false;
	}
	//计算选中设备合计
	eds.each(function(){
		var temp = $(this).parent().siblings().last().text();
		ed_total = ed_total + (temp == null ? 0 : parseFloat(temp));
	})
	if (eds.size() > 0) {
		SPLIT_RATIO = ed_total / total;
	}
	else {
		SPLIT_RATIO = SPLIT_RATIO / 100;
	}
	var jsonData = {
		"SPLIT_RATIO": SPLIT_RATIO,
		"ids": ids.toString(),
		"PAYLIST_CODE": $("input[name='PAYLIST_CODE']").val(),
		"SPLIT_PERIOD": $("select[name='SPLIT_PERIOD'] option:selected").val()
	}
	jQuery.ajax({
		type: "POST",
		dataType: "json",
		async: true,
		url: _basePath + "/pay/PayTask!disconnectCalculate.action",
		data: jsonData,
		success: function(msg){
			var data1 = {
				total: msg.data.data1.length,
				rows: msg.data.data1
			}
			$('#pageTable1').datagrid("loadData", data1);
			var data2 = {
				total: msg.data.data2.length,
				rows: msg.data.data2
			}
			$('#pageTable2').datagrid("loadData", data2);
			
			//给隐藏域复制（两张支付表的融资额）
			$("input[name='TOPRIC_SUBFIRENT1']").val(msg.data.TOPRIC_SUBFIRENT1);
			$("input[name='TOPRIC_SUBFIRENT2']").val(msg.data.TOPRIC_SUBFIRENT2);
			$("input[name='LEASE_TOPRIC1']").val(msg.data.LEASE_TOPRIC1);
			$("input[name='LEASE_TOPRIC2']").val(msg.data.LEASE_TOPRIC2);
			//测算成功后放开保存按钮
			$("#disconnectSave").removeAttr("disabled");
		}
	});
}


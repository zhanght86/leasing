
function clear_(){
	 $('#queryForm').form('clear');
}
function se(){
	var content = {};
	$("#queryForm :input").each(function() {
		if ($(this).attr("name") == undefined)
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	debugger ;
	$('#pageTable').datagrid('load', content);
}
function manipulate(num){
	if (num == 1) {//等于1的时候是变更
		//alert("变更");
		var row = $('#pageTable').datagrid('getSelected');
		if (!row) {//等于空的时候表示为选择任何行
			alert("请选择需要变更的还款计划");
			return null;
		}
		else {
			var status = row.STATUS;
			if (status != 0) {//支付表状态正常的才能变更
				alert("该还款计划不允许变更");
				return null;
			}
			else {
				$("#dialog").show();
				//$("a[name='btn']").attr("href",$("a[name='btn']").attr("href")+row.PAYLIST_CODE);
				$("a[name='btn']").each(function(){
					$(this).attr("href", $(this).attr("href") + row.PAYLIST_CODE + "&ID=" + row.ID);
				})
				$('#dialog').dialog({
					modal: true
				});
				
				
			}
		}
	}
	else 
		if (num == 2) {//等于2的时候是拆分
			var row = $('#pageTable').datagrid('getSelected');
			if (!row) {//等于空的时候表示为选择任何行
				alert("请选择需要变更的还款计划");
				return null;
			}
			else {
				var status = row.STATUS;
				if (status != 0) {//支付表状态正常的才能变更
					alert("该还款计划不允许变更");
					return null;
				}
				else {
					var url =_basePath+"/pay/PayTask!disconnect.action?PAYLIST_CODE="+row.PAYLIST_CODE+"&LEASE_CODE="+row.LEASE_CODE+"&RENTER_NAME="+row.RENTER_NAME;
					top.addTab("还款计划拆分",url);
//					window.location.href = _basePath+"/pay/PayTask!disconnect.action?PAYLIST_CODE="+row.PAYLIST_CODE+
//					"&LEASE_CODE="+row.LEASE_CODE+"&RENTER_NAME="+row.RENTER_NAME;
				}
			}
		}
}

function compure(value,rowData){
	var updateHandle = "<a href='javascript:void(0)' onclick=editBuyContract('"+rowData.ID+"','"+rowData.PROJECT_ID+"','"+rowData.CUST_SIGN_ID+"')>更新</a>";
	var queryHandle = "<a href='javascript:void(0)' onclick=showBuyContract('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>查看</a>　" ;
	//debugger ;
	if(rowData.STATUS==0){
		return queryHandle+updateHandle ;
	}
	return queryHandle;
}

function compureFincial(value,rowData){
	return "<a href='javascript:void(0)' onclick=showExcel('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>导出</a>　";
}
function showExcel(ID,PROJECT){
	
	window.location.href=_basePath+"/pay/PayTask!exportFinancialExcel.action?ID="+ID;
}
function start_jbpm(PAYLIST_CODE){
			jQuery.ajax({
	        type: "POST",
	        dataType: "json",
			async:false,
	        url: _basePath + "/pay/PayTask!startJBPM.action",
	        data: "PAYLIST_CODE="+PAYLIST_CODE,
	        success: function(msg){
				alert(msg.data);;
				window.location.href= _basePath + "/pay/PayTask.action"
	        }
		    })
		}
function showDetail(ID,PROJECT_ID){
	top.addTab("还款计划明细",_basePath+"/pay/PayTask!toMgshowDetail.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}

function showContract(ID,PROJECT_ID){
	top.addTab("买卖合同",_basePath+"/contract/BuyContract!buyContractHandle.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID +"&METHOD_TYPE=" + METHOD_TYPE );
}

function editBuyContract(ID,PROJECT_ID,CUST_SIGN_ID){
	top.addTab("更新买卖合同",_basePath+"/contract/BuyContract!editBuyContractPage.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CUST_SIGN_ID);
}
function showBuyContract(ID,PROJECT_ID){
	top.addTab("查看买卖合同",_basePath+"/contract/BuyContract!showBuyContractPage.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}
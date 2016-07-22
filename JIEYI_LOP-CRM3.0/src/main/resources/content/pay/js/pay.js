
function clear_() {
	$('#queryForm').form('clear');
}
function se() {
	var content = {};
//	$("#queryForm :input").each(function() {
//		if ($(this).attr("name") == undefined)
//			return;
//		content[$(this).attr("name")] = $(this).val();
//	});
	
	
	$("#queryForm :input").each(
			function() {
				if ($(this).is(":checkbox,:radio")) {
					if ($(this).attr("checked")) {
						content[$(this).attr("name")] = $(this).val();
					}
				} else {
					if ($(this).is("select")) {
						content[$(this).attr("name")] = $(this).find(":selected").val();
					} else {
						content[$(this).attr("name")] = $.trim($(this).val());
					}
				}
			});	
	$('#pageTable').datagrid('load', content);
	// alert("sss");
	// var record_name=$("input[name='record_name']").val();
	// var jsonData = "?"+$("#queryForm").serialize();
	// jsonData = _parserUrl(jsonData);
	// $('#pageTable').datagrid('load', jsonData);
	/*
	 * var jsonData =
	 * "?page="+$(".pagination-num").val()+"&rows="+$(".pagination-page-list
	 * option:selected").val()+"&"+$("#queryForm").serialize(); var parserUrl =
	 * _parserUrl(jsonData); jQuery.ajax({ type: "POST", dataType:"json", url:
	 * "PayTask!payListing.action", data: jsonData, success: function(msg){
	 * //alert( "Data Saved: "); $('#pageTable').datagrid("loadData",msg); } });
	 */
	
	
	
	
	
}
function manipulate(num) {
	if (num == 1) {// 等于1的时候是变更
		// alert("变更");
		var row = $('#pageTable').datagrid('getSelected');
		if (!row) {// 等于空的时候表示为选择任何行
			alert("请选择需要变更的还款计划");
			return null;
		} else {
			var status = row.STATUS;
			if (status != 0) {// 支付表状态正常的才能变更
				alert("该还款计划不允许变更");
				return null;
			} else {
				$("#dialog").show();
				// $("a[name='btn']").attr("href",$("a[name='btn']").attr("href")+row.PAYLIST_CODE);
				$("a[name='btn']").each(
						function() {
							$(this).attr(
									"href",
									$(this).attr("href") + row.PAYLIST_CODE
											+ "&ID=" + row.ID);
						})
				$('#dialog').dialog( {
					modal : true
				});

			}
		}
	} else if (num == 2) {// 等于2的时候是拆分
		var row = $('#pageTable').datagrid('getSelected');
		if (!row) {// 等于空的时候表示为选择任何行
			alert("请选择需要变更的还款计划");
			return null;
		} else {
			var status = row.STATUS;
			if (status != 0) {// 支付表状态正常的才能变更
				alert("该还款计划不允许变更");
				return null;
			} else {
				var url = _basePath
						+ "/pay/PayTask!disconnect.action?PAYLIST_CODE="
						+ row.PAYLIST_CODE + "&LEASE_CODE=" + row.LEASE_CODE
						+ "&RENTER_NAME=" + row.RENTER_NAME;
				top.addTab("还款计划拆分", url);
				// window.location.href =
				// _basePath+"/pay/PayTask!disconnect.action?PAYLIST_CODE="+row.PAYLIST_CODE+
				// "&LEASE_CODE="+row.LEASE_CODE+"&RENTER_NAME="+row.RENTER_NAME;
			}
		}
	}
}

function toViewCust(row) {
	var value=row.CLIENT_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}
/*
function toShowCustInfo(value, rowData){
	 return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+value+"</a>";
}


function payplan(value, rowData) {
	 return "<a href='javascript:void(0)' onclick=showDetail('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>"+ value +"</a>　";
}

 
 
function compure(value, rowData) {
	var approval = "";
	if (rowData.VERSION_CODE <= -2 && rowData.STATUS_NAME == '正常'
			&& rowData.P_STATUS != 5 && rowData.P_STATUS != 6) {
		approval = "<a href='javascript:void(0)' onclick=start_jbpm('"
				+ rowData.PAYLIST_CODE + "')>变更流程</a>";
	}
	//approval =  "<a href='javascript:void(0)' onclick=showContract('"+rowData.ID+"','"+rowData.PROJECT_ID+"','"+"add"+"')>买卖合同</a>&nbsp;";

//	if(rowData.PLATFORM_NAME!="大项目租赁")
//	{//alert(rowData.PLATFORM_NAME);
//		return "<a href='javascript:void(0)' onclick=showDetail('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>查看</a>　"
//	}	 
//	if(rowData.ZQX_PARENT_ID ==null ){//&& START_DATE ==null
	if(rowData.PLATFORM_NAME=="大项目租赁" ){
		approval =approval + "<a href='javascript:void(0)' onclick=showzqx('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>租前息</a>&nbsp;";
	}else{
		approval =approval + "<font color='#BEBEBE'>租前息</font>";
	}
	return approval;
	//return "<a href='javascript:void(0)' onclick=showDetail('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>查看</a>　"+approval;

//	alert(value);
//	alert(rowData.ID);
//	return "操作"
	//top.addTab("还款计划明细",_basePath+"pay/PayTask!payDetailShow.action?ID="+PAY_ID+"&PROJECT_ID="+PROJECT_ID);
}
*/
function compureFincial(value, rowData) {
	return "<a href='javascript:void(0)' onclick=showExcel('" + rowData.ID
			+ "','" + rowData.PROJECT_ID + "')>导出</a>　";
}
function showExcel(ID, PROJECT) {

	window.location.href = _basePath
			+ "/pay/PayTask!exportFinancialExcel.action?ID=" + ID;
}
function start_jbpm(PAYLIST_CODE) {
	jQuery.ajax( {
		type : "POST",
		dataType : "json",
		async : false,
		url : _basePath + "/pay/PayTask!startJBPM.action",
		data : "PAYLIST_CODE=" + PAYLIST_CODE,
		success : function(msg) {
			alert(msg.data);
			;
			window.location.href = _basePath + "/pay/PayTask.action"
		}
	})
}

function showDetail(ID,PROJECT_ID,PAYLIST_CODE){
	top.addTab(PAYLIST_CODE+"还款计划明细",_basePath+"/pay/PayTask!toMgshowDetail.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}


function showDetailChange(ID, PROJECT_ID) {
	top.addTab("还款计划明细", _basePath + "/pay/PayTask!toMgshowDetailChange.action?ID="
			+ ID + "&PROJECT_ID=" + PROJECT_ID);
}


function showzqx(ID,PROJECT_ID){
	top.addTab("租前息",_basePath+"/pay/PayTask!rentBeforeInterest.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}


function changePay(value,rowData){
	return "<a href='javascript:void(0)' onclick=showDetail('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>查看</a>　";
}

function changePayChange(value,rowData){
	return "<a href='javascript:void(0)' onclick=showDetailChange('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>查看</a>　";
}



function showContract(ID, PROJECT_ID,METHOD_TYPE) {
	top.addTab("买卖合同", _basePath
			+ "/contract/BuyContract!buyContractHandle.action?ID="
			+ ID + "&PROJECT_ID=" + PROJECT_ID + "&METHOD_TYPE=" + METHOD_TYPE);
}
function rentbefore(value,rowData){
	return "<a href='javascript:void(0)' onclick=showZQXDetail('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>查看</a>　";
}
function showZQXDetail(ID, PROJECT_ID) {
	top.addTab("还款计划明细", _basePath + "/pay/PayTask!toMgshowZQXDetail.action?ID="
			+ ID + "&PROJECT_ID=" + PROJECT_ID);
}


function YSTJ(ID,PROJECT_ID){
	top.addTab("应收统计",_basePath+"/fundReport/FundReportTable.action?REPORT_TYPE=4");
}

function SSTJ(ID,PROJECT_ID){
	top.addTab("实收统计",_basePath+"/fundReport/FundReportTable!toChartFundSS.action?REPORT_TYPE=4");
}

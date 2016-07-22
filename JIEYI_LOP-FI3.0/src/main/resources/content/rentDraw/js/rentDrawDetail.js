$(document).ready(function(){
		$("#pageTable").datagrid({
			url:_basePath+"/rentDraw/RentDraw!getRentDrawDetailPage.action?BATCH=" + $("#BATCH").val() + "&OVERDUE=" + $("#OVERDUE").val(),
			pageSize:20,
			fit : true,
			pagination : true,
			rownumbers : true,
			//singleSelect : true,
			toolbar : "#pageForm",
            columns:[[
                {field:'ID',checkbox:true,width:50},
                {field:'detail',width:100,title:'操作',align:'center',formatter:detail},
				{field:'BATCH',width:100,title:'批次',align:'center',formatter:batch},
				{field:'LEASE_CODE',width:150,title:'合同号',align:'center'},
				{field:'CUST_NAME',width:100,title:'客户姓名',align:'center'},
				{field:'PAY_ID',width:100,title:'支付表号',align:'center'},
				{field:'PERIOD_NUM',width:100,title:'期次',align:'center'},
				{field:'LEASE_TERM',width:100,title:'租赁总期数',align:'center'},
				{field:'RENT_MONEY',width:100,title:'租金',align:'center'},
				{field:'PRINCIPAL',width:100,title:'本金',align:'center'},
				{field:'INTEREST',width:100,title:'利息',align:'center'},
				{field:'RENT_RECE_MONEY',width:100,title:'已收租金',align:'center'},
				{field:'RENT_SURPLUS_MONEY',width:100,title:'剩余租金',align:'center'},
				{field:'DUN_FLAG_NAME',width:100,title:'是否逾期',align:'center'},
				{field:'OVERDUE_DATE',width:100,title:'逾期天数',align:'center'},
				{field:'OVERDUE_MONEY',width:100,title:'逾期金额',align:'center',formatter:function(value,rowData){
					if(value == null || value == ''){
						return '0';
					}else{
						return value;
					}
				}},
				{field:'DEDUCT_TYPE',width:100,title:'扣款方式',align:'center'},
				{field:'DEDUCT_STATUS',width:100,title:'当前扣款状态',align:'center'},
				{field:'DEDUCT_RESULT',width:100,title:'扣款结果',align:'center'}
            ]]
        });
		hideBatch();
});

/**
 * 动态查询明细信息
 */
function search(){
	$('#pageTable').datagrid('load', {
		LEASE_CODE : $('#LEASE_CODE').val(),	// 合同号
		CUST_NAME : $('#CUST_NAME').val(),		// 用户名称
		PERIOD_NUM : $('#PERIOD_NUM').val(),	// 期次
		DEDUCT_TYPE : $('#DEDUCT_TYPE').val()	// 扣款方式
	}); 
}

/**
 * 隐藏批次号
 */
function hideBatch(){
	var batch = $("#BATCH").val();
	if(null == batch || "" == batch){
		$("#pageTable").datagrid('hideColumn', 'BATCH'); 
	}
}

/**
 * 为批次号赋值
 * @param value
 * @param rowData
 * @returns
 */
function batch(value,rowData){
	return $("#BATCH").val();
}

/**
 * 查看操作日志-为超链接赋值
 * @param value
 * @param rowData
 * @returns {String}
 */
function detail(value,rowData){
	return "<a href=\"javascript:void(0)\" onclick=\"rentDetail('"+rowData.LEASE_CODE+"', "+rowData.PERIOD_NUM+")\">查看划扣日志</a>";
}

/**
 * 查看操作日志方法-链接到日志页面
 * @param LEASE_CODE	合同号
 * @param PERIOD_NUM	期数
 */
function rentDetail(LEASE_CODE,PERIOD_NUM){
	top.addTab("划扣日志明细",_basePath+"/rentDraw/RentDraw!toMgshowLog.action?LEASE_CODE="+LEASE_CODE+"&PERIOD_NUM="+PERIOD_NUM);
}
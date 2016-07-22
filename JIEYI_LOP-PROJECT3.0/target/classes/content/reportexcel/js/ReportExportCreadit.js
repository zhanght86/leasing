$(document).ready(function(){
	$("#ReportCreaditPageTable").datagrid({
	    pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		toolbar:'#pageForm',
	    url:'ReportExcelCreadit!queryData.action',
	    columns:[[{field:'FLAG',title:'业务类型'},
	              {field:'JJBH',title:'进件编号'},
	              {field:'JJSJ',title:'进件时间'},
	              {field:'MD',title:'门店'},
	              {field:'NAME',title:'客户姓名'},
	              {field:'ID_CARD_NO',title:'客户身份证'},
	              {field:'SHORTNAME',title:'客户渠道'},
	              {field:'SCHEME_NAME',title:'方案名称'},
	              {field:'FIRSTPAYALL',title:'首付款'},
	              {field:'FINANCE_TOPRIC',title:'融资额'},
	              {field:'LEASE_TERM',title:'期数'},
	              {field:'ITEM_MONEY',title:'月还'},
	              {field:'STATUS_NEW',title:'当前状态'},
	              {field:'CSJL',title:'初审结论'},
	              {field:'ZSJL',title:'终审结论'},
	              {field:'CSRQ',title:'初审日期'},
	              {field:'ZSRQ',title:'终审日期'},
	              {field:'XSGKRY',title:'信审挂靠人员'},
	              {field:'CSJJYY',title:'初审拒绝原因'},
	              {field:'ZSJJYY',title:'终审拒绝原因'},
	              {field:'ZZRW',title:'截止任务'},
	              {field:'RWDDSJ',title:'任务到达时间'}
	              ]]
	});
});


function exportData() {
	var EJJSJ = $("input[name='EJJSJ']").val();
	var SJJSJ = $("input[name='SJJSJ']").val();
	var NAME = $("input[name='NAME']").val();
	var PLATFORM_TYPE = $("select[name='PLATFORM_TYPE']").val();
	var strUrl = "";
	strUrl = _basePath
			+ "/reportexcel/ReportExcelCreadit!excelUpload.action?EJJSJ="
			+ EJJSJ + "&SJJSJ=" + SJJSJ + "&NAME=" + NAME+"&PLATFORM_TYPE="+PLATFORM_TYPE;
	$.messager.confirm("提示", "您确认要导出数据吗？", function(a) {
		if (a) {
			$("#divFrom").html('');
			$("#divFrom").append(
					"<form id='formSubmit' method='post' action='" + strUrl
							+ "'></form>");
			$("#formSubmit").submit();
		}
	});
}
/**
 * 客户逾期原因记录js
 */
function setOperation(val, row) {
	if(val == undefined){
		//如果没有电话号码
		return '无';
	}else{
		return "<a href='#' style='color:blue;' onclick=showReasonRecord('" + val + "','" + row.KHMC + "','" + row.PROJ_ID + "','" + row.RENT_LIST + "','" + row.PROJ_FUND_ID + "')>" + val + "</a>";
	}
}
/**
 * 显示客户逾期原因记录页面
 */
function showReasonRecord(PHONE,KHMC,PROJ_ID,RENT_LIST,PROJ_FUND_ID){
	window.open(_basePath + "/call/OverdueClientStatistics!toShowReasonRecord.action?PHONE=" + PHONE + "&KHMC=" + KHMC + "&PROJ_ID=" + PROJ_ID + "&RENT_LIST=" + RENT_LIST + "&PROJ_FUND_ID=" + PROJ_FUND_ID);
	//parent.addTab("客户逾期原因记录-" + KHMC, _basePath + "/call/OverdueClientStatistics!toShowReasonRecord.action?PHONE=" + PHONE + "&KHMC=" + KHMC + "&PROJ_ID=" + PROJ_ID + "&RENT_LIST=" + RENT_LIST + "&PROJ_FUND_ID=" + PROJ_FUND_ID);
}
/**
 * 清空按钮，清空日期及可填写字段
 */
function emptyData(){
	//清空日期
	$("#YS_DATE_BEGIN").datebox('clear');
	$("#YS_DATE_END").datebox('clear');
	$("#QZ_CONFIRM_DATE_BEGIN").datebox('clear');
	$("#QZ_CONFIRM_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var DLD = $("#DLD").val();
	var PROJ_ID = $("#PROJ_ID").val();
	var PROD_TYPE = $("#PROD_TYPE").val();
	var ZZS = $("#ZZS").val();
	var KHMC = $("#KHMC").val();
	var PRO_NO = $("#PRO_NO").val();
	var FEE_TYPE = $("#FEE_TYPE").val();
	var YQ_QS = $("#YQ_QS").val();
	var YS_DATE_BEGIN = $("#YS_DATE_BEGIN").datebox("getValue");
	var YS_DATE_END = $("#YS_DATE_END").datebox("getValue");
	var QZ_CONFIRM_DATE_BEGIN = $("#QZ_CONFIRM_DATE_BEGIN").datebox("getValue");
	var QZ_CONFIRM_DATE_END = $("#QZ_CONFIRM_DATE_END").datebox("getValue");
	if(YQ_QS==4){
		var YQ_QS2 = YQ_QS;
		$('#dg').datagrid('load', {"DLD":DLD,"PROJ_ID":PROJ_ID,"PROD_TYPE":PROD_TYPE,"ZZS":ZZS,"KHMC":KHMC,"PRO_NO":PRO_NO,"FEE_TYPE":FEE_TYPE,"YS_DATE_BEGIN":YS_DATE_BEGIN,"YS_DATE_END":YS_DATE_END,"QZ_CONFIRM_DATE_BEGIN":QZ_CONFIRM_DATE_BEGIN,"QZ_CONFIRM_DATE_END":QZ_CONFIRM_DATE_END,"YQ_QS2":YQ_QS2});
	}else{
		$('#dg').datagrid('load', {"DLD":DLD,"PROJ_ID":PROJ_ID,"PROD_TYPE":PROD_TYPE,"ZZS":ZZS,"KHMC":KHMC,"PRO_NO":PRO_NO,"FEE_TYPE":FEE_TYPE,"YS_DATE_BEGIN":YS_DATE_BEGIN,"YS_DATE_END":YS_DATE_END,"QZ_CONFIRM_DATE_BEGIN":QZ_CONFIRM_DATE_BEGIN,"QZ_CONFIRM_DATE_END":QZ_CONFIRM_DATE_END,"YQ_QS":YQ_QS});
	}
}
/**
 * 添加一条客户逾期原因记录
 */
function doAddRecord(){
	var PROJ_FUND_ID = $("#PROJ_FUND_ID").attr("value");//项目资金计划编号
	var PROJ_ID = $("#PROJ_ID").attr("value");//项目编号
	var YQ_REASON = $("#YQ_REASON").attr("value");//逾期原因
	var CS_PLAN = $("#CS_PLAN").attr("value");//催收计划
	var CUST_COMMITMENT = $("#CUST_COMMITMENT").attr("value");//客户承诺
	var KK_RESULT = $("#KK_RESULT").attr("value");//扣款结果
	var FAIL_MEASURE = $("#FAIL_MEASURE").attr("value");//催收无果措施
	var CS_LOG = $("#CS_LOG").attr("value");//催收日志
	var REMARK = $("#REMARK").attr("value");//备注
	var RENT_LIST = $("#RENT_LIST").attr("value");//期次
	//以下用于刷新
	var PHONE = $("#PHONE").attr("value");//电话
	var KHMC = $("#KHMC").attr("value");//客户名称
	jQuery.ajax({
		url : _basePath + "/call/OverdueClientStatistics!doAddRecord.action",
		data : { "PROJ_FUND_ID": PROJ_FUND_ID,"PROJ_ID": PROJ_ID,"YQ_REASON" : YQ_REASON,"CS_PLAN" : CS_PLAN,"CUST_COMMITMENT" : CUST_COMMITMENT,"KK_RESULT" : KK_RESULT,"FAIL_MEASURE" : FAIL_MEASURE,"CS_LOG" : CS_LOG,"REMARK" : REMARK,"RENT_LIST" : RENT_LIST},
		dataType:'json',
		success:function(data){
			//刷新页面
			//showReasonRecord(PHONE,KHMC,PROJ_ID,RENT_LIST,PROJ_FUND_ID);
			//关闭当前页面
			window.close();
		}
	});
}

//导出 TODO
function exportExcel(flag){
	//params
	var searchParams = getFromData('#pageForm');
	//url
	var url = _basePath + "/call/OverdueClientStatistics!exportExcel.action";
	if(flag == 'all'){
		url += "?exportAll=true";
	}
	//submit
	$('#fm_search').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			if($('#searchParams').length<=0){
				$('#fm_search').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
			}
			$('#searchParams').val(searchParams);
        }
    });
	//remove
	$('#searchParams').remove();
}

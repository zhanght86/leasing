/**
 * 管理信息系统模板导出 js
 * 作者:韩晓龙
 */
/**
 * 清空按钮
 */
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
	$("#QZ_CONFIRM_DATE_BEGIN").datebox('clear');
	$("#QZ_CONFIRM_DATE_END").datebox('clear');
	$("#SIGN_DATE_BEGIN").datebox('clear');
	$("#SIGN_DATE_END").datebox('clear');
}
/**
 * 查询
 */
function dosearch(){
	var PROJ_ID = $("#PROJ_ID").val();
	var KHMC = $("#KHMC").val();
	var QZ_CONFIRM_DATE_BEGIN = $("#QZ_CONFIRM_DATE_BEGIN").datebox("getValue");
	var QZ_CONFIRM_DATE_END = $("#QZ_CONFIRM_DATE_END").datebox("getValue");
	var SIGN_DATE_BEGIN = $("#SIGN_DATE_BEGIN").datebox("getValue");
	var SIGN_DATE_END = $("#SIGN_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"PROJ_ID":PROJ_ID,"KHMC":KHMC,"QZ_CONFIRM_DATE_BEGIN":QZ_CONFIRM_DATE_BEGIN,"QZ_CONFIRM_DATE_END":QZ_CONFIRM_DATE_END,"SIGN_DATE_BEGIN":SIGN_DATE_BEGIN,"SIGN_DATE_END":SIGN_DATE_END});
}

//导出 TODO
function exportExcel(flag){
	//params
	var searchParams = getFromData('#pageForm');
	//url
	var url = _basePath + "/infoexport/InfoExport!exportExcel.action";
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

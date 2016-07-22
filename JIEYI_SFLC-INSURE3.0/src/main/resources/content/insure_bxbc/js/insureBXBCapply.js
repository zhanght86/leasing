/**
 * 保险补差管理js
 * @author 韩晓龙
 */
var urltemp = "";

/**
 * 清空按钮
 */
function emptyData(){
	$("#CHECK_DATE_BEGIN").datebox('clear');
	$("#CHECK_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var PROJ_ID = $("#PROJ_ID").val();
	var DLD = $("#DLD").val();
	var CHECK_DATE_BEGIN = $("#CHECK_DATE_BEGIN").datebox("getValue");
	var CHECK_DATE_END = $("#CHECK_DATE_END").datebox("getValue");
	var PROD_ID = $("#PROD_ID").val();
	var ZZS = $("#ZZS").val();
	var KHMC = $("#KHMC").val();
	$('#dg').datagrid('load', {"PROJ_ID":PROJ_ID,"DLD":DLD,"CHECK_DATE_BEGIN":CHECK_DATE_BEGIN,"CHECK_DATE_END":CHECK_DATE_END,"PROD_ID":PROD_ID,"ZZS":ZZS,"KHMC":KHMC});
}
/**
 * 确认按钮
 */
function addItem(){
	//得到选中行的数据
	var datagridList = $('#dg').datagrid('getChecked');
	var ID_DATA = [];//ID 序列
	var BXBCJE_DATA = [];//保险补差金额 序列
	var SK_DATE_DATA = [];//保险补差收款日期 序列
	var ZF_DATE_DATA = [];//保险补差支付日期 序列
	if(datagridList.length<=0){
		$.messager.alert('提示','还没有勾选条目!','info',null);
		return false;
	}
	
	for(var i = 0; i < datagridList.length; i++){
		if(datagridList[i].BXBCJE){
			BXBCJE_DATA.push("'"+datagridList[i].BXBCJE+"'");
		}else{
			$.messager.alert('提示','第' + (i+1) +'条记录请填写保险补差金额!','info',null);
			return false;
		}
		if(datagridList[i].SK_DATE){
			SK_DATE_DATA.push("'"+datagridList[i].SK_DATE+"'");
		}else{
			$.messager.alert('提示','第' + (i+1) +'条记录请填写保险补差收款日期!','info',null);
			return false;
		}
		if(datagridList[i].ZF_DATE){
			ZF_DATE_DATA.push("'"+datagridList[i].ZF_DATE+"'");
		}else{
			$.messager.alert('提示','第' + (i+1) +'条记录请填写保险补差支付日期!','info',null);
			return false;
		}
		ID_DATA.push("'"+datagridList[i].ID+"'");
	}
	
	urltemp = _basePath +'/insurebxbc/InsureBXBCManagement!doAddInsurChargeExhib.action?ID_DATA=' + ID_DATA + '&BXBCJE_DATA=' + BXBCJE_DATA + '&SK_DATE_DATA=' + SK_DATE_DATA + '&ZF_DATE_DATA=' + ZF_DATE_DATA;
	
	//利用ajax发送请求
	$.ajax({
		type: "POST",
	    dataType:"json",
	    url: urltemp,
	    //data: jsonData,
	    success: function(flag){
			alert('保险补差申请成功!');
			$('#dg').datagrid('reload');
		}
	});
	//////////////////////////////ajax结束//////////////////////////////////////////
	
}

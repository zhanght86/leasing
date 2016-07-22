/**
 * 补差管理收取申请  -   新增 - (保险补差费用)收取收款  js
 * @author 韩晓龙
 */
var url = "";

/**
 * 清空按钮
 */
function emptyData(){
	$("#CHECK_DATE_BEGIN").datebox('clear');
	$("#CHECK_DATE_END").datebox('clear');
	$("#PLAN_DATE_BEGIN").datebox('clear');
	$("#PLAN_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
//查询
function dosearch(){
	var DLD = $("#DLD").val();
	var PROJ_ID = $("#PROJ_ID").val();
	var ZLWMC = $("#ZLWMC").val();
	var ZZS = $("#ZZS").val();
	var KHMC = $("#KHMC").val();
	var PAYEE = $("#PAYEE").val();
	var CHECK_DATE_BEGIN = $("#CHECK_DATE_BEGIN").datebox("getValue");
	var CHECK_DATE_END = $("#CHECK_DATE_END").datebox("getValue");
	var PLAN_DATE_BEGIN = $("#PLAN_DATE_BEGIN").datebox("getValue");
	var PLAN_DATE_END = $("#PLAN_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"DLD":DLD,"PROJ_ID":PROJ_ID,"ZLWMC":ZLWMC,"ZZS":ZZS,"KHMC":KHMC,"PAYEE":PAYEE,"CHECK_DATE_BEGIN":CHECK_DATE_BEGIN,"CHECK_DATE_END":CHECK_DATE_END,"PLAN_DATE_BEGIN":PLAN_DATE_BEGIN,"PLAN_DATE_END":PLAN_DATE_END});
	//清空值
	$("#FK_AMT").val("");//实收
	$("#PROJ_AMT").val("");//项目数量
	$("#FACT_DATE").datebox('clear');
}

/**
 * 计算应收金额， 实收金额， 项目数量
 */
function onChangeSelect(){
	var datagridList=$("#dg").datagrid('getSelections');
	var PLAN_MONEY=0;
	var FI_PAY_MONEYAll=0;
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++)
	{
		datagridList[i].ID.checkbox = true;
			
		var REALITY_MONEY = datagridList[i].PLAN_MONEY;
		PLAN_MONEY = accAdd(PLAN_MONEY,REALITY_MONEY);
			
		NUM++;
	}
	$("#FK_AMT").val(PLAN_MONEY);//实收
	$("#PROJ_AMT").val(NUM);//项目数量
}
/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}

/**
 * 保存按钮
 */
function sureItem(){
	//得到选中行的数据
	var datagridList = $('#dg').datagrid('getChecked');
	var ID_DATA = [];//ID 序列
	if(datagridList.length<=0){
		$.messager.alert('提示','还没有勾选条目!','info',null);
		return false;
	}
	for(var i = 0; i < datagridList.length; i++){
		ID_DATA.push("'"+datagridList[i].ID+"'");
	}
	
	var FK_AMT = $("#FK_AMT").val();//实收金额
	var PROJ_AMT = $("#PROJ_AMT").val();//项目数量
	var FACT_DATE = $("#FACT_DATE").datebox("getValue");//实付日期
	
	if(FACT_DATE == "" || FACT_DATE == null){
		$.messager.alert('提示','请填写实付日期!','info',null);
		return false;
	}
	
	urltemp = _basePath +'/insurebxbc/InsureBXBCReceiveDetail!doUpdateInsurChargeExhib.action?ID_DATA=' + ID_DATA + '&FK_AMT=' + FK_AMT + '&PROJ_AMT=' + PROJ_AMT + '&FACT_DATE=' + FACT_DATE;
	
	//利用ajax发送请求
	$.ajax({
		type: "POST",
	    dataType:"json",
	    url: urltemp,
	    //data: jsonData,
	    success: function(flag){
			alert('申请成功!请及时提交!');
			$('#dg').datagrid('reload');
			//清空值
			$("#FK_AMT").val("");//实收
			$("#PROJ_AMT").val("");//项目数量
			$("#FACT_DATE").datebox('clear');
		}
	});
	//////////////////////////////ajax结束//////////////////////////////////////////
	
}


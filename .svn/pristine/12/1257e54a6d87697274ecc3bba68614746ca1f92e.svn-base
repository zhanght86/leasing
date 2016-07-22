/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	var PLAN_DATE1 = $("input[name='PLAN_DATE1']").val();
	var PLAN_DATE2 = $("input[name='PLAN_DATE2']").val();
	var COMPANY_NAME = $("select[name='COMPANY_NAME']").attr("selected",true).val();
	var PRODUCT_NAME = $("select[name='PRODUCT_NAME']").attr("selected",true).val();
	$('#pageTable').datagrid('load', {
		"CLIENT_NAME" : CLIENT_NAME,
		"PRO_CODE" : PRO_CODE,
		"PLAN_DATE1" : PLAN_DATE1,
		"PLAN_DATE2" : PLAN_DATE2,
		"COMPANY_NAME" : COMPANY_NAME,
		"PRODUCT_NAME" : PRODUCT_NAME
	});
}

/**
 * 清空查询数据
 * @return
 */
function clearQuery(){
	$("#PLAN_DATE1").datebox('clear');
	$("#PLAN_DATE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 申请提交
 * @return
 */
function toSubmtit(){
	var detailData = $("#pageTable").datagrid('getSelections');
	if(detailData.length<=0){
		$.messager.alert("提示","请选择申请核销数据");
		return true;
	}
	var FI_PAY_MONEY = $("#FI_PROJECT_NUM").val();
	var FI_REALITY_MONEY = $("#FI_PROJECT_NUM").val();
	var FI_PROJECT_NUM = $("#FI_PROJECT_NUM").val();
	
	
	
	if(detailData.length>0){
		for(var i = 0; i<detailData.length; i++){
			if(detailData[i].FIRST_PAYMENT_TYPE==undefined || detailData[i].BCYS_MONEY==0){	
				$.messager.alert("提示","请确定选择申请的项目首期款付款方式不为空或本期应收金额不为空的项目！");
				return true;
			}
		}
	}
	var FI_PAY_TYPE = $("#FI_PAY_TYPE").val();
	var FI_PAY_DATE = $("#FI_PAY_DATE").datebox("getValue");
	if(FI_PAY_TYPE==""){
		$.messager.alert("提示","请选择付款方式");
		return true;
	}
	if(FI_PAY_DATE==""){
		$.messager.alert("提示","请选择付款日期");
		return true;
	}
	if(checked()){
		$("#toSubmtit").linkbutton("disable");
		var data = {};
		data["getBaseData"] = getBaseData();
		data["getDetailData"] = getDetailData();
		$.ajax({
			url:_basePath+"/fundNotEBank/FundNotEBank!doInsertAppData.action",
		    type:'post',
		    data:'data='+JSON.stringify(data),
		    dataType:'json',
		    success:function(json){
			    if(json.flag == true){
			    	$.messager.alert("首期款付款申请","首期款付款申请成功！！");
			    	//页面刷新
			    	window.location.href = _basePath+"/fundNotEBank/FundNotEBank!toMgDeduct.action";
			    	//清空基础数据
			    	$("#FI_PAY_DATE").datebox('clear');
			    	$("#getBaseData").each(function(){
			    		$(this).val("");
			    	});
			    }else {
			    	$.messager.alert("首期款付款申请","首期款付款申请失败！！");
			    	//页面刷新
			    	$('#pageTable').datagrid('reload');
			    	window.location.href = _basePath+"/fundNotEBank/FundNotEBank!toMgDeduct.action";
			    	//清空基础数据
			    	$("#FI_PAY_DATE").datebox('clear');
			    	$("#getBaseData").each(function(){
			    		$(this).val("");
			    	});
			    }
		    }
		});
	}else{
		$.messager.alert("数据校验","请选择必须按项目");
	}
}

/**
 * 获取申请单基础数据
 * @auth yx
 * @date 2013-09-28
 * @return
 */
function getBaseData(){
	var getBaseData = {};
	var FI_PAY_DATE = $("#FI_PAY_DATE").datebox('getValue');
	var FI_PAY_TYPE = $("#FI_PAY_TYPE").attr("selected",true).val();
	var FI_FLAG = $("#FI_FLAG").attr("selected",true).val();
	getBaseData["FI_PAY_DATE"]  = FI_PAY_DATE;
	getBaseData["FI_PAY_TYPE"]  = FI_PAY_TYPE;
	getBaseData["FI_FLAG"]  = FI_FLAG;
	$("#baseData tr").each(function(){
		//var temp = {};
		getBaseData["COMPANY_NAME"] = $(this).find($("input[name=COMPANY_NAME]")).val();		
		getBaseData["FI_PAY_MONEY"]  = $(this).find($("input[name=FI_PAY_MONEY]")).val();
		getBaseData["FI_REALITY_MONEY"]  = $(this).find($("input[name=FI_REALITY_MONEY]")).val();
		getBaseData["FI_PROJECT_NUM"]  = $(this).find($("input[name=FI_PROJECT_NUM]")).val();
		//getBaseData.push(temp);
	});

	return getBaseData;
}

/**
 * 获取明细信息
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.PROJECT_HEAD_ID = detailData[i].HEAD_ID;
		temp.PRO_CODE = detailData[i].PRO_CODE;
		temp.CLIENT_ID = detailData[i].CLIENT_ID;
		temp.CLIENT_NAME = detailData[i].CLIENT_NAME;
		//temp.D_PAY_MONEY = detailData[i].D_PAY_MONEY;
		temp.FIRST_VALUE_STR = detailData[i].FIRST_PAYMENT_TYPE;
		temp.PAYMENT_STATUS = detailData[i].PAYMENT_STATUS;
		temp.YS_MONEY = detailData[i].YS_MONEY;
		temp.DB_MONEY = detailData[i].DB_MONEY;
		temp.REALITY_MONEY = detailData[i].REALITY_MONEY;
		temp.BCYS_MONEY = detailData[i].BCYS_MONEY;
		temp.PAYLIST_CODE = detailData[i].PAYLIST_CODE;
		getDetailData.push(temp);
	}
	return getDetailData;//JSON.stringify(getDetailData);//encodeURI(JSON.stringify(getDetailData));
} 



/**
 * 计算应收金额， 实收金额， 项目数量
 * @auth yx
 * @date 2013-09-28
 * @return
 */
function onChangeSelect()
{
	var datagridList=$("#pageTable").datagrid('getSelections');
	var REALITY_MONEYAll=0;
	var FI_PAY_MONEYAll=0;
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++)
	{
		datagridList[i].HEAD_ID.checkbox = true;
			
		var REALITY_MONEY=datagridList[i].REALITY_MONEY;
		REALITY_MONEYAll=accAdd(REALITY_MONEYAll,REALITY_MONEY);
			
		var FI_PAY_MONEY=datagridList[i].BCYS_MONEY;
		FI_PAY_MONEYAll=accAdd(FI_PAY_MONEYAll,FI_PAY_MONEY);
		NUM++;
	}
	$("#FI_REALITY_MONEY").val(REALITY_MONEYAll);//实收
	$("#FI_PROJECT_NUM").val(NUM);//项目数量
	$("#FI_PAY_MONEY").val(FI_PAY_MONEYAll);//应收
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
 * 页面提交数据校验
 * @author 杨雪
 * @date 2013-09-29
 * @return
 */
function checked(){
	var flag = true;
	$(".warm").each(
					function() {
						if ($("#FI_PAY_TYPE").attr("selected", true).val() == ''
								|| $("#FI_PAY_TYPE").attr("selected", true)
										.val() == null
								|| $("#FI_PAY_DATE").datebox('getValue') == ''
								|| $("#FI_PAY_DATE").datebox('getValue') == null) {
							$(this).addClass("red");
							flag = false;
						} else {
							$(this).removeClass("red");
						}
					});

	return flag;
}
/********************************************************以下为设置单元格可修改***杨雪********************************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#pageTable').datagrid('validateRow', editIndex)) {
		$('#pageTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

/**
 * 单元格转为可编辑样式
 * @auth yx
 * @date 2013-09-28
 * @param index
 * @param field
 * @return
 */
function onClickCell(index, field) {
	$('#pageTable').datagrid('endEdit', editIndex);
	if (endEditing()) {
			$('#pageTable').datagrid('editCell', {index : index,field : field});
			editIndex = index;
		}
		onChangeSelect();
}

/**
 * 单元格修改好变更数据
 * @auth yx
 * @date 2013-09-28
 * @param rowIndex
 * @param rowData
 * @param changes
 * @return
 */
function toChangeCell(rowIndex,rowData,changes){
	var BCYS_MONEY=rowData.BCYS_MONEY;
	var REALITY_MONEY=rowData.REALITY_MONEY;
	if(parseFloat(changes.REALITY_MONEY)>parseFloat(rowData.BCYS_MONEY)){
		alert("本次实收金额应小于等于本次应收金额！");
		$('#pageTable').datagrid('updateRow',{
			index: rowIndex,
			row: {
			REALITY_MONEY: rowData.BCYS_MONEY
			}
		});
		return;
	}
	if(parseFloat(REALITY_MONEY)-parseFloat(BCYS_MONEY)>0)
	{
		$('#pageTable').datagrid('updateRow',{
			index: rowIndex,
			row: {
				REALITY_MONEY: BCYS_MONEY
			}
		});
	}
}
/********************************************************以上为设置单元格可修改***杨雪********************************************************************************/
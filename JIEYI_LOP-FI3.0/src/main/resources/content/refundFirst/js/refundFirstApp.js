/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	var SUP_NAME = $("input[name='SUP_NAME']").val();
	var PRODUCT_NAME = $("input[name='PRODUCT_NAME']").val();
	
	$('#pageTable').datagrid('load', {
		"CLIENT_NAME" : CLIENT_NAME,
		"PRO_CODE" : PRO_CODE,
		"SUP_NAME" : SUP_NAME,
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

function toSubmtit(){
	
}

/**
 * 获取申请单基础数据
 * @auth yx
 * @date 2013-09-28
 * @return
 */
function getBaseData(){
	var getBaseData = new Array();
	$("#baseData tr").each(function(){
		var temp = {};
		temp.COMPANY_NAME = this.find($("input[name=COMPANY_NAME]")).val();
		temp.payment_type = this.find($("select[name=payment_type]")).attr("selected",true).val();
		temp.RE_DATE  = this.find($("input[name=RE_DATE]")).val();
		temp.FI_PAY_MONEY  = this.find($("input[name=FI_PAY_MONEY]")).val();
		temp.FI_REALITY_MONEY  = this.find($("input[name=FI_REALITY_MONEY]")).val();
		temp.FI_PROJECT_NUM  = this.find($("input[name=FI_PROJECT_NUM]")).val();
		getBaseData.push(temp);
	});

	return encodeURI(JSON.stringify(getBaseData));
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
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++){
		datagridList[i].HEAD_ID.checkbox = true;
		var REALITY_MONEY=datagridList[i].REALITY_MONEY;
		REALITY_MONEYAll=accAdd(REALITY_MONEYAll,REALITY_MONEY);
		NUM++;
	}
	$("#FI_REALITY_MONEY").val(REALITY_MONEYAll);
	$("#FI_PROJECT_NUM").val(NUM);
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
	$('#bank_C_PageTable').datagrid('endEdit', editIndex);
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
	var BCYS_MONEY=changes.BCYS_MONEY;
	var REALITY_MONEY=rowData.REALITY_MONEY;
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

function toSubmitRental(){
	var datagridList=$("#pageTable").datagrid('getSelections');
	var SELECT_PRO_IDS="";
	for(var i = 0; i < datagridList.length; i++){
		SELECT_PRO_IDS=SELECT_PRO_IDS +"-"+datagridList[i].HEAD_ID+":"+datagridList[i].REALITY_MONEY;
	}
	if(SELECT_PRO_IDS == ""){
		$.messager.alert("提示","请选择要退款的项目!");
		return;
	}
	if($('#RE_DATE').datebox("getValue") == ""){
		$.messager.alert("提示","请选择预计退款日期!");
		return;
	}
	$('#SELECT_PRO_IDS').val(SELECT_PRO_IDS);
	$.messager.confirm("提示","您确认提交退款申请吗？",function(flag){
		if(flag){
			$('#form01').form('submit',{
		        url:_basePath+"/refundFirst/RefundFirst!doSubmitRental.action",
		        success : function(date) {
					date = $.parseJSON(date);
					if (date.flag == true) {
						$('#pageTable').datagrid('load', {});
					} else {
						$.messager.alert("提示","申请退款失败请重试!");
					}
				},
				error : function(e) {
					$.messager.alert(e.message);
				}
		        
		    });
		}
		
	});
	
}


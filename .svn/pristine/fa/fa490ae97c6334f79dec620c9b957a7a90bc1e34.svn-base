/**
 * 保险补差收取申请审核js
 * @author 韩晓龙
 */

/**
 * 点击单号转查看明细
 */
function setOperation(val, row) {
	return "<a href='#' style='color:blue' onclick='showDetail(" + val + ")'>" + val + "</a>";
}

/**
 * TODO 查看明细页面
 */
function showDetail(GLIDE_ID){
	var METHOD = "m1";//收款
	top.addTab("明细信息", _basePath + "/insurebxbc/InsureBXBCDetail.action?GLIDE_ID=" + GLIDE_ID + "&METHOD=" + METHOD);
	return false;
}

/**
 * 清空按钮
 */
function emptyData(){
	$("#FK_DATE_BEGIN").datebox('clear');
	$("#FK_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var GLIDE_ID = $("#GLIDE_ID").val();
	var DLD = $("#DLD").val();
	var FK_DATE_BEGIN = $("#FK_DATE_BEGIN").datebox("getValue");
	var FK_DATE_END = $("#FK_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"GLIDE_ID":GLIDE_ID,"DLD":DLD,"FK_DATE_BEGIN":FK_DATE_BEGIN,"FK_DATE_END":FK_DATE_END});
}

/**
 * 核销功能
 */
function affirmItem(){
	//得到选中行的数据
	var datagridList = $('#dg').datagrid('getChecked');
	if(datagridList.length<=0){
		$.messager.alert('提示','还没有勾选条目!无法核销!','info',null);
		return false;
	}
	//校验是否有实付日期
	if(!datagridList[0].DZ_DATE){
		$.messager.alert('提示','请填写到账日期!','info',null);
		return false;
	}
	//校验是否有核销银行
	if(!datagridList[0].HX_BANK){
		$.messager.alert('提示','请填写核销银行!','info',null);
		return false;
	}
	
	//利用ajax发送请求
	$.messager.confirm("提交","确定要核销单号为" + datagridList[0].ID + "的记录吗?",function(r){
		if(r){
			$.ajax({
				type: "POST",
			    dataType:"json",
			    url: _basePath +'/insurebxbc/InsureBXBCReceiveAffirm!doUpdateBXBCItem.action',
			    data: "ID=" + datagridList[0].ID + "&DZ_DATE=" + datagridList[0].DZ_DATE + "&HX_BANK=" + datagridList[0].HX_BANK,//样式控制只能选择单条
			    success: function(flag){
					$.messager.confirm('提示','核销成功!',function(r){
						//刷新
						$('#dg').datagrid('reload');
					});
				}
			});
		}
	});
}

/**
 * 驳回功能
 */
function goBack(){
	//得到选中行的数据
	var datagridList = $('#dg').datagrid('getChecked');
	if(datagridList.length<=0){
		$.messager.alert('提示','还没有勾选条目!无法驳回!','info',null);
		return false;
	}
	//利用ajax发送请求
	$.messager.confirm("提交","确定要驳回单号为" + datagridList[0].ID + "的记录吗?",function(r){
		if(r){
			$.ajax({
				type: "POST",
			    dataType:"json",
			    url: _basePath +'/insurebxbc/InsureBXBCReceiveAffirm!doBackBXBCItem.action',
			    data: "ID=" + datagridList[0].ID,//样式控制只能选择单条
			    success: function(flag){
					$.messager.confirm('提示','已驳回!',function(r){
						//刷新
						$('#dg').datagrid('reload');
					});
				}
			});
		}
	});
}


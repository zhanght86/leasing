/**
 * 补差管理支付申请js
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
	var METHOD = "m2";//付款
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
	var FK_DATE_BEGIN = $("#FK_DATE_BEGIN").datebox("getValue");
	var FK_DATE_END = $("#FK_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"GLIDE_ID":GLIDE_ID,"FK_DATE_BEGIN":FK_DATE_BEGIN,"FK_DATE_END":FK_DATE_END});
}
/**
 * 显示 新增 - (保险补差费用)放款 
 */
function showAddPage(){
	top.addTab(" 新增 - (保险补差费用)支付放款", _basePath + "/insurebxbc/InsureBXBCPay!toShowAddApply.action");
}

/**
 * 删除条目
 */
function delItem(){
	//得到选中行的数据
	var datagridList = $('#dg').datagrid('getChecked');
	if(datagridList.length<=0){
		$.messager.alert('提示','还没有勾选条目!无法删除!','info',null);
		return false;
	}
	//利用ajax发送请求
	$.messager.confirm("删除","确定要删除该申请吗?",function(r){
		if(r){
			$.ajax({
				type: "POST",
			    dataType:"json",
			    url: _basePath +'/insurebxbc/InsureBXBCPay!deleteBCZFApply.action',
			    data: "ID=" + datagridList[0].ID,//样式控制只能选择单条
			    success: function(flag){
					$.messager.confirm('提示','删除成功!',function(r){
						//刷新
						$('#dg').datagrid('reload');
					});
				}
			});
		}
	});
}
/**
 * 提交   //update FIL_INSURE_APPLY_INFO set is_sub='已提交',status='0'
 */
function commitItem(){
	//得到选中行的数据
	var datagridList = $('#dg').datagrid('getChecked');
	if(datagridList.length<=0){
		$.messager.alert('提示','还没有勾选条目!无法提交!','info',null);
		return false;
	}
	//利用ajax发送请求
	$.messager.confirm("提交","确定要提交该申请吗?",function(r){
		if(r){
			$.ajax({
				type: "POST",
			    dataType:"json",
			    url: _basePath +'/insurebxbc/InsureBXBCPay!commitBCZFApply.action',
			    data: "ID=" + datagridList[0].ID,//样式控制只能选择单条
			    success: function(flag){
					$.messager.confirm('提示','提交成功!',function(r){
						//刷新
						$('#dg').datagrid('reload');
					});
				}
			});
		}
	});
}

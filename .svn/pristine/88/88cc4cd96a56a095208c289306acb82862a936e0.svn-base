/**
 * 款项确认
 * 
 * @author hanxl
 */
$(document).ready(function(){
	$("#FIN_CUST_ID").combobox({
		valueField: 'VALUE',
		textField: 'LABEL'
	});
	$("#PROJECT_ID").combobox({
		valueField: 'VALUE',
		textField: 'LABEL'
	});
	//供应商下拉选改变，客户名称相应改变
	$("#DLD").combobox({
		onLoadSuccess :function(){
			getClients();
		},
		onSelect : function(){
			getClients();
		}
	});
	//客户名称下拉选改变，项目编号相应改变
	$("#FIN_CUST_ID").combobox({
		onLoadSuccess :function(){
			getProjects();
		},
		onSelect : function(){
			getProjects();
		}
	});
	//得到供应商下的所属客户
	function getClients(){
		var SUP_ID = $("#DLD").combobox("getValue");
		$("#FIN_CUST_ID").combobox("clear");//客户下拉选清空
		if(SUP_ID == "") {
			return;
		}
		$("#FIN_CUST_ID").combobox("reload",_basePath + "/insureSettlement/InsureSettlementConfirm!getClients.action?SUP_ID="+encodeURI(SUP_ID));
	}
	//得到客户下的所属项目
	function getProjects(){
		var CUST_ID = $("#FIN_CUST_ID").combobox("getValue");
		$("#PROJECT_ID").combobox("clear");//项目编号下拉选清空
		if(CUST_ID == "") {
			return;
		}
		$("#PROJECT_ID").combobox("reload",_basePath + "/insureSettlement/InsureSettlementConfirm!getProjects.action?CUST_ID="+encodeURI(CUST_ID));
	}
});
/*
function setOperation(val, row, index) {
	if(row.RKSTATUS == '未认款'){
		return "<a href='#' style='color:blue;' onclick=confirmFee('" + index + "')>认款</a>";
	}else if(row.RKSTATUS == '已认款' && row.CANCEL_STATUS == '可撤销'){
		return "<a href='#' style='color:blue;' onclick=cancelFee('" + row.ID + "')>撤销</a>";
	}else{
		return "<a href='#' style='color:black;' >不可撤销</a>";
	}
}
*/
function setOperation(val, row, index) {
	//未认款的
	if(row.RKSTATUS == '未认款' && row.HLSTATUS == '未忽略'){
		return "<a href='#' style='color:blue;' onclick=confirmFee('" + index + "')>认款</a>||<a href='#' style='color:blue;' onclick=ignoreIt('" + row.ID + "')>忽略</a>";
	}else if(row.RKSTATUS == '未认款' && row.HLSTATUS == '已忽略'){
		return "<a href='#' style='color:blue;' onclick=confirmFee('" + index + "')>认款</a>||<a href='#' style='color:blue;' onclick=notIgnoreIt('" + row.ID + "')>撤销忽略</a>";
	}
	//已认款的
	else if(row.RKSTATUS == '已认款' && row.CANCEL_STATUS == '可撤销'){
		return "<a href='#' style='color:blue;' onclick=cancelFee('" + row.ID + "')>撤销认款</a>";
	}else{
		return "<a href='#' style='color:black;' >已认款不可撤销</a>";
	}
}
/**
 * 忽略
 */
function ignoreIt(ID){
	$.ajax({
		type:"post",
		url:_basePath + "/insureSettlement/InsureSettlementConfirm!ignoreIt.action",
		data:"ID=" + ID,
		dataType:"json",
		success: function(flag){
			alert("已忽略");
			dosearch();    //刷新数据
		}
	});
}
/**
 * 撤销忽略
 */
function notIgnoreIt(ID){
	$.ajax({
		type:"post",
		url:_basePath + "/insureSettlement/InsureSettlementConfirm!notIgnoreIt.action",
		data:"ID=" + ID,
		dataType:"json",
		success: function(flag){
			alert("已撤销忽略");
			dosearch();    //刷新数据
		}
	});
}
/**
 * 撤销
 * @param ID为FI_INSUREPAID_FEE_UPLOAD的ID
 */
function cancelFee(ID){
	$.ajax({
		type:"post",
		url:_basePath + "/insureSettlement/InsureSettlementConfirm!cancelConfirm.action",
		data:"ID=" + ID,
		dataType:"json",
		success: function(flag){
			alert("撤销成功");
			dosearch();    //刷新数据
		}
	});
}
/**
 * 认款
 */
function confirmFee(index){
	$('#dg').datagrid('selectRow',index);//根据index选中row
	$("#FIN_CUST_ID").combobox("clear");//客户下拉选清空
	$("#PROJECT_ID").combobox("clear");//项目编号下拉选清空
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle',row.ID + ' -认款');
        $('#fm').form('load',row);
    }
}
/**
 * 清空按钮，清空日期及可填写字段
 */
function emptyData(){
	//清空日期
	$("#CREATE_TIME").datebox('clear');
	$("#ACCOUNT_DATE").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var MONEY_LENDER = $("#MONEY_LENDER").val();
	var OPPOSITE_ACCOUNT = $("#OPPOSITE_ACCOUNT").val();
	var CREATE_TIME = $("#CREATE_TIME").datebox("getValue");
	var ACCOUNT_DATE = $("#ACCOUNT_DATE").datebox("getValue");
	
	$('#dg').datagrid('load', {"MONEY_LENDER":MONEY_LENDER,"OPPOSITE_ACCOUNT":OPPOSITE_ACCOUNT,"CREATE_TIME":CREATE_TIME,"ACCOUNT_DATE":ACCOUNT_DATE});
}
/**
 * 保存认款
 */
function save(){
    $('#fm').form('submit',{
        url: _basePath + "/insureSettlement/InsureSettlementConfirm!saveConfirmMes.action",
        onSubmit: function(){
    	
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
                $('#dlg').dialog('close');      //关闭窗口
                $('#dg').datagrid('reload');    //刷新数据
            }
        }
    });
}

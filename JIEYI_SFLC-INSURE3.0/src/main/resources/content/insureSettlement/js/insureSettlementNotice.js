/**
 * 保险理赔款款项通知
 * @author hanxl
 */
function setOperation(val, row) {
	return "<a href='#' style='color:blue;' onclick=showDetail('" + row.CREATE_TIME + "')>" + row.CREATE_TIME + "款项" +"</a>";
}

function showDetail(CREATE_TIME){
	top.addTab("款项确认", _basePath + "/insureSettlement/InsureSettlementConfirm!exeNotice.action?CREATE_TIME="+CREATE_TIME);
}
/**
 * 刷新
 */
function refreshPage(){
	$('#dg').datagrid('reload');//刷新
}
/**
 * 忽略其余未认款项
 */
function ignoreElse(){
	var CREATE_TIME = $("#CREATE_TIME").val();//上传时间
	$.messager.confirm("忽略其余未认款项","此操作将忽略其余未认款项,确定执行吗?",function(r){
		if(r){
			$.ajax({
				type:"post",
				url:_basePath + "/insureSettlement/InsureSettlementConfirm!ignoreElse.action",
				data:"CREATE_TIME=" + CREATE_TIME,
				dataType:"json",
				success: function(flag){
					alert("已忽略其余未认款项");
					dosearch();    //刷新数据
				}
			});
		}else{
			return;
		}
		$('#dg').datagrid('reload');//刷新
	});
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

////////////////////////以下是认款功能//////////////////////////////
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

function setOpt(val, row, index) {
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
	var ACCOUNT_DATE = $("#ACCOUNT_DATE").datebox("getValue");
	
	$('#dg').datagrid('load', {"MONEY_LENDER":MONEY_LENDER,"OPPOSITE_ACCOUNT":OPPOSITE_ACCOUNT,"ACCOUNT_DATE":ACCOUNT_DATE});
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



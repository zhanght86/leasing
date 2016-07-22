/**
 * 担保费发票填写
 * 作者:韩晓龙
 */

function dosearch(){
	var DLD = $("#DLD").val();
	var PROJ_ID = $("#PROJ_ID").val();
	var KHMC = $("#KHMC").val();
	var FLAG = $("#FLAG").val();
	var QZ_CONFIRMDATE_BEGIN = $("#QZ_CONFIRMDATE_BEGIN").datebox("getValue");
	var QZ_CONFIRMDATE_END = $("#QZ_CONFIRMDATE_END").datebox("getValue");
	if(""==FLAG){
		$('#pageTable').datagrid('load', {"QZ_CONFIRMDATE_BEGIN":QZ_CONFIRMDATE_BEGIN,"QZ_CONFIRMDATE_END":QZ_CONFIRMDATE_END,"DLD":DLD,"PROJ_ID":PROJ_ID,"KHMC":KHMC,"FLAG":FLAG});
	}else if(FLAG == '1'){
		$('#pageTable').datagrid('load', {"QZ_CONFIRMDATE_BEGIN":QZ_CONFIRMDATE_BEGIN,"QZ_CONFIRMDATE_END":QZ_CONFIRMDATE_END,"DLD":DLD,"PROJ_ID":PROJ_ID,"KHMC":KHMC,"FLAG1":"1"});
	}else if(FLAG == '2'){
		$('#pageTable').datagrid('load', {"QZ_CONFIRMDATE_BEGIN":QZ_CONFIRMDATE_BEGIN,"QZ_CONFIRMDATE_END":QZ_CONFIRMDATE_END,"DLD":DLD,"PROJ_ID":PROJ_ID,"KHMC":KHMC,"FLAG2":"2"});
	}
}

/**
 * 清空按钮
 */
function emptyData(){
	//清空日期
	$("#QZ_CONFIRMDATE_BEGIN").datebox('clear');
	$("#QZ_CONFIRMDATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}

// 操作
function setOperation(val,row,index){
     return "<a href=javascript:void(0) onclick=show(" + index +") >新建/修改发票</a>";
}

//显示担保费条目信息
function show(index){
    $('#pageTable').datagrid('selectRow',index);//根据index选中row
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','添加/修改发票信息');
        $('#fm').form('load',row);
    }
    
}

//新增/修改
function save(){
	var ID = $("#ID").val();
    var GUARANTEE_NUM = $("#GUARANTEE_NUM").val();//担保号
    var GUARANTEE_DATE = $("#GUARANTEE_DATE").datebox("getValue");//担保日期
    var INVOICE_NUM = $("#INVOICE_NUM").val();//发票号
    var INVOICE_DATE = $("#INVOICE_DATE").datebox("getValue");//发票日期
    
    $.messager.confirm("添加/修改","确定保存录入的信息吗?",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/vouchInvoices/VouchInvoices!updateVouchItem.action",
				data : { "ID": ID,"GUARANTEE_NUM": GUARANTEE_NUM,"GUARANTEE_DATE": GUARANTEE_DATE,"INVOICE_NUM": INVOICE_NUM,"INVOICE_DATE": INVOICE_DATE},
				dataType:'json',
				success:function(data){
					$.messager.confirm("结果","保存成功",function(r){
						//刷新页面
						top.addTab("担保费发票填写", _basePath + "/vouchInvoices/VouchInvoices.action");
					})
				}
			});
		}
	});
}
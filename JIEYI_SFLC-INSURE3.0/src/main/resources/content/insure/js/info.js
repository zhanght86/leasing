$(document).ready(function(){
	var data = {};
	$("#pageForm :input").each(function(){
		data[$(this).attr("name")]=$(this).val();
	});
	$("#search").click(function(){
		data = {};
		$("#pageForm :input").each(function(){
			data[$(this).attr("name")]=$(this).val();
		});
		$('#pageTable').datagrid('load', data);
	});
	
	$("#pageTable").datagrid({
		url:_basePath+"/insure/InsureInfo!getPageList.action",
		pageSize:20,
		fit : true,
		selectOnCheck:true,
		pagination : true,
		rownumbers : true,
		queryParams:data,
		sortName :"EST_DATE",
		toolbar : "#pageForm",
        columns:[[
            {field:'ID',checkbox:true},
            {field:'STATUS',width:50,title:'状态',sortable:true},
            {field:'PRO_CODE',width:100,title:'项目编号'},
            {field:'SUPP_NAME',width:80,title:'供应商'},
            {field:'CUST_NAME',width:80,title:'客户名称'},
            {field:'CUST_NAME_END',width:80,title:'终端客户名称'},
            {field:'EQU_NAME',width:100,title:'租赁物名称'},
            {field:'EQU_TYPE',width:80,title:'租赁物类型'},
            {field:'FACTORY_NO',width:130,title:'出厂编号'},
            {field:'CAR_SYMBOL',width:140,title:'车架号'},
            {field:'EQU_MONEY',width:80,title:'租赁物购买价款'},
            {field:'START_CONFIRM_DATE',width:80,title:'起租确认日'},
            {field:'PRO_END_DATE',width:80,title:'项目到期日'},
            {field:'EST_DATE',width:80,title:'预投日期',sortable:true},
            {field:'INS_COM_NAME',width:80,title:'保险公司'},
            {field:'INS_CODE',width:80,title:'保单号'},
            {field:'INS_MONEY',width:80,title:'保险金额'},
            {field:'INS_START',width:80,title:'起始日期',sortable:true},
            {field:'INS_END',width:80,title:'截止日期',sortable:true},
            {field:'YEARNUM',width:80,title:'租赁期限'},
            {field:'op',width:80,title:'操作',formatter:function(value,rowData,rowIndex){
					var re = " <a href='javascript:void(0);' onclick='lookFile("+rowData.ID+")'>附件</a>";
	            	return re;
	    		}
	        }
        ]]
    });
});

function lookFile(id){
	top.addTab("保险提醒"+id,_basePath+"/insure/InsureConfirm!getFileList.action?id="+id);
}

function excel(){
	var ids = "";
	$.each($('#pageTable').datagrid("getSelections"),function(){
		ids = ids + this.ID+",";
	});
	window.open(_basePath+"/insure/InsureInfo!excel.action?ids="+ids);
}

function excelAll(){
	window.open(_basePath+"/insure/InsureInfo!excelAll.action");
}

function clear_(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
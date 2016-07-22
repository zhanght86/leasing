$(function(){
	
	$("#table1").datagrid({
		columns : [[
			{field:'ID',checkbox:true,width:100,align:'center'},
			{field:'FLAG',title:'资料是否齐全',align:'center',width:100,formatter:function(value,rowData,rowIndex){
				if(value==true)
					return "<a href=javascript:void('0') onclick=showFileList('"+rowData.CLIENT_NAME+"','"+rowData.LEASE_CODE+"','"+rowData.PAYLIST_CODE+"')>齐全</a>";
				else
					return "<a href=javascript:void('0') onclick=showFileList('"+rowData.CLIENT_NAME+"','"+rowData.LEASE_CODE+"','"+rowData.PAYLIST_CODE+"')><font color=red>不齐全</font></a>";
			}},
			{field:'CLIENT_NAME',title:'客户名称',width:150,align:'center'},
			{field:'PRO_CODE',title:'项目编号',width:100,align:'center'},
			{field:'LEASE_CODE',title:'合同编号',width:100,align:'center'},
			{field:'PAYLIST_CODE',title:'还款计划编号',width:100,align:'center'},
			{field:'START_DATE_',title:'起租日期',width:100,align:'center'},
			{field:'LEASE_TERM_',title:'租赁期限',width:100,align:'center'},
			{field:'PAY_WAY_',title:'支付方式',width:100,align:'center'},
			{field:'MOENY',title:'融资基数',width:100,align:'center'},
			{field:'STATUS',title:'状态',width:100,align:'center'},
			{field:'COMPANY_NAME',title:'厂商',width:100,align:'center'},
			{field:'SUPPLIERS_NAME',title:'供应商',width:100,align:'center'},
			{field:'PRODUCT_NAME',title:'租赁物类型',width:100,align:'center'},
			{field:'SPEC_NAME',title:'机型',width:100,align:'center'},
			{field:'REALITY_DATE',title:'实际放款日期',width:100,align:'center'}
		]],	
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'ID',
		fitColumns : false,
		autoRowHeight : true,
		//singleSelect : true,
		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
		
		pageSize : 100,
		pageList : [20,50,100,200,300],
		onSelect:onChangeSelect,
		onSelectAll:onChangeSelect,
		onUnselectAll:onChangeSelect,
		onUnselect:onChangeSelect
	});
	$('#form01').form({
        url:""
    });
	
});

function onChangeSelect(){
	var datagridList=$("#table1").datagrid('getSelections');
	var REALITY_MONEYAll=0;
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++)
	{
		var REALITY_MONEY=datagridList[i].MOENY;
		REALITY_MONEYAll=accAdd(REALITY_MONEYAll,REALITY_MONEY);
		NUM++;
	}
	$("#FINANCE_MONEY").text(REALITY_MONEYAll);
	$("#PROJECT_NUM").text(NUM);//项目数量
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

//查询
function se(){
	$("#FID").val($("#PAY_WAY option:selected").attr("fid"));
	var param = getFromData('#toolbar');
	$('#table1').datagrid({
		url : _basePath+'/screened/FinanceScreen!queryMGData.action?param='+param
	});
}

//清空
function clearQuery(){
//	$('#form01').form('clear');
	$('#START_DATE').datebox('setValue','');
	$('#START_DATE1').datebox('setValue','');
	$('#PAY_DATE').datebox('setValue','');
	$('#PAY_DATE1').datebox('setValue','');
	$('#STATUS').val("");
	$('#LEASE_TERM').val("");
}



//创建项目-弹出框
function addallProject(){
	var datagridList=$("#table1").datagrid('getSelections');
	if(datagridList.length == 0){
		$.messager.alert("提示","请选择融资业务项目");
		return;
	}
	$("#toAddRePro").dialog("open");
}
/**
 * 关闭弹出窗口
 * @param div
 * @return
 */
function closeAddRePro(div){
	$("#"+div).dialog('close');
}



//创建项目操作-融资筛选
function doAddRePro(){
	var checked=0;
	var proid="";
	var REALITY_MONEYAll=0;//选中剩余租金总额
	var PRODUCT_PROJECT_COUNT=0;//选择项目数量
	var detailData = $("#table1").datagrid('getSelections');
	if(detailData.length == 0){
		$.messager.alert("提示","请选择融资业务项目");
		return;
	}
	for(var i = 0; i<detailData.length; i++) {
		var REALITY_MONEY=detailData[i].MOENY;
		REALITY_MONEYAll=accAdd(REALITY_MONEYAll,REALITY_MONEY);
		PRODUCT_PROJECT_COUNT++;
			
		proid+=detailData[i].ID+",";
		checked= checked+1;
	}
	proid +="0";
	var  PROJECT_CODE = $("#PROJECT_CODE").val();
	if(PROJECT_CODE==""||PROJECT_CODE==null){
		$.messager.alert("提示","请填写项目编号！");
		return;
	}
    var dataJson={
	   PRO_IDS:proid,
	   PROJECT_CODE:PROJECT_CODE,
	   PLAN_DATE :$("#PLAN_DATE").datebox("getValue"),
	   PRODUCT_PAY_WAY :$("#PAY_WAY").val(),
	   PAY_BASE:$("#PAY_BASE").val(),
	   PRODUCT_PROJECT_MONEY: REALITY_MONEYAll,
	   PROJECT_NAME: $("#PROJECT_NAME").val(),
	   PRODUCT_PROJECT_COUNT: PRODUCT_PROJECT_COUNT
	};
	$.ajax({
		  type: "post",
		  url: _basePath+"/screened/FinanceScreen!createProject.action",
		  data:"dataJson="+JSON.stringify(dataJson),
		  dataType:"json",
		  success: function(json){
			  if(json.flag==true){
				  $.messager.alert("提示","创建成功,"+json.msg+json.data);
				  $("#toAddRePro").dialog("close");
				  se();
			  }else{
				  $.messager.alert("提示","创建失败:"+json.msg);
				  $("#toAddRePro").dialog("close");
			  }
		  }
     });
 
}

/**
 * 查看融资资料是否齐全
 * 
 * @author King 2014年9月19日
 */
function showFileList(CLIENT_NAME,LEASE_CODE,PAYLIST_CODE){
	top.addTab(LEASE_CODE+"资料列表",_basePath+"/screened/FinanceScreen!showFileList.action?FID="+$("#FID").val()
				+"&PAY_WAY="+$("#PAY_WAY").val()+"&CLIENT_NAME="+CLIENT_NAME+"&LEASE_CODE="+LEASE_CODE
				+"&PAYLIST_CODE="+PAYLIST_CODE);
}
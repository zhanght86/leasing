$(document).ready(function(){
	$("#LITIG_CASE").dialog('close');
	
	$("#litigation_MG").datagrid({
		url:"litigationCase!litigation_case_OP_AJAX.action",
		pagination:true,//是否分页 true为是
		singleSelect:true,	
		fit:true,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'FLAG_NAME',title:'状态',width:130},
		          	{field:'CUST_NAME',title:'客户名称',width:130},
		          	{field:'PRO_CODE',title:'项目编号',width:130},
		          	{field:'PAYLIST_CODE',title:'还款计划',width:130},
		          	{field:'CUST_FLAG',title:'客户类型',width:130},
		          	{field:'CUST_CARD',title:'身份证/组织机构代码',width:130},
		          	{field:'CLERK_NAME',title:'客户经理',width:130},
		          	{field:'CREATE_TIME',title:'创建时间',width:130},
		          	{field:'PAYLIST_ID',hidden:true}
		         ]],
		         frozenColumns:[[
		       		{field:'AA',width:250,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
		       			if(rowData.CASE_NOT_COUNT=='0')
		       			{
		       				return "<a href='javascript:void(0)' onclick='createLitigationCase(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+">添加案例</a>&nbsp;|&nbsp;<a href='../overdue/Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='../overdue/Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;";
		       			}
		       			else{
		       				return "<a href='../overdue/Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='../overdue/Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;";
			       		}
		        	 }}
				]]
		});
	
});

//
/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

function createLitigationCase(obj)
{
	var PAYLIST_CODE=$(obj).attr("PAYLIST_CODE");
	
		jQuery.ajax({
			type:"post",
			url:"litigationCase!queryCreateFormAjax.action?PAYLIST_CODE="+PAYLIST_CODE,
			dataType:"json",
			success:function(e){
				$("#CUST_NAME_C").val(e.data[0].CUST_NAME);
				$("#CUST_ID_C").val(e.data[0].CUST_ID);
				$("#PROJECT_CODE_C").val(e.data[0].PROJECT_CODE);
				$("#PROJECT_ID_C").val(e.data[0].PROJECT_ID);
				$("#PAYLIST_CODE_C").val(e.data[0].PAYLIST_CODE);
				$("#PAYLIST_ID_C").val(e.data[0].PAYLIST_ID);
				$("#SUPPER_NAME_C").val(e.data[0].SUPPER_NAME);
				$("#SUPPER_ID_C").val(e.data[0].SUPPER_ID);
				$("#AREA_NAME_C").val(e.data[0].AREA_NAME);
				$("#AREA_ID_C").val(e.data[0].AREA_ID);
				$("#LITIG_CASE").dialog('open');
			},
		error:function(e){alert("转诉讼失败");}   
		});
}

function subLitigation()
{
	var CUST_ID=$("#CUST_ID_C").val();
	var CUST_NAME=$("#CUST_NAME_C").val();
	var PROJECT_ID=$("#PROJECT_ID_C").val();
	var PROJECT_CODE=$("#PROJECT_CODE_C").val();
	var PAYLIST_ID=$("#PAYLIST_ID_C").val();
	var PAYLIST_CODE=$("#PAYLIST_CODE_C").val();
	var SUPPER_ID=$("#SUPPER_ID_C").val();
	var SUPPER_NAME=$("#SUPPER_NAME_C").val();
	var AREA_ID=$("#AREA_ID_C").val();
	var AREA_NAME=$("#AREA_NAME_C").val();
	var ALLEGED_MONEY=$("#ALLEGED_MONEY_C").val();
	var AREA_COURT=$("#AREA_COURT_C").val();
	var COURT_TEL=$("#COURT_TEL_C").val();
	var LAW_TYPE=$("#LAW_TYPE_C").val();
	var REMARK=$("#REMARK_C").val();
	var CASE_CODE=$("#CASE_CODE_C").val();
	var TITLE_NAME=$("#TITLE_NAME_C").val();
	
//	if (ALLEGED_MONEY == ''){
//		alert("请输入涉嫌金额");
//		return ;
//	}
	
	if (AREA_COURT == ''){
		alert("请输入管辖法院");
		return ;
	}
	
	if (COURT_TEL == ''){
		alert("请输入法院联系方式");
		return ;
	}
	
	if (CASE_CODE == ''){
		alert("请输入案件编号");
		return ;
	}
	
	if (TITLE_NAME == ''){
		alert("请输入案件名称");
		return ;
	}
	
	jQuery.ajax({
		type:"post",
		url:"litigationCase!createLitigation.action?CUST_ID="+CUST_ID+"&CUST_NAME="+encodeURI(CUST_NAME)+"&PROJECT_ID="+PROJECT_ID+"&PROJECT_CODE="+PROJECT_CODE+"&PAYLIST_ID="+PAYLIST_ID+"&PAYLIST_CODE="+PAYLIST_CODE+"&SUPPER_ID="+SUPPER_ID+"&SUPPER_NAME="+encodeURI(SUPPER_NAME)+"&AREA_ID="+AREA_ID+"&AREA_NAME="+encodeURI(AREA_NAME)+"&ALLEGED_MONEY="+ALLEGED_MONEY+"&AREA_COURT="+encodeURI(AREA_COURT)+"&COURT_TEL="+COURT_TEL+"&LAW_TYPE="+LAW_TYPE+"&CASE_CODE="+encodeURI(CASE_CODE)+"&TITLE_NAME="+encodeURI(TITLE_NAME)+"&REMARK="+encodeURI(REMARK),
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("创建成功！");
				$("#LITIG_CASE").dialog('close');
				seach();
			}else{
				alert("创建失败"+json.msg);
			}
		},
	error:function(e){alert("转诉讼失败");}   
	});
}
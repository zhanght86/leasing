$(document).ready(function(){
//alert("contractMAN");
	
	$("#pageTable").datagrid({
		url:"Contract!pageAjax.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fitColumns:true,
		toolbar:'#pageForm',
//		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
//            if (data.rows.length > 0) {
//            	$('#pageTable').datagrid('selectRow', 0);
//            	$("#LEASE_CODE").val(data.rows[0].LEASE_CODE);
//            }
//        },
//		onClickRow:function(index,data){
//			$("#LEASE_CODE").val(data.LEASE_CODE);
//		},
        frozenColumns:[[
					{field:'aaa',title:'操作',width:70,align:'center',formatter:function(value,row,rowIndex){
							 var STATUS=row.STATUS;
							 var LCNAME=row.LCNAME;
							 var rehtml="";
							 rehtml = "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sechemSHow(" + JSON.stringify(row) + ")'>查看</a>"
							 return rehtml;
					 }
					}
                ]],
		columns:[[
		    {field:'STATUS_NAME',title:'状态',width:70,align:'center'},
		    {field:'PLATFORM_NAME',title:'业务类型',width:120,align:'center'},
		    {field:'LEASE_CODE',title:'融资租赁合同号',width:150,align:'center'},
	      	{field:'ID',hidden:true,align:'center'},
	      	{field:'CUST_TYPE',hidden:true,align:'center'},
	      	{field:'CUST_ID',hidden:true,align:'center'},
	      	{field:'STATUS',hidden:true,align:'center'},
	      	{field:'PRO_CODE',title:'项目编号',width:130,align:'center'},
//	      	{field:'LEASE_MODEL',title:'租赁模式',width:110},
	      	{field:'PRO_NAME',hidden:true,align:'center'},
	      	{field:'CUST_NAME',title:'客户名称',width:150,align:'center',formatter:function(value,rowData,rowIndex){
	        	  return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+rowData.CUST_NAME+"</a>";
	          }},
	        {field:'CUST_TYPE_NAME',title:'客户类型',width:100,align:'center'},
	      	{field:'CLERK_NAME',title:'客户经理',width:130,align:'center'},
	      	{field:'CREATE_TIME',title:'创建时间',width:100,align:'center'}
	     ]]
	});
	
});

function dowStartBook(PROJECT_ID){
	window.location.href=_basePath+"/pay/PayTask!doShowRentStartInfo.action?PROJECT_ID="+PROJECT_ID;
}
function delProject(id){
	if(confirm("当前项目删除后不可撤回，确认删除？")){
		$.ajax({
			url : _basePath+"/project/project!doDelProject.action?id="+id,
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("项目删除成功！");
					$("#pageTable").datagrid("reload");
				}else{
					alert(json.msg);
				}
			}
		});
	}
}

function doSh(id){
	if(confirm("确认收回当前项目?")){
		$.ajax({
			url : _basePath+"/project/project!doSh.action?id="+id,
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("成功");
					$("#pageTable").datagrid("reload");
				}else{
					alert(json.msg);
				}
			}
		});
	}
}

function toViewCust(row) {
	var value=row.CUST_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}


function sechemSHowOld(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectShowOld.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function sechemSHow(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"查看",_basePath+"/project/project!projectShowContract.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}


//查看流程信息  楊雪  2013-12-12
function liuchengShow(row){
	if (row){
		 var TTT_ID=row.TTT_ID;
		 var JM_ID  =row.JM_ID  ;
		 top.addTab(TTT_ID,_basePath+"/bpm/Task!toShowOld.action?MEMO_ID="+JM_ID+"&JBPM_ID"+encodeURI(TTT_ID));
	}else {
		
	}
}


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

/**
 * 导出excel
 * @author 韩晓龙
 */
function exportExcel(){
	
	//params
	var searchParams = getFromData('#pageForm');
	
	$.messager.confirm("导出","确定要导出查询记录吗?",function(r){
		/*
		if(r){
			jQuery.ajax({
				url : _basePath + "/project/project!exportExcel.action",
				data : { "searchParams": searchParams},
				dataType:'json',
				success:function(data){
					
				}
			});
		}
		*/
		if(r){
			window.location.href = _basePath + "/project/project!exportExcel.action?searchParams=" + searchParams;
		}
	});
}

//根据条件取数据，需jQuery json支持
function getFromData(str) {
	var data = {};
	$(str + ' [name]').each(
			function() {
				if ($(this).is(":checkbox,:radio")) {
					if ($(this).attr("checked")) {
						data[$(this).attr("name")] = $(this).val();
					}
				} else {
					if ($(this).is("select")) {
						data[$(this).attr("name")] = $(this).find(":selected").val();
					} else {
						data[$(this).attr("name")] = $(this).val();
					}
				}
			});
	return JSON.stringify(data);
}

$(document).ready(function(){
	$("#divMarkInvalid").dialog('close');
	$("#dialogProduct").dialog('close');
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	var PRO_CODE=$("input[name='PRO_CODE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var STATUS=$("select[name='STATUS']").val();
	var STATUS2=$("#STATUS2").val();//hxl
	$("#pageTable").datagrid({
		url:_basePath+"/project/Contract!pageAjax.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		fitColumns:true,
		toolbar:'#pageForm',
		queryParams:{"PLATFORM_TYPE":PLATFORM_TYPE,"PRO_CODE":PRO_CODE,"CUST_NAME":CUST_NAME,"STATUS":STATUS,"STATUS2":STATUS2},
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
            	$('#pageTable').datagrid('selectRow', 0);
            	$("#PROJECT_ID_CONTRACT").val(data.rows[0].ID);
            	$("#PLATFORM_TYPE_CONTRACT").val(data.rows[0].PLATFORM_TYPE);
            	$("#STATUS_CONTRACT").val(data.rows[0].STATUS);
            }
        },
		onClickRow:function(index,data){
			$("#PROJECT_ID_CONTRACT").val(data.ID);
			$("#PLATFORM_TYPE_CONTRACT").val(data.PLATFORM_TYPE);
        	$("#STATUS_CONTRACT").val(data.STATUS);
		},
		 frozenColumns:[[
							{field:'aaa',title:'操作',width:130,align:'center',formatter:function(value,row,rowIndex){
									 var STATUS=row.STATUS;
									 var LCNAME=row.LCNAME;
									 var PARENT_ID = row.PARENT_ID;
									 var rehtml="";
									 if(100 != STATUS){
										 rehtml = rehtml+ "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='showMarkInvalid(" + JSON.stringify(row.ID) + ")'>作废</a> | ";
									 }else if(undefined == PARENT_ID){
										 rehtml = rehtml+ "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='divMarkSign(" + JSON.stringify(row.ID) + ")'>合同重签</a> | ";
									 }
									 rehtml = rehtml+ "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sechemSHow(" + JSON.stringify(row) + ")'>查看</a> | ";
									 rehtml = rehtml+ "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='lookJbpmList(" + JSON.stringify(row.ID) + ")'>申请审批</a>";
									 return rehtml;
							 }
							}
		                ]],
		columns:[[
				    {field:'STATUS_NAME',title:'状态',width:60,align:'center'},
				    {field:'PLATFORM_NAME',title:'业务类型',width:100,align:'center'},
				    {field:'LEASE_CODE',title:'融资租赁合同号',width:120,align:'center'},
			      	{field:'ID',hidden:true,align:'center'},
			      	{field:'CUST_TYPE',hidden:true,align:'center'},
			      	{field:'CUST_ID',hidden:true,align:'center'},
			      	{field:'STATUS',hidden:true},
			      	{field:'PRO_CODE',title:'项目编号',width:130,align:'center'},
//			      	{field:'LEASE_MODEL',title:'租赁模式',width:110},
			      	{field:'PRO_NAME',hidden:true,align:'center'},
			      	{field:'CUST_NAME',align:'center',title:'客户名称',width:150,formatter:function(value,rowData,rowIndex){
			        	  return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+rowData.CUST_NAME+"</a>";
			          }},
			        {field:'CUST_TYPE_NAME',title:'客户类型',width:100,align:'center'},
			      	{field:'CLERK_NAME',title:'提件人',width:130,align:'center'},
			      	{field:'CREATE_TIME',title:'创建时间',width:100,align:'center'}
			     ]]
	});
});

function dowStartBook(PROJECT_ID){
	window.location.href=_basePath+"/pay/PayTask!doShowRentStartInfo.action?PROJECT_ID="+PROJECT_ID;
}

function add_project(CUST_ID,CUST_TYPE,CUST_NAME,PRO_CODE){
	 top.addTab(PRO_CODE+"添加项目",
	_basePath+"/project/project!lodingDiv.action?CUSTOMER_ID="+CUST_ID+"&CUSTOMER_TYPE="+CUST_TYPE+"&PLATFORM_TYPE=4&PROJECT_MODEL=4"
      +"&CUSTOMER_NAME="+CUST_NAME+"&LEASE_MODEL=back_leasing&PRO_CODE="+PRO_CODE);
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
		 var STATUS = row.STATUS;
		 var PARENT_ID = row.PARENT_ID;
		 var CQTYPE = "";
		 if(STATUS=='100'){
			 CQTYPE="zf";
		 }
		 if(null != PARENT_ID){
			 CQTYPE="cq";
		 }
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"查看",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID+"&CQTYPE="+CQTYPE);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function appendScheme(row){
	if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PLATFORM_TYPE=row.PLATFORM_TYPE;
		 top.addTab(PRO_CODE+"追加设备方案",_basePath+"/project/project!appendScheme.action?PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function qzApp(row){
	if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"起租申请",_basePath+"/leaseApplication/LeaseApplication!queryEQList.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}


function sechemExpPDF(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PRO_NAME=row.PRO_NAME;
		 var CUST_NAME=row.CUST_NAME;
		 var CUST_TYPE_NAME=row.CUST_TYPE_NAME;
		 var CUST_TYPE=row.CUST_TYPE;
		 var PLATFORM_NAME=row.PLATFORM_NAME;
		 var AREA_NAME=row.AREA_NAME;
		 var CUST_ID=row.CUST_ID;
		 var ORG_NAME=row.ORG_NAME;
		 var LEASE_TOPRIC=row.LEASE_TOPRIC;
		 var BANK_NAME=row.BANK_NAME;
		 var POB_ID=row.POB_ID;
//		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID);
		 window.location.href = _basePath+"/project/project!projectExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID;
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function confirExpPDF(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PRO_NAME=row.PRO_NAME;
		 var CUST_NAME=row.CUST_NAME;
		 var CUST_TYPE_NAME=row.CUST_TYPE_NAME;
		 var CUST_TYPE=row.CUST_TYPE;
		 var PLATFORM_NAME=row.PLATFORM_NAME;
		 var AREA_NAME=row.AREA_NAME;
		 var CUST_ID=row.CUST_ID;
		 var ORG_NAME=row.ORG_NAME;
		 var LEASE_TOPRIC=row.LEASE_TOPRIC;
		 var BANK_NAME=row.BANK_NAME;
		 var POB_ID=row.POB_ID;
//		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID);
		 window.location.href = _basePath+"/project/project!confirmationExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID;
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function sechemUpdateOld(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab("项目立项",_basePath+"/project/project!projectUpdateOld.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}


function sechemUpdate(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"修改",_basePath+"/project/project!projectUpdate.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function lookJbpmList(PROJECT_ID){
	top.addTab("流程查看",_basePath+"/project/project!doShowProjectJbpmList.action?PROJECT_ID="+PROJECT_ID);
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
function  divMarkSign(id){
	$("#divMarkSign-buttons").attr("display","block");
	$("#divMarkSign input[name='signProjectId']").val('');
	$("#divMarkSign input[name='signProjectId']").val(id);
	$("#divMarkSign textarea[name='markSignTextArea']").val('');
	$('#divMarkSign').dialog('open').dialog('setTitle','重签理由');
}
function checkProjectReSignContract(){
	var id =$("#divMarkSign input[name='signProjectId']").val();
	var MARK_SIGN = $("#markSignTextArea").val();
	if(confirm("确定要重签项目吗？")){
		$.ajax({
			url:_basePath+"/project/project!checkProjectReSignContract.action?_dateTime="+new Date(),
			data:'ID='+id+"&MARK_SIGN="+MARK_SIGN,
			dataType:'json',
			type:'post',
			success:function(json){
				if(json.flag){
					alert("重签项目成功！");
					$("#divMarkSign").dialog("close");
					$("#pageTable").datagrid("reload");
				}else{
					$("#divMarkSign").dialog("close");
					alert(json.msg);
				}
				
			}
		});
	}
}
function closeDialog(name){
	   $('#'+name).dialog('close');
	}
function showMarkInvalid(id){
	$("#divMarkInvalid-buttons").attr("display","block");
	$("#divMarkInvalid input[name='invalidProjectId']").val('');
	$("#divMarkInvalid input[name='invalidProjectId']").val(id);
	$("#divMarkInvalid textarea[name='markInvalidTextArea']").val('');
	$('#divMarkInvalid').dialog('open').dialog('setTitle','作废理由');
}
function invalidProject(){
	var id =$("#divMarkInvalid input[name='invalidProjectId']").val();
	var MARK_INVAILD = $("#markInvalidTextArea").val();
	if(confirm("确定要作废此合同？")){
		$.ajax({
			url : _basePath+"/project/project!doInvalidProject.action?PROJECT_ID="+id+"&MARK_INVAILD="+MARK_INVAILD,
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("项目作废成功！");
					$("#divMarkInvalid").dialog("close");
					$("#pageTable").datagrid("reload");
				}else{
					$("#divMarkInvalid").dialog("close");
					alert(json.msg);
				}
			}
		});
	}
}
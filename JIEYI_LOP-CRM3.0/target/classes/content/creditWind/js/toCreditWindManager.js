$(document).ready(function(){

	$("#pageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		url:'CreditWind!toCreditWindAjaxData.action',
		columns:[[
					{field:'BB',width:10,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
					//	return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='ADDWIND(" + JSON.stringify(rowData) + ")'>添加</a> &nbsp;<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='ViewWind(" + JSON.stringify(rowData) + ")'>查看</a> &nbsp;";
						return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='ViewWind(" + JSON.stringify(rowData) + ")'>查看</a> &nbsp; | &nbsp; <a href='"+_basePath+"/project/Contract!expCreditWindInfo.action?PROJECT_ID="+rowData.PROJECT_ID+"&FILE_NAME=评审会议纪要'>导出</a>| &nbsp; <a href='"+_basePath+"/project/Contract!expCreditWindInfoPDF.action?TPM_TYPE=评审批复函&CLIENT_ID="+rowData.CLIENT_ID+"&PROJECT_ID="+rowData.PROJECT_ID+"'>导出PDF</a>";//Modify By YangJ 2014-5-22 上午10:04:34
					}},
		          	{field:'ID',hidden:true},
		          	{field:'PROJECT_ID',hidden:true},
		          	{field:'PDF_ID',hidden:true},
		          	{field:'STATUS_NAME',title:'评审会议结果',width:20,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:15,align:'center'},
		          	{field:'SERIAL_NUMBER',title:'评审会议编号',width:15,align:'center'},
		          	{field:'MEEING_TIME',title:'评审会议时间',width:20,align:'center'}
	                
		         ]]
	});
});

function ADDWIND(row){
	var PROJECT_ID=row.PROJECT_ID;
	top.addTab("添加",_basePath+"/creditWind/CreditWind!AddControlMeetingPage.action?PROJECT_ID="+PROJECT_ID);
}

function ViewWind(row){
	var ID=row.ID;
	var SERIAL_NUMBER=row.SERIAL_NUMBER;
	top.addTab(SERIAL_NUMBER+"查看",_basePath+"/creditWind/CreditWind!toShowWindView.action?windID="+ID);
}

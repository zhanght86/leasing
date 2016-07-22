$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/managereport/ManageReport!toMgManageReport.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
//		frozenColumns:[[    
//					{field:'TR_ID',width:200,align:'center',title:'操作', formatter:function(value,rows,index){
//						if(rows.TR_ID==undefined){
//							return "<a href='#' onclick='add("+rows.PROJECT_ID+"\,"+rows.CLIENT_ID+"\,\""+rows.CUST_ID+"\",\""+rows.CLIENT_NAME+"\")'>添加</a>  " ;
//						}else {
//						if(rows.TR_ID!=undefined){
//							return "<a href='#' onclick='view("+rows.TR_ID+"\,"+rows.PROJECT_ID+"\,"+rows.CLIENT_ID+"\,\""+rows.CUST_ID+"\",\""+rows.CLIENT_NAME+"\")'>查看</a> || " +
//						  		"<a href='#'onclick='update("+rows.TR_ID+"\,"+rows.PROJECT_ID+"\,"+rows.CLIENT_ID+"\,\""+rows.CUST_ID+"\",\""+rows.CLIENT_NAME+"\")'>修改</a> ||" +
//						  		"<a href='#' onclick='updateQixian("+rows.TR_ID+"\,\""+rows.ZX_INVESTIGATE_DATE+"\")'>修改调查期限</a> || " +
//							    "<a href='#' onclick='del("+rows.TR_ID+")'>删除</a>";
//						}
//					}}    
//		             ]], 
		columns:[[
		          {field:'CUST_ID',width:120,align:'center',title:'区域'}, 
		          {field:'CLIENT_NAME',width:120,align:'center',title:'区域经理'},
				  {field:'ZX_ACCESS_TYPE',width:120,align:'center',title:'总融资额'},
				  {field:'SPEC_NAME',width:120,align:'center',title:'小额批量'}, 
				  {field:'hh',width:120,align:'center',title:'个人购车'},
				  {field:'UNIT_PRICE',width:120,align:'center',title:'其他类型'},
				  {field:'START_PERCENT',width:120,align:'center',title:'坏账损失金额'},
				  {field:'LEASE_TERM',width:120,align:'center',title:'坏账率'},
				  {field:'ITEM_MONEY',width:120,align:'center',title:'利润'},
				  {field:'ZX_VISIT_ADDR',width:120,align:'center',title:'合格新申请数'},
				  {field:'ZX_INVESTIGATE_DATE',width:120,align:'center',title:'合格数'},//, editor:'datebox'
				  {field:'ZX_REMARK',width:120,align:'center',title:'贷款余额'},
				  {field:'ZX_REMARK',width:120,align:'center',title:'管理经销商数'}
		         ]]	
	});
	
	//页面清空按钮操作
	$("#qingkong").click(function(){
		$(".paramData").each(function(){
			$(this).val("");
		});
	});
});

function tofindData(){
	var searchParams = getFromData('#pageForm');
	$('#insDialog').datagrid('load',{"searchParams":searchParams});
}
$(document).ready(function(){
	
	$("#paid_PageTable").datagrid({
		url:_basePath+"/shouldPaid/shouldPaid!shouldPaid_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'CUST_NAME',title:'客户名称',width:160,align:'center',formatter:function(value,rowData,rowIndex){
   		        	 return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+value+"</a>";
		          	}}, 
		          	{field:'LEASE_CODE',title:'合同号',width:160,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:160,align:'center',formatter:function(value,rowData,rowIndex){
   		        	 return "<a href='javascript:void(0)' onclick=showDetail('"+rowData.PAY_ID+"','"+rowData.PROJECT_ID+"','"+rowData.PAYLIST_CODE+"')>"+ value +"</a>　";
   		            }}, 
		          	{field:'ZJ_TOTAL_MONEY',title:'应收总额',width:110,align:'right'},
		          	{field:'WH_ZJ_MONEY',title:'应收未收',width:110,align:'right'},
		          	{field:'YH_ZJ_MONEY',title:'实收',width:130,align:'right'},
		          	{field:'YH_PERIOD',title:'已还期次',width:70,align:'center'},
		          	{field:'WH_PERIOD',title:'未还期次',width:70,align:'center'},
		          	{field:'DUE_PERIOD',title:'逾期期次',width:70,align:'center'},
		          	{field:'WH_DUE_MONEY',title:'逾期租金',width:110,align:'right'}
		          	
		         ]]
	});
	
});

/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
}

function toViewCust(row) {
	var value=row.CUST_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}

function showDetail(ID,PROJECT_ID,PAYLIST_CODE){
	top.addTab(PAYLIST_CODE+"还款计划明细",_basePath+"/pay/PayTask!toMgshowDetail.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}
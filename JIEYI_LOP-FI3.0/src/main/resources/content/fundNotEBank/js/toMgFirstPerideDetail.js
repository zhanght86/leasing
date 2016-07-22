$(function() {
	$('#pageTable').datagrid({
		url:_basePath+"/fundNotEBank/FundNotEBank!toMgFirstPeriodDeatiData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		           {field:'FUND_ID',align:'center',width:40,title:'付款单编号'},
		           {field:'D_PROJECT_CODE',align:'center',width:40,title:'项编号'},
  				   {field:'D_CLIENT_CODE',align:'center',hidden:true,width:50},
                   {field:'D_CLIENT_NAME',align:'center',width:50,title:'客户名称'},
                   {field:'PAYMENT_STATUS',align:'center',width:35,title:'放款方式'},
                   {field:'PRODUCT_NAME',align:'center',width:45,title:'租赁物类型'},
                   {field:'SUP_SHORTNAME',align:'center',width:50,title:'厂商'},                            
                   {field:'LEASE_TOPRIC',align:'center',width:60,title:'租赁物购买价款'},
  				   {field:'D_PAY_CODE',align:'center',width:50,hidden:true},
                   {field:'FIRST_MONEY',align:'center',width:50,title:'首期款合计'},
                   {field:'OTHER_MONEY',align:'center',width:50,title:'其他费用合计'},
                   {field:'DB_MONEY',align:'center',width:50,title:'DB保证金'},
                   {field:'CS_MONEY',align:'center',width:50,title:'厂商保证金'},
                   {field:'YS_MONEY',align:'center',width:50,title:'应收金额',hidden:true},
                   {field:'RECEIVABLE',align:'center',width:50,title:'本次应收金额',hidden:true},
  			       {field:'VERITABLE_MONEY',align:'center',width:50,title:'本次实收金额'}
		          ]]
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var FUND_ID = $("input[name='FUND_ID']").val();
	var D_CLIENT_NAME = $("input[name='D_CLIENT_NAME']").val();
	var D_PROJECT_CODE = $("input[name='D_PROJECT_CODE']").val();
	$('#pageTable').datagrid('load', {
		"D_PROJECT_CODE" : D_PROJECT_CODE,
		"D_CLIENT_NAME" : D_CLIENT_NAME,
		"FUND_ID" : FUND_ID
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}
$(document).ready(function(){
	$("#cyberBank_C_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		pageSize:40,
		toolbar:'#pageForm',
		url:'rentAccountMonitoring!query.action',
		columns:[[
		    
		          	{field:'LEASE_CODE',title:'合同号',width:150},
		          	{field:'CONTRACTSTATUS',title:'合同状态',width:150},
		          	{field:'NAME',title:'客户姓名',width:150},
		          	{field:'ID_CARD_NO',title:'身份证号',width:150},
		          	{field:'TEL_PHONE',title:'联系电话',width:150},
		          	{field:'CITYNAME',title:'城市',width:150},
		          	{field:'STORESNAME',title:'门店',width:150},
		          	{field:'SCHEME_NAME',title:'产品类型',width:150},
		          	{field:'CARNAME',title:'车辆名称',width:150},
		          	{field:'CAR_SYMBOL',title:'车架号',width:150},
		          	{field:'FINANCE_TOPRIC',title:'融资金额',width:150},
		          	{field:'FIRSTPAYALL',title:'首付金额',width:150},
		          	{field:'LEASE_TERM',title:'期限',width:150},
		          	{field:'MONTHRATE',title:'利率',width:150},
		          	{field:'START_PERCENT',title:'首付比例',width:150},
		          	{field:'DEPOSIT_MONEY',title:'保证金金额',width:150},
		          	{field:'BEGINNING_MONEY',title:'月租金额',width:150},
		          	{field:'PREPAYRENT',title:'预付租金',width:150},
		          	{field:'INTERESTREDUCTION',title:'利息减免',width:150},
		          	{field:'FIRSTMONEY',title:'还款金额',width:150},
		          	{field:'CROSSTOWNDATE',title:'交车日期',width:150},
		          	{field:'FIRSTDATE',title:'首次还款日',width:150},
		          	{field:'ENDDATE',title:'还款截止日',width:150},
		          	{field:'PAYMENTSDATE',title:'应还款日期',width:150},
		          	{field:'RECEIVEPAYMENTSMONEY',title:'当期应还',width:150},
		          	{field:'RETURNRENT',title:'已还租金',width:150},
		          	{field:'RESIDUALRENT',title:'剩余租金',width:150},
		          	{field:'CURRENTPRINCIPAL',title:'当期本金',width:150},
		          	{field:'CURRENTINTEREST',title:'当期利息',width:150},
		          	{field:'RESIDUALPRINCIPAL',title:'剩余本金',width:150},
		          	{field:'RESIDUALPERIOD',title:'剩余期数',width:150},
		          	{field:'ISYQ',title:'是否逾期',width:150},
		          	{field:'YQMONEY',title:'逾期金额',width:150},
		          	{field:'PENALTY_DAY',title:'逾期天数',width:150},
		          	{field:'PENALTY',title:'罚息',width:150},
		          	{field:'WYJ_REALITY_TIME',title:'实际还款时间',width:150},
		          	{field:'PERIOD',title:'当期逾期期数',width:150},
		          	{field:'YQQS',title:'累计逾期期数',width:150},
		          	{field:'REALITY_TIME',title:'提前结清时间',width:150},
		          	{field:'BEGINNING_PAID',title:'提前结清金额',width:150},
		          	{field:'FLAG',title:'业务类型',width:150}
		         ]]
	});
});
//导出
function exportData(){
	$("#divFrom").empty();
//	if($('#LOCKTYPENO').attr('abc')=='lock'){
//		alert('正在下载请稍后,如果已经下载,请点击查询按钮');
//		return false;
//	}
	     var PLAN_START_DATE=$("input[name='PLAN_START_DATE']").val();
   		 var YW_TYPE=$("select[name='YW_TYPE']").find("option:selected").val();//开户行
   		   var NAME=$("input[name='NAME']").val();
   		     var LEASE_CODE=$("input[name='LEASE_CODE']").val();
	
	if(PLAN_START_DATE==null || PLAN_START_DATE ==""){
		alert("应还日期必须选择");
		return false;
	}
	var exportType="";
	var IDS="";
	var IDS1="";
	var zuJin="";
	var weiYueJin="";
	var strUrl="";
	var datagridList=$('#cyberBank_C_PageTable').datagrid('getChecked');
		if(datagridList.length<=0)
		{
			exportType="all";
			strUrl=_basePath+"/newRentWrite/rentAccountMonitoring!excelUpload.action?exportType="+exportType+"&PLAN_START_DATE="+PLAN_START_DATE+"&YW_TYPE="+YW_TYPE+"&NAME="+NAME+"&LEASE_CODE="+LEASE_CODE;

		}else{
//			exportType="select";
//			var selectData = [];
//			for(var i = 0; i < datagridList.length; i++)
//			{
//				if(i==0){
//					IDS=datagridList[i].ID;
//				}
//				else{
//					IDS=IDS+","+datagridList[i].ID;
//				}
//			}
//			strUrl=_basePath+"/newRentWrite/financeExport!query.action?UPLOAD_NUMBER="+UPLOAD_NUMBER+"&exportType="+exportType+"&IDS="+IDS+"&PLAN_START_DATE="+PLAN_START_DATE;
		}
		$.messager.confirm("提示","您确认要导出数据吗？",function(a){
			if(a){
//				$('#LOCKTYPENO').attr('abc','lock');
				$("#divFrom").append("<form id='formSubmit' method='post' action='"+strUrl+"'></form>");
				$("#formSubmit").submit();
			}
		});
	
}




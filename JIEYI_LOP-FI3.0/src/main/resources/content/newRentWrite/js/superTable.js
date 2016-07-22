$(document).ready(function(){
	$("#cyberBank_C_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		pageSize:20,
		toolbar:'#pageForm',
		url:'superTable!query.action',
		columns:[[
{field:'HHZT',title:'合同状态',width:150},
{field:'JJSJ',title:'进件时间',width:150},//
{field:'JJBH',title:'进件编号',width:150},//
{field:'KHLY',title:'客户来源',width:150},//
{field:'CPMC',title:'产品名称',width:150},//
{field:'QYZT',title:'签约主体',width:150},//
{field:'FB',title:'分部',width:150},//
{field:'MD',title:'门店',width:150},//
{field:'SQR',title:'申请人',width:150},//
{field:'SFZH',title:'身份证号',width:150},//
{field:'DH',title:'电话',width:150},//
{field:'CPXH',title:'厂牌型号',width:150},//
{field:'SCCJ',title:'生产厂家',width:150},//
{field:'PPXH',title:'品牌型号',width:150},//
{field:'CK',title:'款式',width:150},//
{field:'PL',title:'排量',width:150},//
{field:'BSX',title:'变速箱',width:150},//
{field:'CX',title:'车型',width:150},//--
{field:'ZWS',title:'座位',width:150},//
{field:'YS',title:'颜色',width:150},//
{field:'GZS',title:'购置税',width:150},//
{field:'SP',title:'上牌',width:150},//
{field:'HBBF',title:'环保标志',width:150},//
{field:'LSPZ',title:'临时牌照',width:150},//
{field:'GPSFY',title:'GPS费用',width:150},//
{field:'LQF',title:'路桥费',width:150},//
{field:'JQX',title:'交强险',width:150},//
{field:'SYX',title:'商业险',width:150},//
{field:'CCS',title:'车船税',width:150},//
{field:'SFBL',title:'首付比',width:150},//
{field:'QX',title:'期限',width:150},//
{field:'BZJ',title:'保证金',width:150},//
{field:'HTBH',title:'合同编号',width:150},
{field:'DBJ',title:'打包价',width:150},//
{field:'BCEK',title:'补差额款',width:150},//
{field:'SFK',title:'首付款合计',width:150},//
{field:'SF',title:'首付款',width:150},//
{field:'RZJE',title:'融资金额',width:150},//
{field:'SHTGRQ',title:'审核通过日期',width:150},//
{field:'CGJ',title:'采购价',width:150},//
{field:'YZJE',title:'月租金额',width:150},//
{field:'YFZJ',title:'预付租金',width:150},//
{field:'QYRQ',title:'签约日期',width:150},//
{field:'YHKH',title:'银行账号',width:150},//
{field:'KHH',title:'开户行',width:150},//
{field:'HTBB',title:'合同版本',width:150},//
{field:'HTSHRQ',title:'合同审核日期',width:150},
{field:'CJH',title:'车架号',width:150},
{field:'4SDMC',title:'4S店名称',width:150},
{field:'JCRQ',title:'交车日期',width:150},
{field:'YFQS',title:'预付期数',width:150},
{field:'SCHKR',title:'首次还款日',width:150},
{field:'HKJZR',title:'还款截止日',width:150},
{field:'SCHKJE',title:'首次还款金额',width:150},
{field:'QLL',title:'期利率',width:150},//
{field:'WYJ',title:'违约金',width:150},
{field:'FJ',title:'罚金',width:150},
{field:'YQQS',title:'逾期期数',width:150},
{field:'YQTS',title:'逾期天数',width:150},
{field:'YQJE',title:'逾期金额',width:150},
{field:'TQJQSJ',title:'提前结清时间',width:150},
{field:'TQJQJE',title:'提前结清金额',width:150},
{field:'YWLX',title:'业务类型',width:150},
{field:'BJ',title:'本金',width:150},
{field:'ZCFWF',title:'租车服务费',width:150},
{field:'JYFWF',title:'交易服务费',width:150}
		         ]]
	});
});
//导出
function exportData(){
	$("#divFrom").empty();
	
	     var JJ_START_DATE=$("input[name='JJ_START_DATE']").val();
   	     var JJ_END_DATE=$("input[name='JJ_END_DATE']").val();
   		 var YW_TYPE=$("select[name='YW_TYPE']").find("option:selected").val();//开户行
   		 var NAME=$("input[name='NAME']").val();
   		 var LEASE_CODE=$("input[name='LEASE_CODE']").val();
   		 var KHLY=$("select[name='KHLY']").find("option:selected").val();//客户来源
	     var PROCODE=$("input[name='PROCODE']").val();
	     
   		 var QY_START_DATE=$("input[name='QY_START_DATE']").val();
   	     var QY_END_DATE=$("input[name='QY_END_DATE']").val();
   	     
   	     var SHTG_START_DATE=$("input[name='SHTG_START_DATE']").val();
   	     var SHTG_END_DATE=$("input[name='SHTG_END_DATE']").val();
//	if(PLAN_START_DATE==null || PLAN_START_DATE ==""){
//		alert("应还日期必须选择");
//		return false; 
//	}
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
			strUrl=_basePath+"/newRentWrite/superTable!excelUpload.action?exportType="+exportType+"&JJ_START_DATE="+JJ_START_DATE+"&JJ_END_DATE="+JJ_END_DATE+"&YW_TYPE="+YW_TYPE+"&NAME="+NAME+"&LEASE_CODE="+LEASE_CODE+"&KHLY="+KHLY+"&PROCODE="+PROCODE+"&QY_START_DATE="+QY_START_DATE+"&QY_END_DATE="+QY_END_DATE+"&SHTG_START_DATE="+SHTG_START_DATE+"&SHTG_END_DATE="+SHTG_END_DATE;

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
				$("#divFrom").append("<form id='formSubmit' method='post' action='"+strUrl+"'></form>");
				$("#formSubmit").submit();
			}
		});
	
}




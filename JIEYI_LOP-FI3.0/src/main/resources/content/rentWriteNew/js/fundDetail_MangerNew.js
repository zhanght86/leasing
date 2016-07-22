$(document).ready(function(){
	$('#fundMangerForm').datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'ID',align:'center',width:100,title:'付款单号',align:'center'},
		          {field:'FI_FLAG',title:'核销方式',align:'center',width:150,formatter:function(value,rowData,rowIndex){
		        	  	if(value=='2')
		          		{
		          			return "租金-网银";
		          		}
		        	  	else if(value=='3')
		          		{
		          			return "租金-非网银";
		          		}
		          		else if(value=='4')
		          		{
		          			return "经销商垫付-网银";
		          		}
		          		else if(value=='5')
		          		{
		          			return "经销商垫付-非网银";
		          		}
		          		else if(value=='15')
		          		{
		          			return "租金-网银-不足额";
		          		}
		          		else if(value=='16')
		          		{
		          			return "经销商垫付-网银-虚拟";
		          		}
		          		else if(value=='17')
		          		{
		          			return "经销商垫付-非网银-虚拟";
		          		}
		          		else if(value=='18')
		          		{
		          			return "退款-虚拟";
		          		}
		          		else if(value=='19')
		          		{
		          			return "退款-租金池-虚拟";
		          		}
		          		else if(value=='6')
		          		{
		          			return "资金-非网银";
		          		}
		          		else if(value=='7')
		          		{
		          			return "保证金抵扣";
		          		}
		          		else if(value=='-1')
		          		{
		          			return "冲红";
		          		}  
	                  }},
	              {field:'COMPANY_NAMES',align:'center',width:200,title:'厂商'},
	              {field:'SUPPLIER_NAMES',align:'center',width:200,title:'经销商'},
	              {field:'PRODUCT_NAMES',align:'center',width:200,title:'品牌名称'},
	              {field:'D_CLIENT_NAME',align:'center',width:200,title:'客户名称'},
	              {field:'LEASE_CODE',align:'center',width:150,title:'融资租赁合同号'},
	              {field:'D_PAY_CODE',align:'center',width:150,title:'支付表号'},
	              {field:'D_PAY_PROJECT',align:'center',width:100,title:'来款项目'},
	              {field:'PERIOD',align:'center',width:100,title:'期次'},
	              {field:'D_PAY_MONEY',align:'center',width:100,title:'付款金额'},
	              {field:'D_RECEIVE_DATE',align:'center',width:100,title:'应收日期'},
	              {field:'FI_ACCOUNT_DATE',align:'center',width:100,title:'核销日期'},
	              {field:'FI_STATUS',title:'状态',align:'center',width:150,formatter:function(value,rowData,rowIndex){
		        	  	if(value=='0')
		          		{
		          			return "已申请未提交";
		          		}
		        	  	else if(value=='1')
		          		{
		          			return "虚拟核销";
		          		}
		        	  	else if(value=='2')
		          		{
		          			return "已核销";
		          		}
		          		else if(value=='3')
		          		{
		          			return "已提交核销失败";
		          		}
		          		else if(value=='4')
		          		{
		          			return "已驳回";
		          		}
		          		else if(value=='5')
		          		{
		          			return "已提交未核销";
		          		}
		          		else if(value=='6')
		          		{
		          			return "已退款";
		          		}
		          		else if(value=='7')
		          		{
		          			return "已冲红";
		          		}
	                  }},
/**	导出状态在有网银导出提交给银行后有用，先不启动，有网银核销时候在启动
               		{field:'UPLOADTYPE',title:'导出状态',width:100,formatter:function(value,rowData,rowIndex){
	                	  	var FI_FLAG=rowData.FI_FLAG;
			        	  	if(value=='0' && (FI_FLAG=='2' || FI_FLAG=='4' || FI_FLAG=='16'))
			          		{
			          			return "未导出";
			          		}
			        	  	else if(value=='1')
			          		{
			          			return "已导出";
			          		}
			          		
		          }},
		          */
	              {field:'BJ_MONEY_SF',align:'center',width:100,title:'本金'},
	              {field:'LX_MONEY_SF',align:'center',width:100,title:'利息'},
	              {field:'LXZZS_MONEY_SF',align:'center',width:100,title:'利息增值税'},
	              {field:'SXF_MONEY_SF',align:'center',width:100,title:'手续费'},
	              {field:'GLF_MONEY_SF',align:'center',width:100,title:'管理费'},
	              {field:'WYJ_MONEY_SF',align:'center',width:100,title:'违约金'},
	              {field:'FI_TO_THE_PAYEE',align:'center',width:200,title:'来款人'},
	              {field:'FI_TO_THE_ACCOUNT',align:'center',width:200,title:'来款账号'},
	              {field:'FI_REALITY_BANK',align:'center',width:200,title:'核销银行'}
		    ]]
	})         
	
});


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
}
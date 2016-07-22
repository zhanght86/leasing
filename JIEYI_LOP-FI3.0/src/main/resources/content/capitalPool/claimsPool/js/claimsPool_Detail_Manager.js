function dd(){
$("#claims_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'ClaimsPool!claimsPool_Detail_PageAjax.action',
		onSelect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onSelectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		columns:[[
		          	{field:'STATUS_NAME',title:'项目状态',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:100},
		          	{field:'HEAD_ID',title:'退款单号',width:100},
		          	{field:'CLAIMS_STATUS',title:'退款状态',width:70,formatter:function(value,rowData,rowIndex){
		        	  	if(value=='-1')
		          		{
		          			return "未申请";
		          		}
		        	  	else if(value=='0')
		          		{
		          			return "未提交";
		          		}
		          		else if(value=='1')
		          		{
		          			return "审核中";
		          		}
		          		else if(value=='2')
		          		{
		          			return "审核通过";
		          		}
		          		else if(value=='4')
		          		{
		          			return "已放款";
		          		}
	                  }},
	                {field:'CUSTNAME',title:'客户名称',width:150},
		          	{field:'COMPANY_NAME',title:'厂商',width:150},
		          	{field:'SUP_SHORTNAME',title:'供应商',width:150},
		          	{field:'PRODUCT_NAME',title:'租赁物',width:150},
		          	{field:'BASE_MONEY',title:'理赔款',width:150},
		          	{field:'CANUSE_MONEY',title:'剩余理赔款',width:100},
		          	{field:'PAYEE_NAME',title:'收款人',width:100},
		          	{field:'PAY_BANK_NAME',title:'收款方开户行',width:100},
		          	{field:'PAY_BANK_ADDRESS',title:'收款方开户行地址',width:100},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款方开户行卡号',width:100}
		         ]]
	});

}
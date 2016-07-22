$(document).ready(function(){

	$('#pageTable_NotCon').datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		url:'rentWrite!toMgAppCheckMgData.action',
		columns:[[
		          {field:'ck',align:'center',width:5,checkbox:true},
		          {field:'HEAD_ID',align:'center',width:10,title:'付款单号'},
		          {field:'APP_CREATE',align:'center',width:15,title:'申请人'},
		          {field:'FI_PAY_MONEY',align:'center',width:10,title:'付款金额'},
		          {field:'FI_PAY_DATE',align:'center',width:10,title:'计划还款日'},
		          {field:'FI_PROJECT_NUM',align:'center',width:5,title:'项目数量'},
		          {field:'FI_ACCOUNT_DATE',align:'center',width:10,editor:'text',title:'核销日期'},
		          {field:'FI_REALITY_BANK',align:'center',width:15,editor:'text',title:'核销银行'},
		          {field:'FI_FLAG',title:'付款方式',width:10,formatter:function(value,rowData,rowIndex){
		          		if(value=='3')
		          		{
		          			return "租金-非网银";
		          		}
		          		else if(value=='4')
		          		{
		          			return "供应商垫付-网银";
		          		}
		          		else if(value=='5')
		          		{
		          			return "供应商垫付-非网银";
		          		}
	                	  
	                  }},
		          {field:'FI_STATUS',align:'center',width:5,title:'确认状态'}
		          ]],
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
		},
		onExpandRow : function(index, row) {
			$('#ddv-' + row.HEAD_ID).datagrid({
                loadMsg:'',
				height:'100%',
				href : _basePath+"/rentWrite/rentWrite!getCheckedDetail.action?FUND_ID="+row.HEAD_ID,              	
			    onResize:function(){
                    $('#pageTable_NotCon').datagrid('fixDetailRowHeight',row.HEAD_ID);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#pageTable_NotCon').datagrid('fixDetailRowHeight',row.HEAD_ID);
                    },0);
                }
			});
			$('#pageTable_NotCon').datagrid('fixDetailRowHeight', row.HEAD_ID);
		}
	});
});


$(document).ready(function(){

	$("#pageTable_QueryM").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:5},
		          	{field:'HEAD_ID',title:'付款单号',width:15},
		          	{field:'FI_PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'FI_PAY_MONEY',title:'付款金额',width:15},
		          	{field:'FI_PAY_DATE',title:'计划还款日',width:15},
		          	{field:'FI_FLAG',title:'付款方式',width:20,formatter:function(value,rowData,rowIndex){
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
		          		else if(value=='2')
		          		{
		          			return "租金-网银";
		          		}
		          		else if(value=='15')
		          		{
		          			return "租金-网银-不足额";
		          		}
		          		else if(value=='16')
		          		{
		          			return "供应商垫付-网银-虚拟";
		          		}
		          		else if(value=='17')
		          		{
		          			return "供应商垫付-非网银-虚拟";
		          		}
	                	  
	                  }},
	                {field:'STATUS_FLAG',title:'确认状态',width:10},
		          	{field:'FI_STATUS',hidden:true}
		         ]],
		        view:detailview,
		 		detailFormatter : function(index, row) {
		 			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
		 		},
				onExpandRow : function(index, row) {
					jQuery.ajax({
						url:_basePath+"/rentWrite/rentWrite!getQueryFundDetailData.action?HEAD_ID="+row.HEAD_ID,  
						type:'post',
						dataType:'json',
					    success: function(json){
							var data = {flag:json.flag,total:json.data.length,rows:json.data};
							var pRowIndex = "ddv-"+row.HEAD_ID;
							$('#ddv-'+row.HEAD_ID).datagrid({
								fitColumns:true,
								columns:[[
								          	{field:'PRO_CODE',align:'center',width:10,title:'项目编号'},
				                            {field:'CUSTNAME',align:'center',width:15,title:'客户名称'},
				                            {field:'COMPANY_NAME',align:'center',width:15,title:'厂商'},
				                            {field:'SUP_NAME',align:'center',width:15,title:'供应商'},
				                            {field:'EQUIPMENINFOS',align:'center',width:15,title:'租赁物'},
				                            {field:'FI_PRO_NAME',align:'center',width:10,title:'款项名称'},
				                            {field:'BEGINNING_NUM',align:'center',width:5,title:'期次'},
				                            {field:'D_RECEIVE_DATE',align:'center',width:10,title:'计划收取日期'},
				                            {field:'BEGINNING_MONEY',align:'center',width:10,title:'本次应收金额'}
								         ]],
								onResize:function(){
			                        $('#pageTable_QueryM').datagrid('fixDetailRowHeight',index);
			                    },
			                    onLoadSuccess:function(){
			                        setTimeout(function(){
			                            $('#pageTable_QueryM').datagrid('fixDetailRowHeight',index);
			                        },0);
			                    }
							});
							 $('#pageTable_QueryM').datagrid('fixDetailRowHeight',index);
								$('#ddv-'+row.HEAD_ID).datagrid("loadData",data);
					}
				});
		 		}
	});
});


$(document).ready(function(){

	$("#pageTable_QueryM").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:1,align:'center'},
		          	{field:'HEAD_ID',title:'付款单号',width:3,align:'center'},
		          	{field:'FPH',title:'发票号',width:3,align:'center'},
		          	{field:'FI_CERTIFICATE',title:'凭证号',width:3,align:'center'},
		          	{field:'FI_PROJECT_NUM',title:'项目数量',width:3,align:'center'},
		          	{field:'FI_PAY_MONEY',title:'付款金额',width:3,align:'center'},
		          	{field:'FI_PAY_DATE',title:'计划还款日',width:3,align:'center'},
		          	{field:'FI_FLAG',title:'付款方式',align:'center',width:4,formatter:function(value,rowData,rowIndex){
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
	                {field:'STATUS_FLAG',title:'确认状态',width:2,align:'center'},
		          	{field:'FI_STATUS',hidden:true},
		          	{field:'TYPE',hidden:true}
		         ]],
		        view:detailview,
		 		detailFormatter : function(index, row) {
		 			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
		 		},
				onExpandRow : function(index, row) {
					jQuery.ajax({
						url:_basePath+"/rentWrite/rentWriteNew!getQueryFundDetailData.action?HEAD_ID="+row.HEAD_ID+"&TYPE="+row.TYPE,  
						type:'post',
						dataType:'json',
					    success: function(json){
							var data = {flag:json.flag,total:json.data.length,rows:json.data};
							var pRowIndex = "ddv-"+row.HEAD_ID;
							$('#ddv-'+row.HEAD_ID).datagrid({
								fitColumns:true,
								columns:[[
				                            {field:'CUSTNAME',align:'center',width:15,title:'客户名称'},
				                            {field:'COMPANY_NAME',align:'center',width:15,title:'厂商'},
				                            {field:'SUP_NAME',align:'center',width:15,title:'经销商'},
				                            {field:'LEASE_CODE',align:'center',width:15,title:'融资租赁合同号'},
											{field:'PAYLIST_CODE',align:'center',width:15,title:'支付表号'},
				                            {field:'EQUIPMENINFOS',align:'center',width:15,title:'品牌'},
				                            {field:'FI_PRO_NAME',align:'center',width:10,title:'款项名称'},
				                            {field:'BEGINNING_NUM',align:'center',width:5,title:'期次'},
				                            {field:'D_RECEIVE_DATE',align:'center',width:10,title:'计划收取日期'},
				                            {field:'BEGINNING_MONEY',align:'center',width:10,title:'本次应收金额'},
				                            {field:'D_RECEIVE_MONEY',align:'center',width:10,title:'本次实收金额'}
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


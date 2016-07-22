function dd(){
$("#claims_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		fitColumns:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'ClaimsPool!claimsPool_HeadView_PageAjax.action',
		columns:[[
		          	{field:'HEAD_ID',title:'退款单号',width:15},
		          	{field:'STATUS',title:'状态',width:15,formatter:function(value,rowData,rowIndex){
		          		if(value=='0')
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
	                 {field:'REALITY_DATE',title:'放款日期',width:20},
		          	{field:'COMP_NAME',title:'厂商',width:20},
		          	{field:'SUP_NAME',title:'供应商',width:20},
		          	{field:'PROJECT_NUM',title:'项目数量',width:10},
		          	{field:'PROJECT_MONEY',title:'退款金额',width:20},
		          	{field:'SUP_ID',hidden:true},
		          	{field:'COMP_ID',hidden:true}
		         ]],view:detailview,
			 		detailFormatter : function(index, row) {
			 			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
			 		},
					onExpandRow : function(index, row) {
						jQuery.ajax({
							url:_basePath+"/capitalPool/claimsPool/ClaimsPool!claimsPool_Detail.action?HEAD_ID="+row.HEAD_ID,  
							type:'post',
							dataType:'json',
						    success: function(json){
								var data = {flag:json.flag,total:json.data.length,rows:json.data};
								var pRowIndex = "ddv-"+row.HEAD_ID;
								$('#ddv-'+row.HEAD_ID).datagrid({
									fitColumns:true,
									columns:[[
												{field:'PRO_CODE',title:'项目编号',width:100},
												{field:'CUSTNAME',title:'客户名称',width:150},
												{field:'COMPANY_NAME',title:'厂商',width:150},
												{field:'SUP_SHORTNAME',title:'供应商',width:150},
												{field:'PRODUCT_NAME',title:'租赁物',width:150},
												{field:'BASE_MONEY',title:'理赔款',width:150},
												{field:'CANUSE_MONEY',title:'剩余理赔款',width:100},
												{field:'PAYEE_NAME',title:'收款人',width:100},
												{field:'PAY_BANK_NAME',title:'收款方开户行',width:100},
												{field:'PAY_BANK_ADDRESS',title:'收款方开户行地址',width:100},
												{field:'PAY_BANK_ACCOUNT',title:'收款方开户行卡号',width:100},
									         ]],
									onResize:function(){
				                        $('#claims_PageTable').datagrid('fixDetailRowHeight',index);
				                    },
				                    onLoadSuccess:function(){
				                        setTimeout(function(){
				                            $('#claims_PageTable').datagrid('fixDetailRowHeight',index);
				                        },0);
				                    }
								});
								 $('#claims_PageTable').datagrid('fixDetailRowHeight',index);
									$('#ddv-'+row.HEAD_ID).datagrid("loadData",data);
						}
					});
			 		}
	});

}
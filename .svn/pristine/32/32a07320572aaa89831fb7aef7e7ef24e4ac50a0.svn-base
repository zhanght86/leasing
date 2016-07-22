function dd(){
$("#claims_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		fitColumns:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'ClaimsPool!claimsHead_S_PageAjax.action',
		columns:[[
		          	{field:'aa',checkbox:true,width:10},
		          	{field:'HEAD_ID',title:'退款单号',width:15},
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


//新增
function createClaimsHead()
{
	 window.location.href =  _basePath+"/capitalPool/claimsPool/ClaimsPool!claimsPool_C_Manger.action";
}

//作废
function IS_Claims_Remove(){
	$("#divFrom").empty();
	var datagridList=$("#claims_PageTable").datagrid('getSelections');
	var HEAD_IDS="";
	for(var i = 0; i < datagridList.length; i++)
	{
		if(i==0){
			HEAD_IDS=datagridList[i].HEAD_ID;
		}
		else{
			HEAD_IDS=HEAD_IDS+","+datagridList[i].HEAD_ID;
		}
	}
	var url=_basePath+"/capitalPool/claimsPool/ClaimsPool!claimsPool_Remove.action?HEAD_IDS="+HEAD_IDS;
	$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
	$("#formSub").submit();
}

//提交
function IS_Claims_Submit(){
	$("#divFrom").empty();
	var datagridList=$("#claims_PageTable").datagrid('getSelections');
	var HEAD_IDS="";
	for(var i = 0; i < datagridList.length; i++)
	{
		if(i==0){
			HEAD_IDS=datagridList[i].HEAD_ID;
		}
		else{
			HEAD_IDS=HEAD_IDS+","+datagridList[i].HEAD_ID;
		}
	}
	var url=_basePath+"/capitalPool/claimsPool/ClaimsPool!JBPM_ClaimsPool.action?HEAD_IDS="+HEAD_IDS;
	$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
	$("#formSub").submit();
}
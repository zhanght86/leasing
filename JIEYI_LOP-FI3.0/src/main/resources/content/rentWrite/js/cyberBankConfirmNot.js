$(document).ready(function(){
	$('#uploadDialog').dialog({closed : true});
	
	$("#cyberBankConfirmNotPageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		url:'rentNotWrite!cyberConfirmNot_PageAjax.action',
		columns:[[
		          	{field:'ID',checkbox:true,width:50},
		          	{field:'ID_CARD_NO',title:'客户身份证号',width:150},
		          	{field:'ACCOUNT_NAME',title:'开户名',width:100},
		          	{field:'BANK_NAME',title:'开户银行',width:100},
		          	{field:'BANK_ACCOUNT',title:'客户账号',width:100},
		          	{field:'BEGINNING_MONEY',title:'金额',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:100,formatter:function(value,rowData,rowIndex){
	                	  return "<a href='#'>"+rowData.PRO_CODE+"</a>";
	                  }},
	                  {field:'PAYLIST_CODE',title:'还款计划',width:110}, 
		          	{field:'BEGINNING_NUM',title:'期次',width:50},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划还款日',width:100},
		          	{field:'BEGINNING_NAME',title:'类别',width:70},
		          	{field:'SUPPLIERS_NAME',title:'供应商',width:150},
		          	{field:'BEGINNING_STATUS',title:'状态',width:100},
		          	{field:'BEGINNING_FALSE_REASON',title:'失败原因',width:170},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUSTNAME',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'CUST_NAME',hidden:true}
		         ]]
	});
});

/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

function rollbackNot(){
	var datagridList=$('#cyberBankConfirmNotPageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续重置操作！");
		return false;
	}
	
	$.messager.confirm("提示","您确认要重置选中的数据？",function(flag){
		if(flag){
			var datagridList=$('#cyberBankConfirmNotPageTable').datagrid('getChecked');
			var IDS="";
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				if(i==0)
				{
					IDS=datagridList[i].ID;
				}
				else
				{
					IDS=IDS+","+datagridList[i].ID;
				}
				
			}
			$("#IDS").val(IDS);
			$("#formRoll").submit();
		}
	});
	
}

function uploadNotExcel(){
	var datagridList=$('#cyberBankConfirmNotPageTable').datagrid('getRows');
	if(datagridList.length == 0)
	{
		$.messager.alert("提示","没有待确认数据!");
	}
	var url = "rentNotWrite!uploadNotExcel.action";

	var bank_type = "";
	var fromDate = "";
	var fileN="";
	
	var filename = $('#uploadFile').val();
	fileN=filename;
	$('#uploadDialog').dialog({
		title : '上传回执',
		width : 400,
		height : 170,
		closed : true,
		cache : false,
		modal : true,
		closed : false,
		buttons : 
			[{
				text : '上传',
				iconCls : 'icon-up',
				handler : function()
					{
							$('#fileUploadForm').form('submit',
								{
                                    onSubmit: function()
									{
										
										bank_type = $('#bankFlag').combobox("getValue");
										fromDate = $('#fromDate').datebox("getValue");
										if($('#uploadFile').val().indexOf('.xls') == -1 &&  bank_type =="1")
										{
											$.messager.alert('提示','建设银行请上传.xls结尾的文件!','');
											return false;
										}
										if($('#uploadFile').val().indexOf('.txt') == -1 &&  bank_type =="2")
										{
											$.messager.alert('提示','民生银行请上传.txt结尾的文件!','');
											return false;
										}
										  
                                    },
                                    url:url+"?bankFlag="+$('#bankFlag').combobox("getValue")+"&fromDate="+$('#fromDate').datebox("getValue")+"&fileN="+$('#uploadFile').val(),
                					success:function(data)
									{
                					 	var data = eval('(' + data + ')');
                						$.messager.alert('',data.msg,'');
										$('#uploadDialog').dialog('close');
                					}
                            });
                        
        			}
			},
			{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function()
				{
					$('#uploadDialog').dialog('close');
				}
			}]
	});
}

function resultDataNot(){
	top.addTab("回执查询结果",_basePath+"/rentWrite/rentWrite!cyberBank_Result_Manger.action?FILE_STATUS=1");
}
$(function(){
	$("#table1").datagrid({
		url : url,
		columns : [[
			{field:'ID',checkbox:true,width:100},
			{field:'DLD',title:'供应商',width:100},
			{field:'PROJ_ID',title:'项目编号',width:100},
			{field:'CUST_NAME',title:'客户姓名',width:100},
			{field:'BANK_NAME',title:'开户银行',width:100},
			{field:'BANK_ACCOUNT',title:'银行卡号',width:100},
			{field:'ID_CARD_NO',title:'身份证号',width:100},
			{field:'CREATE_DATE',title:'立项日期',width:100},
			{field:'FIRST_MONEY',title:'首付款',width:100},
			{field:'OTHER_MONEY',title:'其他费用',width:100},
			{field:'MONEY',title:'应收金额',width:100},
			{field:'SQFKSS_DATE',title:'到账日期',width:100},
			{field:'FIRST_APP_STATE',title:'状态',width:100}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
		//singleSelect : true,
		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
		
		pageSize : 20,
		pageList : [10,20,50,100,200,300]
	});
	
	$('#form01').form({
        url:""
    });
	$('#fileUploadForm').form({
        url:""
    });
    $('#fileDialog').dialog({closed : true});

	
});

//查询
function se(){
	var searchParams = getFromData('#toolbar');
	$('#table1').datagrid('load',{"searchParams":searchParams});
}

//上传回执
function uploadExcel(){
	var searchParams = getFromData('#toolbar');
	var datagridList=$('#table1').datagrid('getRows');
	if(datagridList.length == 0)
	{
		$.messager.alert("提示","没有待确认数据!");
		return true;
	}
	//url
	var url = "FundEbank!uploadExcel.action";

	var bank_type = "";
	var fromDate = "";

	$('#fileUploadForm').form('clear');
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
				id : 'fileUploadLink',
				handler : function()
					{
							$('#fileUploadForm').form('submit',
								{
                                    onSubmit: function()
									{
										var filename = $('#uploadFile').val();
										bank_type = $('#bankFlag').combobox("getValue");
										var fromDate = $('#fromDate').datebox("getValue");
										if(fromDate == ""){
											$.messager.alert('提示','请选择到账日期!','');
											return false;
										}
										if(filename.indexOf('.xls') == -1 &&  bank_type =="1")
										{
											$.messager.alert('提示','建设银行请上传.xls结尾的文件!','');
											return false;
										}
										if(filename.indexOf('.txt') == -1 &&  bank_type =="")
										{
											$.messager.alert('提示','民生银行请上传.txt结尾的文件!','');
											return false;
										}
										$('#fileUploadLink').linkbutton('disable');  
                                    },
                                    url:url+"?bankFlag="+$('#bankFlag').combobox("getValue")+"&REALITY_DATE="+$('#fromDate').datebox("getValue"),
                					success:function(data)
									{
                					 	var data = eval('(' + data + ')');
                						if(data.flag)
										{	
                							//刷新表单
                							$('#table1').datagrid('load',{"searchParams":searchParams});
                						}
                						$.messager.alert('',data.msg,'');
                						$('#fileUploadLink').linkbutton('enable');
										$('#uploadDialog').dialog('close');
                					},
                					error : function(e) {
                						$.messager.alert(e.message);
                					}
                            });
                        
        			}
			},
			{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function()
				{
					$('#fileUploadLink').linkbutton('enable');
					$('#uploadDialog').dialog('close');
				}
			}]
	});
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

function rollback(){
	if(isNotChecked('驳回')) return;
	$.messager.confirm("提示","您确认要驳回数据吗？",function(flag){
		if(flag){
			//data
			var datagridList=$('#table1').datagrid('getChecked');
			var sqlData = [];	
			
			for(var i = 0; i < datagridList.length; i++)
			{
				sqlData.push("'"+datagridList[i].ID+"'");
			}
			//params
			var searchParams = getFromData('#toolbar');
			//url
			var url = "FundEbank!resetSignAccount.action";
			var choiceType = $(":checkbox[name$='ck_all']:checked").attr("name");
			
			//submit
			
			$('#form01').form('submit',{
		        url:url,
		        onSubmit: function(){
					//导出标识
					if($('#sqlData').length<=0){
						$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
					}
					$('#sqlData').val(sqlData.join(','));
					
					if(choiceType=="data_ck_all"){
						//选取类型
						if($('#choiceType').length<=0){
							$('#form01').append('<input name=\"choiceType\" id=\"choiceType\" type=\"hidden\" />');
						}
						$('#choiceType').val("allData");
					}
		        },
		        success : function(date) {
					date = $.parseJSON(date);
					if (date.flag) {
						se();
					} else {
						$.messager.alert("提示","驳回失败请重试!");
					}
				},
				error : function(e) {
					$.messager.alert(e.message);
				}
		    });
			//remove
			$('#sqlData').remove();
			$('#choiceType').remove();
		}
	});
	
}
//是否全选
function change(obj){
	$(obj).is(":checked") ? $('#table1').datagrid('selectAll') : $('#table1').datagrid('unselectAll') ;
}

$(function(){
	//页面全选
	$("input[name='ck_all']").click(function(){
		if($(this).attr("checked")){
			$("input[name='inverse_ck_all']").attr("checked",false);
			$("input[name='data_ck_all']").attr("checked",false);
			$('#table1').datagrid('selectAll');
		}else{
			$('#table1').datagrid('unselectAll');
		}
	});
	/*
	//反选功能
	$("input[name='inverse_ck_all']").click(function(){
			$("input[name='ck_all']").attr("checked",false);
			$("input[name='data_ck_all']").attr("checked",false);
			$("input[name='list']").each(function(){
				$(this).attr("checked",!$(this).attr("checked"));
			});
	});
	*/
	//数据全选 -- 页面配合选中
	$("input[name='data_ck_all']").click(function(){
		if($(this).attr("checked")){
			$("input[name='inverse_ck_all']").attr("checked",false);
			$("input[name='ck_all']").attr("checked",false);
			$('#table1').datagrid('selectAll');
		}else{
			$('#table1').datagrid('unselectAll');
		}
	});
});
//数据选中判断
function isNotChecked(val){
	var datagridList=$('#table1').datagrid('getChecked');
	if(datagridList.length == 0){
		$.messager.alert("提示","请选择要【"+val+"】的数据!");
		return true;
	}
	return false;
}
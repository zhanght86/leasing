$(function(){
	$("#table1").datagrid({
		url : 'BankSignMg!doSelectBankSignConfirmPageData.action',
		columns : [[
			{field:'BANK_ID',checkbox:true,width:100},
			{field:'SUP_SHORTNAME',title:'供应商',width:100},
			{field:'PRO_CODE',title:'项目编号',width:100},
			{field:'CUST_TYPE',title:'客户类型',width:100},
			{field:'CUST_NAME',title:'客户姓名',width:100},
			{field:'BANK_CUSTNAME',title:'持卡人',width:100},
			{field:'BANK_NAME',title:'开户银行',width:100},
			{field:'BANK_ACCOUNT',title:'银行卡号',width:100},
			{field:'ID_CARD_NO',title:'身份证号',width:100},
			{field:'CREATE_TIME',title:'立项日期',width:100}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'BANK_ID',
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
		alert("没有待确认数据...");
		return ;
	}
	var bank_flag = $("#bank_flag").find(":selected").val();
	var bank_flag1 = bank_flag;
	$("#bank_flag2").val(bank_flag);
	if(bank_flag='CCB'){
		bank_flag='中国建设银行';
	}else{
		bank_flag='中国民生银行';
	}
	if(!confirm('确定要签约【'+bank_flag+"】？")){
		return ;
	};
	
	$("#bank_flag1").val(bank_flag);
	//url
	var url = "BankSignMg!doUploadConfirmExcelInfo.action";
	$('#uploadDialog').dialog({
		title : '上传回执',
		width : 450,
		height : 250,
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
						var flag = false;
						$.messager.confirm('Confirm','确定上传文件：'+$('#uploadFile').val(),function(r)
						{
							if(!r)
							{
								return;
							}
							$('#fileUploadForm').form('submit',
								{
                                    url:url+"?bank_flag="+bank_flag1,
                                    onSubmit: function()
									{
										var filename = $('#uploadFile').val();
										if(filename.indexOf('xls') == -1)
										{
											$.messager.alert('提示','请勿上传非xls结尾的文件','');
											return false;
										}
                                    },
                					success:function(data)
									{
                					 	var data = eval('(' + data + ')');
                						if(data.flag)
										{	
                							//刷新表单
                							$('#table1').datagrid('load',{"searchParams":searchParams});
                						}
                						$.messager.alert('',data.msg,'');
										$('#uploadDialog').dialog('close');
                					}
                            });
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
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

function rollback(){
	if(isNotChecked('驳回')) return;
	
	//data
	var datagridList=$('#table1').datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].BANK_ID+"'");
	}
	//params
	var searchParams = getFromData('#toolbar');
	//url
	var url = "BankSignMg!doResetBankSignApply.action";
	var choiceType = $(":checkbox[name$='ck_all']:checked").attr("name");
	
	//submit
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			if($('#searchParams').length<=0){
				$('#form01').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
			}
			$('#searchParams').val(searchParams);
			
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
		success:function(data){
		 	var data = eval('(' + data + ')');
			if(data.flag){	
				//刷新表单
				var searchParams = getFromData('#toolbar');
			}
			alert(data.msg);
			$('#table1').datagrid('load',{"searchParams":searchParams});
		}
    });
	//remove
	$('#sqlData').remove();
	$('#searchParams').remove();
	$('#choiceType').remove();
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
		alert("请选择要【"+val+"】的数据...");
		return true;
	}
	return false;
}
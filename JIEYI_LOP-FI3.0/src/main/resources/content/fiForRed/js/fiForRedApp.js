$(function(){
	$("#fiForRedTable").datagrid({
		pagination:true,
		rownumbers:true,
		fit:true,
		fitColumns:true,
		url:_basePath+'/fiForRed/FiForRed!doShowMgFiForRedApp.action',
		toolbar:'#fiForRedForm',
		onSelect:function(rowIndex, rowData){
			var D_PAY_PROJECT=rowData.D_PAY_PROJECT;
			var PERIOD=rowData.PERIOD;
			var rows=jQuery('#fiForRedTable').datagrid('getSelections');
			if(D_PAY_PROJECT!='违约金'&&PERIOD!=''&&PERIOD!=null){
				var PAY_CODE=rowData.D_PAY_CODE;
				$.ajax({
					type:'post',
					url:_basePath+"/fiForRed/FiForRed!checkDunHX.action",
					data:"PAY_CODE="+PAY_CODE+"&PERIOD="+PERIOD,
					dataType:"json",
					success:function(json){
						if(json.flag){
							var flag=false;
							for(var i=0;i<rows.length;i++){
								var wyj=rows[i].D_PAY_PROJECT;
								var num=rows[i].PERIOD;
								if(wyj=='违约金'&&num==PERIOD){
									flag=true;
									break;
								}
							}
							if(!flag){
								$.messager.alert("提示","该期存在已核销的违约金，请先处理违约金的冲红作废");
								jQuery('#fiForRedTable').datagrid('unselectRow',rowIndex);
							}
						}
					}
				});
			}
		}
	});
});
function seFiForRedTable(){
	$("#fiForRedTable").datagrid('load',{"param":getFromData("#fiForRedForm")});
}

function cleanFiForRedTable(){
	$("#PROJECT_CODE").val('');
	$("#RECEIVE_DATE_BEGIN").datebox('setValue','');
	$("#RECEIVE_DATE_END").datebox('setValue','');
	$("#PERIOD").val('');
	$("#SUPPLIER_NAME").val('');
	$("#REALITY_DATE_BEGIN").datebox('setValue','');
	$("#REALITY_DATE_END").datebox('setValue','');
}

function FiForRedApp(TYPE){
	var rows=$("#fiForRedTable").datagrid("getSelections");
	var sta=false;
	for(var i=0; i<rows.length; i++){
		if(rows[i].PAY_STATUS!='0'&&rows[i].PAY_STATUS!='3'&&rows[i].PAY_STATUS!='8'){
			sta=true;
			break;
		}
	}
	if(sta){
		$.messager.alert("提示","该批还款计划中有处于"+rows[i].PAY_STATUS_NAME+"的，不能作废冲红费用");
		return;
	}
	
	if(rows.length == 0){
		$.messager.alert("提示","请选择要申请的冲红作废数据！");
		return ;
	}else{
		if(TYPE=='1'){
			$.messager.confirm("提示","您确定要申请冲红",function(r){
				if(r){
					var array=new Array();
					for(var i=0; i<rows.length; i++){
						var temp={};
						temp.D_PROJECT_CODE=rows[i].D_PROJECT_CODE;
						temp.D_PAY_CODE=rows[i].D_PAY_CODE;
						temp.CLIENT_NAME=rows[i].CLIENT_NAME;
						temp.BANK_CUSTNAME=rows[i].BANK_CUSTNAME;
						temp.SUPPLIER_NAME=rows[i].SUPPLIER_NAME;
						temp.FI_FLAG=rows[i].FI_FLAG;
						temp.D_PAY_PROJECT=rows[i].D_PAY_PROJECT;
						temp.D_RECEIVE_MONEY=rows[i].D_RECEIVE_MONEY;
						temp.PERIOD=rows[i].PERIOD;
						temp.D_RECEIVE_DATE=rows[i].D_RECEIVE_DATE;
						temp.D_REALITY_DATE=rows[i].D_REALITY_DATE;
						temp.STATUS=rows[i].STATUS;
						temp.FI_DETAIL_ID=rows[i].F_DETAIL_ID;
						temp.FI_FUND_ID=rows[i].D_FUND_ID;
						temp.TYPE=TYPE;
						array.push(temp);
					}
					
					$.ajax({
						type:'post',
						url:_basePath+"/fiForRed/FiForRed!doAddFiForRedApp.action",
						data:"param="+JSON.stringify(array),
						dataType:'json',
						success:function(json){
							if(json.flag){
								seFiForRedTable();
							}else{
								$.messager.alert("提示",json.msg);
							}
						},
						error:function(){
							$.messager.alert("提示","网络问题，请联系网络管理员");
						}
					});
				}
			});
		}else{
			$.messager.confirm("提示","您确定要申请作废",function(r){
				if(r){
					var array=new Array();
					for(var i=0; i<rows.length; i++){
						var temp={};
						temp.D_PROJECT_CODE=rows[i].D_PROJECT_CODE;
						temp.D_PAY_CODE=rows[i].D_PAY_CODE;
						temp.CLIENT_NAME=rows[i].CLIENT_NAME;
						temp.BANK_CUSTNAME=rows[i].BANK_CUSTNAME;
						temp.SUPPLIER_NAME=rows[i].SUPPLIER_NAME;
						temp.FI_FLAG=rows[i].FI_FLAG;
						temp.D_PAY_PROJECT=rows[i].D_PAY_PROJECT;
						temp.D_RECEIVE_MONEY=rows[i].D_RECEIVE_MONEY;
						temp.PERIOD=rows[i].PERIOD;
						temp.D_RECEIVE_DATE=rows[i].D_RECEIVE_DATE;
						temp.D_REALITY_DATE=rows[i].D_REALITY_DATE;
						temp.STATUS=rows[i].STATUS;
						temp.FI_DETAIL_ID=rows[i].F_DETAIL_ID;
						temp.FI_FUND_ID=rows[i].D_FUND_ID;
						temp.TYPE=TYPE;
						array.push(temp);
					}
					
					$.ajax({
						type:'post',
						url:_basePath+"/fiForRed/FiForRed!doAddFiForRedApp_ZF.action",
						data:"param="+JSON.stringify(array),
						dataType:'json',
						success:function(json){
							if(json.flag){
								seFiForRedTable();
							}else{
								$.messager.alert("提示",json.msg);
							}
						},
						error:function(){
							$.messager.alert("提示","网络问题，请联系网络管理员");
						}
					});
				}
			});
		}
	}
}

function seFiForRedConfirmTable(){
	$("#fiForRedConfirmTable").datagrid('load',{"param":getFromData("#fiForRedConfirmForm")});
}

function cleanFiForRedConfirmTable(){
	$("#PROJECT_CODE").val('');
	$("#RECEIVE_DATE_BEGIN").datebox('setValue','');
	$("#RECEIVE_DATE_END").datebox('setValue','');
	$("#PERIOD").val('');
	$("#SUPPLIER_NAME").val('');
	$("#REALITY_DATE_BEGIN").datebox('setValue','');
	$("#REALITY_DATE_END").datebox('setValue','');
}

function FiForRedConfirmPass(){

	var rows=$("#fiForRedConfirmTable").datagrid("getSelections");
	if(rows.length == 0){
		$.messager.alert("提示","请选择要冲红作废的数据！");
		return ;
	}
	$.messager.confirm("提示","确认冲红作废数据吗？",function(r){
		if(r){
			var TYPE;
			var array=new Array();
			for(var i=0; i<rows.length; i++){
				var temp={};
		        temp.ID=rows[i].ID;
				temp.PROJECT_CODE=rows[i].D_PROJECT_CODE;
				temp.PAYLIST_CODE=rows[i].D_PAY_CODE;
				temp.ITEM_NAME=rows[i].D_PAY_PROJECT;
				temp.RECEIVE_MONEY=rows[i].D_RECEIVE_MONEY;
				temp.PERIOD=rows[i].PERIOD;
				temp.RECEIVE_DATE=rows[i].D_RECEIVE_DATE;
				temp.REALITY_DATE=rows[i].D_REALITY_DATE;
				TYPE=rows[i].TYPE;
				temp.FI_FUND_ID=rows[i].FI_FUND_ID;
				array.push(temp);
			}
			$.ajax({
				type:'post',
				url:_basePath+"/fiForRed/FiForRed!doUpdateFiForRedConfirmPass.action",
				data:"TYPE="+TYPE+"&param="+JSON.stringify(array),
				dataType:'json',
				success:function(json){
					if(json.flag){
						seFiForRedConfirmTable();
					}else{
						$.messager.alert("提示",json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络问题，请联系网络管理员");
				}
			});
		}
	});
}

function FiForRedConfirmNotPass(){
	var rows=$("#fiForRedConfirmTable").datagrid("getSelections");
	if(rows.length == 0){
		$.messager.alert("提示","请选择要驳回的冲红作废数据！");
		return ;
	}
	$.messager.confirm("提示","确认驳回冲红作废数据吗？",function(r){
		if(r){
			var array=new Array();
			
			for(var i=0; i<rows.length; i++){
				var temp={};
		        temp.ID=rows[i].ID;//fi_red表id
				temp.FI_DETAIL_ID=rows[i].FI_DETAIL_ID;//fi_fund_detail表id
				temp.FI_FUND_ID=rows[i].FI_FUND_ID;//fi_fund_head表id
				array.push(temp);
			}
			$.ajax({
				type:'post',
				url:_basePath+"/fiForRed/FiForRed!doUpdateFiForRedConfirmNotPass.action",
				data:"param="+JSON.stringify(array),
				dataType:'json',
				success:function(json){
					if(json.flag){
						seFiForRedConfirmTable();
					}else{
						$.messager.alert("提示",json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络问题，请联系网络管理员");
				}
			});
		}
	});
}


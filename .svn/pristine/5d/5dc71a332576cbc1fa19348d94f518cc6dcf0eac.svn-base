$(document).ready(function(){

	$('#pageTable_NotCon').datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		url:'rentWriteVinual!toMgAppCheckMgData.action',
		columns:[[
		          {field:'ck',align:'center',width:5,checkbox:true},
		          {field:'HEAD_ID',align:'center',width:10,title:'付款单号'},
		          {field:'FI_APP_NAME',align:'center',width:15,title:'申请人'},
		          {field:'FI_PAY_MONEY',align:'center',width:10,title:'付款金额'},
		          {field:'FI_PAY_DATE',align:'center',width:10,title:'计划还款日'},
		          {field:'FI_PROJECT_NUM',align:'center',width:5,title:'项目数量'},
		          {field:'FI_ACCOUNT_DATE',align:'center',width:10,editor:'text',title:'核销日期'},
		          {field:'FI_REALITY_BANK',align:'center',width:15,editor:'text',title:'核销银行'},
		          {field:'FI_FLAG',title:'付款方式',align:'center',width:10,formatter:function(value,rowData,rowIndex){
		          		if(value=='16')
		          		{
		          			return "经销商垫付-网银-虚拟";
		          		}
		          		else if(value=='17')
		          		{
		          			return "经销商垫付-非网银-虚拟";
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
				href : _basePath+"/rentWrite/rentWriteVinual!getCheckedDetail.action?FUND_ID="+row.HEAD_ID,              	
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

function toCommit(obj){
	var tr=$(obj).parent().parent().parent();
	if($(tr).find("#FI_TO_THE_PAYEE").val()==null || $(tr).find("#FI_TO_THE_PAYEE").val()==undefined || $(tr).find("#FI_TO_THE_PAYEE").val().length<=0){
		return $.messager.alert('警告','请填写来款人');
	}
	var FI_REALITY_BANK = $(tr).find("select[name='FI_REALITY_BANK']").find("option:selected").val();
	if(FI_REALITY_BANK.length<=0){
		return $.messager.alert('警告','请填写核销银行');
	}
	if($(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==null || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==undefined  || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue').length<=0){
		return $.messager.alert('警告','请填写核销时间');
	}
	var data = {};
	data["getBaseData"] = getBaseData(tr);
	
	if($(tr).find("#FI_REALITY_ACCOUNT").val()==null || $(tr).find("#FI_REALITY_ACCOUNT").val()==undefined){
		return $.messager.alert('警告','请填写本次实付！');
	}else{
		if(parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())<parseFloat($(tr).find("#fi_pay_money_").val())){
			var totalMoney = parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())+parseFloat($(tr).find("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($(tr).find("#fi_pay_money_").val())){
				return $.messager.alert('警告','您选择金额大于本次应收核销金额，请重新选择金额！');
			}else{
				data["getPoolData"] = getPoolData(tr);	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/rentWrite/rentWriteVinual!doCommitHXSheet.action",
	    type:'post',
	    data:'data='+encodeURI(JSON.stringify(data)),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");	
			//页面刷新
			 window.location.href = _basePath+"/rentWrite/rentWriteVinual!toMgAppCheckMg.action";
		 }else{
			 $.messager.alert("提示","保存失败！！");			 
		 }
		
		}
	});
}

function toCommitHexiao(obj)
{
	var tr=$(obj).parent().parent().parent();
	if($(tr).find("#FI_TO_THE_PAYEE").val()==null || $(tr).find("#FI_TO_THE_PAYEE").val()==undefined || $(tr).find("#FI_TO_THE_PAYEE").val().length<=0){
		return $.messager.alert('警告','请填写来款人');
	}
	var FI_REALITY_BANK = $(tr).find("select[name='FI_REALITY_BANK']").find("option:selected").val();
	if(FI_REALITY_BANK.length<=0){
		return $.messager.alert('警告','请填写核销银行');
	}
	if($(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==null || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==undefined  || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue').length<=0){
		return $.messager.alert('警告','请填写核销时间');
	}
	var data = {};
	data["getBaseData"] = getBaseData(tr);
	
	if($(tr).find("#FI_REALITY_ACCOUNT").val()==null || $(tr).find("#FI_REALITY_ACCOUNT").val()==undefined){
		return $.messager.alert('警告','请填写本次实付！');
	}else{
		if(parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())<parseFloat($(tr).find("#fi_pay_money_").val())){
			var totalMoney = parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())+parseFloat($(tr).find("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($(tr).find("#fi_pay_money_").val())){
				return $.messager.alert('警告','您选择金额大于本次应收核销金额，请重新选择金额！');
			}else{
				data["getPoolData"] = getPoolData(tr);	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/rentWrite/rentWriteVinual!doCommitHXSheetHexiao.action",
	    type:'post',
	    data:'data='+encodeURI(JSON.stringify(data)),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","核销成功！！");	
			//页面刷新
			 window.location.href = _basePath+"/rentWrite/rentWriteVinual!toMgAppCheckMg.action";
		 }else{
			 $.messager.alert("提示","核销失败！！");			 
		 }
		
		}
	});
}

function getPoolData(obj){
	var getPoolData = [];
	$(obj).find("#pool_tab tr").each(function(){
		var temp = {};
		temp.POOL_TYPE= $(this).find("input[name=POOLID]").val();//租金池类别
		temp.POOLUSERMONEY = $(this).find("input[name=POOLUSERMONEY]").val();//可用余额
		temp.dichong_money = $(this).find("input[name=dichong_money]").val();//所选冲抵款合计
		getPoolData.push(temp);
	});
	return getPoolData;
}

function doRegist(){
	if(getDetailData()==''){
		$.messager.alert("警告","请选择需驳回数据！！");
	}else{
		$.messager.confirm('提示','确定要驳回吗?', function(r) {
			if (r) {
				var data = {};
				data["getDetailData"] = getDetailData();
				
				$.ajax({		
					url:_basePath+"/rentWrite/rentWriteVinual!doReject.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					 if(json.flag == true){
						 $.messager.alert("提示","驳回成功！！");				
					 }else{
						 $.messager.alert("提示","驳回失败！！");
					 }
					//页面刷新
					window.location.href = _basePath+"/rentWrite/rentWriteVinual!toMgAppCheckMg.action";
					}
				});
			}		
		});
	}
}

function toCommintFund(){
	var detailData = $("#pageTable_NotCon").datagrid('getSelections');//获取选中信息
	if(detailData.length<=0){
		return $.messager.alert('警告','请填选择提交数据');
	}else{
		for(var i = 0; i<detailData.length; i++) {
			
			var HEAD_ID = detailData[i].HEAD_ID;//付款单号
			var FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
			var FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
			if(FI_ACCOUNT_DATE==null || FI_ACCOUNT_DATE=='undefined' || FI_ACCOUNT_DATE.length <=0)
			{
				alert("收款单号:"+HEAD_ID+"没有保存到账日期，不能核销！");
				return false;
			}
			
			if(FI_REALITY_BANK==null || FI_REALITY_BANK=='undefined' || FI_REALITY_BANK.length<=0)
			{
				alert("收款单号:"+HEAD_ID+"没有保存核销银行，不能核销！");
				return false;
			}
		}
		
		$.messager.confirm('提示','确定核销吗?',function(r) {
			if (r) {
					var data = {};
					data["getDetailData"] = getDetailData();
					$("#confirBut").linkbutton("disable");
					$.ajax( {
							url : _basePath+ "/rentWrite/rentWriteVinual!doCheckedFund.action",
					       type : 'post',
						   data : 'data=' + JSON.stringify(data),
					   dataType : 'json',
						success : function(json) {
							if (json.flag == true) {
								$.messager.alert("提示",
										"核销成功！！");
								//页面刷新
								window.location.href = _basePath
										+ "/rentWrite/rentWriteVinual!toMgAppCheckMg.action";		
							} else {
								$.messager.alert("提示",
										"核销失败！！");
							}
										
							}
					});
			}
		});
		
	}
}

function getBaseData(obj){
	var getBaseData = {};
	var FI_ACCOUNT_DATE = $(obj).find("#FI_ACCOUNT_DATE").datebox('getValue');
	var FI_REALITY_BANK = $(obj).find("select[name='FI_REALITY_BANK']").find("option:selected").val();
	var FI_TAGE = $(obj).find("select[name='FI_TAGE']").find("option:selected").val();
	var FI_TAGE_ID=$(obj).find("select[name='FI_TAGE_ID']").find("option:selected").val();
	var FI_TAGE_NAME=$(obj).find("select[name='FI_TAGE_ID']").find("option:selected").attr("FI_TAGE_NAME");
	getBaseData["FI_ACCOUNT_DATE"] = FI_ACCOUNT_DATE;
	getBaseData["FI_REALITY_BANK"] = FI_REALITY_BANK;
	getBaseData["FI_TAGE"] = FI_TAGE;
	getBaseData["FUND_ID"] = $(obj).find("#FUND_ID").val();
	getBaseData["FI_TO_THE_PAYEE"] = $(obj).find("#FI_TO_THE_PAYEE").val();
	getBaseData["FI_REALITY_MONEY"] = $(obj).find("#FI_PAY_MONEY").val();
	getBaseData["FI_REALITY_ACCOUNT"] = $(obj).find("#FI_REALITY_ACCOUNT").val();
	getBaseData["FI_TAGE_ID"] = FI_TAGE_ID;
	getBaseData["FI_TAGE_NAME"] = FI_TAGE_NAME;
	getBaseData["FI_PAY_BANK"] = $(obj).find("#FI_PAY_BANK").val();
	getBaseData["FI_PAY_YUE"] = $(obj).find("#FI_PAY_YUE").val();
	var FUND_ID=$(obj).find("#FUND_ID").val();
	getBaseData["MONEYORDERSAVE"] = $(obj).find("#moneyOrderSave"+FUND_ID).val();

	return getBaseData;
}

function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable_NotCon").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.HEAD_ID = detailData[i].HEAD_ID;//付款单号
		temp.FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
		temp.FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
		temp.FI_REMARK = detailData[i].FI_REMARK;//驳回原因
		getDetailData.push(temp);
	}
	return getDetailData;
}


function toChange(obj){
	var tr=$(obj).parent().parent();
	var trP=$(obj).parent().parent().parent();
	var FI_REALITY_ACCOUNT = parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val());//本次实付
	var fi_pay_money = parseFloat($(trP).find("#fi_pay_money_").val());//本次应收核销
	var unusedAmount = parseFloat(0);
	
	//当本次实付>本次应收金额时， 计算本次来款余额
	if(FI_REALITY_ACCOUNT>=fi_pay_money){
		unusedAmount = FI_REALITY_ACCOUNT - fi_pay_money;//本次来款余额
		$(trP).find("#poolTab").attr("style", "display:none");
		$(trP).find("a[name='saveHref']").attr("style", "display:block");
		$(trP).find("a[name='heXsHref']").attr("style", "display:none");
	}else if(FI_REALITY_ACCOUNT<fi_pay_money){
		//本次来款余额
		unusedAmount=0;		
		$(trP).find("#poolTab").attr("style", "display:block");
		$(trP).find("a[name='saveHref']").attr("style", "display:none");
		$(trP).find("a[name='heXsHref']").attr("style", "display:block");
	}
	
	$(tr).find("#FI_PAY_YUE").val(fomatFloat(unusedAmount,2));
}

function updateMoneyOrder(obj){
	 $("#dlgUp"+obj).dialog("open");
}

function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);       
} 

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}
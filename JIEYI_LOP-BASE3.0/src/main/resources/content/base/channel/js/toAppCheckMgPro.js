var ii = 0 ;
var sup_id = "";
$(function() {
	$('#pageTable').datagrid({
		url:_basePath+"/base/channel/Channel!toMgAppCheckProData.action?SUP_ID"+$("#SUP_ID").val()+"&APP_ID="
		+$("#APP_ID").val()+"&ASSURE_PAYMENT_MODE="+"&SUP_NAME="+$("#SUP_NAME").val()+"&APPLY_ID="+$("#APPLY_ID").val(),//
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		onClickCell:onClickCell,
		singleSelect:true,
		columns:[[
		          {field:'ID',align:'center',width:30,checkbox:true},
		          {field:'SUP_ID',align:'center',width:50,title:'供应商标识'},
		          {field:'SUP_NAME',align:'center',width:50,title:'供应商简称'},
		          {field:'ASSURE_AMOUNT',align:'center',width:50,title:'现有担保额度'},
		          {field:'FUND_NAME',align:'center',width:50,title:'担保类型'},
		          {field:'ASSURE_PAYMENT_MODE',align:'center',width:50,title:'付款方式'},
		          {field:'PLAN_AMT',align:'center',width:50,title:'计划付款金额'},
		          {field:'EXPAND_MULTIPLE',align:'center',width:50,title:'放大系数'},
		          {field:'ASSURE_AMOUNT_HIST',align:'center',width:50,title:'对应担保金额'}
		          ]],
        onSelect:function(index, row){
			sup_id = row.SUP_ID;
			$("#FI_REALITY_ACCOUNT").val(formatMoney(row.PLAN_AMT));//计划金额
			$("#FI_SUPPLIERS").val(row.SUP_NAME);
			$("#FI_REALITY_MONEY").val(row.PLAN_AMT);//应核销金额
			$("#fi_pay_money_").val(formatMoney(row.PLAN_AMT));//累计金额
			$("#FI_PAY_YUE").val("0");//余款
			$("#fi_pay_money_dk").val("0");//应冲抵
			$("#fi_pay_money_").removeClass("font_red");
			$("#fi_pay_money_").addClass("font_blue");
			$('#pool_tab').html("");
		},
		onLoadSuccess:function(){
			$('#pool_tab').html("");
		}
//		view : detailview,
//		detailFormatter : function(index, row) {
//			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
//		},
//		onExpandRow : function(index, row) {
//			$('#ddv-' + index).datagrid({
//                loadMsg:'',
//				height:'320',//列表展开显示错乱问题  100% 改为固定360 
//				href : _basePath+"/base/channel/Channel!getCheckedDetail.action?FUND_NUM="+ii+"&ID="+row.ID+"&SUP_ID="+row.SUP_ID+"&SUP_NAME="+row.SUP_NAME, 
//			    onResize:function(){
//                    $('#pageTable').datagrid('fixDetailRowHeight',index);
//                },
//                onLoadSuccess:function(){
//                    setTimeout(function(){
//                        $('#pageTable').datagrid('fixDetailRowHeight',index);
//                    },0);
//                }
//			});
//			++ii;
//			$('#pageTable').datagrid('fixDetailRowHeight', index);
//		}
	});
});

/**
 * 本次实付在修改时， 本次实付金额要与本次应收核销进行对比。
 * 
 * 当本次实付金额大于本次应收金额时： 计算本次来款余额。 即为：本次实付金额-本次应收金额
 * 
 * 当本次实付金额小于本次应收金额时： 本次实付金额+余款池的总金额 = 本次应收金额
 * 
 * @return
 */
function toChange(){
	if(sup_id == ''){
		return;
	}
	var FI_REALITY_MONEY = parseFloat($("#FI_REALITY_MONEY").val().replace(',',''));//本次应核销金额 计划
	var FI_REALITY_ACCOUNT = parseFloat($("#FI_REALITY_ACCOUNT").val().replace(',',''));//本次来款实付
	var realityMoneyTotal = parseFloat($("#realityMoneyTotal") != null ? $("#realityMoneyTotal").val() : 0);//冲抵金额
	var fi_pay_money = parseFloat($("#fi_pay_money_").val().replace(',',''));//本次核销合计 
	var fi_pay_money_dk = parseFloat($("#fi_pay_money_dk").val().replace(',',''));//
	
	var unusedAmount = parseFloat(0);
	//当本次实付>本次应收金额时， 计算本次来款余额
	if(FI_REALITY_ACCOUNT>=FI_REALITY_MONEY){
		//来款多
		unusedAmount = FI_REALITY_ACCOUNT - FI_REALITY_MONEY;//本次来款余额
		$("#poolTab").hide();
//		$("a[name='saveHref']").show();
//		$("a[name='HeXaioFirst']").hide();
		$("#FI_PAY_YUE").val("");
		$("#FI_PAY_YUE").val(formatMoney(unusedAmount));
		$("#FI_REALITY_ACCOUNT").val(formatMoney(FI_REALITY_ACCOUNT));//计划金额
		$("#fi_pay_money_").val(formatMoney(FI_REALITY_MONEY));//累计金额
		$("#fi_pay_money_dk").val("0");

		$("#fi_pay_money_").removeClass("font_red");
		$("#fi_pay_money_").addClass("font_blue");
	}else if(FI_REALITY_ACCOUNT<fi_pay_money){

		$("#FI_PAY_YUE").val("0");
		$("#FI_REALITY_ACCOUNT").val(formatMoney(FI_REALITY_ACCOUNT));//本次来款实付
		$("#fi_pay_money_").val(formatMoney(parseFloat(FI_REALITY_ACCOUNT)));//累计金额
		$("#fi_pay_money_dk").val(formatMoney(FI_REALITY_MONEY-FI_REALITY_ACCOUNT));
		

		$("#fi_pay_money_").addClass("font_red");
		$("#fi_pay_money_").removeClass("font_blue");
		
		//用资金池余款
		//本次来款余额
		unusedAmount=0;		
		$("#poolTab").show();
		$.ajax({		
			url:_basePath+"/base/channel/Channel!getPoolInfo.action",
		    type:'post',
		    data:'SUP_ID='+sup_id,
		    dataType:'json',
		    success:function(json){
			 if(json.flag == true){
				    $("#FI_PAY_YUE").val("");
					$("#FI_PAY_YUE").val(unusedAmount);
					
					$("#pool_tab").html("<tr bgcolor='#f4f4f4' align='center'>"+
											"<td width='15%' >项目备注</td>"+
											"<td width='20%'>可用余额合计</td>"+
											"<td width='20%'>所选冲抵款合计</td>"+
										"</tr>");
					
				 var data = json.data;
				 var sum_amt = 0.00;
				 for(var i = 0; i < data.length; i++){
					 $("#pool_tab").append(
							 "<tr align='center'>"+
								"<td align='right'>" +
								"	<input type='text' name='POOLNAME' id='POOLNAME' value='"+data[i].POOLNAME+"' readonly/>"+
								"	<input type='hidden' name='POOLID' id='POOLID' value='"+data[i].POOLID+"' readonly/>"+
								"</td>"+
								"<td align='right'><input type='text' name='POOLUSERMONEY' id='POOLUSERMONEY' value='"+data[i].POOLUSERMONEY+"' readonly/></td>"+
								"<td align='right'><input type='text' name='dichong_money' id='dichong_money' value='0' onchange='toChangePool(this);' /></td>"+
							"</tr>");
					 sum_amt += data[i].POOLUSERMONEY;
				 }
//				 console.info(json.data[0].POOLNAME);
				 $("#pool_tab").append("<tr>"+
							"<td align='right' style='font-weight:bold'>合计：</td>"+
							"<td align='right' style='font-weight:bold'><input type='text' name='spareMoneyTotal' value='"+sum_amt+"' readonly></td>"+
							"<td align='right' style='font-weight:bold'><input type='text' name='realityMoneyTotal' id='realityMoneyTotal' value='0' readonly></td>"+
                        "</tr>");
			 }else{
			 }
			}
		});
	}
}


/**
 * 计算应/实收金额总数
 * @return
 */
function toChangePool(obj){
	
	//1.判断输入金额要小于等于可使用金额
	var tr=$(obj).parent().parent();
	var money1=$(tr).find("input[name='POOLUSERMONEY']").val();//可使用金额
	var money2=$(tr).find("input[name='dichong_money']").val();//想抵充金额
	if(parseFloat(money2)>money1)
	{
		$(tr).find("input[name='dichong_money']").val(money1);
	}
	
	//2.总金额累加
	var realityMoneyTotal = parseFloat(0);
	$("#pool_tab input[name=dichong_money]").each(function(){
		if(isNaN($(this).val())){
			alert("请填写正确数字！！");
			$(this).val("0.00");
		}else{
			realityMoneyTotal += parseFloat($(this).val());
		}
	});
	$("#realityMoneyTotal").val(realityMoneyTotal);

	var FI_REALITY_ACCOUNT = parseFloat($("#FI_REALITY_ACCOUNT").val().replace(',',''));//本次来款实付
	$("#fi_pay_money_").val(formatMoney(FI_REALITY_ACCOUNT+realityMoneyTotal));//累计金额
	if(FI_REALITY_ACCOUNT+realityMoneyTotal==parseFloat($("#FI_REALITY_MONEY").val().replace(',',''))){
		$("#fi_pay_money_").removeClass("font_red");
		$("#fi_pay_money_").addClass("font_blue");
		
	}else{
		$("#fi_pay_money_").removeClass("font_blue");
		$("#fi_pay_money_").addClass("font_red");
	}
}


/**
 * 核销
 * 
 * 用户在核销页面中选择将要核销的数据，后核销选择内容
 * 
 * @return
 */
function toCommintFund(){
	if(getDetailData()==''){
		return $.messager.alert('警告','请填选择提交数据');
	}else{		
		var detailData = $("#pageTable").datagrid('getSelections');//获取选中信息
		var FI_ACCOUNT_DATE = $('#FI_ACCOUNT_DATE').datebox("getValue");//核销日期
		var FI_PAY_BANK = $('#FI_PAY_BANK').val();//来款银行
		var FI_TO_THE_PAYEE = $('#FI_TO_THE_PAYEE').val();//来款人
		var FI_REALITY_BANK = $('#FI_REALITY_BANK').val();//核销银行
		var FI_REALITY_ACCOUNT = parseFloat($('#FI_REALITY_ACCOUNT').val().replace(',',''));//本次来款实付
		var FI_PAY_YUE = parseFloat($('#FI_PAY_YUE').val().replace(',',''));//余额
		var FI_REALITY_MONEY = parseFloat($('#FI_REALITY_MONEY').val().replace(',',''));//本次应核销金额 计划
		var realityMoneyTotal = parseFloat(
				isNaN($("#realityMoneyTotal").val()) || $("#realityMoneyTotal") == undefined || $("#realityMoneyTotal")=='' 
					? 0 : $("#realityMoneyTotal").val() );//冲抵金额

		var fi_pay_money = parseFloat($("#fi_pay_money_").val().replace(',',''));//本次核销合计 
		var fi_pay_money_dk = parseFloat($("#fi_pay_money_dk").val().replace(',',''));//应冲抵金额
		
		//使用池子金额

		var fi_pay_money_dk = parseFloat($("#fi_pay_money_dk").val().replace(',',''));//
		
		var poolData = JSON.stringify(getPoolData());
		
		if(FI_ACCOUNT_DATE == null || FI_ACCOUNT_DATE == ''){
			alert("未填写核销日期");
			return;
		}
		if(FI_REALITY_MONEY == null || FI_REALITY_MONEY == ''){
			alert("未生成核销金额");
			return;
		}
		if(FI_REALITY_ACCOUNT+realityMoneyTotal != FI_REALITY_MONEY){
			alert("来款小于应收款，不支持不足额核销");
			return;
		}
		$.messager.confirm('提示','确定核销吗?',function(r) {
			
			if(r){
				for(var i = 0; i<detailData.length; i++) {
					var ID = detailData[i].ID;//核销明细id
					var SUP_ID = detailData[i].SUP_ID;//供应商id
					
					$.ajax({
					    async:false,
							url : _basePath+ "/base/channel/Channel!dohexiao.action",
					       type : 'post',
						   data : 'ID=' + ID+'&FI_ACCOUNT_DATE='+FI_ACCOUNT_DATE+'&FI_PAY_BANK='+FI_PAY_BANK
						   +'&FI_TO_THE_PAYEE='+FI_TO_THE_PAYEE+'&FI_REALITY_BANK='+FI_REALITY_BANK+'&FI_TO_THE_PAYEE='+FI_TO_THE_PAYEE
						   +'&FI_REALITY_ACCOUNT='+FI_REALITY_ACCOUNT+'&FI_PAY_YUE='+FI_PAY_YUE+"&FI_REALITY_MONEY="+FI_REALITY_MONEY
						   +"&poolData="+poolData+"&SUP_ID="+sup_id,
					    dataType : 'json',
						success : function(json) {
							if (json.flag == true) {
								$.messager.alert("提示","核销成功!" );
							} else {
								$.messager.alert("提示",
								"审核失败！！");
							}		
							//刷新页面
							$('#pageTable').datagrid('load');	
							$('#pool_tab').html("");
						}
					});
				}
			}
		});
	}
}
/**
 * 查询模糊
 * FI_SUPPLIERS_ID
 * @return
 */ 
function toSeacher() {
	var SUP_NAME = $("input[name='SUP_NAME']").val();
	$('#pageTable').datagrid('load', {
		"SUP_NAME" : SUP_NAME
	});
}

/**
 * 清空查询数据
 * @return
 */
function clearQuery(){
	$("#FI_PAY_DATE1").datebox('clear');
	$("#FI_PAY_DATE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 核销/驳回数据提取
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.ID = detailData[i].ID;//核销明细id
		temp.SUP_ID = detailData[i].SUP_ID;//供应商id
		getDetailData.push(temp);
	}
	return getDetailData;
}



/**
 * 获取资金池中数据
 * @return
 */
function getPoolData(){
	var getPoolData = [];
	$("#pool_tab tr").each(function(){
		if($(this).find("input[name=POOLID]").val()!= undefined){
			var temp = {};
			temp.POOLID= $(this).find("input[name=POOLID]").val();//租金池id
			temp.POOL_TYPE= $(this).find("input[name=POOLNAME]").val();//租金池类别
			temp.POOLUSERMONEY = $(this).find("input[name=POOLUSERMONEY]").val();//可用余额
			temp.dichong_money = $(this).find("input[name=dichong_money]").val();//所选冲抵款合计
			getPoolData.push(temp);
		}
	});
	return getPoolData;
}




















////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/******************************************************以下为资金扣划核销操作*********************************************************************/
/**
 * 提交
 * 用户在点击展开页面后， 填写相关数据后提交保存
 * @return
 */
function toCommit(FUND_ID_APP){

//	if($("#FI_TO_THE_PAYEE").val()==null || $("#FI_TO_THE_PAYEE").val()==undefined){
//		return $.messager.alert('警告','请填写来款人');
//	}//$('#cc').combobox('setValue', '001')
//	if($("#FI_REALITY_BANK").attr('selected',true)==null || $("#FI_REALITY_BANK").attr('selected',true)==undefined){
//		return $.messager.alert('警告','请填写核销银行');
//	}
	if($("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==null || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==""){
		return $.messager.alert('警告','请填写核销时间');
	}
	
	
	var data = {};
	data["getBaseData"] = getBaseData(FUND_ID_APP);	
	data["getDetailData"] = getDetailData1();
	
	if($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==null || $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==undefined){
		return $.messager.alert('警告','请填写本次实付');
	}else{
		if(parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
			var totalMoney = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())+parseFloat($("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额大于本次应收核销金额,请重新选择金额');
			}else if(parseFloat(totalMoney)<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额应等于本次应收核销金额,请重新选择金额');
			}else{
				data["getPoolData"] = getPoolData();	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/fundNotEBank/FundNotEBank!doCommitHXSheet.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");
			
			//刷新页面
			$('#pageTable').datagrid('load');
		 }else{
			 $.messager.alert("提示","保存失败！！");
			 
			//刷新页面
			$('#pageTable').datagrid('load');
		 }
		}
	});
}

//核销
function toCommitHexiao(FUND_ID_APP){
//	if($("#FI_TO_THE_PAYEE").val()==null || $("#FI_TO_THE_PAYEE").val()==undefined){
//		return $.messager.alert('警告','请填写来款人');
//	}//$('#cc').combobox('setValue', '001')
//	if($("#FI_REALITY_BANK").attr('selected',true)==null || $("#FI_REALITY_BANK").attr('selected',true)==undefined){
//		return $.messager.alert('警告','请填写核销银行');
//	}
	
	var head_flag = false ; 

	$.ajax( {
	    async:false,
			url : _basePath+ "/fundNotEBank/FundNotEBank!checkFirstInfoByHeadIds.action",
	       type : 'post',
		   data : 'HEAD_IDS=' + FUND_ID_APP,
	   dataType : 'json',
		success : function(json) {
			if (json.flag == true) {
				head_flag = true ;
				$.messager.alert("提示","收款单号:"+FUND_ID_APP+json.msg );
			} 		
		}
	});
	if(head_flag == true){
		return false;
	}
	
	if($("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==null || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==""){
		return $.messager.alert('警告','请填写核销时间!');
	}
	
	
	var data = {};
	data["getBaseData"] = getBaseData();	
	data["getDetailData"] = getDetailData1();
	if($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==null || $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==undefined){
		return $.messager.alert('警告','请填写本次实付');
	}else{
		if(parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
			var totalMoney = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())+parseFloat($("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额大于本次应收核销金额请重新选择金额');
			}else if(parseFloat(totalMoney)<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额应等于本次应收核销金额,请重新选择金额');
			}else{
				data["getPoolData"] = getPoolData();	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/fundNotEBank/FundNotEBank!doCommitHXSheetPool.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");			
		 }else{
			 $.messager.alert("提示","保存失败！！");			 
		 }
		//刷新页面
		$('#pageTable').datagrid('load');
		}
	});
}

/**
 * 提交的基础数据提取
 * @return
 */
function getBaseData(FUND_ID_APP){
	var getBaseData = {};
	var FI_PAY_BANK = $("#FI_PAY_BANK"+FUND_ID_APP).val();//付款银行
	var FI_ACCOUNT_DATE = $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue');//核销时间
	var FI_REALITY_BANK = $("#FI_REALITY_BANK"+FUND_ID_APP).attr("selected",true).val();//核销银行
	var FI_TAGE = $("#FI_TAGE"+FUND_ID_APP).find("option:selected").val();//余款挂账类型
	var FI_TAGE_ID= $("#FI_TAGE_ID"+FUND_ID_APP).find("option:selected").val();//挂账人员id
	var FI_TAGE_NAME= $("#FI_TAGE_ID"+FUND_ID_APP).find("option:selected").attr("FI_TAGE_NAME");//挂账人名称
	getBaseData["FI_PAY_BANK"] = FI_PAY_BANK;
	getBaseData["FI_ACCOUNT_DATE"] = FI_ACCOUNT_DATE;
	getBaseData["FI_REALITY_BANK"] = FI_REALITY_BANK;
	getBaseData["FI_TAGE"] = FI_TAGE;
	getBaseData["FI_TAGE_ID"] = FI_TAGE_ID;
	getBaseData["FI_TAGE_NAME"] = FI_TAGE_NAME;
	getBaseData["FUND_ID"] = $("#FUND_ID"+FUND_ID_APP).val();//申请单id
	getBaseData["FI_TO_THE_PAYEE"] = $("#FI_TO_THE_PAYEE"+FUND_ID_APP).val();//来款人
	getBaseData["FI_REALITY_MONEY"] = $("#FI_REALITY_MONEY"+FUND_ID_APP).val();//实际来款金
	getBaseData["FI_REALITY_ACCOUNT"] = $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val();//实际付款金额
	getBaseData["FI_TAGE_MONEY"] = $("#FI_PAY_YUE"+FUND_ID_APP).val();//本次来款余额
	var FUND_ID = $("#FUND_ID"+FUND_ID_APP).val();
	getBaseData["MONEYORDERSAVE"] = $("#moneyOrderSave"+FUND_ID).val();
	return getBaseData;
}


/**
 * 获取列表数据
 * @return
 */
function getDetailData1(){
	var getDetailData = [];
	var detailData = $("#pageTable_").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.D_PROJECT_CODE = detailData[i].D_PROJECT_CODE;
		temp.D_CLIENT_CODE = detailData[i].D_CLIENT_CODE;
		temp.YS_MONEY = detailData[i].YS_MONEY;
		temp.RECEIVABLE = detailData[i].RECEIVABLE;
		//temp.D_PAY_MONEY = detailData[i].D_PAY_MONEY;
		temp.VERITABLE_MONEY = detailData[i].VERITABLE_MONEY;
		getDetailData.push(temp);
	}
	return getDetailData;//JSON.stringify(getDetailData);//encodeURI(JSON.stringify(getDetailData));
} 



function updateMoneyOrder(obj){
	 $("#dlgUp"+obj).dialog("open");
}
/******************************************************以上为资金扣划核销操作*****************************************************************************************/

/******************************************************以下为资金扣划驳回操作*****************************************************************************************/
function doRegist(){
	if(getDetailData()==''){
		$.messager.alert("警告","请选择需驳回数据！！");
	}else{
		$.messager.confirm('提示','确定要驳回吗?', function(r) {
			if (r) {
				var data = {};
				data["getDetailData"] = getDetailData();
				
				$.ajax({		
					url:_basePath+"/fundNotEBank/FundNotEBank!doReject.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					 if(json.flag == true){
						 $.messager.alert("提示","驳回成功！！");				
					 }else{
						 $.messager.alert("提示","驳回失败！！");
					 }
					//刷新页面
					$('#pageTable').datagrid('load');
					}
				});
			}		
		});
	}
}
/******************************************************以上为资金扣划驳回操作*****************************************************************************************/

/********************************************************以下为设置单元格可修改***杨雪********************************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	$("#pageTable").datagrid("checkRow",editIndex);
	
	if ($('#pageTable').datagrid('validateRow', editIndex)) {		
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCell(index, field) {
	$('#pageTable').datagrid('endEdit', editIndex);
	if (endEditing()) {
		$('#pageTable').datagrid('editCell', {index : index,field : field});
		editIndex = index;
	}
}

/**
 * 单元格修改好变更数据
 * @param rowIndex
 * @param rowData
 * @param changes
 * @return
 */
//function toChangeCell(rowIndex,rowData,changes){
//	var VERITABLE_MONEY=changes.VERITABLE_MONEY;//本次实收金额
//	var RECEIVABLE  = rowData.RECEIVABLE;//本次应收金额
//	var FI_PAY_MONEY = parseFloat($("#FI_PAY_MONEY").val());//本次实付
//	var fi_pay_money = parseFloat($("#fi_pay_money_").val());//本次应收核销
//	var unusedAmount = parseFloat(0);
//	
//	if(parseFloat(VERITABLE_MONEY)>parseFloat(RECEIVABLE)){
//		FI_PAY_MONEY = FI_PAY_MONEY - parseFloat(RECEIVABLE) + parseFloat(VERITABLE_MONEY);
//		unusedAmount = FI_PAY_MONEY - fi_pay_money;
//	}else if(parseFloat(VERITABLE_MONEY)<parseFloat(RECEIVABLE)){
//		FI_PAY_MONEY = FI_PAY_MONEY - parseFloat(RECEIVABLE) + parseFloat(VERITABLE_MONEY);
//		unusedAmount = 0;
//	}
//	
//	$("#FI_PAY_YUE").val(unusedAmount);//本次实付余额
//	$("#FI_PAY_MONEY").val(FI_PAY_MONEY);//本次实付
//	
//}
/********************************************************以上为设置单元格可修改***杨雪*****************~~**************************************/

/********************************************************通用方法****************************************************************************/
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
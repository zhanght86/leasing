var dkflag=true;
/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	var COMPANY_NAME = $("select[name='COMPANY_NAME']").attr("selected",true).val();
	var PRODUCT_NAME = $("select[name='PRODUCT_NAME']").attr("selected",true).val();
	$('#pageTable').datagrid('load', {
		"CLIENT_NAME" : CLIENT_NAME,
		"PRO_CODE" : PRO_CODE,
		"COMPANY_NAME" : COMPANY_NAME,
		"PRODUCT_NAME" : PRODUCT_NAME
	});
}

/**CashDeposit
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 计算应收金额， 实收金额， 项目数量
 * @auth yx
 * @date 2013-09-28
 * @return
 */
function onChangeSelect()
{
	var datagridList=$("#pageTable").datagrid('getSelections');
	var DEDUCTION_YUEAll=0;
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++)
	{
		datagridList[i].PRO_ID.checkbox = true;
			
		var DEDUCTION_YUE=datagridList[i].DEDUCTION_YUE;
		DEDUCTION_YUEAll=accAdd(DEDUCTION_YUEAll,DEDUCTION_YUE);
		NUM++;
	}
	$("#FI_REALITY_MONEY").val(DEDUCTION_YUEAll);//实收
	$("#FI_PROJECT_NUM").val(NUM);//项目数量
	$("#FI_PAY_MONEY").val(DEDUCTION_YUEAll);//应收
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

//保存提交数据
function toSubmtit(){
	var detailData = $("#pageTable").datagrid('getSelections');
	if(detailData.length<=0){
		return alert("请选择申请结清抵扣数据");
	}else if($("#COMPANY_NAME_0").val()==null||$("#COMPANY_NAME_0").val()==undefined||$("#COMPANY_NAME_0").val()==""){
		return alert("供应商不能为空");
	}else if($("#FI_FLAG").attr("selected",true).val()==null||$("#FI_FLAG").attr("selected",true).val()==""||$("#FI_FLAG").attr("selected",true).val()==""){
		return alert("付款方式不能为空");
	}else if($("#FI_PAY_DATE").datebox("getValue")==null||$("#FI_PAY_DATE").datebox("getValue")==""||$("#FI_PAY_DATE").datebox("getValue")==""){
		return alert("付款日期不能为空");
	}else if($("#FI_PAY_MONEY").val()==null||$("#FI_PAY_MONEY").val()==undefined||$("#FI_PAY_MONEY").val()==""){
		return alert("应付款金额不能为空");
	}else if($("#FI_PROJECT_NUM").val()==null||$("#FI_PROJECT_NUM").val()==undefined||$("#FI_PROJECT_NUM").val()==""){
		return alert("项目数量不能为空");
	}else if($("#FI_REALITY_MONEY").val()==null||$("#FI_REALITY_MONEY").val()==undefined||$("#FI_REALITY_MONEY").val()==""){
		return alert("实际付款金额不能为空");
	}else {
		var data = {};
		data["getBaseData"] = getBaseData();
		data["getDetailData"] = getDetailData();
		if(dkflag){
			$.ajax({
				url:_basePath+"/renterpool/CashDeposit!checkedLockTypeRent.action",
			    type:'post',
			    data:'data='+JSON.stringify(data),
			    dataType:'json',
			    success:function(josn1){
					if(josn1.flag==true){
						$.ajax({
							url:_basePath+"/renterpool/CashDeposit!doInsertFundHead.action",
						    type:'post',
						    data:'data='+JSON.stringify(data),
						    dataType:'json',
						    success:function(json){
							    if(json.flag == true){
							    	$.messager.alert("结清抵扣申请","结清抵扣申请成功！！");
							    	//页面刷新
							    	$('#pageTable').datagrid('load');
							    	//window.location.href = _basePath+"/renterpool/CashDeposit!toMgJQDKData.action";
							    	//清空基础数据
							    	$("#FI_PAY_DATE").datebox('clear');
							    	$("#getBaseData").each(function(){
							    		$(this).val("");
							    	});
							    }else {
							    	$.messager.alert("结清抵扣申请","结清抵扣申请失败！！");
							    	//页面刷新
							    	$('#pageTable').datagrid('load');
							    	//window.location.href = _basePath+"/renterpool/CashDeposit!toMgJQDKData.action";
							    	//清空基础数据
							    	$("#FI_PAY_DATE").datebox('clear');
							    	$("#getBaseData").each(function(){
							    		$(this).val("");
							    	});
							    }
						    }
						});
					}else {
						alert(josn1.msg);
						$.messager.alert("结清抵扣申请-提示","支付表："+josn1.msg+"已经被锁住，请重新选择数据");
					}
				}
			});
		}else{
			$.messager.alert("提示","所选的违约金正在别其他模块使用，不能进行抵扣");
			return;
		}
	}
}

/**
 * 获取申请单基础数据
 * @auth yx
 * @date 2013-09-28
 * @return
 */
function getBaseData(){
	var getBaseData = {};
	var FI_PAY_DATE = $("#FI_PAY_DATE").datebox('getValue');
	var FI_PAY_TYPE = $("#FI_PAY_TYPE").attr("selected",true).val();
	var FI_FLAG = $("#FI_FLAG").attr("selected",true).val();
	getBaseData["FI_PAY_DATE"]  = FI_PAY_DATE;
	getBaseData["FI_PAY_TYPE"]  = FI_PAY_TYPE;
	getBaseData["FI_FLAG"]  = FI_FLAG;
	$("#baseData tr").each(function(){
		//var temp = {};
		getBaseData["COMPANY_NAME"] = $(this).find($("input[name=COMPANY_NAME]")).val();		
		getBaseData["FI_PAY_MONEY"]  = $(this).find($("input[name=FI_PAY_MONEY]")).val();
		getBaseData["FI_REALITY_MONEY"]  = $(this).find($("input[name=FI_REALITY_MONEY]")).val();
		getBaseData["FI_PROJECT_NUM"]  = $(this).find($("input[name=FI_PROJECT_NUM]")).val();
		//getBaseData.push(temp);
	});

	return getBaseData;
}

/**
 * 获取明细信息
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var DUE_STATUS=detailData[i].DUE_STATUS;
		var OVERDUE_STATUS=detailData[i].OVERDUE_STATUS;
		if((OVERDUE_STATUS!='0' && OVERDUE_STATUS!=null)||(DUE_STATUS!='1'&&DUE_STATUS !=null)){
			dkflag=false;
			break;
		}
		var temp = {};
		temp.PRO_ID = detailData[i].PRO_ID;
		temp.PRO_CODE = detailData[i].PRO_CODE;
		temp.CLIENT_ID = detailData[i].CLIENT_ID;
		temp.CUST_NAME = detailData[i].CUST_NAME;
		//temp.D_PAY_MONEY = detailData[i].D_PAY_MONEY;
		temp.PRODUCT_NAME = detailData[i].PRODUCT_NAME;
		temp.COMPANY_NAME = detailData[i].COMPANY_NAME;
		temp.WS_NUM = detailData[i].WS_NUM;
		temp.BJ_MONEY = detailData[i].BJ_MONEY;
		temp.LX_MONEY = detailData[i].LX_MONEY;
		temp.ZJ_MONEY = detailData[i].ZJ_MONEY;
		temp.WS_WYJ = detailData[i].WS_WYJ;
		temp.BZJ_MONEY = detailData[i].BZJ_MONEY;
		temp.DEDUCTION_YUE = detailData[i].DEDUCTION_YUE;
		temp.SY_BZJ = detailData[i].SY_BZJ;
		temp.PAYLIST_CODE = detailData[i].PAYLIST_CODE;
		getDetailData.push(temp);
	}
	return getDetailData;//JSON.stringify(getDetailData);//encodeURI(JSON.stringify(getDetailData));
}
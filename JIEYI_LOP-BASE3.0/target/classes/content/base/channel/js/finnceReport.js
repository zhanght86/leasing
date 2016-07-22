
/**
 * 乘法
 * @param arg1
 * @param arg2
 * @return
 */
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try { m += s1.split(".")[1].length; } catch (e) { }
    try { m += s2.split(".")[1].length; } catch (e) { }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
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

/**
 * 除法
 * @param arg1
 * @param arg2
 * @return
 */
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length ;} catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length ;} catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

/**
 * 减法
 * @param arg1
 * @param arg2
 * @return
 */
function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

//两数之减（输入坐标） arg3=arg1-arg2
function CoordinateAccSub(arg1,arg2,arg3)
{
	var value1=$("#"+arg1).val();//id 为arg1的值
	var value2=$("#"+arg2).val();//id 为arg2的值
	
	//value1=value1.replace(/[^\d\.]/,"");
	//value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	$("#"+arg1).val(anyDouble(value1,2));
	$("#"+arg2).val(anyDouble(value2,2));
	$("#"+arg3).val(anyDouble(accSub(value1,value2),2));
	
	
	eval($("#"+arg3).attr("liandong"));
	eval($("#"+arg3).attr("liandong1"));
	eval($("#"+arg3).attr("liandong2"));
}

//多数相加  contype标识第几列（B），num1,num2：第几行  . 拼起来应该是：B1，B2. agr3为求和后被赋予的元素
function CoordinateAccAdd(conType,num1,num2,arg3)
{
	var totalValue=0;
	for(var i=num1;i<=num2;i++)
	{
		var value1=$("#"+conType+i).val();
		
		//value1=value1.replace(/[^\d\.]/,"");
		
		if(value1=="")
		{
			value1=0;
		}
		
		
		$("#"+conType+i).val(anyDouble(value1,2));
		
		totalValue=anyDouble(accAdd(value1,totalValue),2);
	}
	$("#"+arg3).val(totalValue);
	eval($("#"+arg3).attr("liandong"));
	eval($("#"+arg3).attr("liandong1"));
}


//多数相加返回值
function returnAccAdd(conType,num1,num2)
{
	var totalValue=0;
	for(var i=num1;i<=num2;i++)
	{
		var value1=$("#"+conType+i).val();
		
		//value1=value1.replace(/[^\d\.]/,"");
		
		if(value1=="")
		{
			value1=0;
		}
		
		$("#"+conType+i).val(anyDouble(value1,2));
		
		totalValue=anyDouble(accAdd(value1,totalValue),2);
	}
	
	return totalValue;
}

//多处 多数相加(最多支持3处)
function CoordinateMoreAdd(conType1,num1,num2,conType2,num3,num4,conType3,num5,num6,args)
{
	var totalValue1=returnAccAdd(conType1,num1,num2);
	var totalValue2=returnAccAdd(conType2,num3,num4);
	var totalValue3=returnAccAdd(conType3,num5,num6);
	
	totalValue=anyDouble(accAdd(accAdd(totalValue1,totalValue2),totalValue3),2);
	$("#"+args).val(totalValue);
	
	eval($("#"+args).attr("liandong"));
	eval($("#"+args).attr("liandong1"));
}

//C7+C8-C9-C10-C11  ((C7+C8)-(C9+C10+C11))
function addSubfun(conType1,num1,num2,conType2,num3,num4,args)
{
	var totalValue1=returnAccAdd(conType1,num1,num2);
	var totalValue2=returnAccAdd(conType2,num3,num4);
	
	totalValue=anyDouble(accSub(totalValue1,totalValue2),2);
	$("#"+args).val(totalValue);
	eval($("#"+args).attr("liandong"));
	eval($("#"+args).attr("liandong1"));
	
}



//多数相加（B14+B25+B35+B36）
function returnAccSubMore(args1,args2,args3,args4,args5)
{
	var totalValue=0;
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	var value3=$("#"+args3).val();//id 为arg3的值
	var value4=$("#"+args4).val();//id 为arg4的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
//	value3=value3.replace(/[^\d\.]/,"");
//	value4=value4.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	if(value3=="")
	{
		value3=0;
	}
	
	if(value4=="")
	{
		value4=0;
	}
	
	
	
	$("#"+args1).val(anyDouble(value1,2));
	$("#"+args2).val(anyDouble(value2,2));
	$("#"+args3).val(anyDouble(value3,2));
	$("#"+args4).val(anyDouble(value4,2));
	$("#"+args5).val(anyDouble(accAdd(accAdd(accAdd(value1,value2),value3),value4),2));
	
	eval($("#"+args5).attr("liandong"));
	eval($("#"+args5).attr("liandong1"));
}

//多数相加减（E29-E30+E31-E32）
function returnAccAddSubMore(args1,args2,args3,args4,args5)
{
	var totalValue=0;
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	var value3=$("#"+args3).val();//id 为arg3的值
	var value4=$("#"+args4).val();//id 为arg4的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
//	value3=value3.replace(/[^\d\.]/,"");
//	value4=value4.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	if(value3=="")
	{
		value3=0;
	}
	
	if(value4=="")
	{
		value4=0;
	}
	
	
	
	$("#"+args1).val(anyDouble(value1,2));
	$("#"+args2).val(anyDouble(value2,2));
	$("#"+args3).val(anyDouble(value3,2));
	$("#"+args4).val(anyDouble(value4,2));
	$("#"+args5).val(anyDouble(accSub(accAdd(accSub(value1,value2),value3),value4),2));
	
	eval($("#"+args5).attr("liandong"));
	eval($("#"+args5).attr("liandong1"));
}


//利润分配比例计算

// 未分配利润勾稽差异
function notProfitRate(args1,args2,args3,args4)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	
	$("#"+args1).val(anyDouble(value1,2));
	$("#"+args2).val(anyDouble(value2,2));
	
	
	
	value3=anyDouble(accSub(value1,value2),2);
	
	var value4="";
	if(value3==0)
	{
		value4="平衡";
	}
	else
	{
		value4="不平衡";
	}
	
	$("#"+args3).val(value3);
	$("#"+args4).val(value4);
	
}

//法定盈余公积提取比例
function SURPLUS_RESERVE_RATE(args1,args2,args3,args4)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	$("#"+args1).val(anyDouble(value1,2));
	$("#"+args2).val(anyDouble(value2,2));
	
	var value3=0;
	if(value1!=0)
	{
		value3=anyDouble(accMul(accDiv(value2,value1),100),2);
	}
	var value4="";
	if(value1<=0)
	{
		if(value2==0)
		{
			value4="正确";
		}
		else
		{
			value4="不正确";
		}
	}
	else
	{
		if(value3==10)
		{
			value4="比例正确";
		}
		else
		{
			value4="比例不正确";
		}
	}
	$("#"+args3).val(value3);
	$("#"+args4).val(value4);
	eval($("#"+args4).attr("liandong"));
	eval($("#"+args4).attr("liandong1"));
}

//法定公益金提取比例：
function WELFARE_FUND_RATE(args1,args2,args3,args4)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	$("#"+args1).val(anyDouble(value1,2));
	$("#"+args2).val(anyDouble(value2,2));
	var value3=0;
	if(value1!=0)
	{
		value3=anyDouble(accMul(accDiv(value2,value1),100),2);
	}
	var value4="";
	if(value1<=0)
	{
		if(value2==0)
		{
			value4="正确";
		}
		else
		{
			value4="不正确";
		}
	}
	else
	{
		if(value3==5)
		{
			value4="比例正确";
		}
		else
		{
			value4="比例不正确";
		}
	}
	$("#"+args3).val(value3);
	$("#"+args4).val(value4);
	eval($("#"+args4).attr("liandong"));
	eval($("#"+args4).attr("liandong1"));
}

function SURPLUS_RESERVE_RATEAll(args1,args2,args3,args4,args5,args6,args7)
{
	SURPLUS_RESERVE_RATE(args1,args2,args3,args4);
	WELFARE_FUND_RATE(args1,args5,args6,args7);
}

//前年所得税负
function INCOME_TAX_RATE1()
{
	var value1=$("#C17").val();
	var value2=$("#C18").val();
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	var value3="";
	if(value1!=0)
	{
		value3=anyDouble(accMul(accDiv(value2,value1),100),2);
	}
	$("#C44").val(value3);
}

//所得税负
function INCOME_TAX_RATE(args1,args2,args3,args4,args5)
{
	var value1=$("#"+args1).val();
	var value2=$("#"+args2).val();
	var value3=$("#"+args3).val();
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	var value4="";
	if(value1!=0)
	{
		value4=anyDouble(accMul(accDiv(value2,value1),100),2);
	}
	$("#"+args4).val(value4);
	var value5="";
	if(value3=="" && value4=="")
	{
		value5="";
	}
	else
	{
		if(value3==value4)
		{
			value5="比例相同";
		}
		else
		{
			value5="比例不同";
		}
	}
	$("#"+args5).val(value5);
}

//比较正确、不正确
function compTure(args1,args2)
{
	var value1=$("#"+args1).val();
	
	var value2="";
	if(value1==0)
	{
		value2="正确";
	}else
	{
		value2="不正确";
	}
	
	$("#"+args2).val(value2);
}

function not_profitfuzhai(args1,args2,args3,args4)
{
	//页面初始其他财务系统数据
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	var value3=anyDouble(accSub(value1,value2),2);
	
	$("#"+args3).val(value3);
	
	var value4="";
	if(value3==0)
	{
		value4="平衡";
	}else
	{
		value4="不平衡";
	}
	$("#"+args4).val(value4);
}

function cashJs(args1,args2,args3,args4)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	var value3=anyDouble(accSub(value1,value2),2);
	
	$("#"+args3).val(value3);
	
	var value4="";
	if(value3==0)
	{
		value4="正确";
	}else
	{
		value4="不正确";
	}
	$("#"+args4).val(value4);
}

function cashJs1(args1,args2,args3,args4,args5)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	var value3=$("#"+args3).val();//id 为arg2的值
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	if(value3=="")
	{
		value3=0;
	}
	
	var value4=anyDouble(accSub(accSub(value1,value2),value3),2);
	
	$("#"+args4).val(value4);
	
	var value5="";
	if(value4==0)
	{
		value5="正确";
	}else
	{
		value5="不正确";
	}
	$("#"+args5).val(value5);
}

//IF(（本年数）计提的资产减值准备-资产负债表!（上年数）合计=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）计提的资产减值准备-资产负债表!上年数合计,"0.00")))
function tongji1(args1,args2,args3)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	$("#"+args1).val(value1);
	$("#"+args2).val(value2);
	
	var value3="";
	if(accSub(value1,value2)==0)
	{
		value3="与资产负债表数值相符";
	}else
	{
		var value=anyDouble(accSub(value1,value2),2);
		
		value3="差额"+value;
	}
	$("#"+args3).val(value3);
}

//IF(（本年数）固定资产折旧-(资产负债表!（本年数）减:累计折旧-资产负债表!（上年数）减:累计折旧)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）固定资产折旧-(资产负债表!（本年数）减:累计折旧-资产负债表!（上年数）减:累计折旧),"0.00")))
//IF(（本年数）待摊费用减少(减:增加)-(资产负债表!（上年数）待摊费用-资产负债表!（本年数）待摊费用)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）待摊费用减少(减:增加)-(资产负债表!（上年数）待摊费用-资产负债表!（本年数）待摊费用),"0.00")))
//IF(（本年数）预提费用增加(减:减少)-(资产负债表!（本年数）预提费用-资产负债表!（上年数）预提费用)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）预提费用增加(减:减少)-(资产负债表!（本年数）预提费用-资产负债表!（上年数）预提费用),"0.00")))
//IF(（本年数）递延税款贷项(减:借项)-(资产负债表!（本年数）递延税款贷项-资产负债表!（本年数）递延税款借项)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）递延税款贷项(减:借项)-(资产负债表!（本年数）递延税款贷项-资产负债表!（本年数）递延税款借项),"0.00")))
//IF(（本年数）存货的减少(减:增加)-(资产负债表!（上年数）存货-资产负债表!（本年数）存货)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）存货的减少(减:增加)-(资产负债表!（上年数）存货-资产负债表!（本年数）存货),"0.00")))
function tongji2(args1,args2,args3,args4)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	var value3=$("#"+args3).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
//	value3=value3.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	if(value3=="")
	{
		value3=0;
	}
	
	$("#"+args1).val(value1);
	$("#"+args2).val(value2);
	$("#"+args3).val(value3);
	
	var value4="";
	if(accSub(value1,accSub(value2,value3))==0)
	{
		value4="与资产负债表数值相符";
	}
	else
	{
		var value=anyDouble(accSub(value1,accSub(value2,value3)),2);
		
		value4="差额"+value;
	}
	$("#"+args4).val(value4);
}



//IF(（本年数）财务费用-利润及利润分配表!（本年数）财务费用=0,"与利润表数值相符",CONCATENATE("差额",TEXT(（本年数）财务费用-利润及利润分配表!（本年数）财务费用,"0.00")))
function tongji3(args1,args2,args3)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	$("#"+args1).val(value1);
	$("#"+args2).val(value2);
	
	var value3="";
	if(accSub(value1,value2)==0)
	{
		value3="与利润表数值相符";
	}else
	{
		var value=anyDouble(accSub(value1,value2),2);
		
		value3="差额"+value;
	}
	$("#"+args3).val(value3);
}

//IF(（本年数）投资损失(减:收益)+利润及利润分配表!（本年数）加:投资收益=0,"与利润表数值相符",CONCATENATE("差额",TEXT(（本年数）投资损失(减:收益)+利润及利润分配表!（本年数）加:投资收益,"0.00")))
function tongji4(args1,args2,args3)
{
	var value1=$("#"+args1).val();//id 为arg1的值
	var value2=$("#"+args2).val();//id 为arg2的值
	
//	value1=value1.replace(/[^\d\.]/,"");
//	value2=value2.replace(/[^\d\.]/,"");
	
	if(value1=="")
	{
		value1=0;
	}
	
	if(value2=="")
	{
		value2=0;
	}
	
	$("#"+args1).val(value1);
	$("#"+args2).val(value2);
	
	var value3="";
	if(accAdd(value1,value2)==0)
	{
		value3="与利润表数值相符";
	}else
	{
		var value=anyDouble(accAdd(value1,value2),2);
		
		value3="差额"+value;
	}
	$("#"+args3).val(value3);
}



//保存利润及利润分配表
function saveFinnce_ProfitDistriBution()
{
	var MAIN_INCOME={};
	MAIN_INCOME.MAIN_INCOME1 = $("input[name='MAIN_INCOME1']").val();
	MAIN_INCOME.MAIN_INCOME2 = $("input[name='MAIN_INCOME2']").val();
	MAIN_INCOME.MAIN_INCOME3 = $("input[name='MAIN_INCOME3']").val();
	
	var MAIN_COST={};
	MAIN_COST.MAIN_COST1 = $("input[name='MAIN_COST1']").val();
	MAIN_COST.MAIN_COST2 = $("input[name='MAIN_COST2']").val();
	MAIN_COST.MAIN_COST3 = $("input[name='MAIN_COST3']").val();
	
	var MAIN_TAXES={};
	MAIN_TAXES.MAIN_TAXES1 = $("input[name='MAIN_TAXES1']").val();
	MAIN_TAXES.MAIN_TAXES2 = $("input[name='MAIN_TAXES2']").val();
	MAIN_TAXES.MAIN_TAXES3 = $("input[name='MAIN_TAXES3']").val();
	
	var MAIN_PROFIT={};
	MAIN_PROFIT.MAIN_PROFIT1 = $("input[name='MAIN_PROFIT1']").val();
	MAIN_PROFIT.MAIN_PROFIT2 = $("input[name='MAIN_PROFIT2']").val();
	MAIN_PROFIT.MAIN_PROFIT3 = $("input[name='MAIN_PROFIT3']").val();
	
	var MAIN_OTHER_PROFIT={};
	MAIN_OTHER_PROFIT.MAIN_OTHER_PROFIT1 = $("input[name='MAIN_OTHER_PROFIT1']").val();
	MAIN_OTHER_PROFIT.MAIN_OTHER_PROFIT2 = $("input[name='MAIN_OTHER_PROFIT2']").val();
	MAIN_OTHER_PROFIT.MAIN_OTHER_PROFIT3 = $("input[name='MAIN_OTHER_PROFIT3']").val();
	
	var MAIN_OPER_COST={};
	MAIN_OPER_COST.MAIN_OPER_COST1 = $("input[name='MAIN_OPER_COST1']").val();
	MAIN_OPER_COST.MAIN_OPER_COST2 = $("input[name='MAIN_OPER_COST2']").val();
	MAIN_OPER_COST.MAIN_OPER_COST3 = $("input[name='MAIN_OPER_COST3']").val();
	
	var MAIN_MAN_FEE={};
	MAIN_MAN_FEE.MAIN_MAN_FEE1 = $("input[name='MAIN_MAN_FEE1']").val();
	MAIN_MAN_FEE.MAIN_MAN_FEE2 = $("input[name='MAIN_MAN_FEE2']").val();
	MAIN_MAN_FEE.MAIN_MAN_FEE3 = $("input[name='MAIN_MAN_FEE3']").val();
	
	var MAIN_FINNA_FEE={};
	MAIN_FINNA_FEE.MAIN_FINNA_FEE1 = $("input[name='MAIN_FINNA_FEE1']").val();
	MAIN_FINNA_FEE.MAIN_FINNA_FEE2 = $("input[name='MAIN_FINNA_FEE2']").val();
	MAIN_FINNA_FEE.MAIN_FINNA_FEE3 = $("input[name='MAIN_FINNA_FEE3']").val();
	
	var OPER_PROFIT={};
	OPER_PROFIT.OPER_PROFIT1 = $("input[name='OPER_PROFIT1']").val();
	OPER_PROFIT.OPER_PROFIT2 = $("input[name='OPER_PROFIT2']").val();
	OPER_PROFIT.OPER_PROFIT3 = $("input[name='OPER_PROFIT3']").val();
	
	var INVESTMENT_PROFIT={};
	INVESTMENT_PROFIT.INVESTMENT_PROFIT1 = $("input[name='INVESTMENT_PROFIT1']").val();
	INVESTMENT_PROFIT.INVESTMENT_PROFIT2 = $("input[name='INVESTMENT_PROFIT2']").val();
	INVESTMENT_PROFIT.INVESTMENT_PROFIT3 = $("input[name='INVESTMENT_PROFIT3']").val();
	
	var SUBSIDY_INCOME={};
	SUBSIDY_INCOME.SUBSIDY_INCOME1 = $("input[name='SUBSIDY_INCOME1']").val();
	SUBSIDY_INCOME.SUBSIDY_INCOME2 = $("input[name='SUBSIDY_INCOME2']").val();
	SUBSIDY_INCOME.SUBSIDY_INCOME3 = $("input[name='SUBSIDY_INCOME3']").val();
	
	var OPER_INCOME={};
	OPER_INCOME.OPER_INCOME1 = $("input[name='OPER_INCOME1']").val();
	OPER_INCOME.OPER_INCOME2 = $("input[name='OPER_INCOME2']").val();
	OPER_INCOME.OPER_INCOME3 = $("input[name='OPER_INCOME3']").val();
	
	var OPER_EXPENDITURE={};
	OPER_EXPENDITURE.OPER_EXPENDITURE1 = $("input[name='OPER_EXPENDITURE1']").val();
	OPER_EXPENDITURE.OPER_EXPENDITURE2 = $("input[name='OPER_EXPENDITURE2']").val();
	OPER_EXPENDITURE.OPER_EXPENDITURE3 = $("input[name='OPER_EXPENDITURE3']").val();
	
	var PROFIT_TOTAL={};
	PROFIT_TOTAL.PROFIT_TOTAL1 = $("input[name='PROFIT_TOTAL1']").val();
	PROFIT_TOTAL.PROFIT_TOTAL2 = $("input[name='PROFIT_TOTAL2']").val();
	PROFIT_TOTAL.PROFIT_TOTAL3 = $("input[name='PROFIT_TOTAL3']").val();
	
	var INCOME_TAX={};
	INCOME_TAX.INCOME_TAX1 = $("input[name='INCOME_TAX1']").val();
	INCOME_TAX.INCOME_TAX2 = $("input[name='INCOME_TAX2']").val();
	INCOME_TAX.INCOME_TAX3 = $("input[name='INCOME_TAX3']").val();
	
	var PROFIT_NET={};
	PROFIT_NET.PROFIT_NET1 = $("input[name='PROFIT_NET1']").val();
	PROFIT_NET.PROFIT_NET2 = $("input[name='PROFIT_NET2']").val();
	PROFIT_NET.PROFIT_NET3 = $("input[name='PROFIT_NET3']").val();
	
	var NOT_ASSIGNED_PROFIT={};
	NOT_ASSIGNED_PROFIT.NOT_ASSIGNED_PROFIT1 = $("input[name='NOT_ASSIGNED_PROFIT1']").val();
	NOT_ASSIGNED_PROFIT.NOT_ASSIGNED_PROFIT2 = $("input[name='NOT_ASSIGNED_PROFIT2']").val();
	NOT_ASSIGNED_PROFIT.NOT_ASSIGNED_PROFIT3 = $("input[name='NOT_ASSIGNED_PROFIT3']").val();
	
	var OTHER_TRANSFER={};
	OTHER_TRANSFER.OTHER_TRANSFER1 = $("input[name='OTHER_TRANSFER1']").val();
	OTHER_TRANSFER.OTHER_TRANSFER2 = $("input[name='OTHER_TRANSFER2']").val();
	OTHER_TRANSFER.OTHER_TRANSFER3 = $("input[name='OTHER_TRANSFER3']").val();
	
	var DISTRIBUTION_PROFIT={};
	DISTRIBUTION_PROFIT.DISTRIBUTION_PROFIT1 = $("input[name='DISTRIBUTION_PROFIT1']").val();
	DISTRIBUTION_PROFIT.DISTRIBUTION_PROFIT2 = $("input[name='DISTRIBUTION_PROFIT2']").val();
	DISTRIBUTION_PROFIT.DISTRIBUTION_PROFIT3 = $("input[name='DISTRIBUTION_PROFIT3']").val();
	
	var STATU_SURPLUS_RESERVE={};
	STATU_SURPLUS_RESERVE.STATU_SURPLUS_RESERVE1 = $("input[name='STATU_SURPLUS_RESERVE1']").val();
	STATU_SURPLUS_RESERVE.STATU_SURPLUS_RESERVE2 = $("input[name='STATU_SURPLUS_RESERVE2']").val();
	STATU_SURPLUS_RESERVE.STATU_SURPLUS_RESERVE3 = $("input[name='STATU_SURPLUS_RESERVE3']").val();
	
	var STATU_SURPLUS_FUND={};
	STATU_SURPLUS_FUND.STATU_SURPLUS_FUND1 = $("input[name='STATU_SURPLUS_FUND1']").val();
	STATU_SURPLUS_FUND.STATU_SURPLUS_FUND2 = $("input[name='STATU_SURPLUS_FUND2']").val();
	STATU_SURPLUS_FUND.STATU_SURPLUS_FUND3 = $("input[name='STATU_SURPLUS_FUND3']").val();
	
	var BONUS_WELFARE_FUND={};
	BONUS_WELFARE_FUND.BONUS_WELFARE_FUND1 = $("input[name='BONUS_WELFARE_FUND1']").val();
	BONUS_WELFARE_FUND.BONUS_WELFARE_FUND2 = $("input[name='BONUS_WELFARE_FUND2']").val();
	BONUS_WELFARE_FUND.BONUS_WELFARE_FUND3 = $("input[name='BONUS_WELFARE_FUND3']").val();
	
	var RESERVE_FUND={};
	RESERVE_FUND.RESERVE_FUND1 = $("input[name='RESERVE_FUND1']").val();
	RESERVE_FUND.RESERVE_FUND2 = $("input[name='RESERVE_FUND2']").val();
	RESERVE_FUND.RESERVE_FUND3 = $("input[name='RESERVE_FUND3']").val();
	
	var DEVELOPMENT_FUND={};
	DEVELOPMENT_FUND.DEVELOPMENT_FUND1 = $("input[name='DEVELOPMENT_FUND1']").val();
	DEVELOPMENT_FUND.DEVELOPMENT_FUND2 = $("input[name='DEVELOPMENT_FUND2']").val();
	DEVELOPMENT_FUND.DEVELOPMENT_FUND3 = $("input[name='DEVELOPMENT_FUND3']").val();
	
	var PROFIT_RETURN_INVESTMENT={};
	PROFIT_RETURN_INVESTMENT.PROFIT_RETURN_INVESTMENT1 = $("input[name='PROFIT_RETURN_INVESTMENT1']").val();
	PROFIT_RETURN_INVESTMENT.PROFIT_RETURN_INVESTMENT2 = $("input[name='PROFIT_RETURN_INVESTMENT2']").val();
	PROFIT_RETURN_INVESTMENT.PROFIT_RETURN_INVESTMENT3 = $("input[name='PROFIT_RETURN_INVESTMENT3']").val();
	
	var KGFPLR_A={};
	KGFPLR_A.KGFPLR_A1 = $("input[name='KGFPLR_A1']").val();
	KGFPLR_A.KGFPLR_A2 = $("input[name='KGFPLR_A2']").val();
	KGFPLR_A.KGFPLR_A3 = $("input[name='KGFPLR_A3']").val();
	
	var KGFPLR_B={};
	KGFPLR_B.KGFPLR_B1 = $("input[name='KGFPLR_B1']").val();
	KGFPLR_B.KGFPLR_B2 = $("input[name='KGFPLR_B2']").val();
	KGFPLR_B.KGFPLR_B3 = $("input[name='KGFPLR_B3']").val();
	
	var INVESTOR_PROFIT={};
	INVESTOR_PROFIT.INVESTOR_PROFIT1 = $("input[name='INVESTOR_PROFIT1']").val();
	INVESTOR_PROFIT.INVESTOR_PROFIT2 = $("input[name='INVESTOR_PROFIT2']").val();
	INVESTOR_PROFIT.INVESTOR_PROFIT3 = $("input[name='INVESTOR_PROFIT3']").val();
	
	var PAININ_DIVIDEND={};
	PAININ_DIVIDEND.PAININ_DIVIDEND1 = $("input[name='PAININ_DIVIDEND1']").val();
	PAININ_DIVIDEND.PAININ_DIVIDEND2 = $("input[name='PAININ_DIVIDEND2']").val();
	PAININ_DIVIDEND.PAININ_DIVIDEND3 = $("input[name='PAININ_DIVIDEND3']").val();
	
	var EXTRACTION_SURPLUS_RESERVE={};
	EXTRACTION_SURPLUS_RESERVE.EXTRACTION_SURPLUS_RESERVE1 = $("input[name='EXTRACTION_SURPLUS_RESERVE1']").val();
	EXTRACTION_SURPLUS_RESERVE.EXTRACTION_SURPLUS_RESERVE2 = $("input[name='EXTRACTION_SURPLUS_RESERVE2']").val();
	EXTRACTION_SURPLUS_RESERVE.EXTRACTION_SURPLUS_RESERVE3 = $("input[name='EXTRACTION_SURPLUS_RESERVE3']").val();
	
	var PAIDIN_ORDINARY_FUND={};
	PAIDIN_ORDINARY_FUND.PAIDIN_ORDINARY_FUND1 = $("input[name='PAIDIN_ORDINARY_FUND1']").val();
	PAIDIN_ORDINARY_FUND.PAIDIN_ORDINARY_FUND2 = $("input[name='PAIDIN_ORDINARY_FUND2']").val();
	PAIDIN_ORDINARY_FUND.PAIDIN_ORDINARY_FUND3 = $("input[name='PAIDIN_ORDINARY_FUND3']").val();
	
	var CAPITAL_FUND={};
	CAPITAL_FUND.CAPITAL_FUND1 = $("input[name='CAPITAL_FUND1']").val();
	CAPITAL_FUND.CAPITAL_FUND2 = $("input[name='CAPITAL_FUND2']").val();
	CAPITAL_FUND.CAPITAL_FUND3 = $("input[name='CAPITAL_FUND3']").val();
	
	var UNDISTR_PROFIT={};
	UNDISTR_PROFIT.UNDISTR_PROFIT1 = $("input[name='UNDISTR_PROFIT1']").val();
	UNDISTR_PROFIT.UNDISTR_PROFIT2 = $("input[name='UNDISTR_PROFIT2']").val();
	UNDISTR_PROFIT.UNDISTR_PROFIT3 = $("input[name='UNDISTR_PROFIT3']").val();
	
	var UNDIST_PROFIT_DIFF={};
	UNDIST_PROFIT_DIFF.UNDIST_PROFIT_DIFF1 = $("input[name='UNDIST_PROFIT_DIFF1']").val();
	UNDIST_PROFIT_DIFF.UNDIST_PROFIT_DIFF2 = $("input[name='UNDIST_PROFIT_DIFF2']").val();
	UNDIST_PROFIT_DIFF.UNDIST_PROFIT_DIFF3 = $("input[name='UNDIST_PROFIT_DIFF3']").val();
	
	var SURPLUS_RESERVE_RATE={};
	SURPLUS_RESERVE_RATE.SURPLUS_RESERVE_RATE1 = $("input[name='SURPLUS_RESERVE_RATE1']").val();
	SURPLUS_RESERVE_RATE.SURPLUS_RESERVE_RATE2 = $("input[name='SURPLUS_RESERVE_RATE2']").val();
	SURPLUS_RESERVE_RATE.SURPLUS_RESERVE_RATE3 = $("input[name='SURPLUS_RESERVE_RATE3']").val();
	
	var WELFARE_FUND_RATE={};
	WELFARE_FUND_RATE.WELFARE_FUND_RATE1 = $("input[name='WELFARE_FUND_RATE1']").val();
	WELFARE_FUND_RATE.WELFARE_FUND_RATE2 = $("input[name='WELFARE_FUND_RATE2']").val();
	WELFARE_FUND_RATE.WELFARE_FUND_RATE3 = $("input[name='WELFARE_FUND_RATE3']").val();
	
	var INCOME_TAX_RATE={};
	INCOME_TAX_RATE.INCOME_TAX_RATE1 = $("input[name='INCOME_TAX_RATE1']").val();
	INCOME_TAX_RATE.INCOME_TAX_RATE2 = $("input[name='INCOME_TAX_RATE2']").val();
	INCOME_TAX_RATE.INCOME_TAX_RATE3 = $("input[name='INCOME_TAX_RATE3']").val();
	
	var INTEREST_EXPENSES={};
	INTEREST_EXPENSES.INTEREST_EXPENSES1 = $("input[name='INTEREST_EXPENSES1']").val();
	INTEREST_EXPENSES.INTEREST_EXPENSES2 = $("input[name='INTEREST_EXPENSES2']").val();
	INTEREST_EXPENSES.INTEREST_EXPENSES3 = $("input[name='INTEREST_EXPENSES3']").val();
	
	var LONG_TERM_DEBT_BURDEN={};
	LONG_TERM_DEBT_BURDEN.LONG_TERM_DEBT_BURDEN1 = $("input[name='LONG_TERM_DEBT_BURDEN1']").val();
	LONG_TERM_DEBT_BURDEN.LONG_TERM_DEBT_BURDEN2 = $("input[name='LONG_TERM_DEBT_BURDEN2']").val();
	LONG_TERM_DEBT_BURDEN.LONG_TERM_DEBT_BURDEN3 = $("input[name='LONG_TERM_DEBT_BURDEN3']").val();
	
	var CURRENT_DEBT_BURDEN={};
	CURRENT_DEBT_BURDEN.CURRENT_DEBT_BURDEN1 = $("input[name='CURRENT_DEBT_BURDEN1']").val();
	CURRENT_DEBT_BURDEN.CURRENT_DEBT_BURDEN2 = $("input[name='CURRENT_DEBT_BURDEN2']").val();
	CURRENT_DEBT_BURDEN.CURRENT_DEBT_BURDEN3 = $("input[name='CURRENT_DEBT_BURDEN3']").val();
	
	var NET_SALES={};
	NET_SALES.NET_SALES1 = $("input[name='NET_SALES1']").val();
	NET_SALES.NET_SALES2 = $("input[name='NET_SALES2']").val();
	NET_SALES.NET_SALES3 = $("input[name='NET_SALES3']").val();
	
	var IN_DATE={};
	IN_DATE.IN_DATE1 = $("input[name='IN_DATE1']").val();
	IN_DATE.IN_DATE2 = $("input[name='IN_DATE2']").val();
	IN_DATE.IN_DATE3 = $("input[name='IN_DATE3']").val();
	
	var dataJson ={
			MAIN_INCOME:JSON.stringify(MAIN_INCOME),
			MAIN_COST:JSON.stringify(MAIN_COST),
			MAIN_TAXES:JSON.stringify(MAIN_TAXES),
			MAIN_PROFIT:JSON.stringify(MAIN_PROFIT),
			MAIN_OTHER_PROFIT:JSON.stringify(MAIN_OTHER_PROFIT),
			MAIN_OPER_COST:JSON.stringify(MAIN_OPER_COST),
			MAIN_MAN_FEE:JSON.stringify(MAIN_MAN_FEE),
			MAIN_FINNA_FEE:JSON.stringify(MAIN_FINNA_FEE),
			OPER_PROFIT:JSON.stringify(OPER_PROFIT),
			INVESTMENT_PROFIT:JSON.stringify(INVESTMENT_PROFIT),
			SUBSIDY_INCOME:JSON.stringify(SUBSIDY_INCOME),
			OPER_INCOME:JSON.stringify(OPER_INCOME),
			OPER_EXPENDITURE:JSON.stringify(OPER_EXPENDITURE),
			PROFIT_TOTAL:JSON.stringify(PROFIT_TOTAL),
			INCOME_TAX:JSON.stringify(INCOME_TAX),
			PROFIT_NET:JSON.stringify(PROFIT_NET),
			NOT_ASSIGNED_PROFIT:JSON.stringify(NOT_ASSIGNED_PROFIT),
			OTHER_TRANSFER:JSON.stringify(OTHER_TRANSFER),
			DISTRIBUTION_PROFIT:JSON.stringify(DISTRIBUTION_PROFIT),
			STATU_SURPLUS_RESERVE:JSON.stringify(STATU_SURPLUS_RESERVE),
			STATU_SURPLUS_FUND:JSON.stringify(STATU_SURPLUS_FUND),
			BONUS_WELFARE_FUND:JSON.stringify(BONUS_WELFARE_FUND),
			RESERVE_FUND:JSON.stringify(RESERVE_FUND),
			DEVELOPMENT_FUND:JSON.stringify(DEVELOPMENT_FUND),
			PROFIT_RETURN_INVESTMENT:JSON.stringify(PROFIT_RETURN_INVESTMENT),
			KGFPLR_A:JSON.stringify(KGFPLR_A),
			KGFPLR_B:JSON.stringify(KGFPLR_B),
			INVESTOR_PROFIT:JSON.stringify(INVESTOR_PROFIT),
			PAININ_DIVIDEND:JSON.stringify(PAININ_DIVIDEND),
			EXTRACTION_SURPLUS_RESERVE:JSON.stringify(EXTRACTION_SURPLUS_RESERVE),
			PAIDIN_ORDINARY_FUND:JSON.stringify(PAIDIN_ORDINARY_FUND),
			CAPITAL_FUND:JSON.stringify(CAPITAL_FUND),
			UNDISTR_PROFIT:JSON.stringify(UNDISTR_PROFIT),
			UNDIST_PROFIT_DIFF:JSON.stringify(UNDIST_PROFIT_DIFF),
			SURPLUS_RESERVE_RATE:JSON.stringify(SURPLUS_RESERVE_RATE),
			WELFARE_FUND_RATE:JSON.stringify(WELFARE_FUND_RATE),
			INCOME_TAX_RATE:JSON.stringify(INCOME_TAX_RATE),
			INTEREST_EXPENSES:JSON.stringify(INTEREST_EXPENSES),
			LONG_TERM_DEBT_BURDEN:JSON.stringify(LONG_TERM_DEBT_BURDEN),
			CURRENT_DEBT_BURDEN:JSON.stringify(CURRENT_DEBT_BURDEN),
			IN_DATE:JSON.stringify(IN_DATE),
			NET_SALES:JSON.stringify(NET_SALES),
			UNIT_NAME:$("input[name='UNIT_NAME']").val(),
			CHECK_PEOPLE:$("input[name='CHECK_PEOPLE']").val(),
			CHECK_DATE:$("input[name='CHECK_DATE']").val(),
			REVIEW_PEOPLE:$("input[name='REVIEW_PEOPLE']").val(),
			REVIEW_DATE:$("input[name='REVIEW_DATE']").val(),
			INDEX_CODE:$("input[name='INDEX_CODE']").val(),
			BELONG_DATE:$("input[name='BELONG_DATE']").val(),
			PROJECT_ID:$("input[name='PROJECT_ID']").val(),
			CREADE_CODE:$("input[name='CREADE_CODE']").val()
	};
	$("#ChangeViewData").val(JSON.stringify(dataJson));
	$("#formView").ajaxSubmit({
		success:function (data){
				alert("保存成功");
		}
	});
	
	

}

//保存现金流量表
function saveFinnce_Cash()
{
	var PROVIDE_SERVICE_CASH={};
	PROVIDE_SERVICE_CASH.PROVIDE_SERVICE_CASH1 = $("input[name='PROVIDE_SERVICE_CASH1']").val();
	PROVIDE_SERVICE_CASH.PROVIDE_SERVICE_CASH2 = $("input[name='PROVIDE_SERVICE_CASH2']").val();
	PROVIDE_SERVICE_CASH.PROVIDE_SERVICE_CASH3 = $("input[name='PROVIDE_SERVICE_CASH3']").val();
	
	var TAX_RETURN={};
	TAX_RETURN.TAX_RETURN1 = $("input[name='TAX_RETURN1']").val();
	TAX_RETURN.TAX_RETURN2 = $("input[name='TAX_RETURN2']").val();
	TAX_RETURN.TAX_RETURN3 = $("input[name='TAX_RETURN3']").val();
	
	var OTHER_OPERATE={};
	OTHER_OPERATE.OTHER_OPERATE1 = $("input[name='OTHER_OPERATE1']").val();
	OTHER_OPERATE.OTHER_OPERATE2 = $("input[name='OTHER_OPERATE2']").val();
	OTHER_OPERATE.OTHER_OPERATE3 = $("input[name='OTHER_OPERATE3']").val();
	
	var OPERATE_INTO_TOTAL={};
	OPERATE_INTO_TOTAL.OPERATE_INTO_TOTAL1 = $("input[name='OPERATE_INTO_TOTAL1']").val();
	OPERATE_INTO_TOTAL.OPERATE_INTO_TOTAL2 = $("input[name='OPERATE_INTO_TOTAL2']").val();
	OPERATE_INTO_TOTAL.OPERATE_INTO_TOTAL3 = $("input[name='OPERATE_INTO_TOTAL3']").val();
	
	var PROVIDE_PAYMENT_CASH={};
	PROVIDE_PAYMENT_CASH.PROVIDE_PAYMENT_CASH1 = $("input[name='PROVIDE_PAYMENT_CASH1']").val();
	PROVIDE_PAYMENT_CASH.PROVIDE_PAYMENT_CASH2 = $("input[name='PROVIDE_PAYMENT_CASH2']").val();
	PROVIDE_PAYMENT_CASH.PROVIDE_PAYMENT_CASH3 = $("input[name='PROVIDE_PAYMENT_CASH3']").val();
	
	var PAYMENT_STAFF_CASH={};
	PAYMENT_STAFF_CASH.PAYMENT_STAFF_CASH1 = $("input[name='PAYMENT_STAFF_CASH1']").val();
	PAYMENT_STAFF_CASH.PAYMENT_STAFF_CASH2 = $("input[name='PAYMENT_STAFF_CASH2']").val();
	PAYMENT_STAFF_CASH.PAYMENT_STAFF_CASH3 = $("input[name='PAYMENT_STAFF_CASH3']").val();
	
	var PAY_ITEM_TAX={};
	PAY_ITEM_TAX.PAY_ITEM_TAX1 = $("input[name='PAY_ITEM_TAX1']").val();
	PAY_ITEM_TAX.PAY_ITEM_TAX2 = $("input[name='PAY_ITEM_TAX2']").val();
	PAY_ITEM_TAX.PAY_ITEM_TAX3 = $("input[name='PAY_ITEM_TAX3']").val();
	
	var PAY_PROVIDE_CASH={};
	PAY_PROVIDE_CASH.PAY_PROVIDE_CASH1 = $("input[name='PAY_PROVIDE_CASH1']").val();
	PAY_PROVIDE_CASH.PAY_PROVIDE_CASH2 = $("input[name='PAY_PROVIDE_CASH2']").val();
	PAY_PROVIDE_CASH.PAY_PROVIDE_CASH3 = $("input[name='PAY_PROVIDE_CASH3']").val();
	
	var OPERATE_OUT_TOTAL={};
	OPERATE_OUT_TOTAL.OPERATE_OUT_TOTAL1 = $("input[name='OPERATE_OUT_TOTAL1']").val();
	OPERATE_OUT_TOTAL.OPERATE_OUT_TOTAL2 = $("input[name='OPERATE_OUT_TOTAL2']").val();
	OPERATE_OUT_TOTAL.OPERATE_OUT_TOTAL3 = $("input[name='OPERATE_OUT_TOTAL3']").val();
	
	var OPERATE_NET={};
	OPERATE_NET.OPERATE_NET1 = $("input[name='OPERATE_NET1']").val();
	OPERATE_NET.OPERATE_NET2 = $("input[name='OPERATE_NET2']").val();
	OPERATE_NET.OPERATE_NET3 = $("input[name='OPERATE_NET3']").val();
	
	var INVEST_CASH={};
	INVEST_CASH.INVEST_CASH1 = $("input[name='INVEST_CASH1']").val();
	INVEST_CASH.INVEST_CASH2 = $("input[name='INVEST_CASH2']").val();
	INVEST_CASH.INVEST_CASH3 = $("input[name='INVEST_CASH3']").val();
	
	var INVEST_INCOME={};
	INVEST_INCOME.INVEST_INCOME1 = $("input[name='INVEST_INCOME1']").val();
	INVEST_INCOME.INVEST_INCOME2 = $("input[name='INVEST_INCOME2']").val();
	INVEST_INCOME.INVEST_INCOME3 = $("input[name='INVEST_INCOME3']").val();
	
	var ASSETS_CASH={};
	ASSETS_CASH.ASSETS_CASH1 = $("input[name='ASSETS_CASH1']").val();
	ASSETS_CASH.ASSETS_CASH2 = $("input[name='ASSETS_CASH2']").val();
	ASSETS_CASH.ASSETS_CASH3 = $("input[name='ASSETS_CASH3']").val();
	
	var OTHER_INVEST={};
	OTHER_INVEST.OTHER_INVEST1 = $("input[name='OTHER_INVEST1']").val();
	OTHER_INVEST.OTHER_INVEST2 = $("input[name='OTHER_INVEST2']").val();
	OTHER_INVEST.OTHER_INVEST3 = $("input[name='OTHER_INVEST3']").val();
	
	var INVEST_INTO={};
	INVEST_INTO.INVEST_INTO1 = $("input[name='INVEST_INTO1']").val();
	INVEST_INTO.INVEST_INTO2 = $("input[name='INVEST_INTO2']").val();
	INVEST_INTO.INVEST_INTO3 = $("input[name='INVEST_INTO3']").val();
	
	var ASSET_PAY={};
	ASSET_PAY.ASSET_PAY1 = $("input[name='ASSET_PAY1']").val();
	ASSET_PAY.ASSET_PAY2 = $("input[name='ASSET_PAY2']").val();
	ASSET_PAY.ASSET_PAY3 = $("input[name='ASSET_PAY3']").val();
	
	var INVEST_PAY={};
	INVEST_PAY.INVEST_PAY1 = $("input[name='INVEST_PAY1']").val();
	INVEST_PAY.INVEST_PAY2 = $("input[name='INVEST_PAY2']").val();
	INVEST_PAY.INVEST_PAY3 = $("input[name='INVEST_PAY3']").val();
	
	var OTHER_INVEST_PAY={};
	OTHER_INVEST_PAY.OTHER_INVEST_PAY1 = $("input[name='OTHER_INVEST_PAY1']").val();
	OTHER_INVEST_PAY.OTHER_INVEST_PAY2 = $("input[name='OTHER_INVEST_PAY2']").val();
	OTHER_INVEST_PAY.OTHER_INVEST_PAY3 = $("input[name='OTHER_INVEST_PAY3']").val();
	
	var INVEST_OUT={};
	INVEST_OUT.INVEST_OUT1 = $("input[name='INVEST_OUT1']").val();
	INVEST_OUT.INVEST_OUT2 = $("input[name='INVEST_OUT2']").val();
	INVEST_OUT.INVEST_OUT3 = $("input[name='INVEST_OUT3']").val();
	
	var INVEST_NET={};
	INVEST_NET.INVEST_NET1 = $("input[name='INVEST_NET1']").val();
	INVEST_NET.INVEST_NET2 = $("input[name='INVEST_NET2']").val();
	INVEST_NET.INVEST_NET3 = $("input[name='INVEST_NET3']").val();
	
	var ABSORB_INVEST={};
	ABSORB_INVEST.ABSORB_INVEST1 = $("input[name='ABSORB_INVEST1']").val();
	ABSORB_INVEST.ABSORB_INVEST2 = $("input[name='ABSORB_INVEST2']").val();
	ABSORB_INVEST.ABSORB_INVEST3 = $("input[name='ABSORB_INVEST3']").val();
	
	var LOAN={};
	LOAN.LOAN1 = $("input[name='LOAN1']").val();
	LOAN.LOAN2 = $("input[name='LOAN2']").val();
	LOAN.LOAN3 = $("input[name='LOAN3']").val();
	
	var OTHER_PREPARE_IN={};
	OTHER_PREPARE_IN.OTHER_PREPARE_IN1 = $("input[name='OTHER_PREPARE_IN1']").val();
	OTHER_PREPARE_IN.OTHER_PREPARE_IN2 = $("input[name='OTHER_PREPARE_IN2']").val();
	OTHER_PREPARE_IN.OTHER_PREPARE_IN3 = $("input[name='OTHER_PREPARE_IN3']").val();
	
	var PREPARE_INCOM_TOTAL={};
	PREPARE_INCOM_TOTAL.PREPARE_INCOM_TOTAL1 = $("input[name='PREPARE_INCOM_TOTAL1']").val();
	PREPARE_INCOM_TOTAL.PREPARE_INCOM_TOTAL2 = $("input[name='PREPARE_INCOM_TOTAL2']").val();
	PREPARE_INCOM_TOTAL.PREPARE_INCOM_TOTAL3 = $("input[name='PREPARE_INCOM_TOTAL3']").val();
	
	var DEBT_PAY={};
	DEBT_PAY.DEBT_PAY1 = $("input[name='DEBT_PAY1']").val();
	DEBT_PAY.DEBT_PAY2 = $("input[name='DEBT_PAY2']").val();
	DEBT_PAY.DEBT_PAY3 = $("input[name='DEBT_PAY3']").val();
	
	var INTEREST_PAY={};
	INTEREST_PAY.INTEREST_PAY1 = $("input[name='INTEREST_PAY1']").val();
	INTEREST_PAY.INTEREST_PAY2 = $("input[name='INTEREST_PAY2']").val();
	INTEREST_PAY.INTEREST_PAY3 = $("input[name='INTEREST_PAY3']").val();
	
	var OTHER_PREPARE_PAY={};
	OTHER_PREPARE_PAY.OTHER_PREPARE_PAY1 = $("input[name='OTHER_PREPARE_PAY1']").val();
	OTHER_PREPARE_PAY.OTHER_PREPARE_PAY2 = $("input[name='OTHER_PREPARE_PAY2']").val();
	OTHER_PREPARE_PAY.OTHER_PREPARE_PAY3 = $("input[name='OTHER_PREPARE_PAY3']").val();
	
	var PREPARE_OUT_TOTAL={};
	PREPARE_OUT_TOTAL.PREPARE_OUT_TOTAL1 = $("input[name='PREPARE_OUT_TOTAL1']").val();
	PREPARE_OUT_TOTAL.PREPARE_OUT_TOTAL2 = $("input[name='PREPARE_OUT_TOTAL2']").val();
	PREPARE_OUT_TOTAL.PREPARE_OUT_TOTAL3 = $("input[name='PREPARE_OUT_TOTAL3']").val();
	
	var PREPARE_NET={};
	PREPARE_NET.PREPARE_NET1 = $("input[name='PREPARE_NET1']").val();
	PREPARE_NET.PREPARE_NET2 = $("input[name='PREPARE_NET2']").val();
	PREPARE_NET.PREPARE_NET3 = $("input[name='PREPARE_NET3']").val();
	
	var CHANGE_RATE={};
	CHANGE_RATE.CHANGE_RATE1 = $("input[name='CHANGE_RATE1']").val();
	CHANGE_RATE.CHANGE_RATE2 = $("input[name='CHANGE_RATE2']").val();
	CHANGE_RATE.CHANGE_RATE3 = $("input[name='CHANGE_RATE3']").val();
	
	var CASH_NET={};
	CASH_NET.CASH_NET1 = $("input[name='CASH_NET1']").val();
	CASH_NET.CASH_NET2 = $("input[name='CASH_NET2']").val();
	CASH_NET.CASH_NET3 = $("input[name='CASH_NET3']").val();
	
	var NET={};
	NET.NET1 = $("input[name='NET1']").val();
	NET.NET2 = $("input[name='NET2']").val();
	NET.NET3 = $("input[name='NET3']").val();
	
	var ASSET_REDUCE={};
	ASSET_REDUCE.ASSET_REDUCE1 = $("input[name='ASSET_REDUCE1']").val();
	ASSET_REDUCE.ASSET_REDUCE2 = $("input[name='ASSET_REDUCE2']").val();
	ASSET_REDUCE.ASSET_REDUCE3 = $("input[name='ASSET_REDUCE3']").val();
	
	var FIXED_DEP={};
	FIXED_DEP.FIXED_DEP1 = $("input[name='FIXED_DEP1']").val();
	FIXED_DEP.FIXED_DEP2 = $("input[name='FIXED_DEP2']").val();
	FIXED_DEP.FIXED_DEP3 = $("input[name='FIXED_DEP3']").val();
	
	var INVISIBLE_DEP={};
	INVISIBLE_DEP.INVISIBLE_DEP1 = $("input[name='INVISIBLE_DEP1']").val();
	INVISIBLE_DEP.INVISIBLE_DEP2 = $("input[name='INVISIBLE_DEP2']").val();
	INVISIBLE_DEP.INVISIBLE_DEP3 = $("input[name='INVISIBLE_DEP3']").val();
	
	var PREPAID_FEE={};
	PREPAID_FEE.PREPAID_FEE1 = $("input[name='PREPAID_FEE1']").val();
	PREPAID_FEE.PREPAID_FEE2 = $("input[name='PREPAID_FEE2']").val();
	PREPAID_FEE.PREPAID_FEE3 = $("input[name='PREPAID_FEE3']").val();
	
	var PREPAID_REDUCE={};
	PREPAID_REDUCE.PREPAID_REDUCE1 = $("input[name='PREPAID_REDUCE1']").val();
	PREPAID_REDUCE.PREPAID_REDUCE2 = $("input[name='PREPAID_REDUCE2']").val();
	PREPAID_REDUCE.PREPAID_REDUCE3 = $("input[name='PREPAID_REDUCE3']").val();
	
	var HOLD_ADD={};
	HOLD_ADD.HOLD_ADD1 = $("input[name='HOLD_ADD1']").val();
	HOLD_ADD.HOLD_ADD2 = $("input[name='HOLD_ADD2']").val();
	HOLD_ADD.HOLD_ADD3 = $("input[name='HOLD_ADD3']").val();
	
	var ASSET_LOSS={};
	ASSET_LOSS.ASSET_LOSS1 = $("input[name='ASSET_LOSS1']").val();
	ASSET_LOSS.ASSET_LOSS2 = $("input[name='ASSET_LOSS2']").val();
	ASSET_LOSS.ASSET_LOSS3 = $("input[name='ASSET_LOSS3']").val();
	
	var FIXED_LOSS={};
	FIXED_LOSS.FIXED_LOSS1 = $("input[name='FIXED_LOSS1']").val();
	FIXED_LOSS.FIXED_LOSS2 = $("input[name='FIXED_LOSS2']").val();
	FIXED_LOSS.FIXED_LOSS3 = $("input[name='FIXED_LOSS3']").val();
	
	var FINANCE_FEE={};
	FINANCE_FEE.FINANCE_FEE1 = $("input[name='FINANCE_FEE1']").val();
	FINANCE_FEE.FINANCE_FEE2 = $("input[name='FINANCE_FEE2']").val();
	FINANCE_FEE.FINANCE_FEE3 = $("input[name='FINANCE_FEE3']").val();
	
	var INVEST_LOSS={};
	INVEST_LOSS.INVEST_LOSS1 = $("input[name='INVEST_LOSS1']").val();
	INVEST_LOSS.INVEST_LOSS2 = $("input[name='INVEST_LOSS2']").val();
	INVEST_LOSS.INVEST_LOSS3 = $("input[name='INVEST_LOSS3']").val();
	
	var DEFERR={};
	DEFERR.DEFERR1 = $("input[name='DEFERR1']").val();
	DEFERR.DEFERR2 = $("input[name='DEFERR2']").val();
	DEFERR.DEFERR3 = $("input[name='DEFERR3']").val();
	
	var STOCK_REDUCE={};
	STOCK_REDUCE.STOCK_REDUCE1 = $("input[name='STOCK_REDUCE1']").val();
	STOCK_REDUCE.STOCK_REDUCE2 = $("input[name='STOCK_REDUCE2']").val();
	STOCK_REDUCE.STOCK_REDUCE3 = $("input[name='STOCK_REDUCE3']").val();
	
	var OPERATE_INCOME={};
	OPERATE_INCOME.OPERATE_INCOME1 = $("input[name='OPERATE_INCOME1']").val();
	OPERATE_INCOME.OPERATE_INCOME2 = $("input[name='OPERATE_INCOME2']").val();
	OPERATE_INCOME.OPERATE_INCOME3 = $("input[name='OPERATE_INCOME3']").val();
	
	var OPERATE_PAY={};
	OPERATE_PAY.OPERATE_PAY1 = $("input[name='OPERATE_PAY1']").val();
	OPERATE_PAY.OPERATE_PAY2 = $("input[name='OPERATE_PAY2']").val();
	OPERATE_PAY.OPERATE_PAY3 = $("input[name='OPERATE_PAY3']").val();
	
	var OTHE={};
	OTHE.OTHE1 = $("input[name='OTHE1']").val();
	OTHE.OTHE2 = $("input[name='OTHE2']").val();
	OTHE.OTHE3 = $("input[name='OTHE3']").val();
	
	var OPERATE_NET_D={};
	OPERATE_NET_D.OPERATE_NET_D1 = $("input[name='OPERATE_NET_D1']").val();
	OPERATE_NET_D.OPERATE_NET_D2 = $("input[name='OPERATE_NET_D2']").val();
	OPERATE_NET_D.OPERATE_NET_D3 = $("input[name='OPERATE_NET_D3']").val();
	
	var DEBT_TO_CAPITAL={};
	DEBT_TO_CAPITAL.DEBT_TO_CAPITAL1 = $("input[name='DEBT_TO_CAPITAL1']").val();
	DEBT_TO_CAPITAL.DEBT_TO_CAPITAL2 = $("input[name='DEBT_TO_CAPITAL2']").val();
	DEBT_TO_CAPITAL.DEBT_TO_CAPITAL3 = $("input[name='DEBT_TO_CAPITAL3']").val();
	
	var YEAR_BOND={};
	YEAR_BOND.YEAR_BOND1 = $("input[name='YEAR_BOND1']").val();
	YEAR_BOND.YEAR_BOND2 = $("input[name='YEAR_BOND2']").val();
	YEAR_BOND.YEAR_BOND3 = $("input[name='YEAR_BOND3']").val();
	
	var FINANCE={};
	FINANCE.FINANCE1 = $("input[name='FINANCE1']").val();
	FINANCE.FINANCE2 = $("input[name='FINANCE2']").val();
	FINANCE.FINANCE3 = $("input[name='FINANCE3']").val();
	
	var END_BALANCE={};
	END_BALANCE.END_BALANCE1 = $("input[name='END_BALANCE1']").val();
	END_BALANCE.END_BALANCE2 = $("input[name='END_BALANCE2']").val();
	END_BALANCE.END_BALANCE3 = $("input[name='END_BALANCE3']").val();
	
	var BEGIN_BALANCE={};
	BEGIN_BALANCE.BEGIN_BALANCE1 = $("input[name='BEGIN_BALANCE1']").val();
	BEGIN_BALANCE.BEGIN_BALANCE2 = $("input[name='BEGIN_BALANCE2']").val();
	BEGIN_BALANCE.BEGIN_BALANCE3 = $("input[name='BEGIN_BALANCE3']").val();
	
	var THING_END_BALANCE={};
	THING_END_BALANCE.THING_END_BALANCE1 = $("input[name='THING_END_BALANCE1']").val();
	THING_END_BALANCE.THING_END_BALANCE2 = $("input[name='THING_END_BALANCE2']").val();
	THING_END_BALANCE.THING_END_BALANCE3 = $("input[name='THING_END_BALANCE3']").val();
	
	var THING_BEGIN_BALANCE={};
	THING_BEGIN_BALANCE.THING_BEGIN_BALANCE1 = $("input[name='THING_BEGIN_BALANCE1']").val();
	THING_BEGIN_BALANCE.THING_BEGIN_BALANCE2 = $("input[name='THING_BEGIN_BALANCE2']").val();
	THING_BEGIN_BALANCE.THING_BEGIN_BALANCE3 = $("input[name='THING_BEGIN_BALANCE3']").val();
	
	
	var CASH_INCREASE={};
	CASH_INCREASE.CASH_INCREASE1 = $("input[name='CASH_INCREASE1']").val();
	CASH_INCREASE.CASH_INCREASE2 = $("input[name='CASH_INCREASE2']").val();
	CASH_INCREASE.CASH_INCREASE3 = $("input[name='CASH_INCREASE3']").val();
	
	var A_COMPARE_D={};
	A_COMPARE_D.A_COMPARE_D1 = $("input[name='A_COMPARE_D1']").val();
	A_COMPARE_D.A_COMPARE_D2 = $("input[name='A_COMPARE_D2']").val();
	A_COMPARE_D.A_COMPARE_D3 = $("input[name='A_COMPARE_D3']").val();
	
	var B_COMPARE_G={};
	B_COMPARE_G.B_COMPARE_G1 = $("input[name='B_COMPARE_G1']").val();
	B_COMPARE_G.B_COMPARE_G2 = $("input[name='B_COMPARE_G2']").val();
	B_COMPARE_G.B_COMPARE_G3 = $("input[name='B_COMPARE_G3']").val();
	
	var C_COMPARE_PROFIT={};
	C_COMPARE_PROFIT.C_COMPARE_PROFIT1 = $("input[name='C_COMPARE_PROFIT1']").val();
	C_COMPARE_PROFIT.C_COMPARE_PROFIT2 = $("input[name='C_COMPARE_PROFIT2']").val();
	C_COMPARE_PROFIT.C_COMPARE_PROFIT3 = $("input[name='C_COMPARE_PROFIT3']").val();
	
	var E_COMPARE_BEGIN={};
	E_COMPARE_BEGIN.E_COMPARE_BEGIN1 = $("input[name='E_COMPARE_BEGIN1']").val();
	E_COMPARE_BEGIN.E_COMPARE_BEGIN2 = $("input[name='E_COMPARE_BEGIN2']").val();
	E_COMPARE_BEGIN.E_COMPARE_BEGIN3 = $("input[name='E_COMPARE_BEGIN3']").val();
	
	var E_COMPARE_END={};
	E_COMPARE_END.E_COMPARE_END1 = $("input[name='E_COMPARE_END1']").val();
	E_COMPARE_END.E_COMPARE_END2 = $("input[name='E_COMPARE_END2']").val();
	E_COMPARE_END.E_COMPARE_END3 = $("input[name='E_COMPARE_END3']").val();
	
	var F_COMPARE_BEGIN={};
	F_COMPARE_BEGIN.F_COMPARE_BEGIN1 = $("input[name='F_COMPARE_BEGIN1']").val();
	F_COMPARE_BEGIN.F_COMPARE_BEGIN2 = $("input[name='F_COMPARE_BEGIN2']").val();
	F_COMPARE_BEGIN.F_COMPARE_BEGIN3 = $("input[name='F_COMPARE_BEGIN3']").val();
	
	var B_COMPARE_DEFFERENCE={};
	B_COMPARE_DEFFERENCE.B_COMPARE_DEFFERENCE1 = $("input[name='B_COMPARE_DEFFERENCE1']").val();
	B_COMPARE_DEFFERENCE.B_COMPARE_DEFFERENCE2 = $("input[name='B_COMPARE_DEFFERENCE2']").val();
	B_COMPARE_DEFFERENCE.B_COMPARE_DEFFERENCE3 = $("input[name='B_COMPARE_DEFFERENCE3']").val();
	
	var CASH_HOUJIA_VAVLE={};
	CASH_HOUJIA_VAVLE.CASH_HOUJIA_VAVLE1 = $("input[name='CASH_HOUJIA_VAVLE1']").val();
	CASH_HOUJIA_VAVLE.CASH_HOUJIA_VAVLE2 = $("input[name='CASH_HOUJIA_VAVLE2']").val();
	CASH_HOUJIA_VAVLE.CASH_HOUJIA_VAVLE3 = $("input[name='CASH_HOUJIA_VAVLE3']").val();
	
	var CASH_VALUE={};
	CASH_VALUE.CASH_VALUE1 = $("input[name='CASH_VALUE1']").val();
	CASH_VALUE.CASH_VALUE2 = $("input[name='CASH_VALUE2']").val();
	CASH_VALUE.CASH_VALUE3 = $("input[name='CASH_VALUE3']").val();
	
	var IN_DATE={};
	IN_DATE.IN_DATE1 = $("input[name='IN_DATE1']").val();
	IN_DATE.IN_DATE2 = $("input[name='IN_DATE2']").val();
	IN_DATE.IN_DATE3 = $("input[name='IN_DATE3']").val();
	
	var dataJson ={
			PROVIDE_SERVICE_CASH:JSON.stringify(PROVIDE_SERVICE_CASH),
			TAX_RETURN:JSON.stringify(TAX_RETURN),
			OTHER_OPERATE:JSON.stringify(OTHER_OPERATE),
			OPERATE_INTO_TOTAL:JSON.stringify(OPERATE_INTO_TOTAL),	
			PROVIDE_PAYMENT_CASH:JSON.stringify(PROVIDE_PAYMENT_CASH),
			PAYMENT_STAFF_CASH:JSON.stringify(PAYMENT_STAFF_CASH),
			PAY_ITEM_TAX:JSON.stringify(PAY_ITEM_TAX),
			PAY_PROVIDE_CASH:JSON.stringify(PAY_PROVIDE_CASH),
			OPERATE_OUT_TOTAL:JSON.stringify(OPERATE_OUT_TOTAL),
			OPERATE_NET:JSON.stringify(OPERATE_NET),
			INVEST_CASH:JSON.stringify(INVEST_CASH),
			INVEST_INCOME:JSON.stringify(INVEST_INCOME),
			ASSETS_CASH:JSON.stringify(ASSETS_CASH),
			OTHER_INVEST:JSON.stringify(OTHER_INVEST),
			INVEST_INTO:JSON.stringify(INVEST_INTO),
			ASSET_PAY:JSON.stringify(ASSET_PAY),
			INVEST_PAY:JSON.stringify(INVEST_PAY),
			OTHER_INVEST_PAY:JSON.stringify(OTHER_INVEST_PAY),
			INVEST_OUT:JSON.stringify(INVEST_OUT),
			INVEST_NET:JSON.stringify(INVEST_NET),
			ABSORB_INVEST:JSON.stringify(ABSORB_INVEST),
			LOAN:JSON.stringify(LOAN),
			OTHER_PREPARE_IN:JSON.stringify(OTHER_PREPARE_IN),
			PREPARE_INCOM_TOTAL:JSON.stringify(PREPARE_INCOM_TOTAL),
			DEBT_PAY:JSON.stringify(DEBT_PAY),
			INTEREST_PAY:JSON.stringify(INTEREST_PAY),
			OTHER_PREPARE_PAY:JSON.stringify(OTHER_PREPARE_PAY),
			PREPARE_OUT_TOTAL:JSON.stringify(PREPARE_OUT_TOTAL),
			PREPARE_NET:JSON.stringify(PREPARE_NET),
			CHANGE_RATE:JSON.stringify(CHANGE_RATE),
			CASH_NET:JSON.stringify(CASH_NET),
			NET:JSON.stringify(NET),
			ASSET_REDUCE:JSON.stringify(ASSET_REDUCE),
			FIXED_DEP:JSON.stringify(FIXED_DEP),
			INVISIBLE_DEP:JSON.stringify(INVISIBLE_DEP),
			PREPAID_FEE:JSON.stringify(PREPAID_FEE),
			PREPAID_REDUCE:JSON.stringify(PREPAID_REDUCE),
			HOLD_ADD:JSON.stringify(HOLD_ADD),
			ASSET_LOSS:JSON.stringify(ASSET_LOSS),
			FIXED_LOSS:JSON.stringify(FIXED_LOSS),
			FINANCE_FEE:JSON.stringify(FINANCE_FEE),
			INVEST_LOSS:JSON.stringify(INVEST_LOSS),
			DEFERR:JSON.stringify(DEFERR),	
			STOCK_REDUCE:JSON.stringify(STOCK_REDUCE),
			OPERATE_INCOME:JSON.stringify(OPERATE_INCOME),
			OPERATE_PAY:JSON.stringify(OPERATE_PAY),
			OTHE:JSON.stringify(OTHE),
			OPERATE_NET_D:JSON.stringify(OPERATE_NET_D),
			DEBT_TO_CAPITAL:JSON.stringify(DEBT_TO_CAPITAL),
			YEAR_BOND:JSON.stringify(YEAR_BOND),
			FINANCE:JSON.stringify(FINANCE),
			END_BALANCE:JSON.stringify(END_BALANCE),
			BEGIN_BALANCE:JSON.stringify(BEGIN_BALANCE),
			THING_END_BALANCE:JSON.stringify(THING_END_BALANCE),
			THING_BEGIN_BALANCE:JSON.stringify(THING_BEGIN_BALANCE),
			CASH_INCREASE:JSON.stringify(CASH_INCREASE),
			A_COMPARE_D:JSON.stringify(A_COMPARE_D),
			B_COMPARE_G:JSON.stringify(B_COMPARE_G),
			C_COMPARE_PROFIT:JSON.stringify(C_COMPARE_PROFIT),
			E_COMPARE_BEGIN:JSON.stringify(E_COMPARE_BEGIN),
			E_COMPARE_END:JSON.stringify(E_COMPARE_END),
			F_COMPARE_BEGIN:JSON.stringify(F_COMPARE_BEGIN),
			B_COMPARE_DEFFERENCE:JSON.stringify(B_COMPARE_DEFFERENCE),
			CASH_HOUJIA_VAVLE:JSON.stringify(CASH_HOUJIA_VAVLE),
			CASH_VALUE:JSON.stringify(CASH_VALUE),
			UNKNOWN_ONE:$("input[name='UNKNOWN_ONE']").val(),
			UNKNOWN_TWO:$("input[name='UNKNOWN_TWO']").val(),
			UNKNOWN_THREE:$("input[name='UNKNOWN_THREE']").val(),
			UNKNOWN_FOUR:$("input[name='UNKNOWN_FOUR']").val(),
			UNKNOWN_FIVE:$("input[name='UNKNOWN_FIVE']").val(),
			UNKNOWN_SIX:$("input[name='UNKNOWN_SIX']").val(),
			UNKNOWN_SEVEN:$("input[name='UNKNOWN_SEVEN']").val(),
			UNKNOWN_EIGHT:$("input[name='UNKNOWN_EIGHT']").val(),
			IN_DATE:JSON.stringify(IN_DATE),
			UNIT_NAME:$("input[name='UNIT_NAME']").val(),
			CHECK_PEOPLE:$("input[name='CHECK_PEOPLE']").val(),
			CHECK_DATE:$("input[name='CHECK_DATE']").val(),
			REVIEW_PEOPLE:$("input[name='REVIEW_PEOPLE']").val(),
			REVIEW_DATE:$("input[name='REVIEW_DATE']").val(),
			INDEX_CODE:$("input[name='INDEX_CODE']").val(),
			BELONG_DATE:$("input[name='BELONG_DATE']").val(),
			PROJECT_ID:$("input[name='PROJECT_ID']").val(),
			CREATE_CODE:$("input[name='CREATE_CODE']").val()
			
	};
	
	$("#ChangeViewData").val(JSON.stringify(dataJson));
	$("#formView").ajaxSubmit({
		success:function (data){
				alert("保存成功");
		}
	});

}




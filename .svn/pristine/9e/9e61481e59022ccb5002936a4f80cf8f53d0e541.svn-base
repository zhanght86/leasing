/**
 * 乘法
 * 
 * @param arg1
 * @param arg2
 * @return
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
}

/**
 * 加法
 * 
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

/**
 * 除法
 * 
 * @param arg1
 * @param arg2
 * @return
 */
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

/**
 * 减法
 * 
 * @param arg1
 * @param arg2
 * @return
 */
function accSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));//10 9
	n = (r1 >= r2) ? r1 : r2; //9
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 两数之减（输入坐标） arg3=arg1-arg2
function CoordinateAccSub(arg1, arg2, arg3) {
	var value1 = $("#" + arg1).val();// id 为arg1的值
	var value2 = $("#" + arg2).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}
	$("#" + arg1).val(anyDouble(value1, 2));
	$("#" + arg2).val(anyDouble(value2, 2));
	$("#" + arg3).val(anyDouble(accSub(value1, value2), 2));

	eval($("#" + arg3).attr("liandong"));
	eval($("#" + arg3).attr("liandong1"));
	eval($("#" + arg3).attr("liandong2"));
}

// 多数相加 contype标识第几列（B），num1,num2：第几行 . 拼起来应该是：B1，B2. agr3为求和后被赋予的元素
function CoordinateAccAdd(conType, num1, num2, arg3) {
	var totalValue = 0;
	for ( var i = num1; i <= num2; i++) {
		var value1 = $("#" + conType + i).val();

		// value1=value1.replace(/[^\d\.]/,"");

		if (value1 == "") {
			value1 = 0;
		}

		$("#" + conType + i).val(anyDouble(value1, 2));

		totalValue = anyDouble(accAdd(value1, totalValue), 2);
	}
	$("#" + arg3).val(totalValue);
	eval($("#" + arg3).attr("liandong"));
	eval($("#" + arg3).attr("liandong1"));
}

// 多数相加返回值
function returnAccAdd(conType, num1, num2) {//c 4 4
	var totalValue = 0;
	for ( var i = num1; i <= num2; i++) {
		var value1 = $("#" + conType + i).val();
		// value1=value1.replace(/[^\d\.]/,"");

		if (value1 == "") {
			value1 = 0;
		}

		$("#" + conType + i).val(anyDouble(value1, 2));

		totalValue = anyDouble(accAdd(value1, totalValue), 2);
	}

	return totalValue;
}

// 多处 多数相加(最多支持3处)
function CoordinateMoreAdd(conType1, num1, num2, conType2, num3, num4,
		conType3, num5, num6, args) {
	var totalValue1 = returnAccAdd(conType1, num1, num2);
	var totalValue2 = returnAccAdd(conType2, num3, num4);
	var totalValue3 = returnAccAdd(conType3, num5, num6);

	totalValue = anyDouble(
			accAdd(accAdd(totalValue1, totalValue2), totalValue3), 2);
	$("#" + args).val(totalValue);

	eval($("#" + args).attr("liandong"));
	eval($("#" + args).attr("liandong1"));
}

// C7+C8-C9-C10-C11 ((C7+C8)-(C9+C10+C11))//'C',4,4,'C',5,6,'C7'
function addSubfun(conType1, num1, num2, conType2, num3, num4, args) {
	
	var totalValue1 = returnAccAdd(conType1, num1, num2);  //9
	var totalValue2 = returnAccAdd(conType2, num3, num4);  //0
	totalValue = anyDouble(accSub(totalValue1, totalValue2), 2);//9
	$("#" + args).val(totalValue);
	eval($("#" + args).attr("liandong"));
	eval($("#" + args).attr("liandong1"));

}

// 多数相加（B14+B25+B35+B36）
function returnAccSubMore(args1, args2, args3, args4, args5) {
	var totalValue = 0;
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值
	var value3 = $("#" + args3).val();// id 为arg3的值
	var value4 = $("#" + args4).val();// id 为arg4的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");
	// value3=value3.replace(/[^\d\.]/,"");
	// value4=value4.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	if (value3 == "") {
		value3 = 0;
	}

	if (value4 == "") {
		value4 = 0;
	}

	$("#" + args1).val(anyDouble(value1, 2));
	$("#" + args2).val(anyDouble(value2, 2));
	$("#" + args3).val(anyDouble(value3, 2));
	$("#" + args4).val(anyDouble(value4, 2));
	$("#" + args5)
			.val(
					anyDouble(accAdd(accAdd(accAdd(value1, value2), value3),
							value4), 2));

	eval($("#" + args5).attr("liandong"));
	eval($("#" + args5).attr("liandong1"));
}

// 多数相加减（E29-E30+E31-E32）
function returnAccAddSubMore(args1, args2, args3, args4, args5) {
	var totalValue = 0;
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值
	var value3 = $("#" + args3).val();// id 为arg3的值
	var value4 = $("#" + args4).val();// id 为arg4的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");
	// value3=value3.replace(/[^\d\.]/,"");
	// value4=value4.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	if (value3 == "") {
		value3 = 0;
	}

	if (value4 == "") {
		value4 = 0;
	}

	$("#" + args1).val(anyDouble(value1, 2));
	$("#" + args2).val(anyDouble(value2, 2));
	$("#" + args3).val(anyDouble(value3, 2));
	$("#" + args4).val(anyDouble(value4, 2));
	$("#" + args5)
			.val(
					anyDouble(accSub(accAdd(accSub(value1, value2), value3),
							value4), 2));

	eval($("#" + args5).attr("liandong"));
	eval($("#" + args5).attr("liandong1"));
}

// 利润分配比例计算

// 未分配利润勾稽差异
function notProfitRate(args1, args2, args3, args4) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	$("#" + args1).val(anyDouble(value1, 2));
	$("#" + args2).val(anyDouble(value2, 2));

	value3 = anyDouble(accSub(value1, value2), 2);

	var value4 = "";
	if (value3 == 0) {
		value4 = "平衡";
	} else {
		value4 = "不平衡";
	}

	$("#" + args3).val(value3);
	$("#" + args4).val(value4);

}

// 法定盈余公积提取比例
function SURPLUS_RESERVE_RATE(args1, args2, args3, args4) {

	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	// alert(value1+"----------2------------");

	// alert(value2+"----------3------------");
	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}
	$("#" + args1).val(anyDouble(value1, 2));
	$("#" + args2).val(anyDouble(value2, 2));

	var value3 = 0;
	if (value1 != 0) {
		value3 = anyDouble(accMul(accDiv(value2, value1), 100), 2);
	}
	var value4 = "";
	if (value1 <= 0) {
		if (value2 == 0) {
			value4 = "正确";
		} else {
			value4 = "不正确";
		}
	} else {
		if (value3 == 10) {
			value4 = "比例正确";
		} else {
			value4 = "比例不正确";
		}
	}
	$("#" + args3).val(value3);
	$("#" + args4).val(value4);
	eval($("#" + args4).attr("liandong"));
	eval($("#" + args4).attr("liandong1"));
}

// 法定公益金提取比例：
function WELFARE_FUND_RATE(args1, args2, args3, args4) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}
	$("#" + args1).val(anyDouble(value1, 2));
	$("#" + args2).val(anyDouble(value2, 2));
	var value3 = 0;
	if (value1 != 0) {
		value3 = anyDouble(accMul(accDiv(value2, value1), 100), 2);
	}
	var value4 = "";
	if (value1 <= 0) {
		if (value2 == 0) {
			value4 = "正确";
		} else {
			value4 = "不正确";
		}
	} else {
		if (value3 == 5) {
			value4 = "比例正确";
		} else {
			value4 = "比例不正确";
		}
	}
	$("#" + args3).val(value3);
	$("#" + args4).val(value4);
	eval($("#" + args4).attr("liandong"));
	eval($("#" + args4).attr("liandong1"));
}

function SURPLUS_RESERVE_RATEAll(args1, args2, args3, args4, args5, args6,
		args7) {
	SURPLUS_RESERVE_RATE(args1, args2, args3, args4);
	WELFARE_FUND_RATE(args1, args5, args6, args7);
}

// 前年所得税负
function INCOME_TAX_RATE1() {
	var value1 = $("#C17").val();
	var value2 = $("#C18").val();

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	var value3 = "";
	if (value1 != 0) {
		value3 = anyDouble(accMul(accDiv(value2, value1), 100), 2);
	}
	$("#C44").val(value3);
}

// 所得税负
function INCOME_TAX_RATE(args1, args2, args3, args4, args5) {
	var value1 = $("#" + args1).val();
	var value2 = $("#" + args2).val();
	var value3 = $("#" + args3).val();

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}
	var value4 = "";
	if (value1 != 0) {
		value4 = anyDouble(accMul(accDiv(value2, value1), 100), 2);
	}
	$("#" + args4).val(value4);
	var value5 = "";
	if (value3 == "" && value4 == "") {
		value5 = "";
	} else {
		if (value3 == value4) {
			value5 = "比例相同";
		} else {
			value5 = "比例不同";
		}
	}
	$("#" + args5).val(value5);
}

// 比较正确、不正确
function compTure(args1, args2) {
	var value1 = $("#" + args1).val();

	var value2 = "";
	if (value1 == 0) {
		value2 = "正确";
	} else {
		value2 = "不正确";
	}

	$("#" + args2).val(value2);
}

function not_profitfuzhai(args1, args2, args3, args4) {
	// 页面初始其他财务系统数据
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	var value3 = anyDouble(accSub(value1, value2), 2);

	$("#" + args3).val(value3);

	var value4 = "";
	if (value3 == 0) {
		value4 = "平衡";
	} else {
		value4 = "不平衡";
	}
	$("#" + args4).val(value4);
}

function cashJs(args1, args2, args3, args4) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	var value3 = anyDouble(accSub(value1, value2), 2);

	$("#" + args3).val(value3);

	var value4 = "";
	if (value3 == 0) {
		value4 = "正确";
	} else {
		value4 = "不正确";
	}
	$("#" + args4).val(value4);
}

function cashJs1(args1, args2, args3, args4, args5) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值
	var value3 = $("#" + args3).val();// id 为arg2的值

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	if (value3 == "") {
		value3 = 0;
	}

	var value4 = anyDouble(accSub(accSub(value1, value2), value3), 2);

	$("#" + args4).val(value4);

	var value5 = "";
	if (value4 == 0) {
		value5 = "正确";
	} else {
		value5 = "不正确";
	}
	$("#" + args5).val(value5);
}

// IF(（本年数）计提的资产减值准备-资产负债表!（上年数）合计=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）计提的资产减值准备-资产负债表!上年数合计,"0.00")))
function tongji1(args1, args2, args3) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	$("#" + args1).val(value1);
	$("#" + args2).val(value2);

	var value3 = "";
	if (accSub(value1, value2) == 0) {
		value3 = "与资产负债表数值相符";
	} else {
		var value = anyDouble(accSub(value1, value2), 2);

		value3 = "差额" + value;
	}
	$("#" + args3).val(value3);
}

// IF(（本年数）固定资产折旧-(资产负债表!（本年数）减:累计折旧-资产负债表!（上年数）减:累计折旧)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）固定资产折旧-(资产负债表!（本年数）减:累计折旧-资产负债表!（上年数）减:累计折旧),"0.00")))
// IF(（本年数）待摊费用减少(减:增加)-(资产负债表!（上年数）待摊费用-资产负债表!（本年数）待摊费用)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）待摊费用减少(减:增加)-(资产负债表!（上年数）待摊费用-资产负债表!（本年数）待摊费用),"0.00")))
// IF(（本年数）预提费用增加(减:减少)-(资产负债表!（本年数）预提费用-资产负债表!（上年数）预提费用)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）预提费用增加(减:减少)-(资产负债表!（本年数）预提费用-资产负债表!（上年数）预提费用),"0.00")))
// IF(（本年数）递延税款贷项(减:借项)-(资产负债表!（本年数）递延税款贷项-资产负债表!（本年数）递延税款借项)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）递延税款贷项(减:借项)-(资产负债表!（本年数）递延税款贷项-资产负债表!（本年数）递延税款借项),"0.00")))
// IF(（本年数）存货的减少(减:增加)-(资产负债表!（上年数）存货-资产负债表!（本年数）存货)=0,"与资产负债表数值相符",CONCATENATE("差额",TEXT(（本年数）存货的减少(减:增加)-(资产负债表!（上年数）存货-资产负债表!（本年数）存货),"0.00")))
function tongji2(args1, args2, args3, args4) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值
	var value3 = $("#" + args3).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");
	// value3=value3.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	if (value3 == "") {
		value3 = 0;
	}

	$("#" + args1).val(value1);
	$("#" + args2).val(value2);
	$("#" + args3).val(value3);

	var value4 = "";
	if (accSub(value1, accSub(value2, value3)) == 0) {
		value4 = "与资产负债表数值相符";
	} else {
		var value = anyDouble(accSub(value1, accSub(value2, value3)), 2);

		value4 = "差额" + value;
	}
	$("#" + args4).val(value4);
}

// IF(（本年数）财务费用-利润及利润分配表!（本年数）财务费用=0,"与利润表数值相符",CONCATENATE("差额",TEXT(（本年数）财务费用-利润及利润分配表!（本年数）财务费用,"0.00")))
function tongji3(args1, args2, args3) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	$("#" + args1).val(value1);
	$("#" + args2).val(value2);

	var value3 = "";
	if (accSub(value1, value2) == 0) {
		value3 = "与利润表数值相符";
	} else {
		var value = anyDouble(accSub(value1, value2), 2);

		value3 = "差额" + value;
	}
	$("#" + args3).val(value3);
}

// IF(（本年数）投资损失(减:收益)+利润及利润分配表!（本年数）加:投资收益=0,"与利润表数值相符",CONCATENATE("差额",TEXT(（本年数）投资损失(减:收益)+利润及利润分配表!（本年数）加:投资收益,"0.00")))
function tongji4(args1, args2, args3) {
	var value1 = $("#" + args1).val();// id 为arg1的值
	var value2 = $("#" + args2).val();// id 为arg2的值

	// value1=value1.replace(/[^\d\.]/,"");
	// value2=value2.replace(/[^\d\.]/,"");

	if (value1 == "") {
		value1 = 0;
	}

	if (value2 == "") {
		value2 = 0;
	}

	$("#" + args1).val(value1);
	$("#" + args2).val(value2);

	var value3 = "";
	if (accAdd(value1, value2) == 0) {
		value3 = "与利润表数值相符";
	} else {
		var value = anyDouble(accAdd(value1, value2), 2);

		value3 = "差额" + value;
	}
	$("#" + args3).val(value3);
}

//修改损益表
function editFinnce_ProfitDistriBution(){
	if(!$("#validForm").form("validate")) return;
	var flag = true ;
	var formData = [];
	var dataMap0 = {};
	var dataMap1 = {};
	var dataMap = {};
	var inputs = $("td[name='td_data0']").find("input:not(:radio)");
	var obj=$("td[name='td_data0']").find("input[type='radio']");
	for(var n=0;n<obj.length;n++){
	if(obj[n].checked==true){
		var name = $(obj[n]).parent().parent().attr("name");
		dataMap0[name] = $(obj[n]).val();
		}	
	}
	for ( var i = 0; i < inputs.length; i++) {
		var name = $(inputs[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_1 = $(inputs[i]).datebox("getValue"); 
			if(in_date_value_1==null || in_date_value_1=='' || in_date_value_1==undefined){
				flag = false ;
			}
			dataMap0[name] = in_date_value_1 ;
		}else {
			dataMap0[name] = $(inputs[i]).val();
		}
		
	}

	formData.push(dataMap0);
	
	var debt1 = [];
	var inputs1 = $("td[name='td_data1']").find("input:not(:radio)");
	var obj1=$("td[name='td_data1']").find("input[type='radio']");
	for(var n=0;n<obj1.length;n++){
	if(obj1[n].checked==true){
		var name = $(obj1[n]).parent().parent().attr("name");
		dataMap1[name] = $(obj1[n]).val();
		}	
	}
	for ( var i = 0; i < inputs1.length; i++) {
		var name = $(inputs1[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var  in_date_value_2 = $(inputs1[i]).datebox("getValue");
			if(in_date_value_2==null || in_date_value_2=='' || in_date_value_2==undefined){
				flag = false ;
			}
			dataMap1[name] = in_date_value_2;
		}else {
			dataMap1[name] = $(inputs1[i]).val();
		}
		
	}
	
	formData.push(dataMap1);
	var debt2 = [];
	var inputs2 = $("td[name='td_data2']").find("input:not(:radio)");
	var obj2=$("td[name='td_data2']").find("input[type='radio']");
	for(var n=0;n<obj2.length;n++){
	if(obj2[n].checked==true){
		var name = $(obj2[n]).parent().parent().attr("name");
		dataMap[name] = $(obj2[n]).val();
		}	
	}
	for ( var i = 0; i < inputs2.length; i++) {
		var name = $(inputs2[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_3 = $(inputs2[i]).datebox("getValue");
			if(in_date_value_3==null || in_date_value_3=='' || in_date_value_3==undefined){
				flag = false ;
			}
			dataMap[name] = in_date_value_3 ;
		}else {
			dataMap[name] = $(inputs2[i]).val();
		}
		
	}
	formData.push(dataMap);
	
	var dataMap2 = {
		FORMDATA : formData,
		CLIENT_ID : $("input[name='CLIENT_ID']").val(),
		UNIT_NAME : $("input[name='UNIT_NAME']").val(),
		CHECK_PEOPLE : $("input[name='CHECK_PEOPLE']").val(),
		CHECK_DATE : $("#CHECK_DATE").datebox("getValue"),
		INDEX_CODE : $("input[name='INDEX_CODE']").val(),
		BELONG_DATE : $("input[name='BELONG_DATE']").val(),
		REVIEW_PEOPLE : $("input[name='REVIEW_PEOPLE']").val(),
		REVIEW_DATE : $("#REVIEW_DATE").datebox("getValue")
	};
	$("#ChangeViewData").val(JSON.stringify(dataMap2));
	//$("#formView").submit();
	if(flag){
		$('#formView').form('submit',{
		 success:function(data){
			 data = $.parseJSON(data);
			 if(data.flag==true){
				 $.messager.alert('提示', "保存成功", "保存成功");
				  
			 }else {
				 $.messager.alert('提示', "保存失败", "保存失败");
			 }
		 }
	});
	}
	
}
// 保存利润及利润分配表
function saveFinnce_ProfitDistriBution() {
	//alert("Profit") ;
	if(!$("#validForm").form("validate")) return;
	var flag = true ;
	var formData = [];
	var dataMap0 = {};
	var dataMap1 = {};
	var dataMap = {};
	var inputs = $("td[name='td_data0']").find("input:not(:radio)");
	var obj=$("td[name='td_data0']").find("input[type='radio']");
	//debugger ;
	for(var n=0;n<obj.length;n++){
	if(obj[n].checked==true){
		var name = $(obj[n]).parent().parent().attr("name");
		dataMap0[name] = $(obj[n]).val();
		}	
	}
	for ( var i = 0; i < inputs.length; i++) {
		var name = $(inputs[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_1 = $(inputs[i]).datebox("getValue");
			if(in_date_value_1==null || in_date_value_1=='' || in_date_value_1==undefined){
				flag = false ;
			}
			dataMap0[name] = in_date_value_1 ;
		}else {
			dataMap0[name] = $(inputs[i]).val();
		}
	
	}
	formData.push(dataMap0);
	
	//var debt1 = [];
	var inputs1 = $("td[name='td_data1']").find("input:not(:radio)");
	var obj1=$("td[name='td_data1']").find("input[type='radio']");
	for(var n=0;n<obj1.length;n++){
	if(obj1[n].checked==true){
		var name = $(obj1[n]).parent().parent().attr("name");
		dataMap1[name] = $(obj1[n]).val();
		}	
	}
	for ( var i = 0; i < inputs1.length; i++) {
		var name = $(inputs1[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_2 = $(inputs1[i]).datebox("getValue");
			if(in_date_value_2==null || in_date_value_2=='' || in_date_value_2==undefined){
				flag = false ;
			}
			dataMap1[name] = in_date_value_2 ;
		}else {
			dataMap1[name] = $(inputs1[i]).val();
		}
		
	}
	formData.push(dataMap1);
	//var debt2 = [];
	var inputs2 = $("td[name='td_data2']").find("input:not(:radio)");
	var obj2=$("td[name='td_data2']").find("input[type='radio']");
	for(var n=0;n<obj2.length;n++){
	if(obj2[n].checked==true){
		var name = $(obj2[n]).parent().parent().attr("name");
		dataMap[name] = $(obj2[n]).val();
		}	
	}
	for ( var i = 0; i < inputs2.length; i++) {
		var name = $(inputs2[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_3 = $(inputs2[i]).datebox("getValue");
			if(in_date_value_3==null || in_date_value_3=='' || in_date_value_3==undefined){
				flag = false ;
			}
			dataMap[name] = in_date_value_3 ;
		}else {
			dataMap[name] = $(inputs2[i]).val();
		}
		
	}
	formData.push(dataMap);
	
	var dataMap2 = {
		FORMDATA : formData,
		CLIENT_ID : $("input[name='CLIENT_ID']").val(),
		UNIT_NAME : $("input[name='UNIT_NAME']").val(),
		CHECK_PEOPLE : $("input[name='CHECK_PEOPLE']").val(),
		CHECK_DATE : $("#CHECK_DATE").datebox("getValue"),
		INDEX_CODE : $("input[name='INDEX_CODE']").val(),
		BELONG_DATE : $("input[name='BELONG_DATE']").val(),
		REVIEW_PEOPLE : $("input[name='REVIEW_PEOPLE']").val(),
		REVIEW_DATE : $("#REVIEW_DATE").datebox("getValue")
	};
	$("#ChangeViewData").val(JSON.stringify(dataMap2));
	//$("#formView").submit();
	if(flag){
		$('#formView').form('submit',{
		 success:function(data){
			 data = $.parseJSON(data);
			 if(data.flag==true){
				 $.messager.alert('提示', "保存成功", "保存成功");
				 $('# subButton').linkbutton('disable');
			 }else {
				 $.messager.alert('提示', "保存失败", "保存失败");
			 }
		 }
	});
	}
	
}

// 保存现金流量表
function saveFinnce_Cash() {
	//alert("cash") ;
	if(!$("#validForm").form("validate")) return;
	var flag = true ;
	var formData = [];
	var dataMap = {};
	var dataMap0 = {};
	var dataMap1 = {};
	var inputs = $("td[name='td_data0']").find("input:not(:radio)");
	var obj=$("td[name='td_data0']").find("input[type='radio']");
	for(var n=0;n<obj.length;n++){
	if(obj[n].checked==true){
		var name = $(obj[n]).parent().parent().attr("name");
		dataMap0[name] = $(obj[n]).val();
		}	
	}
	for ( var i = 0; i < inputs.length; i++) {
		var name = $(inputs[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_1 =$(inputs[i]).datebox("getValue");
			if(in_date_value_1==null || in_date_value_1=='' || in_date_value_1==undefined){
				flag = false ;
			}
			dataMap0[name] = in_date_value_1 ;
		}else {
			dataMap0[name] = $(inputs[i]).val();
		}
		
	}
	formData.push(dataMap0);
	var debt1 = [];
	var inputs1 = $("td[name='td_data1']").find("input:not(:radio)");
	var obj1=$("td[name='td_data1']").find("input[type='radio']");
	for(var n=0;n<obj1.length;n++){
	if(obj1[n].checked==true){
		var name = $(obj1[n]).parent().parent().attr("name");
		dataMap1[name] = $(obj1[n]).val();
		}	
	}
	for ( var i = 0; i < inputs1.length; i++) {
		var name = $(inputs1[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_2 = $(inputs1[i]).datebox("getValue");
			if(in_date_value_2==null || in_date_value_2=='' || in_date_value_2==undefined){
				flag = false ;
			}
			dataMap1[name] = in_date_value_2 ;
		}else {
			dataMap1[name] = $(inputs1[i]).val();
		}
	}
	formData.push(dataMap1);
	var debt2 = [];
	var inputs2 = $("td[name='td_data2']").find("input:not(:radio)");
	var obj2=$("td[name='td_data2']").find("input[type='radio']");
	for(var n=0;n<obj2.length;n++){
	if(obj2[n].checked==true){
		var name = $(obj2[n]).parent().parent().attr("name");
		dataMap[name] = $(obj2[n]).val();
		}	
	}
	for ( var i = 0; i < inputs2.length; i++) {
		var name = $(inputs2[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			dataMap[name] = $(inputs2[i]).datebox("getValue");
			var in_date_value_3 = $(inputs2[i]).datebox("getValue");
			if(in_date_value_3==null || in_date_value_3=='' || in_date_value_3==undefined){
				flag = false ;
			}
		}else {
			dataMap[name] = $(inputs2[i]).val();
		}
	}
	formData.push(dataMap);
	
	var dataMap2 = {
		FORMDATA : formData,
		UNIT_NAME : $("input[name='UNIT_NAME']").val(),
		CHECK_PEOPLE : $("input[name='CHECK_PEOPLE']").val(),
		CHECK_DATE : $("#CHECK_DATE").datebox("getValue"),
		INDEX_CODE : $("input[name='INDEX_CODE']").val(),
		BELONG_DATE : $("input[name='BELONG_DATE']").val(),
		REVIEW_PEOPLE : $("input[name='REVIEW_PEOPLE']").val(),
		REVIEW_DATE : $("#REVIEW_DATE").datebox("getValue"),
		UNKNOWN_ONE : $("input[name='UNKNOWN_ONE']").val(),
		UNKNOWN_TWO : $("input[name='UNKNOWN_TWO']").val(),
		UNKNOWN_THREE : $("input[name='UNKNOWN_THREE']").val(),
		UNKNOWN_FOUR : $("input[name='UNKNOWN_FOUR']").val(),
		UNKNOWN_FIVE : $("input[name='UNKNOWN_FIVE']").val(),
		UNKNOWN_SIX : $("input[name='UNKNOWN_SIX']").val(),
		UNKNOWN_SEVEN : $("input[name='UNKNOWN_SEVEN']").val(),
		UNKNOWN_EIGHT : $("input[name='UNKNOWN_EIGHT']").val(),
		CLIENT_ID : $("input[name='CLIENT_ID']").val()
	};
	$("#ChangeViewData").val(JSON.stringify(dataMap2));
	//$("#formView").submit();
	if(flag){
		$('#formView').form('submit',{
		 success:function(data){
			 data = $.parseJSON(data);
			 if(data.flag==true){
				 $.messager.alert('提示', "保存成功", "保存成功");
				 $('#subFinnceButton').linkbutton('disable');
			 }else {
				 $.messager.alert('提示', "保存失败", "保存失败");
			 }
		 }
	});
	}
	
}
//修改现金流量表
function editFinnce_Cash() {
	if(!$("#validForm").form("validate")) return;
	var flag = true ;
	var formData = [];
	var dataMap = {};
	var dataMap0 = {};
	var dataMap1 = {};
	var inputs = $("td[name='td_data0']").find("input:not(:radio)");
	var obj=$("td[name='td_data0']").find("input[type='radio']");
	for(var n=0;n<obj.length;n++){
	if(obj[n].checked==true){
		var name = $(obj[n]).parent().parent().attr("name");
		dataMap0[name] = $(obj[n]).val();
		}	
	}
	for ( var i = 0; i < inputs.length; i++) {
		var name = $(inputs[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_1 = $(inputs[i]).datebox("getValue");
			if(in_date_value_1==null || in_date_value_1=='' || in_date_value_1==undefined){
				flag = false ;
			}
			dataMap0[name] = in_date_value_1 ;
		}else {
			dataMap0[name] = $(inputs[i]).val();
		}
		
	}
	formData.push(dataMap0);
	var debt1 = [];
	var inputs1 = $("td[name='td_data1']").find("input:not(:radio)");
	var obj1=$("td[name='td_data1']").find("input[type='radio']");
	for(var n=0;n<obj1.length;n++){
	if(obj1[n].checked==true){
		var name = $(obj1[n]).parent().parent().attr("name");
		dataMap1[name] = $(obj1[n]).val();
		}	
	}
	for ( var i = 0; i < inputs1.length; i++) {
		var name = $(inputs1[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_2 = $(inputs1[i]).datebox("getValue");
			if(in_date_value_2==null || in_date_value_2=='' || in_date_value_2==undefined){
				flag = false ;
			}
			dataMap1[name] = in_date_value_2 ;
		}else {
			dataMap1[name] = $(inputs1[i]).val();
		}
		
	}
	formData.push(dataMap1);
	var debt2 = [];
	var inputs2 = $("td[name='td_data2']").find("input:not(:radio)");
	var obj2=$("td[name='td_data2']").find("input[type='radio']");
	for(var n=0;n<obj2.length;n++){
	if(obj2[n].checked==true){
		var name = $(obj2[n]).parent().parent().attr("name");
		dataMap[name] = $(obj2[n]).val();
		}	
	}
	for ( var i = 0; i < inputs2.length; i++) {
		var name = $(inputs2[i]).parent().parent().attr("name");
		if(name=='IN_DATE'){
			var in_date_value_3 = $(inputs2[i]).datebox("getValue");
			if(in_date_value_3==null || in_date_value_3=='' || in_date_value_3==undefined){
				flag = false ;
			}
			dataMap[name] = in_date_value_3 ;
		}else {
			dataMap[name] = $(inputs2[i]).val();
		}
		
	}
	formData.push(dataMap);
	
	var dataMap2 = {
		FORMDATA : formData,
		UNIT_NAME : $("input[name='UNIT_NAME']").val(),
		CHECK_PEOPLE : $("input[name='CHECK_PEOPLE']").val(),
		CHECK_DATE : $("#CHECK_DATE").datebox("getValue"),
		INDEX_CODE : $("input[name='INDEX_CODE']").val(),
		BELONG_DATE : $("input[name='BELONG_DATE']").val(),
		REVIEW_PEOPLE : $("input[name='REVIEW_PEOPLE']").val(),
		REVIEW_DATE : $("#REVIEW_DATE").datebox("getValue"),
		UNKNOWN_ONE : $("input[name='UNKNOWN_ONE']").val(),
		UNKNOWN_TWO : $("input[name='UNKNOWN_TWO']").val(),
		UNKNOWN_THREE : $("input[name='UNKNOWN_THREE']").val(),
		UNKNOWN_FOUR : $("input[name='UNKNOWN_FOUR']").val(),
		UNKNOWN_FIVE : $("input[name='UNKNOWN_FIVE']").val(),
		UNKNOWN_SIX : $("input[name='UNKNOWN_SIX']").val(),
		UNKNOWN_SEVEN : $("input[name='UNKNOWN_SEVEN']").val(),
		UNKNOWN_EIGHT : $("input[name='UNKNOWN_EIGHT']").val(),
		CLIENT_ID : $("input[name='CLIENT_ID']").val()
	};
	$("#ChangeViewData").val(JSON.stringify(dataMap2));
	//$("#formView").submit();
	if(flag){
		$('#formView').form('submit',{
		 success:function(data){
			 data = $.parseJSON(data);
			 if(data.flag==true){
				 $.messager.alert('提示', "保存成功", "保存成功");
			 }else {
				 $.messager.alert('提示', "保存失败", "保存失败");
			 }
		 }
	});
	}
	
}


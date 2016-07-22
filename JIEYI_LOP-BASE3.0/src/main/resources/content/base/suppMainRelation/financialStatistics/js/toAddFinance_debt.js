//$().ready(function() {
//	$("#subButton").click(function() {
//			alert("000000000000000000000000000000000000000000000000000000000000000");
//		
//			var flag = true;
//			// 收集报表数据
//			getDabtData();
//			// 收集报告时间
//			//getDate();
//			getHead();
//			if (flag) {
//				alert("000000000000000000000000000000000000000000000000000000000000000");
//				 $('#myform').form({
//					 success:function(data){
//					 $.messager.alert('Info', data, 'info');
//					 }
//				});
//			}
//		});
//});


function dosaveEditDebt(){
	if(!$("#validForm").form("validate")) return;
	// 收集报表数据
	var flag = getDabtData();
	// 收集报告时间
	getHead();
	if (flag) {
		 $('#myform').form('submit',{
			 success:function(data){
			      data=eval("("+data+")");
				 if(data.flag==true){
					 $.messager.alert('提示', "保存成功", "保存成功");
				 }else {
					 $.messager.alert('提示', "保存失败", "保存失败");
				 }
			 }
		});
	}
}


function dosaveDebt(){
	if(!$("#validForm").form("validate")) return;
	// 收集报表数据
	var flag = getDabtData();
	// 收集报告时间
	getHead();
	if (flag) {
		 $('#myform').form('submit',{
			 success:function(data){
			      data=eval("("+data+")");
				 if(data.flag==true){
					 $.messager.alert('提示', "保存成功", "保存成功");
					 $('#subDebtButton').linkbutton('disable');  
				 }else {
					 $.messager.alert('提示', "保存失败", "保存失败");
				 }
			 }
		});
	}
}

/**
 * 收集表头部信息
 */
function getHead(map) {
	var name = "";
	var value = "";
	$("input[name]").each(function() {
		name = $(this).attr("name");
		value = $(this).val();
		var input = $("<input>");
		$(input).attr("name", name);
		$(input).val(value);
		$("#myform").append($(input));
	});
}

/**
 * 收集财务数据
 * 
 * @param {Object}
 *            map
 */
function getDabtData() {
	var flag = true;
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
			var in_date_value = $(inputs[i]).datebox("getValue") ;
			if(in_date_value==null || in_date_value=='' || in_date_value==undefined){
				flag = false;
			}
			dataMap0[name] = in_date_value;
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
			var in_date_value1 =  $(inputs1[i]).datebox("getValue") ;
			if(in_date_value1==null || in_date_value1=='' || in_date_value1==undefined){
				flag = false;
			}
			dataMap1[name] = in_date_value1;
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
			var in_date_value2 = $(inputs2[i]).datebox("getValue");
			if(in_date_value2==null || in_date_value2=='' || in_date_value2==undefined){
				flag = false;
			}
			dataMap[name] = in_date_value2 ;
		}else {
			dataMap[name] = $(inputs2[i]).val();
		}

		
	}
	formData.push(dataMap);
	
	var dataMap2 = {
			FORMDATA:formData
	};
	var input = $("<input>");
	$(input).attr("name", "ChangeViewData");
	$(input).val(JSON.stringify(dataMap2));
	$("#myform").append($(input));
	return flag ;
}

/**
 * 获取日期
 */
//function getDate() {
//	var dataMap = {};
//	var inputs = $("tr[name='IN_DATE']").find("input");
//	for ( var i = 0; i < inputs.length; i++) {
//		dataMap["IN_DATE" + i] = $(inputs[i]).val();
//	}
//	var input = $("<input>");
//	$(input).attr("name", "IN_DATE");
//	$(input).val(JSON.stringify(dataMap));
//	$("#myform").append($(input));
//}

//(本年)年初试算差异(上年负债及所有者权益合计-上年资产总计)
function jisuan1() {
	var DABT_OWER_TOTAL1 = $("input[name='DABT_OWER_TOTAL1']").val();

	var NET_TOTAL1 = $("input[name='NET_TOTAL1']").val();

	if (DABT_OWER_TOTAL1 == "") {
		DABT_OWER_TOTAL1 = 0;
	}

	if (NET_TOTAL1 == "") {
		NET_TOTAL1 = 0;
	}
	var value1 = anyDouble(accSub11(DABT_OWER_TOTAL1, NET_TOTAL1), 2);

	var value2 = "";

	if (value1 == 0) {
		value2 = "平衡";
	} else {
		value2 = "不平衡";
	}
	$("input[name='YEAR_BEGIN_DEFFERENCE']").val(value1);
	$("input[name='YEAR_BEGIN_DEFFERENCE1']").val(value2);
}

// (本年)年末试算差异(本年负债及所有者权益合计-本年资产总计)
function jisuan2() {
	var DABT_OWER_TOTAL1 = $("input[name='DABT_OWER_TOTAL2']").val();

	var NET_TOTAL1 = $("input[name='NET_TOTAL2']").val();

	if (DABT_OWER_TOTAL1 == "") {
		DABT_OWER_TOTAL1 = 0;
	}

	if (NET_TOTAL1 == "") {
		NET_TOTAL1 = 0;
	}

	var value1 = anyDouble(accSub11(DABT_OWER_TOTAL1, NET_TOTAL1), 2);

	var value2 = "";

	if (value1 == 0) {
		value2 = "平衡";
	} else {
		value2 = "不平衡";
	}

	$("input[name='YEAR_END_DEFFERENCE']").val(value1);
	$("input[name='YEAR_END_DEFFERENCE1']").val(value2);
}

// (本年)年初坏帐准备提取比例
function jisuan3() {
	var ACCOUNTS_SECTION1 = $("#ACCOUNTS_SECTION_1").val();

	var BAD_SECTION1 = $("#BAD_READY_1").val();

	if (ACCOUNTS_SECTION1 == "") {
		ACCOUNTS_SECTION1 = 0;
	}

	if (BAD_SECTION1 == "") {
		BAD_SECTION1 = 0;
	}

	var value1 = "";

	if (ACCOUNTS_SECTION1 == 0) {
		;
	} else {
		value1 = anyDouble(accDiv11(BAD_SECTION1, ACCOUNTS_SECTION1), 2);
	}

	$("input[name='YEAR_BEGIN_BAD_PROPORTION']").val(value1);
}

// (本年)年末尾坏帐准备提取比例
function jisuan4() {
	var ACCOUNTS_SECTION1 = $("#ACCOUNTS_SECTION_2").val();

	var BAD_SECTION1 = $("#BAD_READY_2").val();

	var YEAR_END_BAD_PROPORTION = $("input[name='YEAR_END_BAD_PROPORTION']")
			.val();

	if (ACCOUNTS_SECTION1 == "") {
		ACCOUNTS_SECTION1 = 0;
	}

	if (BAD_SECTION1 == "") {
		BAD_SECTION1 = 0;
	}

	var value1 = "";

	var value2 = "";

	if (ACCOUNTS_SECTION1 == 0) {
		;
	} else {
		value1 = anyDouble(accDiv11(BAD_SECTION1, ACCOUNTS_SECTION1), 2);
	}

	if (value1 != "" && YEAR_END_BAD_PROPORTION != ""
			&& YEAR_END_BAD_PROPORTION == value1) {
		value2 = "比例相同";
	} else {
		value2 = "比例不同";
	}

	$("input[name='YEAR_END_BAD_PROPORTION']").val(value1);
	$("input[name='YEAR_END_BAD_PROPORTION1']").val(value2);
}

// (本年)年初坏帐准备提取比例
function jisuan5() {
	var NOT_ASSIGNED_PROFIT = $("input[name='NOT_ASSIGNED_PROFIT']").val();

	var NOT_PROFIT1 = $("input[name='NOT_PROFIT1']").val();

	if (NOT_ASSIGNED_PROFIT == "") {
		NOT_ASSIGNED_PROFIT = 0;
	}

	if (NOT_PROFIT1 == "") {
		NOT_PROFIT1 = 0;
	}

	var value1 = anyDouble(accSub11(NOT_ASSIGNED_PROFIT, NOT_PROFIT1), 2);

	var value2 = "";

	if (value1 == 0) {
		value2 = "平衡";
	} else {
		value2 = "不平衡";
	}

	$("input[name='YEAR_BEGIN_PROFIT']").val(value1);
	$("input[name='YEAR_BEGIN_PROFIT1']").val(value2);
}

// (本年)年初坏帐准备提取比例
function jisuan6() {
	var SURPLUS_RESERVE1 = $("input[name='SURPLUS_RESERVE1']").val();

	var SURPLUS_RESERVE2 = $("input[name='SURPLUS_RESERVE2']").val();

	var TATU_SURPLUS_RESERVE = $("input[name='TATU_SURPLUS_RESERVE']").val();

	var STATU_SURPLUS_FUND = $("input[name='STATU_SURPLUS_FUND']").val();

	var EXTRACTION_SURPLUS_RESERVE = $(
			"input[name='EXTRACTION_SURPLUS_RESERVE']").val();

	if (SURPLUS_RESERVE1 == "") {
		SURPLUS_RESERVE1 = 0;
	}

	if (SURPLUS_RESERVE2 == "") {
		SURPLUS_RESERVE2 = 0;
	}

	if (TATU_SURPLUS_RESERVE == "") {
		TATU_SURPLUS_RESERVE = 0;
	}

	if (STATU_SURPLUS_FUND == "") {
		STATU_SURPLUS_FUND = 0;
	}

	if (EXTRACTION_SURPLUS_RESERVE == "") {
		EXTRACTION_SURPLUS_RESERVE = 0;
	}

	var value1 = anyDouble(accSub11(accSub11(accSub11(accSub11(
			SURPLUS_RESERVE1, SURPLUS_RESERVE2), TATU_SURPLUS_RESERVE),
			STATU_SURPLUS_FUND), EXTRACTION_SURPLUS_RESERVE), 2);

	var value2 = "";

	if (value1 == 0) {
		value2 = "平衡";
	} else {
		value2 = "不平衡";
	}

	$("input[name='YEAR_END_SURPLUS']").val(value1);
	$("input[name='YEAR_END_SURPLUS1']").val(value2);
}

/**
 * 乘法
 * 
 * @param arg1
 * @param arg2
 * @return
 */
function accMul11(arg1, arg2) {
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
function accAdd11(arg1, arg2) {
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
function accDiv11(arg1, arg2) {
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
function accSub11(arg1, arg2) {
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
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
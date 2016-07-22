$().ready(function(){
	$("#subButton").click(function(){
		//$("#subButton").attr("disabled","disabled");
		var flag=true;
		//清除表单中的数据
	//$("#myform").empty();
		//收集头部数据
		getHead();
		//收集报表数据
		
	flag=getDabtData();
	//收集报告时间
	getDate();
		//保存数据
		if (flag){
			$("#myform").submit();
		}
		
	});
});

function subButton(){
	//$("#subButton").attr("disabled","disabled");
	var flag=true;
	//清除表单中的数据
//$("#myform").empty();
	//收集头部数据
	getHead();
	//收集报表数据
	
flag=getDabtData();
//收集报告时间
getDate();
	//保存数据
	if (flag){
		$("#myform").ajaxSubmit({
			success:function (data){
				alert("保存成功");
			}
		});
	}
	
};



//必须添加数字
//$().ready(function(){
//	$("input").not(".notNum ,.data, .Button").blur(function(){
//		var value = $(this).val();
//		if (isNaN(value)) {
//			alert("请输入数字");
//		}
//	});
//	
//	//初始化数据
////	$(".tbody >tr").each(function(){
////		$(this).find("input").each(function(){
////			$(this).val(0);
////		});
////	})
//	
//});


/**
 * 收集财务数据
 * @param {Object} map
 */
function getDabtData(){
	
	var flag=true;
	$(".tbody > tr").each(function (){
		
			var name=$(this).attr("name");
			var inputs=$(this).find("input");
			
			var item={};
			for (var i=0;i<inputs.length&&flag;i++)
			{
				var value=$(inputs[i]).val();
				if (value.length>0)
				{
					//不为空 
					if (isNaN(value))
					{ 
						flag=false;
						
						alert("请输入数字");
						$(inputs[i]).focus();
					}
					
				}
				else{
					//为空，默认0
					value=0;
				}
				item[name+i]=value;
			}
			var input=$("<input>");
			$(input).attr("name",name);
			$(input).attr("type","hidden");
			$(input).val(JSON.stringify(item));
			$("#myform").append($(input));
			
	})
	
	return flag;
}


/**
 * 收集表头部信息
 */
function getHead(map)
{
	var name="";
	var value="";
	$("input[name]").each(function (){
		name=$(this).attr("name");
		value=$(this).val();
		var input=$("<input>");
		$(input).attr("name",name);
		$(input).val(value);
		$("#myform").append($(input));
		
	});
	
}

/**
 * 获取日期
 */
function getDate(){
	var dataMap={};
	dataMap["IN_DATE0"]=$("input[name='IN_DATE0']").val();
	dataMap["IN_DATE1"]=$("input[name='IN_DATE1']").val();
	dataMap["IN_DATE2"]=$("input[name='IN_DATE2']").val();
	
	var input=$("<input>");
	$(input).attr("name","IN_DATE");
	$(input).val(JSON.stringify(dataMap));
	$("#myform").append($(input));
	
}


//(本年)年初试算差异(上年负债及所有者权益合计-上年资产总计)
function jisuan1()
{
	var DABT_OWER_TOTAL1=$("input[name='DABT_OWER_TOTAL1']").val();
	
	var NET_TOTAL1=$("input[name='NET_TOTAL1']").val();
	
	if(DABT_OWER_TOTAL1=="")
	{
		DABT_OWER_TOTAL1=0;
	}
	
	if(NET_TOTAL1=="")
	{
		NET_TOTAL1=0;
	}
	
	var value1=anyDouble(accSub11(DABT_OWER_TOTAL1,NET_TOTAL1),2);
	
	var value2="";
	
	if(value1==0)
	{
		value2="平衡";
	}
	else
	{
		value2="不平衡";
	}
	
	$("input[name='YEAR_BEGIN_DEFFERENCE']").val(value1);
	$("input[name='YEAR_BEGIN_DEFFERENCE1']").val(value2);
}


//(本年)年末试算差异(本年负债及所有者权益合计-本年资产总计)
function jisuan2()
{
	var DABT_OWER_TOTAL1=$("input[name='DABT_OWER_TOTAL2']").val();
	
	var NET_TOTAL1=$("input[name='NET_TOTAL2']").val();
	
	if(DABT_OWER_TOTAL1=="")
	{
		DABT_OWER_TOTAL1=0;
	}
	
	if(NET_TOTAL1=="")
	{
		NET_TOTAL1=0;
	}
	
	var value1=anyDouble(accSub11(DABT_OWER_TOTAL1,NET_TOTAL1),2);
	
	var value2="";
	
	if(value1==0)
	{
		value2="平衡";
	}
	else
	{
		value2="不平衡";
	}
	
	$("input[name='YEAR_END_DEFFERENCE']").val(value1);
	$("input[name='YEAR_END_DEFFERENCE1']").val(value2);
}


//(本年)年初坏帐准备提取比例
function jisuan3()
{
	var ACCOUNTS_SECTION1=$("input[name='ACCOUNTS_SECTION1']").val();
	
	var BAD_SECTION1=$("input[name='BAD_SECTION1']").val();
	
	if(ACCOUNTS_SECTION1=="")
	{
		ACCOUNTS_SECTION1=0;
	}
	
	if(BAD_SECTION1=="")
	{
		BAD_SECTION1=0;
	}
	
	var value1="";
	
	if(ACCOUNTS_SECTION1==0)
	{
		;
	}
	else
	{
		value1=anyDouble(accDiv11(BAD_SECTION1,ACCOUNTS_SECTION1),2);
	}
	
	$("input[name='YEAR_BEGIN_BAD_PROPORTION']").val(value1);
}

//(本年)年初坏帐准备提取比例
function jisuan4()
{
	var ACCOUNTS_SECTION1=$("input[name='ACCOUNTS_SECTION2']").val();
	
	var BAD_SECTION1=$("input[name='BAD_SECTION2']").val();
	
	var YEAR_END_BAD_PROPORTION=$("input[name='YEAR_END_BAD_PROPORTION']").val();
	
	if(ACCOUNTS_SECTION1=="")
	{
		ACCOUNTS_SECTION1=0;
	}
	
	if(BAD_SECTION1=="")
	{
		BAD_SECTION1=0;
	}
	
	var value1="";
	
	var value2="";
	
	if(ACCOUNTS_SECTION1==0)
	{
		;
	}
	else
	{
		value1=anyDouble(accDiv11(BAD_SECTION1,ACCOUNTS_SECTION1),2);
	}
	
	if(value1!="" && YEAR_END_BAD_PROPORTION!="" && YEAR_END_BAD_PROPORTION==value1)
	{
		value2="比例相同";
	}
	else
	{
		value2="比例不同";
	}
	
	$("input[name='YEAR_END_BAD_PROPORTION']").val(value1);
	$("input[name='YEAR_END_BAD_PROPORTION1']").val(value2);
}


//(本年)年初坏帐准备提取比例
function jisuan5()
{
	var NOT_ASSIGNED_PROFIT=$("input[name='NOT_ASSIGNED_PROFIT']").val();
	
	var NOT_PROFIT1=$("input[name='NOT_PROFIT1']").val();
	
	if(NOT_ASSIGNED_PROFIT=="")
	{
		NOT_ASSIGNED_PROFIT=0;
	}
	
	if(NOT_PROFIT1=="")
	{
		NOT_PROFIT1=0;
	}
	
	var value1=anyDouble(accSub11(NOT_ASSIGNED_PROFIT,NOT_PROFIT1),2);
	
	var value2="";
	
	if(value1==0)
	{
		value2="平衡";
	}
	else
	{
		value2="不平衡";
	}
	
	$("input[name='YEAR_BEGIN_PROFIT']").val(value1);
	$("input[name='YEAR_BEGIN_PROFIT1']").val(value2);
}


//(本年)年初坏帐准备提取比例
function jisuan6()
{
	var SURPLUS_RESERVE1=$("input[name='SURPLUS_RESERVE1']").val();
	
	var SURPLUS_RESERVE2=$("input[name='SURPLUS_RESERVE2']").val();
	
	var TATU_SURPLUS_RESERVE=$("input[name='TATU_SURPLUS_RESERVE']").val();
	
	var STATU_SURPLUS_FUND=$("input[name='STATU_SURPLUS_FUND']").val();
	
	var EXTRACTION_SURPLUS_RESERVE=$("input[name='EXTRACTION_SURPLUS_RESERVE']").val();
	
	if(SURPLUS_RESERVE1=="")
	{
		SURPLUS_RESERVE1=0;
	}
	
	if(SURPLUS_RESERVE2=="")
	{
		SURPLUS_RESERVE2=0;
	}
	
	if(TATU_SURPLUS_RESERVE=="")
	{
		TATU_SURPLUS_RESERVE=0;
	}
	
	if(STATU_SURPLUS_FUND=="")
	{
		STATU_SURPLUS_FUND=0;
	}
	
	if(EXTRACTION_SURPLUS_RESERVE=="")
	{
		EXTRACTION_SURPLUS_RESERVE=0;
	}
	
	
	
	var value1=anyDouble(accSub11(accSub11(accSub11(accSub11(SURPLUS_RESERVE1,SURPLUS_RESERVE2),TATU_SURPLUS_RESERVE),STATU_SURPLUS_FUND),EXTRACTION_SURPLUS_RESERVE),2);
	
	var value2="";
	
	if(value1==0)
	{
		value2="平衡";
	}
	else
	{
		value2="不平衡";
	}
	
	$("input[name='YEAR_END_SURPLUS']").val(value1);
	$("input[name='YEAR_END_SURPLUS1']").val(value2);
}


/**
 * 乘法
 * @param arg1
 * @param arg2
 * @return
 */
function accMul11(arg1, arg2) {
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
function accAdd11(arg1, arg2) {
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
function accDiv11(arg1, arg2) {
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
function accSub11(arg1, arg2) {
    var r1, r2, m, n;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}


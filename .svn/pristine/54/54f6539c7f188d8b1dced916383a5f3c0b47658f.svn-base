//验证数字，保留一位小数
function one(ele){
	ele.value=ele.value.replace(/\.{2,100}|[a-zA-Z]{1,100}|[\u4e00-\u9fa5]{1,100}|[\+\_\=\!\@\#\$\%\^\&\*\(\)\"\'\?\/\>\<\,\s]{1,100}/,"");
	if(ele.value.indexOf('.')!=-1){
		ele.value=ele.value.substring(0,ele.value.indexOf('.')+2);
	}
}
//验证数字，保留2位小数
function two(ele){
	ele.value=ele.value.replace(/\.{2,100}|[a-zA-Z]{1,100}|[\u4e00-\u9fa5]{1,100}|[\+\_\=\!\@\#\$\%\^\&\*\(\)\"\'\?\/\>\<\,\s]{1,100}/,"");
	if(ele.value.indexOf('.')!=-1){
		ele.value=ele.value.substring(0,ele.value.indexOf('.')+3);
	}
}
//验证数字，保留N位小数 参数为Dom
function any(ele,n){
	ele.value=ele.value.replace(/\.{2,100}|[a-zA-Z]{1,100}|[\u4e00-\u9fa5]{1,100}|[\+\_\=\!\@\#\$\%\^\&\*\(\)\"\'\?\/\>\<\,\s]{1,100}/,"");
	if(ele.value.indexOf('.')!=-1){
		ele.value=ele.value.substring(0,ele.value.indexOf('.')+n+1);
	}
}
//验证数字,保留N位小数 参数为String
function anyString(str,n){
	str=str+"";
	str=str.replace(/\.{2,100}|[a-zA-Z]{1,100}|[\u4e00-\u9fa5]{1,100}|[\+\_\=\!\@\#\$\%\^\&\*\(\)\"\'\?\/\>\<\,\s]{1,100}/,"");
	if(n==null||n.length==0||n==undefined){
		n=2;
	}
	if(str.indexOf('.')!=-1){
		str=str.substring(0,str.indexOf('.')+n+1);
	}
	return str;
}
//验证数字,保留N位小数 参数为Str,默认保留2位
function anyDouble(str,n){
	if(n==null||n==""||n==undefined){
		n=2;
	}
	str+="";
	if(str==null|str==undefined||str==""){
		return 0;
	}
	var flag=false;
	if(str.indexOf(".")==str.length-1){
		flag=true;
	}
	str=parseFloat(anyString(str,n+1));
	var tempValue=Math.pow(10, n);
	str=accDiv(Math.round(accMul(str,tempValue)),tempValue);
	if(isNaN(str)){
		return 0;
	}
	if(flag){
		str+=".";
	}
	return str;
}
//验证数字,保留N位小数 参数为Str,默认保留2位--吴国伟新加
function anyDouble2(str,n){
	if(n==null||n==""||n==undefined){
		n=2;
	}
	if(str==null|str==undefined||str==""){
		return 0;
	}
	str=parseFloat(anyString(str,n));
	var tempValue=Math.pow(20, n);
	str=accDiv(Math.round(accMul(str,tempValue)),tempValue);
	if(isNaN(str)){
		return 0;
	}
	return str;
}
//只能输入一定长度
function max(str,length){
	return str.substring(0,length+1);
}
//只能输入数字
function zero(ele){
	ele.value=ele.value.replace(/\D{1,100}/,"");
}
//长度匹配，小于
function less(str,lengthNum){
	if(str.length<lengthNum){
		return true;
	}else{
		return false;
	}
}
//长度匹配，大于
function more(str,lengthNum){
	if(str.length>lengthNum){
		return true;
	}else{
		return false;
	}
}
//测试
function test(ele){
	ele.value=ele.value.replace(/\.{2,100}|[a-zA-Z]{1,100}|[\u4e00-\u9fa5]{1,100}|[\+\_\=\!\@\#\$\%\^\&\*\(\)\"\'\?\/\>\<\,]{1,100}/,"");
	if(ele.value.indexOf('.')!=-1){
		ele.value=ele.value.substring(0,ele.value.indexOf('.')+3);
	}
}

//根据条件取数据，需jQuery json支持
function getData(str){
	var data={};
	$(str +' [name]').each(function(){
		if($(this).is(":checkbox,:radio")){
			if($(this).attr("checked")){
				data[$(this).attr("name")]=$.trim($(this).val());
			}
		}else{
			if($(this).is("select")){
				data[$(this).attr("name")]=$.trim($(this).find(":selected").val());
			}else{
				data[$(this).attr("name")]=$.trim($(this).val());
			}
		}
	});
	return JSON.stringify(data);
}
//根据条件取数组数据，需jQuery json支持
function getListData(str){
	var dataList=[];
	$(str).find("tr").each(function(){
		var tempData={};
		if($(this).is(".hidden")||$(this).is(".templete")){
			
		}else{
			$(this).find("[name]").each(function(){
				if($(this).is(":checkbox,:radio")){
					if($(this).attr("checked")){
						tempData[$(this).attr("name")]=$.trim($(this).val());
					}
				}else{
					if($(this).is("select")){
						tempData[$(this).attr("name")]=$.trim($(this).find(":selected").val());
					}else{
						tempData[$(this).attr("name")]=$.trim($(this).val());
					}
				}
			});
		}
		if(tempData!={}){
			dataList.push(tempData);
		}
	});
	return eval(JSON.stringify(dataList));
}
//提交数据。。。。我懒。。。
function sendData(url,jsonData){
	var str1;
	jQuery.ajax({
		url:url,
		data:"json="+jsonData.replace(/\%/g,"％").replace(/\+/g,'%2B').replace(/\=/g,"%3D").replace(/\&/g,"%26"),
		type:"POST",
		timeout:10000,
		async:false,
		success:function(str){
			str1= str;
		},
		error:function(e){
			alert("失败！请刷新页面或检查网络！");
		}
	});
	return str1;
}
//转成中文 如100转成壹佰元 需money.js 支持；另外此方法会找出所有需转换的值，故效率低
function toChinese(flag){
	if(flag==undefined||jQuery.trim(flag)==""){
		flag="[chinese]";
	}
	$(flag).each(function(){
		if($($(this).attr("chinese")).is("input")){
			$($(this).attr("chinese")).val(atoc($(this).val()));
		}else{
			$($(this).attr("chinese")).text(atoc($(this).val()));
		}
	});
}
//格式化钱，如10000格式化为10,000 保留2位小数
function formatMoney(str){
	str=anyDouble(anyString(str,3));
	str=str+"";
	var strInt="";
	var strFloat="";
	var point=str.indexOf(".");
	if(point!=-1){
		strInt=str.substring(0,point);
		strFloat=str.substring(point,str.length);
	}
	var count=0;
	var count1=0;
	var targetStr="";
	for(i=0;i<strInt.length;i++){
		targetStr=(strInt+"").substring(strInt.length-count1-1,strInt.length-count1)+targetStr;
		count++;
		count1++;
		if(count==3&&count1!=strInt.length){
			targetStr=","+targetStr;
			count=0;
		}
	}
	targetStr=targetStr+strFloat;
	return targetStr;
}
//格式化时间  参数  y||Y:年, M月 ,d||D:日 ,h||H时,m:分,s||S 秒        支持yyyy年MM月dd日 HH时 mm分 ss秒写法。。。。
function formatDate(date,format){
	var yearLength=0;
	var monthLength=0;
	var dayLength=0;
	var hourLength=0;
	var minuteLength=0;
	var secendLength=0;
	var getFlag= function(length,flag){
		var tempStr="";
		for(j=0;j<length;j++){
			tempStr+=flag;
		}
		return tempStr;
	}
	for(i=0;i<format.length;i++){
		switch(format.charAt(i)){
			case "y":yearLength++;break;
			case "M":monthLength++;break;
			case "d":dayLength++;break;
			case "D":dayLength++;break;
			case "H":hourLength++;break;
			case "h":hourLength++;break;
			case "m":minuteLength++;break;
			case "s":secendLength++;break;
			case "S":secendLength++;break;
		}
	}
	if(yearLength!=0){
		format=format.replace(getFlag(yearLength,"y"),(date.getFullYear()+"").substring(4-yearLength,4));
	}
	if(yearLength!=0){
		format=format.replace(getFlag(yearLength,"Y"),(date.getFullYear()+"").substring(4-yearLength,4));
	}
	if(monthLength!=0){
		format=format.replace(getFlag(monthLength,"M"),(date.getMonth()+1+"").substring(2-monthLength,2));
	}
	if(dayLength!=0){
		format=format.replace(getFlag(dayLength,"d"),(date.getDate()+"").substring(2-dayLength,2));
	}
	if(dayLength!=0){
		format=format.replace(getFlag(dayLength,"D"),(date.getDate()+"").substring(2-dayLength,2));
	}
	if(hourLength!=0){
		format=format.replace(getFlag(hourLength,"h"),(date.getHours()+"").substring(2-hourLength,2));
	}
	if(hourLength!=0){
		format=format.replace(getFlag(hourLength,"H"),(date.getHours()+"").substring(2-hourLength,2));
	}
	if(minuteLength!=0){
		format=format.replace(getFlag(minuteLength,"m"),(date.getMinutes()+"").substring(2-minuteLength,2));
	}
	if(secendLength!=0){
		format=format.replace(getFlag(secendLength,"s"),(date.getSeconds()+"").substring(2-secendLength,2));
	}
	if(secendLength!=0){
		format=format.replace(getFlag(secendLength,"S"),(date.getSeconds()+"").substring(2-secendLength,2));
	}
	return format;
}
//date插件 参数必须是class名，不用加"."号
function dateUI(flag){
	if(flag==undefined){
		flag="date";
	}
	
	
	jQuery(flag).datepicker({
		closeText: '关闭',
		prevText: '&#x3c;上月',
		nextText: '下月&#x3e;',
		currentText: '今天',
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一','二','三','四','五','六','七','八','九','十','十一','十二'],
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		
		changeMonth:true,
		changeYear:true,
		showWeek:true,
		showButtonPanel: true,
		showOtherMonths:true,
		selectOtherMonths:true,
		autoSize:false,
		
		showMonthAfterYear: true});
	$(flag).each(function(){
		if($(this).attr(flag)!=undefined){
			$(this).val($(this).attr(flag));
		}
	})
}
//输入空的验证
function checkNull(flag){
	if(flag==undefined||flag==""){
		flag=".checkNull";
	}
	var booleanFlag=true;
	$(flag).each(function(){
		var value=$.trim($(this).val());
		if(value.length==0||value==""||value==undefined){
			$(this).addClass("red");
			booleanFlag=false;
		}else{
			$(this).removeClass("red");
		}
	});
	$(flag).focusin(function(){$(this).removeClass("red")});
	return booleanFlag;
}
//清除某个范围内的数据
function clearAll(flag){
	if(flag==undefined||jQuery.trim(flag)==""){
		return false;
	}
	$(flag).find(":text,select,:radio,:checkbox").each(function(){
		var temp=$(this);
		if(temp.is(":text")){
			temp.val("");
		}else{
			if(temp.is("select")){
				temp.find(":selected").removeAttr("selected");
			}else{
				if(temp.is(":radio")){
					temp.removeAttr("checked");
				}else{
					if(temp.is(":checkbox")){
						temp.removeAttr("checked");
					}
				}
			}
		}
	})
}
//设置某范围内的元素可用或不可用，默认第一次调用的时候会把元素设成不可用 flag:范围 intflag:初始值，1可用,0不可用 方法HU全名 HoverUsability 太长 懒得写
function HU(flag,intflag){
	var target=$(flag);
	var flag;
	if(intflag==undefined||intflag==""){
		intflag=0;
	}else{
		intflag=1;
	}
	if(target.data("useFlag")){
		flag=target.data("useFlag");
	}else{
		if(intflag==1){
			intflag=0;
		}else{
			intflag=1;
		}
		flag=intflag;
	}
	if(flag!=0){
		target.data("useFlag","0");
		target.find(":text,select,:radio,:checkbox,:button").attr("disabled","disabled");
	}else{
		target.data("useFlag","1");
		target.find(":text,select,:radio,:checkbox,:button").removeAttr("disabled");
	}	
}
//组合数组，去数组中重复元素，参数：dataList数据；flagList标记数组，标记以什么字段区别数据数组中重复项；flag标记数据数组中以什么字段求和，该字段必须为整形;totalColnum:
function getTotalList(dataList,flagList,flag,totalColnum){
	if(flag==totalColnum&&totalColnum!=undefind&&totalColnum!=undefind){
		alert("flag和totalColnum参数不能相等，会出错！")
		return [];
	}
	var compareObject=function (Object1,Object2){
		var tempFlag=true;
		for(var key in Object1){
			tempFlag=Object1[key]==Object2[key]&&tempFlag;
			if(!tempFlag){
				return tempFlag;
			}
		}
		return tempFlag;
	};
	var targetList=[];
	if(dataList==undefined||dataList.length==0){
		return [];
	}
	if(flagList==undefined){
		flagList=[];
		for(var flagEle in dataList[0]){
			flagList.push(flagEle);
		}
	}
	for(var k=0;k<dataList.length;k++){
		var tempObject1={};
		for( var i=0;i<flagList.length;i++){
			tempObject1[flagList[i]]=dataList[k][flagList[i]];
		}
		var countFlag=0;
		for(var i=0;i<targetList.length;i++){
			var tempFlag=false;
			var tempObject2={};
			for(var j=0;j<flagList.length;j++){
				tempObject2[flagList[j]]=targetList[i][flagList[j]];
			}
			tempFlag=compareObject(tempObject1,tempObject2)||false;
			if(tempFlag){
				countFlag=i;
				break;
			}
		}
		if(tempFlag){
			if(totalColnum!=""&&totalColnum!=undefined)targetList[countFlag][totalColnum]+=","+dataList[k][totalColnum];
			if(flag!=""&&flag!=undefined)targetList[countFlag][flag]=parseInt(targetList[countFlag][flag])+parseInt(dataList[k][flag]);
		}else{
			targetList.push(dataList[k]);
		}
	}
	return targetList;
}
//算合 flag参数是class名 取class名下元素text的和 scope是范围 
function getTIT(flag,scope){
	var total=0;
	var ele;
	if(scope != null && scope !=undefined && scope!= ""){
		ele=$(scope+" ."+flag);
	}else{
		ele=$("."+flag);
	}
	ele.each(function(){
		if($(this).text()!=undefined&&$(this).text()!=""){
			total=parseFloat(total)+parseFloat($(this).text());
		}
	});
	return anyDouble(total);
}
//算合 flag参数是class名 取class名下元素value的和 scope是范围 
function getTIV(flag,scope){
	var total=0;
	var ele;
	if(scope != null && scope !=undefined && scope!= ""){
		ele=$(scope+" ."+flag);
	}else{
		ele=$("."+flag);
	}
	ele.each(function(){
		if($(this).val()!=undefined&&$(this).val()!=""){
			total=parseFloat(total)+parseFloat($(this).val());
		}
	});
	return anyDouble(total);
}
//以下代码使用条件比较苛刻。。。含风险。。。
//初始化统计总和并格式化总合成员的输入值
function initTotal(scopeName,targetName,membersName,flag){
	var result=0;
	var temp=0;
	if(membersName==null||membersName==""){
		membersName=".member";
	}
	$(membersName).keyup(function(){
		if(flag==1){
			one(this);
		}else{
			if(flag==2){
				two(this);
			}else{
				if(flag==0){
					zero(this);
				}else{
					two(this);
					flag=2;
				}
			}
		}
	});
	$(scopeName).keyup(function(e){
		result=0;
		var target=$(e.target);
		if(target.is(membersName)){
			$(membersName).each(function(){
				if($(this).val()==null||$(this).val()==""||$(this).val().length==0){
					temp=0;
				}else{
					if(isNaN(temp)){
						temp=0;
					}else{
						temp=$(this).val();
						result=accAdd(parseFloat(result),parseFloat(temp));
					}
				}
			});
			$(targetName).val(anyString(result,flag));
		}
	});
}
//多功能算和器。。。。。。targetFlag:根据什么算和；totalFlag:最后的和(可为空， 默认为#total)；otherFlag:其它需要加入和的标记；valueFalg:某个元素的值（可为空，默认为.value）；percentFlag:某个元素的百分数（可为空，默认为.percent）；
function initPercentValue(targetFlag,otherFlag,totalFlag,valueFlag,percentFlag){
	if($(targetFlag).val()!=null&&$(targetFlag).val()!=null&&!isNaN($(targetFlag).val()))value=parseFloat($(targetFlag).val());
	if(!valueFlag||valueFlag==undefined){
		valueFlag=".value";
	}
	if(!percentFlag||percentFlag==undefined){
		percentFlag=".percent";
	}
	if(!totalFlag||totalFlag==undefined){
		totalFlag="#total";
	}
	var getPercent=function(ele){
		$(ele).parents("td").find(percentFlag).val(anyString(accMul(accDiv($(ele).val(),$.trim($(targetFlag).val())),100)));
	};
	var getValue=function(ele){
		$(ele).parents("td").find(valueFlag).val(anyString(accDiv(accMul($(ele).val(),$.trim($(targetFlag).val())),100)));
	};
	var getTotal=function(){
		var count=0;
		$(valueFlag).each(function(){
			if($(this).val().length!=0){
				count=accAdd(count,$(this).val());
			}
		});
		$(otherFlag).each(function(){
			if($(this).val().length!=0){
				count=accAdd(count,$(this).val());
			}
		});
		$(totalFlag).val(count);
	};
	var onValueChange=function(ele){
		getPercent(ele);
		getTotal();
	};
	var onPercentChange=function(ele){
		getValue(ele);
		getTotal();
	};
	var onTotalChange=function(){
		$(percentFlag).each(function(){
			getValue(this);
		});
		getTotal();
	};
	$(targetFlag+","+valueFlag+","+percentFlag).each(function(){$(this).keyup(function(){two(this);});});
	if(otherFlag&&otherFlag!=undefined){
		$(otherFlag).each(function(){$(this).keyup(function(){two(this);});});
		$(otherFlag).keyup(getTotal);
	}
	$(targetFlag).keyup(onTotalChange);
	$(valueFlag).keyup(function(){onValueChange(this);});
	$(percentFlag).keyup(function(){onPercentChange(this);});
}
//判断一个字符是否包含在另一字符中。。。。参数无顺序
function strLike(str1,str2){
	if(str1.indexOf(str2)!=-1 || str2.indexOf(str1)!=-1){
		return true;
	}else{
		return false;
	}
}

//组合数组，去数组中重复元素，参数：dataList数据；flagList标记数组，标记以什么字段区别数据数组中重复项；flag标记数据数组中以什么字段求和，该字段必须为整形;totalColnum:
function getTotalList(dataList,flagList,flag,totalColnum){
	if(flag==totalColnum&&totalColnum!=undefind&&totalColnum!=undefind){
		alert("flag和totalColnum参数不能相等，会出错！")
		return [];
	}
	var compareObject=function (Object1,Object2){
		var tempFlag=true;
		for(var key in Object1){
			tempFlag=Object1[key]==Object2[key]&&tempFlag;
			if(!tempFlag){
				return tempFlag;
			}
		}
		return tempFlag;
	};
	var targetList=[];
	if(dataList==undefined||dataList.length==0){
		return [];
	}
	if(flagList==undefined){
		flagList=[];
		for(var flagEle in dataList[0]){
			flagList.push(flagEle);
		}
	}
	for(var k=0;k<dataList.length;k++){
		var tempObject1={};
		for( var i=0;i<flagList.length;i++){
			tempObject1[flagList[i]]=dataList[k][flagList[i]];
		}
		var countFlag=0;
		for(var i=0;i<targetList.length;i++){
			var tempFlag=false;
			var tempObject2={};
			for(var j=0;j<flagList.length;j++){
				tempObject2[flagList[j]]=targetList[i][flagList[j]];
			}
			tempFlag=compareObject(tempObject1,tempObject2)||false;
			if(tempFlag){
				countFlag=i;
				break;
			}
		}
		if(tempFlag){
			if(totalColnum!=""&&totalColnum!=undefined)targetList[countFlag][totalColnum]+=","+dataList[k][totalColnum];
			if(dataList[k][flag]!=null&&flag!=""&&flag!=undefined&&!isNaN(dataList[k][flag]))targetList[countFlag][flag]=parseInt(targetList[countFlag][flag])+parseInt(dataList[k][flag]);
		}else{
			targetList.push(dataList[k]);
		}
	}
	return targetList;
}

//LLL专用。。
function LLLInitBase(){
	$(":text").focusin(function(){
		this.select();
	});
	dateUI();
}








//精确计算浮点数 网上C的 加法
function accAdd(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
	m=Math.pow(10,Math.max(r1,r2));
	return (arg1*m+arg2*m)/m;
}
//精确计算浮点数 网上C的 乘法
function accMul(arg1,arg2)
{
var m=0,s1=arg1.toString(),s2=arg2.toString();
try{m+=s1.split(".")[1].length;}catch(e){}
try{m+=s2.split(".")[1].length;}catch(e){}
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
//精确计算浮点数 网上C的 减法
function Subtr(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
    try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
    m=Math.pow(10,Math.max(r1,r2));
    //last modify by deeka
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}
//精确计算浮点数 网上C的 除法
function accDiv(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length;}catch(e){}
	try{t2=arg2.toString().split(".")[1].length;}catch(e){}
	with(Math){
	r1=Number(arg1.toString().replace(".",""));
	r2=Number(arg2.toString().replace(".",""));
	return (r1/r2)*pow(10,t2-t1);
	}
} 





//精确计算浮点数 网上C的 加法
function jia(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
	m=Math.pow(10,Math.max(r1,r2));
	return (arg1*m+arg2*m)/m;
}
//精确计算浮点数 网上C的 乘法
function ceng(arg1,arg2)
{
var m=0,s1=arg1.toString(),s2=arg2.toString();
try{m+=s1.split(".")[1].length;}catch(e){}
try{m+=s2.split(".")[1].length;}catch(e){}
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
//精确计算浮点数 网上C的 减法
function jian(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
    try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
    m=Math.pow(10,Math.max(r1,r2));
    //last modify by deeka
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}
//精确计算浮点数 网上C的 除法
function cu(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length;}catch(e){}
	try{t2=arg2.toString().split(".")[1].length;}catch(e){}
	with(Math){
	r1=Number(arg1.toString().replace(".",""));
	r2=Number(arg2.toString().replace(".",""));
	return (r1/r2)*pow(10,t2-t1);
	}
} 
//电话号码验证,Dom对象
function IsTelephone(key)// 正则判断
{ 
	//var pattern=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}(13|15|18|14)[0-9]{9}$)/; 
	var obj=$(key).val();
	if(obj.length==11) 
	{ 
		return true; 
	} 
	else 
	{ 
		alert("联系方式输入错误，请重新输入！");
		$(key).val("");
		return false; 
	} 
}
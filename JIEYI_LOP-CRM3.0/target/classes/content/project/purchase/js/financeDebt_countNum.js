
/**
 * 计算减法
 * @param {Object} minClass 被减数类名
 * @param {Object} meiClass 减数类名
 * @param {Object} inputId 要显示结果的ID
 */
function countSub(minClass,meiClass,inputId){
	var result=0;
	var min=0;  //被减数
	var mei=0;  //减数
	var temp="";
	
	min=myParseFloat($("."+minClass).val());
	$("."+meiClass).each(function (){
		mei=myParseFloat($(this).val());
		min=min-mei;
	});
		
	
	$("#"+inputId).val(anyDouble(min));
	var mychange=$("#"+inputId).attr("myonchange");
	if (mychange!=undefined&&mychange.length>0)
	{
		eval(mychange);
	}

}
/**
 * 计算加法
 * @param {Object} addClass 要相加的数的类名
 * @param {Object} inputId 要显示结果的ID
 */
function countAdd(addClass,inputId)
{
	var result=0; //结果
	//var temp=""; //临时变量
	$("."+addClass).each(function (){
		//temp=
		result=parseFloat(result)+myParseFloat($(this).val());
	});
	
	$("#"+inputId).val(anyDouble(result));
	var mychange=$("#"+inputId).attr("myonchange");
	if (mychange!=undefined&&mychange.length>0)
	{
		eval(mychange);
	}

}

/*
 * 将字符串转换为float
 * 如果不是数字返回0
 */
function myParseFloat(string)
{
	if (string.length>0&&(!isNaN(string))){
			string=parseFloat(string);
		}
		else
		{
			string=0;
		}
		return string;
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

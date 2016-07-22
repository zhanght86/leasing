
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
 * 将字符串转换为float 如果不是数字返回0
 */
function myParseFloat(string) {
	if (string.length > 0 && (!isNaN(string))) {
		string = parseFloat(string);
	} else {
		string = 0;
	}
	return string;
}

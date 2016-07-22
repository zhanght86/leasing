function SchemeView(SCHEME_ROW_NUM,PROJECT_ID,PROJECT_CODE,ROWNUMBER,PLATFORM_TYPE){
	top.addTab(PROJECT_CODE+"项目方案"+ROWNUMBER+"查看",_basePath+"/project/project!SchemeView.action?SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE);
}


/**
 * 小写金额转换大写
 * @param numberValue
 * @return
 */
function atoc(numberValue) {
	var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for ( var i = 0; i < len; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		if (i != (len - 3) && i != (len - 7) && i != (len - 11)
				&& i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
}


function getAllTotal()
{
	var allTotal=0;
	$("input[name='MONEY_CZJE']").each(function (){
		var total=$(this).val();
		if (total==''||total==undefined)
		{
			total=0;
		}
		allTotal=parseFloat(total)+parseFloat(allTotal);
	});
	$("#LHMONEYALL").text(allTotal);
	$("#MONEY_TOTALLH").val(allTotal);
}

function SAVE(){
	var eqTotalAll=$("#eqTotalAll").val();
	var MONEY_TOTALLH=$("#MONEY_TOTALLH").val();
	var NUM_FLAG=$("#NUM_FLAG").val();
	if((NUM_FLAG=='0' && parseFloat(eqTotalAll)==parseFloat(MONEY_TOTALLH)) || NUM_FLAG!='0'){
		
		var START_DATE=$("input[name='START_DATE']").val();
		if(START_DATE==null||START_DATE==''||START_DATE=='undefined'||START_DATE==undefined){
			alert("请输入起租日期");
			return false;
		}
		$("input[name='START_DATE_CHANGE']").val(START_DATE);
		
		var REPAYMENT_DATE=$("select[name='REPAYMENT_DATE']").val();
		if(REPAYMENT_DATE==null||REPAYMENT_DATE==''||REPAYMENT_DATE=='undefined'||REPAYMENT_DATE==undefined){
			alert("请选择还款日");
			return false;
		}
		
		$("input[name='REPAYMENT_DATE_CHANGE']").val(REPAYMENT_DATE);
		
//		if(REPAYMENT_DATE<1 || REPAYMENT_DATE>31){
//			alert("你输入的还款日不合法!");
//			return false;
//		}
		
		var FL=[];
		
		
			$(".fl_body_tr").each(function(){
					var temp={};
					temp.JOIN_ID= $(this).find("select[name='JOIN_ID']").val();
					temp.MONEY=$(this).find("input[name='MONEY_CZJE']").val();
					temp.MONEY_TOTAL=eqTotalAll;
					FL.push(temp);
			});
		
		var PROJECT_ID=$("input[name='PROJECT_ID']").val();
		var SCHEME_ROW_NUM=$('input:radio:checked').val();
		var LHSQFS=$("select[name='LHSQFS']").val();
		 
		 var dataJson ={
				 FLLIST:FL,
				 PROJECT_ID:PROJECT_ID,
				 SCHEME_ROW_NUM:SCHEME_ROW_NUM,
				 START_DATE_CHANGE:START_DATE,
				 REPAYMENT_DATE_CHANGE:REPAYMENT_DATE,
				 LHSQFS:LHSQFS
				};
				$("#ChangeViewData").val(JSON.stringify(dataJson));
				$("#formSchemeSubmit").submit();
	}else{
		alert("出资金额合计需等于设备总额方可下一步。");
	}
}
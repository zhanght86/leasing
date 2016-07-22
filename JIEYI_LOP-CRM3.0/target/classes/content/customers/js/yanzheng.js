//验证身份证号码
function isCardNo(ele,id_card_type){ 		
	var card = $("#"+ele).val();
	if(id_card_type=="1" || id_card_type=="4"){//驗證是否是居民身份證//若果是居民身份證則驗證身份證是否填寫正確
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
		if(!isIdCardNo(card)){
//			$("#"+ele).val("");
			$("#"+ele)[0].focus(); 
	        return  false;  
		}
//	    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
//	    if(reg.test(card) === false){  
//	        alert("身份证输入不合法");  
//	        $("#"+ele).val("");
//	        return  false;  
//	    }
	    else{
	    	var bornDate = cardNo2bornDate(card);
	    	var i= parseInt(card.charAt(card.length - 1))  ;
	    	if(card.length>15){
	    		i= parseInt(card.charAt(card.length - 2))  ;
	    	}
	    	if(ele=='ID_CARD_NO' &&bornDate!=null&&bornDate!='') {
				$('#BIRTHDAY').datebox('setValue',bornDate);
				var age=jsGetAge(bornDate);
				$("#AGE").val(age);
				console.info($('#BIRTHDAY'));
			    	$("#SEX").val(0);
			    	if(i%2==0){
			    		$("#SEX").val(1);
			    	}
	    	} else if(ele=='SPOUDT_ID_CARD_NO' &&bornDate!=null&&bornDate!=''){
	    		$('#SPOUST_BIRTHDAY').datebox('setValue',bornDate);
	    			
			    	$("#SPOUST_SEX").val(0);
			    	if(i%2==0){
			    		$("#SPOUST_SEX").val(1);
			    	}
	    	}
	    	
	   		isHasSpouse() ;
	    
	    	
	    	
	    	return true;
	    } 
	}else {
		if(id_card_type=="2"){
			
//			 alert("港澳居民来往内地通行证输入错误");
			 $("#"+ele)[0].focus(); 
		}else if(id_card_type=="3"){
			var reg = /(P\d{7})|(G\d{8})/;  
		    if(reg.test(card) === false){  
		    	alert("护照输入错误");
				$("#"+ele)[0].focus();   
		    }else{
		    	return true;
		    } 
			
		}else if(id_card_type=="5"){
//			alert("台湾居民来往大陆通行证输入错误");
			$("#"+ele)[0].focus(); 
		}else if(id_card_type=="6"){
			var reg = /^[0-9]{8}$/;  
		    if(reg.test(card) === false){  
		    	alert("军官证输入错误");
				$("#"+ele)[0].focus();   
		    }else{
		    	return true;
		    } 
			
		}
		return true;
	}  
}

function isHasSpouse(){
	
	var v = $("select[name=SEX]").attr("selected","selected").val();
	var IS_MARRY = $("select[name=IS_MARRY]").attr("selected","selected").val();
	
	if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
		
		var v1 = 0 ;
		
		if(v==v1){
			v1=1 ;
		}
		$("select[name=SPOUST_SEX]").val(v1);
	}
	
}

//function validForm(formId) {
//	var flag = false;
//	$("#"+formId+" :text").each(function(){
//		if($(this).val()) {
//			flag = true;
//			return false;
//		}
//	});
//	if(!flag) alert("请填写相关信息");
//	return flag;
//}
function validForm(formId) {
	var flag = true;
	$("#"+formId+" :text").each(function(){
		if($(this).val()=="" || $(this).val()==null) {
			$(this).focus();
			$(this).addClass('error');
			flag = false;
			return flag;
		}
		$(this).removeClass('error');
	});
	if(!flag) alert("请填写相关信息");
	return flag;
}

function clearForm(formId) {
	$("#"+formId+" :text,textarea").val("");
}

function isCardNoRight(cardNo) {
	// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
    if(reg.test(cardNo) === false){  
        return  false;  
    }else{
    	return true;
    } 
}

function cardNo2bornDate(cardNo) {
	if(cardNo.length==18){
		var str1 = cardNo.substr(6,8);
		var year = str1.substr(0,4);
		var month = str1.substr(4,2);
		var day = str1.substr(6);
		return year + '-' + month + '-' + day;
	}else if(cardNo.length==15){
		var str1 = cardNo.substr(6,6);
		var year = str1.substr(0,2);
		var month = str1.substr(2,2);
		var day = str1.substr(4);
		
		var today = new Date();
		var myDate=new Date();
		myDate.setFullYear("20"+year,month,day);
		if(myDate>today){
			year="19"+year;
		}else{
			year="20"+year;
		}
		return year + '-' + month + '-' + day;
	}else
		return '';
}

//Email验证
function isValidMail(val){
	var value = $("#"+val).val();
	var reMail = /^(?:[a-z\d]+[_\-\+\.]?)*[a-z\d]+@(?:([a-z\d]+\-?)*[a-z\d]+\.)+([a-z]{2,})+$/i;
	if(!reMail.test(value))
	{
		alert("输入的Email有误"); 
        return false; 
	}
	return true;
}

/**
 * 手机号码验证 [不带空格]
 * @param ele: 节点id名称
 * @author yx
 * @date 2013-11-29
 **/
 function isMobilephone(ele){
	 var tel = $("#"+ele).val();
//	 var reg = /(^0?[1][3578][0-9]{9}$)/;
//	 if(reg.test(tel) == false){
	if(tel.length != 11){
		 alert("请输入有效的手机号码！");  
//		 $("#"+ele).val("");
		 $("#"+ele).focus();
		 return false; 
	 }else{
		 //add by lishuo 03-18-2016 捷越黑名单数据验证 Strart
		 $.ajax({
		url:_basePath+"/crm/Customer!doCheckBlackPhone.action",
		data : {"DATAVALUE":tel},
		type:"post",
		dataType:"json",
		success:function (json){
			
			if(json.msg){
				$("#msg").attr("color","red");    
				$("#msg").text(json.msg);
				
			}else{
				$("#msg").attr("color","green");    
				$("#msg").text("验证通过");
				
			}
			
		}
	});
		//add by lishuo 03-18-2016 捷越黑名单数据验证   END
		 return true;
	 }
 }
 
 /**
  * 手机号码验证 [不带空格]
  * @param ele: 节点id名称
  * @author yx
  * @date 2013-11-29
  **/
  function isMobilephone2(ele){
 	 var tel = $("#"+ele).val();
// 	 var reg = /(^0?[1][3578][0-9]{9}$)/;
// 	 if(reg.test(tel) == false){
 	if(tel.length==0){
		 
//		 $("#"+ele).val("");
		 $("#"+ele).focus();
		 return false; 
 	}
 	if(tel.length != 11){
 		 alert("请输入有效的手机号码！");  
// 		 $("#"+ele).val("");
 		 $("#"+ele).focus();
 		 return false; 
 	 }else{
 		 //add by lishuo 03-18-2016 捷越黑名单数据验证 Strart
 		 $.ajax({
 		url:_basePath+"/crm/Customer!doCheckBlackPhone.action",
 		data : {"DATAVALUE":tel},
 		type:"post",
 		dataType:"json",
 		success:function (json){
 			
 			if(json.msg){
 				
 				$("#"+"2"+ele).attr("color","red");   
 				$("#"+"2"+ele).text(json.msg);
 				 return false; 
 			}else{
 				
 				$("#"+"2"+ele).attr("color","green");   
 				$("#"+"2"+ele).text("验证通过");
 			}
 			
 		}
 	});
 		//add by lishuo 03-18-2016 捷越黑名单数据验证   END
 		 return true;
 	 }
  }
 
 /**
  * 有效的电话号码验证[不带空格]
  * @param ele: 节点id名称
  * @return
  * @author yx
  * @date 2013-11-29
  */
 function isPhoneNo(ele){
	 var tel = $("#"+ele).val();
	 var reg = /(^(0[0-9]{2,3}\-)+([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/;
	 if(reg.test(tel) == false){
		 alert("请按【区号-座机号】格式输入！");  
//		 $("#"+ele).val("");
		 $("#"+ele).focus();
		 return false; 
	 }else{
		 return true;
	 }
 }
 
 //只能输入数字和横刚
 function isPhoneNoYanz(ele){
	 var value=ele.value;
		if(!(/[^\d{1,100}\-]/.test(value)))
		{
			ele.value=value.replace(/[^\d{1,100}\-]/,"");
		}
		else
		{
			ele.value="";
		}
 }
 
 /**
  * 有效的邮编验证[不带空格]
  * @param ele: 节点id名称
  * @return
  * @author yx
  * @date 2013-11-29
  */
 function isZipcode(ele){
	 var code = $("#"+ele).val();
	 var reg = /^[0-9]\d{5}$/;
	 if(reg.test(code) == false){
		 alert("请输入有效的邮政编码.");  
		 $("#"+ele).val("");
		 return false; 
	 }else{
		 return true;
	 }
 }
  
 /**
  * 有效的传真验证[不带空格]
  * @param ele: 节点id名称
  * @return
  * @author yx
  * @date 2013-11-29
  */
function isFax(ele){
	var fax = $("#"+ele).val();
	var reg = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	 if(reg.test(fax) == false){
		 alert("请输入有效的传真号码,如:010-29292929.");  
		 $("#"+ele).val("");
		 return false; 
	}else{
		 return true;
	}
}
  
  
/**
 * 有效的组织机构代码证验证[不带空格]
 * @param ele: 节点id名称
 * @return
 * @author yx
 * @date 2013-11-29
 */
function isOragnizationCode(ele){
	var OragnizationCode = $("#"+ele).val();
	var reg = /^([0-9]{8}\-)?([0-9]{1})$/;
	 if(reg.test(OragnizationCode) == false){
		 alert("请输入有效组织机构代码证号.");  
//		 $("#"+ele).val("");
		 $("#"+ele).focus();
		 return false; 
	}else{
		 return true;
	}  
}

/**
 * 有效的税务登记证
 * @param ele: 节点id名称
 * @return
 * @author yx
 * @date 2013-11-29
 */
function isTaxCode(ele){
	var TaxCode = $("input[name="+ele+"]").val();
	var reg = /^([0-9]{15})$/;
	 if(reg.test(TaxCode) == false){
		 alert("请输入有效的税务登记证号.");  
//		 $("input[name="+ele+"]").val("");
		 $("input[name="+ele+"]").focus();
		 return false; 
	}else{
		 return true;
	}  
}

/**
 * 有效的营业执照号
 * @param ele: 节点id名称
 * @return
 * @author yx
 * @date 2013-11-29
 */
function isCorpLicense(ele){
	var CORP_BUSINESS_LICENSE = $("#"+ele).val();
	var reg = /^([0-9]{15})$/;
	 if(reg.test(CORP_BUSINESS_LICENSE) == false){
		 alert("请输入有效的营业执照号.");  
//		 $("#"+ele).val("");
		 $("#"+ele).focus();
		 return false; 
	}else{
		 return true;
	}  
}

/**
 * 有效的数字验证,可以带小数[不带空格]
 * @param ele: 节点id名称
 * @return
 * @author yx
 * @date 2013-11-29
 */
function zpyNumber(ele){
	var number = $("#"+ele).val();
	var reg = /^[0-9]{1}[0-9\,\.]*?$/;
	 if(reg.test(number) == false){
		 alert("请输入数字.");  
		 $("#"+ele).val("");
		 return false; 
	}else{
		 return true;
	}
}

/**
 * 有效的数字验证[不带空格]
 * @param ele: 节点id名称
 * @return
 * @author yx
 * @date 2013-11-29
 */
function zpyIntNumber(ele){
	var IntNumber = $("#"+ele).val();
	var reg = /^[0-9]{1}[0-9]*?$/;
	 if(reg.test(IntNumber) == false){
		 alert("请输入整数.");  
		 $("#"+ele).val("");
		 return false; 
	}else{
		 return true;
	}
}



function jsGetAge(strBirthday)
{      
    var returnAge;
    var strBirthdayArr=strBirthday.split("-");
    var birthYear = strBirthdayArr[0];
    var birthMonth = strBirthdayArr[1];
    var birthDay = strBirthdayArr[2];
   
    var d = new Date();
    var nowYear = d.getYear()+1900+1;
    var nowMonth = d.getMonth() + 1;
    var nowDay = d.getDate();
   
    if(nowYear == birthYear)
    {
        returnAge = 0;//同年 则为0岁
    }
    else
    {
        var ageDiff = nowYear - birthYear ; //年之差
        if(ageDiff > 0)
        {
            if(nowMonth == birthMonth)
            {
                var dayDiff = nowDay - birthDay;//日之差
                if(dayDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
            else
            {
                var monthDiff = nowMonth - birthMonth;//月之差
                if(monthDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
        }
        else
        {
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
        }
    }
   
    return returnAge;//返回周岁年龄
   
}










/*
根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。
出生日期计算方法。
15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
下面是正则表达式:
出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
15位校验规则 6位地址编码+6位出生日期+3位顺序号
18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
公式(1)中：
i----表示号码字符从由至左包括校验码在内的位置序号；
ai----表示第i位置上的号码字符值；
Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1
*/
//身份证号合法性验证 
//支持15位和18位身份证号
//支持地址编码、出生日期、校验位验证

/**
* 身份证校验
* @param {Object} num
*/
function isIdCardNo(num){
   num = num.toUpperCase();
   //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
   if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
       alert('请输入正确的证件号！输入的证件号长度不对，或者号码不符合规定！');
       return false;
   }else if( num.length==18 && !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(num)){
		alert('输入的身份证号出生日期不对！\n 格式为yyyymmdd 年份在1800-2099区间 月份01-12 日 01-31');
       return false;
	}
   
   var city = {
       11: "北京",
       12: "天津",
       13: "河北",
       14: "山西",
       15: "内蒙古",
       21: "辽宁",
       22: "吉林",
       23: "黑龙江 ",
       31: "上海",
       32: "江苏",
       33: "浙江",
       34: "安徽",
       35: "福建",
       36: "江西",
       37: "山东",
       41: "河南",
       42: "湖北 ",
       43: "湖南",
       44: "广东",
       45: "广西",
       46: "海南",
       50: "重庆",
       51: "四川",
       52: "贵州",
       53: "云南",
       54: "西藏 ",
       61: "陕西",
       62: "甘肃",
       63: "青海",
       64: "宁夏",
       65: "新疆",
       71: "台湾",
       81: "香港",
       82: "澳门",
       91: "国外 "
   };
   if (!city[num.substr(0, 2)]) {
       alert('地址编码错误, 如 [36: 江西 11: 北京]');
       return false;
   }
   
   //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
   //下面分别分析出生日期和校验位 
   var len, re;
   len = num.length;
   if (len == 15) {
       re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
       var arrSplit = num.match(re);

       //检查生日日期是否正确 
       var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
       var bGoodDay;
       bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
       if (!bGoodDay) {
           alert('输入的身份证号里出生日期不对！');
           return false;
       }
       else {
           //将15位身份证转成18位 
           //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
           var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
           var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
           var nTemp = 0, i;
           num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
           for (i = 0; i < 17; i++) {
               nTemp += num.substr(i, 1) * arrInt[i];
           }
           num += arrCh[nTemp % 11];
           return num;
       }
   }
   if (len == 18) {
       re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
       var arrSplit = num.match(re);
       
		//判断年份不能超过当前系统年份
		var d = new Date();
		var nowYear = d.getFullYear();
		var inpYear = num.substr(6,4);
		//alert(inpYear);
		if( inpYear> nowYear){
			alert("输入的身份证号出生日期年份不允许大于["+nowYear+"]！");
			return false;
		}
		
       //检查生日日期是否正确 
       var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
       var bGoodDay;
       bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
       if (!bGoodDay) {
           //alert(dtmBirth.getYear());
           //alert(arrSplit[2]);
           alert('输入的身份证号里出生日期不对！');
           return false;
       }
       else {
           //检验18位身份证的校验码是否正确。 
           //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
           var valnum;
           var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
           var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
           var nTemp = 0, i;
           for (i = 0; i < 17; i++) {
               nTemp += num.substr(i, 1) * arrInt[i];
           }
           valnum = arrCh[nTemp % 11];
           if (valnum != num.substr(17, 1)) {
               alert('您输入的身份证最后一位校验码不正确！理论应该为：' + valnum);
               return false;
           }
           return num;
       }
   }
   return false;
}



 
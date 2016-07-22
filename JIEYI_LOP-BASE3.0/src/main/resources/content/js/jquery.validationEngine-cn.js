

(function($) {
	$.fn.validationEngineLanguage = function() {};
	$.validationEngineLanguage = {
		newLang: function() {
			$.validationEngineLanguage.allRules = 	{"required":{    			// Add your regex rules here, you can take telephone as an example
						"regex":"none",
						"alertText":"* 非空选项.",
						"alertTextCheckboxMultiple":"* 请选择一个单选框.",
						"alertTextCheckboxe":"* 请选择一个复选框."},
					"length":{
						"regex":"none",
						"alertText":"* 长度必须在 ",
						"alertText2":" 至 ",
						"alertText3": " 之间."},
					"maxCheckbox":{
						"regex":"none",
						"alertText":"* 最多选择 ",
						"alertText2":" 项."},
					"zpyNumber":{
						"regex":"/^[0-9]{1}[0-9\,\.]*?$/",
						"alertText":"* 请输入数字."},
					"zpyIntNumber":{
						"regex":"/^[0-9]{1}[0-9]*?$/",
						"alertText":"* 请输入数字."},
					"minCheckbox":{
						"regex":"none",
						"alertText":"* 至少选择 ",
						"alertText2":" 项."},	
					"confirm":{
						"regex":"none",
						"alertText":"* 两次输入不一致,请重新输入."},		
					"telephone":{
						"regex":"/(^[ ]?$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/",
						"alertText":"* 请输入有效的电话号码,如:010-29292929."},
					"faxNone":{	//空格和传真都可以通过，以便必非必填项的空值通过
						"regex":"/(^[ ]?$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/",
						"alertText":"* 请输入有效的传真号码,如:010-29292929."},
					"fax":{
						"regex":"/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/",
						"alertText":"* 请输入有效的传真号码,如:010-29292929."},
					"mobilephone":{
						"regex":"/(^0?[1][358][0-9]{9}$)/",
						"alertText":"* 请输入有效1的手机号码."},
					"phoneNone":{
						"regex":"/(^[ ]?$)|(^1[358][0-9]{9}$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/",
						"alertText":"* 请输入有效的手机号码或电话号码."},
					"phone":{
						"regex":"/(^1[358][0-9]{9}$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^(0[0-9]{2,3})?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/",
						"alertText":"* 请输入有效的手机号码或电话号码."},
					"emailNone":{    //空格和邮箱都可以通过，以便必非必填项的空值通过
						"regex":"/(^[ ]?$)|(^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\\.)+[a-zA-Z0-9]{2,4}$)/",
						"alertText":"* 请输入有效的邮件地址."},
					"email":{
						"regex":"/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\\.)+[a-zA-Z0-9]{2,4}$/",
						"alertText":"* 请输入有效的邮件地址."},
					"date":{
                         "regex":"/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/",
                         "alertText":"* 请输入有效的日期,如:2008-08-08."},
					"ip":{
                         "regex":"/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/",
                         "alertText":"* 请输入有效的IP."},
					"chinese":{
						"regex":"/^[\u4e00-\u9fa5]+$/",
						"alertText":"* 请输入中文."},
					"url":{
						"regex":/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/,
						"alertText":"* 请输入有效的网址."},
					"zipcodeNone":{
						"regex":/(^[ ]?$)|(^[0-9]\d{5}$)/,
						"alertText":"* 请输入有效的邮政编码."},
					"zipcode":{
						"regex":/^[0-9]\d{5}$/,
						"alertText":"* 请输入有效的邮政编码."},
					"card":{
						"regex":"/(^[0-9]{15}$)|(^[0-9]{18}$)|(^[0-9]{17}(x|X)$)/",
						"alertText":"* 请输入正确的身份证号."},
					"qq":{
						"regex":"/^[1-9]\d{4,9}$/",
						"alertText":"* 请输入有效的QQ号码."},
					"onlyNumber":{
						"regex":"/(^[ ]?$)|(^[1-9]{1}[0-9\,\.]*?$)/",
						"alertText":"* 请输入数字."},
					"oragnizationCode":{
							"regex":"/^([0-9]{8}\-)?([0-9]{1})$/",
							"alertText":"* 请输入有效组织机构代码证号."},
					"onlyLetter":{
						"regex":"/^[a-zA-Z]+$/",
						"alertText":"* 请输入英文字母."},
					"onlyLetter2":{
						"regex":"/^(^[ ]?$)|([a-zA-Z]+)$/",
						"alertText":"* 请输入英文字母(含空格)."},
					"noSpecialCaracters":{
						"regex":"/^[0-9a-zA-Z]+$/",
						"alertText":"* 请输入英文字母和数字."},	
					"ajaxUser":{
						"file":"validate.action",
						"alertTextOk":"* 帐号可以使用.",	
						"alertTextLoad":"* 检查中, 请稍后...",
						"alertText":"* 帐号不能使用."},	
					"ajaxName":{
						"file":"validateUser.php",
						"alertText":"* This name is already taken",
						"alertTextOk":"* This name is available",	
						"alertTextLoad":"* Loading, please wait"}					
					};	
		}
	};
})(jQuery);

$(document).ready(function() {	
	$.validationEngineLanguage.newLang();
});
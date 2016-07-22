function encode(json) {
	var tmps = [];
	for ( var key in json) {
		tmps.push(key + '=' + json[key]);
	}

	return tmps.join('&');
}  
function getFormParam(from_){
	var content = {};
	$("#"+from_+" :input").each(function() {
		if ($(this).attr("name") == undefined)
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	return content;
}
function getFormParamFormat(from_){
	var content = {};
	$("#"+from_+" :input").each(function() {
		if ($(this).attr("name") == undefined || $(this).val()=="")
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	return content;
}
function totalColumn(data,column){
	var total_ = 0;
	data.each(function(){
		total_ += parseFloat(eval("this."+column))>0?parseFloat(eval("this."+column)):0;
	})
	return formatNumber(total_,"0.00");
}
function getUrl_(){
	return _parserUrl(window.location.href);
}

function _parserUrl(tourl)//解析URL并转换为json形式
{
    if (!tourl) 
        return;
    var paramsArr = tourl.split('?')[1].split('&');
    var args = {}, argsStr = [], param, name, value;
    args['url'] = encodeURIComponent(tourl.split('?')[0]); //首先载入url,问号"?"前面的部分
    for (var i = 0; i < paramsArr.length; i++) {
        param = paramsArr[i].split('=');
        name = param[0], value = param[1];
        if (name == "") 
            name = "unkown";
        if (typeof args[name] == "undefined") { //参数尚不存在
            args[name] = value;
        }
        else 
            if (typeof args[name] == "string") { //参数已经存在则保存为数组
                args[name] = [args[name]];
                args[name].push(value);
            }
            else { //已经是数组的
                args[name].push(value);
            }
    }
    
    var showArg = function(x){ //转换不同数据的显示方式
        if (typeof(x) == "string" && !/\d+/.test(x)) 
            return "'" + x + "'"; //字符串
        if (x instanceof Array) 
            return "[" + x + "]"; //数组
        return x; //数字
    }
    args.toString = function(){//组装成json格式
        for (var i in args) 
            argsStr.push(i + ':' + showArg(args[i]));
        return '{' + argsStr.join(',') + '}';
    }
    return args; //以json格式返回获取的所有参数
}

function validForm(formId) {
	var flag = false;
	$("#"+formId+" :text").each(function(){
		if($(this).val()) {
			flag = true;
			return false;
		}
	});
	if(!flag) alert("请填写相关信息");
	return flag;
}

function formatNumber(number, pattern){
    var str = number.toString();
    var strInt;
    var strFloat;
    var formatInt;
    var formatFloat;
    if (/\./g.test(pattern)) {
        formatInt = pattern.split('.')[0];
        formatFloat = pattern.split('.')[1];
    }
    else {
        formatInt = pattern;
        formatFloat = null;
    }
    
    if (/\./g.test(str)) {
        if (formatFloat != null) {
            var tempFloat = Math.round(parseFloat('0.' + str.split('.')[1]) * Math.pow(10, formatFloat.length)) / Math.pow(10, formatFloat.length);
            strInt = (Math.floor(number) + Math.floor(tempFloat)).toString();
            strFloat = /\./g.test(tempFloat.toString()) ? tempFloat.toString().split('.')[1] : '0';
        }
        else {
            strInt = Math.round(number).toString();
            strFloat = '0';
        }
    }
    else {
        strInt = str;
        strFloat = '0';
    }
    if (formatInt != null) {
        var outputInt = '';
        var zero = formatInt.match(/0*$/)[0].length;
        var comma = null;
        if (/,/g.test(formatInt)) {
            comma = formatInt.match(/,[^,]*/)[0].length - 1;
        }
        var newReg = new RegExp('(\\d{' + comma + '})', 'g');
        
        if (strInt.length < zero) {
            outputInt = new Array(zero + 1).join('0') + strInt;
            outputInt = outputInt.substr(outputInt.length - zero, zero)
        }
        else {
            outputInt = strInt;
        }
        
        var outputInt = outputInt.substr(0, outputInt.length % comma) + outputInt.substring(outputInt.length % comma).replace(newReg, (comma != null ? ',' : '') + '$1')
        outputInt = outputInt.replace(/^,/, '');
        
        strInt = outputInt;
    }
    
    if (formatFloat != null) {
        var outputFloat = '';
        var zero = formatFloat.match(/^0*/)[0].length;
        
        if (strFloat.length < zero) {
            outputFloat = strFloat + new Array(zero + 1).join('0');
            //outputFloat        = outputFloat.substring(0,formatFloat.length);
            var outputFloat1 = outputFloat.substring(0, zero);
            var outputFloat2 = outputFloat.substring(zero, formatFloat.length);
            outputFloat = outputFloat1 + outputFloat2.replace(/0*$/, '');
        }
        else {
            outputFloat = strFloat.substring(0, formatFloat.length);
        }
        
        strFloat = outputFloat;
    }
    else {
        if (pattern != '' || (pattern == '' && strFloat == '0')) {
            strFloat = '';
        }
    }
    
    return strInt + (strFloat == '' ? '' : '.' + strFloat);
}
Date.prototype.format = function(format)  
{  
   var o = {  
     "M+" : this.getMonth()+1, //month  
     "d+" : this.getDate(),    //day  
     "h+" : this.getHours(),   //hour  
     "m+" : this.getMinutes(), //minute  
     "s+" : this.getSeconds(), //second  
     "q+" : Math.floor((this.getMonth()+3)/3), //quarter  
     "S" : this.getMilliseconds() //millisecond  
   }  
   if(/(y+)/.test(format)) format=format.replace(RegExp.$1,  
     (this.getFullYear()+"").substr(4 - RegExp.$1.length));  
   for(var k in o)if(new RegExp("("+ k +")").test(format))  
     format = format.replace(RegExp.$1,  
       RegExp.$1.length==1 ? o[k] :   
         ("00"+ o[k]).substr((""+ o[k]).length));  
   return format;  
} 

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

// 验证数字,保留N位小数 参数为String
function anyString(str, n) {
	str = str + "";
	str = str.replace(/\.{2,100}|[a-zA-Z]{1,100}|[\u4e00-\u9fa5]{1,100}|[\+\_\=\!\@\#\$\%\^\&\*\(\)\"\'\?\/\>\<\,\s]{1,100}/,
					"");
	if (n == null || n.length == 0 || n == undefined) {
		n = 2;
	}
	if (str.indexOf('.') != -1) {
		str = str.substring(0, str.indexOf('.') + n + 1);
	}
	return str;
}



//数字格式化
function fmoney(s, n) {  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
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

//精确计算浮点数 网上C的 乘法
function accMul(arg1,arg2)
{
var m=0,s1=arg1.toString(),s2=arg2.toString();
try{m+=s1.split(".")[1].length;}catch(e){}
try{m+=s2.split(".")[1].length;}catch(e){}
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
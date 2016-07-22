function validate(){  
strYYYY = document.pageForm.YYYY.outerHTML;  
strMM = document.pageForm.MM.outerHTML;  
strDD = document.pageForm.DD.outerHTML;  
MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];  
  
//先给年下拉框赋内容  
var y = new Date().getFullYear();  
var str = strYYYY.substring(0, strYYYY.length - 9);  
for (var i = (y-30); i < (y+30); i++) //以今年为准，前30年，后30年  
{  
str += "<option value='" + i + "'> " + i + "</option>\r\n";  
}  
document.pageForm.YYYY.outerHTML = str +"</select>";  
  
//赋月份的下拉框  
var str = strMM.substring(0, strMM.length - 9);  
for (var i = 1; i < 13; i++)  
{  
str += "<option value='" + i + "'> " + i + "</option>\r\n";  
}  
document.pageForm.MM.outerHTML = str +"</select>";  
  
document.pageForm.YYYY.value = y;  
document.pageForm.MM.value = new Date().getMonth() + 1;  
var n = MonHead[new Date().getMonth()];  
if (new Date().getMonth() ==1 && IsPinYear(YYYYvalue)) n++;  
writeDay(n); //赋日期下拉框  
document.pageForm.DD.value = new Date().getDate();  
} 
 
function YYYYMM(str) //年发生变化时日期发生变化(主要是判断闰平年)  
{  

var MMvalue = document.pageForm.MM.options[document.pageForm.MM.selectedIndex].value;  
if (MMvalue == ""){DD.outerHTML = strDD; return;}  
var n = MonHead[MMvalue - 1];  
if (MMvalue ==2 && IsPinYear(str)) n++;  
writeDay(n);
} 
 
function MMDD(str) //月发生变化时日期联动  
{  
var YYYYvalue = document.pageForm.YYYY.options[document.pageForm.YYYY.selectedIndex].value;  
if (str == ""){DD.outerHTML = strDD; return;}  
var n = MonHead[str - 1];  
if (str ==2 && IsPinYear(YYYYvalue)) n++;  
writeDay(n);  
}  

function writeDay(n) //据条件写日期的下拉框  
{  
var s = strDD.substring(0, strDD.length - 9);  
for (var i=1; i<(n+1); i++)  
s += "<option value='" + i + "'> " + i + "</option>\r\n";  
document.pageForm.DD.outerHTML = s +"</select>";  
}  

function IsPinYear(year)//判断是否闰平年  
{ return(0 == year%4 && (year%100 !=0 || year%400 == 0))} 


/**
 * 增加标签页
 * @param name
 * @param url
 * @return
 */
function addTab(name,url){
	//alert("addTab");
	//alert(url);
	url=url.replace("undefined","");
	//alert(url);
	if(url.lastIndexOf("?")==-1){
		url=url+"?_datetime="+new Date().getTime();
	}else{
		url=url+"&_datetime="+new Date().getTime();
	}
	if($('#tabBox').tabs('exists',name)){
		//alert(1);
		$('#tabBox').tabs('select',name);
		$('#tabBox').tabs('update',{
			tab:$("#tabBox").tabs("getSelected"),
			options:{
				content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
			}
		});
	}else{
		//alert(2);
		$('#tabBox').tabs('add',{
			title: name,
			content: '<iframe src="'+url+'" width="100%" height="100%" style="padding:0px;margin-bottom:-5px;" frameborder="0" border="0"></iframe>',
			closable: true,
			tools:[{
		        iconCls:'icon-mini-refresh',
		        handler:function(){
					$('#tabBox').tabs('update',{
						tab:$("#tabBox").tabs("getSelected"),
						options:{
							content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
						}
					});
		        }
		    }]
		});
	}
} 
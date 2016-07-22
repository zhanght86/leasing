function clear_(){
	 $('#queryForm').form('clear');
}
function se(){
	var content = {};
	$("#queryForm :input").each(function() {
		if ($(this).attr("name") == undefined)
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	 ;
	$('#pageTable').datagrid('load', content);
}

function compure(value,rowData){
	var updateHandle = "<a href='javascript:void(0)' onclick=editInvoiceApp('"+rowData.ID+"')>更新</a>";
	var queryHandle = "<a href='javascript:void(0)' onclick=showInvoiceApp('"+rowData.ID+"')>查看</a>　" ;
	
	return queryHandle+updateHandle;
}

function isBeforeinvoiceCompure(value,rowData){
	if(value==1) return "是" ;
	else return "否" ;
}

function editInvoiceApp(id){
	top.addTab("更新开票协议",_basePath+"/delivery/InvoiceApplication!editInvoiceApplicationPage.action?ID="+id);
}

function showInvoiceApp(id){
	top.addTab("查看开票协议",_basePath+"/delivery/InvoiceApplication!showInvoiceApplicationPage.action?ID="+id);
}


function isBeforeInvoiceHandle(obj){
	//  ;
	if(obj.checked==true){
		$("#invoicePatternId").attr("disabled",true) ;
	}else{
		$("#invoicePatternId").attr("disabled",false) ;
	}
}

function putInVoiceObjVal(){
	var radionVal = $("input[name=ZHENGJIFAPIAO]:checked").val();
	
	// 乙方
	var PARTYBNAME=$("#PARTBNAME_ID").val() ;
	var PARTBPHONE=$("#PARTBPHONE_ID").val() ;
	var PARTBBANK=$("#PARTBBANK_ID").val() ;
	var PARTBNUMBER=$("#PARTBNUMBER_ID").val() ;
	var PARTBDUTY=$("#PARTBDUTY_ID").val() ;
	var PARTBCODE_OR_CARD=$("#PARTBCODE_OR_CARD_ID").val();
	var PARTB_TAX_QUALIFICATION = $("#PARTB_TAX_QUALIFICATION_ID option:selected").val();
	var PARTBADRS = $("#PARTBADRS_ID").val();
	
	
	// 丙方
	var PARTYCNAME=$("#PARTCNAME_ID").val() ;
	var PARTCPHONE=$("#PARTCPHONE_ID").val() ;
	var PARTCBANK=$("#PARTCBANK_ID").val() ;
	var PARTCNUMBER=$("#PARTCNUMBER_ID").val() ;
	var PARTCDUTY=$("#PARTCDUTY_ID").val() ;
	var PARTCCODE_OR_CARD=$("#PARTCCODE_OR_CARD_ID").val();
	var PARTC_TAX_QUALIFICATION = $("#PARTC_TAX_QUALIFICATION_ID option:selected").val();
	var PARTCADRS = $("#PARTCADRS_ID").val();
	
	
	
	if(radionVal=="客户"){
		$("#W_NAME_ID").val(PARTYBNAME) ;
		$("#W_PHONE_ID").val(PARTBPHONE) ;
		$("#W_BANK_ID").val(PARTBBANK) ;
		$("#W_BANK_NUMBER_ID").val(PARTBNUMBER) ;
		$("#W_CODE_OR_CARD_ID").val(PARTBCODE_OR_CARD) ;
		$("#W_ADDR_ID").val(PARTBADRS) ;
		$("#W_DUTY_ID").val(PARTBDUTY) ;
		$("#W_TAX_QUALIFICATION_ID").val(PARTB_TAX_QUALIFICATION) ;
	}else{
		$("#W_NAME_ID").val(PARTYCNAME) ;
		$("#W_PHONE_ID").val(PARTCPHONE) ;
		$("#W_BANK_ID").val(PARTCBANK) ;
		$("#W_BANK_NUMBER_ID").val(PARTCNUMBER) ;
		$("#W_CODE_OR_CARD_ID").val(PARTCCODE_OR_CARD) ;
		$("#W_ADDR_ID").val(PARTCADRS) ;
		$("#W_DUTY_ID").val(PARTCDUTY) ;
		$("#W_TAX_QUALIFICATION_ID").val(PARTC_TAX_QUALIFICATION) ;
	}
	
	
}

function saveInvoiceApplication(){
	if(!$("#invoiceApplicationId").form("validate")) return;
	putInVoiceObjVal() ;
	var methodType=$("#method_type").val() ;
	var url=_basePath+"/delivery/InvoiceApplication!addInvoiceApplication.action"; 
	if(methodType=="edit") 
		url=_basePath+"/delivery/InvoiceApplication!editInvoiceApplication.action"; 
	var formData=$("form").serialize() ;
	$.ajax({
         url: url,   // 提交的页面
         data: formData, // 从表单中获取数据
         type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
         dataType :"json" ,
         processData:true,
         success: function(data) {
             if(data.flag==true){
            	 alert("保存开票协议成功!") ;
            	 $("#saveButtonId").hide() ;
             }else{
            	 alert("保存开票协议失败!") ;
             }
         }
	 });

}

// 日期监听空间
function dateListening(ori_id,dest_id){
	var prefix_id="#" ;
	var oriId = prefix_id + ori_id ;
	var destId = prefix_id + dest_id ;
	$(oriId).datebox({  
        required:true,  
        onSelect: function(date){  
			$(destId).val(date.format("yyyy-MM-dd")) ;
			$(destId).attr("changeFlag","true") ;
        }  
    }); 
}

/**
 * 时间对象的格式化
 */  
 Date.prototype.format = function(format)  
 {  
	 /*
		 * format="yyyy-MM-dd hh:mm:ss";
		 */  
	 var o = {  
	 "M+" : this.getMonth() + 1,  
	 "d+" : this.getDate(),  
	 "h+" : this.getHours(),  
	 "m+" : this.getMinutes(),  
	 "s+" : this.getSeconds(),  
	 "q+" : Math.floor((this.getMonth() + 3) / 3),  
	 "S" : this.getMilliseconds()  
	 } ; 
   
	 if (/(y+)/.test(format))  
	 {  
	 format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
	 - RegExp.$1.length));  
	 }  
   
	 for (var k in o)  
		 {  
		 if (new RegExp("(" + k + ")").test(format))  
		 {  
		 format = format.replace(RegExp.$1, RegExp.$1.length == 1  
		 ? o[k]  
		 : ("00" + o[k]).substr(("" + o[k]).length));  
		 }  
	 }  
 return format;  
 }  ;


// 只能输入数字
function zero(ele){
	ele.value=ele.value.replace(/\D{1,100}/,"");
}


function checkForm(){
	var inputs = $("form").find("input");
	for(var i=0;i<inputs.length;i++){
		var valid = $(inputs[i]).attr("valid") ;
		var value=$(inputs[i]).val() ;
		if(valid=="phone") {
			if(!isTelephone(value)) return false ;
		} 
		if(valid=="banknum"){
			if(!isBankNum(value)) return false ;
		} ;
	}
	return true;
} ;

function checkRequired(){
	var fonts = $("form").find("font");
	for(var i=0;i<fonts.length;i++){
		var textValue=$.trim($(fonts[i]).text()) ;
		if(textValue=='*'){
			var parentNodeValue = $(fonts[i]).parent("td").find("input").val() ;
			if(parentNodeValue==''||parentNodeValue==null || typeof(parentNodeValue)==undefined){
				
				$(fonts[i]).text("该项为必填项!") ; 
				isValid = false ;
			}
		}
	}
}

// 电话号码验证,Dom对象
function isTelephone(val)// 正则判断
{ 
	var pattern=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}(13|15|18|14)[0-9]{9}$)/; 
	if(pattern.test(val)) 
	{ 
		return true; 
	} 
	else 
	{ 
		//$.messager.alert("操作提示","联系方式输入错误，请重新输入！");
		return false; 
	} 
};

$.extend($.fn.validatebox.defaults.rules, {
    isBankNum: {
        validator: function(value,param){
        	var flag = isBankNum(value) ;
        	var lengthFlag =(value.length >= param[0]) ;
        	if(flag==false || lengthFlag==false){
        		isValid = false ;
        		return false ;
        	} 
        	
            return true;
        },
        message: '请输入正确的银行卡号!' 
    },
     isTelephone: {
        validator: function(value,param){
        	var flag = isTelephone(value);	
        	var lengthFlag =(value.length >= param[0]) ;
        	if(flag==false || lengthFlag==false ) {
        		isValid = false ;	
        		return false ;
        	} 
            return true;
        },
        message: '请重新输入正确的电话号码!' 
    }
});

// Luhm校验规则：16位银行卡号（19位通用）:

// 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
// 3.将加法和加上校验位能被 10 整除。

// 方法步骤很清晰，易理解，需要在页面引用Jquery.js


// bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件
function isBankNum(bankno){
	//
  var lastNum=bankno.substr(bankno.length-1,1);// 取出最后一位（与luhm进行比较）

  var first15Num=bankno.substr(0,bankno.length-1);// 前15或18位
  var newArr=new Array();
  for(var i=first15Num.length-1;i>-1;i--){    // 前15或18位倒序存进数组
      newArr.push(first15Num.substr(i,1));
  }
  var arrJiShu=new Array();  // 奇数位*2的积 <9
  var arrJiShu2=new Array(); // 奇数位*2的积 >9
  
  var arrOuShu=new Array();  // 偶数位数组
  for(var j=0;j<newArr.length;j++){
      if((j+1)%2==1){// 奇数位
          if(parseInt(newArr[j])*2<9)
          arrJiShu.push(parseInt(newArr[j])*2);
          else
          arrJiShu2.push(parseInt(newArr[j])*2);
      }
      else // 偶数位
      arrOuShu.push(newArr[j]);
  }
  
  var jishu_child1=new Array();// 奇数位*2 >9 的分割之后的数组个位数
  var jishu_child2=new Array();// 奇数位*2 >9 的分割之后的数组十位数
  for(var h=0;h<arrJiShu2.length;h++){
      jishu_child1.push(parseInt(arrJiShu2[h])%10);
      jishu_child2.push(parseInt(arrJiShu2[h])/10);
  }        
  
  var sumJiShu=0; // 奇数位*2 < 9 的数组之和
  var sumOuShu=0; // 偶数位数组之和
  var sumJiShuChild1=0; // 奇数位*2 >9 的分割之后的数组个位数之和
  var sumJiShuChild2=0; // 奇数位*2 >9 的分割之后的数组十位数之和
  var sumTotal=0;
  for(var m=0;m<arrJiShu.length;m++){
      sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
  }
  
  for(var n=0;n<arrOuShu.length;n++){
      sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
  }
  
  for(var p=0;p<jishu_child1.length;p++){
      sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
      sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
  }      
  // 计算总和
  sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
  
  // 计算Luhm值
  var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
  var luhm= 10-k;
  
  if(lastNum==luhm){
	  return true;
  }
  else{
	  //$.messager.alert("操作提示","请填写正确的银行帐号信息!");
  return false;
  }        
}

function saveOrDelPaymentTermFlag(){
	
}


function closeTab(tabName){
	top.closeTab(tabName);
}



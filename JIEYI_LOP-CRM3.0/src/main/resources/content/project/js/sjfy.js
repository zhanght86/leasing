/**
 * 保存方法
 */
function saveSjfy(){
	 
	var jsonStr = {};
	for (var i = 1; i <= 10; i++) {

		var money = $("#money" + i).val();
		var cost = $("#cost" + i).val();
		jsonStr['money' + i] = money;
		jsonStr['cost' + i] = cost;
	}
	
	var PROJECT_ID = $("#PROJECT_ID").val();
	var FIRST_PAYALL = $("#FIRST_PAYALL").val();
	
	jsonStr['PROJECT_ID'] = PROJECT_ID;
	jsonStr['FIRST_PAYALL'] = FIRST_PAYALL;
	
	jQuery.ajax({
		type:'post',
		url:_basePath + "/customers/Customers!sjfySave1.action",
		data: jsonStr,
		dataType:"json",
		success:function(json){
 			if(json.flag){
 				jQuery.messager.alert("提示","保存成功！");
 			}else{
 				jQuery.messager.alert("提示","保存失败！");
 			}
 		}
	});
}
 
 
 /**
  * 初始化
  */
 function initSifj(){
	 
	 var PROJECT_ID = $("#PROJECT_ID").val();
	 var SCHEME_ROW_NUM = $("#SCHEME_ROW_NUM").val();
	 var PLATFORM_TYPE = $("#PLATFORM_TYPE").val();
	 var SCHEME_ID = $("#SCHEME_ID").val();
	 
	 jQuery.ajax({
		type:'post',
		url:_basePath + "/customers/Customers!initSify.action",
		data: {
			"JD":"leeds",
			"PROJECT_ID" : PROJECT_ID,
			"SCHEME_ROW_NUM" : SCHEME_ROW_NUM,
			"PLATFORM_TYPE" : PLATFORM_TYPE, 
			"SCHEME_ID" : SCHEME_ID
		},
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#money1").val(json.data.money1==''?0:json.data.money1);
				$("#money2").val(json.data.money2==''?0:json.data.money2);
				$("#money3").val(json.data.money3==''?0:json.data.money3);
				$("#money4").val(json.data.money4==''?0:json.data.money4);
				$("#money5").val(json.data.money5==''?0:json.data.money5);
				$("#money6").val(json.data.money6==''?0:json.data.money6);
				$("#money7").val(json.data.money7==''?0:json.data.money7);
				$("#money8").val(json.data.money8==''?0:json.data.money8);
				$("#money9").val(json.data.money9==''?0:json.data.money9);
				$("#money10").val(json.data.money10==''?0:json.data.money10);
				$("#FIRST_PAYALL").val(json.data.FIRST_PAYALL==''?0:json.data.FIRST_PAYALL);
				jQuery.messager.alert("提示","初始化成功!");
			}else{
				jQuery.messager.alert("提示","初始化失败!");
			}
 		}
	});
 }
 
 /**
  * 重置
  */
 function resetSifj(){
	 var money1 =$("#money1").val();
	 var money2 =$("#money2").val();
	 var money3 =$("#money3").val();
	 var money4 =$("#money4").val();
	 var money5 =$("#money5").val();
	 var money6 =$("#money6").val();
	 var money7 =$("#money7").val();
	 var money8 =$("#money8").val();
	 var money9 =$("#money9").val();
	 var money10 =$("#money10").val();
	 var PROJECT_ID=jQuery("#PROJECT_ID").val();
	 if(money1!=null)$("#money1").val(null);
	 if(money2!=null)$("#money2").val(null);
	 if(money3!=null)$("#money3").val(null);
	 if(money4!=null)$("#money4").val(null);
	 if(money5!=null)$("#money5").val(null);
	 if(money6!=null)$("#money6").val(null);
	 if(money7!=null)$("#money7").val(null);
	 if(money8!=null)$("#money8").val(null);
	 if(money9!=null)$("#money9").val(null);
	 if(money10!=null)$("#money10").val(null);
 }
 
 
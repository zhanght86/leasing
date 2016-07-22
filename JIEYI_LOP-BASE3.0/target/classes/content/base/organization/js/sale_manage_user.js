$(document).ready(function(){
	window.opUser = function(ele){
		var t = $(ele);
		jQuery.ajax({
		   url: "Manage!doUpdateSaleUser.action?TYPE="+t.attr("stype")+"&ORG_ID="+$("input[name='userOrgId']").val()+"&USER_ID="+t.attr("sid"),
		   dataType:"json",
		   success:function(flag){
				if(flag.msg=="removesuccess"){
					window.location.reload();
				}else if(flag.msg=="addsuccess"){
					//添加成功的显示
				}else{
					jQuery.messager.alert("提示","移出失败！");  
				}
			}
		});
	}
  
	
	$(".btnResetPsw").click(function(){
	    jQuery.ajax({
			url : _basePath + "/base/user/Manage!doUpdatePwdByUserId.action?EMPLOYEE_ID="+$(this).attr("sid"),
			dataType:"json",
			success : function(json){
		    	jQuery.messager.alert("提示",json.msg); 
			}
		});
	});
	
	// 验证手机号码
	$("#EMPLOYEE_MOBILE_TEL").blur(function(){  
        var telphone = $("#EMPLOYEE_MOBILE_TEL").val();  
        if(telRuleCheck(telphone)){  
            // 是正确手机号码
        }else{  
        	jQuery.messager.alert("提示","手机号码格式不正确！"); 
        };  
    });
	
	// 验证身份证号码
	$("#EMPLOYEE_ID_CARD").blur(function(){  
        var idCard = $("#EMPLOYEE_ID_CARD").val();  
        if(isCardNo(idCard)){  
            // 是正确身份证号码
        }else{  
        	jQuery.messager.alert("提示","身份证号码输入不合法！");  
        };  
    });

});

		
function toShowUser(userId){
	top.addTab("人员查看",_basePath + "/base/user/Manage!showSale1.action?sale=show&EMPLOYEE_ID="+userId);
}

function toUpdateUser(userId){
	top.addTab("人员修改",_basePath + "/base/user/Manage!showSale.action?sale=update&EMPLOYEE_ID="+userId);
}

/** 异步提交表单，修改用户信息(sale_update.vm) **/
function updateUser(){
	
	var idCard = $("#EMPLOYEE_ID_CARD").val();  
    if(null != idCard && '' != idCard){
	    if(isCardNo(idCard)){  
	        // 是正确身份证号码
	    }else{  
	    	jQuery.messager.alert("提示","身份证号码输入不合法！");  
	    	return false;
	    }; 
    }
	
	var telphone = $("#EMPLOYEE_MOBILE_TEL").val();
	if(null != telphone && '' != telphone){
	    if(telRuleCheck(telphone)){  
	        // 是正确手机号码
	    }else{  
	    	jQuery.messager.alert("提示","手机号码格式不正确！"); 
	    	return false;
	    };  
	}
	
	$("#userInfo").ajaxSubmit({
	    type: "post",
	    dataType:"json",
		success : function(res){
		    if(res.flag == true){
			    jQuery.messager.alert("提示",res.msg);
			}else{
			    jQuery.messager.alert("提示",res.msg);
			}
		}
	});
	return false;
}

/*** 
 * check mobile phone:(1)must be digit;(2)must be 11 
 * @param string 
 * @returns {boolean} 
 */  
telRuleCheck = function (string) {  
    var pattern = /^1[34578]\d{9}$/;  
    if (pattern.test(string)) {  
        return true;  
    }  
    //console.log('check mobile phone ' + string + ' failed.');  
    return false;  
};  

/*** 
 * check card no
 * @param string 
 * @returns {boolean} 
 */  
isCardNo = function (card) {  
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
   if(reg.test(card)) {  
       return true;  
   }
   //console.log('check card number ' + card + ' failed.');  
   return false;
} 

/**
 * 验证工号和姓名
 * @returns {Boolean}
 */
function check(){
	var validate = true;
	if(jQuery(".IsUserCode").val()!=""){
		if(jQuery(".IsUserCode").val()=="0"){
			validate=false;
			alert("工号已经存在！");
		}
	}
	$(".noEmpty").each(function(){
		if(jQuery.trim($(this).val()).length==0){
			$(this).animate({ backgroundColor: "red" }, 500);
			validate=false;
			alert("工号和姓名不能为空！");
		}else{
			$(this).animate({ backgroundColor: "#fff" }, 500);
		}
	});
    
    var idCard = $("#EMPLOYEE_ID_CARD").val();  
    if(null != idCard && '' != idCard){
	    if(isCardNo(idCard)){  
	        // 是正确身份证号码
	    }else{  
	    	validate=false;
	    	jQuery.messager.alert("提示","身份证号码输入不合法！");  
	    }; 
    }
	
	var telphone = $("#EMPLOYEE_MOBILE_TEL").val();
	if(null != telphone && '' != telphone){
	    if(telRuleCheck(telphone)){  
	        // 是正确手机号码
	    }else{  
	    	validate=false;
	    	jQuery.messager.alert("提示","手机号码格式不正确！"); 
	    };  
	}
	
	return validate;
}
	
/**
 * 工号重复验证确认
 * @param lnk
 */
function findCodeBlur(lnk){
	var content = jQuery(lnk).val();
	jQuery.ajax({
		url:_basePath + "/base/user/Manage!toValidateUserCodeSale.action",
		data:"data="+encodeURI(content),
		type:"post",
		dataType:"json",
		success:function(res){
			if(res.flag==false){
				jQuery(".checkCode").empty();
				jQuery(".IsUserCode").val("0");
				alert("工号已经存在！");
			}else{
				jQuery(".checkCode").empty();
				jQuery(".IsUserCode").val("1");
			}	
		}
	})
}

/**
 * 添加用户
 * @param b
 */
function SaveUser(b){
	
	if(check()){
	
	    var EMPLOYEE_CODE=$("input[name='EMPLOYEE_CODE']").val();
		var EMPLOYEE_NAME=$("input[name='EMPLOYEE_NAME']").val();
		var EMPLOYEE_MOBILE_TEL=$("input[name='EMPLOYEE_MOBILE_TEL']").val();
		var EMPLOYEE_ID_CARD=$("input[name='EMPLOYEE_ID_CARD']").val();
		var ORG_ID=$("input[name='ORG_ID']").val();
	
		jQuery.ajaxFileUpload({  
            fileElementId: 'PHOTO_PATH',  
            url: 'Manage!doAddSale.action?EMPLOYEE_CODE='+encodeURI(EMPLOYEE_CODE)+'&EMPLOYEE_NAME='+encodeURI(EMPLOYEE_NAME)+'&EMPLOYEE_MOBILE_TEL='+encodeURI(EMPLOYEE_MOBILE_TEL)+'&EMPLOYEE_ID_CARD='+encodeURI(EMPLOYEE_ID_CARD)+'&ORG_ID='+encodeURI(ORG_ID),  
            dataType: 'json',   
            success: function (data, textStatus) { 
			    var json = jQuery.parseJSON(data);
				alert(json.msg);
			    if(json.flag){
				  top.removeTab('添加人员');
				}
			   
            },  
            error: function (XMLHttpRequest, textStatus, errorThrown) {  
               alert("保存失败");
            }
           
       });  
	}
}

/**
 * 清空
 */
function clearText(){
	$("#userInfo input[name='EMPLOYEE_CODE']").val("");
	$("#userInfo input[name='EMPLOYEE_NAME']").val("");
	$("#userInfo input[name='EMPLOYEE_ID_CARD']").val("");
	$("#userInfo input[name='EMPLOYEE_MOBILE_TEL']").val("");
}

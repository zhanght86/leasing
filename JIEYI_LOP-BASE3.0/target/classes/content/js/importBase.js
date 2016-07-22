//根据条件取数据，需jQuery json支持
function getFromData(tag) {
	return JSON.stringify(getQueryParam(tag));
}

function getQueryParam(tag) {
	var data = {};
	$(tag + ' [name]').each(
		function() {
			if ($(this).is(":checkbox,:radio")) {
				if ($(this).attr("checked")) {
					data[$(this).attr("name")] = $(this).val();
				}
			} else {
				if ($(this).is("select")) {
					data[$(this).attr("name")] = $(this).find(":selected").val();
				} else {
					var value = $.trim($(this).val());
					if(value != ""){
						data[$(this).attr("name")] = value;
					}
				}
			}
		});
	return data;
}

function fixWidth(percent) {
	return (document.body.clientWidth - 40) * percent; // 这里你可以自己做调整
}

function fixHeight(percent) {
	return (document.body.clientHeight - 40) * percent; // 这里你可以自己做调整
}

/**
 * 子页面创建父页面的tab标签页
 * 
 * @param name
 *            标签页名
 * @param url
 *            调用的action
 * @return
 * 
 * @author King 2013-8-29
 */
function addParentTabs(name, url) {
	if (top.$('#tabBox').tabs('exists', name)) {
		top.$('#tabBox').tabs('select', name);
		top.$('#tabBox')
				.tabs(
						'update',
						{
							tab : top.$('#tabBox').tabs("getSelected"),
							options : {
								content : '<iframe src="' + url + '" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
							}
						});
	} else {
		top.$('#tabBox')
				.tabs(
						'add',
						{
							title : name,
							content : '<iframe src="' + url + '" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>',
							closable : true
						});
	}
}

//只能输入数字和小数点(全部清空)
function doubleValue(ele)
{
	var value=ele.value;
	if(!(/[^\d{1,100}\.]/.test(value)))
	{
		ele.value=value.replace(/[^\d{1,100}\.]/,"");
	}
	else
	{
		ele.value="";
	}
		
}

function doubleMoneyFixed(ele){
	var value=ele.value;
	if(value ==''){
	}else{
		if(!(/[^\d{1,100}\.]/.test(value))){
			var valueMoney=parseFloat(ele.value).toFixed(2);
			ele.value=valueMoney;
		}else{
			ele.value="";
		}
	}
	
	
	
}

//只能输入数字
function zero(ele){
	ele.value=ele.value.replace(/\D{1,100}/,"");
}


//只能输入数字和空格
function zeroKG(ele){
	var value=ele.value;
	if(!(/[^\d{1,100}\ ]/.test(value)))
	{
		ele.value=value.replace(/[^\d{1,100}\ ]/,"");
	}
	else
	{
		ele.value="";
	}
}


//手机号码或者电话号码验证
function phoneValue(ele){
	var value=ele.value;
	if(!(/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(value)))
	{
		ele.value="";
	}
}

//*****************************************************************************************************************************************************************************************************
//身份证认证---fuyulong---2014-09-02-------js调用方法
function getIdCardStateLB(NAME,ID_CARD_NO){
    jQuery.ajax({
		async: false,//同步异步
        url: _basePath + "/checkIdCard/CheckIdCard!getIdCardState.action",
        data: "NAME=" + NAME + "&ID_CARD_NO=" + ID_CARD_NO + "&_time=" + new Date(),
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				ID_CARD_NO = json.data;
            }
        }
    });
	return ID_CARD_NO;
}

//身份证认证---fuyulong---2014-09-04------添加脚本标签方法
function getIdCardState(NAME,ID_CARD_NO){
    jQuery.ajax({
		async: false,
        url: _basePath + "/checkIdCard/CheckIdCard!getIdCardState.action",
        data: "NAME=" + NAME + "&ID_CARD_NO=" + ID_CARD_NO + "&_time=" + new Date(),
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
//				ID_CARD_NO = json.data;
				$("#"+ID_CARD_NO).parent().append(json.data);
            }
        }
    });
//	return ID_CARD_NO;
}

//身份证认证---fuyulong---2014-09-02
function checkIdCard(e,NAME,ID_CARD_NO){
	$.messager.confirm('提 示', '确定对此【'+ID_CARD_NO+'】身份证进行认证吗?', function(r){
		if (r){
			jQuery.ajax({
		        url: _basePath + "/checkIdCard/CheckIdCard!doCheck.action",
		        data: "NAME=" + NAME + "&ID_CARD_NO=" + ID_CARD_NO + "&_time=" + new Date(),
		        type: "post",
		        dataType: "json",
		        success: function(json){
		            if (json.flag) {
						$(e).attr("src", _basePath + "/img/Verify_go.png")
						$(e).attr("title","已认证")
						$(e).removeAttr("onclick");
						alert("认证成功！");
		            }else{
						$(e).attr("src", _basePath + "/img/Verify_NO.png")
						$(e).attr("title","认证未通过")
						$(e).removeAttr("onclick");
						alert("认证未通过！");
					}
		        }, 
				error: function(e) { 
					alert("认证接口出现问题！请联系管理员！");
				} 
		    });
		}
	});
}

//手动发送短信按钮---fuyulong---2014-09-04------添加脚本标签方法
function getManualSendMessage(NAME,PHONE){
	$("#"+PHONE).parent().append("<a href=\"javascript:void(0);\" ><img onclick=\"manualSendMessage('"+NAME+"','"+PHONE+"')\" title=\"发送短信\" src=\"" + _basePath + "/img/email.png\"/></a>");
}


//手动发送短信页面
function manualSendMessage(NAME,PHONE){
	var html;
	jQuery.ajax({
		async: false,
        url: _basePath + "/sms/Sms!manualSendMessage.action",
        data: "NAME=" + NAME + "&PHONE=" + PHONE + "&_time=" + new Date(),
        type: "post",
        dataType: "html",
        success: function(newHtml){
			html = newHtml;
        }, 
		error: function(e) { 
			alert("出现错误！请联系管理员！");
		} 
    });
	$("body").append(html);
    $('#manualSendMessageDiv').dialog({
	    title: '发送短信',
	    width: 410,
	    height: 320,
	    closed: false,
	    cache: false,
	    modal: true,
		resizable:false,
		buttons: [{
			id:"btnbc",
            text:'发 送',
            iconCls:'icon-save',
            handler:function(){
			  	$('#btnbc').linkbutton('disable');
				var CONTENT = $("#CONTENT").val();
				if(!CONTENT){
					alert("短信内容不能为空值！");
					$('#btnbc').linkbutton('enable');
					return;
				}
				if(!PHONE){
					alert("电话不能为空值！");
					$('#btnbc').linkbutton('enable');
					return;
				}
				jQuery.ajax({
			        url: _basePath + "/sms/Sms!doManualSendMessage.action",
			        data: "NAME=" + NAME + "&PHONE=" + PHONE + "&CONTENT=" + CONTENT + "&_time=" + new Date(),
			        type: "post",
			        dataType: "json",
			        success: function(json){
						if(json.data.FLAG){
							$("#manualSendMessageDiv").dialog('close');
						}else{
							$('#btnbc').linkbutton('enable');
						}
						alert(json.data.STATE);
			        }, 
					error: function(e) { 
						alert("出现错误！请联系管理员！");
					}
			    });
            }
        },{
            text:'取 消',
			iconCls:'icon-cancel',
            handler:function(){
               $("#manualSendMessageDiv").dialog('close');
            }
        }],
		onClose:function(){
			$("#manualSendMessageDiv").remove();
	    }
    });
}


//不能输入数字
function noNumber(ele){
	ele.value=ele.value.replace(/[0-9]*$/,"");
}

//提交校验
function isNotNullSubmit(){
	var flag = true;
	$(".warm").each(function(){
		if($(this).val() == null || $(this).val() ==''){
			var title = $(this).attr('title');
			alert('【'+title+'】不能为空！');
			$(this).focus();
			flag = false;
			return flag;
		}
	});
	
	$(".warmlegalDate").each(function() {
		if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
			var title = $(this).attr('title');
			alert('【'+title+'】不能为空！');
			$(this).focus();
			flag = false;
			return flag;
		}
		
	});
	return flag;
}

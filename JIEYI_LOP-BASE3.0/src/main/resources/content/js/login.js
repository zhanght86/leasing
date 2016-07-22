var logining = false;
$(function() {
	$("#PASSWORD").keydown(function(e) {
		if (e.keyCode == 13) {
			signIn();
		}
	});
	$("#submit").click(signIn);
	if ($.cookie("_remember_")) {
		$("#USERCODE").val($.cookie("_remember_"));
	} else {
		$("#USERCODE").val("");
	}
});
function CalcuMD5(pwd) {
	var totleStr = "CHINALICHAOLOVEANNI20131117BEIJINGPINGQIANGRUANJIAN";
    pwd = pwd.toUpperCase();
    pwd = pwd.replace(/0/g,'~').replace(/1/g, '$').replace(/2/g, '!').replace(/3/g,'@').replace(/4/g,':').
              replace(/5/g,']').replace(/6/g,'[').replace(/7/g,'{').replace(/8/g,'}').replace(/9/g,'`');
    totleStr = totleStr.substring(0, totleStr.length - pwd.length);
    pwd = totleStr + pwd;
    pwd = $.md5(pwd);
	return pwd;
}
function signIn() {
	if (logining)
		return;
	logining = true;
	$("#errorLabel").hide();

	$("#submit").addClass("logining");
	var code = $.trim($("#USERCODE").val());
	var password = $.trim($("#PASSWORD").val());
	if (!(code && password))
		return;
	$.cookie('_remember_', code, {
		expires : 7
	});

//	if ($("#remember").attr("checked"))
//	else
//		$.cookie('_rememberUser_', '', {
//			expires : -1
//		});
	$.ajax({
		url : "Login.action",
		type : "post",
		dataType : "json",
		data : "USERCODE=" + code + "&PASSWORD=" + CalcuMD5(password)
				+ "&REQUEST=" + $.trim($("#request").val()),
		success : function(e) {
			if (e && e.flag) {
				if (e.msg == "token"){
					alert("请输入密钥");
					$("#tokenDialog").dialog("open");
				}else{
					location = "./";
				}
			} else {
				logining = false;
				alert(e.msg);
				$("#submit").removeClass("logining");
				$("#PASSWORD").val("");
			}
		},
		error : function(request, exception) {
			alert(exception);
			logining = false;
			$("#submit").removeClass("logining");
		}
	});
}

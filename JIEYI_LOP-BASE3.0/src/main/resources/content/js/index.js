function addTab(name,url){
	if(url.lastIndexOf("?")==-1){
		url=url+"?_datetime="+new Date().getTime();
	}else{
		url=url+"&_datetime="+new Date().getTime();
	}
	if($('#tabBox').tabs('exists',name)){
		$('#tabBox').tabs('select',name);
		$('#tabBox').tabs('update',{
			tab:$("#tabBox").tabs("getSelected"),
			options:{
				content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
			}
		});
	}else{
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

function removeTab(){
	var tab = $('#tabBox').tabs('getSelected');
	if (tab){
		var index = $('#tabBox').tabs('getTabIndex', tab);
		$('#tabBox').tabs('close', index);
	}
}

function closeTab(name){
	if($('#tabBox').tabs('exists',name)){
		$('#tabBox').tabs('close',name);
	}
}

function closeTabsAll(){
	var count=$('#tabBox').tabs('tabs').length;
	for(var index=count;index>1;index--){
		$('#tabBox').tabs('close', index);
	}
}

//更新待办事宜
function updateTask(flag){
	if(flag){
		removeTab();
	}
	if(top.updateTaskPortal) top.updateTaskPortal();
	if($('#tabBox').tabs('exists',"待办任务")){
		$('#tabBox').tabs('select',"待办任务");
		$('#tabBox').tabs('update',{
			tab:$('#tabBox').tabs('getSelected'),
			options:{
				content: '<iframe src="'+_basePath+'/bpm/Task.action" width="100%" height="100%" align="center" frameborder="0" border="0"></iframe>'
			}
		});
	}
}

function execLoad(){
	/*
	 $('#tabBox').tabs('add',{
	 title: "待办事宜",
	 content: '<iframe src="'+_basePath+'/bpm/Task.action" width="100%" height="100%" align="center" frameborder="0" border="0"></iframe>',
	 closable: false
	 });
	 */
	var leftAccordion = $("#leftAccordion");
	leftAccordion.accordion({
//		fit:true,
		border:false,
		animate:false
	});

	$.ajax({
		type:'GET',
		url:_basePath+'/systemManagement/Menu!getMenus.action?'+new Date().getTime(),
		data:'',
		//async:false,
		success:function(data){
			var treeDatas =  JSON.parse(data);
			for(var p = 0 ; p < treeDatas.length; p++){
				//var t = "tree"+p;
				var lpeanl = treeDatas[p];
				var onClick = "function(node){if(node.attributes.url){addTab(node.text,node.attributes.url);}$(this).tree(\"toggle\",node.target);}";
				var child = JSON.stringify(lpeanl.children);
				if(p==0){
					leftAccordion.accordion("add",{
						title: lpeanl.text,
						content: "<ul id='tree'"+p+" style=\"width:205px\" class='easyui-tree' data-options='data:"+child+",onClick:"+onClick+"'></ul>",
						selected: false,
						iconCls: lpeanl.iconCls
					});
				}else{
					leftAccordion.accordion("add",{
						title: lpeanl.text,
						content: "<ul id='tree'"+p+" style=\"width:205px\" class='easyui-tree' data-options='collapsabled:false,animate:true,data:"+child+",onClick:"+onClick+"'></ul>",
						selected: false,
						iconCls: lpeanl.iconCls
					});
				}
			}
			leftAccordion.find(".accordion-body").css("overflow-x","hidden");
			leftAccordion.accordion("select",0);
		}
	});

	$('#tabBox').tabs('add',{
		title: "主页",
		content: '<iframe src="'+_basePath+'/Index!portal.action" width="100%" height="100%" align="center" frameborder="0" border="0"></iframe>',
		closable: false,
		tools:[{
			iconCls:'icon-mini-refresh',
			handler:function(){
				$('#tabBox').tabs('update',{
					tab:$("#tabBox").tabs("getSelected"),
					options:{
						content: '<iframe src="'+_basePath+'/Index!portal.action" width="100%" height="100%" align="center" frameborder="0" border="0"></iframe>'
					}
				});
			}
		}]
	});

//	$('#tabBox').tabs('add',{
//		title: "桌面",
//		content: '<iframe src="'+_basePath+'/Index!desktop.action" width="100%" height="100%" align="center" frameborder="0" border="0"></iframe>',
//		closable: false
//	});

	$('#tabBox').tabs('select',0);

	$("#closeAll").hide();
//	$('#layout').layout('collapse','west');
	$("#tabBox").tabs({
		onContextMenu:function(e,title){
			e.preventDefault();
			$("#closeAll").menu('show', {
				left: e.pageX+10,
				top: e.pageY+5
			});
		}
	});
	$("#closeAll").menu({
		onClick:function(item){
			if(item.text=='关闭所有'){
				closeTabsAll();
			}else if(item.text=="刷新当前标签页"){

			}
		}
	});
}


function showErweima(){
	$("#erweimadiv").dialog({
		title:'服务小助手',
		width:334,
		height:508
	});
}

function pwdChange(){
	var changeflag=$("#changeflag").val();
	if(changeflag=="true"){
		$('#pwdChange').dialog({
			title: '设置密码',
			width: 250,
			height: 250,
			modal:true,
			cache: true,
			iconCls : "icon-group",
			buttons : "#pwdChange-buttons"
		});
	}else{
		$('#pwdChange').dialog({
			title: '设置密码',
			width: 250,
			height: 250,
			cache: true,
			iconCls : "icon-group",
			buttons : "#pwdChange-buttons"
		});
	}
	$("#oldPwd").val("");
	$("#newPwd1").val("");
	$("#newPwd2").val("");
}

function doPwdChange(){
	var oldPwd = $("#oldPwd").val();
	var newPwd1 = $("#newPwd1").val();
	var newPwd2 = $("#newPwd2").val();
	var MOBILE=$("#USER_MOBILE").val();
	var USER_EMAIL=$("#USER_EMAIL").val();
	var code = /^(?:[a-z]+[_\-\+\.]?)*[a-z]+$/i;
	var userflag=$("#userflag").val();
	if(MOBILE==""&&userflag=="true"){
		$.messager.alert("提示","请输入手机号");return;
	}else if(USER_EMAIL==""&&userflag=="true"){
		$.messager.alert("提示","请输入邮箱");return;
	}else if(!checkPhone(MOBILE)&&userflag=="true"){
		$.messager.alert("提示","请输入正确的手机号");return;
	}else if(!checkEmail(USER_EMAIL)&&userflag=="true"){
		$.messager.alert("提示","请输入正确的邮箱地址");return;
	}else if(newPwd1.length<6){
		$.messager.alert("提示","密码长度必须大于或等于6位");return;
	}else if(oldPwd == "") {
		$.messager.alert("提示","请输入原密码");return;
	}else if(!isNaN(newPwd1)||code.test(newPwd1)){
		$.messager.alert("提示","请使用数字和字母组合密码");return;
	}else if(newPwd1 == "") {
		$.messager.alert("提示","请输入新密码");return;
	}else if(newPwd2 == "") {
		$.messager.alert("提示","请输入确认密码");return;
	}else if(newPwd1 != newPwd2) {
		$.messager.alert("提示","两次输入新密码不一致");return;
	}

	$.ajax({
		url : _basePath+"/user/MyInfo!doChangePwd.action",
		data : {
			oldPwd : CalcuMD5(oldPwd),
			newPwd : newPwd1,
			mobile:  MOBILE,
			eamail:  USER_EMAIL
		},
		dataType : "json",
		success : function(e){
			if(e && e.flag){
				$.messager.alert("提示","修改成功");
				$("#pwdChange").dialog('close');
				window.location.reload(true);
			}else{
				$.messager.alert("提示",e.msg);
			}
		}
	});
}
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

//Email验证
function checkEmail(val){
	var reMail = /^(?:[a-z\d]+[_\-\+\.]?)*[a-z\d]+@(?:([a-z\d]+\-?)*[a-z\d]+\.)+([a-z]{2,})+$/i;
	if(!reMail.test(val))
	{
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
function checkPhone(ele){
	var reg = /(^0?[1][3458][0-9]{9}$)/;
	if(reg.test(ele) == false){
		return false;
	}else{
		return true;
	}
}

function switchpage(){
	var changeflag=$("#changeflag").val();
	if(changeflag=="true"){
		$('#switchpage').dialog({
			title: '切换页面',
			modal:true,
			cache: true,
			iconCls : "icon-group"
		});
	}else{
		$('#switchpage').dialog({
			title: '切换页面',
			cache: true,
			iconCls : "icon-group"
		});
	}
}

function doswitchpage(e)
{
	var id = e;
	window.location="Index!loadRole.action?id="+id;
}

//setInterval(function(){
//	 $.ajax({
//		 url : _basePath+"/Sys!getMsg.action",
//		 dataType : "text",
//		 success:function(text){
//			 if(text && text != ""){
//				 $.messager.show({
//					 title:'通知',
//					 msg: text,
//					 showType:'show'
//				 })
//			 }
//		 }
//	 });
// }, 3000);
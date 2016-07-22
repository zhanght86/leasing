var app = angular.module('jxlApp',[]);
var __baseApi = document.getElementById("ctx").value;
__baseApi += '/telephonenumberlist/TelephoneNumberList!';
var projectId = document.getElementById("projectId").value;
var updt = document.getElementById("updt").value;

function checkPwd(){
	var pwd = document.getElementById("pwd");
	var pwdCheck = document.getElementById("pwdCheck");
	if(pwdCheck.checked){
		pwd.type = "text";
	}else{
		pwd.type = "password";
	}
}

app.controller('collectCtrl', ['$scope','$http','$timeout','$q', function($scope,$http,$timeout,$q){			
	var jxlVm = $scope.jxlVm = {};
	jxlVm.accountEditable = false;
	jxlVm.resetPwdMethod = 0;
	jxlVm.resetPwd = false;
	jxlVm.supportCategories = [];
	//请求消息
	var defaultReqMsg = {
		token:"",
		account:"",
		password:"",
		captcha:"",
		type:"",
		contact1:"",
		contact2:"",
		contact3:"",
		name:"",
		id_card_num:"",
		project_id:projectId,
		updt:updt
	};

	//请求消息类型
	jxlVm.reqMsgType = {
		resendCaptcha:"RESEND_CAPTCHA", // 重新发送验证码
		submitPwd:"SUBMIT_PWD", // 提交密码
		submitCollectCaptcha:"SUBMIT_CAPTCHA", // 提交验证码
		resetPwd:"RESET_PWD", // 重置密码
		resetPwdResendCaptcha:"RESEND_RESET_PWD_CAPTCHA"// 重新重置密码验证码
	};

	//申请信息
	jxlVm.customer_info = {
		selected_website: [],
		basic_info: {
			name:"",
			id_card_num:"",
			cell_phone_num:"",
			project_id:projectId,
			updt:updt
		}
	};

	jxlVm.ApiURL = {
		getDataSources: __baseApi + "dataSources.action",
		application :__baseApi + "application.action",//生成token
		collectReq  :__baseApi + "collect.action",//采集请求
		resetReq :__baseApi + "reset.action" //重置请求
	};

	jxlVm.task = {
		inputPwd:"inputPwd"
	};
	
	jxlVm.currentStep = "apply";
	jxlVm.currentTask = "";
	//当前抓取的网站
	jxlVm.currentWebsiteDisplayName = "";
	jxlVm.currentWebsite = "";
	jxlVm.datasourcesLoaded = false;
	// 机构支持的数据源
	jxlVm.supportDatasources = [];
	jxlVm.selectedDatasourceKeys = [];
	
	jxlVm.reqMsg = angular.copy(defaultReqMsg);

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 业务代码  开始
	////////////////////////////////////////////////////////////////////////////////////////////////////

	//1.生成token
	jxlVm.generateToken = function(){
		jxlVm.customer_info.basic_info.name = document.getElementById("name").defaultValue;
		jxlVm.customer_info.basic_info.cell_phone_num = document.getElementById("cellPhone").defaultValue;
		jxlVm.customer_info.basic_info.id_card_num = document.getElementById("idCardNum").defaultValue;
		$http.post(jxlVm.ApiURL.application, jxlVm.customer_info.basic_info)
			.success(function(resp){
				jxlVm.datasourcesLoaded = true;
				console.log("# 生成token 返回值 ==>", resp);
				if(resp.flag){
					if(resp.msg == '100'){
						console.log(jxlVm.reqMsg);
						//显示 登录界面
						jxlVm.currentStep = 'collect';
						console.log("返回值："+resp.data);
						//设置当前数据源账号
						if(resp.data.datasource.category == 'mobile'){
							jxlVm.reqMsg.account = jxlVm.customer_info.basic_info.cell_phone_num;
						}else{
							jxlVm.accountEditable = true;
						}
						//设置当前数据源
						jxlVm.currentWebsite = resp.data.datasource.website;
						jxlVm.currentWebsiteDisplayName = resp.data.datasource.name;
						//重置密码方式
						jxlVm.resetPwdMethod = resp.data.datasource.reset_pwd_method;
						console.log("# 显示采集界面开始采集 ==>" + jxlVm.currentWebsite);
						// 设置 token
						jxlVm.reqMsg.token = resp.data.token;
						// 设置请求消息中的website
						jxlVm.reqMsg.website = jxlVm.currentWebsite;
						var password = resp.data.password;
						if(password != null && password != ""){
							jxlVm.reqMsg.password = password;
						}
						jxlVm.reqMsg.name = resp.data.name;
						jxlVm.reqMsg.id_card_num = resp.data.id_card_num;
						// 设置请求消息中的token
						console.log("# 请求消息为0", jxlVm.reqMsg);
						console.log("# 设置当前任务为：登录");
						console.log("# 重置密码方式" + jxlVm.resetPwdMethod);
						jxlVm.currentTask = "LOGIN";
					}else if(resp.msg == '200'){
						jxlVm.currentStep = 'finish';
					}else {
						var info = jxlVm.customer_info.basic_info;
						self.location = __baseApi + 'toReportPage.action?projectId=' + projectId + '&name=' + info.name + '&cellPhone=' + info.cell_phone_num + "&idCardNum=" + info.id_card_num;
					}
				}else{
					jxlVm.fatalErrorMsg = resp.msg;
					jxlVm.currentStep = 'error';
				}
			})
			.error(function(data,status){
				console.log("error");
			})
	};

	jxlVm.generateToken();

	//2.数据源登录
	jxlVm.websiteLogin = function(){
		jxlVm.processLoadding = true;
		jxlVm.sendCollectReq().then(function(data){
			console.log("==========================");
			console.log(data);
			console.log("==========================");
			jxlVm.getCollectResponse(data,60);
		},function(resp){
			jxlVm.currentStep = 'error';
			jxlVm.errorMsg = resp.msg;
			jxlVm.controlMsg = "";
			jxlVm.processLoadding = false;
		})

	};
		
	//3.提交动态密码
	jxlVm.submitCollectCaptcha = function(){
		jxlVm.processLoadding = true;
		jxlVm.reqMsg.type = "SUBMIT_CAPTCHA";
		jxlVm.sendCollectReq().then(function(data){
		jxlVm.getCollectResponse(data,120);
		},function(resp){
			jxlVm.currentStep = 'error';
			jxlVm.errorMsg = resp.msg;
			jxlVm.controlMsg = "";
			jxlVm.processLoadding = false;
		})
	};

	//4.重新发送采集动态密码
	jxlVm.resendCollectCaptcha = function(){
		console.log("# 重新发送采集动态密码！");
		jxlVm.processLoadding = true;
		var resend = document.getElementById("resend");
		var count = 60;
		var timeId = setInterval(function(){
			count--;
			resend.disabled = true;
			resend.innerHTML = '重发动态密码(' + count + ')';
			if(count == 0){
				resend.disabled = false;
				resend.innerHTML = '重发动态密码';
				clearInterval(timeId);
			}
		}, 1000);
		jxlVm.reqMsg.type = "RESEND_CAPTCHA";
		jxlVm.sendCollectReq().then(function(data){
			console.log("重新发送采集动态密码！ ===> 成功");
			jxlVm.getCollectResponse(data,60);
		},function(resp){
			jxlVm.currentStep = 'error';
			jxlVm.errorMsg = resp.msg;
			jxlVm.controlMsg = "";
			jxlVm.processLoadding = false;
		})
	};
		
	jxlVm.sendCollectReq=function(){
		var deferred=$q.defer();
		//var url=jxlVm.ApiURL.collectReq;
		//var url="http://localhost:8080/jieyi-project/telephonenumberlist/TelephoneNumberList!collect.action";
		console.log("# 请求URL为"+jxlVm.ApiURL.collectReq,"请求内容为"+jxlVm.reqMsg);
		$http.post(jxlVm.ApiURL.collectReq, jxlVm.reqMsg)
			.success(function(resp,status){
				if(resp.flag){
					console.log("# 发送交互请求成功");
					deferred.resolve(resp.data);
				}else{
					console.log("# 发送交互请求失败");
					deferred.reject(resp);
				}
			})
			.error(function(resp,status){
				deferred.reject(resp);
				return deferred.promise;
			});
		return deferred.promise;
	};

	jxlVm.getCollectResponse=function(data,times){
		console.log(data);
		//debugger;
		//jxlVm.respMsg=resp.data;
		console.log("# log==>" + data.content);
		if(data.content){
			// 对于返回信息的逻辑处理  ==》 开始
			if(data.type == 'CONTROL'){
				jxlVm.controlMsg = data.content;
				if(data.content.indexOf('服务密码错误') != -1){
				  jxlVm.currentTask = "SUBMIT_PWD";
				  jxlVm.reqMsg.type = 'SUBMIT_PWD';
				}
				if (data.content.indexOf('输入动态密码') != -1
					|| data.content.indexOf('动态密码错误') != -1
					|| data.content.indexOf('请用本机发送') != -1
					|| data.content.indexOf('短信码失效请用本机发送') != -1) {
					jxlVm.currentTask = "SUBMIT_CAPTCHA";
					jxlVm.reqMsg.type = 'SUBMIT_CAPTCHA';
				}
				//前端交互流程完成
				if (data.process_code === 10008) {
					//流程结束标识
					if (data.finish) {
						console.log("==================================");
						console.log("采集完成");
						console.log("初始化请求数据");
						jxlVm.reqMsg.account = '';
						jxlVm.reqMsg.password = '';
						console.log("==================================");
						jxlVm.currentStep = 'finish';

					} else {
						alert("【" + jxlVm.currentWebsiteDisplayName + "】收集完成,下一个为" + "【"
							+ data.next_datasource.name + "】");
						//清除提示消息
						jxlVm.controlMsg = "";
						jxlVm.currentWebsite = data.data.next_datasource.website;
						jxlVm.currentWebsiteDisplayName = data.data.next_datasource.name;
						console.log("初始化请求数据");

						jxlVm.reqMsg.password = '';
						jxlVm.currentTask = "LOGIN";
						jxlVm.reqMsg.website = jxlVm.currentWebsite;

						if (data.next_datasource.category == 'mobile') {
							jxlVm.reqMsg.account = jxlVm.customer_info.basic_info.cell_phone_num;
						} else {
							jxlVm.accountEditable = true;
							jxlVm.reqMsg.account = '';
						}
					}
				}
				jxlVm.errorMsg = null;
				jxlVm.tipMsg = null;
			} else if (data.type == 'ERROR') {
				jxlVm.errorMsg = data.content;
				jxlVm.controlMsg = null;
				jxlVm.tipMsg = null;
			} else if (data.type == 'TIP') {
				jxlVm.tipMsg = data.content;
				jxlVm.controlMsg = null;
				jxlVm.errorMsg = null;
			}

			jxlVm.processLoadding = true;

			// 除了TIP以外的类型均属于 打断流程的消息
			if (data.type != 'TIP') {
				times = 0;
				jxlVm.processLoadding = false;
			}
			// 对于返回信息的逻辑处理  ==》 结束
		}
	}

}]);
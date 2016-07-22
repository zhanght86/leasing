
define([/*'jquery',*/ 'wx'],function (wx){ //$,
	/**
	 * 微信接口
	 * @param options :  	appId:  	// 必填，企业号的唯一标识，此处填写企业号corpid
						    timestamp:  // 必填，生成签名的时间戳
						    nonceStr:  // 必填，生成签名的随机串
						    signature: // 必填，签名，见附录1
						    debug : // 可选， 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	 */

	return function( options ){
		// 运行状态
		var openwWindow1 = function() {
			$( "#loading" ).css({
				'opacity' : 0.6
				,'position':'absolute'
				,'top':'0px'
				,'left':'0px'
				,'width': $(document).width()
				,'height': $(document).height()
				,'line-height':'400px'
				,'background-color':'#000'
				,'text-align':'center'
				,'vertical-align':'middel'
				,'color':'#fff'
			}).fadeIn(100);
		}
		var openwWindow2 = function() {
			$('#loading').fadeOut(100);
		}
		/*if($('#loading').size() == 0){
			var div = '<div id="loading" style="display:none;font-size:2em;">正在初始化微信接口</div>';
			$(document.body).append(div );
			openwWindow1();
		}*/
		
		
		var authenticate = false;		// 认证 ： 只有true时这里的方法才能被调用
		var deaultOpt = {
			  'debug'		: false
		    , 'appId'		: ''
		    , 'timestamp'	: ''
		    , 'nonceStr'	: ''
		    , 'signature'	: ''
		    , 'jsApiList' : [
		                     'checkJsApi',
		                     'chooseImage',
		 	                'previewImage',
		 	                'uploadImage',
		 	                'downloadImage',
		 	                'closeWindow'
		                /*'onMenuShareTimeline',
		                'onMenuShareAppMessage',
		                'onMenuShareQQ',
		                'onMenuShareWeibo',
		                'hideMenuItems',
		                'showMenuItems',
		                'hideAllNonBaseMenuItem',
		                'showAllNonBaseMenuItem',
		                'translateVoice',
		                'startRecord',
		                'stopRecord',
		                'onRecordEnd',
		                'playVoice',
		                'pauseVoice',
		                'stopVoice',
		                'uploadVoice',
		                'downloadVoice',
		               'getNetworkType',
		                'openLocation',
		                'getLocation',
		                'hideOptionMenu',
		                'showOptionMenu',
		                'scanQRCode',
		                'chooseWXPay',
		                'openProductSpecificView',
		                'addCard',
		                'chooseCard',
		                'openCard' */
		                ] 
		};
		if(options){
//			alert(JSON.stringify(options) );
			$.extend( deaultOpt , options);
		}
		wx.config( deaultOpt);
		//---------------
		 // 5.1 拍照、本地选图
		 var images = {
			   localIds: [],
			   serverIds: [],
		 };
		 
		 
		 var isAuthorized = function (){
			if(!authenticate ){
				console.debug('网络微信JS接口认证没有通过，此方法不可用');
				alert('亲，操作太快了');
			}
			return authenticate;
		 }
		 //1、 config信息验证后会执行ready方法
		 wx.ready(function(){
			 var timetemp = null;
			 if(deaultOpt.debug){
	    		console.debug('微信JS接口初始化完成！');
	    		timetemp = new Date().getTime();
			 }
			 openwWindow2();
			 wx.hideOptionMenu();
			// 2、需要检测的JS接口列表
			wx.checkJsApi({
				     jsApiList: [
				           'chooseImage'
				           ,'previewImage'
				           ,'downloadImage'
				           ,'uploadImage'
	                 ] 
				   , success: function(res) {
					    openwWindow2();
				        // 以键值对的形式返回，可用的api值true，不可用为false
				        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
				    	if( res['errMsg'].split(":") == "ok" ){
				    		return alert('注意：当前客户端版本不支持微信JS接口，部分相关微信的操作将不能正常使用！');
				    	}
				    	var failureAPIs = [];
				    	var checkResult = res['checkResult'];
			    		for(var attr in checkResult){
			    			if(checkResult[attr] != true){
			    				failureAPIs.push(attr);
			    			}
				    	}
			    		if(failureAPIs.length > 0){
			    			return alert('当前浏览器不支持部分微信JS接口：'+ failureAPIs.join(','));
			    		}
			    		authenticate = true;
			    		wx.hideAllNonBaseMenuItem({ });
			    		if(deaultOpt.debug){
				    		var gname = '检测的JS接口';
				    		console.group( gname);
				    		console.debug(res);
				    		console.groupEnd( gname);
				    		
				    		alert('接口验证用时：'+( new Date().getTime() - timetemp )+ " ms");
				    	}
				    }
					,fail:function(){
						console.debug('.....');
					}
		    });
		 });
		 
		 //error  config信息验证失败会执行error函数
		 wx.error(function(res){
			 authenticate = false;
			 if(deaultOpt.debug){
				 console.debug('微信公众平台验证失败@_@');
			 }
			 alert('微信公众平台验证失败:'+ res.errMsg);
			 openwWindow2();
		 });
		
		 /**
		  * 选择 或拍照
		  * @param callback( localIds ) 
		  */
		this.chooseImage = function (callback) {
			if( !isAuthorized() ){
				return ;
			}
		   images.localIds = [];
		   wx.chooseImage({
			     success: function (res) {
			    	 images.localIds = res.localIds;
			    	 if(images.localIds.length <= 0){
			    		 return alert('请选择至少一张图片');
			    	 }
//			    	 alert('已选择 ' + res.localIds.length + ' 张图片');
			    	if(deaultOpt.debug){
			    		var gname = '选择或拍照';
			    		console.group( gname);
			    		console.debug(res);
			    		console.groupEnd( gname);
			    	}
			       if(callback){
			    	   setTimeout(function (){
			    		   callback(res.localIds);
			    	   }, 1000);
			       }
			     }
		   		,fail: function (res){
//		   			alert(JSON.stringify(res));
		   			alert('!_! , 图片接口出问题了');
		   		}
		   	
		   });
		 };
		
	     
	     /**
		 * 图片预览
		 * @param preData : like {current : '', urls:[]};
		 */
	     this.previewImage = function (eventstr ,wrapper ,srcSelector){
	    	 $(document.body).on(eventstr,wrapper+" "+srcSelector, function () {
	    		if( !isAuthorized() ){
	  				return ;
				}
				var preData = {current : window.location.origin + $(this).attr('_src') , urls:[]};
				$(srcSelector, wrapper).each(function (i,doc){
					var path = $(this).attr('_src');
		    		path = window.location.origin + path;
	 	   			preData.urls.push( path );
	 	   		});
//				console.debug(preData);
	 	   		wx.previewImage(preData);
	 	   	 });
	     };
		 
	     
	     /**
		  * 上传图片到微信服务器
		  * @param itemCallback(index, localId) : 每一张图片上传成功调用
		  * @param completeCallback(serverIds) : 所有图片上传成功调用, sucesslocalIds 上传成功的 ， serverIds 所有的
		  */
		 this.uploadImage = function (itemCallback, completeCallback ){
			 if( !isAuthorized() ){
					return ;
			}
			 if (!images.localIds || images.localIds.length <= 0) {
			     alert('请先选择图片');
			     return;
			 }

			var ids  = [];
			var i = 0 ;
			var length = images.localIds.length;
			function upload() {
				wx.uploadImage({
					localId : images.localIds[i],
					success : function(res) {
						ids.push(res.serverId); // media_id 
						if(itemCallback){
							itemCallback(i,images.localIds[i]);
						}
//						alert('已上传：' + i + '/' + length);
						if (++i < length ) {
							setTimeout(function(){upload(i);}, 300);
							return ;
						}
						
						if(deaultOpt.debug){
							alert('将有 '+ ids.length +' 张图片被上传。');
						}
						if(completeCallback){
							completeCallback(ids );
						}
					},
					fail : function(res) {
						images.serverIds = [];
						ids = [];
						alert(JSON.stringify(res));
						alert("呀！微信服务又在打小差，请重新上传");
					}
				});
			}
			upload();
	     };
	     
	     
	     /**
	      * 下载图片
	      */
	     this.downloadImage = function ( callback ){
	    	 if( !isAuthorized() ){
					return ;
	    	 }
	    	 if (images.serverId.length === 0) {
				alert('请先使用 uploadImage 上传图片');
				return;
			}
			var i = 0, length = images.serverId.length;
			images.localId = [];
			function download() {
				wx.downloadImage({
					serverId : images.serverId[i],
					success : function(res) {
						i++;
						alert('已下载：' + i + '/' + length);
						images.localId.push(res.localId);
						if (i < length) {
							download();
						}
					}
				});
			}
			download();
	     };
	     
	     
	}
});

/**
 * <pre>
 * 此插件用与页面操作等待时 显示遮罩层功能。
 * 用法：$(document).usePageServer();
 * </pre>
 */
(function ($){
	/**
	 * 文件名与插件名： 文件名 == jquery.插件名去掉'use'并把原'use'后的字母改成小写
	 */
	$.fn.usePageServer = function (options){
		// 什么时候用参数了，自己写吧
		var settings = $.extend({
			tipMsg : ''
		}, options); 
		
		
		//XXX 加载多个内嵌页面有可能有问题的
		return this.each(function (){
			console.group("jquery.pageServer");
			console.debug(this);
			
			var initwWindow = function() {
				if($('.loading__').size() == 0){
					var msg = "操作处理中...";
					if(settings.tipMsg){
						msg = settings.tipMsg;
					}
					var div = '<div class="loading__" style="display:none;font-size:2em;">'+ msg +'</div>';
					$(document.body).append(div );
				}
				
				$( ".loading__" ).css({
					'opacity' : 0.4
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
				console.debug('-- 页面操作保护层显示');
			};
			var closeWindow = function() {
				if($('.loading__').size() != 0){
					$('.loading__').fadeOut(100);
					console.debug('-- 页面操作保护层隐藏');
				}
			};
			var openwWindow = initwWindow;
			
			console.groupEnd();
			$( this ).ajaxStart(function() {
				openwWindow();
			});
			$( this ).ajaxComplete(function() {
				closeWindow();
			});
			$(this ).ajaxError(function(event,request, settings){
				console.error('-- 页面操作保护层服务超时');
				closeWindow();
			});
			
		});
	}
})(jQuery);
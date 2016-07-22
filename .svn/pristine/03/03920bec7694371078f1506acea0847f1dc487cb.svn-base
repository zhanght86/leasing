/**
 * <pre>
 * 此插件功能类似confirm .很强大
 * 用法： 
 * 		启动本页面的自定义confirm :$(targetselector).useConfirm(); 
 * 		targetselector 元素可能要设置的属性： confirm-msg |confirm-yes-action |confirm-no-action | confirm-opacity
 * </pre>
 * @author luyanfeng
 */
(function ($){
	/**
	 * 文件名与插件名： 文件名 == jquery.插件名去掉'use'并把原'use'后的字母改成小写
	 */
	$.fn.useConfirm = function (options){
		
		/*
		 * 什么时候用参数了，自己写吧
		 * 这里的是默认值，就是全局的
		 */ 
		var settings = $.extend({
			tipMsg 			: '未知操作',
			yesAction		: "Function()",
			noAction  		: "closeWindow",
			layoutOpacity	: 0.4,
			open			: false
		}, options); 
		
		//XXX 加载多个内嵌页面有可能有问题的
		return this.each(function (index ){
			var wrapClassName = 'wrap_'+index;
			var layoutClassName = 'layout_confirm_'+index;
			var confirmCalssName = 'confirm_'+index;
			console.group("jquery.useConfirm");
			console.debug(this);
			var initWindow = function() {
				if($('.'+wrapClassName).size() == 0){
					var wrap = '<div class="'+ wrapClassName +'" style="display:none;"></div>';
					
					var confirmDiv =  '<div  style="" class="'+ confirmCalssName +'">  '
								+'	<h4>提示</h4>                                           '
								+'	<p>'+ settings.tipMsg +'</p>                   '
								+'	<p>                                                     '
								+'		<div class="button btn-primary yes"  >确定</div>'
								+'		<div class="button btn-default no"  >取消</div>'
								+'	</p>                                                    '
								+'</div>                                                    '
					;
					
					var div = '<div class="'+ layoutClassName +'" style="font-size:2em;"></div>';
					$(document.body).append(wrap );
					$('.'+wrapClassName).append(div).append(confirmDiv);
					
					$('.'+confirmCalssName).css({
												opacity:1,
												width:'100%',
												backgroundColor:"#fff",
												zIndex : 99999,
												position: 'fixed',
												bottom:'100px',
												padding: '10px',
												bottom: '50%',
												textAlign: 'center'
											})
											.on('click', '.yes', eval(settings.yesAction) )
											.on('click', '.no', eval(settings.noAction) )
										  ;
				}
				
				$( "."+layoutClassName ).css({
					'opacity' : settings.layoutOpacity
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
					,'zIndex' : 99999
				});
				$('.'+wrapClassName).css({'zIndex': 99999}).fadeIn(100);
				console.debug('-- confirm操作保护层显示');
			};
			var closeWindow = function() {
				if($('.'+wrapClassName).size() != 0){
					$('.'+wrapClassName).fadeOut(100);
					console.debug('-- confirm操作保护层隐藏');
					return false;
				}
			};
			var openwWindow = initWindow;
			
			$.extend(settings, {
				tipMsg 			: $(this).attr('confirm-msg') ? $(this).attr('confirm-msg') : settings.tipMsg,
				yesAction		: $(this).attr('confirm-yes-action') ? $(this).attr('confirm-yes-action') : settings.yesAction,
				noAction  		: $(this).attr('confirm-no-action') ? $(this).attr('confirm-no-action') : settings.noAction,
				layoutOpacity	: $(this).attr('confirm-opacity') ? $(this).attr('confirm-opacity')  : settings.layoutOpacity
			});
			
			console.groupEnd();
			
			// 开始事件绑定
			$(this).on('click', this, openwWindow);
		});
	};
	
	/**
	 * 使用方式：
	 * $.useConfirm({tipMsg: "提交成功！是否xxx"}, function (value){
			if(	value ){
			}else{
			}
		});
	 */
	$.extend({
		/**
		 * callback : function (value) : value == true | false
		 * @returns this
		 */
		useConfirm: function (options, callback){
			var T__ = this;
			var settings = $.extend({
				tipMsg 			: '未知操作',
				layoutOpacity	: 0.4
			}, options); 
			var index = (Math.random()+"").substr(2);
			var wrapClassName = 'wrap_'+index;
			var layoutClassName = 'layout_confirm_'+index;
			var confirmCalssName = 'confirm_'+index;
			console.group("jquery.useConfirm");
			console.debug(this);
			var initWindow = function() {
				if($('.'+wrapClassName).size() == 0){
					var wrap = '<div class="'+ wrapClassName +'" style="display:none;"></div>';
					
					var confirmDiv =  '<div  style="" class="'+ confirmCalssName +'">  '
								+'	<h4>提示</h4><p>&nbsp;</p>'
								+'	<p>'+ settings.tipMsg +'</p>                   '
								+'	<p>                                                     '
								+'		<div class="button btn-primary yes"  >确定</div>'
								+'		<div class="button btn-default no"  >取消</div>'
								+'	</p>                                                    '
								+'</div>                                                    '
					;
					
					var div = '<div class="'+ layoutClassName +'" style="font-size:2em;"></div>';
					$(document.body).append(wrap );
					$('.'+wrapClassName).append(div).append(confirmDiv);
					
					$('.'+confirmCalssName).css({
												opacity:1,
												width:'100%',
												backgroundColor:"#fff",
												position: 'fixed',
												bottom:'100px',
												padding: '10px',
												bottom: '50%',
												textAlign: 'center',
												zIndex : 99999
											})
											.on('click', '.yes', function (){T__.close(); if(callback) callback(true); } )
											.on('click', '.no',  function (){T__.close(); if(callback) callback(false); } )
										  ;
				}
				
				$( "."+layoutClassName ).css({
					'opacity' : settings.layoutOpacity
					,'position':'absolute'
					,'top':'0px'
					,'left':'0px'
					,'width':  $(document).width()
					,'height':  $(document).height()
					,'line-height':'400px'
					,'background-color':'#000'
					,'text-align':'center'
					,'vertical-align':'middel'
					,'color':'#fff'
					,'zIndex': 99999
				});
				$('.'+wrapClassName).css({'zIndex': 99999}).fadeIn(100);
				console.debug('-- confirm操作保护层显示');
			};
			this.close = function() {
				if($('.'+wrapClassName).size() != 0){
					$('.'+wrapClassName).fadeOut(100);
					console.debug('-- confirm操作保护层隐藏');
					return false;
				}
			};
			initWindow();
			return T__;
		}
	});
})(jQuery);
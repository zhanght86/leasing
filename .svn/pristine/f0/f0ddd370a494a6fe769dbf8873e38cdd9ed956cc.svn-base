

define([/*'jquery', */'ejs','jquery.layout'], function (/*jquery,*/ ejs, la){
	var jQuery = jquery = $ = jQuery;
	
	
	
	
	//==========================out of object =================================
	/**
	 * 页面信息输入校验
	 */
	(function ($){
		// 字母转大写
		$.fn.autoupper_check = function (options){
			var settings = $.extend({ }, options); 
			
			return this.each(function (){
				var newVal = this.value;
				if(this.value){
					var arr = this.value.split('');
					for( var i in arr ){
						arr[i] = arr[i].toUpperCase() ;
						
					}
					newVal = arr.join('');
				}
				this.value  = newVal;
				
			});
		}
		
		
		// 小数 
		$.fn.decimal_check = function (options){
			var settings = $.extend({ }, options); 
			return this.each(function(){
				if( !this.value || isNaN(this.value) ){
					this.value  = '';
					return false;
				}
			});
		}
		
		// 仅支持汉字、字母、空格
		$.fn.name_check = function (options){
			var settings = $.extend({ }, options); 
			return this.each(function(){
				var myreg = /(\d|\w| )/i;
				var arr = this.value.split('');
				if(arr.length > 0){
					for(var i = 0 ; i < arr.length ; i++){
						if( !myreg.test(arr[i]) ){
							this.value = '';
							return false;
						}
					}
				}
			});
		}
		
		// 手机号
		$.fn.phone_check = function (options){
			var settings = $.extend({ }, options); 
			return this.each(function(){
				var myreg = /^(1[0-9]{2})\d{8}$/;
		        if(!myreg.test(this.value)){
//		        	alert(this.title + '格式不正确');
		        	this.value= '';
		        	return false;
		        }
			});
		}
		// 年龄
		$.fn.age_check = function (options){
			var settings = $.extend({ }, options); 
			return this.each(function(){
		        if( !this.value || isNaN(this.value) ){
		        	this.value= '';
		        	return false;
		        }
		        this.value = parseInt(this.value);
	        	if(  this.value <= 0 || this.value >= 180 ){
		        	this.value= '';
		        	return false;
	        	}
			});
		}
		// 数值
		$.fn.number_check = function (options){
			var settings = $.extend({ }, options); 
			return this.each(function(){
				if( !this.value || isNaN(this.value) ){
//		        	alert(this.title + '格式不正确.');
					this.value= '';
					return false;
				}
			});
		}
		
		// 不包含数值
		$.fn.contain_number_check = function (options){
			var settings = $.extend({ }, options); 
			return this.each(function(){
				var reg = new RegExp("[0-9]+", 'gi');
				if( reg.test( this.value)){
					this.value = '';
					return false;
				}
			});
		}
		
		/**
		 * form 表单校验 
		 * $('#save_form').form_check()
		 * @param exclusiveArray 不验证的元素selector
		 * @param inclusiveArray 验证的元素selector
		 * return options.result ;
		 */ 
		$.fn.form_check = function (options){
			if(!options){
				options = {};
			}
			var settings = $.extend({
					exclusiveArray: [':hidden','[readonly]', '[disabled]']
					,inclusiveArray: []
				}, options); 
			return this.each(function (){
				var ischeked = false;
				if( !ischeked){
					var incl = settings.inclusiveArray;
					if(incl && incl.length > 0){
						$(incl.join(',')).each(function (){
							if( !this.value ){
								ischeked = true; 
								this.focus(); 
								alert("请输入【"+this.getAttribute('title')+"】！");
								options.result = this.getAttribute('title');
								return false;
							}
						});
					}
				}
				
				
				if( !ischeked &&  settings.exclusiveArray && settings.exclusiveArray.length > 0){
					var excl = settings.exclusiveArray;
					$(':input[title]:not('+ excl.join(',') +')').each(function (){
						if(!this.value){
							ischeked = true;
							alert("请输入【"+this.getAttribute('title')+"】！");
							this.focus();
							options.result = this.getAttribute('title');
							return false;
						}
					});
				}
			});
		}
		
	}(jquery));

	$(function (){
		if(document.querySelector('#__page')){
			document.querySelector('#__page').value = 1;
		}
		
		$('.decimal_check').on('keyup',function (){
			$(this).decimal_check();
		});
		// 
		$('.name_check').on('keyup',function (){
			$(this).name_check();
		});
		$('.phone_check').on('change',function (){
			$(this).phone_check();
		});
		$('.age_check').on('keyup',function (){
			$(this).age_check();
		});
		
		$('.contain_number_check').on('keyup',function (){
			$(this).contain_number_check();
		}).keydown(function (e){
			if(/\d/.test(e.key)){
				return false;
			}
		});
		
		$('.number_check').on('keyup',function (){
			$(this).number_check();
		});
		
		// 点击全选
		$('input:not(:hidden)').on('click',function (){
			this.select();
		});
		
		$(document).on('change','.autoupper_check', function (){
			$(this).autoupper_check();
		});
		
	});

	$(function (){
		$('[_goback]').on('click', function (){
			window.location.href = $(this).attr('_goback');
		});
	});

	
	
	
	
	
	
	return  new function (){
		//左右上下划动事件
		this.isTouchDevice = function (options) {
			var defaultOpt = {
					target : document
					,fire_LR : 100 // 左右划动触处事件 位移点
					,fire_UD : 100 // 上下划动触处事件 位移点
					,is_fire_more : false // 是否可以触处多个事件 （及斜着划 可能都触发）
					, upCallBack : null
					, downCallBack : null
					, leftCallBack : null
					, rightCallBack : null
					, touchCallBack : null
			}
			$.extend(defaultOpt, options);
			
			var startX = 0, startY = 0;  
			var currentX = 0 , currentY = 0;
			var startTime , endTime;
			try {
				var nodeList = document.querySelectorAll(defaultOpt.target);
				if(nodeList.length > 0){
					for(var index in nodeList){
						var target = nodeList[index];
						console.debug(target);
						target.addEventListener('touchstart', function (evt){
							 try {
								 evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
								 console.debug("start..........");
								 startTime = new Date().getTime();
								var touch = evt.touches[0]; // 获取第一个触点
								// 记录触点初始位置
								startX = Number(touch.pageX);  
								startY = Number(touch.pageY); 
							} catch (e) {
								alert('touchSatrtFunc：' + e.message);
							}  
						}, false);
						target.addEventListener('touchmove', function (evt){
					        	try {
									evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
									var touch = evt.touches[0]; // 获取第一个触点
									currentX = Number(touch.pageX); // 页面触点X坐标
									currentY = Number(touch.pageY); // 页面触点Y坐标
								} catch (e) {
									alert('touchMoveFunc：' + e.message);
								}  
				        }, false);  
						target.addEventListener('touchend', function (evt){
					        	try {
					        		endTime = new Date().getTime();
					        		if(endTime  - startTime >= 2*1000 && defaultOpt.touchCallBack){
					        			fire_count++;
					        			defaultOpt.touchCallBack( endTime  - startTime );
					        		}
					        		if(!defaultOpt.is_fire_more && fire_count >= 1){// 只能触处一种事件
					        			return true;
					        		}
					        		evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
//					        		console.debug("X:"+(currentX - startX));
//					        		console.debug("Y:"+(currentY - startY));
					        		var fire_count = 0;
				        			if(currentX - startX < 0 && Math.abs(currentX - startX) >= defaultOpt.fire_LR ){ 
					        			if(defaultOpt.rightCallBack){
					        				fire_count++;
					        				defaultOpt.rightCallBack();
					        			}
					        		}else if(currentX - startX > defaultOpt.fire_LR){
					        			if(defaultOpt.leftCallBack){
					        				fire_count++;
					        				defaultOpt.leftCallBack();
					        			}
					        		}
					        		if(!defaultOpt.is_fire_more && fire_count >= 1){// 只能触处一种事件
					        			return true;
					        		}
				        			if(currentY - startY < 0 && Math.abs(currentY - startY) >= defaultOpt.fire_UD ){ 
					        			if(defaultOpt.upCallBack){
					        				fire_count++;
					        				defaultOpt.upCallBack();
					        			}
					        		}else if(currentY - startY > defaultOpt.fire_UD){
					        			if(defaultOpt.downCallBack){
					        				fire_count++;
					        				defaultOpt.downCallBack();
					        			}
					        		}
				        			if(!defaultOpt.is_fire_more && fire_count >= 1){// 只能触处一种事件
					        			return true;
					        		}
								} catch (e) {
									alert('touchEndFunc：' + e.message);
								}
						}, false);  
					}
				}
				
				
			} catch (e) {
				alert("不支持TouchEvent事件！" + e.message);
				
			}
		};
		
		/**
		 * 列表分页
		 * @param options 各种参数
		 */
		this.getPageData = function (options){
				var defaultOpt = {
					isappend : true
					,container : '#main_container'
					,htmlurl : ''
					,dataurl : ''
					,dataParam : ''
				}
				$.extend(defaultOpt, options);
				var template = '';
				if($('.js_no_data').size() >= 1){
					return ;
				}
				jQuery.ajax({ async:false, type:'get', dataType:'html'
					,url : defaultOpt.htmlurl
					,global : false
				}).done(function (html){ template = html;  })
				.fail(function (){ alert('出错');});
				jQuery.ajax({ async:false, type:'post',dataType:'json'
					,url :defaultOpt.dataurl
					,data: defaultOpt.dataParam
				}).done(function (json){
					
					$('#__rows').val(json.page.rows);
					$('#__page').val(json.page.page);
					var html = new EJS({text : template, type:'<'}).render(json.page);
					if(defaultOpt.isappend){
						$('.js_more').hide();
						$(defaultOpt.container).append(html);
					}else{
						$(defaultOpt.container).html(html);
					}
				}).fail(function (){
					console.debug(arguments);
				});
				
			};
			
			//保存
			this.saveScheme = function (){
				var T__= $('.next_page');
				$(T__).unbind('click').text('正在保存数据，请不要离开');
				jQuery.ajax({
					url :$('#next_page_FM').attr('action')
					,data:$('input,select', '#next_page_FM').serialize()
					,type:'post'
					,dataType:'json'
					,success: function (o){
						if(o.flag){
							$(T__).text('数据已保存');
							alert("客户资料已保存，请继续申请！");
							window.location.replace(_basePath+'/weixin/Business!sqList.action');
						}else{
							alert(o.data.error);
							$(T__).text('保存').bind('click',function (){saveScheme(T__);} );
						}
					}
					
				});
			}
			/**
			 * 删除一个项目
			 * @param obj
			 * @param ID
			 */
			this.delBusiness = function (obj, ID ){
				if(obj && confirm('确定删除此申请？')){
					 $.ajax({
						url : _basePath+ '/weixin/Business!delBusiness.action',
						type:'post',
						data:{ID : ID },
						dataType:'json',
						success:function (o){
							if(o.ok){
								$(obj).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(500, function (){$(this).remove(); });
							}else{
								alert('操作异常'+o.error);
							}
						}
					});
				}
			};
			/**
			 * 计算购置税
			 * if(车价<指导价*93%)   购置税=指导价*93%/11.7
			 * if(车价>=指导价*93%)   购置税=车价/11.7
			 */
			this.calcGZS = function (car_price, car_price_zd){
				if(car_price && (car_price.indexOf('￥') >= 0 || car_price.indexOf(',') >= 0 )){
					car_price = car_price.replace(/(\,|￥)/g,'');
				}
				var gzs = $('[name=GZS_MONEY]').val();
				if( car_price < car_price_zd * 0.93 ){
					$('[name=GZS_MONEY]').val( Number(car_price_zd * 0.93 / 11.7 ).toFixed(2) );
				}else{
					$('[name=GZS_MONEY]').val( Number(car_price / 11.7).toFixed(2) );
				}
			};
		
		
	}
});

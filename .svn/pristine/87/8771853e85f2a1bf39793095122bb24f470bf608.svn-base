define([
//		'jquery'
        ],function( ) {
	
	$(function (){
		// 运行状态
		if($('#loading').size() == 0){
			var div = '<div id="loading" style="display:none;font-size:2em;">操作正在处理中</div>';
			$(document.body).append(div );
		}
		$( document ).ajaxStart(function() {
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
		});
		$( document ).ajaxComplete(function() {
			$('#loading').fadeOut(100);
		});
		$(document ).ajaxError(function(event,request, settings){
			$('#loading').fadeOut(100);
			alert('服务超时');
		});
	});
	console.debug('jquery 遮罩层ok.');
	return $;
	
});
$(function() {
//	$.ajax(_basePath+'/leeds/cust_info_input/CustInfoInput!test1.action');
	$("#cc").layout({fit:true});
	$('#cc').layout('add', {
		iconCls: 'icon-search',
		region : 'east',
		width : ($('#mainDiv').width()-10)*0.35,
		title : '查看图片',
		split : true,
//		onResize: function(width, height){
//			var p = $('#cc').layout('panel', 'east');
////			p.panel('refresh');
//		},
		content: '<iframe width="100%" height="100%" frameborder=0 scrolling=auto src="'+_basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action"></iframe>'
//		href: _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action'
	});
//	var tabs1 = [
//		{title: '承租人', url:_basePath+'/leeds/cust_info_input/CustInfoInput!loadRenter.action'},
//		{title: '方案', url:_basePath+'/leeds/cust_info_input/CustInfoInput!loadRenter.action'},
//		{title: '共同承租人', url:_basePath+'/leeds/cust_info_input/CustInfoInput!loadRenter.action'},
//		{title: '担保人', url:_basePath+'/leeds/cust_info_input/CustInfoInput!loadRenter.action'},
//		{title: '附件资料', url:_basePath+'/leeds/cust_info_input/CustInfoInput!loadRenter.action'}
//	];
	$.each(tabs1, function(i, n){
		$('#tt').tabs('add',{    
		    title: n.title,
		    cache: true,
//		    content: '<iframe width="100%" height="100%" frameborder=0 scrolling=auto src="'+_basePath+n.url+'"></iframe>',
		    href: _basePath+n.url,
		    selected : i==0?true:false,
		    style:{
		    	padding: 20
		    },
		    closable:false,    
		    tools:[{    
		        iconCls:'icon-mini-refresh',    
		        handler:function(){    
		       		var tab = $('#tt').tabs('getTab', i);
		       		setTimeout(function(){
			       		tab.panel('refresh');
		       		},0);
		        }    
		    }],    
		    onSelect: function(title, index) {
		    	//切换右侧图片
		    }
		}); 
	});
});
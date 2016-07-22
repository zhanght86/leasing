$(function() {
	$("#cc").layout({fit:true});
	
	$.each(tabs1, function(i, n){
		$('#tt').tabs('add',{    
		    title: n.title,
		    cache: true,
		    content: '<iframe src="'+_basePath+n.url+'" width="100%" height="100%" frameborder=0 scrolling=auto ></iframe>',
		    selected : i==0?true:false,
		    style:{
		    	padding: 5
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
		    }]
		}); 
	});
});
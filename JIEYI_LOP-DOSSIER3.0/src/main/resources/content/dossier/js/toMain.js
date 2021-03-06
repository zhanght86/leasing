$(function() {
	$("#cc").layout({fit:true});
	
	$.each(tabs1, function(i, n){
		$('#tt').tabs('add',{    
		    title: n.title,
		    cache: true,
		    content: '<iframe width="100%" height="100%" frameborder=0 scrolling=auto src="'+_basePath+n.url+'"></iframe>',
		    selected : i==0?true:false,
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

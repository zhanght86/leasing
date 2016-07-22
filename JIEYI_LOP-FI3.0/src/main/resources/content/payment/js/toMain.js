$(function() {
//	$.ajax(_basePath+'/leeds/cust_info_input/CustInfoInput!test1.action');
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
	
	$.each(tabs2, function(i, n){
		$('#ttr').tabs('add',{    
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
//	
//	var src = _basePath+'/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID='+FK_ID+'&PHASE=放款前&taskStatue=1&PROJECT_ID='+PROJECT_ID+'&YHBC='+YHBC;
//	
//	$('#ttr').tabs('add',{
//	    title: '1',
//	    cache: true,
//	    content: '<iframe width="100%" height="100%" frameborder=0 scrolling=auto src="'+src+'"></iframe>',
//	    selected : i==0?true:false,
//	    closable:false,    
//	    tools:[{    
//	        iconCls:'icon-mini-refresh',    
//	        handler:function(){    
//	       		var tab = $('#tt').tabs('getTab', i);
//	       		setTimeout(function(){
//		       		tab.panel('refresh');
//	       		},0);
//	        }    
//	    }]
//	});
});

$(function() {
	$("#cc").layout({fit:true});
	ScreenSwitcher.init();
	$('#cc').layout('add', {
		iconCls: 'icon-search',
		region : ScreenSwitcher.getCurPos(),
		collapsible: false,
		width : ($('#cc').width())*0.48,
		title : '查看图片',
		split : true,
		tools:[{
					iconCls:'icon-reset',
					handler:function(){
						ScreenSwitcher.doSwitch(function(){
							var tab = $('#base_ifo').tabs('getSelected');
							var title = tab.panel('options').title;
							if(!(title=='共同承租人'||title=='担保人')) {
					    		title = '承租人';
					    	}
							var src = _basePath+'/crm/Customer!toMgElectronicPhotoAlbum11.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title+'&HTSH='+HTSH+'&JCSH='+JCSH+'&YHBC='+YHBC;
							$('#_iframe1').attr('src', src);
						});
					}
				}],
		content: '<iframe  width="100%" id="_iframe1" height="100%" frameborder=0 scrolling=auto ></iframe>'
	});
	
	$.each(tabs1, function(i, n){
		var url=_basePath+n.url;
		if(n.title=="百度查询"){
			url=n.url;
		}
		$('#base_ifo').tabs('add',{    
		    title: n.title,
		    cache: true,
		    //href: _basePath+n.url,
		    content: '<iframe src="'+url+'" width="100%" height="100%" frameborder=0 scrolling=auto ></iframe>',
		    selected : i==0?true:false,
		    closable:false,    
		    tools:[{    
		        iconCls:'icon-mini-refresh',    
		        handler:function(){    
		       		var tab = $('#base_ifo').tabs('getTab', i);
		       		setTimeout(function(){
			       		tab.panel('refresh');
		       		},0);
		        }
		    }]
		}); 
	});
	
	$('#base_ifo').tabs({
		onSelect: function(title, index) {
			if($('#_iframe1').attr("src")==undefined){
		    	//var src = _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
		    	var src = _basePath+'/crm/Customer!toMgElectronicPhotoAlbum11.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title+'&YHBC='+YHBC+'&JFBG='+JFBG+'&HTSH='+HTSH+'&JCSH='+JCSH;
		    	if(title=='承租人'||title=='共同承租人'||title=='担保人') {
					$('#_iframe1').attr('src', src);
		    	}
	    	}
	    }
	});
});

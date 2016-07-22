$(function() {
	$("#cc").layout({fit:true});
	
	$('#cc').layout('add', {
		iconCls: 'icon-search',
		region : ScreenSwitcher.getCurPos(),
		collapsible: false,
		style:{
			border:0
		},
		width : 500,
		title : '查看资料',
		split : true,
		tools:[{
					iconCls:'icon-reset',
					handler:function(){
						ScreenSwitcher.doSwitch(function(){
							genRightTabs();
							var tab = $('#tt').tabs('getSelected');
							var title = tab.panel('options').title;
							if(!(title=='共同承租人'||title=='担保人')) {
					    		title = '承租人';
					    	}
							var src = _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
							$('#_iframe1').attr('src', src);
						});
					}
				}],
		content: '<div id="tt1" class="easyui-tabs" data-options="fit:true, border:0"></div>'
	});
	genRightTabs();
	function genRightTabs() {
		$('#tt1').tabs('add',{    
		    title: '资料',
		    cache: true,
		    content: '<iframe width="100%" id="_iframe1" height="100%" frameborder=0 scrolling=auto ></iframe>',
		    selected : true,
		    closable:false,    
		    tools:[{    
		        iconCls:'icon-mini-refresh',    
		        handler:function(){    
		       		var pp = $('#tt1').tabs('getSelected');    
					pp.panel('refresh');
		        }    
		    }]
		}); 
//		$('#tt1').tabs('add',{    
//		    title: '话术',
//		    cache: true,
//		    content: '<iframe width="100%" height="100%" frameborder=0 scrolling=auto src="'+_basePath+'/leeds/talkSkill/CallVerify!loadTalkSkillResults.action?PROJECT_ID='+PROJECT_ID+'"></iframe>',
////		    href: _basePath+n.url,
//		    selected : false,
//		    closable:false,    
//		    tools:[{    
//		        iconCls:'icon-mini-refresh',    
//		        handler:function(){    
//		       		var pp = $('#tt1').tabs('getSelected');    
//					pp.panel('refresh');
//					
//		        }    
//		    }]
//		}); 	
	}
	
	$.each(tabs1, function(i, n){
		
		$('#tt').tabs('add',{    
		    title: n.title,
		    cache: true,
		    href: _basePath+n.url,
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
//	$('#tt').tabs({
//		onSelect: function(title, index) {
//	    	var src = _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
//	    	if(title=='承租人'||title=='共同承租人'||title=='担保人') {
//				$('#_iframe1').attr('src', src);
//	    	}
//	    }
//	});
});
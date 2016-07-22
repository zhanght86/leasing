$(function() {
	$("#cc").layout({fit:true});
	
	$('#cc').layout('add', {
		iconCls: 'icon-search',
		region : ScreenSwitcher.getCurPos(),
		collapsible: false,
//		style:{
//			border:0
//		},
		width : 600,
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
							//var src = _basePath+'/leeds/cust_info_input/CustInfoInput.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
							var src = _basePath+'/crm/Customer!toMgElectronicPhotoAlbum11.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
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
//		    href: _basePath+n.url,
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
		
//		if(cust_type == 'NP'){
//			$('#tt1').tabs('add',{    
//			    title: '身份证认证',
//			    cache: true,
//			    content: '<iframe width="100%" height="100%" id="_iframeSF" frameborder=0 scrolling=auto src="'+_basePath+'/leeds/idCard/IdCard.action?CUST_ID='+CLIENT_ID+'&TASKBZ='+taskBz+'" ></iframe>',
//			    selected : false,
//			    closable:false,    
//			    tools:[{    
//			        iconCls:'icon-mini-refresh',    
//			        handler:function(){    
//			       		var pp = $('#tt1').tabs('getSelected');    
//						pp.panel('refresh');
//			        }    
//			    }]
//			});
//		}else{
//			$('#tt1').tabs('add',{    
//			    title: '身份证认证',
//			    cache: true,
//			    content: '<iframe width="100%" height="100%" id="_iframeSF" frameborder=0 scrolling=auto src="'+_basePath+'/leeds/idCard/IdCard!toLpCardView.action?CUST_ID='+CLIENT_ID+'"></iframe>',
//			    selected : false,
//			    closable:false,    
//			    tools:[{    
//			        iconCls:'icon-mini-refresh',    
//			        handler:function(){    
//			       		var pp = $('#tt1').tabs('getSelected');    
//						pp.panel('refresh');
//			        }    
//			    }]
//			});
//		}
//		
//		$('#tt1').tabs('add',{    
//		    title: '征信报告',
//		    cache: true,
//		    content: '<iframe width="100%" height="100%" id="_iframeZX" frameborder=0 scrolling=auto src="'+_basePath+'/creditReports/CreditReports!toPage.action?PROJECT_ID='+PROJECT_ID+'" ></iframe>',
//		    selected : false,
//		    closable:false,    
//		    tools:[{    
//		        iconCls:'icon-mini-refresh',    
//		        handler:function(){    
//		       		var pp = $('#tt1').tabs('getSelected');    
//					pp.panel('refresh');
//		        }    
//		    }]
//		});
	}
	
	$.each(tabs1, function(i, n){
		$('#tt').tabs('add',{    
		    title: n.title,
		    cache: true,
		    content: '<iframe width="100%" height="100%" frameborder=0 scrolling=auto src="'+_basePath+n.url+'"></iframe>',
//		    href: _basePath+n.url,
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
	$('#tt').tabs({
		onSelect: function(title, index) {
	    	//var src = _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg1.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
			var src = _basePath+'/crm/Customer!toMgElectronicPhotoAlbum11.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title+'&YHBC='+JDMC;
    		if(title=='承租人'||title=='共同承租人'||title=='担保人'||title=='家访') {
				$('#_iframe1').attr('src', src);
	    	}
	    }
	});
	
	$('#tt1').tabs({
		onSelect: function(title, index) {
			if(title=='身份证认证'){
				$('#tt').tabs({
					onSelect: function(title1, index) {
						var srt="";
			    		if(title1 == '承租人'){
			    			if(cust_type == 'NP'){
			    				srt=_basePath+'/leeds/idCard/IdCard.action?CUST_ID='+CLIENT_ID+'&TASKBZ='+taskBz;
			    				$('#_iframeSF').attr('src', srt);
			    			}else{
			    				srt=_basePath+'/leeds/idCard/IdCard!toLpCardView.action?CUST_ID='+CLIENT_ID;
			    				$('#_iframeSF').content(srt);
			    			}
			    		}else if(title1 == '共同承租人'){
			    			if(GTCZR_CUST_TYPE == 'NP'){
			    				srt=_basePath+'/leeds/idCard/IdCard.action?CUST_ID='+GTCZR_CLIENT_ID+'&TASKBZ='+taskBz;
			    			}
			    			$('#_iframeSF').attr('src', srt);
			    		}else if(title1 == '担保人'){
			    			if(DBR_CUST_TYPE == 'NP'){
			    				srt=_basePath+'/leeds/idCard/IdCard.action?CUST_ID='+DBR_CLIENT_ID+'&TASKBZ='+taskBz;
			    				$('#_iframeSF').attr('src', srt);
			    			}else{
			    				srt=_basePath+'/leeds/idCard/IdCard!toLpCardView.action?CUST_ID='+DBR_CLIENT_ID;
			    				$('#_iframeSF').content(srt);
			    			}
			    		}else{
			    			;
			    		}
			    		
				    }
				});
			}
	    }
	});
});
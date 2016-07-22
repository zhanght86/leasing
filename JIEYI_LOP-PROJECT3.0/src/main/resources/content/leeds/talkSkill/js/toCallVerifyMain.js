$(function() {
	$("#cc").layout({fit:true});
	$('#cc').layout('add', {
		iconCls: 'icon-search',
		region : ScreenSwitcher.getCurPos(),
		collapsible: false,
		style:{
			border:0
		},
		width : 800,
		title : '查看资料',
		split : true,
		tools:[{
					iconCls:'icon-reset',
					handler:function(){
						ScreenSwitcher.doSwitch(function(){
							genRightTabs();
							var tab = $('#tt2').tabs('getSelected');
							var title = tab.panel('options').title;
							var src = _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
							$('#_iframe1').attr('src', src);	
						});
					}
				}],
		content: '<div id="tt1" class="easyui-tabs" data-options="fit:true, border:0"></div>'
	});
	function genRightTabs() {
		//上传图片
		$.each(tabs1, function(i, n){
			$('#tt1').tabs('add',{    
				 title: n.title,
			    cache: true,
			    //href: _basePath+n.url,
			    content: '<iframe src="'+_basePath+n.url+'" width="100%" height="100%" frameborder=0 scrolling=auto ></iframe>',
			    selected : i==0?true:false,
			    closable:false,   
			    tools:[{    
				    iconCls:'icon-mini-refresh',    
			        handler:function(){    
			       		var tab = $('#tt2').tabs('getTab', i);
			       		setTimeout(function(){
				       		tab.panel('refresh');
			       		},0);
			        }
			    }]
			});
		});
		$('#tt1').tabs('add',{    
		    title: '附件资料',
		    cache: true,
		    content: '<iframe id="_iframe1" width="100%" height="100%" frameborder=0 scrolling=auto></iframe>',
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
	}
	genRightTabs();
	$('.recordList').each(function(){
		var that = $(this);
		that.edatagrid({
			url : _basePath + '/leeds/talkSkill/CallVerify!loadTpls.action',
			destroyUrl : '',
			singleSelect : true,
			fit:true,
			
//			scrollbarSize:0,
			fitColumns : true,
			rownumbers : true,
			striped : true,
			nowrap:false,
			idField : 'TALK_SKILL_ID',
			queryParams : {
				PROJECT_ID : PROJECT_ID,
				TYPE: that.attr('tplType')
			},
			columns : [[{
						field : 'TITLE',
						title : '标题',
						width : 80,
						align : 'center'
					}, {
						field : 'CONTENT',
						title : '题目',
						width : 200,
						align : 'center'
					}, {
						field : 'TSC_MEMO',
						title : '答复备注',
						width : 150,
						align : 'center',
						editor : {
							type : 'textarea'
						}
					}, {
						field : 'IS_CONSIST',
						title : '-',
						width : 35,
						align : 'center',
						formatter: function(value,row,index) {
							var isCheck = value==1 ? 'checked' : '';
							return '<input type="checkbox" '+isCheck+' name="ck'+index+'_'+that.attr('tplType')+'"/>';
						}
					}]],
			onSelect: function(rowIndex, rowData) {
				that.edatagrid('editRow', rowIndex);
			},
			onDblClickRow: function(rowIndex, rowData) {
				that.edatagrid('saveRow', rowIndex);
			},
			onBeforeSave : function(index) {
				var IS_CONSIST = 0;
				that.ck = $('input[name=ck'+index+'_'+that.attr('tplType')+']').attr('checked');
				if(that.ck)
					IS_CONSIST = 1;
				that.edatagrid('options').updateUrl = _basePath
						+ '/leeds/talkSkill/CallVerify!saveConsist.action'
						+ '?PROJECT_ID='+PROJECT_ID 
						+ '&IS_CONSIST='+IS_CONSIST;
			},
			onSave : function(index, row) {
				$('input[name=ck'+index+'_'+that.attr('tplType')+']').attr('checked', that.ck?true:false);
				row.TSC_ID = row.data;
				leeds.msg('保存成功！');
			}
		});
	});
	$('#tt2').tabs({
		onSelect: function(title, index) {
	    	var src = _basePath+'/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+title;
			$('#_iframe1').attr('src', src);
	    }
	});
});

function save() {
	$('#form1').form('submit', {
		url : _basePath + '/leeds/talkSkill/CallVerify!saveConsist.action',
		onSubmit : function(param) {
			
		},
		success : function(data) {
			
		}
	});
}
$(function() {
	$('.recordList2').each(function(){
		var that = $(this);
		that.datagrid({
			title: that.attr('tplTitle'),
			url : _basePath + '/leeds/talkSkill/CallVerify!loadTpls.action',
//			width: ($('#mainDiv').width()-10)*0.4,
			singleSelect : true,
//			scrollbarSize:0,
			fitColumns : true,
			rownumbers : true,
			striped : true,
			fit: true,
			nowrap:false,
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
						align : 'center'
					}, {
						field : 'IS_CONSIST',
						title : '-',
						width : 50,
						align : 'center',
						formatter: function(value,row,index) {
							return value==1 ? '<img src="'+_basePath+'/img/006.ico" title="一致"/>' : '<img src="'+_basePath+'/img/007.ico" title="不一致"/>';
						}
					}]]
		});
		
	});
});

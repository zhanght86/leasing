/**
 * 客户巡视功能 添加交往巡视记录js
 * @author 韩晓龙
 */

/**
 * 保存功能
 */
function save(){
	$.messager.confirm("添加","确定要保存该交往记录吗?",function(r){
		if(r){
			$('#fm_record').form('submit',{
		        url: _basePath + "/custTour/CustTour!doAddContactTour.action",
		        onSubmit: function(){
		            return $(this).form('validate');
		        },
		        success : function(date) {
		        	date = $.parseJSON(date);
					if (date.flag) {
						$.messager.confirm("提示",date.msg,function(r){
							top.closeTab("添加交往巡视记录");
						});
					} else {
						$.messager.confirm("提示",date.msg,function(r){
							top.closeTab("添加交往巡视记录");
						});
					}
				},
				error : function(e) {
					$.messager.alert(e.message);
				}
		    });
		}
	});
}

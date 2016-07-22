/**
 * 客户巡视功能 查看理赔巡视记录js
 * @author 韩晓龙
 */

function setOperation(val,row,index) {
	return "<a href='#' style='color:blue'  onclick=delItem('" + val + "','" + row.PROJ_ID + "')>删除</a>";
}

/**
 * 删除功能
 */
function delItem(ID,PROJ_ID){
	$.messager.confirm("删除","确定要删除该巡视记录吗?",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/custTour/CustTour!doDeleteClaimsTour.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(date){
					if (date.flag) {
						$.messager.confirm("提示",date.msg,function(r){
							refresh(PROJ_ID);
						});
					} else {
						$.messager.confirm("提示",date.msg,function(r){
							refresh(PROJ_ID);
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
/**
 * 刷新
 */
function refresh(PROJ_ID){
	top.addTab("查看理赔巡视记录", _basePath + "/custTour/CustTour!toShowDetailClaimsRecord.action?PROJ_ID=" + PROJ_ID);
}
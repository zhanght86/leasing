function se(){
	var  NAME= $("#pageForm select[name='NAME']").find("option:selected").val();
	var  CONTENT= $("#pageForm input[name='CONTENT']").val();
    $('#pageTable').datagrid('load', {"NAME":NAME,"CONTENT":CONTENT});
    
}
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
function setOperation(val,row,index) {
	return "<a href='#'  onclick='selPolicy(" + val + ")'>查看</a>|<a href='#'  onclick='updPolicy(" + val + ")'>批改</a>|<a href='#'  onclick='delPolicy(" + val + ")'>删除</a>";
}
function selPolicy(ID)
{
		top.addTab("查看保单", _basePath + "/insure/Insurance!selPolicy.action?ID=" + ID);
}
function updPolicy(ID)
{
		top.addTab("批改保单", _basePath + "/insure/Insurance!updPolicy.action?ID=" + ID);
}
function delPolicy(ID)
{
	$.messager.confirm("删除","确定要删除该保单吗?",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/insure/Insurance!delPolicy.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					if(data.flag){
						$.messager.alert("结果","删除成功！");
						//刷新页面
						window.location.href=_basePath+"/insure/Insurance!manager.action";
					}else
					{
						$.messager.alert("结果","删除失败！");
					}
				}
			});
		}
	});
}
function showInsureCompany(){
	top.closeTab("保单管理");
	top.addTab("保单管理", _basePath + "/insure/Insurance!manager.action");
}
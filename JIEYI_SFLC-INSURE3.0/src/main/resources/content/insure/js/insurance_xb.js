function setOperation(val,row,index) {
	return "<a href='#'  onclick='newPolicy(" + val + ")'>重新生成保单</a>";
}
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
function newPolicy(ID)
{
	top.addTab("重新生成保单", _basePath + "/insure/Insurance!reInsuListForEqu.action?ID=" + ID+"&LX=CXSC");
}
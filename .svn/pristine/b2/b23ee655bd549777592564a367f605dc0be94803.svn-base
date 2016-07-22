/**
 * 厂家审批意见页签
 * 供下载附件使用
 * hanxl
 */
//页面加载后
$(function(){
	var PROJECT_ID = $("#IneedID").attr("value");//项目id
	showdownload(PROJECT_ID);
});

//显示所有附件
function showdownload(PROJECT_ID){
	//先清空  拼上 label
	$("#myLabel").empty().append("<label>附件列表:</label>");
	//使用ajax 查询该政策下的所有的附件	
	var urltemp = _basePath + "/manufacturerApproval/ManufacturerApproval!selectAllFiles.action?PROJECT_ID=" + PROJECT_ID;
	//利用ajax	查询当前政策的所有附件
	jQuery.ajax({
		type: "POST",
	    dataType:"json",
	    url: urltemp,
	    //data: jsonData,
	    success: function(json){
			var tempVal = "";
			for(var i in json){
				tempVal = json[i].FILE_NAME;
				var temp = "<br/><a href='javascript:void(0)' class='easyui-linkbutton' onclick='downFile(" + json[i].ID + ")'>" + tempVal.substring(13, tempVal.length) + "</a>";//此处文件名进行了优化
				$("#myLabel").append(temp); 
			}
	}});
}

//下载附件
function downFile(fileId){
	//还必须用个form请求  - -！
	$('#fmwl').form('submit',{
      url: _basePath + "/manufacturerApproval/ManufacturerApproval!downloadAttachment.action?ID=" + fileId,
      onSubmit: function(){
      },
      success: function(result){
      	
      }
  });
}
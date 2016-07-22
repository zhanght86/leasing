/**
 * 山重建机厂商项目审批页面 js
 * 作者:韩晓龙
 */

/**
 * 导出Word
 */
function exportWord(PROJECT_ID,KH_TYPE){
	//发送导出请求
	//FILETYPE控制个人还是法人模板
	window.location.href=_basePath+"/manufacturerApproval/ManufacturerApproval!exportSZJJWord.action?PROJECT_ID=" + PROJECT_ID + "&FILETYPE=" + KH_TYPE;
}

/**
 * 保存功能
 */
function save(){
	//保存按钮置为不可用
	$('#savebtn').linkbutton('disable');
	
	var PROJECT_ID = $("#PROJECT_ID").attr("value");//项目id
	var PROJ_ID = $("#PROJ_ID").attr("value");//项目编号
	var MANUFACTURER = $("#MANUFACTURER").attr("value");//厂商
	var DLD = $("#DLD").attr("value");//供应商
	var ADVICE = $("#ADVICE").attr("value");//意见
	$.messager.confirm("添加","确定要保存该审批意见吗?",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/manufacturerApproval/ManufacturerApproval!saveAdvice.action",
				data : { "PROJECT_ID": PROJECT_ID,"PROJ_ID": PROJ_ID,"MANUFACTURER" : MANUFACTURER,"ADVICE" : ADVICE,"DLD" : DLD},
				dataType:'json',
				success:function(date){
					alert("保存成功");
					//关闭页面
					top.closeTab("山重建机厂商项目审批");
					freshPage(PROJECT_ID);
				}
			});
		}
	});
}
//刷新页面
function freshPage(PROJECT_ID){
	top.addTab("项目审批", _basePath + "/manufacturerApproval/ManufacturerApproval.action");
}

/////////////////////////////////////////////////////////////////////////////////////////////////
$(function(){
	var PROJECT_ID = $("#PROJECT_ID").attr("value");//项目id
	showdownload(PROJECT_ID);
});
//新增上传文件
var tempurl = "";
var _PROJECT_ID = "";
//显示上传附件窗口
function showUpload(PROJECT_ID){
	_PROJECT_ID = PROJECT_ID;
	$('#dlg3').dialog('open').dialog('setTitle','上传附件');
	document.getElementById("upload_file").value = "";//先清空上传文件
	tempurl = _basePath + "/manufacturerApproval/ManufacturerApproval!uploadAttachment.action?PROJECT_ID=" + PROJECT_ID;
}
//上传附件
function upload(){
	$('#fm3').form('submit',{
      url: tempurl,
      onSubmit: function(){
		if(document.getElementById("upload_file").value == ""){
			alert("没有选择附件哟！");
			return false;
		}
      },
      success: function(result){
          var result = eval('('+result+')');
          if (result.flag==false){
              $.messager.show({
                  title: '出错',
                  msg: result.msg
              });
          } else {
          	alert("上传成功");
          	$('#dlg3').dialog('close');
          	//修改文件列表
          	showdownload(_PROJECT_ID);
          }
      }
  });
}
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
				var temp = "<br/><a href='javascript:void(0)' text-decoration='underline' onclick='delFile(" + json[i].ID + ")'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' class='easyui-linkbutton' onclick='downFile(" + json[i].ID + ")'>" + tempVal.substring(13, tempVal.length) + "</a>";//此处文件名进行了优化
				$("#myLabel").append(temp); 
			}
	}});
}

//删除附件
function delFile(fileId){
	//利用ajax	查询当前政策的所有附件
	jQuery.ajax({
		type: "POST",
	    dataType:"json",
	    url: _basePath + "/manufacturerApproval/ManufacturerApproval!deleteOneFile.action?ID=" + fileId,
	    //data: jsonData,
	    success: function(data){
		    if (data.flag==false){
		    	alert("删除失败");
		    } else {
		    	alert("删除成功");
		    }
	    	var tempID = $("#PROJECT_ID").attr("value");//项目id
	    	//修改文件列表
	    	showdownload(tempID);
		}
	});
}

//下载附件
function downFile(fileId){
	//还必须用个form请求  - -！
	$('#fm3').form('submit',{
      url: _basePath + "/manufacturerApproval/ManufacturerApproval!downloadAttachment.action?ID=" + fileId,
      onSubmit: function(){
      },
      success: function(result){
      	
      }
  });
}

///////////////////////////////////////////////////////////////////////////////////////////

function test(){
	
	alert("projectContraceControl.js");
}

/**
 * 增加标签页
 * @param name
 * @param url
 * @return
 */
function addTab(name,url){
	//alert("addTab");
	//alert(url);
	url=url.replace("undefined","");
	if(url.lastIndexOf("?")==-1){
		url=url+"?_datetime="+new Date().getTime();
	}else{
		url=url+"&_datetime="+new Date().getTime();
	}
	if($('#tabBox').tabs('exists',name)){
		//alert(1);
		$('#tabBox').tabs('select',name);
		$('#tabBox').tabs('update',{
			tab:$("#tabBox").tabs("getSelected"),
			options:{
				content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
			}
		});
	}else{
		//alert(2);
		$('#tabBox').tabs('add',{
			title: name,
			content: '<iframe src="'+url+'" width="100%" height="100%" style="padding:0px;margin-bottom:-5px;" frameborder="0" border="0"></iframe>',
			closable: true,
			tools:[{
		        iconCls:'icon-mini-refresh',
		        handler:function(){
					$('#tabBox').tabs('update',{
						tab:$("#tabBox").tabs("getSelected"),
						options:{
							content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
						}
					});
		        }
		    }]
		});
	}
}

//Window.prototype.top = new Window();

$(document).ready(function(){
	$("#dialogProduct").dialog('close');
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	var PRO_CODE=$("input[name='PRO_CODE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var STATUS=$("select[name='STATUS']").val();
	var STATUS2=$("#STATUS2").val();//hxl
	$("#pageTable").datagrid({
		url:_basePath+"/projectContraceControl/ProjectContraceControl!pageAjaxProjectControl.action",
		pagination:false,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		//pageSize:20,
		fitColumns:true,
		toolbar:'#pageForm',
		queryParams:{"PLATFORM_TYPE":PLATFORM_TYPE,"PRO_CODE":PRO_CODE,"CUST_NAME":CUST_NAME,"STATUS":STATUS,"STATUS2":STATUS2},
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
			//alert(data);
            if (data.rows.length > 0) {
            	$('#pageTable').datagrid('selectRow', 0);
            	$("#PROJECT_ID_CONTRACT").val(data.rows[0].ID);
            	$("#PLATFORM_TYPE_CONTRACT").val(data.rows[0].PLATFORM_TYPE);
            	$("#STATUS_CONTRACT").val(data.rows[0].STATUS);
            }
        },
		onClickRow:function(index,data){
			$("#PROJECT_ID_CONTRACT").val(data.ID);
			$("#PLATFORM_TYPE_CONTRACT").val(data.PLATFORM_TYPE);
        	$("#STATUS_CONTRACT").val(data.STATUS);
		},
		 
		 columns:[[{field:'aaa',title:'操作',width:70,align:'center',formatter:function(value,row,rowIndex){
					 var STATUS=row.STATUS;
					 var LCNAME=row.LCNAME;
					 var rehtml="";
					 rehtml = rehtml+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sechemSHow(" + JSON.stringify(row) + ")'>查看 | </a>"
					 rehtml = rehtml+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updloadFile(" + JSON.stringify(row.ID) + ")'>上传附件</a>"
					 return rehtml;
			            }},
				    {field:'STATUS_NAME',title:'状态',width:70,align:'center'},
				    {field:'PLATFORM_NAME',title:'业务类型',width:120,align:'center'},
				    {field:'LEASE_CODE',title:'融资租赁合同号',width:150,align:'center'},
			      	{field:'ID',hidden:true,align:'center'},
			      	{field:'CUST_TYPE',hidden:true,align:'center'},
			      	{field:'CUST_ID',hidden:true,align:'center'},
			      	{field:'STATUS',hidden:true},
			      	{field:'PRO_CODE',title:'项目编号',width:130,align:'center'},
//			      	{field:'LEASE_MODEL',title:'租赁模式',width:110},
			      	{field:'PRO_NAME',hidden:true,align:'center'},
			      	{field:'CUST_NAME',align:'center',title:'客户名称',width:150},
			      	//,formatter:function(value,rowData,rowIndex){ return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+rowData.CUST_NAME+"</a>";}
			        {field:'CUST_TYPE_NAME',title:'客户类型',width:100,align:'center'},
			      	{field:'CLERK_NAME',title:'客户经理',width:130,align:'center'},
			      	{field:'CREATE_TIME',title:'创建时间',width:100,align:'center'}
			     ]]
	});
});


function sechemSHow(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"查看",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function updloadFile(id){
	alert("id="+id);
	$("#dialogDivUpload").dialog({
		title:"上传附件",
		       buttons: [{
			 	text:'上传',
				iconCls:'icon-up',
				handler:function(){
				$("#uploadFileForm").attr("action",_basePath+"/projectContraceControl/ProjectContraceControl!upLoadFileById.action");
				$("#uploadFileForm").submit();
			}},{
			   text:'关闭',
			   iconCls:'icon-cancel',
		       handler:function(){
			      $("#dialogDivUpload").dialog('close');
			   }}]
	});
	$("#dialogDivUpload").dialog("open");
}

function checkFileType(fileType){
	var fileTypes=[".rm",".rmvb",".flv",".avi",".mp4",".wmv",".mkv"];
	for(var i=0;i<fileTypes.length;i++){
		if(fileType==fileTypes[i]){
			return true;
		}
	}
	return false;
}

function getUploadFileContent(file){
	var maxFileSize=200*1024*1024;
	var fileType=file.value.substr(file.value.lastIndexOf("."));
	if(!checkFileType(fileType)){
		$.messager.alert("提示","请选择支持的格式!");
		return;
	}
	
	var l=$("#oldUploadFileList").children("input[type='file']").length;
	file.id="uploadFIle"+l;
	file.name="uploadFIle"+l;
	var obj_file = document.getElementById(file.id);
	filesize = obj_file.files[0].size;
//	checkfile(file.id);
	var fileSizeContext="";
	var roundFileSize=Math.round((filesize/1024/1024)*100)/100;
	if(roundFileSize>maxFileSize){
		$.messager.alert("提示","上传文件过大，请重新选择!");
		return;
	}
	if(0==roundFileSize){
		roundFileSize=Math.round((filesize/1024)*100)/100;
		fileSizeContext=roundFileSize+"KB";
	}else{
		fileSizeContext=roundFileSize+"MB";
	}
	$("#olduploadFileListName").append("文件名称："+file.value+"&nbsp;&nbsp;&nbsp;&nbsp;文件大小："+fileSizeContext+"</br>");
	$(file).clone(true).appendTo("#oldUploadFileList");
	//$("#oldUploadFileList").append(file);
	$("#olduploadFileListName").show();
}


/*获取文件大小（废弃）*/
function checkfile(id){
	  var  browserCfg = {};
	  var ua = window.navigator.userAgent;
	  alert(ua);
	  if (ua.indexOf("MSIE")>=1){
	   browserCfg.ie = true;
	  }else if(ua.indexOf("Firefox")>=1){
	   browserCfg.firefox = true;
	  }else if(ua.indexOf("Chrome")>=1){
	   browserCfg.chrome = true;
	  }
	   try {
		var obj_file = document.getElementById(id);
		if (obj_file.value == "") {
			alert("请先选择上传文件");
			return;
		}
		var filesize = 0;
		if (browserCfg.firefox || browserCfg.chrome) {
			filesize = obj_file.files[0].size;
			alert(filesize);
		} else if (browserCfg.ie) {
			var obj_img = document.getElementById('tempimg');
			obj_img.dynsrc = obj_file.value;
			filesize = obj_img.fileSize;
		} else {
			alert(tipMsg);
			return;
		}
		if (filesize == -1) {
			alert(tipMsg);
			return;
		} else if (filesize > maxsize) {
			alert(errMsg);
			return;
		} else {
			alert("文件大小符合要求");
			return;
		}
	} catch (e) {
		alert(e);
	}
}



function saveAndUpload(){
//	alert($("#PROJECT_ID_CONTRACT").val());
	var files={};
	$("#oldUploadFileList").children("input[type='file']").each(function(index){
			files[index]=this.id;
		});
	jQuery.ajaxFileUpload({
	    url: _basePath+"/projectContraceControl/ProjectContraceControl!saveFiles.action?_dateTime="+new Date(),//?param=+getFromData("#fromDate2"),
	    type: "post",
		secureuri: false,
	    fileElementId: files,
	    dataType: "json",
	    success: function(json){
	    	jQuery.ajax({
		        url: _basePath+"/projectContraceControl/ProjectContraceControl!addProjectControlFileContext.action?_dateTime="+new Date(),
		        data: "PROJECT_ID="+$("#PROJECT_ID_CONTRACT").val()+"&list="+JSON.stringify(json.data),
		        type: "post",
				dataType: "json",
		        success: function(json){
	                if(json){
						$.messager.alert('提示','保存成功');
						queryProjectControlFileContext();
					}else{
						$.messager.alert('提示','保存失败，请与管理员联系！','warning');
					}
		        }
		    });
	    }});
}

function queryProjectControlFileContext(){
//	alert("queryProjectControlFileContext");
//	$("#showUploadFileList")
	 $("#showUploadFileList").empty();
	jQuery.ajax({
		url:_basePath+"/projectContraceControl/ProjectContraceControl!queryProjectControlFileContext.action",
		data: "PROJECT_ID="+$("#PROJECT_ID_CONTRACT").val(),
        type: "post",
		dataType: "json",
		success: function(json){
//			alert(json.data[0]);
			for(var i=0; i<json.data.length; i++){
				var queryData=json.data[i];
				var ID = "";
				if(null!=queryData.ID){
					ID = queryData.ID;
				}
				var FILE_NAME = "";
				if(null!=queryData.FILE_NAME){
					FILE_NAME = queryData.FILE_NAME;
				}
				var FILE_PATH = "";
				if(null!=queryData.FILE_PATH){
					FILE_PATH = queryData.FILE_PATH;
				}
				var UPLOAD_DATE = "";
				if(null!=queryData.UPLOAD_DATE){
					UPLOAD_DATE = queryData.UPLOAD_DATE;
				}
				var FILE_SIZE = "";
				if(null!=queryData.FILE_SIZE){
					FILE_SIZE = queryData.FILE_SIZE;
				}
				 $("#showUploadFileList").append($("<tr>")
						    .append("<td align='center'><div id='path"+ID+"' style='width:auto'></div></td>")
						    .append("<td align='center'>"+FILE_NAME+"</td>")
						    .append("<td align='center'>"+UPLOAD_DATE+"</td>")
						    .append("<td align='center'>"+FILE_SIZE+"</td>")
							.append("</tr>")); 
			}
			//.append("<td align='center'>"+FILE_PATH+"</td>")
        }
	});
} 

/* 申请审批流程 */
function checkProjectContractByJbpm(){
	if(confirm("确定要申请审批吗？")){
		$.ajax({
			url:_basePath+"/contractProjectManage/ContractProjectManage!startCacelContractProjectByJbpm.action?_dateTime="+new Date(),
			data:'ID='+id,
			dataType:'json',
			type:'post',
			success:function(json){
//				alert("RST="+json.data.RST);
				$.messager.alert("提示","合同申请审批已发起,流程编号为："+json.data.RST);
				//location.reload();
			}
		});
	}
}

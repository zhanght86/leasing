$(function(){
	var PRO_CODE = $("#PRO_CODE").val();
	var RENTER_CODE = $("#RENTER_CODE").val();
	var RENTER_NAME = $("#RENTER_NAME").val();
	var CUSTOMER_TYPE = $("#CUSTOMER_TYPE").val();
	var TYPE_=$("#TYPE_").val();
	$("#pageTable12345").datagrid({
		url: _basePath+"/crm/Customer!toMgElectronicPhotoAlbumData1.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		pageSize:50,
		toolbar:'#pageForm12345',
		onLoadSuccess : function(){
			$(".startBpmBtn").linkbutton('enable');			
		},
		queryParams :{PRO_CODE:PRO_CODE,RENTER_CODE:RENTER_CODE,RENTER_NAME:RENTER_NAME,CUSTOMER_TYPE:CUSTOMER_TYPE,TYPE_:TYPE_},
        columns:[[
			{field:'DZDA_TYPE',align:'center',width:30,title:'资料类型'},
			//{field:'CODE_TYPE',align:'center',width:15,title:'是否必选'},
			{field:'CODE_TYPE_FLAG',hidden:true},
			{field:'PICTURE',hidden:true},
            {field:'CATALOG_ID',align:'center',width:20,title:'文件列表',formatter:function setHouserDel(value,rowData) {
				if(rowData.PICTURE == 'YES'){
					return "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(rowData) + ")'><font color='blue'>查看（已上传）</font></a>";
				}else{
					return "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(rowData) + ")'><font color='blue'>查看（</font><font color='red'>未上传</font><font color='blue'>）</font></a>";
				}
			}
		},
			{field:'CATALOG_TYPE',align:'center',title: '操作',width:20,formatter:function setHouserDel(value,rowData,rowIndex) {
					return "<a href='#' onclick='uploadFile(" + JSON.stringify(rowData)+ ',' + rowIndex + ")'><font color='blue'>上传</font></a>";
				}
			}
        ]]
	});
});

function uploadFile(row,index){
	var divId = 'uploadFileDiv';
	var formId = 'uploadFileForm';
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:'上传项目资料',
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			 	$('#btnbc').linkbutton('disable');
			 	var DZDA_TYPE = row.DZDA_TYPE;
				var RENTER_NAME = row.RENTER_NAME;
				var RENTER_CODE = row.RENTER_CODE;
				var CATALOG_TYPE = row.CATALOG_TYPE;
				var PRO_CODE = row.PRO_CODE;
				var FILE_TYPE = $("#FILE_TYPE1").combobox("getValue");
				var REMARK = $("#uploadFileForm").find("#REMARK").val();
//				$('#'+formId).form('submit', {
//					url:_basePath+'/crm/Customer!doAddXMZL.action?RENTER_NAME='+encodeURI(RENTER_NAME)+"&RENTER_CODE="+encodeURI(RENTER_CODE)+"&FILE_TYPE="+encodeURI(FILE_TYPE)+"&CATALOG_TYPE="+encodeURI(CATALOG_TYPE)+"&PRO_CODE="+encodeURI(PRO_CODE)+"&_datetime="+new Date().getTime(),
//					success:function(json){
//						json = jQuery.parseJSON(json);
//						if(json.flag){
////							row.rec = "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(row) + ")'>查看（已上传）</a>";
////           				$('#pageTable12345').datagrid('refreshRow', index);
//							alert("保存成功");
//							$("#"+divId).dialog('close');
//							conditionsSelect();
//						}else{
//							alert(json.msg);
//						}
//					}
//				});
				var data_={'RENTER_NAME':RENTER_NAME,'CATALOG_TYPE':CATALOG_TYPE,'FILE_TYPE':FILE_TYPE,'PRO_CODE':PRO_CODE,'RENTER_CODE':RENTER_CODE,'REMARK':REMARK};
				//console.info(data_);
				$("#uploadify").uploadify("settings", "formData", data_); 
				$('#uploadify').uploadify('upload','*');
            }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("[name='upload']").each(function(){
		$(this).val('');
	});
	$('#FILE_TYPE1').combobox('select', row.DZDA_TYPE);
	$("#uploadFileForm").find("#REMARK").attr("value","");
}

function conditionsSelect(){
	var PRO_CODE = $("#PRO_CODE").val();
	var RENTER_CODE = $("#RENTER_CODE").val();
	var RENTER_NAME = $("#RENTER_NAME").val();
	var CUSTOMER_TYPE = $("#CUSTOMER_TYPE").val();
	var FILE_TYPE = $("#FILE_TYPE").combobox("getValue");
	var TYPE_=$("#TYPE_").val();
	$('#pageTable12345').datagrid("load",{PRO_CODE:PRO_CODE,FILE_TYPE:FILE_TYPE,RENTER_CODE:RENTER_CODE,RENTER_NAME:RENTER_NAME,CUSTOMER_TYPE:CUSTOMER_TYPE,TYPE_:TYPE_});
}

function clearSelect() {
	$("#FILE_TYPE").combobox('select', '');
	$("#FILE_NAME").val('');
	$("#CREATE_DATE").datebox('setValue', '');
}

function deleteDZDA(row) {
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/crm/Customer!doDeleteDZDA.action?ORIGINAL_PATH="+encodeURI(row.ORIGINAL_PATH) + "&ID=" + encodeURI(row.ID)+"&_datetime="+new Date().getTime(),
				dataType : "json",
				success : function(json){
					if(json.flag){
						conditionsSelect();
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}

function updateDZDA(row){
var divId = 'uploadFileDiv';
var formId = 'uploadFileForm';
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:'上传项目资料',
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			 	$('#btnbc').linkbutton('disable');
				var RENTER_NAME = $("#RENTER_NAME").val();
				var RENTER_CODE = $("#RENTER_CODE").val();
				var REMARK = $("#REMARK").val();
				var FILE_TYPE = $("#FILE_TYPE1").combobox("getValue");
				$('#'+formId).form('submit', {
					url:_basePath+'/crm/Customer!updateDZDA.action?RENTER_NAME='+encodeURI(RENTER_NAME)+"&RENTER_CODE="+encodeURI(RENTER_CODE)+"&FILE_TYPE="+encodeURI(FILE_TYPE)+"&ID="+row.ID+"&_datetime="+new Date().getTime()+"&REMARK="+encodeURI(REMARK),
					success:function(json){
						json = jQuery.parseJSON(json);
						if(json.flag){
							alert("保存成功");
							$("#"+divId).dialog('close');
							conditionsSelect();
						}else{
							alert(json.msg);
						}
					}
				});
            }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
                $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("[name='upload']").each(function(){
		$(this).val('');
	});
	$('#FILE_TYPE1').combobox('select', row.DZDA_TYPE);
	$("#REMARK").val(row.REMARK);
}

function downloadFile(row){
	window.location.href=_basePath+"/crm/Customer!downloadPictrue.action?FILE_PATH="+encodeURI(row.ORIGINAL_PATH)+"&_datetime="+new Date().getTime(); 
}

function selectUpLoadDialog(row) {
	$('#upShowDiv1').empty();
	$('#upShowDiv1').load(_basePath+"/crm/Customer!selectFileList.action?CATALOG_ID="+encodeURI(row.CATALOG_ID)+"&_datetime="+new Date().getTime());
	$("#upShowDiv").show();
	$('#upShowDiv').dialog();
}

function close(divId,formId) {
	$("#"+divId).hide();
	$("#"+divId).dialog('close');
	$("#"+formId).form('clear');
}

function downFile(ID){
	var path = encodeURI($("#"+ID).val()) ;
	/*var _datetime = new Date().getTime() ;
	jQuery.ajax({
				url : _basePath+"/crm/Customer!downFile.action",
					dataType : "json",
					data:{
						path:path,
						_datetime:_datetime
					},
					success : function(json){
					if(json ==undefined || json =='' || typeof(json)==undefined){
						$.messager.alert("提示信息","文件删除失败!") ;
					}	
					if(json.flag){						
						conditionsSelect();
					}else{
						$.messager.alert("提示信息","下载文件失败!") ;
					}	
				}
	});*/
	

	window.location.href=_basePath+"/crm/Customer!downFile.action?path="+encodeURIComponent(path)+"&_datetime="+new Date().getTime(); 
}

function showImg(ID){	
	top.addTab("图片查看",_basePath+"/crm/Customer!showImg.action?path="+encodeURIComponent(path)+"&_datetime="+new Date().getTime());
}

function deleteFile(ID){
	//alert(encodeURI($("#"+ID).val())) ;
	//return ;
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			var path = $("#"+ID).val();
			jQuery.ajax({
				url : _basePath+"/crm/Customer!deleteFile.action",
					dataType : "json",
					data:{
						path:encodeURI(path),
						_datetime:new Date().getTime()
					},
					success : function(json){
					if(json ==undefined || json =='' || typeof(json)==undefined){
						$.messager.alert("提示信息","文件删除失败!") ;
					}	
					if(json.flag){
						$("#upShowDiv").hide();
						$("#upShowDiv").dialog('close');
						conditionsSelect();
					}else{
						$.messager.alert("提示信息","文件删除失败!") ;
					}	
				}
			});
		}
	});
}
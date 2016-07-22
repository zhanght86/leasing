
$(function(){
	var RENTER_CODE = $("#RENTER_CODE").val();
	var RENTER_NAME = $("#RENTER_NAME").val();
	var CUSTOMER_TYPE = $("#CUSTOMER_TYPE").val();
	$("#pageTable2").datagrid({
		url: _basePath+"/crm/Customer!toMgElectronicPhotoAlbumData.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		toolbar:'#pageForm',
		queryParams :{RENTER_CODE:RENTER_CODE,RENTER_NAME:RENTER_NAME,CUSTOMER_TYPE:CUSTOMER_TYPE},
        columns:[[
			{field:'DZDA_TYPE',align:'center',width:30,title:'电子档案类型'},
            {field:'CATALOG_ID',align:'center',width:20,title:'文件列表',formatter:function setHouserDel(value,rowData) {
				if(rowData.PICTURE == 'YES'){
					return "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(value) + ")'>查看（已上传）</a>";
				}else{
					return "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(value) + ")'>查看（<font color='red'>未上传</font>）</a>";
				}
			}
		},
			{field:'CATALOG_TYPE',align:'center',title: '操作',width:20,formatter:function setHouserDel(value,rowData) {
					return "<a href='#' onclick='uploadFile(" + JSON.stringify(rowData) + ")'>上传</a>";
				}
			}
        ]]
	});
});
    	
function conditionsSelect(){ 
	var RENTER_CODE = $("#RENTER_CODE").val();
	var RENTER_NAME = $("#RENTER_NAME").val();
	var CUSTOMER_TYPE = $("#CUSTOMER_TYPE").val();
	var FILE_TYPE = $("#FILE_TYPE").combobox("getValue");
	$('#pageTable2').datagrid("load",{FILE_TYPE:FILE_TYPE,RENTER_CODE:RENTER_CODE,RENTER_NAME:RENTER_NAME,CUSTOMER_TYPE:CUSTOMER_TYPE});
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

//function setHouserDel(val,row) {
//			if(row.ID){
//				return "<a href='#' class='easyui-linkbutton' iconCls='icon-edit' plain='true' onclick='updateDZDA(" + JSON.stringify(row) + ")'>修改</a> |"+
//				       "<a href='#' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='downloadFile(" + JSON.stringify(row) + ")'>下载</a> |"+
//				       "<a href='#' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='deleteDZDA(" + JSON.stringify(row) + ")'>删除</a>";
//			}else{
//		return "<a href='#' onclick='uploadFile(" + JSON.stringify(row) + ")'>上传</a>";
//			}
//}

//function setHouserDel1(val,row) {
//	return "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(val) + ")'>查看</a>";
//}

function selectUpLoadDialog(val) {
	$('#upShowDiv1').empty();
	$('#upShowDiv1').load(_basePath+"/crm/Customer!selectFileList1.action?CATALOG_ID="+encodeURI(val)+"&_datetime="+new Date().getTime());
	$("#upShowDiv").show();
	$('#upShowDiv').dialog();
}

function uploadFile(row){
var divId = 'uploadFileDiv';
var formId = 'uploadFileForm';
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:'上传电子档案',
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			 	$('#btnbc').linkbutton('disable'); 
    			var RENTER_NAME = row.RENTER_NAME;
 				var RENTER_CODE = row.RENTER_CODE;
 				var CATALOG_TYPE = row.CATALOG_TYPE;
 				var PRO_CODE = row.PRO_CODE;
 				var FILE_TYPE = $("#FILE_TYPE1").combobox("getValue");
 				$('#'+formId).form('submit', {
 					url:_basePath+'/crm/Customer!updateDZDA.action?RENTER_NAME='+encodeURI(RENTER_NAME)+"&RENTER_CODE="+encodeURI(RENTER_CODE)+"&FILE_TYPE="+encodeURI(FILE_TYPE)+"&CATALOG_TYPE="+encodeURI(CATALOG_TYPE)+"&PRO_CODE="+encodeURI(PRO_CODE)+"&_datetime="+new Date().getTime(),
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
	$("#upload").val('');
	$('#FILE_TYPE1').combobox('select', row.DZDA_TYPE);
	$("#REMARK").val(row.REMARK);
}

//		function downloadFile(row){
//        	window.location.href=_basePath+"/crm/Customer!downloadPictrue.action?FILE_PATH="+encodeURI(row.ORIGINAL_PATH); 
//        }

function close(divId,formId) {
	$("#"+divId).hide();
	$("#"+divId).dialog('close');
	$("#"+formId).form('clear');
}

function downFile(ID){
	var path = $("#"+ID).val();
	window.location.href=_basePath+"/crm/Customer!downFile1.action?path="+encodeURI(path)+"&_datetime="+new Date().getTime(); 
}

function deleteFile(ID){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			var path = $("#"+ID).val();
			jQuery.ajax({
				url : _basePath+"/crm/Customer!deleteFile1.action?path="+encodeURI(path)+"&_datetime="+new Date().getTime(),
					dataType : "json",
					success : function(json){
					if(json.flag){
						$("#upShowDiv").hide();
						$("#upShowDiv").dialog('close');
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}
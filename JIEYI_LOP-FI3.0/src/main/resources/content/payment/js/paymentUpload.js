$(function(){
	var PAYMENT_ID = $("#PAYMENT_ID").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	var TYPE = $("#TYPE").combobox("getValue");
	$("#pageTable12345").datagrid({
		url: _basePath+"/payment/PaymentApply!toGetUpload.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		pageSize:50,
		toolbar:'#pageForm12345',
		onLoadSuccess : function(){
			$(".startBpmBtn").linkbutton('enable');			
		},
		queryParams :{PAYMENT_ID:PAYMENT_ID,PROJECT_ID:PROJECT_ID,TYPE:TYPE},
        columns:[[
			{field:'TPM_TYPE',align:'center',width:30,title:'资料类型'},
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
function clearSelect() {
	$("#TYPE").combobox('select', '');
	$("#NAME").val('');
	$("#CREATE_DATE").datebox('setValue', '');
}

function conditionsSelect(){
	var PAYMENT_ID = $("#PAYMENT_ID").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	var TYPE = $("#TYPE").combobox("getValue");
	$('#pageTable12345').datagrid("load",{PAYMENT_ID:PAYMENT_ID,PROJECT_ID:PROJECT_ID,TYPE:TYPE});
}

function uploadFile(row,index){
	var divId = 'uploadFileDiv';
	var formId = 'uploadFileForm';
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:'资料上传',
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
//			 	$('#btnbc').linkbutton('disable');
			 	var PAYMENT_ID = $("#PAYMENT_ID").val();
				var PROJECT_ID = $("#PROJECT_ID").val();
				var FILE_TYPE = $("#FILE_TYPE").combobox("getValue");
//				var REMARK = $("#uploadFileForm").find("#REMARK").val();
//				$('#'+formId).form('submit', {
//					url:_basePath+'/crm/Customer!doAddXMZL.action?RENTER_NAME='+encodeURI(RENTER_NAME)+"&RENTER_CODE="+encodeURI(RENTER_CODE)+"&FILE_TYPE="+encodeURI(FILE_TYPE)+"&CATALOG_TYPE="+encodeURI(CATALOG_TYPE)+"&PRO_CODE="+encodeURI(PRO_CODE)+"&_datetime="+new Date().getTime(),
//					success:function(json){
//						json = jQuery.parseJSON(json);
//						if(json.flag){
//							row.rec = "<a href='#' onclick='selectUpLoadDialog(" + JSON.stringify(row) + ")'>查看（已上传）</a>";
//							$('#pageTable12345').datagrid('refreshRow', index);
//							alert("保存成功");
//							$("#"+divId).dialog('close');
//							conditionsSelect();
//						}else{
//							alert(json.msg);
//						}
//					}
//				});
				var data_={'PAYMENT_ID':PAYMENT_ID,'PROJECT_ID':PROJECT_ID,'FILE_TYPE':FILE_TYPE};
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
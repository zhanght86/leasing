$(document).ready(function(){

	var FILE_STATUS=$("input[name='FILE_STATUS']").val();
	$("#resultMangerTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		url:"rentWriteNew!cyberBank_Result_PageAjax.action",
		queryParams:{"FILE_STATUS":FILE_STATUS},
		columns:[[
		          	{field:'ID',title:'标示',width:100},
		          	{field:'FILE_TEMP_NAME',hidden:true,align:'center'},
		          	{field:'FILE_NAME',title:'文件名称',width:220,formatter:function(value,rowData,rowIndex){
		          		var PATH=rowData.PATH;
		          		if(PATH==null||PATH==''||PATH=='undefined'){
		          			return value;
		          		}else{
		          			return "<a href='javascript:void(0);' onclick=downloadFile('" + encodeURI(PATH) +"','"+encodeURI(rowData.FILE_TEMP_NAME)+"')>"+value+"</a>";
		          		}
						   
					}},
		         // 	{field:'CREATE_NAME',title:'上传操作人',width:150},
		         // 	{field:'CREATE_TIME',title:'上传时间',width:150},
		          	{field:'FUND_DATE',title:'核销时间',width:150},
		          	{field:'FILE_ALL_NUM',title:'总条数',width:150},
		          	{field:'NUM_ED',title:'已处理总条数',width:150},
		          	{field:'SUCCESS_NUM',title:'成功条数',width:100},
		          	{field:'ERROR_BANK_NUM',title:'失败条数',width:100}, 
		          	{field:'ERROR_NUM',title:'异常条数',width:100},
		          	{field:'aaa',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
						  return "<a href='javascript:void(0);' onclick=ERROR_INFO('" + rowData.ID + "')>异常处理</a> | <a href='javascript:void(0);' onclick=ERROR_APP('" + rowData.ID + "')>异常明细</a>";  
					}}
		         ]]
	});
});

function downloadFile(PATH,FILE_TEMP_NAME) {
	window.location.href = _basePath + "/rentWrite/rentWriteNew!downFileTemplate.action?PATH=" + PATH + "&FILE_TEMP_NAME=" + FILE_TEMP_NAME;
}

function ERROR_APP(ID){
	if (ID==null||ID==''||ID=='undefined'){
		$.messager.alert("请选择一条回执文件!");
	}else{
		
		 top.addTab(ID+"异常明细",_basePath+"/rentWrite/rentWriteNew!ERROR_APP.action?ID="+ID);
	}
}

function ERROR_INFO(ID){
	if (ID==null||ID==''||ID=='undefined'){
		$.messager.alert("请选择一条回执文件!");
		 
	}else{
		if(confirm("确认进行异常处理?")){
			$.ajax({
				url : _basePath+"/rentWrite/rentWriteNew!ERROR_INFO.action?ID="+ID,
				dataType :"json",
				success : function(json){
					if(json.flag){
						alert("请稍后查看回执结果异常数据！");
						$("#resultMangerTable").datagrid("reload");
					}else{
						alert(json.msg);
					}
				}
			});
		}
	}
}


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}


function seach(){
	var FILE_NAME=$("input[name='FILE_NAME']").val();
	var CREATE_TIME1=$("input[name='CREATE_TIME1']").val();
	var CREATE_TIME2=$("input[name='CREATE_TIME2']").val();
	var FUND_DATE1=$("input[name='FUND_DATE1']").val();
	var FUND_DATE2=$("input[name='FUND_DATE2']").val();
	var FILE_STATUS=$("input[name='FILE_STATUS']").val();
	$('#resultMangerTable').datagrid('load', {"FILE_NAME":FILE_NAME,"CREATE_TIME1":CREATE_TIME1,"CREATE_TIME2":CREATE_TIME2,"FUND_DATE1":FUND_DATE1,"FUND_DATE2":FUND_DATE2,"FILE_STATUS":FILE_STATUS});
}


function autoHx(){
	if(confirm("确认自动核销?")){
		$.ajax({
			url : _basePath+"/rentWrite/rentWriteNew!autoHx.action",
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("请稍后查看回执结果异常数据！");
					$("#resultMangerTable").datagrid("reload");
				}
			}
		});
	}
}



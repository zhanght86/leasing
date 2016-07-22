function anchoredSe(){
	$("#anchoredTable").datagrid('load',{"param":getFromData("#pageForm")});
}

function onload(){
 jQuery("#anchoredTable").datagrid({
	 pagination:true,
	 singleSelect:true,
	 rownumbers:true,
	 fit:true,
	 fitColumns:true,
	 url:_basePath+"/anchored/AnchoredManager!doShowFl_Company.action",
	 toolbar:"#pageForm",
	 columns:[[
		      {field:'NAME',width:35,title:'融资租赁公司名称'},
		      {field:'ORGAN_CODE',width:35,title:'组织机构代码'},
		      {field:'ADDRESS',width:35,title:'注册地址'},
		      {field:'POST',width:35,title:'邮编'},
		      {field:'TELPHONE',width:35,title:'联系电话'},
		      {field:'QUALIFICATION',width:35,title:'纳税人资质'},
		      {field:'IDENTIFIER',width:35,title:'纳税人识别号'},
		      {field:'OPEN_BANK_NAME',width:35,title:'开户行名称'},
		      {field:'OPEN_BANK_ACCOUNT',width:35,title:'开户账号'},
		      {field:'OPEN_BANK_ADDRESS',width:35,title:'开户行地址'},
		      {field:'MAIL_ADDRESS',width:35,title:'邮寄地址'},
			  {field:'Compture',width:40,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
				 return "<a href='javascript:void(0)' onclick=updateAnchoredOpen('"+rowIndex+"')>修改</a>" +
				 	    "|<a href='javascript:void(0)' onclick=destroyType('"+rowData.ID+"')>删除</a>";
		      }}
		      ]]
 });
}
function clean(){
	$("#pageForm").find("input[name=NAME]").val('');
}

function destroyType(ID){
	$.messager.confirm("删除","确定要删除该条数据吗？",function(r){
		if(r){
			$.ajax({
				type:'post',
				url:_basePath+"/anchored/AnchoredManager!doDeletePL.action",
				data:"ID="+ID,
				dataType:"json",
				success:function(json){
					if(json.flag){
						$.messager.alert("提示","删除成功！");
						$("#anchoredTable").datagrid('load',{"param":getFromData("#pageForm")});
					}else{
						$.messager.alert("提示","删除失败！");
					}
				},
				error:function(json){
					$.messager.alert("提示",json.msg);
				}
			});
		}
	});
}

function addAnchoredOpen(){
	$("#addAnchoredDialog").dialog("open");
//	$("#addAnchoredDialog").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5});  
	$("#addAchoredForm").form('clear');
}
function addAnchored(){
	if(!$("#addAchoredForm").form('validate')){
		return false;
	}
	var NAME=$("#addAchoredForm").find("input[name=NAME]").val();
	var ORGAN_CODE=$("#addAchoredForm").find("input[name=ORGAN_CODE]").val();
	var ADDRESS=$("#addAchoredForm").find("input[name=ADDRESS]").val();
	var POST=$("#addAchoredForm").find("input[name=POST]").val();
	var TELPHONE=$("#addAchoredForm").find("input[name=TELPHONE]").val();
	var IDENTIFIER=$("#addAchoredForm").find("input[name=IDENTIFIER]").val();
	var OPEN_BANK_ACCOUNT=$("#addAchoredForm").find("input[name=OPEN_BANK_ACCOUNT]").val();
	var OPEN_BANK_ADDRESS=$("#addAchoredForm").find("input[name=OPEN_BANK_ADDRESS]").val();
	var OPEN_BANK_NAME=$("#addAchoredForm").find("input[name=OPEN_BANK_NAME]").val();
	var MAIL_ADDRESS=$("#addAchoredForm").find("input[name=MAIL_ADDRESS]").val();
	var QUALIFICATION=$("#addAchoredForm").find("#QUALIFICATION").combobox('getValue');
	
	$.ajax({
		type:'post',
		url:_basePath+"/anchored/AnchoredManager!doAddPL.action",
		data:"NAME="+encodeURI(NAME)+"&ORGAN_CODE="+encodeURI(ORGAN_CODE)+"&ADDRESS="+encodeURI(ADDRESS)+
			 "&POST="+encodeURI(POST)+"&TELPHONE="+encodeURI(TELPHONE)+"&QUALIFICATION="+
			 encodeURI(QUALIFICATION)+"&IDENTIFIER="+encodeURI(IDENTIFIER)+"&OPEN_BANK_ACCOUNT="+encodeURI(OPEN_BANK_ACCOUNT)+
			 "&OPEN_BANK_ADDRESS="+encodeURI(OPEN_BANK_ADDRESS)+"&OPEN_BANK_NAME="+encodeURI(OPEN_BANK_NAME)+
			 "&MAIL_ADDRESS="+encodeURI(MAIL_ADDRESS),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#anchoredTable").datagrid('load',{"param":getFromData("#pageForm")});
				$("#addAnchoredDialog").dialog("close");
			}else{
				$.messager.alert("提示",json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","网络问题，请联系管理员");
		}
	});
}

function closeAddAnchored(){
	$("#addAnchoredDialog").dialog("close");
}

function closeUpdateAnchored(){
	$("#updateAchoredForm").form('clear');
	$("#updateAnchoredDialog").dialog("close");
}

function updateAnchoredOpen(rowIndex){
	$("#anchoredTable").datagrid('selectRow',rowIndex);
	var row=$("#anchoredTable").datagrid('getSelected');
	if(row){
		$("#updateAnchoredDialog").dialog("open");
//		$("#updateAnchoredDialog").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5}); 
//		$("#updateAchoredForm").form('load',row);
	}else{
		$.messager.alert("提示","请选择需要修改的数据行");
	}
}

function updateAnchored(){
	if(!$("#updateAchoredForm").form('validate')){
		return false;
	}
	var ID=$("#updateAchoredForm").find("input[name=ID]").val();
	var NAME=$("#updateAchoredForm").find("input[name=NAME]").val();
	var ORGAN_CODE=$("#updateAchoredForm").find("input[name=ORGAN_CODE]").val();
	var ADDRESS=$("#updateAchoredForm").find("input[name=ADDRESS]").val();
	var POST=$("#updateAchoredForm").find("input[name=POST]").val();
	var TELPHONE=$("#updateAchoredForm").find("input[name=TELPHONE]").val();
	var QUALIFICATION=$("#updateAchoredForm").find("#QUALIFICATION").combobox('getValue');
	var IDENTIFIER=$("#updateAchoredForm").find("input[name=IDENTIFIER]").val();
	var OPEN_BANK_ACCOUNT=$("#updateAchoredForm").find("input[name=OPEN_BANK_ACCOUNT]").val();
	var OPEN_BANK_ADDRESS=$("#updateAchoredForm").find("input[name=OPEN_BANK_ADDRESS]").val();
	var OPEN_BANK_NAME=$("#updateAchoredForm").find("input[name=OPEN_BANK_NAME]").val();
	var MAIL_ADDRESS=$("#updateAchoredForm").find("input[name=MAIL_ADDRESS]").val();
	
	
	$.ajax({
		type:'post',
		url:_basePath+"/anchored/AnchoredManager!doUpdateFL.action",
		data:"ID="+ID+"&NAME="+encodeURI(NAME)+"&ORGAN_CODE="+encodeURI(ORGAN_CODE)+"&ADDRESS="+encodeURI(ADDRESS)+
		 "&POST="+encodeURI(POST)+"&TELPHONE="+encodeURI(TELPHONE)+"&QUALIFICATION="+
		 encodeURI(QUALIFICATION)+"&IDENTIFIER="+encodeURI(IDENTIFIER)+"&OPEN_BANK_ACCOUNT="+encodeURI(OPEN_BANK_ACCOUNT)+
		 "&OPEN_BANK_ADDRESS="+encodeURI(OPEN_BANK_ADDRESS)+"&OPEN_BANK_NAME="+encodeURI(OPEN_BANK_NAME)+
		 "&MAIL_ADDRESS="+encodeURI(MAIL_ADDRESS),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","修改成功!");
				$("#anchoredTable").datagrid('load',{"param":getFromData("#pageForm")});
				$("#updateAnchoredDialog").dialog("close");
			}else{
				$.messager.alert("提示",json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","网络问题，请联系管理员");
		}
	});
}
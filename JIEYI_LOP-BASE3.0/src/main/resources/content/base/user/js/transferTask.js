
function toTransfer(row){
	if (row){
		top.addTab("员工业务移交",_basePath+"/base/user/TransferTask!toTransfer.action?EMPLOYEE_ID="+row.EMPLOYEE_ID);
    }
}

function doTransfer(transferor){
	if(!transferor){
		return alert('请指定移交人');
	}
	
	var projects = $('.project_list').datagrid("getSelections"); // 选择的项目
	
	var jsonOjb = [];
	if( !$('#transferAll:checkbox').is(':checked') ){
		if(projects.length == 0 && $('.customer_list').datagrid("getSelections").length == 0){
			return alert('请选择移交客户');
		}
		if(projects.length > 0){
			projects.forEach(function (v2, i2 ,r2){
				jsonOjb.push({ID:v2.ID});
			});
		}else{
			$('.customer_list').datagrid("getSelections").forEach(function(v,i,r){
				jsonOjb.push({ID:v.ID , NAME: v.NAME});
			});
		}
		
		if(projects.length == 0){
			if(!confirm('注意：将要移交您【已选择客户】的所有项目数据，请确认？')){
				return false;
			}
		}else if(!confirm('注意：将要移交您已选择【所有项目】数据，请确认？')){
			return false;
		}
		
	}else  if(!confirm('注意：您选择了全部移交，这样该员工下的【所有客户】都会被移交，请确认？')){
		return false;
	}
	var emp = $('.emp_list').datagrid("getSelected");
	if(!emp){
		return alert('请指定接收人');
	}
	var transferee = emp.EMPLOYEE_CODE;
	
	var transfer_resume = $('#transfer_resume').val();
	if(!transfer_resume){
		return alert('请输入移交说明');
	}
	if(!confirm('业务移交即将开始，最终确认？')){
		return false;
	}
	$.messager.progress(); 
	$.ajax({
		url :_basePath+'/base/user/TransferTask!doTransfer.action'
		,type:'post'
		,data:{data: JSON.stringify(jsonOjb) 
			, transferAll: $('#transferAll:checkbox').is(':checked') ? 'all' :( projects.length>0 ? 'project':'client' )
			, transferor:transferor 
			, transferee: transferee
			, resume : transfer_resume
		}
		,dataType:'json'
	}).done(function (o){
		if(o.ok){
			alert('移交成功');
//			$('.customer_list').datagrid('load', { EMPLOYEE_CODE: $('#EMPLOYEE_CODE').val() });
			qryClient();
			qryProject();
		}else{
			alert("移交失败"+o.msg);
		}
		$.messager.progress('close');
	}).fail(function (e){
		console.debug(e);
		alert('处理中出错'+e);
		$.messager.progress('close');
	});
}
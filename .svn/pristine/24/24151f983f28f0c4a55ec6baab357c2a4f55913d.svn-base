
function se(){
	var content = {};
	$('#pageForm :input').each(function(){
		content[$(this).attr('name')] = $(this).val();
	});
	console.debug(content);
	$('#pageTable').datagrid('load',content);
}

function exportExcel(){
	var rows = $('#pageTable').datagrid('getSelections');
	var ids = '';
	if(rows.length<=0){
		return alert('请选择数据');
	};
	for(var i=0;i<rows.length;i++){
		ids +=rows[i].ID;
		if(i!=rows.length-1){
			ids +=','; 
		}
	}
	window.location.href=_basePath+"/commission/Commission!exportExcel.action?IDS="+ids;
}

function readExcel(){
	var fileName = $('#file').val();
	if(chk_ext(get_ext(fileName))==false){
		return alert('请选择Excel文件！');
	}
	$.ajaxFileUpload({
	    url:_basePath+"/commission/Commission!readExcel.action",
	    secureuri:false,
	    fileElementId:"file",	 
	    dataType: "json",
		success: function (data,status)  //服务器成功响应处理函数
        {
			var obj = JSON.parse(data);
			alert(obj.msg);
			$('#pageTable').datagrid('reload');
        }
	});	
}

function apply(){
	var rows = $('#pageTable').datagrid('getSelections');
	var ids = '';
	if(rows.length<=0){
		return alert('请选择数据');
	};
	for(var i=0;i<rows.length;i++){
		ids +=rows[i].ID;
		if(i!=rows.length-1){
			ids +=','; 
		}
	}
	top.addTab("返佣申请",_basePath+"/commission/Commission!apply.action?IDS="+ids);
}

function rebateApply(){
	var params = new Array();
	$(".mytable").each(function (){
		var $table = $(this);
		var jsonObj = {};
		$table.find("input").each(function(){
			if(this.name){
				jsonObj[this.name] = this.value;
			}
		});
		params.push(JSON.stringify(jsonObj));
	});
	console.debug(params);
	$.ajax({
		url:_basePath+'/commission/Commission!startCommissionByJbpm.action',
		data: "jsonStr="+params,
		dataType: 'json',
		type:'post',
		success: function(data){
			alert(data.msg);
		}
	});
}

//获取文件扩展名
function get_ext(f_path){
	var ext = '';
	if(f_path != null && f_path != ''){
	   ext = f_path.substring(f_path.lastIndexOf(".") + 1, f_path.length);
	}
	return ext;
}

//验证文件扩展名
function chk_ext(f_path){
	var ext = get_ext(f_path);
	//根据需求定制
	var accept_ext = new Array("xls","xlsx");
	var flag = false;
	if(ext != ''){
	   for(var i=0; i<accept_ext.length; i++){
	    if(ext == accept_ext[i])
	     flag = true;
	   }
	}
	return flag;
}


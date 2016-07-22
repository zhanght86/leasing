$(function(){
	//添加数据字典信息
	
	$(".addR").click(function(){
		var tempTR=$(".templete").clone();
		$("#addTbody").append(tempTR.removeClass("hidden templete"));
	});
	
	$("#addTbody").click(function(e){
		if($(e.target).is(".del")){
			$(e.target).parents("tr").remove();
		}
	});
	$("#RECORD_NAME").change(function(){
		check();
	});
	
	
		
		
		
		
		//数据字典配置操作
		$(".updateData").click(function(){
			var  TYPE=$(this).attr("TYPE"); 
			window.location.href="DataDictionary!toDataTypeInfo.action?TYPE="+TYPE;
			
		});
		
		//作废与启动类型
		$(".invalidStatus").click(function(){
			var DATA_ID = $(this).attr("DATA_ID");
			var  STATUS = $(this).attr("STATUS");
			var  TYPE = $(this).attr("TYPES");
			var DESCRIBE=$(this).attr("DESCRIBE");
			jQuery.ajax({
				url: "DataDictionary!invalidDataType.action",
				data: "DATA_ID="+DATA_ID+"&STATUS="+STATUS,
				dataType:"json",
				success: function(date){
					if(date.flag==true){
					   alert("操作成功");
					   window.location="DataDictionary!toDataTypeInfo.action?TYPE="+TYPE+"&DESCRIBE="+DESCRIBE;
				   }
				   else{
					   alert("操作失败请重试！");
				   }
				},
				error: function(e){
					alert(e.message);
				}
			});
		});
});

function del(obj)
{
	$(obj).parents("tr").remove();
}


function addType(){
	window.location.href="DataDictionary!toDataType.action";
};

function editType(row)
{
	 if (row){
		 top.addTab(row.TYPE+"数据字典修改",_basePath+'/dataDictionary/DataDictionary!toDataTypeInfo.action?TYPE='+row.TYPE+'&DESCRIBE='+row.DESCRIBE);
	 }
}


//删除数据字典信息
function destroyType(row){
	if(confirm("确定要删除此记录？")){
		if (row){
			jQuery.ajax({
				url: "DataDictionary!deleteDictionary.action",
				data: "TYPE="+row.TYPE,
				dataType:"json",
				success: function(date){
					if(date.flag==true){
					   alert("操作成功");
					   $("#pageTable").datagrid('reload');
				   }
				   else{
					   alert("操作失败请重试！");
				   }
				},
				error: function(e){
					alert(e.message);
				}
			});
		}
	} 
}

function addRecordButton()
{
		//保存数据字典信息
		 if(!checkNull()){
				return ;
		}
		 if(!checkFlag()){
				return ;
			}

		var  saveRecord = new Array();
		$(".addData:not(.templete)").each(function(){
			var temp={};
			//名称
			temp.FLAG=$(this).find("[name=FLAG]").val();
			//国际化
			temp.FLAG_INTNA=$(this).find("[name=FLAG_INTNA]").val();
			//标识
			temp.CODE=$(this).find("[name=CODE]").val();
			//简称
			temp.SHORTNAME=$(this).find("[name=SHORTNAME]").val();
			//级别
			temp.LEVEL_NUM=$(this).find("[name=FIFC_ORDER1]").val();
			//备注
			temp.REMARK=$(this).find("[name=REMARK]").val();
			saveRecord.push(temp);
		});
		var addData ={
			RECORD_NAME:$("#RECORD_NAME").val(),
			DESCRIBE:$("#DESCRIBE").val(),
			RECORD_LIST:saveRecord
		};
					
		var alldata = JSON.stringify(addData);
			jQuery.ajax({
					url: "DataDictionary!addDataDictionary.action",
					type:"post",
					data: "alldata="+alldata,
					dataType:"json",
					success: function(date){
					if(date.flag==true){
						alert("添加成功");
							window.location="DataDictionary!getDataList.action";
						}else{
							alert("添加失败 ");
						}
					},
						error: function(i){
						alert("网络连接失败，请重试");
						}
					}); 
	
}


//修改数据字典
function updateRecordButton(){
	 if(!checkNull()){
			return ;
		}
	 if(!checkFlag()){
			return ;
		}
 
	var  updateRecord = new Array();
	$(".addData:not(.templete)").each(function(){
  		var temp={};
     	//ID
		temp.DATA_ID=$(this).find("[name=DATA_ID]").val();
  		//名称
		temp.FLAG=$(this).find("[name=FLAG]").val();
		//国际化
		temp.FLAG_INTNA=$(this).find("[name=FLAG_INTNA]").val();
		//标识
		temp.CODE=$(this).find("[name=CODE]").val();
		//简称
		temp.SHORTNAME=$(this).find("[name=SHORTNAME]").val();
		//级别
		temp.LEVEL_NUM=$(this).find("[name=FIFC_ORDER1]").val();
		//备注
		temp.REMARK=$(this).find("[name=REMARK]").val().replace(/\%/g,"％");
		//状态
		if($(this).find("[name=USTATUS]").attr("STATUS")==0){
			temp.STATUS=1;
		}
		if($(this).find("[name=USTATUS]").attr("STATUS")==1){
			temp.STATUS=0;
		}
		updateRecord.push(temp);
  });

	 
	var addData ={
			RECORD_NAME:$("#RECORD_NAME").val(),
			DESCRIBE:$("#DESCRIBE").val(),
			RECORD_LIST:updateRecord
	};
	var alldata = JSON.stringify(addData);
		jQuery.ajax({
			url: "DataDictionary!updateDataDictionary.action",
			type:"post",
			data: "alldata="+alldata,
			dataType:"json",
			success: function(date){
				if(date.flag==true){
					alert("修改成功");
					window.location="DataDictionary!toDataTypeInfo.action?TYPE="+$("#RECORD_NAME").val();
				}else{
					alert("修改失败 ");
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
			}
		});
}


//判断填写是否有重复的名称 FLAG

function checkFlag(){
	var fl=	$(".addData:not(.templete)").find("[name=FLAG]");
	for(var i=0;i<fl.length;i++ ){
		for(var j=i+1; j<fl.length;j++){
			if($(fl[i]).val() == $(fl[j]).val()){
				alert("名称不能相同！！");
				return false;
			}
		}
	}
	var co=	$(".addData:not(.templete)").find("[name=CODE]");
	for(var i=0;i<co.length;i++ ){
		for(var j=i+1; j<co.length;j++){
			if($(co[i]).val() == $(co[j]).val()){
				alert("标示不能相同！！");
				return false;
			}
		}
	}	
	return true;
}



//判断类型名称是否已存在
function check(){
		var type = $("#RECORD_NAME").val();
		if (type=="") {
			alert("请填写类型!");
			return false;
		}
		
		jQuery.ajax({
			url: "DataDictionary!checkType.action",
			type:"post",
			data: "TYPE="+type,
			dataType:"json",
			success: function(date){
				if(date.flag==true){
					alert("该类型已存在,请重新输入!");
					return false;
				}else{
					return true;
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
				return false;
			}
		});
	}



//判断填写是否合法
function checkNull(){
	var sub = true;
	$(".addData:not(.templete)").each(function(){
		if($.trim($(this).find("[name=FLAG]").val()).length == 0){
			alert("名称不能为空!! 如果有空行请删除！！ ");
			sub = false;
			return false;
		}
		
		if($.trim($(this).find("[name=FLAG_INTNA]").val()).length == 0){
			alert("细项国际化不能为空!! 如果有空行请删除！！ ");
			sub = false;
			return false;
		}
	});
	if(!sub){
		return false;
	}
		
	$(".addData:not(.templete)").each(function(){
		if($.trim($(this).find("[name=CODE]").val()).length == 0){
			alert("标示不能为空!!")
			sub = false;
			return false;
		}
	})
	if(!sub){
		return false;
	}
	
	$(".addData:not(.templete)").each(function(){
		if($.trim($(this).find("[name=FIFC_ORDER1]").val()) == 0){
			alert("级别不能为空!!")
			sub = false;
			return false;
		}
	})
	if(!sub){
		return false;
	}
	
	return true;
}	
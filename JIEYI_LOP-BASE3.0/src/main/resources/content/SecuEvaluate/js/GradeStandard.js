$(function(){
	//添加评级标准信息
	
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
	
	
		
		
		
		
		//评级标准配置操作
		$(".updateData").click(function(){
			var  TYPE=$(this).attr("TYPE"); 
			window.location.href="GradeStandard!toDataTypeInfo.action?TYPE="+TYPE;
			
		});
		
		//作废与启动类型
		$(".invalidStatus").click(function(){
			var DATA_ID = $(this).attr("DATA_ID");
			var  STATUS = $(this).attr("STATUS");
			var  TYPE = $(this).attr("TYPES");
			var DESCRIBE=$(this).attr("DESCRIBE");
			jQuery.ajax({
				url: "GradeStandard!invalidDataType.action",
				data: "DATA_ID="+DATA_ID+"&STATUS="+STATUS,
				dataType:"json",
				success: function(date){
					if(date.flag==true){
					   alert("操作成功");
					   window.location="GradeStandard!toDataTypeInfo.action?TYPE="+TYPE+"&DESCRIBE="+DESCRIBE;
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
	window.location.href="GradeStandard!toDataType.action";
};

function editType(row)
{
	 if (row){
		 top.addTab("评级标准修改",_basePath+'/secuEvaluate/GradeStandard!toDataTypeInfo.action?TYPE='+row.TYPE+'&DESCRIBE='+row.DESCRIBE+'&MAIN_TYPE='+row.MAIN_TYPE);
	 }
}


//删除评级标准信息
function destroyType(row){
		if (row){
			jQuery.ajax({
				url: "GradeStandard!deleteDictionary.action",
				data: "TYPE="+encodeURI(row.TYPE),
				dataType:"json",
				success: function(date){
					if(date.flag==true){
					   alert("操作成功");
					   window.location="GradeStandard!getDataList.action";
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
var flag1="";//Add By:YangJ 2014-4-22 保存提交时用作判断变量
function addRecordButton()
{
		
//		 若已存在，则不予保存   Add By：YangJ 2014-4-22
		 check();
		 if(!flag1){
			 return ;
		 }

		//保存评级标准信息
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
			var FLAG=$(this).find("[name=FLAG]").val();
			temp.FLAG=FLAG;
			if($("#DESCRIBE").val() == "1"){
				temp.START_NUM = FLAG.split("~")[0];
				temp.END_NUM = FLAG.split("~")[1];
			}
			//简称
			temp.FLAG_INTNA=$(this).find("[name=FLAG_INTNA]").val();
			//级别
			temp.LEVEL_NUM=$(this).find("[name=FIFC_ORDER1]").val();
			//备注
			temp.REMARK=$(this).find("[name=REMARK]").val();
			saveRecord.push(temp);
		});
		var addData ={
			RECORD_NAME:$("#RECORD_NAME").val(),
			DESCRIBE:$("#DESCRIBE").val(),
			MAIN_TYPE:$("#MAIN_TYPE").val(),
			RECORD_LIST:saveRecord
		};
					
		var alldata = JSON.stringify(addData);
			jQuery.ajax({
					url: "GradeStandard!addGradeStandard.action",
					type:"post",
					data: "alldata="+alldata,
					dataType:"json",
					success: function(date){
					if(date.flag==true){
						alert("添加成功");
							window.location="GradeStandard!getDataList.action";
						}else{
							alert("添加失败 ");
						}
					},
						error: function(i){
						alert("网络连接失败，请重试");
						}
					}); 
	
}


//修改评级标准
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
		var FLAG=$(this).find("[name=FLAG]").val();
		temp.FLAG=FLAG;
		if($("#DESCRIBE").val() == "1"){
			temp.START_NUM = FLAG.split("~")[0];
			temp.END_NUM = FLAG.split("~")[1];
		}
		else if($("#DESCRIBE").val() == "2"){
			temp.START_NUM = FLAG.split("~")[0];
			temp.END_NUM = FLAG.split("~")[1];
		}
		//分值
		temp.FLAG_INTNA=$(this).find("[name=FLAG_INTNA]").val();
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
			MAIN_TYPE:$("#MAIN_TYPE").val(),
			RECORD_LIST:updateRecord
	};
	var alldata = JSON.stringify(addData);
		jQuery.ajax({
			url: "GradeStandard!updateGradeStandard.action",
			type:"post",
			data: "alldata="+alldata,
			dataType:"json",
			success: function(date){
				if(date.flag==true){
					alert("修改成功");
					top.updateGradeStandard();
					top.closeTab("评级标准修改");
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
	var RECORD_NAME = $("#RECORD_NAME").val();
	var DESCRIBE = $("#DESCRIBE").val();
	var MAIN_TYPE = $("#MAIN_TYPE").val();
	if(RECORD_NAME == null || RECORD_NAME == '' || RECORD_NAME =='undefind'){
		alert("请填写评级标准名称！");
		return false;
	}
	if(DESCRIBE == null || DESCRIBE == '' || DESCRIBE =='undefind'){
		alert("请选择评级标准类型！");
		return false;
	}
	if(MAIN_TYPE == null || MAIN_TYPE == '' || MAIN_TYPE =='undefind'){
		alert("请选择主体类型！");
		return false;
	}
	var fl=	$(".addData:not(.templete)").find("[name=FLAG]");
	for(var i=0;i<fl.length;i++ ){
		for(var j=i+1; j<fl.length;j++){
			if($(fl[i]).val() == $(fl[j]).val()){
				alert("名称不能相同！！");
				return false;
			}
		}

		if($("#DESCRIBE").val() != "0"){
			var FLAG = $(fl[i]).val();
			var START_NUM = FLAG.split("~")[0];
			var END_NUM = FLAG.split("~")[1];
			if(isNaN(START_NUM)||isNaN(END_NUM)){
				alert("请填写正确的区间数值！例：10~20");
				return false;
			}
		}
	}
	return true;
}

function checkNumber(temp){
	if(isNaN($(temp).val()) && $("#DESCRIBE").val() != "2"){
		alert("请填写正确的对应分值！");
		$(temp).val("0");
	}
}


//判断类型名称是否已存在
function check(){
		var type = $("#RECORD_NAME").val();
		if (type=="") {
			alert("请填写类型!");
			return false;
		}
		
		jQuery.ajax({
			url: "GradeStandard!checkType.action",
			type:"post",
			data: "TYPE="+type,
			dataType:"json",
			success: function(date){
				if(date.flag==true){
					alert("该类型已存在,请重新输入!");
					flag1=false;//Add By:YangJ 2014-4-22 保存提交时用作判断变量
					return false;
				}else{
					flag1=true;//Add By:YangJ 2014-4-22 保存提交时用作判断变量
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
			alert("选项不能为空!! 如果有空行请删除！！ ");
			sub = false;
			return false;
		}
		
		if($.trim($(this).find("[name=FLAG_INTNA]").val()).length == 0){
			alert("对应分值不能为空!! 如果有空行请删除！！ ");
			sub = false;
			return false;
		}
	});
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
//搜索
function conditionsSelect(){
	$('#pageTable').datagrid('load', {
		STATR_DATE:$("#STATR_DATE").datebox('getValue'),
		END_DATE:$("#END_DATE").datebox('getValue'),
		NAME:$("#NAME").val(),
		DEPARTMENT:$("#DEPARTMENT").val(),
		POST:$("#POST").val(),
		STATE:$("#STATE").combobox('getValue'),
		POST_LEVEL:$("#POST_LEVEL").combobox('getValue')
	});
}

function clearInput(){
	$("#pageForm input").val("");
}

function getValue(value,rowDate,inedx){
	if(value == '0'){
		return '启用中';
	}else if(value == '1'){
		return '已禁用';
	}else{
		return '';
	}
}

function getOperation(value,rowDate,index){
	var STATE = rowDate.STATE;
	if(STATE == '1' || STATE == ''){
		return "<a href='javascript:void(0)' onclick='showAC("+value+")'>查看</a>" + "&nbsp|&nbsp" +
			   "<a href='AssessmentConfigur!toAssessmentConfigurAddAndUpdate.action?ID="+value+"'>修改</a>" + "&nbsp|&nbsp" +
			   "<a href='javascript:void(0)' onclick='deleteAC("+value+")'>删除</a>" + "&nbsp|&nbsp" +
			   "<a href='javascript:void(0)' onclick='stateAC("+index+","+JSON.stringify(rowDate)+","+value+",1)'>启用</a>";
	}else {
		return "<a href='javascript:void(0)' onclick='showAC("+value+")'>查看</a>" + "&nbsp|&nbsp" +
			   "<a href='AssessmentConfigur!toAssessmentConfigurAddAndUpdate.action?ID="+value+"'>修改</a>" + "&nbsp|&nbsp" +
			   "<a href='javascript:void(0)' onclick='deleteAC("+value+")'>删除</a>" + "&nbsp|&nbsp" +
			   "<a href='javascript:void(0)' onclick='stateAC("+index+","+JSON.stringify(rowDate)+","+value+",0)'>禁用</a>";
	}
}

//获取岗位
function getPost(ID){
	jQuery.ajax({
	   url: "AssessmentConfigur!getPost.action?PARENT_ID="+ID,
	   type: "POST",
	   dataType:"json",
	   success: function(json){
			var data = json.data;
			var temp = new Array();
			$('#POST').combobox('loadData', temp);
			if(data != null && data.length > 0){
				for(var i = 0; i < data.length; i++){
					temp.push({
						"value":data[i].ID,
						"text":data[i].NAME
					});
				}
				$('#POST').combobox('loadData', temp);
			}
	   }
	});
}

//添加Table
function addTable(){
	$("#updateTable").append($(".hiddenClassTable").clone().removeClass("templete hiddenClassTable").attr('id',Math.uuidFast()));
}

//删除Table
function deleteTable(e){
	$(e).parents("table").remove();
	$(e).parents("br").remove();
}

//添加Tr
function addTr(e){
	var tableId = $(e).parent().parent().parent().parent().attr("id");
	var num = $("#" + tableId + " tr").length;
	$("#" + tableId).append($("#hiddenTr").clone().removeAttr("id"));
}

//删除Tr
function deleteTr(e){
	$(e).parents("tr").remove();
}

//提交保存
function save(){
	$('#btnbc').linkbutton('disable');
	var  saveRecord = new Array();
	$(".addData:not(.templete)").each(function(){
  		var temp={};
		//是否更新考核项
		temp.AT_ID_YES=$(this).find("[name=AT_ID_YES]").val();
		//考核项ID
		temp.AT_ID=$(this).find("[name=AT_ID]").val();
		var  saveRecord1 = new Array();
		$(this).find(".hiddenTrClass").each(function(){
			var temp1={};
			//最大值
			temp1.MAX_VALUE=$(this).find("[name=MAX_VALUE]").val();
			//最小值
			temp1.MIN_VALUE=$(this).find("[name=MIN_VALUE]").val();
			//等于
			temp1.EQUAL_VALUE=$(this).find("[name=EQUAL_VALUE]").val();
			//绩效金额
			temp1.PERFORMANCE_MONEY=$(this).find("[name=PERFORMANCE_MONEY]").val();
			saveRecord1.push(temp1);
		});
		temp.LIST = saveRecord1;
		saveRecord.push(temp);
    });
	var addData ={ 
		AC_ID:$("#AC_ID").val(),//模版ID
		NAME:$("#NAME").val(),//模版名称
		STATR_DATE:$("#STATR_DATE").datebox('getValue'),//开始使用时间
		END_DATE:$("#END_DATE").datebox('getValue'),//使用结束时间
		DEPARTMENT:$("#DEPARTMENT").combobox('getValue'),//部门
		POST:$("#POST").combobox('getValue'),//岗位
		POST_LEVEL:$("#POST_LEVEL").combobox('getValue'),//岗位级别
		STATE:$("#STATE").combobox('getValue'),//启用状态
		PERIOD:$("#PERIOD").combobox('getValue'),//考核周期
		DATA_LIST:saveRecord
	};
	var alldata = JSON.stringify(addData);
	jQuery.ajax({
		url: _basePath+"/performance/AssessmentConfigur!doSaveAssessmentConfigur.action",
		type:"post",
		data: { "alldata":alldata },
		dataType: "json",
		success: function(json){
			if(json.flag){
				$.messager.alert('提示', '保存成功！');
				if(json.data.DOTYPE){
					$("#AC_ID").val(json.data.AC_ID);
				}
				$('#btnbc').linkbutton('enable');
			}else{
				$.messager.alert('提示','保存失败，请与管理员联系！');
				$('#btnbc').linkbutton('enable');
			}
		}
	}); 
}

function showAC(ID){
	top.addTab("查看考核标准配置",_basePath+"/performance/AssessmentConfigur!toAssessmentConfigurShow.action?ID="+ID+"&_dateTime="+new Date());
}

function deleteAC(ID){
	var opts = $('#pageTable').datagrid('getSelected');
	$.messager.confirm('提示', '确定删除模版"'+opts.NAME+'"吗？', function(r){
		jQuery.ajax({
	        url: "AssessmentConfigur!doDeleteAC.action?ID="+ID+"&_dateTime="+new Date(),
	        type: "post",
	        dataType: "json",
	        success: function(json){
	            if(json.flag){
					$('#pageTable').datagrid('load');
				}else{
					$.messager.alert('提示','删除失败，请与管理员联系！','warning');
				}
	        }
	    })
	})
}

function stateAC(index,rowDate,ID,STATE){
	var TYPE = "";
	if(STATE == '0'){
		STATE='1';
		TYPE='禁用';
	}else{
		STATE='0';
		TYPE='启用';
	}	
	$.messager.confirm('提示', '确定'+TYPE+'模版"'+rowDate.NAME+'"吗？', function(r){
		jQuery.ajax({
	        url: "AssessmentConfigur!doUpdateAC.action?ID="+ID+"&STATE="+STATE+"&_dateTime="+new Date(),
	        type: "post",
	        dataType: "json",
	        success: function(json){
	            if(json.flag){
					rowDate.STATE=STATE;
					$('#pageTable').datagrid('updateRow', { 
						index: index, 
						row: rowDate
					});
				}else{
					$.messager.alert('提示',TYPE+'失败，请与管理员联系！','warning');
				}
	        }
	    })
	})
}


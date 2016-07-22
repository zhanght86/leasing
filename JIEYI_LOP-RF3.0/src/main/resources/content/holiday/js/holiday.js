$(function() {
	$('#holidayMg').datagrid({
		url:_basePath+"/holiday/HolidayMain!toMgHolidayData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[ 	 	 	 
		          {field:'ID',align:'center',width:40,title:'操作',formatter:toOperate},
		          {field:'CREATE_YEAR',align:'center',width:40,title:'年份'}
		         
		]]
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var create_year = $("select[id='create_year']").val();
	$('#holidayMg').datagrid('load', {
		"create_year" : create_year
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#create_year").datebox('clear');
}

function addTask(){
	window.location.href=_basePath+"/holiday/HolidayMain!toAddHoliday.action";
}

function saveHoliday(){

	var saveByYear = $("#saveByYear").datebox('getValue');
	var saveYear_ = saveByYear.substring(0,4);
	
	var nowDate = new Date();
	var nowYear = nowDate.getFullYear();
	
	if(saveByYear<nowYear){
		alert("只能输入当前年份或当前年份之后的年份！");
	}else{
		$("#loading").show();
		
		jQuery.ajax({
			url:_basePath+"/holiday/HolidayMain!toSaveTaskByYear.action",
			type:"post",
			data:"saveByYear="+saveYear_,
			dataType:"json",
			success:function(json){
				if(json.flag==true){
					$("#loading").hide();
					alert("保存成功！");
					window.location.href=_basePath+"/holiday/HolidayMain!toqueryHoliday.action?saveByYear="+saveByYear+"&statusPage=1";
				}else{
					$("#loading").hide();
					alert("数据已存在！");
				}
			},
			error:function(e){
				alert(e);
			}
		});
	  }
}

//操作
function toOperate(val, row){
	return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toQueryHoliday(\""+row.CREATE_YEAR+"\")'>修改</a> " +
			" |  " +
			"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='addBeiZhu("
			+ row.ID
			+",\""
			+2
			+ "\")'> 添加备注</a>" +
			"  |  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='searchBeiZhu("
			+ row.ID
			+",\""
			+2
			+ "\")'>查看备注</a>";
}

//修改
function toQueryHoliday(time){
	window.location.href=_basePath+"/holiday/HolidayMain!toqueryHoliday.action?saveByYear="+time;
}

function addBeiZhu(seqId,TypeId){
	$("#previewAddBeiZhu").dialog({
		tetile:"添加备注",
		 closed: false,
		 cache: false,
		 buttons:[{
			 text:"取消",
			 handler:function(){
			 $("#COMMENT_CONTENT").val("");
			 $("#previewAddBeiZhu").dialog("close");
		 	}
		 },{
			 text:"确认", 
			 handler:function(){
			 if($("#COMMENT_CONTENT").val() == ""){
					alert("备注内容不能为空");
				}else{
					jQuery.ajax({
						url:_basePath+"/holiday/HolidayMain!doAddPbComment.action",
						type:"post",
						data:"PB_SEQ_ID="+seqId+"&COMMENT_TYPE="+TypeId+"&COMMENT_CONTENT="+$("#COMMENT_CONTENT").val(),
						dataType:"json",
						success:function(json){
							if(json.flag==true){
								alert("添加备注成功！");
								
							}else{
								alert("添加备注失败！");
							}
							$("#COMMENT_CONTENT").val("");
						}
					});
					$("#previewAddBeiZhu").dialog("close");
				}
		 	}
		 }]
	});
}

function searchBeiZhu(seqId,TypeId){
	jQuery.ajax({
        url:_basePath+"/holiday/HolidayMain!queryPbComment.action",
        type:"post",
        data:"PB_SEQ_ID="+seqId+"&COMMENT_TYPE="+TypeId,
        dataType:"json",
        success:function(returnedData){
					$("#COMMENT_CONTENT2").val(returnedData.data);
		}
     });
	 $("#getpreviewBeiZhu").dialog({
		 tetile:"查看备注",
		 		closed: false,
		 		cache: false,
				buttons :[{
					text:"关闭",
					handler : function(){
						$("#COMMENT_CONTENT2").value = "";
						$("#getpreviewBeiZhu").dialog("close");
					}					
				}]
			});
}
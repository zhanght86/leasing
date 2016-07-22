var count=0;
function myformatter(date){  
	 var y = date.getFullYear();
	 var m = date.getMonth()+1;
	 var d = date.getDate();  
	 return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);  
}
function myparser(s){  
	if (!s) return new Date();  
	var ss = (s.split('-'));  
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10); 
	var d = parseInt(ss[2],10);  
	 if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		 return new Date(y,m-1,d);  
	 }else{
		 return new Date();  
	 }
}

$(document).ready(function(){//<tr class="addData" style="height:30px;">
	$("#addR").click(function(){
		$("#addTbody").append($("<tr class='addData' style='height:30px;'>")
				.append("<td style='text-align:center'><input type='text' name='RECORD_DATE' class='easyui-datebox' id='RECORD_DATE"+count+"'/></td>")
				.append("<td style='text-align:center'><input type='text' name='INCOME'  onKeyUp='two(this)'/></td>")
				.append("<td style='text-align:center'><input type='text' name='GROWTH_RATE'  onKeyUp='two(this)'/></td>")
				.append("<td style='text-align:center'><a href='javascript:void(0)' class='easyui-linkbutton del' iconCls='icon-remove' plain='true' >删 除</a></td></tr>"));
		
		$("#RECORD_DATE"+count).datebox();
		count++;
//		var curr_time = new Date();     
//		var temp = "record_date"+count;
//		var tempTR=$(".templete").clone();
//		tempTR.find(".RECORD_DATE").attr("id",temp);
//		//$("#"+temp).datebox();
//		//$.parser.parse(tempTR);
//		alert($("#"+temp).attr("class"));
//		$("#addTbody").append(tempTR.removeClass("hidden templete"));
//		count++;
	});
	
	$("#addTbody").click(function(e){
		if($(e.target).is(".del")){
			$(e.target).parents("tr").remove();
		}
	});
	
	$("#addR1").click(function(){
		$("#addTbody1").append($("<tr class='addData1' style='height:30px;'>")
				.append("<td style='text-align:center'><input type='text' name='ANALYZE_DATE' class='easyui-datebox' id='ANALYZE_DATE"+count+"'/></td>")
				.append("<td style='text-align:center'><input type='text' name='GDP_YEAR'  onKeyUp='two(this)'/></td>")
				.append("<td style='text-align:center'><input type='text' name='GDP_YEAR_RATE'  onKeyUp='two(this)'/></td>")
				.append("<td style='text-align:center'><a href='javascript:void(0)' class='easyui-linkbutton del' iconCls='icon-remove' plain='true' >删 除</a></td></tr>"));
		
		$("#ANALYZE_DATE"+count).datebox();
		count++;
//		var tempTR=$(".templete1").clone();
//		$("#addTbody1").append(tempTR.removeClass("hidden templete1"));
	});
	
	$("#addTbody1").click(function(e){
		if($(e.target).is(".del1")){
			$(e.target).parents("tr").remove();
		}
	});
	
	//****************************************************************保存行业分析(三个函数)***********************************************************************	
	//保存行业简介 Add By:YangJ 2014-4-24 9:46:16
	$("#addIndustry").click(function(){			
		$('#AddIndustryForm').form('submit', {
			url : _basePath + '/analysisBySynthesis/Industry!doInertIndustry.action',
			success : function(date) {
				date = $.parseJSON(date);
				if (date.flag) {
					$.messager.alert("提示", "添加成功");
					
					//top.closeTab("新动态发表");
					$("textarea").attr("disabled", true);
					$("#INDUSTRY_CODE").attr("disabled", true);
					$("#INDUSTRY_NAME").attr("disabled", true);
					$("#addIndustry").unbind("click");
				} else {
					$.messager.alert("提示", "添加失败");
				}
			},
			error : function(e) {
				alert(e.message);
			}
		});
	});	
	
	//保存行业主要数据
	$("#baocunChild").click(function(){		
		var  saveRecord = new Array();
		$(".addData").each(function(){
	  		var temp={};
			temp.RECORD_DATE=$(this).find("[name=RECORD_DATE]").val();
			temp.INCOME=$(this).find("[name=INCOME]").val();
			temp.GROWTH_RATE=$(this).find("[name=GROWTH_RATE]").val();
			saveRecord.push(temp);
		});
		var addData ={
				HY_ID:$("#HY_ID").val(),
			CHILD_DATA:saveRecord
		};
		var alldata = JSON.stringify(addData);
		jQuery.ajax({
			url: "Industry!doInertIndustryChild.action",
			type:"post",
			data: "alldata="+alldata,
			dataType:"json",
			success: function(json){
				if(json.flag){
					alert("添加行业主要数据成功");
				}else{
					alert("添加行业主要数据失败 ");
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
			}
		}); 
	});	
	
	
	//保存行业分析
	$("#analyze").click(function(){	
		var  saveRecord = new Array();
		$(".addData1:not(.templete1)").each(function(){
	  		var temp={};
			temp.ANALYZE_DATE=$(this).find("[name=ANALYZE_DATE]").val();
			temp.GDP_YEAR=$(this).find("[name=GDP_YEAR]").val();
			temp.GDP_YEAR_RATE=$(this).find("[name=GDP_YEAR_RATE]").val();
			saveRecord.push(temp);
		});
		
		var addData ={
				HY_ID:$("#HY_ID").val(),
				ANALYZE:saveRecord
		};
		
		var alldata = JSON.stringify(addData);
		jQuery.ajax({
			url: "Industry!doInertIndustryANALYZE.action",
			type:"post",
			data: "alldata="+alldata,
			dataType:"json",
			success: function(json){
				if(json.flag){
					alert("添加行业分析成功");
				}else{
					alert("添加行业分析失败 ");
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
			}
		}); 
	});	
	
//****************************************************************修改行业分析(三个函数)***********************************************************************
		
	//修改行业简介 Add By:YangJ 2014-4-24 18:01:38
	$("#addIndustryUpdate").click(function(){	
		var data=getFromData("#UpdateIndustryForm");
		jQuery.ajax({
			url: "Industry!doUpdateINDUSTRY.action",//"$_basePath/analysisBySynthesis/Industry!doInertIndustry.action?CREDIT_ID=$!param.CREDIT_ID"
			type:"post",
			data: "json="+data,
			dataType:"json",
			success: function(i){
				if(i.data!=0){
					alert("修改行业简介成功");
					 $('#UpdateIndustryForm')[0].reset();
				}else{
					alert("修改行业简介失败 ");
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
			}
		}); 
	});
	
	//修改行业主要数据
	$("#UpdateChild").click(function(){
		//alert();
		var  saveRecord = new Array();
		$(".addData").each(function(){
	  		var temp={};
			temp.RECORD_DATE=$(this).find("[name=RECORD_DATE]").val();
			temp.INCOME=$(this).find("[name=INCOME]").val();
			temp.GROWTH_RATE=$(this).find("[name=GROWTH_RATE]").val();
			temp.INDUSTRY_ID=$(this).find("[name=INDUSTRY_ID]").val();
			temp.CHILD_ID=$(this).find("[name=CHILD_ID]").val();
			saveRecord.push(temp);
		});
		
		var addData ={
				HY_ID:$("#HY_ID").val(),
			CHILD_DATA:saveRecord
		};
		
		var alldata = JSON.stringify(addData);
		jQuery.ajax({
			url: "Industry!doUpdateIndustryChild.action",
			type:"post",
			data: "alldata="+alldata,
			dataType:"json",
			success: function(json){
				if(json.flag){
					alert("修改行业主要数据成功");
				}else{
					alert("修改行业主要数据失败 ");
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
			}
		}); 
	});
	
	
	//修改行业分析
	$("#analyzeUpdate").click(function(){
		var  saveRecord = new Array();
		$(".addData1").each(function(){
	  		var temp={};
			temp.ANALYZE_DATE=$(this).find("[name=ANALYZE_DATE]").val();
			temp.GDP_YEAR=$(this).find("[name=GDP_YEAR]").val();
			temp.GDP_YEAR_RATE=$(this).find("[name=GDP_YEAR_RATE]").val();
			temp.INDUSTRY_ID=$(this).find("[name=INDUSTRY_ID]").val();
			temp.ANALYZE_ID=$(this).find("[name=ANALYZE_ID]").val();
			saveRecord.push(temp);
		});
		
		var addData ={
				HY_ID:$("#HY_ID").val(),
				ANALYZE:saveRecord
		};
		
		var alldata = JSON.stringify(addData);
		jQuery.ajax({
			url: "Industry!doUpdateANALYZE.action",
			type:"post",
			data: "alldata="+alldata,
			dataType:"json",
			success: function(json){
				if(json.flag){
					alert("修改修改行业分析成功");
				}else{
					alert("修改修改行业分析失败 ");
				}
			},
			error: function(i){
				alert("网络连接失败，请重试");
			}
		}); 
	});
});
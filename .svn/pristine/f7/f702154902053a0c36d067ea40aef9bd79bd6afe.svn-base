$(function(){
	$("#yulButton").click(function(){
		var url=$("#url").val();
		var REPORT_TYPE=$("select[name='REPORT_TYPE']").val();//图标类型：1：折线图 2：柱形图  3：饼状图
		var reportLine=[];
		if(REPORT_TYPE=='3')
		{
				;
		}
		else
		{
			$(".reportLineTr").each(function(){
				var temp={};
				temp.LINE_NAME = $(this).find("[name='LINE_NAME']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D');
				temp.LINE_SQL = $(this).find("[name='LINE_SQL']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D');
				temp.LINE_STATE = $(this).find("[name='LINE_STATE']").attr("STATE");
				temp.BASETYPE= $(this).find("[name='LINE_STATE']").attr("BASETYPE");
				reportLine.push(temp);
			
			});
		}
			
		var dataJson ={
				reportLine:reportLine,
				REPORT_TYPE:$("select[name='REPORT_TYPE']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				KEY_VALUE:$("input[name='KEY_VALUE']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				REPORT_VALUE:$("input[name='REPORT_VALUE']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				COMPANY_REPORT:$("input[name='COMPANY_REPORT']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				XVALUE:$("input[name='XVALUE']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				YVALUE:$("input[name='YVALUE']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				COMPANY_FRONT:$("input[name='COMPANY_FRONT']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				SQL_VALUE:$("textarea[name='SQL_VALUE']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D'),
				COMPANY_AFTER:$("input[name='COMPANY_AFTER']").val().replace(new RegExp(/%/g),'%25').replace(new RegExp(/\+/g),'%2B').replace(new RegExp(/\//g),'%2F').replace(new RegExp(/\?/g),'%3F').replace(new RegExp(/#/g),'%23').replace(new RegExp(/&/g),'%26').replace(new RegExp(/=/g),'%3D')
		};
		$.ajax({
			type:'post',
			url:_basePath+'/reportBase/ReportBase!reportBaseYL.action',
			data : "reportParamAll="+JSON.stringify(dataJson),
			dataType:"text",
			success:function(dataBack){
				if(REPORT_TYPE=='1')
				{
					var str="<embed type='application/x-shockwave-flash' src='"+url+"/biReport/charts/MSLine.swf' id='ChartId' name='ChartId' quality='high' wmode='transparent' allowscriptaccess='always' flashvars='chartWidth=50%&chartHeight=99%&debugMode=0&DOMId=ChartId&registerWithJS=0&scaleMode=noScale&lang=EN&dataURL="+url+"/customer/Customer!downFile.action?PATH="+dataBack+"' height='600px' width='850px'>";
					$("#embValue").empty();
					$("#embValue").append(str);
				}
				else if(REPORT_TYPE=='2')
				{
					var str="<embed type='application/x-shockwave-flash' src='"+url+"/biReport/charts/MSColumn3D.swf' id='ChartId' name='ChartId' quality='high' wmode='transparent' allowscriptaccess='always' flashvars='chartWidth=50%&chartHeight=99%&debugMode=0&DOMId=ChartId&registerWithJS=0&scaleMode=noScale&lang=EN&dataURL="+url+"/customer/Customer!downFile.action?PATH="+dataBack+"' height='600px' width='850px'>";
					$("#embValue").empty();
					$("#embValue").append(str);
				}
				else if(REPORT_TYPE=='3')
				{
					var str="<embed type='application/x-shockwave-flash' src='"+url+"/biReport/charts/Pie3D.swf' id='ChartId' name='ChartId' quality='high' wmode='transparent' allowscriptaccess='always' flashvars='chartWidth=50%&chartHeight=99%&debugMode=0&DOMId=ChartId&registerWithJS=0&scaleMode=noScale&lang=EN&dataURL="+url+"/customer/Customer!downFile.action?PATH="+dataBack+"' height='600px' width='850px'>";
					$("#embValue").empty();
					$("#embValue").append(str);
				}
			},error:function(e){
				alert("你输入的SQL语句有问题，请检查后在预览！");
			}
	 	
		});
		
	});
	
	$("#resetAll").click(function(){
		$("#KEY_VALUE").attr("value","");
		$("#allId").attr("checked",false);
		$(".tmpId").attr("checked",false);
		$(".ywType").attr("value", "");
		$(".areaType").attr("value","");
	});
	//预览跳转
	$("#yulan").click(function(){
		if($(".groupType").val() == "HY_TYPE" && $(":input[name=hyType][checked]").length == '0'){
			alert("请选择行业类型");
			return;
		}
		if($(".groupType").val() == "YW_TYPE" && $(".ywType").val() == null){
			alert("请选择业务类型");
			return;
		}
		if($(".groupType").val() == "AREA_NAME" && $(".areaType").val() == null){
			alert("请选择区域");
			return;
		}
		var actionUrl = $("#reportBaseForm").attr("action");
		var url = actionUrl+"?flag=1";
		$("#newView").show();
		$("#reportBaseForm").attr("action",url);
		$("#reportBaseForm").submit();
		//还原action
		$("#reportBaseForm").attr("action",actionUrl);
	});
	
	
	$("#subsave").click(function(){
		var REPORT_TYPE=$("select[name='REPORT_TYPE']").val();//图标类型：1：折线图 2：柱形图  3：饼状图
		var reportLine=[];
		if(REPORT_TYPE=='3')
		{
				;
		}
		else
		{
			$(".reportLineTr").each(function(){
				var temp={};
				temp.LINE_NAME = $(this).find("[name='LINE_NAME']").val().replace("%","%25");
				temp.LINE_SQL = $(this).find("[name='LINE_SQL']").val();
				temp.LINE_STATE = $(this).find("[name='LINE_STATE']").attr("STATE");
				temp.BASETYPE= $(this).find("[name='LINE_STATE']").attr("BASETYPE");
				reportLine.push(temp);
			
			});
		}
			
		var dataJson ={
				reportLine:reportLine,
				REPORT_TYPE:$("select[name='REPORT_TYPE']").val().replace("%","%25"),
				KEY_VALUE:$("input[name='KEY_VALUE']").val().replace("%","%25"),
				REPORT_VALUE:$("input[name='REPORT_VALUE']").val().replace("%","%25"),
				COMPANY_REPORT:$("input[name='COMPANY_REPORT']").val().replace("%","%25"),
				XVALUE:$("input[name='XVALUE']").val().replace("%","%25"),
				YVALUE:$("input[name='YVALUE']").val().replace("%","%25"),
				COMPANY_FRONT:$("input[name='COMPANY_FRONT']").val().replace("%","%25"),
				SQL_VALUE:$("textarea[name='SQL_VALUE']").val(),
				COMPANY_AFTER:$("input[name='COMPANY_AFTER']").val().replace("%","%25")
		};
		$("#reportParamAll").val(JSON.stringify(dataJson));
		$("#reportBaseForm").submit();
	});
	
	
});


function LINE_CHECK(obj)
{
	var state=$(obj).attr("STATE");
	if(state=='0')
	{
		$(obj).attr("STATE","1")
		$(obj).val("已禁用");
	}
	else
	{
		$(obj).attr("STATE","0");
		$(obj).val("已启用");
	}
}

function changeType(obj)
{
	var type=$(obj).val();
	if(type=='1')//折线图
	{
		$("#reportTypeDiv").removeClass("hidden");
		$("input[name='XVALUE']").attr("disabled",false);
		$("input[name='YVALUE']").attr("disabled",false);
		$("#photosImg").attr("src","images/1.jpg");
	}
	else if(type=='2')//柱形图
	{
		$("#reportTypeDiv").removeClass("hidden");
		$("input[name='XVALUE']").attr("disabled",false);
		$("input[name='YVALUE']").attr("disabled",false);
		$("#photosImg").attr("src","images/2.jpg");
	}
	else//饼状图
	{
		$("#reportTypeDiv").addClass("hidden");
		$("input[name='XVALUE']").attr("disabled",true).val("");
		$("input[name='YVALUE']").attr("disabled",true).val("");
		$("#photosImg").attr("src","images/3.jpg");
	}
}

function toCreidt()
{
	window.location.href='ReportBase!insertReportBase.action';
}

function baseFlagUpd(obj)
{
	var BASE_FLAG='0';
	var flag=$(obj).attr("BASE_FLAG");
	if(flag=='0'){
		$(obj).text("已禁用");
		$(obj).attr("BASE_FLAG",'1');
		BASE_FLAG='1';
	}
	else
	{
		$(obj).text("已启用");
		$(obj).attr("BASE_FLAG",'0');
		BASE_FLAG='0';
	}
	
	var BASE_ID=$(obj).attr("BASE_ID");
	$.ajax({
		type:'post',
		url:_basePath+'/reportBase/ReportBase!reportBaseFlagUp.action',
		data : "BASE_FLAG="+BASE_FLAG+"&BASE_ID="+BASE_ID,
		success:function(dataBack){
			;
		},error:function(e){
			alert("操作失败，请联系管理员！");
		}
 	
	});
}

function baseViewPage(obj)
{
	var BASE_ID=$(obj).attr("BASE_ID");
	window.location.href='ReportBase!viewReportBasePage.action?ID='+BASE_ID;
}

function baseUpdPage(obj)
{
	var BASE_ID=$(obj).attr("BASE_ID");
	window.location.href='ReportBase!updateReportBasePage.action?ID='+BASE_ID;
}

function toSendTime()
{
	$.ajax({
		type:'post',
		url:_basePath+'/reportBase/ReportBase!sendTimeRun.action',
		success:function(){
		alert("数据加载成功！");;
		},error:function(e){
			alert("操作失败，请联系管理员！");
		}
	});
}

function key_Select(obj)
{
	var KEY_VALUE=$(obj).val();
	if(KEY_VALUE.length<=0)
	{
		alert("请输入KEY名称!");
		$(obj).focus();
	}
	else{
		$.ajax({
			type:'post',
			url:_basePath+'/reportBase/ReportBase!reportBaseKEY_NAME.action',
			data : "KEY_VALUE="+KEY_VALUE,
			success:function(dataBack){
				if(dataBack=='false')
				{
					alert("KEY名称已经存在,请重新输入!");
					$(obj).val("");
					$(obj).focus();
				}
			},error:function(e){
				alert("操作失败，请联系管理员！");
			}
		});
	}
}
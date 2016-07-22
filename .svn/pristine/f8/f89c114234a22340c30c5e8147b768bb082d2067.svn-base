function adjust(){
	var DEPEND_TIME=$("#DEPEND_TIME").val();
	if(DEPEND_TIME==''||DEPEND_TIME==null||DEPEND_TIME==undefined){
		$.messager.alert("提示","请先维护利率配置");
		return;	
	}
	var BATCH;
	$.messager.confirm("调息提示","确定要根据<font color='red'>"+DEPEND_TIME+"</font>的利率浮动比例做调息吗？",function(r){
		if(r){
			var adjustRateDate=$("#adjustRateDate").datebox('getValue');
			if(adjustRateDate==''||adjustRateDate==null){
				$.messager.alert("提示","请选择调息开始日期");
				return;
			}
			var PAYLIST_CODE=$("#PAYLIST_CODE").val();
			$.ajax({
				type:'post',
				url:_basePath+"/adjustRate/AdjustRate!getBatch.action",
				dataType:'json',
				success:function(json){
					if(json.flag){
						BATCH=json.data;
						//循环次数
						var count=0;
						adjustRate(BATCH,DEPEND_TIME,PAYLIST_CODE,adjustRateDate,count);
					}
				}
			});
		}
	});
}

/**
 * 调息
 * @param BATCH
 * @param DEPEND_TIME
 * @param PAYLIST_CODE
 * @param count
 * @author King 2014年9月23日
 */
function adjustRate(BATCH,DEPEND_TIME,PAYLIST_CODE,adjustRateDate,count){
	$.ajax({
		type:'post',
		url:_basePath+"/adjustRate/AdjustRate!adjustRate.action",
		data:"ADJUSTRATEDATE="+adjustRateDate+"&DEPEND_TIME="+DEPEND_TIME+"&PAYLIST_CODE="+PAYLIST_CODE+"&BATCH="+BATCH+"&COUNT="+count,
		dataType:'json',
		success:function (json){
			if(json.flag){
				if(json.msg=='end'){
					$.messager.alert("提示","调息进行中，请等候……");
					$('#pageTable').datagrid('load', {"param":""});
				}else{
					$('#pageTable').datagrid('load', {"param":""});
					count++;
//					adjustRate(BATCH,DEPEND_TIME,PAYLIST_CODE,adjustRateDate,count);
				}
			}else{
				$.messager.alert("失败","调息失败:"+json.msg);
			}
		}
	});	
}
/**
 * 查看调息结果明细
 * @param BATCH
 * @author King 2014年9月11日
 */
function showAdjustDetail(BATCH){
	top.addTab("调息结果查看",_basePath+"/adjustRate/AdjustRate!showAdjustDetail.action?BATCH="+BATCH);
}

/**
 * 数据恢复
 * @param BATCH
 * @author King 2014年9月17日
 */
function hfAdjustDetail(BATCH){
	$.messager.confirm("删除","确定要恢复该数据吗？",function(r){
		if(r){
			$.ajax({
				type:'post',
				url:_basePath+"/adjustRate/AdjustRate!returnPay.action",
				data:'BATCH='+BATCH,
				dataType:'json',
				success:function(json){
					if(json.flag){
						$.messager.alert("提示",json.msg);
						$('#pageTable').datagrid('load', {"param":""});
					}else{
						$.messager.alert("提示",json.msg);
					}
				}
			});
		}
	}); 
}

/**
 * 数据恢复
 * @param BATCH
 * @author King 2014年9月17日
 */
function hfAdjustDetail1(BATCH,PAYLIST_CODE){
	$.messager.confirm("删除","确定要恢复该数据吗？",function(r){
		if(r){
			$.ajax({
				type:'post',
				url:_basePath+"/adjustRate/AdjustRate!returnPay.action",
				data:'BATCH='+BATCH+"&PAYLIST_CODE="+PAYLIST_CODE,
				dataType:'json',
				success:function(json){
					if(json.flag){
						$.messager.alert("提示",json.msg);
						$('#pageTable').datagrid('load', {"param":""});
					}else{
						$.messager.alert("提示",json.msg);
					}
				}
			});
		}
	});
}

/**
 * 导出租金调整通知书
 * 
 * @author King 2014年9月25日
 */
function expAdjustRatePdf(){
	window.location.href=_basePath+"/adjustRate/AdjustRateNotice!expPdf.action";
}
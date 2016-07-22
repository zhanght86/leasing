$(function() {
	$('#retentionChecked').datagrid({
		url:_basePath+"/retentionRefund/RetentionRefund!toMgRetentionChedkedData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		onClickCell:onClickCell,
		columns:[[
		          {field:'RE_ID',align:'center',width:20,checkbox:true},
		          {field:'RE_CODE',align:'center',width:40,title:'付款单号'},
		          {field:'NAME',align:'center',width:40,title:'供应商'},
		          {field:'RE_MONEY',align:'center',width:40,title:'付款金额'},
		          {field:'RE_PROJECT_COUNT',align:'center',width:40,title:'项目数量'},
		          {field:'RE_DATE',align:'center',width:40,title:'付款日期'},
		          {field:'RE_REAL_TIME',align:'center',width:40,editor: {type:'datebox'},title:'实付日期'},
		          {field:'RE_STATUS',align:'center',width:40,title:'确认状态'},		          
		          ]],
		//以下为”+“号展看显示内容。
		 view : detailview,
		 detailFormatter : function(index, row) {
		  	return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
		  },
		 onExpandRow : function(index, row) {
		  	jQuery.ajax({
		  		url:_basePath+"/retentionRefund/RetentionRefund!findDetailApp.action?RE_ID="+row.RE_ID,  
		  		type:'post',
		  		dataType:'json',
		  		success: function(json){
		  			var data = {flag:json.flag,total:json.data.length,rows:json.data};
		  			var pRowIndex = "ddv-"+index;
		  			$('#ddv-'+index).datagrid({
		  				fitColumns:true,
		                singleSelect:true,
		  			    rownumbers:true,
		                loadMsg:'加载中...',
		                height:'auto',
		                columns:[[          
		                         {field:'ORG_NAME',align:'center',width:40,title:'供应商'},
		                         {field:'PRO_CODE',align:'center',width:40,title:'项编号'},
		                         {field:'CLIENT_NAME',align:'center',width:50,title:'客户名称'},
		                         {field:'EQUIPMENINFOS',align:'center',width:45,title:'租赁物类型'},
		                         {field:'LEASE_TOPRIC',align:'center',width:60,title:'租赁物购买价款'},
		                         {field:'START_DATE',align:'center',width:50,title:'起租确认日期'},
		                         {field:'ITEM_NAME',align:'center',width:50,title:'款项名称'},
		                         {field:'PLAN_DATE',align:'center',width:50,title:'退款计划日期'},
		                         {field:'ITEM_MONEY',align:'center',width:50,title:'款项金额'},
		                         {field:'SUP_NAME',align:'center',width:50,title:'收款单位'},                            
		                         {field:'PAY_DATE',align:'center',width:50,title:'租赁到期日'},
		                         {field:'FINISH_TYPE',align:'center',width:50,title:'结束方式'}
		                 ]],
		                       
		            onResize:function(){
		                  $('#retentionChecked').datagrid('fixDetailRowHeight',index);
		             },
		             onLoadSuccess:function(){
		                    setTimeout(function(){
		                        $('#retentionChecked').datagrid('fixDetailRowHeight',index);
		                    },0);
		             }
			  	});
	
			    $('#retentionChecked').datagrid('fixDetailRowHeight',index);
			    $('#ddv-'+index).datagrid("loadData",data);
		    }
		  });
		}
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var RE_PAYEE_UNIT = $("input[name='RE_PAYEE_UNIT']").val();
	var SUPPLIER_NAME = $("input[name='SUPPLIER_NAME']").val();
	$('#retentionChecked').datagrid('load', {
		"RE_PAYEE_UNIT" : RE_PAYEE_UNIT,
		"SUPPLIER_NAME" : SUPPLIER_NAME
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#RE_DATE1").datebox('clear');
	$("#RE_DATE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 获取参数
 * @return
 */
function getDetailL(){
	var getDetailData = [];
	var detailData = $("#retentionChecked").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.RE_ID = detailData[i].RE_ID;
		temp.RE_REAL_TIME = detailData[i].RE_REAL_TIME;
		getDetailData.push(temp);
	}
	return getDetailData;
}

function getDetailChecked(){
	var detailData = $("#retentionChecked").datagrid('getSelections');
    var RE_REAL_TIME = '';
    var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		RE_REAL_TIME = detailData[i].RE_REAL_TIME;		
		if(RE_REAL_TIME==undefined){
			$.messager.alert('警告','请填写实付日期！');
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}

/********************************************************************留购价退款审核*************************************************************************/
function toCheckedApp(){	
	if(getDetailL()==''){
		return $.messager.alert('警告','请填选择审核数据');
	}else{
		if(getDetailChecked()){
			var data = {};
			data["getDetailL"] = getDetailL();
			$.messager.confirm('提示','确认审核选中数据?',function(r){
				if (r){
				   $.ajax({
					   url:_basePath+"/retentionRefund/RetentionRefund!doCheckedRetention.action",
				   	   type:'post',
					   data:'data='+JSON.stringify(data),
					   dataType:'json',
					   success:function(json){
							if(json.flag==true){
								$.messager.alert("留购价退款审核","留购价退款审核成功！！");
							}else{
								$.messager.alert("留购价退款审核","留购价退款审核失败！！");
							}
							//页面刷新
					    	window.location.href = _basePath+"/retentionRefund/RetentionRefund!toMgRetentionChedked.action";
						}
				   });
				}
			});
		}	
	}
}

/********************************************************************留购价退款驳回*************************************************************************/
function doCancelRetention(){
	
	if(getDetailL()==''){
		return $.messager.alert('警告','请填选择驳回数据');
	}else{
		var data = {};
		data["getDetailL"] = getDetailL();
		$.messager.confirm('提示','确认驳回选中数据?',function(r){
			if (r){
			   $.ajax({
				   url:_basePath+"/retentionRefund/RetentionRefund!doCancelRetention.action",
			   	   type:'post',
				   data:'data='+JSON.stringify(data),
				   dataType:'json',
				   success:function(json){
						if(json.flag==true){
							$.messager.alert("留购价退款驳回","留购价退款驳回成功！！");
						}else{
							$.messager.alert("留购价退款驳回","留购价退款驳回失败！！");
						}
						//页面刷新
				    	window.location.href = _basePath+"/retentionRefund/RetentionRefund!toMgRetentionChedked.action";
					}
			   });
			}
		});
	}
}

/******************************************************以下为单元格可编辑设置******************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#retentionChecked').datagrid('validateRow', editIndex)) {
		$('#retentionChecked').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

/**
 * 单元格转为可编辑样式
 * @auth yx
 * @date 2013-09-28
 * @param index
 * @param field
 * @return
 */
function onClickCell(index, field) {
	$('#retentionChecked').datagrid('endEdit', editIndex);
	if (endEditing()) {
			$('#retentionChecked').datagrid('editCell', {index : index,field : field});
			editIndex = index;
		}
}
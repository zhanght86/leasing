$(document).ready(function(){ 
	$('#dialogDiv').dialog({
	    onClose:function(){
			$("#LEVEL_TYPE").val('');
			$("#PARENT_ID").val('');
			$("#SAVE_TYPE").val('');
			$("#ID").val('');
			$("input[name='SHORT_NAME']").val('');
			$("input[name='NAME']").val('');
			$("input[name='LESSEE_AREA']").val('');
			$("form").removeAttr('id');
			$("#tr1").hide();
	    }
	});
	
	window.onresize=resizeBannerImage;//当窗口改变宽度时执行此函数
});

function resizeBannerImage(){
	$('#guojia').datagrid('resize', {
		width:function(){return document.body.clientWidth;}
	});
	$('#shengfen').datagrid('resize', {
		width:function(){return document.body.clientWidth;}
	});
	$('#shi').datagrid('resize', {
		width:function(){return document.body.clientWidth;}
	});
	$('#quxian').datagrid('resize', {
		width:function(){return document.body.clientWidth;}
	});
}
    
function clickTrGuoJia(index,rowDate){
	$('#shi').datagrid('loadData', { total: 0, rows: [] });
	$('#quxian').datagrid('loadData', { total: 0, rows: [] });
	$("#PARENT_ID_shi").val('');
	$("#PARENT_ID_quxian").val('');
	clickTrEnd(index,rowDate,'shengfen');
}
function clickTrShengFen(index,rowDate){
	$('#quxian').datagrid('loadData', { total: 0, rows: [] });
	$("#PARENT_ID_quxian").val('');
	clickTrEnd(index,rowDate,'shi');
}
function clickTrShi(index,rowDate){
	clickTrEnd(index,rowDate,'quxian');
}

//查询下一级区域
function clickTrEnd(index,rowDate,type){
	$("#PARENT_ID_"+type).val('');
	var id = rowDate.ID;
    jQuery.ajax({
        url: "Area!selectSubset.action",
        data: "PARENT_ID=" + id,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
                var rows = [];
                for (var i = 0; i < json.data.length; i++) {
                    rows.push({
                        NAME: json.data[i].NAME,
                        LESSEE_AREA: json.data[i].LESSEE_AREA,
                        SHORT_NAME: json.data[i].SHORT_NAME,
                        ID: json.data[i].ID,
                        PARENT_ID:  json.data[i].PARENT_ID
                    });
                }
				$("#PARENT_ID_"+type).val(id);
                $("#"+type).datagrid('loadData', rows);
            } else {
				$.messager.alert('提示','加载失败！请与管理员联系！','warning');
            }
        }
    });
}

//添加页面
function dialogAdd(type){
	$("form").attr("id","fromDate");
	var title = '';
	$("#fromDate").find("td").eq(0).empty();
	$("#fromDate").find("td").eq(2).empty();
	$("#fromDate").find("td").eq(4).empty();
	$("input[name='SHORT_NAME']").removeAttr("disabled");
	if(type == 'guojia'){
		title = '添加国家';
		$("#fromDate").find("td").eq(0).append("国家名称：");
		$("#fromDate").find("td").eq(2).append("国家代号：");
	}
	if(type == 'shengfen'){
		title = '添加省份';
		$("#fromDate").find("td").eq(0).append("省份名称：");
		$("#fromDate").find("td").eq(2).append("区号：");
	}
	if(type == 'shi'){
		title = '添加市';
		$("#fromDate").find("td").eq(0).append("市名称：");
		$("#fromDate").find("td").eq(2).append("区号：");
		$("input[name='SHORT_NAME']").attr('disabled','true');
	}
	if(type == 'quxian'){
		title = '添加区/县';
		$("#fromDate").find("td").eq(0).append("区/县名称：");
		$("#fromDate").find("td").eq(2).append("区号：");
		$("input[name='SHORT_NAME']").attr('disabled','true');
	}
	 if (checkOperation(type)) {
	 	$("#SAVE_TYPE").val(type);
		$("#fromDate").find("td").eq(4).append("简写：");
		$("#dialogDiv").show();
		$("#dialogDiv").dialog({title:title});
		$("#dialogDiv").dialog('open');
	}
}

function genParentSel(type) {
	$("#tr1").show();
	$("#tr1 select").empty();
	var field = '';
	var level = 0;
	if(type=='shengfen') {
		field = 'guojias';
		level = 1;
	} else if(type=='shi') {
		field = 'shengfens';
		level = 2;
	} else if(type=='quxian') {
		field = 'shis';
		level = 3;
	}
	if(!window[field]) {
		$.ajax({
			url: 'Area!loadAreas.action',
			data: 'AREA_LEVEL='+level,
			dataType: 'json',
			async: false,
			success: function(json) {
				window[field] = json.data;//ID、NAME
			}
		});
	}
	var sel = $("#fromDate select");
	$.each(window[field], function(i,n){
		sel.append('<option value="'+n.ID+'">' + n.NAME + '</option>');
	});
}

//修改页面
function dialogUpdate(type){
	$("form").attr("id","fromDate");
    var title = '';
	$("#fromDate").find("td").eq(0).empty();
	$("#fromDate").find("td").eq(2).empty();
	$("#fromDate").find("td").eq(4).empty();
	$("input[name='SHORT_NAME']").removeAttr("disabled");
	if(type == 'guojia'){
		title = '修改国家';
		$("#fromDate").find("td").eq(0).append("国家名称：");
		$("#fromDate").find("td").eq(2).append("国家代号：");
	} else {
		genParentSel(type);
		if(type == 'shengfen'){
			title = '修改省份';
			$("#fromDate").find("td").eq(0).append("省份名称：");
			$("#fromDate").find("td").eq(2).append("区号：");
		}
		if(type == 'shi'){
			title = '修改市';
			$("#fromDate").find("td").eq(0).append("市名称：");
			$("#fromDate").find("td").eq(2).append("区号：");
			$("input[name='SHORT_NAME']").attr('disabled','true');
		}
		if(type == 'quxian'){
			title = '修改区/县';
			$("#fromDate").find("td").eq(0).append("区/县名称：");
			$("#fromDate").find("td").eq(2).append("区号：");
			$("input[name='SHORT_NAME']").attr('disabled','true');
		}
	}
	 if (checkOperation(type)) {
	 	var opts = $('#'+type).datagrid('getSelected');
		if (opts) {
			$("#ID").val(opts.ID);
			$("input[name='NAME']").val(opts.NAME);
			$("input[name='LESSEE_AREA']").val(opts.LESSEE_AREA);
			$("input[name='SHORT_NAME']").val(opts.SHORT_NAME);
			$("#tr1 option[value="+opts.PARENT_ID+"]").attr('selected', true);
		 	$("#SAVE_TYPE").val(type);
			$("#fromDate").find("td").eq(4).append("简写：");
			$("#dialogDiv").show();
			$("#dialogDiv").dialog({title:title});
			$("#dialogDiv").dialog('open');
		}else{
			$.messager.alert('提示','请选择要修改的数据！');
		}
	}
}

//添加and修改保存
function save(){
	var type = $("#SAVE_TYPE").val();
	$("#PARENT_ID").val($("#PARENT_ID_"+type).val());
	var opts = $('#'+type).datagrid('getSelected');
	var index = $('#'+type).datagrid('getRowIndex',opts);
	$("#LEVEL_TYPE").val(type);
	var url = '';
	if($("#ID").val()){
		url = 'Area!doUpdateArea.action?_dateTime='+new Date();
//		submitUpdate();
	}else{
		url = 'Area!doAddArea.action?_dateTime='+new Date();
//		submitAdd();
	}
	if($("input[name='NAME']").validatebox('isValid')){
		jQuery.ajax({
	        url: url,
	        data: "param="+getFromData("#fromDate"),
	        type: "post",
	        dataType: "json",
	        success: function(json){
                if($("#ID").val()){
                	if ($("#PARENT_ID").val() != $("#tr1 option:selected").val()) {
						$('#'+type).datagrid('deleteRow', index);
						var arr = ['guojia', 'shengfen', 'shi', 'quxian'];
						var startIndex = 0;
						$.each(arr, function(i,n){
							if(n==type) startIndex = i;
							if(startIndex && i>startIndex) $('#'+n).datagrid('loadData', {total:0, rows:[]});  
						});
						closeDialog();
					} else {
						successSaveUpdate(json);
					}
				}else{
					successSaveAdd(json);
				}
	        }
	    });
	}else{
		$.messager.alert('提示','请填写必填项！','warning');
	}
}

//function submitAdd(){
//	$('#fromDate').form({   
//		url:'Area!doAddArea.action?_dateTime='+new Date(),  
//		method:'get',
//		//若文本框加非空等校验需用到此段代码
//		onSubmit: function(){  
//			var check = $("#fromDate").form('validate');
//			if(!check){
//				 $.messager.alert('提示','请填写必填项！','warning');
//				 return false;
//			}
//		},   
//		success: function(json){
//			successSaveAdd(json);
//		}
//	});   
//	$('#fromDate').submit(); 
//}
//
//function submitUpdate(){
//	$('#fromDate').form({   
//		url:'Area!doUpdateArea.action?_dateTime='+new Date(),  
//		method:'get',
//		//若文本框加非空等校验需用到此段代码
//		onSubmit: function(){  
//			var check = $("#fromDate").form('validate');
//			if(!check){
//				 $.messager.alert('提示','请填写必填项！','warning');
//				 return false;
//			}
//		},   
//		success: function(json){
//			successSaveUpdate(json);
//		}
//	});   
//	$('#fromDate').submit(); 
//}

function successSaveAdd(json){
	var type = $("#SAVE_TYPE").val();
	if(json.flag){
		$('#'+type).datagrid('insertRow',{
			index: 0,
			row: {
				NAME: json.data.NAME,
                LESSEE_AREA: json.data.LESSEE_AREA,
                SHORT_NAME: json.data.SHORT_NAME,
                ID: json.data.ID,
                PARENT_ID: json.data.PARENT_ID
			}
		});
		closeDialog();
	}else{
		$.messager.alert('提示','保存失败！请与管理员联系！');
	}
}

function successSaveUpdate(json){
	var type = $("#SAVE_TYPE").val();
	var opts = $('#'+type).datagrid('getSelected');
	var index = $('#'+type).datagrid('getRowIndex',opts);
	if(json.flag){
 		$('#'+type).datagrid('updateRow', { 
			index: index, 
			row: { 
				NAME: json.data.NAME,
				LESSEE_AREA: json.data.LESSEE_AREA, 
				SHORT_NAME: json.data.SHORT_NAME,
				ID: json.data.ID,
				PARENT_ID: json.data.PARENT_ID
			} 
		});
		closeDialog();
	}else{
		$.messager.alert('提示','保存失败！请与管理员联系！');
	}
}

//校验是否选择了上级区域
function checkOperation(type){
	if(type != 'guojia'){
		if($("#PARENT_ID_"+type).val()){
			return true;
		}else{
			$.messager.alert('提示','请选择上级区域！','warning');
			return false;
		}
	}else{
		return true;
	}
}

//删除
function doDelete(type){
	if(checkOperation(type)){
		var opts = $('#'+type).datagrid('getSelected');
		var index = $('#'+type).datagrid('getRowIndex',opts);
		if(opts){
			$.messager.confirm('提示', '确定要删除"'+opts.NAME+'"这条数据吗？', function(r){
				if (r){
					var ID = opts.ID;
			        jQuery.ajax({
			            url: "Area!doDeleteArea.action",
			            data: "ID=" + ID,
			            type: "post",
			            dataType: "json",
			            success: function(json){
			                if (json.flag > 0) {
//			                    $.messager.alert('提示','删除成功！');
								$('#'+type).datagrid('deleteRow',index);
								if(type == 'guojia'){
									$('#shengfen').datagrid('loadData', { total: 0, rows: [] });
									$('#shi').datagrid('loadData', { total: 0, rows: [] });
									$('#quxian').datagrid('loadData', { total: 0, rows: [] });
								}
								if(type == 'shengfen'){
									$('#shi').datagrid('loadData', { total: 0, rows: [] });
									$('#quxian').datagrid('loadData', { total: 0, rows: [] });
								}
								if(type == 'shi'){
									$('#quxian').datagrid('loadData', { total: 0, rows: [] });
								}
			                }
			                else {
								$.messager.alert('提示','删除失败！');
			                }
			            }
			        });
				}
			});
		}else{
			$.messager.alert('提示','请选择要删除的数据！');
		}
	}
}

//关闭
function closeDialog(){
    $('#dialogDiv').dialog('close');
}

function getArea(selectId,method){
	var PARENT_ID = $('#' + selectId).combobox('getValue');
	var temp = new Array();
	if(selectId == 'GUOJIA_ID'){
		$('#QUYU_ID').combobox('clear');
		$('#SHENGFEN_ID').combobox('clear');
		$('#SHI_ID').combobox('clear');
		$('#QUYU_ID').combobox('loadData',temp);
		$('#SHENGFEN_ID').combobox('loadData',temp);
		$('#SHI_ID').combobox('loadData',temp);
	}
	if(selectId == 'QUYU_ID'){
		$('#SHENGFEN_ID').combobox('clear');
		$('#SHI_ID').combobox('clear');
		$('#SHENGFEN_ID').combobox('loadData',temp);
		$('#SHI_ID').combobox('loadData',temp);
	}
	if(selectId == 'SHENGFEN_ID'){
		$('#SHI_ID').combobox('clear');
		$('#SHI_ID').combobox('loadData',temp);
	}
	if(selectId != 'SHI_ID' && PARENT_ID != ''){
		jQuery.ajax({
		   url: _basePath+"/base/area/Area!"+method+".action?PARENT_ID="+PARENT_ID+"&_dateTime="+new Date(),
		   dataType:"json",
		   success: function(json){
				var data =json.data;
				if(data != null && data.length > 0){
					temp.push({"value": "", "text": "请选择"});
					for(var i = 0; i < data.length; i++){
						temp.push({"value":data[i].ID,"text":data[i].NAME});
					}
					if(selectId == 'GUOJIA_ID'){
						$('#QUYU_ID').combobox('loadData', temp);
					}else if(selectId == 'QUYU_ID'){
						$('#SHENGFEN_ID').combobox('loadData', temp);
					}else if(selectId == 'SHENGFEN_ID'){
						$('#SHI_ID').combobox('loadData', temp);
					}
				}
		   }
		});
	}
	setArea();
}

function setArea(){
	var GUOJIA_ID = $('#GUOJIA_ID').combobox('getValue');
	var QUYU_ID = $('#QUYU_ID').combobox('getValue');
	var SHENGFEN_ID = $('#SHENGFEN_ID').combobox('getValue');
	var SHI_ID = $('#SHI_ID').combobox('getValue');
	var AREA_ID = GUOJIA_ID + ',' + QUYU_ID + ',' + SHENGFEN_ID + ',' +  SHI_ID;
	$("#AREA_ID").val(AREA_ID);
	
	var GUOJIA_NAME = $('#GUOJIA_ID').combobox('getText');
	var QUYU_NAME = $('#QUYU_ID').combobox('getText');
	var SHENGFEN_NAME = $('#SHENGFEN_ID').combobox('getText');
	var SHI_NAME = $('#SHI_ID').combobox('getText');
	if(GUOJIA_NAME == '请选择'){ GUOJIA_NAME = ''; }
	if(QUYU_NAME == '请选择'){ QUYU_NAME = ''; }
	if(SHENGFEN_NAME == '请选择'){ SHENGFEN_NAME = ''; }
	if(SHI_NAME == '请选择'){ SHI_NAME = ''; }
	var AREA_NAME = GUOJIA_NAME + ',' + QUYU_NAME + ',' + SHENGFEN_NAME + ',' +  SHI_NAME;
	$("#AREA_NAME").val(AREA_NAME);
}
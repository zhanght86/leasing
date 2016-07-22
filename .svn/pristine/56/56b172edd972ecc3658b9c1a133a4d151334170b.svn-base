$(function(){
	var CASE_TYPE = $(".tab01-nav_active").attr("BStype");
	$("#CASE_TYPE").val(CASE_TYPE);
    $('#pageTable').datagrid({
    	view: detailview,
        columns:[[
		          {field:'TYPE_NAME',align:'left',width:100,title:'我司状态'},
		          {field:'STATUS_NAME',align:'left',width:100,title:'状态'},
		          {field:'CASE_NAME',align:'left',width:100,title:'案例类型'},
		          {field:'CASE_CODE',align:'left',width:100,title:'案件编号'},
		          {field:'TITLE_NAME',align:'left',width:150,title:'案件名称'},
		          {field:'CUST_NAME',align:'left',width:150,title:'客户名称'},
		          {field:'PROJECT_CODE',align:'left',width:150,title:'项目编号'},
		          {field:'PAYLIST_CODE',align:'left',width:150,title:'还款计划编号'},
		          {field:'SUPPER_NAME',align:'left',width:200,title:'供应商'},
		          {field:'AREA_NAME',align:'left',width:150,title:'区域'},
		          {field:'ALLEGED_MONEY',align:'left',width:150,title:'涉嫌金额'},
		          {field:'AREA_COURT',align:'left',width:200,title:'法院'},
		          {field:'COURT_TEL',align:'left',width:150,title:'联系方式'},
		          {field:'TRIAL_DATE',align:'left',width:100,title:'开庭时间'},
		          {field:'TRIAL_END',align:'left',width:100,title:'结案时间'},
		          {field:'RESULT_NAME',align:'left',width:100,title:'诉讼结果'},
		          {field:'ID',hidden:true},
		          {field:'STATUS',hidden:true},
		          {field:'TYPE',hidden:true},
		          {field:'main_compure',align:'center',title: '操作',width:200,formatter:function(value,rowData,rowIndex){
		        	  if(rowData.TYPE=='1'){
		        		  return "<a href='javascript:void(0);' onclick=litiViewFormS(" + rowData.ID + ")>查看</a> | "+
		        		  		 "<a href='javascript:void(0);' onclick=litiUpdateFormS(" + rowData.ID + ")>修改</a> | "+
		        		  	     "<a href='javascript:void(0);' onclick=deleteThis(" + JSON.stringify(rowData) + ")>删除</a>"; 
		        	  }
		        	  else{
		        		  return "<a href='javascript:void(0);' onclick=litiCreateForm(" + JSON.stringify(rowData) + ")>过程记录</a> | "+
		        		  	     "<a href='javascript:void(0);' onclick=deleteThis(" + JSON.stringify(rowData) + ")>删除</a>";  
		        	  }
		        	  
		          }
		      }
		    ]],
        detailFormatter:function(index,row){
	    	if(row.TYPE=='0'){
	    		return '<table class="table_02" cellpadding="0" cellspacing="0" id="ddv-' + index + '" style="padding:5px;"></table>';
	    	}
	    	else{
	    		return '';
	    	}
        },
        //展开层
        onExpandRow: function(index,row){
	        $('#ddv-'+index).panel({
	        	width:'100%',
			border:false,
			cache:false,
	        height:'auto',
	        href:_basePath+"/litigation_case/litigationCase!doShowDetailList.action?ID="+row.ID,
	        onLoad:function(){
		        $('#pageTable').datagrid('fixDetailRowHeight',index);
	        }
	        });
        }
        });
});

function seach(){
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm1")});
}

//
/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
	
	$("select[name=STATAUS]").val("");
	$("select[name=RESULT]").val("");
}

function changeTab(tabThis){
	$(".tab01-nav_active").attr("class","tab01-nav");
	$(tabThis).attr("class","tab01-nav_active");
	var CASE_TYPE = $(".tab01-nav_active").attr("BStype");
	if(CASE_TYPE=="0"){
		$("#pageTable").datagrid("hideColumn","CASE_NAME");
		$("#pageTable").datagrid("hideColumn","TITLE_NAME");
		$("#pageTable").datagrid("showColumn","TYPE_NAME");
		$("#pageTable").datagrid("showColumn","STATUS_NAME");
		$("#pageTable").datagrid("showColumn","CUST_NAME");
		$("#pageTable").datagrid("showColumn","PROJECT_CODE");
		$("#pageTable").datagrid("showColumn","PAYLIST_CODE");
		$("#pageTable").datagrid("showColumn","SUPPER_NAME");
		$("#pageTable").datagrid("showColumn","AREA_NAME");
		$("#pageTable").datagrid("showColumn","COURT_TEL");
		$(".removeClass").attr("style","display:none");
		$(".removeClassSELF").attr("style","");
		$(".removeClassSH").attr("style","display:none");
		
		emptyData();
	}else{
		$("#pageTable").datagrid("showColumn","CASE_NAME");
		$("#pageTable").datagrid("showColumn","TITLE_NAME");
		$("#pageTable").datagrid("hideColumn","TYPE_NAME");
		$("#pageTable").datagrid("hideColumn","STATUS_NAME");
		$("#pageTable").datagrid("hideColumn","CUST_NAME");
		$("#pageTable").datagrid("hideColumn","PROJECT_CODE");
		$("#pageTable").datagrid("hideColumn","PAYLIST_CODE");
		$("#pageTable").datagrid("hideColumn","SUPPER_NAME");
		$("#pageTable").datagrid("hideColumn","AREA_NAME");
		$("#pageTable").datagrid("hideColumn","COURT_TEL");
		$(".removeClass").attr("style","");
		$(".removeClassSELF").attr("style","display:none");
		$(".removeClassSH").attr("style","");
		emptyData();
	}
	$("#CASE_TYPE").val(CASE_TYPE);
	$("#pageTable").datagrid("load",{"param":getFromData("#pageForm1")});
}

function litiCreateForm(row){
	if (row){
		 top.addTab("添加过程",_basePath+"/litigation_case/litigationCase!litiCreateForm.action?ID="+row.ID+"&TYPE_NAME="+encodeURI(row.TYPE_NAME)+"&CUST_NAME="+encodeURI(row.CUST_NAME)+"&PROJECT_CODE="+row.PROJECT_CODE+"&PAYLIST_CODE="+row.PAYLIST_CODE+"&SUPPER_NAME="+encodeURI(row.SUPPER_NAME)+"&AREA_NAME="+encodeURI(row.AREA_NAME)+"&ALLEGED_MONEY="+row.ALLEGED_MONEY+"&AREA_COURT="+encodeURI(row.AREA_COURT)+"&COURT_TEL="+row.COURT_TEL+"&CASE_CODE="+encodeURI(row.CASE_CODE));
		 
	}else{
		$.messager.alert("请选择一个案例!");
	}
}

function createLiteSHCase(){
	top.addTab("添加社会案例",_basePath+"/litigation_case/litigationCase!createLiteSHCase.action");
}

function saveLitiCreateApp() {
	var D_HEAD_ID=$("#D_HEAD_ID").val();
	var D_STATAUS = $("select[name=D_STATAUS]").val();
	var D_TITLE_NAME = $("#D_TITLE_NAME").val();
	var D_CREATE_DATE = $("input[name=D_CREATE_DATE]").val();
	var D_RESULT = $("select[name=D_RESULT]").val();
	var D_TRIAL_DATE = $("input[name=D_TRIAL_DATE]").val();
	var D_TRIAL_END = $("input[name=D_TRIAL_END]").val();
	var D_JILU = $("#D_JILU").val();
	var D_FENXI = $("#D_FENXI").val();
	
	$.ajaxFileUpload( {
		type : "post",
		url : _basePath
				+ "/litigation_case/litigationCase!saveLitiCreateApp.action?D_HEAD_ID="
				+ D_HEAD_ID + "&D_STATAUS=" + D_STATAUS
				+ "&D_TITLE_NAME=" + encodeURI(D_TITLE_NAME) + "&D_TITLE_NAME="
				+ encodeURI(D_TITLE_NAME) + "&D_CREATE_DATE=" + D_CREATE_DATE
				+ "&D_RESULT=" + encodeURI(D_RESULT) + "&D_TRIAL_DATE="
				+ D_TRIAL_DATE + "&D_TRIAL_END=" + D_TRIAL_END + "&D_JILU="
				+ encodeURI(D_JILU) + "&D_FENXI=" + encodeURI(D_FENXI),
		secureuri : false,
		fileElementId : "FILEPATH",
		dataType : "json",
		success : function(json, status) {
			if (status) {
				$.messager.alert("提示", "添加诉讼过程成功");
				$("#addRecordButton").linkbutton("disable");
				$("#addRecordButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function saveLitiCreateS(){
	var S_TITLE_NAME=$("#S_TITLE_NAME").val();
	var S_TYPE = $("#S_TYPE").val();
	var S_CASE_CODE = $("input[name=S_CASE_CODE]").val();
	var S_RESULT = $("select[name=S_RESULT]").val();
	var S_AREA_COURT = $("input[name=S_AREA_COURT]").val();
	var S_ALLEGED_MONEY = $("input[name=S_ALLEGED_MONEY]").val();
	var S_TRIAL_DATE = $("input[name=S_TRIAL_DATE]").val();
	var S_TRIAL_END = $("input[name=S_TRIAL_END]").val();
	var S_JILU = $("#S_JILU").val();
	var S_FENXI = $("#S_FENXI").val();
	
	$.ajaxFileUpload( {
		type : "post",
		url : _basePath
				+ "/litigation_case/litigationCase!saveLitiCreateS.action?TYPE="
				+ S_TYPE +"&TITLE_NAME="+encodeURI(S_TITLE_NAME)+ "&CASE_CODE=" + S_CASE_CODE
				+ "&RESULT=" + encodeURI(S_RESULT) + "&AREA_COURT="
				+ encodeURI(S_AREA_COURT) + "&ALLEGED_MONEY=" + S_ALLEGED_MONEY
				+ "&TRIAL_DATE=" + S_TRIAL_DATE + "&TRIAL_END="
				+ S_TRIAL_END + "&JILU=" + encodeURI(S_JILU) + "&FENXI="
				+ encodeURI(S_FENXI),
		secureuri : false,
		fileElementId : "S_FILEPATH",
		dataType : "json",
		success : function(json, status) {
			if (status) {
				$.messager.alert("提示", "添加社会案例成功");
				$("#addSButton").linkbutton("disable");
				$("#addSButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function saveLitiUpdateS(){
	var ID = $("input[name=ID]").val();
	var S_TITLE_NAME=$("input[name=S_TITLE_NAME]").val();
	var DTAIL_ID = $("input[name=DTAIL_ID]").val();
	var S_CASE_CODE = $("input[name=S_CASE_CODE]").val();
	var S_RESULT = $("select[name=S_RESULT]").val();
	var S_AREA_COURT = $("input[name=S_AREA_COURT]").val();
	var S_ALLEGED_MONEY = $("input[name=S_ALLEGED_MONEY]").val();
	var S_TRIAL_DATE = $("input[name=S_TRIAL_DATE]").val();
	var S_TRIAL_END = $("input[name=S_TRIAL_END]").val();
	var S_JILU = $("#S_JILU").val();
	var S_FENXI = $("#S_FENXI").val();
	
	$.ajaxFileUpload( {
		type : "post",
		url : _basePath
				+ "/litigation_case/litigationCase!saveLitiUpdateS.action?ID="+ID+"&DTAIL_ID="+DTAIL_ID
				+ "&CASE_CODE=" + S_CASE_CODE+ "&TITLE_NAME=" + encodeURI(S_TITLE_NAME)
				+ "&RESULT=" + encodeURI(S_RESULT) + "&AREA_COURT="
				+ encodeURI(S_AREA_COURT) + "&ALLEGED_MONEY=" + S_ALLEGED_MONEY
				+ "&TRIAL_DATE=" + S_TRIAL_DATE + "&TRIAL_END="
				+ S_TRIAL_END + "&JILU=" + encodeURI(S_JILU) + "&FENXI="
				+ encodeURI(S_FENXI),
		secureuri : false,
		fileElementId : "S_FILEPATH",
		dataType : "json",
		success : function(json, status) {
			if (status) {
				$.messager.alert("提示", "修改社会案例成功");
				$("#updateSButton").linkbutton("disable");
				$("#updateSButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function litiSelfView(id){
	 top.addTab("查看过程",_basePath+"/litigation_case/litigationCase!litiSelfView.action?ID="+id);
}

function litiViewFormS(id){
	top.addTab("查看社会案例",_basePath+"/litigation_case/litigationCase!queryViewS.action?ID="+id);
}

function litiUpdateFormS(id){
	top.addTab("修改社会案例",_basePath+"/litigation_case/litigationCase!queryUpdateS.action?ID="+id);
}

function downloadFile(){
	var FILEPATH = $("#FILEPATH").val();
	var FILEPATH_NAME = $("#FILEPATH_NAME").val();
	alert(FILEPATH);
	alert(FILEPATH_NAME);
	alert(_basePath+"/litigation_case/litigationCase!downLoadRecordFile.action?file_url="+encodeURI(FILEPATH)+"&file_name="+encodeURI(FILEPATH_NAME));
	window.location.href=_basePath+"/litigation_case/litigationCase!downLoadRecordFile.action?file_url="+encodeURI(FILEPATH)+"&file_name="+encodeURI(FILEPATH_NAME); 
}

function deleteThis(row){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/litigation_case/litigationCase!deleteThis.action?ID="+encodeURI(row.ID),
					dataType : "json",
					success : function(json){
					if(json.flag){
						$('#pageTable').datagrid('load');
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}
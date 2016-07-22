$(function(){
	var DOSSIER_TYPE = $(".tab01-nav_active").attr("BStype");
	$("#DOSSIER_TYPE").val(DOSSIER_TYPE);
    $('#pageTable').datagrid({
        view: detailview,//显示与隐藏
        columns:[[
                  
                  {field:'STATUS',align:'center',width:5,formatter:function (value,rowData,rowIndex){
        				if(value=='3'){
        					return "<div class='icon-red'>&nbsp;</div>";
        				}else if(rowData.PORTFOLIO_NUMBER==''||rowData.PORTFOLIO_NUMBER==null||rowData.CABINET_NUMBER==''||rowData.CABINET_NUMBER==null){
        					return "<div class='icon-yellow'>&nbsp;</div>";
        				}else{
        					return "<div class='icon-green'>&nbsp;</div>";
        				}
        			}},
        			{field:'main_compure',align:'center',title: '操作',width:15,formatter:function(value,rowData,rowIndex){
  		        	  return "<a href='javascript:void(0);' onclick=toPigeonholeApplyForm('"+rowData.PROJECT_ID+"','"+rowData.CLIENT_ID+"','"+rowData.PROJECT_CODE+"-1')>归档申请</a>&nbsp;|&nbsp;"
  		        	  +"<a href='javascript:void(0);' onclick=toBorrowAppForm('"+rowData.PROJECT_CODE+"','"+rowData.CLIENT_ID+"','"+rowData.CABINET_NUMBER+"')>借阅申请</a>&nbsp;|&nbsp;"
  		        	  +"<a href='javascript:void(0);' onclick=toTransferAppForm('"+rowData.PROJECT_CODE+"','"+rowData.CLIENT_ID+"','"+rowData.CABINET_NUMBER+"')>移交申请</a>&nbsp;|&nbsp;"
  		        	  +"<a href='javascript:void(0);' onclick=toShowPhoto('"+rowData.PROJECT_ID+"')>资料预览</a>";  
  		          }
  		          },
		          {field:'PROJECT_CODE',align:'left',width:10,title:'融资租赁合同号',align:'center'},
		          {field:'CLIENT_NAME',align:'left',width:10,title:'客户名称',align:'center'},
		          {field:'PLATFORM_TYPE_NAME',align:'left',width:10,title:'业务类型',align:'center'},
		          
//		          {field:'TPM_BUSINESS_PLATE',align:'left',width:10,title:'商务板块'},
		          {field:'PORTFOLIO_NUMBER',align:'left',width:10,title:'档案袋编号',align:'center'},
		          {field:'CABINET_NUMBER',align:'left',width:10,title:'档案柜编号',align:'center'}
		          
		          ]],
        detailFormatter:function(index,row){
    	return '<table class="table_02" cellpadding="0" cellspacing="0" id="ddv-' + index + '" style="padding:5px;"></table>';
        },
        //展开层
        onExpandRow: function(index,row){
        //	alert('dfsfsfs'+row.PROJECT_CODE);
	        $('#ddv-'+index).panel({
	        	width:'100%',
				border:false,
				cache:false,
		        height:'auto',
		        href:_basePath+"/dossier/DossierManager!doShowDossierDetailList.action?itemid="
		        +row.itemid+"&PROJECT_CODE="+row.PROJECT_CODE+"&DOSSIER_TYPE="+encodeURI($("#DOSSIER_TYPE").val())
		        +"&PORTFOLIO_NUMBER="+row.PORTFOLIO_NUMBER+"&CABINET_NUMBER="+encodeURI(row.CABINET_NUMBER)+"&CLIENT_NAME="+encodeURI(row.CLIENT_NAME),
		        onLoad:function(){
			        $('#pageTable').datagrid('fixDetailRowHeight',index);
		        }
	        });  
        }
        
        
        });
});

function toPigeonholeApplyForm(PROJECT_ID,CLIENT_ID,PAYLIST_CODE){
	var DOSSIER_TYPE=$("#DOSSIER_TYPE").val();
	top.addTab("归档申请",_basePath+"/pigeonhole/Pigeonhole!toDossierConfirm1.action?PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&PAYLIST_CODE="+PAYLIST_CODE+"&DOSSIER_TYPE="+encodeURI(DOSSIER_TYPE));
}

function toBorrowAppForm(PROJECT_CODE,CLIENT_ID,CABINET_NUMBER){
	top.addTab("借阅申请",_basePath+"/borrow/DossierBorrow!toBorrowAppForm.action?PROJECT_CODE="+PROJECT_CODE+"&CLIENT_ID="+CLIENT_ID+"&CABINET_NUMBER="+CABINET_NUMBER);
}

function toTransferAppForm(PROJECT_CODE,CLIENT_ID,CABINET_NUMBER){
	top.addTab("移交申请",_basePath+"/Transfer/DossierTransferApp!toTransferAppForm.action?PROJECT_CODE="+PROJECT_CODE+"&CLIENT_ID="+CLIENT_ID+"&CABINET_NUMBER="+CABINET_NUMBER);
}

function toShowPhoto(PROJECT_ID){
	if(PROJECT_ID){
		top.addTab("资料预览",_basePath+"/dossier/DossierManager!DatPpreview.action?PROJECT_ID="+PROJECT_ID+"&YHBC=1&JFBG=1");
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function changeTab(tabThis){
	$(".tab01-nav_active").attr("class","tab01-nav");
	$(tabThis).attr("class","tab01-nav_active");
	var DOSSIER_TYPE = $(".tab01-nav_active").attr("BStype");
	$("#DOSSIER_TYPE").val(DOSSIER_TYPE);
	if(DOSSIER_TYPE=="合同档案"){
		$("#pageTable").datagrid("showColumn","PROJECT_CODE");
	}else{
		$("#pageTable").datagrid("hideColumn","PROJECT_CODE");
	}
	$("#pageTable").datagrid("load",{"param":getFromData("#pageForm1")});
}

function se(){
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm1")});
}

function clean(){
	$("#CLIENT_NAME").val('');
	$("#PORTFOLIO_NUMBER").val('');
	$("#CABINET_NUMBER").val('');
	$("#PLATFORM_TYPE_NAME").val('');
	$("#BUSINESS_PLATE").val('');
	$("#PROJECT_CODE").val('');
	$("#SUPPLIER_NAMES").val('');
}

function openUpLoadDialog(TYPE,ID){
	if(TYPE == '1'){
		$("#ID").val(ID);
		$("#FILE_TYPE").val(TYPE);
		$("#upDiv").show();
		$("#upDiv").dialog();
	}else{
		alert("暂无");
	}
}

function selectUpLoadDialog(ID) {
	$('#upShowDiv1').empty();
	$('#upShowDiv1').load(_basePath+"/dossier/DossierManager!selectFileList.action?ID="+ID+"&_datetime="+new Date().getTime());
	$("#upShowDiv").show();
	$('#upShowDiv').dialog();
}

function close(divId,formId) {
	$("#"+divId).hide();
	$("#"+divId).dialog('close');
	$("#"+formId).form('clear');
}

function upLoadFile(){
	$('#btnbc').linkbutton('disable');
	var ID = $("#ID").val();
	var FILE_TYPE = $("#FILE_TYPE").val();
	$('#upForm').form('submit', {
		url:_basePath+'/dossier/DossierManager!upLoadFile.action?ID='+encodeURI(ID)+"&FILE_TYPE="+encodeURI(FILE_TYPE)+"&_datetime="+new Date().getTime(),
		success:function(json){
			json = jQuery.parseJSON(json);
			if(json.flag){
				alert('上传成功');
				close('upDiv','upForm');
				$('#pageTable').datagrid('load');
			}else{
				alert(json.msg);
			}
		}
	});
}

function downFile(ID){
	var path = $("#"+ID).val();
	window.location.href=_basePath+"/dossier/DossierManager!downFile.action?path="+encodeURI(path)+"&_datetime="+new Date().getTime(); 
}

function deleteFile(ID){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			var path = $("#"+ID).val();
			jQuery.ajax({
				url : _basePath+"/dossier/DossierManager!deleteFile.action?path="+encodeURI(path)+"&_datetime="+new Date().getTime(),
					dataType : "json",
					success : function(json){
					if(json.flag){
						$("#upShowDiv").hide();
						$("#upShowDiv").dialog('close');
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}
/**
 * 选中
 * @param node
 * @author King 2014年8月10日
 */
function choseAllFile(node){
	$(node).parent("td").parent("tr").parent("thead").parent("table")
	.children("tbody").children("tr").children("td").children(".ID").each(function(){
		if($(node).attr("checked")=='checked'){
			$(this).attr("checked",true);
		}else{
			$(this).attr("checked",false);
		}
	});
}

/**
 * 接收选中文件
 * @param node
 * @author King 2014年8月10日
 */
function recieveFile(node){
	var idArray=new Array();
	$(node).parent("td").parent("tr").parent("tbody").children("tr").children("td").children(".ID").each(function(){
		if($(this).attr("checked")=='checked'){
			temp={};
			temp.ID=$(this).val();
			idArray.push(temp);
		}
	});
	if(idArray.length>=1){
		$.ajax({
			type:'post',
			url:_basePath+"/dossier/DossierManager!doRecieveFile.action",
			data:"param="+JSON.stringify(idArray),
			dataType:'json',
			success:function(json){
				if(json.flag){
					$.messager.alert("提示","选中的"+idArray.length+"个文件已接受"+json.data+"个");
					$(node).parent("td").parent("tr").parent("tbody").children("tr").children("td").children(".ID").each(function(){
						if($(this).attr("checked")=='checked'){
							$(this).parent("td").parent("tr").children("td").eq("6").html("已归档");
						}
					});
				}else{
					$.messager.alert("提示","接收失败:"+json.msg);
				}
			}
		});
	}else{
		$.messager.alert("提示","请选择需要接收的文件");
	}
}

/**
 * 接收单一档案文件
 * @param node
 * @author King 2014年8月10日
 */
function recieveFileOne(node){
	var temp={};
	temp.ID=$(node).parent("td").parent("tr").children("td").children("#ID").val();
	var idArray=new Array();
	idArray.push(temp);
	if(idArray.length>=1){
		$.ajax({
			type:'post',
			url:_basePath+"/dossier/DossierManager!doRecieveFile.action",
			data:"param="+JSON.stringify(idArray),
			dataType:'json',
			success:function(json){
				if(json.flag){
					$(node).parent("td").parent("tr").children("td").eq("6").html("已归档");
				}else{
					$.messager.alert("提示","接收失败:"+json.msg);
				}
			}
		});
	}
}

/**
 * 消毁一档案文件
 * @param node
 * @author King 2014年8月10日
 */
function destoryFileOne(node){
	var temp={};
	temp.ID=$(node).parent("td").parent("tr").children("td").children("#ID").val();
	var idArray=new Array();
	idArray.push(temp);
	if(idArray.length>=1){
		$.ajax({
			type:'post',
			url:_basePath+"/dossier/DossierManager!doDestoryFile.action",
			data:"param="+JSON.stringify(idArray),
			dataType:'json',
			success:function(json){
				if(json.flag){
					$(node).parent("td").parent("tr").children("td").eq("6").html("已销毁");
				}else{
					$.messager.alert("提示","接收失败:"+json.msg);
				}
			}
		});
	}
}

function chooseCabine(){
	var PORTFOLIO_HEAD=$("[name=PORTFOLIO_HEAD]").val();
	var hrows=$("input[name=PORTFOLIO_ROW]").val();
	var hline=$("input[name=PORTFOLIO_LINE]").val();
	$.ajax({
		type:"post",
		url:_basePath+"/pigeonhole/Pigeonhole!doShowCabRowLine.action",
		data:"PORTFOLIO_HEAD_1="+PORTFOLIO_HEAD,
		dataType:"json",
		success:function(json){
			if(json.flag){
				 var rows=json.data.ROW_NUM;
				 var lines=json.data.LINENUM;
				 var PORTFOLIO_ROW=$("#PORTFOLIO_ROW");
				 var PORTFOLIO_LINE=$("#PORTFOLIO_LINE");
				 PORTFOLIO_ROW.empty();
				 PORTFOLIO_LINE.empty();
				 for(var i=1;i<=rows;i++){
					var op1=$("<option>");
					op1.attr("value","00"+i);
					if(hrows=="00"+i){
						op1.attr("selected",true);
					}
					op1.text("00"+i);
					PORTFOLIO_ROW.append(op1);
				 }
				 for(var i=1;i<=lines;i++){
					var op1=$("<option>");
					op1.attr("value","00"+i);
					if(hline=="00"+i){
						op1.attr("selected",true);
					}
					op1.text("00"+i);
					PORTFOLIO_LINE.append(op1);					 
				 }
				 
			}else{
				$.messager.alert("提示","获取档案柜行列数据失败");
			}
		}
	});
}

/**
 * 档案入柜
 * @param node
 * @author King 2014年8月10日
 */
function dossierRG(node){
	var idArray=new Array();
	flag=true;
	$(node).parent("td").parent("tr").parent("tbody").children("tr").children("td").children(".ID").each(function(){
		if($(this).attr("checked")=='checked'){
			if($.trim($(this).parent("td").parent("tr").children("td").eq("6").text())=="未接收"){
				flag=false;
				return;
			}
			temp={};
			temp.ID=$(this).val();
			idArray.push(temp);
		}
	});
	if(flag==false){
		$.messager.alert("提示","请先接收文件，再做归档！");
		return;
	}
	var head=$(node).parent("td").children("#PORTFOLIO_HEAD").val();
	var row=$(node).parent("td").children("#PORTFOLIO_ROW").val();
	var line=$(node).parent("td").children("#PORTFOLIO_LINE").val();
	var PORTFOLIO_NUMBER=$(node).parent("td").children("#PORTFOLIO_NUMBER").val();
	if(head==""){
		$.messager.alert("提示","请选择档案柜编号");
		return;
	}else{
		head=head+"-"+row+"-"+line;
	}
	if(idArray.length>=1){
		$.ajax({
			type:'post',
			url:_basePath+"/dossier/DossierManager!dossierFileRG.action",
			data:"CABINET_NUMBER="+head+"&PORTFOLIO_NUMBER="+PORTFOLIO_NUMBER+"&param="+JSON.stringify(idArray),
			dataType:'json',
			success:function(json){
				if(json.flag){
					$.messager.alert("提示","选中的"+idArray.length+"个文件已入柜"+json.data+"个");
				}else{
					$.messager.alert("提示","入柜失败:"+json.msg);
				}
			}
		});
	}else{
		$.messager.alert("提示","请选择需要入柜的文件");
	}
}
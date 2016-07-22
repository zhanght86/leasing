$(document).ready(function(){
	$("#dialogProduct").dialog('close');
	var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	var PRO_CODE=$("input[name='PRO_CODE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var STATUS=$("select[name='STATUS']").val();
	var STATUS2=$("#STATUS2").val();//hxl
	$("#pageTable").datagrid({
		url:_basePath+"/project/project!pageAjax.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
		queryParams:{"PLATFORM_TYPE":PLATFORM_TYPE,"PRO_CODE":PRO_CODE,"CUST_NAME":CUST_NAME,"STATUS":STATUS,"STATUS2":STATUS2},
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
            	$('#pageTable').datagrid('selectRow', 0);
            	$("#PROJECT_ID_CONTRACT").val(data.rows[0].ID);
            	$("#PLATFORM_TYPE_CONTRACT").val(data.rows[0].PLATFORM_TYPE);
            	$("#STATUS_CONTRACT").val(data.rows[0].STATUS);
            }
        },
		onClickRow:function(index,data){
			$("#PROJECT_ID_CONTRACT").val(data.ID);
			$("#PLATFORM_TYPE_CONTRACT").val(data.PLATFORM_TYPE);
        	$("#STATUS_CONTRACT").val(data.STATUS);
		},
        frozenColumns:[[
					{field:'aaa',title:'操作',width:220,align:'center',formatter:function(value,row,rowIndex){
							 var STATUS=row.STATUS;
							 var LCNAME=row.LCNAME;
							 var rehtml="";
							 if(STATUS==0)
							 {
								rehtml = "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sechemSHow(" + JSON.stringify(row) + ")'>查看</a> | <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' plain='true' onclick='sechemUpdate(" + JSON.stringify(row) + ")'>修改</a> ";// +
								//rehtml = rehtml+" | <a target='myframe' href='"+_basePath+"/project/project!fundConfirmationLetter.action?pro_code="+row.PRO_CODE+"'>资金确认函</a>"
					//			var rehtml = rehtml+" | <a href='_basePath+"/project/project!fundConfirmationLetter.action?pro_code="+PRO_CODE' >资金确认函</a>"
								if(row.PLATFORM_TYPE !='4'){
									rehtml=rehtml+" | <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='appendScheme(" + JSON.stringify(row) + ")'>追加方案</a>";
								}
								
								if("true"==$("#isDelAuth").val()){
									if(row.PARENT_ID==null){
										rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + row.ID +  ")'>删除</a> ";
									}else{
										rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + row.PARENT_ID + ")'>删除</a> ";
									}
								} else
									//需要屏蔽 临时开放
									//rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + row.ID + "," + row.LCNAME  +")'>删除</a> ";
									rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + JSON.stringify(row) + ")'>删除</a> ";
									
								
					//"| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' plain='true' onclick='sechemExpPDF(" + JSON.stringify(row) + ")'>导出审批单</a> | <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' plain='true' onclick='confirExpPDF(" + JSON.stringify(row) + ")'>导出确认函</a>";
							 }else if(LCNAME !=undefined && STATUS==1&&LCNAME.indexOf('DB提交申请')>=0){
								 rehtml = "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sechemSHow(" + JSON.stringify(row) + ")'>查看</a> | <a  href='javascript:void(0)' class='easyui-linkbutton' onclick='lookJbpmList("+row.ID+")'>审批流程</a>";// +
								 //rehtml = rehtml+" | <a target='myframe' href='"+_basePath+"/project/project!fundConfirmationLetter.action?pro_code="+row.PRO_CODE+"'>资金确认函</a>"
								 //			var rehtml = rehtml+" | <a href='_basePath+"/project/project!fundConfirmationLetter.action?pro_code="+PRO_CODE' >资金确认函</a>"
								 
								 if("true"==$("#isDelAuth").val()){
										if(row.PARENT_ID==null){
											rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + row.ID + ")'>删除</a> ";
										}else{
											rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + row.PARENT_ID + ")'>删除</a> ";
										}
									} else
										//需要屏蔽 临时开放
										rehtml = rehtml+" | <a href='javascript:void(0)' onclick='delProject(" + row.ID + ")'>删除</a> ";
									 
							 }
							 else
							 {
								 rehtml="<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='sechemSHow(" + JSON.stringify(row) + ")'>查看</a>| <a  href='javascript:void(0)' class='easyui-linkbutton' onclick='lookJbpmList("+row.ID+")'>审批流程</a>";
								 rehtml+="| <a href='javascript:void(0)' onclick='delProject(" + JSON.stringify(row)+")'>删除</a> ";
								 // rehtml+="| <a href='javascript:void(0)' onclick='delProject(" + row.ID + "," + LCNAME  +")'>删除</a> ";
								 //	"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='dowStartBook(" + row.ID + ")'>起租说明书</a>"
									 //+" | <a  target='myframe' href='"+_basePath+"/project/project!fundConfirmationLetter.action?pro_code="+row.PRO_CODE+"'>资金确认函</a>"
//									 +"  | <a href='javascript:void(0)' onclick='fundConfirmationLetter(\"" + row.PRO_CODE + "\")' >资金确认函</a>"
									 
							 }
							 if(row.IS_READ==undefined && row.IS_READ==null && row.IS_READ != "is_read" && row.TTT_ID && row.TTT_ID != ""){
								 rehtml+= " | <a href='javascript:void(0)' onclick='doSh(" + row.ID + ")'>收回</a> ";
							 }
//							 rehtml=rehtml+" | <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='qzApp(" + JSON.stringify(row) + ")'>起租申请</a>";
							 if(row.PLATFORM_TYPE=='4' && row.ADD_PROJECT!='1' && row.LEASE_MODEL=='直接租赁业务'){
								 rehtml+= " | <a href='javascript:void(0)' onclick='add_project(" + row.CUST_ID + ",\""+ row.CUST_TYPE+"\",\""+row.CUST_NAME+"\",\""+row.PRO_CODE+"\")'>添加项目</a> ";
							  
							 }
							 return rehtml;
					 }
					}
                ]],
		columns:[[
		    {field:'LCNAME',title:'流程节点名称',width:100,align:'center'},
		   
		    //upd gengcb 20160121 start
	      	//{field:'STATUS_NAME',title:'状态',width:70,align:'center'},
		    {field:'STATUS_NAME',hidden:true},
	      	{field:'STATUS_NEW',title:'状态',width:100,align:'center'},
	        //upd gengcb 20160121 end
	      	{field:'ID',hidden:true,align:'center'},
	      	{field:'CUST_TYPE',hidden:true},
	      	{field:'CUST_ID',hidden:true},
	      	{field:'STATUS',hidden:true},
	      	//{field:'STATUS',hidden:true},
	      	{field:'PLATFORM_NAME',title:'业务类型',width:110,align:'center'},
	      	{field:'PRO_CODE',title:'项目编号',align:'center',width:130,formatter:function(value,rowData,rowIndex){
	      		//upd gengcb 20160120 JZZL-64  start
//	      		if(rowData.LCNAME=='01、立项申请'||rowData.LCNAME=='02、DB经理审批'||rowData.STATUS=='0'||rowData.STATUS>='20'){
//	      			return "<a href='javascript:void(0)'  onclick='sechemSHow(" + JSON.stringify(rowData) + ")'>"+rowData.PRO_CODE+"</a>";
//	      		}else {
//	      			return "<a href='javascript:void(0)'  onclick='liuchengShow(" + JSON.stringify(rowData) + ")'>"+rowData.PRO_CODE+"</a>";
//	      		}
	      		return "<a href='javascript:void(0)'  onclick='tabRightsShow(" + JSON.stringify(rowData) + ")'>"+rowData.PRO_CODE+"</a>";
	      		//upd gengcb 20160120 JZZL-64 end
	      		}},
	      	{field:'LEASE_CODE',title:'融资租赁合同号',width:130,align:'center'},	
	      	{field:'PRO_NAME',hidden:true},
	      	{field:'CUST_NAME',align:'center',title:'客户名称',width:130,formatter:function(value,rowData,rowIndex){
	        	  return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+rowData.CUST_NAME+"</a>";
	          }},
	      	{field:'CUST_TYPE_NAME',hidden:true},
	      	{field:'KHQD',title:'客户渠道',width:100,align:'center'},
	      	{field:'CLERK_NAME',title:'提件人',width:100,align:'center'},
	      	{field:'CREATE_TIME',title:'创建时间',width:100,align:'center'},
	      	{field:'APP_DATE',title:'进件时间',width:90,align:'center'},
	      	{field:'SHOP_NAME',title:'提件的网点',width:100,align:'center'},
	      	{field:'CUST_ID_INFO',title:'客户身份信息',width:140,align:'center'},
	      	{field:'SCHEME_NAME',title:'产品信息',width:100,align:'center'},
	      	{field:'OP_TIME',title:'上一个节点完成时间',width:100,align:'center'},
	      	//modify by lishuo 12.16.2015 start
	      	//{field:'STATUS_NEW',title:'其他状态',width:100,align:'center'},TODO
	      	{field:'FGS',title:'分公司',width:100,align:'center'},
	      	{field:'CATENA_NAME',title:'车系',width:100,align:'center'},
	      	{field:'WMEMO',title:'当前节点意见',width:100,align:'center'},
	      	//{field:'SCHEME_NAME1',title:'产品',width:100,align:'center'}, JZZL-211
	      	//{field:'TEL_PHONE',title:'电话号码',width:100,align:'center'},
	      	{field:'LEASE_TERM',title:'期限',width:100,align:'center'},
	      	{field:'FINANCE_TOPRIC',title:'融资金额',width:100,align:'center'},
	      	{field:'SALE_NAME',title:'销售人员',width:100,align:'center'},
	      	{field:'ENDTRIAL_UPDDATE',title:'终审时间',width:100,align:'center'},
	      /*	{field:'MEMO',title:'有条件通过',width:100,align:'center',formatter:function(value,rowData,rowIndex){
	      		if(undefined!=rowData.MEMO){
//	      			alert(rowData.JM_MEMO);
	      			var url="<a href='#' onclick=showPassContext('"+rowData.MEMO+"');>查看</a>";
	      			return url;
	      		}else {
	      			return "未找到意见";
	      		}
	      		}},*/
	      	//modify by lishuo 12.16.2015 end
	      	{field:'PLATFORM_TYPE',hidden:true}
	     ]]
	});
});


function showPassContext(value){
	$("#showPassContext").empty();
	$("#showPassContext").append("<textarea  style='display: block;height: 150px;width: 300px;' disabled='disabled'>"+value+"</textarea>");
	$("#showPassContext").show();
	$("#showPassContext").dialog({
		 title: '查看内容',
         collapsible: true,
         minimizable: true,
         maximizable: true,
         resizable: true,
         width: 300,
         height: 200,
         modal: true
  });
	
	$("#showPassContext").dialog('open');
}

function dowStartBook(PROJECT_ID){
	window.location.href=_basePath+"/pay/PayTask!doShowRentStartInfo.action?PROJECT_ID="+PROJECT_ID;
}

function add_project(CUST_ID,CUST_TYPE,CUST_NAME,PRO_CODE){
	 top.addTab(PRO_CODE+"添加项目",
	_basePath+"/project/project!lodingDiv.action?CUSTOMER_ID="+CUST_ID+"&CUSTOMER_TYPE="+CUST_TYPE+"&PLATFORM_TYPE=4&PROJECT_MODEL=4"
      +"&CUSTOMER_NAME="+CUST_NAME+"&LEASE_MODEL=back_leasing&PRO_CODE="+PRO_CODE);
}
/*modify by lishuo 12.24.2015 
增加仅删除流程节点为客服交叉质检或空的单子*/
function delProject(row){
	var userName =$("#userName").val();
	var STATUS_NAME =row.STATUS_NAME;
	if(userName == '超级管理员'){
		//admin
		if(confirm("当前项目删除后不可撤回，确认删除？")){
		$.ajax({
			url : _basePath+"/project/project!doDelProject.action?id="+row.ID,
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("项目删除成功！");
					$("#pageTable").datagrid("reload");
				}else{
					alert("该项目已签约不可删除！");
				}
			}
		});
	  }
	}else {
		//no admin 只能删除状态为"草稿"的和流程节点为"客服交叉质检"的单子
		if(confirm("当前项目删除后不可撤回，确认删除？")){
			if(row.LCNAME != undefined){
				var num  = row.LCNAME.indexOf('客服交叉质检');
			}
			if( STATUS_NAME=='草稿' ||  num > -1){ 
				$.ajax({
					url : _basePath+"/project/project!doDelProject.action?id="+row.ID,
					dataType :"json",
					success : function(json){
						if(json.flag){
							alert("项目删除成功！");
							$("#pageTable").datagrid("reload");
						}else{
							alert(json.msg);
						}
					}
				});
			}else{
				$.messager.alert("提示","抱歉，现仅支持删除流程节点名称为客服交叉质检和状态为草稿的单子!");
			}
		}
	}
}

function doSh(id){
	if(confirm("确认收回当前项目?")){
		$.ajax({
			url : _basePath+"/project/project!doSh.action?id="+id,
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("成功");
					$("#pageTable").datagrid("reload");
				}else{
					alert(json.msg);
				}
			}
		});
	}
}

function toViewCust(row) {
	var value=row.CUST_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}


function sechemSHowOld(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectShowOld.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function sechemSHow(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.CUST_NAME;
		 top.addTab(PRO_CODE,_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function appendScheme(row){
	if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PLATFORM_TYPE=row.PLATFORM_TYPE;
		 top.addTab(PRO_CODE+"追加设备方案",_basePath+"/project/project!appendScheme.action?PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function qzApp(row){
	if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"起租申请",_basePath+"/leaseApplication/LeaseApplication!queryEQList.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}


function sechemExpPDF(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PRO_NAME=row.PRO_NAME;
		 var CUST_NAME=row.CUST_NAME;
		 var CUST_TYPE_NAME=row.CUST_TYPE_NAME;
		 var CUST_TYPE=row.CUST_TYPE;
		 var PLATFORM_NAME=row.PLATFORM_NAME;
		 var AREA_NAME=row.AREA_NAME;
		 var CUST_ID=row.CUST_ID;
		 var ORG_NAME=row.ORG_NAME;
		 var LEASE_TOPRIC=row.LEASE_TOPRIC;
		 var BANK_NAME=row.BANK_NAME;
		 var POB_ID=row.POB_ID;
//		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID);
		 window.location.href = _basePath+"/project/project!projectExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID;
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function confirExpPDF(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PRO_NAME=row.PRO_NAME;
		 var CUST_NAME=row.CUST_NAME;
		 var CUST_TYPE_NAME=row.CUST_TYPE_NAME;
		 var CUST_TYPE=row.CUST_TYPE;
		 var PLATFORM_NAME=row.PLATFORM_NAME;
		 var AREA_NAME=row.AREA_NAME;
		 var CUST_ID=row.CUST_ID;
		 var ORG_NAME=row.ORG_NAME;
		 var LEASE_TOPRIC=row.LEASE_TOPRIC;
		 var BANK_NAME=row.BANK_NAME;
		 var POB_ID=row.POB_ID;
//		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID);
		 window.location.href = _basePath+"/project/project!confirmationExpPDF.action?PROJECT_ID="+PROJECT_ID+"&PRO_CODE="+PRO_CODE+"&PRO_NAME="+PRO_NAME+"&CUST_NAME="+CUST_NAME+"&CUST_TYPE_NAME="+CUST_TYPE_NAME+"&CUST_TYPE="+CUST_TYPE+"&PLATFORM_NAME="+PLATFORM_NAME+"&AREA_NAME="+AREA_NAME+"&CUST_ID="+CUST_ID+"&ORG_NAME="+ORG_NAME+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&BANK_NAME="+BANK_NAME+"&POB_ID="+POB_ID;
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function sechemUpdateOld(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab("项目立项",_basePath+"/project/project!projectUpdateOld.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}


function sechemUpdate(row)
{
	 if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.CUST_NAME;
		 top.addTab(PRO_CODE+"修改",_basePath+"/project/project!projectUpdate.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function lookJbpmList(PROJECT_ID){
	top.addTab("流程查看",_basePath+"/project/project!doShowProjectJbpmList.action?PROJECT_ID="+PROJECT_ID);
}

//查看流程信息  楊雪  2013-12-12
function liuchengShow(row){
	if (row){
		 var TTT_ID=row.TTT_ID;
		 var JM_ID  =row.JM_ID  ;
		 top.addTab(TTT_ID,_basePath+"/bpm/Task!toShowOld.action?MEMO_ID="+JM_ID+"&JBPM_ID"+encodeURI(TTT_ID));
	}else {
		
	}
}

//add gengcb 20160120 JZZL-64  start
function tabRightsShow(row,value){
	if (row){
		 var TTT_ID=row.TTT_ID;
		 var JM_ID  =row.JM_ID;
		 var projectId = row.ID;
		 var title = row.CUST_NAME+"."+projectId;
		 top.addTab(title,_basePath+"/bpm/Task!tabRightsShow.action?MEMO_ID="+JM_ID+"&JBPM_ID="+encodeURI(TTT_ID)
				 +"&STATUS="+encodeURI(row.STATUS)+"&PROJECT_ID="+encodeURI(projectId));
	}
}
//add gengcb 20160120 JZZL-64  end

/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 导出excel
 * @author 韩晓龙
 */
function exportExcel(){
	
	//params
	var searchParams = getFromData('#pageForm');
	
	$.messager.confirm("导出","确定要导出查询记录吗?",function(r){
		/*
		if(r){
			jQuery.ajax({
				url : _basePath + "/project/project!exportExcel.action",
				data : { "searchParams": searchParams},
				dataType:'json',
				success:function(data){
					
				}
			});
		}
		*/
		if(r){
			window.location.href = _basePath + "/project/project!exportExcel.action?searchParams=" + searchParams;
		}
	});
}

//根据条件取数据，需jQuery json支持
function getFromData(str) {
	var data = {};
	$(str + ' [name]').each(
			function() {
				if ($(this).is(":checkbox,:radio")) {
					if ($(this).attr("checked")) {
						data[$(this).attr("name")] = $(this).val();
					}
				} else {
					if ($(this).is("select")) {
						data[$(this).attr("name")] = $(this).find(":selected").val();
					} else {
						data[$(this).attr("name")] = $(this).val();
					}
				}
			});
	return JSON.stringify(data);
}

$(document).ready(function(){

	$("#pageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		//fitColumns:true,
		toolbar:'#pageForm',
		url:'Insurance!getinsureList.action?LEASE_CODE='+$("input[name='LEASE_CODE']").val(),
		frozenColumns:[[
		                {field:'AA',width:150,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
							if(rowData.INSURANCE_FLAG==1)
							{
								return "";
							}
							else{
								return "<a href='#'  onclick='addNewPolicy(" + JSON.stringify(rowData) + ")'>新建保单</a>|<a href='#'  onclick='closeNewPolicy(" + JSON.stringify(rowData) + ")'>停止新建</a>";
							}
							
		                	}}
		                ]],
		columns:[[

		          	{field:'FPEID',hidden:true},
		          	{field:'NAME',title:'客户名称',width:100,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同编号',width:100,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:100,align:'center'},
		          	{field:'FLAG',title:'业务类型',width:100,align:'center'},
		          	{field:'COMPANY_NAME',title:'厂商',width:100,align:'center'},
		          	{field:'SUPPLIERS_NAME',title:'经销商',width:100,align:'center'},
		          	{field:'PRODUCT_NAME',title:'品牌名称',width:100,align:'center'},
		          	{field:'SPEC_NAME',title:'品牌型号',width:100,align:'center'},
		          	{field:'CAR_SYMBOL',title:'整机编号/车架号',width:100,align:'center'},
		          	{field:'ENGINE_TYPE',title:'发动机编号',width:100,align:'center'}
	                  
		         ]],
		        view:detailview,
		 		detailFormatter : function(index, row) {
		 			return '<div id="ddv-' + row.FPEID + '" style="padding:5px 0;"></div>';
		 		},
				onExpandRow : function(index, row) {
					jQuery.ajax({
						url:_basePath+"/insure/Insurance!InsurancePolicy.action?ID="+row.FPEID,  
						type:'post',
						dataType:'json',
					    success: function(json){
							var data = {flag:json.flag,total:json.data.length,rows:json.data};
							var pRowIndex = "ddv-"+row.FPEID;
							$('#ddv-'+row.FPEID).datagrid({
								fitColumns:true,
								rownumbers:true,
								columns:[[
								          	{field:'INCU_CODE',align:'center',width:10,title:'保单编号'},
				                            {field:'INSURE_NAME',align:'center',width:15,title:'险种名称'},
				                            {field:'INSU_START_DATE',align:'center',width:15,title:'开始时间'},
				                            {field:'INSU_END_DATE',align:'center',width:10,title:'结束时间'},
				                            {field:'INSU_PRICE',align:'center',width:10,title:'保单金额'},
				                            {field:'BB',width:30,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
				    			       				return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='selPolicy(" + JSON.stringify(rowData) + ")'>查看</a> &nbsp;|&nbsp;" +
				    			       						"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='updPolicy(" + JSON.stringify(rowData) + ")'>修改</a> &nbsp;";
				    			       		 }}
								         ]],
								onResize:function(){
			                        $('#pageTable').datagrid('fixDetailRowHeight',index);
			                    },
			                    onLoadSuccess:function(){
			                        setTimeout(function(){
			                            $('#pageTable').datagrid('fixDetailRowHeight',index);
			                        },0);
			                    }
							});
							 $('#pageTable').datagrid('fixDetailRowHeight',index);
								$('#ddv-'+row.FPEID).datagrid("loadData",data);
					}
				});
		 		}
	});
});


var url = "";
function se(){
	var  NAME= $("#pageForm input[name='NAME']").val();
	var  LEASE_CODE= $("#pageForm input[name='LEASE_CODE']").val();
	var  PAYLIST_CODE= $("#pageForm input[name='PAYLIST_CODE']").val();
    $('#pageTable').datagrid('load', {"NAME":NAME,"LEASE_CODE":LEASE_CODE,"PAYLIST_CODE":PAYLIST_CODE});
    
}
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
//function setOperation(val,row,index) {
//	if(row.INSURANCE_FLAG==1)
//	{
//		return "";
//	}else{
//		return "<a href='#'  onclick='addNewPolicy(" + val + ")'>新建保单</a>|<a href='#'  onclick='closeNewPolicy(" + val + ")'>停止新建</a>";
//	}
//}
function addNewPolicy(row){
	//added by xingzhili 新建保单时，检查是否有正在生效的保单，如果有，就提示已经有保单了，否则，不提示。start
	var ID=row.FPEID;
	jQuery.ajax({
		url : _basePath + "/insure/Insurance!hasValidPolicy.action",
		data : { "ID": ID},
		dataType:'json',
		success:function(data){
			if(data.flag){
				if(confirm("该车辆已创建过保单，是否继续创建？")){
					top.addTab("新建保单", _basePath + "/insure/Insurance!toaddNewPolicy.action?ID=" + ID + "&PROJECT_ID=" + $("#PROJECT_ID").val());
				}
			}else{
				top.addTab("新建保单", _basePath + "/insure/Insurance!toaddNewPolicy.action?ID=" + ID + "&PROJECT_ID=" + $("#PROJECT_ID").val());
			}
		}
	});
	//added by xingzhili 新建保单时，检查是否有正在生效的保单，如果有，就提示已经有保单了，否则，不提示。end
}

function updPolicy(row){
	var ID=row.INCU_ID;
	top.addTab("修改保单", _basePath + "/insure/Insurance!updPolicy.action?ID=" + ID);
}

function closeNewPolicy(row){
	var ID=row.FPEID;
	$.messager.confirm("停止新建","确定要停止新建该设备的保单吗?",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/insure/Insurance!stopNewPolicy.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					if(data.flag){
						$.messager.alert("结果","停止新建成功！");
						//刷新页面
//						showInsureCompany();
						window.location.href=_basePath+"/insure/Insurance!execute.action?FPEID="+ID+"&LEASE_CODE="+$("input[name='LEASE_CODE']").val()
					}else
					{
						$.messager.alert("结果","停止新建失败！");
					}
				}
			});
		}
	});
}
function showInsureCompany(){
	top.closeTab("投保管理");
	top.addTab("投保管理", _basePath + "/insure/Insurance.action");
}
function getInsurance()
{
	$("#POLICY").empty();
	var ID=$("#INCP_ID").val();
	jQuery.ajax({
	url: _basePath + "/insure/Insurance!getInsuPolicyType.action?ID="+ID,
	type:"POST",
	dataType:"json",
	success:function(data){
				var company=data.data.map;
				var POLICY=data.data.list;
				$("#RECEIVE_UNIT").val(company.COMPANY_ADDR);
				$("#incp_phone").val(company.CONTACT_NUM);
				
				if(POLICY.length>0)
				{
					var products1="<option value=''>------请选择-------</option>";
					for(var i=0;i<POLICY.length;i++){
						products1=products1+"<option value='"+POLICY[i].ID+"'>"+POLICY[i].INSURE_NAME+"</option>";
					}
					$("#POLICY").append(products1);
				}
			}
	});
}
//验证保单号是否重复（同一保险公司重复）
function repeatInsuList(){
	var INCP_ID = jQuery.trim($("#INCP_ID").val());
	var INCU_CODE =jQuery.trim($("#INCU_CODE").val());
	jQuery.ajax({
					dataType:'json',
					url:  _basePath+"/insure/Insurance!repeatInsuList.action?INCP_ID="+INCP_ID+"&INCU_CODE="+INCU_CODE,
					success: function(b){
						if (b.flag) {
						}else
						{
							alert("保单号重复！");
						}
					}
				})
 }

/**
 * 保存保单
 * @returns {Boolean}
 */
function createInsuList()
{
	$("#addsave").linkbutton("disable");
	var url = _basePath+"/insure/Insurance!createInsuList.action";
	
	var INCP_ID=$("select[name='INCP_ID']").find("option:selected").val();
	if(null == INCP_ID || "" == INCP_ID){
		alert("请选择保险公司!");
		$("#addsave").linkbutton('enable');
		return false;
	}
	
	var POLICY=$("select[name='POLICY']").find("option:selected").val();
	if(null == POLICY || "" == POLICY){
		alert("请选择保单险种!");
		$("#addsave").linkbutton('enable');
		return false;
	}
	
	var INCU_CODE=$("input[name='INCU_CODE']").val();
	if(INCU_CODE==null || INCU_CODE=='')
	{
		alert("保单号不能为空!");
		$("#addsave").linkbutton('enable');
		return false;
	}
	
	var INSU_START_DATE=$("input[name='INSU_START_DATE']").val();
	if(null == INSU_START_DATE || "" == INSU_START_DATE){
		alert("投保期限开始时间不能为空!");
		$("#addsave").linkbutton('enable');
		return false;
	}
	
	var INSU_END_DATE=$("input[name='INSU_END_DATE']").val();
	if(null == INSU_END_DATE || "" == INSU_END_DATE){
		alert("投保期限结束时间不能为空!");
		$("#addsave").linkbutton('enable');
		return false;
	}
	
	if(INSU_START_DATE >= INSU_END_DATE){
		alert("投保期限开始时间不能大于结束时间!");
		$("#addsave").linkbutton('enable');
		return false;
	}
	
	// 保费差异原因验证，保费差额不为空时，需要选择保费差异原因
	var INSU_BALANCE = $("#INSU_BALANCE").val();
	var INSU_BALANCE_REMARK = $("#INSU_BALANCE_REMARK").val();
	if(null != INSU_BALANCE && "" != INSU_BALANCE){
			
		if(null == INSU_BALANCE_REMARK || "" == INSU_BALANCE_REMARK){
			alert("请选择保费差异原因!");
			$("#addsave").linkbutton('enable');
			return false;
		}
			
	}
	
	
	var INSU_NAME=$("input[name='INSU_NAME']").val();
	var INSU_ADDRESS=$("input[name='INSU_ADDRESS']").val();
	var MORTGAGE=$("input[name='MORTGAGE']").val();
	var INSU_CERTIFICATE=$("input[name='INSU_CERTIFICATE']").val();
	var TO_INSU_NAME=$("input[name='TO_INSU_NAME']").val();
	var TO_INSU_ADDRESS=$("input[name='TO_INSU_ADDRESS']").val();
	var INSU_PRICE_TOTAL=$("input[name='INSU_PRICE_TOTAL']").val();
	var INSU_PRICE=$("input[name='INSU_PRICE']").val();
	var INSU_PRICE_REBATE=$("input[name='INSU_PRICE_REBATE']").val();
	var REMARK=$("textarea[name='REMARK']").val();
	var TO_INSU_CERTIFICATE=$("input[name='TO_INSU_CERTIFICATE']").val();
	var EQMT_ID=$("input[name='eqmt_id']").val();
	
	var param = {
			INCU_CODE:INCU_CODE,
			INCP_ID:INCP_ID,
			INSU_NAME:INSU_NAME,
			INSU_ADDRESS:INSU_ADDRESS,
			MORTGAGE:MORTGAGE,
			MORTGAGE:MORTGAGE,
			INSU_CERTIFICATE:INSU_CERTIFICATE,
			TO_INSU_NAME:TO_INSU_NAME,
			TO_INSU_ADDRESS:TO_INSU_ADDRESS,
			INSU_PRICE_TOTAL:INSU_PRICE_TOTAL,
			INSU_PRICE:INSU_PRICE,
			INSU_PRICE_REBATE:INSU_PRICE_REBATE,
			INSU_END_DATE:INSU_END_DATE,
			INSU_START_DATE:INSU_START_DATE,
			REMARK:REMARK,
			TO_INSU_CERTIFICATE:TO_INSU_CERTIFICATE,
			EQMT_ID:EQMT_ID,
			POLICY:POLICY,
			INSU_BALANCE:INSU_BALANCE,
			INSU_BALANCE_REMARK:INSU_BALANCE_REMARK
	}
	jQuery.ajax({
		url:url,
		data:param,
		dataType:'json',
		success:function(b){
			if (b.flag) {
						alert("保存成功！");
						top.closeTab("新建保单");
					} else {
						alert("保存失败！");
						$("#addsave").linkbutton('enable');
				}
		   }
	});
}
function updInsuList()
{
	$("#updsave").linkbutton("disable");
	var url = _basePath+"/insure/Insurance!updInsuList.action";
	var INCU_ID=$("input[name='INCU_ID']").val();
	
	var INCP_ID=$("select[name='INCP_ID']").find("option:selected").val();
	if(null == INCP_ID || "" == INCP_ID){
		alert("请选择保险公司!");
		$("#updsave").linkbutton('enable');
		return false;
	}
	
	var POLICY=$("select[name='POLICY']").find("option:selected").val();
	if(null == POLICY || "" == POLICY){
		alert("请选择保单险种!");
		$("#updsave").linkbutton('enable');
		return false;
	}
	
	var INCU_CODE=$("input[name='INCU_CODE']").val();
	if(INCU_CODE==null || INCU_CODE=='')
	{
		alert("保单号不能为空!");
		$("#updsave").linkbutton('enable');
		return false;
	}
	
	var INSU_START_DATE=$("input[name='INSU_START_DATE']").val();
	if(null == INSU_START_DATE || "" == INSU_START_DATE){
		alert("投保期限开始时间不能为空!");
		$("#updsave").linkbutton('enable');
		return false;
	}
	
	var INSU_END_DATE=$("input[name='INSU_END_DATE']").val();
	if(null == INSU_END_DATE || "" == INSU_END_DATE){
		alert("投保期限结束时间不能为空!");
		$("#updsave").linkbutton('enable');
		return false;
	}
	
	if(INSU_START_DATE >= INSU_END_DATE){
		alert("投保期限开始时间不能大于结束时间!");
		$("#updsave").linkbutton('enable');
		return false;
	}
	
	// 保费差异原因验证，保费差额不为空时，需要选择保费差异原因
	var INSU_BALANCE = $("#INSU_BALANCE").val();
	var INSU_BALANCE_REMARK = $("#INSU_BALANCE_REMARK").val();
	if(null != INSU_BALANCE && "" != INSU_BALANCE){
			
		if(null == INSU_BALANCE_REMARK || "" == INSU_BALANCE_REMARK){
			alert("请选择保费差异原因!");
			$("#updsave").linkbutton('enable');
			return false;
		}
			
	}
	

	var INSU_NAME=$("input[name='INSU_NAME']").val();
	var INSU_ADDRESS=$("input[name='INSU_ADDRESS']").val();
	var MORTGAGE=$("input[name='MORTGAGE']").val();
	var INSU_CERTIFICATE=$("input[name='INSU_CERTIFICATE']").val();
	var TO_INSU_NAME=$("input[name='TO_INSU_NAME']").val();
	var TO_INSU_ADDRESS=$("input[name='TO_INSU_ADDRESS']").val();
	var INSU_PRICE_TOTAL=$("input[name='INSU_PRICE_TOTAL']").val();
	var INSU_PRICE=$("input[name='INSU_PRICE']").val();
	var INSU_PRICE_REBATE=$("input[name='INSU_PRICE_REBATE']").val();
	var REMARK=$("textarea[name='REMARK']").val();
	var TO_INSU_CERTIFICATE=$("input[name='TO_INSU_CERTIFICATE']").val();
	var EQMT_ID=$("input[name='eqmt_id']").val();
	var param = {
			INCU_CODE:INCU_CODE,
			INCP_ID:INCP_ID,
			INSU_NAME:INSU_NAME,
			INSU_ADDRESS:INSU_ADDRESS,
			MORTGAGE:MORTGAGE,
			INSU_CERTIFICATE:INSU_CERTIFICATE,
			TO_INSU_NAME:TO_INSU_NAME,
			TO_INSU_ADDRESS:TO_INSU_ADDRESS,
			INSU_PRICE_TOTAL:INSU_PRICE_TOTAL,
			INSU_PRICE:INSU_PRICE,
			INSU_PRICE_REBATE:INSU_PRICE_REBATE,
			INSU_END_DATE:INSU_END_DATE,
			INSU_START_DATE:INSU_START_DATE,
			REMARK:REMARK,
			TO_INSU_CERTIFICATE:TO_INSU_CERTIFICATE,
			EQMT_ID:EQMT_ID,
			POLICY:POLICY,
			ID:INCU_ID,
			INSU_BALANCE:INSU_BALANCE,
			INSU_BALANCE_REMARK:INSU_BALANCE_REMARK
	}
	jQuery.ajax({
		url:url,
		data:param,
		dataType:'json',
		success:function(b){
			if (b.flag) {
						alert("保存成功！");
						top.closeTab('修改保单');
					} else {
						alert("保存失败！");
						$("#updsave").linkbutton('enable');
				}
		   }
	});
}

/**
 * 显示查看保单
 * @param row
 */
function selPolicy(row)
{	
	var ID=row.INCU_ID;
	top.addTab("查看保单", _basePath + "/insure/Insurance!selPolicy.action?ID=" + ID);
}

/**
 * 显示修改保单
 * @param row
 */
function updPolicy(row)
{	
	var ID=row.INCU_ID;
	var PROJECT_ID = $("#PROJECT_ID").val();
	top.addTab("修改保单", _basePath + "/insure/Insurance!updPolicy.action?ID=" + ID + "&PROJECT_ID=" + PROJECT_ID);
}

/**
 * 计算商业险的差额
 */
function balanceMoney(){
	
	var INSU_PRICE = $("#INSU_PRICE").val();
	var INSU_PRICE_OLD = $("#INSU_PRICE_OLD").val();
	
	if(null != INSU_PRICE && '' != INSU_PRICE){
		
		if(null != INSU_PRICE_OLD && '' != INSU_PRICE_OLD){
			if(INSU_PRICE != INSU_PRICE_OLD){
				var balance = parseFloat(INSU_PRICE_OLD)-parseFloat(INSU_PRICE);
				$("#INSU_BALANCE").val(balance.toFixed(2));
			}else{
				$("#INSU_BALANCE").val(null);
			}
		}
		
	}else{
		$("#INSU_BALANCE").val(0);
	}
	
}

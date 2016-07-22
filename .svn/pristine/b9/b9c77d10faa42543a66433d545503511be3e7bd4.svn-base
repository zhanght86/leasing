$(document).ready(function(){
	$("#withDrawn_MG").datagrid({
		pagination:true,//是否分页 true为是
//		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit:true,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'FLAG_NAME',title:'状态',width:130},
		          	{field:'PROJECT_ID',hidden:true},
		          	{field:'PRO_CODE',title:'项目编号',width:130,formatter:function(value,rowData,rowIndex){
			      		return "<a href='javascript:void(0)'  onclick='sechemSHow22(" + JSON.stringify(rowData) + ")'>"+rowData.PRO_CODE+"</a>";
		      		}},
		          	{field:'CUST_NAME',title:'客户名称',width:130},
		          	{field:'SUPPLIER_NAMES',title:'供应商',width:130},
		          	{field:'COMPANY_NAMES',title:'厂商',width:130},
		          	{field:'PRODUCT_NAMES',title:'租赁物名称',width:130},
		          	{field:'EQUIPMENT_AMOUNTS',title:'台量',width:130},
		          	{field:'ENGINE_TYPES',title:'机型',width:130},
		          	{field:'WHOLE_ENGINE_CODES',title:'出厂编号',width:130},
		          	{field:'PROJECT_DATE',title:'立项日期',width:130},
		          	{field:'START_DATE',title:'起租确定日期',width:130},
		          	{field:'LEASE_TOPRIC',title:'租赁物价款',width:130},
		          	{field:'LEASE_TERM',title:'租赁期限',width:130},
		          	{field:'ITEM_MONEY_ALL',title:'租金总额',width:130},
		          	{field:'COUNTED',title:'已付期数',width:130},
		          	{field:'PAIDMONEYALL',title:'已付租金',width:130},
		          	{field:'COUNTNUM',title:'未付期数',width:130},
		          	{field:'REAYMONEYALL',title:'未付租金',width:130},
		          	{field:'DUN_PERIOD_COUNT',title:'逾期期数',width:130},
		          	{field:'PENALTY_RECE_ALL',title:'逾期金额',width:130},
		          	{field:'PLAN_STATUS',hidden:true},
		          	{field:'STATUS',hidden:true},
		          	{field:'CLIENT_ID',hidden:true},
		          	{field:'SUP_ID',hidden:true},
		       		{field:'AA',width:100,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
		       			if(rowData.PLAN_STATUS=='100')
		       			{
		       				if(rowData.STATUS=='1'){
		       					return "已作废";
		       				}
		       				return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='create_form_view(" + JSON.stringify(rowData) + ")'>查看（已作废）</a> &nbsp;";
		       			}
		       			else if(rowData.PLAN_STATUS=='101'){
		       				return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='create_form_view(" + JSON.stringify(rowData) + ")'>查看（作废中）</a> &nbsp;";
		       			}
		       			else
		       			{
		       				if(rowData.STATUS=='1')
		       				{
		       					return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='ZuoFei(" + JSON.stringify(rowData) + ")'>项目作废申请</a>&nbsp;";
		       				}
		       				else{
		       					return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='create_FORM(" + JSON.stringify(rowData) + ")'>项目作废申请</a>&nbsp;";
		       				}
		       				
		       			}
		        	 }}
		       	 ]]
		});
	
});


function sechemSHow22(row)
{
	
	 if (row){
		 var PROJECT_ID=row.PROJECT_ID;
		 var PRO_CODE=row.PRO_CODE;
		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
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
}

//直接作废
function ZuoFei(row){
	var PROJECT_ID=row.PROJECT_ID;
	var PRO_CODE=row.PRO_CODE;
	$.messager.confirm("删除","确定要作废"+PRO_CODE+"吗？",function(r){
		if(r){
			 $.ajax({
					type:"post",
					url:_basePath+"/project_withdrawn/project_withdrawn!whithDrawn_ZuoFei.action",
					data:"PROJECT_ID="+PROJECT_ID,
					dataType:"json",
					success:function(json){
						if(json.flag){
							alert("作废成功！");
							seach();
						}else{
							alert("作废失败"+json.msg);
						}
					},
					error:function(){
						$.messager.alert("提示","网络原因，请联系管理员");
					}
				});
		}
	});
}

function checkCount(){
	var YFMoney=0;
	$(".checktype").each(function(){
		if($(this).attr("checked")){
			YFMoney=jia(YFMoney,$(this).val());
		}
	});
	$("input[name='PAYABLE_MONEY']").val(YFMoney);
	$("input[name='PLAN_MONEY']").val(YFMoney);
}

//申请
function create_FORM(row)
{
	var PROJECT_ID=row.PROJECT_ID;
	var PROJECT_CODE=row.PRO_CODE;
	var STATUS=row.STATUS;
	var CUST_NAME=row.CUST_NAME;
	var SUPPLIER_NAMES=row.SUPPLIER_NAMES;
	var COMPANY_NAMES=row.COMPANY_NAMES;
	var CLIENT_ID=row.CLIENT_ID;
	var SUP_ID=row.SUP_ID;
	var PLAN_STATUS=row.PLAN_STATUS;
	 top.addTab(PROJECT_CODE+"作废申请",_basePath+"/project_withdrawn/project_withdrawn!create_FORM.action?PROJECT_ID="+PROJECT_ID+"&SUP_ID="+encodeURI(SUP_ID)+"&PROJECT_CODE="+encodeURI(PROJECT_CODE)+"&STATUS="+STATUS+"&CUST_NAME="+encodeURI(CUST_NAME)+"&SUPPLIER_NAMES="+encodeURI(SUPPLIER_NAMES)+"&COMPANY_NAMES="+encodeURI(COMPANY_NAMES)+"&CLIENT_ID="+CLIENT_ID+"&PLAN_STATUS="+PLAN_STATUS);
	
}

//查看
function create_form_view(row)
{
	var PROJECT_ID=row.PROJECT_ID;
	var PROJECT_CODE=row.PRO_CODE;
	var STATUS=row.STATUS;
	var CUST_NAME=row.CUST_NAME;
	var SUPPLIER_NAMES=row.SUPPLIER_NAMES;
	var COMPANY_NAMES=row.COMPANY_NAMES;
	var CLIENT_ID=row.COMPANY_NAMES;
	 top.addTab(PROJECT_CODE+"作废查看",_basePath+"/project_withdrawn/project_withdrawn!whithDrawn_view.action?PROJECT_ID="+PROJECT_ID+"&PROJECT_CODE="+encodeURI(PROJECT_CODE)+"&STATUS="+STATUS+"&CUST_NAME="+encodeURI(CUST_NAME)+"&SUPPLIER_NAMES="+encodeURI(SUPPLIER_NAMES)+"&COMPANY_NAMES="+encodeURI(COMPANY_NAMES)+"&CLIENT_ID="+CLIENT_ID);
	
}

function JBPM_START()
{
	var PAYEE_NAME=$("#PAYEE_NAME").val();
	var RECE_BANK=$("#RECE_BANK").val();
	var RECE_BANK_ADDREES=$("#RECE_BANK_ADDREES").val();
	var RECE_ACCOUNT=$("#RECE_ACCOUNT").val();
	var PLAN_MONEY=$("#PLAN_MONEY").val();
	var PLAN_DATE=$("input[name='PLAN_DATE']").val();
	
	if (PAYEE_NAME == ''){
		alert("请输入收款单位");
		return ;
	}
	
	if (RECE_BANK == ''){
		alert("请输入开户行");
		return ;
	}
	
	if (RECE_BANK_ADDREES == ''){
		alert("请输入开户行地址");
		return ;
	}
	
	if (RECE_ACCOUNT == ''){
		alert("请输入收款账号");
		return ;
	}
	var PROJECT_ID=$("#PROJECT_ID").val();
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var PROJECT_CODE=$("#PROJECT_CODE").val();
	var STATUS=$("#STATUS").val();
	var PLAN_STATUS=$("#PLAN_STATUS").val();
	var PAYMENT_MONEY=0;
	var RECEIVABLE_MONEY=0;
	var DUN_NUM=0;
	var DUN_BEGGING_MONEY=0;
	var DUN_MONEY=0;
	var PRINCIPAL_MONEY=0;
	if(STATUS=='3'){
		PAYMENT_MONEY=$("#PAYMENT_MONEY").val();
		RECEIVABLE_MONEY=$("#RECEIVABLE_MONEY").val();
		DUN_NUM=0;
	}
	else if(STATUS=='4'){
		RECEIVABLE_MONEY=$("#RECEIVABLE_MONEY").val();
		DUN_NUM=$("#DUN_NUM").val();
		DUN_BEGGING_MONEY=$("#DUN_BEGGING_MONEY").val();
		DUN_MONEY=$("#DUN_MONEY").val();
		PRINCIPAL_MONEY=$("#PRINCIPAL_MONEY").val();
	}
	var PAYABLE_MONEY=$("#PAYABLE_MONEY").val();
	
	var check_FEE=[];
	 $(".checktype").each(function(){
				var temp={};
				temp.KEY_NAME_EN=$(this).attr("name");
				temp.KEY_NAME_ZN=$(this).attr("ZN_NAME");
				temp.VALUE_STR=$(this).val();
				if($(this).attr("checked")){
					temp.CHECKISF=1;//1:选中
				}
				else{
					temp.CHECKISF=0;//1:未选中
				}
				check_FEE.push(temp);
	 });
	
	
		 $.ajax({
				type:"post",
				url:_basePath+"/project_withdrawn/project_withdrawn!whithDrawn_JBPM.action",
				data:"PROJECT_ID="+PROJECT_ID+"&PROJECT_CODE="+PROJECT_CODE+"&CLIENT_ID="+CLIENT_ID+"&SUPPLIER_ID="+SUPPLIER_ID+"&STATUS="+STATUS+"&PAYMENT_MONEY="+PAYMENT_MONEY+"&RECEIVABLE_MONEY="+RECEIVABLE_MONEY+"&DUN_NUM="+DUN_NUM+"&DUN_BEGGING_MONEY="+DUN_BEGGING_MONEY+"&PRINCIPAL_MONEY="+PRINCIPAL_MONEY+"&DUN_MONEY="+DUN_MONEY+"&PAYEE_NAME="+PAYEE_NAME+"&RECE_BANK="+RECE_BANK+"&RECE_BANK_ADDREES="+RECE_BANK_ADDREES+"&RECE_ACCOUNT="+RECE_ACCOUNT+"&PLAN_MONEY="+PLAN_MONEY+"&PLAN_STATUS="+PLAN_STATUS+"&PAYABLE_MONEY="+PAYABLE_MONEY+"&PLAN_DATE="+PLAN_DATE+"&check_FEE="+encodeURI(JSON.stringify(check_FEE)),
				dataType:"json",
				success:function(json){
					if(json.flag){
						alert("申请成功！");
					}else{
						alert("申请失败"+json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络原因，请联系管理员");
				}
			});
	
}

//精确计算浮点数 网上C的 加法
function jia(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
	m=Math.pow(10,Math.max(r1,r2));
	return (arg1*m+arg2*m)/m;
}

//查看客户
function toViewCust(CUST_ID,CUST_TYPE) {
	top.addTab("查看客户明细", _basePath+ "/customers/Customers!toViewCustInfo.action?CLIENT_ID=" + CUST_ID+ "&TYPE=" + CUST_TYPE + "&tab=view");

}

//查看项目
function sechemSHow(PROJECT_ID,PRO_CODE)
{
	top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	
}

function confirmRefundApp(){
	if($('#REALITY_MONEY').val() == ""){
		$.messager.alert("提示","请填写实际退款金额!");
		return;
	}
	if($('#REALITY_DATE').datebox("getValue") == ""){
		$.messager.alert("提示","请选择实际退款日期!");
		return;
	}
	$.messager.confirm("提示","您确认核销退款吗？",function(flag){
		if(flag){
			$('#form01').form('submit',{
		        url:_basePath+"/refundFirst/RefundFirst!doConfirmRefundOver.action",
		        success : function(date) {
					date = $.parseJSON(date);
					if (date.flag == true) {
						$.messager.alert("提示","核销退款成功!");
					} else {
						$.messager.alert("提示","核销退款失败请重试!");
					}
				},
				error : function(e) {
					$.messager.alert(e.message);
				}
		        
		    });
		}
		
	});
	
}
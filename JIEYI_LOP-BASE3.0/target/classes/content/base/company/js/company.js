var url="";

function update(row){
    if (row){
        //$('#dlg').dialog('open').dialog('setTitle','修改信息');
        //$('#fm').find('input').val("");
        //$('#fm').form('load',row);
        //$('#BUSINESS_SECTOR').combobox('select', row.BUSINESS_SECTOR);
        //url ="Company!saveCompany.action";
        top.addTab("厂商信息修改",_basePath+"/base/company/Company!updateCompany.action?COMPANY_ID="+row.COMPANY_ID);
    }
}

function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加');
    $('#fm').form('clear');
    $("#fm").find("input[name=STATUS][value=0]").attr("checked","checked");

    url = "Company!addCompany.action";
}
//修改厂商信息
function saveCompany(){
	 if(!checkSave()){
		 return ;
	 }
	 var comParams = getFromDate();
	 jQuery.ajax({
			url: "Company!saveCompany.action",
			data: "params="+comParams,
			dataType:"json",
			success: function(res){
				 if (res.flag==false){
					 $.messager.alert("提示",res.msg);
		         } else {
		        	 $.messager.alert("提示",res.msg);
		             $('#dlg').dialog('close');        
		             $('#pageTable').datagrid('reload');    
		         }
	        }
	 });
}

function getFromDate(){
	var temp = {};
	var data1 = {};
	var list1 = new Array();
	$("[SID=COMPANY]").each(function (){
		data1[$(this).attr("name")] = $(this).val();
	});
	data1[$("#frmSearch [name=START_TIME]").attr("name")] = $("#frmSearch [name=START_TIME]").val();
	data1[$("#frmSearch [name=STOP_TIME]").attr("name")] = $("#frmSearch [name=STOP_TIME]").val();
	data1[$("#frmSearch [name=PRODUCT_STATUS]:checked").attr("name")] = $("#frmSearch [name=PRODUCT_STATUS]:checked").val();
	data1[$("#frmSearch [name=STATUS]:checked").attr("name")] = $("#frmSearch [name=STATUS]:checked").val();
	list1.push(data1)
	temp.COMPANY = list1
	
	var data2 = {};
	var list2 = new Array();
	$("[SID=COMPANYINFO]").each(function (){
		data2[$(this).attr("name")] = $(this).val();
	});
	list2.push(data2);
	temp.COMPANYINFO = list2;
	return JSON.stringify(temp);

}

function del(row){
	if (row){
		 if(confirm("确定要删除该厂商吗？")){
			 jQuery.ajax({
				url: "Company!delCompany.action",
				data: "COMPANY_ID="+row.COMPANY_ID,
				dataType:"json",
				success: function(res){
					if(res.flag==true){
						$.messager.alert("提示",res.msg);
						$('#pageTable').datagrid('reload');
				   }
				   else{
					   $.messager.alert("提示",res.msg);
//					   alert("操作失败请重试！");
				   }
				}
					 
			 });
		 }
	}
}

function save(){
	if(!checkSave()){
		return ;
	}
	 jQuery.ajax({
			url: url,
			data:$("#fm").serialize(),
			dataType:"json",
			success: function(res){
				 if (res.flag==false){
					 $.messager.alert("提示",res.msg);
//		             $.messager.show({
//		                 title: '出错',
//		                 msg: res.msg
//		             });
		         } else {
		        	 $.messager.alert("提示",res.msg);
//		        	 $.messager.show({
//		                 title: '成功',
//		                 msg: res.msg
//		             });
		             $('#dlg').dialog('close');        
		             $('#pageTable').datagrid('reload');    
		         }
	        }
	 });
   
}

function checkSave(){
	 var flag = $('#frmSearch').form('validate');
	 if(!flag){
		 return false;
	 }
	 
	var COMPANY_CODE=$("input[name='COMPANY_CODE']").val();
	 if(COMPANY_CODE==null || COMPANY_CODE=="")
	 {
		 alert("厂商编号不能为空!");
		 return false;
	 }
	 var COMPANY_NAME=$("input[name='COMPANY_NAME']").val();
	 if(COMPANY_NAME==null || COMPANY_NAME=="")
	 {
		 alert("厂商名称不能为空!");
		 return false;
	 }
	 var COMPANY_SHORTNAME=$("input[name='COMPANY_SHORTNAME']").val();
	 if(COMPANY_SHORTNAME==null || COMPANY_SHORTNAME=="")
	 {
		 alert("厂商简称不能为空!");
		 return false;
	 }
//	 var ZIP=$("input[name='ZIP']").val();
//	 if(ZIP==null || ZIP=="")
//	 {
//		 alert("厂商邮编不能为空!");
//		 return false;
//	 }
//	 var FLAG=$("input[name='FLAG']").val();
//	 if(FLAG==null || FLAG=="")
//	 {
//		 alert("厂商标识不能为空!");
//		 return false;
//	 }
//	 var BUSINESS_SECTOR=$("#BUSINESS_SECTOR").combobox('getValue');
//	 if(BUSINESS_SECTOR==null || BUSINESS_SECTOR=="")
//	 {
//		 alert("所属商务板块不能为空!");
//		 return false;
//	 }
//	 var TAX_CODE=$("input[name='TAX_CODE']").val();
//	 if(TAX_CODE==null || TAX_CODE=="")
//	 {
//		 alert("纳税人识别号不能为空!");
//		 return false;
//	 }
//	 var OPEN_BANK=$("input[name='OPEN_BANK']").val();
//	 if(OPEN_BANK==null || OPEN_BANK=="")
//	 {
//		 alert("开户行不能为空!");
//		 return false;
//	 }
//	 var OPEN_BANK_ACCOUNT=$("input[name='OPEN_BANK_ACCOUNT']").val();
//	 if(OPEN_BANK_ACCOUNT==null || OPEN_BANK_ACCOUNT=="")
//	 {
//		 alert("开户行账号不能为空!");
//		 return false;
//	 }
//	 var OPEN_BANK_ADDR=$("input[name='OPEN_BANK_ADDR']").val();
//	 if(OPEN_BANK_ADDR==null || OPEN_BANK_ADDR=="")
//	 {
//		 alert("开户行地址不能为空!");
//		 return false;
//	 }
//	 var ORAGNIZATION_CODE=$("input[name='ORAGNIZATION_CODE']").val();
//	 if(ORAGNIZATION_CODE==null || ORAGNIZATION_CODE=="")
//	 {
//		 alert("组织机构代码不能为空!");
//		 return false;
//	 }
//	 var ADDRESS=$("input[name='ADDRESS']").val();
//	 if(ADDRESS==null || ADDRESS=="")
//	 {
//		 alert("厂商地址不能为空!");
//		 return false;
//	 }
//	 var LINK_MAN=$("input[name='LINK_MAN']").val();
//	 if(LINK_MAN==null || LINK_MAN=="")
//	 {
//		 alert("联系人姓名不能为空!");
//		 return false;
//	 }
//	 var LINK_IDCARD=$("input[name='LINK_IDCARD']").val();
//	 if(LINK_IDCARD==null || LINK_IDCARD=="")
//	 {
//		 alert("联系人身份证不能为空!");
//		 return false;
//	 }
//	 var LINK_TELEPHONE=$("input[name='LINK_TELEPHONE']").val();
//	 if(LINK_TELEPHONE==null || LINK_TELEPHONE=="")
//	 {
//		 alert("联系人电话不能为空!");
//		 return false;
//	 }
//	 var LINK_MOBILE=$("input[name='LINK_MOBILE']").val();
//	 if(LINK_MOBILE==null || LINK_MOBILE=="")
//	 {
//		 alert("联系人手机不能为空!");
//		 return false;
//	 }
//	 var LINK_EMAIL=$("input[name='LINK_EMAIL']").val();
//	 if(LINK_EMAIL==null || LINK_EMAIL=="")
//	 {
//		 alert("邮箱不能为空!");
//		 return false;
//	 }
	 
	var startDate = $("[name=START_TIME]").val();
	var stopDate = $("[name=STOP_TIME]").val();
	
	var d1 = new Date(startDate.replace(/-/g, "/"));
	var d2 = new Date(stopDate.replace(/-/g, "/")); 
	if (Date.parse(d1) - Date.parse(d2) > 0) {
		alert("结束日期 小于 开始日期");
		return false;
	} 
	 return true;
}
//修改页面
$(document).ready(function(){
$("#frmSearch [name=STATUS]").click( function(){
	var value = $("#frmSearch [name=STATUS]:checked").val();
	if(value == 1){
		$("#START_TIME").datebox({disabled:true});
		$("#STOP_TIME").datebox({disabled:true});
		$("#START_TIME").datebox('setValue',"");
		$("#STOP_TIME").datebox('setValue',"");
	}else {
		$("#START_TIME").datebox({disabled:false});
		$("#STOP_TIME").datebox({disabled:false});
	}
});
});
//添加页面
$(document).ready(function(){
	$("#fm [name=STATUS]").click( function(){
		var value = $("#fm [name=STATUS]:checked").val();
		if(value == 1){
			$("#fm_start_time").datebox({disabled:true});
			$("#fm_stop_time").datebox({disabled:true});
			$("#fm_start_time").datebox('setValue',"");
			$("#fm_stop_time").datebox('setValue',"");
		}else {
			$("#fm_start_time").datebox({disabled:false});
			$("#fm_stop_time").datebox({disabled:false});
		}
});
});
//初始化加载
$(function (){
	var value = $("#frmSearch [name=STATUS]:checked").val();
	if(value == 1){
		$("#START_TIME").datebox({disabled:true});
		$("#STOP_TIME").datebox({disabled:true});
		$("#START_TIME").datebox('setValue',"");
		$("#STOP_TIME").datebox('setValue',"");
	}else {
		$("#START_TIME").datebox({disabled:false});
		$("#STOP_TIME").datebox({disabled:false});
	}
	
	
});

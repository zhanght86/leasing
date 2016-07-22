var table1;
var ccb_sign_flag = '';
var bcm_sign_flag = '';
var icbc_sign_flag = '';

$(function(){
	table1 = $('#table1');
	table1.datagrid({
		url : 'Bank!doSelectAccountSignPageData.action',
		columns : [[
//			{field:'ID',checkbox:true,width:100},
			{field:'SUP_SHORTNAME',title:'供应商',width:100},
			{field:'PRO_CODE',title:'项目编号',width:100},
			{field:'CUST_TYPE',title:'客户类型',width:100},
			{field:'CUST_NAME',title:'客户姓名',width:150},
			{field:'BANK_CUSTNAME',title:'持卡人',width:100},
			{field:'BANK_NAME',title:'开户银行',width:100},
			{field:'BANK_ACCOUNT',title:'银行卡号',width:100},
			{field:'ID_CARD_NO',title:'身份证号',width:100},
			{field:'CREATE_TIME',title:'立项日期',width:100},
			{field:'CCB_SIGN_FLAG',title:'建行签约',width:80,
				editor:{
					type:'checkbox',options:{on:'已签约',off:'未签约'}
				}
			},
			{field:'ICBC_SIGN_FLAG',title:'工行签约',width:80,
				editor:{
					type:'checkbox',options:{on:'已签约',off:'未签约'}
				}
			},
			{field:'BCM_SIGN_FLAG',title:'交行签约',width:80,
				editor:{
					type:'checkbox',options:{on:'已签约',off:'未签约'}
				}
			}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
//		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
//		singleSelect : true,
//		checkOnSelect : true,
		nowrap : true,
//		rownumbers : true,
		pageSize : 20,
		pageList : [10,20,50,100,200,300],
		onClickRow: onClickRow,
		onAfterEdit : onAfterEdit
	});
	
});
var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true;}
    if (table1.datagrid('validateRow', editIndex)){
        table1.datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickRow(index,rowData){
    //if (editIndex != index){
    if (endEditing()){
        table1.datagrid('beginEdit', index);//.datagrid('selectRow', index)
                
        ccb_sign_flag = rowData.CCB_SIGN_FLAG;
        icbc_sign_flag = rowData.ICBC_SIGN_FLAG;
        bcm_sign_flag = rowData.BCM_SIGN_FLAG;
        editIndex = index;
    } else {
        table1.datagrid('selectRow', editIndex);
    }
    //}
}
function onAfterEdit(rowIndex, rowData, changes){
	var changeFlag = false;
	var changeValues = '';
	if(ccb_sign_flag != rowData.CCB_SIGN_FLAG){
		changeValues += '&ccb_sign_flag='+rowData.CCB_SIGN_FLAG;
		changeFlag = true;
	}
	if(icbc_sign_flag != rowData.ICBC_SIGN_FLAG){
		changeValues += '&icbc_sign_flag='+rowData.ICBC_SIGN_FLAG;
		changeFlag = true;
	}
	if(bcm_sign_flag != rowData.BCM_SIGN_FLAG){
		changeValues += '&bcm_sign_flag='+rowData.BCM_SIGN_FLAG;
		changeFlag = true;
	}
	if(!changeFlag){return;}
	$.messager.confirm('确认','确定要执行此操作?',function(flag){
		if(flag){
			$.ajax({
				url : "Bank!doUpdateAccountSignInfo.action",
				type : "POST",
				dataType:"json",
				data : "bank_account="+ rowData.BANK_ACCOUNT+"&bank_name="+ rowData.BANK_NAME+"&BANK_CUSTNAME="+ rowData.BANK_CUSTNAME+changeValues,
				success : function(msg){
					$.messager.show({
		                title:'操作反馈',
		                msg:msg.msg,
		                showType:'show'
		            });
					table1.datagrid('reload');
				}
			});
		}else{
			table1.datagrid('reload');
		}
	});
}
//查询
function se(){
	var searchParams = getFromData('#toolbar');
	table1.datagrid('load',{"searchParams":searchParams});
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

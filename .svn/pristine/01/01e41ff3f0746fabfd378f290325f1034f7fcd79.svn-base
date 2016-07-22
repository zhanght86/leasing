
			$(document).ready(function(){
				$("#pageTable").datagrid({
					url:_basePath+"/letterOpinion/LetterOpinion!getLetterOpinionPage.action",
					pageSize:20,
					fit : true,
					pagination : true,
					rownumbers : true,
					singleSelect : true,
					toolbar : "#pageForm",
                    columns:[[
						{field:'xsry',width:200,title:'信审人员',formatter:xsry,align:'center'},
						{field:'START_DATE',width:100,title:'进件日期',align:'center'},
						{field:'ZS_DATE',width:100,title:'终审日期',align:'center'},
						{field:'PRO_CODE',width:120,title:'项目号',align:'center'},
						{field:'STATUS_NEW',width:70,title:'状态',align:'center'},
						{field:'NAME',width:80,title:'客户',align:'center'},
						{field:'ID_CARD_NO',width:140,title:'身份证',align:'center'},
						{field:'SCHEME_NAME',width:120,title:'产品',align:'center'},
						{field:'LEASE_TERM',width:60,title:'期数',align:'center'},
						{field:'FINANCE_TOPRIC',width:80,title:'融资额',align:'center'},
						{field:'SPEC_NAME',width:80,title:'车型',align:'center'},
						{field:'FGS',width:100,title:'分公司',align:'center'},
						{field:'SHOP_NAME',width:100,title:'门店',align:'center'}
                    ]]
                });
			});

function xsry(value, rowData){
	
	if(rowData.FIRSTTRIAL_PERSON != null && rowData.FIRSTTRIAL_PERSON != ''
		&& rowData.FIRSTTRIAL_PERSON != undefined && rowData.ENDTRIAL_PERSON != null 
		&& rowData.ENDTRIAL_PERSON != '' && rowData.ENDTRIAL_PERSON != undefined){
		return "初审人:"+rowData.FIRSTTRIAL_PERSON + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终审人:"+rowData.ENDTRIAL_PERSON;
	}else if(rowData.FIRSTTRIAL_PERSON != null && rowData.FIRSTTRIAL_PERSON != ''
		&& rowData.FIRSTTRIAL_PERSON != undefined){
		return "初审人:"+rowData.FIRSTTRIAL_PERSON;
	}else if(rowData.ENDTRIAL_PERSON != null && rowData.ENDTRIAL_PERSON != ''
		&& rowData.ENDTRIAL_PERSON != undefined){
		return "终审人:"+rowData.ENDTRIAL_PERSON
	}else {
		return "";
	}
}
function search1(){
	$('#pageTable').datagrid('load', { 
		"LETTEROPERSON":$("#LetterOPerson").val(),
		"TS_DATE":$("#ts_date").val(),
		"ZS_DATE":$("#zs_date").val()
		});
}
$(document).ready(function(){
				
				$("#search").click(function(){
					$('#pageTable').datagrid('load', { 
						"TYPE":$("[name='TYPE']").val(),
						"FI_CERTIFICATE":$("[name='FI_CERTIFICATE']").val()
						,"START_DATE":$("[name='START_DATE']").val()
						,"END_DATE":$("[name='END_DATE']").val()
					});
				});
				
				$("#pageTable").datagrid({
					url:_basePath+"/credentials/Credentials!getMsgPage.action",
					onSelect: function(rowIndex, rowData){
						if($("input[type='checkbox']")[rowIndex+1].disabled){
							$('#pageTable').datagrid('unselectRow', rowIndex);
						}
					},
					onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
			            if (data.rows.length > 0) {
			                //循环判断操作为新增的不能选择
			                for (var i = 0; i < data.rows.length; i++) {
			                	var ORDERID=data.rows[i].ORDERID;
			                	if(ORDERID == '2'){
			                		 $("input[type='checkbox']")[i + 1].disabled = "disabled";
			                	}
			                }
			            }
			            $("input[type='checkbox']")[0].disabled = "disabled";
			        },
		            columns:[[
		                {field:'ID',checkbox:true,width:50},
		                {field:'CREDENTIALS_COMPANY',width:200,title:'公司',align:'center'},
		                {field:'JZ_DATE',width:150,title:'记账日期',align:'center'},
		                {field:'YW_DATE',width:150,title:'业务日期',align:'center'},
		                {field:'MONTH',width:150,title:'会计期间',align:'center'},
		                {field:'CREDENTIALS_TYPE',width:200,title:'凭证类型',align:'center'},
		                {field:'FI_CERTIFICATE',width:150,title:'凭证号',align:'center'},
		                {field:'FLH',width:100,title:'分录号',align:'center'},
		                {field:'REMARK',width:100,title:'摘要',align:'center'},
		                {field:'SUBJECT',width:80,title:'科目',align:'center'},
		                {field:'BZ',width:80,title:'币种',align:'center'},
		                {field:'HL',width:80,title:'汇率',align:'center'},
		                {field:'CREDENTIALS_DIRECTION',width:80,title:'方向',align:'center'},
		                {field:'FI_PAY_MONEY',width:80,title:'原币金额',align:'center'},
		                {field:'COUNT',width:80,title:'数量',align:'center'},
		                {field:'PRICE',width:80,title:'单价',align:'center'},
		                {field:'JF_MONEY',width:80,title:'借方金额',align:'center'},
		                {field:'DF_MONEY',width:80,title:'贷方金额',align:'center'},
		                {field:'FI_APP_NAME',width:80,title:'制单人',align:'center'},
		                {field:'GZ_NAME',width:80,title:'过账人',align:'center'},
		                {field:'FI_CHECK_NAME',width:80,title:'审核人',align:'center'},
		                {field:'FJSL',width:80,title:'附件数量',align:'center'},
		                {field:'GZBJ',width:80,title:'过账标记',align:'center'},
		                {field:'JZPZMK',width:80,title:'机制凭证模块',align:'center'},
		                {field:'SCBJ',width:80,title:'删除标记',align:'center'},
		                {field:'PZXH',width:80,title:'凭证序号',align:'center'},
		                {field:'DW',width:80,title:'单位',align:'center'},
		                {field:'CKXX',width:80,title:'参考信息',align:'center'},
		                {field:'SFYXJLL',width:80,title:'是否有现金流量',align:'center'},
		                {field:'XJLLBJ',width:80,title:'现金流量标记',align:'center'},
		                {field:'YWBH',width:80,title:'业务编号',align:'center'},
		                {field:'JSFS',width:80,title:'结算方式',align:'center'},
		                {field:'JSH',width:80,title:'结算号',align:'center'},
		                {field:'FZZZY',width:80,title:'辅助账摘要',align:'center'},
		                {field:'HSXM1',width:80,title:'核算项目1',align:'center'},
		                {field:'BM1',width:80,title:'编码1',align:'center'},
		                {field:'MC1',width:80,title:'名称1',align:'center'},
		                {field:'HSXM2',width:80,title:'核算项目2',align:'center'},
		                {field:'BM2',width:80,title:'编码2',align:'center'},
		                {field:'MC2',width:80,title:'名称2',align:'center'},
		                {field:'HSXM3',width:80,title:'核算项目3',align:'center'},
		                {field:'BM3',width:80,title:'编码3',align:'center'},
		                {field:'MC3',width:80,title:'名称3',align:'center'},
		                {field:'HSXM4',width:80,title:'核算项目4',align:'center'},
		                {field:'BM4',width:80,title:'编码4',align:'center'},
		                {field:'MC4',width:80,title:'名称4',align:'center'},
		                {field:'HSXM5',width:80,title:'核算项目5',align:'center'},
		                {field:'BM5',width:80,title:'编码5',align:'center'},
		                {field:'MC5',width:80,title:'名称5',align:'center'},
		                {field:'HSXM6',width:80,title:'核算项目6',align:'center'},
		                {field:'BM6',width:80,title:'编码6',align:'center'},
		                {field:'MC6',width:80,title:'名称6',align:'center'},
		                {field:'HSXM7',width:80,title:'核算项目7',align:'center'},
		                {field:'BM7',width:80,title:'编码7',align:'center'},
		                {field:'MC7',width:80,title:'名称7',align:'center'},
		                {field:'HSXM8',width:80,title:'核算项目8',align:'center'},
		                {field:'BM8',width:80,title:'编码8',align:'center'},
		                {field:'MC8',width:80,title:'名称8',align:'center'},
		                {field:'FPH',width:80,title:'发票号',align:'center'},
		                {field:'HPZH',width:80,title:'换票证号',align:'center'},
		                {field:'KH',width:80,title:'客户',align:'center'},
		                {field:'FYLB',width:80,title:'费用类别',align:'center'},
		                {field:'SKR',width:80,title:'收款人',align:'center'},
		                {field:'WL',width:80,title:'物料',align:'center'},
		                {field:'CWZZ',width:80,title:'财务组织',align:'center'},
		                {field:'GYS',width:80,title:'供应商',align:'center'},
		                {field:'FZZYWRQ',width:80,title:'辅助账业务日期',align:'center'},
		                {field:'DQR',width:80,title:'到期日',align:'center'}
		               
		            ]]
		        });
				
});

//驳回
function IS_BOHUI(){
	$.messager.confirm("提示","您确认要驳回选中的数据？",function(flag){
		if(flag){
			$("#divFrom").empty();
			var datagridList=$("#pageTable").datagrid('getSelections');
			var FI_CERTIFICATES="";
			for(var i = 0; i < datagridList.length; i++)
			{
				if(i==0){
					FI_CERTIFICATES=datagridList[i].FI_CERTIFICATE;
				}
				else{
					FI_CERTIFICATES=FI_CERTIFICATES+","+datagridList[i].FI_CERTIFICATE;
				}
			}
			var url=_basePath+"/credentials/Credentials!credentialsBack.action?FI_CERTIFICATES="+FI_CERTIFICATES;
			$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
			$("#formSub").submit();
		}
	});
	
}
																									

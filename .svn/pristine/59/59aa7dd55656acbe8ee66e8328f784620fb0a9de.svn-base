$(document).ready(function(){
				$("#pageTable").datagrid({
					url:_basePath+"/rentDraw/RentDraw!getRentDrawLogPage.action?LEASE_CODE=" + $("#LEASE_CODE").val(),
					pageSize:20,
					fit : true,
					pagination : true,
					rownumbers : true,
					singleSelect : true,
					toolbar : "#pageForm",
                    columns:[[
						{field:'BATCH_NO',width:150,title:'批次号',align:'center'},
						{field:'ITEM_NO',width:100,title:'流水号',align:'center'},
						{field:'CUST_NAME',width:100,title:'客户姓名',align:'center'},
						{field:'IDENTIFICATION_NUMBER',width:150,title:'证件号码',align:'center'},
						{field:'BANK_CODE',width:100,title:'银行代码',align:'center'},
						{field:'BRANCH_NAME',width:100,title:'分支行',align:'center'},
						{field:'PROVINCE',width:100,title:'省份',align:'center'},
						{field:'CITY',width:100,title:'城市',align:'center'},
						{field:'ACCOUNT_NUMBER',width:150,title:'银行账号',align:'center'},
						{field:'LEASE_CODE',width:150,title:'合同号',align:'center'},
						{field:'PAY_ID',width:100,title:'支付记录号',align:'center'},
						{field:'LEASE_TERM',width:100,title:'期次',align:'center',formatter:function(value,rowData){
							return $("#PERIOD_NUM").val()==null?value:$("#PERIOD_NUM").val();
						}},
						{field:'AMOUNT',width:100,title:'金额',align:'center'},
						{field:'TYPE',width:100,title:'划扣方式',align:'center'},
						{field:'SUBMIT_DATE',width:150,title:'提交时间',align:'center'},
						{field:'RETURN_DATE',width:150,title:'返回时间',align:'center'},
						{field:'USE_STATE',width:100,title:'处理状态',align:'center'},
						{field:'SUBMIT_STATE',width:100,title:'提交状态码',align:'center'},
						{field:'SUBMIT_ERR_MSG',width:100,title:'提交状态说明',align:'center'},
						{field:'RETURN_STATUS',width:100,title:'返回状态',align:'center'},
						{field:'RETURN_ERR_MSG',width:100,title:'返回状态说明',align:'center'},
						{field:'BANK_DATE',width:100,title:'回盘时间',align:'center'},
						{field:'FFPDID',width:100,title:'放款ID',align:'center'}
                    ]]
                });
			});

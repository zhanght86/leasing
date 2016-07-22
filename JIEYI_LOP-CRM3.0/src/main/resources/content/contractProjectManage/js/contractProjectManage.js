$(document).ready(function(){
	getDataList();
});

function getDataList(){
	//alert('contractProjectManage.vm');
	$("#cacelContract").dialog('close');
//var LEASE_CODE=$("input[name='LEASE_CODE']").val();
	$("#pageTable").datagrid({
		url:_basePath+"/contractProjectManage/ContractProjectManage!getContractProjectManageData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
//		queryParams:{"LEASE_CODE":LEASE_CODE},
		frozenColumns:[[
					{field:'ID',title:'操作',width:180,align:'center',formatter:function(value,rowData,rowIndex){
						if(0!=rowData.CACELTYPE){
							var doWork="<a href='javascript:void(0)' onclick='queryContractProjectById("+value+","+rowIndex+","+rowData.CLIENT_ID+")'>查看</a>";
							if(null!=rowData.START_DATE&&""!=rowData.START_DATE&&1!=rowData.CACELTYPE){
								doWork += "  |  <a href='javascript:void(0)' onclick='cacelContractProjectByJbpm("+value+","+rowData.LEASE_CODE+")'>申请撤销</a>";
								//doWork += "  |  <a href='javascript:void(0)' onclick='detailContract("+value+","+rowIndex+","+rowData.CLIENT_ID+")'>审批</a>";
							}
						}
						return doWork;
					}}
					]], 
		columns:[[
			
		    {field:'STATUS_FLAG',title:'状态',width:180,align:'center'},
	      	{field:'PRO_NAME',title:'项目名称',width:180,align:'center'},
	      	//{field:'ID',hidden:true,align:'center'},
	      	//{field:'CUST_TYPE',hidden:true},
	      	//{field:'CUST_ID',hidden:true},
	      	//{field:'STATUS',hidden:true},
	      	//{field:'STATUS',hidden:true},
	      	{field:'LEASE_CODE',title:'合同号',width:180,align:'center'},
	      	{field:'CUST_NAME',title:'客户名称',align:'center',width:180},
	      	{field:'CUST_FLAG',title:'客户类型',width:90,align:'center'},
	      	{field:'START_DATE',title:'起租日',width:180,align:'center'},
	      	//{field:'PRO_NAME',hidden:true},
	      	{field:'PLATFORM_FLAG',align:'center',title:'业务类型',width:180},//,formatter:function(value,rowData,rowIndex){
        	  //return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+rowData.CUST_NAME+"</a>";
   // }
	      	//{field:'CUST_TYPE_NAME',hidden:true},
	      	{field:'CLERK_NAME',title:'客户经理',width:100,align:'center'},
	      	{field:'CACELTYPE',title:'撤销状态',width:100,align:'center',formatter:function(value,rowData,rowIndex){
	      		if(0==value){
	      			return "已撤销";
	      		}else if(1==value){
	      			return "撤销中";
	      		}else{
	      			return "未撤销";
	      		}
	      	}}
	      	//,formatter:getOperation()
	      	//{field:'PLATFORM_TYPE',hidden:true}
	     ]]
	});
}

function getOperation(value,rowData,index){
	
	//var srin=JSON.stringify(rowData);
	return  "<a href='javascript:void(0)' onclick='queryContractProjectById("+value+","+index+","+rowData.CLIENT_ID+")'>查看</a>";
	        //+
			//"&nbsp;|&nbsp;" +
			//"<a href='javascript:void(0)' onclick='deleteSql("+index+")'>删除</a>" ;
}

function queryContractProjectById(id,index,client_id){
	//alert(id+"--"+client_id);
	//window.location.href=_basePath+"/contractProjectManage/ContractProjectManage!getContractProjectById.action?id="+id+"&index="+index+"&client_id="+client_id;
	$("#cacelContract").dialog({
	title:"查看",
	buttons: [{
		id:"btnbc",
        text:'取消',
     iconCls:'icon-cancel',
     handler:function(){
    	 $("#cacelContract").dialog('close');
	 	}
	}]
  });
	$("#cacelContractTable").load(_basePath+"/contractProjectManage/ContractProjectManage!getContractProjectById.action?id="+id+"&index="+index+"&client_id="+client_id);
	$("#cacelContract").dialog('open');
	
}


//ontractProjectManage/ContractProjectManage.action
function SchemeView(id,index){
	
}

/**
 * 小写金额转换大写
 * @param numberValue
 * @return
 */
function atoc(numberValue) {
	var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for ( var i = 0; i < len; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		if (i != (len - 3) && i != (len - 7) && i != (len - 11)
				&& i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
}


/**
 * 清空按钮
 * @return
 */
function emptyDataProject(){
	$("#CREATE_TIME1").datebox('clear');
	$("#CREATE_TIME2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}


/**
 * 增加标签页
 * @param name
 * @param url
 * @return
 */
function addTab(name,url){
	//alert("addTab");
	//alert(url);
	url=url.replace("undefined","");
	//alert(url);
	if(url.lastIndexOf("?")==-1){
		url=url+"?_datetime="+new Date().getTime();
	}else{
		url=url+"&_datetime="+new Date().getTime();
	}
	if($('#tabBox').tabs('exists',name)){
		//alert(1);
		$('#tabBox').tabs('select',name);
		$('#tabBox').tabs('update',{
			tab:$("#tabBox").tabs("getSelected"),
			options:{
				content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
			}
		});
	}else{
		//alert(2);
		$('#tabBox').tabs('add',{
			title: name,
			content: '<iframe src="'+url+'" width="100%" height="100%" style="padding:0px;margin-bottom:-5px;" frameborder="0" border="0"></iframe>',
			closable: true,
			tools:[{
		        iconCls:'icon-mini-refresh',
		        handler:function(){
					$('#tabBox').tabs('update',{
						tab:$("#tabBox").tabs("getSelected"),
						options:{
							content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
						}
					});
		        }
		    }]
		});
	}
}

/**发起申请撤销流程**/
function cacelContractProjectByJbpm(id,LEASE_CODE){
	alert(LEASE_CODE);
	if(confirm("确定要申请撤销吗？")){
		$.ajax({
			url:_basePath+"/contractProjectManage/ContractProjectManage!startCacelContractProjectByJbpm.action?_dateTime="+new Date(),
			data:'ID='+id,
			dataType:'json',
			type:'post',
			success:function(json){
//				alert("RST="+json.data.RST);
				$.messager.alert("提示","合同撤销申请已发起,流程编号为："+json.data.RST);
				//location.reload();
			}
		});
	}
}

function detailContract(id,index,client_id){
	//alert(id+"-"+index+"-"+client_id);
	$("#cacelContract").dialog({
		title:"撤销",
			buttons: [{
				  id:"btnbc",
	            text:'撤销',
	         iconCls:'icon-save',
	         handler:function(){
			 	//$('#btnbc').linkbutton('disable');
//	        	 alert("撤销按钮");
	        	 jQuery.ajax({
						url: _basePath+"/contractProjectManage/ContractProjectManage!cacelContractProject.action?_dateTime="+new Date(),
				        data: "id="+id+"&index="+index+"&client_id="+client_id,
				        type: "post",
				        dataType: "json",
				        success: function(json){
//				        	alert(json.data);
				        	if(json.data){
				        		$.messager.alert('提示','撤销已完成！');
				        		getDataList();
				        		$("#cacelContract").dialog('close');
				        	}else{
				        		$.messager.alert('提示','撤销失败，请与管理员联系！');
				        	}
				        }
					});
			 	}
			},{
				id:"btnbc1",
	            text:'取消',
	         iconCls:'icon-cancel',
	         handler:function(){
	        	 $("#cacelContract").dialog('close');
			 	}
			}]
		 });
	    $("#cacelContractTable").load(_basePath+"/contractProjectManage/ContractProjectManage!getContractProjectById.action?id="+id+"&index="+index+"&client_id="+client_id);
		$("#cacelContract").dialog('open');
}


function approveContract(id,index,client_id){
	//alert(id+"-"+index+"-"+client_id);
	$("#cacelContract").dialog({
		title:"审批",
			buttons: [{
				  id:"btnbc",
	            text:'撤销',
	         iconCls:'icon-save',
	         handler:function(){
			 	//$('#btnbc').linkbutton('disable');
//	        	 alert("撤销按钮");
	        	 jQuery.ajax({
						url: _basePath+"/contractProjectManage/ContractProjectManage!cacelContractProject.action?_dateTime="+new Date(),
				        data: "id="+id+"&index="+index+"&client_id="+client_id,
				        type: "post",
				        dataType: "json",
				        success: function(json){
//				        	alert(json.data);
				        	if(json.data){
				        		$.messager.alert('提示','审批已完成！');
				        		getDataList();
				        		$("#cacelContract").dialog('close');
				        	}else{
				        		$.messager.alert('提示','审批失败，请与管理员联系！');
				        	}
				        }
					});
			 	}
			},{
				id:"btnbc1",
	            text:'取消',
	         iconCls:'icon-cancel',
	         handler:function(){
	        	 $("#cacelContract").dialog('close');
			 	}
			}]
		 });
	    $("#cacelContractTable").load(_basePath+"/contractProjectManage/ContractProjectManage!getContractProjectById.action?id="+id+"&index="+index+"&client_id="+client_id);
		$("#cacelContract").dialog('open');
}
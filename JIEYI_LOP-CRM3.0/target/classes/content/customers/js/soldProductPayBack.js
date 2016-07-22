$(document).ready(function(){
	$("#dialogProduct").dialog('close');
	/*var PLATFORM_TYPE=$("input[name='PLATFORM_TYPE']").val();
	var PRO_CODE=$("input[name='PRO_CODE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var STATUS=$("select[name='STATUS']").val();
	var STATUS2=$("#STATUS2").val();*/
	var datetimeName=$("#datetimeName").val();
	$("#pageTable").datagrid({
		//url:_basePath+"/project/project!pageAjax.action",
		url:_basePath+"/customers/Customers!soldAjax.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
		queryParams:{"_datetime":datetimeName},
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
            	/*$('#pageTable').datagrid('selectRow', 0);
            	$("#PROJECT_ID_CONTRACT").val(data.rows[0].ID);
            	$("#PLATFORM_TYPE_CONTRACT").val(data.rows[0].PLATFORM_TYPE);
            	$("#STATUS_CONTRACT").val(data.rows[0].STATUS);*/
            }
        },
		onClickRow:function(index,data){
			$("#pageTable").datagrid("beginEdit",index);
			//ToUPdate();
			/*$("#PROJECT_ID_CONTRACT").val(data.ID);
			$("#PLATFORM_TYPE_CONTRACT").val(data.PLATFORM_TYPE);
        	$("#STATUS_CONTRACT").val(data.STATUS);*/
		},
        frozenColumns:[[
        {field:'ID',title:'操作',width:100,align:'center',formatter:function(value,row,rowIndex)
        	{
				 var STATUS = row.STATUS;
				 var LCNAME = row.LCNAME;
				 var rehtml = " <a onclick='ToUPdate("+value+")' href='javaScript:void(0);'>修改</a>|&nbsp;";
				 rehtml +=    " <a onclick='ToDelete("+value+")' href='javaScript:void(0);'>删除</a>";
				 
				 return rehtml;
			}
		}]],
		columns:[[
	      	{field:'PRODUCT_NAME',title:'产品名称',width:220,align:'center'},
	      	{field:'CUONTS',title:'期数',width:70,align:'center'},
	      	{field:'CAR_PRICE_FORM',title:'车价（From含）',width:150,align:'center'},
	      	{field:'CAR_PRICE_TO',title:'车价（To含）',width:150,align:'center'},
	      	{field:'PAYBACK',title:'月还',width:150,align:'center'},
	      	{field:'TOTALMONEY',title:'融资额',width:150,align:'center'},
	      	{field:'CAR_TYPE_MEMO',title:'车型备注',width:150,align:'center'}
	     ]]
	});
});
//全选
function AllCheck(){
	$(".easyui-checkboxbutton").attr('checked','checked');
}
/**
 * add by lishuo 12.2.2015
 * 删除固定月还产品
 */
function ToDelete(ID){
	var url=_basePath+"/customers/Customers!DeleteSoldProduct.action";
	var args ={"ID":ID};
	if(ID>0){
		$.messager.confirm('确认', '确定删除该条信息吗?', function(r){
			if (r){
				$.ajax({
					url:url,
					data:args,
					dataType:"json",
					success:function(data){
						if(data.flag){
							$.messager.alert("提示","删除信息成功!"); 
							location.reload();
						}else
							$.messager.alert("提示","删除修改失败！")
					}
				});
			}
		});
		return false;
	}else{
		$.messager.alert("提示","请选择要删除的行！");
	}
}
/**
 * add by lishuo 12.2.2015
 * 修改固定月还产品
 */
function ToUPdate(num){
	//根据ID查询详细信息
	 var args ={"ID":num};
	 $.ajax({
		url:_basePath + "/customers/Customers!FindSoldProductByID.action",
		data:args,
		dataType:"json",
		type:"post",
		success:function(data){
			if(data){
				var json = data.data;
				for(var i =0; i<json.length;i++){
					  $("#PRODUCT_NAME").val(json[i].PRODUCT_NAME);
			    	  $("#CUONTS").val(json[i].CUONTS);
			    	  $("#CAR_PRICE_FORM").val(json[i].CAR_PRICE_FORM);
			    	  $("#CAR_PRICE_TO").val(json[i].CAR_PRICE_TO);
			    	  $("#PAYBACK").val(json[i].PAYBACK);
			    	  $("#TOTALMONEY").val(json[i].TOTALMONEY);
			    	  $("#CAR_TYPE_MEMO").val(json[i].CAR_TYPE_MEMO);
				}
			}
		}
	 });
	$("#controlCustEmergency").dialog({
		width:450,
		height:300,
		title:"修改",
		buttons: [{
			id:"btnsave",
	        text:'保存修改',
	     iconCls:'icon-save',		     
	     handler:function(){
	    	 var ID,PRODUCT_NAME,CUONTS,CAR_PRICE_FORM,CAR_PRICE_TO,PAYBACK,TOTALMONEY,CAR_TYPE_MEMO;
	    	 PRODUCT_NAME   =  $("#PRODUCT_NAME").val();
	    	 CUONTS         =  $("#CUONTS").val();
	    	 CAR_PRICE_FORM =  $("#CAR_PRICE_FORM").val();
	    	 CAR_PRICE_TO   =  $("#CAR_PRICE_TO").val();
	    	 PAYBACK        =  $("#PAYBACK").val();
	    	 TOTALMONEY     =  $("#TOTALMONEY").val();
	    	 CAR_TYPE_MEMO  =  $("#CAR_TYPE_MEMO").val();
	    	 
	    	 if(null == PRODUCT_NAME || "" == PRODUCT_NAME){
	    		 $.messager.alert("提示","产品名称不能为空！");
	    		 return false;
	    	 }
	    	 
	    	 if(null == CUONTS || "" == CUONTS){
	    		 $.messager.alert("提示","期数不能为空，并且必须为数字！");
	    		 return false;
	    	 }else{
	    		 if(isNaN(CUONTS)){
	    			 $.messager.alert("提示","期数不能为空，并且必须为数字！");
	 	 			return false;
	 	 		}
	    	 }
	 		
	 		if(!isTwoNum(CAR_PRICE_FORM,"车价(From含)")){
	 			return false;
	 		}
	 		
	 		if(!isTwoNum(CAR_PRICE_TO,"车价To")){
	 			return false;
	 		}
	 		
	 		if(!isTwoNum(PAYBACK,"月还")){
	 			return false;
	 		}
	 		
	 		if(!isTwoNum(TOTALMONEY,"融资额")){
	 			return false;
	 		}
	 		
	 		if(null == CAR_TYPE_MEMO || "" == CAR_TYPE_MEMO){
	 			$.messager.alert("提示","车型备注不能为空！");
	    		 return false;
	    	 }
	    	 args={"ID":num,"PRODUCT_NAME":PRODUCT_NAME,"CUONTS":CUONTS,"CAR_PRICE_FORM":CAR_PRICE_FORM,"CAR_PRICE_TO":CAR_PRICE_TO,
	    			 "PAYBACK":PAYBACK,"TOTALMONEY":TOTALMONEY,"CAR_TYPE_MEMO":CAR_TYPE_MEMO};
			jQuery.ajax({
				type:"post",
				url : _basePath + "/customers/Customers!UpdateSoldProcuct.action",
				data : args,
				dataType : "json",
				success : function(result) {
					if (result) {
						$.messager.alert("提示","修改成功"); 
						location.reload();
					} else {
						$.messager.alert("提示","保存失败，请联系管理员！"); 
					}
				}
			});
		 	}
		},
		{ id:"btnbc",
	        text:'取消',
	     iconCls:'icon-cancel',
	     handler:function(){
	    	 $("#controlCustEmergency").dialog('close');
		 	}
		}]
	  });
	$("#addCustEmergency").show();
	$("#controlCustEmergency").dialog('open');
}

//获取文件扩展名
function get_ext(f_path){
	var ext = '';
	if(f_path != null && f_path != ''){
	   ext = f_path.substring(f_path.lastIndexOf(".") + 1, f_path.length);
	}
	return ext;
}

//验证文件扩展名
function chk_ext(f_path){
	var ext = get_ext(f_path);
	
	//根据需求定制
	var accept_ext = new Array("xls","xlsx");
	var flag = false;
	if(ext != ''){
	   for(var i=0; i<accept_ext.length; i++){
	    if(ext == accept_ext[i])
	     flag = true;
	   }
	}
	return flag;
}

/**
 * add by lishuo 12.3.2015
 * 
 */
function readExcel(){
	var fileName = $('#uploadify').val();
	if(fileName==''){
		return $.messager.alert("提示",'请选择Excel文件！');
	}
	if(chk_ext(get_ext(fileName))==false){
		return $.messager.alert("提示",'请选择Excel文件！');
	}
	
	$('#readFile').submit();
}

/**
 * add by lishuo 12.4.2015
 * 清空
 */
function clean(){
		$("#form").form('clear');
}

/**
 * 判断是否为数字
 * @param num
 * @returns
 */
function isNum(num){
  var reNum=/^\d*$/;
  return(reNum.test(num));
} 

/**
 * 判断是否为两位小数
 * @param num 数字
 * @param str 输出语句
 */
function isTwoNum(num,str){
	if(null != num && "" != num){
		if(!isNaN(num)){
	        var dot = num.indexOf(".");
	        if(dot != -1){
	            var dotCnt = num.substring(dot+1,num.length);
	            if(dotCnt.length > 2){
	            	$.messager.alert("提示",str + "小数位已超过2位！");
	                return false;
	            }
	        }
	    }else{
	    	$.messager.alert("提示",str + "数字不合法！");
	        return false;
	    }
	}else{
		$.messager.alert("提示",str + "不能为空，并且必须为数字");
		return false;
	}
	return true;
}

function conditionsSelect() {
	$('#pageTable').datagrid('load', {
		"param" : getFromData("#pageForm")
	});
}


//固定月还产品新增
function appendBack(){
	
	$("#controlCustEmergency").dialog({
		width:450,
		height:300,
		title:"保存",
		buttons: [{
			id:"btnsave",
	        text:'保存',
	     iconCls:'icon-save',		     
	     handler:function(){
	    	 var PRODUCT_NAME,CUONTS,CAR_PRICE_FORM,CAR_PRICE_TO,PAYBACK,TOTALMONEY,CAR_TYPE_MEMO;
	    	 PRODUCT_NAME   =  $("#PRODUCT_NAME").val();
	    	 CUONTS         =  $("#CUONTS").val();
	    	 CAR_PRICE_FORM =  $("#CAR_PRICE_FORM").val();
	    	 CAR_PRICE_TO   =  $("#CAR_PRICE_TO").val();
	    	 PAYBACK        =  $("#PAYBACK").val();
	    	 TOTALMONEY     =  $("#TOTALMONEY").val();
	    	 CAR_TYPE_MEMO  =  $("#CAR_TYPE_MEMO").val();
	    	 
	    	 if(null == PRODUCT_NAME || "" == PRODUCT_NAME){
	    		 $.messager.alert("提示","产品名称不能为空！");
	    		 return false;
	    	 }
	    	 
	    	 if(null == CUONTS || "" == CUONTS){
	    		 $.messager.alert("提示","期数不能为空，并且必须为数字！");
	    		 return false;
	    	 }else{
	    		 if(isNaN(CUONTS)){
	    			 $.messager.alert("提示","期数不能为空，并且必须为数字！");
	 	 			return false;
	 	 		}
	    	 }
	 		
	 		
	 		if(!isTwoNum(CAR_PRICE_FORM,"车价(From含)")){
	 			return false;
	 		}
	 		
	 		if(!isTwoNum(CAR_PRICE_TO,"车价To")){
	 			return false;
	 		}
	 		
	 		if(!isTwoNum(PAYBACK,"月还")){
	 			return false;
	 		}
	 		
	 		if(!isTwoNum(TOTALMONEY,"融资额")){
	 			return false;
	 		}
	 		
	 		if(null == CAR_TYPE_MEMO || "" == CAR_TYPE_MEMO){
	 			$.messager.alert("提示","车型备注不能为空！");
	    		 return false;
	    	 }
	 		
	 		args={"PRODUCT_NAME":PRODUCT_NAME,"CUONTS":CUONTS,"CAR_PRICE_FORM":CAR_PRICE_FORM,"CAR_PRICE_TO":CAR_PRICE_TO,
	    			 "PAYBACK":PAYBACK,"TOTALMONEY":TOTALMONEY,"CAR_TYPE_MEMO":CAR_TYPE_MEMO};
	 		
			jQuery.ajax({
				type:"post",
				url : _basePath + "/customers/Customers!saveCarInfo.action",
				data: args,
				dataType : "json",
				success : function(result) {
					if (result) {
						$.messager.alert("提示","保存成功"); 
						location.reload();
					} else {
						$.messager.alert("提示","保存失败，请联系管理员！"); 
					}
				}
			});
		 	}
		},
		{ id:"btnbc",
	        text:'取消',
	     iconCls:'icon-cancel',
	     handler:function(){
	    	 $("#controlCustEmergency").dialog('close');
		 	}
		}]
	  });
	$("#addCustEmergency").show();
	$("#controlCustEmergency").dialog('open');
}
function newOne(){
	top.addTab("添加供应商", _basePath+"/base/suppliers/Suppliers!addSupPage.action");
}

function update(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("供应商信息修改",_basePath+"/base/suppliers/Suppliers!modifySupPage.action?SUP_ID="+row.SUP_ID);
    }
}
//打开dialog
function openDialog(div,name){
    $('#'+div).dialog('open').dialog('setTitle',name);
}
//关闭dialog
function closeDialog(name){
   $('#'+name).dialog('close');
}
function saveUpLoad(){
	var f=$("#fileid").val();
	var FIL_MEMO=$("#FIL_MEMO").val();
	if(f==null||f==""){
		alert("请选择要上传的文件");
		return false;
	}else if(FIL_MEMO==""){
		alert("文件描述不能为空");
		return false;
	}else{
	    var supplier_name = $("#SUPPLIERS_NAME").val();
		var ID=$("#SUP_ID").val();
		var FIL_TYPE=$("#fil_type").val();
		jQuery.ajaxFileUpload({
        	    url:"Channel!uploadFile.action?FIL_MEMO="+FIL_MEMO+"&SUP_ID="+ID+"&SUP_NAME="+supplier_name+"&FIL_TYPE="+FIL_TYPE,
        	    secureuri:false,
        	    fileElementId:"fileid",	 
        	    dataType: "json",
				success: function (data,status)  //服务器成功响应处理函数
                {
				     var data = JSON.parse(data);
					 var bsdte="";
					 for(var i=0;i<data.length;i++){
						bsdte=bsdte+"<tr>";
						bsdte=bsdte+"<td align='center' title='"+data[i].FIL_TYPE+"'>"+data[i].FIL_TYPE+"</td>";
            			bsdte=bsdte+"<td align='center' title='"+data[i].FIL_NAME+"'>"+data[i].FIL_NAME+"</td>";
            			bsdte=bsdte+"<td align='center' title='"+data[i].FIL_MEMO+"'>"+data[i].FIL_MEMO+"</td>";
                        bsdte=bsdte+"<td align='center'><a href='Channel!downLoadSupFile.action?FIL_ID="+data[i].FIL_ID+"'>下载</a>   ";
    					bsdte=bsdte+"<a href='javascript:void(0);' onclick='delFile("+data[i].FIL_ID+",this)'>删除</a></td></tr>";
					 }
					 if($("#fil_type").val()=="报告"){
						 $("#bgid").html(bsdte);	 
					 }else{
						 $("#dfkid").html(bsdte);	
					 }
                },

       	});	
		 $("#dialoguploadfile1").dialog('close');
	}
}
//删除附件
function delFile(fil_id,obj){
	if(confirm("是否确定删除")){
    	jQuery.ajax({
    		type: "POST",
    		url: "Channel!deleteSupFile.action",
    		data: "FIL_ID="+fil_id,
    		dataType: "json",
    		success: function(res){
				if(res.flag==true){
					jQuery.messager.alert("提示",res.msg);
					var tab=obj.parentNode.parentNode.parentNode;
					var tr=obj.parentNode.parentNode;
					tab.removeChild(tr);  
				}else{
				    jQuery.messager.alert("提示",res.msg);
				}
    		}
    	});
	}
}
/*
 * 统计建议授信额度
 */
function statlimit(){
	var adviceamplification = $("#adviceamplification").val();
	var realyassets = $("#realyassets").val().replace('$','').replace(',','');
	var temp = adviceamplification*realyassets;
	$("#advicelimit").val(formatCurrency(Math.round(temp)));
}

function format(id){
	$("#"+id).val(formatCurrency($("#"+id).val()));
}

function formatCurrency(num) {
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
	num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	if(cents<10)
	cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + '$' + num + '.' + cents);
	}

function savestat(){
	var adviceamplification = $("#adviceamplification").val();
	var realyassets = $("#realyassets").val().replace('$','').replace(',','');
	var advicelimit = $("#advicelimit").val().replace('$','').replace(',','');
	var AVG_MONTH = $("#AVG_MONTH").val().replace('%','');
	var AVG_AMOUNT = $("#AVG_AMOUNT").val().replace('%','');
	var AVG_RENT = $("#AVG_RENT").val().replace('%','');
	var AVG_CUR_RENT = $("#AVG_CUR_RENT").val().replace('%','');
	var AVG_UK_RAT = $("#AVG_UK_RAT").val().replace('%','');
	var APP_ID = $("#APP_ID").val().replace('%','');
	var instructions = $("#instructions").val().replace('%','');
	var pa = "adviceamplification="+adviceamplification
	+"&realyassets="+realyassets
	+"&advicelimit="+advicelimit
	+"&AVG_MONTH="+AVG_MONTH
	+"&AVG_AMOUNT="+AVG_AMOUNT
	+"&AVG_RENT="+AVG_RENT
	+"&AVG_CUR_RENT="+AVG_CUR_RENT
	+"&AVG_UK_RAT="+AVG_UK_RAT
	+"&APP_ID="+APP_ID
	+"&instructions="+instructions;
	jQuery.ajax({
		type: "POST",
		url: "Channel!savestat.action",
		data: pa,
		dataType: "json",
		success: function(res){
			jQuery.messager.alert("提示",res.msg);
		}
	});
}



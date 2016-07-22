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
        	    url:"Channel!uploadFile.action?FIL_MEMO="+FIL_MEMO+"&SUP_ID="+ID+"&SUP_NAME="+supplier_name+"&FIL_TYPE="+FIL_TYPE+"&APP_ID="+$("#APP_ID").val(),
        	    secureuri:false,
        	    fileElementId:"fileid",	 
        	    dataType: "json",
				success: function (data,status)  //服务器成功响应处理函数
                {
				     var data = JSON.parse(data);
					 var bsdte="";
					 for(var i=0;i<data.length;i++){
						bsdte=bsdte+"<tr>";
//						bsdte=bsdte+"<td align='center' title='"+data[i].FIL_TYPE+"'>"+data[i].FIL_TYPE+"</td>";
//            			bsdte=bsdte+"<td align='center' title='"+data[i].FIL_NAME+"'>"+data[i].FIL_NAME+"</td>";
            			bsdte=bsdte+"<td align='center' title='"+data[i].FIL_MEMO+"'>"+data[i].FIL_MEMO+"</td>";
                        bsdte=bsdte+"<td align='center'><a href='Channel!downLoadSupFile.action?FIL_ID="+data[i].FIL_ID+"'>下载</a>   ";
    					bsdte=bsdte+"<a href='javascript:void(0);' onclick='delFile("+data[i].FIL_ID+",this)'>删除</a></td></tr>";
					 }
					 if($("#fil_type").val()=="财务报表"){
						 $("#bgid").html(bsdte);	 
					 }else if($("#fil_type").val()=="打分卡"){
						 $("#dfkid").html(bsdte);	
					 }else{
						 $("#tblfsg").html(bsdte);
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
	//var adviceamplification = $("#adviceamplification").val();
	//var realyassets = $("#realyassets").val().replace('￥','').replace(',','');
	//var temp = adviceamplification*realyassets;
	//$("#advicelimit").val(formatCurrency(Math.round(temp)));
}


function getscore1(){
	var sum = 0;
	for(i = 1; i <= 5;i++){
		sum += parseFloat($("#score0"+i).val() == "" ? 0 :  $("#score0"+i).val());
	}
	$("#scoreSum").val(Math.round(sum));
	
}
function getscore2(){
	var sum = 0;
	for(i = 1; i <= 5;i++){
		sum += parseFloat($("#score1"+i).val() == "" ? 0 :  $("#score1"+i).val());
	}
	$("#scoreSum").val(Math.round(sum));
	
}
function getscorebz1(){
	var sum = 0;
	for(i = 1; i <= 5;i++){
		sum += parseFloat($("#scoreSum0"+i).val() == "" ? 0 :  $("#scoreSum0"+i).val());
	}
	$("#SCORESUMBZ").val(Math.round(sum));
	
}
function getscorebz2(){
	var sum = 0;
	for(i = 1; i <= 5;i++){
		sum += parseFloat($("#scoreSum1"+i).val() == "" ? 0 :  $("#scoreSum1"+i).val());
	}
	$("#SCORESUMBZ").val(Math.round(sum));
	
}
//建议授信额度
function dosum1(){
	$("#advicelimit").val(formatCurrency(Math.round( 
			parseFloat($("#creditAdvice").val() == "" ? 0 :  $("#creditAdvice").val())
			+ (parseFloat($("#channelCreditAdvice").val() == "" ? 0 :  $("#channelCreditAdvice").val())
					* parseFloat($("#adviceamplification").val() == "" ? 0 :  $("#adviceamplification").val())
					)
					
					+parseFloat($("#operating_assets").val() == "" ? 0 :  $("#operating_assets").val()) 
					+ parseFloat($("#evaluation_guarantee").val() == "" ? 0 :  $("#evaluation_guarantee").val()) )));
}
//求合计
function dosum(){
	var netasset = parseFloat($("#netasset").val()== "" ? 0 :  $("#netasset").val());
	var unrecyclableAsset = parseFloat($("#unrecyclableAsset").val()== "" ? 0 :  $("#unrecyclableAsset").val());
	var badDebt = parseFloat($("#badDebt").val()== "" ? 0 :  $("#badDebt").val());
	var non_performing_assets = parseFloat($("#non_performing_assets").val()== "" ? 0 :  $("#non_performing_assets").val());
	var eliminate_assets = parseFloat($("#eliminate_assets").val()== "" ? 0 :  $("#eliminate_assets").val());
	var actual_controller = parseFloat($("#actual_controller").val()== "" ? 0 :  $("#actual_controller").val());
	var actual_operating_assets = parseFloat($("#actual_operating_assets").val()== "" ? 0 :  $("#actual_operating_assets").val());
	var sum = netasset - unrecyclableAsset - badDebt  - non_performing_assets - eliminate_assets + actual_controller + actual_operating_assets;
	$("#realyassets").val(formatCurrency(Math.round(sum)));
	statlimit();
}
$(function(){
	format('realyassets');
	format('advicelimit');
	
});

function format(id){
	$("#"+id).val(formatCurrency($("#"+id).val()));
}

function formatCurrency(num) {
	num = num.toString().replace(/\￥|\,/g,'');
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
	return (((sign)?'':'-') + '' + num + '.' + cents);
	}

function savestat(){
	var adviceamplification = $("#adviceamplification").val();
	var realyassets = $("#realyassets").val().replace(',','').replace(',','').replace(',','');
	var advicelimit = $("#advicelimit").val().replace(',','').replace(',','').replace(',','');
	var STATUS =  $("#STATUS").val();
	var AVG_MONTH ;
	var AVG_AMOUNT;
	var AVG_RENT;
	var AVG_CUR_RENT;
	var AVG_UK_RAT;
	var APP_ID;
	var instructions;
	if(STATUS!=-1){
		AVG_MONTH = $("#AVG_MONTH")!= undefined ?  $("#AVG_MONTH").val().replace('%','').replace(',','').replace(',','').replace(',','') : '';
		AVG_AMOUNT = $("#AVG_AMOUNT")!= undefined ? $("#AVG_AMOUNT").val().replace('%','').replace(',','').replace(',','').replace(',','') : '';
		AVG_RENT = $("#AVG_RENT")!= undefined ? $("#AVG_RENT").val().replace('%','').replace(',','').replace(',','').replace(',','') : '';
		AVG_CUR_RENT = $("#AVG_CUR_RENT")!= undefined ? $("#AVG_CUR_RENT").val().replace('%','').replace(',','').replace(',','').replace(',',''): '';
		AVG_UK_RAT = $("#AVG_UK_RAT")!= undefined ? $("#AVG_UK_RAT").val().replace('%','').replace(',','').replace(',','').replace(',',''): '';
		APP_ID = $("#APP_ID")!= undefined ? $("#APP_ID").val().replace('%','').replace(',','').replace(',','').replace(',',''): '';
		instructions = $("#instructions")!= undefined ? $("#instructions").val().replace('%','').replace(',','').replace(',','').replace(',',''): '';
	}
	var searchParams = getFromData1('#frmSearch');
	//新加
	
	var pa = "adviceamplification="+adviceamplification
	+"&realyassets="+realyassets
	+"&advicelimit="+advicelimit
	+"&AVG_MONTH="+AVG_MONTH
	+"&AVG_AMOUNT="+AVG_AMOUNT
	+"&AVG_RENT="+AVG_RENT
	+"&AVG_CUR_RENT="+AVG_CUR_RENT
	+"&AVG_UK_RAT="+AVG_UK_RAT
	+"&APP_ID="+APP_ID
	+"&instructions="+instructions
	+"&searchParams="+searchParams;

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
function getFromData1(str) {
	var data = {};
	$(str + ' [name]').each(
			function() {
				if ($(this).is(":checkbox,:radio")) {
					if ($(this).attr("checked")) {
						data[$(this).attr("name")] = $(this).val().replace('%','').replace(',','').replace(',','').replace(',','').replace(',','').replace('￥','');
					}
				} else {
					if ($(this).is("select")) {
						data[$(this).attr("name")] = $(this).find(":selected").val().replace('%','').replace(',','').replace(',','').replace(',','').replace(',','').replace('￥','');
					} else {
						data[$(this).attr("name")] = $.trim($(this).val()).replace('%','').replace(',','').replace(',','').replace(',','').replace(',','').replace('￥','');
					}
				}
			});
	return JSON.stringify(data);
}


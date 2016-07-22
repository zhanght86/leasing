$(function(){
	balance();
});
//删除付款约定
function deletePay(obj){
	 $(obj).parent("td").parent("tr").remove();
	 sortBatch();
	}
//批次排序	
function sortBatch(){
	$(".BATCH").each(function(index){
				index=index+1;
				$(this).html(index);
				$(this).parent("td").parent("tr").find("input[name='BATCH']").val(index);
    });
}
function addPay(obj){
	 var table=$(obj).parent("td").parent("tr").parent("tbody").parent("table").find(".pay_body");
	 var dictFlagValue=$("#dictFlagValue").val();
	 table.append("<tr align=\"center\" class=\"pay_tr_add\">"
   				   +"<td ><span class=\"BATCH\"><\span></td>"
                       +"<td><input type=\"text\" class=\"notNull\" id=\"PAY_AMOUNT\" name=\"PAY_MONEY\" onblur=\"two(this);balance()\"  style=\"width:100px;\"  value=\"\"\><font color=\"red\">*</font></td>"
                       +"<td><input type=\"text\" name=\"SQ_PRICE\" value=\"0\" readonly style=\"width:100px;\" />"+"</td>"
		               +"<td><input type=\"text\" name=\"DISCOUNT_MONEY\" value=\"0\" readonly style=\"width:100px;\" />"+"</td>"
		               +"<td><input class=\"easyui-datebox\"  type=\"text\" size =\"15\" name=\"RESERVE_DATE\" id=\"EXPECTED_PAY_TIME\"  />"+"</td>"
                       +"<td>"+dictFlagValue+"</td>"
                       +"<td><a href=\"javascript:void(0);\" onclick=\"deletePay(this);\">删除</a></td>"
                 +"</tr>");
	$.parser.parse();
	sortBatch();
}
//付款约定  计算余额
function balance(obj){
	  //debugger  ;
	  var surplus=0;
	  var sumpay=0;
	  var firstAmount = 0;
	  var tiexiAmount = 0;
	  $(".pay_tr").each(function(){
		  	//放款金额
		    var PAY_AMOUNT=$(this).find("input[name='PAY_MONEY']").val();
		    sumpay=accAdd(sumpay,PAY_AMOUNT);
		  	//首期款
		    var FIRST_AMOUNT=$(this).find("input[name='SQ_PRICE']").val();
		    firstAmount=accAdd(firstAmount,FIRST_AMOUNT);
		  	//贴息金额
		    var DISCOUNT_MONEY=$(this).find("input[name='DISCOUNT_MONEY']").val();
		    tiexiAmount=accAdd(tiexiAmount,DISCOUNT_MONEY);
		    
	    });
     var equipsum=$("#equipsum").val();
     equipsum = jian(equipsum,firstAmount);
     equipsum = jian(equipsum,tiexiAmount);
	   surplus=jian(equipsum,sumpay);
	   if(surplus<0){
		   alert("付款金额不能大于（设备总额-首期款-贴息金额）！");
		   $(obj).val(0);
	   }else{
		   if($(obj).val() == null || $(obj).val() == "" || $(obj).val() == "undefined"){
			   $(obj).val(0);
		   }
		   $("#balance").html(surplus);
		   $("#sumpay").html(sumpay);
	   }
	   
}
//验证必须相等--付款金额 、设备总额
function balanceCheck(){
	    var booleanFlag=true;
	    var surplus=0;
		  var sumpay=0;
		  var firstAmount = 0;
		  var tiexiAmount = 0;
		  $(".pay_tr").each(function(){
			  	//放款金额
			    var PAY_AMOUNT=$(this).find("input[name='PAY_MONEY']").val();
			    sumpay=accAdd(sumpay,PAY_AMOUNT);
			  	//首期款
			    var FIRST_AMOUNT=$(this).find("input[name='SQ_PRICE']").val();
			    firstAmount=accAdd(firstAmount,FIRST_AMOUNT);
			  	//贴息金额
			    var DISCOUNT_MONEY=$(this).find("input[name='DISCOUNT_MONEY']").val();
			    tiexiAmount=accAdd(tiexiAmount,DISCOUNT_MONEY);
			    
		    });
	     var equipsum=$("#equipsum").val();
	     equipsum = jian(equipsum,firstAmount);
	     equipsum = jian(equipsum,tiexiAmount);
		   surplus=jian(equipsum,sumpay);
		   if(surplus>0){
			   booleanFlag = false;
		   }
      return booleanFlag;
	   
}

//只能输入数字
function zero(ele){
	ele.value=ele.value.replace(/\D{1,100}/,"");
}

//更新买卖合同
function saveBuyContract(obj){
	$(obj).linkbutton('disable');
	var notNull =$(".notNull");
	var contract=[];
	//TODO 展现数据Map没有用到
	var showInfoMap={};
	var allInfoMap={} ;
    	for ( var i = 0; i < notNull.length; i++) {
    		if(notNull[i].value.trim()==""){
    			alert("请填写带*文本框数据,如果为空请添0!");
    			$(obj).linkbutton('enable');
    			return false;
    		}
    	}
		var flag=true;

		if (flag)
		{
			if(confirm("确定保存买卖合同？")){
        			if(balanceCheck()){
        				//var inputs =$("table[name='contractdata']").find("input");
        				var inputs =$("#contractdata").find("input");
        				for ( var i = 0; i < inputs.length; i++) {
        					var name = $(inputs[i]).attr("name");
        					var type = $(inputs[i]).attr("type") ;
        					//debugger ;
        					if(name != undefined){
        						allInfoMap[name] = $(inputs[i]).val();
        						if("hidden"!=type){
        							showInfoMap[name] = $(inputs[i]).val();
        						}
        					}
        				}
        				//contract.push(dataMap);
        				//alert(inputs.length) ;
        				//debugger ;
        				var url  ;
        				// start 未添加操作项
        				var payTrSaveRecord = new Array() ;
        				var payTrconstraintRecord = new Array() ;
        				payRecord(".pay_tr",payTrSaveRecord,payTrSaveRecord) ;
        				
        				//start 后面添加操作项
        				var payTrAddSaveRecord = new Array() ;
        				var payTrAddTrconstraintRecord = new Array() ;
        				var addData = {} ;
        				var method_type = $("#method_type").val() ;
        				payRecord(".pay_tr_add",payTrAddSaveRecord,payTrAddTrconstraintRecord) ;
        				
        				 if("add"==method_type){
							 url=_basePath+"/contract/BuyContract!saveBuyContract.action" ;
							 payTrSaveRecord.concat(payTrAddSaveRecord) ;
							 payTrconstraintRecord.concat(payTrAddTrconstraintRecord) ;
							 addData ={
	                					allInfoMap:allInfoMap,
	                					PAYMENT_LIST:payTrSaveRecord,
	                					constraintRecord:payTrconstraintRecord
	        					};							 
						 }else if("queryOrUpdate"==method_type){
							 url=_basePath+"/contract/BuyContract!updateBuyContract.action" ;
							 addData = {
									    allInfoMap:allInfoMap,
									    payTrSaveRecord:payTrSaveRecord,
									    payTrconstraintRecord:payTrconstraintRecord,
									    payTrAddSaveRecord:payTrAddSaveRecord,
									    payTrAddTrconstraintRecord:payTrAddTrconstraintRecord
							 };
						 }else{
							 alert("") ;
							 return ;
						 }
        				
        				var alldata = JSON.stringify(addData);
						// var contractForm=JSON.stringify(showInfoMap);
						 jQuery.ajax({
						 	type:"post",
							url: url ,
							data:"alldata="+alldata,
							dataType:"json",
							success:function(s){
							 if(s.flag==true){
								 alert(s.msg);
							 }else{
								 alert(s.msg);
							 }
								
							}
						 });
						 
        				 //$("#mainForm").submit();
        			}else{
        		    	alert("付款约定的总金额必须和设备总额相等！");
            			$(obj).linkbutton('enable');
        			}
			}
			else
			{
    			$(obj).linkbutton('enable');
			}
		}
		else
		{
			$(obj).linkbutton('enable');
		}
	}

function payRecord(obj,paymentdetails,paymentterms){
	var  saveRecord = new Array();
	var  constraintRecord = new Array();
	$(obj).each(function(){
		var temp={};
		//放款金额
		temp.PAY_MONEY=$(this).find("[name=PAY_MONEY]").val();
		//首期款
		temp.SQ_PRICE=$(this).find("[name=SQ_PRICE]").val();
		//贴息金额
		temp.DISCOUNT_MONEY=$(this).find("[name=DISCOUNT_MONEY]").val();
		//预计付款时间
		temp.RESERVE_DATE=$(this).find("[name=RESERVE_DATE]").val();
		//constraint 约束条件
		var checkboxs =$(".pay_body").find("input[type='checkbox']");
		debugger ;
		for ( var i = 0; i < checkboxs.length; i++) {
			var constraintTemp={} ;
			if(checkboxs[i].checked == true)
			  {
				constraintTemp.TERM_ID=checkboxs[i].value;
				constraintTemp.TERM_NAME=checkboxs[i].nextSibling.nodeValue ;
			  };
			  constraintRecord.push(constraintTemp) ;
		};	
		saveRecord.push(temp);
	});
	paymentdetails = saveRecord ;
	paymentterms = constraintRecord ;
}
var tagName="查看买卖合同" ;
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
	var dateid = new Date().getTime();
	 var table=$(obj).parent("td").parent("tr").parent("tbody").parent("table").find(".pay_body");
	 var dictFlagValue=$("#dictFlagValue").val();
	 table.append("<tr align=\"center\" class=\"pay_tr\">"
   				   +"<td ><span class=\"BATCH\"><\span></td>"
                       +"<td><input type=\"text\" class=\"notNull\" id=\"PAY_AMOUNT\" name=\"PAY_MONEY\" onblur=\"two(this);balance()\"  style=\"width:100px;\"  value=\"\"\><font color=\"red\">*</font></td>"
                       +"<td><input type=\"text\" name=\"SQ_PRICE\" value=\"0\" readonly style=\"width:100px;\" />"+"</td>"
		               +"<td><input type=\"text\" name=\"DEKOU_SQ\" value=\"0\" readonly style=\"width:100px;\" />"+"</td>"
		               +"<td><input type=\"text\" size =\"15\" name=\"RESERVE_DATE\" id=\""+dateid+"\"  />"+"</td>"
                       +"<td>"+dictFlagValue+"</td>"
                       +"<td><a href=\"javascript:void(0);\" onclick=\"deletePay(this);\">删除</a></td>"
                 +"</tr>");
//	$.parser.parse();
	$("#"+dateid).datebox();
	sortBatch();
}

//日期监听空间
function dateListening(ori_id,dest_id){
	var prefix_id="#" ;
	var oriId = prefix_id + ori_id ;
	var destId = prefix_id + dest_id ;
	$(oriId).datebox({  
        required:true,  
        onSelect: function(date){  
			$(destId).val(date.format("yyyy-MM-dd")) ;
			$(destId).attr("changeFlag","true") ;
        }  
    }); 
}

/** 
 * 时间对象的格式化 
 */  
 Date.prototype.format = function(format)  
 {  
	 /* 
	 * format="yyyy-MM-dd hh:mm:ss"; 
	 */  
	 var o = {  
	 "M+" : this.getMonth() + 1,  
	 "d+" : this.getDate(),  
	 "h+" : this.getHours(),  
	 "m+" : this.getMinutes(),  
	 "s+" : this.getSeconds(),  
	 "q+" : Math.floor((this.getMonth() + 3) / 3),  
	 "S" : this.getMilliseconds()  
	 } ; 
   
	 if (/(y+)/.test(format))  
	 {  
	 format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
	 - RegExp.$1.length));  
	 }  
   
	 for (var k in o)  
		 {  
		 if (new RegExp("(" + k + ")").test(format))  
		 {  
		 format = format.replace(RegExp.$1, RegExp.$1.length == 1  
		 ? o[k]  
		 : ("00" + o[k]).substr(("" + o[k]).length));  
		 }  
	 }  
 return format;  
 }  ;

//付款约定  计算余额
function balance(obj){
	
	//签订时间 监听
	dateListening("SIGN_DATE_SHOW_ID","SIGN_DATE_HIDE_ID") ;
	//担保人签字日期 监听
	dateListening("GUARANTOR_SIGNED_SEAL_DAT_SHOW_ID","GUARANTOR_SIGNED_SEAL_DAT_HIDE_ID") ;
	//鉴证日期 监听
	dateListening("FORENSIC_AUTHORITIES_SEAL_DAT_SHOW_ID","FORENSIC_AUTHORITIES_SEAL_DAT_HIDE_ID") ;

	
	var isQuery = $("#isQuery").val() ;
	$("#closeId").hide() ;
	if(isQuery=="true"){
			$("#saveId").hide() ;
			$("#closeId").show() ;
		$("input").each(function(){
			$(this).attr("disabled","true") ;
		});
	}
	$("#contractdata input").each(function (){
		$(this).attr("changeFlag","false") ;
		var type = $(this).attr("type") ;
		var name = $(this).attr("name") ;
		if(type!=undefined){
			$(this).change(function(){
				$(this).attr("changeFlag","true") ;
			});
		}
	});
	
	
	
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
		    var DISCOUNT_MONEY=$(this).find("input[name='DEKOU_SQ']").val();
		    tiexiAmount=accAdd(tiexiAmount,DISCOUNT_MONEY);
		    
	    });
     var equipsum=$("#equipsum").val();
     equipsum = jian(equipsum,firstAmount);
     equipsum = jian(equipsum,tiexiAmount);
	   surplus=anyDouble2(jian(equipsum,sumpay),2);
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
	    var  LEASE_MODEL = $("#LEASE_MODEL").val();
	    var booleanFlag=true;
	    if(LEASE_MODEL=='back_leasing'){
	    	booleanFlag=true;
	    }else{
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
				    var DISCOUNT_MONEY=$(this).find("input[name='DEKOU_SQ']").val();
				    tiexiAmount=accAdd(tiexiAmount,DISCOUNT_MONEY);
				    
			    });
		     var equipsum=$("#equipsum").val();
		     equipsum = jian(equipsum,firstAmount);
		     equipsum = jian(equipsum,tiexiAmount);
			   surplus=anyDouble2(jian(equipsum,sumpay),2);
			   if(surplus>0){
				   booleanFlag = false;
			   }
	    }
	   
	      
      return booleanFlag;
	   
}

//只能输入数字
function zero(ele){
	ele.value=ele.value.replace(/\D{1,100}/,"");
}

function saveOrDelPaymentTermFlag(){
	
}


function closeTab(){
	//alert(tagName) ;
	top.closeTab(tagName);
}

function clostTab(tabName){
	top.closeTab(tabName);
}

//更新买卖合同
function saveBuyContract(obj){
	if(!chk1()) return;
	$(obj).linkbutton('disable');
	var notNull =$(".notNull");
	var contract=[];
	//TODO 展现数据Map没有用到
	var showInfoMap={};
	var allInfoMap={} ;
    	for ( var i = 0; i < notNull.length; i++) {
    		if($.trim(notNull[i].value)==""){
    			alert("请填写带*文本框数据,如果为空请添0!");
    			$(obj).linkbutton('enable');
    			return false;
    		}
    	}
		var QD_TIME = $("input[name='SIGN_DATE']").val();
		if(!QD_TIME){
			alert("请填写带*文本框数据,如果为空请添0!");
			$(obj).linkbutton('enable');
			return false;
		}
		var flag=true;

		if (flag)
		{
			if(confirm("确定保存买卖合同？")){
        			if(balanceCheck()){
        				
        				var allInfoMap={} ;
        				var changeInfoMap={} ;
        				$("#contractdata input").each(function (){
        					var name = $(this).attr("name");
        					var type = $(this).attr("type") ;
        					if(name!=undefined){
        						if($(this).attr("changeFlag")=="true"){
        							changeInfoMap[name]=$(this).val();
        						}
        						allInfoMap[name]=$(this).val();
        					}
        				});
        			
        				var  saveRecord = new Array();
        				var  constraintRecord = {};
        				var i=0;
        				$(".pay_tr").each(function(){
        					var temp={};
        					var temp_constraint=new Array()  ;
        					//paymentdetail id
        					var paymentId = $(this).find("[name=paymentDetailId]").val() ;
        					temp.ID = paymentId ;
        					
        					//放款金额
        					temp.PAY_MONEY=$(this).find("[name=PAY_MONEY]").val();
        					//首期款
        					temp.SQ_PRICE=$(this).find("[name=SQ_PRICE]").val();
        					//贴息金额
        					temp.DISCOUNT_MONEY=$(this).find("[name=DEKOU_SQ]").val();
        					//预计付款时间
        					temp.RESERVE_DATE=$(this).find("[name=RESERVE_DATE]").val();
        					//constraint 约束条件
        					var checkboxs =$(this).find("input[type='checkbox']");
        					
        					for(var j=0;j<checkboxs.length;j++){
        						var constraintTemp={} ;
        						var idValue = checkboxs[j].getAttribute("idValue");
        				
        						if(idValue==undefined){
        							constraintTemp.ID="" ;
        						}else{
        							constraintTemp.ID=idValue ;
        						}
        						var saveOrDelFlag = false ;
        						if(checkboxs[j].checked == true)
        						  {	
        							constraintTemp.TERM_ID=checkboxs[j].value;
        							constraintTemp.TERM_NAME=checkboxs[j].getAttribute("nameValue") ;
        						  }else{
        							  if(constraintTemp.ID!=""){
        								  saveOrDelFlag = true ;
        							  }
        						  };
        						  constraintTemp.SAVE_OR_DEL_FLAG = saveOrDelFlag ;
        						  temp_constraint.push(constraintTemp) ;
        					}
        		
        					constraintRecord[i]=temp_constraint ;
        					saveRecord.push(temp);
        					i++;
        						
        				}); 
        				
        				
        				var addData ={
        					allInfoMap:allInfoMap,
        					changeInfoMap:changeInfoMap,
        					PAYMENT_LIST:saveRecord,
        					constraintRecord:constraintRecord,
        					LEASE_MODEL:$("#LEASE_MODEL").val()
        				};
        							
        				var alldata = JSON.stringify(addData);
						 var contractForm=JSON.stringify(showInfoMap);
						 var isEdit = $("#isEdit").val() ;
						 var url  ;
						 if("true"==isEdit){
						 	 tagName = "更新买卖合同" ;
							 url=_basePath+"/contract/BuyContract!updateBuyContract.action" ;
						 }else{
						 	 tagName = "添加买卖合同" ;
							 url=_basePath+"/contract/BuyContract!saveBuyContract.action" ;
						 }	 
						
						 jQuery.ajax({
						 	type:"post",
							url: url ,
							data:"alldata="+alldata,
							dataType:"json",
							success:function(s){
							 if(s.flag==true){
								 alert(s.msg);
								 $("#saveId").hide() ;
								 $("#closeId").show() ;
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

function chk1() {
	if($(".chk1Items:checked").size()<=0) return true;
	var url=_basePath+"/contract/BuyContract!chk1.action";
	var data = {};
	data.PROJECT_CODE=$('input[name=PROJECT_CODE]').val();//合同号
	data.PAYLIST_CODE=$('input[name=PAYLIST_CODE]').val();//支付表号
	data.FPE_ID=$('input[name=FPE_ID]').val();//设备id
	data.CLIENT_ID=$('input[name=CLIENT_ID]').val();//承租人id
	data.IDS='';
	$(".chk1Items:checked").each(function(){
		data.IDS += $(this).val()+',';
	});
	data.IDS = data.IDS.substring(0, data.IDS.length-1);
	var flag = false;
	$.ajax({
		url: url,
		data: data,
		dataType: 'json',
		async: false,
		success: function(json) {
			if(!json.data.flag) {
				$.messager.alert('提示', json.data.msg);
				flag = false;
			} else flag = true;
			
		}
	});
	return flag;
}
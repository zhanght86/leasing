
//添加银行    初始化
function addBankInit(){
	window.location="Bank!getCreateBank.action";
}

//添加帐号    初始化
function addBankAccountInit(){
	window.location="Bank!getCreateAccount.action";
}

//添加银行页面    验证            
function addBank() {
		if ($("#BANK_NAME").val()=="") {
			alert("请填写银行名称!");
			return false;
		}
		if ($("#BANK_CODE").val()=="") {
			alert("请填写银行编号!");
			return false;
		}
		if ($("#BANK_LINKMAN").val()=="") {
			alert("请填写联系人!");
			return false;
		}
		
	    if (telPhone($.trim($("#BANK_PHONE").val()), '联系电话：')) {
	    	   return false;
	    	}
	   	if (postCode($.trim($("#POST_CODE").val()), '邮编：')) {
	    	   return false;
	    	}		
	  	var obj = $("input[type='radio']");
	  	for(var i = 0; i<obj.length;i++) {
	  		if(obj[i].checked) {
	  			if(obj[i].value == 1){
	  				if($("#PARENT_ID").val() == 0 ){
	  					alert("请选择父银行！");
	  					return false;
	  				}
	  			}
	  		}
	  	}
	  	$("#mainForm").submit();
}


//添加账户页面    验证            
function addBankAccount() {
	if ($("#ACCOUNT_ALIAS").val()=="") {
		alert("请填写账户别名!");
		return false;
	}
	if ($("#ACCOUNT_TYPE").val()==0) {
		alert("请填写账号类型！!");
		return false;
	}
	$("#mainForm").submit();
}


//查找并回显一条银行记录
function showBank(row){
	 if (row){
		 window.location="Bank!getBankByIdForShow.action?BABI_ID="+row.BABI_ID;
	 }
	
}

//查找并回显一条银行记录
function preUpdateBank(row){
	 if (row){
		 window.location="Bank!getBankByIdForUpdate.action?BABI_ID="+row.BABI_ID;
	 }
}

//更新银行    验证
function updateBank(){
	
	if ($("#BANK_NAME").val()=="") {
		alert("请填写银行名称!");
		return false;
	}
	if ($("#BANK_CODE").val()=="") {
		alert("请填写银行编号!");
		return false;
	}
	if ($("#BANK_LINKMAN").val()=="") {
		alert("请填写联系人!");
		return false;
	}	

  if (telPhone($.trim($("#BANK_PHONE").val()), '联系电话：')) {
 	   return false;
 	}
	if (postCode($.trim($("#POST_CODE").val()), '邮编：')) {
 	   return false;
 	}		
	
	var obj = $("input[type='radio']");
	for(var i = 0; i<obj.length;i++) {
		if(obj[i].checked) {
			if(obj[i].value == 1){
				if($("#PARENT_ID").val() == 0 ){
					alert("请选择父银行！");
					return false;
				}
			}
		}
	}  	
	$("#mainForm").submit();
}

//作废一银行记录
function invalidBank(row){
	 if (row){
		 var id=row.BABI_ID;
		 jQuery.ajax({
				url: "Bank!getBankAndAccountCountByParentId.action",
				type:"post",
				data: "BABI_ID="+id,
				dataType:"json",
				success: function(date){
					if(date.flag==false){
			    		if(confirm('确认作废该条记录？')){
			    			window.location="Bank!invalidBank.action?BABI_ID="+id;
			    		}else{
			    			return false;
			    		}
			    	}else{
			    		alert("此银行还有下属分行或者已有在该行开户的账号，必须先删除下面的分行或账号，才能删除此银行！！");
			    		return false;
			    	}				
				},
				error: function(i){
					alert("网络连接失败，请重试");
					return false;
				}
			});   	
	 }
}

//修改页面  根据银行类型 确定单选框选哪个
function parentBankDisplay(obj,id) {	
	var value = $(obj).val();
	$("#PARENT_ID").val(0);
	if(value == 0) {
		$("#parentList").css("display","none");
		isBankSubmit = true;
		isBankHaveChild = true;
	}else{
		jQuery.ajax({
			url: "Bank!getChildCountByParentId.action",
			type:"post",
			data: "BABI_ID="+id,
			dataType:"json",
			success: function(date){
				if(date.flag==false){
	    			$("#parentList").css("display","");
	    			isBankSubmit = true;
	    			isBankHaveChild = true;
	    		}else{
	    			$("input[name='TYPE'][value='0']").attr("checked",true);
	    			isBankSubmit = false;
	    			isBankHaveChild = false;
	    			alert("此银行还有下属分行，不能设为分行！");
	    			return;
	    		}    				
			},
			error: function(i){
				alert("网络连接失败，请重试");
				return false;
			}
		});    		
	}
}
	
	//显示指定id的银行账户
	  function showBankAccount(row){	
		  if (row){
			window.location.href="Bank!getBankAccountByIdForShow.action?BABA_ID="+row.BABA_ID;
		  }
	  }
	  
	//查找并显示指定id的银行账户
	  function preUpdateBankAccount(row){
		  if (row){
			window.location.href="Bank!getBankAccountByIdForUpdate.action?BABA_ID="+row.BABA_ID;
		  }
	  }
	  
	//更新银行账户 并验证
	  function updateBankAccount(){
	     	  
	  		if ($("#ACCOUNT_NAME").val()=="") {
	  			alert("请填写开户人!");
	  			return false;
	  		}
	  		if ($("#ACCOUNT_ALIAS").val()=="") {
	  			alert("请填写账户别名!");
	  			return false;
	  		}
	  		if ($("#ACCOUNT_TYPE").val()==0) {
	  			alert("请填写账号类型！!");
	  			return false;
	  		}		
	  		
	    	var obj = $("input[type='radio']");
	    	for(var i = 0; i<obj.length;i++) {
	    		if(obj[i].checked) {
	    			if(obj[i].value == 1){
	    				if($("#PARENT_ID").val() == 0 ){
	    					alert("请选择主账户！");
	    					return false;
	    				}
	    			}
	    			if(obj[i].value == 0){
	    				if ($("#BABI_ID").val() == 0) {
	    					alert("请选择银行！");
	    					return false;
	    				}
	    			}
	    		}
	    	}

	  	$("#mainForm").submit();	
	  }
	  
	//删除指定id的账户
	  function invalidBankAccount(row){
		  if (row){
			  var id=row.BABA_ID;
			  jQuery.ajax({
					url: "Bank!getAccountCountByParentId.action",
					type:"post",
					data:"BABA_ID="+id,
					dataType:"json",
					success: function(date){
						  	if(date.flag==false){
						  		if(confirm('确认删除该条记录？')){
						  			window.location="Bank!invalidBankAccount.action?BABA_ID="+id;
						  		}else{
						  			return false;
						  		}
						  	}else{
						  		alert("该账户还有子账户，不能删除");
						  		return false;
						  	}	
					},
					error: function(i){
						alert("网络连接失败，请重试");
						return false;
					}
				});   
		  }
	  }

	  //添加账户页面       是否 显示下拉选列表
	    function createParentAccountDisplay(obj) {
	    	var value = $(obj).val();
	    	 $("#PARENT_ID").val(0);
	    	if(value == 0) {
	    		$("#parentList").css("display","none");
	    		$("#bankList").css("display","");
	    		$("#bankByPAccount").css("display","none");
	    	}else{
	    		$("#parentList").css("display","");
	    		$("#bankList").css("display","none");
	    		$("#bankByPAccount").css("display","");
	    	}
	    }
	    
	  //添加银行页面       根据是否总行分行 显示分行下拉选列表
	    function createParentBankDisplay(obj) {
	    	var value = $(obj).val();
	    	$("#parent_id").val(0);
	    	if(value == 0) {
	    		$("#parentList").css("display","none");
	    	}else{
	    		$("#parentList").css("display","");
	    	}
	    }
	    
	    function telPhone(value, name) {
    		if (value != "") {
    			if (!checkTelPhone(value)) {
    				alert(name + '必须是电话');
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    		return false;
    	}
	    
	    function checkTelPhone(value)
	    {
	      var regex1 = /^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})){1}\d{8}$/;
	    	var regex2 = /^(0[0-9]{2,3}(\-)?)?([2-9]{1}[0-9]{6,7}){1}(\-[0-9]{1,4})?$/;
	      return regex1.test(value)||regex2.test(value);
	    }   
	  
	    function postCode(value, name) {
	    	  value = value.replace(/[￥,]/g, '');
	    		if (value != "") {
	    			if (value.length != 6 || !/^\d+(\.\d+)?$/.test(value)) {
	    				alert(name + '只能输入6位数字！');
	    				return true;
	    			}
	    			else {
	    				return false;
	    			}
	    		}
	    		return false;
	    	}
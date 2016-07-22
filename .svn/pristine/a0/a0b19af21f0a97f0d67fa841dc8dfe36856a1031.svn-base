//保存抵押
function save() {
	if(check()){
			$("#mainForm").form('submit',{
				dataType:"json",
				
				success:function(json){
					var obj = eval('(' + json + ')');
					if(obj.flag==true){
						alert("创建抵押成功");
						window.location.href = _basePath+"/mortgageProduct/MortgageProduct.action";
					}else{
						alert("创建抵押失败");
						window.location.href = _basePath+"/mortgageProduct/MortgageProduct.action";
					}
				}
			});
	}else{
			alert('请填入必填项');
	}
}

//必填选项验证
function check (){
	var flag=true;
	if($("#MORTGAGE_DATE").datebox('getValue')==''||$("#MORTGAGE_DATE").datebox('getValue')==null){
		return flag = false;
	}
	
	if($("#MORTGAGE_START_TIME").datebox('getValue')==''||$("#MORTGAGE_START_TIME").datebox('getValue')==null){
		return flag = false;
	}
	
	if($("#MORTGAGE_END_TIME").datebox('getValue')==''||$("#MORTGAGE_END_TIME").datebox('getValue')==null){
		return flag = false;
	}
	var i = 0;
	$(".yanzradio").each(function(){
			if(!$(this).attr("checked")){
				i++;
			}
		});
	if(i==3){
		flag=false;
	}
	return flag;
}	

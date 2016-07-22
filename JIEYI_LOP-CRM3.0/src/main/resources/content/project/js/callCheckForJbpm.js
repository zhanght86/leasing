/**
 * 05.呼叫中心审批js
 * 作者:韩晓龙
 */
var url = "";
function save(TYPE,PROJECT_ID){
	url = _basePath +'/call/CallCenterFlow!saveMessage.action?TYPE=' + TYPE + '&PROJECT_ID=' + PROJECT_ID;
	$('#fm').form('submit',{
		url: url,
        onSubmit: function(){
    		//校验各个按钮是否都已选择
        },
        success: function(result){
        	//成功后的执行事件
        	alert("执行保存操作!");
        }
    });
}

//radio全选是
function chall(){
	$("input[type=radio][value='是']").attr("checked","checked");
	
	$("input[type=radio][value='是']").each(
			function(){
				 var radvalue=$(this).val();
				 var inputName=$(this).attr("INPUTNAME");
				
				 if(radvalue=='是'){
					 if(inputName == "" || inputName == null || inputName == undefined){
						 inputName=$(this).attr("SELECTNAME");
						 if(inputName == "" || inputName == null || inputName == undefined){
							 ;
						 }
						 else{
//							 var OldValue=$("select[name='"+inputName+"']").attr("OLDVALUE");
							 $("select[name='"+inputName+"']").attr("style","border:0");
							 $("select[name='"+inputName+"']").attr("disabled","disabled");
//							 $("select[name='"+inputName+"'] option[CODE='"+OldValue+"']").attr("selected",true);
						 }
					 }
					 else{
//						 var OldValue=$("input[name='"+inputName+"']").attr("OLDVALUE");
						 $("input[name='"+inputName+"']").attr("style","border:0");
						 $("input[name='"+inputName+"']").attr("readonly","readonly");
//						 $("input[name='"+inputName+"']").val(OldValue);
					 }
				 }
				 
			 }
	)
}

$(function(){
	 $("input[type=radio]").click(function(){
		 var radvalue=$(this).val();
		 var inputName=$(this).attr("INPUTNAME");
		
		 if(radvalue=='是'){
			 if(inputName == "" || inputName == null || inputName == undefined){
				 inputName=$(this).attr("SELECTNAME");
				 if(inputName == "" || inputName == null || inputName == undefined){
					 ;
				 }
				 else{
//					 var OldValue=$("select[name='"+inputName+"']").attr("OLDVALUE");
					 $("select[name='"+inputName+"']").attr("style","border:0");
					 $("select[name='"+inputName+"']").attr("disabled","disabled");
//					 $("select[name='"+inputName+"'] option[CODE='"+OldValue+"']").attr("selected",true);
				 }
			 }
			 else{
//				 var OldValue=$("input[name='"+inputName+"']").attr("OLDVALUE");
				 $("input[name='"+inputName+"']").attr("style","border:0");
				 $("input[name='"+inputName+"']").attr("readonly","readonly");
//				 $("input[name='"+inputName+"']").val(OldValue);
			 }
		 }
		 else{
			 if(inputName == "" || inputName == null || inputName == undefined){
				 inputName=$(this).attr("SELECTNAME");
				 if(inputName == "" || inputName == null || inputName == undefined){
					 ;
				 }
				 else{
					 $("select[name='"+inputName+"']").attr("style","border:1");
					 $("select[name='"+inputName+"']").removeAttr("disabled");
				 }
				 
			 }
			 else{
				 $("input[name='"+inputName+"']").attr("style","border:1");
				 $("input[name='"+inputName+"']").removeAttr("readonly","readonly");
			 }
		 }
	 });
});


function selChange(obj,inputName,selName){
	var valCode=$(obj).find("option:selected").attr("CODE");
	var valValue=$(obj).find("option:selected").val();
	$("input[name='"+inputName+"']").val(valCode);
	$("input[name='"+selName+"']").val(valValue);
}
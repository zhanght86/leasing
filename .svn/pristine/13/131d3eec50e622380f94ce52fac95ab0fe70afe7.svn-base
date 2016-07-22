 $(document).ready(function() {
	$("#frmSearch").validationEngine();
})
$(document).ready(function() {
    $("#insetBCform").validationEngine();
})
$(document).ready(function() {
	$("#upBCform").validationEngine();
})

		function insert(type){
		}
			//显示弹出层
       function showDiv() {
    		$('#insetBC').dialog({
        		modal:true,
        		autoOpen: true,
        		width:350,
        		height: 200,
				
				buttons:{
					"保存":function(){
						if(!$("#insetBCform").validationEngine({returnIsValid:true})){
						return false;
					}
						var date="";
						$("#insetBC [name]").each(function(){
							var ele=$(this);
							date+="&"+ele.attr("name")+"="+ele.val();
							alert(ele.val());
						});
						date=date.substring(1,date.length);
						
            			jQuery.ajax({
            				url : _basePath+"/Condition/Condition!doAjaxInsetBailoutCondition.action",
            				data : date,
            				success : function(text)
            				{
								window.location.href="Condition!getAllCondition.action";
            				}
            			  });
					}
        		}
			});
			
        	$('#insetBC').dialog('open');		  
        }
		//关闭弹出层
        function closeDivDialog(){
        	$('#insetBC').dialog('close');
        }

		function update(type){
		}
			//显示弹出层
       function showDiv1(TRCID,CNAME,BIDBONDRENT) {
	   document.getElementById("UPCNAME").innerHTML=CNAME;
	   document.getElementById("UPBIDBONDRENT").value=BIDBONDRENT;
    		$('#updateBC').dialog({
        		modal:true,
        		autoOpen: true,
        		width:400,
        		height: 200,
				buttons:{
					"重置":function(){
						 document.getElementById("UPCNAME").value=CNAME;
						  document.getElementById("UPBIDBONDRENT").value=BIDBONDRENT;
					},
					"保存":function(){
					if(!$("#upBCform").validationEngine({returnIsValid:true})){
						return false;
					}
						var date="";
						$("[name]").each(function(){
							var ele=$(this);
							date+="&"+ele.attr("name")+"="+ele.val();
						});
						date=date.substring(1,date.length);
						date=date+"&TRCID="+TRCID;
            			jQuery.ajax({
            				url : _basePath+"/Condition/Condition!doAjaxgetReviseBailoutCondition.action",
            				data : date,
            				success : function(text)
            				{
								window.location.href=_basePath+"/Condition/Condition!getAllCondition.action";
            				}
            			});
					}
        		}
			});
		
        	$('#updateBC').dialog('open');		  
        }
		//关闭弹出层
        function closeDivDialog(){
        	$('#updateBC').dialog('close');
        }
		function dataDictionary(){
			document.getElementById("dictionary").value="正在获取..";
			document.getElementById("dictionary").disabled=true;
			jQuery.post(_basePath+'/Condition/Condition!insertDataDictionary.action',{'':''},function(data){
				if(parseInt(data)>0){
					document.getElementById("dictionary").value="获取成功";
					alert("成功添加了："+data+"条数据！");
					window.location.href=_basePath+"/Condition/Condition!getAllCondition.action";
				}else{
					document.getElementById("dictionary").value="获取失败";
					document.getElementById("dictionary").disabled=false;
				}
    	 },'text');
	}
	function cleans(){
			$('#CNAME').val('');
			$('#INSETDATE').val('');
	}
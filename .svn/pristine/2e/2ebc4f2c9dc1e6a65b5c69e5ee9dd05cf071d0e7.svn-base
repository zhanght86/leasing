	$(document).ready(function(){ 
		window.onresize=resizeBannerImage
	})

	function resizeBannerImage(){
		$('#panel').panel({ width:document.getElementById("maxDiv").clientWidth });
	}
	
	function check(){
		var name = $("#NAME").val();
		var ID_CARD_NO = $("#IDCARD").val();
		if(!name){
			alert("证件名称不能为空值！");
			return;
		}
		if(!ID_CARD_NO){
			alert("证件号不能为空值！");
			return;
		}
		$(".photo").attr("src","loading.gif");  
		jQuery.ajax({
			url:_basePath+"/checkIdCard/CheckIdCard!doCheck.action?_time="+new Date(),
			data: "NAME="+name+"&ID_CARD_NO="+ID_CARD_NO,
			type : "post",
			dataType:"json",
			async : true,
			success: function(json){
				if(json.flag){
					$("#result").empty();
					$("#result").append(json.data.RESULT);
					$("#ZJ_NAME").empty();
					$("#ZJ_NAME").append(json.data.NAME);
					$("#ID_CARD_NO").empty();
					$("#ID_CARD_NO").append(json.data.ID_CARD_NO);
					$("#SEX").empty();
					$("#SEX").append(json.data.SEX);
					$("#BIRTHDAY").empty();
					$("#BIRTHDAY").append(json.data.BIRTHDAY);
					$("#ADDRESS").empty();
					$("#ADDRESS").append(json.data.ADDRESS);
//					$(".photo").attr("src","data:jpeg/png;base64,"+json.data.ceshi); 
					$(".photo").attr("src",_basePath+"/checkIdCard/CheckIdCard!downloadPictureFile.action?ID_CARD_NO="+json.data.ID_CARD_NO); 
					$(".photoA").attr("href",_basePath+"/checkIdCard/CheckIdCard!downloadPictureFile.action?ID_CARD_NO="+json.data.ID_CARD_NO); 
				}else{
					$("#result").empty();
					if(json.data){
						if(json.data.RESULT){
							$("#result").append(json.data.RESULT);
						}
					}else{
						$("#result").append('验证失败');
					}
					$("#ZJ_NAME").empty();
					$("#ZJ_NAME").append('无');
					$("#ID_CARD_NO").empty();
					$("#ID_CARD_NO").append('无');
					$("#SEX").empty();
					$("#SEX").append('无');
					$("#BIRTHDAY").empty();
					$("#BIRTHDAY").append('无');
					$("#ADDRESS").empty();
					$("#ADDRESS").append('无');
					$(".photo").attr("src","noPic.png"); 
					$(".photoA").attr("href","javascript:void(0)");
				}
			}
		});
	}
	//验证身份证号码
	function isCardNo(ele){ 		
		var card = $("#"+ele).val();
		var id_card_type = '1';
		if(id_card_type=="1"){//驗證是否是居民身份證//若果是居民身份證則驗證身份證是否填寫正確
			// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
		    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		    if(reg.test(card) === false){  
		        alert("身份证输入不合法");  
		        $("#"+ele).val("");
		        return  false;  
		    }else{
		    	var bornDate = cardNo2bornDate(card);
		    	if(ele=='ID_CARD_NO') {
					$('#BIRTHDAY').datebox('setValue',bornDate);
		    	} else if(ele=='SPOUDT_ID_CARD_NO'){
		    		$('#SPOUST_BIRTHDAY').datebox('setValue',bornDate);
		    	}
		    	return true;
		    } 
		}else {
			alert("证件类号为港澳台通行证或护照");
			return true;
		}  
	}

var reTimeFlag = 1;
function downfiles(){
		
		var files = document.getElementsByName("downfils");
		if(files==null || files.length==0){
			alert("没有下载项");
		}else{
			var flag=0;
			for(var i=0;i<files.length;i++){
				if(files[i].checked){
					flag+=1;
				}
			}
			if(flag>0){
            	var arrayObj = [];
        		for(var i=0;i<files.length;i++){
        			if(files[i].checked){
            			var count = i+1;
            			var pdfTempId = $("#pdfTempId"+count).val();
            			var FILE_NAME = $("#pdfTempName"+count).val();
            			var tempPath = $("#tempPath"+count).val();            			
                    	var  fileItem = {
                    	    	"pdfTempId":pdfTempId,
                    	    	"FILE_NAME":FILE_NAME,
                    	    	"tempPath":tempPath
                    		};
                    		arrayObj.push(fileItem);
							
        			}
                }
                var CLIENT_ID = $("#CLIENT_ID").val();
                var project_id = $("#PROJECT_ID").val();
			   $("#files").val(JSON.stringify(arrayObj));
				$("#uploadform").submit();
			}else{
				alert("没有选择下载项");
			}
		}
		
	}


function selectAllbox(){
	var selectalls = document.getElementById("selectAlls");
	var files = document.getElementsByName("downfils");
	if(selectalls.checked){
		for(var i=0;i<files.length;i++){
			files[i].checked=true;
		}
	}else{
		for(var i=0;i<files.length;i++){
			files[i].checked=false;
		}
	}
	
}

function exportData(){

	$("#divFrom").empty();
	var PROJECT_ID=$("input[name='PROJECT_ID_CONTRACT']").val();
	
	var url=_basePath+"/project/Contract!uploadPayDetail.action?PROJECT_ID="+PROJECT_ID;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
	
}

function downloadPdf(PATH)
{
	window.location.href = _basePath
	+ "/project/Contract!downPdfTemplate.action?PATH="
	+ encodeURI(PATH);
}

//add gengchangbao 2016年4月21日11:19:53 JZZL-167 Start
function expContract(url){
	if (reTimeFlag == 1) {
		reTimeFlag = 2;
		
		 var h = document.body.clientHeight;   
		 $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:h}).appendTo("body");   
		 $("<div class=\"datagrid-mask-msg\"></div>").html("正在导出，请稍候.....").appendTo("body").css({display:"block",  
		  left:($(document.body).outerWidth(true) - 190) / 2,  
		  top:(h - 45) / 2}); 
		  window.location.href=url;
		setTimeout(function() {
			reTimeFlag = 1;
			 $('.datagrid-mask-msg').remove();  
			 $('.datagrid-mask').remove(); 
		}, 3000);//3秒后才能点击
	}
}
//add gengchangbao 2016年4月21日11:19:53 JZZL-167 End
	
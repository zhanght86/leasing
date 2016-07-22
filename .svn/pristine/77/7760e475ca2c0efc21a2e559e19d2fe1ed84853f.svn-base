/**
 * 营销平台—添加-省市县下拉列表
 * @return
 */
function pjtLocationJs(parentId,areaLevel,pjtLocation){
	
	jQuery.ajax({
        url: "GlsPjtLocation!getGlsPjtLocationProvince.action",
        data:"parentId="+parentId+"&areaLevel="+areaLevel,
        type: "post",
        dataType: "json",
        success: function(json){
			if(2==areaLevel){
				var selectedId="";
				$("#province").empty();
				$("#province").append("<option>--请选择--</option>");
				for ( var int = 0; int < json.data.length; int++) {
					if(null!=pjtLocation&&pjtLocation.split("-")[0]==json.data[int].NAME){
						$("#province").append("<option value='"+json.data[int].ID+",3' selected='true'>"+json.data[int].NAME+"</option>");
						selectedId=json.data[int].ID;
					}else{
					    $("#province").append("<option value='"+json.data[int].ID+",3'>"+json.data[int].NAME+"</option>");
					}
				}
				$("#city").empty();
				$("#city").append("<option>--请选择--</option>");
				$("#county").empty();
				$("#county").append("<option>--请选择--</option>");
				if(null!=pjtLocation){
					pjtLocationJs(selectedId,3,pjtLocation);
				}
			}else if(3==areaLevel){
				var selectedId="";
				$("#city").empty();
				$("#city").append("<option>--请选择--</option>");
				for ( var int = 0; int < json.data.length; int++) {
					if(null!=pjtLocation&&pjtLocation.split("-")[1]==json.data[int].NAME){
						$("#city").append("<option value='"+json.data[int].ID+",4' selected='true'>"+json.data[int].NAME+"</option>");
						selectedId=json.data[int].ID;
					}else{
					    $("#city").append("<option value='"+json.data[int].ID+",4'>"+json.data[int].NAME+"</option>");
					}
				}
				$("#county").empty();
				$("#county").append("<option>--请选择--</option>");
				if(null!=pjtLocation){
					pjtLocationJs(selectedId,4,pjtLocation);
				}
			}else if(4==areaLevel){
				$("#county").empty();
				$("#county").append("<option>--请选择--</option>");
				if(parentId!=100439000||parentId!=100246608||parentId!=100010003||parentId!=100009998){
				for ( var int = 0; int < json.data.length; int++) {
						if(null!=pjtLocation&&pjtLocation.split("-")[2]==json.data[int].NAME){
							$("#county").append("<option value='"+json.data[int].ID+"' selected='true'>"+json.data[int].NAME+"</option>");
						}else{
							$("#county").append("<option value='"+json.data[int].ID+"'>"+json.data[int].NAME+"</option>");
						}
					}
				}
			}
        }
    });
}

/**
 * 对数据库字段进行赋值
 * @return
 */
function getPjtLocationData(){
	 var province,city,county;
	 var PJT_LOCATION = "";
	 var provinceIndex = document.getElementById("province").options.selectedIndex;
	 var cityIndex = document.getElementById("city").options.selectedIndex;
	 var countyIndex = document.getElementById("county").options.selectedIndex;
	 if(0!=provinceIndex){
		 province = $("#province").find("option:selected").text();
		 PJT_LOCATION+=province;
	 }
	 if(0!=cityIndex){
		 city = $("#city").find("option:selected").text();
		 PJT_LOCATION+="-"+city;
	 }
	 if(0!=countyIndex){
		 county = $("#county").find("option:selected").text();
		 PJT_LOCATION+="-"+county;
	 }
	 $("#PJT_LOCATION").val(PJT_LOCATION);
	 //$("#PJT_LOCATION1").val(PJT_LOCATION);
	 //alert(PJT_LOCATION);
}
/**省市县三级下拉列表begin**/
function showPjtLocation(pjtLocation){
	    //alert(pjtLocation);
		pjtLocationJs(160216745,2,pjtLocation);
		$("#province").change(function(){
		   var str=this.value.split(",");
		   pjtLocationJs(str[0],str[1]);
		});
		$("#city").change(function(){
		   var str=this.value.split(",");
		   pjtLocationJs(str[0],str[1]);
		});
		
}

/*批示/批示查看**/
function toFindGlsByOrderId(ID,ORD_ID){
	//alert("ID="+ID+"|ORD_ID="+ORD_ID);
	$("#addInstruct").hide();
    $("#queryInstruct").hide();
	jQuery.ajax({
        url: "Gls!getGlsData.action?_dateTime="+new Date(),
        data: "ID=" + ID,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				$("#fromDate1 input[name='ID']").val(json.data.ID);
				$("#fromDate1 input[name='PRO_CODE']").val(json.data.PRO_CODE);
				$("#fromDate1 input[name='PJT_SHOT_NAME']").val(json.data.PJT_SHOT_NAME);
				$("#fromDate1 input[name='CUS_NAME']").val(json.data.CUS_NAME);
				$("#PRICE1").numberbox('setValue',json.data.PRICE);
				$("#GENJINREN1").val(json.data.GENJINREN);
				$("#PRO_COUNT1").numberbox('setValue',json.data.PRO_COUNT);
				$("#fromDate1 input[name='PJT_NAME']").val(json.data.PJT_NAME);
				
				$("#vv1").datebox('setValue', json.data.GENJIN_DATE);
				$("#sjtxdate1").datebox('setValue', json.data.DATAEND_DATE);
				$("#fromDate1 input[name='DEPT']").val(json.data.DEPT);
				$("#fromDate1 input[name='PJT_LOCATION']").val(json.data.PJT_LOCATION);
				$("#fromDate1 input[name='PJT_REFEREE']").val(json.data.PJT_REFEREE);
				$("#fromDate1 input[name='MEASUREMENT']").val(json.data.MEASUREMENT);
				$("#fromDate1 input[name='WRITE_MEN']").val(json.data.WRITE_MEN);
			
				$("#FOLLOWUP_RECORDS1").val(json.data.FOLLOWUP_RECORDS);
				$("#NEXT_FOLLOW_PLAN1").val(json.data.NEXT_FOLLOW_PLAN);
				$("#TEMPLATE_TYPE_UPDATE1").combobox('select',json.data.STATUS);
				/* $("#uploadedFile").empty();
				jQuery.ajax({
					url: "GlsPjtLocation!getFileListByProjectId.action?_dateTime="+new Date(),
			        data: "ID=" + ID,
			        type: "post",
			        dataType: "json",
			        success: function(json){
			        	if(json.data.length > 0){
						    for(var j=0; j < json.data.length; j++){
							  // alert("file_path="+glsFile[j].FILE_PATH+"|file_name="+glsFile[j].FILE_NAME);
						       $("#uploadedFile").append("<a href='Gls!download.action?path="+json.data[j].FILE_PATH+"&fileName="+json.data[j].FILE_NAME+"'>"+json.data[j].FILE_NAME+"</a></br>");
						    }
						}else{
							$("#uploadedFile").append("未上传文件");
						}
			        }
				}); */
				controlMove("dialogDivShow"); 
				if(parseInt(orgId)==parseInt(ORD_ID)){
					queryInstructByGlsId(json.data.PRO_CODE);
					$("#queryInstruct").show();
					$("#dialogDivShow").dialog({   
						 title:"批示查看",
						 buttons: [{
				            text:'关 闭',
							iconCls:'icon-cancel',
				            handler:function(){
				               $("#dialogDivShow").dialog('close');
				            }
				        }]
					}); 
				}else{
					$("#addInstruct").show();
					$("#dialogDivShow").dialog({   
						 title:"批示",
						 buttons: [{
				            text:'关 闭',
							iconCls:'icon-cancel',
				            handler:function(){
				               
				               $("#dialogDivShow").dialog('close');
				            }
				        }]
					}); 
				}
				$("#dialogDivShow").dialog('open');
            } else {
				$.messager.alert('提示','加载失败，请与管理员联系！');
            }
        }
    })  
}
/**校验批示**/
function validateInstruct(){
	if(""==$("#INSTRUCT_NAME").combobox("getValue")){
		$.messager.alert('提示','请选择批示人');
		return false;
	};
	if(""==$("#INSTRUCT_DATE").datebox("getValue")){
		$.messager.alert('提示','请选择批示时间');
		return false;
	};
	return true;
}

/**保存批示（领导）**/
function saveInstruct(){
	//alert("saveInstruct()");
	if(validateInstruct()){
		jQuery.ajax({
	        url: "GlsPjtLocation!saveInstructionByGlsId.action?_dateTime="+new Date(),
	        data: "param="+getFromData("#fromDate1"),
	        type: "post",
	        dataType: "json",
	        success: function(json){
	        	if(json){
	        		var proCode=$("#fromDate1 input[name='PRO_CODE']").val();
	        		//alert(proCode);
	        		queryInstructByGlsId(proCode);
	        		$("#queryInstruct").show();
	        		$.messager.alert('提示','保存成功');	
	        	}else{
	        		$.messager.alert('提示','保存失败，请与管理员联系！');	
	        	}
	        }
		});
	}
}

/**批示查看**/
function queryInstructByGlsId(proCode){
	//alert("queryInstruct()");
	jQuery.ajax({
        url: "GlsPjtLocation!queryInstructByGlsId.action?_dateTime="+new Date(),
        data: "PRO_CODE="+proCode,
        type: "post",
        dataType: "json",
        success: function(json){
        	//alert(json.data[0]);
        	$("#queryInstruct").empty();
        	if(json.data.length>0){
        		$("#queryInstruct").append($("<tr><td class='table_th' style='text-align:center ;' colspan='4'> 批 示 查 看 </td></tr>"));
        		for(var i=0;i<json.data.length;i++){
        			var glsData =json.data[i];
        			var INSTRUCT_NAME ="";
					if(glsData.INSTRUCT_NAME){
						INSTRUCT_NAME = glsData.INSTRUCT_NAME;
					}
					var INSTRUCT_DATE ="";
					if(glsData.INSTRUCT_DATE){
						INSTRUCT_DATE = glsData.INSTRUCT_DATE;
					}
					var INSTURCT_CONTENT ="";
					if(glsData.INSTURCT_CONTENT){
						INSTURCT_CONTENT = glsData.INSTURCT_CONTENT;
					}
        			//alert("INSTRUCT_NAME="+INSTRUCT_NAME+"|INSTRUCT_DATE="+INSTRUCT_DATE+"INSTURCT_CONTENT="+INSTURCT_CONTENT);
        			 $("#queryInstruct").append($("<tr>").append("<td style='text-align:left;border-top-style: solid;border-top-width: 1px;' colspan='4'> 批 示 "+(i+1)+":</td></tr>"));
        			
        			 $("#queryInstruct").append($("<tr>")
								.append("<td style='text-align:right'>批示人：</td>")
								.append("<td ><input name='INSTRUCT_NAME1' class='easyui-textbox' id='INSTRUCT_NAME1' style='width: 180px;margin:0px' readonly = true value='"+INSTRUCT_NAME+"'/></td>")
								.append("<td style='text-align:right'>批示日期：</td>")
			  					.append("<td ><input class='easyui-textbox' data-options='required:true' style='width:180px' readonly = true value='"+INSTRUCT_DATE+"'></td></tr>"));
        			 $("#queryInstruct").append($("<tr >")
								.append("<td style='text-align:right'>批示内容：</td>")
			 					.append("<td colspan='4'><textarea style='width: 465px;height:100px;resize: none;' readonly = true>"+INSTURCT_CONTENT+"</textarea> </td>")
								.append("</tr>"));
            	}	
        	}else{
        		$("#queryInstruct").append($("<tr><td class='table_th' style='text-align:center ;' colspan='4'> 批 示 查 看 </td></tr><tr><td style='text-align:center ;' colspan='4'>对不起，没有批示记录</td></tr>"));
        	}
        }
	});
	
}

function getUploadFileContent(file){
	var l=$("#oldUploadFileList").children("input[type='file']").length;
	//alert(l+" | "+file.value);
	file.id="uploadFIle"+l;
	file.name="uploadFIle"+l;
	$("#olduploadFileListName").append(file.value+"</br>");
	$(file).clone(true).appendTo("#oldUploadFileList");
	//$("#oldUploadFileList").append(file);
	$("#olduploadFileListName").show();
}


function getUploadFileContent2(file){
	var l=$("#oldUploadFileList2").children("input[type='file']").length;
	//alert(l+" | "+file.value);
	file.id="uploadFIle"+l;
	file.name="uploadFIle"+l;
	$("#olduploadFileListName2").append(file.value+"</br>");
	$(file).clone(true).appendTo("#oldUploadFileList2");
	//$("#oldUploadFileList").append(file);
	$("#olduploadFileListName2").show();
}

/**控制dialog在body内  id为dialog的id**/
function controlMove(id){
	var default_left;  
	var default_top;  
	$('#'+id).dialog({  
	       onOpen:function(){   
	          //dialog原始left  
	          default_left=$('#'+id).panel('options').left;   
	          //dialog原始top  
	          default_top=$('#'+id).panel('options').top;                     
	},  
	      onMove:function(left,top){  //鼠标拖动时事件  
	           var body_width=document.body.offsetWidth;//body的宽度  
	           var body_height=document.body.offsetHeight;//body的高度  
	           var dd_width= $('#'+id).panel('options').width;//dialog的宽度  
	           var dd_height= $('#'+id).panel('options').height;//dialog的高度                 
	           if(left<1||left>(body_width-dd_width)||top<1||top>(body_height-dd_height)){  
	                  $('#'+id).dialog('move',{      
	                        left:default_left,      
	                        top:default_top      
	                  });    
	          }  
	      }
	}); 
}


function test(){
	
	//alert("glsAreaPjt.js");
}

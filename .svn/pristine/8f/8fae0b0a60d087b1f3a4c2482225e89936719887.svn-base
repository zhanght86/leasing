//$().ready(alert());



function getCitys(ID){
//	 alert(ID);
      jQuery.ajax({
		url :"GpsSearch!getCity.action?PARENT_ID="+ID,
		type :"post",
		dataType :"json",
		success:function (data){
    	  if(data.flag==false)
    	  alert("失败！！ "+data.msg);
			//将本行的输入框初始化
			$("#PROJECT_CITY").each(function (){
				//初始化
				if ($(this).is("select")){
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
					
				}
			});
			$("#PROJECT_COUN").each(function (){
				//初始化
				if ($(this).is("select")){
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
					
				}
			});
			for(var i=0;i<data.length;i++){
                $("#PROJECT_CITY").append("<option sid='"+data[i].AREA_ID+"'  value='"+data[i].NAME+"' onclick='getCouns("+data[i].AREA_ID+")' >"+data[i].NAME+"</option>");
			}
		}
	});
	 
  }
  
  function getCouns(ID){
      jQuery.ajax({
		url :"GpsSearch!getCoun.action?PARENT_ID="+ID,
		type :"post",
		dataType :"json",
		success:function (data){
			//将本行的输入框初始化
			$("#PROJECT_COUN").each(function (){
				//初始化
				if ($(this).is("select")){
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
					
				}
			});
			for(var i=0;i<data.length;i++){
                $("#PROJECT_COUN").append("<option sid='"+data[i].AREA_ID+"'  value='"+data[i].NAME+"'  >"+data[i].NAME+"</option>");
			}
		}
	});
	 
  }
				function baocun(){
					var PROJECT_AREA=$("select[name='PROJECT_AREA']").val();
					var PROJECT_CITY=$("select[name='PROJECT_CITY']").val();
					var EQUIPMENT_ID=$("input[name='EQUIPMENT_ID']").val();
					var PROJECT_COUN=$("select[name='PROJECT_COUN']").val();
					var PROJECT_DETAIL=$("input[name='PROJECT_DETAIL']").val();
					if(PROJECT_AREA==null||PROJECT_AREA==""){
					   alert("请选择省（直辖市/自治区）");
					   return;
					}
					var ADDRESS = PROJECT_AREA+PROJECT_CITY+PROJECT_COUN+PROJECT_DETAIL;
					   var mGeo = new BMap.Geocoder();  
					   mGeo.getPoint(ADDRESS, function(point){  
                              if (point) {  
							  jQuery.ajax({
						      url : "GpsSearch!DeviceGps.action",
						      data :"EQUIPMENT_ID="+EQUIPMENT_ID+"&EQMT_LONGITUDE="+point.lng+"&EQMT_LATITUDE="+point.lat+"&ADDRESS="+ADDRESS,
						      success : function(text){
						    	  alert("保存成功！");
							     window.location="GpsSearch!SelectForDeviceGps.action?EQUIPMENT_ID="+EQUIPMENT_ID;
						       }
					         });
							 }
                            }, PROJECT_AREA);  
					   
				
				}
				
				function deleteGps(id){
					if(confirm("确定删除？")==false)
						return;
				    var EQUIPMENT_ID=$("input[name='EQUIPMENT_ID']").val();
				    jQuery.ajax({
						url : "GpsSearch!deleteDeviceGps.action",
						data :"ID="+id,
						success : function(text){
							window.location="GpsSearch!SelectForDeviceGps.action?EQUIPMENT_ID="+EQUIPMENT_ID;
						}
					    });
				
				}
				
 
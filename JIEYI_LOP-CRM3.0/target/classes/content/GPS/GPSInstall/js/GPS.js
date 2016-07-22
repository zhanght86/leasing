$(document).ready(function(){
	$('#pageTable').datagrid({
		url:_basePath+"/GPS/GPSInstall/GPSManage!GPSListing.action",
		iconCls:'icon-edit',
		idField:'itemid',  
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
		 frozenColumns:[[
		       {field:'aaa',title:'操作',width:250,align:'center',formatter:function compure(value, rowData) {
		    	   var approval;
		    	   if(rowData.GPS_CODE != null && rowData.GPS_CODE != "")
		    	   {
		    		   approval = "<a href='javascript:void(0);' onclick=history('"+rowData.GPS_CODE+"')>命令记录</a>";
		    		   approval = approval + " | <a href='javascript:void(0);' onclick=command('"+rowData.GPS_CODE+"')>发送命令</a>&nbsp;";
		    	   }else
		    	   {
		    		   approval = "<a href='javascript:void(0);' style='color:#CCCCCC'>命令记录</a>";
		    		   approval = approval + " | <a href='javascript:void(0);' style='color:#CCCCCC'>发送命令</a>&nbsp;";
		    	   }
		    	   if(rowData.CODE_ == 1 || rowData.CODE_ == 2 || rowData.CODE_ == 3 || rowData.CODE_ == 4 ){
//		    		   approval = approval + " | <a href='javascript:void(0);' onclick='changeGPS("+rowData.GPS_ID+","+rowData.EQUIPMENT_ID+",\"changeGPS\")'>变更GPS</a>&nbsp;"
//		    		   + " | <a href='javascript:void(0);' onclick='removeGPS("+rowData.GPS_ID+","+rowData.EQUIPMENT_ID+")'>解除GPS</a>&nbsp;";
		    		   approval = approval + " | <a href='javascript:void(0);' onclick='removeGPS("+rowData.GPS_ID+")'>解除GPS</a>&nbsp;";
		    	   }else{
		    		   approval += " | <a href='javascript:void(0);' onclick=\"installGPS(\'"+rowData.GPS_ID+"\',\'"+rowData.EQUIPMENT_ID+"\',\'installGPSDiv\',\'installGPSForm\',\'"+rowData.CUST_NAME+"\',\'"+rowData.PRO_CODE+"\',\'"+rowData.PRODUCT_NAME+"\',\'"+rowData.TYPE_NAME+"\',\'"+rowData.PAYLIST_CODE+"\',\'"+rowData.CERTIFICATE+"\',\'"+rowData.LEASE_CODE+"\',\'"+rowData.WHOLE_ENGINE_CODE+"\',\'"+rowData.ENGINE_CODE+"\',\'"+rowData.GPS_CODE+"\',\'"+rowData.GPS_STATUS+"\')\">安装GPS</a>&nbsp;";		   
		    	   }
		    	   if(rowData.GPS_CODE != null && rowData.GPS_CODE != "")
		    	   {
		    		   approval = approval + " | <a href='javascript:void(0);' onclick=\"gpsbaoyang(\'"+rowData.GPS_CODE+"\',\'"+rowData.STAR_DISTANCE+"\',\'"+rowData.END_DISTANCE+"\')\">设备保养</a>&nbsp;";
		    	   }else
		    	   { 
		    		   approval = approval + " | <a href='javascript:void(0);' style='color:#CCCCCC'>设备保养</a>&nbsp;";
		    	   }
		    	   return approval;
		    	}
		       }
		 ]],
		 columns:[[
		         {field:'CUST_NAME',title:'客户名称',width:120,align:'center'},
		         {field:'PRO_CODE',title:'项目编号',width:100,align:'center'}, 
		         {field:'PRODUCT_NAME',title:'品牌名称',width:80,align:'center'}, 
		         {field:'TYPE_NAME',title:'品牌型号',width:80,align:'center'}, 
		         {field:'PAYLIST_CODE',title:'支付表号',width:140,align:'center'}, 
		         {field:'CERTIFICATE',title:'合格证状态',width:50,align:'center'},
		         {field:'LEASE_CODE',title:'融资租赁合同号',width:120,align:'center'},
		         {field:'WHOLE_ENGINE_CODE',title:'出厂编号',width:100,align:'center'}, 
		         {field:'ENGINE_CODE',title:'发动机编号',width:100,align:'center'}, 
		         {field:'GPS_CODE',title:'GPS编号',width:100,align:'center'}, 
		         {field:'GPS_STATUS',title:'GPS状态',width:70,align:'center'},
		         {field:'STAR_DISTANCE',title:'初始化里程',width:70,align:'center',formatter : function(v,r)
		         {
		        	 if(v==undefined)
		        	 {
		        		 return null;
		        	 }
		        	 return v+' 千米' ;
		         }},
		         {field:'END_DISTANCE',title:'保养里程',width:70,align:'center',formatter : function(v,r)
		         {
		        	 if(v==undefined)
		        	 {
		        		 return null;
		        	 }
		        	 return v+' 千米' ;
		         }}
		          
		 ]]
});
});
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            var ed = $(this).datagrid('getEditor', param);
            if (ed){
                if ($(ed.target).hasClass('textbox-f')){
                    $(ed.target).textbox('textbox').focus();
                } else {
                    $(ed.target).focus();
                }
            }
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    },
    enableCellEditing: function(jq){
        return jq.each(function(){
            var dg = $(this);
            var opts = dg.datagrid('options');
            opts.oldOnClickCell = opts.onClickCell;
            opts.onClickCell = function(index, field){
                if (opts.editIndex != undefined){
                    if (dg.datagrid('validateRow', opts.editIndex)){
                        dg.datagrid('endEdit', opts.editIndex);
                        opts.editIndex = undefined;
                    } else {
                        return;
                    }
                }
                dg.datagrid('selectRow', index).datagrid('editCell', {
                    index: index,
                    field: field
                });
                opts.editIndex = index;
                opts.oldOnClickCell.call(this, index, field);
            }
        });
    }
});

$(function(){
    $('#pageTable').datagrid().datagrid('enableCellEditing');
});
function gpsbaoyang(GPS_CODE,STAR_DISTANCE,END_DISTANCE)
{
	jQuery.ajax({
		url: 'GPSManage!gpsbaoyang.action?GPS_CODE='+GPS_CODE ,
		dataType:"json",
		success: function(res){
		if(res.flag==true){
			$("#dis #GPS_CODE").val(GPS_CODE);
			$('#dis').dialog({
				title: '设备保养',
				width: 270,
				height: 150,
				modal:true,
				closed: false,
				href:'GPSManage!findgpsdis.action?GPS_CODE='+GPS_CODE 
			});
	    }
	    else if(res.flag==false){
		    	$("#disDiv #GPS_CODE").val(GPS_CODE);
		    	$('#disDiv').dialog({
		    		title: '设备保养',
		    		width: 270,
		    		height: 150,
		    		modal:true,
		    		cache: false
		    	});
		    }
		}
	 });
}
function baoyang(GPS_CODE,STAR_DISTANCE,END_DISTANCE)
{
		if(confirm("确定要设置保养吗？")) {
			var LAT=new Array();
			var LON=new Array();
			var dd=0;
			var LATITUDE ="";
			var LONGITUDE ="";
			var STATUE='1';
			window.GPS_CODE=$("#GPS_CODE").val();
			window.STAR_DISTANCE=$("#STAR_DISTANCE").val();
			window.END_DISTANCE=$("#END_DISTANCE").val();
			var de;
			var sc;
			//window.DISTANCE =function DISTANCE(GPS_CODE,STAR_DISTANCE,END_DISTANCE){
					jQuery.ajax( {
							url : _basePath + "/GPS/GPSbaoyang/GPSbaoyang!findbyopen.action",
							data : "GPS_CODE="+GPS_CODE, 
							dataType : "json",
							success : function(json) {
													 for(var i=json.length-1;i>=0;i--){
													 LATITUDE=json[i].LATITUDE;
													 LONGITUDE=json[i].LONGITUDE;	
													 LAT.push(LATITUDE);
													 LON.push(LONGITUDE);
													}	
													  var EARTH_RADIUS = 6378137.0; //单位M 
					                                var PI = Math.PI; 
					                                
					                                function getRad(d){ 
					                                return d*PI/180.0; 
					                                } 		                               			                     
					                        		var tempLON = LON.slice();
					                        		var tempLAT = LAT.slice();
					                        		while(tempLON.length >= 2){
					                        			var f = getRad((parseFloat(tempLON[0]) + parseFloat(tempLON[1]))/2);
					                                    var g = getRad( (parseFloat(tempLON[0]) - parseFloat(tempLON[1]))/2 );
					                                    var l = getRad((parseFloat(tempLAT[0]) - parseFloat(tempLAT[1]))/2);
					                                    var sg = Math.sin(g);
					                                    var sl = Math.sin(l);
					                                    var sf = Math.sin(f);
					                                    
					                                    var s,c,w,r,d,h1,h2;
					                                    var a = EARTH_RADIUS;
					                                    var fl = 1/298.257;
					                                    
					                                    sg = sg*sg;
					                                    sl = sl*sl;
					                                    sf = sf*sf;
					                                    
					                                    s = sg*(1-sl) + (1-sf)*sl;
					                                    c = (1-sg)*(1-sl) + sf*sl;
					                                    
					                                    w = Math.atan(Math.sqrt(s/c));
					                                    r = Math.sqrt(s*c)/w;
					                                    d = 2*w*a;
					                                    h1 = (3*r -1)/2/c;
					                                    h2 = (3*r +1)/2/s;
					                                    sc=d*(1 + fl*(h1*sf*(1-sg) - h2*(1-sf)*sg));
					                        			tempLON.shift();
					                        			tempLAT.shift();
					                        			dd=dd+sc;
					                        		} 
													 de=parseFloat(dd)+parseFloat(STAR_DISTANCE*1000);
													 jQuery.ajax( {
															url : _basePath + "/GPS/GPSInstall/GPSManage!savedistance.action",
															data : "GPS_CODE="+GPS_CODE+"&STAR_DISTANCE="+STAR_DISTANCE+"&DISTANCE="+de+"&END_DISTANCE="+END_DISTANCE+"&STATUE="+STATUE, 
															dataType : "json",
															success : function(res) {
															if(res.flag==true){
																jQuery.messager.alert("success",res.msg);
																$('#pageTable').datagrid('reload');
																window.DISTANCE =function DISTANCE(GPS_CODE,STAR_DISTANCE,END_DISTANCE){
																jQuery.ajax( {
																		url : _basePath + "/GPS/GPSbaoyang/GPSbaoyang!findbyopen.action",
																		data : "GPS_CODE="+GPS_CODE, 
																		dataType : "json",
																		success : function(json) {
																								 for(var i=json.length-1;i>=0;i--){
																								 LATITUDE=json[i].LATITUDE;
																								 LONGITUDE=json[i].LONGITUDE;	
																								 LAT.push(LATITUDE);
																								 LON.push(LONGITUDE);
																								}	
																								  var EARTH_RADIUS = 6378137.0; //单位M 
																                                var PI = Math.PI; 
																                                
																                                function getRad(d){ 
																                                return d*PI/180.0; 
																                                } 		                               			                     
																                        		var tempLON = LON.slice();
																                        		var tempLAT = LAT.slice();
																                        		while(tempLON.length >= 2){
																                        			var f = getRad((parseFloat(tempLON[0]) + parseFloat(tempLON[1]))/2);
																                                    var g = getRad( (parseFloat(tempLON[0]) - parseFloat(tempLON[1]))/2 );
																                                    var l = getRad((parseFloat(tempLAT[0]) - parseFloat(tempLAT[1]))/2);
																                                    var sg = Math.sin(g);
																                                    var sl = Math.sin(l);
																                                    var sf = Math.sin(f);
																                                    
																                                    var s,c,w,r,d,h1,h2;
																                                    var a = EARTH_RADIUS;
																                                    var fl = 1/298.257;
																                                    
																                                    sg = sg*sg;
																                                    sl = sl*sl;
																                                    sf = sf*sf;
																                                    
																                                    s = sg*(1-sl) + (1-sf)*sl;
																                                    c = (1-sg)*(1-sl) + sf*sl;
																                                    
																                                    w = Math.atan(Math.sqrt(s/c));
																                                    r = Math.sqrt(s*c)/w;
																                                    d = 2*w*a;
																                                    h1 = (3*r -1)/2/c;
																                                    h2 = (3*r +1)/2/s;
																                                    sc=d*(1 + fl*(h1*sf*(1-sg) - h2*(1-sf)*sg));
																                        			tempLON.shift();
																                        			tempLAT.shift();
																                        			dd=dd+sc;
																                        		} 
																								 de=parseFloat(dd)+parseFloat(STAR_DISTANCE);
																								
																								 jQuery.ajax( {
																										url : _basePath + "/GPS/GPSInstall/GPSManage!savedistance.action",
																										data : "GPS_CODE="+GPS_CODE+"&STAR_DISTANCE="+STAR_DISTANCE+"&DISTANCE="+de+"&END_DISTANCE="+END_DISTANCE+"&STATUE="+STATUE, 
																										dataType : "json",
																										success : function(res) {
																										if(res.flag==true){
																											jQuery.messager.alert("success",res.msg);
																											$('#pageTable').datagrid('reload');
																											}
																											  else{
																						  					 jQuery.messager.alert("no",res.msg);
																						  					 alert("操作失败");
																						    				}
																										}
																									});	
																	}				
																});
														}
														window.setInterval("DISTANCE.call(window, GPS_CODE,STAR_DISTANCE,END_DISTANCE)",864000);
																}
																  else{
											  					 jQuery.messager.alert("no",res.msg);
											  					 alert("操作失败");
											    				}
															}
														});	
						}				
					});
			//}
			//window.setInterval("DISTANCE.call(window, GPS_CODE,STAR_DISTANCE,END_DISTANCE)",1000);
		}
}
/**
 * 解除GPS绑定
 * @author 许长朋 2014.11.17
 */
function removeGPS(GPS_ID){
	if(confirm("确认解除当前GPS绑定?")){
		$.ajax({
			url : _basePath+"/GPS/GPSInstall/GPSManage!removeGPS.action?ID=" +GPS_ID,
			dataType :"json",
			success : function(json){
				if(json.flag){
					alert("解除绑定成功");
					$("#pageTable").datagrid("reload");
					window.location.reload(true);
				}else{
					alert("解除绑定失败");
				}
			}
		});
	}
}
/**
 * 搜索
 * @return
 */
function seach(){
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var PRO_CODE=$("input[name='PRO_CODE']").val();
	var LEASE_CODE=$("input[name='LEASE_CODE']").val();
	var PAYLIST_CODE=$("input[name='PAYLIST_CODE']").val();
	var GPS_STATUS=$("#GPS_STATUS").val();
	
	$('#pageTable').datagrid('load', {"CUST_NAME":CUST_NAME,"PRO_CODE":PRO_CODE,"LEASE_CODE":LEASE_CODE,"PAYLIST_CODE":PAYLIST_CODE,"GPS_STATUS":GPS_STATUS});
}
/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 安装GPS
 * @author 许长朋 2014.11.14
 */
function installGPS(GPS_ID,EQUIPMENT_ID, divId, formId,CUST_NAME,PRO_CODE,PRODUCT_NAME,TYPE_NAME,PAYLIST_CODE,CERTIFICATE,LEASE_CODE,WHOLE_ENGINE_CODE,ENGINE_CODE,GPS_CODE,GPS_STATUS) {
	//$("#EQUIPMENT_ID").val(EQUIPMENT_ID);
	var now = new Date();
	$("#" + divId).show();
	$("#" + divId).dialog({
		title : "安装GPS",
		modal : true,
		resizable : true,
		buttons : [{
			id : "btnbc",
			text : '保 存',
			iconCls : 'icon-save',
			handler : function() {
				var jsonData = $("#" + formId).serialize();
				jQuery.ajax({
					type : "POST",
					dataType : "json",
					url : _basePath + '/GPS/GPSInstall/GPSManage!installGPS.action?EQUIPMENT_ID=' + EQUIPMENT_ID + "&ID=" +GPS_ID
					+"&CUST_NAME=" +CUST_NAME+"&PRO_CODE=" +PRO_CODE+"&PRODUCT_NAME=" +PRODUCT_NAME+"&PAYLIST_CODE=" +PAYLIST_CODE
					+"&CERTIFICATE=" +CERTIFICATE+"&LEASE_CODE=" +LEASE_CODE+"&DDATE=" + (now.getFullYear() +"-" + (now.getMonth()+1)+ "-" + now.getDate() +" "+ now.getHours()+ ":"+ now.getMinutes()+ ":"+ now.getSeconds()),
					data : jsonData,
					success : function(json) {
						if (json.flag) {
							alert("保存成功");
							$('#btnbc').linkbutton('disable');
							$("#" + formId).form('clear');
							$("#" + divId).dialog('close');
							$('#pageTable').datagrid('load',{"param" : JSON.stringify(json.data)});
							window.location.reload(true);
						} else {
							alert(json.msg);
						}
					}
				});
			}
		},
 
		{
			text : '关 闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#" + divId).dialog('close');
			}
		}]
	});
}
function command(GPS_CODE)
{
	jQuery.ajax({
		url: 'GPSManage!findfa.action?GPS_CODE='+GPS_CODE ,
		dataType:"json",
		success: function(res){
		if(res.flag==true){
			$("#com #GPS_CODE").val(GPS_CODE);
			$('#com').dialog({
				title: '发送命令',
				width: 270,
				height: 300,
				modal:true,
				closed: false,
				href:'GPSManage!findming.action?GPS_CODE='+GPS_CODE 
			});
	    }
	    else if(res.flag==false){
		    	$("#commandDiv #GPS_CODE").val(GPS_CODE);
		    	$('#commandDiv').dialog({
		    		title: '发送命令',
		    		width: 270,
		    		height: 300,
		    		modal:true,
		    		cache: false
		    	});
		    }
		}
	 });
}
/**
 * 设置命令
 * @param GPS_CODE
 * @author King 2014年11月17日
 */
function commandSet(GPS_CODE){
	$("#commandDiv #ADD_GPS_CODE").val(GPS_CODE);
	$('#commandDiv').dialog({
		title: '发送命令',
		width: 270,
		height: 300,
		modal:true,
		cache: false
	});
}
function history(GPS_CODE)
{
	top.addTab("命令记录", _basePath + "/GPS/GPSInstall/GPSManage!history.action?GPS_CODE=" + GPS_CODE);
}
function addGpsCommand(){
	var now = new Date();
	$.ajax({
		type:'post',
		url:_basePath+"/GPS/GPSCommand/GPSCommand!addGpsCommand.action",
		data:"GPS_CODE="+$("#ADD_GPS_CODE").val()+"&COMMAND_TYPE="+$("#COMMAND_TYPE").val()+"&GPS_TYPE="+$("#GPS_TYPE").val()+"&COMMAND_MODEL="+
			  $("#COMMAND_MODEL").val()+"&WORK_TIME="+$("#WORK_TIME").val()+"&GPS_FIRST_DATE="+$("#GPS_FIRST_DATE").val()+"&INTERVAL_TIME="+
			  $("#INTERVAL_TIME").val()+"&LOCK_LEVEL="+$("#LOCK_LEVEL").val()+"&CREATE_DATE=" + (now.getFullYear() +"-" + (now.getMonth()+1)+ "-" + now.getDate() +" "+ now.getHours()+ ":"+ now.getMinutes()+ ":"+ now.getSeconds()),
		dataType:'json',
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","命令发送成功!");
			}else
				$.messager.alert("提示",json.msg);
		}
	});
}
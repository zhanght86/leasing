$(function() {
	$('#toMgBailoutCondition').datagrid({
		url:_basePath+"/bailoutCondition/BailoutCondition!toMgBailoutData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[ 	 	 	 	 	 	 	 	 	 	 	 	
		          {field:'TRBCID',hidden:true,malign:'center',width:20},
		          {field:'TRBOID',hidden:true,malign:'center',width:20},
		          {field:'TRBWID',hidden:true,malign:'center',width:20},
		          {field:'FLAG',align:'center',width:40,title:'融资方式名称'},
		          {field:'ORGAN_NAME',align:'center',width:40,title:'融资机构'},
		          {field:'INSETDATE',align:'center',width:40,title:'添加日期'},
		          {field:'OPERATION',align:'center',width:40,title:'操作',formatter:function(value,rowData,rowIndex){
		        	  return "<input type='hidden' class='pCondition' value='"+rowData.CONDITIONONE+"'/>" +
		        	  		"<a href='javascript:void(0)' class='easyui-linkbutton modifyA' iconCls='icon-edit' onclick='modifyA("
							+ rowData.OPERATION
							+",\""
							+rowData.ORGAN_NAME
							+"\"" 
							+",\""
							+rowData.BAILOUTWAY_NAME
							+"\""
							+",\""
							+rowData.TRBOID
							+"\""
							+",\""
							+rowData.TRBWID
							+"\")'>修改</a> ";							
                   }}
		          ]]
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var ORGAN_NAME1 = $("input[name='ORGAN_NAME1']").val();
	var CREDIT_DEADLINE1 = $("input[name='CREDIT_DEADLINE1']").val();
	var CREDIT_DEADLINE2 = $("input[name='CREDIT_DEADLINE2']").val();
	var CREDIT_STATUS = $("select[name='CREDIT_STATUS']").attr("selected",true).val();
	$('#toMgBailoutCondition').datagrid('load', {
		"ORGAN_NAME1" : ORGAN_NAME1,
		"CREDIT_DEADLINE1" : CREDIT_DEADLINE1,
		"CREDIT_DEADLINE2" : CREDIT_DEADLINE2,
		"CREDIT_STATUS" : CREDIT_STATUS
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#INSETDATE").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

//显示弹出层
function showDiv(type,TRBCID,BAILOUTWAY_NAME,ORGAN_NAME,CONDITIONONE) {
 	  if(type=='1'){
	  	$('#insetBC').dialog('open');
	  	jQuery.ajax({
	  		url:_basePath+'/bailoutCondition/BailoutCondition!toAddBailout.action',
	  		dataType:'json',
	  		success:function(json){
	  			var insetBC = document.getElementById("BCTD1");
	  			insetBC.innerHTML = json.data;
	  		}
	  	});
 	  }
}

/**
 * 融资机构获取
 * @author yx
 * @date 2013-10-15
 * @param ID
 * @return
 */
function optionse(ID){
	   var trbw = document.getElementById("TRBWID");
	   	 if(ID!=0){
	   		jQuery.ajax({
	   			url:_basePath+'/bailoutCondition/BailoutCondition!getBailoutWay.action',
	   			data:"JG_ID="+ID,
		  		dataType:'json',
		  		success:function(json){
	   				var trbw = document.getElementById("TRBWID");
	   				trbwlength=trbw.options.length;
	   				if(trbw.options.length>0){
						trbw.options.length=1;
					}
					for(var i = 0;i<json.data.length;i++){
						trbw.options.add(new Option(""+json.data[i].namese,""+json.data[i].value)); //这个兼容IE与firefox
					}
		  		}
	   		});
		}else{
		 trbw.options.length=1;
		}
}

/**
 * 选择方式保存
 * @author yx
 * @date 2013-10-15
 * @return
 */
function submitContract(){
	var insetBC=$("#insetBC");
	var TRBOID=insetBC.find("[name=TRBOID]").val();
	var TRBWID=insetBC.find("[name=TRBWID]").val();
	var saveCon=new Array();
	var flag=true;
	$(".conditonTr").each(function(){		
		var node=$(this);
		var cond=node.find(".CONDITIONONE");
		if(cond.attr("checked")=="checked"){
			var temp={};
			temp.contionId=cond.val();
			if(node.find('[name=FILE_COUNT]').val()==""){
				flag=false;
			}			
			temp.FILE_TYPE=node.find(".FILE_TYPE:checked").val();
			temp.FILE_COUNT=node.find(".FILE_COUNT").val();
			saveCon.push(temp);
		}
	});
	if(flag==false){
		alert("请将条件补充齐全");
		return false;
	}else{
		var json=JSON.stringify(saveCon);		
		var url=_basePath+"/bailoutCondition/BailoutCondition!doAjaxInsetBailoutCondition.action";				
			jQuery.ajax({
        		url:url,
        		data:"CONDITIONONE="+json+"&TRBOID="+TRBOID+"&TRBWID="+TRBWID,
        		type:"POST",
        		timeout:10000,
        		async:false,
        		dataType:"json",
        		success:function(json){
					if(json.flag == true){
						alert("方式选择成功");
					}else{
						alert("方式选择失败或系统中已存在融资条件方式");
					}
	        			window.location.href="BailoutCondition!toMgBailout.action";
	        		},
        		error:function(e){
        			alert("失败！请刷新页面或检查网络！");
        		}
        	});
	}
}

function modifyA(pTRBCID,ORGAN_NAME,BAILOUTWAY_NAME,TRBOID,TRBWID){
	$('#updtaBC').dialog('open');	
	document.getElementById("TRBCID1").value=pTRBCID;
	document.getElementById("BCJG").innerHTML=ORGAN_NAME;
	document.getElementById("BCRZS").innerHTML=BAILOUTWAY_NAME;
	document.getElementById("TRBOID1").value=TRBOID;
	document.getElementById("TRBWID1").value=TRBWID;
	jQuery.ajax({
  		url:_basePath+"/bailoutCondition/BailoutCondition!toAddBailout.action?pTRBCID="+pTRBCID,
  		dataType:'json',
  		success:function(json){
  			var insetBC = document.getElementById("BCTD2");
  			insetBC.innerHTML = json.data;
  		}
  	});
}

function submitContract1(){
	
	var insetBC=$("#updtaBC");
	var TRBCID=insetBC.find("[name=TRBCID1]").val();
	var TRBOID=insetBC.find("[name=TRBOID1]").val();
	var TRBWID=insetBC.find("[name=TRBWID1]").val();
	var saveCon=new Array();
	var flag=true;
	$(".conditonTr").each(function(){
		var node=$(this);
		var cond=node.find(".CONDITIONONE");
		if(cond.attr("checked")=="checked"){
			var temp={};
			temp.contionId=cond.val();
			if(node.find('[name=FILE_COUNT]').val()==""){
				flag=false;
			}
			
			temp.FILE_TYPE=node.find(".FILE_TYPE:checked").val();
			temp.FILE_COUNT=node.find(".FILE_COUNT").val();
			saveCon.push(temp);
		}
	});
	
	if(flag==false){
		alert("请将条件补充齐全");
		return false;
	}else{
		var json=JSON.stringify(saveCon);
		var url=_basePath+"/bailoutCondition/BailoutCondition!doReviseBailoutCondition.action";				
			jQuery.ajax({
        		url:url,
        		data:"CONDITIONONE="+json+"&TRBOID="+TRBOID+"&TRBWID="+TRBWID+"&TRBCID="+TRBCID,
        		type:"POST",
        		timeout:10000,
        		async:false,
        		dataType:"json",
        		success:function(json){
					if(json.flag == true){
						alert("方式选择更新成功");
					}else{
						alert("方式选择更新失败");
					}
        			window.location.href="BailoutCondition!toMgBailout.action";
        		},
        		error:function(e){
        			alert("失败！请刷新页面或检查网络！");
        		}
        	});
	}
}

function showProc(){
	//创建灰色背景层
	procbg = document.createElement("div"); 
	procbg.setAttribute("id","mybg"); 
	procbg.style.background = "#000"; 
	procbg.style.width ="100%";
	procbg.style.height = "100%"; 
	procbg.style.position = "absolute"; 
	procbg.style.top = "0"; 
	procbg.style.left = "0"; 
	procbg.style.zIndex = "500"; 
	procbg.style.opacity = "0.3"; 
	procbg.style.filter = "Alpha(opacity=30)"; 
	//背景层加入页面
	document.body.appendChild(procbg);
	document.body.style.overflow = "hidden";
	var message_box=document.getElementById('message_box');
	message_box.style.visibility='visible';
}
$(document).ready(function(){
	$("#dlg").dialog('close');
	$("#dlgUp").dialog('close');
	$("#btnGetRes").click();
	
	$("#perbtn").click(function(){
	  jQuery.ajax({
          url:"Auth!addRolePermisson.action",
          data:"ROLE_ID=230",
          type:"post",
          dataType:"json",
          success:function(json){
		    if(json[0].flag){
			   alert("角色授权成功");
			}else{
			   alert("角色授权失败");
			}
		  }
       });
	});
});

function btnGetRole(){
	jQuery.post(_basePath+"/secu/Auth!getRoles.action?role_Name="+encodeURI($("#ROLE_NAME").val()),function(html){
		$("#roleContent").html(html);
		$(".ROLERADIO").click(function(){
              var role_id=$(this).val();
              $('#tt').tree('collapseAll');
              var nodes = $('#tt').tree('getChecked');
              for(var i=0; i<nodes.length; i++){
            	  var node = $('#tt').tree('find',nodes[i].id);
            	  $('#tt').tree('uncheck',node.target);
              }
              jQuery.ajax({
                  url:"Auth!getRolePerList.action",
                  data:"ROLE_ID="+role_id,
                  type:"post",
                  dataType:"json",
                  cache:false,
                  success:function(json){
        	   			jQuery.each(json,function(i,n){
				 			 var node = $('#tt').tree('find',json[i].PER);
				 			 if(node !=null ){
				 				 $('#tt').tree('check',node.target);
				 				 $('#tt').tree('expandTo',node.target);
				 			 }
				 			
        	   			});
                  }
              });  
         });
	});
	
}

function btnGetRes(){
	$('#tt').tree({
        url:"Auth!treeJson.action?RES_NAME="+encodeURI($("#RES_NAME").val()),
        onLoadSuccess:function(){
			//绑定权限 
			var roleid = $("input[name='ROLERADIO']:checked").val();
			if(roleid!=null && roleid.length>0 && roleid!='undefined')
			{
				jQuery.ajax({  
		               url:'Auth!getRolePerList',
		                data:"ROLE_ID="+roleid,
	                    type:"post",
	                    dataType:'json',
	                    cache:false,
		                success:function(json){
							jQuery.each(json,function(i,n){
					 			 var node = $('#tt').tree('find',json[i].PER);  
					 			 $('#tt').tree('check',node.target);
					 			$('#tt').tree('expandTo',node.target);
	                       });
		               } 
		           })
			}
    	}
    });
}


function btnUpRes(){
	   var roleid = $("input[name='ROLERADIO']:checked").val();
	   if(roleid==null||roleid==""){
	     alert("请先选择角色");
		 return;
	   }
	   
	   var nodes = $('#tt').tree('getChecked');
	   var sid="";
	   for(var i=0; i<nodes.length; i++){
		   if (sid != '') sid += ',';
		   sid += nodes[i].id;
	   }
	   
	   var Allid=sid;
	   
	   var checkNodes=  $('#tt').tree('getChecked', 'unchecked');
	   
	   for(var i=0; i<checkNodes.length; i++){
		   if (Allid != '') Allid += ',';
		   Allid += checkNodes[i].id;
	   }
	   
	   checkNodes=  $('#tt').tree('getChecked', 'indeterminate');
	   for(var i=0; i<checkNodes.length; i++){
		   if (Allid != '') Allid += ',';
		   Allid += checkNodes[i].id;
	   }
	   
	   checkNodes=  $('#tt').tree('getChecked', ['checked','indeterminate']);
	   for(var i=0; i<checkNodes.length; i++){
		   if (Allid != '') Allid += ',';
		   Allid += checkNodes[i].id;
	   }
	   
	 
	   var RES_NAME=$("#RES_NAME").val();
	   var perids=Allid;
	   if(RES_NAME==""){
	     perids="";
	   }
	   
	   jQuery.ajax({
        url:"Auth!doSetRolePer.action",
        data:"ROLE_ID="+roleid+"&sids="+sid+"&RES_NAME="+encodeURI(RES_NAME)+"&perids="+perids,
        type:"post",
        dataType:"json",
        success:function(json){
			    if(json[0].flag){
			     alert("更新成功");
				}else{
				 alert("更新失败");
				}
			  }
			});  
	  
	}

function updateBtnGetRole(){
	var ROLERADIO = $('.ROLERADIO:checked').val();
	if(ROLERADIO!=null && ROLERADIO.length>0)
	{
		jQuery.post(_basePath+'/secu/Auth!getUpdateRole.action?ROLE_ID='+ROLERADIO,function(html){
			$("#dlgUp").html(html);
		});
		
			jQuery.ajax({
		        url:"Auth!getUpdateRole.action",
		        data:"ROLE_ID="+ROLERADIO,
		        type:"post",
		        dataType:"json",
		        success:function(roleMap){
				    $("#ROLE_ID_UPDAE").val(roleMap.ROLE_ID);
				    $("#CHARACTER_NAME1").val(roleMap.ROLE_NAME);
				    $("#INSTRUCTIONS1").val(roleMap.ROLE_MEMO);
				    $("#TAB_RIGHTS1").val(roleMap.TAB_RIGHTS);
				  }
		     });
		
		$("#dlgUp").dialog("open");
	}
	else
	{
		alert("请先选中一项在做修改操作！");
	}
}


function deleteBtnGetRole(){
	var ROLE_ID = $(".ROLERADIO:checked").val();
	var ROLE_NAME = $(".ROLERADIO:checked").attr("ROLE_NAME");
	if(ROLE_ID!=null && ROLE_ID.length>0)
	{
		if(window.confirm("确定删除角色名称为“"+ROLE_NAME+"”的选项吗？")){
			 jQuery.ajax({
		          url:"Auth!deleteBtnGetRole.action",
		          data:"ROLE_ID="+ROLE_ID,
		          type:"post",
		          dataType:"json",
		          success:function(json){
					 if(json.flag==true){
	 					 alert(json.errorMsg);
	 					 $(".ROLERADIO:checked").parent().parent().remove();
					 }else{
	 					 alert(json.errorMsg);
	 				 }	
				  }
			 }); 
		}
	}
	else
	{
		alert("请先选中一项在做删除操作！");
	}
	
}
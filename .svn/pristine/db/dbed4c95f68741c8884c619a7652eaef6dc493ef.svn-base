$(function() {

	$('#pageTable').datagrid({		
        //url:_basePath+"/documentApp/RemindDossier!toRemindDossier.action",/   
		view: detailview,//注意1  
        title: '我的取数',  
		fitColumns: true,  
        singleSelect:true,  
        height: 340,  
        pagination: true,  
       //toolbar:'#pageForm',
       columns:[[
			 {field:'PROJECT_ID',align:'center',hidden:true,width:30,checkbox:false},
             {field:'STATUS',align:'center',width:50,title:'资料状态',formatter:function (value,rowData,rowIndex){
   			    if(value==undefined||value=='0'){
   				      return "未归档";
   			    }else if(value="1"){
   				       return "已申请";
   			    }else if(value="2"){
   				       return "已入柜";
   			    }else if(value="3"){
   				       return "已驳回";
   			    }
   			   }},	      
	          {field:'LEASE_CODE',align:'center',width:100,title:'融资租赁合同号'},
	          {field:'CLIENT_NAME',align:'center',width:100,title:'客户名称'},
	          {field:'FLAG_TYPE',align:'center',width:100,title:'业务类型'},
	          {field:'B',align:'center',width:100,title:'事业部'},
	   		  {field:'C',align:'center',width:100,title:'客户类型'},	
	   		  {field:'PROJECT_USERCODE',align:'center',width:100,title:'责任客户经理'},	
	          {field:'PORTFOLIO_NUMBER',align:'center',width:100,title:'档案袋编号'},
	          {field:'CABINET_NUMBER',align:'center',width:100,title:'档案柜编号'}
	          ]],
	          
	          detailFormatter:function(index,row){
    	        return '<table class="table_02" cellpadding="0" cellspacing="0" id="ddv-' + index + '" style="padding:5px;"></table>';
              },
              onExpandRow:function(index,row){
            	  $('#ddv-'+index).panel({
            		 width:'100%',
            		 border:false,
            		 cache:false,
            		 height:'auto',
            		 href:_basePath+"/documentApp/RemindDossier!toShowRecodeApp.action?PROJECT_ID="+row.PROJECT_ID,  
            		 onLoad:function(){
            		     $('#pageTable').datagrid('fixDetailRowHeight',index);
            	     }
            	  });
              }
              
	          //展开层
//	          onExpandRow: function(index,row){
//	          	jQuery.ajax({
//	          		url:_basePath+"/api/datalist/DataTemplet!toShowRecodeApp.action?LEASE_CODE="+row.LEASE_CODE+"&CLIENT_NAME="+row.CLIENT_NAME+"&PORTFOLIO_NUMBER="+row.PORTFOLIO_NUMBER+"&CABINET_NUMBER="+row.CABINET_NUMBER
//	          		+"&DOSSIER_APPLY_ID="+row.A+"&FLAG_TYPE="+row.FLAG_TYPE+"&B="+row.B+"&C="+row.C,  
//	  				type:'post',
//	  				dataType:'json',
//	  			    success: function(json){
//	  				var data = {flag:json.flag,total:json.data.length,rows:json.data};
//	  				alert(json.data.WARRANT_TYPE);
//	  				var pRowIndex = "ddv-"+index;
//	  				$('#ddv-'+index).datagrid({
//	  					fitColumns:true,
//	                    singleSelect:true,
//	  					rownumbers:true,
//	                    loadMsg:'加载中...',
//	                    height:'auto',
//	                    columns:[[                     
//	                              {field:'WARRANT_TYPE',align:'center',width:40,title:'权证类型'},
//	                              {field:'WARRANT_NAME',align:'center',width:50,title:'权证名称'},
//	                              {field:'0',align:'center',width:35,title:'资料状态',formatter:function(value,row,index){
//	                              	if(value=="0"){
//	                              		return "已归档";
//	                              	}else if(value=="1"){
//	                              		return "未归档";
//	                              	}
//	                              }},
//	                              {field:'TYPE',align:'center',width:45,title:'原件/复印件',formatter:function(value,row,index){
//	                              	if(value=="0"){
//	                              		return "原件";
//	                              	}else if(value=="1"){
//	                              		return "复印件";
//	                              	}
//	                              }},
//	                              {field:'FENSHU',align:'center',width:50,title:'总份数'}
//	                       ]],
//	                       
//	                       onResize:function(){
//	                          $('#pageTable').datagrid('fixDetailRowHeight',index);
//	                      },
//	                      onLoadSuccess:function(){
//	                          setTimeout(function(){
//	                              $('#pageTable').datagrid('fixDetailRowHeight',index);
//	                          },0);
//	                      }
//	  				});
//
//	                  $('#pageTable').datagrid('fixDetailRowHeight',index);
//	  				$('#ddv-'+index).datagrid("loadData",data);
//	  			    }});
//		
//	
//	       } 
	});

 
});

//按钮--清空
function clean(){
		$("#CLIENT_NAME").val('');
		$("#PORTFOLIO_NUMBER").val('');
		$("#CABINET_NUMBER").val('');
		$("#PLATFORM_TYPE").val('');
		$("#BUSINESS_PLATE").val('');
		$("#LEASE_CODE").val('');
}
//按钮-查询
function se(){
	$("#pageTable").datagrid('load',{"CLIENT_NAME":$("input[name=CLIENT_NAME]").val()});
	$("#pageTable").datagrid('load',{"LEASE_CODE":$("input[name=LEASE_CODE]").val()});
	//$('#pageTable').datagrid('load', {"param":getFromData("#pageForm1")});
}
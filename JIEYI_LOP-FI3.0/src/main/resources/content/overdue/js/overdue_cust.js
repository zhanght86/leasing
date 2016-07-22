var checkData = null;
$(document).ready(function(){
	$(".linkbutton").linkbutton();
	checkData = $("#overdue_Cust_MG").datagrid({
		url:"Overdue!query_overdue_Price_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fitColumns:true,
		checkOnSelect:true,
		selectOnCheck : true,
		fit:true,
		idField : 'PAY_ID',	
		nowrap: true,
		pageSize:10,
		toolbar:'#pageForm',
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
            	//$('#overdue_Cust_MG').datagrid('selectRow', 0);
            	$("#PAY_CODE").val(data.rows[0].PAYLIST_CODE);
            }
        },
		onClickRow:function(index,data){
			$("#PAY_CODE").val(data.PAYLIST_CODE);
		},
		columns:[[
		          	{field:'PAY_ID',checkbox:true,width:5},
					{field:'AA',title:'操作',width:40,formatter:function(value,rowData,rowIndex){
						  
						  if(rowData.STATUS=='100'){
					//		  return "<a href='javascript:void(0)' onclick='TOTALSUM(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+"  MONEY="+rowData.PAID_MONEY_ALL+">计算应付总额</a>&nbsp;|&nbsp;<a href='Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;|&nbsp;已转诉讼";
							  return "<a href='javascript:void(0)' onclick='TOTALSUM(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+"  MONEY="+rowData.PAID_MONEY_ALL+">计算应付总额</a>&nbsp;|&nbsp;<a href='Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;|&nbsp;<a href='javascript:void(0)' onclick=exportPDF('"+rowData.PAY_ID+"','"+rowData.CUST_NAME+"')>逾期通知PDF </a> &nbsp;|&nbsp;已转诉讼";
						  }
						  else{
					//		  return "<a href='javascript:void(0)' onclick='TOTALSUM(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+"  MONEY="+rowData.PAID_MONEY_ALL+">计算应付总额</a>&nbsp;|&nbsp;<a href='Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;|&nbsp;<a href='javascript:void(0)' onclick='subLawsuit(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+">转诉讼</a>";
							  return "<a href='javascript:void(0)' onclick='TOTALSUM(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+"  MONEY="+rowData.PAID_MONEY_ALL+">计算应付总额</a>&nbsp;|&nbsp;<a href='Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;|&nbsp;<a href='javascript:void(0)' onclick=exportPDF('"+rowData.PAY_ID+"','"+rowData.CUST_NAME+"')>逾期通知PDF </a>&nbsp;|&nbsp;<a href='javascript:void(0)' onclick='subLawsuit(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+">转诉讼</a>";
						  }
					}},		          	
					{field:'DUNFLAG',width:5,formatter:function(value,rowData,rowIndex){
							if(value == 1){
				    		   return "<div class='icon-red'>&nbsp;</div>";
						    }else{
							   return "<div class='icon-green'>&nbsp;</div>";
							}
		          	}},
		          	{field:'CUST_NAME',title:'客户名称',width:15},
		          	//{field:'COMPANY_NAME',title:'厂商',width:13},
		          	{field:'PLAN_STATE',title:'状态',width:5},
		          	{field:'PAYLIST_CODE',title:'还款计划',width:15},
		          	{field:'PAID_MONEY_ALL',title:'应收总金额',width:10},
		          	{field:'RENT_RECE_ALL',title:'逾期金额',width:10},
		          	{field:'VINUAL_MONEY_ALL',title:'代理商垫付金额',width:10},
		          	{field:'PENALTY_RECE_ALL',title:'罚息金额',width:10},
		          	{field:'VINUAL_PAID_ALL',title:'代理商垫付罚息',width:10}
		        		
		          	
		         ]],
		         	view:detailview,
			 		detailFormatter : function(index, row) {
			 			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
			 		},
					onExpandRow : function(index, row) {
			 			$('#ddv-'+index).panel({
			 				height:190,  
			 				border:false,
			 				cache:false,
			 				href:'Overdue!create_GT_page.action?PAYLIST_CODE='+row.PAYLIST_CODE,
			 				onLoad:function(){
			 					$('#overdue_Cust_MG').datagrid('fixDetailRowHeight',index);
			 				} 
			 			});
			 			$('#overdue_Cust_MG').datagrid('fixDetailRowHeight',index);
			 		}
			 		
//			 		pagination:true,	
//			 		toolbar : [{
//			 		id : 'btnadd',
//			 		text : '导出',
//			 		iconCls : 'icon-add',
//			 		handler : exportExcel(d)
//			 		} ]
		});
	
	$("#WDATE").dialog('close');
	
	
	$("#asset_MG").datagrid({
		url:"Overdue!query_asset_MG_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		pageSize:50,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'CUST_NAME',title:'客户名称',width:35},
		          	{field:'COMPANY_NAMES',title:'厂商',width:35},
		          	{field:'PRODUCT_NAMES',title:'租赁物名称',width:35},
		          	{field:'EQUIPMENT_AMOUNTS',title:'台量',width:20},
		          	{field:'ENGINE_TYPES',title:'机型',width:20},
		          	{field:'WHOLE_ENGINE_CODES',title:'出厂编号',width:20},
		          	{field:'PROJECT_DATE',title:'立项日期',width:30},
		          	{field:'START_DATE',title:'起租确定日期',width:30},
		          	{field:'LEASE_TOPRIC',title:'租赁物价款',width:30},
		          	{field:'LEASE_TERM',title:'租赁期限',width:30},
		          	{field:'ITEM_MONEY_ALL',title:'租金总额',width:30},
		          	{field:'DUN_PERIOD_COUNT',title:'逾期期数',width:30},
		          	{field:'DUN_COUNT',title:'累计逾期',width:30},
		          	{field:'DUN_DAY_COUNT',title:'逾期天数',width:30},
		          	{field:'PAID_MONEY',title:'逾期租金',width:30},
		          	{field:'PENALTY_RECE_All',title:'违约金额',width:30},
		          	{field:'AA',title:'操作',width:45,formatter:function(value,rowData,rowIndex){
	                	  
	                	  if(rowData.DUN_PERIOD_COUNT>0){
	                		  return "<a href='javascript:void(0)' onclick='TOTALSUM(this)' PAYLIST_CODE="+rowData.PAYLIST_CODE+"  MONEY="+rowData.PAID_MONEY_ALL+">计算应付总额</a>&nbsp;|&nbsp;<a href='Overdue!query_overdue_All.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >逾期明细 </a>&nbsp;|&nbsp;<a href='Overdue!STATEMENTS_ALL.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >还款明细 </a>&nbsp;|&nbsp;已转诉讼";
	                	  }
	                	  else
	                	  {
	                		  return "&nbsp;";
	                	  }
		          	}}
		         ]]
		});
	
});

function TOTALSUM(obj)
{
	var PAYLIST_CODE=$(obj).attr("PAYLIST_CODE");
	
	$("#PAY_PAYLIST_CODE").val(PAYLIST_CODE);
	$("#PAY_PAYLIST_MONEY").val($(obj).attr("MONEY"));
	$("#id1").empty();
	$("#id2").empty();
	$("#id3").empty();
	$("#id4").empty();
	$("#id1").append($(obj).attr("MONEY"));
	$("#WDATE").dialog({
		title:PAYLIST_CODE+"计算应付总额",
		modal:true,
		autoOpen:false,
		width:700,
		height:250
	});
	$("#WDATE").dialog('open');
}

function subLawsuit(obj)
{
	var PAYLIST_CODE=$(obj).attr("PAYLIST_CODE");
	if(confirm("是否确定转诉讼！")){
		
		jQuery.ajax({
			type:"post",
 			url:"Overdue!subLawsuit.action?PAYLIST_CODE="+PAYLIST_CODE,
 			dataType:"json",
 			success:function(e){
				if(e.flag == true)
				{
					alert("已转诉讼");
					window.location.href="../overdue/Overdue!query_overdue_Price_MG.action?PAYLIST_CODE="+PAYLIST_CODE;
				}
				else{
					alert("转诉讼失败");
				}
 			},
			error:function(e){alert("转诉讼失败");}   
 		});
	}
}

function pay_Over_dun(){
	var PAY_TIME_CURR = $("input[name='PAY_TIME_CURR']").val();
	var PAY_PAYLIST_CODE = $("#PAY_PAYLIST_CODE").val();
	if(PAY_TIME_CURR==''){
		alert("请选择日期");
		return;
	}
	jQuery.ajax({
 			type:'post',
 			url:"Overdue!catulateFI.action?PAYLIST_CODE="+PAY_PAYLIST_CODE+"&CURR_DATE="+PAY_TIME_CURR,
 			dataType:"json",
 			success:function(databack){
				$("#id2").empty();
				$("#id3").empty();
				$("#id4").empty();
				$("#id2").append(databack.data[0].DUNDAY);
				$("#id3").append((databack.data[0].SUM_FINE).toFixed(2));
				$("#id4").append((databack.data[0].CURR_DUN_MONEY).toFixed(2));
 			}
 		});
	
}


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}


/*function xuanzhetype(obj){
	if(obj.value=="2"){
		alert("上门催收时，可以添加一条巡视记录");
	}
}*/

function delnewAdd(e){ 
	$(e).parent().parent().remove();
}
 
function Voicecalls(e){
		var tr=$(e).parent().parent();
		var LINKMAN=tr.find("td:eq(3)").text();
		var LINKMAN_PHONE=tr.find("td:eq(4)").text();
		var renter_code=$(e).attr("RENTER_CODE");
		var PAY_CODE=$(e).attr("PAY_CODE");
		var td = $(e).parent().parent().parent().parent().parent().parent().parent(); 
		var data ={
			LINKMAN : LINKMAN,
			LINKMAN_PHONE : LINKMAN_PHONE,
			PAY_CODE : PAY_CODE,
			renter_code : renter_code
		}
			jQuery.ajax({
		url : "OverdueCall!ins.action",
        data : data,
        dataType:"json",
        success : function(json)
        {
			if(json==1)
			{
				alert("呼叫成功！");
				jQuery.get("OverdueCollect!getSpreadOverdueRecordInfo.action?RENTER_CODE="+renter_code+"&PAY_CODE="+PAY_CODE,function(text){
					td.html(text);
					td.data("__isLoad__",true);
				});
			}else
			{
				alert("呼叫失败！");
			}
			
        }
	  });  
		
	}
	function guan1(){
		$("#tian").dialog('close');
	}
	function gai(ID,RENTER_CODE,CALL_PAY_CODE,e)
	{ 
		var td = $(e).parent().parent().parent().parent().parent().parent().parent();
		var tdid=td.attr("id");
		var ID = ID;
				jQuery.post("OverdueCall!xiugai.action?ID="+ID+"&RENTER_CODE="+RENTER_CODE+"&CALL_PAY_CODE="+CALL_PAY_CODE+"&tdid="+tdid,function(html){
				
					$("#tian").dialog("open");
					$("#tian").html(html);
				});
   		
	}
	
	function getRandom(n){
    	return Math.floor(Math.random()*n+1)
    }
	
	var  i=0;
	$(function(){
	
	//页面操作JS
	$("body").click(function(e){
		var ele = $(e.target);
		 if(ele.is(".addpressRecord")){
			$(".uselessTR").remove();
			var div=ele.parent().parent().parent().parent().parent();
			var tr1=div.find(".template").clone();
			tr1.removeClass("template");
			tr1.addClass("new");
			tr1.attr("style","");
			var myDate = new Date();
			var time="";
			var year=myDate.getFullYear();
			var month=myDate.getMonth()+1; 
			var day=myDate.getDate();   
			var hour=myDate.getHours();
			var Minutes=myDate.getMinutes();
			time=year+"-"+month+"-"+day+" "+hour+":"+Minutes;
			var tempt = tr1.find("#upfile");
			var temptPath = tr1.find("#upfile1");
			var temptName = tr1.find("#upfile2");
			var id = getRandom(1000000);
			tempt.attr("id",id);
			temptPath.attr("id",id+"path");
			temptName.attr("id",id+"name");
			tr1.find("input[name='TIME']").val(time);
			div.find(".pressrecorddate").append(tr1);
		//添加催收记录数据
		}else if(ele.is(".adddate")){
			var tr=ele.parent().parent();
			var PAYLIST_CODE=tr.find("input[name='paylist_code']").val();
			var RETURN_TYPE=tr.find("select[name='RETURN_TYPE']").val();
			var Typex=tr.find("select[name='TYPEX']").val();
			var Dname=tr.find("input[name='DNAME']").val();
			var Jname=tr.find("input[name='JNAME']").val();
			var Time=tr.find("input[name='TIME']").val();
			var Record=tr.find("input[name='RECORD']").val();
			var Telno=tr.find("input[name='TELNO']").val();
			var REPAYMENT_TYPE=tr.find("select[name='REPAYMENT_TYPE']").val();
			var id =tr.find(".upfile").attr("id");
			
			jQuery.ajaxFileUpload({
				type:"post",
				url:"Overdue!doAddpressDunLog.action?CALL_NAME="+Dname+"&RECIEVE_NAME="+Jname+"&CALL_DATE="+Time+"&RECORD="+Record+"&CALL_TEL="+Telno+"&TYPE="+Typex+"&REPAYMENT_TYPE="+REPAYMENT_TYPE+"&PAYLIST_CODE="+PAYLIST_CODE+"&RETURN_TYPE="+RETURN_TYPE,
				dataType:"json",
				fileElementId:id,
				success:function(e){
						alert("保存成功");
						$("#overdue_Cust_MG").datagrid("reload");
						
				},
				error:function(e){alert("保存失败");}
			});
		//删除
		}else if(ele.is(".delolddate")){
			var ta=ele;
			var id=ta.attr("SID");
			var tr=ta.parent().parent();
			if(confirm("请对你的操作负责  确认删除吗")){
				jQuery.ajax({
    				type:"post",
    				url:"Overdue!doDeletePressDunLog.action",
    				data:"ID="+id,
					dataType:"json",
    				success:function(e){
						if(e.flag == true)
						{
							alert("已删除");
							tr.remove();	
						}
    				},
    				error:function(e){alert("删除有误！");}   
    			});
			}
		}
	});
	
});
	
	
	function statisticsBlock(PAYLIST_CODE){
    	var d= document.getElementById("Statistics");
    		jQuery.ajax({
         			type:'post',
         			url:'$request.contextPath'+'/OverdueCollect/OverdueCollect!getResultStatistics.action?PAYLIST_CODE='+PAYLIST_CODE,
         			data:'',
         			success:function(databack){
         				$("#showLog").html("<font color='green'>刷新完毕!</font>"); 	
         			}
         		});
			openAddInsureType("ResultStatistics");
    }
	
	function referDue(){
		
		if(confirm("是否要更新逾期数据！")){
			
			jQuery.ajax({
				type:"post",
     			url:'Overdue!referDue.action',
     			dataType:"json",
     			success:function(e){
					if(e.flag == true)
					{
						alert("已更新");
						window.location.href="../overdue/Overdue!query_overdue_Price_MG.action";
					}
					else{
						alert("更新失败");
					}
     			},
				error:function(e){alert("更新失败");}   
     		});
		}
	}
	function turnUrge(){
		$('#urge').dialog({title:"转催收"});
		$('#urge').dialog('open');
	}
	function addUrge(type){
		var url = "Overdue!addUrge.action";
		var datagridList=checkData.datagrid('getChecked');
		if(datagridList.length == 0){
			alert("请先选择数据");
			return;
		}
		var PAY_ID_ARR = "";	
		for(var i = 0; i < datagridList.length; i++)
		{
			PAY_ID_ARR = PAY_ID_ARR + datagridList[i].PAY_ID + ",";
		}
		
		console.info(PAY_ID_ARR);
		jQuery.ajax({
			type:"post",
 			url:url,
 			data:{PAY_ID_ARR:PAY_ID_ARR,TYPE:type},
 			dataType:"json",
 			success:function(e){
				if(e.flag == true){
					alert("更新成功");
					$('#urge').dialog('close');
				} else{
					alert("任务失败");
				}
 			},
			error:function(e){alert("系统错误，请联系管理员");}   
 		});
		
	}
	
	//导出
	function exportExcels() {
	
		   var page = $('#overdue_Cust_MG').datagrid("getRows").length;
		   var rows = $('#overdue_Cust_MG').datagrid("options").pageSize;
//		   var grid = $('#overdue_Cust_MG');  
//		   var options = grid.datagrid('getPager').data("pagination").options;  
//		   var curr = options.pageNumber;  
//		   var total = options.total;  
//		   var max = Math.ceil(total/options.pageSize);  
	   
		   window.location.href = _basePath + "/overdue/Overdue!exportExcels.action?page="+page+"&rows="+rows;
	}
	

	
	
	/**单个值循环***/
	function exportPDF(PAY_ID,CUST_NAME){
		var page = $('#overdue_Cust_MG').datagrid("getRows").length;
	    var rows = $('#overdue_Cust_MG').datagrid("options").pageSize;
		if(confirm(""+CUST_NAME+"逾期催收通知,确认导出它吗?")){		
				 window.location.href = _basePath + "/overdue/PDFReport!generatePDFs.action?page="+page+"&rows="+rows+"&PAY_ID="+PAY_ID;
		}
		
	}
	
	/**多个值循环***/
	function exportLotsOfPdf(){
		var exportData_ = [];

		var datagridList = $('#overdue_Cust_MG').datagrid('getChecked');
		var page = $('#overdue_Cust_MG').datagrid("getRows").length;
	    var rows = $('#overdue_Cust_MG').datagrid("options").pageSize;
	    
		if(datagridList.length<=0)
		{
			$.messager.alert("提示","请先选择数据在继续提交操作!");
			return false;
		}
		if(datagridList.length==1){
			$.messager.alert("提示","批量请选择2条以上继续提交操作!");
			return false;
		}else if(datagridList.length>1){
			for(var i = 0; i< datagridList.length; i++){
				var temp = {};	
				temp.PAY_ID=datagridList[i].PAY_ID;
				//temp.CUST_NAME =  datagridList[i].CUST_NAME;//CUST_NAME 客户
				//temp.PAY_START =  datagridList[i].PAY_START;//PAY_START  实付日期
				//temp.CREATE_DATE =  datagridList[i].CREATE_DATE;//PAY_START 逾期日期
				//temp.RENT_RECE_ALL= datagridList[i].RENT_RECE_ALL; //逾期金额
			    //temp.PENALTY_RECE_ALL= datagridList[i].PENALTY_RECE_ALL; //罚息金额				    
				//if(datagridList[i].TOPRIC_SUBFIRENT==undefined){//融资额'
					//	temp.TOPRIC_SUBFIRENT=0.00;
				//}else{
				//		temp.TOPRIC_SUBFIRENT=datagridList[i].TOPRIC_SUBFIRENT;
				//}
					//	temp.RECE_ALL=temp.RENT_RECE_ALL+temp.PENALTY_RECE_ALL;	
						exportData_.push(temp);			
			};
				
			var exportData_= {exportData:exportData_};

			
			if(confirm("逾期催收通知,确认导出它吗?")){
				  window.location.href = _basePath + "/overdue/PDFReport!exportLotsOfPdf.action?exportData="+encodeURI(JSON.stringify(exportData_))+"&page="+page+"&rows="+rows;
		    }
		}

}
	
	
	
	
$(document).ready(function(){
	$("#Remind_Cust_MG").datagrid({
		url:"RentRemind!query_Remind_Price_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		pageSize:10,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'NAME',title:'客户名称',width:13,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:13,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:6,align:'center'},
		          	{field:'BEGINNING_NUM',title:'应收期次',width:10,align:'center'},
		          	{field:'LEASE_TERM',title:'租期总数',width:9,align:'center'},
		          	{field:'LASE_TERM',title:'剩余租期',width:9,align:'center'},
		          	{field:'BEGINNING_MONEY',title:'应收款金额',width:9,align:'center'},
		          	{field:'BEGINNING_PAID',title:'实收金额',width:9,align:'center'},
		          	{field:'BALANCE',title:'余额 ',width:9,align:'center'},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'应收日期',width:9,align:'center'},
		         ]],
		         view:detailview,
			 		detailFormatter : function(index, row) {
			 			return '<div id="ddv-' + row.PAYLIST_CODE + '" style="padding:5px 0;"></div>';
			 		},
					onExpandRow : function(index, row) {
			 			$('#ddv-'+row.PAYLIST_CODE).panel({
			 				height:190,  
			 				border:false,
			 				cache:false,
			 				href:'RentRemind!create_GT_page.action?PAYLIST_CODE='+row.PAYLIST_CODE,
			 				onLoad:function(){
			 					$('#Remind_Cust_MG').datagrid('fixDetailRowHeight',index);
			 				} 
			 			});
			 			$('#Remind_Cust_MG').datagrid('fixDetailRowHeight',index);
			 		}
		});
});

function emptyData(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}
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
				url:"RentRemind!doAddpressDunLog.action?CALL_NAME="+Dname+"&RECIEVE_NAME="+Jname+"&CALL_DATE="+Time+"&RECORD="+Record+"&CALL_TEL="+Telno+"&TYPE="+Typex+"&REPAYMENT_TYPE="+REPAYMENT_TYPE+"&PAYLIST_CODE="+PAYLIST_CODE+"&RETURN_TYPE="+RETURN_TYPE,
				dataType:"json",
				fileElementId:id,
				success:function(e){
						alert("保存成功");
						$("#Remind_Cust_MG").datagrid("reload");
						
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
    				url:"RentRemind!doDeletePressDunLog.action",
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
function getRandom(n){
	return Math.floor(Math.random()*n+1)
}
function delnewAdd(e){ 
	$(e).parent().parent().remove();
}
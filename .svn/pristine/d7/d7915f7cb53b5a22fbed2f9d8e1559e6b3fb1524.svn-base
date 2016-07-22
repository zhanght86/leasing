function addfollow(toId,modelId1,modelId2)
{
	var tr1=jQuery("#"+modelId1).clone(true);
	jQuery(tr1).removeAttr("style");
	jQuery(tr1).removeAttr("id");
	jQuery("#"+toId).prev().after(tr1);
	var tr2=jQuery("#"+modelId2).clone(true);
	jQuery(tr2).removeAttr("style");
	jQuery(tr2).removeAttr("id");
	jQuery("#"+toId).prev().after(tr2);
}

function save2()
{
	
	var url = _basePath+"/project/FollowProject!addfollow.action";
	var PROJECT_ID = $("#PROJECT_ID").val();
	var EXPECTED_DATE=$("input[name='EXPECTED_DATE']").val();
	var STATUS=$("select[name='STATUS']").find("option:selected").val();
	var STATUS1=$("select[name='STATUS']").find("option:selected").text();
	var FOLLOW_START=$("#FOLLOW_START").datebox('getValue');
	var FOLLOW_END=$("#FOLLOW_END").datebox('getValue');
	var MEMO=$("textarea[name='MEMO']").val();
	
	//debugger ;
	if(!EXPECTED_DATE || !FOLLOW_START || !FOLLOW_END){
		$.messager.alert("提示信息","请填写相关信息!");
		return ;
	}
	$("#addsave").linkbutton("disable");
	var param = {
			PROJECT_ID:PROJECT_ID,
			EXPECTED_DATE:EXPECTED_DATE,
			STATUS:STATUS,
			FOLLOW_START:FOLLOW_START,
			FOLLOW_END:FOLLOW_END,
			MEMO:MEMO
	} ;
	//alert(JSON.stringify(param)) ;
	jQuery.ajax({
		url:url,
		data:param,
		dataType:'json',
		success:function(b){
			if (b.flag) {
						alert("保存成功！");
						html="<tr><td>"+EXPECTED_DATE+"</td>";
						html+="<td>"+STATUS1+"</td>";
						html+="<td>"+FOLLOW_START+"</td>";
						html+="<td>"+FOLLOW_END+"</td>";
						html+="<td>"+MEMO+"</td>";
						html+="<td ><a href='javascript:void(0)' onclick='delfollow(this,"+b.data+")'>删除</a></td>";
						$("#Monitor").append(html);
						emptyData();
						$("#addsave").linkbutton('enable');
					} else {
						alert("保存失败！");
						$("#addsave").linkbutton('enable');
				}
		   }
	});
}
function emptyData()
{
	$("#EXPECTED_DATE").datebox("setValue","");
	$("#FOLLOW_START").datebox("setValue","");
	$("#FOLLOW_END").datebox("setValue","");
	$("textarea[name='MEMO']").val('');
}

function emptyData1()
{
	$("#SPEND_DATE").datebox("setValue","");
	$("#SPEND_MONEY").val('');
	$("input[name='SPEND_MONEY']").val('');
	$("textarea[name='SPEND_MEMO']").val('');
}
function save1()
{
	
	var url = _basePath+"/project/FollowProject!addspend.action";
	var PROJECT_ID = $("#PROJECT_ID").val();
	var SPEND_DATE=$("input[name='SPEND_DATE']").val();
	var SPEND_STATUS=$("select[name='SPEND_STATUS']").find("option:selected").val();
	var SPEND_STATUS1=$("select[name='SPEND_STATUS']").find("option:selected").text();
	var SPEND_MONEY=$("input[name='SPEND_MONEY']").val();
	var SPEND_MEMO=$("textarea[name='SPEND_MEMO']").val();
	var param = {
			PROJECT_ID:PROJECT_ID,
			SPEND_DATE:SPEND_DATE,
			SPEND_STATUS:SPEND_STATUS,
			SPEND_MONEY:SPEND_MONEY,
			SPEND_MEMO:SPEND_MEMO
	};

	//debugger ;
	if((SPEND_DATE==""||SPEND_DATE==null)&&(SPEND_MONEY==""||SPEND_MONEY==null)&&(SPEND_MEMO==""||SPEND_MEMO==null)){
		$.messager.alert("提示信息","请填写相关信息!");
		return ;
	}
	$("#addsave1").linkbutton("disable");
	jQuery.ajax({
		url:url,
		data:param,
		dataType:'json',
		success:function(b){
			if (b.flag) {
				alert("保存成功！");
				html="<tr><td>"+SPEND_DATE+"</td>";
				html+="<td>"+SPEND_STATUS1+"</td>";
				html+="<td>"+SPEND_MONEY+"</td>";
				html+="<td>"+SPEND_MEMO+"</td>";
				html+="<td><a href='javascript:void(0)'   onclick='delspend(this,"+b.data+")'>删除</a></td>";
				$("#Monitor1").append(html);
				$("#addsave1").linkbutton('enable');
			} else {
				alert("保存失败！");
				$("#addsave1").linkbutton('enable');
			}
			emptyData1();
	    }
	});
}
function delfollow(e,ID)
{
	var tr=$(e).parent().parent();
	var url = _basePath+"/project/FollowProject!delfollow.action";
	var id=ID;
	var param={
			ID:id
	}
	jQuery.ajax({
		url:url,
		data:param,
		dataType:'json',
		success:function(b){
			if (b.flag) {
						alert("删除成功！");
						tr.remove();
					} else {
						alert("删除失败！");
				}
		   }
	});
}

function delspend(e,ID)
{
	var tr=$(e).parent().parent();
	var url = _basePath+"/project/FollowProject!delspend.action";
	var id=ID;
	var param={
			ID:id
	}
	jQuery.ajax({
		url:url,
		data:param,
		dataType:'json',
		success:function(b){
			if (b.flag) {
						alert("删除成功！");
						tr.remove();
					} else {
						alert("删除失败！");
				}
		   }
	});
}
function graphTJ(url){
	$.ajax({
		type:'post',
		url:url,
		data:"param="+getFromData('#'+formId),
		dataType:'json',
		success:function(json){
			if(json.flag){
				$("#chartShow").html(json.data);
			}
		}
	});
}
function toSeacher() {
	var dataUrl = 'Positions!doPositionsData.action';
	var searchParams = getFromData('#pageForm');
	
	$('#table1').datagrid({
		url : dataUrl,
		queryParams : {"searchParams" : searchParams}
	});
	if(_isNotLoad){
		$('#table1').datagrid({
			url : dataUrl,
			queryParams : {"searchParams" : searchParams}
		});
		_isNotLoad = false;
	}else{
		$('#table1').datagrid('load', {"searchParams" : searchParams});
	}
}
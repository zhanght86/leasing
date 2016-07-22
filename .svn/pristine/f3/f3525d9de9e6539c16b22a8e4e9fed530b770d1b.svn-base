/**
 * 档案借出
 */
function saveDossierOut(){
	
	var json = [];
	$('[_LEASE_CODE]').each(function(){
		var _T = this; 
		var lease_code = {
				LEASE_CODE: $(_T).attr('_LEASE_CODE'),
				dossList : []
		};
		$('.DOSSIERID:checked',_T).each(function(){
			var _T2 = this;
			var doss = {
					ID: $(_T2).val(),
					STORAGE_ID : $(_T2).attr('_STORAGE_ID'),
					DOSSIER_COUNT : $('.zfs' , $(_T2).parents('tr')).val(),
					DOSSIER_NUMBER : $('.jyfs',$(_T2).parents('tr')).val(),
					//实际借阅类型
					DOSSIER_TEMP : $('.jylx:checked',$(_T2).parents('tr')).val()?"2":"1",
					ORIGINAL_RETURN : $('.yj:checked',$(_T2).parents('tr')).val(),
					COPY_RETURN : $('.fyj:checked',$(_T2).parents('tr')).val(),
					STATUS : '1'	//可借
			};
			lease_code.dossList.push(doss);
		});
		$('.DOSSIERID:not(:checked)',_T).each(function(){
			var _T2 = this;
			var doss = {
					ID: $(_T2).val(),
					STATUS : '2'	//不可借
			};
			lease_code.dossList.push(doss);
		});
		json.push(lease_code);
	});
	console.debug(json);
	$.ajax({
		url: _basePath+ '/documentApp/DossierBorrow!saveDossierOutJbpm.action',
		data: 'BORROW_ID='+$('#BORROW_ID').val()+'&jsonStr='+JSON.stringify(json),
		dataType : 'json',
		type:'post',
		success : function (data){
			alert(data.msg);
		}
	});
}
/**
 * 档案归还
 */
function saveBorrowerReturn(){
	var json = [];
	$('[_LEASE_CODE]' ).each(function (){
		var _T = this;
		var lease_code = {
				lease_code : $(_T).attr('_LEASE_CODE'),
				dossList : []
		};
		$('.DOSSIERID:checked',_T).each(function(){
			var _T2 = this;
//			var sfyj = $('.sfyj',$(_T2).parents('tr')).val();
			var doss = {
					ID: $(_T2).val(),
					STORAGE_ID : $(_T2).attr('_STORAGE_ID'),
					DOSSIER_NUMBER : $('.jyfs',$(_T2).parents('tr')).val(),
					//实际借阅类型
					DOSSIER_TEMP : $('.jylx:checked',$(_T2).parents('tr')).val()?"2":"1",
					//原件是否归还
					ORIGINAL_RETURN : $('.yj:checked',$(_T2).parents('tr')).val(),
					//复印件是否归还
					COPY_RETURN : $('.fyj:checked',$(_T2).parents('tr')).val(),
//					HANDOVER_STATUS : sfyj,//是否移交
//					STATUS : '1' ,	//可借
					ORIGINAL_RETURN : '1' , //原件归还
					COPY_RETURN : '1' //复印件归还
			};
			lease_code.dossList.push(doss);
		});
		$('.DOSSIERID:not(:checked)',_T).each(function(){
			var _T2 = this;
			var doss = {
					ID: $(_T2).val(),
					STORAGE_ID : $(_T2).attr('_STORAGE_ID'),
//					STATUS : '2' ,	//不可借
					ORIGINAL_RETURN : '2' , //原件不归还
					COPY_RETURN : '2' //复印件不归还
			};
			lease_code.dossList.push(doss);
		});
		json.push(lease_code);
	});
	console.debug(json);
	$.ajax({
		url: _basePath+ '/documentApp/DossierBorrow!saveBorrowerReturnJbpm.action',
		data: 'BORROW_ID='+$('#BORROW_ID').val()+'&jsonStr='+JSON.stringify(json),
		dataType : 'json',
		type:'post',
		success : function (data){
			alert(data.msg);
			location.reload(true);
		}
	});
}
/**
 * 直接移交
 */
function saveHandover(){
	var json = [];
	$('[_LEASE_CODE]').each(function(){
		var _T = this;
		var lease_code = {
				lease_code : $(_T).attr('_LEASE_CODE'),
				dossList : []
		};
		$('.DOSSIERID:checked').each(function(){
			var _T2 = this;
			var doss = {
				ID : $(_T2).val(),
				STORAGE_ID : $(_T2).attr('STORAGE_ID'),
				//借阅份数
				DOSSIER_NUMBER : $('.jyfs',$(_T2).parents('tr')).val(),
				//实际借阅类型
				DOSSIER_TEMP : $('.jylx:checked',$(_T2).parents('tr')).val()?"2":"1",
				//原件是否归还
				ORIGINAL_RETURN : $('.yj:checked',$(_T2).parents('tr')).val(),
				//复印件是否归还
				COPY_RETURN : $('.fyj:checked',$(_T2).parents('tr')).val(),
				//移交状态设为1：移交
				HANDOVER_STATUS : '1'
			};
			lease_code.dossList.push(doss);
		});
		$('.DOSSIERID:not(:checked)',_T).each(function(){
			var _T2 = this;
			var doss = {
					ID: $(_T2).val(),
					STORAGE_ID : $(_T2).attr('_STORAGE_ID'),
					//移交状态设为0：不移交
					HANDOVER_STATUS : '0'
			};
			lease_code.dossList.push(doss);
		});
		json.push(lease_code);
	});
	$.ajax({
		url : _basePath+'/documentApp/DossierBorrow!saveBorrowerHandoverJbpm.action',
		data: 'BORROW_ID='+$('#BORROW_ID').val()+'&jsonStr='+JSON.stringify(json),
		dataType : 'json',
		type:'post',
		success : function (data){
			alert(data.msg);
			location.reload(true) ;
		}
	});
}


function checkAll(all){
	if($("#"+all).is(":checked")){
		$("."+all+":not(:disabled)").attr('checked',true);
	}else{
		$("."+all).attr('checked',false);
	}
}

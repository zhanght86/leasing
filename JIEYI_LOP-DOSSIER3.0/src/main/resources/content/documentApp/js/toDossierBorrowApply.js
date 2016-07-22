
function save(){
//	checkRequired();
	
	var borrowform = $('#borrowform').serialize() ;
	var json = [];
	$('[_LEASE_CODE]').each(function (){
		var T__ = this;
		var hetong = {
				LEASE_CODE:$(T__).attr('_LEASE_CODE')
				,dosslist : []
		};
		console.debug($('.DOSSIERID:checked',T__).size());
		$('.DOSSIERID:checked',T__).each(function (){
			var T2__ = this;
			var dossier = {
				STORAGE_ID: $(T2__).val()
				,DOSSIER_COUNT : $('.zfs' , $(T2__).parents('tr')).val()
				,DOSSIER_NUMBER:$('.jyfs' , $(T2__).parents('tr')).val()
				,DOSSIER_TEMP: $('.jylx:checked' , $(T2__).parents('tr')).val() ? 
									$('.jylx:checked' , $(T2__).parents('tr')).val():"1"
				,ORIGINAL_RETURN : $('.yj:checked',$(T2__).parents('tr')).val()
				,COPY_RETURN : $('.fyj:checked',$(T2__).parents('tr')).val()
			};
			hetong.dosslist.push(dossier);
		});
		json.push(hetong);
	});
	console.debug(json);
	
	$.ajax({
		url:_basePath+'/documentApp/DossierBorrow!startBorrowByJbpm.action',
		data:borrowform+"&jsonStr="+JSON.stringify(json),
		dataType:"json",
		type:'post',
		success:function(data){
			alert(data.msg);
			top.removeTab('批量借阅申请');
			top.addTab('权证管理-档案管理',_basePath+'/documentApp/ApplyDossier.action');
		}
	});
}

function selectDirect(){
	$('#ZIP_CODE').val('');
	$('#MAILING_ADDRESS').val('');
	$('#ZIP_CODE').attr('disabled',true);
	$('#MAILING_ADDRESS').attr('disabled',true);
	$('#ZIP_CODE').removeAttr('_type');
	$('#MAILING_ADDRESS').removeAttr('_type');
}

function selectMailing(){
	$('#ZIP_CODE').attr('disabled',false);
	$('#MAILING_ADDRESS').attr('disabled',false);
	$('#ZIP_CODE').attr('_type','邮政编码');
	$('#MAILING_ADDRESS').attr('_type','邮寄地址');
}

function checkRequired(){
	$('[_type]:input').each(function(){
		if($(this).val()==''){
			var type = $(this).attr('_type');
			alert("【"+type+"】不能为空");
			return false;
		}
	});
	if($('[name="RECEIVE_MODE"]:radio:checked').val()==null){
		alert("【接收方式】不能为空");
		return false;
	}
	return true;
}

function checkAll(all){
	if($("#"+all).is(":checked")){
		$("."+all+":not(:disabled)").attr('checked',true);
	}else{
		$("."+all).attr('checked',false);
	}
}


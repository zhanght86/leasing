function addrow(toId,modelId){
   	var tr=jQuery("#"+modelId).clone(true);
	jQuery(tr).removeAttr("style");
	jQuery(tr).removeAttr("id");
	jQuery(tr).attr("name",toId);
	jQuery("#"+toId).after(tr);
}

function saveMessForBigPro(){
    var url = _basePath+"/project/TrialReport!saveTrialReport.action";
	var PROJECT_ID = $("#PROJECT_ID").val();
	var shortLoan = [];
	jQuery("tr[name='CLIENT_SHORT_JIE']").each(function(){
	   var item={};
	   item.TYPE = "1";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.END_TIME = $(this).find("input[name='END_TIME']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   shortLoan.push(item);
	});
	jQuery("#shortLoan").val(JSON.stringify(shortLoan));
	
    var longLoan = [];
	jQuery("tr[name='CLIENT_LONG_JIE']").each(function(){
	   var item={};
	   item.TYPE = "2";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.END_TIME = $(this).find("input[name='END_TIME']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   longLoan.push(item);
	});
	jQuery("#longLoan").val(JSON.stringify(longLoan));
	
	var landHouse = [];
	jQuery("tr[name='CLIENT_FANG']").each(function(){
	   var item={};
	   item.TYPE = "3";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   landHouse.push(item);
	});
	jQuery("#landHouse").val(JSON.stringify(landHouse));
	
	var bankCun = [];
	jQuery("tr[name='CLIENT_CUN']").each(function(){
	   var item={};
	   item.TYPE = "6";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   bankCun.push(item);
	});
	jQuery("#bankCun").val(JSON.stringify(bankCun));
	
	var haveEqs = [];
	jQuery("tr[name='CLIENT_EQ']").each(function(){
	   var item={};
	   item.TYPE = "4";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   haveEqs.push(item);
	});
	jQuery("#haveEqs").val(JSON.stringify(haveEqs));
	
	var otherAssets = [];
	$("tr[name='CLIENT_OTHER']").each(function(){
	   var item={};
	   item.TYPE = "5";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   otherAssets.push(item);
	});
	jQuery("#otherAssets").val(JSON.stringify(otherAssets));
	
	var assureLandHouse = [];
	jQuery("tr[name='ASSURE_FANG']").each(function(){
	   var item={};
	   item.TYPE = "7";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   assureLandHouse.push(item);
	});
	jQuery("#assureLandHouse").val(JSON.stringify(assureLandHouse));
	
	var assureBankCun = [];
	jQuery("tr[name='ASSURE_CUN']").each(function(){
	   var item={};
	   item.TYPE = "8";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   assureBankCun.push(item);
	});
	jQuery("#assureBankCun").val(JSON.stringify(assureBankCun));
	
	var assureHaveEqs = [];
	jQuery("tr[name='ASSURE_EQ']").each(function(){
	   var item={};
	   item.TYPE = "9";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = $(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = jQuery(this).find("input[name='WORTH_MONEY']").val(); 
	   assureHaveEqs.push(item);
	});
	jQuery("#assureHaveEqs").val(JSON.stringify(assureHaveEqs));
	
	var assureOtherAssets = [];
	jQuery("tr[name='ASSURE_OTHER']").each(function(){
	   var item={};
	   item.TYPE = "10";
	   item.PROJECT_ID = PROJECT_ID ;
	   item.DETAIL = jQuery(this).find("input[name='DETAIL']").val();
	   item.WORTH_MONEY = $(this).find("input[name='WORTH_MONEY']").val(); 
	   assureOtherAssets.push(item);
	});
	jQuery("#assureOtherAssets").val(JSON.stringify(assureOtherAssets));
	
	jQuery('#form01').form('submit',{
        url:url,
        onSubmit: function(){
		     jQuery("#saveButton").css("disabled","disabled");
        },
        success : function(){
        	jQuery.messager.alert('提示','保存成功！');
        }
    });
}
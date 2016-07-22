$().ready(function(){
	$('#tt').tabs({   
	    border:false,   
	    onSelect:function(title){
			if(title=='资产负债表')
			{
				$("#dist_Div").empty();
				$("#cash_Div").empty();
			}
			else if(title=='利润及利润分配表')
			{
				$("#debt_Div").empty();
				$("#cash_Div").empty();
				not_profitfuzhai('H34','C100','C37','D37');
			}
			else{
				$("#dist_Div").empty();
				$("#debt_Div").empty();
			
				cashJs('E6','LC19','B44','C44');
				cashJs('F6','LD19','B45','C45');
				cashJs('I6','LH19','B46','C46');
				cashJs('E29','ZB4','B47','C47');
				cashJs('F29','ZC4','B48','C48');
				cashJs('I29','ZH4','B49','C49');
	            cashJs('I30','ZC4','B50','C50');
				cashJs1('H37','ZC4','ZH4','B51','C51');
				
				//统计
				tongji1('I7','ZH13','G7');
				tongji2('I8','ZC25','ZB25','G8');
				tongji2('I11','ZB20','ZC20','G11');
				tongji2('I12','ZF14','ZE14','G12');
				tongji3('I16','LD11','G16');
				tongji4('I17','LD13','G17');
				tongji2('I18','ZF22','ZC35','G18');
				tongji2('I19','ZB12','ZC12','G19');
			}
	    }
	});
	
});

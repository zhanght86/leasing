var socialProductAgentId = "sh001";
var socialProductor = "社会产品";
//更改年利率
	function changePoundageRatio(){
		var global_projectMoney = document.all.ProjectMoney.value?parseFloat(document.all.ProjectMoney.value):0;
		var global_poundageRatio = document.all.PoundageRatio.value?parseFloat(document.all.PoundageRatio.value):0;
	    document.all.Poundage.value = decimal(global_projectMoney*(global_poundageRatio/100),0);
   }
   function yearRatioAuto()
   {
	   //再融租标志
       var is_release = getTracywindyObject('id_combo_is_release').getRawValue();
	   //是否是社会产品
	   var isSocialProduct = document.all.zzs.value.toLowerCase()==socialProductor;
	   //陕重汽特殊供应商——“河南亚飞”利率和手续费
	   var dl_name = document.all.AgentCompany.value;
	   var productMaker   = document.forms[0].zzs.value;
	   var firstCostRatio_temp = document.all.HireScale.value;
	   var firstCostRatio=firstCostRatio_temp?parseFloat(firstCostRatio_temp):0;
	   var zlQX_temp = document.all.FinanceTerm.value;
	   var zlQX=zlQX_temp?parseFloat(zlQX_temp):0;
	   var equip_is_new=document.all.equip_is_new.value ;
	   var temp_YearRatio = document.all.YearRatio.value;//用于扬州亚星年利率的特殊处理
	   document.all.YearRatio.value="0";
 	   document.all.PoundageRatio.value="0";
 	   
 	  //年利率和手续费率只读开关__以下:
 	  if(productMaker=="扬州亚星"){
 		   document.all.YearRatio.readOnly = false;
 		   document.all.YearRatio.value = temp_YearRatio;//避免每次变动利率都清空
		   removeClass(document.all.YearRatio,"td-content-readonly");
 	   }
 	  if(isSocialProduct)//社会产品
	   {
		   document.all.YearRatio.readOnly = false;
		   document.all.PoundageRatio.readOnly = false;
		   removeClass(document.all.YearRatio,"td-content-readonly");
		   removeClass(document.all.PoundageRatio,"td-content-readonly");
	   }
 	   
 	   //年利率和手续费自动计算逻辑__以下:
 	  if(productMaker=="陕重汽")
 	  { 
 		  if(dl_name=="河南亚飞"){
 			  if(zlQX>0){
 				  document.all.YearRatio.value="8.79";
 				  document.all.PoundageRatio.value="0";  				  
 			  }
 		  }else{
 	 	  var onCard = getTracywindyObject('id_combo_onCard').getRawValue();
	 		 if(onCard=="上牌抵押不挂靠"||onCard=="上牌抵押挂靠"){
	 			if(firstCostRatio>=35){
	 				if(zlQX>=1 && zlQX<12){//1年期限
	 					document.all.YearRatio.value="6.00";
	 					document.all.PoundageRatio.value="0.4";
	 				}else if (zlQX==12){
	 					document.all.YearRatio.value="6.00";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="6.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.11";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio>=30 && firstCostRatio<35){
	 				if(zlQX>=1 && zlQX<12){//1年期限
	 					document.all.YearRatio.value="6.96";
	 					document.all.PoundageRatio.value="0.4";
	 				}else if (zlQX==12){
	 					document.all.YearRatio.value="6.96";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.86";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio<30){
	 				if(zlQX>=1 && zlQX<12){//1年期限
	 					document.all.YearRatio.value="7.81";
	 					document.all.PoundageRatio.value="0.4";
	 				}else if(zlQX==12){
	 					document.all.YearRatio.value="7.81";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 		}else{
	 			if(firstCostRatio>=45){
	 				if(zlQX>=1 && zlQX<12){//1年期限
	 					document.all.YearRatio.value="6.00";
	 					document.all.PoundageRatio.value="0.4";
	 				}else if(zlQX==12){
	 					document.all.YearRatio.value="6.00";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="6.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.11";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio>=40 && firstCostRatio<45){
	 				if(zlQX>=1 && zlQX<12){//1年期限
	 					document.all.YearRatio.value="6.96";
	 					document.all.PoundageRatio.value="0.4";
	 				}else if(zlQX==12){
	 					document.all.YearRatio.value="6.96";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.86";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio<40){
	 				if(zlQX>=1 && zlQX<12){//1年期限
	 					document.all.YearRatio.value="7.81";
	 					document.all.PoundageRatio.value="0.4";
	 				}else if(zlQX==12){
	 					document.all.YearRatio.value="7.81";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 		}
	 		var fuel_type = getTracywindyObject('id_combo_fuel_type').getRawValue();
	 		if(fuel_type=="天然气" && zlQX > 12){
	 			document.all.PoundageRatio.value="0.5";
	 		}
 		  }
 		}
 	  else if(productMaker=="陕西通力")
	  { 
	 	  var onCard = getTracywindyObject('id_combo_onCard').getRawValue();
	 		 if(onCard=="上牌抵押不挂靠"||onCard=="上牌抵押挂靠"){
	 			if(firstCostRatio>=35){
	 				if(zlQX>=6 && zlQX<=12){//1年期限
	 					document.all.YearRatio.value="6.00";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="6.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.11";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio>=30 && firstCostRatio<35){
	 				if(zlQX>=6 && zlQX<=12){//1年期限
	 					document.all.YearRatio.value="6.96";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.86";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio<30){
	 				if(zlQX>=6 && zlQX<=12){//1年期限
	 					document.all.YearRatio.value="7.81";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 		}else{
	 			if(firstCostRatio>=45){
	 				if(zlQX>=6 && zlQX<=12){//1年期限
	 					document.all.YearRatio.value="6.00";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="6.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.11";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio>=40 && firstCostRatio<45){
	 				if(zlQX>=6 && zlQX<=12){//1年期限
	 					document.all.YearRatio.value="6.96";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.61";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.86";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 			if(firstCostRatio<40){
	 				if(zlQX>=6 && zlQX<=12){//1年期限
	 					document.all.YearRatio.value="7.81";
	 					document.all.PoundageRatio.value="0.5";
	 				}else if(zlQX>12 && zlQX<=18){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.6";
	 				}else if(zlQX>18 && zlQX<=24){
	 					document.all.YearRatio.value="7.96";
	 					document.all.PoundageRatio.value="0.7";
	 				}else if(zlQX>24 && zlQX<=30){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.8";
	 				}else if(zlQX>30 && zlQX<=36){
	 					document.all.YearRatio.value="8.61";
	 					document.all.PoundageRatio.value="0.9";
	 				}else if(zlQX>36 && zlQX<=42){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.0";
	 				}else if(zlQX>42 && zlQX<=48){
	 					document.all.YearRatio.value="8.86";
	 					document.all.PoundageRatio.value="1.1";
	 				}
	 			}
	 		}
	 		var fuel_type = getTracywindyObject('id_combo_fuel_type').getRawValue();
	 		if(fuel_type=="天然气" && zlQX > 12){
	 			document.all.PoundageRatio.value="0.5";
	 		}
		}
 	  	else if(productMaker=="扬州盛达"){
 	  		if(firstCostRatio>=45){
 				if(zlQX>=1 && zlQX<12){//1年期限
 					document.all.YearRatio.value="6.00";
 					document.all.PoundageRatio.value="0.4";
 				}else if(zlQX==12){
 					document.all.YearRatio.value="6.00";
 					document.all.PoundageRatio.value="0.5";
 				}else if(zlQX>12 && zlQX<=18){
 					document.all.YearRatio.value="6.61";
 					document.all.PoundageRatio.value="0.6";
 				}else if(zlQX>18 && zlQX<=24){
 					document.all.YearRatio.value="7.11";
 					document.all.PoundageRatio.value="0.7";
 				}
 			}
 			if(firstCostRatio>=40 && firstCostRatio<45){
 				if(zlQX>=1 && zlQX<12){//1年期限
 					document.all.YearRatio.value="6.96";
 					document.all.PoundageRatio.value="0.4";
 				}else if(zlQX==12){
 					document.all.YearRatio.value="6.96";
 					document.all.PoundageRatio.value="0.5";
 				}else if(zlQX>12 && zlQX<=18){
 					document.all.YearRatio.value="7.61";
 					document.all.PoundageRatio.value="0.6";
 				}else if(zlQX>18 && zlQX<=24){
 					document.all.YearRatio.value="7.86";
 					document.all.PoundageRatio.value="0.7";
 				}
 			}
 			if(firstCostRatio<40){
 				if(zlQX>=1 && zlQX<12){//1年期限
 					document.all.YearRatio.value="7.81";
 					document.all.PoundageRatio.value="0.4";
 				}else if(zlQX==12){
 					document.all.YearRatio.value="7.81";
 					document.all.PoundageRatio.value="0.5";
 				}else if(zlQX>12 && zlQX<=18){
 					document.all.YearRatio.value="7.96";
 					document.all.PoundageRatio.value="0.6";
 				}else if(zlQX>18 && zlQX<=24){
 					document.all.YearRatio.value="7.96";
 					document.all.PoundageRatio.value="0.7";
 				}
 			}
 	  	}
 	  	else if(productMaker=="扬州亚星"){  	
 	  		if(firstCostRatio>=0){
 	  			if(zlQX>=1 && zlQX<12){//1年期限 					
 					document.all.PoundageRatio.value="0.4";
 				}else if(zlQX==12){
 					document.all.PoundageRatio.value="0.5";
 				}else if(zlQX>12 && zlQX<=18){
 					document.all.PoundageRatio.value="0.6";
 				}else if(zlQX>18 && zlQX<=24){
 					document.all.PoundageRatio.value="0.7";
 				}else if(zlQX>24 && zlQX<=30){
 					document.all.PoundageRatio.value="0.8";
 				}else if(zlQX>30 && zlQX<=36){
 					document.all.PoundageRatio.value="0.9";
 				}
 	  		}			
 	  	}
		else if(productMaker=="山推机械")//||productMaker=="山推股份")
		{ //||(productMaker=="山推股份"&&document.forms[0].ProductType.value!="装载机")){
			if(equip_is_new=='否'){//是否新车
				
				if(firstCostRatio>=20){
 					if(zlQX>=6 && zlQX<=12){//1年期限
 						document.all.YearRatio.value="7.81";
 						document.all.PoundageRatio.value="0.5";
 					}else if(zlQX>12 && zlQX<=24){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.7";
 					}else{
 						alert("请核实租赁期限是否在6-24个月内！！");
 						//document.all.FinanceTerm.value=24;
 					}
 				}else{
 					alert("起租比例不能低于20%！！");
 					//document.all.HireScale.value=20;
 				}
				
			}else{
 				if(firstCostRatio>=35){
 					if(zlQX>=6 && zlQX<12){//1年期限
 						document.all.YearRatio.value="6.0";
 						document.all.PoundageRatio.value="0.25";
 					}else if(zlQX==12){//1年期限
 						document.all.YearRatio.value="6.0";
 						document.all.PoundageRatio.value="0.5";
 					}else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="6.61";
 						document.all.PoundageRatio.value="0.6";
 					}else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="7.11";
 						document.all.PoundageRatio.value="0.7";
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}
 				}
 				if(firstCostRatio>=30 && firstCostRatio<35){
 					if(zlQX>=6 && zlQX<=12){//1年期限
 						document.all.YearRatio.value="6.96";
 						document.all.PoundageRatio.value="0.5";
 					}else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="7.61";
 						document.all.PoundageRatio.value="0.6";
 					}else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="7.86";
 						document.all.PoundageRatio.value="0.7";
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}
 				}
 				if(firstCostRatio<30){
 					if(zlQX>=6 && zlQX<=12){//1年期限
 						document.all.YearRatio.value="7.81";
 						document.all.PoundageRatio.value="0.5";
 					}else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.6";
 					}else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.7";
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}
 				}
 				
			}//是否新车
		}else if(productMaker=="山推股份"||productMaker=="山重建机" )
		{ //||(productMaker=="山推股份"&&document.forms[0].ProductType.value!="装载机")){
			if(equip_is_new=='否'){//是否新车
				//是否再融租
				if(is_release == '是'){
					if(zlQX>=6 && zlQX<=12){//1年期限
 						document.all.YearRatio.value="7.81";
 					}else if(zlQX>12 && zlQX<=24){
 						document.all.YearRatio.value="7.96";
 					}else{
 						alert("请核实租赁期限是否在6-24个月内！！");
 						//document.all.FinanceTerm.value=24;
 					}
				}else{
					if(firstCostRatio>=20){
	 					if(zlQX>=6 && zlQX<=12){//1年期限
	 						document.all.YearRatio.value="7.81";
	 						document.all.PoundageRatio.value="0.5";
	 					}else if(zlQX>12 && zlQX<=24){
	 						document.all.YearRatio.value="7.96";
	 						document.all.PoundageRatio.value="0.7";
	 					}//临时开放山推二手车24期以上利率
	 					else if(zlQX>24 && zlQX<=30){
	 						document.all.YearRatio.value="7.96";
	 						document.all.PoundageRatio.value="0.8";
	 					}else if(zlQX>30 && zlQX<=36){
	 						document.all.YearRatio.value="7.96";
	 						document.all.PoundageRatio.value="0.9";
	 					}else{
	 						alert("请核实租赁期限是否在6-24个月内！！");
	 						//document.all.FinanceTerm.value=24;
	 					}
	 				}else{
	 					alert("起租比例不能低于20%！！");
	 					//document.all.HireScale.value=20;
	 				}
				}
			}else{
 				if(firstCostRatio>=30){
 					if(zlQX>=1 && zlQX<12){//1年期限
 						document.all.YearRatio.value="6.0";
 						document.all.PoundageRatio.value="0.25";
 					}else if(zlQX==12){//1年期限
 						document.all.YearRatio.value="6.0";
 						document.all.PoundageRatio.value="0.5";
 					}else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="6.15";//"6.61";09-24
 						document.all.PoundageRatio.value="0.6";
 					}else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="6.15";//"7.11";
 						document.all.PoundageRatio.value="0.7";
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}
 				}
 				if(firstCostRatio<30){
 					if(zlQX>=1 && zlQX<12){
 						alert("起租比例小于30的项目,租赁期限不能小于12!");
 					}
 					else if(zlQX==12){
 						document.all.YearRatio.value="7.81";
 						document.all.PoundageRatio.value="0.5";
 					}
 					else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.6";
 					}
 					else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.7";
 						//特殊项目特殊处理 湖南山建 SFHNSJ130132 手续费比率 hxl
 						if(document.all['ProjectNo'].value == 'SFHNSJ130132'){
 							document.all.PoundageRatio.value="0.23";
 						}
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}
 				}
 				
			}//是否新车
		}else if(productMaker=="山推楚天"){//租赁门店校验
			if(equip_is_new=='否'){//是否新车
				//是否再融租
				if(is_release == '是'){
					if(zlQX>=6 && zlQX<=12){//1年期限
 						document.all.YearRatio.value="7.81";
 					}else if(zlQX>12 && zlQX<=24){
 						document.all.YearRatio.value="7.96";
 					}else{
 						alert("请核实租赁期限是否在6-24个月内！！");
 						//document.all.FinanceTerm.value=24;
 					}
				}else{
					if(firstCostRatio>=20){
	 					if(zlQX>=6 && zlQX<=12){//1年期限
	 						document.all.YearRatio.value="7.81";
	 						document.all.PoundageRatio.value="0.5";
	 					}else if(zlQX>12 && zlQX<=24){
	 						document.all.YearRatio.value="7.96";
	 						document.all.PoundageRatio.value="0.7";
	 					}else{
	 						alert("请核实租赁期限是否在6-24个月内！！");
	 						//document.all.FinanceTerm.value=24;
	 					}
	 				}else{
	 					alert("起租比例不能低于20%！！");
	 					//document.all.HireScale.value=20;
	 				}
				}
			}else{//新车
 				if(firstCostRatio>=30){
 					if(zlQX>=1 && zlQX<12){//1年期限
 						//新增 起租比例>=30% 租赁期限1-12个月年利率6.0%,手续费率0.25%
 						document.all.YearRatio.value="6.0";
 						document.all.PoundageRatio.value="0.25";
 					}else if(zlQX==12){//1年期限
 						document.all.YearRatio.value="6.0";
 						document.all.PoundageRatio.value="0.5";
 					}else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="6.15";//"6.61";09-24
 						document.all.PoundageRatio.value="0.6";
 					}else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="6.15";//"7.11";
 						document.all.PoundageRatio.value="0.7";
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}else if(zlQX>48 && zlQX<=54){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.2";
 					}else if(zlQX>54 && zlQX<=60){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.3";
 					}else if(zlQX>60){
 						alert("融资期限最长60个月!");
 					}
 				}
 				if(firstCostRatio<30){
 					if(zlQX>=1 && zlQX<12){
 						//新增 起租比例<30% 租赁期限1-11个月年利率7.81%,手续费率0.25%
 						document.all.YearRatio.value="7.81";
 						document.all.PoundageRatio.value="0.25";
 					}
 					else if(zlQX==12){
 						document.all.YearRatio.value="7.81";
 						document.all.PoundageRatio.value="0.5";
 					}
 					else if(zlQX>12 && zlQX<=18){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.6";
 					}
 					else if(zlQX>18 && zlQX<=24){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.7";
 					}else if(zlQX>24 && zlQX<=30){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.8";
 					}else if(zlQX>30 && zlQX<=36){
 						document.all.YearRatio.value="7.96";
 						document.all.PoundageRatio.value="0.9";
 					}else if(zlQX>36 && zlQX<=42){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.0";
 					}else if(zlQX>42 && zlQX<=48){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.1";
 					}else if(zlQX>48 && zlQX<=54){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.2";
 					}else if(zlQX>54 && zlQX<=60){
 						document.all.YearRatio.value="8.21";
 						document.all.PoundageRatio.value="1.3";
 					}else if(zlQX>60){
 						alert("融资期限最长60个月!");
 					}
 				}
 				
			}//是否新车
		}
   }
   function changeYearRatio()
   {
	   yearRatioAuto();
	   changeValue();
	   isSavedButtonPressed = false;
   }
   //年利率生成
   function changeValue()
   {
	   //是否是社会产品
	   //var isSocialProduct = document.all.AgentId.value.toLowerCase()==socialProductAgentId;
	   var isSocialProduct = document.all.zzs.value.toLowerCase()==socialProductor;
	   var productMaker   = document.forms[0].zzs.value;
	   var firstCostRatio_temp = document.all.HireScale.value;
	   var firstCostRatio=firstCostRatio_temp?parseFloat(firstCostRatio_temp):0;
	   var zlQX_temp = document.all.FinanceTerm.value;
	   var zlQX=zlQX_temp?parseFloat(zlQX_temp):0;
	 //项目金额
	 var global_projectMoney = document.all.ProjectMoney.value?parseFloat(document.all.ProjectMoney.value):0;
	 //1、起租租金
	 var global_startCostMoney = decimal(global_projectMoney*(firstCostRatio/100),0);
	 document.all.HeadRent.value = global_startCostMoney;
	 /*融资金额*/
	 var global_leaseMoney  = global_projectMoney-global_startCostMoney;
	 document.all.LeaseMoney.value = global_leaseMoney;
	 //2、客户保证金：
	 if(!isSocialProduct)//不是社会产品
	 {
		 var combo_bailMoney_value = getTracywindyObject("id_combo_bailRatio").getRawValue();
		 if(combo_bailMoney_value)
		 {
			 document.all.Bail.value = decimal(global_leaseMoney*((new Number(combo_bailMoney_value))/100),0);
	     }
	 }
     else
     {
    	 document.all.Bail.readOnly= false;
    	 removeClass(document.all.Bail,"td-content-readonly");
    	 document.all.Bail.onchange = changeValue;
     }
	 var global_BailMoney = document.all.Bail.value?parseFloat(document.all.Bail.value):0;
	 //3、第1期租金：
	 var global_FirstTermRentMoney = document.all.FirstTermRent.value?parseFloat(document.all.FirstTermRent.value):0;
     //租赁物明细table
     var productDetailTable = getTracywindyObject('id_table_leasing_product_detail');
     var productDetailTableData = productDetailTable.tableData;
     var productDetailTableDataRowsLen = productDetailTableData.length;
	 //4、保险费：
	 var temp_InsuranceRatio = 0;
	 var combo_insuranceType_value =  getTracywindyObject('id_combo_insuranceType').getRawValue();
	 if(combo_insuranceType_value)
	 {
		 if("租赁"==combo_insuranceType_value)
		 {
			 var combo_insuranceRatio_value = getTracywindyObject('id_combo_insuranceRatio').getRawValue();
			 temp_InsuranceRatio = new Number(combo_insuranceRatio_value);
	     }
     }
	 var temp_otherInsurance = 0;
	 var combo_otherBail_value = getTracywindyObject('id_combo_otherBail').getRawValue();
     if(combo_otherBail_value)
     {
    	 temp_otherInsurance = new Number(combo_otherBail_value);
	 }
	 if(document.all.Insurance.value && (!/^[+]?\d+$/.test(document.all.Insurance.value)))
	 {
		 alert("保险费必须为整数");
    	 return;
     }
	 //5、担保费：
	 var combo_assureType_value = getTracywindyObject('id_combo_assureType').getRawValue();
	 var assureratio_obj = document.all.assureratio;
	 assureratio_obj.readOnly = false;
	 removeClass(assureratio_obj,"td-content-readonly");
	 assureratio_obj.onchange = changeYearRatio;
	 if(combo_assureType_value)
	 {
		 if("租赁公司代办"==combo_assureType_value)
		 {
			 assureratio_obj.value = "1.8";
			 addClass(assureratio_obj,"td-content-readonly");
			 assureratio_obj.onchange = function(){};
			 assureratio_obj.readOnly = true;
	     }
		 else if("免担保"==combo_assureType_value)
		 {
			 assureratio_obj.value = "0";
			 addClass(assureratio_obj,"td-content-readonly");
			 assureratio_obj.onchange = function(){};
			 assureratio_obj.readOnly = true;
	     }
		 else if("第三方法人担保"==combo_assureType_value)
		 {
			 assureratio_obj.value = "0";
			 addClass(assureratio_obj,"td-content-readonly");
			 assureratio_obj.onchange = function(){};
			 assureratio_obj.readOnly = true;
		 }
		 else if("本地担保公司"==combo_assureType_value)
		 {}
		 else if("一单一议"==combo_assureType_value)
		 {}
		 if(document.all.assureratio.value)
		 {
			 document.all.assure.value = decimal(global_leaseMoney*((new Number(assureratio_obj.value))/100),0);
		 }
     }
	 var global_AssureMoney = document.all.assure.value?parseFloat(document.all.assure.value):0;
	//#################保险开始
		var INTN = parseInt(zlQX/12);//期限除以12取整数
		var MODN = zlQX%12;//期限除以12取余数
		//'保险费率P
		var FinanceTerm    = zlQX;
		var InsuranceRatio = temp_InsuranceRatio;//doc_user.InsuranceRatio(0)//保险费率
		var OtherBail = temp_otherInsurance*(productDetailTableDataRowsLen-1);//doc_user.OtherBail(0)//三者险
		var HireScale = firstCostRatio;//doc_user.HireScale(0)//起租比例
		var MOD9 =  parseInt((FinanceTerm%12)/9);//Int((Val(FinanceTerm) Mod 12 )/9)//期限除于12取余再除以9
		var INTN9 =parseInt(FinanceTerm/9);//Int(Cstr(Val(FinanceTerm)/9))//期限除于9取整数
		var BailRatio = assureratio_obj.value ?(new Number(assureratio_obj.value)):0;//doc_user.assureratio(0)//保证金比例
		var Insurance = 0;//定义常量为0
		//global_projectMoney
		if("租赁"!=combo_insuranceType_value)
		{
			Insurance = 0;
		}
		else
		{
			if( FinanceTerm>=12)
			{
				Insurance =decimal(
							(global_projectMoney*(1-Math.pow(0.8,INTN))/INTN  * 5 *InsuranceRatio * 0.01
							+ OtherBail)*( INTN+ MODN/10 - 0.05*( MODN-8)* MOD9) 
							,0);
			}
			else
			{
				Insurance = decimal(
						(global_projectMoney * InsuranceRatio * 0.01 +OtherBail)
						* (FinanceTerm/10-0.05*(FinanceTerm-8)* INTN9)		
			   ,0);
			}
		}
		document.all.Insurance.value = Insurance;
		var global_InsuranceMoney = document.all.Insurance.value?parseFloat(document.all.Insurance.value):0;
	 //#################保险结束
	 if(productDetailTableDataRowsLen>1)
	 {
		 var sumProductPrice_all = doStatisticSumTableData();
		 for(var i=0;i<(productDetailTableDataRowsLen-1);i++)
		 {
			 var currentTableDataRow = productDetailTableData[i];
			 var productPrice =  currentTableDataRow.productPrice;
             if(firstCostRatio)
             {
                 var productDetailLeasingMoney = productPrice*(100-firstCostRatio)/100;
            	 var assureMoney = decimal(productDetailLeasingMoney*((new Number(assureratio_obj.value))/100),0);
            	 currentTableDataRow['assureMoney'] = assureMoney;
             }
             var tempCurrentInsuranceMoney = 0;
        	 tempCurrentInsuranceMoney = decimal(productPrice*global_InsuranceMoney/sumProductPrice_all,0);

             currentTableDataRow['insuranceMoney'] = tempCurrentInsuranceMoney;
	     }
     }
	 //6手续率
	 if(!isSocialProduct)//不是社会产品
	 {
		 var global_poundageRatio = document.all.PoundageRatio.value?parseFloat(document.all.PoundageRatio.value):0;
	     document.all.Poundage.value = decimal(global_projectMoney*(global_poundageRatio/100),0);
	   //特殊项目特殊处理 湖南山建 SFHNSJ130132 手续费1000 hxl
			if(document.all['ProjectNo'].value == 'SFHNSJ130132'){
				document.all.Poundage.value = "1000";
			}
	 }
     else
     {
    	 document.all.Poundage.readOnly= false;
    	 removeClass(document.all.Poundage,"td-content-readonly");
    	 document.all.Poundage.onchange = changeValue;
     }
     var global_poundageMoney = document.all.Poundage.value?parseFloat(document.all.Poundage.value):0;
	 //document.all.Poundage.value = global_poundageMoney;
	 //7、留购价款：
     if(!isSocialProduct)//不是社会产品
     {
    	 //document.all.SupervisionMoney.value = 100;
    	 //change 2012-8-28
    	 //document.all.SupervisionMoney.value = decimal(global_projectMoney*0.0005,0);
     }
     else
     {
    	 document.all.SupervisionMoney.readOnly= false;
    	 removeClass(document.all.SupervisionMoney,"td-content-readonly");
    	 document.all.SupervisionMoney.onchange = changeValue;
     }
	 var global_SupervisionMoney = document.all.SupervisionMoney.value?parseFloat(document.all.SupervisionMoney.value):0;
     //首付款合计
     var global_SumHeadMoney = 0;
         global_SumHeadMoney += global_startCostMoney;
         global_SumHeadMoney += global_BailMoney;
         global_SumHeadMoney += global_InsuranceMoney;
         global_SumHeadMoney += global_AssureMoney;
         global_SumHeadMoney += global_poundageMoney;
         global_SumHeadMoney += global_SupervisionMoney;
     var combo_rentIncomeType_value = getTracywindyObject('id_combo_rentIncomeType').getValue();
     if("期初支付"==combo_rentIncomeType_value)
     {
    	 global_SumHeadMoney += global_FirstTermRentMoney;
     }
     document.all.SumHeadMoney.value = decimal(global_SumHeadMoney,0);
     //8、供应商保证金
     if(!isSocialProduct)//不是社会产品
     {
         var combo_bailRatio2_value = getTracywindyObject("id_combo_bailRatio2").getRawValue();
         if(combo_bailRatio2_value)
         {
        	 document.all.DBBail.value = decimal(global_leaseMoney*((new Number(combo_bailRatio2_value))/100),0);
         }
     }
     else
     {
    	 document.all.DBBail.readOnly= false;
    	 removeClass(document.all.DBBail,"td-content-readonly");
     }
     var global_DBBail = document.all.DBBail.value?parseFloat(document.all.DBBail.value):0;
     //9、管理服务费
     var global_manageMoney = document.all.ManageMoney.value?parseFloat(document.all.ManageMoney.value):0;
     //SFLC费用支付
     var global_otherPayment = document.all.OtherPayment.value?parseFloat(document.all.OtherPayment.value):0;
     //总总额
     var gloabal_allRentMoney = 0;
         gloabal_allRentMoney += global_SumHeadMoney;
         gloabal_allRentMoney += global_DBBail;
         gloabal_allRentMoney += global_manageMoney;
         gloabal_allRentMoney += global_otherPayment;
      //document.all.AllRent.value = decimal(gloabal_allRentMoney,0);
     //更新租赁物明细
     doStatisticSumTableData();
     //10、放款金额
     var equipamt =  document.all.ProjectMoney.value?parseFloat(document.all.ProjectMoney.value):0;;
	  var dbbail = document.all.DBBail.value?parseFloat(document.all.DBBail.value):0;;
	  var first_payment = document.all.SumHeadMoney.value?parseFloat(document.all.SumHeadMoney.value):0;;
	  var loan_way = "";
	  if( document.getElementById('loan_way')){
		  loan_way =  document.all.loan_way.value;
	  }else{
		  loan_way = getTracywindyObject('id_combo_loan_way').getRawValue();
		  
	  }
		if(loan_way=="差额放款" ){
			document.all.fact_money.value=equipamt-dbbail-first_payment;
		}else{
			document.all.fact_money.value=equipamt;
		}
   }
   

function allvalidate(lease){//保存验证全部
    	
	
	var id_combo_productType=getTracywindyObject('id_combo_productType').getRawValue();  //租赁物名称
	var YearRatio=document.all['YearRatio'].value;                                       //年利率
	var FinanceTerm=document.all['FinanceTerm'].value;									 //融资期限(月)

	var ProjectMoney=document.all['ProjectMoney'].value;								 //项目金额
	var HireScale=document.all['HireScale'].value;									 	 //起租比例
	var HeadRent=document.all['HeadRent'].value;									 	 //1、起租租金

	var LeaseMoney=document.all['LeaseMoney'].value;									 //融资金额
	var id_bailRatio=getTracywindyObject('id_combo_bailRatio').getRawValue();			 //客户保证金比例
	var Bail=document.all['Bail'].value;									 			 //2、保证金

	var id_leaseInterval=getTracywindyObject('id_combo_leaseInterval').getRawValue();	//租金间隔
	var FirstRent=document.all['FirstRent'].value;									 	//每期租金
	var FirstTermRent=document.all['FirstTermRent'].value;								//3、第1期租金

	var id_insuranceType=getTracywindyObject('id_combo_insuranceType').getRawValue();	//保险方式
	var id_insuranceRatio=getTracywindyObject('id_combo_insuranceRatio').getRawValue();	//保险费率
	var Insurance=document.all['Insurance'].value;									 	//4、保险费

	var id_assureType=getTracywindyObject('id_combo_assureType').getRawValue();			//担保方式
	var assureratio=document.all['assureratio'].value;									//担保费比例
	var assure=document.all['assure'].value;									 		//5、担保费

	var id_otherBail=getTracywindyObject('id_combo_otherBail').getRawValue();			//三者险年保费
	var PoundageRatio=document.all['PoundageRatio'].value;								//手续费比例
	var Poundage=document.all['Poundage'].value;										//6、手续费
	
	var id_rulePayment=getTracywindyObject('id_combo_rulePayment').getRawValue();		//规则付款
	var DepositDate=document.all['DepositDate'].value;									//首期付款日期
	var SupervisionMoney=document.all['SupervisionMoney'].value;						//7、留购价款
	
	var id_firstSource=getTracywindyObject('id_combo_firstSource').getRawValue();		//首付款来源
	var id_rentPayType=getTracywindyObject('id_combo_rentPayType').getRawValue();			   //租金付款方式
	var SumHeadMoney=document.all['SumHeadMoney'].value;								//首期付款合计
	
	var id_firstPayType=getTracywindyObject('id_combo_firstPayType').getRawValue();		//首期付款方式
	var id_bailRatio2=getTracywindyObject('id_combo_bailRatio2').getRawValue();		        //DB保证金比例
	var DBBail=document.all['DBBail'].value;											//8、供应商保证金
	
	var id_bankType=getTracywindyObject('id_combo_bankType').getRawValue();				//开户银行
	var id_onCard=getTracywindyObject('id_combo_onCard').getRawValue();					//上牌
	var ManageMoney=document.all['ManageMoney'].value;									//9、管理服务费
	
	var AccountName=document.all['AccountName'].value;									 //户名
	var id_infoSub=getTracywindyObject('id_combo_infoSub').getRawValue();				//资料后补
	var OtherPayment=document.all['OtherPayment'].value;								//SFLC费用支付
	
	var Account=document.all['Account'].value;									 		//银行卡号
	var id_isLand=getTracywindyObject('id_combo_isLand').getRawValue();					//有无盗抢险
	var AllRent=document.all['AllRent'].value;											//租金总额
	
	var tmpAccount=document.all['tmpAccount'].value;									//确认账号
	var id_rentSource=getTracywindyObject('id_combo_rentSource').getRawValue();				//还租依赖工程款
	var id_equipmentMonitor=getTracywindyObject('id_combo_equipmentMonitor').getRawValue();	//监控设备
	
	var id_rentIncomeType=getTracywindyObject('id_combo_rentIncomeType').getRawValue();		//租金支付方式
	var id_rentDoInvoiceType=getTracywindyObject('id_combo_rentDoInvoiceType').getRawValue();	//租金开票方式
	//var id_operationType=getTracywindyObject('id_combo_operationType').getRawValue();			//业务模式
	
	var Project =document.all['Project'].value;									 				//工程名称
	var id_projectArea=getTracywindyObject('id_combo_projectArea').getRawValue();				//是否异地施工
	
	
	var id_projectType=getTracywindyObject('id_combo_projectType').getRawValue();				//工程性质
	var ProjectCost =document.all['ProjectCost'].value;										//施工规模(万)
	var ProjectTime =document.all['ProjectTime'].value;										//工程期限(月)
	
	var id_contractProperty=getTracywindyObject('id_combo_contractProperty').getRawValue();			//承包性质
	var MonthIncome =document.all['MonthIncome'].value;										//每月收入(万)
	var YearIncome =document.all['YearIncome'].value;         								//年营业收入(万)
	var MonthDebt=document.all['MonthDebt'].value;  										//每月债务(万)
	var RentPrice=document.all['RentPrice'].value;											//台班费/月(万)
	var IsStrange=document.all['IsStrange'].value;											//是否异地租赁
			
													
	var radios = document.getElementsByName('ConstructionType');							//单选按钮(承包,自用,再租赁)
	
	
	var Require=/.+/;					   //不能为空
	var Number=/^[+]?\d+$/; 			   //验证是否为数字，不要求位数
	var Rate=/^[+]?\d+(\.[0-9]{1,2})?$/;   //利率为正并且小数点后保留2位
	var Money=/^[+]?\d+(\.[0-9]{1,2})?$/;  //金额为正并且小数点后保留2位
	var PMoney=/^[+]?\d+$/;				   //验证金额为正并且取整
	var PRate =/^[+]?\d+$/;  			   //验证利率为正并且取整
	//授信额度相关字段
	 if(!Require.test(id_onCard)){alert("上牌不能为空!");return false;}
	 if(!Require.test(id_equipmentMonitor)){alert("监控设备不能为空!");return false;}
	 //if(!Require.test(HireScale)){alert("起租比例不能为空!");return false;}
	 if(!Require.test(id_combo_productType)){alert("租赁物名称不能为空!");return false;}
	//var LeaseMoney = !document.all['LeaseMoney'].value ? "0" :document.all['LeaseMoney'].value ;
	lease;
	//判断授信额度
	if(id_assureType == "免担保"){
		alert("该项目正在使用的是标准额度（建机额度）！");
		var AgentCreditA = !document.all['AgentCreditA'].value ? "0" :document.all['AgentCreditA'].value ;
		var sumrentD = !document.all['sumrentD'].value ? "0" :document.all['sumrentD'].value ;
		var leasingAllMoney = !document.all['leasingAllMoney'].value ? "0" :document.all['leasingAllMoney'].value ;
		if(parseInt(AgentCreditA)<parseInt(lease)+parseInt(sumrentD)+parseInt(leasingAllMoney)){
		alert("供应商授信标准额度【"+AgentCreditA+"】\n小于融资额【"+lease+"】与标准额度项目的租金余额【"+parseInt(sumrentD)+"+"+parseInt(leasingAllMoney)+"】之和，\n不能提交下一步！");
		return false;
	  }
	}else if(id_assureType == "本地担保公司"||id_assureType == "租赁公司代办"||id_assureType== "第三方法人担保"){
		alert("该项目正在使用的是支持额度！");
		var AgentCreditSustain = !document.all['AgentCreditSustain'].value ? "0" :document.all['AgentCreditSustain'].value ;
		var sumrentD_s = !document.all['sumrentD_s'].value ? "0" :document.all['sumrentD_s'].value ;
		var leasingAllMoney_s = !document.all['leasingAllMoney_s'].value ? "0" :document.all['leasingAllMoney_s'].value ;
		if(parseInt(AgentCreditSustain)<parseInt(lease)+parseInt(sumrentD_s)+parseInt(leasingAllMoney_s)){
			alert("供应商授信支持额度小于融资额与支持额度项目的租金余额之和，不能提交下一步！");
			return false;
		}
	}else if(id_assureType== "一单一议"){
		alert("该项目正在使用的是一单一议额度！");
		var AgentCreditOnetoone = !document.all['AgentCreditOnetoone'].value ? "0" :document.all['AgentCreditOnetoone'].value ;
		var sumrentD_o = !document.all['sumrentD_o'].value ? "0" :document.all['sumrentD_o'].value ;
		var leasingAllMoney_o = !document.all['leasingAllMoney_o'].value ? "0" :document.all['leasingAllMoney_o'].value ;
		if(parseInt(AgentCreditOnetoone)<parseInt(lease)+parseInt(sumrentD_o)+parseInt(leasingAllMoney_o)){
			alert("供应商授信一单一议额度小于融资额与一单一议项目的租金余额之和，不能提交下一步！");
			return false;
		}
	}	
	
	
	
	
	
	// if(!Require.test(id_combo_productType)){alert("租赁物名称不能为空!");return false;}
	 if(!Require.test(DepositDate)){alert("首期付款日期不能为空!");return false;}
	 if(!Rate.test(YearRatio) ){alert("年利率格式不正确!");return false;}
	 if(!Number.test(FinanceTerm)){alert("融资期限格式错误!");return false;}
	// if(!PRate.test(HireScale)){alert("请检查起租比例是否正确!");return false;}
	 //if(!PMoney.test(ProjectMoney)){alert("请检查项目金额是否正确!");	return false;}	 
	 if(document.all['ProjectNo'].value != 'SFHNHZ130010' && document.all['ProjectNo'].value != 'SFXMCL130007'&& document.all['ProjectNo'].value != 'SFAHFD130055'  ){//暂时
		 if(!PRate.test(HireScale)){alert("请检查起租比例是否正确!");return false;}
	 }	 //if(!PMoney.test(HeadRent)){alert("起租租金填写不正确！");return false;}
	 
	 if(!PMoney.test(LeaseMoney)){alert("请检查融资金额是否正确!");return false;}
	 if(!PRate.test(id_bailRatio)){alert("请检查客户保证金比例是否正确");return false;}
	 if(!PMoney.test(Bail)){alert("请检查保证金是否正确!");return false;}
	 
	 if(!Require.test(id_leaseInterval)){alert("融资间隔不能为空!");return false;}
	 //if(!PMoney.test(FirstRent)){alert("请检查每期租金是否正确!");return false;}
	 //if(!PMoney.test(FirstTermRent)){alert("请检查第一期租金是否正确!");return false;}
	 
	 //if(!Require.test(id_insuranceType)){alert("保险方式不能为空!");return false;} 
	 if(Insurance && (!Number.test(Insurance))){alert("请检查保险费是否正确!");return false;}
	 
	 if(!Require.test(id_assureType)){alert("担保方式不能为空!");return false;}
	 //if(!Rate.test(assureratio)){alert("请检查担保费比率是否正确");return false;}
	 if(!PMoney.test(assure)){alert("请检查担保费是否正确!");return false;}
	 
	
	 if(!Rate.test(PoundageRatio)){alert("请检查手续费比率是否正确");return false;}
	 if(!PMoney.test(Poundage)){alert("请检查手续费是否正确!");return false;}
	 
	 if(!Require.test(id_rulePayment)){alert("规则付款不能为空!");return false;}
	 if(!PMoney.test(SupervisionMoney)){alert("请检查留购价款是否正确!");return false;}
	 
	 if(!Require.test(id_firstSource)){alert("首付款来源不能为空!");return false;}
	 if(!Require.test(id_rentPayType)){alert("租金付款方式不能为空!");return false;}
	 if(!PMoney.test(SumHeadMoney)){alert("请检查 首期付款合计是否正确!");return false;}
	 
	 
	 if(!Require.test(id_firstPayType)){alert("首期付款方式不能为空!");return false;}
	 if(!PRate.test(id_bailRatio2)){alert("请检查DB保证金比例是否正确");return false;}
	 //if(!PMoney.test(DBBail)){alert("请检查供应商保证金是否正确!");return false;}
	 
	 var CustomerType = document.all['CustomerType'].value;
	 if(CustomerType=='个人')
	 {
		 if(!Require.test(id_bankType)){alert("开户银行不能为空!");return false;}
	 }
	
	 //if(!PMoney.test(ManageMoney)){alert("请检查管理服务费是否正确!");return false;}
	 
	 if(!Require.test(id_infoSub)){alert("资料后补不能为空!");return false;}
	 if(!Require.test(id_isLand)){alert("有无盗抢险不能为空!");return false;}
	 if(Account!=tmpAccount){alert("两次银行账号不一致 ,请重新输入！");return false;};
	 if(!Require.test(id_rentSource)){alert("还租依赖工程款不能为空!");return false;}
	 
	
	 if(!Require.test(id_rentIncomeType)){alert("租金支付方式不能为空!");return false;}
	 if(!Require.test(id_rentDoInvoiceType)){alert("租金开票方式不能为空!");return false;}
	 //if(!Require.test(id_operationType)){alert("业务模式不能为空!");return false;}
	 
	 
	 if(id_insuranceType=="租赁"){
		 if(!PMoney.test(id_otherBail)){alert("请检查三者险年保费是否正确!");return false;}
		 if(!Rate.test(id_insuranceRatio)){alert("请检查保险费率是否正确");return false;}	
	 }
	 
	 /*for(var index=0;index<radios.length;index++)
		{
			var currentRadio = radios[index];
			if(currentRadio.checked)
			{
				if(currentRadio.value=="承包工程"){
					 if(!Require.test(Project)){alert("工程名称不能为空!");return false;}
					 if(!Require.test(id_projectArea)){alert("是否异地施工不能为空!");return false;}
					 if(!Require.test(id_projectType)){alert("工程性质不能为空!");return false;}
					 if(!Money.test(ProjectCost)){alert("请检查施工规模(万)是否正确!");return false;}
					 if(!Number.test(ProjectTime)){alert("请检查工程期限(月)是否正确!");return false;}
					 if(!Require.test(id_contractProperty)){alert("承包性质不能为空!");return false;}
					 if(!Money.test(MonthIncome)){alert("请检查施工规模(万)是否正确!");return false;}
				 }else if(currentRadio.value=="自用"){
					 if(!Money.test(YearIncome)){alert("请检查年营业收入(万)是否正确!");return false;}
					 if(!Money.test(MonthDebt)){alert("请检查每月债务(万)是否正确!");return false;}
				 }else{
					 if(!Money.test(RentPrice)){alert("请检查年台班费/月(万)是否正确!");return false;}
					 if(!Require.test(IsStrange)){alert("是否异地租赁不能为空!");return false;}
				 }
			}
		}*/
	 //终端用户
	 /*
	 if(document.all['ProjectNo'].value == 'SFSDCD120012' || document.all['ProjectNo'].value == 'SFSDCD120014'){//暂时
		 
	 }else{
		 if(id_onCard.indexOf('抵押挂靠')>-1)
		 {
			 if(document.all['CustomerType'].value == '个人')
			 {
				 alert("个体客户的上牌方式不能选择【 "+id_onCard+" 】");
				 return false;
			 }
			 var terminal_cust = getTracywindyObject('id_combo_terminal_cust').getRawValue();
			 if(!terminal_cust){alert("请选择终端用户");return false;}
			 var TerminalBankType = getTracywindyObject('id_combo_terminalBankType').getRawValue();
			 if(!TerminalBankType){alert("终端客户开户行不能为空");return false;}
			 var TerminalAccountName = document.all['TerminalAccountName'].value;
			 if(!TerminalAccountName){alert("终端客户户名不能为空");return false;}
			 var TerminalAccount     = document.all['TerminalAccount'].value;
			 if(!TerminalAccount){alert("终端客户银行卡号");return false;}
			 var ConfirmTerminalAccount     = document.all['ConfirmTerminalAccount'].value;
			 if(ConfirmTerminalAccount != TerminalAccount){alert("终端客户银行卡号两次输入不一致");return false;}
		 }
	 }*/
	 return true;
}
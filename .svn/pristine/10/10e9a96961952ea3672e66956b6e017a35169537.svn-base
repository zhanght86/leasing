/**
 * 项目方案CLOB字段获取方式
 * @param str  最外层元素ID
 * @return
 *
 * @author King 2014-3-28
 */
function getProjectSchemeCLOB(str) {
	var saveRecord = new Array();
	var GPSFLAG="true";
	$(str + ' [name]').each(
			function() {
				var temp={};
				if($(this).attr("name")!='FYGS'){
					if ($(this).is(":checkbox,:radio")) {
						if ($(this).attr("checked")) {
							temp.KEY_NAME_ZN=$(this).attr("SID");
							temp.KEY_NAME_EN=$(this).attr("name");
							temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
							temp.VALUE_STR=$.trim($(this).val());
							temp.MINVALUE=$(this).attr("minValue");
							temp.COMPUTE=$(this).attr("compute");
							temp.CODE_MONEY=$(this).attr("compute");
							temp.COMPUTEBY=$(this).attr("computeBy");
							temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
							temp.FYGS=$(this).attr("FYGS");
							temp.ROW_NUM=$(this).attr("ROW_NUM");
							temp.DSFS=$(this).attr("DSFS");
							temp.CALCULATE=$(this).attr("CALCULATE");
						}
					} else {
						
						if ($(this).is("select")) {
							temp.KEY_NAME_ZN=$(this).attr("SID");
							temp.KEY_NAME_EN=$(this).attr("name");
							temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
							temp.VALUE_STR=$.trim($(this).find(":selected").val());
							temp.MINVALUE=$(this).attr("minValue");
							temp.COMPUTE=$(this).attr("compute");
							temp.CODE_MONEY=$(this).attr("compute");
							temp.COMPUTEBY=$(this).attr("computeBy");
							temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
							temp.FYGS=$(this).attr("FYGS");
							temp.ROW_NUM=$(this).attr("ROW_NUM");
							temp.DSFS=$(this).attr("DSFS");
							temp.CALCULATE=$(this).attr("CALCULATE");
						} else {
							var name=$(this).attr("name");
							if(name=='STARTING_DATE_PROJECT'){
								temp.KEY_NAME_ZN=$("#"+name).attr("SID");
								temp.KEY_NAME_EN=$(this).attr("name");
								temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
								temp.VALUE_STR=$.trim($(this).val());
								temp.MINVALUE=$(this).attr("minValue");
								temp.COMPUTE=$(this).attr("compute");
								temp.CODE_MONEY=$(this).attr("compute");
								temp.COMPUTEBY=$(this).attr("computeBy");
								temp.ITEM_FLAG=$("#"+name).attr("ITEM_FLAG");
								temp.FYGS=$(this).attr("FYGS");
								temp.ROW_NUM=$("#"+name).attr("ROW_NUM");
								temp.DSFS=$(this).attr("DSFS");
								temp.CALCULATE=$(this).attr("CALCULATE");
							}else if(name=='GPS_MONEY'){
								var money=$.trim($(this).val());
								if(parseFloat(money)>0.000001){
									temp.KEY_NAME_ZN=$(this).attr("SID");
									temp.KEY_NAME_EN=$(this).attr("name");
									temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
									temp.VALUE_STR=$.trim($(this).val());
									temp.MINVALUE=$(this).attr("minValue");
									temp.COMPUTE=$(this).attr("compute");
									temp.CODE_MONEY=$(this).attr("compute");
									temp.COMPUTEBY=$(this).attr("computeBy");
									temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
									temp.FYGS=$(this).attr("FYGS");
									temp.ROW_NUM=$(this).attr("ROW_NUM");
									temp.DSFS=$(this).attr("DSFS");
									temp.CALCULATE=$(this).attr("CALCULATE");
								}else{
									GPSFLAG=false;
								}
								
							}
							//add by lishou 03-30-2016 12W校验获取产品的政策开始时间CLOB  Start
							else if(name=='KSQX_DATE'){
									temp.KEY_NAME_ZN=$(this).attr("SID");
									temp.KEY_NAME_EN=$(this).attr("name");
									temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
									temp.VALUE_STR=$.trim($("#KSQX_DATE").val());
							}
							//add by lishou 03-30-2016 12W校验获取产品的政策开始时间                  End
							else{
								temp.KEY_NAME_ZN=$("#"+name).attr("SID");
								temp.KEY_NAME_EN=$(this).attr("name");
								temp.VALUE_STATUS=$("#"+name).attr("VALUE_STATUS");
								if(name.indexOf("DATE")>-1)
									temp.VALUE_STR=$.trim($("#"+name).datebox('getValue'));
								else
									temp.VALUE_STR=$.trim($("#"+name).val());
								temp.MINVALUE=$(this).attr("minValue");
								temp.COMPUTE=$(this).attr("compute");
								temp.CODE_MONEY=$(this).attr("compute");
								temp.COMPUTEBY=$(this).attr("computeBy");
								temp.ITEM_FLAG=$("#"+name).attr("ITEM_FLAG");
								temp.FYGS=$("#"+name).attr("FYGS");
								temp.ROW_NUM=$("#"+name).attr("ROW_NUM");
								temp.DSFS=$("#"+name).attr("DSFS");
								temp.CALCULATE=$("#"+name).attr("CALCULATE");
							}
						}
					}
					if(GPSFLAG){
						saveRecord.push(temp);
					}else{
						GPSFLAG=true;
					}
					
				}
			});
	
	var divHidden=str+"DivHidden";
	divHidden=divHidden.replace("#","");
	$("div[class='"+divHidden+"'] [name]").each(
			function() {
				var temp={};
				if($(this).attr("name")!='FYGS'){
					if ($(this).is(":checkbox,:radio")) {
						if ($(this).attr("checked")) {
							temp.KEY_NAME_ZN=$(this).attr("SID");
							temp.KEY_NAME_EN=$(this).attr("name");
							temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
							temp.VALUE_STR=$.trim($(this).val());
							temp.MINVALUE=$(this).attr("minValue");
							temp.COMPUTE=$(this).attr("compute");
							temp.CODE_MONEY=$(this).attr("compute");
							temp.COMPUTEBY=$(this).attr("computeBy");
							temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
							temp.FYGS=$(this).attr("FYGS");
							temp.ROW_NUM=$(this).attr("ROW_NUM");
							temp.DSFS=$(this).attr("DSFS");
							temp.CALCULATE=$(this).attr("CALCULATE");
						}
					} else {
						
						if ($(this).is("select")) {
							temp.KEY_NAME_ZN=$(this).attr("SID");
							temp.KEY_NAME_EN=$(this).attr("name");
							temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
							temp.VALUE_STR=$.trim($(this).find(":selected").val());
							temp.MINVALUE=$(this).attr("minValue");
							temp.COMPUTE=$(this).attr("compute");
							temp.CODE_MONEY=$(this).attr("compute");
							temp.COMPUTEBY=$(this).attr("computeBy");
							temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
							temp.FYGS=$(this).attr("FYGS");
							temp.ROW_NUM=$(this).attr("ROW_NUM");
							temp.DSFS=$(this).attr("DSFS");
							temp.CALCULATE=$(this).attr("CALCULATE");
						} else {
							var name=$(this).attr("name");
							if(name=='STARTING_DATE_PROJECT'){
								temp.KEY_NAME_ZN=$("#"+name).attr("SID");
								temp.KEY_NAME_EN=$(this).attr("name");
								temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
								temp.VALUE_STR=$.trim($(this).val());
								temp.MINVALUE=$(this).attr("minValue");
								temp.COMPUTE=$(this).attr("compute");
								temp.CODE_MONEY=$(this).attr("compute");
								temp.COMPUTEBY=$(this).attr("computeBy");
								temp.ITEM_FLAG=$("#"+name).attr("ITEM_FLAG");
								temp.FYGS=$(this).attr("FYGS");
								temp.ROW_NUM=$("#"+name).attr("ROW_NUM");
								temp.DSFS=$(this).attr("DSFS");
								temp.CALCULATE=$(this).attr("CALCULATE");
							}else if(name=='GPS_MONEY'){
								var money=$.trim($(this).val());
								if(parseFloat(money)>0.000001){
									temp.KEY_NAME_ZN=$(this).attr("SID");
									temp.KEY_NAME_EN=$(this).attr("name");
									temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
									temp.VALUE_STR=$.trim($(this).val());
									temp.MINVALUE=$(this).attr("minValue");
									temp.COMPUTE=$(this).attr("compute");
									temp.CODE_MONEY=$(this).attr("compute");
									temp.COMPUTEBY=$(this).attr("computeBy");
									temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
									temp.FYGS=$(this).attr("FYGS");
									temp.ROW_NUM=$(this).attr("ROW_NUM");
									temp.DSFS=$(this).attr("DSFS");
									temp.CALCULATE=$(this).attr("CALCULATE");
								}else{
									GPSFLAG=false;
								}
								
							}else{
								temp.KEY_NAME_ZN=$(this).attr("SID");
								temp.KEY_NAME_EN=$(this).attr("name");
								temp.VALUE_STATUS=$(this).attr("VALUE_STATUS");
								temp.VALUE_STR=$.trim($(this).val());
								temp.MINVALUE=$(this).attr("minValue");
								temp.COMPUTE=$(this).attr("compute");
								temp.CODE_MONEY=$(this).attr("compute");
								temp.COMPUTEBY=$(this).attr("computeBy");
								temp.ITEM_FLAG=$(this).attr("ITEM_FLAG");
								temp.FYGS=$(this).attr("FYGS");
								temp.ROW_NUM=$(this).attr("ROW_NUM");
								temp.DSFS=$(this).attr("DSFS");
								temp.CALCULATE=$(this).attr("CALCULATE");
							}
						}
					}
					if(GPSFLAG){
						saveRecord.push(temp);
					}else{
						GPSFLAG=true;
					}
					
				}
			});
	
	return JSON.stringify(saveRecord);
}

/**
 * 获取项目方案字段
 * @param str 方案外层id
 * @return
 *
 * @author King 2014-3-28
 */
function getProjectSchemeBaseInfo(str){
	var projectScheme={};
	var test1 = $('#lease_term').val()-1;
	var last_zj = $('.datagrid-body [field="zj"]:eq('+test1+')').text();
	projectScheme.MONTH_RENT=last_zj;//月供
	projectScheme.SCHEME_CODE=$("#SCHEME_CODE").val(); //方案编号
	projectScheme.LEASE_TERM=$("#lease_term").val();//租赁期限
	projectScheme.LEASE_PERIOD=$("#lease_period").val();//租赁周期
	projectScheme.LEASE_TOPRIC=$("#LEASE_TOPRIC").val();//设备总价值
	projectScheme.START_PERCENT=$("#START_PERCENT").val();//起租比例
	projectScheme.FINANCE_TOPRIC=$("#FINANCE_TOPRIC").val();//融资额
	projectScheme.PAY_WAY=$("#PAY_WAY").val(); //还款政策  等额 不等额
	projectScheme.MANAGEMENT_FEETYPE=$("#MANAGEMENT_FEETYPE").val();//手续费收取方式 
	projectScheme.MANAGEMENT_FEEDSTYPE=$("#MANAGEMENT_FEEDSTYPE").val();//手续费代收方式
	projectScheme.CALCULATE=$("#CALCULATE_TYPE").val();//计算方式  pmt
	projectScheme.INTERNAL_RATE=$("#INTERNAL_RATE").val();//收益率
	projectScheme.POUNDAGE_WAY=$("#POUNDAGE_WAY").val();//保证金抵充方式
	projectScheme.YEAR_TYPE =$("#YEAR_TYPE").val();//固定年利率
	projectScheme.YEAR_INTEREST=parseFloat($("#YEAR_INTEREST").val())/100;//年利率
	projectScheme.DAY_PUACCRATE=$("#DAY_PUACCRATE").val();//日罚息率
	projectScheme.BAIL_PERCENT=$("#DEPOSIT_PERCENT").val();//保证金比例
	projectScheme.POUNDAGE_RATE=$("#fees").val();//手续费比例
	projectScheme.POUNDAGE_PRICE=$("#FEES_PRICE").val();//手续费金额
	projectScheme.DEAL_MODE=$("#DEAL_MODE").val();//租赁期满处理方式(数据字典CODE)
	projectScheme.COMPANY_PERCENT =$("#COMPANY_PERCENT").val();//厂商保证金
	projectScheme.TOTAL_MONTH_PRICE=$("#MONEYALL").val();//租金合计
	projectScheme.FIRSTPAYALL=$("#FIRSTPAYALL").val();//首期款合计
	projectScheme.PROJECT_ID=$("#PROJECT_ID").val();//项目ID
	projectScheme.SCHEME_ROW_NUM=$("#SCHEME_ROW_NUM").val();//设备标示
	projectScheme.FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');//首期款约定付款日期
	projectScheme.INSURANCE_PERCENT=$("#INSURANCE_PERCENT").val();//保险费比例
	projectScheme.LXTQSQ=$("#LXTQSQ").val();//利息提前收取方式
	projectScheme.DAY_PUACCRATE_TYPE=$("#DAY_PUACCRATE_TYPE").val();//罚息计算方式
	projectScheme.GDLX=$("#GDLX").val();//固定利息
	projectScheme.DISCOUNT_MONEY=$("#DISCOUNT_MONEY").val();//贴息金额
	projectScheme.DISCOUNT_TYPE=$("#DISCOUNT_TYPE").val();//贴息方式
	projectScheme.DB_BAIL_PERCENT=$("#DB_BAIL_PERCENT").val();//DB保证金比例
	projectScheme.INSURANCE_MONEY=$("#INSURANCE_MONEY").val();//保险费
	projectScheme.CARDWAY=$("#CARDWAY").val();//上牌方式
	projectScheme.FIRST_MONEY=$("#START_MONEY").val();//首期租金
	projectScheme.LAST_MONEY=$("#LAST_MONEY").val();//尾款
	projectScheme.SFFPQZ=$("#SFFPQZ").val();//是否分批起租
	projectScheme.ZBGS_ID=$("#ZBGS_ID").val();//指标公司ID
	projectScheme.SFBZHT=$("#SFBZHT").val();//是否标准合同
	projectScheme.COOPERATION_AGENCY=$("#COOPERATION_AGENCY").val();//指标公司ID
	projectScheme.COOPERATION_AGENCY=$("#COOPERATION_AGENCY").val();//指标公司ID
	projectScheme.KSQX_DATE=$("#KSQX_DATE").val();//产品启用日期  add by lishuo 03-30-2016
    /*2016年4月13日 17:40:23 捷众新算法 添加新字段 吴国伟 start*/
	projectScheme.P2P_FINANCING = $("#P2P_FINANCING").val();
	projectScheme.P2P_SERVICE_FEE = $("#P2P_SERVICE_FEE").val();
	/*-----------------------------------------------end*/
	projectScheme.SCHEME_CLOB=getProjectSchemeCLOB(str);
	return JSON.stringify(projectScheme);
}
/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function tofindData() {
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	var CLIENT_CARD = $("input[name='CLIENT_CARD']").val();
	var TYPE = $("select[name='TYPE']").attr("selected",true).val();
	var CUST_ID = $("input[name='CUST_ID']").val();
	var CREATE_TIME1 = $("input[name='CREATE_TIME1']").val();
	var CREATE_TIME2 = $("input[name='CREATE_TIME2']").val();
	$('#customersTab').datagrid('load', {
		"CLIENT_NAME" : CLIENT_NAME,
		"CLIENT_CARD" : CLIENT_CARD,
		"TYPE" : TYPE,
		"CUST_ID" : CUST_ID,
		"CREATE_TIME1" : CREATE_TIME1,
		"CREATE_TIME2" : CREATE_TIME2
	});
}

/**
 * 客户管理-操作
 * @param val
 * @param row
 * @return
 */
function getValue(val, row) {
	var JUDGE_GUARANTOR = row.JUDGE_GUARANTOR;
//	var addProject=$("#addProject").val();
//	if(addProject=="true"){
		if(JUDGE_GUARANTOR == '0'){
			return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewCust("
			+ row.CLIENT_ID
			+ ",\""
			+row.NAME
			+ "\",\""
			+ row.TYPE
			+ "\")'>查看</a>  |  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toUpdateCust("
			+ row.CLIENT_ID
			+ ",\""
			+row.NAME
			+ "\",\""
			+ row.TYPE
			+ "\")'>修改</a>"
			//+"  | <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='findche("
			//+ row.CLIENT_ID
			//+ ",\""
			//+row.NAME
			//+ "\",\""
			//+ row.TYPE
			//+ "\")'>贷款信息</a>"
			+ "  | <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='doDelCust("
			+ row.CLIENT_ID + ",\""
			+ row.TYPE+"\""+")'>删除</a>   |  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toProjectCreatd("
			+row.CLIENT_ID
			+",\""
			+row.TYPE
			+"\"" 
			+",\""
			+row.NAME
			+"\""
			+",\""
			+row.SUPP_ID
			+"\")'>立项送审</a> ";
			//+"| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='tozonghefenxi("+ row.CLIENT_ID+")'>综合分析标签页</a>"
			//+"| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toAddFinance("+ row.CLIENT_ID+")'>法人财报</a>" 
			//+"| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toShowFinance("+ row.CLIENT_ID+")'>财报查看</a>";
		}else{
			return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewCust(" + row.CLIENT_ID + ",\"" + row.TYPE + "\")'>查看</a>";
		}
//	}else{
//		if(JUDGE_GUARANTOR == '0'){
//			return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewCust("
//			+ row.CLIENT_ID
//			+ ",\""
//			+row.NAME
//			+ "\",\""
//			+ row.TYPE
//			+ "\")'>查看</a>  |  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='toUpdateCust("
//			+ row.CLIENT_ID
//			+ ",\""
//			+row.NAME
//			+ "\",\""
//			+ row.TYPE
//			+ "\")'>  修改</a>  |  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='doDelCust("
//			+ row.CLIENT_ID + ")'>删除</a>";
//		}else{
//			return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewCust(" + row.CLIENT_ID + ",\"" + row.TYPE + "\")'>查看</a>";
//		}
//	}
}

/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$("#CREATE_TIME1").datebox('clear');
	$("#CREATE_TIME2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 进入客户添加[页面]
 * 
 * @return
 */
function toAddCustInfo() {
	top.addTab("添加客户明细", _basePath + "/customers/Customers!toAddCust.action?CUST_TYPE=NP");
}

function getCustLeverAll(){
	window.location.href=_basePath+"/customers/Customers!getCustLeverAll.action";
}

/**
 * 查看客户详细[页面]
 * @author yx
 * @date 2013-09-02
 * @return
 */
function toViewCust(value,NAME, type) {
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&NAME=" + NAME+ "&TYPE=" + type + "&tab=view"+"&date="+new Date().getTime());

}

/**
 * 修改客户信息[页面]
 * @author 杨雪
 * @date 2013-09-02
 * @return
 */
function toUpdateCust(value,NAME, type) {
	top.addTab("修改客户信息", _basePath
			+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="
			+ value + "&NAME=" + NAME+"&TYPE=" + type + "&tab=update"+"&date="+new Date().getTime());
}

/**
 * 测试-法人财务报表
 * @param cust_id
 * @return
 */
//http://localhost:8080/sflc-project/customers/FinancialStatistics.action?CUST_ID=165172368
function toAddFinance(cust_id){
	top.addTab("法人财报", _basePath+"/customers/FinancialStatistics.action?CUST_ID="+ cust_id +"&date="+new Date().getTime());
}

/**
 * 测试-综合分析
 * @param cust_id
 * @return
 */
function tozonghefenxi(cust_id){
	top.addTab("综合分析", _basePath+"/analysisBySynthesis/AnalysisBySynthesis.action?CUST_ID="+ cust_id +"&date="+new Date().getTime());
}

/**
 * 测试-法人财务报表查看
 * @param cust_id
 * @return
 */
function toShowFinance(cust_id){
	top.addTab("财报查看", _basePath+"/customers/FinancialStatistics!toMgAnalazy.action?CLIENT_ID="+ cust_id +"&date="+new Date().getTime());
}

/**
 * 删除客户信息[操作]
 * @author 杨雪
 * @date 2013-08-30
 * @return
 */
function doDelCust(CLIENT_ID ,TYPE) {
	
	if (confirm("确认删除？")) {
		jQuery.ajax({
			url : _basePath + "/customers/Customers!checedCustToPro.action",
			data :{
				CLIENT_ID:CLIENT_ID,
				TYPE:TYPE
			} ,
			dataType:'json',
			success:function(data){
				if(data.flag==true){
					return alert("该客户已有项目，不可删除");
				}else{
					jQuery.ajax( {
						url : _basePath + "/customers/Customers!doDetCustInfo.action",
						data : {
							CLIENT_ID:CLIENT_ID,
							TYPE:TYPE
						} ,
						dataType : "json",
						success : function(data) {
							if (data.flag == true) {
								alert("删除客户成功");
								window.location = _basePath
										+ "/customers/Customers!findCustomersPage.action";
							} else {
								alert("删除客户失败");
								window.location = _basePath
										+ "/customers/Customers!findCustomersPage.action";
							}
						},
						error : function(e) {
							alert(e.message);
						}
					});
				}
			}
		});
	}
}

/**
 * 企业关联
 * 
 * @return
 */
function findMgRelation() {
	var row = $('#customersTab').datagrid('getSelected');
	if (row) {
		if (row.TYPE == "LP") {
			top.addTab(
					"企业关联信息",
					_basePath
							+ "/customers/CustMainRelation!findMgRelation.action?CLIENT_ID="
							+ row.CLIENT_ID + "&TYPE=" + row.TYPE
							+ "&tab=enterprise"+"&date="+new Date());
		} else {
			return alert("请选择法人客户查看企业关联信息");
		}
	}
}

/**
 * 从业历程
 * @yx
 * @date 2013-09-11
 * @return
 */
function findMgCustWorkExp() {
	var row = $('#customersTab').datagrid('getSelected');
	if (row) {
		top.addTab(
				"客户从业历程",
				_basePath
						+ "/customers/CustMainRelation!findMgCustWorkExp.action?CLIENT_ID="
						+ row.CLIENT_ID + "&TYPE=" + row.TYPE+"&date="+new Date().getTime());
	}
}

/**
 * 承租人族谱
 * @yx
 * @date 2013-09-11
 * @return
 */
function toMgZupu() {
	var row = $('#customersTab').datagrid('getSelected');
	if (row) {
		top.addTab("族谱", _basePath
				+ "/customers/Customers!getFamilyTree.action?CLIENT_ID="
				+ row.CLIENT_ID + "&TYPE=" + row.TYPE+"&date="+new Date().getTime());
	}
}
function findche(value,NAME, type) {
	top.addTab("查看车贷明细", _basePath
			+ "/customers/Customers!findche.action?CLIENT_ID=" + value
			+ "&NAME=" + NAME+ "&TYPE=" + type + "&tab=view"+"&date="+new Date().getTime());

}
function showScore(TYPE,CLIENT_ID,INDUSTRY_FICATION){
	$.ajax({
		dataType:'json',
		url:_basePath + '/customers/Customers!doGetId.action?MAIN_TYPE=1&CLIENT_ID='+CLIENT_ID,
		success:function(result){
		  if(result.flag == true){
			var ID = result.data.ID;
			top.addTab("打分",_basePath
					+"/customers/Customers!doGradeScore.action?MAIN_ID="
					+CLIENT_ID+"&INDUSTRY_TYPE="+INDUSTRY_FICATION
					+"&MAIN_TYPE=1&TYPE="+TYPE+"&ID="+ID);
		  }else{
			  alert(result.msg);
		  }
	    }
	});
}


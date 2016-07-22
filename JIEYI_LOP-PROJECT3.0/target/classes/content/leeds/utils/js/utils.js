/**
 * 依赖，jQuery、easyui。
 * 
 * @type
 */
leeds = {};
/**
 * 用于，列表datagrid，删除某条记录。 id是该记录的ID。。 url是删除操作的url。
 * tableId是datagrid的id。。用于删除该记录后，刷新列表 cbFn是可选项。是删除操作成功后，回调函数。可传入函数名（无参），或使用匿名函数
 * 
 * @param {}
 *            id
 */
leeds.del = function(id, url, tableId, cbFn) {
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
				if (r) {
					$.ajax({
								url : url,
								data : 'ID=' + id,
								dataType : 'json',
								success : function(res) {
									if(res.data.flag==undefined || res.data.flag==true) {
										leeds.msg('删除成功！');
										$("#" + tableId).datagrid('reload');
										if (cbFn != undefined) cbFn();
									} else if(res.data.flag==false){
										leeds.msg(res.data.msg);
									}
								}
							});
				}
			});
}
/**
 * 用于，加载数据到表单
 * 
 * @param {}
 *            id 是该表单所存储记录的id
 * @param {}
 *            url 加载所需url
 * @param {}
 *            formId 表单id
 */
leeds.loadToForm = function(id, url, formId) {
	$.ajax({
				url : url,
				data : 'ID=' + id,
				dataType : 'json',
				success : function(res) {
					$('#' + formId).form('load', res.data);
				}
			});
}
/**
 * 返回一个用于easyui，生成dialog的，配置项options。 此处的option，用于新建、更新
 * <br>
 * form内的hidden域，默认关闭弹窗时，不进行重置。若需要重置，请在该hidden域，
 * 加isReset属性。
 * @param {}
 *            width 可选。默认400
 * @param {}
 *            divId dialog的div id
 * @param {}
 *            formId 弹窗内，主form的id
 * @param {}
 *            fnSave 保存按钮的handler
 * @return {}
 */
leeds.dialogInitOp = function(divId, formId, fnSave, width, opts) {
	var options = {
		title : '保存',
		iconCls : 'icon-save',
		top : opts ? (opts.top || 100) : 100,
		width : width || 400,
		closed : true,
		cache : false,
		modal : true,
		onOpen : function() {
		},
		onClose : function() {
			$("#" + formId + " *[name]").attr('readonly', false);
			$("#" + formId + " *[name]").attr('disabled', false);
			$("#" + formId + " *[name]").attr('checked', false);
			$("#" + formId + " .easyui-datebox").datebox({readonly: false});
			$("#" + formId + " .easyui-numberbox").attr('readonly', false);
			$("#" + formId).form("reset");
			$('#' + formId + ' :hidden[isReset]').val('');
			$('#' + divId).dialog(leeds.dialogInitOp(divId, formId, fnSave,
					width, opts));
		},
		buttons : [{
					text : '保存',
					handler : function() {
						if($('#'+formId).form('validate'))
							fnSave();
						else leeds.msg('请正确填写表单！');
					}
				}, {
					text : '关闭',
					handler : function() {
						$("#" + divId).dialog("close");
					}
				}]
	};
	return options;
}
/**
 * 返回一个用于easyui，生成dialog的，配置项options。 此处的option，用于查看
 * 
 * @param {}
 *            width 默认400
 * @param {}
 *            divId dialog 的div id
 * @param {}
 *            formId 弹窗内，主form的id
 * @return {}
 */
leeds.dialogViewOp = function(divId, formId, width) {
	var options = {
		title : '查看',
		iconCls : 'icon-search',
		top : 100,
		width : width || 400,
		closed : true,
		cache : false,
		modal : true,
		onOpen : function() {
			$("#" + formId + " *[name]").attr('readonly', true);
			$("#" + formId + " select[name]").attr('disabled', true);
			$("#" + formId + " .easyui-datebox").datebox({readonly: true});
			$("#" + formId + " .easyui-numberbox").attr('readonly', true);
		},
		buttons : [{
					text : '关闭',
					handler : function() {
						$("#" + divId).dialog("close");
					}
				}]
	};
	return options;
}

//---以上方法，deprecated。推荐使用下面的包，来替代。。以后所有方法，都建立在包之中----------
//---------------------------------------------------begin
/**
 * datagrid顶级包
 * @type 
 */
leeds.datagrid = {
}
/**
 * 普通datagrid---快速开发一个列表
 * @type 
 */
leeds.datagrid.NormalGrid = {
	defauts: {
		url:''
	},
	init: function(tableId, options) {
		
	}
}
/**
 * 可展开的datagrid
 * @type 
 */
leeds.datagrid.DetailGrid = {
	tableId: 'mainList',//默认tableId
	expandRow: undefined,
	isExpand: false,
	clickCell: function(rowIndex, field, value) {
		var that = leeds.datagrid.DetailGrid;
		function expandRow(_rowIndex) {
			$('#'+that.tableId).datagrid('expandRow', _rowIndex);
			that.expandRow = _rowIndex;//当前正在展开的列的index
			that.isExpand = true;
		}
		function collapseRow(_rowIndex) {
			$('#'+that.tableId).datagrid('collapseRow', _rowIndex);
			that.isExpand = false;
		}
		
		if(field != 'ID') {//对“操作”列无效
			if(that.isExpand) {//当有列打开时
				if(that.expandRow==rowIndex)//若所开列为当前列，则关闭
					collapseRow(rowIndex);
				else {//否则，关闭所开列，然后打开当前列
					collapseRow(that.expandRow);
					expandRow(rowIndex);
				}
			} else {//当无列打开时，直接开启当前列
				expandRow(rowIndex);
			}
		}
	}
}
/**
 * 可编辑的datagrid
 * @type 
 */
leeds.datagrid.EditGrid = {
	
}
//---------------------------------------------------end
/**
 * 提供一个查询表单的id，获取，该表单中查询域的值，构成一个Object返回
 * 
 * @param {}
 *            formId
 * @return {}
 */
leeds.getFormParams = function(formId) {
	var params = {};
	$('#' + formId).find("input[name], select, textarea").each(function() {
				params[$(this).attr('name')] = $(this).val();
			});
	return params;
}

//-------------------------------------------------------------------

/**
 * 常用右下角提示框。。简写形式。
 * 
 * @param {}
 *            msg
 */
leeds.msg = function(msg) {
	$.messager.show({
				title : '提示',
				msg : msg
			});
}
/**
 * 
 * @param {} msg 必选
 * @param {} info 可选。info可选值，同easyui alert
 * @param {} fn 可选。点击确定之后的回调函数
 */
leeds.alert = function(msg, info, fn) {
	var info1 = '';
	var fn1 = function(){};
	if(typeof(arguments[1]) == 'string') {
		info1 = arguments[1];
	}else if(typeof(arguments[1]) == 'function') {
		fn1 = arguments[1];
	}
	if(typeof(arguments[2]) == 'function') {
		fn1 = arguments[2];
	}
	$.messager.alert('提示', msg, info1, fn1);
}

//------------------------------------------

/**
 * 提供服务器上一个文件的路径，下载该文件
 * 
 * @param {}
 *            path
 */
leeds.downloadFile = function(path) {
	window.location.href = _basePath
			+ "/customer/Customer!download.action?FILE_PATH=" + path;
}
//----------------------------------------------------------------------
/**
 * 导入模块
 * @type 
 */
leeds.importer = {
	/**
	 * tplName://模板名称
	 *	backUrl: //导入完毕，跳转的页面
	 *	processor: //解析单条数据的方法名---ImportController中
	 * @type 
	 */
	tplMaps: [
		{tplName: '员工档案', backUrl: '/staff/Staff.action', processor: 'parseStaff'},
		{tplName: '客户基本信息', backUrl: '/customer/Customer.action', processor: 'parseCustBasic'},
		{tplName: '担保人', backUrl: '/customer/Customer.action', processor: 'parseCustGua'},
		{tplName: '还款账号', backUrl: '/customer/Customer.action', processor: 'parseCustAcct'},
		{tplName: '法人客户联系方式', backUrl: '/customer/Customer.action', processor: 'parseCustLegalCont'},
		{tplName: '法人担保人联系方式', backUrl: '/customer/Customer.action', processor: 'parseCustLegalGuaCont'},
		{tplName: '客户财产记录', backUrl: '/customer/Customer.action', processor: 'parseCustAsset'},
		{tplName: '客户催收记录', backUrl: '/collection/Collection.action', processor: 'parseCustCollRecd'},
		{tplName: '客户档案', backUrl: '/customer/Customer.action', processor: 'parseCustBasic,parseCustGua,parseCustAcct,parseCustLegalCont,parseCustLegalGuaCont,parseCustAsset,parseCustCollRecd'},
		{tplName: '还款计划', backUrl: '/payplan/Payplan.action', processor: 'parsePayplan'},
		{tplName: '还款计划明细', backUrl: '/payplan/Payplan.action', processor: 'parsePayDetail'},
		{tplName: '核销记录', backUrl: '/collection/Collection.action', processor: 'parsePayHx'},
		{tplName: '履约计划明细', backUrl: '/fundmatch/Fundmatch.action', processor: 'parseLvDetail'},
		{tplName: '履约记录', backUrl: '/fundmatch/Fundmatch.action', processor: 'parseLvHx'},
		{tplName: '设备信息', backUrl: '/payplan/Payplan.action', processor: 'parseEquip'},
		{tplName: 'gps信息', backUrl: '/payplan/Payplan.action', processor: 'parseEquipGps'},
		{tplName: '投保信息', backUrl: '/payplan/Payplan.action', processor: 'parseEquipInsure'},
		{tplName: '设备监控记录', backUrl: '/equipMoni/EquipMoni.action', processor: 'parseEquipMoniRecord'},
		{tplName: '还款计划信息', backUrl: '/payplan/Payplan.action', processor: 'parsePayplan,parsePayDetail,parsePayHx,parseLvDetail,parseLvHx,parseEquip,parseEquipGps,parseEquipInsure,parseEquipMoniRecord'},
		{tplName: '旧机基本信息', backUrl: '/oldVehicle/OldVehicle.action', processor: 'parseOldVehicle'},
		{tplName: '过程记录', backUrl: '/oldVehicle/OldVehicle.action', processor: 'parseOldVehiRecd'},
		{tplName: '旧机管理', backUrl: '/oldVehicle/OldVehicle.action', processor: 'parseOldVehicle,parseOldVehiRecd'}
	],
	tplMap: {},
	isPreview: false,
	init: function(tplName, isPreview) {
		this.isPreview = isPreview;
		$.each(leeds.importer.tplMaps, function(i, n) {//在$.each中，this指向发生改变
			if(n.tplName == $.trim(tplName)) {
				leeds.importer.tplMap = n;
				return false;
			}
		});
		$('body').append('<div id="importDiv" ></div>');
		$('#importDiv').dialog({
			title : '导入',
			iconCls : 'icon-save',
			top : 100,
			width : 350,
			closed : true,
			cache : false,
			modal : true,
			content : '<form id="importForm" enctype="multipart/form-data" method="post">'
					+ '<table width="100%"  class="table_01" border="0">'
					+ '<tr>'
					+ '<td style="text-align:left">导入文件：'
					+ '<input type="file" name="FILE" />'
					+ '</td>'
					+ '</tr>'
					+ '</table>' + '</form>',
			toolbar : [{
						text : '下载导入模板',
						iconCls : 'icon-help',
						handler : function() {
							var path = leeds.importer.tplMap.tplName + ".xls";
							leeds.downloadFile(path);
						}
					}],
			buttons : [{
						text : leeds.importer.isPreview?'导入预览':'导入',
						handler : function() {
							if (!$('#importForm input[name=FILE]').val()) {
								leeds.msg('请选择文件！');
								return;
							}
							$('#importDiv').dialog('close');
							if(leeds.importer.isPreview) {
								$("#importForm").attr('action', _basePath+'/channel/utils/Common!importPreview.action');
								$("#importForm").append('<input type="hidden" name="backUrl" value="'+leeds.importer.tplMap.backUrl+'"/>');
								$("#importForm").append('<input type="hidden" name="processor" value="'+leeds.importer.tplMap.processor+'"/>');
								$("#importForm").submit();
							} else {
								$("#importForm").form('submit', {
									url: _basePath+'/channel/utils/Common!doImport.action',
									onSubmit: function(param) {
										param.processor = leeds.importer.tplMap.processor;
										$('body').mask('正在导入，请稍候...');
									},
									success: function(res) {
										$('body').unmask();
										var res = eval('(' + res + ')');
										if(res.flag) {
											leeds.alert('成功导入【'+res.data+'】条记录！', function() {
												window.location.href = _basePath
													+ leeds.importer.tplMap.backUrl;
											});
										} else {
											leeds.alert('导入失败！', function() {
												window.location.href = _basePath
													+ leeds.importer.tplMap.backUrl;
											});
										}
									}
									
								});
							}
						}
					}, {
						text : '关闭',
						handler : function() {
							$("#importDiv").dialog("close");
						}
					}]
		});
	},
	invoke: function() {
		$('#importDiv').dialog('open');
	}
}

/**
 * 千分符，格式化金额。可以控制小数位数。<br>
 * n默认是2
 * @param {} s 原始数字
 * @param {} n 小数位数
 * @return {Number}
 */
leeds.fmoney = function(s, n) {
   if(!s) return 0;
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;   
}

//重写easyui-validatebox，验证规则-------------------------------------begin 
$.extend($.fn.validatebox.defaults.rules, {    
    contact:{//联系方式    
        validator: function(value,param){
        	var regExp = /^1[358]\d{9}$|^(0\d{2,3}-)?\d{7,8}$|^\(0\d{2,3}\)\d{7,8}$/g;
        	return regExp.test(value);
        },    
        message: '请输入正确的电话格式'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    idCard:{//身份证   
        validator: function(value,param){
        	var regExp = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/g;
        	return regExp.test(value);
        },    
        message: '请输入正确的身份证格式'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {    
    bankCard:{//银行卡号
        validator: function(value,param){
        	var regExp = /^\d{16,20}$/g;
        	return regExp.test(value);
        },    
        message: '请输入正确的银行卡号格式'   
    }    
});
$.extend($.fn.validatebox.defaults.rules, {
	/**
	 * 检查编号唯一性。
	 * 1、需要validator传递两个参数，一个是type，指定编号的类型。
	 * 		一个是所在form的选择器。<br>
	 * 2、要求，所在form需有一个name="ID"的隐藏域。并且，当前表单保存后，要将id值传递给该隐藏域
	 * 
	 * @type 
	 */
    chkRepeat:{
        validator: function(value,param){
        	var that = this;
        	var flag1 = false;
        	var p1 = {code: value, type: param[0]};
        	var id = $(param[1]).find('input[name=ID]').val();
        	if(id) {
        		p1.ID = id;
        	}
        	$.ajax({
        		url: _basePath+'/channel/utils/Common!chkRepeat.action',
        		data: p1,
        		dataType: 'json', 
        		async: false,
        		success: function(res) {
        			if(!res.flag)  {
        				leeds.msg('唯一性检查出错，请联系管理员！');
        				return;
        			}
        			if(res.data.flag!=undefined && res.data.flag==false) {//false表示重复
        				flag1 = false;
        				that.message = res.data.msg;
        			} else {
        				flag1 = true;
        			}
        			
        		}
        	});
        	return flag1;
        },    
        message: ''   
    }    
});
/**
 * 依赖easyui-validatebox，对一个值进行验证。
 * @param {} value 待验证参数
 * @param {} type 是数组，可以不传（此时为非空验证），
 * 可以传多个validType字符串构成的数组（注意required如果有，则必须在第一位，用于验证既非空又要符合其他条件的情况），
 * 		验证的时候，会按照传递的先后顺序，依次验证。
 * @return {} 返回一个Object。。有flag、msg两个属性。当flag为false是表示验证失败，msg为验证失败时的提醒消息
 */
leeds.validate = function(value, type) {
	var res = {flag: true};
	if(!value) {//当参数为空
		if(!type || type[0]=='required') {
			res.flag = false;
			res.msg = '请输入必输项';
			return res;
		}
	} else {
		var i = 0;
		if(type[0]=='required') {
			i = 1;
		}
		for(; i<type.length; i++) {
			var validator = $.fn.validatebox.defaults.rules[type[i]];
			if(!validator.validator(value)) {
				res.flag = false;
				res.msg = validator.message;
				return res;
			}
		}
	}
	return res;
}

/**
 * 提供一个formId，对form中的field进行验证
 * 要求：待验证的field， 添加一个valids属性。规则同leeds.validate。多个规则用逗号隔开
 * @param {String} formId
 * @return 返回一个Object。flag表示是否验证通过。msg，如果验证失败，返回的失败消息
 */
leeds.validForm = function(formId) {
	var res = {flag: true};
	var validFields = $('#'+formId+' input[name][valids], #'+formId+' textarea[name][valids]');
	if(!validFields || validFields.size()==0) return res;
	else {
		validFields.each(function(i){
			var validTypes = $(this).attr('valids').toString().replace(/\s+/g,'').split(',');
			var res1 = leeds.validate($(this).val(), validTypes);
			if(!res1.flag) {
				res = res1;
				return false;
			}
		});
		return res;
	}
}

//重写easyui-validatebox，验证规则-------------------------------------end

//日期计算-----------------------------------------------------begin
/**
 * 日期运算器
 * @type 
 */
leeds.dater = {
	/**
	 * 可传入String，格式为yyyy-MM-dd， 返回值为str。<br>
	 * 也可传入date对象，则返回date对象。<br>
	 * 经过计算后，返回值精确到天。<br>
	 */
	addMonth : function(d, n) {
		function f1(d1, n1) {
			var month = d1.getMonth();
			return new Date(d1.getFullYear(), month+n1, d1.getDate());
		}
		if(d instanceof Date) {
			return f1(d,n);
		} else {
			var d2 = this.toDate(d);
			return this.format(f1(d2, n), 'yyyy-MM-dd');
		}
	},
	/**
	 * 日期格式化:
	 * 格式 YYYY/yyyy/YY/yy 表示年份
	 * MM/M 月份 
	 * W/w 星期  
	 * dd/DD/d/D 日期  
	 * hh/HH/h/H 时间  
	 * mm/m 分钟  
	 * ss/SS/s/S 秒  
	 * @param {} date 传入待格式化的日期对象
	 * @param {} formatStr 传入格式。。默认为'yyyy-MM-dd'
	 * @return {} 返回格式化后的字符串
	 */
	format: function(date, formatStr) {
		if(!formatStr) formatStr = 'yyyy-MM-dd';
		return date.Format(formatStr);
	},
	/**
	 * 字符串转成日期类型
	 * @param {} dateStr 格式YYYY-MM-dd 、YYYY/MM/dd、MM/dd/YYYY、 MM-dd-YYYY  
	 * @return {}
	 */
	toDate: function(dateStr) {
		var converted = Date.parse(dateStr);  
		var myDate = new Date(converted);  
		if (isNaN(myDate))  
		{   
		    var arys= dateStr.split('-');  
		    myDate = new Date(arys[0],--arys[1],arys[2]);  
		}  
		return myDate; 
	}
}
Date.prototype.Format = function(formatStr)   
{   
    var str = formatStr;   
    var Week = ['日','一','二','三','四','五','六'];  
  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
  
    str=str.replace(/MM/,this.getMonth()+1>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,this.getMonth()+1);   
  
    str=str.replace(/w|W/g,Week[this.getDay()]);   
  
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
  
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
  
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
  
    return str;   
}

//日期计算-----------------------------------------------------begin

//选择员工模块-------------------------------------------------begin
/**
 * 选择员工，公共模块
 * @type 
 */
leeds.selectStaff = {
	fieldIds: [],
	/**
	 * 选择员工模块，引擎初始化。<br>
	 * 注意：选择员工后，程序会默认在所绑定引擎的输入框后的一个直接同辈节点
	 * （通常设为XX_ID的隐藏域）中，设其value值为所选员工的ID。
	 * 若无此节点，则无法接收返回的员工ID
	 * @param {Array} fieldIds 要绑定引擎的输入框的id构成的数组
	 */
	init: function(fieldIds, isBind) {
		this.fieldIds = fieldIds;
		$('body').append('' +
			'<div id="parentDiv">' +
			'<form id="pdForm">' +
			'<table width="100%" class="table_01" border="0">' +
			'	<tr>' +
			'		<td style="text-align:left">姓名：' +
			'			<input type="text" name="NAME" style="width:100px"/>' +
			'			<a id="ssBtn" href="#" onclick="leeds.selectStaff.queryParent()">查询</a>' +
			'		</td>' +
			'	</tr>' +
			'</table>' +
			'</form>' +
			'<table id="pdTable"></table>' +
			'</div>');
		$('#ssBtn').linkbutton({
			iconCls: 'icon-search'
		});
		$('#parentDiv').dialog({//初始化弹窗
			top : 50,
			width : 450,
			closed : true,
			cache : false,
			modal : true,
			onClose : function() {
				$("#pdForm").form("clear");
			}
		});
		if(isBind) this.bindEngine();
	},
	bindEngine: function() {
		$.each(this.fieldIds, function(i,n) {
				$('#'+n).unbind('focus');//先卸载可能已经的绑定
				$('#'+n).bind('focus', function(){//闭包
					f1(n);
				});
			});
		function f1(fieldId) {
			$("#parentDiv").dialog({
				title : '选择员工',
				iconCls : 'icon-search',
				buttons : [{
					text : '确定',
					handler : function() {
						var row = $("#pdTable").datagrid('getSelected');
						$('#'+fieldId).val(row.NAME);
						$('#'+fieldId).next().val(row.ID);
						$("#parentDiv").dialog("close");
					}
				}, {
					text : '关闭',
					handler : function() {
						$("#parentDiv").dialog("close");
					}
				}]
			});
			$("#parentDiv").dialog("open");
			$('#pdTable').datagrid({// 员工列表
				url : _basePath + '/staff/Staff!mainPage.action',
				queryParams: {},
				height: 320,
				columns : [[{
							field : 'ID',
							title : '-',
							width : 100,
							align : 'center',
							formatter : function(value, row, index) {
								return '<input type="radio" name="r1"/>';
							}
						}, {
							field : 'NAME',
							title : '姓名',
							width : 100,
							align : 'center'
						}, {
							field : 'JOB',
							title : '岗位',
							width : 100,
							align : 'center'
						}]],
				fitColumns : true,
				striped : true,
				pagination : true,
				rownumbers : true,
				pageSize : 10,
				singleSelect : true,
				onSelect : function(rowIndex, rowData) {
					$('#parentDiv input[name=r1]:eq(' + rowIndex + ')').attr('checked',
							true);
				}
			});
		}
	},
	unbindEngine:function() {
		$.each(this.fieldIds, function(i,n) {
			$('#'+n).unbind('focus');
		});
	},
	queryParent: function() {
		var params = {};
		$("#pdForm input").each(function() {
					if ($.trim($(this).val()))
						params[$(this).attr('name')] = $(this).val();
				});
		$("#pdTable").datagrid('load', params);
	}
}
//选择员工模块-------------------------------------------------end

/**
 * 页面，响应键盘操作，控制器
 * @type 
 */
leeds.keyer = {
	/**
	 * 在当前window下，点击enter键的响应操作。
	 * @param {} fn 为响应函数。也可传入匿名函数
	 */
	winEnter: function(fn) {
		this.focusEnter(window, fn);
	},
	/**
	 * 当eleSle选择的元素获得焦点时，点击enter键的响应操作
	 * @param {String} eleSle 选择器
	 * @param {function} fn 
	 */
	focusEnter: function(eleSle,fn) {
		$(eleSle).keydown(function(event){
		  if(event.which==13) {
		  	event.preventDefault();
		  	fn();
		  }
		});
	}
}

//--------------
/**
 * 带查询功能的复选框.
 * @type 
 */
leeds.selecter = {
	inputId: '',
	/**
	 * 定义预置的，查询类型
	 * @type 
	 */
	defaults: {
		AREA:{//区域。AREA是查询类型
			url: _basePath+'/basicSet/Area!queryAreasForCombo.action',
			multiple: true
		},
		staff:{//员工
			url: _basePath+'/staff/Staff!queryStaffsForCombo.action',
			multiple: false
		}
	},
	/**
	 * 1、使用时，需要在页面，相应位置，加入《input type="text" id=""/》<br>
	 * 		1.1、当有多个绑定框时，需要同时指定sType属性（指定查询类型）、id。默认只有id时，sType值为id<br>
	 * 		1.2、当初始化时（调用init时），input有值value，则会尝试设值。<br>
	 * 2、默认情况下，执行完毕后，会在input所在form中生成：name为type、type_ID的隐藏域。
	 * 		值分别是所选项的name、id构成的字符串（如果多选，则是用逗号隔开的字符串）<br>
	 * 3、options，配置项：<br>
	 * 		3.4、【注意】其他所有可以设置到combogrid上的配置项，都可以在此设置<br>
	 * 		3.1、要求：url必须是由ReplyAjaxPage返回的，结果包含ID、NAME的action方法<br>
	 * 		3.2、fn(nameStr, idStr)包含两个参数。分别是2中所述的字符串。<br>
	 * 		3.3、nameField/idField，用于覆盖2中所述隐藏域默认的name<br>
	 * 4、加载值的时候，调用$('#'+inputId).combogrid('setValues', nameStr.split('、'));//其中nameStr即2中所述字符串
	 * 
	 * @param {} inputId 绑定下拉框input的id。
	 * @param {} options 可选配置项。设置后，会覆盖默认值。<br>
	 * 	
	 */
	init: function(inputId, options) {
		//查询类型
		var type = $('#'+inputId).attr('sType') ? $('#'+inputId).attr('sType') : inputId;
		if(!this.defaults[type]) return;
		this.inputId = inputId;
		var opts = {};
		$.extend(opts, $.fn.combo.defaults, this.defaults[type], options||{});//合并配置到opts中
		
		var nameTitle = opts.multiple ? '全选' : '';
		var nameField = opts.nameField || type;
		var idField = opts.idField || type+'_ID';
    	$('#'+inputId).parents('form')
    		.append("<input type='hidden' name='"+nameField+"'/>")
    		.append("<input type='hidden' name='"+idField+"'/>");
    	
		$('#'+inputId).combogrid({    
		    delay: 500,    
	    	mode: 'remote',
	    	multiple: opts.multiple,
	    	separator: '、',
		    idField:'NAME',
		    height: opts.height,
		    textField:'NAME',
		    panelHeight: opts.panelHeight,
		    url: opts.url,    
		    columns:[[
		        {field:'ID',checkbox:true},
		        {field:'NAME',title:'<font style="font-weight:bold">'+nameTitle+'</font>',width:80}
		    ]],
		    onSelect: function(rowIndex, rowData) {
	    		var nameStr = $('#'+inputId).combogrid('getText');
	    		$('input[name='+nameField+']:hidden').val(nameStr);
	    		var idStr = $('input[name='+idField+']:hidden').val();
	    		if(opts.multiple) idStr = idStr ? idStr+','+rowData.ID : rowData.ID;
	    		else idStr= rowData.ID;
		    	$('input[name='+idField+']:hidden').val(idStr);
		    	
		    	if(opts.fn&&typeof(opts.fn)=='function') opts.fn(nameStr, idStr);//回调函数
		    }
		});
		//绑定
		$('#'+inputId).next('span').bind('click', function(){
			$('#'+inputId).combogrid('showPanel');
			var g = $('#'+inputId).combogrid('grid');
			g.datagrid('scrollTo', g.datagrid('getRowIndex', g.datagrid('getSelected')));
		});
		//初始化设值
		if($('#'+inputId).val()) $('#'+inputId).combogrid('setValues', $('#'+inputId).val().split('、'));
	},
	initValues: function(nameStr) {
		if(nameStr) $('#'+inputId).combogrid('setValues', nameStr.split('、'));
	}
}


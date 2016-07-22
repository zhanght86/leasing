$(function(){
	$('#pageTable').datagrid({
		  view: detailview,		  
			fit : true,
			fitColumns:false,
         columns:[[
				  {field:'PROJECT_ID',align:'center',width:30,checkbox:true},
                  {field:'STATUS',align:'center',width:33,title:'预警',formatter:function (value,rowData,rowIndex){
                	  if(rowData.GDTX=='Y'){
                		if(rowData.DAYTYPE=='W'){
                			if((value==undefined||value=='0')&& (rowData.A_DATE > rowData.TIXING)){
                				return "<div class='icon-red'>&nbsp;</div>";
                			}else{
                				return "<div class='icon-green'>&nbsp;</div>";
                			}
                		}else if(rowData.DAYTYPE=='N'){
                			if((value==undefined||value=='0')&& (rowData.B_DATE > rowData.TIXING)){
                				return "<div class='icon-red'>&nbsp;</div>";
                			}else{
                				return "<div class='icon-green'>&nbsp;</div>";
                			}
                		}
                	  }else{
                		  return "<div class='icon-green'>&nbsp;</div>";
                	  }
                	  
                	  
//        				else if(rowData.PORTFOLIO_NUMBER==''||rowData.PORTFOLIO_NUMBER==null||rowData.CABINET_NUMBER==''||rowData.CABINET_NUMBER==null){
//        					return "<div class='icon-yellow'>&nbsp;</div>";
//        				}
//        				else{
//        					return "<div class='icon-green'>&nbsp;</div>";
//        				}
        			}},
        			{field:'main_compure',align:'center',title: '操作',width:230,formatter:function(value,rowData,rowIndex){
  		        	  return "<a href='javascript:void(0);' onclick=toPigeonholeApplyForm('"+rowData.INDUSTRY_TYPE+"','"+rowData.PROJECT_ID+"','"+rowData.CLIENT_CODE+"','"+rowData.LEASE_CODE+"','"+rowData.CLIENT_NAME+"','"+rowData.PAY_CODE+"','"+rowData.PLATFORM_TYPE1+"')>归档申请</a>&nbsp;|&nbsp;"
  		        	  //+"<a href='javascript:void(0);' onclick=toShow('"+rowData.A+"')>归档申请查看</a>&nbsp;|&nbsp;"
  		        	  //+"<a href='javascript:void(0);' onclick=toUpdate('"+rowData.A+"')>档案入柜</a>&nbsp;|&nbsp;</br>"
  		        	  +"<a href='javascript:void(0);' onclick=toBorrowAppForm('"+rowData.LEASE_CODE+"','"+rowData.CLIENT_NAME+"','"+rowData.CABINET_NUMBER+"','"+rowData.A+"')>借阅申请</a>&nbsp;|&nbsp;"
  		        	  +"<a href='javascript:void(0);' onclick=toTransferAppForm('"+rowData.LEASE_CODE+"','"+rowData.CLIENT_NAME+"','"+rowData.CABINET_NUMBER+"','"+rowData.A+"')>移交申请</a>";  
  		          }
  		          },
		        //  {field:'LEASE_CODE',hidden:true,align:'left',width:200,title:'融资租赁合同号',align:'center'},
  		          {field:'STATUS1',align:'left',width:100,title:'归档状态',align:'center'},
  		          {field:'PAY_CODE',align:'center',width:200,title:'支付表编号'},
		          {field:'CLIENT_NAME',align:'left',width:100,title:'客户名称',align:'center'},
		          {field:'PLATFORM_TYPE',align:'left',width:100,title:'业务类型',align:'center'},
		          {field:'INDUSTRY_TYPE',align:'left',width:100,title:'事业部',align:'center'},
		          {field:'AREA_NAME',align:'center',width:100,title:'省公司'},
	              {field:'PAYMENT_DATE',align:'center',width:100,title:'放款时间'},
                  {field:'PAYMENT_STATUS',align:'center',width:80,title:'放款状态'},
 		          {field:'PAY_STATUS',align:'center',width:100,title:'支付表状态'},
 		          {field:'START_DATE',align:'center',width:100,title:'起租日期'},
		          {field:'PORTFOLIO_NUMBER',align:'left',width:100,title:'档案袋编号',align:'center'},
		          {field:'CABINET_NUMBER',align:'left',width:100,title:'档案柜编号',align:'center'}
		          ]],
		          
        detailFormatter:function(index,row){
        		return '<table class="table_02" cellpadding="0" cellspacing="0" id="ddv-' + index + '" style="padding:5px;"></table>';
        },
        
        //展开层
        onExpandRow: function(index,row){
        	
        	jQuery.ajax({
        		url:_basePath+"/documentApp/ApplyDossier!toShowDossierApp.action?LEASE_CODE="+row.LEASE_CODE+"&CLIENT_NAME="+row.CLIENT_NAME+"&PORTFOLIO_NUMBER="+row.PORTFOLIO_NUMBER
        		+"&CABINET_NUMBER="+row.CABINET_NUMBER+"&DOSSIER_APPLY_ID="+row.A+"&PAYLIST_CODE="+row.PAYLIST_CODE+"&PROJECT_ID="+row.PROJECT_ID
        		+"&PAYMENT_DATE="+row.PAYMENT_DATE+"&PAYMENT_STATUS="+row.PAYMENT_STATUS+"&PAY_STATUS="+row.PAY_STATUS+"&START_DATE="+row.START_DATE,  
				type:'post',
				dataType:'json',
			    success: function(json){
				var data = {flag:json.flag,total:json.data.length,rows:json.data};
				var pRowIndex = "ddv-"+index;
				$('#ddv-'+index).datagrid({
					fitColumns:true,
                    singleSelect:true,
					rownumbers:true,
                    loadMsg:'加载中...',
                    height:'auto',
                    columns:[[                     
                            {field:'FILF_FLAG',align:'center',width:40,title:'权证类型'},
                            {field:'FILE_NAME',align:'center',width:50,title:'权证名称'},
                            {field:'DOSSIER_TEMP',align:'center',width:45,title:'原件/复印件',formatter:function(value,row,index){
                            	if(value=="1"){
                            		return "原件";
                            	}else {
                            		return "复印件";
                            	}
                            }},                   
                            {field:'FENSHU',align:'center',width:30,title:'要求份数'},
                            {field:'DOSSIER_COUNT',align:'center',width:30,title:'存档份数'},         
          		            {field:'IS_RETURN',align:'center',width:60,title:'是否退回',formatter:function(value,row,index){
                            	if(value=="1"){
                            		return "退回";
                            	}else {
                            		return "未退回";
                            	}
                            }},
          		            {field:'FLAG',align:'center',width:60,title:'错误原因'},
                            {field:'DOSSIER_NUMBER',align:'center',width:50,title:'借出份数'},
                            {field:'DOSSIER_TEMP_B',align:'center',width:50,title:'借出文件原/附'},
                            {field:'TRANSFER_NUMBER',align:'center',width:50,title:'移交份数'}
                     ]],
                     
                     onResize:function(){
                        $('#pageTable').datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(){
                        setTimeout(function(){
                            $('#pageTable').datagrid('fixDetailRowHeight',index);
                        },0);
                    }
				});

                $('#pageTable').datagrid('fixDetailRowHeight',index);
				$('#ddv-'+index).datagrid("loadData",data);
			    }});
        }
        });
});

//按钮-查询
function se(){
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm")});
}
	
//按钮--清空
function clean(){
		$("#CLIENT_NAME").val('');
		$("#PORTFOLIO_NUMBER").val('');
		$("#CABINET_NUMBER").val('');
		$("#PLATFORM_TYPE_NAME").val('');
		$("#BUSINESS_PLATE").val('');
		$("#LEASE_CODE").val('');
		$("#INDUSTRY_TYPE").val('');
		$("#PAYMENT_STATUS").val('');
		$("#PAYMENT_DATE1").datebox('setValue',''); 
		$("#PAYMENT_DATE2").datebox('setValue',''); 
		$("#START_DATE1").datebox('setValue','');
		$("#START_DATE2").datebox('setValue','');
		$("#AREA_NAME").val('');
		$("#STATUS1").val('');
}

//单个项目归档-进入申请页面
function toPigeonholeApplyForm(INDUSTRY_TYPE,PROJECT_ID,CLIENT_ID,LEASE_CODE,CLIENT_NAME,PAY_CODE,PLATFORM_TYPE){
	top.addTab(CLIENT_NAME+"归档申请", _basePath+"/documentApp/ApplyDossier!toAddpDossierApp.action?INDUSTRY_TYPE="+INDUSTRY_TYPE+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&LEASE_CODE="+LEASE_CODE+"&PAY_CODE="+PAY_CODE+"&CLIENT_NAME="+CLIENT_NAME+"&PLATFORM_TYPE="+PLATFORM_TYPE);
}

//单个项目移交'"+rowData.LEASE_CODE+"','"+rowData.CLIENT_NAME+"','"+rowData.CABINET_NUMBER+"','"+rowData.A+"'
function toTransferAppForm(LEASE_CODE ,CLIENT_NAME,CABINET_NUMBER,DOSSIER_APPLY_ID){
	if(CABINET_NUMBER!=undefined){
		top.addTab(CLIENT_NAME+"档案移交", _basePath+"/documentApp/ApplyTransfer!toTransferAppForm.action?CLIENT_NAME="+CLIENT_NAME+"&CABINET_NUMBER="+CABINET_NUMBER+"&LEASE_CODE="+LEASE_CODE+"&DOSSIER_APPLY_ID="+DOSSIER_APPLY_ID);
	}
}

//单个项目借阅
function toBorrowAppForm(LEASE_CODE,CLIENT_NAME,CABINET_NUMBER,DOSSIER_APPLY_ID){
	top.addTab(CLIENT_NAME+"档案借阅",_basePath+"/documentApp/DossierBorrow!toBorrowAppForm.action?LEASE_CODE="+LEASE_CODE+"&CLIENT_NAME="+CLIENT_NAME+"&DOSSIER_APPLY_ID="+DOSSIER_APPLY_ID);
}

//归档申请查看
function toShow(apply_id){
	top.addTab("归档申请查看", _basePath+"/documentApp/ApplyDossier!toShowAppDossier.action?DOSSIER_APPLY_ID="+apply_id);
}

//修改档案
function toUpdate(apply_id){
	top.addTab("档案入柜", _basePath+"/documentApp/ApplyDossier!toUpdateDossierCabinet.action?DOSSIER_APPLY_ID="+apply_id);
}

/**
 * 多项目申请归档.
 * @returns
 */
function toAppDossier(){
	
	if(toGetData().length==0){
		return $.messager.alert('警告','请填选择归档申请数据');
	}else{
		var dataList_ = [];
		var dataList = $("#pageTable").datagrid('getSelections');//获取选中信息
		var industery = "";
		for(var i = 0; i<dataList.length; i++){
			if(i==0){
				industery = dataList[i].INDUSTRY_TYPE;//事业部
			}else {
				if(industery != dataList[i].INDUSTRY_TYPE){
					return alert("请选择相同事业部的项目");
				}
				industery = dataList[i].INDUSTRY_TYPE;
			}
			
			
			var temp = {};
			temp.PROJECT_ID = dataList[i].PROJECT_ID;//项目id
			temp.LEASE_CODE = dataList[i].LEASE_CODE;//合同编号
			temp.CLIENT_NAME = dataList[i].CLIENT_NAME;//客户名称
			temp.CLIENT_CODE = dataList[i].CLIENT_CODE;//客户名称
			temp.PLATFORM_TYPE = dataList[i].PLATFORM_TYPE;//业务类型
			temp.PAY_CODE = dataList[i].PAY_CODE;//支付表编号
			dataList_.push(temp);
			
		}
		
		var dataList_ = {dataList:dataList_};
		top.addTab("批量归档申请", _basePath+"/documentApp/ApplyDossier!toAddpDossierAppLot.action?dataList="+encodeURI(JSON.stringify(dataList_)));
	}
}


/**
 * 多项目申请借阅.
 */
function toAppBorrow(){
	if(toGetData().length==0){
		return $.messager.alert('警告','请填选择借阅申请数据');
	}else{
		var data = toGetData();
		var jsonstr = JSON.stringify(data);
		console.debug(jsonstr);
		console.debug(JSON.parse(jsonstr));
		top.addTab("批量借阅申请",_basePath+"/documentApp/DossierBorrow.action?dataList="+encodeURIComponent(JSON.stringify(data)));
	}
}

/**
 * 多项目申请移交.
 */
function toAppTransfer(){
	if(toGetData().length==0){
		return $.messager.alert('警告','请填选择移交申请数据');
	}else{
		var data = toGetData();
		var dataList_ = {dataList:data};
		top.addTab("批量移交申请",_basePath+"/documentApp/ApplyTransfer.action?dataList="+encodeURIComponent(JSON.stringify(dataList_)));
	}
}


/**
 * 档案管理-申请归档, 申请借阅, 移交共同方法,
 *  判断是否选中项目
 * @return
 */
function toGetData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.PROJECT_ID = detailData[i].PROJECT_ID;//项目id
		temp.LEASE_CODE = detailData[i].LEASE_CODE;//合  同编号
		temp.CLIENT_NAME = detailData[i].CLIENT_NAME;//客户名称
		temp.CLIENT_CODE = detailData[i].CLIENT_CODE;//客户名称
		temp.PLATFORM_TYPE = detailData[i].PLATFORM_TYPE;//业务类型
		temp.PAY_CODE = detailData[i].PAY_CODE;//支付表编号
		temp.DOSSIER_APPLY_ID = detailData[i].A;
		getDetailData.push(temp);
	}
	
	return getDetailData;
}

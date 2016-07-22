function newOne(){
	top.addTab("添加金融机构", _basePath+"/base/financingInstitution/FinancingInstitution!addSupPage.action");
}

function update(row){
	if (row){
		top.addTab("金融机构修改",_basePath+"/base/financingInstitution/FinancingInstitution!modifySupPage.action?SUP_ID="+row);
    }
}


/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildArea(val,childId){
	
	jQuery.ajax({
		url:_basePath+"/base/suppliers/Suppliers!getAddress.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				//将本行的输入框初始化
				$("#"+childId).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.address.length;i++){
					$("#"+childId).append($("<option value='"+json.address[i].ID+"'>"+json.address[i].NAME+"</option>"));				
				}
		}
	});
}

/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildArea2(val,childId1,childId2){
	
	jQuery.ajax({
		url:_basePath+"/base/suppliers/Suppliers!getAddress.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				
				//将本行的输入框初始化
				$("#"+childId1).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				//将本行的输入框初始化
				$("#"+childId2).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.address.length;i++){
					$("#"+childId1).append($("<option value='"+json.address[i].ID+"'>"+json.address[i].NAME+"</option>"));				
				}
		}
	});
}
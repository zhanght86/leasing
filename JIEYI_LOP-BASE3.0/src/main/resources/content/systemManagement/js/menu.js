

/*
function reload(){
	var node = $('#tt2').tree('getSelected');
	if (node){
		$('#tt2').tree('reload', node.target);
	} else {
		$('#tt2').tree('reload');
	}
}
function getChildren(){
	var node = $('#tt2').tree('getSelected');
	if (node){
		var children = $('#tt2').tree('getChildren', node.target);
	} else {
		var children = $('#tt2').tree('getChildren');
	}
	var s = '';
	for(var i=0; i<children.length; i++){
		s += children[i].text + ',';
	}
	alert(s);
}
function getChecked(){
	var nodes = $('#tt2').tree('getChecked');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].text;
	}
	alert(s);
}
function getSelected(){
	var node = $('#tt2').tree('getSelected');
	alert(node.text);
}
function collapse(){
	var node = $('#tt2').tree('getSelected');
	$('#tt2').tree('collapse',node.target);
}
function expand(){
	var node = $('#tt2').tree('getSelected');
	$('#tt2').tree('expand',node.target);
}
function collapseAll(){
	var node = $('#tt2').tree('getSelected');
	if (node){
		$('#tt2').tree('collapseAll', node.target);
	} else {
		$('#tt2').tree('collapseAll');
	}
}
function expandAll(){
	var node = $('#tt2').tree('getSelected');
	if (node){
		$('#tt2').tree('expandAll', node.target);
	} else {
		$('#tt2').tree('expandAll');
	}
}
function append(){
	var node = $('#tt2').tree('getSelected');
	$('#tt2').tree('append',{
		parent: (node?node.target:null),
		data:[{
			text:'new1',
			checked:true
		},{
			text:'new2',
			state:'closed',
			children:[{
				text:'subnew1'
			},{
				text:'subnew2'
			}]
		}]
	});
}
function remove(){
	var node = $('#tt2').tree('getSelected');
	$('#tt2').tree('remove', node.target);
}
function update(){
	var node = $('#tt2').tree('getSelected');
	if (node){
		node.text = '<span style="font-weight:bold">new text</span>';
		node.iconCls = 'icon-save';
		$('#tt2').tree('update', node);
	}
}
function isLeaf(){
	var node = $('#tt2').tree('getSelected');
	var b = $('#tt2').tree('isLeaf', node.target);
	alert(b);
}*/

 var draged=false;
 tdiv=null;
function dragStart(){
 ao=event.srcElement;
 if((ao.tagName=="TD")||(ao.tagName=="TR"))ao=ao.offsetParent;
 else return;
 draged=true;
 tdiv=document.createElement("div");
 tdiv.innerHTML=ao.outerHTML;
 tdiv.style.display="block";
 tdiv.style.position="absolute";
 tdiv.style.filter="alpha(opacity=70)";
 tdiv.style.cursor="move";
 tdiv.style.width=ao.offsetWidth;
 tdiv.style.height=ao.offsetHeight;
 tdiv.style.top=getInfo(ao).top;
 tdiv.style.left=getInfo(ao).left;
 document.body.appendChild(tdiv);
 lastX=event.clientX;
 lastY=event.clientY;
 lastLeft=tdiv.style.left;
 lastTop=tdiv.style.top;
 try{
  ao.dragDrop(); 
 }catch(e){}
}
function draging(){//重要:判断MOUSE的位置
 if(!draged)return;
 var tX=event.clientX;
 var tY=event.clientY;
 tdiv.style.left=parseInt(lastLeft)+tX-lastX;
 tdiv.style.top=parseInt(lastTop)+tY-lastY;
 for(var i=0;i<parentTable.cells.length;i++){
  var parentCell=getInfo(parentTable.cells[i]);
  if(tX>=parentCell.left&&tX<=parentCell.right&&tY>=parentCell.top&&tY<=parentCell.bottom){
   var subTables=parentTable.cells[i].getElementsByTagName("table");
   if(subTables.length==0){
    if(tX>=parentCell.left&&tX<=parentCell.right&&tY>=parentCell.top&&tY<=parentCell.bottom){
     parentTable.cells[i].appendChild(ao);
    }
    break;
   }
   for(var j=0;j<subTables.length;j++){
    var subTable=getInfo(subTables[j]);
    if(tX>=subTable.left&&tX<=subTable.right&&tY>=subTable.top&&tY<=subTable.bottom){
     parentTable.cells[i].insertBefore(ao,subTables[j]);
     break;
    }else{
     parentTable.cells[i].appendChild(ao);
    } 
   }
  }
 }
}

function dragEnd(){
 if(!draged)return;
 draged=false;
 mm=ff(150,15);
}
function getInfo(o){//取得坐标
 var to=new Object();
 to.left=to.right=to.top=to.bottom=0;
 var twidth=o.offsetWidth;
 var theight=o.offsetHeight;
 while(o!=document.body){
  to.left+=o.offsetLeft;
  to.top+=o.offsetTop;
  o=o.offsetParent;
 }
  to.right=to.left+twidth;
  to.bottom=to.top+theight;
 return to;
}
function ff(aa,ab){//从GOOGLE网站来,用于恢复位置
 var ac=parseInt(getInfo(tdiv).left);
 var ad=parseInt(getInfo(tdiv).top);
 var ae=(ac-getInfo(ao).left)/ab;
 var af=(ad-getInfo(ao).top)/ab;
 return setInterval(function(){if(ab<1){
       clearInterval(mm);
       tdiv.removeNode(true);
       ao=null;
       return
      }
     ab--;
     ac-=ae;
     ad-=af;
     tdiv.style.left=parseInt(ac)+"px";
     tdiv.style.top=parseInt(ad)+"px"
    }
,aa/ab)
}
function inint(){//初始化
 for(var i=0;i<parentTable.cells.length;i++){
  var subTables=parentTable.cells[i].getElementsByTagName("table");
  for(var j=0;j<subTables.length;j++){
   if(subTables[j].className!="dragTable")break;
   subTables[j].rows[0].className="dragTR";
   subTables[j].rows[0].attachEvent("onmousedown",dragStart);
   subTables[j].attachEvent("ondrag",draging);
   subTables[j].attachEvent("ondragend",dragEnd);
  }
 }
}
inint();
function showImg(path,FILE_ID,PROJECT_ID){
	window.open(_basePath+"/project/ProjectContractManager!showPicture.action?path="+path+"&FILE_ID="+FILE_ID+"&PROJECT_ID="+PROJECT_ID);
//	top.addTab("图片查看"+FILE_ID,_basePath+"/project/ProjectContractManager!showPicture.action?path="+path+"&FILE_ID="+FILE_ID+"&PROJECT_ID="+PROJECT_ID);
}

function showImgPath(path){
	top.addTab("图片查看"+Math.round(Math.random()*100),_basePath+"/project/ProjectContractManager!showContractPicturePath.action?path="+path);
}

function showImg1(FILE_ID){
	top.addTab("图片查看"+FILE_ID,_basePath+"/project/ProjectContractManager!showContractPicture.action?FILE_ID="+FILE_ID);
}
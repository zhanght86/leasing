var app= angular.module('app',[]);
var __baseApi = document.getElementById("ctx").value;
__baseApi += '/telephonenumberlist/TelephoneNumberList!';
var projectId = document.getElementById("projectId").value;
var name = document.getElementById("name").value;
var idCardNum = document.getElementById("idCardNum").value;
var cellPhone = document.getElementById("cellPhone").value;

app.controller('myCtrl',['$scope','$http',function($scope,$http){
	var url= __baseApi + "accessDataReport.action";
	var sendData={projectId: projectId, name:name, idCardNum:idCardNum, cellPhone:cellPhone};
	$http.post(url,sendData)
		.success(function(resp){
			if(resp.flag){
				console.log("data.flag"+resp);
				$scope.trip_infos=resp.data.trip_info;
				$scope.ebusiness_expenses=resp.data.ebusiness_expense;
				$scope.main_services=resp.data.main_service;
				$scope.cell_behaviors=resp.data.cell_behavior;
				$scope.contact_regions=resp.data.contact_region;
				$scope.contact_list=resp.data.contact_list;
				$scope.deliver_address=resp.data.deliver_address;
				$scope.collection_contact=resp.data.collection_contact;
				$scope.behavior_check=resp.data.behavior_check;
				$scope.application_check=resp.data.application_check;
				$scope.data_source=resp.data.data_source;
				$scope.person=resp.data.person;
				$scope.report=resp.data.report;
				$scope.trip_consume = resp.data.trip_consume;
			}else{
				alert(resp.msg);
			}
		})
		.error(function(resp){
			console.log("error"+resp.flag);
		})
	
}]);
var testApp = angular.module('testApp', ["ngRoute"]);

testApp.config(function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "app/partials/index.html",
        controller: "getEmpsController"
    }).when("/add", {
        templateUrl: "app/partials/add.html",
        controller: 'employeeController'
    }).when("/profile", {
    	templateUrl: "app/partials/profile.html",
    	controller: 'profileController'
    })
});

testApp.controller('employeeController', function ($scope, $http, $location, $window, $route) {
    $scope.add = function () {
        console.log("Came");
        var data = {
            name: $scope.form.name,
            address: $scope.form.address,
            nic: $scope.form.nic,
            mobile: $scope.form.mobile,
            department: $scope.form.department,
            role: $scope.form.role
        }
        $http.post("http://localhost:8080/testApp/webapi/employee", data)
            .then(function (response) {
                console.log(response);
                $location.path("/");
                $scope.disabled = false;
                $scope.form = {}
            }).catch(function (err) {
                console.log(err);
                $scope.error = true;
                $scope.errorMessage = "Error";
                $scope.disabled = false;
                $scope.form = {}
            })
    }
});

testApp.controller('profileController', function ($scope, $http, $location, sharedDataService, fileService) {
	$scope.employee = sharedDataService;
	$scope.pics = $scope.employee.pictures;
	$scope.images = [];
	
	prepareImages();
	
	function getImage(){
		var uri = "http://localhost:8080/testApp/webapi/file/download/MdsbfWQBSDY3JU2lBTi2/0";
		$http.get(uri)
		.then(function (res) {
            $scope.picture = res.data;
        }).catch(function(err){
        	console.log(err);
        });
		console.log(uri);
	}
	
	function prepareImages(){
		for (var i=0; i<$scope.employee.pictures.length; i++){
			$scope.images.push('http://localhost:8080/testApp/webapi/file/download/' + $scope.employee.id + "/" + i);
			console.log($scope.images);
		}
	}
	
	$scope.upload = function(){
		var file = $scope.picture;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "http://localhost:8080/testApp/webapi/file/upload/" + $scope.employee.id;
        fileService.uploadFileToUrl(file, uploadUrl);
	}
});

testApp.controller('getEmpsController', function ($scope, $http, $location, sharedDataService) {
    $scope.employee = sharedDataService;
    $scope.updateButton = false;

    getEmp();

    $scope.search = function () {
        var keyword = $scope.ser.keyword;
        $http.get("http://localhost:8080/testApp/webapi/employee/search/"+keyword).then(function (res) {
            $scope.employees = res.data;
        });
    }

    $scope.select = function(emp){
        $scope.employee.name = emp.name;
        $scope.employee.address = emp.address;
        $scope.employee.nic = emp.nic;
        $scope.employee.mobile = emp.mobile;
        $scope.employee.department = emp.department;
        $scope.employee.role = emp.role;
        $scope.employee.pictures = emp.pictures;
        $scope.employee.id = emp.id;
        console.log($scope.employee.id);
        showButton();
    }
    
    $scope.update = function(){
    	var em = {
    		name: $scope.employee.name,
    		address:  $scope.employee.address,
    		nic: $scope.employee.nic,
    		mobile: $scope.employee.mobile,
    		department: $scope.employee.department,
    		role: $scope.employee.role
    	}
    	
    	$http({
    		method: 'PUT',
    		url: "http://localhost:8080/testApp/webapi/employee/" + $scope.employee.id,
    		data: em
    	}).then(function(response) {
            getEmp();
		}).catch(function(err){
			$scope.errorMessage = "Error";
		})
    }
    
    $scope.view = function(emp){
    	$scope.employee.name = emp.name;
        $scope.employee.address = emp.address;
        $scope.employee.nic = emp.nic;
        $scope.employee.mobile = emp.mobile;
        $scope.employee.department = emp.department;
        $scope.employee.role = emp.role;
        $scope.employee.pictures = emp.pictures;
        $scope.employee.id = emp.id;
        console.log($scope.employee.id);
        $location.path('/profile');
    }
    
    $scope.remove = function(emp){
        $http.delete("http://localhost:8080/testApp/webapi/employee/" + emp.id)
        .then(function(data){
            getEmp();
    	}).catch(function(err){
    		console.log(err);
    	})
    }
    
    function getEmp(){
    	$http.get("http://localhost:8080/testApp/webapi/employee")
    	.then(function (res) {
            $scope.employees = res.data;
            console.log($scope.employees);
        }).catch(function (err){
        	console.log(err);
        });
    }
    
    function showButton(){
    	$scope.updateButton = true;
    }
    
    function hideButton(){
    	$scope.updateButton = false;
    }
    
    function clear(){
    	$scope.employee.name = "";
        $scope.employee.address = "";
        $scope.employee.nic = "";
        $scope.employee.mobile = "";
        $scope.employee.department = "";
        $scope.employee.role = "";
        $scope.employee.id = "";
    }
});
angular.module('testApp').service('sharedDataService', function(){
    var employee = {};
    return employee;
});

angular.module('testApp').service('fileService', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        if (file != null){
        	fd.append('file', file);
        	console.log(uploadUrl);
        	$http.post(uploadUrl, fd, {
        		transformRequest: angular.identity,
        		headers: {'Content-Type': undefined}
        	}) .then(function(data){
        		console.log('Uploaded: ' + data);
        	}) .catch(function(err){
        		console.log('Failed to upload: ' + err);
        	});
        }else{
        	alert("Please select an image and try again.");
        }
    }
}]);

angular.module('testApp').directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

// References
// [1] http://jsfiddle.net/JeJenny/ZG9re/

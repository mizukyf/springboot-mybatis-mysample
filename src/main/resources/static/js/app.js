angular
.module('restapi-tester', ['ui.bootstrap'])
.controller("main", function($scope, $location, $http, $filter, $log){
	var REQUEST_METHODS = ['GET', 'POST'];
	var REQUEST_PREFIX = null;
	
	if ($location.protocol() === 'file') {
		var absUrl = $location.absUrl();
		var restapiEndpointIndex = absUrl.indexOf('/templates/restapi-tester.html');
		REQUEST_PREFIX = $location.absUrl().slice(0, restapiEndpointIndex) + '/static';
	} else {
		var absUrl = $location.absUrl();
		var restapiEndpointIndex = absUrl.indexOf('/restapi-tester');
		REQUEST_PREFIX = $location.absUrl().slice(0, restapiEndpointIndex);
	}
	
	$scope.requestPrefix = REQUEST_PREFIX;
	$scope.requestMethod = REQUEST_METHODS[0];
	$scope.requestPath = '';
	$scope.requestBody = '';
	$scope.responseBody = '';
	
	$scope.requestSubmitClick = function() {
		var conf = {};
		conf.method = $scope.requestMethod;
		conf.url = $scope.requestPrefix + $scope.requestPath;
		if (conf.method === 'POST') {
			conf.data = $scope.requestBody;
		}
		
		$http(conf).success(function(data, status, headers, conf){
			$scope.responseStatus = status;
			if (typeof data === 'string') {
				$scope.responseBody = data;
			} else {
				$scope.responseBody = $filter('json')(data);
			}
		}).error(function(data, status, headers, conf){
			$scope.responseStatus = status;
			if (typeof data === 'string') {
				$scope.responseBody = data;
			} else {
				$scope.responseBody = $filter('json')(data);
			}
		});
	};
	
	$scope.requestPathChange = function() {
		if ($scope.requestPath.length > 0 && $scope.requestPath.slice(0,1) === '/') {
			$scope.submitDisabled = false;
		} else {
			$scope.submitDisabled = true;
		}
	};
	
	$scope.requestPathChange();
});


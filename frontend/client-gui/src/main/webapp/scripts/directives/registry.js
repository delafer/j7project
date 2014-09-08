define(['./module', '../infrastructure/index'], function (directives) {
  'use strict';
  directives.directive('appUrl', [ 'AppRegistryService', function (AppRegistryService) {
      return {
        restrict: 'E',
        scope: {
        	remoteUrl:'@',
            name:'@'
        },
        link: function($scope, element, attrs) {
                //Get the HTML of div

            	AppRegistryService.setUrl($scope.name, $scope.remoteUrl);
//            	alert($scope.name + "=" + urlValue);
            	//alert(AppRegistryService.getUrl('test2'));
            	//alert(AppRegistryService.getUrl('test'));

        }
      };
  }]);
});
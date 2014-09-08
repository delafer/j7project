define([ './module','jquery', '../infrastructure/index' ], function(controllers) {
	'use strict';
	controllers.controller('PopupCtrl', [ '$scope', '$modalInstance', '$log', '$timeout',
			'detailId', 'DetailsService','$sce',  'AppRegistryService', 'XDMGagdetService', function($scope, $modalInstance, $log, $timeout, detailId, DetailsService, $sce, AppRegistryService, XDMGagdetService) {

			$scope.uuid = detailId;
			//alert('token:'+SessionService.getToken());
            XDMGagdetService.retrieveToken(function(retrievedToken) {
            	$timeout(function() {
                	$scope.$apply(function(){
                		$scope.pdfURL =  AppRegistryService.getUrl('baseUrl') + '/document/'+$scope.uuid+'?mediaType=application/pdf&Access-Token='+retrievedToken;
                	});
                  });
            });

			var args = {};
			args.uuid = $scope.uuid;
			args.header =  { 'Accept': 'text/plain' };

			DetailsService.fnLoadInquiry(args, function(
					data, status, headers, config) {
				$scope.data = data;
			});


				$scope.close = function() {
					$modalInstance.dismiss('close');
				};
			} ]);
});

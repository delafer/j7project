define([ './module', '../infrastructure/index' ], function(controllers) {

    'use strict';

    controllers.controller('DetailsCtrl', [ '$scope', '$location', '$modal', '$timeout', 'DetailsService', '$routeParams', 'AppRegistryService', 'XDMGagdetService', function($scope, $location, $modal, $timeout, DetailsService, $routeParams, AppRegistryService, XDMGagdetService) {

        $scope.detailsParams = {};
        $scope.detailsParams.uuid = $routeParams.uuid;
        $scope.detailsParams.crefoNr = $routeParams.crefonr;
        $scope.detailsParams.version = $routeParams.version;

        //$scope.pdfURL =  AppRegistryService.getUrl('baseUrl') + '/document/' + $scope.detailsParams.uuid + '?mediaType=application/pdf&Access-Token='+XDMTokenService.getToken();
        $scope.pdfURL = "";
        $scope.showInquiry = false;
        $scope.inquiryText = undefined;

        $scope.btnIcon = 'glyphicon-eye-open';

        //DetailsService.fnLoadMetaData($scope.detailsParams);

        DetailsService.fnLoadMetaData($scope.detailsParams, function(result) {

            $scope.data = result.length > 0 ? result[0] : {};

            if ($scope.data.uuid == undefined) {
            	$location.path('/');
            }

            XDMGagdetService.retrieveToken(function(retrievedToken) {
            	$timeout(function() {
                	$scope.$apply(function(){
                		$scope.pdfURL =  AppRegistryService.getUrl('baseUrl') + '/document/' + $scope.data.uuid + '?mediaType=application/pdf&Access-Token='+retrievedToken;
                	});
                  });
            });


            $scope.viewInquiry = function() {
                if ($scope.inquiryText == undefined) {
                    var args = {};
                    args.uuid = $scope.data.uuid;
                    args.header = {
                        'Accept' : 'text/plain'
                    };

                    DetailsService.fnLoadInquiry(args, function(data, status, headers, config) {
                        $scope.inquiryText = data;
                       //$defer.resolve($scope.inquiryText);
                        // $scope.showInquiry = true;
                    });
                }


                $scope.showInquiry = !$scope.showInquiry;
                $scope.btnIcon = $scope.showInquiry ? 'glyphicon-eye-close' : 'glyphicon-eye-open';
            };

            if ($routeParams.crefonr != undefined) {
            	$scope.viewInquiry();
            }


        }, function(error) {
            alert('Failure: ' + angular.toJson(error));
        });


    } ]);
});
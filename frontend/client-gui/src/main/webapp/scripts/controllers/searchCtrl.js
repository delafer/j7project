define(['./module'], function(controllers) {
    'use strict';
    controllers.controller('SearchCtrl', ['$scope', '$location', '$modal', '$routeParams', 'HistAgencyService', 'showSearchParams', 'ngTableParams', '$filter',
        function($scope, $location, $modal, $routeParams, HistAgencyService, showSearchParams, ngTableParams, $filter) {
            $scope.showSearchParams = showSearchParams;

            $scope.dataList = [];

            $scope.searchParams = {};
            $scope.comboParams = {};

            $scope.searched = false;
            // TODO use config
            $scope.maxHits = 200;


            $scope.resetSearch = function() {
                $scope.searchParams = {};
                $scope.comboParams = {};
            };


            // onBtnClick:search()
            $scope.search = function() {

            	if (typeof($scope.comboParams.countryCode) != "undefined") {
            		$scope.searchParams.countryCode = $scope.comboParams.countryCode.key;
            	} else {
            		$scope.searchParams.countryCode = undefined;
            	}


            	if (typeof($scope.comboParams.legalformCode) != "undefined") {
            		$scope.searchParams.legalformCode = $scope.comboParams.legalformCode.key;
            	} else {
            		$scope.searchParams.legalformCode = undefined;
            	}

                HistAgencyService.fnSearch($scope.searchParams, function(result) {

                    $scope.dataList = result;
                    $scope.hitListOversize = ($scope.dataList.length >= $scope.maxHits);

                    $scope.tableParams.reload();
                    $scope.tableParams.page(1);

                    $scope.searched = true;

                });

            };

            // onBtnClick:showDetails()
            $scope.showDetails = function(data) {

                $scope.uuid = data.uuid;
                $location.path('/details/' + data.uuid);
            };

            $scope.open = function(uuid) {

                var modalInstance = $modal.open({
                    templateUrl: 'views/inquiryPopup.jsp',
                    controller: 'PopupCtrl',
                    windowClass: 'inquiryTextPopUp',
                    resolve: {
                        detailId: function() {
                            return uuid;
                        }
                    }

                });

            };

            $scope.tableParams = new ngTableParams({
                page: 1, // show first page
                count: 10 // count per page
            }, {
                total: $scope.dataList.length, // length of data
                getData: function($defer, params) {

                    if ($scope.dataList.length === 0)
                        return;

                    // use build-in angular filter
                    var orderedData = params.sorting() ? $filter('orderBy')($scope.dataList, params.orderBy()) : $scope.dataList;

                    $scope.users = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());

                    params.total(orderedData.length); // set total for
                    // recalc pagination
                    $defer.resolve($scope.users);
                }
            });



            if ($routeParams.crefonr != undefined && $routeParams.crefonr != "!") {
            	$scope.resetSearch();
            	$scope.searchParams.crefoNr = $routeParams.crefonr;
            	$scope.search();
            }


        }]);
});

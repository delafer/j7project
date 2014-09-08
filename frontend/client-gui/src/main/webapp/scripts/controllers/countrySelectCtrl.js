define([ './module' ], function(controllers) {
    'use strict';
    controllers.controller('CountrySelectCtrl', [ '$scope', 'HistAgencyService', function($scope, HistAgencyService) {

        HistAgencyService.fnLoadCountries($scope.searchParams, function(result) {

            $scope.countries = result;
        });

    } ]);
});

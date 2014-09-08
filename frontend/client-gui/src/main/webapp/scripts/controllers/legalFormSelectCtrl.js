define([ './module' ], function(controllers) {
    'use strict';
    controllers.controller('LegalFormSelectCtrl', [ '$scope', 'HistAgencyService', function($scope, HistAgencyService) {

        HistAgencyService.fnLoadLegalforms($scope.searchParams, function(result) {

            $scope.legalForms = result;
        });

    } ]);
});

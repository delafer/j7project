define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('MyCtrl1', ['$scope','$modal', function ($scope, $modal) {

        $scope.launch = function(uuid) {
        	var language = window.navigator.userLanguage || window.navigator.language;
        	alert(language);
            var modalInstance = $modal.open({
                templateUrl: 'partials/pageModal.html',
                controller: 'MyCtrl2'
            });

        };

    }]);
});

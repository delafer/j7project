define(['./module', './configConstants'],
        function(configs) {
            'use strict';

            configs.config(['$httpProvider', function($httpProvider) {
                    $httpProvider.interceptors.push('HttpAuthInterceptor');
                }]);

            configs.config(['$httpProvider', function($httpProvider) {
                    $httpProvider.interceptors.push('SpinnerHttpInterceptor');
                }]);

            configs.config(['$httpProvider', function($httpProvider) {
                    $httpProvider.interceptors.push('HttpErrorInterceptor');
                }]);

            configs.config(['$locationProvider', function($locationProvider) {
                    //$locationProvider.html5Mode(true);
                }]);

        });

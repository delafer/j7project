define(['./module','./token-http-interceptor'],
        function(configs) {
            'use strict';
            configs.config(['$httpProvider', function($httpProvider) {
                    $httpProvider.interceptors.push("HttpAccessTokenInterceptor");
                }]);


        });

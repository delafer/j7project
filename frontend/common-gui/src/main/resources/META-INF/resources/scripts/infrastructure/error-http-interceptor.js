define(['./module'],
        function(factories) {
            'use strict';
            factories.factory('HttpErrorInterceptor', ['$q', '$rootScope', 'toaster',
                function($q, $rootScope, toaster) {

                    return {
                        'responseError': function(response) {

                            //The REST Services always send 500 when an exception occurs
                            if (response.status === 500) {

                                var error = {};
                                error.reason = response.data.reason;
                                error.uuid = response.data.uuid;
                                error.source = response.data.source;
                                error.code = response.data.code;

                                toaster.pop('error', "", error, 0, 'template');

                                return $q.reject(response);
                            } else {

                                return $q.reject(response);
                            }
                        }
                    };
                }]);
        });
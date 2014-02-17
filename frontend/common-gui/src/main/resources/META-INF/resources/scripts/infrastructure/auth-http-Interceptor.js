define(['./module'],
        function(factories) {
            'use strict';

            factories.factory('HttpAuthInterceptor', ['$q', '$rootScope', 'httpBuffer', function($q, $rootScope, httpBuffer) {
            	 return {
            		   'responseError': function(response) {
            			   if (response.status === 401) {
                               var deferred = $q.defer();
                               httpBuffer.append(response.config, deferred);
                               $rootScope.$broadcast('event:auth-loginRequired');
                               return deferred.promise;
                           } else if (response.status === 403) {
                               $rootScope.$broadcast('event:auth-notAuthorized');
                               return $q.reject(response);
                           } else {
                               return $q.reject(response);
                           }
            		    }
            		  };
                }]);

            factories.factory('httpBuffer', [function($injector) {
                    /** Holds all the requests, so they can be re-requested in future. */
                    var buffer = [];

                    /** Service initialized later because of circular dependency problem. */
                    var $http;

                    function retryHttpRequest(config, deferred) {
                        function successCallback(response) {
                            deferred.resolve(response);
                        }
                        function errorCallback(response) {
                            deferred.reject(response);
                        }
                        $http = $http || $injector.get('$http');
                        $http(config).then(successCallback, errorCallback);
                    }

                    return {
                        /**
                         * Appends HTTP request configuration object with deferred response attached to buffer.
                         */
                        append: function(config, deferred) {
                            buffer.push({
                                config: config,
                                deferred: deferred
                            });
                        },
                        /**
                         * Retries all the buffered requests clears the buffer.
                         */
                        retryAll: function() {
                            for (var i = 0; i < buffer.length; ++i) {
                                retryHttpRequest(buffer[i].config, buffer[i].deferred);
                            }
                            buffer = [];
                        }
                    };
                }]);
        });
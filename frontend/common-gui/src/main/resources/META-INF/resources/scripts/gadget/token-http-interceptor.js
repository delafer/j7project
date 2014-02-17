define(['./module', './xdm-gagdet-service'],
        function(factories) {
            'use strict';
            factories.factory('HttpAccessTokenInterceptor', ['$q', 'SessionService', 'XDMGagdetService', function($q, SessionService, XDMGagdetService) {
                    var loadToken = function() {
                        var deferred = $q.defer();
                        XDMGagdetService.retrieveToken(function(retrievedToken) {
                            //SessionService.set('token', retrievedToken);
                            deferred.resolve(retrievedToken);
                        });
                        return deferred.promise;
                    };

                    var processToken = function(token, config) {
                        config.headers['access_token'] = token;
                        SessionService.unset('token');
                        console.log('Token Received: ' + token);
                        return config || $q.when(config);
                    };

                    return {
                        'request': function(config) {
                            var token = SessionService.get('token');

                            if (!token) {
                                return loadToken().then(function(result) {
                                    return processToken(result, config);
                                });
                            }
                            else
                            {
                                return processToken(token, config);
                            }
                        }
                    };
                }]);
        });
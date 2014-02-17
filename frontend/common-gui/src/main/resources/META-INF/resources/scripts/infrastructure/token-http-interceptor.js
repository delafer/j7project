define(['./module', 'easyxdm'],
        function(factories) {
            'use strict';
            factories.factory('HttpAccessTokenInterceptor', ['$q', 'SessionService', 'remoteXDM', function($q, SessionService, remoteXDM) {
                    return {
                        'request': function(config) {
                            var token = SessionService.get('token');
                            if (!token && remoteXDM) {
                                var rpc = new easyXDM.Rpc({
                                    local: '/../crefowebjars/easyxdm/2.4.19.3/name.html',
                                    onReady: function() {
                                        
                                    }
                                },
                                {
                                    local: {
                                        helloWorld: function(one, two, thre_args, successFn, errorFn) {
                                            return {
                                                this_is: "an object"
                                            };
                                        }
                                    },
                                    remote: {
                                        retrieveToken: {}
                                    }

                                });
                                console.log('1' + token);
                                rpc.retrieveToken(function(parentToken) {
                                    SessionService.set('token', parentToken);
                                });

                            }
                            console.log('2' + token);

                            config.headers['access-token'] = token;
                            return config || $q.when(config);
                        }
                    };
                }]);
        });
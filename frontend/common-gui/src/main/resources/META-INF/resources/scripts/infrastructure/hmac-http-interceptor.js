define(['./module','hmacSha256'],
        function(factories) {
            'use strict';
            factories.factory('HttpHmacInterceptor', ['$q','SessionService',function($q, SessionService) {
                    return {
                        'request': function(config) {
                            if (config.url.indexOf('/') !== -1 && SessionService.get('authenticated')) {
                                var token = SessionService.get('token');
                                var url = '/'+config.url;
                                var hmac = CryptoJS.algo.HMAC.create(CryptoJS.algo.SHA256, token);
                                hmac.update(url);

                                var hash = hmac.finalize();
                                config.headers['X-AUTH'] = CryptoJS.enc.Hex.stringify(hash);
                                config.headers['X-USER'] = SessionService.get('username');
                                config.headers['X-CONTEXT'] = SessionService.get('contextid');
                            }
                            return config || $q.when(config);
                        }
                    };
                }]);
        });
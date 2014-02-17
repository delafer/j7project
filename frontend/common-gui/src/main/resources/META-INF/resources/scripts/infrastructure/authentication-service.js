define(['./module'],
        function(factories) {
            'use strict';

            factories.factory("AuthenticationService",
                    ['$http', 'SessionService', '$rootScope', 'httpBuffer',function($http, SessionService, $rootScope, httpBuffer) {

                            var cacheSession = function(data) {
                                SessionService.set('authenticated', true);
                                SessionService.set('token', data.secret);
                                SessionService.set('fullName', data.username);
                                SessionService.set('username', data.username);
                                SessionService.set('contextid', data.contextId);

                            };

                            var uncacheSession = function() {
                                SessionService.unset('authenticated');
                                SessionService.unset('token');
                                SessionService.unset('fullName');
                                SessionService.unset('username');
                                SessionService.unset('contextid');
                            };

                            /**
                             * Call this function to indicate that authentication was successfull and trigger a retry of all
                             * deferred requests.
                             *
                             * @param data
                             *            an optional argument to pass on to $broadcast which may be useful for example if you need
                             *            to pass through details of the user that was logged in
                             */
                            var loginConfirmed = function(data) {
                                $rootScope.$broadcast('event:auth-loginConfirmed', data);
                                httpBuffer.retryAll();
                            };

                            var loginAction = function(credentials, success, error) {
                                var login = $http.post("/sandbox-login-gui/api/users/login", credentials);
                                login.success(cacheSession);
                                login.success(success);
                                login.error(error);
                                return login;
                            };

                            var logoutAction = function() {
                                var logout = $http.get("/logout");
                                logout.success(uncacheSession);
                                logout.success(function() {
                                    console.log("redirect");
                                    window.location.href = "/login.html";
                                });
                                return logout;
                            };

                            var isLoggedIn = function() {
                                return SessionService.get('authenticated');
                            };
                            
                            return {
                                login: loginAction,
                                logout: logoutAction,
                                isLoggedIn: isLoggedIn,
                                loginConfirmed: loginConfirmed
                            };
                        }]);
            
            

        });
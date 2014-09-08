/**
 * Defines the main routes in the application.
 * The routes you see here will be anchors '#/' unless specifically configured otherwise.
 */

define(['./app'], function (app) {
    'use strict';
    return app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'views/search.jsp',
        }).when('/:crefonr', {
        	controller: 'SearchCtrl',
        	templateUrl: 'views/search.jsp',
          }).when('/details/:uuid', {
        	controller: 'DetailsCtrl',
        	templateUrl: 'views/details.jsp',
          }).when('/details/:crefonr/:version', {
        	controller: 'DetailsCtrl',
        	templateUrl: 'views/details.jsp',
          }).otherwise({
        	   redirectTo : '/'
          });
//        $routeProvider.otherwise({
//            redirectTo: '/'
//        });
    }]);
});
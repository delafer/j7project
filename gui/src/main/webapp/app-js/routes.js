/**
 * Defines the main routes in the application.
 * The routes you see here will be anchors '#/' unless specifically configured otherwise.
 */

define(['./app'], function (app) {
    'use strict';
    return app.config(['$routeProvider', '$routeSegmentProvider', function ($routeProvider, $routeSegmentProvider) {
        $routeSegmentProvider.when('/view1', 's1');

        $routeSegmentProvider.when('/view2', 's2');

        $routeSegmentProvider.segment('s1', {
            templateUrl: 'partials/partial1.html',
            controller: 'MyCtrl1'
            }
        );

        $routeSegmentProvider.segment('s2', {
            templateUrl: 'partials/partial2.html',
            controller: 'MyCtrl2'
            }
        );

        $routeProvider.otherwise({
            redirectTo: '/view1'
        });
    }]);
});

/**
 * loads sub modules and wraps them up into the main module
 * this should be used for top-level module definitions only
 */
define([
    'angular',
    'angularRoute',
    'angularAnimate',
    'angularResource',
    'angularCookies',
    'angularSanitize',
    'angularToastr',
    'angularChosen',
    'angularBootstrap',
    'routeSegment',
    'ui.select',
    'ngTable',
    './controllers/index',
    './directives/index',
    './filters/index',
    './services/index'
], function (angular) {
    'use strict';

    return angular.module('app', ['ngRoute',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ui.select',
        'ui.bootstrap',
        'toaster',
        'localytics.directives',
        'ngTable',
	'route-segment',
	'view-segment',
        'app.controllers',
        'app.directives',
        'app.filters',
        'app.services'
    ]);
});

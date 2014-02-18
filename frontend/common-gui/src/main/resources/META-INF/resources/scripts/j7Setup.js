define([
    'angular',
    'angularAnimate',
    'angularResource',
    'angularCookies',
    'angularSanitize',
    'angularRoute',
    'angularUiSelect2',
    'angularUiBootstrap',
    'angularToastr',
    'angularChosen',
    'ngTable',
    './config/index',
    './common/index',
    './infrastructure/index',
    './base/directives/index'
], function(ng) {
    'use strict';
    return ng.module('j7Base', ['ngRoute',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ui.select2',
        'ui.bootstrap',
        'toaster',
        'localytics.directives',
        'j7.config',
        'j7.common',
        'ngTable',
        'j7.infrastructure',
        'j7.directives']
            );
});


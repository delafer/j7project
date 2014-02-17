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
    return ng.module('crefoBase', ['ngRoute',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ui.select2',
        'ui.bootstrap',
        'toaster',
        'localytics.directives',
        'crefo.config',
        'crefo.common',
        'ngTable',
        'crefo.infrastructure',
        'crefo.directives']
            );
});


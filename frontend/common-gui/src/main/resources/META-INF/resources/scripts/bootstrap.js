define([
    'require',
    'angular',
    'app',
    'routes',
    'es5Shim'
], function (require, ng) {
    'use strict';
    require(['domReady!'], function (document) {
        ng.bootstrap(document, ['app']);
    });
});
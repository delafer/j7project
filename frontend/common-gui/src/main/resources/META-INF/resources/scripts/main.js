require.config({
    // alias libraries paths
    paths: {
        'domReady': '../webjars/requirejs-domready/domReady',
        'angular': '../webjars/angularjs/angular',
        'angularAnimate': '../webjars/angularjs/angular-animate',
        'angularResource': '../webjars/angularjs/angular-resource',
        'angularCookies': '../webjars/angularjs/angular-cookies',
        'angularRoute': '../webjars/angularjs/angular-route',
        'angularSanitize': '../webjars/angularjs/angular-sanitize',
        'bootstrap3': '../webjars/bootstrap/js/bootstrap',
        'es5Shim': '../webjars/es5-shim/es5-shim',
        'angularUiBootstrap': '../webjars/angular-ui-bootstrap/ui-bootstrap-tpls',
        'angularToastr': '../webjars/angular-toastr/toaster',
        'ngTable': '../webjars/ng-table/ng-table',
        'ui.select': '../webjars/ui-select/select',
        'jquery': '../webjars/jquery/jquery',
        'chosen': '../webjars/chosen/chosen.jquery',
        'angularChosen': '../webjars/angular-chosen/chosen'
    },
    // angular does not support AMD out of the box, put it in a shim
    shim: {
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angularAnimate': ['angular'],
        'angularResource': ['angular'],
        'angularCookies': ['angular'],
        'angularSanitize': ['angular'],
        'angularRoute': {
            deps: ['angular'],
            exports: 'angularRoute'
        },
        'bootstrap3': {
            deps: ['jquery'],
            exports: 'bootstrap3'
        },
        'chosen': {
            deps: ['jquery'],
            exports: 'chosen'
        },
        'es5Shim': {
            exports: 'es5Shim'
        },
        'angularUiBootstrap': ['bootstrap3', 'angular', 'angularAnimate'],
        'angularToastr': ['angular', 'angularAnimate'],
        'jquery': {
            exports: 'jquery'
        },
        'ngTable': ['angular'],
        'ui.select': ['angular']
    },
    priority: [
        'angular'
    ],
    // kick start application
    deps: ['./bootstrap']
});
require.config({
    // alias libraries paths
    paths: {
        'domReady': '../webjars/requirejs-domready/2.0.1/domReady',
        'angular': '../webjars/angularjs/1.2.10/angular',
        'angularAnimate': '../webjars/angularjs/1.2.10/angular-animate',
        'angularResource': '../webjars/angularjs/1.2.10/angular-resource',
        'angularCookies': '../webjars/angularjs/1.2.10/angular-cookies',
        'angularRoute': '../webjars/angularjs/1.2.10/angular-route',
        'angularSanitize': '../webjars/angularjs/1.2.10/angular-sanitize',
        'select2': '../webjars/select2/3.4.4/select2',
        'bootstrap3': '../webjars/bootstrap/3.1.0/js/bootstrap',
        'es5Shim': '../webjars/es5-shim/2.3.0/es5-shim',
        'hmacSha256': '../webjars/cryptojs/3.1.2/rollups/hmac-sha256',
        'angularUiSelect2': '../webjars/ui-select2/0.0.5/ui-select2',
        'angularUiUtils': '../webjars/angular-ui-utils/0.1.0/ui-utils',
        'angularUiBootstrap': '../webjars/angular-ui-bootstrap/0.10.0/ui-bootstrap-tpls',
        'angularToastr': '../webjars/angular-toastr/0.4.1/toaster',
        'chosen': '../webjars/chosen/1.0.0/chosen.jquery',
        'ngTable': '../webjars/ng-table/0.3.1/ng-table',
        'angularChosen': '../webjars/angular-chosen/1.0.4/chosen',
        'jquery': '../webjars/jquery/1.11.0/jquery',
        'easyxdm': '../webjars/easyxdm/2.4.19.3/easyXDM.debug'
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
        'select2': {
            deps: ['jquery'],
            exports: 'select2'
        },
        'es5Shim': {
            exports: 'es5Shim'
        },
        'hmacSha256': {
            exports: 'hmacSha256'
        },
        'chosen': {
            deps: ['jquery'],
            exports: 'chosen'
        },
        'angularUiBootstrap': ['bootstrap3', 'angular', 'angularAnimate'],
        'angularUiUtils': ['angular'],
        'angularUiSelect2': ['angular', 'select2'],
        'angularChosen': ['angular', 'chosen'],
        'angularToastr': ['angular', 'angularAnimate'],
        'jquery': {
            exports: 'jquery'
        },
        'ngTable': ['angular'],
        'easyxdm': {
            exports: 'easyxdm'
        }

    },
    priority: [
        'angular'
    ],
    // kick start application
    deps: ['./bootstrap']
});
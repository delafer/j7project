/**
 * configure RequireJS
 * prefer named modules to long paths, especially for version mgt
 * or 3rd party libraries
 */
require.config({
    // alias libraries paths
    paths: {
        'domReady': '../webjars/requirejs-domready/domReady',
        'es5Shim': '../webjars/es5-shim/es5-shim',
        'bootstrap3': '../styles/bootstrap/js/bootstrap',
        'jquery': '../webjars/jquery/jquery',
        'chosen': '../webjars/chosen/chosen.jquery',
        'angular': '../webjars/angularjs/angular',
        'angularAnimate': '../webjars/angularjs/angular-animate',
        'angularCookies': '../webjars/angularjs/angular-cookies',
        'angularChosen': '../webjars/angular-chosen/chosen',
        'angularResource': '../webjars/angularjs/angular-resource',
        'angularRoute': '../webjars/angularjs/angular-route',
        'angularSanitize': '../webjars/angularjs/angular-sanitize',
        'angularToastr': '../webjars/angular-toastr/toaster',
        'angularBootstrap': '../webjars/ui-bootstrap/ui-bootstrap-tpls',
        'routeSegment': '../webjars/route-segment/angular-route-segment',
        'ngTable': '../webjars/ng-table/ng-table',
        'ui.select': '../webjars/ui-select/select',
        'sockjs': '../webjars/sockjs/sockjs',
	'angularSocket': '../webjars/angular-socket/angular-socket'
    },
    /**
     * for libs that either do not support AMD out of the box, or
     * require some fine tuning to dependency mgt'
     */
    shim: {
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angularAnimate': ['angular'],
        'angularResource': ['angular'],
        'angularCookies': ['angular'],
        'angularSanitize': ['angular'],
        'angularChosen': ['angular'],
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
        'angularBootstrap': {
	    deps: ['bootstrap3', 'angular', 'angularAnimate'],
            exports: 'angularBootstrap'		
	},
        'routeSegment': {
	    deps: ['angular', 'angularAnimate', 'angularRoute'],
            exports: 'routeSegment'		
	},
        'angularToastr': ['angular', 'angularAnimate'],
        'jquery': {
            exports: 'jquery'
        },
        'ngTable': ['angular'],
        'ui.select': ['angular'],
        'sockjs': {
            deps: ['jquery'],
            exports: 'sockjs'
        },
	'angularSocket': {
            deps: ['sockjs'],
            exports: 'angularSocket'
        }

    },
    priority: [
        'angular'
    ],
    deps: [
        // kick start application... see bootstrap.js
        './bootstrap'
    ]
});

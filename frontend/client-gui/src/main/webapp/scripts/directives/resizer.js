define(['./module','crefoGadgetSetup'], function (directives) {
    'use strict';
    directives.directive('resizer', [ 'XDMGagdetService', function (XDMGagdetService) {
        return {
            restrict: 'E',
            scope: {
                minHeight: '@'
            },
            link: function (scope, element, attrs) {
                var timer;
                var originalHeight = document.body.clientHeight || document.body.offsetHeight || document.body.scrollHeight;

                if (!timer) {
                    timer = setInterval(function () {
                        try {
                            var newHeight = document.body.clientHeight || document.body.offsetHeight || document.body.scrollHeight;
                            if (newHeight < scope.minHeight) {
                            	newHeight = scope.minHeight;
                            }

                            if (newHeight != originalHeight) {
                                // The height has changed since last we checked
                                originalHeight = newHeight;
                                XDMGagdetService.resizeFrame({height:originalHeight});
                            }
                        } catch (e) {
                            // We tried to read the property at some point when it wasn't available
                        }
                    }, 333);
                }
            }
        };
    }]);
});
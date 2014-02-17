define(['./module'], function (directives) {
  'use strict';
  directives.directive('printBtn',
    function () {
      return {
        restrict: 'E',
        template: '<button ng-disabled="disableBtn" type="button" class="btn btn-default btn-sm" ng-click="printDiv()"><span class="glyphicon glyphicon-print"></span> <span  ng-transclude></span></button>',
        transclude: true,
        scope: {
        	disableBtn:'=',
        	printDivId:'@'
        },
        link: function($scope, element, attrs) {
            $scope.printDiv = function() {
                //Get the HTML of div
            	var printDivId = $scope.printDivId;
                var divElements = document.getElementById(printDivId).innerHTML;

                var win = window.open('about:blank','_blank','width=100,height=100,left=0,top=0,menubar=no,toolbar=no,scrollbars=no,status=no,resizable=no,directories=no,location=no', true);
            	win.border = 0;
                //Reset the page's HTML with div's HTML only
                win.document.write("<html><head><title>Textauskunft</title></head><body>");
                win.document.write(divElements);
                win.document.write("</body>");
                win.document.close();
                //Print Page
                win.focus();
                win.print();
            	win.close();
                };
        }
      };
    }
  );
});
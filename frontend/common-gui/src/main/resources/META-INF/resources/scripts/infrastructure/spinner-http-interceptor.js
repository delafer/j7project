define(['jquery','./module'],
        function($,module) {
            'use strict';
            var runningCounter = 0;
            /*global jQuery:true */
            // register the interceptor as a service, intercepts ALL angular ajax http
            // calls
            module.factory('SpinnerHttpInterceptor', ['$q','spinner',function($q,spinner) {
            	 var decreaseSpinner = function(){
                     runningCounter--;
                     if (runningCounter === 0) {
                         $('.spinner_container').hide();
                     }
            	 };
            	 
            	 var increaseSpinner = function(){
            		 runningCounter++;
                     if (spinner) {
                         $('.spinner_container').show();
                     }
            	 };
            	
            	 return {
            		   'request': function(config) {
            			  increaseSpinner();
            		      return config || $q.when(config);
            		    },
            		   'requestError': function(rejection) {
            			  decreaseSpinner(); 
            			  return $q.reject(rejection);
            		    },
            		    'response': function(response) {
            		      decreaseSpinner();	
            			  return response || $q.when(response);
            		    },
            		   'responseError': function(rejection) {
            			  decreaseSpinner(); 
            			  return $q.reject(rejection);
            		    }
            	};
            }]);

        });
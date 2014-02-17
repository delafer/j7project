define([], 
function () {
  'use strict';

  Array.prototype.clear = function () {
    while(this.length > 0) {
      this.pop();
    }
  };

  Array.prototype.pushArray = function (arr) {
    this.push.apply(this, arr);
  };

  // IE8: Don't user console.log if undefined
  var alertFallback = false;
  if(typeof console === 'undefined' || typeof console.log === 'undefined') {
    console = {};
    if(alertFallback) {
      console.log = function (msg) {
        window.alert(msg);
      };
    } else {
      console.log = function () {};
    }
  }
});
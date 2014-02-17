define(['./module'],
        function(factories) {
            'use strict';
            factories.factory('MultiOrder', [
                function() {
                    var MultiOrder = {};
                    // override these in your controller
                    // an order that is appended to the end of any sort (only matters
                    // when the sorted element has identical elements)
                    MultiOrder.forceOrder = [];
                    // set this in the controller to set the initial sorting - after
                    // this contains the actual current sort
                    MultiOrder.orderArray = [];
                    // the class to apply to ascendingly sorted order selection element
                    // (usually the header)
                    MultiOrder.plusSortClass = '';
                    // the class to apply to descendingly sorted order selection element
                    // (usually the header)
                    MultiOrder.minusSortClass = '';
                    // use in on-click event
                    MultiOrder.modOrder = function(evt, name) {
                        // setup vars, remove the force sorts and clear all classes.
                        MultiOrder.orderArray.splice(MultiOrder.orderArray.length - (MultiOrder.forceOrder.length) + 1,
                                MultiOrder.forceOrder.length);
                        angular.element(evt.currentTarget).removeClass(
                                MultiOrder.plusSortClass + ' ' + MultiOrder.minusSortClass);
                        if (evt.shiftKey) {
                            // multisort
                            for (var i = 0; i < MultiOrder.orderArray.length; i++) {
                                if (MultiOrder.orderArray[i] === '-' + name) {
                                    MultiOrder.orderArray[i] = '+' + name;
                                    angular.element(evt.currentTarget).addClass(MultiOrder.plusSortClass);
                                    return false;
                                } else if (MultiOrder.orderArray[i] === '+' + name) {
                                    MultiOrder.orderArray[i] = '-' + name;
                                    angular.element(evt.currentTarget).addClass(MultiOrder.minusSortClass);
                                    return false;
                                }
                            }
                            MultiOrder.orderArray.push('+' + name);
                            angular.element(evt.currentTarget).addClass(MultiOrder.plusSortClass);
                        } else {
                            // single sort
                            angular.element(evt.currentTarget).parent().children().removeClass(
                                    MultiOrder.plusSortClass + ' ' + MultiOrder.minusSortClass);
                            if (MultiOrder.orderArray[0] === '+' + name) {
                                MultiOrder.orderArray = ['-' + name];
                                angular.element(evt.currentTarget).addClass(MultiOrder.minusSortClass);
                            } else {
                                MultiOrder.orderArray = ['+' + name];
                                angular.element(evt.currentTarget).addClass(MultiOrder.plusSortClass);
                            }
                        }
                        // add sort forces - remember forceOrder elements are -value and
                        // name is value
                        for (var order = 0; order < MultiOrder.forceOrder.length; order++) {
                            if (MultiOrder.forceOrder[order].replace(/[+\-]/, '').indexOf(name.replace(/[+ \-]/, '')) === -1) {
                                MultiOrder.orderArray.push(MultiOrder.forceOrder[order]);
                            }
                        }
                        return false;
                    };

                    return MultiOrder;
                }]);
        });
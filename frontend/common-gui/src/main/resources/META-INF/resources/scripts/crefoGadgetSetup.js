define([
    'angular',
    'crefoSetup',
    './gadget/index'
], function(ng) {
    'use strict';
    return ng.module('crefoGadgetBase', ['crefoBase',
        'crefo.gadget']
            );
});


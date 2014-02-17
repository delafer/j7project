define(['./module', 'easyxdm'],
        function(factories) {
            'use strict';

            factories.factory("XDMGagdetService", ['$q', function($q) {
                    try {
                        var rpc = new easyXDM.Rpc({
                            local: 'partials/transport.partial',
                            onReady: function() {
                                console.log('XDMGagdetService initialized');
                            }
                        },
                        {
                            local: {
                                helloWorld: function(one, two, thre_args, successFn, errorFn) {
                                    return {
                                        this_is: "an object"
                                    };
                                }
                            },
                            remote: {
                                retrieveToken: {},
                                resizeFrame: {}
                            }

                        });
                    }
                    catch (e) {
                        console.log(e);
                    }

                    var retrieveToken = function(callback) {
                        if (rpc) {
                            rpc.retrieveToken(function(parentToken) {
                                callback(parentToken);
                            });
                        }
                        else {
                            return callback('standalone_Mode');
                        }
                    };

                    var resizeFrame = function(height){
                    	rpc.resizeFrame(height);
					}

                    return {
                        retrieveToken: retrieveToken,
                        resizeFrame: resizeFrame
                    };
                }]);
        });
define([ './module', '../infrastructure/index' ], function(factories) {

    'use strict';

    factories.factory('DetailsService', [ '$resource', '$http', 'production', 'AppRegistryService', function($resource, $http, production, AppRegistryService) {

        // $RESource RESTfull call

        // JavaScript 'F'u'N'ction
        var fnLoadInquiry;
        var fnLoadMetaData;

        if (production) {


            /**
             * Calls the REST-Service to get a list of all pressure sensors.
             *
             * @return array with pressure sensors
             */
            fnLoadInquiry = function(args, success, error) {
            	var baseUrl =  AppRegistryService.getUrl('baseUrl');
                $http({
                    method : 'GET',
                    headers : args.header,
                    url : baseUrl+'/document/' + args.uuid
                }).success(success).error(error);
            };

            /**
             * Calls the REST-Service to load meta data.
             *
             * @return array with metaData
             */
            fnLoadMetaData = function(detailsParams, success, error) {

                return $resource(AppRegistryService.getUrl('baseUrl') + '/metadata', {}, {
                    query : {
                        method : 'GET',
                        isArray : true
                    }
                }).query(detailsParams, success, error);

            };
        } else {

            /**
             * ### MOCK ### Get the steering mechanism specification from the
             * mock.
             */
            fnLoadInquiry = function(detailsParams, success, error) {
                $http({
                    method : 'GET',
                    url : 'data/test/' + detailsParams.uuid + '.txt'
                }).success(success).error(error);
            };

            /**
             * ### MOCK ### Get the steering mechanism specification from the
             * mock.
             */
            fnLoadMetaData = function(detailsParams, success, error) {
                $http({
                    method : 'GET',
                    url : 'data/test/search/fuzzy/' + detailsParams.uuid + '.json'
                }).success(success).error(error);
            };
        }

        // common declarations
        return {
            fnLoadInquiry : fnLoadInquiry,
            fnLoadMetaData : fnLoadMetaData
        };
    } ]);
});

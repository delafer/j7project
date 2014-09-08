define([ './module', '../infrastructure/index' ], function(factories) {

    'use strict';

    factories.factory('HistAgencyService', [ '$resource', '$http', 'production', 'AppRegistryService', function($resource, $http, production, AppRegistryService) {

        // $RESource RESTfull call
        var resSearch;
        // JavaScript 'F'u'N'ction
        var fnSearch;

        var fnLoadCountries;

        var fnLoadLegalforms;

        if (production) {

            /**
             * Calls the REST-Service to get a list of agency history inquiries.
             *
             * @return array with metaData to fill/show a table
             */
            fnSearch = function(searchParams, success, error) {

                return $resource('http://localhost:9080/rest-service/metadata', {}, {
                    query : {
                        method : 'GET',
                        isArray : true
                    }
                }).query(searchParams, success, error);

            };

            /**
             * Calls the REST-Service to get a list of all pressure sensors.
             *
             * @return array with pressure sensors
             */
            fnLoadCountries = function(args, success, error) {
                $resource('http://localhost:9080/rest-service/items/countries', {}, {
                    query : {
                        method : 'GET',
                        isArray : true
                    }
                }).query(args, success, error);
            };

            /**
             * Calls the REST-Service to get a list of all pressure sensors.
             *
             * @return array with pressure sensors
             */
            fnLoadLegalforms = function(args, success, error) {
                $resource('http://localhost:9080/rest-service/items/legalForms', {}, {
                    query : {
                        method : 'GET',
                        isArray : true
                    }
                }).query(args, success, error);
            };

        } else {

            // ### MOCK ### NONE-PRODUCTION CODE
            resSearch = $resource('data/test.json', {}, {
                query : {
                    method : 'GET',
                    isArray : true
                }
            });

            /**
             * ### MOCK ### Calls the REST-Service to get a list of agency
             * history inquiries.
             *
             * @return array with metaData to fill/show a table
             */
            fnSearch = function(searchParams, success, error) {
                return resSearch.query({}, success, error);
            };

            /**
             * ### MOCK ### Get the steering mechanism specification from the
             * mock.
             */
            fnLoadCountries = function(detailsParams, success, error) {

                $http({
                    method : 'GET',
                    url : 'data/test/countries.json'
                }).success(success).error(error);
            };

            /**
             * ### MOCK ### Get the steering mechanism specification from the
             * mock.
             */
            fnLoadLegalforms = function(detailsParams, success, error) {

                $http({
                    method : 'GET',
                    url : 'data/test/legalforms.json'
                }).success(success).error(error);
            };

            // end else [end of MOCK code]
        }
        ;

        // common declarations
        return {
            fnSearch : fnSearch,
            fnLoadCountries : fnLoadCountries,
            fnLoadLegalforms : fnLoadLegalforms
        };

    } ]);
});
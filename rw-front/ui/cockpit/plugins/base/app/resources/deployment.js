'use strict';

module.exports = ['$resource', 'Uri', function ($resource, Uri) {
  return $resource(Uri.appUri('engine://engine/:engine/deployment/:deployment_id/:action/:resource_id/:data'), {
    deployment_id: '@deployment_id',
    resource_id: '@resource_id'
  }, {
    create: {
      method: 'POST',
      transformRequest: null,
      headers: { 'Content-type': undefined },
      params: { action: 'create' }
    },
    getResources: {
      method: 'GET',
      isArray: true,
      params: { action: 'resources' }
    },
    getResourceXml: {
      method: 'GET',
      params: { action: 'resources', data: 'data' }
    }
  });
}];

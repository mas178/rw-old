'use strict';

var fs = require('fs');

var template = fs.readFileSync(__dirname + '/process-definitions.html', 'utf8');

var transformResourceToXml = function (resource) {
  return Object.keys(resource).map(function (key) {
    return isFinite(key) ? resource[key] : '';
  }).join('');
};

var getResourceIdByName = function (resources, resourceName) {
  return resources.filter(function (resource) {
    return resourceName === resource.name;
  })[0].id;
};

var deploymentForm = function (processName, resourceName, resource, tenantId) {
  var formData = new FormData();
  var fileName = resourceName.replace(/\//g, "_");
  formData.append('deployment-source', 'process application');
  formData.append('deployment-name', processName);
  formData.append('data', new File([transformResourceToXml(resource)], fileName, { type: "application/octet-stream" }));
  formData.append('tenant-id', tenantId);
  return formData;
};

module.exports = ['ViewsProvider', function (ViewsProvider) {
  ViewsProvider.registerDefaultView('cockpit.processes.dashboard', {
    id: 'process-definition',
    label: 'Deployed Process Definitions',
    template: template,
    controller: [
      '$scope',
      'Views',
      'camAPI',
      'Deployment',
      function ($scope, Views, camAPI, Deployment) {

        var processInstancePlugins = Views.getProviders({ component: 'cockpit.processInstance.view' });

        $scope.hasHistoryPlugin = processInstancePlugins.filter(function (plugin) {
            return plugin.id === 'history';
          }).length > 0;

        var processData = $scope.processData.newChild($scope);

        $scope.hasReportPlugin = Views.getProviders({ component: 'cockpit.report' }).length > 0;

        var processDefinitionService = camAPI.resource('process-definition');
        var tenantService = camAPI.resource('tenant');
        var deploymentService = camAPI.resource('deployment');

        var updateProcessDefinitionList = function (err, data) {
          console.log('Start updateProcessDefinitionList', data.items);

          $scope.processDefinitionData = data.items;

          $scope.view = processData.observe('processDefinitionStatistics', function (processDefinitionStatistics) {
            $scope.statistics = processDefinitionStatistics;

            $scope.statistics.forEach(function (statistic) {
              var processDefId = statistic.definition.id;

              console.log('hoge1', statistic, processDefId, $scope.processDefinitionData);
              var foundIds = $scope.processDefinitionData.filter(function (pd) {
                console.log('hoge2', pd.id, processDefId);
                return pd.id === processDefId;
              });

              var foundObject = foundIds[0];
              if (!!foundObject) {
                foundObject.incidents = statistic.incidents;
                foundObject.instances = statistic.instances;
                console.log('hoge3', foundObject);
              } else {
                console.log('hoge4', foundIds);
              }
            });

            console.log('End updateProcessDefinitionList', $scope.processDefinitionData);
          });
        };

        processDefinitionService.list({ latest: true }, updateProcessDefinitionList);

        $scope.selected = 'list';
        $scope.selectTab = function (which) {
          $scope.selected = which;
        };

        // Set current user's tenant IDs on $scope
        $scope.userId = $scope.$root.authentication.name;
        tenantService.list({
          userMember: $scope.userId
        }, function (err, data) {
          $scope.tenantIds = data.map(function (tenant) {
            return tenant.id;
          });
        });

        // Deploy the process which selected on screen
        $scope.deploy = function (processDefinition) {
          console.log('Start deploy', processDefinition);
          // deploymentService.getResources(processDefinition.deploymentId, function (resources) {
          //   console.log('resources', resources);
          //   Deployment.getResourceXml({
          //     deployment_id: processDefinition.deploymentId,
          //     resource_id: getResourceIdByName(resources, processDefinition.resource)
          //   }).$promise.then(function (resource) {
          //     for (var i = 0; i < $scope.tenantIds.length; i++) {
          //       Deployment.create(
          //         deploymentForm(processDefinition.name, processDefinition.resource, resource, $scope.tenantIds[i])
          //       ).$promise.then(function (deployment) {
          //         processDefinitionService.list({ latest: true }, updateProcessDefinitionList);
          //       });
          //     }
          //   });
          // });

          Deployment.getResources({
            deployment_id: processDefinition.deploymentId
          }).$promise.then(function (resources) {
            console.log('resources', resources);
            Deployment.getResourceXml({
              deployment_id: processDefinition.deploymentId,
              resource_id: getResourceIdByName(resources, processDefinition.resource)
            }).$promise.then(function (resource) {
              for (var i = 0; i < $scope.tenantIds.length; i++) {
                Deployment.create(
                  deploymentForm(processDefinition.name, processDefinition.resource, resource, $scope.tenantIds[i])
                ).$promise.then(function (deployment) {
                  processDefinitionService.list({ latest: true }, updateProcessDefinitionList);
                });
              }
            });
          });
        };

        $scope.suspend = function (processDefinition) {
          deploymentService.delete(processDefinition.deploymentId, {}, function () {
            processDefinitionService.list({ latest: true }, updateProcessDefinitionList);
          });
        };
      }],
    priority: 0
  });
}];

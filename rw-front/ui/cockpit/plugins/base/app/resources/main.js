'use strict';

var angular = require('angular'),
  processDefinition = require('./processDefinition'),
  processInstance = require('./processInstance'),
  deployment = require('./deployment');

var ngModule = angular.module('cockpit.plugin.base.resources', []);

ngModule.factory('PluginProcessDefinitionResource', processDefinition);
ngModule.factory('PluginProcessInstanceResource', processInstance);
ngModule.factory('Deployment', deployment);

module.exports = ngModule;

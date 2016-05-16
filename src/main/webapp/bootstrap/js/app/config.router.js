define(['app','angularAMD'], function (app,angularAMD) {
	var root = WX.constants.path;
	app.run(['$rootScope', '$state', '$stateParams',function($rootScope,$state,$stateParams){
	      $rootScope.$state = $state;
	      $rootScope.$stateParams = $stateParams;        
	}]).config(['$stateProvider', '$urlRouterProvider',function ($stateProvider, $urlRouterProvider) {
	      $urlRouterProvider.otherwise('/upload');
	      $stateProvider.state('core', {
	          abstract: true,
	          url: '/',
	          templateUrl: root + 'pages/html/tpl/main.html',
	      });
	      $stateProvider.state('core.upload', angularAMD.route({
              url: "upload",
              templateUrl: root + 'pages/html/tpl/upload.html',
              controller : 'FileUploadCtrl',
              controllerUrl: "controllers1/chart"
          }));
	    		  
	      $stateProvider.state('core.server', angularAMD.route({
              url: "server/info",
              templateUrl: root + 'pages/html/tpl/server.info.html',
              controller : 'ServerInfoCtrl',
			  controllerAs : "vm",
              controllerUrl: "controllers1/ServerInfoCtrl"
          }));
	      
	      
	      
	}]);
});

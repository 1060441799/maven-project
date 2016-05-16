define(['app', 
        'config.router', 
        'im', 
        'websocket'], function (app, router, im, websocket) {
	app.controller('AppCtrl', ['$scope', '$localStorage', '$window', '$log', 'toaster', '$http', '$webim', function ($scope, $localStorage, $window, $log, toaster, $http, $webim) {
        $scope.app = {
            name: 'Next',
            version: '2.0.1',
            color: {
                primary: '#7266ba',
                info: '#23b7e5',
                success: '#27c24c',
                warning: '#fad733',
                danger: '#f05050',
                light: '#e8eff0',
                dark: '#3a3f51',
                black: '#1c2b36'
            },
            settings: {
                themeID: 1,
                navbarHeaderColor: 'bg-index-header',
                navbarCollapseColor: 'bg-index-header',
                asideColor: 'bg-index-aside',
                headerFixed: true,
                asideFixed: false,
                asideFolded: false,
                asideDock: false,
                container: false
            }
        }
        //if (angular.isDefined($localStorage.settings)) {
        //    $scope.app.settings = $localStorage.settings;
        //} else {
        //    $localStorage.settings = $scope.app.settings;
        //}
        //$scope.$watch('app.settings', function () {
        //    if ($scope.app.settings.asideDock
        //        && $scope.app.settings.asideFixed) {
        //        $scope.app.settings.headerFixed = true;
        //    }
        //    $localStorage.settings = $scope.app.settings;
        //}, true);
        localStorage.removeItem("settings");

        layer.close(WX.index);
        $scope.server = {};
        $scope.server.message = 'Welcome to visit this application.';

        $scope.testWebsocket = function () {
            var websocket;
            if (websocket != null) {
                websocket.onopen = function (result) {
                    toaster.pop('success', 'Websocket open', 'WebSocket已打开.');
                };
                websocket.onerror = function (result) {
                    toaster.pop('error', 'Websocket error', '未与服务器链接.');
                };
                websocket.onclose = function (result) {
                    toaster.pop('error', 'Websocket close', 'WebSocket已关闭.');
                };
            }
        };
        $scope.openChat = function (size) {
            $webim.open();
        };
//        $http.get(WX.constants.path + "login/left").success(function (response) {
//            console.info("response", response.obj);
//            if (response.success) {
//                WX.menuMap = response.obj.map;
//                WX.configs = response.obj.configs;
//                _.each(WX.menuMap[0], function (menu) {
//                    _.each(menu.functions, function (c) {
//                        c.functionUrl = WX.constants.path + c.functionUrl;
//                    });
//                });
//                $scope.items = WX.menuMap[0];
//            }
//        });
    }]);
});

define([
        // 'global',
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ngStorage',
        'ui.router',
        'ui.utils',
//        'textAngular',
        'ngImgCrop',
        'ui.bootstrap.tpls',
        // 'services/ui.window',
        'services/ui.webim',
        'services/ui.websocket',
        'directive/ui.webim.message',
        'directive/ui.validate',
        'directive/ui.webim.list',
        'toaster',
        'highcharts-ng',
        // 'angularFileUpload',
        'sanitize'], function () {
        var app = angular.module('app', [
            // 'angularFileUpload',
            'ngAnimate',
            'ngCookies',
            'ngResource',
            'ngSanitize',
            'ngStorage',
            'ui.router',
            'ui.utils',
            'angularFileUpload',
            'ngImgCrop',
            'ui.bootstrap',
            // 'ui.window',
            'ui.websocket',
            'ui.webim',
            'ui.webim.message',
            'ui.webim.list',
            'toaster',
            'highcharts-ng',
            'ui.validate'
        ]);
        app.config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
            function ($controllerProvider, $compileProvider, $filterProvider, $provide) {
                app.controller = $controllerProvider.register;
                app.directive = $compileProvider.directive;
                app.filter = $filterProvider.register;
                app.factory = $provide.factory;
                app.service = $provide.service;
                app.constant = $provide.constant;
                app.value = $provide.value;
            }
        ]);
        return app;
    });
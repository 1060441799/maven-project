define([
    // 'global',
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngStorage',
    'ui.router',
    'ui.utils',
    'ngImgCrop',
    'ui.bootstrap.tpls',
    'directive/ui.validate',
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
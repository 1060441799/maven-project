$(function () {
    var registerRequirejsAdapter = function (scope, urlRoot, config) {
        if (urlRoot[urlRoot.length - 1] != "/") {
            urlRoot += '/';
        }
        if (null == scope.raw_require) {
            scope.raw_require = scope.require;
        }
        if (null == scope.raw_define) {
            scope.raw_define = scope.define;
        }
        (function () {
            var fix_url = function (url) {
                while (url != url.replace('//', '/')) {
                    url = url.replace('//', '/');
                }
            };
            var build_url = function (url) {
                return fix_url(urlRoot + '/' + url + '?r=' + Version);
            };
            var find_shim = function (url) {
                for (var shim in (config.shim || {})) {
                    if (url === shim) {
                        return shim;
                    }
                }
                for (var path in (config.paths || {})) {
                    if (url === path) {
                        return config.paths[path] + '.js';
                    }
                }
            };
            var find_raw_files = function (url) {
                var ret = [], shim;
                if (shim = find_shim(url)) {
                    ret = [shim];
                } else {
                    if (url.substr(url.length - 3, 3) != '.js') {
                        url += '.js';
                    }
                    var path = build_url(url);
                    ret.push(path);
                }
                return ret;
            }
            var get_require_files = function (files) {
                if (!(files instanceof Array)) {
                    files = [files];
                }
                require_files = [];
                for (var i = 0; i < files.length; i++) {
                    var url = find_raw_files(files[i]);
                    require_files = require_files.concat(url);
                }
                return require_files;
            }
            scope.require = function (files, func) {
                require_files = get_require_files(files);
                var inner = function (files) {
                    if (files.length > 1) {
                        scope.raw_require([files[0]], function () {
                            inner(files.slice(1));
                        });
                    } else {
                        scope.raw_require([files[0]], func);
                    }
                }
                inner(require_files);
            }
            scope.define = function (files, func) {
                if (arguments.length == 2) {
                    require_files = get_require_files(files);
                    scope.raw_define(require_files, func);
                } else {
                    scope.raw_define.apply(this, arguments);
                }
            }
        })();
        requirejs.config(config);
    }
    require.config({
        baseUrl: WX.constants.path,
        paths: {
            'app': 'bootstrap/js/app/app',
            'app.login': 'bootstrap/js/app/app.login',
            'controllers': 'pages/newframework/static/js/controllers',
            'controllers1': 'pages/html/js/controllers',
            'directive': 'pages/newframework/static/js/directive',
            'websocket': 'pages/newframework/static/js/websocket/websocket',
            'services': 'pages/newframework/static/js/services',
            'global': 'bootstrap/js/global',
            'ngAnimate': 'bootstrap/js/angular/angular-animate',
            'ngCookies': 'bootstrap/js/angular/angular-cookies',
            'ngResource': 'bootstrap/js/angular/angular-resource',
            'ngSanitize': 'bootstrap/js/angular/angular-sanitize',
            'ngStorage': 'bootstrap/js/angular/angular-ui-router',
            'ui.router': 'bootstrap/js/angular/ngStorage',
            'ui.utils': 'bootstrap/js/angular/ui-utils',
//            'oc.lazyLoad': 'bootstrap/js/angular/ocLazyLoad',
            // 'angular': 'bootstrap/js/angular/angular',
            'chosen': 'bootstrap/js/chosen/chosen.jquery',
            'filestyle': 'bootstrap/js/bootstrap-filestyle/src/bootstrap-filestyle',
            'wysiwyg': 'bootstrap/js/bootstrap-wysiwyg/bootstrap-wysiwyg',
            'tagsinput': 'bootstrap/js/bootstrap-tagsinput/dist/bootstrap-tagsinput',
            'textAngular': 'bootstrap/js/textAngular/dist/textAngular.min',
            'sanitize': 'bootstrap/js/textAngular/dist/textAngular-sanitize.min',
            'jquery.cookie': 'bootstrap/js/jquery.cookie',
            "angularAMD": "bootstrap/js/angular/angularAMD",
            "ngload": "bootstrap/js/angular/ngload",
            "toaster": "bootstrap/js/angularjs-toaster/toaster",
            // 'jquery': 'bootstrap/js/jquery-2.1.3',
            // 'underscore': 'bootstrap/js/underscore',
            // 'layer': 'bootstrap/js/layer',
            'bootstrap': 'bootstrap/js/bootstrap',
            'ngImgCrop': 'bootstrap/js/ngImgCrop/compile/minified/ng-img-crop',
            'config.router': 'bootstrap/js/app/config.router',
            'config.lazyload': 'bootstrap/js/app/config.lazyload',
            'im': 'bootstrap/js/im',
            'ui.bootstrap.tpls': 'bootstrap/js/angular-bootstrap/ui-bootstrap-tpls',
            'draggable': 'pages/newframework/static/js/directive/draggable',

            'exporting': 'bootstrap/js/Highcharts/exporting.src',
            'highcharts': 'bootstrap/js/Highcharts/highcharts.src',
            'highcharts-more': 'bootstrap/js/Highcharts/highcharts-more.src',
            'highcharts-ng': 'bootstrap/js/Highcharts/highcharts-ng',
//            "route.config": "bootstrap/js/angular/route-config",
//            "lazy.directives": "bootstrap/js/angular/lazy-directives",
            // 'angularFileUpload' : 'bootstrap/js/angular-file-upload/angular-file-upload'
        },
        shim: {
            // 'underscore': {exports: '_'},
            // 'bootstrap': {deps: ['jquery'], exports: 'Bootstrap'},
            // 'layer': {deps: ['jquery']},
            // 'jquery.cookie': {deps: ['jquery', 'layer']},
            // 'filestyle' : {deps: ['jquery']},
            // 'wysiwyg' : {deps: ['jquery']},
            // 'tagsinput' : {deps: ['jquery']},
            // 'chosen' :{deps: ['jquery']},
            // 'textAngular' : {deps: ['angular']},
            // 'sanitize' : {deps: ['angular']},
            // 'ngAnimate': {deps: ['angular']},
            // 'ngCookies': {deps: ['angular']},
            // 'ngResource': {deps: ['angular']},
            // 'ngSanitize': {deps: ['angular']},
            // 'ngStorage': {deps: ['angular']},
            // 'ui.router': {deps: ['angular']},
            // 'ui.utils': {deps: ['angular']},
            // 'oc.lazyLoad':{deps: ['angular']},
            // 'ngImgCrop' : {deps: ['angular']},
            // 'angularFileUpload' : {deps: ['angular']},
//        	'config.router' : {deps: ['app']},
//        	'config.lazyload' : {deps: ['app']}
            'highcharts-ng' : {deps: ['exporting']},
            'highcharts-more' : {deps: ['highcharts']},
            'exporting': {deps: ['highcharts-more']},
            "ngload": ["angularAMD"],
            'app.login': {deps: ['filestyle', 'wysiwyg', 'tagsinput', 'chosen', 'bootstrap']},
            'app': {deps: ['filestyle', 'wysiwyg', 'tagsinput', 'chosen', 'bootstrap']}
        }
    });
});
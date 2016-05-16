require.config({

    baseUrl: WX.constants.path,
    
	// alias libraries paths
    paths: {
        'angular': 'bootstrap/js/angular/angular',
        'angular-route': 'bootstrap/js/angular/angular-ui-router',
        'ui.router': 'bootstrap/js/angular/angular-ui-router',
        'angularAMD': 'bootstrap/js/angular/angularAMD',
        'ngload': 'bootstrap/js/angular/ngload',
        'ngAnimate': 'bootstrap/js/angular/angular-animate',
        'ngCookies': 'bootstrap/js/angular/angular-cookies',
        'ngResource': 'bootstrap/js/angular/angular-resource',
        'ngSanitize': 'bootstrap/js/angular/angular-sanitize',
        'ngStorage': 'bootstrap/js/angular/ngStorage',
        'ui.utils': 'bootstrap/js/angular/ui-utils',
        
        'SigninFormController': 'pages/newframework/static/js/signin',
    },

    // Add angular modules that does not support AMD out of the box, put it in a shim
    shim: {
        'angularAMD': ['angular']
    },

    // kick start application
    deps: ['bootstrap/js/app/app']
});

$(function() {
	require([ 'controllers/IndexCtrl', 'controllers1/bootstrap' ], function() {
		angular.bootstrap(document, [ 'app' ]);
	});
});
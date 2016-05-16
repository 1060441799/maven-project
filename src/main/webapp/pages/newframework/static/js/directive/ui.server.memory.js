/**
 * Created by xm on 2016/4/26.
 */
define(['exporting', 'app'], function (exporting, app) {
    app.directive('uiServerMemory', function () {
            return {
                restrict: 'EA',
                controller: 'ServerInfoCtrl',
                template: "<div></div>",
                transclude: true,
                replace: true,
                scope:{
                    type : "@"
                },
                link: function ($scope, element, attrs) {
                    if ($scope.type === "spline") {
                        element.highcharts($scope.server.spline);
                    }
                    if ($scope.type === "column") {
                        element.highcharts($scope.server.column);
                    }
                    if ($scope.type === "gauge") {
                        element.highcharts($scope.server.gauge.data, $scope.server.gauge.fn);
                    }
                }
            };
        })
});
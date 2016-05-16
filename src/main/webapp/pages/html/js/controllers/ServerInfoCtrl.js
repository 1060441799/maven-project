/**
 * Created by xm on 2016/4/26.
 */
define(['app'], function (app) {
    app.controller('ServerInfoCtrl', ['$scope', '$data', '$websocket', 'highchartsNG', function ($scope, $data, $websocket) {
        $scope.info = {};
        $scope.info.jvmMaxMemory = [];
        $scope.info.jvmFreeMemory = [];
        $scope.info.time = [];

        $scope.chartSeries = [{
            name: 'FreeMemory',
            data: $scope.info.jvmFreeMemory
        }, {
            name: 'MaxMemory',
            data: $scope.info.jvmMaxMemory
        }];
        $scope.chartConfig = {
            options: {
                chart: {
                    type: 'spline',
                    animation: Highcharts.svg, // don't animate in old IE
                    marginRight: 5,
                }
            },
            tooltip: {
                valueSuffix: 'MB'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },

            xAxis: {
                categories: $scope.info.time
            },
            yAxis: {
                title: {
                    text: 'MB'
                },
                plotLines: [{
                    value: 0,
                    color: '#808080'
                }],
            },
            series: $scope.chartSeries,
            title: {
                text: 'JVM Memory Info'
            },
            // subtitle: {
            //     text: $scope.info.jvmMaxMemory
            // },
            loading: false,
            size: {}
        }

        $scope.$on("SERVERTOUSERS", function (event, data) {
            var time = moment(data.messageDate).format('HH:mm:ss');
            var jvmFreeMemory = Math.round(data.messageData.jvmFreeMemory / 1024 / 1024);
            var jvmMaxMemory = Math.round(data.messageData.jvmMaxMemory / 1024 / 1024);

            $scope.$apply(function () {
                if ($scope.info.jvmFreeMemory.length === 10
                    && $scope.info.time.length === 10) {
                    $scope.info.jvmFreeMemory.shift();
                    $scope.info.time.shift();
                    $scope.info.jvmMaxMemory.shift();
                } else {
                    $scope.info.time.push(time);
                    $scope.info.jvmFreeMemory.push(jvmFreeMemory);
                    $scope.info.jvmMaxMemory.push(jvmMaxMemory);
                }

                console.info("$scope.info.time", $scope.info.time);
                console.info("$scope.info.jvmFreeMemory", $scope.info.jvmFreeMemory);
            });
        });
    }
    ])
    ;
})
;
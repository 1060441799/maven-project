define(['app','directive/RepeatDoneDirective'], function (app,RepeatDoneDirective) {
    'use strict';
    app.controller('MainController', ['$scope', '$http', function ($scope, $http) {

        $scope.doSomething = function () {
            layer.close(WX.index);
            $('.root').show();
        }

        $http.get(WX.constants.path + "login/left").success(function (response) {
            console.info("response", response.obj);
            if (response.success) {
                WX.menuMap = response.obj.map;
                WX.configs = response.obj.configs;
                _.each(WX.menuMap[0], function (menu) {
                    _.each(menu.functions, function (c) {
                        c.functionUrl = WX.constants.path + c.functionUrl;
                    });
                });
                $scope.items = WX.menuMap[0];
            }
        });

        // ui evnet
        +function ($) {
            $(function () {
                $(document).on('click', '[ui-toggle]', function (e) {
                    e.preventDefault();
                    var $this = $(e.target);
                    $this.attr('ui-toggle') || ($this = $this.closest('[ui-toggle]'));
                    var $target = $($this.attr('target')) || $this;
                    $target.toggleClass($this.attr('ui-toggle'));
                }).on('click', '[ui-nav] a', function (e) {
                    var $this = $(e.target), $active;
                    $this.is('a') || ($this = $this.closest('a'));
                    $active = $this.parent().siblings(".active");
                    $active && $active.toggleClass('active').find('> ul:visible').slideUp(200);
                    ($this.parent().hasClass('active') && $this.next().slideUp(200)) || $this.next().slideDown(200);
                    $this.parent().toggleClass('active');
                    $this.next().is('ul') && e.preventDefault();
                });
            });
        }(jQuery);

        $scope.logout = function () {
            layer.confirm('确认退出系统？', {
                icon: 3,
                title: '提示',
                shade: 0.8,
                btn: ['Yes', 'No']
            }, function () {
                $http.get(WX.constants.path + 'login/logout').success(function (data) {
                    if (data.success) {
                        var url = location.href.substring(0, location.href.indexOf("#"));
                        location.href = url;
                    }
                });
            },
            function (index) {
                layer.close(index);
            });
        }
    }]);
});
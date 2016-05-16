define(['app.login', 'jquery.cookie'], function (app) {
    'use strict';
    app.controller('SigninFormController', ['$scope', '$http', '$timeout', '$q', function ($scope, $http, $timeout, $q) {
        $scope.user = {};
        $scope.userNameError = null;
        $scope.passwordError = null;
        $scope.randCodeError = null;
        $scope.otherError = null;
        //设置cookie
        function setCookie() {
            if (!!$scope.user.setCookie) {
                $.cookie("userName", $scope.user.userName, {expires: 7});
                $.cookie("password", $scope.user.password, {expires: 7});
            } else {
                $.cookie("userName", null);
                $.cookie("password", null);
            }
        }

        //读取cookie
        function getCookie() {
            var userName = $.cookie("userName");
            var password = $.cookie("password");
            if (userName && password) {
                $scope.user.userName = userName;
                $scope.user.password = password;
                $scope.user.setCookie = true;
                $('#sigin_icode').find('input').attr("autofocus", "autofocus");
            } else {
                $scope.user.setCookie = false;
                $('#userAccount').find('input').attr("autofocus", "autofocus");
            }
        }

        $scope.notBlackListed = function (value) {
            var blacklist = ['bad@domain.com', 'verybad@domain.com'];
            return blacklist.indexOf(value) === -1;
        };

        $scope.title = "登录 " + WX.constants.APPNAME;

        //WX.checkField([{
        //    rootId : 'root',
        //    rootElement: $('#userPassword'),
        //    $scope: $scope,
        //    validate: {
        //        isNull: {valid: true, errorMessage: "不能为空"},
        //        reg: {content: /^[0-9A-Za-z]{6,12}$/, errorMessage: "格式错误"}
        //    },
        //    fieldError: 'passwordError'
        //}, {
        //    rootId : 'root',
        //    $http: $http,
        //    checkUrl: WX.constants.path + "login/checkUserName?userName=",
        //    rootElement: $('#userAccount'),
        //    $scope: $scope,
        //    validate: {
        //        isNull: {valid: true, errorMessage: "不能为空"},
        //        reg: {content: /^[0-9A-Za-z]{4,12}$/, errorMessage: "格式错误"}
        //    },
        //    fieldError: 'userNameError',
        //    type: 'signin'
        //}, {
        //    rootId : 'root',
        //    $http: $http,
        //    checkUrl: WX.constants.path + "login/checkRandCode?randCode=",
        //    rootElement: $('#sigin_icode'),
        //    $scope: $scope,
        //    validate: {
        //        isNull: {valid: true, errorMessage: "不能为空"}
        //    },
        //    fieldError: 'randCodeError'
        //}], function (result) {
        //    console.info('result', result);
        //});
        getCookie();
        $scope.login = function () {
            if ($scope.passwordError || $scope.randCodeError || $scope.userNameError || !$scope.user.userName || !$scope.user.password || !$scope.user.randCode) {
                return;
            }
            var formData = {
                "userName": $scope.user.userName,
                "password": $scope.user.password,
                "randCode": $scope.user.randCode
            };
            $scope.$invalid = true;
            var url = location.href.substring(0, location.href.indexOf("#"));
            var href = url;
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: WX.constants.path + 'login/checkuser',
                data: formData,
                error: function () {
                    $scope.$invalid = false;
                    $scope.otherError = "server error.";
                    $('.container').find('.bg-danger').fadeIn();
                    setTimeout(function () {
                        $('.container').find('.bg-danger').fadeOut();
                    }, 3000);
                },
                success: function (data) {
                    try {
                        console.info(data);
                        var d = data;
                        if (d.success) {
                            var index = layer.load(1, {
                                shade: false
                            });
                            $scope.otherError = d.msg;
                            $('.container').find('.text').removeClass('bg-danger').addClass("bg-success").fadeIn();
                            setTimeout(function () {
                                setCookie();
                                layer.close(index);
                                $('.container').find('.text').fadeOut();
                                location.href = href;
                            }, 1000);
                        } else {
                            if (d.msg == "a") {
                                // $.dialog.confirm("数据库无数据,是否初始化数据?", function () {
                                //     location = "init.jsp";
                                // }, function () {
                                // });
                            } else if (d.msgType == 1) {
                                $scope.userNameError = d.msg;
                                $('#userAccount').find('.passport-note').fadeIn();
                            } else if (d.msgType == 3) {
                                $scope.randCodeError = d.msg;
                                $('#sigin_icode').find('.passport-note').fadeIn();
                            } else if (d.msgType == 2) {
                                $scope.passwordError = d.msg;
                                $('#userPassword').find('.passport-note').fadeIn();
                            } else {
                                $scope.otherError = d.msg;
                                $('.container').find('.bg-danger').fadeIn();
                                setTimeout(function () {
                                    $('.container').find('.bg-danger').fadeOut();
                                }, 3000);
                            }
                        }
                    } catch (e) {
                        $scope.otherError = "Program error!";
                        $('.container').find('.bg-danger').fadeIn();
                        setTimeout(function () {
                            $('.container').find('.bg-danger').fadeOut();
                        }, 3000);
                    }
                    $scope.$invalid = false;
                }
            });
        };
    }]);
});
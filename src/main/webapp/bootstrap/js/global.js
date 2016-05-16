/**
 * Created by xm on 2016/3/17.
 */
$(function () {
    Core.createNamespaces("WX.constants");
    Core.createNamespaces("WX.require");
    Core.createNamespaces("WX.WebSocket");
    var base = document.getElementsByTagName('base'),
        title = document.getElementsByTagName('title');
    if (base.length) {
        WX.constants.path = base[base.length - 1].href;
    } else {
        throw "No set base url.";
    }
    if (title.length) {
        var name = title[0].innerText;
        var version = title[0].getAttribute('data-version');
        WX.constants.APPVERSION = version;
        WX.constants.APPNAME = name;
    }
    // WX.WebSocket.getWebSocket = function () {
    //     var websocket = new SockJS("/websocket");
    //     WX.WebSocket.getWebSocket.websocket = function () {
    //         return websocket;
    //     }
    //     return websocket;
    // }
    WX.require.requireAfter = function () {
        angular.bootstrap(document, ['app']);
        console.info('After require...');
    }
    Core.after('requireAfter', function () {
        // angular.element(document).ready(function() {
        // });
        console.info('After aop...');
        $('.root').fadeIn();
        layer.close(WX.index);
    }, WX);
    WX.index = layer.load(1, {
        shade: false
    });
    $(document).ajaxStart(function () {
        index = layer.load(1, {
            shade: false
        });
    });
    $(document).ajaxStop(function () {
        if (index) {
            layer.close(index);
        }
    });
    $(document).ajaxError(function () {
        layer.msg("Ajax error!", {
            time: 3000,
            icon: 2,
            shift: 6,
            offset: '0px'
        });
    });
    $.ajaxSetup({
        timeout: 10000
    });
    var MessageBus = WX.MessageBus = (function () {
        var clientList = {};
        var listen, trigger, remove;
        listen = function (key, fn) {
            if (!clientList[key]) {
                clientList[key] = [];
            }
            clientList[key].push(fn);
        };
        trigger = function () {
            var key = [].shift.call(arguments);
            var fns = clientList[key];
            if (!fns || fns.length === 0) {
                return false;
            }
            for (var i = 0, fn; fn = fns[i++];) {
                fn.apply(this, arguments);
            }
        };
        remove = function (key, fn) {
            var fns = clientList[key];
            // key对应的消息么有被人订阅
            if (!fns) {
                return false;
            }
            // 没有传入fn(具体的回调函数), 表示取消key对应的所有订阅
            if (!fn) {
                fns && (fns.length = 0);
            } else {
                // 反向遍历
                for (var i = fns.length - 1; i >= 0; i--) {
                    var _fn = fns[i];
                    if (_fn === fn) {
                        // 删除订阅回调函数
                        fns.splice(i, 1);
                    }
                }
            }
        };
        return {
            listen: listen,
            trigger: trigger,
            remove: remove
        }
    }());
// 使用方法
    MessageBus.listen("login", function (a) {
        alert(a);
    });

    $(document).keydown(function (e) {
        if (e.keyCode == 13) {
            $('button').click();
        }
    });
    $('.list-group-item').find('input').focus(function () {
        $(this).parent().addClass("list-group-item-focus");
    }).on('blur', function () {
        $(this).parent().removeClass("list-group-item-focus");
    });
    function reloadRandCodeImage() {
        this.src = WX.constants.path + 'randCodeImage?a=' + new Date().getTime();
    }

// 刷新验证码
    $('.imgcode img').on('click', function () {
        reloadRandCodeImage.call(this);
    });
    $('.imgcode > i').on('click', function () {
        reloadRandCodeImage.call($('.imgcode img')[0]);
    });
    WX.checkField = function (options, callback) {
        var result = {};
        if (options) {
            _.map(options, function (option) {
                handle(option);
            });
        } else {
            throw new Error("Parameter is null.");
        }
        function handle(option) {
            var scope = option.scope;
            var $root = option.rootElement;
            var isNull = option.validate.isNull;
            var reg = option.validate.reg;
            option.rootElement.find('input').on('focus', function () {
                $root.find('.passport-error-text').fadeOut();
            }).on('blur', function () {
                if (option.$http && option.checkUrl) {
                    if (this.value) {
                        if (reg && reg.content) {
                            if (!reg.content.test(this.value)) {
                                option.callback(reg.errorMessage);
                                $root.find('.passport-note').fadeIn();
                            } else {
                                $root.find('.passport-note').fadeOut();
                                option.$http.get(option.checkUrl + this.value).success(function (d) {
                                    if (option.type === 'signin') {
                                        if (d.success) {
                                            // if(!scope.$$phase) {
                                                // scope.$apply(function () {
                                                //     scope.error[option.fieldError] = "不存在";
                                                // });
                                            // }
                                            option.callback("不存在");
                                            $root.find('.passport-note').fadeIn();
                                        } else {
                                            // scope.$apply(function(){
                                            //     scope.error[option.fieldError] = null;
                                            // });
                                            option.callback(null);
                                            $root.find('.passport-note').fadeOut();
                                        }
                                    } else {
                                        if (!d.success) {
                                            // scope.$apply(function(){
                                            //     scope.error[option.fieldError] = d.msg;
                                            // });
                                            option.callback(d.msg);
                                            $root.find('.passport-note').fadeIn();
                                        } else {
                                            // scope.$apply(function(){
                                            //     scope.error[option.fieldError] = null;
                                            // });
                                            option.callback(null);
                                            $root.find('.passport-note').fadeOut();
                                        }
                                    }
                                });
                            }
                        } else {
                            option.$http.get(option.checkUrl + this.value).success(function (d) {
                                if (option.type === 'signin') {
                                    if (d.success) {
                                        // scope.$apply(function(){
                                        //     scope.error[option.fieldError] = "不存在";
                                        // });
                                        option.callback("不存在");
                                        $root.find('.passport-note').fadeIn();
                                    } else {
                                        // scope.$apply(function(){
                                        //     scope.error[option.fieldError] = null;
                                        // });
                                        option.callback(null);
                                        $root.find('.passport-note').fadeOut();
                                    }
                                } else {
                                    if (!d.success) {
                                        // scope.$apply(function(){
                                        //     scope.error[option.fieldError] = d.msg;
                                        // });
                                        option.callback(d.msg);
                                        $root.find('.passport-note').fadeIn();
                                    } else {
                                        // scope.$apply(function () {
                                        //     scope.error[option.fieldError] = null;
                                        // });
                                        option.callback(null);
                                        $root.find('.passport-note').fadeOut();
                                    }
                                }
                            });
                        }
                    } else {
                        // scope.$apply(function(){
                        //     scope.error[option.fieldError] = isNull.errorMessage;
                        // });
                        option.callback(isNull.errorMessage);
                        $root.find('.passport-note').fadeIn();
                    }
                } else {
                    if (isNull.valid) {
                        if (!this.value) {
                            // scope.$apply(function(){
                            //     scope.error[option.fieldError] = isNull.errorMessage;
                            // });
                            option.callback(isNull.errorMessage);
                            $root.find('.passport-note').fadeIn();
                        } else {
                            $root.find('.passport-note').fadeOut();
                        }
                    }
                    if (reg.content) {
                        if (!reg.content.test(this.value)) {
                            // scope.$apply(function(){
                            //     scope.error[option.fieldError] = reg.errorMessage;
                            // });
                            option.callback(reg.errorMessage);
                            $root.find('.passport-note').fadeIn();
                        } else {
                            $root.find('.passport-note').fadeOut();
                        }
                    }
                }
                if (option.callback && typeof option.callback === "function") {
                    option.callback(result);
                }
            });
        }
    }
});
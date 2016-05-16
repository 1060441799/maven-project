/**
 * Created by xm on 2016/4/27.
 */
define(["toaster"], function (toaster) {
    angular.module('ui.websocket', [])
        .run(['$websocket', "toaster", "$rootScope", function ($websocket, toaster, $rootScope) {
            $websocket.onopen(function () {
                toaster.pop('success', 'Websocket', "成功打开Websocket连接。");
            });
            $websocket.onerror(function () {
                toaster.pop('error', 'Websocket', "打开Websocket失败。");
            });
            $websocket.onmessage(function (result) {
                var data = JSON.parse(result.data);
                var topic = "";
                switch (data.topic) {
                    case 1 :
                        topic = "SERVERTOONEUSER";
                        break;
                    case 2 :
                        topic = "SERVERTOUSERS";
                        break;
                    case 3 :
                        topic = "SERVERTOROOM";
                        break;
                    case 4 :
                        topic = "USERTOUSER";
                        break;
                    case 5 :
                        topic = "USERTOROOM";
                        break;
                }
                console.info("topic", topic);
                $rootScope.$broadcast(topic, data);
            });
        }])
        .factory('$websocket', ['toaster', function (toaster) {
            var websocket = new SockJS("/websocket");
            return {
                getWsInstance: function () {
                    return websocket;
                },
                onopen: function (fn) {
                    websocket.onopen = function (result) {
                        if (fn) {
                            fn(result);
                        }
                    };
                },
                onmessage: function (fn) {
                    websocket.onmessage = function (result) {
                        if (fn) {
                            fn(result);
                        }
                    };
                },
                onerror: function (fn) {
                    websocket.onerror = function (result) {
                        if (fn) {
                            fn(result);
                        }
                    };
                },
                onclose: function (fn) {
                    websocket.onclose = function (result) {
                        if (fn) {
                            fn(result);
                        }
                    };
                },
                send: function (obj) {
                    if (websocket != null) {
                        websocket.send(obj);
                        toaster.pop('success', 'Websocket data', obj);
                    } else {
                        toaster.pop('error', 'Websocket error', '未与服务器链接.');
                    }
                }
            };
        }])
});
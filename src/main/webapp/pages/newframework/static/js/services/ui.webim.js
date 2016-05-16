/**
 * Created by xm on 2016/4/24.
 */
define(['services/ui.window', 'toaster'], function () {
    angular.module("ui.webim", ["ui.websocket", "ui.window", "ui.webim.main", "ui.webim.templates", "toaster"]);

    angular.module("ui.webim.templates", ["main.html", "chat.html"]);

    angular.module("main.html", []).run(["$templateCache", function ($templateCache) {
        $templateCache.put("main",
            "<div class=\"chat\">\n" +
            "   <tabset class=\"nav-tabs-alt\" justified=\"true\">\n" +
            "   <tab>\n" +
            "   <tab-heading>\n" +
            "   <i class=\"glyphicon glyphicon-user text-md text-muted wrapper-sm\"></i>\n" +
            "   </tab-heading>\n" +
            "   <div class=\"form-group\" style=\"padding: 5px;margin-bottom: 10px;margin-top: 10px;\">\n" +
            "   <input type=\"text\" class=\"form-control rounded\" placeholder=\"搜索当前所在在线用户\" ng-keypress=\"searchUser($event)\">\n" +
            "   </div>\n" +
            "   <div class=\"wrapper-md chat-all-user\" style=\"padding-top: 0\">\n" +
            "   <ul class=\"list-group no-bg pull-in\">\n" +
            "   <div webim-list ng-repeat=\"user in chatList.all\" user=\"user\" love=\"loveThisUser(user)\" open=\"openChatDetailWindow(user)\"></div>\n" +
            "   </ul>\n" +
            "   <div class=\"text-center\">\n" +
            "   <button class=\"btn btn-success\" ui-toggle-class=\"show inline\" target=\"#spin\">\n" +
            "   <span class=\"text\">更多在线用户</span>\n" +
            "   <span class=\"text-active\">加载中...</span>\n" +
            "   </button>\n" +
            "   <i class=\"fa fa-spin fa-spinner hide\" id=\"spin\"></i>\n" +
            "   </div>\n" +
            "   </div>\n" +
            "   </tab>\n" +
            "   <tab>\n" +
            "   <tab-heading>\n" +
            "   <i class=\"glyphicon glyphicon-comment text-md text-muted wrapper-sm\"></i>\n" +
            "   </tab-heading>\n" +
            "   <div class=\"form-group\" style=\"padding: 5px;margin-bottom: 10px;margin-top: 10px;\">\n" +
            "   <input type=\"text\" class=\"form-control rounded\" placeholder=\"搜索关注列表\">\n" +
            "   </div>\n" +
            "   <div class=\"wrapper-md chat-all-user\" style=\"padding-top: 0\">\n" +
            "   <ul class=\"list-group pull-in auto\">\n" +
            "   <div webim-list ng-repeat=\"user in chatList.all | filter:{islove: true}\" user=\"user\" love=\"loveThisUser(user)\" open=\"openChatDetailWindow(user)\"></div>\n" +
            "   </ul>\n" +
            "   </div>\n" +
            "   </tab>\n" +
            "   <tab>\n" +
            "   <tab-heading>\n" +
            "   <i class=\"glyphicon glyphicon-transfer text-md text-muted wrapper-sm\"></i>\n" +
            "   </tab-heading>\n" +
            "   <div class=\"wrapper-md chat-all-user\" style=\"height: 513px;\">\n" +
            "   <div class=\"m-b-sm text-md\">动态</div>\n" +
            "   <ul class=\"list-group list-group-sm list-group-sp list-group-alt auto m-t\">\n" +
            "   <li class=\"list-group-item\">\n" +
            "   <span class=\"text-muted\">张三的动态 at 3:00 pm</span>\n" +
            "   <span class=\"block text-md text-info\">B 15,000.00</span>\n" +
            "   </li>\n" +
            "   </ul>\n" +
            "   </div>\n" +
            "   </tab>\n" +
            "   </tabset>\n" +
            "   <div class=\"footer\">\n" +
            "   <ul>\n" +
            "   <li ng-click=\"chatSetting()\"><i class=\"icon-settings\"></i></li>\n" +
            "   <li ng-click=\"chatScrollToTop()\"><i class=\"icon-arrow-up\"></i></li>\n" +
            "   <li ng-click=\"chatBell()\"><i class=\"icon-bell\"></i></li>\n" +
            "   <li ng-click=\"chatRefresh()\"><i class=\"icon-refresh\"></i></li>\n" +
            "   <li ng-click=\"loveAll()\"><i class=\"fa fa-heart\"></i></li>\n" +
            "   <li ng-click=\"unloveAll()\"><i class=\"fa fa-heart-o\"></i></li>\n" +
            "   </ul>\n" +
            "   </div>\n" +
            "   </div>");
    }]);

    angular.module("chat.html", []).run(["$templateCache", function ($templateCache) {
        $templateCache.put("chat",
            "   <div class=\"row\" style=\"margin: 0;\">\n" +
            "   <div id=\"chat-window-history\" class=\"row\" style=\"height: 350px;margin: 10px;background: rgba(238, 238, 238, 0.39);overflow-y: auto;padding: 5px;\">\n" +
            "   <div webim-message ng-repeat=\"message in messages\" message=\"message\" close=\"closeMessage(message)\">{{message.msg}}</div>\n" +
            "   </div>\n" +
            "   <div class=\"row\" style=\"margin: 10px;position: relative;\">\n" +
            "   <ul class=\"chat-window-ul\">\n" +
            "   <li class=\"chat-window-li\">\n" +
            "   <i class=\"icon-emoticon-smile\"></i>\n" +
            "   </li>\n" +
            "   <li class=\"chat-window-li chat-window-li-second\">\n" +
            "   <i class=\"icon-picture\"></i>\n" +
            "   </li>\n" +
            "   <li class=\"chat-window-li chat-window-li-second\">\n" +
            "   <i class=\"icon-like\"></i>\n" +
            "   </li>\n" +
            "   </ul>\n" +
            "   <textarea ng-model=\"chat.message\" class=\"form-control chat-window-textarea\" placeholder=\"Type your message\"></textarea>\n" +
            "   <div class=\"chat-window-btn\">\n" +
            "   <button class=\"btn btn-danger btn-xs\" ui-toggle-class=\"btn-success\" ng-click=\"close()\">\n" +
            "   <i class=\"icon-close\"></i>\n" +
            "   <span class=\"text\">关闭</span>\n" +
            "   <i class=\"fa fa-check text-active\"></i>\n" +
            "   <span class=\"text-active\">Success</span>\n" +
            "   </button>\n" +
            "   <button class=\"btn btn-info btn-xs\" ui-toggle-class=\"btn-success\" ng-click=\"sendMessage()\">\n" +
            "   <i class=\"icon-cursor\"></i>\n" +
            "   <span class=\"text\">发送</span>\n" +
            "   <i class=\"fa fa-check text-active\"></i>\n" +
            "   <span class=\"text-active\">Success</span>\n" +
            "   </button>\n" +
            "   </div>\n" +
            "   </div>\n" +
            "   </div>\n");
    }]);
    angular.module('ui.webim.main', [])
        .factory('$data', ['$websocket', '$http', '$q', 'toaster', '$log', '$rootScope', function ($websocket, $http, $q, toaster, $log, $rootScope) {
            var $data = {
                sendToOne: function (obj) {
                    $websocket.send(obj);
                },
                getChatList: function () {
                    var deferred = $q.defer();// 声明延后执行，表示要去监控后面的执行
                    $http.get(WX.constants.path + 'chat/getChatList')
                        .error(function (data, status, headers, config) { // 声明执行失败，即服务器返回错误
                            deferred.reject(data);
                        })
                        .success(function (data, status, headers, config) { // 声明执行成功，即http请求数据成功，可以返回数据了
                            $log.info('getChatList result:', data);
                            data = {
                                all: [
                                    {
                                        id: '1',
                                        name: 'zhangsan1',
                                        age: '12',
                                        signture: 'pages/newframework/static/img/a0.jpg',
                                        // islove: true,
                                        role: '游客',
                                    },
                                ],
                                room: [
                                    {
                                        id: '1',
                                        name: 'room 1',
                                        usercount: 12,
                                        password: '1123',
                                        descript: 'my room',
                                        signture: 'pages/newframework/static/img/a0.jpg'
                                    },
                                ]
                            };
                            deferred.resolve(data); // 返回承诺，这里并不是最终数据，而是访问最终数据的API
                        });
                    return deferred.promise;
                }
            };
            return $data;
        }])
        .controller('ChatDetailController', ['$scope', '$localStorage', '$window', '$nextWindowProvider', '$log', 'toaster', '$data', function ($scope, $localStorage, $window, $nextWindowProvider, $log, toaster, $data) {
            $scope.chat = {};
            $scope.messages = [
                // { type: 'success', msg: 'Well done! You successfully read this important alert message.' },
                // { type: 'info', msg: 'Heads up! This alert needs your attention, but it is not super important.' },
                // { type: 'warning', msg: 'Warning! Best check yo self, you are not looking too good...' }
            ];
            $scope.closeMessage = function (message) {
                delete message;
            };
            var i = 0;
            $scope.$on("SERVERTOUSERS", function (event, data) {
                i++;
                $scope.$apply(function () {
                    if (i % 2) {
                        $scope.messages.push({
                            name: "JvmTotalMemory",
                            signture: 'pages/newframework/static/img/a0.jpg',
                            type: 'info',
                            who: "other",
                            time: moment(data.messageDate).format('YYYY-MM-DD HH:mm:ss'),
                            msg: Math.floor(data.messageData.jvmTotalMemory / 1024 / 1024) + "MB"
                        });
                    } else {
                        $scope.messages.push({
                            name: 'JvmFreeMemory',
                            signture: 'pages/newframework/static/img/a0.jpg',
                            type: 'success',
                            who: "me",
                            time: moment(data.messageDate).format('YYYY-MM-DD HH:mm:ss'),
                            msg: Math.floor(data.messageData.jvmFreeMemory / 1024 / 1024) + "MB"
                        });
                    }
                });
            });
            $scope.sendMessage = function () {
                $data.sendToOne($scope.chat.message);
            }
            $scope.close = function () {
                console.info('close');
            }
        }])
        .controller('ChatListController', ['$scope', '$localStorage', '$window', '$nextWindowProvider', '$log', '$http', '$templateCache', '$data',
            function ($scope, $localStorage, $window, $nextWindowProvider, $log, $http, $templateCache, $data) {

                var promise = $data.getChatList(); // 同步调用，获得承诺接口
                promise.then(function (data) {  // 调用承诺API获取数据 .resolve
                    $scope.chatList = data;
                }, function (data) {  // 处理错误 .reject
                    $scope.chatList = {error: '获取错误！'};
                });

                $scope.openChatDetailWindow = function (user) {
                    var modalInstance = $nextWindowProvider.open({
                        // templateUrl: 'myChatDetailModalContent.html',
                        template: $templateCache.get('chat'),
                        controller: 'ChatDetailController',
                        title: "正在和" + user.name + "聊天",
                        data: user,
                        windowId: 'WebIM-' + user.id,
                        // templateUrl : 'template/modal/chat-detail.html',
                        size: {width: '550px', height: '570px'},
                        resolve: {}
                    });
                    modalInstance.result.then(function (selectedItem) {
                        $scope.selected = selectedItem;
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                }

                $scope.loveThisUser = function (thisUser) {
                    console.info('thisUser', thisUser);
                    thisUser.islove = !thisUser.islove;
                };

                $scope.chatSetting = function () {
                    console.info('chatSetting');
                }
                $scope.chatScrollToTop = function () {
                    $('.chat-all-user').scrollTop(0);
                }
                $scope.chatBell = function () {
                    console.info('chatBell');
                }
                $scope.chatRefresh = function () {
                    console.info('chatRefresh');
                }
                $scope.loveAll = function () {
                    _.each($scope.chatList.all, function (user) {
                        user.islove = true;
                    });
                }
                $scope.unloveAll = function () {
                    _.each($scope.chatList.all, function (user) {
                        user.islove = false;
                    });
                }

                $scope.searchUser = function ($event) {
                    if ($event.keyCode === 13) {
                        var searchInput = $event.currentTarget.value;
                        $scope.chatList.all = _.filter($scope.chatList.all, function (user) {
                            return user.name.indexOf(searchInput) !== -1;
                        })
                    }
                }

                $scope.filterUser = function (item) {
                    return item.islove;
                };
            }])
        .service('$webim', ['$nextWindowProvider', '$templateCache', function ($nextWindowProvider, $templateCache) {
            return {
                open: function (options) {
                    options = {
                        // templateUrl: 'pages/html/tpl/blocks/chat.list.html',
                        // templateUrl: 'webimmain',
                        // templateUrl: 'webimmain1',
                        template: $templateCache.get('main'),
                        controller: 'ChatListController',
                        title: 'Web IM',
                        windowId: 'WebIM-window-list',
                        data: {},
                        size: {width: '350px', height: '657px'},
                        // resolve: {
                        //     items: function () {
                        //         return $scope.items;
                        //     }
                        // }
                    }
                    var windowInstance = $nextWindowProvider.open(options);
                    return windowInstance;
                }
            };
        }]);
})
;
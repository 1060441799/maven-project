/**
 * Created by xm on 2016/4/22.
 */
define([], function () {
    var template =
        "<div class=\"chat-window-message\" ng-class=\"[(message.who || 'me')]\" role=\"alert\">" +
        "<div class=\"\" ng-class=\"['signture-' + (message.who || 'me')]\">" +
        "<span class=\"pull-left thumb-xs\"><img ng-src=\"{{message.signture}}\" class=\"img-circle\"></span>" +
        "<span class=\"name\"><span class='username'>{{message.name}}</span><div class='time'>{{message.time}}</div></span>" +
        "</div>" +
        "<div class='alert' ng-class=\"['messgae-' + (message.who || 'me'), 'alert-' + (message.type || 'warning'), (isMeShowClsoe()) ? 'alert-dismissable' : null]\">\n" +
        "    <button ng-show=\"closeable\" type=\"button\" class=\"close\" ng-click=\"close()\">\n" +
        "        <span style='font-size: 16px;' aria-hidden=\"true\" class='icon-action-redo'></span>\n" +
        "        <span class=\"sr-only\">Close</span>\n" +
        "    </button>\n" +
        "    <div class=\"message-content\" ng-transclude></div>\n" +
        "</div></div>\n";

    angular.module('ui.webim.message', [])
        .controller('ChatmessageController', ['$scope', '$attrs', function ($scope, $attrs) {
            $scope.closeable = 'close' in $attrs;
            this.close = $scope.close;
            $scope.isMeShowClsoe = function () {
                if ($scope.closeable && $scope.message.who === 'me') {
                    return true;
                } else {
                    $scope.closeable = false;
                    return false;
                }
            };
        }])
        .directive('webimMessage', function ($timeout) {
            return {
                restrict: 'EA',
                controller: 'ChatmessageController',
                template: template,
                transclude: true,
                replace: true,
                scope: {
                    close: '&',
                    message: '=message'
                },
                link: function ($scope, element, attrs) {
                    $timeout(function () {
                        element.parent().scrollTop(element.parent()[0].scrollHeight);
                    }, 1);
                }
            };
        })
        .directive('chatmessageDismissOnTimeout', ['$timeout', function ($timeout) {
            return {
                require: 'webimMessage',
                link: function (scope, element, attrs, ctrl) {
                    $timeout(function () {
                        ctrl.close();
                    }, parseInt(attrs.dismissOnTimeout, 10));
                }
            };
        }]);
});


/**
 * Created by xm on 2016/4/22.
 */
define([], function () {
    var template =
        "<li class=\"list-group-item\">" +
        "<span class=\"pull-left thumb-sm m-r\"><img ng-src=\"{{user.signture}}\" class=\"img-circle\"></span>" +
        "<a herf=\"javascript:void(0)\" class=\"text-muted\" ng-click=\"open()\">\n" +
        "   <i class=\"fa fa-comment-o pull-right text-md heart-chat\"></i>\n" +
        "</a>" +
        "<a herf=\"javascript:void(0)\" class=\"text-muted\" ng-click=\"love()\">\n" +
        "   <i class=\"fa pull-right text-md heart-chat\" ng-class=\"[user.islove ? 'fa-heart' : 'fa-heart-o']\"></i>\n" +
        "</a>" +
        "<div class=\"clear\">" +
        "<div><a href>{{user.name}}</a></div>" +
        "<small class=\"text - muted\">{{user.role}}</small>" +
        "</div>" +
        "</li>";

    angular.module('ui.webim.list', [])
        .controller('ChatlistController', ['$scope', '$attrs', function ($scope, $attrs) {
            this.love = $scope.love;
            this.open = $scope.open;
        }])
        .directive('webimList', function ($timeout) {
            return {
                restrict: 'EA',
                controller: 'ChatlistController',
                template: template,
                transclude: true,
                replace: true,
                scope: {
                    user: '=user',
                    love: '&',
                    open: '&'
                },
                link: function ($scope, element, attrs) {
                    if ($scope.$last) {
                        // $timeout(function () {
                        //     element.parent().scrollTop(element.parent()[0].scrollHeight);
                        // }, 1);
                    }
                }
            };
        })
});


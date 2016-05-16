define([], function () {
    angular.module("ui.window", ["ui.window.templates", "ui.window.window"]);
    angular.module("ui.window.templates", ["window-title-template.html"]);
    angular.module("window-title-template.html", []).run(["$templateCache", function ($templateCache) {
        $templateCache.put("title",
            "<div class=\"next-window next-anim\">\n" +
            "   <div class=\"next-title\">{{title}}</div>\n" +
            "   <div modal-transclude class=\"next-window-content\"></div>\n" +
            "   <span class=\"next-close-span\">\n" +
            "       <i class=\"icon-close next-window-title-close\" ng-click=\"close($event)\"></i>\n" +
            "   </span>\n" +
            "   </div>\n");
    }]);
    angular.module('ui.window.window', [])
        .factory('$$windowStackedMap', function () {
            return {
                createNew: function () {
                    var stack = [];
                    return {
                        getWindowByWindowId: function (windowId) {
                            if (windowId) {
                                return _.find(stack, function (win) {
                                    return win.value.windowId === windowId;
                                });
                            } else {
                                return void 0;
                            }
                        },
                        getById: function (id) {
                            for (var i = 0; i < stack.length; i++) {
                                if (id == stack[i].id) {
                                    return stack[i];
                                }
                            }
                        },
                        add: function (key, value, id) {
                            stack.push({
                                id: id,
                                key: key,
                                value: value
                            });
                        },
                        get: function (key) {
                            for (var i = 0; i < stack.length; i++) {
                                if (key == stack[i].key) {
                                    return stack[i];
                                }
                            }
                        },
                        keys: function () {
                            var keys = [];
                            for (var i = 0; i < stack.length; i++) {
                                keys.push(stack[i].key);
                            }
                            return keys;
                        },
                        top: function () {
                            return stack[stack.length - 1];
                        },
                        remove: function (key) {
                            var idx = -1;
                            for (var i = 0; i < stack.length; i++) {
                                if (key == stack[i].key) {
                                    idx = i;
                                    break;
                                }
                            }
                            return stack.splice(idx, 1)[0];
                        },
                        removeTop: function () {
                            return stack.splice(stack.length - 1, 1)[0];
                        },
                        length: function () {
                            return stack.length;
                        }
                    };
                }
            };
        })
        .factory('$windowStack', ['$timeout', '$document', '$compile', '$rootScope', '$$windowStackedMap',
            function ($timeout, $document, $compile, $rootScope, $$windowStackedMap) {
                var openedWindows = $$windowStackedMap.createNew();
                var $windowStack = {};

                function removeModalWindow(windowInstance) {
                    var body = $document.find('body').eq(0);
                    var wi = openedWindows.get(windowInstance).value;
                    openedWindows.remove(wi);
                    removeAfterAnimate(wi.modalDomEl, wi.modalScope, 300, function () {
                        wi.modalScope.$destroy();
                    });
                }

                function removeAfterAnimate(domEl, scope, emulateTime, done) {
                    scope.animate = false;
                    $timeout(afterAnimating);
                    function afterAnimating() {
                        if (afterAnimating.done) {
                            return;
                        }
                        afterAnimating.done = true;
                        domEl.remove();
                        if (done) {
                            done();
                        }
                    }
                }

                var id = 0;
                $windowStack.open = function (windowInstance, windowOptions) {
                    if (openedWindows.getWindowByWindowId(windowOptions.windowId)) {
                        return;
                    }
                    var data = windowOptions.data;
                    console.info('windowOptions data', data);


                    id++;
                    openedWindows.add(windowInstance, {
                        deferred: windowOptions.deferred,
                        modalScope: windowOptions.scope,
                        windowId: windowOptions.windowId,
                    }, id);
                    var body = $document.find('body').eq(0);
                    var angularDomEl = angular.element('<div next-window></div>');
                    var size = windowOptions.size;
                    var key = windowInstance;
                    angularDomEl.attr({
                        'template-url': windowOptions.windowTemplateUrl,
                        'window-class': windowOptions.windowClass,
                        'data-key': id,
                        'title': windowOptions.title,
                        'index': openedWindows.length() - 1,
                    }).html(windowOptions.content);
                    angularDomEl.css({'width': size.width, 'height': size.height});
                    var modalDomEl = $compile(angularDomEl)(windowOptions.scope);
                    openedWindows.top().value.modalDomEl = modalDomEl;
                    body.append(modalDomEl);
                };
                $windowStack.closeById = function (id) {
                    var modalWindow = openedWindows.getById(id);
                    if (modalWindow) {
                        removeModalWindow(modalWindow.key);
                    }
                };
                $windowStack.closeAll = function (reason) {
                    var topModal = this.getTop();
                    while (topModal) {
                        this.dismiss(topModal.key, reason);
                        topModal = this.getTop();
                    }
                };
                $windowStack.getById = function (id) {
                    return openedWindows.getById(id);
                };
                $windowStack.getTop = function () {
                    return openedWindows.top();
                };
                return $windowStack;
            }])
        .directive('nextWindow', ['$templateCache', '$windowStack', '$timeout', function ($templateCache, $windowStack, $timeout) {
            return {
                restrict: 'EA',
                scope: {
                    index: '@',
                    animate: '='
                },
                replace: true,
                transclude: true,
                template: $templateCache.get('title'),
                link: function (scope, element, attrs) {
                    var startX = 0, startY = 0, x = 0, y = 0;
                    var index = 0;
                    var ismove = false;
                    var isdock = false;
                    var isable = false;
                    var moveEnd = function () {
                        var $current = element;
                        var off = $current.offset();
                        var top = off.top;
                        ismove = true;
                        if (top < 10) {
                            isable = true;
                        } else {
                            isable = false;
                        }
                    }
                    try {
                        var $current = element;
                        var off = $current.offset();
                        var top = off.top;
                        $current.find('.next-title').mousedown(function () {
                            ismove = false;
                        });
                        $current.mouseleave(function () {
                            if (ismove && isable) {
                                $current.animate({
                                    top: '-' + (height - 5) + 'px'
                                }, 210, function () {
                                    isdock = true;
                                    ismove = false;
                                });
                            }
                        });
                        $current.mouseenter(function () {
                            if (isdock && isable) {
                                $current.animate({
                                    top: '1px'
                                }, 210, function () {
                                    isdock = false;
                                    ismove = true;
                                });
                            }
                        });
                    } catch (e) {
                    }
                    var win = $(window);
                    var conf = {
                        element: element,
                        setY: 0,
                        moveLayer: function () {
                            var element = conf.element, mgleft = parseInt(element.css('margin-left'));
                            var lefts = parseInt(conf.move.css('left'));
                            mgleft === 0 || (lefts = lefts - mgleft);
                            if (element.css('position') !== 'fixed') {
                                lefts = lefts - element.parent().offset().left;
                                conf.setY = 0;
                            }
                            element.css({left: lefts, top: parseInt(conf.move.css('top')) - conf.setY});
                        }
                    };
                    element.find('.next-title').on('mousedown', function (M) {
                        M.preventDefault();
                        conf.ismove = true;
                        var xx = conf.element.offset().left,
                            yy = conf.element.offset().top,
                            ww = conf.element.outerWidth(),
                            hh = conf.element.outerHeight();
                        $('body').append('<div id="next-moves" class="next-moves" style="left:' + xx + 'px; top:' + yy + 'px; width:' + ww + 'px; height:' + (hh) + 'px; z-index:2147483584"></div>');
                        conf.move = $('#next-moves');
                        conf.moveX = M.pageX - conf.move.position().left;
                        conf.moveY = M.pageY - conf.move.position().top;
                        conf.element.css('position') !== 'fixed' || (conf.setY = win.scrollTop());
                    });
                    $(document).mousemove(function (M) {
                        if (conf.ismove) {
                            var offsetX = M.pageX - conf.moveX, offsetY = M.pageY - conf.moveY;
                            M.preventDefault();
                            if (true) {
                                conf.setY = win.scrollTop();
                                var setRig = win.width() - conf.move.outerWidth(), setTop = conf.setY;
                                offsetX < 0 && (offsetX = 0);
                                offsetX > setRig && (offsetX = setRig);
                                offsetY < setTop && (offsetY = setTop);
                                offsetY > win.height() - conf.move.outerHeight() + conf.setY && (offsetY = win.height() - conf.move.outerHeight() + conf.setY);
                            }
                            conf.move.css({left: offsetX, top: offsetY});
                            offsetX = offsetY = setRig = setTop = null;
                        }
                    }).mouseup(function (M) {
                        try {
                            if (conf.ismove) {
                                conf.moveLayer();
                                conf.move.remove();
                                moveEnd && moveEnd();
                            }
                            conf.ismove = false;
                        } catch (e) {
                            conf.ismove = false;
                        }
                    });
                    var height = element.height();
                    var winWidth = element.outerWidth();
                    var winHeight = element.outerHeight();
                    var pageHeight = document.body.clientHeight;
                    var pageWidth = document.body.clientWidth;
                    var top = (pageHeight - winHeight) / 2;
                    var left = (pageWidth - winWidth) / 2;
                    element.css({'top': top, 'left': left});
                    element.find('.next-window-content').css({'height': winHeight - 42});
                    element.addClass(attrs.windowClass || '');
                    scope.size = attrs.size;
                    scope.title = attrs.title;
                    scope.data = attrs.data;
                    $timeout(function () {
                        scope.animate = true;
                        if (!element[0].querySelectorAll('[autofocus]').length) {
                            element[0].focus();
                        }
                    });
                    scope.close = function (evt) {
                        var id = $(evt.currentTarget).parent().parent().attr('data-key');
                        var modal = $windowStack.getById(id);
                        if (modal && (evt.target === evt.currentTarget)) {
                            evt.preventDefault();
                            evt.stopPropagation();
                            element.addClass('next-anim-out');
                            $timeout(function () {
                                $windowStack.closeById(id);
                            },500);
                        }
                    };
                }
            };
        }])
        .provider('$nextWindowProvider', function () {
            var $nextWindowProvider = {
                $get: ['$injector', '$rootScope', '$q', '$http', '$templateCache', '$controller', '$windowStack',
                    function ($injector, $rootScope, $q, $http, $templateCache, $controller, $windowStack) {
                        var $nextWindow = {};

                        function getTemplatePromise(options) {
                            return options.template ? $q.when(options.template) :
                                $http.get(angular.isFunction(options.templateUrl) ? (options.templateUrl)() : options.templateUrl, {cache: $templateCache})
                                    .then(function (result) {
                                        return result.data;
                                    });
                        }

                        function getResolvePromises(resolves) {
                            var promisesArr = [];
                            angular.forEach(resolves, function (value) {
                                if (angular.isFunction(value) || angular.isArray(value)) {
                                    promisesArr.push($q.when($injector.invoke(value)));
                                }
                            });
                            return promisesArr;
                        }

                        $nextWindow.open = function (options) {
                            var winResultDeferred = $q.defer(), winOpenedDeferred = $q.defer(), windowInstance = {
                                result: winResultDeferred.promise,
                                opened: winOpenedDeferred.promise,
                                close: function (result) {
                                    $windowStack.close(windowInstance, result);
                                },
                                closeAll: function (reason) {
                                    $windowStack.closeAll(windowInstance, reason);
                                }
                            };
                            options = angular.extend({}, options);
                            options.resolve = options.resolve || {};
                            if (!options.template && !options.templateUrl) {
                                throw new Error('参数template和templateUrl二选一！');
                            }
                            var templateAndResolvePromise = $q.all([getTemplatePromise(options)].concat(getResolvePromises(options.resolve)));
                            templateAndResolvePromise.then(function resolveSuccess(tplAndVars) {
                                var winScope = (options.scope || $rootScope).$new();
                                winScope.$close = windowInstance.close;
                                winScope.$closeAll = windowInstance.closeAll;
                                var ctrlInstance, ctrlLocals = {}, resolveIter = 1;
                                if (options.controller) {
                                    ctrlLocals.$scope = winScope;
                                    ctrlLocals.$modalInstance = windowInstance;
                                    angular.forEach(options.resolve, function (value, key) {
                                        ctrlLocals[key] = tplAndVars[resolveIter++];
                                    });
                                    ctrlInstance = $controller(options.controller, ctrlLocals);
                                    if (options.controllerAs) {
                                        winScope[options.controllerAs] = ctrlInstance;
                                    }
                                }
                                $windowStack.open(windowInstance, {
                                    scope: winScope,
                                    deferred: winResultDeferred,
                                    content: tplAndVars[0],
                                    windowClass: options.windowClass,
                                    windowTemplateUrl: options.windowTemplateUrl,
                                    size: options.size || {width: '350px', height: '450px'},
                                    title: options.title,
                                    data: options.data,
                                    windowId: options.windowId,
                                });
                            }, function resolveError(reason) {
                                winResultDeferred.reject(reason);
                            });
                            templateAndResolvePromise.then(function () {
                                winOpenedDeferred.resolve(true);
                            }, function () {
                                winOpenedDeferred.reject(false);
                            });
                            return windowInstance;
                        };
                        return $nextWindow;
                    }]
            };
            return $nextWindowProvider;
        });
});
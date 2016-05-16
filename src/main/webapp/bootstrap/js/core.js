var Core = Core || {};
;+function () {
    var global = this,
        objectPrototype = Object.prototype,
        toString = objectPrototype.toString,
        enumerables = [
            'valueOf',
            'toLocaleString',
            'toString',
            'constructor'
        ],
        emptyFn = function () {
        },
        privateFn = function () {
        },
        identityFn = function (o) {
            return o;
        },
        callOverrideParent = function () {
            var method = callOverrideParent.caller.caller;
            return method.$owner.prototype[method.$name].apply(this, arguments);
        },
        manifest = Core.manifest || {},
        i,
        iterableRe = /\[object\s*(?:Array|Arguments|\w*Collection|\w*List|HTML\s+document\.all\s+class)\]/,
        MSDateRe = /^\\?\/Date\(([-+])?(\d+)(?:[+-]\d{4})?\)\\?\/$/;
    Core.global = global;

    emptyFn.$nullFn = identityFn.$nullFn = emptyFn.$emptyFn = identityFn.$identityFn = privateFn.$nullFn = true;
    privateFn.$privacy = 'framework';

    Core['suspendLayouts'] = Core['resumeLayouts'] = emptyFn;
    for (i in {
        toString: 1
    }) {
        enumerables = null;
    }

    Core.enumerables = enumerables;

    Core.apply = function (object, config, defaults) {
        if (defaults) {
            Core.apply(object, defaults);
        }
        if (object && config && typeof config === 'object') {
            var i, j, k;
            for (i in config) {
                object[i] = config[i];
            }
            if (enumerables) {
                for (j = enumerables.length; j--;) {
                    k = enumerables[j];
                    if (config.hasOwnProperty(k)) {
                        object[k] = config[k];
                    }
                }
            }
        }
        return object;
    };
    Core.apply(Core, {
        idSeed: 0,
        idPrefix: 'core-',
        isSecure: /^https/i.test(window.location.protocol),
        enableGarbageCollector: false,
        enableListenerCollection: true,
        name: Core.sandboxName || 'Core',
        privateFn: privateFn,
        emptyFn: emptyFn,
        identityFn: identityFn,
        frameStartTime: +new Date(),
        manifest: manifest,
        debugConfig: Core.debugConfig || manifest.debug || {
            hooks: {
                '*': true
            }
        },
        validIdRe: /^[a-z_][a-z0-9\-_]*$/i,
        BLANK_IMAGE_URL: 'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==',
        makeIdSelector: function (id) {
            if (!Core.validIdRe.test(id)) {
                Core.Error.raise('Invalid id selector: "' + id + '"');
            }
            return '#' + id;
        },
        id: function (o, prefix) {
            if (o && o.id) {
                return o.id;
            }
            var id = (prefix || Core.idPrefix) + (++Core.idSeed);
            if (o) {
                o.id = id;
            }
            return id;
        },
        returnId: function (o) {
            return o.getId();
        },
        returnTrue: function () {
            return true;
        },
        emptyString: new String(),
        $eventNameMap: {},
        $vendorEventRe: /^(Moz.+|MS.+)/,
        canonicalEventName: function (name) {
            return Core.$eventNameMap[name] || (Core.$eventNameMap[name] = (Core.$vendorEventRe.test(name) ? name : name.toLowerCase()));
        },
        applyIf: function (object, config) {
            var property;
            if (object) {
                for (property in config) {
                    if (object[property] === undefined) {
                        object[property] = config[property];
                    }
                }
            }
            return object;
        },
        now: (global.performance && global.performance.now) ? function () {
            return performance.now();
        } : (Date.now || (Date.now = function () {
            return +new Date();
        })),
        destroy: function () {
            var ln = arguments.length,
                i, arg;
            for (i = 0; i < ln; i++) {
                arg = arguments[i];
                if (arg) {
                    if (Core.isArray(arg)) {
                        this.destroy.apply(this, arg);
                    } else if (Core.isFunction(arg.destroy)) {
                        arg.destroy();
                    }
                }
            }
            return null;
        },
        destroyMembers: function (object) {
            for (var ref, name,
                     i = 1,
                     a = arguments,
                     len = a.length; i < len; i++) {
                ref = object[name = a[i]];

                if (ref != null) {
                    object[name] = Core.destroy(ref);
                }
            }
        },
        override: function (target, overrides) {
            if (target.$isClass) {
                target.override(overrides);
            } else if (typeof target == 'function') {
                Core.apply(target.prototype, overrides);
            } else {
                var owner = target.self,
                    name, value;
                if (owner && owner.$isClass) {

                    for (name in overrides) {
                        if (overrides.hasOwnProperty(name)) {
                            value = overrides[name];
                            if (typeof value === 'function') {
                                if (owner.$className) {
                                    value.name = owner.$className + '#' + name;
                                }
                                value.$name = name;
                                value.$owner = owner;
                                value.$previous = target.hasOwnProperty(name) ? target[name] :
                                    callOverrideParent;
                            }

                            target[name] = value;
                        }
                    }
                } else {
                    Core.apply(target, overrides);
                }
            }
            return target;
        },
        valueFrom: function (value, defaultValue, allowBlank) {
            return Core.isEmpty(value, allowBlank) ? defaultValue : value;
        },
        isEmpty: function (value, allowEmptyString) {
            return (value == null) || (!allowEmptyString ? value === '' : false) || (Core.isArray(value) && value.length === 0);
        },
        isArray: ('isArray' in Array) ? Array.isArray : function (value) {
            return toString.call(value) === '[object Array]';
        },
        isDate: function (value) {
            return toString.call(value) === '[object Date]';
        },
        isMSDate: function (value) {
            if (!Core.isString(value)) {
                return false;
            }
            return MSDateRe.test(value);
        },
        isObject: (toString.call(null) === '[object Object]') ? function (value) {
            return value !== null && value !== undefined && toString.call(value) === '[object Object]' && value.ownerDocument === undefined;
        } : function (value) {
            return toString.call(value) === '[object Object]';
        },
        isSimpleObject: function (value) {
            return value instanceof Object && value.constructor === Object;
        },
        isPrimitive: function (value) {
            var type = typeof value;
            return type === 'string' || type === 'number' || type === 'boolean';
        },
        isFunction: (typeof document !== 'undefined' && typeof document.getElementsByTagName('body') === 'function') ? function (value) {
            return !!value && toString.call(value) === '[object Function]';
        } : function (value) {
            return !!value && typeof value === 'function';
        },

        isNumber: function (value) {
            return typeof value === 'number' && isFinite(value);
        },

        isNumeric: function (value) {
            return !isNaN(parseFloat(value)) && isFinite(value);
        },

        isString: function (value) {
            return typeof value === 'string';
        },

        isBoolean: function (value) {
            return typeof value === 'boolean';
        },

        isElement: function (value) {
            return value ? value.nodeType === 1 : false;
        },

        isTextNode: function (value) {
            return value ? value.nodeName === "#text" : false;
        },

        isDefined: function (value) {
            return typeof value !== 'undefined';
        },

        isIterable: function (value) {
            if (!value || typeof value.length !== 'number' || typeof value === 'string' || Core.isFunction(value)) {
                return false;
            }
            if (!value.propertyIsEnumerable) {
                return !!value.item;
            }
            if (value.hasOwnProperty('length') && !value.propertyIsEnumerable('length')) {
                return true;
            }
            return iterableRe.test(toString.call(value));
        },
        clone: function (item) {
            if (item === null || item === undefined) {
                return item;
            }
            if (item.nodeType && item.cloneNode) {
                return item.cloneNode(true);
            }
            var type = toString.call(item),
                i, j, k, clone, key;

            if (type === '[object Date]') {
                return new Date(item.getTime());
            }
            if (type === '[object Array]') {
                i = item.length;
                clone = [];
                while (i--) {
                    clone[i] = Core.clone(item[i]);
                }
            }
            else if (type === '[object Object]' && item.constructor === Object) {
                clone = {};
                for (key in item) {
                    clone[key] = Core.clone(item[key]);
                }
                if (enumerables) {
                    for (j = enumerables.length; j--;) {
                        k = enumerables[j];
                        if (item.hasOwnProperty(k)) {
                            clone[k] = item[k];
                        }
                    }
                }
            }
            return clone || item;
        },

        getUniqueGlobalNamespace: function () {
            var uniqueGlobalNamespace = this.uniqueGlobalNamespace,
                i;
            if (uniqueGlobalNamespace === undefined) {
                i = 0;
                do {
                    uniqueGlobalNamespace = 'ExtBox' + (++i);
                } while (global[uniqueGlobalNamespace] !== undefined);
                global[uniqueGlobalNamespace] = Core;
                this.uniqueGlobalNamespace = uniqueGlobalNamespace;
            }
            return uniqueGlobalNamespace;
        },

        functionFactoryCache: {},
        cacheableFunctionFactory: function () {
            var me = this,
                args = Array.prototype.slice.call(arguments),
                cache = me.functionFactoryCache,
                idx, fn, ln;
            if (Core.isSandboxed) {
                ln = args.length;
                if (ln > 0) {
                    ln--;
                    args[ln] = 'var Core=window.' + Core.name + ';' + args[ln];
                }
            }
            idx = args.join('');
            fn = cache[idx];
            if (!fn) {
                fn = Function.prototype.constructor.apply(Function.prototype, args);
                cache[idx] = fn;
            }
            return fn;
        },
        functionFactory: function () {
            var args = Array.prototype.slice.call(arguments),
                ln;
            if (Core.isSandboxed) {
                ln = args.length;
                if (ln > 0) {
                    ln--;
                    args[ln] = 'var Core=window.' + Core.name + ';' + args[ln];
                }
            }
            return Function.prototype.constructor.apply(Function.prototype, args);
        },

        Logger: {
            log: function (message, priority) {
                if (message && global.console) {
                    if (!priority || !(priority in global.console)) {
                        priority = 'log';
                    }
                    message = '[' + priority.toUpperCase() + '] ' + message;
                    global.console[priority](message);
                }
            },
            verbose: function (message) {
                this.log(message, 'verbose');
            },
            info: function (message) {
                this.log(message, 'info');
            },
            warn: function (message) {
                this.log(message, 'warn');
            },
            error: function (message) {
                throw new Error(message);
            },
            deprecate: function (message) {
                this.log(message, 'warn');
            }
        } || {
            verbose: emptyFn,
            log: emptyFn,
            info: emptyFn,
            warn: emptyFn,
            error: function (message) {
                throw new Error(message);
            },
            deprecate: emptyFn
        },
    });

    Core.apply(Core, {
        namespaceRewrites: [
            {
                from: 'Core.',
                to: Core
            }
        ],
        enableNamespaceParseCache: true,
        namespaceParseCache: {},
        parseNamespace: function (namespace) {
            if (typeof namespace !== 'string') {
                throw new Error("[Ext.ClassManager] Invalid namespace, must be a string");
            }
            var cache = this.namespaceParseCache,
                parts, rewrites, root, name, rewrite, from, to, i, ln;
            if (this.enableNamespaceParseCache) {
                if (cache.hasOwnProperty(namespace)) {
                    return cache[namespace];
                }
            }
            parts = [];
            rewrites = this.namespaceRewrites;
            root = global;
            name = namespace;
            for (i = 0 , ln = rewrites.length; i < ln; i++) {
                rewrite = rewrites[i];
                from = rewrite.from;
                to = rewrite.to;
                if (name === from || name.substring(0, from.length) === from) {
                    name = name.substring(from.length);
                    if (typeof to !== 'string') {
                        root = to;
                    } else {
                        parts = parts.concat(to.split('.'));
                    }
                    break;
                }
            }
            parts.push(root);
            parts = parts.concat(name.split('.'));
            if (this.enableNamespaceParseCache) {
                cache[namespace] = parts;
            }
            return parts;
        },
        setNamespace: function (name, value) {
            var root = global,
                parts = this.parseNamespace(name),
                ln = parts.length - 1,
                leaf = parts[ln],
                i, part;
            for (i = 0; i < ln; i++) {
                part = parts[i];
                if (typeof part !== 'string') {
                    root = part;
                } else {
                    if (!root[part]) {
                        root[part] = {};
                    }
                    root = root[part];
                }
            }
            root[leaf] = value;
            return root[leaf];
        },
        createNamespaces: function () {
            var root = global,
                parts, part, i, j, ln, subLn;
            for (i = 0 , ln = arguments.length; i < ln; i++) {
                parts = this.parseNamespace(arguments[i]);
                for (j = 0 , subLn = parts.length; j < subLn; j++) {
                    part = parts[j];
                    if (typeof part !== 'string') {
                        root = part;
                    } else {
                        if (!root[part]) {
                            root[part] = {};
                        }
                        root = root[part];
                    }
                }
            }
            return root;
        },
        // Apply around advice to all matching functions in the given namespaces
        around: function (pointcut, advice, namespaces) {
            // if no namespaces are supplied, use a trick to determine the global ns
            if (namespaces == undefined || namespaces.length == 0)
                namespaces = [(function () {
                    return this;
                }).call()];
            // loop over all namespaces
            for (var i in namespaces) {
                var ns = namespaces[i];
                for (var member in ns) {
                    if (typeof ns[member] == 'function' && member.match(pointcut)) {
                        (function (fn, fnName, ns) {
                            // replace the member fn slot with a wrapper which calls
                            // the 'advice' Function
                            ns[fnName] = function () {
                                return advice.call(ns, {
                                    fn: fn,
                                    fnName: fnName,
                                    arguments: arguments
                                });
                            };
                        })(ns[member], member, ns);
                    }
                }
            }
        },
        next: function (f) {
            return f.fn.apply(this, f.arguments);
        },
        before: function (pointcut, advice, namespaces) {
            this.around(pointcut, function (f) {
                advice.apply(this, f.arguments);
                return Core.next(f)
            }, namespaces);
        },
        after: function (pointcut, advice, namespaces) {
            this.around(pointcut, function (f) {
                var ret = Core.next(f);
                advice.apply(this, f.arguments);
                return ret;
            }, namespaces);
        }
    })
}();
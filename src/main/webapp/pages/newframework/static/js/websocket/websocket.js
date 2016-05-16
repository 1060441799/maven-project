define([], function () {
    var websocket = new SockJS("/websocket");
    // websocket.onopen = function (evnt) {
    // };
    // websocket.onmessage = function (evnt) {
    // };
    // websocket.onerror = function (evnt) {
    // };
    // websocket.onclose = function (evnt) {
    // }
    return websocket;
});
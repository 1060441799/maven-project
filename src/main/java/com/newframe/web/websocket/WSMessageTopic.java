package com.newframe.web.websocket;

/**
 * Created by xm on 2016/4/25.
 */
public interface WSMessageTopic {
    Integer SERVERTOONEUSER = 1;
    Integer SERVERTOUSERS = 2;
    Integer SERVERTOROOM = 3;
    Integer USERTOUSER = 4;
    Integer USERTOROOM = 5;
}

package com.newframe.web.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketMessage;

/**
 * Created by xm on 2016/4/25.
 */
public class MessageEvent extends ApplicationEvent {

    public MessageEvent(WebSocketMessage source) {
        super(source);
    }
}

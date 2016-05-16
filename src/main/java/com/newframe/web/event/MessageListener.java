package com.newframe.web.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by xm on 2016/4/25.
 */
@Component
public class MessageListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MessageEvent) {
            System.out.println(event.getSource());
        }
    }
}
